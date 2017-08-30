package org.piphonom.arepa.model;

import org.piphonom.arepa.dao.DeviceGroupDAO;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.CertificateGenerationException;
import org.piphonom.arepa.exceptions.GroupExistsException;
import org.piphonom.arepa.exceptions.GroupNotExistsException;
import org.piphonom.arepa.helpers.pki.GroupCAGenerator;
import org.piphonom.arepa.service.CAGenerator;
import org.piphonom.arepa.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.CertificateEncodingException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * Created by piphonom
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private DeviceGroupDAO groupDAO;

    /**
     * TODO: make MBean to control these values
     */
    private int DEFAULT_GROUP_CA_DAYS_VALIDITY = 365;
    private int DEFAULT_CA_KEY_SIZE = 2048;
    private int DEFAULT_CA_GENERATORS_TASKS_NUMBER = 3;

    private ThreadFactory daemonThreadFactory = r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        };

    /**
    * TODO: Due to key generation is long task move CA generation into separate threads
     * Problems: in case of generation error group will be in inconsistent state
    * */
    private final Queue<GroupDNSource> dnSourceQueue = new ArrayBlockingQueue<GroupDNSource>(DEFAULT_CA_GENERATORS_TASKS_NUMBER);
    private final ExecutorService caGenerationSchedulerTask = Executors.newFixedThreadPool(1, daemonThreadFactory);
    private final ExecutorService caGenerationTasks = Executors.newFixedThreadPool(DEFAULT_CA_GENERATORS_TASKS_NUMBER, daemonThreadFactory);

    @Override
    public DeviceGroup save(DeviceGroup group) {
        return groupDAO.save(group);
    }

    @Override
    public DeviceGroup createGroup(Customer customer, String groupName) throws GroupExistsException, CertificateGenerationException {
        List<DeviceGroup> customerGroups = customer.getGroups();
        if (customerGroups.stream().filter(group -> group.getName().equals(groupName)).collect(Collectors.toList()).size() != 0) {
            throw new GroupExistsException();
        }
        DeviceGroup newGroup = new DeviceGroup();
        newGroup.setName(groupName);
        newGroup.setDeactivated(false);
        newGroup.setOwnerCustomerRef(customer);
        CAGenerator caGenerator = createGroupCAGenerator(customer.getEmail(), groupName);
        caGenerator.generate();
        try {
            newGroup.setCertificateCA(caGenerator.getCertificate().getEncoded());
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
            throw new CertificateGenerationException("Internal.error");
        }
        return newGroup;
    }

    @Override
    public DeviceGroup getGroupByName(Customer customer, String groupName) throws GroupNotExistsException {
        DeviceGroup group = groupDAO.findByOwnerCustomerRefAndName(customer, groupName);
        if (group != null)
            return group;
        else
            throw new GroupNotExistsException();
    }

    private CAGenerator createGroupCAGenerator(String customerEmail, String groupName) {

            return new GroupCAGenerator()
                    .setCustomerEmail(customerEmail)
                    .setGroupName(groupName)
                    .setDaysValidity(DEFAULT_GROUP_CA_DAYS_VALIDITY)
                    .setKeySizeInBits(DEFAULT_CA_KEY_SIZE);
    }

    private class GroupDNSource {
        String customerEmail;
        String groupName;
    }
}

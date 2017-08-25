package org.piphonom.arepa.model;

import org.piphonom.arepa.dao.DeviceGroupDAO;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.GroupExistsException;
import org.piphonom.arepa.exceptions.GroupNotExistsException;
import org.piphonom.arepa.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piphonom
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    DeviceGroupDAO groupDAO;

    @Override
    public DeviceGroup save(DeviceGroup group) {
        return groupDAO.save(group);
    }

    @Override
    public DeviceGroup createGroup(Customer customer, String groupName) throws GroupExistsException {
        List<DeviceGroup> customerGroups = customer.getGroups();
        if (customerGroups.stream().filter(group -> group.getName().equals(groupName)).collect(Collectors.toList()).size() != 0) {
            throw new GroupExistsException();
        }
        DeviceGroup newGroup = new DeviceGroup();
        newGroup.setName(groupName);
        newGroup.setDeactivated(false);
        /**
        * TODO: Add group CA generation
        * */
        newGroup.setOwnerCustomerRef(customer);
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
}

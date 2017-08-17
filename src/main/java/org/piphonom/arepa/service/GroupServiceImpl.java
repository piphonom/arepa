package org.piphonom.arepa.service;

import org.piphonom.arepa.dao.DeviceGroupDAO;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.GroupExistsException;
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
    public void createNewGroup(Customer customer, String groupName) throws GroupExistsException {
        List<DeviceGroup> customerGroups = groupDAO.findByOwnerCustomerRef(customer);
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
        groupDAO.save(newGroup);
    }

    @Override
    public List<DeviceGroup> getGroups(Customer customer) {
        return groupDAO.findByOwnerCustomerRef(customer);
    }
}

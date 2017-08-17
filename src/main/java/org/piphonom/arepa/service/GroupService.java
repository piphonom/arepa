package org.piphonom.arepa.service;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.GroupExistsException;

import java.util.List;

/**
 * Created by piphonom
 */
public interface GroupService {
    void createNewGroup(Customer customer, String groupName) throws GroupExistsException;
    List<DeviceGroup> getGroups(Customer customer);
}

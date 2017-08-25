package org.piphonom.arepa.service;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.GroupExistsException;
import org.piphonom.arepa.exceptions.GroupNotExistsException;

/**
 * Created by piphonom
 */
public interface GroupService {
    /**
     * Saves a given group on the storage. Use the returned instance for further operations as the save operation might have changed the
     * group instance completely.
     * @param group
     * @return saved group.
     */
    DeviceGroup save(DeviceGroup group);

    /**
     * Creates the new group with specified name for customer. Group is not saved on the storage.
     * @param customer
     * @param groupName
     * @return new group
     * @throws GroupExistsException if group with this name already exists for the customer
     */
    DeviceGroup createGroup(Customer customer, String groupName) throws GroupExistsException;

    /**
     * Get group with specified name
     * @param customer
     * @param groupName
     * @return group
     * @throws GroupNotExistsException if group doesn't exist
     */
    DeviceGroup getGroupByName(Customer customer, String groupName) throws GroupNotExistsException;
}

package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by piphonom
 */
public interface DeviceGroupDAO extends CrudRepository<DeviceGroup, Integer> {
    public List<DeviceGroup> findByOwnerCustomerRef(Customer customer);
}

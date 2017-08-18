package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by piphonom
 */
@Transactional
public interface DeviceGroupDAO extends CrudRepository<DeviceGroup, Integer> {
    public List<DeviceGroup> findByOwnerCustomerRef(Customer customer);
}

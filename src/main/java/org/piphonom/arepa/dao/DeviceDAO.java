package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by piphonom
 */
public interface DeviceDAO extends CrudRepository<Device, Integer> {
}

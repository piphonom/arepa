package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.Device;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by piphonom
 */
@Transactional
public interface DeviceDAO extends CrudRepository<Device, Integer> {
}

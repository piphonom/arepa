package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.DeviceToken;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by piphonom
 */
@Transactional
public interface DeviceTokenDAO extends CrudRepository<DeviceToken, Integer> {
}

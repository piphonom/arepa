package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.DeviceToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by piphonom
 */
public interface DeviceTokenDAO extends CrudRepository<DeviceToken, Integer> {
}

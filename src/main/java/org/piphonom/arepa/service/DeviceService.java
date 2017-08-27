package org.piphonom.arepa.service;

import org.piphonom.arepa.dao.dataset.Device;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.DeviceExistsException;
import org.piphonom.arepa.exceptions.DeviceNotExistsException;

/**
 * Created by piphonom
 */
public interface DeviceService {

    /**
     * Saves a given device on the storage. Use the returned instance for further operations as the save operation might have changed the
     * device instance completely.
     * @param device
     * @return saved device
     */
    Device save(Device device);

    /**
     * Creates new device. Device is not saved on the storage
     * @param group group where device will be created
     * @param name device name in group namespace
     * @return created device
     * @throws DeviceExistsException
     */
    Device createDevice(DeviceGroup group, String name) throws DeviceExistsException;

    /**
     * Retrieves device by it's public ID
     * @param pubId
     * @return
     * @throws DeviceNotExistsException
     */
    Device getDeviceByPublicId(String pubId) throws DeviceNotExistsException;

    /**
     * Register device in the group
     * @param pubId
     * @return
     * @throws DeviceNotExistsException
     */
    Device registerDevice(String pubId) throws RuntimeException;
}

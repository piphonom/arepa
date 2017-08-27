package org.piphonom.arepa.model;


import org.piphonom.arepa.dao.DeviceDAO;
import org.piphonom.arepa.dao.dataset.Device;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.DeviceExistsException;
import org.piphonom.arepa.exceptions.DeviceNotExistsException;
import org.piphonom.arepa.service.DeviceService;
import org.piphonom.arepa.service.PKIService;
import org.piphonom.arepa.service.PubIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.stream.Collectors;

/**
 * Created by piphonom
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    DeviceDAO deviceDAO;

    @Autowired
    PubIdGenerator pubIdGenerator;

    @Autowired
    PKIService pkiService;

    @Override
    public Device save(Device device) {
        return deviceDAO.save(device);
    }

    @Override
    public Device createDevice(DeviceGroup group, String name) throws DeviceExistsException {
        if (group.getDevices().stream().filter(device -> device.getName().equals(name)).collect(Collectors.toList()).size() != 0) {
            throw new DeviceExistsException();
        }
        Device device = new Device();
        device.setName(name);
        device.setCreationTime(new Timestamp(System.currentTimeMillis()));
        device.setPubId(pubIdGenerator.createNew());
        device.setState(Device.State.CREATED);
        device.setDeviceGroupRef(group);
        return device;
    }

    @Override
    public Device getDeviceByPublicId(String pubId) throws DeviceNotExistsException {
        Device device = deviceDAO.getByPubId(pubId);
        if (device == null) {
            throw new DeviceNotExistsException();
        }
        return device;
    }

    @Override
    public Device registerDevice(String pubId) throws RuntimeException {
        Device device = getDeviceByPublicId(pubId);
        /* TODO: generate certificate */
        return device;
    }
}

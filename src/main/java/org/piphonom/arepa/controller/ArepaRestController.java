package org.piphonom.arepa.controller;

import org.piphonom.arepa.dao.dataset.Device;
import org.piphonom.arepa.rest.request.DevicePointDescriptor;
import org.piphonom.arepa.rest.response.DeviceRegistrationResponse;
import org.piphonom.arepa.rest.response.Response;
import org.piphonom.arepa.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by piphonom
 */
@RestController
public class ArepaRestController {

    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/point/name/{pubID}", method = RequestMethod.GET)
    public Response getDevicePointName(@PathVariable String pubId) {
        Device device = deviceService.getDeviceByPublicId(pubId);
        Response response = new DeviceRegistrationResponse();
        return response;
    }

    @RequestMapping(value = "/point/register/{pubID}", method = RequestMethod.PUT)
    public Response registerDevicePoint(@PathVariable String pubId, @RequestBody DevicePointDescriptor descriptor) {
        byte[] certificate = deviceService.registerDevice(pubId, descriptor.getCsr());
        DeviceRegistrationResponse response = new DeviceRegistrationResponse();
        response.setCertificate(certificate);
        return response;
    }
}

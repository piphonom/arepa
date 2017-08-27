package org.piphonom.arepa.controller;

import org.piphonom.arepa.dao.dataset.Device;
import org.piphonom.arepa.rest.request.DevicePointDescriptor;
import org.piphonom.arepa.rest.response.DeviceRegistrationResponse;
import org.piphonom.arepa.rest.response.Response;
import org.piphonom.arepa.service.DeviceService;
import org.piphonom.arepa.service.PKIService;
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
    @ResponseBody
    public Response getDevicePointName(@PathVariable String pubId) throws RuntimeException {
        Device device = deviceService.getDeviceByPublicId(pubId);
        Response response = new DeviceRegistrationResponse();
        return response;
    }

    @RequestMapping(value = "/point/register/{pubID}", method = RequestMethod.PUT)
    @ResponseBody
    public Response registerDevicePoint(@PathVariable String pubId, @RequestBody DevicePointDescriptor descriptor) throws RuntimeException {
        Device device = deviceService.registerDevice(pubId);
        /* TODO: set certificate into response */
        DeviceRegistrationResponse response = new DeviceRegistrationResponse();
        return response;
    }
}

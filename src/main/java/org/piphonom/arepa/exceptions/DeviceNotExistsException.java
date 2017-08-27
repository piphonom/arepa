package org.piphonom.arepa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by piphonom
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Device.notExists")
public class DeviceNotExistsException extends RuntimeException{
    public DeviceNotExistsException() {
        super("Device.notExists");
    }

    public DeviceNotExistsException(String message) {
        super(message);
    }
}

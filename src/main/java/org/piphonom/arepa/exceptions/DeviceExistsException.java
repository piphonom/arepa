package org.piphonom.arepa.exceptions;

/**
 * Created by piphonom
 */
public class DeviceExistsException extends Exception {
    public DeviceExistsException() {
        super("Device.exists");
    }

    public DeviceExistsException(String message) {
        super(message);
    }
}

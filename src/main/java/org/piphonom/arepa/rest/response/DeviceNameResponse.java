package org.piphonom.arepa.rest.response;

/**
 * Created by piphonom
 */
public class DeviceNameResponse extends Response {
    private String name;

    public DeviceNameResponse() {
    }

    public DeviceNameResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package org.piphonom.arepa.web;

/**
 * Created by piphonom
 */
public class NewDeviceForm {
    private String groupName;
    private String deviceName;

    public NewDeviceForm() {}

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

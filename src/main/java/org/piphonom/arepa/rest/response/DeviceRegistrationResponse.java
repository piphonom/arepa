package org.piphonom.arepa.rest.response;

/**
 * Created by piphonom
 */
public class DeviceRegistrationResponse extends Response {
    private String certificate;

    public DeviceRegistrationResponse() {
    }

    public DeviceRegistrationResponse(String certificate) {
        this.certificate = certificate;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}

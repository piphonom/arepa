package org.piphonom.arepa.rest.response;

import org.apache.tomcat.util.codec.binary.Base64;

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

    public byte[] getCertificate() {
        return Base64.decodeBase64(certificate);
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = Base64.encodeBase64String(certificate);
    }
}

package org.piphonom.arepa.rest.request;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Created by piphonom
 */
public class DevicePointDescriptor {
    /* Certificate signing request, if needed */
    private String csr;

    public byte[] getCsr() {
        return Base64.decodeBase64(csr);
    }

    public void setCsr(byte[] csr) {
        this.csr = Base64.encodeBase64String(csr);
    }
}

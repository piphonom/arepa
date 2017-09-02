package org.piphonom.arepa.helpers.pki;

import org.apache.commons.lang3.ArrayUtils;

import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * Created by lesha on 01.06.17.
 */
public final class PKCS12ContentSaver {
    PrivateKey privateKey = null;
    Certificate[] certificateChain = null;
    String keyAlias = null;
    String storePassword = null;
    String keyPassword = null;


    public PKCS12ContentSaver setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public PKCS12ContentSaver setCertificateChain(Certificate[] certificateChain) {
        this.certificateChain = certificateChain;
        return this;
    }

    public PKCS12ContentSaver setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
        return this;
    }

    public PKCS12ContentSaver setStorePassword(String storePassword) {
        this.storePassword = storePassword;
        return this;
    }

    public PKCS12ContentSaver setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
        return this;
    }

    public boolean saveToStream(OutputStream outputStream) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            /* order of certs should be like this: User Cert, Sub CA Cert, Root CA Cert */
            ArrayUtils.reverse(certificateChain);
            keyStore.setKeyEntry(keyAlias, privateKey,
                    keyPassword.toCharArray(), certificateChain);
            //fos = new java.io.FileOutputStream(location);
            keyStore.store(outputStream, storePassword.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

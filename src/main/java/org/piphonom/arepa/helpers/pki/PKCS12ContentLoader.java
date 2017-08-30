package org.piphonom.arepa.helpers.pki;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * Created by piphonom
 */
public class PKCS12ContentLoader {
    private PrivateKey privateKey;
    private Certificate[] certChain;

    private PKCS12ContentLoader() {}

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public Certificate[] getCertChain() {
        return certChain;
    }

    private static PKCS12ContentLoader getInstance(InputStream readStream, String alias, String storePassword, String keyPassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(readStream, storePassword.toCharArray());
            PKCS12ContentLoader storeContent = new PKCS12ContentLoader();
            if (keyStore.isKeyEntry(alias)) {
                storeContent.privateKey = (PrivateKey) keyStore.getKey(alias, keyPassword.toCharArray());
                storeContent.certChain = /*(X509Certificate[])*/keyStore.getCertificateChain(alias);
                return storeContent;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Abstract PKCS12 KeyStore content loader
     * */
    public abstract static class StoreContentLoader {
        protected String keyAlias = null;
        protected String storePassword = null;
        protected String keyPassword = null;

        public StoreContentLoader setKeyAlias(String keyAlias) {
            this.keyAlias = keyAlias;
            return this;
        }

        public StoreContentLoader setStorePassword(String storePassword) {
            this.storePassword = storePassword;
            return this;
        }

        public StoreContentLoader setKeyPassword(String keyPassword) {
            this.keyPassword = keyPassword;
            return this;
        }

        public abstract PKCS12ContentLoader load();
    }

    /**
     * PKCS12 KeyStore content loader from file
     */
    public static class LoaderFromFile extends StoreContentLoader {
        private String storeLocation = null;

        public LoaderFromFile setStoreLocation(String location) {
            this.storeLocation = location;
            return this;
        }

        @Override
        public PKCS12ContentLoader load() {
            try {
                InputStream readStream = new FileInputStream(storeLocation);
                return PKCS12ContentLoader.getInstance(readStream, keyAlias, storePassword, keyPassword);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * PKCS12 KeyStore content loader from byte stream
     */
    public static class LoaderFromStream extends StoreContentLoader {
        private InputStream storeInputStream = null;

        public LoaderFromStream setStoreInputStream(InputStream readStream) {
            this.storeInputStream = readStream;
            return this;
        }

        @Override
        public PKCS12ContentLoader load() {
            return PKCS12ContentLoader.getInstance(storeInputStream, keyAlias, storePassword, keyPassword);
        }
    }
}

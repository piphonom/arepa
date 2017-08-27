package org.piphonom.arepa.model;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.piphonom.arepa.exceptions.LoadRootCAException;
import org.piphonom.arepa.exceptions.SignCSRException;
import org.piphonom.arepa.service.PKIService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Properties;

/**
 * Created by piphonom
 */
@Service
public class PKIServiceImpl implements PKIService {

    private static final String PROVIDER_NAME = BouncyCastleProvider.PROVIDER_NAME;
    private static final String SIGNATURE_ALGORITHM = "SHA256WithRSAEncryption";

    private PrivateKey rootCAKey;
    private X509Certificate rootCACertificate;

    public PKIServiceImpl() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Properties properties = new Properties();
            properties.load(inputStream);
            String keyStoreLoaction = properties.getProperty("org.piphonom.arepa.rootca.location");
            String keyStoreAlias = properties.getProperty("org.piphonom.arepa.rootca.alias");
            String storePassword = properties.getProperty("org.piphonom.arepa.rootca.storepass");
            String keyPassword = properties.getProperty("org.piphonom.arepa.rootca.keypass");
            loadRootCA(keyStoreLoaction, keyStoreAlias, storePassword, keyPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] signCSR(byte[] caKey, byte[] csr, long daysValidity) throws SignCSRException {
        /* TODO: implement */
        return new byte[0];
    }

    private KeyStoreContentMapper loadStoreContent(String keyStoreLocation, String alias, String storePassword, String keyPassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream readStream = new FileInputStream(keyStoreLocation);
            keyStore.load(readStream, storePassword.toCharArray());
            KeyStoreContentMapper storeContent = new KeyStoreContentMapper();
            if (keyStore.isKeyEntry(alias)) {
                storeContent.privateKey = (PrivateKey) keyStore.getKey(alias, keyPassword.toCharArray());
                storeContent.certChain = keyStore.getCertificateChain(alias);
                return storeContent;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadRootCA(String keyStoreLocation, String alias, String storePassword, String keyPassword) throws LoadRootCAException {
        KeyStoreContentMapper storeContent = loadStoreContent(keyStoreLocation, alias, storePassword, keyPassword);
        if (storeContent == null) {
            throw new LoadRootCAException();
        }
        rootCAKey = storeContent.privateKey;
        if (storeContent.certChain.length == 0) {
            throw new LoadRootCAException();
        }
        rootCACertificate = (X509Certificate) storeContent.certChain[0];
    }

    class KeyStoreContentMapper {
        PrivateKey privateKey;
        java.security.cert.Certificate[] certChain;
    }
}

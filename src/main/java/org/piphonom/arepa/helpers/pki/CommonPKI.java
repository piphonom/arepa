package org.piphonom.arepa.helpers.pki;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.bc.BcX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v2CRLBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.piphonom.arepa.exceptions.LoadRootCAException;
import org.springframework.stereotype.Component;

import javax.security.auth.x500.X500Principal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;

/**
 * Created by piphonom
 */
@Component
public final class CommonPKI {
    private static final String PROVIDER_NAME = BouncyCastleProvider.PROVIDER_NAME;
    private static final String SIGNATURE_ALGORITHM = "SHA256WithRSAEncryption";

    private X500Principal crlIssuerName;
    private ContentSigner crlContentSigner;

    private PrivateKey rootCAKey;
    private X509Certificate rootCACertificate;

    public CommonPKI() {
        /**
         * TODO: process properties depending on context
         */
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

    PrivateKey getRootCAKey() {
        return rootCAKey;
    }

    X509Certificate getRootCACertificate() {
        return rootCACertificate;
    }

    private void loadRootCA(String keyStoreLocation, String alias, String storePassword, String keyPassword) throws LoadRootCAException {
        PKCS12ContentLoader storeContent = new PKCS12ContentLoader.LoaderFromFile().
                                            setStoreLocation(keyStoreLocation).setKeyAlias(alias).
                                            setStorePassword(storePassword).setKeyPassword(keyPassword).
                load();
        if (storeContent == null) {
            throw new LoadRootCAException();
        }
        rootCAKey = storeContent.getPrivateKey();
        if (storeContent.getCertChain().length == 0) {
            throw new LoadRootCAException();
        }
        rootCACertificate = (X509Certificate) storeContent.getCertChain()[0];
    }

    /**
     *
     * Creates X509 certificate
     *
     * @param certPublicKey Public Key that will be assigned to the certificate
     * @param subjectDN Distinguished name of the certificate
     * @param issuerDN Issuer distinguished name
     * @param daysValidity Duration of the certificate validity in days
     * @param isCA Does this certificate should be SubCA
     * @param signKey Issuer private key to sign the certificate
     * @return
     */
    X509Certificate createCertificate(PublicKey certPublicKey, String subjectDN, String issuerDN, long daysValidity, boolean isCA, PrivateKey signKey) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            /* yesterday */
            Date validityBeginDate = new Date(currentTimeMillis - 24 * 60 * 60 * 1000);
            /* validity duration */
            Date validityEndDate = new Date(currentTimeMillis + daysValidity * 24 * 60 * 60 * 1000);

            /* Generate the X509 certificate */
            X500Principal dnNameSubject = new X500Principal(subjectDN);
            X500Principal dnNameIssuer = new X500Principal(issuerDN);
            JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                    dnNameIssuer,
                    BigInteger.valueOf(System.currentTimeMillis()),
                    validityBeginDate,
                    validityEndDate,
                    dnNameSubject,
                    certPublicKey
            );

            /* Add key identifier */
            certificateBuilder.addExtension(Extension.subjectKeyIdentifier, false,
                    createSubjectKeyIdentifier(certPublicKey));
            /* Mark certificate as CA or not */
            certificateBuilder.addExtension(Extension.basicConstraints, true,
                    new BasicConstraints(isCA));

            return signCertificate(certificateBuilder, signKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    SubjectKeyIdentifier createSubjectKeyIdentifier(Key key)
            throws IOException {
        ByteArrayInputStream bIn = new ByteArrayInputStream(key.getEncoded());
        ASN1InputStream is = null;
        try {
            is = new ASN1InputStream(bIn);
            ASN1Sequence seq = (ASN1Sequence) is.readObject();
            SubjectPublicKeyInfo info = new SubjectPublicKeyInfo(seq);
            return new BcX509ExtensionUtils().createSubjectKeyIdentifier(info);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    X509Certificate signCertificate(
            X509v3CertificateBuilder certificateBuilder,
            PrivateKey signedWithPrivateKey) throws OperatorCreationException,
            CertificateException {
        ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM)
                .setProvider(PROVIDER_NAME)
                .build(signedWithPrivateKey);
        return new JcaX509CertificateConverter()
                .setProvider(PROVIDER_NAME)
                .getCertificate(certificateBuilder.build(signer));
    }

    public KeyPair generateKeyPair(int keySizeInBits) {
        try {
            /* Generate the Public/Private RSA key pair */
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", PROVIDER_NAME);
            keyPairGenerator.initialize(keySizeInBits, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getGroupSubject(String email, String groupName) {
        return new StringBuilder()
                .append("CN=")
                .append(email)
                .append("_")
                .append(groupName)
                .append("_CA")
                .toString();
    }

    public static String getGroupAliasByEmail(String email, String groupName) {
        return getGroupSubject(email, groupName);
    }
}

package org.piphonom.arepa.helpers.pki;


import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.piphonom.arepa.exceptions.http.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * Created by piphonom
 */
@Component
public class CSRSigner {

    @Autowired
    private CommonPKI commonPKI;

    byte[] CSR = null;
    private int daysValidity = 0;
    private PrivateKey caKey = null;
    private X509Certificate caCertificate = null;
    private X509Certificate certificate = null;
    private long serialNumber = 0;

    public byte[] getCertificate() {
        try {
            return certificate.getEncoded();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CSRSigner setCSR(byte[] CSR) {
        this.CSR = CSR;
        return this;
    }

    public CSRSigner setDaysValidity(int daysValidity) {
        this.daysValidity = daysValidity;
        return this;
    }

    public CSRSigner setCaKey(PrivateKey caKey) {
        this.caKey = caKey;
        return this;
    }

    public CSRSigner setCaCertificate(X509Certificate caCertificate) {
        this.caCertificate = caCertificate;
        return this;
    }

    public CSRSigner setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public boolean sign() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            /* yesterday */
            Date validityBeginDate = new Date(currentTimeMillis - 24 * 60 * 60 * 1000);
            /* validity duration */
            Date validityEndDate = new Date(currentTimeMillis + daysValidity * 24 * 60 * 60 * 1000);

            JcaPKCS10CertificationRequest request = new JcaPKCS10CertificationRequest(CSR);
            JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                    caCertificate,
                    BigInteger.valueOf(serialNumber), //serial
                    validityBeginDate,
                    validityEndDate,
                    request.getSubject(),
                    request.getPublicKey()
            );
            /* Add key identifier */
            certificateBuilder.addExtension(Extension.subjectKeyIdentifier, false,
                    commonPKI.createSubjectKeyIdentifier(request.getPublicKey()));

            certificate = commonPKI.signCertificate(certificateBuilder, caKey);

            if (certificate == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("CSR.sign.failure");
        }
    }
}

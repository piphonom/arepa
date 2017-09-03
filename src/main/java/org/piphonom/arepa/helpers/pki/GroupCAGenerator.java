package org.piphonom.arepa.helpers.pki;

import org.piphonom.arepa.exceptions.CertificateGenerationException;
import org.piphonom.arepa.service.CAGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * Created by piphonom
 */
@Component
public class GroupCAGenerator implements CAGenerator {

    private CommonPKI commonPKI;

    private String customerEmail = null;
    private String groupName = null;
    private int daysValidity = 0;
    private int keySizeInBits = 0;

    private KeyPair keyPair = null;
    private X509Certificate certificate = null;

    @Autowired
    public GroupCAGenerator(CommonPKI commonPKI) {
        this.commonPKI = commonPKI;
    }

    @Override
    public KeyPair getKeyPair() {
        return keyPair;
    }

    @Override
    public X509Certificate getCertificate() {
        return certificate;
    }

    public GroupCAGenerator setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public GroupCAGenerator setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public GroupCAGenerator setDaysValidity(int daysValidity) {
        this.daysValidity = daysValidity;
        return this;
    }

    public GroupCAGenerator setKeySizeInBits(int keySizeInBits) {
        this.keySizeInBits = keySizeInBits;
        return this;
    }

    @Override
    public void generate() throws CertificateGenerationException {

        X509Certificate rootCACertificate = commonPKI.getRootCACertificate();
        keyPair = commonPKI.generateKeyPair(keySizeInBits);

        String subjectDN = commonPKI.getGroupSubject(customerEmail, groupName);
        PrivateKey rootCAKey = commonPKI.getRootCAKey();
        certificate = commonPKI.createCertificate(keyPair.getPublic(), subjectDN, rootCACertificate.getSubjectDN().toString(), daysValidity, true, rootCAKey);
        if (certificate == null) {
            throw new CertificateGenerationException("Internal.error");
        }
    }
}

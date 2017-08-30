package org.piphonom.arepa.service;

import org.piphonom.arepa.exceptions.CertificateGenerationException;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

/**
 * Created by piphonom
 */
public interface CAGenerator {
    KeyPair getKeyPair();
    X509Certificate getCertificate();
    void generate() throws CertificateGenerationException;
}

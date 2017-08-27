package org.piphonom.arepa.service;

import org.piphonom.arepa.exceptions.SignCSRException;

/**
 * Created by piphonom
 */
public interface PKIService {
    /**
     * Signs CSR
     * @param caKey CA private key
     * @param csr request for sign
     * @param daysValidity result certificate days validity
     * @return
     * @throws SignCSRException
     */
    public byte[] signCSR(byte[] caKey, byte[] csr, long daysValidity) throws SignCSRException;
}

package org.piphonom.arepa.model;

import org.piphonom.arepa.service.PubIdGenerator;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by piphonom
 */
@Service
public class PubIdGeneratorImpl implements PubIdGenerator {

    private SecureRandom prng;
    private MessageDigest sha;

    public PubIdGeneratorImpl() throws NoSuchAlgorithmException {
        prng = SecureRandom.getInstance("SHA1PRNG");
        sha = MessageDigest.getInstance("SHA-1");
    }

    @Override
    public String createNew() {
        return generateToken();
    }

    private String generateToken() {
        String randomNum = new Integer(prng.nextInt()).toString();
        byte[] result =  sha.digest(randomNum.getBytes());
        return hexEncode(result);
    }

    /**
     * Based on David Flanagan's book "Java In A Nutshell"
     * Or we can use a "Base64" encoding.
     */
    private String hexEncode(byte[] aInput){
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b&0xf0) >> 4 ]);
            result.append(digits[ b&0x0f]);
        }
        return result.toString();
    }
}

package org.piphonom.arepa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by piphonom
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "CSR.sign.failure")
public class SignCSRException extends RuntimeException {
    public SignCSRException() {
        super("CSR.sign.failure");
    }

    public SignCSRException(String message) {
        super(message);
    }
}

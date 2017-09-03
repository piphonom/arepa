package org.piphonom.arepa.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by piphonom
 */
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {

    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}

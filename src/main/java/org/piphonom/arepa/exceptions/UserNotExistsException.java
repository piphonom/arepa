package org.piphonom.arepa.exceptions;

/**
 * Created by piphonom
 */
public class UserNotExistsException extends Exception {
    public UserNotExistsException() {
        super("User.notFound");
    }

    public UserNotExistsException(String message) {
        super(message);
    }
}

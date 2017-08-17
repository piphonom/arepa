package org.piphonom.arepa.exceptions;

/**
 * Created by piphonom
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User.notFound");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

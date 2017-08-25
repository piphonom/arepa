package org.piphonom.arepa.exceptions;

/**
 * Created by piphonom
 */
public class GroupNotExistsException extends Exception {
    public GroupNotExistsException() {
        super("Group.notExists");
    }

    public GroupNotExistsException(String message) {
        super(message);
    }
}

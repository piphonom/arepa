package org.piphonom.arepa.exceptions;

/**
 * Created by piphonom
 */
public class GroupExistsException extends Exception {
    public GroupExistsException() {
        super("Group.exists");
    }

    public GroupExistsException(String message) {
        super(message);
    }
}

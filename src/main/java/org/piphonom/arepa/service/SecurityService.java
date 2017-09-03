package org.piphonom.arepa.service;

import org.piphonom.arepa.exceptions.UserNotExistsException;

/**
 * Created by piphonom
 */
public interface SecurityService {
    String findLoggedInUsername() throws UserNotExistsException;

    void autoLogin(String username, String password);
}

package org.piphonom.arepa.service;

import org.piphonom.arepa.exceptions.UserNotFoundException;

/**
 * Created by piphonom
 */
public interface SecurityService {
    String findLoggedInUsername() throws UserNotFoundException;

    void autoLogin(String username, String password);
}

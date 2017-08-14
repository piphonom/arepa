package org.piphonom.arepa.service;

/**
 * Created by piphonom
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}

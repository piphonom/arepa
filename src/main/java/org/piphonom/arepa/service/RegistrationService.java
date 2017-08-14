package org.piphonom.arepa.service;

import org.piphonom.arepa.dao.dataset.Customer;

/**
 * Created by piphonom
 */
public interface RegistrationService {
    void save(Customer customer);

    Customer findByEmail(String email);
}

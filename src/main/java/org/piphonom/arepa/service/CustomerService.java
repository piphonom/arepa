package org.piphonom.arepa.service;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.exceptions.UserNotExistsException;

/**
 * Created by piphonom
 */
public interface CustomerService {
    void save(Customer customer);
    Customer findByEmail(String email) throws UserNotExistsException;
}

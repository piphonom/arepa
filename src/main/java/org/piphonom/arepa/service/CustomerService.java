package org.piphonom.arepa.service;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.UserNotFoundException;

import java.util.List;

/**
 * Created by piphonom
 */
public interface CustomerService {
    void save(Customer customer);
    Customer findByEmail(String email) throws UserNotFoundException;
}

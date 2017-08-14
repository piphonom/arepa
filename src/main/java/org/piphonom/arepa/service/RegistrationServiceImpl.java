package org.piphonom.arepa.service;


import org.piphonom.arepa.dao.CustomerDAO;
import org.piphonom.arepa.dao.dataset.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by piphonom
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Customer customer) {
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customerDAO.save(customer);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerDAO.findByEmail(email);
    }
}
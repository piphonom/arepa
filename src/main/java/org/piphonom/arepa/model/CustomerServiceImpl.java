package org.piphonom.arepa.model;

import org.piphonom.arepa.dao.CustomerDAO;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.exceptions.UserNotExistsException;
import org.piphonom.arepa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by piphonom
 */
@Service
public class CustomerServiceImpl implements CustomerService {
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
    public Customer findByEmail(String email) throws UserNotExistsException {
        Customer customer =  customerDAO.findByEmail(email);
        if (customer == null) {
            throw new UserNotExistsException();
        }
        return customer;
    }
}

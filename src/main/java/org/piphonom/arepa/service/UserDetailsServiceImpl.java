package org.piphonom.arepa.service;


import org.piphonom.arepa.dao.CustomerDAO;
import org.piphonom.arepa.dao.dataset.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by piphonom
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private CustomerDAO customerDAO;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerDAO.findByEmail(username);

        if (customer == null) {
            logger.debug(String.format("User %s not found", username));
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

        return new User(customer.getEmail(), customer.getPassword(), grantedAuthorities);
    }
}

package org.piphonom.arepa.dao;

import org.piphonom.arepa.dao.dataset.Customer;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by piphonom
 */
@Transactional
public interface CustomerDAO extends CrudRepository<Customer, Integer> {
    /**
     * Return the customer having the passed name or null if no customer is found.
     *
     * @param name the customer email.
     */
    Customer findByName(String name);
    /**
     * Return the customer having the passed email or null if no customer is found.
     *
     * @param email the customer email.
     */
    Customer findByEmail(String email);

    /**
     * Return the customer having the passed token or null if no customer is found.
     *
     * @param token the customer token.
     */
    Customer findByToken(String token);
}

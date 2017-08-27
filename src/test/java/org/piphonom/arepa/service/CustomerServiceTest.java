package org.piphonom.arepa.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.exceptions.UserNotFoundException;
import org.piphonom.arepa.validator.CustomerValidator;
import org.piphonom.arepa.web.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Created by piphonom
 */
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DatabaseSetup(CustomerServiceTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { CustomerServiceTest.DATASET })
@DirtiesContext
public class CustomerServiceTest {

    protected static final String DATASET = "classpath:dbunit/customer.xml";

    private static final String email = "test@me.com";
    private static final String name  = "test customer";
    private static final String pass = "passpass";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerValidator validator;

    @Test
    public void registration() {
        Customer savedCustomer = null;
        try {
            savedCustomer = customerService.findByEmail(email);
        } catch (UserNotFoundException e) {
            fail();
        }
        assertEquals(email, savedCustomer.getEmail());
        assertEquals(name, savedCustomer.getName());
    }

    @Test
    public void duplicateRegistration() {
        UserForm duplicateUser = new UserForm();
        duplicateUser.setEmail(email);
        duplicateUser.setUsername(name);
        duplicateUser.setPassword(pass);
        duplicateUser.setPasswordConfirm(pass);
        BindingResult errors = new MapBindingResult(new HashMap<String, String>(), "testBinding");
        validator.validate(duplicateUser, errors);
        assertTrue(errors.hasErrors());
    }
}

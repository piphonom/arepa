package org.piphonom.arepa.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.exceptions.GroupExistsException;
import org.piphonom.arepa.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by piphonom
 */
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DatabaseSetup(GroupServiceTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { GroupServiceTest.DATASET })
@DirtiesContext
public class GroupServiceTest {
    protected static final String DATASET = "classpath:dbunit/groups.xml";

    private static final String email = "test@me.com";
    private static final String existingGroupName = "groupName";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    CustomerService customerService;

    @Autowired
    GroupService groupService;

    @Test
    public void createNewGroup() {
        Customer customer = getCustomer();
        try {
            groupService.createGroup(customer, "newGroupName");
        } catch (GroupExistsException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void createDuplicateGroup() throws GroupExistsException {
        Customer customer = getCustomer();
        exception.expect(GroupExistsException.class);
        groupService.createGroup(customer, existingGroupName);
    }

    private Customer getCustomer() {
        Customer customer = null;
        try {
            customer = customerService.findByEmail(email);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
        return customer;
    }
}

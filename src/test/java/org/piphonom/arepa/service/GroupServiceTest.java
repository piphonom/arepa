package org.piphonom.arepa.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.CertificateGenerationException;
import org.piphonom.arepa.exceptions.GroupExistsException;
import org.piphonom.arepa.exceptions.GroupNotExistsException;
import org.piphonom.arepa.exceptions.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        final String groupName = "newGroupName";
        Customer customer = getCustomer();
        try {
            DeviceGroup group = groupService.createGroup(customer, groupName);
            groupService.save(group);
            DeviceGroup createdGroup = groupService.getGroupByName(customer, groupName);
            assertEquals(groupName, createdGroup.getName());
        } catch (GroupExistsException | GroupNotExistsException | CertificateGenerationException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void createDuplicateGroup() throws GroupExistsException, CertificateGenerationException {
        Customer customer = getCustomer();
        exception.expect(GroupExistsException.class);
        groupService.createGroup(customer, existingGroupName);
    }

    private Customer getCustomer() {
        Customer customer = null;
        try {
            customer = customerService.findByEmail(email);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            fail();
        }
        return customer;
    }
}

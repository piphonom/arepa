package org.piphonom.arepa.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.Device;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.helpers.pki.GroupCAGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

/**
 * Created by piphonom
 */
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DatabaseSetup(PKITest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { PKITest.DATASET })
@DirtiesContext
public class PKITest {
    protected static final String DATASET = "classpath:dbunit/device.xml";

    private final String REQUEST_FILE = "src/test/resources/csr/TestDevice.csr";

    @Autowired
    private GroupCAGenerator groupCAGenerator;

    @Autowired
    CustomerService customerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private DeviceService deviceService;

    @Test
    @Transactional
    public void signCSRTest() throws Exception {
        final String GROUP_NAME = "signCSRTestGroup";
        final String DEVICE_NAME = "signCSRTestDevice";

        Customer customer = customerService.findByEmail(DBUnitConstants.CUSTOMER_EMAIL);

        /* Create group at first */
        DeviceGroup group = groupService.createGroup(customer, GROUP_NAME);
        groupService.save(group);

        /* Than create device */
        Device device = deviceService.createDevice(group, DEVICE_NAME);
        deviceService.save(device);

        /* Then register it */
        Path path = Paths.get(REQUEST_FILE);
        byte[] csr = new byte[0];
        try {
            csr = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] certificate = deviceService.registerDevice(device.getPubId(), csr);
        assertNotNull(certificate);
    }

    /*
    // used to generate test RootCA
    @Test
    public void createTestRootCA() throws Exception {
        groupCAGenerator.setCustomerEmail("piphonom")
                        .setGroupName("otus")
                        .setDaysValidity(365)
                        .setKeySizeInBits(2048);

        groupCAGenerator.generate();
        KeyPair groupCaKeys = groupCAGenerator.getKeyPair();
        X509Certificate groupCaCert = groupCAGenerator.getCertificate();
        PKCS12ContentSaver saver = new PKCS12ContentSaver()
                .setCertificateChain(new Certificate[] {groupCaCert})
                .setPrivateKey(groupCaKeys.getPrivate())
                .setKeyAlias("otus")
                .setStorePassword("otus")
                .setKeyPassword("otus");

        OutputStream os = new FileOutputStream("src/test/resources/keystore/RootCA.pfx");
        saver.saveToStream(os);
        os.close();
    }
    */

}

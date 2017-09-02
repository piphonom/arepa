package org.piphonom.arepa.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.piphonom.arepa.helpers.pki.CommonPKI;
import org.piphonom.arepa.helpers.pki.GroupCAGenerator;
import org.piphonom.arepa.helpers.pki.PKCS12ContentSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

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
    GroupCAGenerator groupCAGenerator;

    @Test
    public void signCSRTest() {
        Path path = Paths.get(REQUEST_FILE);
        byte[] csr = new byte[0];
        try {
            csr = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
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

package org.piphonom.arepa.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by piphonom
 */
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DatabaseSetup(DeviceRestTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { DeviceRestTest.DATASET })
@DirtiesContext
@AutoConfigureMockMvc
public class DeviceRestTest {
    protected static final String DATASET = "classpath:dbunit/device.xml";

    @Autowired
    private MockMvc mockMvc;

    private final String expectedName = "deviceName";
    private final String pubId = "2f4f";

    @Test
    public void getName() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(get("/point/name/" + pubId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        String content = new String(response.getContentAsByteArray(), java.nio.charset.StandardCharsets.UTF_8);
        assertNotNull(content);
        String name = JsonPath.read(content, "$.name");
        assertEquals(expectedName, name);
    }

    @Test
    public void registerValidDevice() {
        /**
         * TODO: should return signed certificate
         */
    }

    @Test
    public void registerNotExistingDevice() {
        /**
         * TODO: should return HttpStatus.NOT_FOUND
         */
    }
}

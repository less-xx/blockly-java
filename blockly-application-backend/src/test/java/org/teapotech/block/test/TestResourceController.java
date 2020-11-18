package org.teapotech.block.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.teapotech.base.config.RedisAutoConfig;
import org.teapotech.blockly.config.ApplicationRabbitMQConfig;
import org.teapotech.resource.entity.ResourceConfig;
import org.teapotech.resource.entity.ResourceConfig.Type;
import org.teapotech.resource.service.ResourceService;

@SpringBootTest(classes = TestInitializer.class)
@EnableAutoConfiguration(exclude = { ApplicationRabbitMQConfig.class, RedisAutoConfig.class })
@AutoConfigureMockMvc
public class TestResourceController {

	private static final String TEST_REST_GET_SERVICE_RESOURCE_CONFIG_ID_01 = "test-rest-get-service-resource-01";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResourceService resourceService;

	@Test
	public void testExecuteRestGetResource() throws Exception {
		when(resourceService.findById(TEST_REST_GET_SERVICE_RESOURCE_CONFIG_ID_01))
				.thenReturn(testRestGetServiceResourceConfig());

		this.mockMvc.perform(get("/resources/" + TEST_REST_GET_SERVICE_RESOURCE_CONFIG_ID_01)).andDo(print())
				.andExpect(status().isOk());

	}

	private ResourceConfig testRestGetServiceResourceConfig() throws Exception {
		ResourceConfig resConf = new ResourceConfig();
		resConf.setConfiguration(IOUtils.resourceToString("test-rest-get-resource-config-01.json",
				StandardCharsets.UTF_8, getClass().getClassLoader()));
		resConf.setId(TEST_REST_GET_SERVICE_RESOURCE_CONFIG_ID_01);
		resConf.setName("Test REST Get Service 01");
		resConf.setType(Type.REST_SERVICE_GET);
		return resConf;
	}

}

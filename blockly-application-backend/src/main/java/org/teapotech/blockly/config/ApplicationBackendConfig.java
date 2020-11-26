package org.teapotech.blockly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teapotech.blockly.block.def.BlockExecutorRegistry;
import org.teapotech.util.JsonHelper;

@Configuration
public class ApplicationBackendConfig {

	@Bean
	JsonHelper jsonHelper() {
		return JsonHelper.builder().build();
	}

	@Bean
	BlockExecutorRegistry blockExecutorRegistry() throws Exception {
		BlockExecutorRegistry registry = new BlockExecutorRegistry(jsonHelper());
		registry.loadBlockExecutors();
		return registry;
	}
}

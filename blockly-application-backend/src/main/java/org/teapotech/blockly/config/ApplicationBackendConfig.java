package org.teapotech.blockly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teapotech.blockly.util.BlockRegistry;
import org.teapotech.blockly.util.JsonHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ApplicationBackendConfig {

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    JsonHelper jsonHelper() {
        return JsonHelper.newInstance(objectMapper);
    }

    @Bean
    BlockRegistry blockRegistry() throws Exception {
        BlockRegistry registry = new BlockRegistry(null, jsonHelper());
        registry.loadBlockExecutors();
        return registry;
    }
}

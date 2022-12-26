package org.teapotech.blockly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teapotech.blockly.block.execute.BlockExecutorFactory;
import org.teapotech.blockly.block.execute.DefaultBlockExecutorFactory;
import org.teapotech.blockly.execute.event.BlockEventListenerFactory;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.execute.event.SimpleBlockEventListenerFactory;
import org.teapotech.blockly.execute.event.SimpleEventDispatcher;
import org.teapotech.blockly.execute.event.SimpleEventExchange;
import org.teapotech.blockly.util.BlockRegistry;
import org.teapotech.blockly.util.JsonHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ApplicationConfig {

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

    @Bean
    SimpleEventExchange eventExchange() {
        return new SimpleEventExchange();
    }

    @Bean
    BlockExecutorFactory[] blockExecutorFactories() throws Exception {
        BlockEventListenerFactory blockEventListenerFac = new SimpleBlockEventListenerFactory(eventExchange());
        return new BlockExecutorFactory[] {
                DefaultBlockExecutorFactory.build(blockRegistry(), blockEventListenerFac, jsonHelper()) };
    }

    @Bean
    EventDispatcher eventDispatcher() {
        return new SimpleEventDispatcher(eventExchange());
    }
}

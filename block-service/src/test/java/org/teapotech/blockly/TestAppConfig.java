package org.teapotech.blockly;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.teapotech.blockly.block.execute.BlockExecutorFactory;
import org.teapotech.blockly.execute.event.BlockEventListenerFactory;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.service.PipelineExecutionService;
import org.teapotech.blockly.util.JsonHelper;

@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan(basePackages = { "org.teapotech.blockly.entity" })
@EnableJpaRepositories(basePackages = { "org.teapotech.blockly.repo" })
@ComponentScan(basePackages = { "org.teapotech.blockly" })
public class TestAppConfig {

    @Bean
    JsonHelper jsonHelper(){
        return  JsonHelper.newInstance();
    }


    @MockBean
    PipelineExecutionService pipelineExecutionService;
}

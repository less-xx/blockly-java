package org.teapotech.blockly;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan(basePackages = { "org.teapotech.blockly.entity" })
@EnableJpaRepositories(basePackages = { "org.teapotech.blockly.repo" })
@ComponentScan(basePackages = { "org.teapotech.blockly" })
public class TestAppConfig {
}

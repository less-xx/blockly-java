/**
 * 
 */
package org.teapotech.blockly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author jiangl
 *
 */
@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan({ "org.teapotech.blockly.entity" })
@EnableJpaRepositories(basePackages = { "org.teapotech.blockly.repo" })
@ComponentScan(basePackages = { "org.teapotech.blockly" })
public class MainApplicationLoader implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger LOG = LoggerFactory.getLogger(MainApplicationLoader.class);

	public static void main(String[] args) {

		SpringApplication.run(MainApplicationLoader.class, args);

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOG.info("Registered entrypoints:");
		ApplicationContext applicationContext = event.getApplicationContext();
		applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods().forEach((key, value) -> {
			LOG.info("{}\t\t{}", key, value);
		});

	}

}
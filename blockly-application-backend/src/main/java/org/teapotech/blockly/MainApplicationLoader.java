/**
 * 
 */
package org.teapotech.blockly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author jiangl
 *
 */
@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan({ "org.teapotech.common.entity", "org.teapotech.blockly.user.entity" })
@EnableJpaRepositories(basePackages = { "org.teapotech.common.repo", "org.teapotech.blockly.user.repo" })
@ComponentScan(basePackages = { "org.teapotech" })
public class MainApplicationLoader {

	public static void main(String[] args) {

		SpringApplication.run(MainApplicationLoader.class, args);

	}

}
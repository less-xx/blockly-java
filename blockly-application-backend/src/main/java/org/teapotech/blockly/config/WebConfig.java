package org.teapotech.blockly.config;

import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.teapotech.base.config.CorsEndpointProperties;
import org.teapotech.base.interceptor.UserLogonInterceptor;


/**
 * 
 * @author jiangl
 * 
 */
@Configuration
@EnableConfigurationProperties(CorsEndpointProperties.class)
public class WebConfig implements WebMvcConfigurer {

	private static Logger LOG = LoggerFactory.getLogger(WebConfig.class);

	@Autowired
	UserLogonInterceptor userLogonInterceptor;

	@Autowired
	CorsEndpointProperties corsEndpointProperties;

	@Autowired
	ListableBeanFactory listableBeanFactory;

	@PostConstruct
	void init() {
		Map<String, Object> controllers = listableBeanFactory.getBeansWithAnnotation(RestController.class);
		checkLogonRequiredAnnotation(controllers.values());
	}

	private void checkLogonRequiredAnnotation(Collection<Object> controllers) {

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(userLogonInterceptor).addPathPatterns("/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		if (corsEndpointProperties != null) {
			registry.addMapping("/**").allowCredentials(corsEndpointProperties.getAllowCredentials())
					.allowedHeaders(corsEndpointProperties.getAllowedHeaders().toArray(new String[] {}))
					.allowedMethods(corsEndpointProperties.getAllowedMethods().toArray(new String[] {}));
			LOG.info("CORS registry configured");
		}
	}

}

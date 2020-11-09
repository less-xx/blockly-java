/**
 * 
 */
package org.teapotech.block.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.teapotech.base.event.LogEventDispatcher;
import org.teapotech.base.service.KeyValueStoreDelegate;
import org.teapotech.event.LogEvent;

/**
 * @author jiangl
 *
 */
@ComponentScan(basePackages = { "org.teapotech" })
@Configuration
public class TestInitializer {

	@Bean
	LogEventDispatcher logEventDispatcher() {
		return new LogEventDispatcher() {

			@Override
			public void dispatchLogEvent(LogEvent event) {
				System.out
						.println(String.format("Dispatch log event: %s - %s", event.getCatelog(), event.getMessage()));
			}
		};
	}

	@Bean
	KeyValueStoreDelegate keyValueStoreDelegate() {
		return new KeyValueStoreDelegate() {

			@Override
			public Object get(String key, String hash) {
				return null;
			}
		};
	}

}
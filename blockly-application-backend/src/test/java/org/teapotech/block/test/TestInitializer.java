/**
 * 
 */
package org.teapotech.block.test;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teapotech.base.event.LogEventDispatcher;
import org.teapotech.base.service.KeyValueStoreDelegate;
import org.teapotech.event.LogEvent;

/**
 * @author jiangl
 *
 */
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

			@Override
			public void put(String key, String hash, Object object) {
				// TODO Auto-generated method stub

			}

			@Override
			public void delete(String key, String... hash) {
				// TODO Auto-generated method stub

			}

			@Override
			public Set<String> keys(String key) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
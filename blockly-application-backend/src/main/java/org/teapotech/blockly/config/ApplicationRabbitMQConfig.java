package org.teapotech.blockly.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teapotech.base.config.CustomerConfigProperties;
import org.teapotech.base.event.LogEventDispatcher;
import org.teapotech.base.event.RabbitMQLogEventDispatcher;
import org.teapotech.base.service.LogService;
import org.teapotech.event.LogEvent;

@Configuration
@ConditionalOnBean(ConnectionFactory.class)
public class ApplicationRabbitMQConfig {

	private static Logger LOG = LoggerFactory.getLogger(ApplicationRabbitMQConfig.class);

	@Autowired
	CustomerConfigProperties customerProperties;

	@Autowired
	TopicExchange exchange;

	@Autowired
	RabbitAdmin rabbitAdmin;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	LogService logService;

	@PostConstruct
	void init() {

		Binding binding = BindingBuilder.bind(logEventQueue()).to(exchange)
				.with(RabbitMQLogEventDispatcher.getLogEventRoutingKey(customerProperties.getCode()));
		rabbitAdmin.declareBinding(binding);
	}

	@Bean
	LogEventDispatcher logEventDispatcher(final ConnectionFactory connectionFactory,
			Jackson2JsonMessageConverter converter) {
		return new RabbitMQLogEventDispatcher(rabbitTemplate);
	}

	@Bean
	Queue logEventQueue() {
		return new Queue(getLogEventQueueName(), false);
	}

	public String getLogEventQueueName() {
		return "blockly.app.log_event." + customerProperties.getCode() + ".queue";
	}

	@RabbitListener(queues = "#{applicationRabbitMQConfig.getLogEventQueueName()}")
	public void onReceiveLogEvent(LogEvent logEvent) {
		try {
			logService.saveLogEntry(logEvent);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
}

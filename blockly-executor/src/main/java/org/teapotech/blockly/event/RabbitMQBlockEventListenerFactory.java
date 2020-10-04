/**
 * 
 */
package org.teapotech.blockly.event;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

/**
 * @author jiangl
 *
 */
public class RabbitMQBlockEventListenerFactory implements BlockEventListenerFactory {

	private final RabbitAdmin rabbitAdmin;
	private final TopicExchange eventExchange;

	public RabbitMQBlockEventListenerFactory(RabbitAdmin rabbitAdmin, TopicExchange eventExchange) {
		this.rabbitAdmin = rabbitAdmin;
		this.eventExchange = eventExchange;
	}

	@Override
	public BlockEventListener createBlockEventListener(String workspaceId, String blockId) {
		RabbitMQBlockEventListener listener = new RabbitMQBlockEventListener(rabbitAdmin, eventExchange);
		String id = "workspace." + workspaceId + ".queue." + blockId;
		listener.setId(id);
		return listener;
	}

}

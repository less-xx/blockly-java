package org.teapotech.blockly.block.executor.event;

import org.teapotech.blockly.block.event.NamedBlockEvent;

public interface BlockEventListener {

	String getId();

	String getRoutingKey();

	NamedBlockEvent receive(int timeoutSeconds) throws InterruptedException;

	void initialize(String routingKey);

	void destroy();
}
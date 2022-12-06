package org.teapotech.blockly.execute.event;

public interface BlockEventListener {

	String getId();

	String getRoutingKey();

	NamedBlockEvent receive(int timeoutSeconds) throws InterruptedException;

	void initialize(String routingKey);

	void destroy();
}

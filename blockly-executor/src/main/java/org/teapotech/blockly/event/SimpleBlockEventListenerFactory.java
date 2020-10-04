package org.teapotech.blockly.event;

public class SimpleBlockEventListenerFactory implements BlockEventListenerFactory {

	private final SimpleEventExchange eventExchange;

	public SimpleBlockEventListenerFactory(SimpleEventExchange eventExchange) {
		this.eventExchange = eventExchange;
	}

	@Override
	public BlockEventListener createBlockEventListener(String workspaceId, String blockId) {
		SimpleBlockEventListener listener = new SimpleBlockEventListener();
		listener.setEventExchange(eventExchange);
		listener.setId("workspace." + workspaceId + "." + blockId);
		return listener;
	}

}

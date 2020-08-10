/**
 * 
 */
package org.teapotech.blockly.block.executor.event;

import org.teapotech.blockly.block.event.NamedBlockEvent;
import org.teapotech.blockly.workspace.event.WorkspaceEvent;

/**
 * @author jiangl
 *
 */
public class SimpleEventDispatcher implements EventDispatcher {

	private final SimpleEventExchange eventExchange;

	public SimpleEventDispatcher(SimpleEventExchange eventExchange) {
		this.eventExchange = eventExchange;
	}

	@Override
	public void dispatchBlockEvent(NamedBlockEvent event) {
		String routingKey = "workspace." + event.getWorkspaceId() + "." + event.getEventName();
		eventExchange.dispatch(routingKey, event);
	}

	@Override
	public void dispatchWorkspaceEvent(WorkspaceEvent event) {
		// TODO Auto-generated method stub

	}

}

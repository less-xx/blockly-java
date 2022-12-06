/**
 * 
 */
package org.teapotech.blockly.execute.event;

import java.io.Serializable;

/**
 * @author jiangl
 *
 */
public class NamedBlockEvent extends BlockEvent implements Serializable {

    private static final long serialVersionUID = 7418186311889094243L;

    private String eventName;
	private Object parameter;

	public NamedBlockEvent(String workspaceId, String type, String id) {
		super(workspaceId, type, id);
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return new StringBuilder("{workspaceId: ").append(workspaceId).append(", blockType: ").append(this.blockType)
				.append(", blockId: ").append(this.blockId).append(", eventName: ").append(eventName).append("}")
				.toString();
	}
}

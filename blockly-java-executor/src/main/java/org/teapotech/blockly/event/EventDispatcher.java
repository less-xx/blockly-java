/**
 * 
 */
package org.teapotech.blockly.event;

import org.teapotech.blockly.block.event.NamedBlockEvent;
import org.teapotech.blockly.workspace.event.WorkspaceEvent;

/**
 * @author jiangl
 *
 */
public interface EventDispatcher {

	void dispatchBlockEvent(NamedBlockEvent event);

	void dispatchWorkspaceEvent(WorkspaceEvent event);
}

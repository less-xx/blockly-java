package org.teapotech.blockly.execute.event;

public interface BlockEventListenerFactory {

	BlockEventListener createBlockEventListener(String workspaceId, String blockId);
}

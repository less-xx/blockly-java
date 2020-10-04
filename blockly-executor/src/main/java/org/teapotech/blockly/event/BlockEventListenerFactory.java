package org.teapotech.blockly.event;

public interface BlockEventListenerFactory {

	BlockEventListener createBlockEventListener(String workspaceId, String blockId);
}

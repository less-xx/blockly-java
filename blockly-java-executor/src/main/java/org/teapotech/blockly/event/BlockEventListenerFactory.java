package org.teapotech.blockly.event;

import org.teapotech.blockly.model.Block;

public interface BlockEventListenerFactory {

	BlockEventListener createBlockEventListener(String workspaceId, Block block);
}

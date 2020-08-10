package org.teapotech.blockly.block.executor.event;

import org.teapotech.blockly.model.Block;

public interface BlockEventListenerFactory {

	BlockEventListener createBlockEventListener(String workspaceId, Block block);
}

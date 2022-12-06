package org.teapotech.blockly.block.execute;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.user.UserDelegate;

public interface BlockExecutorFactory {

    AbstractBlockExecutor createBlockExecutor(String workspaceId, Block block, Shadow shadow, UserDelegate executor)
            throws BlockExecutorNotFoundException, InvalidBlockExecutorException;

    BlockDefinition getBlockDefinition(String blockType);

}

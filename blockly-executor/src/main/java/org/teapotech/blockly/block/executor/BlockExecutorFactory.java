package org.teapotech.blockly.block.executor;

import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.user.UserDelegate;

public interface BlockExecutorFactory {

	AbstractBlockExecutor createBlockExecutor(String workspaceId, Block block, UserDelegate executor)
			throws BlockExecutorNotFoundException, InvalidBlockExecutorException;

	AbstractBlockExecutor createBlockExecutor(String workspaceId, BlockValue blockValue, UserDelegate executor)
			throws InvalidBlockException, BlockExecutorNotFoundException, InvalidBlockExecutorException;

	boolean supportBlockType(String blockType);
}

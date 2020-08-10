/**
 * 
 */
package org.teapotech.blockly.block.executor;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.def.BlockDefinitionObjectRegistry;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;

/**
 * @author jiangl
 *
 */
public class BlockExecutorFactory {

	private static Logger LOG = LoggerFactory.getLogger(BlockExecutorFactory.class);

	private final BlockDefinitionObjectRegistry blockDefinitionObjectRegistry;

	public static BlockExecutorFactory build(BlockDefinitionObjectRegistry blockDefinitionObjectRegistry) {
		BlockExecutorFactory fac = new BlockExecutorFactory(blockDefinitionObjectRegistry);
		return fac;
	}

	private BlockExecutorFactory(BlockDefinitionObjectRegistry blockDefinitionObjectRegistry) {
		this.blockDefinitionObjectRegistry = blockDefinitionObjectRegistry;
	}

	public BlockExecutor createBlockExecutor(String workspaceId, Block block)
			throws BlockExecutorNotFoundException, InvalidBlockExecutorException {

		if (block == null) {
			throw new IllegalArgumentException("block should not be null");
		}

		Class<? extends AbstractBlockExecutor> c = blockDefinitionObjectRegistry
				.getBlockExecutorClassByBlockType(block.getType());
		if (c == null) {
			throw new BlockExecutorNotFoundException("Block executor not register for type " + block.getType());
		}
		try {

			BlockExecutor be = null;

			Constructor<? extends AbstractBlockExecutor> constructor = c.getConstructor(Block.class);
			if (constructor == null) {
				throw new InvalidBlockExecutorException(
						"The block executor's constructor should have argument of Block type");
			}

			be = constructor.newInstance(block);

			return be;

		} catch (Exception e) {
			throw new InvalidBlockExecutorException(e.getMessage(), e);
		}
	}

	public BlockExecutor createBlockExecutor(String workspaceId, BlockValue blockValue)
			throws InvalidBlockException, BlockExecutorNotFoundException, InvalidBlockExecutorException {
		String blockType = null;
		String id = null;
		if (blockValue.getBlock() != null) {
			blockType = blockValue.getBlock().getType();
			id = blockValue.getBlock().getId();
		} else if (blockValue.getShadow() != null) {
			blockType = blockValue.getShadow().getType();
			id = blockValue.getShadow().getId();
		} else {
			throw new InvalidBlockException(id, blockType,
					"Block value should have either block or shadow, name: " + blockValue.getName());
		}
		Class<? extends AbstractBlockExecutor> c = blockDefinitionObjectRegistry
				.getBlockExecutorClassByBlockType(blockType);
		if (c == null) {
			throw new BlockExecutorNotFoundException("Block executor not register for type " + blockType);
		}

		return createBlockExecutor(workspaceId, blockValue.getBlock());
	}

}

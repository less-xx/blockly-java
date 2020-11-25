/**
 * 
 */
package org.teapotech.blockly.util;

import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress;
import org.teapotech.blockly.block.executor.BlockExecutor;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.Next;
import org.teapotech.blockly.model.BlockValue;

/**
 * @author jiangl
 *
 */
public class BlockExecutorUtils {

	public static Object execute(Block block, BlockExecutionContext context) throws InvalidBlockException,
			BlockExecutionException, InvalidBlockExecutorException, BlockExecutorNotFoundException {

		String name = Thread.currentThread().getName();
		BlockExecutionProgress beg = context.getBlockExecutionProgress().get(name);
		if (beg == null) {
			context.getLogger().error("Cannot find block execution thread by name: {}", name);
			throw new BlockExecutionException("Cannot find block execution progress by name: " + name);
		}

		AbstractBlockExecutor executor = context.getBlockExecutorFactory(block.getType())
				.createBlockExecutor(context.getWorkspaceId(), block, context.getExecutedBy());
		beg.setBlock(block);
		// context.getLogger().info("Block id: [{}], type: [{}] is [{}]", block.getId(),
		// block.getType(),beg.getBlockStatus());

		Object result = executor.execute(context);

		Next next = block.getNext();
		while (next != null) {
			Block nextBlock = next.getBlock();
			result = context.getBlockExecutorFactory(nextBlock.getType())
					.createBlockExecutor(context.getWorkspaceId(), nextBlock, context.getExecutedBy()).execute(context);
			next = next.getBlock().getNext();
		}

		return result;
	}

	public static Object execute(BlockValue bValue, BlockExecutionContext context) throws InvalidBlockException,
			BlockExecutionException, InvalidBlockExecutorException, BlockExecutorNotFoundException {
		String blockType = null;
		Block block = bValue.getBlock();
		if (bValue.getBlock() != null) {
			blockType = block.getType();
		} else if (bValue.getShadow() != null) {
			blockType = bValue.getShadow().getType();
		}
		if (blockType == null) {
			throw new BlockExecutionException("Invalid BlockValue");
		}
		BlockExecutor executor = context.getBlockExecutorFactory(blockType)
				.createBlockExecutor(context.getWorkspaceId(), bValue, context.getExecutedBy());
		if (block != null) {
			String name = Thread.currentThread().getName();
			BlockExecutionProgress beg = context.getBlockExecutionProgress().get(name);
			if (beg == null) {
				context.getLogger().error("Cannot find block execution thread by name: {}", name);
			}
			beg.setBlock(block);
//			context.getLogger().info("Block id: [{}], type: [{}] is [{}]", block.getId(), block.getType(),
//					beg.getBlockStatus());
		}

		return executor.execute(context);
	}
}

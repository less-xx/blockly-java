/**
 * 
 */
package org.teapotech.blockly.block.executor;

import org.slf4j.Logger;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
public abstract class AbstractBlockExecutor implements BlockExecutor {

	protected final Block block;
	protected final Shadow shadow;

	public AbstractBlockExecutor(Block block) {
		this.block = block;
		this.shadow = null;
	}

	public AbstractBlockExecutor(BlockValue blockValue) {
		this.block = blockValue.getBlock();
		this.shadow = blockValue.getShadow();
	}

	@Override
	public final Object execute(BlockExecutionContext context) throws BlockExecutionException {
		Logger LOG = context.getLogger();
		if (context.isStopped()) {
			if (this.block != null) {
				LOG.info("Block execution is stopped. type: {}, id: {}", block.getType(), block.getId());
			} else {
				LOG.info("Shadow execution is stopped. type: {}, id: {}", shadow.getType(), shadow.getId());
			}
			return null;
		}
		try {
			/*
			 * if (this.block != null) { LOG.debug("Executing block, type: {}, id: {}",
			 * block.getType(), block.getId()); } else {
			 * LOG.debug("Executing shadow, type: {}, id: {}", shadow.getType(),
			 * shadow.getId()); }
			 */
			updateBlockStatus(context, BlockStatus.Enter);
			Object result = doExecute(context);
			updateBlockStatus(context, BlockStatus.Exit);

			/*
			 * if (this.block != null) { LOG.debug("Block executed, type: {}, id: {}",
			 * block.getType(), block.getId()); } else {
			 * LOG.debug("Shadow executed, type: {}, id: {}", shadow.getType(),
			 * shadow.getId()); }
			 */
			return result;
		} catch (Exception e) {
			if (context.isStopped()) {
				return null;
			}
			if (e instanceof BlockExecutionException) {
				throw (BlockExecutionException) e;
			} else if (e instanceof InvalidBlockException) {
				InvalidBlockException ibe = (InvalidBlockException) e;
				String msg = "id: " + ibe.getBlockId() + ", type: " + ibe.getBlockType() + ", error: " + e.getMessage();
				throw new BlockExecutionException(msg, e);
			}
			throw new BlockExecutionException(e.getMessage(), e);
		}
	}

	protected void updateBlockStatus(BlockExecutionContext context, BlockStatus status) {
		if (this.block == null) {
			return;
		}
		String name = Thread.currentThread().getName();
		BlockExecutionProgress beg = context.getBlockExecutionProgress().get(name);
		Logger LOG = context.getLogger();
		if (beg == null) {
			LOG.error("Cannot find block execution thread by name: {}", name);
			return;
		}
		if (status == BlockStatus.Enter) {
			beg.setEnterTimestamp(System.currentTimeMillis());
		} else if (status == BlockStatus.Exit) {
			beg.setExitTimestamp(System.currentTimeMillis());
		}
		beg.setBlockStatus(status);
		context.getLogger().debug("{} Block [{}], type: [{}]", status, block.getId(), block.getType());
	}

	abstract protected Object doExecute(BlockExecutionContext context) throws Exception;

}

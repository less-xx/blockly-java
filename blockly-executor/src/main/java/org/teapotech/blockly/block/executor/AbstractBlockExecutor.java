/**
 * 
 */
package org.teapotech.blockly.block.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.entity.WorkspaceExecution.Status;
import org.teapotech.blockly.event.EventDispatcher;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.workspace.event.WorkspaceEvent;

/**
 * @author jiangl
 *
 */
public abstract class AbstractBlockExecutor implements BlockExecutor {
	protected Logger LOG = LoggerFactory.getLogger(getClass());

	protected final Block block;
	protected final Shadow shadow;
	private boolean paused = false;

	public AbstractBlockExecutor(Block block) {
		this.block = block;
		this.shadow = null;
	}

	public AbstractBlockExecutor(BlockValue blockValue) {
		this.block = blockValue.getBlock();
		this.shadow = blockValue.getShadow();
	}

	private void pauseExecution() throws Exception {
		while (paused) {
			Thread.sleep(1000);
		}
		LOG.debug("Execution resumed");
	}

	public String getBlockId() {
		if (this.block != null) {
			return this.block.getId();
		}
		if (this.shadow != null) {
			return this.shadow.getId();
		}
		return null;
	}

	public void resumeExecution() {
		this.paused = false;
	}

	public boolean isPaused() {
		return paused;
	}

	@Override
	public final Object execute(BlockExecutionContext context) throws BlockExecutionException {

		context.setCurrentBlockExecutor(this);

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

			updateBlockStatus(context, BlockStatus.Enter);
			if (context.shouldPause(getBlockId())) {
				this.paused = true;
				context.getContextObject(EventDispatcher.class).dispatchWorkspaceEvent(
						new WorkspaceEvent(context.getWorkspaceId(), context.getInstanceId(), Status.Paused));
				LOG.debug("Execution paused");
				pauseExecution();
			}

			Object result = doExecute(context);

			updateBlockStatus(context, BlockStatus.Exit);

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

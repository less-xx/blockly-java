/**
 * 
 */
package org.teapotech.blockly.block.executor.start_stop;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.start_stop.ExitBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.entity.WorkspaceExecution.Status;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.workspace.event.WorkspaceEvent;

/**
 * @author jiangl
 *
 */
@BlockDef(value = ExitBlock.class)
public class ExitBlockExecutor extends AbstractBlockExecutor {

	public ExitBlockExecutor(Block block) {
		super(block);
	}

	public ExitBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		updateBlockStatus(context, BlockStatus.Running);

		context.getLogger().info("Stopping workspace {}.", context.getWorkspaceId());
		context.getEventDispatcher().dispatchWorkspaceEvent(
				new WorkspaceEvent(context.getWorkspaceId(), context.getInstanceId(), Status.Stopping));

		return null;
	}

}

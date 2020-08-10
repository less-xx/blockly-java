/**
 * 
 */
package org.teapotech.blockly.block.executor.start_stop;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.start_stop.StartBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;

/**
 * @author jiangl
 *
 */
@BlockDef(value = StartBlock.class)
public class StartBlockExecutor extends AbstractBlockExecutor {

	public StartBlockExecutor(Block block) {
		super(block);
	}

	public StartBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {
		context.getLogger().info("Workspace {} start running.", context.getWorkspaceId());
		updateBlockStatus(context, BlockStatus.Running);
		return null;
	}

}

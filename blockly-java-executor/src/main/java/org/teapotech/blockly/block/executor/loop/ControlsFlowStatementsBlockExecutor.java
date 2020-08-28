/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = "controls_flow_statements", category = "control", style = "control_blocks")
public class ControlsFlowStatementsBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public ControlsFlowStatementsBlockExecutor(Block block) {
		super(block);
	}

	public ControlsFlowStatementsBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		updateBlockStatus(context, BlockStatus.Running);

		Field flowField = block.getFieldByName("FLOW", block.getFields().get(0));
		if (flowField == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing field FLOW");
		}
		return flowField.getValue();
	}

}

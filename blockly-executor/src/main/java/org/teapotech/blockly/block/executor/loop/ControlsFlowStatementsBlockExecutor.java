/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_FLOW_STATEMENTS, category = "control", style = "control_blocks")
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

		Field flowField = block.getFieldByName("FLOW", block.getFields().get(0));
		if (flowField == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing field FLOW");
		}
		String value = flowField.getValue();
//		if (context.getLoops().isEmpty()) {
//			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "not in an iteration");
//		}
		// LoopSupport ls = context.getLoops().pop();
		if (value.equalsIgnoreCase("break")) {
			// ls.breakIteration();
			throw new BreakLoopException();
		}
		if (value.equalsIgnoreCase("continue")) {
			// ls.continueNextIteration();
			throw new ContinueLoopException();
		}
		return null;
	}

}

/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Statement;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_REPEAT_EXT, category = "control", style = "control_blocks")
public class ControlsRepeatExtBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public ControlsRepeatExtBlockExecutor(Block block) {
		super(block);
	}

	public ControlsRepeatExtBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		BlockValue timesBv = this.block.getValues().get(0);

		Object times = BlockExecutorUtils.execute(timesBv, context);
		if (!(times instanceof Integer)) {
			throw new BlockExecutionException(
					"The value should be integer. Block type: " + this.block.getType() + ", id: " + this.block.getId());
		}
		int timesInt = ((Integer) times).intValue();
		if (this.block.getStatements() == null || this.block.getStatements().isEmpty()) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(),
					"Missing statements. Block type: " + this.block.getType() + ", id: " + this.block.getId());
		}
		Statement stmt = this.block.getStatements().get(0);
		for (int i = 0; i < timesInt; i++) {
			try {
				BlockExecutorUtils.execute(stmt.getBlock(), context);
			} catch (BreakLoopException e) {
				context.getLogger().debug("Break iteration of {}", block.getType());
				break;

			} catch (ContinueLoopException e) {
				context.getLogger().debug("Continue iteration of {}", block.getType());
				continue;
			}
		}
		return null;
	}

}

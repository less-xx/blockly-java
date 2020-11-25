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
import org.teapotech.blockly.model.Statement;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_WHILE_UNTIL, category = "control", style = "control_blocks")
public class ControlsWhileUntilBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public ControlsWhileUntilBlockExecutor(Block block) {
		super(block);
	}

	public ControlsWhileUntilBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		String mode = this.block.getFieldByName("MODE", this.block.getFields().get(0)).getValue();

		BlockValue expressionBv = this.block.getBlockValueByName("BOOL", this.block.getValues().get(0));
		if (expressionBv == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing expression block");
		}
		Boolean expressionValue = (Boolean) BlockExecutorUtils.execute(expressionBv, context);

		boolean whileTrue = mode.equalsIgnoreCase("while");

		Statement stmt = this.block.getStatementByName("DO", this.block.getStatements().get(0));
		if (stmt == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing statement block");
		}
		while (!(whileTrue ^ expressionValue)) {
			try {
				BlockExecutorUtils.execute(stmt.getBlock(), context);
			} catch (BreakLoopException e) {
				context.getLogger().debug("Break iteration of {}", block.getType());
				break;

			} catch (ContinueLoopException e) {
				context.getLogger().debug("Continue iteration of {}", block.getType());
				expressionValue = (Boolean) BlockExecutorUtils.execute(expressionBv, context);
				continue;
			}
			expressionValue = (Boolean) BlockExecutorUtils.execute(expressionBv, context);
		}

		return null;

	}
}

/**
 * 
 */
package org.teapotech.blockly.block.executor.math;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_ROUND, category = "math")
public class MathRoundBlockExecutor extends AbstractBlockExecutor {

	public MathRoundBlockExecutor(Block block) {
		super(block);
	}

	public MathRoundBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		Field field = this.block.getFieldByName("OP", this.block.getFields().get(0));
		String opValue = field.getValue();

		BlockValue numValueBv = block.getBlockValueByName("NUM", block.getValues().get(0));
		Number numValue = (Number) BlockExecutorUtils.execute(numValueBv, context);

		if (opValue.equalsIgnoreCase("rounddown")) {
			return Math.floor(numValue.doubleValue());
		} else if (opValue.equalsIgnoreCase("roundup")) {
			return Math.ceil(numValue.doubleValue());
		} else {
			return Math.round(numValue.doubleValue());
		}

	}

}

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
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_CHANGE, category = "math")
public class MathChangeBlockExecutor extends AbstractBlockExecutor {

	public MathChangeBlockExecutor(Block block) {
		super(block);
	}

	public MathChangeBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		String var = this.block.getFieldByName("VAR", this.block.getFields().get(0)).getValue();
		Number n = (Number) context.getVariable(var);

		BlockValue deltaValueBv = block.getBlockValueByName("DELTA", block.getValues().get(0));
		Number deltaValue = (Number) BlockExecutorUtils.execute(deltaValueBv, context);

		if ((n instanceof Integer || n instanceof Long)
				&& (deltaValue instanceof Integer || deltaValue instanceof Long)) {
			Long newValue = n.longValue() + deltaValue.longValue();
			context.setVariable(var, newValue);
		} else {
			Double newValue = n.doubleValue() + deltaValue.doubleValue();
			context.setVariable(var, newValue);
		}
		return null;
	}

}

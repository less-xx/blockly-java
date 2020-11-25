package org.teapotech.blockly.block.executor.variable;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * 
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_VARIABLES_SET, category = "variables", style = "variable_blocks")
public class VariableSetBlockExecutor extends AbstractBlockExecutor {

	public VariableSetBlockExecutor(Block block) {
		super(block);
	}

	public VariableSetBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		String var = this.block.getFieldByName("VAR", this.block.getFields().get(0)).getValue();

		if (this.block.getValues().isEmpty()) {
			throw new BlockExecutionException("Missing value for block, type: " + this.block.getType());
		}

		BlockValue bv = this.block.getBlockValueByName("VAR", this.block.getValues().get(0));
		Object value = BlockExecutorUtils.execute(bv, context);
		context.setVariable(var, value);
		return value;
	}

}

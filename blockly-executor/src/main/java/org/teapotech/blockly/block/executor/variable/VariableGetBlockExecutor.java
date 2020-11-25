package org.teapotech.blockly.block.executor.variable;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;

/**
 * 
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_VARIABLES_GET, category = "variables", style = "variable_blocks")
public class VariableGetBlockExecutor extends AbstractBlockExecutor {

	public VariableGetBlockExecutor(Block block) {
		super(block);
	}

	public VariableGetBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		String var = this.block.getFieldByName("VAR", this.block.getFields().get(0)).getValue();
		Object value = context.getVariable(var);
		if (value == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Cannot find variable: " + var);
		}
		return value;
	}

}

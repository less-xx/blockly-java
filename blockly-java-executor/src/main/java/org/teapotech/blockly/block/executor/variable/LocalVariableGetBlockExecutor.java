package org.teapotech.blockly.block.executor.variable;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.variable.GetLocalVariableBlock;
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
@BlockDef(value = GetLocalVariableBlock.class)
public class LocalVariableGetBlockExecutor extends AbstractBlockExecutor {

	public LocalVariableGetBlockExecutor(Block block) {
		super(block);
	}

	public LocalVariableGetBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		String var = this.block.getFieldByName("var", this.block.getFields().get(0)).getValue();
		Object value = context.getLocalVariable(var);
		if (value == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(),
					"Cannot find local variable: " + var);
		}
		context.getLogger().info("Get local variable: {}", var);
		return value;
	}

}

package org.teapotech.blockly.block.executor.variable;

import org.apache.commons.lang3.StringUtils;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.variable.SetLocalVariableBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * 
 * @author jiangl
 *
 */
@BlockDef(value = SetLocalVariableBlock.class)
public class LocalVariableSetBlockExecutor extends AbstractBlockExecutor {

	public LocalVariableSetBlockExecutor(Block block) {
		super(block);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		updateBlockStatus(context, BlockStatus.Running);

		String var = this.block.getFieldByName("var", this.block.getFields().get(0)).getValue();
		if (StringUtils.isBlank(var)) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing field 'var'.");
		}
		BlockValue bValue = this.block.getBlockValueByName("value", null);
		if (bValue == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing 'value' block.");
		}
		Object value = BlockExecutorUtils.execute(bValue, context);
		if (value == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Empty 'value' block.");
		}
		context.setLocalVariable(var, value);
		context.getLogger().info("Set local variable: {}", var);
		return null;
	}

}

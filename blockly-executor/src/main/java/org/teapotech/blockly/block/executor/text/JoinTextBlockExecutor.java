/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import java.util.List;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT_JOIN, category = "basic", style = "basic_blocks")
public class JoinTextBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public JoinTextBlockExecutor(Block block) {
		super(block);
	}

	public JoinTextBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		if (this.block != null) {
			StringBuilder result = new StringBuilder();
			List<BlockValue> values = this.block.getValues();
			for (BlockValue bv : values) {
				Object s = BlockExecutorUtils.execute(bv, context);
				result.append(s);
			}
			return result.toString();
		} else {
			Field field = this.shadow.getField();
			if (field == null) {
				throw new BlockExecutionException("Invalid shadow, missing field");
			}
			String strValue = field.getValue();
			return strValue;
		}

	}

}

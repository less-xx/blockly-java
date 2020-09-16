/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = "text_print", category = "basic", style = "basic_blocks")
public class PrintTextBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public PrintTextBlockExecutor(Block block) {
		super(block);
	}

	public PrintTextBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		BlockValue valueBlock = this.block.getValues().get(0);
		if (valueBlock == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(),
					"Missing value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
		}
		Object value = BlockExecutorUtils.execute(valueBlock, context);
		System.out.println(value);
		return null;
	}

}

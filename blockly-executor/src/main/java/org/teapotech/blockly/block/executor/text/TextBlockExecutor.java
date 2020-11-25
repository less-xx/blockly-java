/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT, category = "basic", style = "basic_blocks")
public class TextBlockExecutor extends AbstractBlockExecutor {

	public TextBlockExecutor(Block block) {
		super(block);
	}

	public TextBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		Field field = null;
		if (this.block != null) {
			field = this.block.getFieldByName("TEXT", this.block.getFields().get(0));
		} else {
			field = this.shadow.getField();
		}
		return field.getValue();
	}

}

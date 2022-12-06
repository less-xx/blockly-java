/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.FieldType;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT, category = "basic", style = "basic_blocks")
public class TextBlockExecutor extends AbstractBlockExecutor {

    public TextBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        Block inputBlock = getInputBlockByKey(Block.InputType.TEXT);
        if (inputBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        return inputBlock.getFieldValue(FieldType.TEXT);
    }

}

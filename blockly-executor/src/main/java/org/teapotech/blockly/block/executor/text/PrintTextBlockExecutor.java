/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT_PRINT, category = "basic", style = "basic_blocks")
public class PrintTextBlockExecutor extends AbstractBlockExecutor {

    public PrintTextBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Block inputBlock = getInputBlockByKey(Block.InputType.TEXT);
        Shadow inputShadow = this.block.getInputs().get(Block.InputType.TEXT).getShadow();

        if (inputBlock == null && inputShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Object value = BlockExecutionHelper.execute(inputBlock, inputShadow, context);
        System.out.println(value);
        return null;
    }

}

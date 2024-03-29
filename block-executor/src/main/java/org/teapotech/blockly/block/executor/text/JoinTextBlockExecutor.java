/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import java.io.Serializable;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.block.def.DefaultBlockDefinition;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.ExtraState;
import org.teapotech.blockly.model.Block.InputType;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT_JOIN, category = CategoryID.ID_OPERATORS, style = "basic_blocks")
public class JoinTextBlockExecutor extends AbstractBlockExecutor {

    /**
     * @param block
     */
    public JoinTextBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Integer itemCount = (Integer) this.block.getExtraState().get(ExtraState.ITEM_COUNT);
        if (itemCount == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "itemCount must be greater than 0");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < itemCount; i++) {
            Block inputBlock = getInputBlockByKey(InputType.ADD + i);
            if (inputBlock == null) {
                continue;
            }
            if(DefaultBlockDefinition.INTERNAL_BLOCK_TYPE_TEXT.equals(inputBlock.getType())){ // if it is a text block
                Serializable value = inputBlock.getFieldValue();
                if (value != null) {
                    result.append(value);
                }
            } else {
                Object value = BlockExecutionHelper.execute(inputBlock, null, context);
                result.append(value);
            }
        }
        return result.toString();

    }

}

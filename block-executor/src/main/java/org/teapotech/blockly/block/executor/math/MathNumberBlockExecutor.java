/**
 * 
 */
package org.teapotech.blockly.block.executor.math;

import org.apache.commons.lang3.math.NumberUtils;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.FieldType;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_NUMBER, category = CategoryID.ID_OPERATORS)
public class MathNumberBlockExecutor extends AbstractBlockExecutor {

    public MathNumberBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        if (this.block != null) {
            return this.block.getFieldValue(FieldType.NUM);
        } else {
            return this.shadow.getFieldValue(FieldType.NUM);
        }
    }

    private Number strToNumber(String input) throws BlockExecutionException {
        if (!NumberUtils.isParsable(input)) {
            throw new BlockExecutionException("Invalid field value: " + input);
        }
        if (input.indexOf(".") > 0) {
            return Double.valueOf(input);
        } else {
            return Integer.valueOf(input);
        }
    }

}

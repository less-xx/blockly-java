/**
 * 
 */
package org.teapotech.blockly.block.executor.math;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.InputType;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_ROUND, category = "math")
public class MathRoundBlockExecutor extends AbstractBlockExecutor {

    public MathRoundBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        String opValue = (String) this.block.getFieldValue("OP");
        if (opValue == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid operator field value. Block type: " + this.block.getType() + ", id: "
                            + this.block.getId());
        }

        Block valueBlock = block.getInputs().get(InputType.NUM).getBlock();
        Shadow valueShadow = block.getInputs().get(InputType.NUM).getShadow();
        Number numValue = (Number) BlockExecutionHelper.execute(valueBlock, valueShadow, context);

        if (opValue.equalsIgnoreCase("rounddown")) {
            return Math.floor(numValue.doubleValue());
        } else if (opValue.equalsIgnoreCase("roundup")) {
            return Math.ceil(numValue.doubleValue());
        } else {
            return Math.round(numValue.doubleValue());
        }

    }

}

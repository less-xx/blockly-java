/**
 * 
 */
package org.teapotech.blockly.block.executor.math;

import org.apache.commons.lang3.RandomUtils;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
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
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_RANDOM_INT, category = CategoryID.ID_OPERATORS)
public class MathRandomIntBlockExecutor extends AbstractBlockExecutor {

    public MathRandomIntBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Block fromBlock = getInputBlockByKey(InputType.FROM);
        Shadow fromShadow = this.block.getInputs().get(InputType.FROM).getShadow();
        if (fromBlock == null && fromShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid FROM field value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }

        Block toBlock = getInputBlockByKey(InputType.TO);
        Shadow toShadow = this.block.getInputs().get(InputType.TO).getShadow();
        if (toBlock == null && toShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid TO field value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }

        Integer fromValue = (Integer) BlockExecutionHelper.execute(fromBlock, fromShadow, context);
        Integer toValue = (Integer) BlockExecutionHelper.execute(toBlock, toShadow, context);
        Integer result = RandomUtils.nextInt(fromValue, toValue);
        return result;
    }

}

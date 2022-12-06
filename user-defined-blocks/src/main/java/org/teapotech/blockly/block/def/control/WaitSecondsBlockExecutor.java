/**
 * 
 */
package org.teapotech.blockly.block.def.control;

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
@ApplyToBlock(value = WaitSecondsBlock.class)
public class WaitSecondsBlockExecutor extends AbstractBlockExecutor {

    public WaitSecondsBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Block valueBlock = this.block.getInputs().get(InputType.VALUE).getBlock();
        Shadow valueShadow = this.block.getInputs().get(InputType.VALUE).getShadow();

        if (valueBlock == null && valueShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Integer value = (Integer) BlockExecutionHelper.execute(valueBlock, valueShadow, context);
        Thread.sleep(value * 1000);
        return null;
    }

}

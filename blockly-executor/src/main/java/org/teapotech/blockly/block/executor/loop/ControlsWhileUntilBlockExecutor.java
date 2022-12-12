/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.InputType;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_WHILE_UNTIL, category = CategoryID.ID_CONTROL, style = "control_blocks")
public class ControlsWhileUntilBlockExecutor extends AbstractBlockExecutor {

    /**
     * @param block
     */
    public ControlsWhileUntilBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        String mode = (String) this.block.getFieldValue("MODE");

        Block conditionBlock = this.block.getInputs().get(InputType.BOOL).getBlock();
        if (conditionBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing condition block");
        }
        Boolean conditionValue = (Boolean) BlockExecutionHelper.execute(conditionBlock, null, context);

        boolean whileTrue = mode.equalsIgnoreCase("while");

        Block doBlock = getInputBlockByKey("DO");
        if (doBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing repeat statements. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        while (!(whileTrue ^ conditionValue)) {
            try {
                BlockExecutionHelper.execute(doBlock, null, context);
            } catch (BreakLoopException e) {
                context.getLogger().debug("Break iteration of {}", block.getType());
                break;

            } catch (ContinueLoopException e) {
                context.getLogger().debug("Continue iteration of {}", block.getType());
                conditionValue = (Boolean) BlockExecutionHelper.execute(conditionBlock, null, context);
                continue;
            }
            conditionValue = (Boolean) BlockExecutionHelper.execute(conditionBlock, null, context);
        }

        return null;

    }
}

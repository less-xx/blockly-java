/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_REPEAT_EXT, category = "control", style = "control_blocks")
public class ControlsRepeatExtBlockExecutor extends AbstractBlockExecutor {

    public ControlsRepeatExtBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Block timesBlock = getInputBlockByKey("TIMES");
        Shadow timeShadow = this.block.getInputs().get("TIMES").getShadow();

        Object times = BlockExecutionHelper.execute(timesBlock, timeShadow, context);
        if (!(times instanceof Integer)) {
            throw new BlockExecutionException(
                    "The value should be integer. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        int timesInt = ((Integer) times).intValue();

        Block doBlock = getInputBlockByKey("DO");
        if (doBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing repeat statements. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }

        for (int i = 0; i < timesInt; i++) {
            try {
                BlockExecutionHelper.execute(doBlock, null, context);
            } catch (BreakLoopException e) {
                context.getLogger().debug("Break iteration of {}", block.getType());
                break;

            } catch (ContinueLoopException e) {
                context.getLogger().debug("Continue iteration of {}", block.getType());
                continue;
            }
        }
        return null;
    }

}

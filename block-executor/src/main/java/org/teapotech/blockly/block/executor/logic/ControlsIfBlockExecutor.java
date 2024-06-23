/**
 * 
 */
package org.teapotech.blockly.block.executor.logic;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
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
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_IF, category = CategoryID.ID_CONTROL, style = "control_blocks")
public class ControlsIfBlockExecutor extends AbstractBlockExecutor {

    public ControlsIfBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        int elseIfCount = (this.block.getExtraState()==null) ? 0 : (Integer) this.block.getExtraState().get(ExtraState.ELSE_IF_COUNT);
        boolean hasElse = this.block.getExtraState() != null && (Boolean) this.block.getExtraState().get(ExtraState.hasElse);

        Block ifCondBlock = getInputBlockByKey(InputType.IF + "0");
        if (ifCondBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing if condition block. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Boolean ifCondition = (Boolean) BlockExecutionHelper.execute(ifCondBlock, null, context);
        if (ifCondition) {
            Block doBlock = getInputBlockByKey(InputType.DO + "0");
            if (doBlock != null) {
                BlockExecutionHelper.execute(doBlock, null, context);
            }
        } else if (elseIfCount > 0) {
            for (int i = 1; i <= elseIfCount; i++) {
                Block elseIfCondBlock = getInputBlockByKey(InputType.IF + i);
                if (elseIfCondBlock == null) {
                    throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                            "Missing elseif condition block. Block type: " + this.block.getType() + ", id: "
                                    + this.block.getId());
                }
                Boolean elseIfCondition = (Boolean) BlockExecutionHelper.execute(elseIfCondBlock, null, context);
                if (elseIfCondition) {
                    Block doBlock = getInputBlockByKey(InputType.DO + i);
                    if (doBlock != null) {
                        BlockExecutionHelper.execute(doBlock, null, context);
                    }
                    break;
                }
            }
        } else if (hasElse) {
            Block doBlock = getInputBlockByKey(InputType.ELSE);
            if (doBlock != null) {
                BlockExecutionHelper.execute(doBlock, null, context);
            }
        }
        return null;
    }

}

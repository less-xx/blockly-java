/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_FLOW_STATEMENTS, category = "control", style = "control_blocks")
public class ControlsFlowStatementsBlockExecutor extends AbstractBlockExecutor {

    public ControlsFlowStatementsBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        String value = (String) this.block.getFieldValue("FLOW");

        if (value.equalsIgnoreCase("break")) {
            // ls.breakIteration();
            throw new BreakLoopException();
        }
        if (value.equalsIgnoreCase("continue")) {
            // ls.continueNextIteration();
            throw new ContinueLoopException();
        }
        return null;
    }

}

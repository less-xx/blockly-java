/**
 * 
 */
package org.teapotech.blockly.block.start_stop;

import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = StartBlock.class)
public class StartBlockExecutor extends AbstractBlockExecutor {

    public StartBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        context.getLogger().info("Workspace {} start running.", context.getWorkspaceId());
        return null;
    }

}

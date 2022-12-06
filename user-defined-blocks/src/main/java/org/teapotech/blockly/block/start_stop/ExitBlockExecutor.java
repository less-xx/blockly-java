/**
 * 
 */
package org.teapotech.blockly.block.start_stop;

import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.exception.ExitBlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = ExitBlock.class)
public class ExitBlockExecutor extends AbstractBlockExecutor {

    public ExitBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        context.getLogger().info("Stopping workspace {}.", context.getWorkspaceId());
//		context.getEventDispatcher().dispatchWorkspaceEvent(
//				new WorkspaceEvent(context.getWorkspaceId(), context.getInstanceId(), Status.Stopping));
        throw new ExitBlockExecutionException();
        // return null;
    }

}

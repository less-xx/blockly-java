/**
 * 
 */
package org.teapotech.blockly.block.def.event;

import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.execute.event.NamedBlockEvent;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = DispatchEventBlock.class)
public class DispatchEventBlockExecutor extends AbstractBlockExecutor {

    public DispatchEventBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Logger LOG = context.getLogger();
        Block eventBlock = block.getInputs().get("EVENT").getBlock();
        if (eventBlock == null) {
            throw new BlockExecutionException("Missing event block");
        }
        NamedBlockEvent evt = (NamedBlockEvent) BlockExecutionHelper.execute(eventBlock, null, context);
        if (evt == null) {
            throw new BlockExecutionException("Invalid event block.");
        }
        context.getContextObject(EventDispatcher.class).dispatchBlockEvent(evt);
        LOG.info("Dispatched block event. Name: {}", evt.getEventName());

        return null;
    }

}

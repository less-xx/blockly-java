/**
 * 
 */
package org.teapotech.blockly.block.def.event;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
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
        Block evtBlock = this.block.getInputs().get("event").getBlock();
        Object value = BlockExecutionHelper.execute(evtBlock, null, context);
        if(value instanceof NamedBlockEvent) {
            NamedBlockEvent evt = (NamedBlockEvent) value;
            context.getContextObject(EventDispatcher.class).dispatchBlockEvent(evt);
            LOG.info("Dispatched block event. Name: {}", evt.getEventName());
        } else if(value instanceof String) {
            NamedBlockEvent evt = new NamedBlockEvent(context.getWorkspaceId(), block.getType(), block.getId());
            evt.setEventName((String) value);
            context.getContextObject(EventDispatcher.class).dispatchBlockEvent(evt);
            LOG.info("Dispatched block event. Name: {}", evt.getEventName());
        } else {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid event. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }

        return null;
    }

}

/**
 * 
 */
package org.teapotech.blockly.block.def.event;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
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
        String eventName = (String) this.block.getFields().get("EVENT_NAME");
        if (StringUtils.isBlank(eventName)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid event name. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        NamedBlockEvent evt = new NamedBlockEvent(context.getWorkspaceId(), block.getType(), block.getId());
        evt.setEventName(eventName);
        context.getContextObject(EventDispatcher.class).dispatchBlockEvent(evt);
        LOG.info("Dispatched block event. Name: {}", evt.getEventName());

        return null;
    }

}

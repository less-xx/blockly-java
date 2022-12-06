/**
 * 
 */
package org.teapotech.blockly.block.def.event;

import org.apache.commons.lang3.StringUtils;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.execute.event.NamedBlockEvent;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = GetEventParameterBlock.class)
public class GetEventParamBlockExecutor extends AbstractBlockExecutor {

    public GetEventParamBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        String eventName = (String) block.getFieldValue("EVENT_NAME");
        if (StringUtils.isBlank(eventName)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing event name. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        NamedBlockEvent evt = (NamedBlockEvent) context.getLocalVariableValue("_event." + eventName);
        if (evt == null) {
            throw new BlockExecutionException("Cannot find event named " + eventName);
        }
        return evt.getParameter();
    }

}

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
import org.teapotech.blockly.block.execute.support.BlockEventListenerSupport;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.execute.event.BlockEventListener;
import org.teapotech.blockly.execute.event.NamedBlockEvent;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.Next;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = HandleEventBlock.class)
public class HandleEventBlockExecutor extends AbstractBlockExecutor implements BlockEventListenerSupport {

    private BlockEventListener blockEventListener;
    private final static int DEFAULT_TIMEOUT_SECONDS = 5;

    public HandleEventBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    public void setBlockEventListener(BlockEventListener listener) {
        this.blockEventListener = listener;
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        String eventName = (String) this.block.getFieldValue("EVENT_NAME");
        if (StringUtils.isBlank(eventName)) {
            throw new BlockExecutionException("Missing event name");
        }
        String evtName = eventName.replace("\\s*", "_");
        String routingKey = "workspace." + context.getWorkspaceId() + "." + evtName;
        blockEventListener.initialize(routingKey);
        Logger LOG = context.getLogger();
        try {
            while (!context.isStopped()) {

                NamedBlockEvent evt = null;
                try {
                    evt = blockEventListener.receive(DEFAULT_TIMEOUT_SECONDS);
                } catch (InterruptedException ie) {
                    LOG.debug("Thread interruptted.");
                }
                if (evt == null) {
                    continue;
                }
                LOG.info("Received event: {}", evt);

                context.setLocalVariableValue("_event." + evt.getEventName(), evt);
                LOG.info("Set local variable: {}", evt.getEventName());

                Next next = this.block.getNext();
                if (next != null && next.getBlock() != null) {
                    BlockExecutionHelper.execute(next.getBlock(), null, context);
                }
            }
            blockEventListener.destroy();
        } catch (Exception ie) {
            LOG.error(ie.getMessage());
        }
        return null;
    }

}

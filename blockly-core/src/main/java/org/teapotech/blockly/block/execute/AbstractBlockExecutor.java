/**
 * 
 */
package org.teapotech.blockly.block.execute;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.execute.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Input;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.model.Variable;
import org.teapotech.blockly.workspace.event.WorkspaceEvent;

/**
 * @author jiangl
 *
 */
public abstract class AbstractBlockExecutor implements BlockExecutor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected final Block block;
    protected final Shadow shadow;
    private boolean paused = false;

    public AbstractBlockExecutor(Block block, Shadow shadow) {
        this.block = block;
        this.shadow = shadow;
    }

    private void pauseExecution() throws Exception {
        while (paused) {
            Thread.sleep(1000);
        }
        logger.debug("Execution resumed");
    }

    public String getBlockId() {
        if (this.block != null) {
            return this.block.getId();
        }
        if (this.shadow != null) {
            return this.shadow.getId();
        }
        return null;
    }

    public void resumeExecution() {
        this.paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public final Object execute(BlockExecutionContext context) throws BlockExecutionException {

        context.setCurrentBlockExecutor(this);

        Logger LOG = context.getLogger();
        if (context.isStopped()) {
            if (this.block != null) {
                LOG.info("Block execution is stopped. type: {}, id: {}", block.getType(), block.getId());
            } else if (this.shadow != null) {
                LOG.info("Shadow execution is stopped. type: {}, id: {}", shadow.getType(), shadow.getId());
            }
            return null;
        }
        try {

            updateBlockStatus(context, BlockStatus.Enter);
            if (context.shouldPause(getBlockId())) {
                this.paused = true;
                context.getContextObject(EventDispatcher.class).dispatchWorkspaceEvent(new WorkspaceEvent(
                        context.getWorkspaceId(), context.getInstanceId(), WorkspaceExecution.Status.Paused));
                LOG.debug("Execution paused at block: {}", getBlockId());
                pauseExecution();
            }

            Object result = doExecute(context);

            updateBlockStatus(context, BlockStatus.Exit);

            return result;
        } catch (Exception e) {
            if (context.isStopped()) {
                return null;
            }
            if (e instanceof BlockExecutionException) {
                throw (BlockExecutionException) e;
            } else if (e instanceof InvalidBlockException) {
                InvalidBlockException ibe = (InvalidBlockException) e;
                String msg = "id: " + ibe.getBlockId() + ", type: " + ibe.getBlockType() + ", error: " + e.getMessage();
                throw new BlockExecutionException(msg, e);
            }
            throw new BlockExecutionException(e.getMessage(), e);
        }
    }

    protected void updateBlockStatus(BlockExecutionContext context, BlockStatus status) {
        if (this.block == null) {
            return;
        }
        String name = Thread.currentThread().getName();
        BlockExecutionProgress beg = context.getBlockExecutionProgress().get(name);
        Logger LOG = context.getLogger();
        if (beg == null) {
            LOG.error("Cannot find block execution thread by name: {}", name);
            return;
        }
        if (status == BlockStatus.Enter) {
            beg.setEnterTimestamp(System.currentTimeMillis());
        } else if (status == BlockStatus.Exit) {
            beg.setExitTimestamp(System.currentTimeMillis());
        }
        beg.setBlockStatus(status);
        context.getLogger().trace("{} Block [{}], type: [{}]", status, block.getId(), block.getType());
    }

    protected Block getInputBlockByKey(String key) throws Exception {
        LinkedHashMap<String, Input> inputs = this.block.getInputs();
        if (inputs == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing inputs");
        }
        Input input = this.block.getInputs().get(key);
        if (input == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Cannot find input '" + key + "'");
        }
        return input.getBlock();
    }

    protected String replaceMacro(String input, BlockExecutionContext context) {
        Map<String,Object> varValues = context.getWorkspaceVariableValueMap();
        Map<String, String> valueMap = new HashMap<>();

        for(Map.Entry<String,Object> entry: varValues.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(Variable.NULL != value) {
                valueMap.put(key, value.toString());
            }
        }
        return StringSubstitutor.replace(input, valueMap);
    }

    abstract protected Object doExecute(BlockExecutionContext context) throws Exception;

}

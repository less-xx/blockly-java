/**
 * 
 */
package org.teapotech.blockly.block.execute;

import org.teapotech.blockly.exception.BlockExecutionException;

/**
 * @author jiangl
 *
 */
public interface BlockExecutor {

    public static enum ExecutionStatus {
        IDLE, Running, Stopping, Stopped, Succeeded, Failed, Paused
    }

    Object execute(BlockExecutionContext context) throws BlockExecutionException;
}

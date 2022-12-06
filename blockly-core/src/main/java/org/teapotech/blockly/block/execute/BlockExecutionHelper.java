/**
 * 
 */
package org.teapotech.blockly.block.execute;

import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.Next;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
public class BlockExecutionHelper {

    public static Object execute(Block block, Shadow shadow, BlockExecutionContext context)
            throws InvalidBlockException, BlockExecutionException, InvalidBlockExecutorException,
            BlockExecutorNotFoundException {
        String name = Thread.currentThread().getName();
        BlockExecutionProgress beg = context.getBlockExecutionProgress().get(name);
        if (beg == null) {
            context.getLogger().error("Cannot find block execution thread by name: {}", name);
            throw new BlockExecutionException("Cannot find block execution progress by name: " + name);
        }

        String type = block == null ? shadow.getType() : block.getType();
        AbstractBlockExecutor executor = context.getBlockExecutorFactory(type)
                .createBlockExecutor(context.getWorkspaceId(), block, shadow, context.getExecutedBy());
        if (block != null) {
            beg.setBlock(block);
        }
        Object result = executor.execute(context);
        if (block == null) {
            return result;
        }
        Next next = block.getNext();
        while (next != null && next.getBlock() != null) {
            Block nextBlock = next.getBlock();
            result = context.getBlockExecutorFactory(nextBlock.getType())
                    .createBlockExecutor(context.getWorkspaceId(), nextBlock, null, context.getExecutedBy())
                    .execute(context);
            next = next.getBlock().getNext();
        }

        return result;
    }
}

/**
 * 
 */
package org.teapotech.blockly.block.def.loop;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.model.Variable;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = ForEachInCollectionBlock.class)
public class ForEachInCollectionBlockExecutor extends AbstractBlockExecutor {

    /**
     * @param block
     */
    public ForEachInCollectionBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Block listBlock = this.block.getInputs().get("LIST").getBlock();
        if (listBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing COLLECTION block");
        }
        String var = (String) this.block.getFieldValue("VAR");
        if (StringUtils.isBlank(var)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Variable cannot be empty.");
        }

        Block doBlock = this.block.getInputs().get("DO").getBlock();
        if (doBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing loop statement body");
        }

        Object collectionObject = BlockExecutionHelper.execute(listBlock, null, context);
        if (!(collectionObject instanceof Collection<?>)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "The input value of " + ForEachInCollectionBlock.BLOCK_TYPE + " block should be a collection.");
        }
        Collection<?> collection = (Collection<?>) collectionObject;

        for (Object item : collection) {
            context.setLocalVariableValue(var, item);
            try {
                BlockExecutionHelper.execute(doBlock, null, context);
            } catch (BreakLoopException e) {
                context.getLogger().debug("Break iteration of {}", block.getType());
                break;

            } catch (ContinueLoopException e) {
                context.getLogger().debug("Continue iteration of {}", block.getType());
                continue;
            }
        }

        return null;

    }
}

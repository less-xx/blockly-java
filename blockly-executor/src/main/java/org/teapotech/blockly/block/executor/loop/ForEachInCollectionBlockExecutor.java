/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.loop.ForEachInCollectionBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Statement;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(value = ForEachInCollectionBlock.class)
public class ForEachInCollectionBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public ForEachInCollectionBlockExecutor(Block block) {
		super(block);
	}

	public ForEachInCollectionBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		BlockValue collectionBV = this.block.getBlockValueByName("COLLECTION", null);
		if (collectionBV == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing COLLECTION block");
		}
		String varName = this.block.getFieldByName("VAR", this.block.getFields().get(0)).getValue();
		if (StringUtils.isBlank(varName)) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Variable name cannot be empty.");
		}

		Statement stmt = this.block.getStatementByName("DO", this.block.getStatements().get(0));
		if (stmt == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing statement block");
		}

		Object collectionObject = BlockExecutorUtils.execute(collectionBV, context);
		if (!(collectionObject instanceof Collection<?>)) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(),
					"The input value of " + ForEachInCollectionBlock.BLOCK_TYPE + " block should be a collection.");
		}
		Collection<?> collection = (Collection<?>) collectionObject;

		for (Object item : collection) {
			context.setLocalVariable(varName, item);
			try {
				BlockExecutorUtils.execute(stmt.getBlock(), context);
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

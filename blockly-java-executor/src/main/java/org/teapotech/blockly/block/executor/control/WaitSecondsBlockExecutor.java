/**
 * 
 */
package org.teapotech.blockly.block.executor.control;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.control.WaitSecondsBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(value = WaitSecondsBlock.class)
public class WaitSecondsBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public WaitSecondsBlockExecutor(Block block) {
		super(block);
	}

	/**
	 * @param blockValue
	 */
	public WaitSecondsBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		updateBlockStatus(context, BlockStatus.Running);

		BlockValue valueBlock = this.block.getValues().get(0);
		if (valueBlock == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(),
					"Missing value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
		}
		Integer value = (Integer) BlockExecutorUtils.execute(valueBlock, context);
		Thread.sleep(value * 1000);
		return null;
	}

}

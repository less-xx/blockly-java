/**
 * 
 */
package org.teapotech.blockly.block.executor.loop;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Statement;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = "controls_repeat_ext", category = "basic/control")
public class ControlsRepeatExtBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public ControlsRepeatExtBlockExecutor(Block block) {
		super(block);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		updateBlockStatus(context, BlockStatus.Running);

		BlockValue timesBv = this.block.getValues().get(0);

		Object times = BlockExecutorUtils.execute(timesBv, context);
		if (!(times instanceof Integer)) {
			throw new BlockExecutionException(
					"The value should be integer. Block type: " + this.block.getType() + ", id: " + this.block.getId());
		}
		int timesInt = ((Integer) times).intValue();
		if (this.block.getStatements() == null || this.block.getStatements().isEmpty()) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(),
					"Missing statements. Block type: " + this.block.getType() + ", id: " + this.block.getId());
		}
		Statement stmt = this.block.getStatements().get(0);
		Object result = null;
		for (int i = 0; i < timesInt; i++) {
			result = BlockExecutorUtils.execute(stmt.getBlock(), context);
		}
		return result;
	}

}
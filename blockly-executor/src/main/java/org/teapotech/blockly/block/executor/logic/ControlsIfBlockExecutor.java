/**
 * 
 */
package org.teapotech.blockly.block.executor.logic;

import java.util.List;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockMutation;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_IF, category = "control", style = "control_blocks")
public class ControlsIfBlockExecutor extends AbstractBlockExecutor {

	/**
	 * @param block
	 */
	public ControlsIfBlockExecutor(Block block) {
		super(block);
	}

	/**
	 * @param block
	 */
	public ControlsIfBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		BlockMutation mut = this.block.getMutation();
		List<BlockValue> values = this.block.getValues();
		int idx = 0;
		Block ifCondBlock = values.get(idx).getBlock();
		Boolean ifCondition = (Boolean) BlockExecutorUtils.execute(ifCondBlock, context);
		if (ifCondition) {
			Block statBlock = this.block.getStatements().get(idx).getBlock();
			BlockExecutorUtils.execute(statBlock, context);
		} else if (mut != null) {

			if (mut.getElseif() != null) {
				idx += 1;
				while (idx <= mut.getElseif()) {
					if (idx >= values.size()) {
						throw new InvalidBlockException(block);
					}
					ifCondBlock = values.get(idx).getBlock();
					ifCondition = (Boolean) BlockExecutorUtils.execute(ifCondBlock, context);
					if (ifCondition) {
						Block statBlock = this.block.getStatements().get(idx).getBlock();
						BlockExecutorUtils.execute(statBlock, context);
					}
					idx += 1;
				}
			} else if (mut.getElse() != null) {
				Block statBlock = this.block.getStatements().get(idx).getBlock();
				BlockExecutorUtils.execute(statBlock, context);
			}
		}
		return null;
	}

}

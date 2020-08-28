/**
 * 
 */
package org.teapotech.blockly.block.executor.event;

import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.event.DispatchEventBlock;
import org.teapotech.blockly.block.event.NamedBlockEvent;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(value = DispatchEventBlock.class)
public class DispatchEventBlockExecutor extends AbstractBlockExecutor {

	public DispatchEventBlockExecutor(Block block) {
		super(block);
	}

	public DispatchEventBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		Logger LOG = context.getLogger();
		BlockValue bv = block.getBlockValueByName("event", null);
		if (bv == null) {
			throw new BlockExecutionException("Missing event block");
		}
		NamedBlockEvent evt = (NamedBlockEvent) BlockExecutorUtils.execute(bv, context);
		if (evt == null) {
			throw new BlockExecutionException("Invalid event block.");
		}
		context.getEventDispatcher().dispatchBlockEvent(evt);
		LOG.info("Dispatched block event. Name: {}", evt.getEventName());

		return null;
	}

}

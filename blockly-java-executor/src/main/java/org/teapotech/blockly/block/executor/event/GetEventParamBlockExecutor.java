/**
 * 
 */
package org.teapotech.blockly.block.executor.event;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.event.GetEventParameterBlock;
import org.teapotech.blockly.block.event.NamedBlockEvent;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;

/**
 * @author jiangl
 *
 */
@BlockDef(value = GetEventParameterBlock.class)
public class GetEventParamBlockExecutor extends AbstractBlockExecutor {

	public GetEventParamBlockExecutor(Block block) {
		super(block);
	}

	public GetEventParamBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		updateBlockStatus(context, BlockStatus.Running);

		Field evtNameFld = block.getFieldByName("event_name", null);
		if (evtNameFld == null) {
			throw new BlockExecutionException("Missing event_name field value.");
		}
		NamedBlockEvent evt = (NamedBlockEvent) context.getLocalVariable(evtNameFld.getValue());
		if (evt == null) {
			throw new BlockExecutionException("Cannot find event named " + evtNameFld.getValue());
		}
		return evt.getParameter();
	}

}

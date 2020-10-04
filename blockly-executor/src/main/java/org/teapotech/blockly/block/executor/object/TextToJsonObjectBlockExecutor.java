package org.teapotech.blockly.block.executor.object;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.object.TextToJsonObjectBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.support.JsonHelperSupport;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.util.BlockExecutorUtils;
import org.teapotech.util.JsonHelper;

@BlockDef(value = TextToJsonObjectBlock.class)
public class TextToJsonObjectBlockExecutor extends AbstractBlockExecutor implements JsonHelperSupport {

	private JsonHelper jsonHelper;

	public TextToJsonObjectBlockExecutor(Block block) {
		super(block);
	}

	public TextToJsonObjectBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		BlockValue bv = block.getBlockValueByName("INPUT_TEXT", null);
		if (bv == null) {
			throw new BlockExecutionException("Missing input text value.");
		}
		Object object = BlockExecutorUtils.execute(bv, context);
		if (object == null) {
			throw new InvalidBlockException(this.block.getId(), this.block.getType(), "The input value is null");
		}

		return jsonHelper.getObject(object.toString());
	}

	@Override
	public void setJsonHelper(JsonHelper jsonHelper) {
		this.jsonHelper = jsonHelper;
	}

}

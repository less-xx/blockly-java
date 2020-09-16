package org.teapotech.blockly.block.executor.object;

import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.object.GetObjectPropertyBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;
import org.teapotech.blockly.util.BlockExecutorUtils;
import org.teapotech.blockly.util.ObjectValueExtractor;

@BlockDef(value = GetObjectPropertyBlock.class)
public class GetObjectPropertyBlockExecutor extends AbstractBlockExecutor {

	public GetObjectPropertyBlockExecutor(Block block) {
		super(block);
	}

	public GetObjectPropertyBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {
		Field propNameField = block.getFieldByName("PROPERTY_NAME", null);
		if (propNameField == null) {
			throw new BlockExecutionException("Missing PROPERTY_NAME field.");
		}
		final String propName = propNameField.getValue();

		BlockValue bv = block.getBlockValueByName("OBJECT", null);
		if (bv == null) {
			throw new BlockExecutionException("Missing OBJECT value.");
		}
		Object object = BlockExecutorUtils.execute(bv, context);

		Object propValue = ObjectValueExtractor.getPropertyValue(object, propName);
		return propValue;
	}

}

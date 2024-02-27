package org.teapotech.blockly.block.def.object;

import org.apache.commons.lang3.StringUtils;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.util.ObjectValueExtractor;

@ApplyToBlock(value = GetObjectPropertyBlock.class)
public class GetObjectPropertyBlockExecutor extends AbstractBlockExecutor {

    public GetObjectPropertyBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        final String propName = (String) this.block.getFieldValue(GetObjectPropertyBlock.FIELD_PROPERTY_NAME);
        if (StringUtils.isBlank(propName)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid property name. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Block valueBlock = this.block.getInputs().get(GetObjectPropertyBlock.INPUT_OBJECT).getBlock();
        if (valueBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing input. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Object object = BlockExecutionHelper.execute(valueBlock, null, context);

        Object propValue = ObjectValueExtractor.getPropertyValue(object, propName);
        return propValue;
    }

}

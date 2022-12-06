package org.teapotech.blockly.block.def.object;

import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.block.execute.support.JsonHelperSupport;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.InputType;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.util.JsonHelper;

@ApplyToBlock(value = TextToJsonObjectBlock.class)
public class TextToJsonObjectBlockExecutor extends AbstractBlockExecutor implements JsonHelperSupport {

    private JsonHelper jsonHelper;

    public TextToJsonObjectBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Block valueBlock = this.block.getInputs().get(InputType.VALUE).getBlock();
        if (valueBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing input. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Object object = BlockExecutionHelper.execute(valueBlock, null, context);
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

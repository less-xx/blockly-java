package org.teapotech.blockly.block.variable;

import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.FieldType;
import org.teapotech.blockly.model.Block.InputType;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.model.Variable;

/**
 * 
 * @author jiangl
 *
 */
@ApplyToBlock(value = SetLocalVariableBlock.class)
public class LocalVariableSetBlockExecutor extends AbstractBlockExecutor {

    public LocalVariableSetBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Variable var = (Variable) this.block.getFieldValue(FieldType.VAR);
        Block valueBlock = this.block.getInputs().get(InputType.VALUE).getBlock();
        if (valueBlock != null) {
            Object value = BlockExecutionHelper.execute(valueBlock, null, context);
            if (value != null) {
                context.setLocalVariableValue("_local_var_" + var.id(), value);
                context.getLogger().info("Set value to local variable: {}", var.id());
            } else {
                context.setLocalVariableValue("_local_var_" + var.id(), Variable.NULL);
            }
        }
        return null;
    }

}

package org.teapotech.blockly.block.executor.variable;

import org.teapotech.blockly.block.def.BlockDefinition;
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
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_VARIABLES_SET, category = "variables", style = "variable_blocks")
public class VariableSetBlockExecutor extends AbstractBlockExecutor {

    public VariableSetBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Variable var = (Variable) this.block.getFieldValue(FieldType.VAR);
        Block valueBlock = getInputBlockByKey(InputType.VALUE);
        if (valueBlock != null) {
            Object value = BlockExecutionHelper.execute(valueBlock, null, context);
            if (value != null) {
                context.setVariableValue("_var_" + var.id(), value);
                context.getLogger().info("Set value to variable: {}", var.id());
            } else {
                context.setVariableValue("_var_" + var.id(), Variable.NULL);
            }
        }
        return null;
    }

}

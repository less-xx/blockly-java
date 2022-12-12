package org.teapotech.blockly.block.executor.variable;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.FieldType;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.model.Variable;

/**
 * 
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_VARIABLES_GET, category = CategoryID.ID_VARIABLES, style = "variable_blocks")
public class VariableGetBlockExecutor extends AbstractBlockExecutor {

    public VariableGetBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        Variable var = (Variable) this.block.getFieldValue(FieldType.VAR);
        Object value = context.getVariableValue("_var_" + var.id());
        if (value == null) {
            throw new BlockExecutionException("Variable " + var.name() + " is not defined");
        }
        return value;
    }

}

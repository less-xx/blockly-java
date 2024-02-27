package org.teapotech.blockly.block.variable;

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
@ApplyToBlock(value = GetLocalVariableBlock.class)
public class LocalVariableGetBlockExecutor extends AbstractBlockExecutor {

    public LocalVariableGetBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        Variable var = (Variable) this.block.getFieldValue(FieldType.VAR);
        Object value = context.getLocalVariableValue("_local_var_" + var.id());
        if (value == null) {
            throw new BlockExecutionException("Local variable " + var.name() + " is not defined");
        }
        return value;
    }

}

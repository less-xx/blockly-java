/**
 * 
 */
package org.teapotech.blockly.block.executor.math;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.FieldType;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.model.Variable;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_CHANGE, category = CategoryID.ID_VARIABLES)
public class MathChangeBlockExecutor extends AbstractBlockExecutor {

    public MathChangeBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        Variable var = (Variable) this.block.getFieldValue(FieldType.VAR);
        Number n = (Number) context.getWorkspaceVariableValue("_var_" + var.id());
        Block deltaValueBlock = getInputBlockByKey("DELTA");
        Shadow deltaValueShadow = this.block.getInputs().get("DELTA").getShadow();
        if (deltaValueBlock == null && deltaValueShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing input. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Number deltaValue = (Number) BlockExecutionHelper.execute(deltaValueBlock, deltaValueShadow, context);

        if ((n instanceof Integer || n instanceof Long)
                && (deltaValue instanceof Integer || deltaValue instanceof Long)) {
            Long newValue = n.longValue() + deltaValue.longValue();
            context.setWorkspaceVariableValue("_var_" + var.id(), newValue);
        } else {
            Double newValue = n.doubleValue() + deltaValue.doubleValue();
            context.setWorkspaceVariableValue("_var_" + var.id(), newValue);
        }
        return null;
    }

}

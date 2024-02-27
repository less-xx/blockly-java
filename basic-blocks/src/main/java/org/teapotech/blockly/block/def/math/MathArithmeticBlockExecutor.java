/**
 * 
 */
package org.teapotech.blockly.block.def.math;

import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = MathArithmeticBlock.class)
public class MathArithmeticBlockExecutor extends AbstractBlockExecutor {

    public MathArithmeticBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        String opValue = (String) this.block.getFieldValue("OP");
        if (opValue == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid operator field value. Block type: " + this.block.getType() + ", id: "
                            + this.block.getId());
        }
        Block A = this.block.getInputs().get("A").getBlock();
        Block B = this.block.getInputs().get("B").getBlock();
        if (A == null || B == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing value element. " + this.block.getType() + ", id: " + this.block.getId());
        }
        Object valueA = BlockExecutionHelper.execute(A, null, context);
        if (valueA == null || !(valueA instanceof Number)) {
            throw new BlockExecutionException("Value A is not a number");
        }
        Number aValue = (Number) valueA;

        Object valueB = BlockExecutionHelper.execute(B, null, context);
        if (valueB == null || !(valueB instanceof Number)) {
            throw new BlockExecutionException("Value B is not a number");
        }
        Number bValue = (Number) valueB;

        if (opValue.equalsIgnoreCase("add")) {
            return aValue.doubleValue() + bValue.doubleValue();
        } else if (opValue.equalsIgnoreCase("minus")) {
            return aValue.doubleValue() - bValue.doubleValue();
        } else if (opValue.equalsIgnoreCase("MULTIPLY")) {
            return aValue.doubleValue() * bValue.doubleValue();
        } else if (opValue.equalsIgnoreCase("DIVIDE")) {
            return aValue.doubleValue() / bValue.doubleValue();
        } else if (opValue.equalsIgnoreCase("MOD")) {
            return aValue.doubleValue() % bValue.doubleValue();
        }
        throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Unknown operator: " + opValue);
    }

}

/**
 * 
 */
package org.teapotech.blockly.block.executor.logic;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.ClassUtils;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_LOGIC_COMPARE, category = CategoryID.ID_CONTROL, style = "control_blocks")
public class LogicCompareBlockExecutor extends AbstractBlockExecutor {

    public LogicCompareBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Serializable op = this.block.getFieldValue("OP");
        if (op == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Invalid OP field value. Block type: " + this.block.getType() + ", id: " + this.block.getId());
        }
        Block A = getInputBlockByKey("A");
        Block B = getInputBlockByKey("B");
        if (A == null || B == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(),
                    "Missing compare element. " + this.block.getType() + ", id: " + this.block.getId());
        }
        Object value0 = BlockExecutionHelper.execute(A, null, context);
        Object value1 = BlockExecutionHelper.execute(B, null, context);
        return compare(value0, value1, (String) op);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Boolean compare(Object v1, Object v2, String operator) throws Exception {

        if (operator.equalsIgnoreCase("eq")) {
            if (v1 instanceof String) {
                return Objects.equals(v1, v2.toString());
            } else if (v2 instanceof String) {
                return Objects.equals(v1.toString(), v2);
            } else if ((v1 instanceof Number) && (v2 instanceof Number)) {
                double dv1 = ((Number) v1).doubleValue();
                double dv2 = ((Number) v2).doubleValue();
                return dv1 == dv2;
            } else {
                return Objects.equals(v1, v2);
            }

        } else if (operator.equalsIgnoreCase("neq")) {
            return !Objects.equals(v1, v2);
        } else {
            if (ClassUtils.isAssignable(v1.getClass(), Number.class)
                    && ClassUtils.isAssignable(v2.getClass(), Number.class)) {
                Number n1 = (Number) v1;
                Number n2 = (Number) v2;
                if (operator.equalsIgnoreCase("gt")) {
                    return n1.doubleValue() > n2.doubleValue();
                } else if (operator.equalsIgnoreCase("gte")) {
                    return n1.doubleValue() >= n2.doubleValue();
                } else if (operator.equalsIgnoreCase("lt")) {
                    return n1.doubleValue() < n2.doubleValue();
                } else if (operator.equalsIgnoreCase("lte")) {
                    return n1.doubleValue() <= n2.doubleValue();
                }
                throw new InvalidBlockExecutorException("Unknown comparision operator " + operator);

            } else if (!v1.getClass().equals(v2.getClass())) {
                throw new BlockExecutionException(
                        "Types of the inputs are not identical. " + v1.getClass() + " vs " + v2.getClass());
            }
            if (ClassUtils.isAssignable(v1.getClass(), Comparable.class)) {

                Comparable c1 = (Comparable) v1;
                Comparable c2 = (Comparable) v2;
                if (operator.equalsIgnoreCase("gt")) {
                    return c1.compareTo(c2) > 0;
                } else if (operator.equalsIgnoreCase("gte")) {
                    return c1.compareTo(c2) >= 0;
                } else if (operator.equalsIgnoreCase("lt")) {
                    return c1.compareTo(c2) < 0;
                } else if (operator.equalsIgnoreCase("lte")) {
                    return c1.compareTo(c2) <= 0;
                }else if (operator.equalsIgnoreCase("neq")) {
                    return !c1.equals(c2);
                }
                throw new InvalidBlockExecutorException("Unknown comparision operator " + operator);
            }
            throw new BlockExecutionException("Type of the inputs are not comparable. " + v1.getClass());
        }
    }

}

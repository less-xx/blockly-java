/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.FieldType;
import org.teapotech.blockly.model.Shadow;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_MULTILINE_TEXT, category = CategoryID.ID_OPERATORS, style = "basic_blocks")
public class MultilineTextBlockExecutor extends AbstractBlockExecutor {

    public MultilineTextBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        if(this.block!=null){
            return this.block.getFieldValue(FieldType.TEXT);
        }
        return this.shadow.getFieldValue(FieldType.TEXT);
    }

}

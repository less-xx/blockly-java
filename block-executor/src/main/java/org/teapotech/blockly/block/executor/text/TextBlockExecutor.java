/**
 * 
 */
package org.teapotech.blockly.block.executor.text;

import org.apache.commons.lang3.StringUtils;
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
@ApplyToBlock(blockType = BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT, category = CategoryID.ID_OPERATORS, style = "basic_blocks")
public class TextBlockExecutor extends AbstractBlockExecutor {

    public TextBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {
        String result = null;

        if(this.block!=null){
            result = (String)this.block.getFieldValue(FieldType.TEXT);
        }else if(this.shadow!=null){
            result = (String)this.shadow.getFieldValue(FieldType.TEXT);
        }
        if(StringUtils.isNoneBlank(result)){
            result = replaceMacro(result, context);
        }
        return result;
    }

}

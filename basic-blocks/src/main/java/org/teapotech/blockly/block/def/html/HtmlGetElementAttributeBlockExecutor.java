package org.teapotech.blockly.block.def.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.exception.BreakLoopException;
import org.teapotech.blockly.exception.ContinueLoopException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.model.Variable;

import java.io.File;

@ApplyToBlock(value = HtmlGetElementAttributeBlock.class)
public class HtmlGetElementAttributeBlockExecutor extends AbstractBlockExecutor {

    public HtmlGetElementAttributeBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Logger LOG = context.getLogger();
        Block attributeNameBlock = this.block.getInputs().get("ATTRIBUTE_NAME").getBlock();
        if(attributeNameBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing attribute name block.");
        }

        String var = (String) this.block.getFieldValue("VAR");
        if (StringUtils.isBlank(var)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Variable cannot be empty.");
        }

        Element item = (Element)context.getLocalVariableValue(var);
        if(item==null){
            throw new BlockExecutionException("Cannot find local var: "+var);
        }

        String attrName = (String)BlockExecutionHelper.execute(attributeNameBlock, null , context);
        if(StringUtils.isBlank(attrName)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "attribute name must not be empty.");
        }
        Attribute attr = item.attribute(attrName);
        return attr==null ? "": attr.getValue();
    }
}

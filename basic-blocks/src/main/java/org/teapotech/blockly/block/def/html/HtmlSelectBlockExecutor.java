package org.teapotech.blockly.block.def.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.def.file.TemporaryFileBlock;
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

@ApplyToBlock(value = HtmlSelectBlock.class)
public class HtmlSelectBlockExecutor extends AbstractBlockExecutor {

    public HtmlSelectBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Logger LOG = context.getLogger();
        Block fileOrTextBlock = this.block.getInputs().get("FILE_OR_TEXT").getBlock();
        if(fileOrTextBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing file or text block.");
        }
        Object fileOrText =  BlockExecutionHelper.execute(fileOrTextBlock, null , context);
        if(fileOrText == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Failed to get file or text");
        }

        Block selectBlock = this.block.getInputs().get("SELECT").getBlock();
        if(selectBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing select block.");
        }

        Block doBlock = this.block.getInputs().get("DO").getBlock();
        if (doBlock == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing loop statement body");
        }

        String select = (String)BlockExecutionHelper.execute(selectBlock, null , context);
        if(StringUtils.isBlank(select)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Select must not be empty.");
        }

        String var = (String) this.block.getFieldValue("VAR");
        if (StringUtils.isBlank(var)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Variable cannot be empty.");
        }

        Document htmlDoc = null;
        if (fileOrText instanceof String) {
            htmlDoc = Jsoup.parse((String)fileOrText);
        }else if(fileOrText instanceof File) {
            htmlDoc = Jsoup.parse((File)fileOrText);
        }
        if(htmlDoc!=null){
            Elements elements = htmlDoc.select(select);
            for(Element item: elements) {
                context.setLocalVariableValue(var, item);
                try {
                    BlockExecutionHelper.execute(doBlock, null, context);
                } catch (BreakLoopException e) {
                    context.getLogger().debug("Break iteration of {}", block.getType());
                    break;
                } catch (ContinueLoopException e) {
                    context.getLogger().debug("Continue iteration of {}", block.getType());
                    continue;
                }
            }
            return null;
        }
        throw new BlockExecutionException("Only file or text are supported.");
    }
}

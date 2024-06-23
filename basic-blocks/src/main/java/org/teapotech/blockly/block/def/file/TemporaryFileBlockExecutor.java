package org.teapotech.blockly.block.def.file;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;

import java.io.File;

@ApplyToBlock(value = TemporaryFileBlock.class)
public class TemporaryFileBlockExecutor extends AbstractBlockExecutor {

    public TemporaryFileBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Logger LOG = context.getLogger();
        Block fileNameBlock = this.block.getInputs().get("FILE_NAME").getBlock();
        Shadow fileNameShadow = this.block.getInputs().get("FILE_NAME").getShadow();
        if(fileNameBlock == null && fileNameShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing temporary file name block.");
        }
        String fileName = (String) BlockExecutionHelper.execute(fileNameBlock, fileNameShadow , context);
        if(StringUtils.isBlank(fileName)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "File name cannot be empty");
        }

        fileName = replaceMacro(fileName, context);

         File workingDir = context.getWorkingDir();
        File outputDir = new File(workingDir, "temp-"+context.getInstanceId());
        if(!outputDir.exists()) {
            outputDir.mkdirs();
            LOG.info("Created temporary file directory: {}", outputDir.getAbsolutePath());
        }
        File file = new File(outputDir, fileName);
        return file;
    }
}

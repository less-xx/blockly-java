/**
 * 
 */
package org.teapotech.blockly.block.def.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.def.event.DispatchEventBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.execute.event.NamedBlockEvent;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.model.Variable;
import org.teapotech.blockly.util.ObjectValueExtractor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jiangl
 *
 */
@ApplyToBlock(value = SaveToFileBlock.class)
public class SaveToFileBlockExecutor extends AbstractBlockExecutor {

    public SaveToFileBlockExecutor(Block block, Shadow shadow) {
        super(block, shadow);
    }

    @Override
    protected Object doExecute(BlockExecutionContext context) throws Exception {

        Logger LOG = context.getLogger();
        Block fileNameBlock = this.block.getInputs().get("FILE_NAME").getBlock();
        Shadow fileNameShadow = this.block.getInputs().get("FILE_NAME").getShadow();
        if(fileNameBlock == null && fileNameShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing file name block");
        }
        String fileName = (String) BlockExecutionHelper.execute(fileNameBlock, fileNameShadow, context);
        if(StringUtils.isBlank(fileName)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "File name cannot be empty");
        }
        Block fileContentBlock = this.block.getInputs().get("FILE_CONTENT").getBlock();
        Shadow fileContentShadow = this.block.getInputs().get("FILE_CONTENT").getShadow();
        if(fileContentBlock == null && fileContentShadow == null) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "Missing file content block");
        }
        String fileContent = (String) BlockExecutionHelper.execute(fileContentBlock, fileContentShadow, context);
        if(StringUtils.isBlank(fileContent)) {
            throw new InvalidBlockException(this.block.getId(), this.block.getType(), "File content cannot be empty");
        }

        Map<String, Object> templateValues = new HashMap<>();
        context.getAllVariableIds().forEach(id -> {
            String orignialId = id;
            if(id.startsWith("_var_")) {
                orignialId = id.substring(5);
            }
            String name = context.getVariableName(orignialId);
            Object value = context.getVariableValue(id);
            if(name!=null && value != null && !Objects.equals(Variable.NULL, value)) {
                templateValues.put(name, value);
            }
        });

        fileContent = StringSubstitutor.replace(fileContent, templateValues);

        File workingDir = context.getWorkingDir();
        File outputDir = new File(workingDir, "output");
        if(!outputDir.exists()) {
            outputDir.mkdirs();
        }
        File file = new File(outputDir, fileName);
        FileUtils.writeStringToFile(file, fileContent, "UTF-8");
        LOG.info("Saved file to {}", file.getAbsolutePath());
        return null;
    }

}

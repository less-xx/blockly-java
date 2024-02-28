package org.teapotech.blockly.block.def.file;

import org.teapotech.blockly.block.def.CustomBlockDefinition;
import org.teapotech.blockly.resource.BlockOption;

public class SaveToFileBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "save_to_file";

    @Override
    public String getBlockType() {
        return BLOCK_TYPE;
    }

    @Override
    public String getCategory() {
        return CategoryID.ID_FILE_OPERATIONS;
    }

    @Override
    public String getConfiguration() {

        return """
               {
                 "type": "save_to_file",
                 "message0": "save to file: %1 %2 content: %3",
                 "args0": [
                   {
                     "type": "input_value",
                     "name": "FILE_NAME",
                     "check": "String"
                   },
                   {
                     "type": "input_end_row"
                   },
                   {
                     "type": "input_value",
                     "name": "FILE_CONTENT",
                     "check": "String"
                   }
                 ],
                 "inputsInline": true,
                 "previousStatement": null,
                 "nextStatement": null,
                 "style": "file_blocks",
                 "tooltip": "",
                 "helpUrl": ""
               }

                """;
    }
}

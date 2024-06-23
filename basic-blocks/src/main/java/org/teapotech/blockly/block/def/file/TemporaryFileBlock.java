package org.teapotech.blockly.block.def.file;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class TemporaryFileBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "temporary_file";

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
                "type": "temporary_file",
                "message0": "temp file named: %1",
                "args0": [
                  {
                    "type": "input_value",
                    "name": "FILE_NAME",
                    "check": "String"
                  }
                ],
                "output": null,
                "style": "file_blocks",
                "tooltip": "",
                "helpUrl": ""
              }
            """;
    }
}

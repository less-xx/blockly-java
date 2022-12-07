package org.teapotech.blockly.block.def.test;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class PrintBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "test_print";

    @Override
    public String getConfiguration() {
        return """
                {
                  "type": "test_print",
                  "message0": "print %1",
                  "args0": [
                    {
                      "type": "input_value",
                      "name": "A",
                      "align": "CENTRE"
                    }
                  ],
                  "inputsInline": true,
                  "style": "test_blocks",
                  "tooltip": "",
                  "helpUrl": ""
                }
                """;
    }

    @Override
    public String getBlockType() {
        return BLOCK_TYPE;
    }

    @Override
    public String getCategory() {
        return "test_category";
    }

}

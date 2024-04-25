package org.teapotech.blockly.block.def.http;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class SimpleHttpGetBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "simple_http_get";
    @Override
    public String getConfiguration() {
        return """
                {
                   "type": "simple_http_get",
                   "implicitAlign0": "RIGHT",
                   "message0": "Get from address %1",
                   "args0": [
                     {
                       "type": "input_value",
                       "name": "URL",
                       "check": "String",
                       "align": "RIGHT"
                     }
                   ],
                   "inputsInline": false,
                   "output": null,
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
        return CategoryID.ID_HTTP;
    }
}

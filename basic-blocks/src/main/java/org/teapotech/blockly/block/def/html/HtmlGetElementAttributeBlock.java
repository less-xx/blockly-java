package org.teapotech.blockly.block.def.html;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class HtmlGetElementAttributeBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "html_get_element_attr";
    @Override
    public String getConfiguration() {
        return """
                {
                    "type": "html_get_element_attr",
                    "message0": "get attribute %1 from %2",
                    "args0": [
                      {
                        "type": "input_value",
                        "name": "ATTRIBUTE_NAME",
                        "check": "String",
                        "align": "RIGHT"
                      },
                      {
                        "type": "field_input",
                        "name": "VAR",
                        "text": "item"
                      }
                    ],
                    "output": null,
                    "style": "file_blocks",
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
        return CategoryID.ID_PARSER;
    }
}


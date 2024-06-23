package org.teapotech.blockly.block.def.html;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class HtmlGetElementTextBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "html_get_element_text";
    @Override
    public String getConfiguration() {
        return """
                {
                    "type": "html_get_element_text",
                    "message0": "get text of %1",
                    "args0": [
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


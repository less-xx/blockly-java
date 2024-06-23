package org.teapotech.blockly.block.def.html;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class HtmlSelectBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "html_select";
    @Override
    public String getConfiguration() {
        return """
                {
                    "type": "html_select",
                    "message0": "select: %2 from file/text: %1  foreach %3 %4",
                    "args0": [
                      {
                        "type": "input_value",
                        "name": "FILE_OR_TEXT",
                        "check": [
                          "String",
                          "temporary_file"
                        ],
                        "align": "RIGHT"
                      },
                      {
                        "type": "input_value",
                        "name": "SELECT",
                        "check": "String",
                        "align": "RIGHT"
                      },
                      {
                        "type": "field_input",
                        "name": "VAR",
                        "text": "item"
                      },
                      {
                        "type": "input_statement",
                        "name": "DO"
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

    @Override
    public String getBlockType() {
        return BLOCK_TYPE;
    }

    @Override
    public String getCategory() {
        return CategoryID.ID_PARSER;
    }
}


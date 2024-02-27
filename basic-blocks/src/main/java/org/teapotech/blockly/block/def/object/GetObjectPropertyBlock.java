package org.teapotech.blockly.block.def.object;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class GetObjectPropertyBlock extends CustomBlockDefinition {

    public final static String BLOCK_TYPE = "get_object_property";
    public final static String FIELD_PROPERTY_NAME = "PROPERTY_NAME";
    public final static String INPUT_OBJECT = "OBJECT";

    @Override
    public String getBlockType() {
        return BLOCK_TYPE;
    }

    @Override
    public String getCategory() {
        return CategoryID.ID_OPERATORS;
    }

    @Override
    public String getConfiguration() {

        return """
                {
                  "type": "get_object_property",
                  "message0": "Get %1 of %2",
                  "args0": [
                    {
                      "type": "field_input",
                      "name": "PROPERTY_NAME",
                      "text": "propertyName",
                      "check": "String"
                    },
                    {
                      "type": "input_value",
                      "name": "OBJECT"
                    }
                  ],
                  "inputsInline": false,
                  "output": null,
                  "style": "operator_blocks",
                  "tooltip": "",
                  "helpUrl": ""
                }
                """;
    }
}

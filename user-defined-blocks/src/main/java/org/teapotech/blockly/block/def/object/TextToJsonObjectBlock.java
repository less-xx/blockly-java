package org.teapotech.blockly.block.def.object;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class TextToJsonObjectBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "text_to_json";

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
				  "type": "text_to_json",
				  "message0": "convert text %1 to json",
				  "args0": [
				    {
				      "type": "input_value",
                      "name": "VALUE",
				      "check": "String"
				    }
				  ],
				  "inputsInline": true,
				  "output": null,
                  "style": "operator_blocks",
				  "tooltip": "",
				  "helpUrl": ""
				}
				""";
	}
}

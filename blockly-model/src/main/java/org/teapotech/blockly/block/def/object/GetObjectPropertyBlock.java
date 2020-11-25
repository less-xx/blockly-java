package org.teapotech.blockly.block.def.object;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class GetObjectPropertyBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "get_object_property";

	@Override
	public String getBlockType() {
		return BLOCK_TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_BASIC;
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
				      "text": "propertyName"
				    },
				    {
				      "type": "input_value",
				      "name": "OBJECT"
				    }
				  ],
				  "inputsInline": false,
				  "output": null,
				  "style": "basic_blocks",
				  "tooltip": "",
				  "helpUrl": ""
				}
				""";
	}
}

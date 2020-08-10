package org.teapotech.blockly.block.def.variable;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class GetLocalVariableBlock extends CustomBlockDefinition {

	public final static String TYPE = "get_local_variable";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_VARIABLES;
	}

	@Override
	public String getConfiguration() {
		return """
				{
					"type": "get_local_variable",
					"message0": "%1",
					"args0": [
						{
							"type": "field_input",
							"name": "var",
							"text": "dynamic_variable"
						}
					],
					"inputsInline": true,
					"output": null,
					"colour": 32,
					"tooltip": "",
					"helpUrl": ""
				}
				""";
	}
}

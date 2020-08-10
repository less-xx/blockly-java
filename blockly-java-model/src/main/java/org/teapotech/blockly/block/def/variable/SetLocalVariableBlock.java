package org.teapotech.blockly.block.def.variable;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class SetLocalVariableBlock extends CustomBlockDefinition {

	public final static String TYPE = "set_local_variable";

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
					"type": "set_local_variable",
					"message0": "set %1 to %2",
					"args0": [
						{
							"type": "field_input",
							"name": "var",
							"text": "local_variable_name"
						},
						{
							"type": "input_value",
							"name": "value"
						}
					],
					"previousStatement": null,
					"nextStatement": null,
					"colour": 32,
					"tooltip": "",
					"helpUrl": ""
				}
								""";
	}

}

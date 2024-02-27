package org.teapotech.blockly.block.variable;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class SetLocalVariableBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "set_local_variable";

	@Override
	public String getBlockType() {
		return BLOCK_TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_VARIABLES;
	}

	@Override
	public String getConfiguration() {
		return """
				{
					"type": "set_local_variable",
					"message0": "set local %1 to %2",
					"args0": [
						{
							"type": "field_input",
                			"name": "VAR",
							"text": "local_variable_name"
						},
						{
							"type": "input_value",
                			"name": "VALUE"
						}
					],
					"previousStatement": null,
					"nextStatement": null,
					"style": "variable_blocks",
					"tooltip": "",
					"helpUrl": ""
				}
								""";
	}

}

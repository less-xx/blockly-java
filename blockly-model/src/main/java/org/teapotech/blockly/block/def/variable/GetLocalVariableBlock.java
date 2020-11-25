package org.teapotech.blockly.block.def.variable;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class GetLocalVariableBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "get_local_variable";

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
					"type": "get_local_variable",
					"message0": "local: %1",
					"args0": [
						{
							"type": "field_input",
							"name": "var",
							"text": "local_variable"
						}
					],
					"inputsInline": true,
					"output": null,
					"style": "variable_blocks",
					"tooltip": "",
					"helpUrl": ""
				}
				""";
	}
}

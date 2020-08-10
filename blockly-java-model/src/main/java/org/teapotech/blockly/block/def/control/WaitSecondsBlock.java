package org.teapotech.blockly.block.def.control;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class WaitSecondsBlock extends CustomBlockDefinition {

	public final static String TYPE = "wait_seconds";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_BASIC + "/" + Category.ID_CONTROL;
	}

	@Override
	public String getConfiguration() {
		return """
				{
					"type": "wait_seconds",
					"message0": "wait %1 seconds",
					"args0": [
						{
							"type": "input_value",
							"name": "value",
							"check": "Number"
						}
					],
					"inputsInline": true,
					"previousStatement": null,
					"nextStatement": null,
					"colour": 42,
					"tooltip": "",
					"helpUrl": ""
				}
				""";
	}

}

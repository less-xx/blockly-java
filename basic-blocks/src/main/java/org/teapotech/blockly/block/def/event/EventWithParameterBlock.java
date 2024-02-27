package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class EventWithParameterBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "event_with_param";

	@Override
	public String getBlockType() {
		return BLOCK_TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_EVENTS;
	}

	@Override
	public String getConfiguration() {
		return """
				{
					"type": "event_with_param",
					"message0": "%1 with parameter %2",
					"args0": [
						{
							"type": "field_input",
                			"name": "EVENT_NAME",
							"text": "event_name"
						},
						{
							"type": "input_value",
                			"name": "PARAMETER"
						}
					],
					"inputsInline": true,
					"output": null,
					"tooltip": "",
					"helpUrl": "",
					"style": "event_blocks"
				}
				""";
	}

}

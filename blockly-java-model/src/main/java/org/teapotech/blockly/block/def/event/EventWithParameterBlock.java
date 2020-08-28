package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class EventWithParameterBlock extends CustomBlockDefinition {

	public final static String TYPE = "event_with_param";

	@Override
	public String getBlockType() {
		return TYPE;
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
							"name": "event_name",
							"text": "event_name"
						},
						{
							"type": "input_value",
							"name": "parameter"
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

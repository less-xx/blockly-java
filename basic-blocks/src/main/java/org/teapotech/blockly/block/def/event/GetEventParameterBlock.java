package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class GetEventParameterBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "get_event_param";

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
					"type": "get_event_param",
					"message0": "get parameter from %1",
					"args0": [
						{
							"type": "field_input",
                			"name": "EVENT_NAME",
							"text": "event_name"
						}
					],
					"output": null,
					"tooltip": "",
					"helpUrl": "",
					"style": "event_blocks"
				}
				""";
	}

}

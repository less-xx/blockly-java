package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class HandleEventBlock extends CustomBlockDefinition {

	public final static String TYPE = "handle_event";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_EVENTS;
	}

	@Override
	public String getConfiguration() {
		return """
				{
					"type": "handle_event",
					"message0": " when I receive %1",
					"args0": [
						{
							"type": "field_input",
							"name": "event_name",
							"text": "event_name"
						}
					],
					"inputsInline": true,
					"nextStatement": null,
					"tooltip": "",
					"helpUrl": "",
					"colour": 52,
					"hat": "cap"
				}
				""";
	}
}

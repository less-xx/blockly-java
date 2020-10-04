package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class DispatchEventBlock extends CustomBlockDefinition {

	@Override
	public String getBlockType() {
		return "dispatch_event";
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_EVENTS;
	}

	@Override
	public String getConfiguration() {
		return """
				{
					"type": "dispatch_event",
					"message0": "broadcast %1",
					"args0": [
						{
							"type": "input_value",
							"name": "event",
							"check": "event_with_param"
						}
					],
					"inputsInline": true,
					"previousStatement": null,
					"nextStatement": null,
					"style": "event_blocks",
					"tooltip": "",
					"helpUrl": ""
				}
				""";
	}

}
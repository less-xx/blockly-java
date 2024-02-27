package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class HandleEventBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "handle_event";

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
					"type": "handle_event",
                	"message0": "when I receive %1",
					"args0": [
                        {
                            "type": "field_input",
                            "name": "EVENT_NAME",
                            "text": "event_name"
                        }
                    ],
					"inputsInline": true,
					"nextStatement": null,
					"tooltip": "",
					"helpUrl": "",
					"style": "event_blocks",
					"hat": "cap"
				}
				""";
	}
}

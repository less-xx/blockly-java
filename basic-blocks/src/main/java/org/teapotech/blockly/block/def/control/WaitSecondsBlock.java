package org.teapotech.blockly.block.def.control;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class WaitSecondsBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "wait_seconds";

	@Override
	public String getBlockType() {
		return BLOCK_TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_CONTROL;
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
                			"name": "VALUE",
                			"check": "Number",
                			"align": "RIGHT"
						}
					],
					"inputsInline": true,
					"previousStatement": null,
					"nextStatement": null,
                	"style": "control_blocks",
					"tooltip": "",
					"helpUrl": ""
				}
				""";
	}

    @Override
    public String getToolboxConfig() {
        return """
                {
                       "type": "wait_seconds",
                       "inputs": {
                           "VALUE": {
                               "shadow": {
                                   "type": "math_number",
                                   "fields": {
                                       "NUM": 5
                                   }
                               }
                           }
                       }
                   }
                """;
    }

}

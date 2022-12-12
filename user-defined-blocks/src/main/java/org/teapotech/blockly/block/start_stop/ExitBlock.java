package org.teapotech.blockly.block.start_stop;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class ExitBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "exit";

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
					"type": "exit",
					"message0": "Exit",
					"previousStatement": null,
					"tooltip": "",
                	"helpUrl": "",
                	"style": "control_blocks"
				}
				""";
	}
}

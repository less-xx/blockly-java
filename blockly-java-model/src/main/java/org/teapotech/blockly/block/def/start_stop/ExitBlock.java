package org.teapotech.blockly.block.def.start_stop;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class ExitBlock extends CustomBlockDefinition {

	public final static String TYPE = "exit";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_START_EXIT;
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
					"style": "start_exit_blocks"
				}
				""";
	}
}

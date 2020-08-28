package org.teapotech.blockly.block.def.start_stop;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class StartBlock extends CustomBlockDefinition {

	public final static String TYPE = "start";

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
					"type": "start",
					"message0": "when started",
					"nextStatement": null,
					"tooltip": "",
					"helpUrl": "",
					"style": "start_exit_blocks",
					"hat": "cap"
				}
				""";
	}

}

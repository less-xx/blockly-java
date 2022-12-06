package org.teapotech.blockly.block.start_stop;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class StartBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "start";

	@Override
	public String getBlockType() {
		return BLOCK_TYPE;
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

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
		return Category.ID_START_STOP;
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
					"colour": 140,
					"hat": "cap"
				}
				""";
	}

}

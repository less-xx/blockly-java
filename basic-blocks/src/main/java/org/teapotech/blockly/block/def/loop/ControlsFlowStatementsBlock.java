package org.teapotech.blockly.block.def.loop;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class ControlsFlowStatementsBlock extends CustomBlockDefinition {

	public final static String BLOCK_TYPE = "controls_flow_statements2";

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
				  "type": "controls_flow_statements2",
				  "message0": "%1 of loop",
				  "args0": [
					{
					  "type": "field_dropdown",
					  "name": "FLOW",
					  "options": [
						[
						  "break out",
						  "break"
						],
						[
						  "continue with next iteration",
						  "continue"
						]
					  ]
					}
				  ],
				  "previousStatement": null,
				  "nextStatement": null,
				  "style": "control_blocks",
				  "tooltip": "",
				  "helpUrl": ""
				}
				""";
	}

}

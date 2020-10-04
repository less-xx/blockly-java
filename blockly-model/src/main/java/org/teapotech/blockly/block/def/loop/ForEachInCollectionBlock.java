package org.teapotech.blockly.block.def.loop;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class ForEachInCollectionBlock extends CustomBlockDefinition {

	public final static String TYPE = "foreach_in_collection";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_CONTROL;
	}

	@Override
	public String getConfiguration() {
		return """
				{
				  "type": "foreach_in_collection",
				  "message0": "foreach %1 in %2 do %3",
				  "args0": [
				    {
				      "type": "field_input",
				      "name": "VAR",
				      "text": "item"
				    },
				    {
				      "type": "input_value",
				      "name": "COLLECTION"
				    },
				    {
				      "type": "input_statement",
				      "name": "DO"
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

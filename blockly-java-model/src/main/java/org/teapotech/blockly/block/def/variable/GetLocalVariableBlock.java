package org.teapotech.blockly.block.def.variable;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class GetLocalVariableBlock extends CustomBlockDefinition {

	public final static String TYPE = "get_local_variable";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_VARIABLES;
	}

}

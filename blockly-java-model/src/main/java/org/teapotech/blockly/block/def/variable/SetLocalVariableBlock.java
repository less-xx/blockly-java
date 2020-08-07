package org.teapotech.blockly.block.def.variable;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class SetLocalVariableBlock extends CustomBlockDefinition {

	public final static String TYPE = "set_local_variable";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_VARIABLES;
	}

}

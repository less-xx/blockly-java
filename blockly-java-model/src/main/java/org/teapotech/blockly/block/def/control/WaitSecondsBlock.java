package org.teapotech.blockly.block.def.control;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class WaitSecondsBlock extends CustomBlockDefinition {

	public final static String TYPE = "wait_seconds";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_BASIC + "/" + Category.ID_CONTROL;
	}

}

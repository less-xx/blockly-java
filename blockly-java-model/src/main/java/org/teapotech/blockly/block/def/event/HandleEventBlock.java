package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class HandleEventBlock extends CustomBlockDefinition {

	public final static String TYPE = "handle_event";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_EVENTS;
	}
}

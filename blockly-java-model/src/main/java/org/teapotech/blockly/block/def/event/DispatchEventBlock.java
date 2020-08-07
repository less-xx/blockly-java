package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class DispatchEventBlock extends CustomBlockDefinition {

	@Override
	public String getBlockType() {
		return "dispatch_event";
	}

	@Override
	public String getCategory() {
		return Category.ID_EVENTS;
	}

}

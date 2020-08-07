package org.teapotech.blockly.block.def.event;

import org.teapotech.blockly.block.def.Category;
import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class GetEventParameterBlock extends CustomBlockDefinition {

	public final static String TYPE = "get_event_param";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return Category.ID_EVENTS;
	}

}

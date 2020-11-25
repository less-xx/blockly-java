package org.teapotch.blockly.block;

import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.def.BlockField.InputField;
import org.teapotech.blockly.block.def.BlockField.LabelField;
import org.teapotech.blockly.block.def.CustomBlockDefinition.BlockDefObject;
import org.teapotech.blockly.block.def.CustomBlockDefinition.BlockDefObject.ValueType;
import org.teapotech.blockly.block.def.start_stop.StartBlock;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestBlockDefinition {

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test01() {
		StartBlock blockDef = new StartBlock();
		System.out.println(blockDef.getConfiguration());
	}

	@Test
	public void testBlockDefObjectSerializer() throws Exception {
		BlockDefObject bdo = new BlockDefObject();
		bdo.setType("test_block_type");
		bdo.addArg(new LabelField("Label 1"));
		bdo.addArg(new InputField("Parameter 1", "param1"));
		bdo.addArg(new InputField("Parameter 2", "param2"));
		bdo.setOutput(new ValueType[] { ValueType.Any });
		bdo.setStyle("block_style");

		String json = mapper.writeValueAsString(bdo);
		System.out.println(json);
	}

}

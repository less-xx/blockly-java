package org.teapotch.blockly.block;

import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.def.start_stop.StartBlock;

public class TestBlockDefinition {

	@Test
	public void test01() {
		StartBlock blockDef = new StartBlock();
		System.out.println(blockDef.getConfiguration());
	}

}

package org.teapotech.blockly.workspace;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.def.BlockExecutorRegistry;
import org.teapotech.blockly.block.def.CustomBlockConfiguration;
import org.teapotech.blockly.model.Category;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.util.BlockXmlUtils;
import org.teapotech.blockly.util.JSONUtils;

public class TestBlockExecutorRegistry {

	@Test
	public void test01() throws Exception {
		BlockExecutorRegistry blockDefRegistry = new BlockExecutorRegistry();
		Workspace w = blockDefRegistry.getToolboxConfiguration();
		assertNotNull(w.getCategories());
		assertTrue(!w.getCategories().isEmpty());

		Category cat = w.getCategories().get(0);
		assertNotNull(cat.getBlocks());
		assertTrue(!cat.getBlocks().isEmpty());

		String xml = BlockXmlUtils.toXml(w);
		System.out.println(xml);
	}

	@Test
	public void test02() throws Exception {
		BlockExecutorRegistry blockDefRegistry = new BlockExecutorRegistry();
		List<CustomBlockConfiguration> blockDefs = blockDefRegistry.getCustomBlockConfigurations();
		assertTrue(blockDefs.size() > 0);

		String json = JSONUtils.getJSON(blockDefs);
		System.out.println(json);
	}
}

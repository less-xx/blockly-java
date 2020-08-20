package org.teapotech.blockly.workspace;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.def.BlockExecutorRegistry;
import org.teapotech.blockly.model.Category;
import org.teapotech.blockly.model.Workspace;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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

		XmlMapper xmlMapper = new XmlMapper();
		String xml = xmlMapper.writeValueAsString(w);
		System.out.println(xml);
	}

}

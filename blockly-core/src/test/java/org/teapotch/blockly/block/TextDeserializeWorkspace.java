package org.teapotch.blockly.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.util.JsonHelper;

public class TextDeserializeWorkspace {

    JsonHelper jsonHelper = JsonHelper.newInstance().build();

    @Test
    public void test01() throws Exception {
        String json = IOUtils.resourceToString("toolbox-config/wait_seconds.json", StandardCharsets.UTF_8,
                getClass().getClassLoader());
        Block block = jsonHelper.getObject(json, Block.class);
        assertNotNull(block);
        assertEquals("wait_seconds", block.getType());
        assertNotNull(block.getInputs().get("VALUE").getShadow());
    }

//	@Test
//	public void test02() throws Exception {
//		String workspaceXml = IOUtils.resourceToString("test_workspace_02.xml", StandardCharsets.UTF_8,
//				getClass().getClassLoader());
//		Workspace w = BlockXmlUtils.loadWorkspace(workspaceXml);
//		assertNotNull(w);
//		assertEquals("workspaceBlocks", w.getId());
//		assertNotNull(w.getVariables());
//		assertEquals(1, w.getVariables().size());
//		Variable var = w.getVariables().get(0);
//		assertNotNull(var);
//		assertEquals("x", var.getValue());
//		assertNotNull(w.getBlocks());
//		assertEquals(1, w.getBlocks().size());
//		Block block = w.getBlocks().get(0);
//		assertEquals("variables_set", block.getType());
//		assertNotNull(block.getValues());
//		assertEquals(1, block.getValues().size());
//		BlockValue bv = block.getValues().get(0);
//		Block b1 = bv.getBlock();
//		assertNotNull(b1);
//		assertEquals("math_number", b1.getType());
//		Field field = b1.getFieldByName("NUM", null);
//		assertNotNull(field);
//		assertEquals("1", field.getValue());
//
//		Next next = block.getNext();
//		assertNotNull(next);
//		Block b2 = next.getBlock();
//		assertNotNull(b2);
//		assertEquals("controls_if", b2.getType());
//		Block b3 = b2.getValues().get(0).getBlock();
//		assertEquals("logic_compare", b3.getType());
//		assertEquals(2, b3.getValues().size());
//
//		w = BlockXmlUtils.updateToUUID(w);
//		System.out.println(BlockXmlUtils.toXml(w));
//
//	}
//
//	@Test
//	public void testUpdateID() throws Exception {
//		String workspaceXml = IOUtils.resourceToString("test_workspace_07.xml", StandardCharsets.UTF_8,
//				getClass().getClassLoader());
//		Workspace w = BlockXmlUtils.loadWorkspace(workspaceXml);
//		System.out.println(BlockXmlUtils.toXml(w));
//		w = BlockXmlUtils.updateToUUID(w);
//		System.out.println(BlockXmlUtils.toXml(w));
//	}
}

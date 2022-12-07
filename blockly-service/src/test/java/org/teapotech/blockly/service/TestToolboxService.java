package org.teapotech.blockly.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Category;
import org.teapotech.blockly.model.Toolbox;
import org.teapotech.blockly.util.BlockRegistry;
import org.teapotech.blockly.util.JsonHelper;

public class TestToolboxService {

    static ToolboxServiceImpl toolboxService = new ToolboxServiceImpl();
    static JsonHelper jsonHelper = JsonHelper.newInstance().build();

    @BeforeAll
    static void init() throws Exception {
        BlockServiceImpl blockService = new BlockServiceImpl();
        blockService.blockRegistry = new BlockRegistry(null);
        blockService.init();
        toolboxService.blockService = blockService;
        toolboxService.jsonHelper = jsonHelper;
        toolboxService.init();
    }

    @Test
    public void test01() {
        Toolbox toolbox = toolboxService.getToolboxConfiguration(null);
        assertNotNull(toolbox.getCategories());
        assertTrue(!toolbox.getCategories().isEmpty());
        Category cat = toolbox.getCategories().get(0);
        assertTrue(cat.getBlocks() != null && !cat.getBlocks().isEmpty());
        Block block = cat.getBlocks().get(0);
        assertNotNull(block.getType());
    }

}

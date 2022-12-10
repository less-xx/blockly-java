package org.teapotech.blockly.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockDefinition.CategoryID;
import org.teapotech.blockly.model.ToolboxItem;
import org.teapotech.blockly.util.BlockRegistry;
import org.teapotech.blockly.util.JsonHelper;

public class TestToolboxService {

    static ToolboxServiceImpl toolboxService = new ToolboxServiceImpl();
    static JsonHelper jsonHelper = JsonHelper.newInstance().build();

    @BeforeAll
    static void init() throws Exception {
        BlockServiceImpl blockService = new BlockServiceImpl();
        blockService.blockRegistry = new BlockRegistry(null, jsonHelper);
        blockService.init();
        toolboxService.blockService = blockService;
        toolboxService.jsonHelper = jsonHelper;
        toolboxService.init();
    }

    @Test
    public void test01() {
        ToolboxItem toolbox = toolboxService.getToolboxConfiguration(null);
        ToolboxItem cat = toolbox.findCategoryByName(CategoryID.ID_START_EXIT);
        assertNotNull(cat);
        assertTrue(!cat.getContents().isEmpty());

        ToolboxItem block = toolbox.findBlockByType(BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT);
        assertNotNull(block);
        assertNotNull(block.getType());
    }

}

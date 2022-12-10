package org.teapotch.blockly.block;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.teapotech.blockly.model.ToolboxItem;
import org.teapotech.blockly.model.ToolboxItem.Kind;
import org.teapotech.blockly.model.ToolboxItem.ToolboxItemFilter;
import org.teapotech.blockly.util.JsonHelper;

public class TestToolbox {

    JsonHelper jsonHelper = JsonHelper.newInstance().build();

    @Test
    public void test01() throws Exception {
        String json = IOUtils.resourceToString("toolbox01.json", StandardCharsets.UTF_8, getClass().getClassLoader());
        ToolboxItem toolbox = jsonHelper.getObject(json, ToolboxItem.class);
        assertNotNull(toolbox);

        ToolboxItem block = toolbox.findToolboxItem(new ToolboxItemFilter() {
            @Override
            public boolean matches(Kind kind, String type, String name) {
                return kind == Kind.block && "text_charAt".equals(type);
            }
        });
        assertNotNull(block);

        ToolboxItem category = toolbox.findToolboxItem(new ToolboxItemFilter() {
            @Override
            public boolean matches(Kind kind, String type, String name) {
                return kind == Kind.category && "Logic".equals(name);
            }
        });
        assertNotNull(category);
        assertTrue(!category.getContents().isEmpty());

        category = toolbox.findCategoryByName("Logic");
        assertNotNull(category);
        assertTrue(!category.getContents().isEmpty());

        category = toolbox.findCategoryByName("ADSDF");
        assertNull(category);
    }

}

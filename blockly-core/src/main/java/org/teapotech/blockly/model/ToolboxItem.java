package org.teapotech.blockly.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.teapotech.blockly.block.def.BlockDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolboxItem extends Block {

    public static enum Kind {
        categoryToolbox, flyoutToolbox, category, block
    };

    private Kind kind;
    private List<ToolboxItem> contents;
    private String name;
    private String colour;
    private String categorystyle;

    public ToolboxItem() {
        this.kind = Kind.categoryToolbox;
    }

    public ToolboxItem(Kind kind) {
        this.kind = kind;
        this.contents = new ArrayList<>();
    }

    public ToolboxItem(Block block) {
        this.kind = Kind.block;
        this.setType(block.getType());
        this.setInputs(block.getInputs());
        this.setFields(block.getFields());
        this.setExtraState(block.getExtraState());
        this.contents = null;
    }

    public ToolboxItem(BlockDefinition blockDef) {
        this.kind = Kind.block;
        this.setType(blockDef.getBlockType());
        this.contents = null;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public List<ToolboxItem> getContents() {
        return contents;
    }

    public void setContents(List<ToolboxItem> contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getCategorystyle() {
        return categorystyle;
    }

    public void setCategorystyle(String categorystyle) {
        this.categorystyle = categorystyle;
    }

    public ToolboxItem findToolboxItem(ToolboxItemFilter filter) {
        if (filter == null) {
            return this;
        }
        if (filter.matches(this.kind, this.getType(), this.name)) {
            return this;
        }
        if (contents == null) {
            return null;
        }
        ToolboxItem result = null;
        for (ToolboxItem child : contents) {
            result = child.findToolboxItem(filter);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public ToolboxItem findCategoryByName(String categoryName) {
        if (StringUtils.isBlank(categoryName)) {
            return null;
        }
        return findToolboxItem(new ToolboxItemFilter() {
            @Override
            public boolean matches(Kind kind, String type, String name) {
                return Kind.category == kind && categoryName.equals(name);
            }
        });
    }

    public ToolboxItem findBlockByType(String blockType) {
        if (StringUtils.isBlank(blockType)) {
            return null;
        }
        return findToolboxItem(new ToolboxItemFilter() {
            @Override
            public boolean matches(Kind kind, String type, String name) {
                return Kind.block == kind && blockType.equals(type);
            }
        });
    }

    @Override
    public String toString() {
        return "ToolboxItem [kind=" + kind + ", name=" + name + ", type=" + getType() + "]";
    }

    public static interface ToolboxItemFilter {
        boolean matches(Kind kind, String type, String name);
    }
}

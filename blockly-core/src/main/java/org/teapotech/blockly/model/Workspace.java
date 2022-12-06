package org.teapotech.blockly.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author Less Jiang
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workspace {

    private String id;

    private List<Variable> variables;

    private Blocks blocks;

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Blocks getBlocks() {
        return blocks;
    }

    public void setBlocks(Blocks blocks) {
        this.blocks = blocks;
    }

    /**
     * 
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Blocks {
        private int languageVersion;
        private List<Block> blocks;

        public int getLanguageVersion() {
            return languageVersion;
        }

        public void setLanguageVersion(int languageVersion) {
            this.languageVersion = languageVersion;
        }

        public List<Block> getBlocks() {
            return blocks;
        }

        public void setBlocks(List<Block> blocks) {
            this.blocks = blocks;
        }

    }

}

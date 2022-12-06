package org.teapotech.blockly.model;

import java.util.LinkedList;
import java.util.List;

import org.teapotech.blockly.util.BlockDefSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = BlockDefSerializer.class)
public class BlockDef {
    public static enum ValueType {
        Any, Number, Boolean, String, Array
    }

    private String type;
    private String message;
    private List<BlockDefArg> args = new LinkedList<>();
    private ValueType[] output;
    private String tooltip = "";
    private String helpUrl = "";
    private String style;
    private Integer colour;
    private Boolean inputsInline;
    private ValueType[] previousStatement;
    private ValueType[] nextStatement;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BlockDefArg> getArgs() {
        return args;
    }

    public void setArgs(List<BlockDefArg> args) {
        this.args = args;
    }

    public void addArg(BlockDefArg arg) {
        this.args.add(arg);
    }

    public ValueType[] getOutput() {
        return output;
    }

    public void setOutput(ValueType[] output) {
        this.output = output;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Integer getColour() {
        return colour;
    }

    public void setColour(Integer colour) {
        this.colour = colour;
    }

    public Boolean getInputsInline() {
        return inputsInline;
    }

    public void setInputsInline(Boolean inputsInline) {
        this.inputsInline = inputsInline;
    }

    public ValueType[] getPreviousStatement() {
        return previousStatement;
    }

    public void setPreviousStatement(ValueType[] previousStatement) {
        this.previousStatement = previousStatement;
    }

    public ValueType[] getNextStatement() {
        return nextStatement;
    }

    public void setNextStatement(ValueType[] nextStatement) {
        this.nextStatement = nextStatement;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}

package org.teapotech.blockly.block.def;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.util.BlockDefObjectSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public abstract class CustomBlockDefinition implements BlockDefinition {

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	private long executionTimeout;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!obj.getClass().equals(getClass())) {
			return false;
		}
		BlockDefinition bdef = (BlockDefinition) obj;
		if (bdef.getBlockType() != null && this.getBlockType() != null) {
			return bdef.getBlockType().equals(this.getBlockType());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (getClass().getName() + "#" + this.getBlockType()).hashCode() * 109;
	}

	public long getExecutionTimeout() {
		return executionTimeout;
	}

	public void setExecutionTimeout(long executionTimeout) {
		this.executionTimeout = executionTimeout;
	}

	@JsonSerialize(using = BlockDefObjectSerializer.class)
	public static class BlockDefObject {

		public static enum ValueType {
			Any, Number, Boolean, String, Array
		}

		private String type;
		private String message;
		private List<BlockField> args = new LinkedList<>();
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

		public List<BlockField> getArgs() {
			return args;
		}

		public void setArgs(List<BlockField> args) {
			this.args = args;
		}

		public void addArg(BlockField arg) {
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
}

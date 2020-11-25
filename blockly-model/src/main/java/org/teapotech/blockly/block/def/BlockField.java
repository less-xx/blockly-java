package org.teapotech.blockly.block.def;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BlockField {
	final String type;
	String name;

	@JsonIgnore
	String text;

	public BlockField(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public String getName() {
		return name;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class LabelField extends BlockField {

		public LabelField(String text) {
			super("input_dummy");
			this.text = text;
		}

		@Override
		public String getText() {
			return this.text;
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class InputField extends BlockField {

		public InputField(String text, String name) {
			super("input_value");
			this.text = text;
			this.name = name;
		}

		@Override
		public String getText() {
			return this.text;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}
}

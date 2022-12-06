package org.teapotech.blockly.block.def;

public class DefaultBlockDefinition implements BlockDefinition {

	private String blockType;
	private String category;
	private String style;

	public DefaultBlockDefinition(String blockType, String category, String style) {
		this.blockType = blockType;
		this.category = category;
		this.style = style;
	}

	@Override
	public String getConfiguration() {
		return null;
	}

	@Override
	public String getBlockType() {
		return this.blockType;
	}

	@Override
	public String getCategory() {
		return this.category;
	}

	public String getStyle() {
		return style;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof DefaultBlockDefinition)) {
			return false;
		}
		DefaultBlockDefinition def = (DefaultBlockDefinition) obj;
		if (def.getBlockType() != null && this.blockType != null) {
			return def.getBlockType().equals(this.blockType);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		if (this.blockType != null) {
			return (this.getClass().getName() + "#" + this.blockType).hashCode() * 101;
		}
		return super.hashCode();
	}
}

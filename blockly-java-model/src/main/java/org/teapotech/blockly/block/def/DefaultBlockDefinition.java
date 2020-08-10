package org.teapotech.blockly.block.def;

public class DefaultBlockDefinition implements BlockDefinition {

	private String blockType;
	private String category;

	public DefaultBlockDefinition(String blockType, String category) {
		this.blockType = blockType;
		this.category = category;
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

}

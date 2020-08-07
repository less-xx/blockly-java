package org.teapotech.blockly.block.def;

public abstract class CustomBlockDefinition implements BlockDefinition {

	protected String configuration;

	@Override
	public String getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
}

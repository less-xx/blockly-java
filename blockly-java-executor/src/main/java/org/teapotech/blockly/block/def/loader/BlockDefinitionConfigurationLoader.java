package org.teapotech.blockly.block.def.loader;

public interface BlockDefinitionConfigurationLoader {

	public String loadConfigurationByBlockType(String blockType) throws Exception;

}

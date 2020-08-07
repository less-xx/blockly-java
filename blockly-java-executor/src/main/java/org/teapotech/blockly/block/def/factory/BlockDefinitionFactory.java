package org.teapotech.blockly.block.def.factory;

import org.teapotech.blockly.block.def.BlockDefinition;

public interface BlockDefinitionFactory {

	BlockDefinition createBlockDefinition(String blockType) throws Exception;
}

package org.teapotech.blockly.block.def.factory;

import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.CustomBlockDefinition;
import org.teapotech.blockly.block.def.loader.BlockDefinitionConfigurationLoader;
import org.teapotech.blockly.block.def.variable.GetLocalVariableBlock;
import org.teapotech.blockly.block.def.variable.SetLocalVariableBlock;

public class DefaultBlockDefinitionFactory implements BlockDefinitionFactory {

	private BlockDefinitionConfigurationLoader configurationLoader;

	public DefaultBlockDefinitionFactory(BlockDefinitionConfigurationLoader configurationLoader) {
		this.configurationLoader = configurationLoader;
	}

	@Override
	public BlockDefinition createBlockDefinition(String blockType) throws Exception {

		CustomBlockDefinition blockDef = null;
		if (blockType.equalsIgnoreCase(GetLocalVariableBlock.TYPE)) {
			GetLocalVariableBlock bd = new GetLocalVariableBlock();
			blockDef = bd;
		} else if (blockType.equalsIgnoreCase(SetLocalVariableBlock.TYPE)) {
			SetLocalVariableBlock bd = new SetLocalVariableBlock();
			blockDef = bd;
		}
		if (blockDef == null) {
			throw new IllegalArgumentException("Block '" + blockType + "' is not supported.");
		}
		blockDef.setConfiguration(configurationLoader.loadConfigurationByBlockType(blockType));
		return blockDef;
	}

}

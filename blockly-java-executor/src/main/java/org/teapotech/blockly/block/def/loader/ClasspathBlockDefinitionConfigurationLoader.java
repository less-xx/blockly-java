package org.teapotech.blockly.block.def.loader;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class ClasspathBlockDefinitionConfigurationLoader implements BlockDefinitionConfigurationLoader {

	@Override
	public String loadConfigurationByBlockType(String blockType) throws Exception {
		String content = IOUtils.resourceToString("block-def-confs/" + blockType + ".tpl.json", StandardCharsets.UTF_8,
				getClass().getClassLoader());
		if (content == null) {
			throw new IllegalArgumentException(
					"Cannot find block configuration for " + blockType + " in the classpath.");
		}
		return content;
	}
}

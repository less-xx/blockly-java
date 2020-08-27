package org.teapotech.blockly.block.def;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public abstract class CustomBlockDefinition implements BlockDefinition {

	public String getBlockDefinitionTemplate() throws IOException {
		String blockType = getBlockType();
		InputStream in = getClass().getClassLoader().getResourceAsStream("block-def-confs/" + blockType + ".tpl.json");
		if (in == null) {
			return null;
		}
		return IOUtils.toString(in, "UTF-8");
	}

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

}

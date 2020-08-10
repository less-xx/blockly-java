package org.teapotech.blockly.block.def;

public abstract class CustomBlockDefinition implements BlockDefinition {

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

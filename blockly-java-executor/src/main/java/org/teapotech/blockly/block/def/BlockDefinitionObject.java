package org.teapotech.blockly.block.def;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockDefinitionObject {
	String type;
	String category;
	String definition;
	String colour;
	String executorClass;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getDefinition() {
		return definition;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getExecutorClass() {
		return executorClass;
	}

	public void setExecutorClass(String executorClass) {
		this.executorClass = executorClass;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BlockDefinitionObject)) {
			return false;
		}
		BlockDefinitionObject bdo = (BlockDefinitionObject) obj;
		if (bdo.getType() != null && this.type != null) {
			return bdo.getType().equals(this.type);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (BlockDefinitionObject.class.getName() + "/" + this.type).hashCode() * 97;

	}

}

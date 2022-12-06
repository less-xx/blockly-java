package org.teapotech.blockly.execute.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class BlockEvent {

	protected String workspaceId;
	protected String blockType;
	protected String blockId;

	public BlockEvent(String workspaceId, String blockType, String blockId) {
		this.workspaceId = workspaceId;
		this.blockId = blockId;
		this.blockType = blockType;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	protected void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getBlockType() {
		return blockType;
	}

	protected void setBlockType(String blockType) {
		this.blockType = blockType;
	}

	public String getBlockId() {
		return blockId;
	}

	protected void setBlockId(String blockId) {
		this.blockId = blockId;
	}

}

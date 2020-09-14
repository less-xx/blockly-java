package org.teapotech.blockly.workspace.event;

import org.teapotech.blockly.entity.WorkspaceExecution.Status;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class WorkspaceEvent {
	private String workspaceId;
	private String status;
	private long instanceId;

	public WorkspaceEvent() {
	}

	public WorkspaceEvent(String workspaceId, long instanceId, Status status) {
		this.workspaceId = workspaceId;
		this.status = status.name();
		this.instanceId = instanceId;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	protected void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}

	public long getInstanceId() {
		return instanceId;
	}
}

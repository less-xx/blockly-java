package org.teapotech.blockly.block.execute;

import java.util.Date;

import org.teapotech.blockly.user.UserDelegate;

public class WorkspaceExecution {

    public static enum Status {
        Waiting, Running, Stopping, Stopped, Succeeded, Failed, Paused, Timeout
    }

    private final long instanceId;
    private final String workspaceId;
    private final Date createdTime;
    private Date startTime;
    private Date endTime;
    private Status status = Status.Waiting;
    private String message;
    private final UserDelegate startBy;
    private UserDelegate endBy;
    private String currentBlockId;

    public WorkspaceExecution(long instanceId, String workspaceId, UserDelegate startBy) {
        this.instanceId = instanceId;
        this.workspaceId = workspaceId;
        this.startBy = startBy;
        this.createdTime = new Date();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public synchronized Status getStatus() {
        return status;
    }

    public synchronized void setStatus(Status status) {
        this.status = status;
    }

    public long getInstanceId() {
        return instanceId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDelegate getStartBy() {
        return startBy;
    }

    public UserDelegate getEndBy() {
        return endBy;
    }

    public void setEndBy(UserDelegate endBy) {
        this.endBy = endBy;
    }

    public String getCurrentBlockId() {
        return currentBlockId;
    }

    public void setCurrentBlockId(String currentBlockId) {
        this.currentBlockId = currentBlockId;
    }

}

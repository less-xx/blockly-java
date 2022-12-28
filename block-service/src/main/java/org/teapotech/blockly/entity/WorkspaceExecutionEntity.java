package org.teapotech.blockly.entity;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "workspace_execution")
public class WorkspaceExecutionEntity {

    @Id
    @SequenceGenerator(name = "workspace_execution_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workspace_execution_id_seq")
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @Column(name = "workspace_id", nullable = false)
    private String workspaceId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "start_time", nullable = true)
    private Date startTime;

    @Column(name = "end_time", nullable = true)
    private Date endTime;

    @Column(name = "start_by_user_id", nullable = true)
    private String startByUserId;

    @Column(name = "start_by_user_name")
    private String startByUserName;

    @Column(name = "end_by_user_id", nullable = true)
    private String endByUserId;

    @Column(name = "end_by_user_name", nullable = true)
    private String endByUserName;

    @Column(name = "message", nullable = true)
    private String message;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdatedTime;

    @Version
    @Column(name = "version", columnDefinition = "int DEFAULT 0", nullable = false)
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public String getStartByUserId() {
        return startByUserId;
    }

    public void setStartByUserId(String startByUserId) {
        this.startByUserId = startByUserId;
    }

    public String getStartByUserName() {
        return startByUserName;
    }

    public void setStartByUserName(String startByUserName) {
        this.startByUserName = startByUserName;
    }

    public String getEndByUserId() {
        return endByUserId;
    }

    public void setEndByUserId(String endByUserId) {
        this.endByUserId = endByUserId;
    }

    public String getEndByUserName() {
        return endByUserName;
    }

    public void setEndByUserName(String endByUserName) {
        this.endByUserName = endByUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String s = """
                {
                	id: %d,
                	workspaceId: %s,
                	status: %s,
                	startTime: %s,
                	endTime: %s,
                	message: %s
                }
                """;
        return s.formatted(this.id, this.workspaceId, this.status, this.startTime, this.endTime, this.message);
    }

}

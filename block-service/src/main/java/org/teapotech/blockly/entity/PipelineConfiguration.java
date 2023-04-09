package org.teapotech.blockly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "pipeline_config", indexes = { @Index(columnList = "pipeline_name") })
public class PipelineConfiguration {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID")
    @Column(name = "id")
    private String id;

    @Column(name = "pipeline_name", nullable = false, unique = true)
    private String name;

    @Column(name = "pipeline_config", columnDefinition = "TEXT", nullable = false)
    private String configuration;

    @Column(name = "execution_timeout", nullable = false)
    private long executionTimeoutInSec = 0;

    @Column(name = "created_by_user_id")
    private String createdByUserId;

    @Column(name = "created_by_user_name")
    private String createdByUserName;

    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    private Date createdTime;

    @Column(name = "updated_by_user_id")
    private String updatedByUserId;

    @Column(name = "updated_by_user_name")
    private String updatedByUserName;

    @Column(name = "last_updated_time")
    @UpdateTimestamp
    private Date lastUpdatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(String updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public long getExecutionTimeoutInSec() {
        return executionTimeoutInSec;
    }

    public void setExecutionTimeoutInSec(long executionTimeoutInSec) {
        this.executionTimeoutInSec = executionTimeoutInSec;
    }

}

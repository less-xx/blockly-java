package org.teapotech.blockly.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "workspace_config", indexes = { @Index(columnList = "name") })
public class WorkspaceConfigEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "workspace_name", nullable = false, unique = true)
    private String name;

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

}

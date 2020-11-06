/**
 * 
 */
package org.teapotech.blockly.user.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.teapotech.user.Permission;
import org.teapotech.user.RoleDelegate;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "role_definition")
public class Role implements RoleDelegate {

	public static final String DEFAULT_ROLE_ID_ADMIN = "ADMINISTRATOR";
	public static final String DEFAULT_ROLE_ID_CREDENTIAL_MANAGER = "CREDENTIAL_MANAGER";
	public static final String DEFAULT_ROLE_ID_RESOURCE_MANAGER = "RESOURCE_MANAGER";
	public static final String DEFAULT_ROLE_ID_USER = "USER";

	@Id
	@Column(name = "role_id", nullable = false)
	private String roleId;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_desc")
	private String description;

	@Column(name = "created_time", nullable = false)
	@CreatedDate
	private Date createdTime;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "last_modified_time", nullable = false)
	@UpdateTimestamp
	private Date lastModifiedTime;

	@Column(name = "last_modified_by", nullable = false)
	private String lastModifiedBy;

	@Transient
	private List<Permission> permissions;

	@Column(name = "can_see_all_quotes", nullable = false)
	private boolean canSeeAllQuotes = false;

	@Override
	public List<? extends Permission> getPermissions() {
		return this.permissions;
	}

	@Override
	public String getRoleId() {
		return this.roleId;
	}

	@Override
	public String getRoleName() {
		return this.roleName;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void addPermission(Permission p) {
		if (this.permissions == null) {
			this.permissions = new ArrayList<>();
		}
		this.permissions.add(p);
	}

	public void setCanSeeAllQuotes(boolean canSeeAllQuotes) {
		this.canSeeAllQuotes = canSeeAllQuotes;
	}

	public boolean canSeeAllQuotes() {
		return canSeeAllQuotes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role r = (Role) obj;
		if (r.roleId != null && this.roleId != null) {
			return this.roleId.equals(r.roleId);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		if (this.roleId != null) {
			return (this.getClass() + "##" + this.roleId).hashCode();
		}
		return super.hashCode();
	}

	@Override
	public String toString() {
		return this.roleId + "-" + this.roleName;
	}

}

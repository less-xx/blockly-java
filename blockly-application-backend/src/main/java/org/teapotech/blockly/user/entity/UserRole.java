/**
 * 
 */
package org.teapotech.blockly.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jiangl
 *
 */
@Entity
@Table(name = "user_role")
public class UserRole {

	@EmbeddedId
	private UserRolePK primaryKey;

	public UserRolePK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(UserRolePK primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Embeddable
	public static class UserRolePK implements Serializable {

		private static final long serialVersionUID = -5422046626501193208L;

		@Column(name = "user_id", nullable = false)
		private String userId;

		@Column(name = "role_id", nullable = false)
		private String roleId;

		public UserRolePK() {
		}

		public UserRolePK(String userId, String roleId) {
			this.userId = userId;
			this.roleId = roleId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getRoleId() {
			return roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof UserRolePK)) {
				return false;
			}

			UserRolePK urpk = (UserRolePK) obj;
			if (urpk.userId != null && this.roleId != null) {
				return urpk.userId.equals(this.userId) && urpk.roleId == this.roleId;
			}
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			if (this.roleId != null && this.userId != null) {
				return (this.userId + "&&" + this.roleId).hashCode();
			}
			return super.hashCode();
		}
	}
}

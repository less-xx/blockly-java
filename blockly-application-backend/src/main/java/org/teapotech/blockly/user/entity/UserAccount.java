/**
 * 
 */
package org.teapotech.blockly.user.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.teapotech.user.RoleDelegate;
import org.teapotech.user.UserDelegate;

/**
 * @author jiangl
 *
 */
@Entity
@Table(name = "user_account")
public class UserAccount implements UserDelegate {

	@Id
	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "email")
	private String email;

	@Column(name = "created_time", nullable = false)
	@CreatedDate
	private Date createdTime;

	@Transient
	private Set<RoleDelegate> roles;

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Set<RoleDelegate> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<RoleDelegate> roles) {
		this.roles = roles;
	}

}

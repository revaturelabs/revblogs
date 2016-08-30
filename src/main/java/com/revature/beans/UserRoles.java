package com.revature.beans;

import javax.persistence.*;

/**
 * Users can be of role:
 * 		CONTRIBUTOR, ADMIN
 */

@Entity
@Table(name="REV_BLOG_USER_ROLES")
public class UserRoles {
	
		@Id
		@Column(name="REV_BLOG_USER_ROLE_ID")
	private int userRoleId;
		@Column(name="REV_BLOG_ROLE", nullable=false)
	private String role;
				
	/**
	 *  Constructors
	 */
		
	public UserRoles() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public UserRoles(int userRoleId, String role) {
		super();
		this.userRoleId = userRoleId;
		this.role = role;
	}
	
	/**
	 *  Getters & Setters
	 */

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
	
}

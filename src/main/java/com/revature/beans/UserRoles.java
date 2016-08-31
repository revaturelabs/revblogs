package com.revature.beans;

import javax.persistence.*;

/**
 * Users can be of role:
 * 		CONTRIBUTOR, ADMIN
 */

@Entity
@Table(name="PP_ROLES")
public class UserRoles {
	
	//----------------------------------
	// Attributes
	@Id
	@Column(name="ROLE_ID", nullable=false)
	private int userRoleId;
	
	@Column(name="ROLE_ROLE", unique=true, nullable=false)
	private String role;
				
	/**
	 *  Constructors
	 */
		
	public UserRoles() {
		super();
	}
		
	public UserRoles(int id, String role) {
		super();
		this.userRoleId = id;
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

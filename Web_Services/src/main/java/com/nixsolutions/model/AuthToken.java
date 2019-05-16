package com.nixsolutions.model;

public class AuthToken {

	private String token;
	private String username;
	private Role roleId;

	public AuthToken() {
	}

	public AuthToken(String token, String username, Role roleId) {
		this.token = token;
		this.username = username;
		this.roleId = roleId;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public AuthToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
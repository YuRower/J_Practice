package com.nixsolutions.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginUser {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
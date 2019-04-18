package com.nixsolutions.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable {
	USER("USER"), ADMIN("ADMIN");

	String userProfileType;

	private UserProfileType(String userProfileType) {
		this.userProfileType = userProfileType;
	}

	public String getUserProfileType() {
		return userProfileType;
	}

	public static UserProfileType getType(User user) {
		Integer typeId = user.getRoleId().getId();
		return UserProfileType.values()[typeId.intValue() - 1];
	}
}
/*
 * public static UserType getType(User user) { Integer typeId =
 * user.getRoleId().getId(); return UserType.values()[typeId.intValue() - 1]; }
 * 
 * public String getName() { return name().toLowerCase(); }
 * 
 * public static UserType getByName(String name) { for (UserType e : values()) {
 * if (e.name().toLowerCase().equals(name)) return e; } return UNKNOWN; }
 */
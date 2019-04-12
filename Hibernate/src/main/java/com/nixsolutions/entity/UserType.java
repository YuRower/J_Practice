package com.nixsolutions.entity;

public enum UserType {
	USER, ADMIN, UNKNOWN, GUEST;

	public static UserType getType(User user) {
		Integer typeId = user.getRoleId().getId();
		return UserType.values()[typeId.intValue() - 1];
	}

	public String getName() {
		return name().toLowerCase();
	}

	public static UserType getByName(String name) {
		for (UserType e : values()) {
			if (e.name().toLowerCase().equals(name))
				return e;
		}
		return UNKNOWN;
	}
}

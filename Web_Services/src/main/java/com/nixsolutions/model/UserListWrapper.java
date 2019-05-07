package com.nixsolutions.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserListWrapper {
	@JsonProperty("user")
	private Collection<User> items;

	@Override
	public String toString() {
		return "UserListWrapper [items=" + items + "]";
	}

	public Collection<User> getItems() {
		return items;
	}

	public void setItems(Collection<User> items) {
		this.items = items;
	}

}

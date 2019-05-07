package com.nixsolutions.dao;

import java.util.List;

import com.nixsolutions.model.Role;

public interface RoleDao {

	List<Role> findAll();

	Role findByName(String type);

	Role findById(int id);

	void create(Role role);

	void update(Role role);

	void remove(Role role);

}

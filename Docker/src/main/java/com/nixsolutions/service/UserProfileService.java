package com.nixsolutions.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.model.Role;

@Transactional
public interface UserProfileService {

	List<Role> findAll();

	Role findById(Integer id);

	Role findByName(String name);

}

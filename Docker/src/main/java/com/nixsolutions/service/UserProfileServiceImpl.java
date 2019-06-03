package com.nixsolutions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.model.Role;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	RoleDao dao;

	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public Role findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Role findByName(String name) {
		return dao.findByName(name);

	}
}

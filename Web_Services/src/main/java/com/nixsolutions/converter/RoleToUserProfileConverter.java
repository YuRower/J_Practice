package com.nixsolutions.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.nixsolutions.model.Role;
import com.nixsolutions.service.UserProfileService;

@Component
public class RoleToUserProfileConverter implements Converter<Object, Role> {

	static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);

	@Autowired
	UserProfileService userProfileService;

	@Override
	public Role convert(Object element) {
		logger.info("Converterrrrrrrrrrrr---------------------------------:");
		Integer id = Integer.parseInt((String) element);
		Role profile = userProfileService.findById(id);
		logger.info("Profile : {}", profile);
		return profile;
	}

}
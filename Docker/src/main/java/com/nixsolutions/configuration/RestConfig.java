package com.nixsolutions.configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.nixsolutions.webservices.UserResource;

@ApplicationPath("/rest")
public class RestConfig extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(UserResource.class));
	}
}
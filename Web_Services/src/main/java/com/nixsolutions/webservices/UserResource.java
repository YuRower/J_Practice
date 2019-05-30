package com.nixsolutions.webservices;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.nixsolutions.model.ApiUserResponse;
import com.nixsolutions.model.User;
import com.nixsolutions.service.UserProfileService;
import com.nixsolutions.service.UserService;

@Path("/users")
@Component
@CrossOrigin(origins = "http://localhost:3200", maxAge = 3600)
public class UserResource {
	static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
	@Autowired
	private UserProfileService userProfile;

	@Autowired
	private UserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> fetchAll() {
		return userService.findAllUsers();
	}

	@GET
	@Path("user/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiUserResponse<User> get(@PathParam("login") String login) {
		User user = userService.findByLogin(login);
		LOGGER.debug("User -- > {}", user);

		if (user == null) {
			return new ApiUserResponse<User>(HttpStatus.NOT_FOUND.value(), "User not found", user);
		}
		return new ApiUserResponse<User>(HttpStatus.OK.value(), "User successfully found by id", user);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiUserResponse<User> create(User user) {
		if (userService.isUserLoginUnique(user.getLogin())) {
			return new ApiUserResponse(HttpStatus.CONFLICT.value(), "user already exists", user);
		}
		LOGGER.debug("User -- > {} ,and role {}", user, user.getRoleId());

		userService.saveUser(user);
		return new ApiUserResponse<User>(HttpStatus.CREATED.value(), "User saved successfully", user);

	}

	@PUT
	@Path("user/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public ApiUserResponse<User> update(@PathParam("login") String login, User newUser) {

		LOGGER.debug("login for update -- > {} and role {} ", login, newUser.getRoleId());

		boolean userExist = userService.isUserLoginUnique(login);
		if (userExist == false) {
			return new ApiUserResponse(HttpStatus.BAD_REQUEST.value(), "user not found", newUser);
		}

		LOGGER.debug("------------------------User for upadte -------- > {}", newUser);
		userService.updateUser(newUser);
		return new ApiUserResponse(HttpStatus.NO_CONTENT.value(), "User updated successfully", newUser);

	}

	@DELETE
	@Path("user/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiUserResponse<User> delete(@PathParam("login") String login) {
		User user = userService.findByLogin(login);
		if (user == null) {
			return new ApiUserResponse(HttpStatus.BAD_REQUEST.value(), "user not found", user);
		}
		LOGGER.debug("------------------------User for delete -------- > {}", user);

		userService.delete(user);
		return new ApiUserResponse(HttpStatus.ACCEPTED.value(), "User deleted successfully", user);

	}

}
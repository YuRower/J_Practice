package com.nixsolutions.web_services;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nixsolutions.model.User;
import com.nixsolutions.service.UserService;

@Path("/users")
@Component
public class UserResource {
	static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	/*
	 * protected static ClientConfig createClientConfig() { ClientConfig config =
	 * new ClientConfig(); config.register(new
	 * LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
	 * Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000)); return config; }
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> fetchAll() {
		return userService.findAllUsers();
	}

	@GET
	@Path("user/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("login") String login) {
		User user = userService.findByLogin(login);
		LOGGER.debug("User -- > {}", user);

		if (user == null) {
			return Response.status(404).entity("User not found").build();
		}
		return Response.ok().entity(user).build();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(User user) {
		if (userService.isUserLoginUnique(user.getLogin())) {
			return Response.status(Status.CONFLICT).build();
		}
		userService.saveUser(user);
		return Response.status(Status.CREATED).build();

	}

	@PUT
	@Path("user/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("login") String login, User newUser) {
		LOGGER.debug("login -- > {}", login);

		boolean userExist = userService.isUserLoginUnique(login);
		if (userExist == false) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		LOGGER.debug("User -- > {}", newUser);
		userService.updateUser(newUser);
		return Response.noContent().build();

	}

	@DELETE
	@Path("user/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("login") String login) {
		User user = userService.findByLogin(login);
		if (user == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		userService.delete(user);
		return Response.status(202).entity("User deleted successfully !!").build();

	}

}
package com.nixsolutions.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nixsolutions.model.ApiResponse;
import com.nixsolutions.model.AuthToken;
import com.nixsolutions.model.LoginUser;
import com.nixsolutions.model.User;
import com.nixsolutions.security.jwt.JwtTokenUtil;
import com.nixsolutions.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {
	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	private final String messageAdmin = new String("{\"message\":\"admin\"}");
	private final String messageUser = new String("{\"message\":\"user\"}");
	private final String messageWrong = new String("{\"message\":\"invalid Credentials\"}");
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
		LOGGER.debug("User -->>>> {}", loginUser);
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getLogin(), loginUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		final User user = userService.findByLogin(loginUser.getLogin());
		final String token = jwtTokenUtil.generateToken(user);
		/*
		 * return Response.status(Response.Status.OK).entity(new
		 * AuthToken(token)).type(MediaType.APPLICATION_JSON) .build();
		 */
		return new ApiResponse<>(200, "success", new AuthToken(token, user.getLogin(), user.getRoleId()));
	}

}
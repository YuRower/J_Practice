package com.nixsolutions.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nixsolutions.model.User;
import com.nixsolutions.service.UserService;

@Controller
@RequestMapping("/")
public class CommonController {
	private static Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	UserService userService;

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		LOGGER.info("list all existing users");

		List<User> users = userService.findAllUsers();
		LOGGER.info("user list  {} ", users);

		model.addAttribute("userList", users);
		model.addAttribute("loggedinuser", getPrincipal());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String personLogin = auth.getName();

		LOGGER.debug("define user Authority");

		model.addAttribute("personLogin", personLogin);
		LOGGER.info("{}", personLogin);
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		LOGGER.info("authorities {}", authorities);

		if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			LOGGER.debug("user");
			return "user";
		} else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			LOGGER.debug("admin");
			return "admin";
		} else {
			LOGGER.debug("login");
			return "login";
		}

	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		LOGGER.debug("Access-Denied redirect");
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("logout requests");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		LOGGER.info("authorities {}", authorities);
		boolean isAuthenticated = isCurrentAuthenticationAnonymous();
		LOGGER.info("user  already authenticated -- >", isAuthenticated);

		if (isAuthenticated) {
			return "login";
		} else {
			return "redirect:/list";
		}
	}

	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}

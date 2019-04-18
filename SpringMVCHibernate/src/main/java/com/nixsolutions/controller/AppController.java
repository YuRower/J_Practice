package com.nixsolutions.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nixsolutions.model.Role;
import com.nixsolutions.model.User;
import com.nixsolutions.service.UserProfileService;
import com.nixsolutions.service.UserService;
import com.nixsolutions.util.VerifyRecaptcha;

@Controller
@SessionAttributes("roles")
public class AppController {
	private static Logger LOGGER = LoggerFactory.getLogger(AppController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/registr" }, method = RequestMethod.GET)
	public String registrUser(ModelMap model) {
		LOGGER.info("forward to registration page");
		User user = new User();

		List<Role> roles = userProfileService.findAll();
		Map<Integer, String> rolesMap = new LinkedHashMap<>();
		for (Role role : roles) {
			rolesMap.put(role.getId(), role.getName());

		}
		LOGGER.info("list of roles -- > {}", rolesMap);

		model.addAttribute("roles", rolesMap);

		model.addAttribute("user", user);
		model.addAttribute("action_user", "Registration");
		model.addAttribute("loggedinuser", getPrincipal());
		LOGGER.info("guest forward to registration page");
		return "registration";
	}

	@RequestMapping(value = { "/registr" }, method = RequestMethod.POST)
	public String registrUser(@Valid User user, BindingResult result, ModelMap model, HttpServletRequest request)
			throws IOException {
		model.addAttribute("registr", true);

		boolean verify = isCaptchaValid(request);
		LOGGER.debug("recaptcha response status  {}", verify);

		if (!verify) {
			return "registration";

		}

		if (!isValid(result, user)) {
			return "registration";
		}

		LOGGER.info("guest performed registration");
		userService.saveUser(user);
		LOGGER.info("saved  user -->  {}", user);

		model.addAttribute("success",
				"User " + user.getFirstName() + " " + user.getLastName() + " creation successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		LOGGER.info("forward to create new user ");
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("action_user", "Creation");
		model.addAttribute("loggedinuser", getPrincipal());

		return "registration";
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result, ModelMap model, HttpServletRequest request) {
		LOGGER.info("Create new user method");

		boolean verify = isCaptchaValid(request);
		LOGGER.debug("recaptcha response status  {}", verify);

		if (!verify) {
			return "registration";

		}

		if (!isValid(result, user)) {
			return "registration";
		}
		userService.saveUser(user);
		LOGGER.info("saved  user -->  {}", user);

		model.addAttribute("success",
				"User " + user.getFirstName() + " " + user.getLastName() + " creation successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/edit-user-{login}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String login, ModelMap model) {
		LOGGER.debug("Forward to edit user page");

		User user = userService.findByLogin(login);
		LOGGER.debug(" update user with login {}", login);

		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("action_user", "Update");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	@RequestMapping(value = { "/edit-user-{login}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String login,
			HttpServletRequest request) {
		LOGGER.debug("update user method");

		boolean verify = isCaptchaValid(request);
		LOGGER.debug("recaptcha response status  {}", verify);

		if (!verify) {
			return "registration";

		}

		if (result.hasErrors()) {
			for (Object object : result.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;

					LOGGER.error(fieldError.getCode());
				}

				if (object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;

					LOGGER.error(objectError.getCode());
				}
			}
			return "registration";
		}
		LOGGER.debug("brfore update -- >" + user);

		userService.updateUser(user);

		LOGGER.debug("after update -- >" + user);

		model.addAttribute("success",
				"User " + user.getFirstName() + " " + user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/delete-user-{login}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String login) {

		LOGGER.debug(" delete user by login -- >", login);

		userService.deleteByLogin(login);
		LOGGER.debug(" redirect after user deletion");

		return "redirect:/list";
	}

	@ModelAttribute("roles")
	public List<Role> initializeProfiles() {
		return userProfileService.findAll();
	}

	private boolean isValid(BindingResult result, User user) {
		if (result.hasErrors()) {
			return false;
		}

		if (userService.isUserLoginUnique(user.getLogin())) {
			FieldError ssoError = new FieldError("user", "login", messageSource.getMessage("non.unique.login",
					new String[] { user.getLogin() }, Locale.getDefault()));
			result.addError(ssoError);
			return false;
		}
		return true;
	}

	private boolean isCaptchaValid(HttpServletRequest request) {
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		boolean verify = false;
		try {
			verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		} catch (IOException e) {
			LOGGER.debug("{}", e);
			throw new RuntimeException();
		}

		LOGGER.debug("recaptcha response status  {}", verify);

		return verify;

	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		LOGGER.debug("User principal  , {}", userName);
		return userName;
	}

}
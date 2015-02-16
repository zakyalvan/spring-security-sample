package com.innovez.sample.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.innovez.sample.core.service.UserManagementService;

@Controller
@RequestMapping(value="/user-admin/**")
public class UserAdministrationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAdministrationController.class);
	
	@Autowired
	private UserManagementService userService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(Model model) {
		LOGGER.debug("Show user administration main page");
		
		return "user-admin/index";
	}
}

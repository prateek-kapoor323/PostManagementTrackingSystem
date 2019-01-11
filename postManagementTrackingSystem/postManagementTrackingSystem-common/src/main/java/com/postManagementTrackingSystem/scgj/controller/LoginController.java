package com.postManagementTrackingSystem.scgj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 *@author Prateek Kapoor
	 *@param user
	 *@return the username, role and session id of the user
	 */
	@RequestMapping("/user")
	public Principal loginAuthentication(Principal user)
	{
		LOGGER.debug("Request received from frontend to authenticate the user");
		
		try
		{
			LOGGER.debug("In try block to authenticate the user");
			return user;
			
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while trying to authenticate the user: " +e);
			return null;
		}
	}
}

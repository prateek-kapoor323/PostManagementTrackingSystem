package com.postManagementTrackingSystem.scgj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postManagementTrackingSystem.scgj.common.Privilege;
import com.postManagementTrackingSystem.scgj.common.SessionUserUtility;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfLoggedInUserDto;
import com.postManagementTrackingSystem.scgj.service.GetNameOfLoggedInUserService;

@RestController
public class GetNameOfLoggedInUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetNameOfLoggedInUserController.class);
	
	@Autowired
	private SessionUserUtility sessionUserUtility;
	@Autowired
	private GetNameOfLoggedInUserService getNameOfLoggedInUserService;
	
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method retrieves the email of the logged in user from the session
	 * and sends the email to the service which is again forwarded to the DAO to get the name
	 * associated with the email of the logged in user
	 * @return
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/getUserName")
	public GetNameOfLoggedInUserDto getNameOfLoggedInUser()
	{
		LOGGER.debug("Request received from front end to get the name of the logged in user");
		LOGGER.debug("Fetching the email of the logged in user from session");
		String email = sessionUserUtility.getSessionMangementfromSession().getUsername();
		LOGGER.debug("Username received from session is: "+email);
		LOGGER.debug("Sending request to GetNameOfLoggedInUserService to get the name of the logged in user");
		String name = getNameOfLoggedInUserService.getNameOfLoggedInUser(email);
		return new GetNameOfLoggedInUserDto(name);
	}
}

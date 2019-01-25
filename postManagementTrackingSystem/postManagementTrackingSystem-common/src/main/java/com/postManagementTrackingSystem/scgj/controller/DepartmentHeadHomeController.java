package com.postManagementTrackingSystem.scgj.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postManagementTrackingSystem.scgj.common.Privilege;
import com.postManagementTrackingSystem.scgj.common.SessionUserUtility;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNotStartedApplicationDTO;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfDepartmentEmployeesDTO;
import com.postManagementTrackingSystem.scgj.service.DepartmentHeadHomeService;

@RestController
public class DepartmentHeadHomeController {

	@Autowired
	private DepartmentHeadHomeService departmentHeadHomeService;
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadHomeController.class);
	
	/**
	 * This method gets the name of all the employees of the department corresponding to the department of the logged in user(Who is department head)
	 * @return List of GetNameOfDepartmentEmployeesDTO object else null
	 */
	@Privilege(value= {"DH"})
	@RequestMapping("/getNameOfDepartmentEmployees")
	public List<GetNameOfDepartmentEmployeesDTO> getNameOfDepartmentEmployees()
	{
		LOGGER.debug("Request received to get the name of all the employees for the department of the logged in user");
		LOGGER.debug("Fetching the email of the logged in user from session");
		String email = sessionUserUtility.getSessionMangementfromSession().getUsername();
		LOGGER.debug("Checking if the retreived email is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Could not retreive the email of the logged in user");
			LOGGER.error("Request could not be processed further");
			LOGGER.error("Returning null to front end");
			return null;
		}
		LOGGER.debug("The retreived email is not null or empty");
		LOGGER.debug("The retreived email from the session is: "+email);
		LOGGER.debug("Processing request to get the names of employees");
		LOGGER.debug("Sending request to service");
		return departmentHeadHomeService.getNameOfDepartmentEmployees(email);
	}
	
	@Privilege(value= {"DH"})
	@RequestMapping("/getNotStartedApplications")
	public Collection<DepartmentHeadNotStartedApplicationDTO> getNotStartedApplicationDetails()
	{
		LOGGER.debug("Request received from front end to get the details of applications with status as NOT STARTED");
		LOGGER.debug("Fetching the email of the logged in user from session");
		String email = sessionUserUtility.getSessionMangementfromSession().getUsername();
		LOGGER.debug("Checking if the retreived email is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Could not retreive the email of the logged in user");
			LOGGER.error("Request could not be processed further");
			LOGGER.error("Returning null to front end");
			return null;
		}
		LOGGER.debug("The retreived email is not null or empty");
		LOGGER.debug("The retreived email from the session is: "+email);
		LOGGER.debug("Processing request to get the application details with status as NOT STARTED");
		LOGGER.debug("Sending request to service");
		return departmentHeadHomeService.getNotStartedApplicationDetails(email);
		
	}
}

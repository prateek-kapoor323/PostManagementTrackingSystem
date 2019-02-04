package com.postManagementTrackingSystem.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.postManagementTrackingSystem.scgj.common.Privilege;
import com.postManagementTrackingSystem.scgj.common.SessionUserUtility;
import com.postManagementTrackingSystem.scgj.dto.ApplicationSearchResultsDto;
import com.postManagementTrackingSystem.scgj.service.DepartmentEmployeeSearchService;

@RestController
public class DepartmentEmployeeSearchController 
{

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentEmployeeSearchController.class);
	@Autowired
	private DepartmentEmployeeSearchService departmentEmployeeSearchService;
	
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	@Privilege(value= {"DE"})
	@RequestMapping("/getApplicationDetailsByApplicationIdDE")
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByApplicationId(@RequestParam("applicationId") String applicationId)
	{
		LOGGER.debug("Request received from service to get the details of application with application id");
		LOGGER.debug("Checking if the received parameter is null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("The application id received is null or empty");
			LOGGER.error("Request cannot be processed, returning null to front end");
			return null;
		}
		LOGGER.debug("Received parameter is not null: "+applicationId);
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
		LOGGER.debug("Sending request to service to get the application details for application id: "+applicationId+" for logged in user with email: "+email);
		return departmentEmployeeSearchService.getApplicationDetailsByApplicationIdDE(applicationId, email);
		
	}
	
	@Privilege(value= {"DE"})
	@RequestMapping("/getApplicationDetailsUsingApplicationStatusDE")
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByStatus(@RequestParam("status") String status)
	{
		LOGGER.debug("Request received from service to get the details of application with status: "+status);
		LOGGER.debug("Checking if the received parameter is null or empty");
		if(status==null||status.isEmpty())
		{
			LOGGER.error("The status received is null or empty");
			LOGGER.error("Request cannot be processed, returning null to front end");
			return null;
		}
		LOGGER.debug("Received parameter is not null: "+status);
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
		LOGGER.debug("Email retreived is: "+email);
		LOGGER.debug("Processing request to get applications for the department of logged in user with status: "+status);
		LOGGER.debug("Sending request to service to get the applications for the department of logged in user with status: "+status);
		return departmentEmployeeSearchService.getApplicationDetailsByStatusDE(status, email);
	}
}
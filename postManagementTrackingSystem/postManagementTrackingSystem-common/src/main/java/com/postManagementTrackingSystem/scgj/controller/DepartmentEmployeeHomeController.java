package com.postManagementTrackingSystem.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.postManagementTrackingSystem.scgj.common.Privilege;
import com.postManagementTrackingSystem.scgj.common.SessionUserUtility;
import com.postManagementTrackingSystem.scgj.dto.AssignedApplicationsDTO;
import com.postManagementTrackingSystem.scgj.dto.PerformActionsOverApplicationDTO;
import com.postManagementTrackingSystem.scgj.service.DepartmentEmployeeHomeService;

@RestController
public class DepartmentEmployeeHomeController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentEmployeeHomeController.class);
	@Autowired
	private SessionUserUtility sessionUserUtility;
	@Autowired
	private DepartmentEmployeeHomeService departmentEmployeeHomeService;
	
	@Privilege(value= {"DE"})
	@RequestMapping("/getAssignedApplicationsDE")
	public Collection<AssignedApplicationsDTO> getAssignedApplications()
	{
		LOGGER.debug("Request received from front end to get the details of the assigned applications for logged in user");
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
		LOGGER.debug("Sending request to service to get the details of assigned applications to logged in user");
		return departmentEmployeeHomeService.getAssignedApplications(email);

	}
	
	@Privilege(value= {"DE"})
	@RequestMapping("/getInActionApplicationsDE")
	public Collection<AssignedApplicationsDTO> getInActionApplications()
	{
		LOGGER.debug("Request received from front end to get the details of IN ACTION applications for logged in user");
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
		LOGGER.debug("Sending request to service to get the details of IN ACTION applications to logged in user");
		return departmentEmployeeHomeService.getInActionApplications(email);
	}
	
	@Privilege(value= {"DE"})
	@RequestMapping("/getOnHoldApplicationsDE")
	public Collection<AssignedApplicationsDTO> getOnHoldApplications()
	{
		LOGGER.debug("Request received from front end to get the details of ON HOLD applications for logged in user");
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
		LOGGER.debug("Sending request to service to get the details of ON HOLD applications to logged in user");
		return departmentEmployeeHomeService.getOnHoldApplications(email);
	}
	
	@Privilege(value= {"DE"})
	@RequestMapping("/getDelayedApplicationsDE")
	public Collection<AssignedApplicationsDTO> getDelayedApplications()
	{
		LOGGER.debug("Request received from front end to get the details of ON HOLD applications for logged in user");
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
		LOGGER.debug("Sending request to service to get the details of ON HOLD applications to logged in user");
		return departmentEmployeeHomeService.getDelayedApplications(email);
	}
	
	
	@Privilege(value= {"DE"})
	@RequestMapping(value="/updateApplicationStatusDE",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public Integer updateApplicationStatusDE(@ModelAttribute PerformActionsOverApplicationDTO performActionsOverApplicationDTO)
	{
		LOGGER.debug("Request received in controller to update the application status of an application for the logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(performActionsOverApplicationDTO.getUpdatedStatus()==null)
		{
			LOGGER.error("The status to be updated is null");
			LOGGER.error("Request cannot be processed, returning -10 to the front end");
			return -5;
		}
		else if(performActionsOverApplicationDTO.getApplicationId()==null||performActionsOverApplicationDTO.getApplicationId().isEmpty())
		{
			LOGGER.error("The application id is null or empty");
			LOGGER.error("Request cannot be processed, returning -10 to the front end");
			return -10;
		}
		else if(performActionsOverApplicationDTO.getAssignedTo()==null||performActionsOverApplicationDTO.getAssignedTo().isEmpty())
		{
			LOGGER.error("The owner of the application name is null or empty");
			LOGGER.error("Request cannot be processed, returning -10 to front end");
			return -10;
		}
		LOGGER.debug("The paramters are not empty");
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
		LOGGER.debug("Sending request to the service to update the status of application with application id: "+performActionsOverApplicationDTO.getApplicationId()+" to status : "+performActionsOverApplicationDTO.getUpdatedStatus());
		return departmentEmployeeHomeService.updateApplicationStatusDE(performActionsOverApplicationDTO, email);
	}
	
}

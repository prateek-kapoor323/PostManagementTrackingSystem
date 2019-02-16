package com.postManagementTrackingSystem.scgj.controller;

import java.util.Collection;
import java.util.List;

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
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNotStartedApplicationDTO;
import com.postManagementTrackingSystem.scgj.dto.DisplayAuditTableDHDTO;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfDepartmentEmployeesDTO;
import com.postManagementTrackingSystem.scgj.dto.PerformActionsOverApplicationDTO;
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
	
	
	/**
	 * This method is used to assign owner to the application and set the status of the application as ASSIGNED
	 * @param applicationId
	 * @param ownerName
	 * @param eta
	 * @return 1 if success, -5 if parameters are null and -30 if an exception occurs
	 */
	@Privilege(value= {"DH"})
	@RequestMapping(value="/assignOwner",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public Integer assignOwner(@ModelAttribute PerformActionsOverApplicationDTO performActionsOverApplicationDTO)
	{
		LOGGER.debug("Request received in controller to change the assignee of the application and update status to Assigned");
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
		LOGGER.debug("Checking if the parameters received are null or empty");
		if(performActionsOverApplicationDTO.getApplicationId()==null||performActionsOverApplicationDTO.getApplicationId().isEmpty())
		{
			LOGGER.error("application id is null or empty");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to front end");
			return -5;
		}
		if(performActionsOverApplicationDTO.getAssignedTo()==null||performActionsOverApplicationDTO.getAssignedTo().isEmpty())
		{
			LOGGER.error("The owner name is empty or null");
			LOGGER.error("Request cannot be processed, returning -5 to front end");
			return -5;
		}
		if(performActionsOverApplicationDTO.getEta()==null)
		{
			LOGGER.error("ETA is null");
			LOGGER.error("Request cannot be processed, returning -5 to front end");
			return -5;
		}
		LOGGER.debug("Parameters are not null or empty");
		LOGGER.debug("Processing request to assign owner to the application with id: "+performActionsOverApplicationDTO.getApplicationId());
		LOGGER.debug("Sending request to service to assign owner to application and set status as assigned for application with application id: "+performActionsOverApplicationDTO.getApplicationId());
		return departmentHeadHomeService.assignOwner(performActionsOverApplicationDTO,email);
		
	}
	
	@Privilege(value= {"DH"})
	@RequestMapping("/getAssignedApplicationsDH")
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
		return departmentHeadHomeService.getAssignedApplications(email);

	}
	
	@Privilege(value= {"DH"})
	@RequestMapping("/populateAuditTableDepartmentHead")
	public Collection<DisplayAuditTableDHDTO> populateAuditTable()
	{
		LOGGER.debug("Request received from front end to populate the audit table for logged in user");
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
		LOGGER.debug("Sending request to service to get the details of the audit table");
		return departmentHeadHomeService.populateAuditTable(email);
	}
	
	@Privilege(value= {"DH"})
	@RequestMapping("/getInActionApplications")
	public Collection<AssignedApplicationsDTO> getInActionApplications()
	{
		LOGGER.debug("Request received from front end to get the details of application with status IN ACTION for logged in user");
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
		LOGGER.debug("Sending request to service to get the details of application with status IN ACTION for logged in user");
		return departmentHeadHomeService.getInActionApplications(email);
		
	}
	
	@Privilege(value= {"DH"})
	@RequestMapping("/getOnHoldApplications")
	public Collection<AssignedApplicationsDTO> getOnHoldApplications()
	{
		LOGGER.debug("Request received from front end to get the details of application with status ON HOLD for logged in user");
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
		LOGGER.debug("Sending request to service to get the details of application with status On HOLD for logged in user");
		return departmentHeadHomeService.getOnHoldApplications(email);
		
	}
	
	@Privilege(value= {"DH"})
	@RequestMapping(value="/updateApplicationStatusDH",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public Integer updateApplicationStatus(@ModelAttribute PerformActionsOverApplicationDTO performActionsOverApplicationDTO)
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
		return departmentHeadHomeService.updateApplicationStatus(performActionsOverApplicationDTO, email);
	}
	
	
	@Privilege(value= {"DH"})
	@RequestMapping("/getInReviewApplications")
	public Collection<AssignedApplicationsDTO> getInReviewApplicationDetails()
	{
		LOGGER.debug("Request received from front end to get the details of applications with status as IN REVIEW");
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
		LOGGER.debug("Processing request to get the application details with status as In Review");
		LOGGER.debug("Sending request to service");
		return departmentHeadHomeService.getInReviewApplicationDetails(email);
		
	}
}

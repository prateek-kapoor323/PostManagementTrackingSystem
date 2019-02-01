package com.postManagementTrackingSystem.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.postManagementTrackingSystem.scgj.common.Privilege;
import com.postManagementTrackingSystem.scgj.common.SessionUserUtility;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditApplicationParamsDHDto;
import com.postManagementTrackingSystem.scgj.dto.ShowEditApplicationDetailsDepartmentHeadDto;
import com.postManagementTrackingSystem.scgj.service.DepartmentHeadEditApplicationService;

@RestController
public class DepartmentHeadEditApplicationController 
{

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadEditApplicationController.class);
	@Autowired
	private DepartmentHeadEditApplicationService departmentHeadEditApplicationService;
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	@Privilege(value= {"DH"})
	@RequestMapping("/showEditApplicationDetailsDH")
	public Collection<ShowEditApplicationDetailsDepartmentHeadDto> showEditApplicationDetails(@RequestParam("applicationId")String applicationId)
	{
		LOGGER.debug("Request received from front end to get the applicaiton details to be edited");
		LOGGER.debug("Checking if the received parameter is null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("The application received in controller is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the front end");
			return null;
		}
		
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
		LOGGER.debug("Sending request to service to get the application details to be edited");
		return departmentHeadEditApplicationService.showEditApplicationDetails(applicationId, email);
	
		
	}
	
	@Privilege(value= {"DH"})
	@RequestMapping(value="/editApplicationOwnerDH",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public Integer updateApplicationOwner(@ModelAttribute ReceiveEditApplicationParamsDHDto receiveEditApplicationParamsDHDto)
	{
		LOGGER.debug("Request received from front end to update the owner of the application");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(receiveEditApplicationParamsDHDto.getApplicationId()==null||receiveEditApplicationParamsDHDto.getApplicationId().isEmpty())
		{
			LOGGER.error("Application id is null or empty");
			LOGGER.error("Request cannot be processed, Returning -30 to front end");
			return -30;
			
		}
		else if(receiveEditApplicationParamsDHDto.getUpdatedOwner()==null||receiveEditApplicationParamsDHDto.getUpdatedOwner().isEmpty())
		{
			LOGGER.error("owner name id is null or empty");
			LOGGER.error("Request cannot be processed, Returning -30 to front end");
			return -30;
		}
		LOGGER.debug("Parameters are not null or empty");
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
		LOGGER.debug("Sending request to service to update the owner of the application");
		return departmentHeadEditApplicationService.updateApplicationOwner(receiveEditApplicationParamsDHDto, email);
	}
	
	
	@Privilege(value= {"DH"})
	@RequestMapping(value="/editApplicationEtaDH",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public Integer updateApplicationEta(@ModelAttribute ReceiveEditApplicationParamsDHDto receiveEditApplicationParamsDHDto)
	{
		LOGGER.debug("Request received in controller to update the eta for the application");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(receiveEditApplicationParamsDHDto.getApplicationId()==null||receiveEditApplicationParamsDHDto.getApplicationId().isEmpty())
		{
			LOGGER.error("Application id is null or empty");
			LOGGER.error("Request cannot be processed, Returning -30 to front end");
			return -30;
			
		}
		else if(receiveEditApplicationParamsDHDto.getUpdatedOwner()==null||receiveEditApplicationParamsDHDto.getUpdatedOwner().isEmpty())
		{
			LOGGER.error("owner name id is null or empty");
			LOGGER.error("Request cannot be processed, Returning -30 to front end");
			return -30;
		}
		LOGGER.debug("Parameters are not null or empty");
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
		LOGGER.debug("Sending request to service to update the ETA of the application");
		return departmentHeadEditApplicationService.updateApplicationEta(receiveEditApplicationParamsDHDto, email);
	}
}

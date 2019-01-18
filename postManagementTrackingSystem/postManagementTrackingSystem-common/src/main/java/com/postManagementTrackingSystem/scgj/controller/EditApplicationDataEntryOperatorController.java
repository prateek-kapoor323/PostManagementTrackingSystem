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
import com.postManagementTrackingSystem.scgj.dto.GetApplicationIdDto;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditParamsDataEntryOperatorDTO;
import com.postManagementTrackingSystem.scgj.dto.ShowEditApplicationDetailsDto;
import com.postManagementTrackingSystem.scgj.service.EditApplicationDataEntryOperatorService;

@RestController
public class EditApplicationDataEntryOperatorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditApplicationDataEntryOperatorController.class);
	
	@Autowired
	private EditApplicationDataEntryOperatorService editApplicationDataEntryOperatorService;
	
	/**
	 * This method is invoked when user enters the application id
	 * and clicks on search button under the edit application section
	 *  @author Prateek Kapoor
	 * @param applicationId 
	 * @return Collection of ShowApplicationDetailsDto
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/getApplicationDetailsById")
	public Collection<ShowEditApplicationDetailsDto> getApplicationDetails(@RequestParam("applicationId") String applicationId)
	{
		LOGGER.debug("Request received from front-end to get application details for application id: "+applicationId);
		LOGGER.debug("Checking if the application id is null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("application id is either null or empty");
			LOGGER.error("Returning null back to front end");
			return null;
		}
		LOGGER.debug("The application id is: "+applicationId);
		LOGGER.debug("Sending request to service with application id as a parameter");
		return editApplicationDataEntryOperatorService.getApplicationDetails(applicationId);
	}
	
	/**
	 * This method is invoked when the DEO(Data Entry Operator) updates the feilds and clicks on update button
	 * @author Prateek Kapoor
	 * @param receiveEditParamsDataEntryOperatorDTO
	 * @return Integer value 1- if the result is successfully updated, -10 if the post cannot be updated 
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping(value="/editPostDetails",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public Integer updatePostDetails(@ModelAttribute ReceiveEditParamsDataEntryOperatorDTO receiveEditParamsDataEntryOperatorDTO)
	{
	
		LOGGER.debug("Request received from front end to update post details where applicationId: "+receiveEditParamsDataEntryOperatorDTO.getApplicationId());
		LOGGER.debug("Fetching application id from the DTO");
		LOGGER.debug("Fetching applicationId out of the DTO to send application id and the DTO object as parameter");
		String applicationId = receiveEditParamsDataEntryOperatorDTO.getApplicationId();
		LOGGER.debug("Checking if the parameters are null or empty");
		if(applicationId == null || applicationId.isEmpty())
		{
			LOGGER.error("The application id is null or empty");
			LOGGER.error("Returning NULL from Controller");
			return null;
		}
		if(receiveEditParamsDataEntryOperatorDTO.getTypeOfDocument() == null || receiveEditParamsDataEntryOperatorDTO.getTypeOfDocument().isEmpty())
		{
			LOGGER.error("The document type is null or empty");
			LOGGER.error("Returning NULL from Controller");
			return null;
		}

		if(receiveEditParamsDataEntryOperatorDTO.getFile()==null||receiveEditParamsDataEntryOperatorDTO.getFile().isEmpty())
		{
			LOGGER.error("The multipart file is null or empty");
			LOGGER.error("Returning null from the controller");
			return null;
		}
		
		LOGGER.debug("Sending request to service method to update the post details");
		Integer updateStatus = editApplicationDataEntryOperatorService.updatePostDetails(receiveEditParamsDataEntryOperatorDTO);
		LOGGER.debug("The update status of application details received in controller is: "+updateStatus);
		return updateStatus;
	}
	
	
	/**
	 * This method is invoked when the user wants to update the owner of the application which is still in NOT STARTED state
	 * @author Prateek Kapoor
	 * @param applicationId
	 * @param ownerName
	 * @return 1 if the update is successful, -1 - if any exception occurs, -255 if applicationId is null, -300 if ownerName is null
	 * 
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/editAssignee")
	public Integer changeAssignee(@RequestParam("applicationId") String applicationId, @RequestParam("ownerName") String ownerName)
	{
		LOGGER.debug("Request received from front end to change the owner of application to : "+ownerName+" and application id : "+applicationId);
		LOGGER.debug("Checking if the application id and the owner name are null or empty");
		
		if(applicationId == null || applicationId.isEmpty())
		{
			LOGGER.error("The application id is null or empty");
			LOGGER.error("Returning error code -255 to front end");
			return -255;
		}
		if(ownerName==null || ownerName.isEmpty())
		{
			LOGGER.error("The owner name is null or empty");
			LOGGER.error("Returning error code -300 to front end");
			return -300;
		}
		else
		{
			LOGGER.debug("Sending request to service to assign application id: "+applicationId+" to owner: "+ownerName);
			return editApplicationDataEntryOperatorService.changeAssignee(applicationId,ownerName);
			
		}
	}
	
	
	/**
	 * This method is invoked when the user clicks on the drop down button to see the list of application id whose status is NOT STARTED
	 * @author Prateek Kapoor
	 * @return Collection of GetApplicationIdDto Object
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/getNotStartedApplicationId")
	public Collection<GetApplicationIdDto> getApplicationIdWithStatusNotStarted()
	{
		LOGGER.debug("Request received from front end to get the application id with status NOT STARTED");
		LOGGER.debug("Sending request to service to get application id with status NOT STARTED");
		return editApplicationDataEntryOperatorService.getApplicationIdWithStatusNotStarted();
	}
	
	
}

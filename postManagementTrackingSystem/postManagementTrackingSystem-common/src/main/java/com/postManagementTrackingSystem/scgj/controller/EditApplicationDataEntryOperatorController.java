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
import com.postManagementTrackingSystem.scgj.dto.GetApplicationIdDto;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditParamsDataEntryOperatorDTO;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditParamsWithoutFileDataEntryOperatorDTO;
import com.postManagementTrackingSystem.scgj.dto.ShowEditApplicationDetailsDto;
import com.postManagementTrackingSystem.scgj.service.EditApplicationDataEntryOperatorService;

@RestController
public class EditApplicationDataEntryOperatorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditApplicationDataEntryOperatorController.class);
	
	@Autowired
	private EditApplicationDataEntryOperatorService editApplicationDataEntryOperatorService;
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	/**
	 * This method is invoked when user enters the application id
	 * and clicks on search button under the edit application section
	 *  @author Prateek Kapoor
	 * @param applicationId 
	 * @return Collection of ShowApplicationDetailsDto
	 */
	@Privilege(value= {"DEO","DH"})
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
	 * This method takes the post details without the file and updates the post related information for that application id
	 * @param receiveEditParamsWithoutFileDataEntryOperatorDTO
	 * @return -20 if not successfull, 1 if successfull
	 */
	@Privilege(value= {"DEO","DH"})
	@RequestMapping(value="/editPostDetailsWithoutFile",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public Integer updatePostDetailsWithoutFile(@ModelAttribute ReceiveEditParamsWithoutFileDataEntryOperatorDTO receiveEditParamsWithoutFileDataEntryOperatorDTO)
	{
	
		LOGGER.debug("Request received from front end to update post details without file where applicationId: "+receiveEditParamsWithoutFileDataEntryOperatorDTO.getApplicationId());
		LOGGER.debug("Fetching application id from the DTO");
		LOGGER.debug("Fetching applicationId out of the DTO to send application id and the DTO object as parameter");
		String applicationId = receiveEditParamsWithoutFileDataEntryOperatorDTO.getApplicationId();
		LOGGER.debug("Checking if the parameters are null or empty");
		if(applicationId == null || applicationId.isEmpty())
		{
			LOGGER.error("The application id is null or empty");
			LOGGER.error("Returning NULL from Controller");
			return null;
		}
		if(receiveEditParamsWithoutFileDataEntryOperatorDTO.getTypeOfDocument() == null || receiveEditParamsWithoutFileDataEntryOperatorDTO.getTypeOfDocument().isEmpty())
		{
			LOGGER.error("The document type is null or empty");
			LOGGER.error("Returning NULL from Controller");
			return null;
		}
		
		LOGGER.debug("Sending request to service method to update the post details");
		Integer updateStatus = editApplicationDataEntryOperatorService.updatePostDetailsWithoutFile(receiveEditParamsWithoutFileDataEntryOperatorDTO);
		LOGGER.debug("The update status of application details received in controller is: "+updateStatus);
		return updateStatus;
	}
	

	
	/**
	 * This method is invoked when the DEO(Data Entry Operator) updates the feilds and clicks on update button
	 * @author Prateek Kapoor
	 * @param receiveEditParamsDataEntryOperatorDTO
	 * @return Integer value 1- if the result is successfully updated, -10 if the post cannot be updated 
	 */
	@Privilege(value= {"DEO","DH"})
	@RequestMapping(value="/editPostDetailsWithFile",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
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
	 * @return 1 if the update is successful, -36 - if any exception occurs, -255 if applicationId is null, -300 if ownerName is null
	 * 
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/editAssignee")
	public Integer changeAssignee(@RequestParam("applicationId") String applicationId, @RequestParam("ownerName") String ownerName, @RequestParam("senderName") String senderName, @RequestParam("subject") String subject, @RequestParam("status") String status, @RequestParam("documentType") String documentType, @RequestParam("documentPath") String documentPath )
	{
		LOGGER.debug("Request received from front end to change the owner of application to : "+ownerName+" and application id : "+applicationId);
		LOGGER.debug("Checking if the application id and the owner name are null or empty");
		
		if(applicationId == null || applicationId.isEmpty())
		{
			LOGGER.error("The application id is null or empty");
			LOGGER.error("Returning error code -255 to front end");
			return -255;
		}
		else if(ownerName==null || ownerName.isEmpty())
		{
			LOGGER.error("The owner name is null or empty");
			LOGGER.error("Returning error code -300 to front end");
			return -300;
		}
		else if(senderName==null || senderName.isEmpty())
		{
			LOGGER.error("The senderName is null or empty");
			LOGGER.error("Returning error code -300 to front end");
			return -300;
		}
		else if(subject==null || subject.isEmpty())
		{
			LOGGER.error("The subject is null or empty");
			LOGGER.error("Returning error code -300 to front end");
			return -300;
		}
		else if(status==null || status.isEmpty())
		{
			LOGGER.error("The status is null or empty");
			LOGGER.error("Returning error code -300 to front end");
			return -300;
		}
		else if(documentType==null || documentType.isEmpty())
		{
			LOGGER.error("The documentType is null or empty");
			LOGGER.error("Returning error code -300 to front end");
			return -300;
		}
		else if(documentPath==null || documentPath.isEmpty())
		{
			LOGGER.error("The documentPath is null or empty");
			LOGGER.error("Returning error code -300 to front end");
			return -300;
		}
		else
		{
			LOGGER.debug("Fetching the email of the user from session");
			String email = sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("Checking if the retreived email is null or empty");
			if(email==null||email.isEmpty())
			{
				LOGGER.error("Could not retreive the email of the logged in user");
				LOGGER.error("Request could not be processed further");
				LOGGER.error("Returning null to front end");
				return -300;
			}
			LOGGER.debug("Email is not empty");
			LOGGER.debug("Sending request to service to assign application id: "+applicationId+" to owner: "+ownerName);
			return editApplicationDataEntryOperatorService.changeAssignee(applicationId,ownerName,senderName,subject,status,documentType,documentPath,email);
			
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

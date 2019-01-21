package com.postManagementTrackingSystem.scgj.controller;
import java.io.IOException;
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
import com.postManagementTrackingSystem.scgj.common.ReadApplicationConstants;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNameDto;
import com.postManagementTrackingSystem.scgj.dto.SubmitPostDetailsDto;
import com.postManagementTrackingSystem.scgj.service.RegisterApplicationService;

@RestController
public class RegisterApplicationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApplicationController.class);
	@Autowired
	private RegisterApplicationService registerApplicationService;
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method returns the names of the heads of departments
	 * @return a DTO of all the names whose role_type is department heads(DH)
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/getDepartmentHeadsName")
	public Collection <DepartmentHeadNameDto> getDepartmentHeadName()
	{
		LOGGER.debug("Request received in controller to get the name of the department heads");
		LOGGER.debug("Sending request to service to get the record of the names of department heads");
		return registerApplicationService.getDepartmentHeadName();
	}
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method is invoked when the data entry operator fills the post details form and clicks on the submit button
	 * This method receives the form data and checks for the necessary fields and forwards the DTO to the service
	 * @param submitPostDetailsDto
	 * @return The generated application id of the post
	 * @throws IOException
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping(value="/submitPostDetails",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public String submitPostDetails(@ModelAttribute SubmitPostDetailsDto submitPostDetailsDto)
	{	
		
		LOGGER.debug("Received post details DTO to be submitted into database");
		LOGGER.debug("Checking if the parameters - documentType, dateReceived,ownerName, file are empty");
		try
		{
			LOGGER.debug("Checking if documentType is null");
			if(submitPostDetailsDto.getDocumentType()==null||submitPostDetailsDto.getDocumentType().isEmpty())
			{
				LOGGER.error("The document type field is null");
				return readApplicationConstants.getDocUploadErrorMessage();
			}
			LOGGER.debug("Checking if the ownerName of the document is null");
			if(submitPostDetailsDto.getOwnerName()==null||submitPostDetailsDto.getOwnerName().isEmpty())
			{
				LOGGER.error("Owner name is empty or null");
				LOGGER.error("Returning error message");
				return readApplicationConstants.getDocUploadErrorMessage();
		
			}
			LOGGER.debug("Checking if the dateReceived is null");
			if(submitPostDetailsDto.getDateReceived()==null)
			{
				LOGGER.error("The date received is null");
				return readApplicationConstants.getDocUploadErrorMessage();
			}
			LOGGER.debug("Checking if the file is empty");
			if(submitPostDetailsDto.getFile().isEmpty())
			{
				LOGGER.error("The multipart file is empty");
				return readApplicationConstants.getDocUploadErrorMessage();
			}
			else
			{
				LOGGER.debug("Received post details dto and multipart file to be submitted");
				LOGGER.debug("Sending request to register application service to submit the details and upload the file");
				return registerApplicationService.submitPost(submitPostDetailsDto);
			}
			
		}
		catch(Exception e)
		{
			LOGGER.error("An exception was caught in the catch block");
			LOGGER.error("Returning Error Message");
			return readApplicationConstants.getApplicationIdGenerationErrorMessage();
		}
		
	}
	

	

}

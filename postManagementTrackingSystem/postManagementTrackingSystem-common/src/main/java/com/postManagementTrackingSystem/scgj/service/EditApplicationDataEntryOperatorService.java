package com.postManagementTrackingSystem.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.EditApplicationDataEntryOperatorDao;
import com.postManagementTrackingSystem.scgj.dao.RegisterApplicationDao;
import com.postManagementTrackingSystem.scgj.dto.GetApplicationIdDto;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditParamsDataEntryOperatorDTO;
import com.postManagementTrackingSystem.scgj.dto.ShowEditApplicationDetailsDto;
import com.postManagementTrackingSystem.scgj.utils.ApplicationUtilityClass;

@Service
public class EditApplicationDataEntryOperatorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditApplicationDataEntryOperatorService.class);
	
	@Autowired
	private EditApplicationDataEntryOperatorDao editApplicationDataEntryOperatorDao;
	
	@Autowired
	private ApplicationUtilityClass applicationUtilityClass;
	
	@Autowired
	private RegisterApplicationDao registerApplicationDao;
	
	/**
	 * This method receives applicationid as a parameter from the controller and sends the parameter
	 * to DAO to get the application details corresponding to that application id
	 * @author Prateek Kapoor
	 * @param applicationId
	 * @return Collection of ShowApplicationDetailsDto
	 */
	public Collection<ShowEditApplicationDetailsDto> getApplicationDetails(String applicationId)
	{
		LOGGER.debug("Received request from controller to get the application details for application id: "+applicationId);
		LOGGER.debug("Sending request to dao along with application id to get the details of the application");
		return editApplicationDataEntryOperatorDao.getApplicationDetails(applicationId);
	}
	
	/**
	 * This method updates the post details received by the controller and also updates the file path which is sent to the DAO
	 * @param applicationId
	 * @param receiveEditParamsDataEntryOperatorDTO
	 * @return -10 if exception occurs, 1 - if the update is successful
	 */
	public Integer updatePostDetails(ReceiveEditParamsDataEntryOperatorDTO receiveEditParamsDataEntryOperatorDTO) 
	{
		// TODO Auto-generated method stub
		Integer updateStatus = -10;
		String uploadPath="";
		Integer updatePostDetails = -20;
		LOGGER.debug("Request received in updatePostDetails method of edit application service to update the application details with application id: "+receiveEditParamsDataEntryOperatorDTO.getApplicationId());
		LOGGER.debug("Checking if the file is present in the DTO");
		if(receiveEditParamsDataEntryOperatorDTO.getFile()==null||receiveEditParamsDataEntryOperatorDTO.getFile().isEmpty())
		{
			LOGGER.debug("The user has not uploaded the file, Updating the remaining fields");
			LOGGER.debug("Sending request to method in DAO to update ");
			updatePostDetails = editApplicationDataEntryOperatorDao.getUpdatePostDetailsWithoutFile(receiveEditParamsDataEntryOperatorDTO);
			LOGGER.debug("The value of updatePostDetails is: "+updatePostDetails);
			LOGGER.debug("Returning updatePostDetails to controller");
			return updatePostDetails;
		}

		else
		{
			
			LOGGER.debug("Sending the DTO and application id to upload file method in file utility class");
			try {
				LOGGER.debug("In try block to send control to uplaod file method");
				uploadPath = applicationUtilityClass.uploadFile(receiveEditParamsDataEntryOperatorDTO.getTypeOfDocument(), receiveEditParamsDataEntryOperatorDTO.getFile(),receiveEditParamsDataEntryOperatorDTO.getApplicationId());
				
				if(uploadPath == null || uploadPath.isEmpty())
				{
					LOGGER.error("Path is null or empty");
					LOGGER.error("File could not be uploaded, sending null to controller");
					return -10;
				}
				else
				{
					LOGGER.debug("The path of uploaded file is: "+uploadPath);
					LOGGER.debug("Sending request to updatePostDetails method in DAO to update the file path,sender name, point of contact, contact number, date received, priority, subject, additional comment against the application id: "+receiveEditParamsDataEntryOperatorDTO.getApplicationId());
					updateStatus = editApplicationDataEntryOperatorDao.updatePostDetails(receiveEditParamsDataEntryOperatorDTO,uploadPath);
					LOGGER.debug("The update Status after updating the post details against a batch ID is: "+updateStatus);
					return updateStatus;
				}
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				LOGGER.error("An exception occured while uploading file: "+e);
				LOGGER.error("Returning -10 (updateStatus value) to the controller ");
				return updateStatus;
			}

		}
		
				
	}

	/**
	 * This method changes the assignee of the application id to the owner given in the paramerts
	 * @param applicationId
	 * @param ownerName
	 * @return status of assigneeUpdate
	 */
	public Integer changeAssignee(String applicationId, String ownerName) {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from controller to change assignee of the application with application id: "+applicationId+" to new owner with name: "+ownerName);
		LOGGER.debug("Sending request to method to get the Id of the owner with name: "+ownerName);
		Integer ownerId = registerApplicationDao.getDocumentOwnerId(ownerName);
		LOGGER.debug("Checking if the owner id is null");
		if(ownerId == null)
		{
			LOGGER.error("The owner id retreived is null");
			LOGGER.error("Returning error code -32");
			return -32;
		}
		LOGGER.debug("The owner id is: "+ownerId);
		LOGGER.debug("Sending request to method to get the docId(PK) corresponding to the application id: "+applicationId);
		Integer documentId = editApplicationDataEntryOperatorDao.getDocumentIdByApplicaitonId(applicationId);
		LOGGER.debug("Checking if the application id is null");
		if(documentId == null)
		{
			LOGGER.error("The document id retreived is null");
			LOGGER.error("Returning error code -23");
			return -23;
		}
		LOGGER.debug("Sending control to changeAssignee method in DAO to update the owner of application id: "+applicationId+ " to owner: "+ownerName );
		Integer updateStatus = editApplicationDataEntryOperatorDao.changeAssignee(ownerId,documentId);
		LOGGER.debug("The status of assignee change is: "+updateStatus);
		return updateStatus;
	}
	
	/**
	 * @author Prateek Kapoor
	 * This method returns the collection of application id with status NOT STARTED
	 * @return COLLECTION of GetApplicationIdDto Object
	 */
	public Collection<GetApplicationIdDto> getApplicationIdWithStatusNotStarted()
	{
		LOGGER.debug("Request received from service to get application id with status NOT STARTED");
		LOGGER.debug("Sending request to DAO to get the application ID");
		return editApplicationDataEntryOperatorDao.getApplicationIdWithStatusNotStarted();
	}
}

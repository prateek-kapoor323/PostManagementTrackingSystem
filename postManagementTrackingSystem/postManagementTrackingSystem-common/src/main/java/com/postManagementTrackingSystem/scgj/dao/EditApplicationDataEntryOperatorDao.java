package com.postManagementTrackingSystem.scgj.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.postManagementTrackingSystem.scgj.common.AbstractTransactionalDao;
import com.postManagementTrackingSystem.scgj.config.EditApplicationDataEntryOperatorConfig;
import com.postManagementTrackingSystem.scgj.dto.GetApplicationIdDto;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditParamsDataEntryOperatorDTO;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditParamsWithoutFileDataEntryOperatorDTO;
import com.postManagementTrackingSystem.scgj.dto.ShowEditApplicationDetailsDto;

@Repository
public class EditApplicationDataEntryOperatorDao extends AbstractTransactionalDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditApplicationDataEntryOperatorDao.class);
	
	@Autowired
	private EditApplicationDataEntryOperatorConfig editApplicationDataEntryOperatorConfig;
	
	private static ApplicationDetailsRowMapper ROW_MAPPER = new ApplicationDetailsRowMapper();
	
	private static ApplicationIdRowMapper ApplicationId_Mapper = new ApplicationIdRowMapper();
	
	/**
	 * This method is invoked when the user enters the application id under edit menu and clicks on search button, application id param is used to fetch the already inserted details of the application and sent to the front end
	 * @param applicationId
	 * @return COLLECTION of ShowApplicationDetailsDto
	 */
	
	public Collection<ShowEditApplicationDetailsDto> getApplicationDetails(String applicationId)
	{
		LOGGER.debug("In Method getApplicationDetails of EditApplicationDataEntryOperatorDao class to get application details for application id: "+applicationId);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> params = new HashMap<>();
		LOGGER.debug("Inserting params into hashmap");
		params.put("applicationId", applicationId);
		LOGGER.debug("Params inserted successfully into hashmap");
		try {
			
			LOGGER.debug("In try block to get the details of application by application id: "+applicationId);
			LOGGER.debug("Executing query to get details of application using application id");
			return  getJdbcTemplate().query(editApplicationDataEntryOperatorConfig.getEditApplicationByIdSearch(), params, ROW_MAPPER);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("An exception occured while fetching the details of the application in class EditApplicationDataEntryOperatorDao and method - getApplicationDetails for application id: "+applicationId);
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL");
			return null;
		}
	}
	
	public static class ApplicationDetailsRowMapper implements RowMapper<ShowEditApplicationDetailsDto>
	{
		@Override
		public ShowEditApplicationDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String applicationId = rs.getString("applicationId");
			String senderName = rs.getString("senderName");
			String senderPointOfContact = rs.getString("senderPointOfContact");
			Long senderContact = rs.getLong("senderContact");
			Date dateReceived = rs.getDate("dateReceived");
			String priority = rs.getString("priority");
			String referenceNumber = rs.getString("referenceNumber");
			String subject = rs.getString("subject");
			String status = rs.getString("status");
			String documentType = rs.getString("documentType");
			String additionalComment = rs.getString("additionalComment");
			
			return new ShowEditApplicationDetailsDto(applicationId,senderName,senderPointOfContact,senderContact,dateReceived,priority,referenceNumber,subject,status,documentType,additionalComment);
			 
			 
			
			
		}
	}

	/**
	 * This method receives the application id and the DTO object to update the post details table against a specific application ID
	 * @param applicationId
	 * @param receiveEditParamsDataEntryOperatorDTO
	 * @param uploadPath 
	 * @return 1 if the update is successful else -10
	 */
	public Integer updatePostDetails(ReceiveEditParamsDataEntryOperatorDTO receiveEditParamsDataEntryOperatorDTO, String uploadPath)
	{
		// TODO Auto-generated method stub
		
		LOGGER.debug("Request received in updatePostDetails method to update application details in the post detials table for application id: "+receiveEditParamsDataEntryOperatorDTO.getApplicationId());		
		LOGGER.debug("Creating Hashmap of objects and inserting values into the map");
		Map<String,Object> updateParams = new HashMap<>();
		LOGGER.debug("Hashmap successfully created");
		LOGGER.debug("Inserting values into the hashmap");
		updateParams.put("applicationId", receiveEditParamsDataEntryOperatorDTO.getApplicationId());
		updateParams.put("senderName", receiveEditParamsDataEntryOperatorDTO.getSenderName());
		updateParams.put("senderPoc", receiveEditParamsDataEntryOperatorDTO.getPointOfContact());
		updateParams.put("senderContact", receiveEditParamsDataEntryOperatorDTO.getContactNumber());
		updateParams.put("dateReceived", receiveEditParamsDataEntryOperatorDTO.getDateReceived());
		updateParams.put("priority", receiveEditParamsDataEntryOperatorDTO.getPriority());
		updateParams.put("referenceNumber", receiveEditParamsDataEntryOperatorDTO.getReferenceNumber());
		updateParams.put("subject", receiveEditParamsDataEntryOperatorDTO.getSubject());
		updateParams.put("documentPath", uploadPath);
		updateParams.put("documentRemarks", receiveEditParamsDataEntryOperatorDTO.getAdditionalComment());
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try {
			LOGGER.debug("Executing query to update the post details table");
			Integer status = getJdbcTemplate().update(editApplicationDataEntryOperatorConfig.getUpdateDocumentDetailsTable(),updateParams);
			LOGGER.debug("The update Status is" +status);
			return status;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("An exception occured while updating records of the post details table for application id: "+receiveEditParamsDataEntryOperatorDTO.getApplicationId());
			LOGGER.error("Returning -10 "+e);
			return -10;
			
		}
		
	}
	/**
	 * This method takes ownerId and documentId as parameters and assigns a new owner to the document id
	 * @param ownerId
	 * @param documentId
	 * @param email 
	 * @param documentPath 
	 * @param documentType 
	 * @param status 
	 * @param subject 
	 * @param senderName 
	 * @param email2 
	 * @return status of assignee update  1 -- if successful,   
	 * @throws Exception and rollsback the transaction
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer changeAssignee(Integer ownerId, Integer documentId, String applicationId, String senderName, String subject, String status, String documentType, String documentPath, String email) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from service to update the owner of the application with documentId: "+documentId+" to owner with ownerId: "+ownerId);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>ownerParams = new HashMap<>();
		LOGGER.debug("Hashmap successfully created");
		LOGGER.debug("Inserting documentId and ownerId into hashmap");
		ownerParams.put("ownerId", ownerId);
		ownerParams.put("documentId", documentId);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block of changeAssignee method to update owner of the document with id: "+documentId);
			LOGGER.debug("Executing query to update the owner id for the document with id: "+documentId);
			Integer updateStatus = getJdbcTemplate().update(editApplicationDataEntryOperatorConfig.getUpdateDocumentOwner(), ownerParams);
			LOGGER.debug("The update status for owner in changeAssignee method is: "+updateStatus);
			LOGGER.debug("Returning updateStatus to the service"+updateStatus);
			if(updateStatus>0)
			{
				LOGGER.debug("The owner successfully updated, Sending request to update the audit table");
				int auditTableUpdateStatus = updateAuditTable(ownerId,applicationId,senderName,subject,status,documentType,documentPath,email);
				return auditTableUpdateStatus;
			}
			else
			{
				LOGGER.error("Record could not be updated, returning -300");
				return -300;
			}
			
		}
		catch (RuntimeException e)
		{
			// TODO: handle exception
			LOGGER.error("An exception has occured while updating the assignee of the document "+e);
			LOGGER.error("Throwing exception to rollback the transaction");
			throw new Exception(e);
		}
	}
	
	
	/**
	 * This method inserts the following parameters into the audit table when the DEO updates the owner of the application
	 * @param ownerId
	 * @param applicationId
	 * @param senderName
	 * @param subject
	 * @param status
	 * @param documentType
	 * @param documentPath
	 * @return 1 if inserted, else throws an exception and rollsback the transaction
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	private int updateAuditTable(Integer ownerId, String applicationId, String senderName, String subject,
			String status, String documentType, String documentPath, String email) throws Exception 
	{
		LOGGER.debug("Request received from submitPostDetails() method to fill the audit table for application with application id: "+applicationId);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>auditTableUpdate = new HashMap<>();
		LOGGER.debug("Hashmap of objects successfully created, Inserting parameters into hashmap");
		auditTableUpdate.put("applicationId", applicationId);
		auditTableUpdate.put("senderName",senderName);
		auditTableUpdate.put("subject",subject);
		auditTableUpdate.put("ownerId", ownerId);
		auditTableUpdate.put("status", status);
		auditTableUpdate.put("documentPath", documentPath);
		auditTableUpdate.put("documentType", documentType);
		auditTableUpdate.put("email", email);
		
		try
		{
			
			LOGGER.debug("In try block to update the audit table details for application with id: "+applicationId);
			LOGGER.debug("Executing query to update the audit table");
			return getJdbcTemplate().update(editApplicationDataEntryOperatorConfig.getUpdateAuditTableDetails(), auditTableUpdate);
		} catch (Exception e)
		{
			LOGGER.error("An exception occured while updating the audit table: "+e);
			LOGGER.error("Throwing exception");
			throw new Exception(e);			
		}
	}
	/**
	 * this method takes application id as a parameter and gets the id(PK) against that applicaiton id 
	 * @param applicationId
	 * @return documentId (PK) against the application id
	 */
	public Integer getDocumentIdByApplicaitonId(String applicationId) {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from service to get the id(PK) against the application id: "+applicationId);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> applicationIdParams = new HashMap<>();
		LOGGER.debug("hashmap successfully created");
		LOGGER.debug("Inserting Application id as a parameter into the hashmap");
		applicationIdParams.put("applicationId",applicationId);
		LOGGER.debug("Application id inserted into the hashmap");
		try {
			
			LOGGER.debug("In try block to get the id(PK) against the application id: "+applicationId);
			LOGGER.debug("Executing query to get the id(PK) for the application id: "+applicationId);
			Integer docId = getJdbcTemplate().queryForObject(editApplicationDataEntryOperatorConfig.getIdByApplicationId(), applicationIdParams,Integer.class);
			LOGGER.debug("The ID(PK) for application id :"+applicationId+ " is : "+docId);
			LOGGER.debug("Returning the docId retreived for application id to the service");
			return docId;
			
		} catch (Exception e) 
		{
			// TODO: handle exception
			LOGGER.error("An exception occured while fetching the id(PK) for application id: "+applicationId);
			LOGGER.error("The exception is: "+e);
			return null;
		}
		

		
	}
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method returns the application id 
	 * @return Collection of Application ID
	 * @return NULL if exception occurs
	 */
	public Collection<GetApplicationIdDto> getApplicationIdWithStatusNotStarted()
	{
		LOGGER.debug("Request received from service to get the application id");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>params = new HashMap<>();
		LOGGER.debug("Hashmap successfully created");
		try 
		{
			LOGGER.debug("In Try block to get the records of application id");
			LOGGER.debug("Executing query to fetch application id");
			return getJdbcTemplate().query(editApplicationDataEntryOperatorConfig.getApplicationIdWithStatusNotStarted(), ApplicationId_Mapper);
			
		} catch (Exception e) {
		
			LOGGER.error("An exception occured while fetching the records of application");
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL");
			return null;
		}		
	}
	
	
	public static class ApplicationIdRowMapper implements RowMapper<GetApplicationIdDto>
	{
		@Override
		public GetApplicationIdDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
		   
			String applicationId = rs.getString("application_id");
			return new GetApplicationIdDto(applicationId);
		
		}
	}
	/**
	 * This method is used to update the post details without updating the file path, i.e. This method will be invoked by the service
	 * when the user has not updated the file. This method will update all the details except for the file path
	 * @param receiveEditParamsDataEntryOperatorDTO
	 * @return
	 */
	public Integer getUpdatePostDetailsWithoutFile(ReceiveEditParamsWithoutFileDataEntryOperatorDTO receiveEditParamsWithoutFileDataEntryOperatorDTO) {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from service to update post details without updating the file path");
		LOGGER.debug("Post details to be updated for applicationId: "+receiveEditParamsWithoutFileDataEntryOperatorDTO.getApplicationId());
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>parameters = new HashMap<>();
		parameters.put("applicationId", receiveEditParamsWithoutFileDataEntryOperatorDTO.getApplicationId());
		parameters.put("senderName", receiveEditParamsWithoutFileDataEntryOperatorDTO.getSenderName());
		parameters.put("senderPoc", receiveEditParamsWithoutFileDataEntryOperatorDTO.getPointOfContact());
		parameters.put("senderContact", receiveEditParamsWithoutFileDataEntryOperatorDTO.getContactNumber());
		parameters.put("dateReceived", receiveEditParamsWithoutFileDataEntryOperatorDTO.getDateReceived());
		parameters.put("priority", receiveEditParamsWithoutFileDataEntryOperatorDTO.getPriority());
		parameters.put("referenceNumber", receiveEditParamsWithoutFileDataEntryOperatorDTO.getReferenceNumber());
		parameters.put("subject", receiveEditParamsWithoutFileDataEntryOperatorDTO.getSubject());
		parameters.put("documentRemarks", receiveEditParamsWithoutFileDataEntryOperatorDTO.getAdditionalComment());
		LOGGER.debug("Parameters inserted successfully");
		try 
		{
		LOGGER.debug("In try block of getUpdatePostDetailsWithoutFile method to update post details");
		LOGGER.debug("Executing query to update post details without updating file path");
		Integer updateStatus = getJdbcTemplate().update(editApplicationDataEntryOperatorConfig.getUpdateDocumentDetailsTableWithoutFile(), parameters);
		LOGGER.debug("The update status for getUpdatePostDetailsWithoutFile is: "+updateStatus);
		return updateStatus;
		}
		catch (Exception e) 
		{
			LOGGER.error("An exception occured while updating post details in method-getUpdatePostDetailsWithoutFile for application id: "+receiveEditParamsWithoutFileDataEntryOperatorDTO.getApplicationId());
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning -20 as response");
			return -20;
			// TODO: handle exception
		}
		
	}
	
	
		
	}
	
	


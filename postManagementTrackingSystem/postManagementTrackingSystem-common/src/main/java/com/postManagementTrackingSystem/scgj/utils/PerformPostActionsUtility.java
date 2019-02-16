package com.postManagementTrackingSystem.scgj.utils;

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
import com.postManagementTrackingSystem.scgj.config.DepartmentHeadHomeConfig;
import com.postManagementTrackingSystem.scgj.config.DepartmentHeadSearchConfig;
import com.postManagementTrackingSystem.scgj.dao.DepartmentHeadSearchDao.ApplicationSearchResults;
import com.postManagementTrackingSystem.scgj.dto.ApplicationSearchResultsDto;
import com.postManagementTrackingSystem.scgj.dto.AssignedApplicationsDTO;
import com.postManagementTrackingSystem.scgj.dto.PerformActionsOverApplicationDTO;

@Repository
public class PerformPostActionsUtility extends AbstractTransactionalDao
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PerformPostActionsUtility.class);
	
	@Autowired
	private DepartmentHeadHomeConfig departmentHeadHomeConfig;
	
	@Autowired
	private DepartmentHeadSearchConfig departmentHeadSearchConfig;
	
	private static AssignedApplicationsMapper APPLICATIONS_ROW_MAPPER = new AssignedApplicationsMapper(); // For getting assigned, on Hold applications
	private static ApplicationSearchResults ROW_MAPPER = new ApplicationSearchResults();
	
	
	/**
	 * This method  updates the status of the application using the document id and owner id to In Action
	 * @param performActionsOverApplicationDTO
	 * @param ownerId
	 * @param documentId
	 * @param email 
	 * @return 1 if the status is updated, -5 if any parameter is empty, -10 if an exception occurs
	 * @throws Exception - this will roll back the update and also will rollback the update in the audit table
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateApplicationStatusToInAction(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,Integer ownerId,Integer documentId, String email) throws Exception
	{
		LOGGER.debug("Request received from service to update the status of application for logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(performActionsOverApplicationDTO.getUpdatedStatus()==null)
		{
			LOGGER.error("The status to be updated is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		if(ownerId==null)
		{
			LOGGER.error("The owner id received is null");
			LOGGER.error("Request cannot be processed, returning - 5 to the service");
			return -5;
		}
		if(documentId == null)
		{
			LOGGER.error("The document id received is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		LOGGER.debug("Parameters are not null or empty, Processing request to update the status of application with application Id: "+performActionsOverApplicationDTO.getApplicationId()+" to new status: "+performActionsOverApplicationDTO.getUpdatedStatus());
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>updateStatusParams = new HashMap<>();
		LOGGER.debug("Hashmap Created Successfully, Inserting parameters into hashmap");
		updateStatusParams.put("updatedStatus", performActionsOverApplicationDTO.getUpdatedStatus());
		updateStatusParams.put("ownerId", ownerId);
		updateStatusParams.put("documentId", documentId);
		LOGGER.debug("updated status, ownerId, documentId inserted into hashmap");
		try
		{
			LOGGER.debug("In try block to update the application status to In Action for application id: "+performActionsOverApplicationDTO.getApplicationId());
			LOGGER.debug("Executing query to update the status of application to In Action");
			Integer updateStatus = getJdbcTemplate().update(departmentHeadHomeConfig.getUpdateStatusToInAction(), updateStatusParams);
			if(updateStatus>0)
			{
				  LOGGER.debug("Document status table successfully updated, updating the audit table");
				  LOGGER.debug("Calling updateAuditTable method to update the details in the audit table");
				  Integer auditTableUpdate = updateAuditTable(performActionsOverApplicationDTO,ownerId,email);
				  LOGGER.debug("The status of audit table update is : "+auditTableUpdate);
				  return auditTableUpdate;
			}
			else
			{
				LOGGER.error("Status cannot be updated, Returning -10 to service");
				return -10;
			}
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while updating the status of the application to In Action: "+e);
			LOGGER.error("Throwing exception to service");
			throw new Exception(e);
		}
		
	}
	
	/**
	 * This method updates the status of the application to ON HOLD
	 * @param performActionsOverApplicationDTO
	 * @param ownerId
	 * @param documentId
	 * @param email 
	 * @return -10 if an exception occurs, 1 if success, -5 if params are empty 
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateApplicationStatusToOnHold(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,Integer ownerId,Integer documentId, String email) throws Exception
	{
		LOGGER.debug("Request received from service to change the application status to On Hold for the logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(performActionsOverApplicationDTO.getUpdatedStatus()==null)
		{
			LOGGER.error("The status to be updated is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		if(ownerId==null)
		{
			LOGGER.error("The owner id received is null");
			LOGGER.error("Request cannot be processed, returning - 5 to the service");
			return -5;
		}
		if(documentId == null)
		{
			LOGGER.error("The document id received is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		LOGGER.debug("Parameters are not null or empty, Processing request to update the status of application with application Id: "+performActionsOverApplicationDTO.getApplicationId()+" to new status: "+performActionsOverApplicationDTO.getUpdatedStatus());
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>updateStatusParams = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting parameters into hashmap");
		updateStatusParams.put("updatedStatus", performActionsOverApplicationDTO.getUpdatedStatus());
		updateStatusParams.put("ownerId", ownerId);
		updateStatusParams.put("documentId", documentId);
		LOGGER.debug("updated status, ownerId, documentId inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to update the application status to On Hold for application id: "+performActionsOverApplicationDTO.getApplicationId());
			LOGGER.debug("Executing query to update the status of application to On Hold");
			Integer updateStatus = getJdbcTemplate().update(departmentHeadHomeConfig.getUpdateStatusToOnHold(), updateStatusParams);
			if(updateStatus>0)
			{
				  LOGGER.debug("Document status table successfully updated, updating the audit table");
				  LOGGER.debug("Calling updateAuditTable method to update the details in the audit table");
				  Integer auditTableUpdate = updateAuditTable(performActionsOverApplicationDTO,ownerId,email);
				  LOGGER.debug("The status of audit table update is : "+auditTableUpdate);
				  return auditTableUpdate;
			}
			else
			{
				LOGGER.error("Status cannot be updated, Returning -10 to service");
				return -10;
			}
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while updating the status of the application to On Hold: "+e);
			LOGGER.error("Throwing exception to service");
			throw new Exception(e);
		}
		
	}
	
	
	/**
	 * This method checks if the startdate of the application is present -
	 * 1. If the startdate is present then it updates the status to completed and updates the date_closed
	 * 2. If the startDate is not present then it updates status to completed and also updates the date_started to curdate() and dateCloed to curdate()
	 * @param performActionsOverApplicationDTO
	 * @param ownerId
	 * @param documentId
	 * @param email 
	 * @return -5 if any parameter is empty, 1 if success, -10 in case of any exception
	 * @throws Exception 
	 * 
	 */
	
	@Transactional(rollbackFor=Exception.class)
	public Integer updateApplicationStatusToClosed(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,Integer ownerId,Integer documentId, String email) throws Exception
	{
		LOGGER.debug("Request received from service to change the application status to Closed for the logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		String checkStartDateStatus="";
		if(performActionsOverApplicationDTO.getUpdatedStatus()==null)
		{
			LOGGER.error("The status to be updated is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		else if(ownerId==null)
		{
			LOGGER.error("The owner id received is null");
			LOGGER.error("Request cannot be processed, returning - 5 to the service");
			return -5;
		}
		else if(documentId == null)
		{
			LOGGER.error("The document id received is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		LOGGER.debug("Parameters are not null or empty, Processing request to update the status of application with application Id: "+performActionsOverApplicationDTO.getApplicationId()+" to new status: "+performActionsOverApplicationDTO.getUpdatedStatus());
		LOGGER.debug("Checking if the application has been started or not");
		LOGGER.debug("Creating hashmap of objects to check if the application has been started");
		Map<String,Object>checkStartDate = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting parameters into hashmap");
		checkStartDate.put("ownerId", ownerId);
		checkStartDate.put("documentId", documentId);
		LOGGER.debug("Parameters inserted into hashmap to check the startDate of the application with applicationId: "+performActionsOverApplicationDTO.getApplicationId());
		try
		{
			LOGGER.debug("In try block to check the startDate of application for the document Id and ownerId");
			LOGGER.debug("Executing query to check the startdate of application");
			checkStartDateStatus = getJdbcTemplate().queryForObject(departmentHeadHomeConfig.getCheckStartDateBeforeUpdate(), checkStartDate, String.class);
			
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while checking the startDate of application with applicationId: "+performActionsOverApplicationDTO.getApplicationId());
			LOGGER.error("Throwing exception to service");
			throw new Exception(e);
		}
		
		if(checkStartDateStatus==null||checkStartDateStatus.isEmpty())
		{
			LOGGER.debug("There is no start date for application with application id: "+performActionsOverApplicationDTO.getApplicationId());
			LOGGER.debug("Creating hashmap of objects");
			Map<String,Object> updateStatusParams = new HashMap<>();
			LOGGER.debug("Hash map of objects created");
			updateStatusParams.put("updatedStatus", performActionsOverApplicationDTO.getUpdatedStatus());
			updateStatusParams.put("ownerId", ownerId);
			updateStatusParams.put("documentId", documentId);
			LOGGER.debug("updated status, ownerId, documentId inserted into hashmap");
			try 
			{
				LOGGER.debug("In try block to update the application status to closed and assign start date as current date for application id: "+performActionsOverApplicationDTO.getApplicationId());
				LOGGER.debug("Executing query to update the status of application to Closed");
				Integer updateStatus = getJdbcTemplate().update(departmentHeadHomeConfig.getUpdateStatusToCompleteWithoutStartDate(), updateStatusParams);
				if(updateStatus>0)
				{
					  LOGGER.debug("Document status table successfully updated, updating the audit table");
					  LOGGER.debug("Calling updateAuditTable method to update the details in the audit table");
					  Integer auditTableUpdate = updateAuditTable(performActionsOverApplicationDTO,ownerId,email);
					  LOGGER.debug("The status of audit table update is : "+auditTableUpdate);
					  return auditTableUpdate;
				}
				else
				{
					LOGGER.error("Status cannot be updated, Returning -10 to service");
					return -10;
				}
			}
			catch(Exception e)
			{
				LOGGER.error("An exception occured while updating the status of the application to Closed: "+e);
				LOGGER.error("Throwing exception to service");
				throw new Exception(e);
			}
		}
		else
		{
			LOGGER.debug("The start date for application with application id: "+performActionsOverApplicationDTO.getApplicationId()+" is present: "+checkStartDate);
			LOGGER.debug("Creating hashmap of objects");
			Map<String,Object> updateStatusParams = new HashMap<>();
			LOGGER.debug("Hash map of objects created");
			updateStatusParams.put("updatedStatus", performActionsOverApplicationDTO.getUpdatedStatus());
			updateStatusParams.put("ownerId", ownerId);
			updateStatusParams.put("documentId", documentId);
			LOGGER.debug("updated status, ownerId, documentId inserted into hashmap");
			try 
			{
				LOGGER.debug("In try block to update the application status to closed and assign start date as current date for application id: "+performActionsOverApplicationDTO.getApplicationId());
				LOGGER.debug("Executing query to update the status of application to Closed");
				Integer updateStatus = getJdbcTemplate().update(departmentHeadHomeConfig.getUpdateStatusToComplete(), updateStatusParams);
				if(updateStatus>0)
				{
					  LOGGER.debug("Document status table successfully updated, updating the audit table");
					  LOGGER.debug("Calling updateAuditTable method to update the details in the audit table");
					  Integer auditTableUpdate = updateAuditTable(performActionsOverApplicationDTO,ownerId,email);
					  LOGGER.debug("The status of audit table update is : "+auditTableUpdate);
					  return auditTableUpdate;
				}
				else
				{
					LOGGER.error("Status cannot be updated, Returning -10 to service");
					return -10;
				}
			}
			catch(Exception e)
			{
				LOGGER.error("An exception occured while updating the status of the application to Closed: "+e);
				LOGGER.error("Throwing exception to service");
				throw new Exception(e);
			}
		}
		
		
		
	}

	/**
	 * This method is used to fill the audit table which will be updated on every action that is performed on the applications
	 * @param performActionsOverApplicationDTO
	 * @param ownerId
	 * @param documentId,String email
	 * @return number of updated rows after insert in audit table
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateAuditTable(PerformActionsOverApplicationDTO performActionsOverApplicationDTO, Integer ownerId,String email) throws Exception {
		
		LOGGER.debug("Request received in DAO - updateAuditTable() to insert the audit details into the table");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> paramMap = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting parameters into hashmap");
		paramMap.put("applicationId", performActionsOverApplicationDTO.getApplicationId());
		paramMap.put("senderName", performActionsOverApplicationDTO.getSenderName());
		paramMap.put("subject",performActionsOverApplicationDTO.getSubject());
		paramMap.put("priority", performActionsOverApplicationDTO.getPriority());
		paramMap.put("ownerId", ownerId);
		paramMap.put("eta", performActionsOverApplicationDTO.getEta());
		paramMap.put("documentRemarks", performActionsOverApplicationDTO.getDocumentRemarks());
		paramMap.put("documentPath", performActionsOverApplicationDTO.getDocumentPath());
		paramMap.put("status", performActionsOverApplicationDTO.getUpdatedStatus());
		paramMap.put("documentType", performActionsOverApplicationDTO.getDocumentType());
		paramMap.put("referenceNumber",performActionsOverApplicationDTO.getReferenceNumber());
		paramMap.put("email", email);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		
		try 
		{
			LOGGER.debug("In try block to update the audit table for application id: "+performActionsOverApplicationDTO.getApplicationId());
			LOGGER.debug("Executing query to insert the details into the audit table");
			return getJdbcTemplate().update(departmentHeadHomeConfig.getInsertAuditTableDetails(), paramMap);
		} 
		catch (Exception e) 
		{
			LOGGER.error("An exception occured while inserting details into the audit table: "+e);
			LOGGER.error("Throwing an exception and rolling back the db transactions");
			throw new Exception(e);
		}
	}
	

	
	/**
	 * @author Prateek Kapoor
	 * This method receives the email which is retrieved from the session and gets the department of the logged in user
	 * @param email
	 * @return department name or null - if unsuccessful
	 */
	public String getDepartmentOfLoggedInUser(String email)
	{
		LOGGER.debug("Checking if the email is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Email of the logged in user received null or empty in dao method");
			LOGGER.error("Returning NULL");
			return null;
		}
		LOGGER.debug("Request received in getDepartmentOfLoggedInUser of DepartmentHeadHomeDao class to get the department name of the logged in user with email: "+email);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>params = new HashMap<>();
		LOGGER.debug("Created hashmap, Inserting email of logged in user");
		params.put("email",email);
		LOGGER.debug("Email inserted into the hashmap");
		try 
		{
			LOGGER.debug("In try block of getDepartmentOfLoggedInUser method to get the name of the logged in user with email: "+email);
			LOGGER.debug("Executing query to get the department name of the logged in user with email: "+email);
			return getJdbcTemplate().queryForObject(departmentHeadHomeConfig.getDepartmentNameUsingEmail(), params, String.class);
			
		} catch (Exception e) {
			
			LOGGER.error("An exception occured while fetching the department of the logged in user with email: "+email);
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL to service");
			return null;
		}
		
	}
	
	
	/**
	 * This method gets the owner id for the owner name and the department
	 * @param ownerName
	 * @return 1 if success, null if params are null or empty or an exception occurs
	 */
	public Integer getOwnerIdByOwnerName(String ownerName)
	{
		LOGGER.debug("Request received in DAO to get the id of the owner");
		LOGGER.debug("Checking if the parameters are null or empty");
		if(ownerName==null||ownerName.isEmpty())
		{
			LOGGER.error("The retreived department name is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the Service");
			return null;
		}
//		if(departmentName==null||departmentName.isEmpty())
//		{
//			LOGGER.error("The department is null or empty in dao");
//			LOGGER.error("Request cannot be processed");
//			LOGGER.error("Returning null to service");
//			return null;
//		}
		LOGGER.debug("Parameters are not null or empty");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> ownerParams = new HashMap<>();
		LOGGER.debug("Hashmap of objects created successfully, Inserting department and ownerName into hashmap");
		ownerParams.put("ownerName", ownerName);
	//	ownerParams.put("department", departmentName);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
		 // LOGGER.debug("In try block to get the id of the owner for the department: "+departmentName);
		  LOGGER.debug("Executing query to get the id of the owner with name: "+ownerName);
		  return getJdbcTemplate().queryForObject(departmentHeadHomeConfig.getDocumentOwnerIdForDepartment(), ownerParams, Integer.class);
		} 
		catch (Exception e) 
		{
			LOGGER.error("An exception occured while fetching the id of the document owner with name: "+ownerName);
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL to service");
			return null;	
		}
		
	}
	
	
	
	/**
	 * This method returns the application details whose status is assigned and owner is the logged in user
	 * @param email
	 * @return AssignedApplicationsDTO object if success , Else returns null
	 */
	public Collection<AssignedApplicationsDTO> getAssignedApplications(String email)
	{
		LOGGER.debug("Request received in DAO to get the details of the application whose status is ASSIGNED");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("The received param - email are null or empty");
			LOGGER.error("Request cannot be processed further");
			LOGGER.error("Returning null to the service");
			return null;
		}
		LOGGER.debug("The received parameter is not null or empty");
		LOGGER.debug("Processing request to get the application details with status ASSIGNED corresponding to the logged in user with email: "+email);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>assignedParams = new HashMap<>();
		assignedParams.put("email", email);
		LOGGER.debug("Parameter email successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the application details with status ASSIGNED for logged in user with email: "+email);
			LOGGER.debug("Executing query to get the details of the ASSIGNED applications");
			return getJdbcTemplate().query(departmentHeadHomeConfig.getAssignedApplications(), assignedParams, APPLICATIONS_ROW_MAPPER);
			
		} catch (Exception e) 
		{
			LOGGER.error("An exception occured while fetching the details of the assigned applications: "+e);
			LOGGER.error("Returning NULL to service");
			return null;
		}
	}
	

	/**
	 * This method returns the application details whose status is IN ACTION for the logged in user
	 * @param email
	 * @return COLLECTION of AssignedApplicationsDTO object
	 */
	public Collection<AssignedApplicationsDTO> getInActionApplications(String email)
	{
		LOGGER.debug("Request received from service to get the applications with status IN ACTION for logged in user");
		LOGGER.debug("Checking if the parameters received are null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Email param received inside the method is null or empty");
			LOGGER.error("Request cannot be processed, returning null to the service");
			return null;
		}
		LOGGER.debug("The received params are not null or empty");
		LOGGER.debug("Processing request to get the details of application with status IN ACTION for logged in user with email: "+email);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> params = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting email into hashmap");
		params.put("email", email);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the details of application with status - IN ACTION for logged in user with email: "+email);
			LOGGER.debug("Executing query to get details of application with status - IN ACTION for logged in user with email: "+email);
			return getJdbcTemplate().query(departmentHeadHomeConfig.getInActionApplications(), params,APPLICATIONS_ROW_MAPPER );
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching records of applications with status IN ACTION: "+e);
			LOGGER.error("Request could not be processed, returning NULL to service");
			return null;
		}
	}
	
	/**
	 * Row Mapper for Assigned and On Hold applications
	 * @author Prateek Kapoor
	 *
	 */
	
	public static class AssignedApplicationsMapper implements RowMapper<AssignedApplicationsDTO>
	{
		@Override
		public AssignedApplicationsDTO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String applicationId = rs.getString("applicationId");
			String senderName = rs.getString("senderName");
			Date dateAssigned = rs.getDate("dateAssigned");
			String subject = rs.getString("subject");
			String priority = rs.getString("priority");
			String status = rs.getString("status");
			String documentType = rs.getString("documentType");
			String referenceNumber = rs.getString("referenceNumber");
			Date eta = rs.getDate("eta");
			String documentRemarks = rs.getString("documentRemarks");
			String documentPath = rs.getString("documentPath");
			String name = rs.getString("name");
			
						
			return new AssignedApplicationsDTO(applicationId, senderName, dateAssigned, subject, priority, eta, status, documentType,referenceNumber, documentRemarks, documentPath,name);

			
		}
	}
	
	/**
	 * This method returns the application details whose status is ON HOLD for the logged in user
	 * @param email
	 * @return COLLECTION of AssignedApplicationsDTO object
	 */
	public Collection<AssignedApplicationsDTO> getOnHoldApplications(String email)
	{
		LOGGER.debug("Request received from service to get the applications with status ON HOLD for logged in user");
		LOGGER.debug("Checking if the parameters received are null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Email param received inside the method is null or empty");
			LOGGER.error("Request cannot be processed, returning null to the service");
			return null;
		}
		LOGGER.debug("The received params are not null or empty");
		LOGGER.debug("Processing request to get the details of application with status ON HOLD for logged in user with email: "+email);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> params = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting email into hashmap");
		params.put("email", email);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the details of application with status - ON HOLD for logged in user with email: "+email);
			LOGGER.debug("Executing query to get details of application with status - ON HOLD for logged in user with email: "+email);
			return getJdbcTemplate().query(departmentHeadHomeConfig.getOnHoldApplications(), params,APPLICATIONS_ROW_MAPPER );
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching records of applications with status ON HOLD: "+e);
			LOGGER.error("Request could not be processed, returning NULL to service");
			return null;
		}
	}
	
	
	
	/**
	 * This method returns the application details whose status is Delayed for the logged in user
	 * @param email
	 * @return COLLECTION of AssignedApplicationsDTO object
	 */
	public Collection<AssignedApplicationsDTO> getDelayedApplications(String email) 
	{
		
		LOGGER.debug("Request received from service to get the applications with status Delayed for logged in user");
		LOGGER.debug("Checking if the parameters received are null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Email param received inside the method is null or empty");
			LOGGER.error("Request cannot be processed, returning null to the service");
			return null;
		}
		LOGGER.debug("The received params are not null or empty");
		LOGGER.debug("Processing request to get the details of application with status Delayed for logged in user with email: "+email);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> params = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting email into hashmap");
		params.put("email", email);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the details of application with status - Delayed for logged in user with email: "+email);
			LOGGER.debug("Executing query to get details of application with status - Delayed for logged in user with email: "+email);
			return getJdbcTemplate().query(departmentHeadHomeConfig.getDelayedApplications(), params,APPLICATIONS_ROW_MAPPER );
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching records of applications with status Delayed: "+e);
			LOGGER.error("Request could not be processed, returning NULL to service");
			return null;
		}
	}
	

	/**
	 * This method returns the list of applications on the basis of department of logged in user and application id of application
	 * @param status
	 * @param department
	 * @return Collection<ApplicationSearchResultsDto>
	 */
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByApplicationId(String applicationId, String department)
	{
		LOGGER.debug("Request received from service to get the application details for application id corresponding to the department of logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("Application id received inside DAO in null or empty");
			LOGGER.error("Request to get the application details using application id could not be processed");
			LOGGER.error("Returning null to the service");
			return null;
		}
		else if(department==null||department.isEmpty())
		{
			LOGGER.error("The department name received inside the dao is null or empty");
			LOGGER.error("Request to get the application details using application id could not be processed");
			LOGGER.error("Returning null to the service");
			return null;
		}
		LOGGER.debug("The received parameters are not null or empty");
		LOGGER.debug("Processing request to get the application details using application id for the department of logged in user");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>applicationIdParams = new HashMap<>();
		LOGGER.debug("Hashmap of objects successfully created, Inserting the application id and department name in the hashmap");
		applicationIdParams.put("applicationId", applicationId);
		applicationIdParams.put("department", department);
		LOGGER.debug("Department and application id successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the details of applicaiton using application id for the department of the logged in user");
			LOGGER.debug("Executing query to get application details for application id: "+applicationId+" and the department: "+department);
			return getJdbcTemplate().query(departmentHeadSearchConfig.getApplicationByApplicationId(), applicationIdParams, ROW_MAPPER);
		} catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching the details of the application using application id: "+applicationId+" for department: "+department);
			LOGGER.error("The exception occured is: " +e);
			LOGGER.error("Returning NULL to the service");
			return null;
			// TODO: handle exception
		}
	}
	
	/**
	 * This method returns the list of applications on the basis of department of logged in user and the status of application
	 * @param status
	 * @param department
	 * @return Collection<ApplicationSearchResultsDto> - if success, else - null
	 */
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByStatus(String status, String department)
	{
		LOGGER.debug("Request received from service to get the application details for status corresponding to the department of logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(status==null||status.isEmpty())
		{
			LOGGER.error("status received inside DAO in null or empty");
			LOGGER.error("Request to get the application details using status could not be processed");
			LOGGER.error("Returning null to the service");
			return null;
		}
		else if(department==null||department.isEmpty())
		{
			LOGGER.error("The department name received inside the dao is null or empty");
			LOGGER.error("Request to get the application details using application id could not be processed");
			LOGGER.error("Returning null to the service");
			return null;
		}
		LOGGER.debug("Parameters received are not null or empty");
		LOGGER.debug("Processing request to get the details of the application for the department of logged in user: "+department+" by status: "+status);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>statusParams = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting parameters into hashmap");
		statusParams.put("status", status);
		statusParams.put("department",department);
		LOGGER.debug("Parameters inserted successfully into hashmap");
		try 
		{
			LOGGER.debug("In try block to get all the applicaitons for the department of logged in user with status: "+status);
			LOGGER.debug("Executing query to get all the applications for the department of logged in user with status: "+status+" and department: "+department);
			return getJdbcTemplate().query(departmentHeadSearchConfig.getApplicationByStatus(), statusParams, ROW_MAPPER);
			
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching the details of the application for department of the logged in user with status: "+status);
			LOGGER.error("Exception is: " +e);
			LOGGER.error("Returning null to service");
			return null;
		}
	}
	
	
	/**
	 * Row Mapper for Mapping Search Results 
	 * @author Prateek Kapoor
	 *
	 */
	public static class ApplicationSearchResults implements RowMapper<ApplicationSearchResultsDto>
	{
		@Override
		public ApplicationSearchResultsDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String applicationId = rs.getString("applicationId");
			String senderName = rs.getString("senderName");
			Date dateReceived = rs.getDate("dateReceived");
			Date dateAssigned = rs.getDate("dateAssigned");
			String subject = rs.getString("subject");
			String ownerName = rs.getString("ownerName");
			String status = rs.getString("status");
			Date eta = rs.getDate("eta");
			String documentPath = rs.getString("documentPath");
			String documentType= rs.getString("documentType");
			String referenceNumber = rs.getString("referenceNumber");
						
			return new ApplicationSearchResultsDto(applicationId, senderName, dateReceived, dateAssigned, subject, ownerName, status, eta, documentPath, documentType,referenceNumber);
			
		}
	}

	/**
	 * This method returns the application details whose status is IN REVIEW for the logged in user
	 * @param email
	 * @return COLLECTION of AssignedApplicationsDTO object
	 */
	public Collection<AssignedApplicationsDTO> getInReviewApplicationDetails(String department) {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from service to get the applications with status IN REVIEW for logged in user");
		LOGGER.debug("Checking if the parameters received are null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Department param received inside the method is null or empty");
			LOGGER.error("Request cannot be processed, returning null to the service");
			return null;
		}
		LOGGER.debug("The received params are not null or empty");
		LOGGER.debug("Processing request to get the details of application with status Delayed for logged in user with department: "+department);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> params = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting department into hashmap");
		params.put("department", department);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the details of application with status - Delayed for logged in user with department: "+department);
			LOGGER.debug("Executing query to get details of application with status - Delayed for logged in user with department: "+department);
			return getJdbcTemplate().query(departmentHeadHomeConfig.getInReviewApplications(), params,APPLICATIONS_ROW_MAPPER );
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching records of applications with status In REVIEW: "+e);
			LOGGER.error("Request could not be processed, returning NULL to service");
			return null;
		}
	}
	
	
	
	/**
	 * This method  updates the status of the application using the document id and owner id to In Action
	 * @param performActionsOverApplicationDTO
	 * @param ownerId
	 * @param documentId
	 * @param email 
	 * @return 1 if the status is updated, -5 if any parameter is empty, -10 if an exception occurs
	 * @throws Exception - this will roll back the update and also will rollback the update in the audit table
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateApplicationStatusToInReview(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,
			Integer ownerId, Integer documentId, String email) throws Exception {
		LOGGER.debug("Request received from service to update the status of application for logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(performActionsOverApplicationDTO.getUpdatedStatus()==null)
		{
			LOGGER.error("The status to be updated is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		if(ownerId==null)
		{
			LOGGER.error("The owner id received is null");
			LOGGER.error("Request cannot be processed, returning - 5 to the service");
			return -5;
		}
		if(documentId == null)
		{
			LOGGER.error("The document id received is null");
			LOGGER.error("Request cannot be processed, returning -5 to the service");
			return -5;
		}
		LOGGER.debug("Parameters are not null or empty, Processing request to update the status of application with application Id: "+performActionsOverApplicationDTO.getApplicationId()+" to new status: "+performActionsOverApplicationDTO.getUpdatedStatus());
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>updateStatusParams = new HashMap<>();
		LOGGER.debug("Hashmap Created Successfully, Inserting parameters into hashmap");
		updateStatusParams.put("updatedStatus", performActionsOverApplicationDTO.getUpdatedStatus());
		updateStatusParams.put("ownerId", ownerId);
		updateStatusParams.put("documentId", documentId);
		LOGGER.debug("updated status, ownerId, documentId inserted into hashmap");
		try
		{
			LOGGER.debug("In try block to update the application status to In Review for application id: "+performActionsOverApplicationDTO.getApplicationId());
			LOGGER.debug("Executing query to update the status of application to In Review");
			Integer updateStatus = getJdbcTemplate().update(departmentHeadHomeConfig.getUpdateStatusToInReview(), updateStatusParams);
			if(updateStatus>0)
			{
				  LOGGER.debug("Document status table successfully updated, updating the audit table");
				  LOGGER.debug("Calling updateAuditTable method to update the details in the audit table");
				  Integer auditTableUpdate = updateAuditTable(performActionsOverApplicationDTO,ownerId,email);
				  LOGGER.debug("The status of audit table update is : "+auditTableUpdate);
				  return auditTableUpdate;
			}
			else
			{
				LOGGER.error("Status cannot be updated, Returning -10 to service");
				return -10;
			}
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while updating the status of the application to In Review: "+e);
			LOGGER.error("Throwing exception to service");
			throw new Exception(e);
		}
	
	}

	
	
	
	
}

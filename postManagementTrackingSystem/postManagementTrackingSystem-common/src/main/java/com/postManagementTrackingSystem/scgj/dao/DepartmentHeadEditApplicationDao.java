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
import com.postManagementTrackingSystem.scgj.config.DepartmentHeadEditApplicationConfig;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditApplicationParamsDHDto;
import com.postManagementTrackingSystem.scgj.dto.ShowEditApplicationDetailsDepartmentHeadDto;

@Repository
public class DepartmentHeadEditApplicationDao extends AbstractTransactionalDao
{

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadEditApplicationDao.class);
	@Autowired
	private DepartmentHeadEditApplicationConfig departmentHeadEditApplicationConfig;
	
	private static EditApplicationDetailsRowMapper ROW_MAPPER = new EditApplicationDetailsRowMapper();
	
	
	
	public Collection<ShowEditApplicationDetailsDepartmentHeadDto> showEditApplicationDetails(String applicationId, String department)
	{
		LOGGER.debug("Request received from service to get the details of the application using application id for the department of the logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("The application received in dao is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the service");
			return null;
		}
		else if(department==null||department.isEmpty())
		{
			LOGGER.error("The department name received is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the service");
			return null;
		}
		LOGGER.debug("The receieved parameters are not null or empty");
		LOGGER.debug("Processing request to get the application details for application id: "+applicationId+" corresponding to the department of the logged in user: "+department);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>applicationParams = new HashMap<>();
		LOGGER.debug("Inserting parameters into hashmap");
		applicationParams.put("applicationId", applicationId);
		applicationParams.put("department",department);
		LOGGER.debug("Application id and department successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the details of applications using application id and department of the logged in user");
			LOGGER.debug("Executing query to get the application details for application id:"+applicationId+" corresponding to the department of the logged in user: "+department);
			return getJdbcTemplate().query(departmentHeadEditApplicationConfig.getShowApplicationUsingApplicationId(), applicationParams, ROW_MAPPER);
		} 
		catch (Exception e) 
		{
			LOGGER.error("An exception occured while fetching the details of the application using application id and department name : "+e);
			LOGGER.error("Request cannot be proccessed, returning null to the service");
			return null;
		}
	}
	
	
	/**
	 * This method updates the application owner against the application id
	 * @param receiveEditApplicationParamsDHDto
	 * @param documentId
	 * @param ownerId
	 * @param email
	 * @return if success - number of rows affected, -20 if any parameter is null
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateApplicationOwner(ReceiveEditApplicationParamsDHDto receiveEditApplicationParamsDHDto, Integer documentId, Integer ownerId, String email) throws Exception
	{
		LOGGER.debug("Request received in dao to update the owner of the application with application Id");
		LOGGER.debug("Checking if the received parameters are null");
		if(documentId==null)
		{
			LOGGER.error("Document id received null in DAO");
			LOGGER.error("Request cannot be processed, returning -20 to service");
			return -20;
		}
		else if(ownerId==null)
		{
			LOGGER.error("Owner id received null in dao");
			LOGGER.error("Request cannot be processed, returning -20 to service");
			return -20;
		}
		else if(email==null)
		{
			LOGGER.error("Email received null in dao");
			LOGGER.error("Request cannot be processed, returning -20 to service");
			return -20;
		}
		LOGGER.debug("Parameters received are not null or empty");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>editApplicationParams = new HashMap<>();
		LOGGER.debug("Hashmap of object successfully created, Inserting document id and owner Id into hashmap");
		editApplicationParams.put("ownerId", ownerId);
		editApplicationParams.put("documentId", documentId);
		LOGGER.debug("Document id and owner id inserted into hash map");
		try 
		{
			LOGGER.debug("In try block to update the owner id against the post with document id: "+documentId);
			LOGGER.debug("Executing query to update the owner of the post");
			Integer updateStatus = getJdbcTemplate().update(departmentHeadEditApplicationConfig.getUpdateApplicationOwner(), editApplicationParams);
			if(updateStatus<1)
			{
				LOGGER.error("Owner of the application could not be updated");
				LOGGER.error("Returning -30 to the servcie");
				return -30;
			}
			LOGGER.debug("Owner of the application successfully updated, sending request to update the audit table");
			LOGGER.debug("Setting the value of status to Assigned to be reflected in the audit table");
			receiveEditApplicationParamsDHDto.setUpdatedStatus("Assigned");
			LOGGER.debug("Application status set to Assigned before inserting information into the audit table");
			Integer updateAuditTableStatus = updateAuditTable(receiveEditApplicationParamsDHDto,ownerId,email);
			return updateAuditTableStatus;
		}
		catch (Exception e) 
		{
			LOGGER.error("An exception occured while updating the owner of the application with applicationId: "+receiveEditApplicationParamsDHDto.getApplicationId());
			LOGGER.error("Throwing an exception to the calling method and rolling back the DB Transactions");
			throw new Exception(e);
		}
	}
	
	
	/**
	 * This method inserts a new entry into audit table every time there is an update in either the owner of the application or in the ETA of the applications
	 * @param receiveEditApplicationParamsDHDto
	 * @param ownerId
	 * @param email
	 * @return number of rows affected
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateAuditTable(ReceiveEditApplicationParamsDHDto receiveEditApplicationParamsDHDto, Integer ownerId, String email) throws Exception {
		
		LOGGER.debug("Request received to update audit table");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>params = new HashMap<>();
		LOGGER.debug("Hashmap of objects successfully created, Inserting parameters into hashmap");
		params.put("applicationId", receiveEditApplicationParamsDHDto.getApplicationId());
		params.put("senderName", receiveEditApplicationParamsDHDto.getSenderName());
		params.put("subject",receiveEditApplicationParamsDHDto.getSubject());
		params.put("priority",receiveEditApplicationParamsDHDto.getPriority());
		params.put("ownerId", ownerId);
		params.put("eta",receiveEditApplicationParamsDHDto.getEta());
		params.put("documentRemarks", receiveEditApplicationParamsDHDto.getDocumentRemarks());
		params.put("documentPath",receiveEditApplicationParamsDHDto.getDocumentPath());
		params.put("status",receiveEditApplicationParamsDHDto.getUpdatedStatus());
		params.put("documentType", receiveEditApplicationParamsDHDto.getDocumentType());
		params.put("email",email);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to update the audit table");
			LOGGER.debug("Executing query to update the audit table with a new entry");
			return getJdbcTemplate().update(departmentHeadEditApplicationConfig.getInsertIntoAuditTable(), params);
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while inserting details into audit table: "+e);
			LOGGER.error("Throwing an exception and rolling back the DB Transactions for update and insert into audit table");
			throw new Exception(e);
		}
		

	}

	/**
	 * This method checks if the status of application whose status has to be updated is delayed or not and then on the basis of status performs the following actions: 
	 * If the status is DELAYED - update the eta and change the status to IN ACTION
	 * ELSE - Update the ETA of the application
	 * @param receiveEditApplicationParamsDHDto
	 * @param ownerId
	 * @param documentId
	 * @param email
	 * @return number of rows affected, -10 if the parameter received is null, -30 if update operation fails
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateApplicationEta(ReceiveEditApplicationParamsDHDto receiveEditApplicationParamsDHDto, Integer ownerId,Integer documentId,String email) throws Exception
	{
		LOGGER.debug("Request received in DAO to update the ETA of application with application Id: "+receiveEditApplicationParamsDHDto.getApplicationId());
		LOGGER.debug("Checking if the received parameters are null");
		if(documentId==null)
		{
			LOGGER.error("Document id received in dao is null");
			LOGGER.error("Request cannot be processed, returning -10 to service");
			return -10;
		}
		else if(email==null)
		{
			LOGGER.error("Email received null in dao");
			LOGGER.error("Request cannot be processed, returning -20 to service");
			return -10;
		}
		else if(receiveEditApplicationParamsDHDto.getApplicationId()==null||receiveEditApplicationParamsDHDto.getApplicationId().isEmpty())
		{
			LOGGER.error("Application id received in dao is null or empty");
			LOGGER.error("Request cannot be processed, returning -10 to service");
			return -10;
		}
		else if(receiveEditApplicationParamsDHDto.getEta()==null)
		{
			LOGGER.debug("The eta is null in dao, Request cannot be processed");
			LOGGER.debug("Returning -10 to the service");
			return -10;
		}
		LOGGER.debug("Parameters received are not null or empty");
		LOGGER.debug("Processing request to update the ETA of application with applicationId: "+receiveEditApplicationParamsDHDto.getApplicationId());
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>editEta = new HashMap<>();
		LOGGER.debug("Hashmap of objects created, inserting parameters into hashmap");
		editEta.put("eta", receiveEditApplicationParamsDHDto.getEta());
		editEta.put("documentId", documentId);
		LOGGER.debug("Parameters - ETA, DOCUMENTID inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to update the eta of the application with document id: "+documentId);
			LOGGER.debug("Checking the existing status of the application");
			if(receiveEditApplicationParamsDHDto.getUpdatedStatus().equalsIgnoreCase("Delayed"))
			{
				LOGGER.debug("The existing status of application with application id: "+receiveEditApplicationParamsDHDto.getApplicationId()+" is: "+receiveEditApplicationParamsDHDto.getUpdatedStatus());
				LOGGER.debug("Executing query to update the ETA and the status of application with application id: "+receiveEditApplicationParamsDHDto.getApplicationId()+" to a new status: In Action");
				Integer etaUpdateStatus = getJdbcTemplate().update(departmentHeadEditApplicationConfig.getUpdateEtaOfApplicationWithStatus(), editEta);
				if(etaUpdateStatus<1)
				{
					LOGGER.error("The ETA and status of the application could not be updated");
					LOGGER.error("Returning -30 to the service");
					return -30;
				}
				else
				{
					LOGGER.debug("ETA and status of the application successfully updated");
					LOGGER.debug("Setting the status of the application to In Action in the DTO");
					receiveEditApplicationParamsDHDto.setUpdatedStatus("In Action");
					LOGGER.debug("The status of the application set to IN ACTION in DTO");
					LOGGER.debug("Sending request to update the audit table");
					int updateAuditTable = updateAuditTable(receiveEditApplicationParamsDHDto, ownerId, email);
					return updateAuditTable;
				}
			}
			else
			{
				LOGGER.debug("The existing status of application with application id: "+receiveEditApplicationParamsDHDto.getApplicationId()+" is: "+receiveEditApplicationParamsDHDto.getUpdatedStatus());
				LOGGER.debug("Executing query to update only the ETA of the application");
				Integer etaUpdateStatus = getJdbcTemplate().update(departmentHeadEditApplicationConfig.getUpdateEtaOfApplicationWithoutStatus(), editEta);
				if(etaUpdateStatus<1)
				{
					LOGGER.error("The ETA and status of the application could not be updated");
					LOGGER.error("Returning -30 to the service");
					return -30;
				}
				else
				{
					LOGGER.debug("ETA and status of the application successfully updated");
					LOGGER.debug("Sending request to update the audit table");
					int updateAuditTable = updateAuditTable(receiveEditApplicationParamsDHDto, ownerId, email);
					return updateAuditTable;
				}
				
			}
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while updating the eta of the application: "+e);
			LOGGER.error("Throwing exception and rollig back the DB changes");
			throw new Exception(e);		
			
		}
	}

	/**
	 * ROW MAPPER for Edit APPLICATION DETAILS
	 * @author Prateek Kapoor
	 *
	 */
	public static class EditApplicationDetailsRowMapper implements RowMapper<ShowEditApplicationDetailsDepartmentHeadDto>
	{
		@Override
		public ShowEditApplicationDetailsDepartmentHeadDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String applicationId = rs.getString("applicationId");
			String senderName = rs.getString("senderName");
			String subject = rs.getString("subject");
			String priority = rs.getString("priority");
			Date dateAssigned = rs.getDate("dateAssigned");
			Date dateReceived = rs.getDate("dateReceived");
			String documentRemarks = rs.getString("documentRemarks");
			String documentPath = rs.getString("documentPath");
			String documentType = rs.getString("documentType");
			String status = rs.getString("status");
			Date eta = rs.getDate("eta");
			String ownerName = rs.getString("ownerName");
			
			return new ShowEditApplicationDetailsDepartmentHeadDto(applicationId, senderName, subject, priority, dateAssigned,dateReceived, documentRemarks,documentPath, documentType, status, eta, ownerName);
			 
			 
			
			
		}
	}
	
}

package com.postManagementTrackingSystem.scgj.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.postManagementTrackingSystem.scgj.common.AbstractTransactionalDao;
import com.postManagementTrackingSystem.scgj.config.DepartmentHeadHomeConfig;
import com.postManagementTrackingSystem.scgj.dto.AssignedApplicationsDTO;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNotStartedApplicationDTO;
import com.postManagementTrackingSystem.scgj.dto.DisplayAuditTableDHDTO;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfDepartmentEmployeesDTO;
import com.postManagementTrackingSystem.scgj.dto.PerformActionsOverApplicationDTO;

@Repository
public class DepartmentHeadHomeDao extends AbstractTransactionalDao{

	@Autowired
	private DepartmentHeadHomeConfig departmentHeadHomeConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadHomeDao.class);
	
	private static DepartmentEmployeesNameRowMapper ROW_MAPPER = new DepartmentEmployeesNameRowMapper();
	private static ApplicationDetailsMapper APPLICATION_MAPPER = new ApplicationDetailsMapper();
	private static AuditTableMapper AUDIT_TABLE_MAPPER = new AuditTableMapper();

	
	/**
	 * @author Prateek Kapoor
	 * This method gets the names of the department employees using the department name parameter
	 * @param department
	 * @return LIST of GetNameOfDepartmentEmployeesDTO object if successful else returns null
	 */
	public List<GetNameOfDepartmentEmployeesDTO> getNameOfEmployees()
	{
		LOGGER.debug("Received request in dao method to get name of employees with role DE and DH");
//		LOGGER.debug("Checking if the department parameter is null or empty");
//		if(department==null || department.isEmpty())
//		{
//			LOGGER.error("Department is null or empty in getNameOfDepartmentEmployees method in dao");
//			LOGGER.error("Returning null to service");
//			return null;
//		}
	//	LOGGER.debug("Department name is not null or empty, Processing the request");
		//LOGGER.debug("Request received to get the name of the employees in department with name: "+department);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>departmentParams = new HashMap<>();
		LOGGER.debug("Hashmap created successfully");
//		LOGGER.debug("Inserting department name into hashmap");
//		departmentParams.put("department", department);
		try 
		{
			
			LOGGER.debug("In try block to get the name of employees with role DE and DH");
			LOGGER.debug("Executing query to get the names of employees with role DE and DH");
			return getJdbcTemplate().query(departmentHeadHomeConfig.getDepartmentEmployeeNameUsingDepartmentName(), departmentParams, ROW_MAPPER);
			
		} catch (Exception e) 
		{
			
			LOGGER.error("An exception occured while fetching the names of department employees with role DE and DH");
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning null to the service");
			return null;
		}
	}
	
	
	/**
	 * This method gets the details of the application corresponding to the department, whose status is not started
	 * @param department
	 * @return Collection of DepartmentHeadNotStartedApplicationDTO object else null
	 */
	public Collection<DepartmentHeadNotStartedApplicationDTO> getNotStartedApplicationDetails(String department)
	{
		LOGGER.debug("Request received in DAO to get the details of not started applications");
		LOGGER.debug("Checking if the received param is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("The received parameter department is null or empty ");
			LOGGER.error("Request cannot be processed, Returning null to the service");
			return null;
		}
		LOGGER.debug("The received parameter is not null or empty");
		LOGGER.debug("The department for which the details of not started applications have to be fetched is: "+department);
		LOGGER.debug("Processing request to get the details of not started applications for department: "+department);
		LOGGER.debug("Creating hashmap of objects ");
		Map<String,Object>applicationParams = new HashMap<>();
		LOGGER.debug("Hashmap successfully created");
		LOGGER.debug("Inserting department into hashmap");
		applicationParams.put("department", department);
		LOGGER.debug("Department param inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block of getNotStartedApplicationDetails() method to get the application details whose status is not started for department: "+department);
			LOGGER.debug("Executing query to fetch the details of not started applications for department: "+department);
			return getJdbcTemplate().query(departmentHeadHomeConfig.getNotStartedApplications(), applicationParams, APPLICATION_MAPPER);
			
		} catch (Exception e) 
		{
			LOGGER.error("An exception has occured while fetching the details of Not Started applications for department: "+department);
			LOGGER.error("The exception is :  "+e);
			LOGGER.error("Request cannot be processed, Returning NULL to the service");
			return null;
		}
	}
	

	/**
	 * Row mapper class to get name of employees in a department
	 * @author Prateek Kapoor
	 *
	 */
	
	public static class DepartmentEmployeesNameRowMapper implements RowMapper<GetNameOfDepartmentEmployeesDTO>
	{
		@Override
		public GetNameOfDepartmentEmployeesDTO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String departmentEmployeeName = rs.getString("name");
						
			return new GetNameOfDepartmentEmployeesDTO(departmentEmployeeName);

			
		}
	}

	/**
	 * Row mapper class to map the application details from database to DepartmentHeadNotStartedApplicationDTO
	 * @author Prateek Kapoor
	 *
	 */
	public static class ApplicationDetailsMapper implements RowMapper<DepartmentHeadNotStartedApplicationDTO>
	{
		@Override
		public DepartmentHeadNotStartedApplicationDTO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String applicationId = rs.getString("applicationId");
			String senderName = rs.getString("senderName");
			String dateReceived = rs.getString("dateReceived");
			String subject = rs.getString("subject");
			String priority = rs.getString("priority");
			String status = rs.getString("status");
			String documentPath = rs.getString("documentPath");
			String documentType = rs.getString("documentType");
			String referenceNumber = rs.getString("referenceNumber");
			String additionalComments = rs.getString("document_remarks");
						
			return new DepartmentHeadNotStartedApplicationDTO(applicationId, senderName, dateReceived, subject, priority,status, documentPath, documentType,referenceNumber, additionalComments);

			
		}
	}
	

	
	
	/**
	 * This method assigns owner, ETA to the applications and sets the status as ASSIGNED for the corresponding application
	 * @param ownerId
	 * @param documentId
	 * @param eta
	 * @return 1 if success, -5 if parameters are empty and -30 if an exception occurs
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer assignOwner(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,Integer ownerId, Integer documentId, String email) throws Exception 
	{
		
		LOGGER.debug("Request received in DAO to update the assignee of the application with document Id: "+documentId);
		LOGGER.debug("Checking if the parameters received are null or empty");
		if(ownerId==null)
		{
			LOGGER.error("The ownerId id is null or empty in DAO");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to service");
			return -5;
		}
		
		if(email==null||email.isEmpty())
		{
			LOGGER.error("The email is null or empty in DAO");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to service");
			return -5;
		}
		if(documentId==null)
		{
			LOGGER.error("The owner name is null or empty in DAO");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to service");
			return -5;
		}
		
		LOGGER.debug("All the parameters received in service are not null or empty");
		LOGGER.debug("Processing the request to update the owner of the application with document id: "+documentId);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> assignOwnerParams = new HashMap<>();
		LOGGER.debug("Hashmap successfully created, Inserting parameters into hashmap");
		assignOwnerParams.put("ownerId", ownerId);
		assignOwnerParams.put("documentId",documentId);
		assignOwnerParams.put("eta",performActionsOverApplicationDTO.getEta());
		assignOwnerParams.put("additionalComment", performActionsOverApplicationDTO.getDocumentRemarks());
		LOGGER.debug("Owner Id, ETA,  Document id successfully inserted into hashmap");
		try 
		{
		  LOGGER.debug("In try block to assign owner to the application with documentId: "+documentId);
		  LOGGER.debug("Executing query to assign owner to application with documentId: "+documentId);
		  Integer documentUpdateStatus = getJdbcTemplate().update(departmentHeadHomeConfig.getAssignApplicationOwner(), assignOwnerParams);
		  
		  if(documentUpdateStatus>0)
		  {
			  LOGGER.debug("Document status table successfully updated, updating the audit table");
			  LOGGER.debug("Calling updateAuditTable method to update the details in the audit table");
			  LOGGER.debug("Setting status of the application as ASSIGNED for Audit Table");
			  Integer auditTableUpdate = updateAuditTable(performActionsOverApplicationDTO,ownerId,email);
			  LOGGER.debug("The status of audit table update is : "+auditTableUpdate);
			  return auditTableUpdate;
		  }
		  
		  else
		  {
			  LOGGER.error("The application if with document id: "+documentId+" cannot be assigned");
			  LOGGER.error("Returning -25");
			  return -25;
		  }
		} catch (Exception e) 
		{
			LOGGER.error("An exception occured while assigning owner to application with document Id: "+documentId );
			LOGGER.error("The exception is: "+e);
			throw new Exception(e);
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
		paramMap.put("referenceNumber", performActionsOverApplicationDTO.getReferenceNumber());
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
	 * This method is used to populate the audit table corresponding to the user who is logged in to the system
	 * @param email
	 * @return Collection of AssignedApplicationsDTO object else return null
	 */
	public Collection<DisplayAuditTableDHDTO> populateAuditTable(String email)
	{
		LOGGER.debug("Request received from service in populateAuditTable() of DAO to populate the audit table for the logged in user");
		LOGGER.debug("Checking if the received parameter is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Email param received inside the method is null or empty");
			LOGGER.error("Request cannot be processed, returning null to the service");
			return null;
		}
		LOGGER.debug("The received params are not null or empty");
		LOGGER.debug("Processing request to populate the audit table for email: "+email);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>auditTable = new HashMap<>();
		LOGGER.debug("Hashmap of objects created, Inserting parameters into hashmap");
		auditTable.put("email",email);
		LOGGER.debug("Params successfully inserted into hash map");
		try 
		{
			LOGGER.debug("In try block to populate the audit table for email: "+email);
			LOGGER.debug("Executing query to get the details of the audit table corresponding to email of the logged in user: "+email);
			return getJdbcTemplate().query(departmentHeadHomeConfig.getPopulateAuditTable(), auditTable, AUDIT_TABLE_MAPPER);
		} catch (Exception e) 
		{
			LOGGER.error("An exception occured while populating the details of the application: "+e);
			LOGGER.error("Returning NULL to the service");
			return null;
		}
		

	}
	
	/**
	 * Row Mapper for audit table 
	 * @author Prateek Kapoor
	 *
	 */
	public static class AuditTableMapper implements RowMapper<DisplayAuditTableDHDTO>
	{
		@Override
		public DisplayAuditTableDHDTO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String applicationId = rs.getString("applicationId");
			String senderName = rs.getString("senderName");
			String subject = rs.getString("subject");
			String priority = rs.getString("priority");
			Date eta = rs.getDate("eta");
			String status = rs.getString("status");
			String documentType = rs.getString("documentType");
			String referenceNumber = rs.getString("referenceNumber");
			String documentRemarks = rs.getString("documentRemarks");
			String documentPath = rs.getString("documentPath");
			String assignedTo = rs.getString("assignedTo");
			String assignedBy = rs.getString("assignedBy");
			
						
			return new DisplayAuditTableDHDTO(applicationId, senderName, subject, priority, eta, status, documentType, referenceNumber,documentRemarks, documentPath, assignedTo, assignedBy);

			
		}
	}


	
}

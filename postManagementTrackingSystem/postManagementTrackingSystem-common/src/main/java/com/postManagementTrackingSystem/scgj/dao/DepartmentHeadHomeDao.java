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
	private static AssignedApplicationsMapper APPLICATIONS_ROW_MAPPER = new AssignedApplicationsMapper(); // For getting assigned, on Hold applications
	private static AuditTableMapper AUDIT_TABLE_MAPPER = new AuditTableMapper();
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
	 * @author Prateek Kapoor
	 * This method gets the names of the department employees using the department name parameter
	 * @param department
	 * @return LIST of GetNameOfDepartmentEmployeesDTO object if successful else returns null
	 */
	public List<GetNameOfDepartmentEmployeesDTO> getNameOfDepartmentEmployees(String department)
	{
		LOGGER.debug("Received department parameter in dao method to get name of department employees");
		LOGGER.debug("Checking if the department parameter is null or empty");
		if(department==null || department.isEmpty())
		{
			LOGGER.error("Department is null or empty in getNameOfDepartmentEmployees method in dao");
			LOGGER.error("Returning null to service");
			return null;
		}
		LOGGER.debug("Department name is not null or empty, Processing the request");
		LOGGER.debug("Request received to get the name of the employees in department with name: "+department);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>departmentParams = new HashMap<>();
		LOGGER.debug("Hashmap created successfully");
		LOGGER.debug("Inserting department name into hashmap");
		departmentParams.put("department", department);
		try 
		{
			
			LOGGER.debug("In try block to get the name of employees in department: "+department);
			LOGGER.debug("Executing query to get the names of employees in department: "+department);
			return getJdbcTemplate().query(departmentHeadHomeConfig.getDepartmentEmployeeNameUsingDepartmentName(), departmentParams, ROW_MAPPER);
			
		} catch (Exception e) 
		{
			
			LOGGER.error("An exception occured while fetching the names of department employees for department: "+department);
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
			String additionalComments = rs.getString("document_remarks");
						
			return new DepartmentHeadNotStartedApplicationDTO(applicationId, senderName, dateReceived, subject, priority,status, documentPath, documentType, additionalComments);

			
		}
	}
	
	/**
	 * This method gets the owner id for the owner name and the department
	 * @param ownerName
	 * @param departmentName
	 * @return 1 if success, null if params are null or empty or an exception occurs
	 */
	public Integer getOwnerIdByOwnerName(String ownerName,String departmentName)
	{
		LOGGER.debug("Request received in DAO to get the id of the owner");
		LOGGER.debug("Checking if the parameters are null or empty");
		if(ownerName==null||ownerName.isEmpty())
		{
			LOGGER.error("The retreived department name is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the Service");
			return null;
		}
		if(departmentName==null||departmentName.isEmpty())
		{
			LOGGER.error("The department is null or empty in dao");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning null to service");
			return null;
		}
		LOGGER.debug("Parameters are not null or empty");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> ownerParams = new HashMap<>();
		LOGGER.debug("Hashmap of objects created successfully, Inserting department and ownerName into hashmap");
		ownerParams.put("ownerName", ownerName);
		ownerParams.put("department", departmentName);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try 
		{
		  LOGGER.debug("In try block to get the id of the owner for the department: "+departmentName);
		  LOGGER.debug("Executing query to get the id of the owner with name: "+ownerName+" and department: "+departmentName);
		  return getJdbcTemplate().queryForObject(departmentHeadHomeConfig.getDocumentOwnerIdForDepartment(), ownerParams, Integer.class);
		} 
		catch (Exception e) 
		{
			LOGGER.error("An exception occured while fetching the id of the document owner with name: "+ownerName+" for the department: "+departmentName);
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL to service");
			return null;	
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
			LOGGER.error("Returning -10 to the calling method");
			throw new Exception(e);
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
	 * Row Mapper for Assigned applications
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
			Date eta = rs.getDate("eta");
			String documentRemarks = rs.getString("documentRemarks");
			String documentPath = rs.getString("documentPath");
			String name = rs.getString("name");
			
						
			return new AssignedApplicationsDTO(applicationId, senderName, dateAssigned, subject, priority, eta, status, documentType, documentRemarks, documentPath,name);

			
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
			String documentRemarks = rs.getString("documentRemarks");
			String documentPath = rs.getString("documentPath");
			String assignedTo = rs.getString("assignedTo");
			String assignedBy = rs.getString("assignedBy");
			
						
			return new DisplayAuditTableDHDTO(applicationId, senderName, subject, priority, eta, status, documentType, documentRemarks, documentPath, assignedTo, assignedBy);

			
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
	 * This method  the status of the application using the document id and owner id to In Action
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
	
}

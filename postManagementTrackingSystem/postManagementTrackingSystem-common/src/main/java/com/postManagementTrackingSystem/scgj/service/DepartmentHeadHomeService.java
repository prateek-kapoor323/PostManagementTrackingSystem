package com.postManagementTrackingSystem.scgj.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.DepartmentHeadHomeDao;
import com.postManagementTrackingSystem.scgj.dao.EditApplicationDataEntryOperatorDao;
import com.postManagementTrackingSystem.scgj.dao.RegisterApplicationDao;
import com.postManagementTrackingSystem.scgj.dto.AssignedApplicationsDTO;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNotStartedApplicationDTO;
import com.postManagementTrackingSystem.scgj.dto.DisplayAuditTableDHDTO;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfDepartmentEmployeesDTO;
import com.postManagementTrackingSystem.scgj.dto.PerformActionsOverApplicationDTO;
import com.postManagementTrackingSystem.scgj.utils.PerformPostActionsUtility;

@Service
public class DepartmentHeadHomeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadHomeService.class);
	@Autowired
	private DepartmentHeadHomeDao departmentHeadHomeDao;
	
	@Autowired
	private EditApplicationDataEntryOperatorDao editApplicationDataEntryOperatorDAO;
	
	@Autowired
	private PerformPostActionsUtility performPostActionsUtility;
	
	/**
	 * This method receives the email of the logged in user and sends request to getDepartmentOfLoggedInUser method to get the name of the logged in user and then gets the names of 
	 * all the employees whose department type is same as that of the logged in user
	 * @param email
	 * @return List of GetNameOfDepartmentEmployeesDTO object if successfull else returns null
	 */
	
	public List<GetNameOfDepartmentEmployeesDTO> getNameOfDepartmentEmployees(String email)
	{
		LOGGER.debug("Request received in getNameOfDepartmentEmployees method of service to get the names of all the employees of a department");
		LOGGER.debug("Checking if the received parameter is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("NULL parameter received from controller");
			LOGGER.error("Returning NULL to controller");
			return null;
		}
		LOGGER.debug("Received parameter is not null or empty, processing further request");
		LOGGER.debug("Sending request to getDepartmentOfLoggedInUser() method to get the name of the department");
		String department = performPostActionsUtility.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Received response from the getDepartmentOfLoggedInUser()");
		LOGGER.debug("Checking if the response is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Could not retreive the department name for the logged in user: "+email);
			LOGGER.error("Request could not be processed further, Returning null to Controller");
			return null;
		}
		LOGGER.debug("The department name was not null or empty");
		LOGGER.debug("The department retreived for user with email: "+email+" is: "+department);
		LOGGER.debug("Sending request to DAO to get the names of employee for the department: "+department);
		return departmentHeadHomeDao.getNameOfDepartmentEmployees(department);
	}
	
	
	/**
	 * This method takes email as a parameter and calls getDepartmentOfLoggedInUser to get the name of the department and then sends the request to DAO to get the details of department specific applicaitons whose 
	 * status is not started
	 * @param email
	 * @return Collection of DepartmentHeadNotStartedApplicationDTO object else returns null
	 */
	public Collection<DepartmentHeadNotStartedApplicationDTO> getNotStartedApplicationDetails(String email)
	{
		LOGGER.debug("Request received in service method to get the application details whose status is not started on the basis of department");
		LOGGER.debug("Received parameter email from controller");
		LOGGER.debug("Checking if the received parameter is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("The received parameter is null or empty");
			LOGGER.error("Request cannot be processed, returning null to the controller");
			return null;
		}
		LOGGER.debug("The parameter received is not null or empty");
		LOGGER.debug("Processing request to get the department name of the logged in user with email: "+email);
		LOGGER.debug("Sending request to getDepartmentOfLoggedInUser to get the department of the logged in user ");
		String departmentName =performPostActionsUtility.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Checking if the retreived department name is null or empty");
		if(departmentName==null||departmentName.isEmpty())
		{
			LOGGER.error("The retreived department name is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the controller");
			return null;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The department name for the logged in user with email: "+email+" is: "+departmentName);
		LOGGER.debug("Sending request to DAO to get the details of the application with status as NOT STARTED");
		LOGGER.debug("Calling getNotStartedApplicationDetails method in DAO and passing departmentName as a parameter");
		return departmentHeadHomeDao.getNotStartedApplicationDetails(departmentName);
				
	}
	

	/**
	 * This method assigns owner, ETA to the applications and sets the status as ASSIGNED for the corresponding application
	 * @param applicationId
	 * @param ownerName
	 * @param eta
	 * @param email 
	 * @return 1 if success, -5 if parameters are empty and -30 if an exception occurs
	 */
	public Integer assignOwner(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,String email) 
	{		
		LOGGER.debug("Request received from controller to assign owner and eta to the application and set status as ASSIGNED");
		
		LOGGER.debug("All the parameters received in service are not null or empty");
		LOGGER.debug("Processing the request to update the owner of the application with application id: "+performActionsOverApplicationDTO.getApplicationId());
		LOGGER.debug("Sending Request to RegisterApplicationDao's getOwnerId method to get the id of the owner with name: "+performActionsOverApplicationDTO.getAssignedTo());
		LOGGER.debug("Sending request to method to get the department of the logged in user");
		String departmentName = performPostActionsUtility.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Checking if the retreived department name is null or empty");
	
		if(departmentName==null||departmentName.isEmpty())
		{
			LOGGER.error("The retreived department name is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the controller");
			return null;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The department name for the logged in user with email: "+email+" is: "+departmentName);
		LOGGER.debug("Sending request to getOwnerIdByOwnerName in DAO to get the id of the document Owner for department: "+departmentName);
		Integer ownerId = performPostActionsUtility.getOwnerIdByOwnerName(performActionsOverApplicationDTO.getAssignedTo(), departmentName);
		LOGGER.debug("Received the ID of the owner for the owner name: "+performActionsOverApplicationDTO.getAssignedTo());
		LOGGER.debug("Checking if the owner id is null or empty");
		
		if(ownerId==null)
		{
			LOGGER.error("The owner id received from registerPostDao's getOwnerId method is null");
			LOGGER.error("Request cannot be processed, Returning -5 to the controller");
			return -5;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The ownerId corresponding to the ownerName: "+performActionsOverApplicationDTO.getAssignedTo()+" is: "+ownerId);
		LOGGER.debug("Sending request to get the document id corresponding to the application with application id: "+performActionsOverApplicationDTO.getApplicationId());
		LOGGER.debug("Sending request to EditApplicationDataEntryOperatorDAO's getDocumentIdByApplicationId method to get the document id for application id: "+performActionsOverApplicationDTO.getApplicationId());
		Integer documentId = editApplicationDataEntryOperatorDAO.getDocumentIdByApplicaitonId(performActionsOverApplicationDTO.getApplicationId());
		LOGGER.debug("Checking if the documentId retreived for application id: "+performActionsOverApplicationDTO.getApplicationId()+" is null or empty");
		if(documentId==null)
		{
			LOGGER.error("The document id retreived is null");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to the controller");
			return -5;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The document id corresponding to the application id: "+performActionsOverApplicationDTO.getApplicationId()+" is : "+documentId);
		LOGGER.debug("Processing request to assign owner to application and set status as ASSIGNED for applicationId: "+performActionsOverApplicationDTO.getApplicationId());
		LOGGER.debug("Sending request to DAO to assign owner, eta, satus of the application");
		try
		{
			LOGGER.debug("In try block to assign owner to the application and set status to assigned");
			return departmentHeadHomeDao.assignOwner(performActionsOverApplicationDTO,ownerId,documentId,email);
		} 
		catch (Exception e) {
		
			LOGGER.error("An exception occured in DAO while assigning ower to the applciation with application id: "+performActionsOverApplicationDTO.getApplicationId());
			LOGGER.error("Request cannot be processed, returning -30");
			return -30;
		}

	}
	
	/**
	 *This method returns the application details whose status is assigned and owner is the logged in user
	 * @param email
	 * @return AssignedApplicationsDTO object if success , Else returns null
	 */
	public Collection<AssignedApplicationsDTO> getAssignedApplications(String email)
	{
		LOGGER.debug("Request received in service to get the details of applications with status as Assigned for logged in user");
		LOGGER.debug("Checking if the params received are null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("The received param - email are null or empty");
			LOGGER.error("Request cannot be processed further");
			LOGGER.error("Returning null to the controller");
			return null;
		}
		LOGGER.debug("Parameter received is not null or empty");
		LOGGER.debug("Processing request to get the details of assigned applications to logged in user using email: "+email);
		LOGGER.debug("Sending request to dao and returning response");
		return performPostActionsUtility.getAssignedApplications(email);
	}
	
	/**
	 *This method is used to populate the audit table corresponding to the user who is logged in to the system
	 * @param email
	 * @return Collection of AssignedApplicationsDTO object else return null
	 */
	public Collection<DisplayAuditTableDHDTO> populateAuditTable(String email)
	{
		LOGGER.debug("Request received from controller to populate the audit table for the logged in user");
		LOGGER.debug("Checking if the received parameter is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Email param received inside the method is null or empty");
			LOGGER.error("Request cannot be processed, returning null to the controller");
			return null;
		}
		LOGGER.debug("The received params are not null or empty");
		LOGGER.debug("Processing request to populate the audit table for email: "+email);
		return departmentHeadHomeDao.populateAuditTable(email);
	}
	
	/**
	 * This method gets the details of the applications whose status is IN ACTION for the logged in user
	 * @param email
	 * @return Collection<AssignedApplicationsDTO>
	 */
	public Collection<AssignedApplicationsDTO> getInActionApplications(String email)
	{
		LOGGER.debug("Request received from controller to get the application with status IN ACTION for the logged in user");
		LOGGER.debug("Checking if the params received are null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("The received param - email are null or empty");
			LOGGER.error("Request cannot be processed further");
			LOGGER.error("Returning null to the controller");
			return null;
		}
		LOGGER.debug("Parameter received is not null or empty");
		LOGGER.debug("Processing request to get the details of application with status IN ACTION for logged in user with email: "+email);
		return performPostActionsUtility.getInActionApplications(email);
		
	}
	
	/**
	 * This method gets the details of the applications whose status is ON HOLD for the logged in user
	 * @param email
	 * @return Collection<AssignedApplicationsDTO>
	 */
	public Collection<AssignedApplicationsDTO> getOnHoldApplications(String email)
	{
		LOGGER.debug("Request received from controller to get the application with status ON HOLD for the logged in user");
		LOGGER.debug("Checking if the params received are null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("The received param - email are null or empty");
			LOGGER.error("Request cannot be processed further");
			LOGGER.error("Returning null to the controller");
			return null;
		}
		LOGGER.debug("Parameter received is not null or empty");
		LOGGER.debug("Processing request to get the details of application with status ON HOLD for logged in user with email: "+email);
		return performPostActionsUtility.getOnHoldApplications(email);
		
	}
	
	/**
	 * This method checks for the status that has to be updated and then updates the status of the application and updates the audit table
	 * @param performActionsOverApplicationDTO
	 * @param email
	 * @return 1 if success, -5 if parameters received are null, -10 if exception occurs 
	 */
	public Integer updateApplicationStatus(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,String email)
	{
		LOGGER.debug("Request received from controller to update the status of application for the logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(performActionsOverApplicationDTO.getUpdatedStatus()==null)
		{
			LOGGER.error("The status to be updated is null");
			LOGGER.error("Request cannot be processed, returning -5 to the Controller");
			return -5;
		}
		else if(performActionsOverApplicationDTO.getApplicationId()==null||performActionsOverApplicationDTO.getApplicationId().isEmpty())
		{
			LOGGER.error("The application id is null or empty");
			LOGGER.error("Request cannot be processed, returning -5 to the Controller");
			return -5;
		}
		else if(email==null||email.isEmpty())
		{
			LOGGER.error("The email is empty or null");
			LOGGER.error("Request cannot be processed, returning -5 to the controller");
			return -5;
		}
		LOGGER.debug("Parameters are not null or empty");
		LOGGER.debug("Sending request to the get department of the logged in user");
		String department = performPostActionsUtility.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Checking if the value of department received from the method is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("The retreived department name is null or empty");
			LOGGER.error("Request cannot be processed, Returning -5 to the controller");
			return -5;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The department name for the logged in user with email: "+email+" is: "+department);
		LOGGER.debug("Sending request to getOwnerIdByOwnerName in DAO to get the id of the document Owner for department: "+department);
		LOGGER.debug("Sending request to get Owner ID by department name and owner name to get the ownerId of the logged in user with email: "+email);
		Integer ownerId = performPostActionsUtility.getOwnerIdByOwnerName(performActionsOverApplicationDTO.getAssignedTo(), department);
		LOGGER.debug("Checking if the parameter received from the getOwnerByOwnerName method is null or empty");
		if(ownerId==null)
		{
			LOGGER.error("The retreived parameter ownerId is null or empty");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to controller");
			return -5;
		}
		LOGGER.debug("The retreived ownerId for logged in user: "+email+" is: "+ownerId);
		LOGGER.error("Sending request to get id by application id method to get the id of the application id:  "+performActionsOverApplicationDTO.getAssignedTo());
		Integer documentId = editApplicationDataEntryOperatorDAO.getDocumentIdByApplicaitonId(performActionsOverApplicationDTO.getApplicationId());
		LOGGER.debug("Checking if the documentId retreived for application id: "+performActionsOverApplicationDTO.getApplicationId()+" is null or empty");
		if(documentId==null)
		{
			LOGGER.error("The document id retreived is null");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to the controller");
			return -5;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The document id corresponding to the application id: "+performActionsOverApplicationDTO.getApplicationId()+" is : "+documentId);
		LOGGER.debug("Checking the status of the application that has to be updated for the application with application id: "+performActionsOverApplicationDTO.getApplicationId());
		if(performActionsOverApplicationDTO.getUpdatedStatus().equalsIgnoreCase("In Action"))
		{
			LOGGER.debug("The application has to be updated to status: "+performActionsOverApplicationDTO.getUpdatedStatus());
			LOGGER.debug("Sending request to updateApplicationStatusToInAction method in dao");
			try 
			{
				LOGGER.debug("In try block to execute query for updating application status to In Action");
				return performPostActionsUtility.updateApplicationStatusToInAction(performActionsOverApplicationDTO, ownerId, documentId, email);
			}
			catch (Exception e)
			{
				LOGGER.error("An exception occured in DAO: "+e);
				LOGGER.error("Database transactions for audit table and doc_status table are rolled back");
				LOGGER.error("Returning -10 to controller");
				return -10;
			}
			
		}
		else if(performActionsOverApplicationDTO.getUpdatedStatus().equalsIgnoreCase("On Hold"))
		{
			LOGGER.debug("The application has to be updated to status: "+performActionsOverApplicationDTO.getUpdatedStatus());
			LOGGER.debug("Sending request to updateApplicationStatusToOnHold method in dao");
			try
			{
				LOGGER.debug("In try block to execute query for updating application status to On Hold");
				return performPostActionsUtility.updateApplicationStatusToOnHold(performActionsOverApplicationDTO, ownerId, documentId,email);
			} 
			catch (Exception e)
			{
				LOGGER.error("An exception occured in DAO: "+e);
				LOGGER.error("Database transactions for audit table and doc_status table are rolled back");
				LOGGER.error("Returning -10 to controller");
				return -10;
			}
		}
		else if(performActionsOverApplicationDTO.getUpdatedStatus().equalsIgnoreCase("Closed"))
		{
			LOGGER.debug("The status of application to be updated is CLOSED");
			LOGGER.debug("The application has to be updated to status: "+performActionsOverApplicationDTO.getUpdatedStatus());
			LOGGER.debug("Sending request to updateApplicationStatusToClosed method in dao");
			try 
			{
				LOGGER.debug("In try block to execute query for updating application status to Delayed");
				return performPostActionsUtility.updateApplicationStatusToClosed(performActionsOverApplicationDTO, ownerId, documentId,email);
			}
			catch (Exception e)
			{
				LOGGER.error("An exception occured in DAO: "+e);
				LOGGER.error("Database transactions for audit table and doc_status table are rolled back");
				LOGGER.error("Returning -10 to controller");
				return -10;
			}

		}
		else
		{
			LOGGER.error("The status to be updated does not match the status in database tables");
			LOGGER.error("Returning -10");
			return -10;
			
		}

	}
}

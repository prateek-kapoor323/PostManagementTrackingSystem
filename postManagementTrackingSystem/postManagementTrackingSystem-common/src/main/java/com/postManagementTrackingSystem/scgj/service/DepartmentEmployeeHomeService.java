package com.postManagementTrackingSystem.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.EditApplicationDataEntryOperatorDao;
import com.postManagementTrackingSystem.scgj.dto.AssignedApplicationsDTO;
import com.postManagementTrackingSystem.scgj.dto.PerformActionsOverApplicationDTO;
import com.postManagementTrackingSystem.scgj.utils.PerformPostActionsUtility;

@Service
public class DepartmentEmployeeHomeService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentEmployeeHomeService.class);
	
	@Autowired
	private PerformPostActionsUtility performPostActionsUtility;
	
	@Autowired
	private EditApplicationDataEntryOperatorDao editApplicationDataEntryOperatorDAO;
	
	/**
	 *This method returns the application details whose status is assigned and owner is the logged in user
	 * @param email
	 * @return AssignedApplicationsDTO object if success , Else returns null
	 */
	public Collection<AssignedApplicationsDTO> getAssignedApplications(String email)
	{
		LOGGER.debug("Request received from controller to get the details of assigned applications to the logged in user");
		LOGGER.debug("Checking if the received parameter is null or empty");
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
	 * This method gets the details of the applications whose status is Delayed for the logged in user
	 * @param email
	 * @return Collection<AssignedApplicationsDTO>
	 */
	public Collection<AssignedApplicationsDTO> getDelayedApplications(String email)
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
		return performPostActionsUtility.getDelayedApplications(email);
		
	}


	/**
	 * This method checks for the status that has to be updated and then updates the status of the application and updates the audit table
	 * @param performActionsOverApplicationDTO
	 * @param email
	 * @return 1 if success, -5 if parameters received are null, -10 if an exception occurs
	 */
	public Integer updateApplicationStatusDE(PerformActionsOverApplicationDTO performActionsOverApplicationDTO,String email)
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

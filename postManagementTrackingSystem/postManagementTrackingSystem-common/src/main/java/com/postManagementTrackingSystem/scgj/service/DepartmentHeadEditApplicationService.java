package com.postManagementTrackingSystem.scgj.service;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.DepartmentHeadEditApplicationDao;
import com.postManagementTrackingSystem.scgj.dao.DepartmentHeadHomeDao;
import com.postManagementTrackingSystem.scgj.dao.EditApplicationDataEntryOperatorDao;
import com.postManagementTrackingSystem.scgj.dto.ReceiveEditApplicationParamsDHDto;
import com.postManagementTrackingSystem.scgj.dto.ShowEditApplicationDetailsDepartmentHeadDto;

@Service
public class DepartmentHeadEditApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadEditApplicationService.class);
	@Autowired
	private DepartmentHeadEditApplicationDao departmentHeadEditApplicationDao;
	@Autowired
	private DepartmentHeadHomeService departmentHeadHomeService;
	
	@Autowired
	private DepartmentHeadHomeDao departmentHeadHomeDao;
	
	@Autowired
	private EditApplicationDataEntryOperatorDao editApplicationDataEntryOperatorDAO;
	
	
	public Collection<ShowEditApplicationDetailsDepartmentHeadDto> showEditApplicationDetails(String applicationId,String email)
	{
		LOGGER.debug("Request received from controller to get the application details for application id");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("The application received in dao is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the service");
			return null;
		}
		else if(email==null||email.isEmpty())
		{
			LOGGER.error("The email received is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the controller");
			return null;
		}
		LOGGER.debug("Received parameters are not null or empty");
		LOGGER.debug("Processing request to get the application details for application id: "+applicationId);
		LOGGER.debug("Sending request to department head home service get department name method to get the name of the department for the logged in user with email: "+email);
		String department = departmentHeadHomeService.getDepartmentOfLoggedInUser(email);
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
		LOGGER.debug("Sending request to DAO to get the application details for application id: "+applicationId+" and department: "+department);
		return departmentHeadEditApplicationDao.showEditApplicationDetails(applicationId, department);

	}
	
	/**
	 * This method updates the owner of the application and if any exception occurs then returns -30
	 * @param receiveEditApplicationParamsDHDto
	 * @param email
	 * @return if success- Number of rows affected . Else - returns -30 as exception, -5 if any parameter is null
	 */
	public Integer updateApplicationOwner(ReceiveEditApplicationParamsDHDto receiveEditApplicationParamsDHDto,String email)
	{
		LOGGER.debug("Request received from controller to update the owner of the application with application id: "+receiveEditApplicationParamsDHDto.getApplicationId());
		LOGGER.debug("Processing request and sending request to getDocumentId by application id to get the document id for application");
		Integer documentId = editApplicationDataEntryOperatorDAO.getDocumentIdByApplicaitonId(receiveEditApplicationParamsDHDto.getApplicationId());
		LOGGER.debug("Checking if the document id receievd is null");
		if(documentId==null)
		{
			LOGGER.error("The document id retreived is null");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to the controller");
			return -5;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The document id corresponding to the application id: "+receiveEditApplicationParamsDHDto.getApplicationId()+" is: "+documentId);
		LOGGER.debug("Sending request to method to get the method to get the department of the logged in user");
		String department = departmentHeadHomeService.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Received response from the getDepartmentOfLoggedInUser()");
		LOGGER.debug("Checking if the response is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Could not retreive the department name for the logged in user: "+email);
			LOGGER.error("Request could not be processed further, Returning -5 to Controller");
			return -5;
		}
		LOGGER.debug("The department name was not null or empty");
		LOGGER.debug("The department retreived for user with email: "+email+" is: "+department);
		LOGGER.debug("Sending request to method to get the id of the owner with department: "+department);
		Integer ownerId = departmentHeadHomeDao.getOwnerIdByOwnerName(receiveEditApplicationParamsDHDto.getUpdatedOwner(), department);
		LOGGER.debug("Checking if the parameter received from the getOwnerByOwnerName method is null or empty");
		if(ownerId==null)
		{
			LOGGER.error("The retreived parameter ownerId is null or empty");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -10 to controller");
			return -5;
		}
		LOGGER.debug("The retreived ownerId for logged in user: "+email+" is: "+ownerId);
		LOGGER.debug("Sending request to dao to update the document id: "+documentId+" with a new owner with owner id: "+ownerId);
		try {
			LOGGER.debug("Sending request to method to update the application owner ");
			return departmentHeadEditApplicationDao.updateApplicationOwner(receiveEditApplicationParamsDHDto, documentId, ownerId, email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("An exception occured while updating the owner of the application: "+e);
			LOGGER.error("All db transactions related to update owner rolled back successfully");
			LOGGER.error("Returning -30 to controller");
			return -30;
			
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
	 * @return number of rows affected, -5 if the parameter received is null, -25 if any exception occurs
	 * 
	 */
	public Integer updateApplicationEta(ReceiveEditApplicationParamsDHDto receiveEditApplicationParamsDHDto,String email)
	{
		LOGGER.debug("Request received from controller to update the ETA of the application");
		LOGGER.debug("Checking if the parameters received are null or empty");
		if(receiveEditApplicationParamsDHDto.getApplicationId()==null||receiveEditApplicationParamsDHDto.getApplicationId().isEmpty())
		{
			LOGGER.error("The application id received in service is null or empty");
			LOGGER.error("Request cannot be processed, returning -5 to controller");
			return -5;
		}
		else if(receiveEditApplicationParamsDHDto.getEta()==null)
		{
			LOGGER.error("The eta is null in service");
			LOGGER.error("Request cannot be processed, returning -5 to controller");
			return -5;
		}
		LOGGER.debug("Sending request to getDocumentId by application id to get the documet id corresponding to the application id: "+receiveEditApplicationParamsDHDto.getApplicationId());
		Integer documentId = editApplicationDataEntryOperatorDAO.getDocumentIdByApplicaitonId(receiveEditApplicationParamsDHDto.getApplicationId());
		LOGGER.debug("Checking if the document id receievd is null");
		if(documentId==null)
		{
			LOGGER.error("The document id retreived is null");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -5 to the controller");
			return -5;
		}
		LOGGER.debug("The retreived parameter is not null");
		LOGGER.debug("The document id corresponding to the application id: "+receiveEditApplicationParamsDHDto.getApplicationId()+" is: "+documentId);
		LOGGER.debug("Sending request to method to get the method to get the department of the logged in user");
		String department = departmentHeadHomeService.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Received response from the getDepartmentOfLoggedInUser()");
		LOGGER.debug("Checking if the response is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Could not retreive the department name for the logged in user: "+email);
			LOGGER.error("Request could not be processed further, Returning -5 to Controller");
			return -5;
		}
		LOGGER.debug("The department name was not null or empty");
		LOGGER.debug("The department retreived for user with email: "+email+" is: "+department);
		LOGGER.debug("Sending request to method to get the id of the owner with department: "+department);
		Integer ownerId = departmentHeadHomeDao.getOwnerIdByOwnerName(receiveEditApplicationParamsDHDto.getUpdatedOwner(), department);
		LOGGER.debug("Checking if the parameter received from the getOwnerByOwnerName method is null or empty");
		if(ownerId==null)
		{
			LOGGER.error("The retreived parameter ownerId is null or empty");
			LOGGER.error("Request cannot be processed");
			LOGGER.error("Returning -10 to controller");
			return -5;
		}
		LOGGER.debug("The retreived ownerId for logged in user: "+email+" is: "+ownerId);
		LOGGER.debug("Sending request to dao to update the eta of the application with application id: "+receiveEditApplicationParamsDHDto.getApplicationId());
		try
		{
			LOGGER.debug("Sending request to dao to update the eta of the application with application id: "+receiveEditApplicationParamsDHDto.getApplicationId());
			return departmentHeadEditApplicationDao.updateApplicationEta(receiveEditApplicationParamsDHDto, ownerId, documentId, email);
		} 
		catch (Exception e) 
		{
			LOGGER.error("An exception occured updating the ETA of the application: "+e);
			LOGGER.error("All the database transactions related to the process of updating the eta rolled back");
			LOGGER.error("Returning -25 to the controller");
			return -25;
		}
		
	}
}

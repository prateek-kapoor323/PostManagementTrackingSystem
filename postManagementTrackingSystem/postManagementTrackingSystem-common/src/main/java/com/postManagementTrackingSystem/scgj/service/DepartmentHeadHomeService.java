package com.postManagementTrackingSystem.scgj.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.DepartmentHeadHomeDao;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNotStartedApplicationDTO;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfDepartmentEmployeesDTO;

@Service
public class DepartmentHeadHomeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadHomeService.class);
	@Autowired
	private DepartmentHeadHomeDao departmentHeadHomeDao;
	
	
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
		String department = getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Received response from the getDepartmentOfLoggedInUser()");
		LOGGER.debug("Checking if the response is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Could not retreive the department name for the logged in user: "+email);
			LOGGER.error("Request could not be processed further, Returning null to front end");
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
		String departmentName = getDepartmentOfLoggedInUser(email);
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
	 * This method receives the email of the logged in user and gets the name of the department corresponding to that email
	 * @param email
	 * @return
	 */
	public String getDepartmentOfLoggedInUser(String email)
	{
		LOGGER.debug("Checking if the email is null or empty");
		if(email==null||email.isEmpty())
		{
			LOGGER.error("Email of the logged in user received null or empty in service method");
			LOGGER.error("Returning NULL");
			return null;
		}
		LOGGER.debug("Request received in service to get the department of the logged in user with email: "+email);
		LOGGER.debug("Calling method in dao to get the department name of the logged in user");
		return departmentHeadHomeDao.getDepartmentOfLoggedInUser(email);
	
	}
	
	
}

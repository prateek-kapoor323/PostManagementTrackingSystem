package com.postManagementTrackingSystem.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.DepartmentEmployeeSearchDao;
import com.postManagementTrackingSystem.scgj.dto.ApplicationSearchResultsDto;
import com.postManagementTrackingSystem.scgj.utils.PerformPostActionsUtility;

@Service
public class DepartmentEmployeeSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentEmployeeSearchService.class);
	
	@Autowired
	private PerformPostActionsUtility performPostActionsUtility;
	@Autowired
	private DepartmentEmployeeSearchDao departmentEmployeeSearchDao;
	
	/**
	 * This method gets the department of the user by using the email and then sends the request to DAO to get the application details on the basis of application id
	 * @param applicationId
	 * @param email
	 * @return Collection<ApplicationSearchResultsDto> - if success, else - returns null
	 * 
	 */
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByApplicationIdDE(String applicationId, String email)
	{
		LOGGER.debug("Received request from controller to fetch the details of applicaiton using application id for the department of the logged in user");
		LOGGER.debug("Checking if the parameters are null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("Application id null received null or empty in service");
			LOGGER.error("request cannot be procesed, returning null to controller");
			return null;
		}
		if(email==null || email.isEmpty())
		{
			LOGGER.error("Email received from controller is null or empty");
			LOGGER.error("Request cannot be processed, returning null to controller");
			return null;
		}
		
		LOGGER.debug("Received parameters are not null or empty");
		LOGGER.debug("Processing request to get the details of the applications for applicationId: "+applicationId);
		LOGGER.debug("Sending request to service method of DepartmentHeadHomeService to get the department of the logged in user");
		String department = performPostActionsUtility.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Received the department of the logged in user");
		LOGGER.debug("Checking if the department is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Department received is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the controller");
			return null;
		}
		LOGGER.debug("Department name is not empty");
		LOGGER.debug("Department name for logged in user: "+email+" is: "+department);
		LOGGER.debug("Sending request to DAO to get the details of application with application id: "+applicationId+" for department: "+department);
		return departmentEmployeeSearchDao.getApplicationDetailsByApplicationId(applicationId, department,email);
		
	}
	
	
	
	/**
	 * This method returns the list of applications on the basis of department of logged in user and the name of the status of the application
	 * @param status
	 * @param department
	 * @return Collection<ApplicationSearchResultsDto> - if success, else - null
	 */
	
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByStatusDE(String status, String email)
	{
		LOGGER.debug("Received request from controller to fetch the details of applicaiton using status and the department of the logged in user");
		LOGGER.debug("Checking if the parameters are null or empty");
		if(status==null||status.isEmpty())
		{
			LOGGER.error("status is null received null or empty in service");
			LOGGER.error("request cannot be procesed, returning null to controller");
			return null;
		}
		if(email==null || email.isEmpty())
		{
			LOGGER.error("Email received from controller is null or empty");
			LOGGER.error("Request cannot be processed, returning null to controller");
			return null;
		}
		
		LOGGER.debug("Received parameters are not null or empty");
		LOGGER.debug("Processing request to get the details of the applications for status: "+status);
		LOGGER.debug("Sending request to service method of DepartmentHeadHomeService to get the department of the logged in user");
		String department = performPostActionsUtility.getDepartmentOfLoggedInUser(email);
		LOGGER.debug("Received the department of the logged in user");
		LOGGER.debug("Checking if the department is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Department received is null or empty");
			LOGGER.error("Request cannot be processed, Returning null to the controller");
			return null;
		}
		LOGGER.debug("Department name is not empty");
		LOGGER.debug("Department name for logged in user: "+email+" is: "+department);
		LOGGER.debug("Sending request to dao to get applications whose status is: "+status);
		return departmentEmployeeSearchDao.getApplicationDetailsByStatus(status, department,email);
	}
}

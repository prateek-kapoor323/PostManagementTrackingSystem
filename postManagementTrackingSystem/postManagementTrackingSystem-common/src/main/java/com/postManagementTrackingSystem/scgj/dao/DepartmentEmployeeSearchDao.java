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

import com.postManagementTrackingSystem.scgj.common.AbstractTransactionalDao;
import com.postManagementTrackingSystem.scgj.config.DepartmentEmployeeSearch;
import com.postManagementTrackingSystem.scgj.dto.ApplicationSearchResultsDto;

@Repository
public class DepartmentEmployeeSearchDao extends AbstractTransactionalDao
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentEmployeeSearchDao.class);
	private static ApplicationSearchResults ROW_MAPPER = new ApplicationSearchResults();
	
	@Autowired
	private DepartmentEmployeeSearch departmentEmployeeSearch;

	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByApplicationId(String applicationId,
			String department,String email) {
		
		LOGGER.debug("Request received from service to get the application details for application id corresponding to the department of logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("Application id received inside DAO in null or empty");
			LOGGER.error("Request to get the application details using application id could not be processed");
			LOGGER.error("Returning null to the service");
			return null;
		}
		else if(email==null || email.isEmpty())
		{
			LOGGER.error("Email received from controller is null or empty");
			LOGGER.error("Request cannot be processed, returning null to controller");
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
		applicationIdParams.put("email", email);
		LOGGER.debug("Department and application id successfully inserted into hashmap");
		try 
		{
			LOGGER.debug("In try block to get the details of applicaiton using application id for the department of the logged in user");
			LOGGER.debug("Executing query to get application details for application id: "+applicationId+" and the department: "+department);
			return getJdbcTemplate().query(departmentEmployeeSearch.getSearchApplicationByApplicationIdDE(), applicationIdParams, ROW_MAPPER);
		} catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching the details of the application using application id: "+applicationId+" for department: "+department);
			LOGGER.error("The exception occured is: " +e);
			LOGGER.error("Returning NULL to the service");
			return null;
		}
	}

	
	
	/**
	 * This method returns the list of applications on the basis of department of logged in user and the status of application
	 * @param status
	 * @param department
	 * @return Collection<ApplicationSearchResultsDto> - if success, else - null
	 */
	
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByStatus(String status, String department,String email) {
		
		LOGGER.debug("Request received from service to get the application details for status corresponding to the department of logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(status==null||status.isEmpty())
		{
			LOGGER.error("status received inside DAO in null or empty");
			LOGGER.error("Request to get the application details using status could not be processed");
			LOGGER.error("Returning null to the service");
			return null;
		}
		else if(email==null || email.isEmpty())
		{
			LOGGER.error("Email received from controller is null or empty");
			LOGGER.error("Request cannot be processed, returning null to controller");
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
		statusParams.put("email", email);
		LOGGER.debug("Parameters inserted successfully into hashmap");
		try 
		{
			LOGGER.debug("In try block to get all the applicaitons for the department of logged in user with status: "+status);
			LOGGER.debug("Executing query to get all the applications for the department of logged in user with status: "+status+" and department: "+department);
			return getJdbcTemplate().query(departmentEmployeeSearch.getSearchApplicationByStatusDE(), statusParams, ROW_MAPPER);
			
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
			
						
			return new ApplicationSearchResultsDto(applicationId, senderName, dateReceived, dateAssigned, subject, ownerName, status, eta, documentPath, documentType);
			
		}
	}
}

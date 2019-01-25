package com.postManagementTrackingSystem.scgj.dao;

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

import com.postManagementTrackingSystem.scgj.common.AbstractTransactionalDao;
import com.postManagementTrackingSystem.scgj.config.DepartmentHeadHomeConfig;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNotStartedApplicationDTO;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfDepartmentEmployeesDTO;

@Repository
public class DepartmentHeadHomeDao extends AbstractTransactionalDao{

	@Autowired
	private DepartmentHeadHomeConfig departmentHeadHomeConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadHomeDao.class);
	
	private static DepartmentEmployeesNameRowMapper ROW_MAPPER = new DepartmentEmployeesNameRowMapper();
	private static ApplicationDetailsMapper APPLICATION_MAPPER = new ApplicationDetailsMapper();
	
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
			String documentPath = rs.getString("documentPath");
			String documentType = rs.getString("documentType");
			String additionalComments = rs.getString("document_remarks");
						
			return new DepartmentHeadNotStartedApplicationDTO(applicationId, senderName, dateReceived, subject, priority, documentPath, documentType, additionalComments);

			
		}
	}
}

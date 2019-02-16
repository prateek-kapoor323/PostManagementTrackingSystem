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
import com.postManagementTrackingSystem.scgj.config.DataEntryOperatorSearchApplicationConfig;
import com.postManagementTrackingSystem.scgj.dto.DataEntryOperatorApplicationSearchResultsDto;

@Repository
public class DataEntryOperatorSearchApplicationDao extends AbstractTransactionalDao{

	private static final Logger LOGGER = LoggerFactory.getLogger(DataEntryOperatorSearchApplicationDao.class);
	
	@Autowired
	private DataEntryOperatorSearchApplicationConfig dataEntryOperatorSearchApplicationConfig;
	
	private static ApplicationDetailsRowMapper ROW_MAPPER = new ApplicationDetailsRowMapper();
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method takes application id as a parameter and returns the records of the application submitted
	 * @param applicationId
	 * @return Collection of DATAENTRYOPERATORAPPLICATIONSEARCHRESULTSDTO
	 */
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByApplicationId(String applicationId)
	{
		LOGGER.debug("Request received from DataEntryOperatorSearchApplicationService class to search application details using application id: "+applicationId);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>applicationIdParam = new HashMap<>();
		LOGGER.debug("Hashmap of object created successfully");
		LOGGER.debug("Inserting application id: "+applicationId);
		applicationIdParam.put("applicationId", applicationId);
		LOGGER.debug("Parameter inserted into the hashmap");
		
		try
		{
			LOGGER.debug("In try block of getApplicationByApplicationId method");
			LOGGER.debug("Executing query to get the details of the application with application-id : "+applicationId);
			return getJdbcTemplate().query(dataEntryOperatorSearchApplicationConfig.getSearchApplicationByApplicationId(),applicationIdParam, ROW_MAPPER);
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while fetching the record of application using application Id: "+e);
			LOGGER.debug("Returning NULL");
			return null;
		}
		
	}
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method is invoked when the user selects the department as a parameter for searching the record of the submitted applications and clicks on the search button
	 * 
	 * @param department
	 * @return Collection of DATAENTRYOPERATORAPPLICATIONSEARCHRESULTSDTO
	 */
	
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByDepartment(String department)
	{
		LOGGER.debug("Request received from service to get application details by department name: "+department);
		LOGGER.debug("Creating Hashmap of objects");
		Map<String,Object>params = new HashMap<>();
		LOGGER.debug("Hashmap created successfully");
		LOGGER.debug("Putting values into the hashmap");
		params.put("department", department);
		LOGGER.debug("Values inserted successfully");
		try {
			
			LOGGER.debug("In try block of getApplicationByDepartment method to get application details using department name: "+department);
			LOGGER.debug("Executing query to get the record of application on the basis of department");
			return getJdbcTemplate().query(dataEntryOperatorSearchApplicationConfig.getSearchApplicationByDepartmentName(), params, ROW_MAPPER);
			
		} catch (Exception e) {
			LOGGER.error("An exception occured while fetching record of the application using department name: "+department);
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL");
			return null;
		}
	}
	
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method takes in 2 parameters - 
	 * 1. Starting Date
	 * 2. Ending Date
	 * The date object is converted into String in the service method and sent to the Dao to get the application information who have the date received between the starting and the ending date
	 * @param startingDate
	 * @param endingDate
	 * @return Collection of DATAENTRYOPERATORAPPLICATIONSEARCHRESULTSDTO
	 */
	
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByDateRange(String startingDate, String endingDate)
	{
		LOGGER.debug("Request received in getApplicationByDateRange to get the application received between: "+startingDate+" to: "+endingDate);
		LOGGER.debug("Creating hashmap of dates");
		Map<String,Object>param = new HashMap<String,Object>();
		LOGGER.debug("Hashmap created successfully");
		LOGGER.debug("Inserting values into hashmap");
		param.put("startingDate", startingDate);
		param.put("endingDate", endingDate);
		LOGGER.debug("Parameters successfully inserted into hashmap");
		try {
			LOGGER.debug("In try block to get application details using date");
			LOGGER.debug("Executing query to get application details using date");
			return getJdbcTemplate().query(dataEntryOperatorSearchApplicationConfig.getSearchApplicationByDateRange(), param, ROW_MAPPER);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			LOGGER.error("An exception occured while fetching application details using date "+e);
			LOGGER.error("Returning NULL");
			return null;
		}
	}
	
	
	
	
	
	
	
	/**
	 * ROW MAPPER for APPLICATION DETAILS
	 * @author Prateek Kapoor
	 *
	 */
	public static class ApplicationDetailsRowMapper implements RowMapper<DataEntryOperatorApplicationSearchResultsDto>
	{
		@Override
		public DataEntryOperatorApplicationSearchResultsDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String applicationId = rs.getString("applicationId");
			String senderName = rs.getString("senderName");
			Date dateReceived = rs.getDate("dateReceived");
			String subject = rs.getString("subject");
			String priority = rs.getString("priority");
			String name = rs.getString("name");//name of scgj poc
			String documentType = rs.getString("documentType");
			String referenceNumber = rs.getString("referenceNumber");
			String status = rs.getString("status");
			String department = rs.getString("department");
			String documentPath = rs.getString("documentPath");
			
			return new DataEntryOperatorApplicationSearchResultsDto(applicationId,senderName,dateReceived,subject,priority,name,documentType,referenceNumber,status,department,documentPath);
			 
			 
			
			
		}
	}

	
}

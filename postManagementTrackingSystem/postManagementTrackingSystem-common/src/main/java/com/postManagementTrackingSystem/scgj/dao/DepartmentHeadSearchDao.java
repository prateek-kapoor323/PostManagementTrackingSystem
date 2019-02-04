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
import com.postManagementTrackingSystem.scgj.config.DepartmentHeadSearchConfig;
import com.postManagementTrackingSystem.scgj.dto.ApplicationSearchResultsDto;

@Repository
public class DepartmentHeadSearchDao extends AbstractTransactionalDao
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHeadSearchDao.class);
	private static ApplicationSearchResults ROW_MAPPER = new ApplicationSearchResults();
	
	@Autowired
	private DepartmentHeadSearchConfig departmentHeadSearchConfig;
	
	/**
	 * This method returns the list of applications on the basis of department of logged in user and the name of the owner of the application
	 * @param status
	 * @param department
	 * @return Collection<ApplicationSearchResultsDto> - if success, else - null
	 */
	public Collection<ApplicationSearchResultsDto> getApplicationDetailsByOwner(String ownerName, String department)
	{
		
		LOGGER.debug("Request received from service to get the application details assigned to owner name: "+ownerName+" corresponding to the department of logged in user");
		LOGGER.debug("Checking if the received parameters are null or empty");
		if(ownerName==null||ownerName.isEmpty())
		{
			LOGGER.error("status received inside DAO in null or empty");
			LOGGER.error("Request to get the application details using Owner Name could not be processed");
			LOGGER.error("Returning null to the service");
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
		LOGGER.debug("Processing request to get the details of the application for the department of logged in user: "+department+" by ownerName: "+ownerName);
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object> ownerParams = new HashMap<>();
		LOGGER.debug("hashmap successfully created, Inserting department and ownerName into hashmap");
		ownerParams.put("ownerName",ownerName);
		ownerParams.put("department", department);
		LOGGER.debug("Parameters successfully inserted into hashmap ");
		try 
		{
			LOGGER.debug("In try block to get all the applicaitons for the department of logged in user with ownerName: "+ownerName);
			LOGGER.debug("Executing query to get all the applications for the department of logged in user with owner: "+ownerName+" and department: "+department);
			return getJdbcTemplate().query(departmentHeadSearchConfig.getApplicationByOwnerName(), ownerParams, ROW_MAPPER);
			
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while fetching the details of the application for department of the logged in user with status: "+ownerName);
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
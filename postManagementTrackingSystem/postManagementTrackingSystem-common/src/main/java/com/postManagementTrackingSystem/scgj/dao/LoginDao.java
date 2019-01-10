package com.postManagementTrackingSystem.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.postManagementTrackingSystem.scgj.common.AbstractTransactionalDao;
import com.postManagementTrackingSystem.scgj.config.LoginConfig;
import com.postManagementTrackingSystem.scgj.dto.GetNameOfLoggedInUserDto;
import com.postManagementTrackingSystem.scgj.dto.SessionManagementDto;

@Repository
public class LoginDao extends AbstractTransactionalDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);
	
	private static final LoginRowMapper login_RowMapper = new LoginRowMapper();
	
	@Autowired
	private LoginConfig loginConfig;
	/**
	 * @Author Prateek Kapoor
	 * @param email
	 * Description - This method receives the email of the user who is trying to login 
	 * and then checks if the email of the user exists in the system or not
	 * @return 0 if the user does not exist and 1 if the user exists in the database
	 */
	public int checkUserExistence(String email) {
		
		LOGGER.debug("Request received from service to check if the user with email: "+email+" exists in the database");
		try
		{
			
			LOGGER.debug("In try block of checkUserExistence method to check the existence of the user");
			LOGGER.debug("Creating hashmap of object");
			Map<String,Object>params = new HashMap<>();
			params.put("email", email);
			LOGGER.debug("Calling JDBC Template to run the SQL Query");
			Integer result = getJdbcTemplate().queryForObject(loginConfig.getCheckUser(),params,Integer.class);
			LOGGER.debug("The result of user existence for email : "+email+" is: " +result);
			return result;
		} 
		catch ( Exception e)
		{
			LOGGER.debug("An exception has occured while checking the existence of user with email: "+email);
			LOGGER.debug("The exception is: "+e);
			return -1;	
		}
		
	}

	/**
	 * @author Prateek Kapoor
	 * @description - This method is called once the email of the user who is trying to login exists in the system
	 *  --> This method gets the following parameters for an existing email address
	 *  1. email
	 *  2. Password
	 *  3. role_type 
	 *  
	 * @param email
	 * @return a DTO containing the following parameters corresponding to the email account - email, password, role_type
	 */
	public SessionManagementDto getValidUserDetails(String email) {
		// TODO Auto-generated method stub
		LOGGER.debug("In getValidUserDetails method to get the details of the user with email: "+email);
		try {
			
			LOGGER.debug("In try block of getValidUserDetails method to get the details of user with email: "+email);
			LOGGER.debug("Creating hashmap of objects");
			Map<String,Object>parameters = new HashMap<>();
			parameters.put("email", email);
			LOGGER.debug("Calling JDBC template to run query for getting the details of a valid user");
			return getJdbcTemplate().queryForObject(loginConfig.getFetchValidUserDetails(),parameters,login_RowMapper);
		} 
		catch (Exception e)
		{
			// TODO: handle exception
			
			LOGGER.error("An exception occured while fetching the details for the valid user");
        	LOGGER.error("In method - getValidUserDetails, while getting Login details for entered email: " +email);
        	LOGGER.error("Exception is: "+e);
        	LOGGER.error("Returning null");
        	return null;
		}
	}
	
	
	public static class LoginRowMapper implements RowMapper<SessionManagementDto>
	{
		public SessionManagementDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String email = rs.getString("email");
			String password = rs.getString("password");
			String role = rs.getString("role_type");
			return new SessionManagementDto(email,password,role);
		}
	}
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method receives email of the logged in user as a parameter
	 * and gets the name of the user in return
	 * @param email
	 * @return NAME of the logged in USER
	 */
	public String getNameOfLoggedInUser(String email)
	{
		LOGGER.debug("Request received from GetNameOfLoggedInUser service to fetch the name of the logged in user with email: "+email);
		LOGGER.debug("Creating hashmap of object");
		Map<String,Object>emailParams = new HashMap<>();
		emailParams.put("email", email);
		LOGGER.debug("Hashmap Created");
		try {
			LOGGER.debug("In try block of get NameOfLoggedInUser to get the name of user with email: " +email);
			LOGGER.debug("Executing query to get the name of the logged in user");
			return getJdbcTemplate().queryForObject(loginConfig.getFetchNameOfLoggedInUser(),emailParams,String.class);
			
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while fetching the name of the logged in user");
			LOGGER.error("The exception is : "+e);
			LOGGER.error("Returning NULL");
			return null;
		}
		
	}

}

package com.postManagementTrackingSystem.scgj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.LoginDao;

@Service
public class GetNameOfLoggedInUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetNameOfLoggedInUserService.class);
	
	@Autowired
	private LoginDao loginDao;
	
	/**
	 * @author Prateek Kapoor
	 * @param email
	 * This method takes email as the param and pass this to LoginDao to fetch the name of the logged in User
	 * @return name of the logged in user
	 */
	public String getNameOfLoggedInUser(String email)
	{
		LOGGER.debug("Sending request to LoginDao to get the name of logged in user with email: "+email);
		return loginDao.getNameOfLoggedInUser(email);
	}
}

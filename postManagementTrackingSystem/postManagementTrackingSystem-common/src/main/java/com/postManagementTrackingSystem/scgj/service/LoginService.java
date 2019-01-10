package com.postManagementTrackingSystem.scgj.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.LoginDao;
import com.postManagementTrackingSystem.scgj.dto.SessionManagementDto;

@Service
public class LoginService implements UserDetailsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private LoginDao loginDao;
	
	
	@Override
	public SessionManagementDto loadUserByUsername(String userEmail) throws UsernameNotFoundException
	{
		LOGGER.debug("In login service to get the user by email");
		
		Collection<SimpleGrantedAuthority>authorities = new ArrayList<SimpleGrantedAuthority>();
		LOGGER.debug("Sending request to DAO to check if the user exists");
		int status = loginDao.checkUserExistence(userEmail);
		
		if(status==0)
		{
			LOGGER.debug("No user with email: "+userEmail+ " could be found in the database");
			authorities.add(new SimpleGrantedAuthority(null));
			LOGGER.debug("Setting the session management Dto to null");
			return new SessionManagementDto(null,null,null);
		}
		
			LOGGER.debug("User with email: "+userEmail+" found in the database");
			LOGGER.debug("Sending request to DAO to get user details");
			return loginDao.getValidUserDetails(userEmail);
			
		}
	}


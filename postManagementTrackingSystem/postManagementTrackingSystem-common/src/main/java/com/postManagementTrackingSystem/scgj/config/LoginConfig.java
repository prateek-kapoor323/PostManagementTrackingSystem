package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="loginSql",locations="classpath:sql/login.yml")
public class LoginConfig {

	private String checkUser;
	private String fetchValidUserDetails;
	private String fetchNameOfLoggedInUser;
	
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getFetchValidUserDetails() {
		return fetchValidUserDetails;
	}
	public void setFetchValidUserDetails(String fetchValidUserDetails) {
		this.fetchValidUserDetails = fetchValidUserDetails;
	}
	public String getFetchNameOfLoggedInUser() {
		return fetchNameOfLoggedInUser;
	}
	public void setFetchNameOfLoggedInUser(String fetchNameOfLoggedInUser) {
		this.fetchNameOfLoggedInUser = fetchNameOfLoggedInUser;
	}
	
	
	
}

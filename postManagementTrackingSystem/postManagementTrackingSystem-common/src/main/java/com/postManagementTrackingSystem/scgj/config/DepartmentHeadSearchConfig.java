package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="departmentHeadSearchYml",locations="classpath:sql/departmentHeadSearch.yml")
public class DepartmentHeadSearchConfig 
{

	private String applicationByApplicationId;
	private String applicationByStatus;
	private String applicationByOwnerName;
	public String getApplicationByApplicationId() {
		return applicationByApplicationId;
	}
	public void setApplicationByApplicationId(String applicationByApplicationId) {
		this.applicationByApplicationId = applicationByApplicationId;
	}
	public String getApplicationByStatus() {
		return applicationByStatus;
	}
	public void setApplicationByStatus(String applicationByStatus) {
		this.applicationByStatus = applicationByStatus;
	}
	public String getApplicationByOwnerName() {
		return applicationByOwnerName;
	}
	public void setApplicationByOwnerName(String applicationByOwnerName) {
		this.applicationByOwnerName = applicationByOwnerName;
	}
	
	
	
	
}

package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="departmentEmployeeSearch",locations="classpath:sql/departmentEmployeeApplicationSearch.yml")
public class DepartmentEmployeeSearch 
{
	private String searchApplicationByApplicationIdDE;
	private String searchApplicationByStatusDE;
	
	public String getSearchApplicationByApplicationIdDE() {
		return searchApplicationByApplicationIdDE;
	}
	public void setSearchApplicationByApplicationIdDE(String searchApplicationByApplicationIdDE) {
		this.searchApplicationByApplicationIdDE = searchApplicationByApplicationIdDE;
	}
	public String getSearchApplicationByStatusDE() {
		return searchApplicationByStatusDE;
	}
	public void setSearchApplicationByStatusDE(String searchApplicationByStatusDE) {
		this.searchApplicationByStatusDE = searchApplicationByStatusDE;
	}
	
	
}

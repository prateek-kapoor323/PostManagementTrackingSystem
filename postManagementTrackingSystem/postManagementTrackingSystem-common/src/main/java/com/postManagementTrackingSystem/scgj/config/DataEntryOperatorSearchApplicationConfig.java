package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="searchApplication",locations="classpath:sql/dataEntryOperatorApplicationSearch.yml")
public class DataEntryOperatorSearchApplicationConfig {

	private String searchApplicationByApplicationId;
	private String searchApplicationByDepartmentName;
	private String searchApplicationByDateRange;
	
	public String getSearchApplicationByApplicationId() {
		return searchApplicationByApplicationId;
	}
	public void setSearchApplicationByApplicationId(String searchApplicationByApplicationId) {
		this.searchApplicationByApplicationId = searchApplicationByApplicationId;
	}
	public String getSearchApplicationByDepartmentName() {
		return searchApplicationByDepartmentName;
	}
	public void setSearchApplicationByDepartmentName(String searchApplicationByDepartmentName) {
		this.searchApplicationByDepartmentName = searchApplicationByDepartmentName;
	}
	public String getSearchApplicationByDateRange() {
		return searchApplicationByDateRange;
	}
	public void setSearchApplicationByDateRange(String searchApplicationByDateRange) {
		this.searchApplicationByDateRange = searchApplicationByDateRange;
	}
	
	
}

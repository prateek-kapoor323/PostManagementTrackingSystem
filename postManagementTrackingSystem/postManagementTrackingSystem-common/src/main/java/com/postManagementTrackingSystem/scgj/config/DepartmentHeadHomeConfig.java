package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="departmentHeadHome",locations="classpath:sql/departmentHeadHome.yml")
public class DepartmentHeadHomeConfig 
{

	private String departmentNameUsingEmail;
	private String departmentEmployeeNameUsingDepartmentName;
	private String notStartedApplications;
	
	public String getDepartmentNameUsingEmail() {
		return departmentNameUsingEmail;
	}
	public void setDepartmentNameUsingEmail(String departmentNameUsingEmail) {
		this.departmentNameUsingEmail = departmentNameUsingEmail;
	}
	public String getDepartmentEmployeeNameUsingDepartmentName() {
		return departmentEmployeeNameUsingDepartmentName;
	}
	public void setDepartmentEmployeeNameUsingDepartmentName(String departmentEmployeeNameUsingDepartmentName) {
		this.departmentEmployeeNameUsingDepartmentName = departmentEmployeeNameUsingDepartmentName;
	}
	public String getNotStartedApplications() {
		return notStartedApplications;
	}
	public void setNotStartedApplications(String notStartedApplications) {
		this.notStartedApplications = notStartedApplications;
	}
	
	
	
	
}

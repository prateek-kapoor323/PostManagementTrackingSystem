package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="registerApplication",locations="classpath:sql/registerApplication.yml")
public class RegisterApplicationConfig {

	private String departmentHeadNamesSql;
	private String totalNumberOfApplications;
	private String submitPostDetails;
	private String documentOwnerId;
	private String submitDocumentStatus;
	
	
	
	
	public String getSubmitDocumentStatus() {
		return submitDocumentStatus;
	}

	public void setSubmitDocumentStatus(String submitDocumentStatus) {
		this.submitDocumentStatus = submitDocumentStatus;
	}

	public String getDocumentOwnerId() {
		return documentOwnerId;
	}

	public void setDocumentOwnerId(String documentOwnerId) {
		this.documentOwnerId = documentOwnerId;
	}

	public String getSubmitPostDetails() {
		return submitPostDetails;
	}

	public void setSubmitPostDetails(String submitPostDetails) {
		this.submitPostDetails = submitPostDetails;
	}

	/**
	 * 
	 * @return departmentHeadNameSql
	 */
	public String getDepartmentHeadNamesSql() {
		return departmentHeadNamesSql;
	}

	/**
	 * 
	 * @param departmentHeadNamesSql
	 */
	public void setDepartmentHeadNamesSql(String departmentHeadNamesSql) {
		this.departmentHeadNamesSql = departmentHeadNamesSql;
	}

	public String getTotalNumberOfApplications() {
		return totalNumberOfApplications;
	}

	public void setTotalNumberOfApplications(String totalNumberOfApplications) {
		this.totalNumberOfApplications = totalNumberOfApplications;
	}
	
	
	
	
	
}

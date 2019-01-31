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
	private String applicationIdById;
	private String insertAuditTableWhileRegister;
	
	
	
	
	public String getInsertAuditTableWhileRegister() {
		return insertAuditTableWhileRegister;
	}
	public void setInsertAuditTableWhileRegister(String insertAuditTableWhileRegister) {
		this.insertAuditTableWhileRegister = insertAuditTableWhileRegister;
	}
	public String getDepartmentHeadNamesSql() {
		return departmentHeadNamesSql;
	}
	public void setDepartmentHeadNamesSql(String departmentHeadNamesSql) {
		this.departmentHeadNamesSql = departmentHeadNamesSql;
	}
	public String getTotalNumberOfApplications() {
		return totalNumberOfApplications;
	}
	public void setTotalNumberOfApplications(String totalNumberOfApplications) {
		this.totalNumberOfApplications = totalNumberOfApplications;
	}
	public String getSubmitPostDetails() {
		return submitPostDetails;
	}
	public void setSubmitPostDetails(String submitPostDetails) {
		this.submitPostDetails = submitPostDetails;
	}
	public String getDocumentOwnerId() {
		return documentOwnerId;
	}
	public void setDocumentOwnerId(String documentOwnerId) {
		this.documentOwnerId = documentOwnerId;
	}
	public String getSubmitDocumentStatus() {
		return submitDocumentStatus;
	}
	public void setSubmitDocumentStatus(String submitDocumentStatus) {
		this.submitDocumentStatus = submitDocumentStatus;
	}
	public String getApplicationIdById() {
		return applicationIdById;
	}
	public void setApplicationIdById(String applicationIdById) {
		this.applicationIdById = applicationIdById;
	}

	
	
	
	
}

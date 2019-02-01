package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="editApplicationDetails",locations="classpath:sql/departmentHeadEdit.yml")
public class DepartmentHeadEditApplicationConfig 
{
	private String showApplicationUsingApplicationId;
	private String updateApplicationOwner;
	private String insertIntoAuditTable;
	private String updateEtaOfApplicationWithStatus;
	private String updateEtaOfApplicationWithoutStatus;
	
	
	
	
	public String getUpdateEtaOfApplicationWithStatus() {
		return updateEtaOfApplicationWithStatus;
	}

	public void setUpdateEtaOfApplicationWithStatus(String updateEtaOfApplicationWithStatus) {
		this.updateEtaOfApplicationWithStatus = updateEtaOfApplicationWithStatus;
	}

	public String getUpdateEtaOfApplicationWithoutStatus() {
		return updateEtaOfApplicationWithoutStatus;
	}

	public void setUpdateEtaOfApplicationWithoutStatus(String updateEtaOfApplicationWithoutStatus) {
		this.updateEtaOfApplicationWithoutStatus = updateEtaOfApplicationWithoutStatus;
	}

	public String getShowApplicationUsingApplicationId() {
		return showApplicationUsingApplicationId;
	}

	public void setShowApplicationUsingApplicationId(String showApplicationUsingApplicationId) {
		this.showApplicationUsingApplicationId = showApplicationUsingApplicationId;
	}

	public String getUpdateApplicationOwner() {
		return updateApplicationOwner;
	}

	public void setUpdateApplicationOwner(String updateApplicationOwner) {
		this.updateApplicationOwner = updateApplicationOwner;
	}

	public String getInsertIntoAuditTable() {
		return insertIntoAuditTable;
	}

	public void setInsertIntoAuditTable(String insertIntoAuditTable) {
		this.insertIntoAuditTable = insertIntoAuditTable;
	}
	
	
	
}

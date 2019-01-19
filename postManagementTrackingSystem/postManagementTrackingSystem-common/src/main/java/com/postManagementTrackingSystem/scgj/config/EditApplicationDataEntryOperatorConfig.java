package com.postManagementTrackingSystem.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="editApplication",locations="classpath:sql/editApplications.yml")
public class EditApplicationDataEntryOperatorConfig {

	private String editApplicationByIdSearch;
	private String updateDocumentDetailsTable;
	private String updateDocumentOwner;
	private String idByApplicationId;
	private String applicationIdWithStatusNotStarted;
	private String updateDocumentDetailsTableWithoutFile;
	

	
	public String getUpdateDocumentDetailsTableWithoutFile() {
		return updateDocumentDetailsTableWithoutFile;
	}
	public void setUpdateDocumentDetailsTableWithoutFile(String updateDocumentDetailsTableWithoutFile) {
		this.updateDocumentDetailsTableWithoutFile = updateDocumentDetailsTableWithoutFile;
	}
	public String getApplicationIdWithStatusNotStarted() {
		return applicationIdWithStatusNotStarted;
	}
	public void setApplicationIdWithStatusNotStarted(String applicationIdWithStatusNotStarted) {
		this.applicationIdWithStatusNotStarted = applicationIdWithStatusNotStarted;
	}
	public String getIdByApplicationId() {
		return idByApplicationId;
	}
	public void setIdByApplicationId(String idByApplicationId) {
		this.idByApplicationId = idByApplicationId;
	}
	public String getEditApplicationByIdSearch() {
		return editApplicationByIdSearch;
	}
	public void setEditApplicationByIdSearch(String editApplicationByIdSearch) {
		this.editApplicationByIdSearch = editApplicationByIdSearch;
	}
	public String getUpdateDocumentDetailsTable() {
		return updateDocumentDetailsTable;
	}
	public void setUpdateDocumentDetailsTable(String updateDocumentDetailsTable) {
		this.updateDocumentDetailsTable = updateDocumentDetailsTable;
	}
	public String getUpdateDocumentOwner() {
		return updateDocumentOwner;
	}
	public void setUpdateDocumentOwner(String updateDocumentOwner) {
		this.updateDocumentOwner = updateDocumentOwner;
	}
	
	

	
	
}

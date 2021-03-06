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
	private String assignApplicationOwner;
	private String documentOwnerIdForDepartment;
	private String assignedApplications;
	private String insertAuditTableDetails;
	private String populateAuditTable;
	private String inActionApplications;
	private String onHoldApplications;
	private String updateStatusToOnHold;
	private String updateStatusToInAction;
	private String updateStatusToComplete;
	private String checkStartDateBeforeUpdate;
	private String updateStatusToCompleteWithoutStartDate;
	private String delayedApplications;
	private String inReviewApplications;
	private String updateStatusToInReview;
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
	public String getAssignApplicationOwner() {
		return assignApplicationOwner;
	}
	public void setAssignApplicationOwner(String assignApplicationOwner) {
		this.assignApplicationOwner = assignApplicationOwner;
	}
	public String getDocumentOwnerIdForDepartment() {
		return documentOwnerIdForDepartment;
	}
	public void setDocumentOwnerIdForDepartment(String documentOwnerIdForDepartment) {
		this.documentOwnerIdForDepartment = documentOwnerIdForDepartment;
	}
	public String getAssignedApplications() {
		return assignedApplications;
	}
	public void setAssignedApplications(String assignedApplications) {
		this.assignedApplications = assignedApplications;
	}
	public String getInsertAuditTableDetails() {
		return insertAuditTableDetails;
	}
	public void setInsertAuditTableDetails(String insertAuditTableDetails) {
		this.insertAuditTableDetails = insertAuditTableDetails;
	}
	public String getPopulateAuditTable() {
		return populateAuditTable;
	}
	public void setPopulateAuditTable(String populateAuditTable) {
		this.populateAuditTable = populateAuditTable;
	}
	public String getInActionApplications() {
		return inActionApplications;
	}
	public void setInActionApplications(String inActionApplications) {
		this.inActionApplications = inActionApplications;
	}
	public String getOnHoldApplications() {
		return onHoldApplications;
	}
	public void setOnHoldApplications(String onHoldApplications) {
		this.onHoldApplications = onHoldApplications;
	}
	public String getUpdateStatusToOnHold() {
		return updateStatusToOnHold;
	}
	public void setUpdateStatusToOnHold(String updateStatusToOnHold) {
		this.updateStatusToOnHold = updateStatusToOnHold;
	}
	public String getUpdateStatusToInAction() {
		return updateStatusToInAction;
	}
	public void setUpdateStatusToInAction(String updateStatusToInAction) {
		this.updateStatusToInAction = updateStatusToInAction;
	}
	public String getUpdateStatusToComplete() {
		return updateStatusToComplete;
	}
	public void setUpdateStatusToComplete(String updateStatusToComplete) {
		this.updateStatusToComplete = updateStatusToComplete;
	}
	public String getCheckStartDateBeforeUpdate() {
		return checkStartDateBeforeUpdate;
	}
	public void setCheckStartDateBeforeUpdate(String checkStartDateBeforeUpdate) {
		this.checkStartDateBeforeUpdate = checkStartDateBeforeUpdate;
	}
	public String getUpdateStatusToCompleteWithoutStartDate() {
		return updateStatusToCompleteWithoutStartDate;
	}
	public void setUpdateStatusToCompleteWithoutStartDate(String updateStatusToCompleteWithoutStartDate) {
		this.updateStatusToCompleteWithoutStartDate = updateStatusToCompleteWithoutStartDate;
	}
	public String getDelayedApplications() {
		return delayedApplications;
	}
	public void setDelayedApplications(String delayedApplications) {
		this.delayedApplications = delayedApplications;
	}
	public String getInReviewApplications() {
		return inReviewApplications;
	}
	public void setInReviewApplications(String inReviewApplications) {
		this.inReviewApplications = inReviewApplications;
	}
	public String getUpdateStatusToInReview() {
		return updateStatusToInReview;
	}
	public void setUpdateStatusToInReview(String updateStatusToInReview) {
		this.updateStatusToInReview = updateStatusToInReview;
	}
	
	

	
}

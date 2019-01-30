package com.postManagementTrackingSystem.scgj.dto;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class PerformActionsOverApplicationDTO extends BaseDto
{

	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private String subject;
	private String priority;
	private String assignedTo;
	private String eta;
	private String documentRemarks;
	private String documentPath;
	private String documentType;
	private String updatedStatus;
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getDocumentRemarks() {
		return documentRemarks;
	}
	public void setDocumentRemarks(String documentRemarks) {
		this.documentRemarks = documentRemarks;
	}
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getUpdatedStatus() {
		return updatedStatus;
	}
	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}
	public PerformActionsOverApplicationDTO(String applicationId, String senderName, String subject, String priority,
			String assignedTo, String eta, String documentRemarks, String documentPath, String documentType,
			String updatedStatus) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.subject = subject;
		this.priority = priority;
		this.assignedTo = assignedTo;
		this.eta = eta;
		this.documentRemarks = documentRemarks;
		this.documentPath = documentPath;
		this.documentType = documentType;
		this.updatedStatus = updatedStatus;
	}
	public PerformActionsOverApplicationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

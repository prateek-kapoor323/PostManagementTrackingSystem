package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class DisplayAuditTableDHDTO extends BaseDto {

	
	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private String subject;
	private String priority;
	private Date eta;
	private String status;
	private String documentType;
	private String documentRemarks;
	private String documentPath;
	private String assignedTo;
	private String assignedBy;
	
	
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
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
		this.eta = eta;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}
	public DisplayAuditTableDHDTO(String applicationId, String senderName, String subject, String priority, Date eta,
			String status, String documentType, String documentRemarks, String documentPath, String assignedTo,
			String assignedBy) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.subject = subject;
		this.priority = priority;
		this.eta = eta;
		this.status = status;
		this.documentType = documentType;
		this.documentRemarks = documentRemarks;
		this.documentPath = documentPath;
		this.assignedTo = assignedTo;
		this.assignedBy = assignedBy;
	}
	public DisplayAuditTableDHDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

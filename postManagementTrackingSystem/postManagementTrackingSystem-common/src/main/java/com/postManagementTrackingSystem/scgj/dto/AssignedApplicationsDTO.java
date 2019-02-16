package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class AssignedApplicationsDTO extends BaseDto
{
	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private Date dateAssigned;
	private String subject;
	private String priority;
	private Date eta;
	private String status;
	private String documentType;
	private String referenceNumber;
	private String documentRemarks;
	private String documentPath;
	private String name;
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
	public Date getDateAssigned() {
		return dateAssigned;
	}
	public void setDateAssigned(Date dateAssigned) {
		this.dateAssigned = dateAssigned;
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
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AssignedApplicationsDTO(String applicationId, String senderName, Date dateAssigned, String subject,
			String priority, Date eta, String status, String documentType, String referenceNumber,
			String documentRemarks, String documentPath, String name) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.dateAssigned = dateAssigned;
		this.subject = subject;
		this.priority = priority;
		this.eta = eta;
		this.status = status;
		this.documentType = documentType;
		this.referenceNumber = referenceNumber;
		this.documentRemarks = documentRemarks;
		this.documentPath = documentPath;
		this.name = name;
	}
	public AssignedApplicationsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}

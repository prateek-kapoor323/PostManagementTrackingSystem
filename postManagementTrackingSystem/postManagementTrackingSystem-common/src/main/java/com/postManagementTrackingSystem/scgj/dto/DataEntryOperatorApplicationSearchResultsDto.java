package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class DataEntryOperatorApplicationSearchResultsDto extends BaseDto
{
	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private Date dateReceived;
	private String subject;
	private String priority;
	private String name;
	private String documentType;
	private String referenceNumber;
	private String status;
	private String department;
	private String documentPath;
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
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public DataEntryOperatorApplicationSearchResultsDto(String applicationId, String senderName, Date dateReceived,
			String subject, String priority, String name, String documentType, String referenceNumber, String status,
			String department, String documentPath) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.dateReceived = dateReceived;
		this.subject = subject;
		this.priority = priority;
		this.name = name;
		this.documentType = documentType;
		this.referenceNumber = referenceNumber;
		this.status = status;
		this.department = department;
		this.documentPath = documentPath;
	}
	public DataEntryOperatorApplicationSearchResultsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

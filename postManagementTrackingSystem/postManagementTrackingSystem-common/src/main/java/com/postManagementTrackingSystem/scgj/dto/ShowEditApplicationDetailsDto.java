package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class ShowEditApplicationDetailsDto extends BaseDto
{

	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private String senderPointOfContact;
	private Long senderContact;
	private Date dateReceived;
	private String priority;
	private String referenceNumber;
	private String subject;
	private String status;
	private String documentType;
	private String additionalComment;
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
	public String getSenderPointOfContact() {
		return senderPointOfContact;
	}
	public void setSenderPointOfContact(String senderPointOfContact) {
		this.senderPointOfContact = senderPointOfContact;
	}
	public Long getSenderContact() {
		return senderContact;
	}
	public void setSenderContact(Long senderContact) {
		this.senderContact = senderContact;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public String getAdditionalComment() {
		return additionalComment;
	}
	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}
	public ShowEditApplicationDetailsDto(String applicationId, String senderName, String senderPointOfContact,
			Long senderContact, Date dateReceived, String priority, String referenceNumber, String subject,
			String status, String documentType, String additionalComment) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.senderPointOfContact = senderPointOfContact;
		this.senderContact = senderContact;
		this.dateReceived = dateReceived;
		this.priority = priority;
		this.referenceNumber = referenceNumber;
		this.subject = subject;
		this.status = status;
		this.documentType = documentType;
		this.additionalComment = additionalComment;
	}
	public ShowEditApplicationDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
}

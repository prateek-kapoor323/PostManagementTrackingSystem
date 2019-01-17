package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class ReceiveEditParamsDataEntryOperatorDTO extends BaseDto {

	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private String pointOfContact;
	private Long contactNumber;
	private Date dateReceived;
	private String priority;
	private String subject;
	private String typeOfDocument;
	private String additionalComment;
	private MultipartFile file;
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
	public String getPointOfContact() {
		return pointOfContact;
	}
	public void setPointOfContact(String pointOfContact) {
		this.pointOfContact = pointOfContact;
	}
	public Long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTypeOfDocument() {
		return typeOfDocument;
	}
	public void setTypeOfDocument(String typeOfDocument) {
		this.typeOfDocument = typeOfDocument;
	}
	public String getAdditionalComment() {
		return additionalComment;
	}
	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public ReceiveEditParamsDataEntryOperatorDTO(String applicationId, String senderName, String pointOfContact,
			Long contactNumber, Date dateReceived, String priority, String subject, String typeOfDocument,
			String additionalComment, MultipartFile file) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.pointOfContact = pointOfContact;
		this.contactNumber = contactNumber;
		this.dateReceived = dateReceived;
		this.priority = priority;
		this.subject = subject;
		this.typeOfDocument = typeOfDocument;
		this.additionalComment = additionalComment;
		this.file = file;
	}
	public ReceiveEditParamsDataEntryOperatorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

	
}

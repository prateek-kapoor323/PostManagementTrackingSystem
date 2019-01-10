package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class SubmitPostDetailsDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private String senderName;
	private String pointOfContact;
	private long contactNumber;
	private Date dateReceived;
	private String priority;
	private String subject;
	private String documentType;
	private String ownerName;
	private String additionalComments;
	private MultipartFile file;
	
	
	
	
	
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
	public long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(long contactNumber) {
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
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public SubmitPostDetailsDto(String senderName, String pointOfContact, long contactNumber, Date dateReceived,
			String priority, String subject, String documentType, String ownerName, String additionalComments,
			MultipartFile file) {
		super();
		this.senderName = senderName;
		this.pointOfContact = pointOfContact;
		this.contactNumber = contactNumber;
		this.dateReceived = dateReceived;
		this.priority = priority;
		this.subject = subject;
		this.documentType = documentType;
		this.ownerName = ownerName;
		this.additionalComments = additionalComments;
		this.file = file;
	}
	public SubmitPostDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	 
	
}

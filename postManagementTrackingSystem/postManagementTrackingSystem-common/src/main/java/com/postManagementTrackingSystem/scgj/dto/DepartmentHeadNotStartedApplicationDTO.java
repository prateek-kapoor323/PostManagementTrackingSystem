package com.postManagementTrackingSystem.scgj.dto;

public class DepartmentHeadNotStartedApplicationDTO 
{

	
	private static final long serialVersionUID = 1L;
	
	private String applicationId;
	private String senderName;
	private String dateReceived;
	private String subject;
	private String priority;
	private String documentPath;
	private String documentType;
	private String additionalComments;
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
	public String getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(String dateReceived) {
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
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public DepartmentHeadNotStartedApplicationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DepartmentHeadNotStartedApplicationDTO(String applicationId, String senderName, String dateReceived,
			String subject, String priority, String documentPath, String documentType, String additionalComments) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.dateReceived = dateReceived;
		this.subject = subject;
		this.priority = priority;
		this.documentPath = documentPath;
		this.documentType = documentType;
		this.additionalComments = additionalComments;
	}

	
	
}

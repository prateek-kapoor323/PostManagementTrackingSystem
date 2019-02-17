package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

/**
 * This dto is used to show the details of the application that has to be edited by the department head
 * @author Prateek Kapoor
 *
 */
public class ShowEditApplicationDetailsDepartmentHeadDto extends BaseDto
{
	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private String subject;
	private String priority;
	private Date dateAssigned;
	private Date dateReceived;
	private String documentRemarks;
	private String documentPath;
	private String documentType;
	private String status;
	private Date eta;
	private String ownerName;
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
	public Date getDateAssigned() {
		return dateAssigned;
	}
	public void setDateAssigned(Date dateAssigned) {
		this.dateAssigned = dateAssigned;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
		this.eta = eta;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public ShowEditApplicationDetailsDepartmentHeadDto(String applicationId, String senderName, String subject,
			String priority, Date dateAssigned, Date dateReceived, String documentRemarks, String documentPath,
			String documentType, String status, Date eta, String ownerName) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.subject = subject;
		this.priority = priority;
		this.dateAssigned = dateAssigned;
		this.dateReceived = dateReceived;
		this.documentRemarks = documentRemarks;
		this.documentPath = documentPath;
		this.documentType = documentType;
		this.status = status;
		this.eta = eta;
		this.ownerName = ownerName;
	}
	public ShowEditApplicationDetailsDepartmentHeadDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}

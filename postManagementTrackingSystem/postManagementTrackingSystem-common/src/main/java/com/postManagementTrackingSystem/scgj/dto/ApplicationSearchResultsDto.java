package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import com.postManagementTrackingSystem.scgj.common.BaseDto;
/**
 * This class is used to show results where the DH wants to search application details using App ID, Owner Name and Status
 * @author Prateek Kapoor
 *
 */
public class ApplicationSearchResultsDto extends BaseDto 
{

	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private Date dateReceived;
	private Date dateAssigned;
	private String subject;
	private String ownerName;
	private String status;
	private Date eta;
	private String documentPath;
	private String documentType;
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
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	public ApplicationSearchResultsDto(String applicationId, String senderName, Date dateReceived, Date dateAssigned,
			String subject, String ownerName, String status, Date eta, String documentPath, String documentType) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.dateReceived = dateReceived;
		this.dateAssigned = dateAssigned;
		this.subject = subject;
		this.ownerName = ownerName;
		this.status = status;
		this.eta = eta;
		this.documentPath = documentPath;
		this.documentType = documentType;
	}
	public ApplicationSearchResultsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

package com.postManagementTrackingSystem.scgj.dto;

import java.sql.Date;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class ReceiveEditApplicationParamsDHDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String senderName;
	private String subject;
	private String priority;
	private String updatedOwner;
	private Date eta;
	private String documentRemarks;
	private String documentPath;
	private String updatedStatus;
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
	public String getUpdatedOwner() {
		return updatedOwner;
	}
	public void setUpdatedOwner(String updatedOwner) {
		this.updatedOwner = updatedOwner;
	}
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
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
	public String getUpdatedStatus() {
		return updatedStatus;
	}
	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public ReceiveEditApplicationParamsDHDto(String applicationId, String senderName, String subject, String priority,
			String updatedOwner, Date eta, String documentRemarks, String documentPath, String updatedStatus,
			String documentType) {
		super();
		this.applicationId = applicationId;
		this.senderName = senderName;
		this.subject = subject;
		this.priority = priority;
		this.updatedOwner = updatedOwner;
		this.eta = eta;
		this.documentRemarks = documentRemarks;
		this.documentPath = documentPath;
		this.updatedStatus = updatedStatus;
		this.documentType = documentType;
	}
	public ReceiveEditApplicationParamsDHDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

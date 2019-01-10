package com.postManagementTrackingSystem.scgj.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applicationConstants.properties")
@ConfigurationProperties
public class ReadApplicationConstants {

	private String saveInvoiceAtLocation;
	private String saveApplicationFormAtLocation;
	private String saveCertificatesAtLocation;
	private String saveMiscellaneousFormAtLocation;
	private String orgNameForApplicationId;
	private String applicationIdSeperator;
	private String invoiceIntitials;
	private String applicationFormInitials;
	private String certificateInitials;
	private String MiscellaneousFormInitials;
	private String documentNotStartedStatus;
	private String docUploadErrorMessage;
	
	
	
	public String getDocUploadErrorMessage() {
		return docUploadErrorMessage;
	}
	public void setDocUploadErrorMessage(String docUploadErrorMessage) {
		this.docUploadErrorMessage = docUploadErrorMessage;
	}
	public String getSaveInvoiceAtLocation() {
		return saveInvoiceAtLocation;
	}
	public String getDocumentNotStartedStatus() {
		return documentNotStartedStatus;
	}
	public void setDocumentNotStartedStatus(String documentNotStartedStatus) {
		this.documentNotStartedStatus = documentNotStartedStatus;
	}
	public void setSaveInvoiceAtLocation(String saveInvoiceAtLocation) {
		this.saveInvoiceAtLocation = saveInvoiceAtLocation;
	}
	public String getSaveApplicationFormAtLocation() {
		return saveApplicationFormAtLocation;
	}
	public void setSaveApplicationFormAtLocation(String saveApplicationFormAtLocation) {
		this.saveApplicationFormAtLocation = saveApplicationFormAtLocation;
	}
	public String getSaveCertificatesAtLocation() {
		return saveCertificatesAtLocation;
	}
	public void setSaveCertificatesAtLocation(String saveCertificatesAtLocation) {
		this.saveCertificatesAtLocation = saveCertificatesAtLocation;
	}
	public String getSaveMiscellaneousFormAtLocation() {
		return saveMiscellaneousFormAtLocation;
	}
	public void setSaveMiscellaneousFormAtLocation(String saveMiscellaneousFormAtLocation) {
		this.saveMiscellaneousFormAtLocation = saveMiscellaneousFormAtLocation;
	}
	public String getOrgNameForApplicationId() {
		return orgNameForApplicationId;
	}
	public void setOrgNameForApplicationId(String orgNameForApplicationId) {
		this.orgNameForApplicationId = orgNameForApplicationId;
	}
	public String getApplicationIdSeperator() {
		return applicationIdSeperator;
	}
	public void setApplicationIdSeperator(String applicationIdSeperator) {
		this.applicationIdSeperator = applicationIdSeperator;
	}
	public String getInvoiceIntitials() {
		return invoiceIntitials;
	}
	public void setInvoiceIntitials(String invoiceIntitials) {
		this.invoiceIntitials = invoiceIntitials;
	}
	public String getApplicationFormInitials() {
		return applicationFormInitials;
	}
	public void setApplicationFormInitials(String applicationFormInitials) {
		this.applicationFormInitials = applicationFormInitials;
	}
	public String getCertificateInitials() {
		return certificateInitials;
	}
	public void setCertificateInitials(String certificateInitials) {
		this.certificateInitials = certificateInitials;
	}
	public String getMiscellaneousFormInitials() {
		return MiscellaneousFormInitials;
	}
	public void setMiscellaneousFormInitials(String miscellaneousFormInitials) {
		MiscellaneousFormInitials = miscellaneousFormInitials;
	}
	
	
	
}

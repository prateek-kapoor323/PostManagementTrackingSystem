package com.postManagementTrackingSystem.scgj.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applicationConstants.properties")
@ConfigurationProperties
public class ReadApplicationConstants {

	private String trainingPartnerAffiliationFormInitials;
	private String assessmentAgencyAffiliationFormInitials;
	private String invoiceIntitials;
	private String NSKFDCLetterInitials;
	private String GeneralDarkAndLetterInitials;
	private String saveInvoiceAtLocation;
	private String saveTrainingPartnerAffiliationFormAtLocation;
	private String saveAssessmentAgencyAffiliationFormAtLocation;
	private String saveNSKFDCLettersAtLocation;
	private String saveGeneralDarkAndLetterAtLocation;
	private String orgNameForApplicationId;
	private String applicationIdSeperator;
	private String documentNotStartedStatus;
	private String docUploadErrorMessage;
	private String applicationIdGenerationErrorMessage;
	private String postDetailsSubmissionFailure;
	private String trainingPartnerAffiliation;
	private String assessmentAgencyAffiliation;
	private String invoice;
	private String GeneralDarkAndLetterName;
	private String NSKFDCName;
	public String getTrainingPartnerAffiliationFormInitials() {
		return trainingPartnerAffiliationFormInitials;
	}
	public void setTrainingPartnerAffiliationFormInitials(String trainingPartnerAffiliationFormInitials) {
		this.trainingPartnerAffiliationFormInitials = trainingPartnerAffiliationFormInitials;
	}
	public String getAssessmentAgencyAffiliationFormInitials() {
		return assessmentAgencyAffiliationFormInitials;
	}
	public void setAssessmentAgencyAffiliationFormInitials(String assessmentAgencyAffiliationFormInitials) {
		this.assessmentAgencyAffiliationFormInitials = assessmentAgencyAffiliationFormInitials;
	}
	public String getInvoiceIntitials() {
		return invoiceIntitials;
	}
	public void setInvoiceIntitials(String invoiceIntitials) {
		this.invoiceIntitials = invoiceIntitials;
	}
	public String getNSKFDCLetterInitials() {
		return NSKFDCLetterInitials;
	}
	public void setNSKFDCLetterInitials(String nSKFDCLetterInitials) {
		NSKFDCLetterInitials = nSKFDCLetterInitials;
	}
	public String getGeneralDarkAndLetterInitials() {
		return GeneralDarkAndLetterInitials;
	}
	public void setGeneralDarkAndLetterInitials(String generalDarkAndLetterInitials) {
		GeneralDarkAndLetterInitials = generalDarkAndLetterInitials;
	}
	public String getSaveInvoiceAtLocation() {
		return saveInvoiceAtLocation;
	}
	public void setSaveInvoiceAtLocation(String saveInvoiceAtLocation) {
		this.saveInvoiceAtLocation = saveInvoiceAtLocation;
	}
	public String getSaveTrainingPartnerAffiliationFormAtLocation() {
		return saveTrainingPartnerAffiliationFormAtLocation;
	}
	public void setSaveTrainingPartnerAffiliationFormAtLocation(String saveTrainingPartnerAffiliationFormAtLocation) {
		this.saveTrainingPartnerAffiliationFormAtLocation = saveTrainingPartnerAffiliationFormAtLocation;
	}
	public String getSaveAssessmentAgencyAffiliationFormAtLocation() {
		return saveAssessmentAgencyAffiliationFormAtLocation;
	}
	public void setSaveAssessmentAgencyAffiliationFormAtLocation(String saveAssessmentAgencyAffiliationFormAtLocation) {
		this.saveAssessmentAgencyAffiliationFormAtLocation = saveAssessmentAgencyAffiliationFormAtLocation;
	}
	public String getSaveNSKFDCLettersAtLocation() {
		return saveNSKFDCLettersAtLocation;
	}
	public void setSaveNSKFDCLettersAtLocation(String saveNSKFDCLettersAtLocation) {
		this.saveNSKFDCLettersAtLocation = saveNSKFDCLettersAtLocation;
	}
	public String getSaveGeneralDarkAndLetterAtLocation() {
		return saveGeneralDarkAndLetterAtLocation;
	}
	public void setSaveGeneralDarkAndLetterAtLocation(String saveGeneralDarkAndLetterAtLocation) {
		this.saveGeneralDarkAndLetterAtLocation = saveGeneralDarkAndLetterAtLocation;
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
	public String getDocumentNotStartedStatus() {
		return documentNotStartedStatus;
	}
	public void setDocumentNotStartedStatus(String documentNotStartedStatus) {
		this.documentNotStartedStatus = documentNotStartedStatus;
	}
	public String getDocUploadErrorMessage() {
		return docUploadErrorMessage;
	}
	public void setDocUploadErrorMessage(String docUploadErrorMessage) {
		this.docUploadErrorMessage = docUploadErrorMessage;
	}
	public String getApplicationIdGenerationErrorMessage() {
		return applicationIdGenerationErrorMessage;
	}
	public void setApplicationIdGenerationErrorMessage(String applicationIdGenerationErrorMessage) {
		this.applicationIdGenerationErrorMessage = applicationIdGenerationErrorMessage;
	}
	public String getPostDetailsSubmissionFailure() {
		return postDetailsSubmissionFailure;
	}
	public void setPostDetailsSubmissionFailure(String postDetailsSubmissionFailure) {
		this.postDetailsSubmissionFailure = postDetailsSubmissionFailure;
	}
	public String getTrainingPartnerAffiliation() {
		return trainingPartnerAffiliation;
	}
	public void setTrainingPartnerAffiliation(String trainingPartnerAffiliation) {
		this.trainingPartnerAffiliation = trainingPartnerAffiliation;
	}
	public String getAssessmentAgencyAffiliation() {
		return assessmentAgencyAffiliation;
	}
	public void setAssessmentAgencyAffiliation(String assessmentAgencyAffiliation) {
		this.assessmentAgencyAffiliation = assessmentAgencyAffiliation;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getGeneralDarkAndLetterName() {
		return GeneralDarkAndLetterName;
	}
	public void setGeneralDarkAndLetterName(String generalDarkAndLetterName) {
		GeneralDarkAndLetterName = generalDarkAndLetterName;
	}
	public String getNSKFDCName() {
		return NSKFDCName;
	}
	public void setNSKFDCName(String nSKFDCName) {
		NSKFDCName = nSKFDCName;
	}
	
}

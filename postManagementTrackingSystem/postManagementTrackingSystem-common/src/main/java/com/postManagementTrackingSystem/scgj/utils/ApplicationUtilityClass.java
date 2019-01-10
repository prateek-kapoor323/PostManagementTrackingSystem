package com.postManagementTrackingSystem.scgj.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.postManagementTrackingSystem.scgj.common.ReadApplicationConstants;
import com.postManagementTrackingSystem.scgj.dao.RegisterApplicationDao;
import com.postManagementTrackingSystem.scgj.dto.SubmitPostDetailsDto;


@Component
public class ApplicationUtilityClass {
	
@Autowired
private ReadApplicationConstants readApplicationConstants;

@Autowired
private RegisterApplicationDao registerApplicationDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtilityClass.class);

	
	
	public String getUniqueApplicationId(SubmitPostDetailsDto submitPostDetailsDto)
	{
		LOGGER.debug("Received request from submitPost method to generate a unique application Id");
		LOGGER.debug("Sending request to getExistingApplicationIdNumber to get the new application id number for the document type: "+submitPostDetailsDto.getDocumentType());
		Integer existingApplicationId = getExistingApplicationIdNumber(submitPostDetailsDto.getDocumentType());
		LOGGER.debug("The existing number of application Id are : "+existingApplicationId);
		
		if(existingApplicationId==null)
		{
			LOGGER.error("The existing application id is null, Returning NULL to controller");
			return null;
		}
		else
		{
			Integer newApplicationIdNumber = existingApplicationId+1;
			LOGGER.debug("The new application id number is: "+newApplicationIdNumber);
			LOGGER.debug("Sending request to documentType method to determine the type of method and generate application id till date");
			String uniqueIdDocumentType = documentType(submitPostDetailsDto);
			LOGGER.debug("The unique id is : "+uniqueIdDocumentType);
			LOGGER.debug("Generating the unique application id using uniqueIdTillDate and newApplicationIdNumber");
			String uniqueApplicationId = uniqueIdDocumentType+newApplicationIdNumber;
			LOGGER.debug("The unique application id generated using uniqueIdTillDate and newApplicationIdNumber is: "+uniqueApplicationId);
			return uniqueApplicationId;
		}
	}
	
	
	
	public Integer getExistingApplicationIdNumber(String documentType)
	{
		LOGGER.debug("Request received from getUniqueApplicationId number to get the existing number of applicaiton Id");
		LOGGER.debug("Sending request to DAO to get the existing number of application Id for document type: "+documentType);
		return registerApplicationDao.getExistingApplicationIdNumber(documentType);
	}
	
	
	
	public String documentType(SubmitPostDetailsDto submitPostDetailsDto)
	{
		
		String uniqueIdTillFormType;
		LOGGER.debug("Request received from getUniqueApplicationId method to determing the type of method and return the string ");
		String documentType = submitPostDetailsDto.getDocumentType();
		
		if(documentType==null||documentType.isEmpty())
		{
			LOGGER.error("The document type is null or empty in method documentType of ApplicationUtilityClass");
			LOGGER.error("Returning null");		
			return null;
		}
		
				
			if(documentType.equalsIgnoreCase("invoice"))
			{
				LOGGER.debug("The type of document is invoice");
				LOGGER.debug("Constructing the unique id");
			    uniqueIdTillFormType = readApplicationConstants.getOrgNameForApplicationId()+readApplicationConstants.getApplicationIdSeperator()+readApplicationConstants.getInvoiceIntitials()+readApplicationConstants.getApplicationIdSeperator();
				LOGGER.debug("The unique id till date is: "+uniqueIdTillFormType);
				return uniqueIdTillFormType;
			}
						
		 if(documentType.equalsIgnoreCase("applicationForm"))
			{
				LOGGER.debug("The type of document is application form");
				uniqueIdTillFormType = readApplicationConstants.getOrgNameForApplicationId()+readApplicationConstants.getApplicationIdSeperator()+readApplicationConstants.getApplicationFormInitials()+readApplicationConstants.getApplicationIdSeperator();
				LOGGER.debug("Unique Id for affiliation form till date: "+uniqueIdTillFormType);
				return uniqueIdTillFormType;
			}
						
		if(documentType.equalsIgnoreCase("certificate"))
			{
				LOGGER.debug("The type of document is certificate");
				uniqueIdTillFormType = readApplicationConstants.getOrgNameForApplicationId()+readApplicationConstants.getApplicationIdSeperator()+readApplicationConstants.getCertificateInitials()+readApplicationConstants.getApplicationIdSeperator();
				LOGGER.debug("The unique id for certificate till date is: "+uniqueIdTillFormType);
				return uniqueIdTillFormType;
			}
				
		else 
		{
			LOGGER.debug("The type of document is Miscellaneous");
		    uniqueIdTillFormType = readApplicationConstants.getOrgNameForApplicationId()+readApplicationConstants.getApplicationIdSeperator()+readApplicationConstants.getMiscellaneousFormInitials()+readApplicationConstants.getApplicationIdSeperator();
			LOGGER.debug("The unique id for miscellaneous till date is: "+uniqueIdTillFormType);
			return uniqueIdTillFormType;
		}
				
						
	}
		
	
	
	
	public String uploadFile(SubmitPostDetailsDto submitPostDetailsDto, String uniqueApplicationId) throws IOException {
		// TODO Auto-generated method stub		
		LOGGER.debug("Request received from Register Application Service - submitPost method to determine the type of document and upload the file");
		LOGGER.debug("Determining the type of document being uploaded for application id: "+uniqueApplicationId);
		String uploadedFilePath="";
		String filePath = "";//To get the path at which file has to be saved
		
		if(submitPostDetailsDto.getDocumentType()==null||submitPostDetailsDto.getDocumentType().isEmpty())
		{
			LOGGER.error("The document type is null or empty in method uploadFile");
			LOGGER.error("Returning null");		
			return null;
		}
		else
		{
			LOGGER.debug("DocumentType is present. Checking the document type of the file");
			String documentType = submitPostDetailsDto.getDocumentType();
			LOGGER.debug("Checking the document type");

				if(documentType.equalsIgnoreCase("invoice"))
				{
						LOGGER.debug("The type of document is invoice");
						LOGGER.debug("Checking if the file is empty");
						if(submitPostDetailsDto.getFile().isEmpty())
						{
							LOGGER.error("File is empty");
							LOGGER.error("Returning NULL");
							return null;
						}
						else
						{
							filePath = readApplicationConstants.getSaveInvoiceAtLocation();
							LOGGER.debug("The path to save invoice in uploadFile method is: "+filePath);
							LOGGER.debug("File exists in the DTO. Passing file Object,filePath and ApplicationID to the saveFile method");
							uploadedFilePath = saveFile(filePath,uniqueApplicationId,submitPostDetailsDto.getFile());
						}
				
				}
				else if(documentType.equalsIgnoreCase("applicationForm"))
				{
					LOGGER.debug("The document is certificate");
					filePath = readApplicationConstants.getSaveApplicationFormAtLocation();
					LOGGER.debug("File exists in the DTO. Passing file Object,filePath and ApplicationID to the saveFile method");
					uploadedFilePath = saveFile(filePath,uniqueApplicationId,submitPostDetailsDto.getFile());
				}
				else if(documentType.equalsIgnoreCase("certificate"))
				{
					LOGGER.debug("The type of document is certificate");
					filePath = readApplicationConstants.getSaveCertificatesAtLocation();
					LOGGER.debug("File exists in the DTO. Passing file Object,filePath and ApplicationID to the saveFile method");
					uploadedFilePath = saveFile(filePath,uniqueApplicationId,submitPostDetailsDto.getFile());

				}
				else 
				{
					LOGGER.debug("The type of document is Miscellaneous");
					filePath = readApplicationConstants.getSaveMiscellaneousFormAtLocation();
					LOGGER.debug("File exists in the DTO. Passing file Object,filePath and ApplicationID to the saveFile method");
					uploadedFilePath = saveFile(filePath,uniqueApplicationId,submitPostDetailsDto.getFile());

					
				}
				
				return uploadedFilePath;
			
		}
		
			
		
	}


	public String saveFile(String filePath, String uniqueApplicationId, MultipartFile file) throws IOException
	{
		LOGGER.debug("Request received in saveFile method to save the file");
		LOGGER.debug("Calculating current date to be used as a folder name");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime today = LocalDateTime.now();
		String curDate = formatter.format(today);
		LOGGER.debug("Reading the path received to upload file at");
		LOGGER.debug("The path received to savefile is: "+filePath);
		LOGGER.debug("The uniqueApplication ID in saveFile method is: "+uniqueApplicationId);
		
		String pathOfUploadedFile = "";
		int folderCreated=0;
		String pathTillDateFolder="";
		String fileName="";
		
		if(!file.isEmpty())
		{
			String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
			LOGGER.debug("The file extension is : "+fileExtension);
			fileName = uniqueApplicationId+"."+fileExtension;
			LOGGER.debug("The name of the file after renaming the file is: "+fileName);
			pathTillDateFolder=filePath+curDate+"//";
			LOGGER.debug("The path where the file has to be stored is: "+pathTillDateFolder);
			
			File folder = new File(pathTillDateFolder);
			if(!folder.exists())
			{
				LOGGER.debug("Folder does not exists");
				if(folder.mkdirs()||folder.canWrite())
				{
					LOGGER.debug("The directory has write permissions");
					folderCreated=1;
					LOGGER.debug("Directory created successfully");
					LOGGER.debug("The file written at: "+pathTillDateFolder+" is: "+fileName);
				}
				else
				{
					LOGGER.error("File cannot be created");
					LOGGER.error("The directory does not have write permissions");
					folderCreated=-1;
					
				}
			}

				byte[] bytes = file.getBytes();
				LOGGER.debug("The file name to be saved is: "+fileName);
				Path path = Paths.get(pathTillDateFolder+fileName);
				LOGGER.debug("The path of the uploaded file is: " + path);
				pathOfUploadedFile = path.toAbsolutePath().toString();
				LOGGER.debug("Absolute Path of uploaded file is: "+pathOfUploadedFile);
				Files.write(path, bytes);
		
		}
		
		LOGGER.debug("Returning the absolute path of the uploaded file: " +pathOfUploadedFile);
		return pathOfUploadedFile;
	}
	
	
	
}
	


















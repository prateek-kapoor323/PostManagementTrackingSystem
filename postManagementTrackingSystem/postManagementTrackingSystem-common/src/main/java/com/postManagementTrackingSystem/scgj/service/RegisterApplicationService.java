package com.postManagementTrackingSystem.scgj.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.postManagementTrackingSystem.scgj.common.ReadApplicationConstants;
import com.postManagementTrackingSystem.scgj.dao.RegisterApplicationDao;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNameDto;
import com.postManagementTrackingSystem.scgj.dto.SubmitPostDetailsDto;
import com.postManagementTrackingSystem.scgj.utils.ApplicationUtilityClass;

@Service
@EnableTransactionManagement

public class RegisterApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApplicationService .class);
	
	@Autowired 
	private RegisterApplicationDao registerApplicationDao;
	
	@Autowired 
	private ApplicationUtilityClass applicationUtilityClass;
	
	
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method receives request from the RegisterApplicationController
	 * to get the names of all the heads of departments
	 * @return collection of names of department heads
	 */
	public Collection<DepartmentHeadNameDto> getDepartmentHeadName()
	{
		LOGGER.debug("Received request in Service to get the names of the department heads");
		LOGGER.debug("Sending request to Dao to get the names of the department heads");
		return registerApplicationDao.getDepartmentHeadName();
		
	}
	
	
	/**
	 * @author Prateek Kapoor
	 * @param submitPostDetailsDto
	 * @return the unique application id once the post details are submitted in database
	 * @throws Exception 
	 */
	
 //   @Transactional(rollbackFor=Exception.class)
	public String submitPost(SubmitPostDetailsDto submitPostDetailsDto,String email)  {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from controller to submit the post details and upload the scanned document");
		LOGGER.debug("Sending request to get the unique application ID");
		String uniqueApplicationId = applicationUtilityClass.getUniqueApplicationId(submitPostDetailsDto);
		LOGGER.debug("Checking if the applicaiton id is null");
		if(uniqueApplicationId==null||uniqueApplicationId.isEmpty())
		{
			LOGGER.error("The unique application id is null or empty");
			LOGGER.error("Returning message -  Application Id could not be generated, Please contact the administrator");
			return readApplicationConstants.getApplicationIdGenerationErrorMessage(); //Returns error message - Id could not be generated, Please contact the administrator
			
		}
		LOGGER.debug("Unique application id generated is: "+uniqueApplicationId);
		String uploadPath="";
		try 
		{
			LOGGER.debug("Sending Request to determine the document type and return the uploadPath");			
			uploadPath = applicationUtilityClass.uploadFile(submitPostDetailsDto.getDocumentType(),submitPostDetailsDto.getFile(),uniqueApplicationId );
		} 
		catch (IOException e) 
		{
			LOGGER.error("An exception occured while writting file to the file system: "+e);
			LOGGER.error("Returning the error message");
			return readApplicationConstants.getDocUploadErrorMessage();
		}
		
		if(uploadPath==null)
		{
			LOGGER.error("An error occured in uploadFile method in ApplicationUtilityClass");
			LOGGER.error("ERROR! The file cannot be uploaded");
			LOGGER.error("Returning ERROR message to controller");
			return readApplicationConstants.getApplicationIdGenerationErrorMessage();
		}
		else
		{
			LOGGER.debug("Path of uploaded file returned is: "+uploadPath);
			
			try 
			{
				LOGGER.debug("Sending request to Dao to submit postDetailsDto along with applicationId and uploadPath into the database");
				return registerApplicationDao.submitPostDetails(submitPostDetailsDto,uniqueApplicationId,uploadPath,email);
			} 
			catch (Exception e) 
			{
		
				LOGGER.error("Caught an exception from submitPostDetails method in RegisterApplicationService:  "+e);
				LOGGER.error("Deleting file at the path: "+uploadPath);
				LOGGER.error("Creating file object and storing the path of file in the file object");
				File file = new File(uploadPath);
				LOGGER.error("File object created successfully, The file path is: "+file.getAbsolutePath());
				LOGGER.error("Deleting the file");
				if(file.delete())
				{
					LOGGER.error("File deleted successfully");
					LOGGER.error("Returning error message to the controller");
					return readApplicationConstants.getApplicationIdGenerationErrorMessage();
					
				}
				else
				{
					LOGGER.error("File could not be deleted from the file system");
					LOGGER.error("Returning error message to the controller");
					return readApplicationConstants.getApplicationIdGenerationErrorMessage();
				}
			}
		}
	}
	
	
}

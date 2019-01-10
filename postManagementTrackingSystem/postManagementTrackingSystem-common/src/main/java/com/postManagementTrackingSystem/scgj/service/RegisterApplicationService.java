package com.postManagementTrackingSystem.scgj.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.common.ReadApplicationConstants;
import com.postManagementTrackingSystem.scgj.dao.RegisterApplicationDao;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNameDto;
import com.postManagementTrackingSystem.scgj.dto.SubmitPostDetailsDto;
import com.postManagementTrackingSystem.scgj.utils.ApplicationUtilityClass;

@Service
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
	public List<DepartmentHeadNameDto> getDepartmentHeadName()
	{
		LOGGER.debug("Received request in Service to get the names of the department heads");
		LOGGER.debug("Sending request to Dao to get the names of the department heads");
		return registerApplicationDao.getDepartmentHeadName();
		
	}
	
	
	/**
	 * @author Prateek Kapoor
	 * @param submitPostDetailsDto
	 * @return
	 * @throws IOException 
	 */
	public String submitPost( SubmitPostDetailsDto submitPostDetailsDto) throws IOException {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from controller to submit the post details and upload the scanned document");
		LOGGER.debug("Sending request to get the unique application ID");
		String uniqueApplicationId = applicationUtilityClass.getUniqueApplicationId(submitPostDetailsDto);
		LOGGER.debug("Unique application id generated is: "+uniqueApplicationId);
		LOGGER.debug("Sending Request to determine the document type and return the uploadPath");
		String uploadPath = applicationUtilityClass.uploadFile(submitPostDetailsDto,uniqueApplicationId );
		if(uploadPath==null)
		{
			LOGGER.error("An error occured in uploadFile method in ApplicationUtilityClass");
			LOGGER.error("ERROR! The file cannot be uploaded");
			LOGGER.error("Returning NULL to the controller");
			return null;
		}
		else
		{
			LOGGER.debug("Path of uploaded file returned is: "+uploadPath);
			LOGGER.debug("Sending request to Dao to submit postDetailsDto along with applicationId and uploadPath into the database");
			
			return registerApplicationDao.submitPostDetails(submitPostDetailsDto,uniqueApplicationId,uploadPath);//Code will come to send the path and other details to the database
		}
	}
	
	
}

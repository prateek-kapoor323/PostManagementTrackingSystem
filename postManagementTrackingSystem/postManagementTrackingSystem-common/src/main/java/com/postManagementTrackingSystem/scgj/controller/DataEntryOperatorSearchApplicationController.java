package com.postManagementTrackingSystem.scgj.controller;

import java.sql.Date;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.postManagementTrackingSystem.scgj.common.Privilege;
import com.postManagementTrackingSystem.scgj.dto.DataEntryOperatorApplicationSearchResultsDto;
import com.postManagementTrackingSystem.scgj.service.DataEntryOperatorSearchApplicationService;
import com.postManagementTrackingSystem.scgj.utils.FileUtility;

@RestController
public class DataEntryOperatorSearchApplicationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEntryOperatorSearchApplicationController.class);
	
	@Autowired
	private DataEntryOperatorSearchApplicationService  dataEntryOperatorSearchApplicationService;
	
	@Autowired
	private FileUtility fileUtility;
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method is invoked when the user enters the application Id and clicks on search button,
	 * The method takes application id as a parameter and returns the fields to be displayed at front end along with
	 * the url of the uploaded file
	 * @param applicationId
	 * @return Collection of Search Results object
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/searchApplicationById")
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByApplicationId(@RequestParam("applicationId")String applicationId)
	{
		LOGGER.debug("Request received from front end to get application details using application id: "+applicationId);
		LOGGER.debug("Checking if the application id is null or empty");
		if(applicationId==null||applicationId.isEmpty())
		{
			LOGGER.error("Application id is null or empty");
			LOGGER.error("Returning NULL to front end");
			return null;
		}
		else
		{
			LOGGER.debug("The application id received from front end is: "+applicationId);
			LOGGER.debug("Sending request to service to get application details on the basis of application Id");
			return dataEntryOperatorSearchApplicationService.getApplicationByApplicationId(applicationId);
		}
		
	}
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method returns the details of an application on the basis of a department,
	 * The method takes *department* as a parameter and sends the parameter to the service
	 * @param department
	 * @return Collection of Search Results object
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/searchApplicationByDepartment")
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByDepartment(@RequestParam("department") String department)
	{
		LOGGER.debug("Request received from front end to get application details using department");
		LOGGER.debug("Checking if the department is null or empty");
		if(department==null||department.isEmpty())
		{
			LOGGER.error("Departmnent is NULL");
			LOGGER.error("Returning NULL to front end");
			return null;
		}
		else
		{
			LOGGER.debug("The department received from front end is: "+department);
			LOGGER.debug("Sending request to service to get applicaiton on the basis of department name");
			return dataEntryOperatorSearchApplicationService.getApplicationByDepartment(department);
		}
	}
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method takes two date parameters - from and to, which will return the record of applications between a range of dates
	 * which is taken as parameters. These two parameters are sent to service 
	 * @param fromDate
	 * @param tillDate
	 * @return Collection of Search Results object
	 */
	@Privilege(value= {"DEO"})
	@RequestMapping("/searchApplicationByDate")
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByDateRange(@RequestParam("from")Date fromDate,@RequestParam("to") Date tillDate)
	{
		LOGGER.debug("Request received from front end to get application record using date params: "+fromDate+" to date: "+tillDate);
		LOGGER.debug("Sending request to service to get record of applications on the basis of date range");
		return dataEntryOperatorSearchApplicationService.getApplicationByDateRange(fromDate, tillDate);
	}
	
	/**
	 * @author Prateek Kapoor
	 * Description - This method in invoked when the user clicks on the pdf file icon in the search application table
	 * and receives the url of the file, This method calls the downloadFile method in FileUtility Class along with the
	 * path(URL) of the file.
	 * @param filePath
	 * @param response
	 * 
	 */
	
	@RequestMapping("/downloadFile")
	public void downloadFile(@RequestParam("filePath") String filePath, HttpServletResponse response)
	{
		LOGGER.debug("Request received from front end to download file at location: "+filePath);
		LOGGER.debug("Checking if the file path is null or empty");
		
		if(filePath == null || filePath.isEmpty())
		{
			LOGGER.error("File Path is null");
			LOGGER.error("File cannot be downloaded");
			
		}
		else
		{
			LOGGER.debug("Sending request to FileUtility Class - downloadFileMethod to download the file");
			fileUtility.downloadFile(filePath,response);
			
		}
	}
}

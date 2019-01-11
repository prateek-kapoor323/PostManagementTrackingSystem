package com.postManagementTrackingSystem.scgj.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postManagementTrackingSystem.scgj.dao.DataEntryOperatorSearchApplicationDao;
import com.postManagementTrackingSystem.scgj.dto.DataEntryOperatorApplicationSearchResultsDto;

@Service
public class DataEntryOperatorSearchApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataEntryOperatorSearchApplicationService.class);
	
	@Autowired
	private DataEntryOperatorSearchApplicationDao dataEntryOperatorSearchApplicationDao;
	
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByApplicationId(String applicationId)
	{
		LOGGER.debug("Request received from controller to get application record by application id: "+applicationId);
		LOGGER.debug("Sending request to getApplicationByApplicationId method in Dao to get the results");
		return dataEntryOperatorSearchApplicationDao.getApplicationByApplicationId(applicationId);
		
	}
	
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByDepartment(String department)
	{
		LOGGER.debug("Request received from controller to get application record by department: "+department);
		LOGGER.debug("Sending request to getApplicationByDepartment method in Dao");
		return dataEntryOperatorSearchApplicationDao.getApplicationByDepartment(department);
	}
	
	public Collection<DataEntryOperatorApplicationSearchResultsDto> getApplicationByDateRange(Date fromDate, Date tillDate)
	{
		LOGGER.debug("Request received from controller to get application record by date");
		LOGGER.debug("Changing the type of date to String");
		LOGGER.debug("Creating date object and setting format as yyyy-MM-dd");
		DateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		LOGGER.debug("Created object - newDateFormat of DateFormat class");
		String startingDate = newDateFormat.format(fromDate);
		String toDate = newDateFormat.format(tillDate);
		LOGGER.debug("The starting date is: "+startingDate+" End date is: "+toDate);
		LOGGER.debug("Sending request to DAO to get application details using date");
		return dataEntryOperatorSearchApplicationDao.getApplicationByDateRange(startingDate, toDate);
	}
	
}

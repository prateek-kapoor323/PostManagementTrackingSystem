package com.postManagementTrackingSystem.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.postManagementTrackingSystem.scgj.common.AbstractTransactionalDao;
import com.postManagementTrackingSystem.scgj.common.ReadApplicationConstants;
import com.postManagementTrackingSystem.scgj.config.RegisterApplicationConfig;
import com.postManagementTrackingSystem.scgj.dto.DepartmentHeadNameDto;
import com.postManagementTrackingSystem.scgj.dto.SubmitPostDetailsDto;



@Repository
public class RegisterApplicationDao extends AbstractTransactionalDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApplicationDao.class);
	
	
	@Autowired
	private RegisterApplicationConfig registerApplicationConfig;
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	private static DepartmentHeadNameRowMapper ROW_MAPPER = new DepartmentHeadNameRowMapper();
	
	/**
	 * @author Prateek Kapoor
	 * @Description - This method returns the name of all the department heads on the basis of their role_type
	 * @return Collection (List) of Name Object
	 */
	
	public Collection <DepartmentHeadNameDto> getDepartmentHeadName()
	{
		LOGGER.debug("Request received in DAO to get the name of all the department heads");
		LOGGER.debug("Creating hashmap of object");
		Map<String,Object> params = new HashMap<String,Object>();
		LOGGER.debug("Hashmap created successfully");
		try
		{
			LOGGER.debug("In try block of getDepartmentHeadName method to get the name of the department heads");
			LOGGER.debug("Executing query to get the name of the department heads");
			return getJdbcTemplate().query(registerApplicationConfig.getDepartmentHeadNamesSql(), params, ROW_MAPPER);
			
		}
		catch(Exception e)
		{
			LOGGER.error("An error has occured while fetching the names of the department heads "+e);
			LOGGER.error("Returning NULL");
			return null;
		}
		
		
	}
	
	
	
	
	public static class DepartmentHeadNameRowMapper implements RowMapper<DepartmentHeadNameDto>
	{
		@Override
		public DepartmentHeadNameDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String departmentHeadName = rs.getString("name");
			return new DepartmentHeadNameDto(departmentHeadName);
		}
		
	}

	
	
	/****************************************************************************************************************************
	 * @author Prateek Kapoor																									*	
	 * Description - This method returns the existing number of application id's that are currently present in the system for a *
	 * particular document type. Example - invoice, certificates 																*
	 * @param documentType																										*	
	 * @return number of existing application id for a particular document type													*
	 * **************************************************************************************************************************
	 */
	public Integer getExistingApplicationIdNumber(String documentType) 
	{
		
		LOGGER.debug("Request received from getExistingApplicationIdNumber method in Register Application Class to get the number of existing application ID");
		LOGGER.debug("The document type received from Application Utility Class getExistingApplicationIdNumber is: "+documentType);
		LOGGER.debug("Creating hashmap of object");
		Map<String,Object>applicationIdParams = new HashMap<String,Object>();
		LOGGER.debug("Hashmap of objects created successfully");
		LOGGER.debug("Inserting params into hashmap");
		applicationIdParams.put("document_type",documentType);
		LOGGER.debug("Params Inserted successfully");
		try
		{
			LOGGER.debug("In try block to get the existing number of application id");
			LOGGER.debug("Executing query to get the total number of application Id");
			Integer existingApplicationNumber = getJdbcTemplate().queryForObject(registerApplicationConfig.getTotalNumberOfApplications(),applicationIdParams, Integer.class);
			LOGGER.debug("The number of existing applications are : "+existingApplicationNumber);
			return existingApplicationNumber;
			
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while fetching the number of existing application id");
			LOGGER.error("The exception is: "+e);
			return null;
		}
		
		
	}

	/**
	 * @author Prateek Kapoor
	 * Description - This method submits the post details into two tables -
	 * 1. document_details - after the information is submitted, it returns the auto incremented key of the inserted application id and sends the control to fill the document status table
	 * 2. doc_status 
	 * Once,The doc_status table is filled. The unique application id is returned to the service
	 * @param submitPostDetailsDto
	 * @param uniqueApplicationId
	 * @param uploadPath
	 * @return unique application id
	 * @throws Exception 
	 */
	
	@Transactional(rollbackFor=Exception.class)
	public String submitPostDetails(SubmitPostDetailsDto submitPostDetailsDto, String uniqueApplicationId,String uploadPath) throws Exception 
	{
		
		// TODO Auto-generated method stub
			
		LOGGER.debug("Request received from registerApplicationService - submitPost method to submit document details, uniqueApplicationId,uploadPath into database");
		LOGGER.debug("Creating hashmap of objects");
		SqlParameterSource postDetailsParams = new MapSqlParameterSource();
		LOGGER.debug("Hashmap created successfully");
		LOGGER.debug("Putting values in the hashmap");
		((MapSqlParameterSource) postDetailsParams).addValue("applicationId", uniqueApplicationId);
		((MapSqlParameterSource) postDetailsParams).addValue("senderName", submitPostDetailsDto.getSenderName());
		((MapSqlParameterSource) postDetailsParams).addValue("senderPoc", submitPostDetailsDto.getPointOfContact());
		((MapSqlParameterSource) postDetailsParams).addValue("senderContact",submitPostDetailsDto.getContactNumber());
		((MapSqlParameterSource) postDetailsParams).addValue("dateReceived", submitPostDetailsDto.getDateReceived());
		((MapSqlParameterSource) postDetailsParams).addValue("priority", submitPostDetailsDto.getPriority());
		((MapSqlParameterSource) postDetailsParams).addValue("subject", submitPostDetailsDto.getSubject());
		((MapSqlParameterSource) postDetailsParams).addValue("documentType", submitPostDetailsDto.getDocumentType());
		((MapSqlParameterSource) postDetailsParams).addValue("documentPath", uploadPath);
		((MapSqlParameterSource) postDetailsParams).addValue("documentRemarks", submitPostDetailsDto.getAdditionalComments());
		LOGGER.debug("Values succcessfully inserted into hashmap");
		LOGGER.debug("Creating object of KeyHolder to return the auto generated key after insertion of the post details");
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			LOGGER.debug("Executing the query to submit post details");
			getJdbcTemplate().update(registerApplicationConfig.getSubmitPostDetails(),postDetailsParams, holder);
			LOGGER.debug("The key which is generated is: "+holder.getKey().intValue());
			Integer docId = holder.getKey().intValue();//To get the primary key (Generated by Auto-Increment) of the document details table
			LOGGER.debug("Details inserted successfully, Key of inserted application id is: "+holder.getKey().intValue());
			LOGGER.debug("Calling method get the Id of the owner to whom the document is assigned");
			Integer documentOwnerId = getDocumentOwnerId(submitPostDetailsDto.getOwnerName());//Auto-incremented id of the DH in the users table
			LOGGER.debug("The document owner id corresponding to ownerName: "+submitPostDetailsDto.getOwnerName()+" and applicationId: "+uniqueApplicationId+" is: "+documentOwnerId);
			LOGGER.debug("Calling method to fill the document status table");
			LOGGER.debug("Sending control to fillDocumentStatusTable");
			Integer submitStatus = fillDocumentStatusTable(docId,documentOwnerId);
			LOGGER.debug("The document has been submitted successfully ");
			LOGGER.debug("Calling method to return application id on the basis of id(PK) :" +docId);
			String uniqueId = getApplicationIdById(docId);
			LOGGER.debug("The unique id corresponding to the generated primary key is: "+docId);
			return uniqueId;
		}
		catch(RuntimeException e)
		{
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL");
			throw new Exception(e);
			
		}
		
		
	}

	/**
	 * This method receives docId(PK) from submit post details method and returns the applicationId corresponding to that docID
	 * @param docId
	 * @return applicationId
	 * @throws Exception 
	 */
	
	@Transactional(rollbackFor = Exception.class)
	private String getApplicationIdById(Integer docId) throws Exception {
		// TODO Auto-generated method stub
		String applicationId;
		LOGGER.debug("Request receieved in getApplicationIdById method to get the application id corressponding to the docId: "+docId);

			LOGGER.debug("Creating hashmap of objects");
			Map<String,Object> idParam = new HashMap<>();
			LOGGER.debug("Hashmap successfully created");
			LOGGER.debug("Inserting docId into hashmap");
			idParam.put("docId", docId);
			LOGGER.debug("DocId successfully inserted into hashmap");
			try
			{
			  LOGGER.debug("In try block to get the applicationId corresponding to id: "+docId);
			  LOGGER.debug("Executing query to get the application id corresponding to docId: "+docId);
			  applicationId = getJdbcTemplate().queryForObject(registerApplicationConfig.getApplicationIdById(), idParam, String.class);
			  LOGGER.debug("The application id corresponding to docId: "+docId+" is : "+applicationId);
			  LOGGER.debug("Returning application id: "+applicationId);
			  return applicationId;
			}
		 catch (Exception e) {
			// TODO: handle exception
			 LOGGER.debug("An exception occured while fetching application id corresponding to docId: "+docId);
			 LOGGER.debug("The exception is: "+e);
			 LOGGER.debug("Returning NULL");
			throw new Exception(); 
			 
		}
	}

	/**
	 * @author Prateek Kapoor
	 * Description - This method receives the docId(Auto generated key) from doc_details table, documentOwnerId(PK) user table
	 * and additional comment from the DTO  
	 * @param docId
	 * @param documentOwnerId
	 * @param additionalComments
	 * @return submission Status - if 1 - the doc_status table was filled . Else - The document status table was not filled
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	private Integer fillDocumentStatusTable(Integer docId, Integer documentOwnerId) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("In fillDocumentStatusTable to submit the details in the documentStatus table");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>docStatusParams = new HashMap<>();
		LOGGER.debug("Hashmap successfully created");
		LOGGER.debug("Putting values in the hashmap");
		docStatusParams.put("ownerId", documentOwnerId);
		docStatusParams.put("status",readApplicationConstants.getDocumentNotStartedStatus());
		docStatusParams.put("documentId", docId);
		try
		{
			LOGGER.debug("In try block of fillDocumentStatusTable to submit details in the doc status table");
			LOGGER.debug("Executing query to submit the details in document status table");
			return getJdbcTemplate().update(registerApplicationConfig.getSubmitDocumentStatus(), docStatusParams);
			
		}
		catch(Exception e)
		{
			LOGGER.error("An error occured while submitting the details in doc status table: "+e);
			LOGGER.error("Throwing exception to rollback the transaction");
			throw new Exception(e);
			
		}
	}

	/**
	 * @author Prateek Kapoor
	 * Description - This method returns the id of the owner to whom the document was assigned - Initially all the documents will be assigned to the department heads
	 * @param ownerName
	 * @return id of the document owner
	 */
	public Integer getDocumentOwnerId(String ownerName) {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received in getDocumentOwnerId method from submitPostDetails method in RegistrationApplicationDao to get the id of the document owner");
		LOGGER.debug("The name of the document owner is: "+ownerName);
		LOGGER.debug("Creating hashmap of objects ");
		Map<String,Object>ownerParams = new HashMap<>();
		ownerParams.put("ownerName", ownerName);
		try
		{	
			LOGGER.debug("Executing query to get the owner id corresponding to the ownerName: "+ownerName);
			return getJdbcTemplate().queryForObject(registerApplicationConfig.getDocumentOwnerId(), ownerParams, Integer.class);
						
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while fetching the id of the document owner");
			LOGGER.error("The exception is: "+e);
			LOGGER.error("Returning NULL");
			return null;
		}
	}
		
	
}

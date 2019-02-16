package com.postManagementTrackingSystem.scgj.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.postManagementTrackingSystem.scgj.common.Privilege;

@Component
public class FileUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtility.class);

	/**
	 * @author Prateek Kapoor
	 * Description - This method is invoked by the download file method in DataEntryOperatorSearchApplicationController class
	 * and accepts the HTTP response along with the file path. This method is used to download the file using the file path
	 * @param filePath
	 * @param response
	 *
	 */
	@Privilege(value= {"DEO","DH","DE"})
	public void downloadFile(String filePath, HttpServletResponse response) {
		// TODO Auto-generated method stub
		LOGGER.debug("Request received from Controller to download path at location: "+filePath);
		try {
			
			LOGGER.debug("In try block to download the file at path"+filePath);
			LOGGER.debug("Creating file object");
			File file = new File(filePath);
			
			LOGGER.debug("Setting the content type of response");
			response.setContentType("application/pdf");

			LOGGER.debug("Setting the header of response as attachment along with the filename");
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

			
			LOGGER.debug("Creating buffered input stream to read file from: "+filePath);
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			
			LOGGER.debug("Creating buffered output stream to append file in the response " +file.getName());
			BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
			
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer)) != -1)
			{
				outStream.write(buffer, 0, bytesRead);
			}

			outStream.flush();
			inputStream.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			
			LOGGER.error("An exception occured while downloading the file from the path");
		}
		
	}
	
	
}

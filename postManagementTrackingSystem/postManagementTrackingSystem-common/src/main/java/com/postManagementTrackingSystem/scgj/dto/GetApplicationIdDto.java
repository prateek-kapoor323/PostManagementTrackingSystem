package com.postManagementTrackingSystem.scgj.dto;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class GetApplicationIdDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	private String applicationId;
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public GetApplicationIdDto(String applicationId) {
		super();
		this.applicationId = applicationId;
	}
	public GetApplicationIdDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

package com.postManagementTrackingSystem.scgj.dto;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class GetNameOfLoggedInUserDto extends BaseDto {

	private String nameOfLoggedInUser;

	public String getNameOfLoggedInUser() {
		return nameOfLoggedInUser;
	}

	public void setNameOfLoggedInUser(String nameOfLoggedInUser) {
		this.nameOfLoggedInUser = nameOfLoggedInUser;
	}

	public GetNameOfLoggedInUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetNameOfLoggedInUserDto(String nameOfLoggedInUser) {
		super();
		this.nameOfLoggedInUser = nameOfLoggedInUser;
	}
	
	
}

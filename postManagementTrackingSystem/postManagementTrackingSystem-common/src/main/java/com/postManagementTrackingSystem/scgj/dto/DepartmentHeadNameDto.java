package com.postManagementTrackingSystem.scgj.dto;

import com.postManagementTrackingSystem.scgj.common.BaseDto;

public class DepartmentHeadNameDto extends BaseDto{

	
	private static final long serialVersionUID = 1L;
	private String departmentHeadName;
	

	public String getDepartmentHeadName() {
		return departmentHeadName;
	}

	public void setDepartmentHeadName(String departmentHeadName) {
		this.departmentHeadName = departmentHeadName;
	}

	public DepartmentHeadNameDto(String departmentHeadName) {
		super();
		this.departmentHeadName = departmentHeadName;
	}

	public DepartmentHeadNameDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

package com.postManagementTrackingSystem.scgj.dto;

public class GetNameOfDepartmentEmployeesDTO {

	private String departmentEmployeeName;

	public String getDepartmentEmployeeName() {
		return departmentEmployeeName;
	}

	public void setDepartmentEmployeeName(String departmentEmployeeName) {
		this.departmentEmployeeName = departmentEmployeeName;
	}

	public GetNameOfDepartmentEmployeesDTO(String departmentEmployeeName) {
		super();
		this.departmentEmployeeName = departmentEmployeeName;
	}

	public GetNameOfDepartmentEmployeesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

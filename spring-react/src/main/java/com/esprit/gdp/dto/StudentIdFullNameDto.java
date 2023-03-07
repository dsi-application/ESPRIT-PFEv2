package com.esprit.gdp.dto;

public class StudentIdFullNameDto
{
	private String idet;
	private String fullName;
	
	public StudentIdFullNameDto() {}
	
	
	public StudentIdFullNameDto(String idet, String fullName)
	{
		this.idet = idet;
		this.fullName = fullName;
	}
	
	
	/************************************************ Getters & Setters *************************************************/

	
	public String getIdet() {
		return idet;
	}

	public void setIdet(String idet) {
		this.idet = idet;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}

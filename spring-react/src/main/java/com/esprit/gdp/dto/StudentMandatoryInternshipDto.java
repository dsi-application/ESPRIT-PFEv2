package com.esprit.gdp.dto;

public class StudentMandatoryInternshipDto
{
	
	private String fullName;
	private String birthDay;

	
	public StudentMandatoryInternshipDto() {}
	
	public StudentMandatoryInternshipDto(String fullName, String birthDay)
	{
		this.fullName = fullName;
		this.birthDay = birthDay;
	}

	
	/************************************************ Getters & Setters *************************************************/

	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

}

package com.esprit.gdp.dto;

public class StudentJustificatifStageDto
{
	private String fullName;
	private String birthDay;
	private String cinPassport;
	
	public StudentJustificatifStageDto() {}
	
	public StudentJustificatifStageDto(String fullName, String birthDay, String cinPassport)
	{
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.cinPassport = cinPassport;
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

	public String getCinPassport() {
		return cinPassport;
	}

	public void setCinPassport(String cinPassport) {
		this.cinPassport = cinPassport;
	}

}

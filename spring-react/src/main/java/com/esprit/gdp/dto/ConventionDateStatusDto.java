package com.esprit.gdp.dto;

public class ConventionDateStatusDto
{
	private String dateConvention;
	private String traiter;
	
	
	public ConventionDateStatusDto() {}
	
	public ConventionDateStatusDto(String dateConvention, String traiter) {
		this.dateConvention = dateConvention;
		this.traiter = traiter;
	}
	
	
	/********************************** Getters & Setters **********************************/

	
	public String getDateConvention() {
		return dateConvention;
	}

	public void setDateConvention(String dateConvention) {
		this.dateConvention = dateConvention;
	}

	public String getTraiter() {
		return traiter;
	}

	public void setTraiter(String traiter) {
		this.traiter = traiter;
	}
	
	
}

package com.esprit.gdp.dto;

import java.util.Date;

public class ConventionDateConvDateDebutStageStatusDto
{
	private String dateConvention;
	private Date dateDebutStage;
	private String traiter;
	
	
	public ConventionDateConvDateDebutStageStatusDto() {}
	
	public ConventionDateConvDateDebutStageStatusDto(String dateConvention, Date dateDebutStage, String traiter)
	{
		this.dateConvention = dateConvention;
		this.dateDebutStage = dateDebutStage;
		this.traiter = traiter;
	}
	
	
	/********************************** Getters & Setters **********************************/

	
	public Date getDateDebutStage() {
		return dateDebutStage;
	}

	public String getDateConvention() {
		return dateConvention;
	}

	public void setDateConvention(String dateConvention) {
		this.dateConvention = dateConvention;
	}

	public void setDateDebutStage(Date dateDebutStage) {
		this.dateDebutStage = dateDebutStage;
	}

	public String getTraiter() {
		return traiter;
	}

	public void setTraiter(String traiter) {
		this.traiter = traiter;
	}
	
	
}

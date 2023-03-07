package com.esprit.gdp.dto;

import java.util.Date;

public class AvenantDateAvDateDebutStageStatusDto
{
	private String dateAvenant;
	private Date dateDebutStage;
	private Boolean traiter;
	
	
	public AvenantDateAvDateDebutStageStatusDto() {}
	
	public AvenantDateAvDateDebutStageStatusDto(String dateAvenant, Date dateDebutStage, Boolean traiter)
	{
		this.dateAvenant = dateAvenant;
		this.dateDebutStage = dateDebutStage;
		this.traiter = traiter;
	}
	
	
	/********************************** Getters & Setters **********************************/

	
	public Date getDateDebutStage() {
		return dateDebutStage;
	}

	public void setDateDebutStage(Date dateDebutStage) {
		this.dateDebutStage = dateDebutStage;
	}

	public Boolean getTraiter() {
		return traiter;
	}

	public void setTraiter(Boolean traiter) {
		this.traiter = traiter;
	}

	public String getDateAvenant() {
		return dateAvenant;
	}

	public void setDateAvenant(String dateAvenant) {
		this.dateAvenant = dateAvenant;
	}
	
	
}

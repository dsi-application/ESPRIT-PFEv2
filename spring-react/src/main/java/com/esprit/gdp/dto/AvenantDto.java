package com.esprit.gdp.dto;

import java.util.Date;

public class AvenantDto
{
	private Date dateAvenant;
	
	private String dateConvention;
	
	private Date dateDebut;
    
	private Date dateFin;
        
	private Boolean traiter;
	
	private String responsableEntreprise;
	
	private String qualiteResponsable;
	
	
	
	public AvenantDto() {}

	public AvenantDto(Date dateAvenant, Date dateDebut, Date dateFin, Boolean traiter, String responsableEntreprise,
			String qualiteResponsable, String dateConvention) {
		this.dateAvenant = dateAvenant;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.traiter = traiter;
		this.responsableEntreprise = responsableEntreprise;
		this.qualiteResponsable = qualiteResponsable;
		this.dateConvention = dateConvention;
	}


	public Date getDateAvenant() {
		return dateAvenant;
	}

	public void setDateAvenant(Date dateAvenant) {
		this.dateAvenant = dateAvenant;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Boolean getTraiter() {
		return traiter;
	}

	public void setTraiter(Boolean traiter) {
		this.traiter = traiter;
	}

	public String getResponsableEntreprise() {
		return responsableEntreprise;
	}

	public void setResponsableEntreprise(String responsableEntreprise) {
		this.responsableEntreprise = responsableEntreprise;
	}

	public String getQualiteResponsable() {
		return qualiteResponsable;
	}

	public void setQualiteResponsable(String qualiteResponsable) {
		this.qualiteResponsable = qualiteResponsable;
	}

	public String getDateConvention() {
		return dateConvention;
	}

	public void setDateConvention(String dateConvention) {
		this.dateConvention = dateConvention;
	}

}

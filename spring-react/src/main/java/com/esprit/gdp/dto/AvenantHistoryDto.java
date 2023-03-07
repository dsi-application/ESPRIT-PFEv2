package com.esprit.gdp.dto;


public class AvenantHistoryDto
{
	private String dateAvenant;
	
	private String dateDebut;
    
	private String dateFin;
        
	private String responsableEntreprise;
	
	private String qualiteResponsable;
	
	
	
	public AvenantHistoryDto() {}

	public AvenantHistoryDto(String dateAvenant, String dateDebut, String dateFin, String responsableEntreprise,
			String qualiteResponsable) {
		this.dateAvenant = dateAvenant;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.responsableEntreprise = responsableEntreprise;
		this.qualiteResponsable = qualiteResponsable;
	}


	public String getDateAvenant() {
		return dateAvenant;
	}

	public void setDateAvenant(String dateAvenant) {
		this.dateAvenant = dateAvenant;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
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

}

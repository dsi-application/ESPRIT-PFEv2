package com.esprit.gdp.dto;

public class ConventionCompanyLabelDateDebutFinStageDto
{
	private String companyLabel;
	private String dateDebut;
	private String dateFin;
	
	
	public ConventionCompanyLabelDateDebutFinStageDto() {}


	public ConventionCompanyLabelDateDebutFinStageDto(String companyLabel, String dateDebut, String dateFin) {
		this.companyLabel = companyLabel;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	
	
	/*********************************************************************/

	public String getDateDebut() {
		return dateDebut;
	}

	public String getCompanyLabel() {
		return companyLabel;
	}

	public void setCompanyLabel(String companyLabel) {
		this.companyLabel = companyLabel;
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


}

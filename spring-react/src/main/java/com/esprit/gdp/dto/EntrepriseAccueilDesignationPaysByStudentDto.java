package com.esprit.gdp.dto;

public class EntrepriseAccueilDesignationPaysByStudentDto
{
	private String mailEntreprise;
	private String nomPays;
	
	
	
	public EntrepriseAccueilDesignationPaysByStudentDto() {}

	public EntrepriseAccueilDesignationPaysByStudentDto(String mailEntreprise, String nomPays)
	{
		this.mailEntreprise = mailEntreprise;
		this.nomPays = nomPays;
	}
	
	/*************************************************************************************************/


	public String getNomPays() {
		return nomPays;
	}

	public String getMailEntreprise() {
		return mailEntreprise;
	}

	public void setMailEntreprise(String mailEntreprise) {
		this.mailEntreprise = mailEntreprise;
	}

	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}

}

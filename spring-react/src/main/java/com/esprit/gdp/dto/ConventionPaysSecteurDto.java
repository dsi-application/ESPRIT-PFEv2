package com.esprit.gdp.dto;


public class ConventionPaysSecteurDto
{
	
	private String companyNomPays;
	private String companyLibelleSecteur;
	
	
	public ConventionPaysSecteurDto() {}
	
	
	public ConventionPaysSecteurDto(String companyNomPays, String companyLibelleSecteur)
	{
		this.companyNomPays = companyNomPays;
		this.companyLibelleSecteur = companyLibelleSecteur;
	}

	public String getCompanyNomPays() {
		return companyNomPays;
	}

	public void setCompanyNomPays(String companyNomPays) {
		this.companyNomPays = companyNomPays;
	}

	public String getCompanyLibelleSecteur() {
		return companyLibelleSecteur;
	}

	public void setCompanyLibelleSecteur(String companyLibelleSecteur) {
		this.companyLibelleSecteur = companyLibelleSecteur;
	}
	
	
}

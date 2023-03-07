package com.esprit.gdp.dto;


public class EntrepriseAccueilForConvHistDto
{
	
	private String companyDesignation;
	
	private String companyResponsable;
	
	private String companySiegeSocial;
	
	private String companyAddressMail;
	
	private String companyFax;
	
	private String companyNomPays;
	
	private String companyLibelleSecteur;
	
	private String motifAnnulation;
	
	
	public EntrepriseAccueilForConvHistDto() {}
	
	
	public EntrepriseAccueilForConvHistDto(String companyDesignation, String companyResponsable,
			String companySiegeSocial, String companyAddressMail, String companyFax, String companyNomPays,
			String companyLibelleSecteur, String motifAnnulation) {
		this.companyDesignation = companyDesignation;
		this.companyResponsable = companyResponsable;
		this.companySiegeSocial = companySiegeSocial;
		this.companyAddressMail = companyAddressMail;
		this.companyFax = companyFax;
		this.companyNomPays = companyNomPays;
		this.companyLibelleSecteur = companyLibelleSecteur;
		this.motifAnnulation = motifAnnulation;
	}
	

	public String getCompanyDesignation() {
		return companyDesignation;
	}

	public void setCompanyDesignation(String companyDesignation) {
		this.companyDesignation = companyDesignation;
	}

	public String getCompanyResponsable() {
		return companyResponsable;
	}

	public void setCompanyResponsable(String companyResponsable) {
		this.companyResponsable = companyResponsable;
	}

	public String getCompanySiegeSocial() {
		return companySiegeSocial;
	}

	public void setCompanySiegeSocial(String companySiegeSocial) {
		this.companySiegeSocial = companySiegeSocial;
	}

	public String getCompanyAddressMail() {
		return companyAddressMail;
	}

	public void setCompanyAddressMail(String companyAddressMail) {
		this.companyAddressMail = companyAddressMail;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
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

	public String getMotifAnnulation() {
		return motifAnnulation;
	}

	public void setMotifAnnulation(String motifAnnulation) {
		this.motifAnnulation = motifAnnulation;
	}
	
}

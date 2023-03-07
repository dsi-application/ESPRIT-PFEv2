package com.esprit.gdp.dto;

import java.util.List;

public class ConventionHistoriqueDto
{
	private String dateConvention;
	
	private String dateDebut;
	
	private String dateFin;

	private String responsable;
	
	private String address;
	
	private String telephone;
	
	private String traiter;
	
	private String companyDesignation;
	
	private String companySiegeSocial;
	
	private String companyAddressMail;
	
	private String companyFax;
	
	private String companyNomPays;
	
	private String companyLibelleSecteur;
	
	private String motifAnnulation;
	
	private List<AvenantHistoryDto> avenantsHistoryDto;
	
	
	public ConventionHistoriqueDto() {}
	
	
	public ConventionHistoriqueDto(String dateConvention) {
		this.dateConvention = dateConvention;
	}
	
	public ConventionHistoriqueDto(String dateConvention, String dateDebut, String dateFin, String responsable, String address,
			String telephone, String traiter, String companyDesignation,
			String companyAddressMail, String companySiegeSocial, String companyFax, String motifAnnulation) {
		this.dateConvention = dateConvention;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.responsable = responsable;
		this.address = address;
		this.telephone = telephone;
		this.traiter = traiter;
		this.companyDesignation = companyDesignation;
		this.companyAddressMail = companyAddressMail;
		this.companySiegeSocial = companySiegeSocial;
		this.companyFax = companyFax;
		this.motifAnnulation = motifAnnulation;
	}
	
//	public ConventionHistoriqueDto(String dateConvention, String dateDebut, String dateFin, String responsable, String address,
//			String telephone, String traiter, String companyDesignation,
//			String companySiegeSocial, String companyAddressMail, String companyFax, String companyNomPays,
//			String companyLibelleSecteur, String motifAnnulation) {
//		this.dateConvention = dateConvention;
//		this.dateDebut = dateDebut;
//		this.dateFin = dateFin;
//		this.responsable = responsable;
//		this.address = address;
//		this.telephone = telephone;
//		this.traiter = traiter;
//		this.companyDesignation = companyDesignation;
//		this.companySiegeSocial = companySiegeSocial;
//		this.companyAddressMail = companyAddressMail;
//		this.companyFax = companyFax;
//		this.companyNomPays = companyNomPays;
//		this.companyLibelleSecteur = companyLibelleSecteur;
//		this.motifAnnulation = motifAnnulation;
//	}

	
	public String getDateConvention() {
		return dateConvention;
	}

	public void setDateConvention(String dateConvention) {
		this.dateConvention = dateConvention;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTraiter() {
		return traiter;
	}

	public void setTraiter(String traiter) {
		this.traiter = traiter;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getCompanyDesignation() {
		return companyDesignation;
	}

	public void setCompanyDesignation(String companyDesignation) {
		this.companyDesignation = companyDesignation;
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

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public String getMotifAnnulation() {
		return motifAnnulation;
	}

	public void setMotifAnnulation(String motifAnnulation) {
		this.motifAnnulation = motifAnnulation;
	}

	public List<AvenantHistoryDto> getAvenantsHistoryDto() {
		return avenantsHistoryDto;
	}

	public void setAvenantsHistoryDto(List<AvenantHistoryDto> avenantsHistoryDto) {
		this.avenantsHistoryDto = avenantsHistoryDto;
	}
	
}

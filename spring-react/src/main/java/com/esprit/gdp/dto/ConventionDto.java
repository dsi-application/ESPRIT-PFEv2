package com.esprit.gdp.dto;

import java.util.Date;

public class ConventionDto
{
	private String dateConvention;
	
	private Date dateDebut;

	private Date dateFin;
	
	//private String nomSociete;
	private CompanyDto companyDto;

	private String mail;

	private String responsable;

	private String address;

	private String telephone;

	private String traiter;
	
	
	public ConventionDto() {}
	
	public ConventionDto(String dateConvention) {
		super();
		this.dateConvention = dateConvention;
	}

	public ConventionDto(String dateConvention, Date dateDebut, Date dateFin, 
			// String nomSociete, 
			String mail,
			String responsable, String address, String telephone, String traiter) {
		super();
		this.dateConvention = dateConvention;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		// this.nomSociete = nomSociete;
		this.mail = mail;
		this.responsable = responsable;
		this.address = address;
		this.telephone = telephone;
		this.traiter = traiter;
	}
	
	public ConventionDto(String dateConvention, Date dateDebut, Date dateFin, 
			CompanyDto companyDto, 
			String mail,
			String responsable, String address, String telephone, String traiter) {
		super();
		this.dateConvention = dateConvention;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.companyDto = companyDto;
		this.mail = mail;
		this.responsable = responsable;
		this.address = address;
		this.telephone = telephone;
		this.traiter = traiter;
	}

	public String getDateConvention() {
		return dateConvention;
	}

	public void setDateConvention(String dateConvention) {
		this.dateConvention = dateConvention;
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

	
//	public String getNomSociete() {
//		return nomSociete;
//	}
//
//	public void setNomSociete(String nomSociete) {
//		this.nomSociete = nomSociete;
//	}

	public CompanyDto getCompanyDto() {
		return companyDto;
	}

	public void setCompanyDto(CompanyDto companyDto) {
		this.companyDto = companyDto;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
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
	
}

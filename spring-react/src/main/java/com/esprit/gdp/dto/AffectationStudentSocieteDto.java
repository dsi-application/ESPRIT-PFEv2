package com.esprit.gdp.dto;

public class AffectationStudentSocieteDto
{
	private String idEt;

	private String libelleSociete;
	
	private String téléphoneSociete;

	private String emailSociete;

	private String paysSociete;
	
	private String adresseSociete;
	
	private String titreProjet;
	

	
	public AffectationStudentSocieteDto(){}


	public AffectationStudentSocieteDto(String idEt, String libelleSociete, String téléphoneSociete, String emailSociete,
			String paysSociete, String adresseSociete, String titreProjet) {
		this.idEt = idEt;
		this.libelleSociete = libelleSociete;
		this.téléphoneSociete = téléphoneSociete;
		this.emailSociete = emailSociete;
		this.paysSociete = paysSociete;
		this.adresseSociete = adresseSociete;
		this.titreProjet = titreProjet;
	}



	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public String getLibelleSociete() {
		return libelleSociete;
	}

	public void setLibelleSociete(String libelleSociete) {
		this.libelleSociete = libelleSociete;
	}

	public String getTéléphoneSociete() {
		return téléphoneSociete;
	}

	public void setTéléphoneSociete(String téléphoneSociete) {
		this.téléphoneSociete = téléphoneSociete;
	}

	public String getEmailSociete() {
		return emailSociete;
	}

	public void setEmailSociete(String emailSociete) {
		this.emailSociete = emailSociete;
	}

	public String getPaysSociete() {
		return paysSociete;
	}

	public void setPaysSociete(String paysSociete) {
		this.paysSociete = paysSociete;
	}

	public String getAdresseSociete() {
		return adresseSociete;
	}

	public void setAdresseSociete(String adresseSociete) {
		this.adresseSociete = adresseSociete;
	}

	public String getTitreProjet() {
		return titreProjet;
	}

	public void setTitreProjet(String titreProjet) {
		this.titreProjet = titreProjet;
	}
	
	
}
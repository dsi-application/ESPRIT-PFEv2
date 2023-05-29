package com.esprit.gdp.dto;

import java.util.Date;

import com.esprit.gdp.models.EntrepriseAccueil;

public class ConventionsValidatedForRSSDto
{

	private String dateConvention;
	private Date dateDebut;
	private Date dateFin;
	private String idEt;
	private String nomEt;
	// private String departEt;
	private String paysConvention;
	private String pathConvention;
	private String pathSignedConvention;
	private String traiter;
	private EntrepriseAccueil entrepriseAccueilConvention;

	private String currentClasse;


	public ConventionsValidatedForRSSDto() {}

	public ConventionsValidatedForRSSDto(
			String dateConvention, Date dateDebut, Date dateFin,
			String idEt, String traiter
			, String paysConvention, String pathConvention
			, EntrepriseAccueil entrepriseAccueilConvention
	) {
		this.dateConvention = dateConvention;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.idEt = idEt;
		this.traiter = traiter;
		this.paysConvention = paysConvention;
		this.pathConvention = pathConvention;
		this.entrepriseAccueilConvention = entrepriseAccueilConvention;
	}


	public ConventionsValidatedForRSSDto(
			String dateConvention, Date dateDebut, Date dateFin,
			String idEt, String traiter
			, String paysConvention, String pathConvention, String pathSignedConvention
			, EntrepriseAccueil entrepriseAccueilConvention
	) {
		this.dateConvention = dateConvention;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.idEt = idEt;
		this.traiter = traiter;
		this.paysConvention = paysConvention;
		this.pathSignedConvention = pathSignedConvention;
		this.pathConvention = pathConvention;
		this.entrepriseAccueilConvention = entrepriseAccueilConvention;
	}

	/********************************** Getters & Setters **********************************/

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

	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public String getNomEt() {
		return nomEt;
	}

	public void setNomEt(String nomEt) {
		this.nomEt = nomEt;
	}

	/*
	public String getDepartEt() {
		return departEt;
	}

	public void setDepartEt(String departEt) {
		this.departEt = departEt;
	}
	*/

	public String getPaysConvention() {
		return paysConvention;
	}

	public void setPaysConvention(String paysConvention) {
		this.paysConvention = paysConvention;
	}

	public EntrepriseAccueil getEntrepriseAccueilConvention() {
		return entrepriseAccueilConvention;
	}

	public void setEntrepriseAccueilConvention(EntrepriseAccueil entrepriseAccueilConvention) {
		this.entrepriseAccueilConvention = entrepriseAccueilConvention;
	}

	public String getPathConvention() {
		return pathConvention;
	}

	public void setPathConvention(String pathConvention) {
		this.pathConvention = pathConvention;
	}

	public String getTraiter() {
		return traiter;
	}

	public void setTraiter(String traiter) {
		this.traiter = traiter;
	}

	public String getPathSignedConvention() {
		return pathSignedConvention;
	}

	public void setPathSignedConvention(String pathSignedConvention) {
		this.pathSignedConvention = pathSignedConvention;
	}

	public String getCurrentClasse() {
		return currentClasse;
	}

	public void setCurrentClasse(String currentClasse) {
		this.currentClasse = currentClasse;
	}

}

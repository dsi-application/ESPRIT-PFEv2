package com.esprit.gdp.dto;

import java.sql.Timestamp;

public class StudentDetails {

	
	private String idEt;
	private String nomet;
	private String prenomet;
	private String adressemailesp;
	private String emailet;
	private String adresseet;
	private String telet;
	private String classe;
	private String anneeDebInscription;
	private String encadrant;
	private Timestamp dateFiche;
	public String getAnneeDebInscription() {
		return anneeDebInscription;
	}
	public void setAnneeDebInscription(String anneeDebInscription) {
		this.anneeDebInscription = anneeDebInscription;
	}
	public String getIdEt() {
		return idEt;
	}
	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}
	public String getNomet() {
		return nomet;
	}
	public void setNomet(String nomet) {
		this.nomet = nomet;
	}
	public String getPrenomet() {
		return prenomet;
	}
	public void setPrenomet(String prenomet) {
		this.prenomet = prenomet;
	}
	public String getAdressemailesp() {
		return adressemailesp;
	}
	public void setAdressemailesp(String adressemailesp) {
		this.adressemailesp = adressemailesp;
	}
	public String getEmailet() {
		return emailet;
	}
	public void setEmailet(String emailet) {
		this.emailet = emailet;
	}
	public String getAdresseet() {
		return adresseet;
	}
	public void setAdresseet(String adresseet) {
		this.adresseet = adresseet;
	}
	public String getTelet() {
		return telet;
	}
	public void setTelet(String telet) {
		this.telet = telet;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}

	public StudentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentDetails(String idEt, String nomet, String prenomet, String adressemailesp,
		String telet, String classe, String anneeDebInscription) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
	
		this.telet = telet;
		this.classe = classe;
		this.anneeDebInscription = anneeDebInscription;
	}
	public String getEncadrant() {
		return encadrant;
	}
	public void setEncadrant(String encadrant) {
		this.encadrant = encadrant;
	}
	public StudentDetails(String idEt, String nomet, String prenomet, String adressemailesp, String emailet,
			String adresseet, String telet, String classe, String anneeDebInscription, String encadrant) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
		this.classe = classe;
		this.anneeDebInscription = anneeDebInscription;
		this.encadrant = encadrant;
	}
	
	public StudentDetails(String idEt, String nomet) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
	}
	public Timestamp getDateFiche() {
		return dateFiche;
	}
	public void setDateFiche(Timestamp dateFiche) {
		this.dateFiche = dateFiche;
	}
	public StudentDetails(String idEt, String nomet, String prenomet, String adressemailesp, String emailet,
			String adresseet, String telet, String classe, String anneeDebInscription, String encadrant,
			Timestamp dateFiche) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
		this.classe = classe;
		this.anneeDebInscription = anneeDebInscription;
		this.encadrant = encadrant;
		this.dateFiche = dateFiche;
	}
	
	
	public StudentDetails(String idEt, String nomet, String prenomet, String adressemailesp, String emailet,
			String adresseet, String telet, String classe, String anneeDebInscription) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
		this.classe = classe;
		this.anneeDebInscription = anneeDebInscription;
	}
	
}

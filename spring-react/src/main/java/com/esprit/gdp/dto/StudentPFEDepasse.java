package com.esprit.gdp.dto;

import java.sql.Timestamp;
import java.util.Date;

public class StudentPFEDepasse {
	private String idEt;
	private String nomet;
	private String prenomet;
	private String adressemailesp;
	private String emailet;
	private String adresseet;
	private String telet;
	private String classe;
	private String etatFiche;
	private Timestamp dateConvention;
	private Timestamp dateDepotFiche;
	private Date dateDebut;
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
	
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public StudentPFEDepasse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Timestamp getDateConvention() {
		return dateConvention;
	}
	public void setDateConvention(Timestamp dateConvention) {
		this.dateConvention = dateConvention;
	}
	public Timestamp getDateDepotFiche() {
		return dateDepotFiche;
	}
	public void setDateDepotFiche(Timestamp dateDepotFiche) {
		this.dateDepotFiche = dateDepotFiche;
	}
	public String getEtatFiche() {
		return etatFiche;
	}
	public void setEtatFiche(String etatFiche) {
		this.etatFiche = etatFiche;
	}
	public StudentPFEDepasse(String idEt, String nomet, String prenomet, String adressemailesp, String emailet,
			String adresseet, String telet, String classe, Timestamp dateConvention, Timestamp dateDepotFiche,
			Date dateDebut) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
		this.classe = classe;
		this.dateConvention = dateConvention;
		this.dateDepotFiche = dateDepotFiche;
		this.dateDebut = dateDebut;
	}
	public StudentPFEDepasse(String idEt, String nomet, String prenomet, String adressemailesp, String emailet,
			String adresseet, String telet, String classe, String etatFiche, Timestamp dateConvention,
			Timestamp dateDepotFiche, Date dateDebut) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
		this.classe = classe;
		this.etatFiche = etatFiche;
		this.dateConvention = dateConvention;
		this.dateDepotFiche = dateDepotFiche;
		this.dateDebut = dateDebut;
	}
	

	
	
	
}

package com.esprit.gdp.dto;

public class StudentForSoutenance {
	private String idEt;
	private String nomet;
	private String anneDeb;
	private String prenomet;
	private String adressemailesp;
	private String encadrant;
	private String etatFiche;
	private String pathRapport;
	
	
	
	
	public StudentForSoutenance(String idEt, String nomet, String anneDeb, String prenomet, String adressemailesp,
			String encadrant, String etatFiche, String pathRapport) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.anneDeb = anneDeb;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.encadrant = encadrant;
		this.etatFiche = etatFiche;
		this.pathRapport = pathRapport;
	}
	public String getEtatFiche() {
		return etatFiche;
	}
	public void setEtatFiche(String etatFiche) {
		this.etatFiche = etatFiche;
	}
	public StudentForSoutenance(String idEt, String nomet, String anneDeb, String prenomet, String adressemailesp,
			String encadrant, String pathRapport) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.anneDeb = anneDeb;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.encadrant = encadrant;
		this.pathRapport = pathRapport;
	}
	public String getAnneDeb() {
		return anneDeb;
	}
	public void setAnneDeb(String anneDeb) {
		this.anneDeb = anneDeb;
	}
	public StudentForSoutenance(String idEt, String nomet, String prenomet, String adressemailesp, String encadrant,
			String pathRapport) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.encadrant = encadrant;
		this.pathRapport = pathRapport;
	}
	public StudentForSoutenance() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getEncadrant() {
		return encadrant;
	}
	public void setEncadrant(String encadrant) {
		this.encadrant = encadrant;
	}
	public String getPathRapport() {
		return pathRapport;
	}
	public void setPathRapport(String pathRapport) {
		this.pathRapport = pathRapport;
	}
	
	
	
}

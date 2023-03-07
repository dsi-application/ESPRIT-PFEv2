package com.esprit.gdp.dto;

public class StudentSTNExcelDto
{
	private String idEt;
	private String nomet;
	private String prenomet;
	private String adressemailesp;
	private String telet;
	private String classe;
	private String dateDepot;
	private String dateSoutenance;
	private String heureSoutenance;
	private String salle;
	private String juryPresident;
	private String mailJuryPresident;
	private String pedagogicalEncadrant;
	private String mailEncadrant;
	private String expert;
	private String mailExpert;
	private String motifDepotIncomplet;
	private String pays;
	private String mailSociete;
	
	
	public StudentSTNExcelDto() {}
	
	
	public StudentSTNExcelDto(String idEt, String nomet, String prenomet, 
			String adressemailesp, String telet, String classe, 
			String dateDepot, String dateSoutenance,
			String heureSoutenance, String salle, String juryPresident, String mailJuryPresident,
			String pedagogicalEncadrant, String mailEncadrant, String expert, String mailExpert, String motifDepotIncomplet,
			String pays, String mailSociete)
	{
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.telet = telet;
		this.classe = classe;
		this.dateDepot = dateDepot;
		this.dateSoutenance = dateSoutenance;
		this.heureSoutenance = heureSoutenance;
		this.salle = salle;
		this.juryPresident = juryPresident;
		this.mailJuryPresident = mailJuryPresident;
		this.pedagogicalEncadrant = pedagogicalEncadrant;
		this.mailEncadrant = mailEncadrant;
		this.expert = expert;
		this.mailExpert = mailExpert;
		this.motifDepotIncomplet = motifDepotIncomplet;
		this.pays = pays;
		this.mailSociete = mailSociete;
	}
	

	/************************************************ Getters & Setters *************************************************/


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

	public String getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(String dateDepot) {
		this.dateDepot = dateDepot;
	}

	public String getDateSoutenance() {
		return dateSoutenance;
	}

	public void setDateSoutenance(String dateSoutenance) {
		this.dateSoutenance = dateSoutenance;
	}

	public String getHeureSoutenance() {
		return heureSoutenance;
	}

	public void setHeureSoutenance(String heureSoutenance) {
		this.heureSoutenance = heureSoutenance;
	}

	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public String getJuryPresident() {
		return juryPresident;
	}

	public void setJuryPresident(String juryPresident) {
		this.juryPresident = juryPresident;
	}

	public String getPedagogicalEncadrant() {
		return pedagogicalEncadrant;
	}

	public void setPedagogicalEncadrant(String pedagogicalEncadrant) {
		this.pedagogicalEncadrant = pedagogicalEncadrant;
	}

	public String getMailEncadrant() {
		return mailEncadrant;
	}

	public void setMailEncadrant(String mailEncadrant) {
		this.mailEncadrant = mailEncadrant;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

	public String getMailExpert() {
		return mailExpert;
	}

	public void setMailExpert(String mailExpert) {
		this.mailExpert = mailExpert;
	}

	public String getMailJuryPresident() {
		return mailJuryPresident;
	}

	public void setMailJuryPresident(String mailJuryPresident) {
		this.mailJuryPresident = mailJuryPresident;
	}

	public String getMotifDepotIncomplet() {
		return motifDepotIncomplet;
	}

	public void setMotifDepotIncomplet(String motifDepotIncomplet) {
		this.motifDepotIncomplet = motifDepotIncomplet;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getMailSociete() {
		return mailSociete;
	}

	public void setMailSociete(String mailSociete) {
		this.mailSociete = mailSociete;
	}

}

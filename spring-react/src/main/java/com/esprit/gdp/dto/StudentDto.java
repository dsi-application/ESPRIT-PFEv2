package com.esprit.gdp.dto;

public class StudentDto
{
	private String idEt;
	private String nomet;
	private String prenomet;
	private String adressemailesp;
	private String emailet;
	private String adresseet;
	private String telet;
	private String jwtPwd;
	private String statusPlanifySTN;
	private String classe;
	private String dateDepot;
	private String session;
	private String dateSoutenance;
	private String heureSoutenance;
	private String salle;
	private String juryPresident;
	private String mailJuryPresident;
	private String pedagogicalEncadrant;
	private String mailEncadrant;
	private String expert;
	private String mailExpert;
	private String traineeKind;
	
	/*
	private Date datenaisset;
	private String lieunaiset;
	*/
	
	public StudentDto() {}
	
	
	public StudentDto(String idEt, String nomet, String prenomet, String adressemailesp, String emailet, String adresseet, String telet, String jwtPwd)
	{
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
		this.jwtPwd = jwtPwd;
	}
	
	public StudentDto(String idEt, String nomet, String prenomet, String adressemailesp, String emailet, String adresseet, String telet)
	{
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
	}
	
	public StudentDto(String idEt, String nomet, String prenomet, 
			String adressemailesp, String telet, String classe, 
			String dateDepot, String session, String statusPlanifySTN, String traineeKind)
	{
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.telet = telet;
		this.classe = classe;
		this.dateDepot = dateDepot;
		this.session = session;
		this.statusPlanifySTN = statusPlanifySTN;
		this.traineeKind = traineeKind;
	}
	
	public StudentDto(String idEt, String nomet, String prenomet, 
			String adressemailesp, String telet, String classe, 
			String dateDepot, String dateSoutenance,
			String heureSoutenance, String salle, String juryPresident, String mailJuryPresident,
			String pedagogicalEncadrant, String mailEncadrant, String expert, String mailExpert)
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
	}
	
	public StudentDto(String idEt, String nomet, String prenomet)
	{
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
	}
	
	public StudentDto(String prenomet, String nomet)
	{
		this.prenomet = prenomet;
		this.nomet = nomet;
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

	public String getEmailet() {
		return emailet;
	}

	public void setEmailet(String emailet) {
		this.emailet = emailet;
	}

	public String getJwtPwd() {
		return jwtPwd;
	}

	public void setJwtPwd(String jwtPwd) {
		this.jwtPwd = jwtPwd;
	}

	public String getStatusPlanifySTN() {
		return statusPlanifySTN;
	}

	public void setStatusPlanifySTN(String statusPlanifySTN) {
		this.statusPlanifySTN = statusPlanifySTN;
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

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
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

	public String getTraineeKind() {
		return traineeKind;
	}

	public void setTraineeKind(String traineeKind) {
		this.traineeKind = traineeKind;
	}

}

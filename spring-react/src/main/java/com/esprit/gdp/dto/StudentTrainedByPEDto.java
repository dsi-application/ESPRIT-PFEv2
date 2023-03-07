package com.esprit.gdp.dto;

public class StudentTrainedByPEDto
{
	private String identifiant;
	private String fullName;
	private String classe;
	private String dateDepot;
	private String session;
	private String etatFiche;
	private String etatTreatDepot;
	
	
	public StudentTrainedByPEDto() {}
	
	
	public StudentTrainedByPEDto(String identifiant, String fullName, String classe, 
			String dateDepot, String session, String etatFiche, String etatTreatDepot)
	{
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.classe = classe;
		this.dateDepot = dateDepot;
		this.session = session;
		this.etatFiche = etatFiche;
		this.etatTreatDepot = etatTreatDepot;
	}

	
	/************************************************ Getters & Setters *************************************************/

	
	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getEtatFiche() {
		return etatFiche;
	}

	public void setEtatFiche(String etatFiche) {
		this.etatFiche = etatFiche;
	}

	public String getEtatTreatDepot() {
		return etatTreatDepot;
	}

	public void setEtatTreatDepot(String etatTreatDepot) {
		this.etatTreatDepot = etatTreatDepot;
	}
	
}

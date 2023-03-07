package com.esprit.gdp.dto;

public class StudentPlanifySTNDto
{
	private String identifiant;
	private String fullName;
	private String classe;
	private String dateDepot;
	private String session;
	// private String etat;
	private String traineeKind;
	private String etatTreatDepotFichePFE;
	private String dateSoutenance;
	
	public StudentPlanifySTNDto() {}
	
	
	public StudentPlanifySTNDto(String identifiant, String fullName, String classe, 
			String dateDepot, String session, 
			// String etat, 
			String traineeKind//, String etatTreatDepotFichePFE
			)
	{
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.classe = classe;
		this.dateDepot = dateDepot;
		this.session = session;
		// this.etat = etat;
		this.traineeKind = traineeKind;
	}
	
	public StudentPlanifySTNDto(String identifiant, String fullName, String classe, 
			String dateDepot, String session, 
			String traineeKind, String dateSoutenance)
	{
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.classe = classe;
		this.dateDepot = dateDepot;
		this.session = session;
		this.traineeKind = traineeKind;
		this.dateSoutenance = dateSoutenance;
	}
	
	public StudentPlanifySTNDto(String identifiant, String fullName, String classe, 
			String dateDepot, String session, 
			String traineeKind, String dateSoutenance, String etatTreatDepotFichePFE)
	{
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.classe = classe;
		this.dateDepot = dateDepot;
		this.session = session;
		this.traineeKind = traineeKind;
		this.dateSoutenance = dateSoutenance;
		this.etatTreatDepotFichePFE = etatTreatDepotFichePFE;
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

//	public String getEtat() {
//		return etat;
//	}
//
//	public void setEtat(String etat) {
//		this.etat = etat;
//	}

	public String getTraineeKind() {
		return traineeKind;
	}

	public void setTraineeKind(String traineeKind) {
		this.traineeKind = traineeKind;
	}

	public String getDateSoutenance() {
		return dateSoutenance;
	}

	public void setDateSoutenance(String dateSoutenance) {
		this.dateSoutenance = dateSoutenance;
	}

	public String getEtatTreatDepotFichePFE() {
		return etatTreatDepotFichePFE;
	}

	public void setEtatTreatDepotFichePFE(String etatTreatDepotFichePFE) {
		this.etatTreatDepotFichePFE = etatTreatDepotFichePFE;
	}
	
}

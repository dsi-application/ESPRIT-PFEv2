package com.esprit.gdp.dto;

public class StudentTreatDepotDto
{
	private String department;
	private String option;
	private String societeLibelle;
	private String titreProjet;
	private String juryPresudentName;
	private String expertName;
	private String dateDebutStage;
	private String dateFinStage;
	
	public StudentTreatDepotDto() {}
	
	public StudentTreatDepotDto(String department, String option, String societeLibelle, String titreProjet, String juryPresudentName, String expertName, String dateDebutStage, String dateFinStage)
	{
		this.department = department;
		this.option = option;
		this.societeLibelle = societeLibelle;
		this.titreProjet = titreProjet;
		this.juryPresudentName = juryPresudentName;
		this.expertName = expertName;
		this.dateDebutStage = dateDebutStage;
		this.dateFinStage = dateFinStage;
	}
	

	/************************************************ Getters & Setters *************************************************/


	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getSocieteLibelle() {
		return societeLibelle;
	}

	public void setSocieteLibelle(String societeLibelle) {
		this.societeLibelle = societeLibelle;
	}

	public String getTitreProjet() {
		return titreProjet;
	}

	public void setTitreProjet(String titreProjet) {
		this.titreProjet = titreProjet;
	}

	public String getJuryPresudentName() {
		return juryPresudentName;
	}

	public void setJuryPresudentName(String juryPresudentName) {
		this.juryPresudentName = juryPresudentName;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getDateDebutStage() {
		return dateDebutStage;
	}

	public void setDateDebutStage(String dateDebutStage) {
		this.dateDebutStage = dateDebutStage;
	}

	public String getDateFinStage() {
		return dateFinStage;
	}

	public void setDateFinStage(String dateFinStage) {
		this.dateFinStage = dateFinStage;
	}
	
}

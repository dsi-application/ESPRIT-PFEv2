package com.esprit.gdp.dto;

public class StudentGrilleAcademicEncadrantDto
{
	
	private String fullName;
	private String department;
	private String option;
	private String dateDepot;
	private String societeLibelle;
	private String titreProjet;
	private String noteRest;
	
	
	public StudentGrilleAcademicEncadrantDto() {}
	
	
	public StudentGrilleAcademicEncadrantDto(String fullName, String department, String option, String dateDepot,
			String societeLibelle, String titreProjet, String noteRest) {
		this.fullName = fullName;
		this.department = department;
		this.option = option;
		this.dateDepot = dateDepot;
		this.societeLibelle = societeLibelle;
		this.titreProjet = titreProjet;
		this.noteRest = noteRest;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(String dateDepot) {
		this.dateDepot = dateDepot;
	}

	public String getNoteRest() {
		return noteRest;
	}

	public void setNoteRest(String noteRest) {
		this.noteRest = noteRest;
	}

}

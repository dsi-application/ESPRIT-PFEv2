package com.esprit.gdp.dto;

public class AffectationStudentSocieteSujetSocieteDto
{
	private String libelleSociete;
	private String titreProjet;
	private String dateDebutStage;
	private String dateFinStage;

	
	public AffectationStudentSocieteSujetSocieteDto(){}


	public AffectationStudentSocieteSujetSocieteDto(String libelleSociete, String titreProjet, String dateDebutStage, String dateFinStage) {
		this.libelleSociete = libelleSociete;
		this.titreProjet = titreProjet;
		this.dateDebutStage = dateDebutStage;
		this.dateFinStage = dateFinStage;
	}


	public String getLibelleSociete() {
		return libelleSociete;
	}

	public void setLibelleSociete(String libelleSociete) {
		this.libelleSociete = libelleSociete;
	}

	public String getTitreProjet() {
		return titreProjet;
	}

	public void setTitreProjet(String titreProjet) {
		this.titreProjet = titreProjet;
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
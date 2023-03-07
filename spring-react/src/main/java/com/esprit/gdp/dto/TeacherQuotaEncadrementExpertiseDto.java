package com.esprit.gdp.dto;

public class TeacherQuotaEncadrementExpertiseDto
{
	private String identifiant;
	private String up;
	private String nom;
	private Integer nbrEncadrements;
	private Integer nbrExpertises;
	
	public TeacherQuotaEncadrementExpertiseDto() {}
	
	public TeacherQuotaEncadrementExpertiseDto(String identifiant, String up, String nom)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
	}
	
	public TeacherQuotaEncadrementExpertiseDto(String identifiant, String up, String nom, Integer nbrEncadrements, Integer nbrExpertises)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
		this.nbrEncadrements = nbrEncadrements;
		this.nbrExpertises = nbrExpertises;
	}
	

	/************************************************ Getters & Setters *************************************************/


	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public Integer getNbrEncadrements() {
		return nbrEncadrements;
	}

	public void setNbrEncadrements(Integer nbrEncadrements) {
		this.nbrEncadrements = nbrEncadrements;
	}

	public Integer getNbrExpertises() {
		return nbrExpertises;
	}

	public void setNbrExpertises(Integer nbrExpertises) {
		this.nbrExpertises = nbrExpertises;
	}
	
}

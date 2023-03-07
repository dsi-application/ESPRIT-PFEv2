package com.esprit.gdp.dto;

public class TeacherQuotaPresidenceMembreDto
{
	private String identifiant;
	private String up;
	private String nom;
	private Integer nbrPresidences;
	private Integer nbrMembres;
	
	public TeacherQuotaPresidenceMembreDto() {}
	
	public TeacherQuotaPresidenceMembreDto(String identifiant, String up, String nom)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
	}
	
	public TeacherQuotaPresidenceMembreDto(String identifiant, String up, String nom, Integer nbrPresidences, Integer nbrMembres)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
		this.nbrPresidences = nbrPresidences;
		this.nbrMembres = nbrMembres;
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

	public Integer getNbrPresidences() {
		return nbrPresidences;
	}

	public void setNbrPresidences(Integer nbrPresidences) {
		this.nbrPresidences = nbrPresidences;
	}

	public Integer getNbrMembres() {
		return nbrMembres;
	}

	public void setNbrMembres(Integer nbrMembres) {
		this.nbrMembres = nbrMembres;
	}

}

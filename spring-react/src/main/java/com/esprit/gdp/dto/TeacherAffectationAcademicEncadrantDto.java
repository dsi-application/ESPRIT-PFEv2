package com.esprit.gdp.dto;

public class TeacherAffectationAcademicEncadrantDto
{
	private String identifiant;
	private String up;
	private String nom;
	private String type;
	private String mail;
	private String téléphone;
	private Integer nbrEncadrements;
	/*
	private Date datenaisset;
	private String lieunaiset;
	*/
	
	public TeacherAffectationAcademicEncadrantDto() {}
	
	public TeacherAffectationAcademicEncadrantDto(String identifiant, String up, String nom, String type, String mail, String téléphone, Integer nbrEncadrements)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
		this.type = type;
		this.mail = mail;
		this.téléphone = téléphone;
		this.nbrEncadrements = nbrEncadrements;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTéléphone() {
		return téléphone;
	}

	public void setTéléphone(String téléphone) {
		this.téléphone = téléphone;
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
	

}

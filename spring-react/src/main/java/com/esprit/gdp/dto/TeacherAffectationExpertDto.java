package com.esprit.gdp.dto;

public class TeacherAffectationExpertDto
{
	private String identifiant;
	private String up;
	private String nom;
	private String type;
	private String mail;
	private String téléphone;
	private Integer nbrExpertises;
	/*
	private Date datenaisset;
	private String lieunaiset;
	*/
	
	public TeacherAffectationExpertDto() {}
	
	public TeacherAffectationExpertDto(String identifiant, String up, String nom, String type, String mail, String téléphone, Integer nbrExpertises)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
		this.type = type;
		this.mail = mail;
		this.téléphone = téléphone;
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

	public Integer getNbrExpertises() {
		return nbrExpertises;
	}

	public void setNbrExpertises(Integer nbrExpertises) {
		this.nbrExpertises = nbrExpertises;
	}


}

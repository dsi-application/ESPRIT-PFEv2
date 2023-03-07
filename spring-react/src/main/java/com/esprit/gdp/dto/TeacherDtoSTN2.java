package com.esprit.gdp.dto;

public class TeacherDtoSTN2
{
	private String identifiant;
	private String up;
	private String nom;
	private String type;
	private String mail;
	private String téléphone;
	/*
	private Date datenaisset;
	private String lieunaiset;
	*/
	
	public TeacherDtoSTN2() {}
	
	public TeacherDtoSTN2(String identifiant, String up, String nom, String type, String mail,String téléphone)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
		this.type = type;
		this.mail = mail;
		this.téléphone = téléphone;
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
	

}

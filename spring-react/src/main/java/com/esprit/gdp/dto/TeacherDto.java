package com.esprit.gdp.dto;

public class TeacherDto
{
	private String identifiant;
	private String nom;
	private String type;
	private String mail;
	private String téléphone;
	private String action;
	/*
	private Date datenaisset;
	private String lieunaiset;
	*/
	
	public TeacherDto() {}
	
	
	public TeacherDto(String nom, String mail) {
		super();
		this.nom = nom;
		this.mail = mail;
	}
	
	public TeacherDto(String idEns, String nom, String téléphone)
	{
		this.identifiant = idEns;
		this.nom = nom;
		this.téléphone = téléphone;
	}
	
	public TeacherDto(String idEns, String nom, String type, String mail,String téléphone)
	{
		this.identifiant = idEns;
		this.nom = nom;
		this.type = type;
		this.mail = mail;
		this.téléphone = téléphone;
	}
	
	public TeacherDto(String idEns, String nom, String type, String mail,String téléphone, String action)
	{
		this.identifiant = idEns;
		this.nom = nom;
		this.type = type;
		this.mail = mail;
		this.téléphone = téléphone;
		this.action = action;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}

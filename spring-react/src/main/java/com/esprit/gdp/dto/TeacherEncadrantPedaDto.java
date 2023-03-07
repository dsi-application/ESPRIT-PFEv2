package com.esprit.gdp.dto;

public class TeacherEncadrantPedaDto
{
	private String identifiant;
	private String nom;
	private String mail;
	
	public TeacherEncadrantPedaDto() {}

	
	public TeacherEncadrantPedaDto(String idEns, String nom, String mail)
	{
		this.identifiant = idEns;
		this.nom = nom;
		this.mail = mail;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}

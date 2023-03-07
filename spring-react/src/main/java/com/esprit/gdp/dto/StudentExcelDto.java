package com.esprit.gdp.dto;

public class StudentExcelDto
{
	private String nom;
	private String prenom;
	private String phone;
	private String mail;
	
	
	public StudentExcelDto() {}
	
	
	public StudentExcelDto(String nom, String prenom, String phone, String mail)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.mail = mail;
	}

	
	/************************************************ Getters & Setters *************************************************/

	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}

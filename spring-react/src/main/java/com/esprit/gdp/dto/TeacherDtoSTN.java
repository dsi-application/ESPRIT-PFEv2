package com.esprit.gdp.dto;

public class TeacherDtoSTN
{
	private String identifiant;
	private String numTel;
	private String mail;
	private String up;
	private String nom;
	private Integer nbrEncadrements;
	
	public TeacherDtoSTN() {}
	
	public TeacherDtoSTN(String identifiant, String up, String nom)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
	}
	
	public TeacherDtoSTN(String identifiant, String up, String nom, Integer nbrEncadrements)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
		this.nbrEncadrements = nbrEncadrements;
	}
	
	public TeacherDtoSTN(String identifiant, String up, String nom, String numTel, String mail, Integer nbrEncadrements)
	{
		this.identifiant = identifiant;
		this.up = up;
		this.nom = nom;
		this.numTel = numTel;
		this.mail = mail;
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

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
}

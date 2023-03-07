package com.esprit.gdp.dto;

public class TeacherDtoSTN3
{
	private String identifiant;
	private String nom;
	private String up;
	private String action;
	
	
	public TeacherDtoSTN3() {}
	
	public TeacherDtoSTN3(String identifiant, String nom, String up, String action)
	{
		this.identifiant = identifiant;
		this.nom = nom;
		this.up = up;
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

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}

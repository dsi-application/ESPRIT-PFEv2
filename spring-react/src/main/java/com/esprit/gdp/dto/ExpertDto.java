package com.esprit.gdp.dto;

public class ExpertDto
{
	private String identifiant;
	private String cep;
	private String nom;
	private Integer nbrExpertises;
	
	public ExpertDto() {}
	
	public ExpertDto(String identifiant, String cep)
	{
		this.identifiant = identifiant;
		this.cep = cep;
	}
	
	public ExpertDto(String identifiant, String cep, String nom)
	{
		this.identifiant = identifiant;
		this.cep = cep;
		this.nom = nom;
	}
	
	public ExpertDto(String identifiant, String cep, String nom, Integer nbrExpertises)
	{
		this.identifiant = identifiant;
		this.cep = cep;
		this.nom = nom;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNbrExpertises() {
		return nbrExpertises;
	}

	public void setNbrExpertises(Integer nbrExpertises) {
		this.nbrExpertises = nbrExpertises;
	}

	
	
}

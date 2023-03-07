package com.esprit.gdp.dto;

public class BinomePVDetailsDto
{
	
	private String identifiant;
	private String fullName;
	private String currentClass;
	
	public BinomePVDetailsDto() {}
	
	
	public BinomePVDetailsDto(String identifiant, String fullName) {
		this.identifiant = identifiant;
		this.fullName = fullName;
	}
	
	public BinomePVDetailsDto(String identifiant, String fullName, String currentClass) {
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.currentClass = currentClass;
	}


	/************************************************ Getters & Setters *************************************************/

	
	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCurrentClass() {
		return currentClass;
	}

	public void setCurrentClass(String currentClass) {
		this.currentClass = currentClass;
	}


}

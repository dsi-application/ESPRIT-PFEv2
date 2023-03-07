package com.esprit.gdp.dto;

public class BinomeIdFullNameDto
{
	
	private String identifiant;
	private String fullName;
	
	public BinomeIdFullNameDto() {}
	
	
	public BinomeIdFullNameDto(String identifiant, String fullName) {
		this.identifiant = identifiant;
		this.fullName = fullName;
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

}

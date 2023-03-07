package com.esprit.gdp.dto;

public class PaysDto
{
	
	private String codePays;
	private String nomPays;
	private String codePostal;

	public PaysDto() {}
	
	

	public PaysDto(String codePays, String nomPays, String codePostal) {
		this.codePays = codePays;
		this.nomPays = nomPays;
		this.codePostal = codePostal;
	}



	/************************************************ Getters & Setters *************************************************/

	public String getCodePays() {
		return codePays;
	}

	public void setCodePays(String codePays) {
		this.codePays = codePays;
	}

	public String getNomPays() {
		return nomPays;
	}

	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	
}

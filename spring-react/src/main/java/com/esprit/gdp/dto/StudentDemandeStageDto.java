package com.esprit.gdp.dto;

public class StudentDemandeStageDto
{
	
	private String fullName;
	private String classe;

	
	public StudentDemandeStageDto() {}
	
	public StudentDemandeStageDto(String fullName, String classe)
	{
		this.fullName = fullName;
		this.classe = classe;
	}

	
	/************************************************ Getters & Setters *************************************************/

	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
		
}

package com.esprit.gdp.dto;

public class StudentFullNameDto
{
	private String nomet;
	private String prenomet;
	
	public StudentFullNameDto() {}
	
	
	public StudentFullNameDto(String nomet, String prenomet)
	{
		this.nomet = nomet;
		this.prenomet = prenomet;
	}
	
	
	/************************************************ Getters & Setters *************************************************/

	
	public String getNomet() {
		return nomet;
	}

	public void setNomet(String nomet) {
		this.nomet = nomet;
	}

	public String getPrenomet() {
		return prenomet;
	}

	public void setPrenomet(String prenomet) {
		this.prenomet = prenomet;
	}

}

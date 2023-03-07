package com.esprit.gdp.dto;

public class StudentIdNomPrenomDto
{
	private String idet;
	private String nomet;
	private String prenomet;
	
	public StudentIdNomPrenomDto() {}
	
	
	public StudentIdNomPrenomDto(String idet, String nomet, String prenomet)
	{
		this.idet = idet;
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

	public String getIdet() {
		return idet;
	}

	public void setIdet(String idet) {
		this.idet = idet;
	}

}

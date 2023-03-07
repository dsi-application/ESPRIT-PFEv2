package com.esprit.gdp.dto;

public class TechnologyDto
{

	private String name;
	
	private String etat;

	
	public TechnologyDto() {}
	
	public TechnologyDto(String name, String etat) {
		super();
		this.name = name;
		this.etat = etat;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
}

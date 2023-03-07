package com.esprit.gdp.dto;

public class StudentSTNGradDto
{
	private String dateSoutenance;
	private String heureSoutenance;
	private String salle;
	private String juryPresident;
	private String pedagogicalEncadrant;
	private String expert;
	
	
	public StudentSTNGradDto() {}
	
	
	public StudentSTNGradDto(String dateSoutenance,
			String heureSoutenance, String salle, String juryPresident, String pedagogicalEncadrant, String expert)
	{
		this.dateSoutenance = dateSoutenance;
		this.heureSoutenance = heureSoutenance;
		this.salle = salle;
		this.juryPresident = juryPresident;
		this.pedagogicalEncadrant = pedagogicalEncadrant;
		this.expert = expert;
	}
	

	/************************************************ Getters & Setters *************************************************/

	
	public String getDateSoutenance() {
		return dateSoutenance;
	}

	public void setDateSoutenance(String dateSoutenance) {
		this.dateSoutenance = dateSoutenance;
	}

	public String getHeureSoutenance() {
		return heureSoutenance;
	}

	public void setHeureSoutenance(String heureSoutenance) {
		this.heureSoutenance = heureSoutenance;
	}

	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public String getJuryPresident() {
		return juryPresident;
	}

	public void setJuryPresident(String juryPresident) {
		this.juryPresident = juryPresident;
	}

	public String getPedagogicalEncadrant() {
		return pedagogicalEncadrant;
	}

	public void setPedagogicalEncadrant(String pedagogicalEncadrant) {
		this.pedagogicalEncadrant = pedagogicalEncadrant;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

}

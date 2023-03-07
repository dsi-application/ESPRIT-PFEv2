package com.esprit.gdp.dto;

import java.util.Date;

public class FichePFEMultiplePlanificationDto
{
	
	private String pedagogicalEncadrant;
	private String expert;
	private String juryPresident;
	private Date dateSoutenance;
	private String hourStartSoutenance;
	private String hourEndSoutenance;
	private String salleSoutenance;
	
	
	public FichePFEMultiplePlanificationDto() {}
	
	
	public FichePFEMultiplePlanificationDto(String pedagogicalEncadrant, String expert,
			String juryPresident, Date dateSoutenance, String hourStartSoutenance, String hourEndSoutenance,
			String salleSoutenance)
	{
		this.pedagogicalEncadrant = pedagogicalEncadrant;
		this.expert = expert;
		this.juryPresident = juryPresident;
		this.dateSoutenance = dateSoutenance;
		this.hourStartSoutenance = hourStartSoutenance;
		this.hourEndSoutenance = hourEndSoutenance;
		this.salleSoutenance = salleSoutenance;
	}


	/************************************************ Getters & Setters *************************************************/

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

	public String getJuryPresident() {
		return juryPresident;
	}

	public void setJuryPresident(String juryPresident) {
		this.juryPresident = juryPresident;
	}

	public Date getDateSoutenance() {
		return dateSoutenance;
	}

	public void setDateSoutenance(Date dateSoutenance) {
		this.dateSoutenance = dateSoutenance;
	}

	public String getHourStartSoutenance() {
		return hourStartSoutenance;
	}

	public void setHourStartSoutenance(String hourStartSoutenance) {
		this.hourStartSoutenance = hourStartSoutenance;
	}

	public String getHourEndSoutenance() {
		return hourEndSoutenance;
	}

	public void setHourEndSoutenance(String hourEndSoutenance) {
		this.hourEndSoutenance = hourEndSoutenance;
	}

	public String getSalleSoutenance() {
		return salleSoutenance;
	}

	public void setSalleSoutenance(String salleSoutenance) {
		this.salleSoutenance = salleSoutenance;
	}

}

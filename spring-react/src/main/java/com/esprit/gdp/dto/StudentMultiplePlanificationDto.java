package com.esprit.gdp.dto;

import java.util.Date;

public class StudentMultiplePlanificationDto
{
	
	private String identifiant;
	private String fullName;
	private String pedagogicalEncadrant;
	private String membre;
	private String juryPresident;
	private Date dateSoutenance;
	private String hourStartSoutenance;
	private String hourEndSoutenance;
	private String salleSoutenance;
	private String department;
	private String decideEHS;
	private Integer nbrStuTrainedByMembre;
	private Integer nbrStuTrainedByJuryPresident;
	
	
	public StudentMultiplePlanificationDto() {}
	
	
	public StudentMultiplePlanificationDto(String identifiant, String fullName, String pedagogicalEncadrant, String membre,
			String juryPresident, Date dateSoutenance, String hourStartSoutenance, String hourEndSoutenance,
			String salleSoutenance, String department, String decideEHS, Integer nbrStuTrainedByMembre, Integer nbrStuTrainedByJuryPresident)
	{
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.pedagogicalEncadrant = pedagogicalEncadrant;
		this.membre = membre;
		this.juryPresident = juryPresident;
		this.dateSoutenance = dateSoutenance;
		this.hourStartSoutenance = hourStartSoutenance;
		this.hourEndSoutenance = hourEndSoutenance;
		this.salleSoutenance = salleSoutenance;
		this.department = department;
		this.decideEHS = decideEHS;
		this.nbrStuTrainedByMembre = nbrStuTrainedByMembre;
		this.nbrStuTrainedByJuryPresident = nbrStuTrainedByJuryPresident;
	}


	/************************************************ Getters & Setters *************************************************/

	
	public String getFullName() {
		return fullName;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPedagogicalEncadrant() {
		return pedagogicalEncadrant;
	}

	public void setPedagogicalEncadrant(String pedagogicalEncadrant) {
		this.pedagogicalEncadrant = pedagogicalEncadrant;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDecideEHS() {
		return decideEHS;
	}

	public void setDecideEHS(String decideEHS) {
		this.decideEHS = decideEHS;
	}

	public Integer getNbrStuTrainedByJuryPresident() {
		return nbrStuTrainedByJuryPresident;
	}

	public void setNbrStuTrainedByJuryPresident(Integer nbrStuTrainedByJuryPresident) {
		this.nbrStuTrainedByJuryPresident = nbrStuTrainedByJuryPresident;
	}

	public String getMembre() {
		return membre;
	}

	public void setMembre(String membre) {
		this.membre = membre;
	}

	public Integer getNbrStuTrainedByMembre() {
		return nbrStuTrainedByMembre;
	}

	public void setNbrStuTrainedByMembre(Integer nbrStuTrainedByMembre) {
		this.nbrStuTrainedByMembre = nbrStuTrainedByMembre;
	}
	
}

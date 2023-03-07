package com.esprit.gdp.dto;

public class FichePFEPlanificationSTNDto
{
	
	private String dateDepotReports;
	private String dateSoutenance;
	private String trainingKind;
	private String libelleSession;
	
	
	public FichePFEPlanificationSTNDto() {}

	
	public FichePFEPlanificationSTNDto(String dateDepotReports, String dateSoutenance, String trainingKind, String libelleSession)
	{
		this.dateDepotReports = dateDepotReports;
		this.dateSoutenance = dateSoutenance;
		this.trainingKind = trainingKind;
		this.libelleSession = libelleSession;
	}


	public String getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(String dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public String getTrainingKind() {
		return trainingKind;
	}

	public void setTrainingKind(String trainingKind) {
		this.trainingKind = trainingKind;
	}

	public String getLibelleSession() {
		return libelleSession;
	}

	public void setLibelleSession(String libelleSession) {
		this.libelleSession = libelleSession;
	}

	public String getDateSoutenance() {
		return dateSoutenance;
	}

	public void setDateSoutenance(String dateSoutenance) {
		this.dateSoutenance = dateSoutenance;
	}
		
}

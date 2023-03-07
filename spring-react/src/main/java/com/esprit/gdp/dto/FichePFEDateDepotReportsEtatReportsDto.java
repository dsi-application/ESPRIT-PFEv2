package com.esprit.gdp.dto;

public class FichePFEDateDepotReportsEtatReportsDto
{
	private String dateDepotReports;
	private String etatDepotReports;
	
	public FichePFEDateDepotReportsEtatReportsDto() {}

	public FichePFEDateDepotReportsEtatReportsDto(String dateDepotReports, String etatDepotReports) {
		this.dateDepotReports = dateDepotReports;
		this.etatDepotReports = etatDepotReports;
	}

	
	public String getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(String dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public String getEtatDepotReports() {
		return etatDepotReports;
	}

	public void setEtatDepotReports(String etatDepotReports) {
		this.etatDepotReports = etatDepotReports;
	}


}

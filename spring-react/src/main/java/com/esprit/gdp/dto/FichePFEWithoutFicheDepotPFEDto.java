package com.esprit.gdp.dto;

import java.util.Date;


public class FichePFEWithoutFicheDepotPFEDto
{
	 
	private Date dateDepotReports;
	private String sessionLabel;
	private String etatFiche;
	private String etatValidDepot;
	
	
	public FichePFEWithoutFicheDepotPFEDto() {}
	

	public FichePFEWithoutFicheDepotPFEDto(Date dateDepotReports, String sessionLabel, String etatFiche, String etatValidDepot) {
		this.dateDepotReports = dateDepotReports;
		this.sessionLabel = sessionLabel;
		this.etatFiche = etatFiche;
		this.etatValidDepot = etatValidDepot;
	}

	/****************************************************************************/
	
	public Date getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(Date dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public String getSessionLabel() {
		return sessionLabel;
	}

	public void setSessionLabel(String sessionLabel) {
		this.sessionLabel = sessionLabel;
	}

	public String getEtatFiche() {
		return etatFiche;
	}

	public void setEtatFiche(String etatFiche) {
		this.etatFiche = etatFiche;
	}

	public String getEtatValidDepot() {
		return etatValidDepot;
	}

	public void setEtatValidDepot(String etatValidDepot) {
		this.etatValidDepot = etatValidDepot;
	}
	
}

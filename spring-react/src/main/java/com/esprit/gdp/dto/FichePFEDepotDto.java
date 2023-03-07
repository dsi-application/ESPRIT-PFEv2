package com.esprit.gdp.dto;

public class FichePFEDepotDto
{
	
	private String etatFiche;
	private String etatDepot;
	private String motifDepotIncomplet;
	private String dateDepotReports;
	private String dateTreatReports;
	
	
	public FichePFEDepotDto() {}
	
	
	public FichePFEDepotDto(String etatFiche, String etatDepot, String motifDepotIncomplet, String dateDepotReports, String dateTreatReports)
	{
		this.etatFiche = etatFiche;
		this.etatDepot = etatDepot;
		this.motifDepotIncomplet = motifDepotIncomplet;
		this.dateDepotReports = dateDepotReports;
		this.dateTreatReports = dateTreatReports;
	}
	

	/****************************************************************************/


	public String getEtatFiche() {
		return etatFiche;
	}

	public void setEtatFiche(String etatFiche) {
		this.etatFiche = etatFiche;
	}

	public String getEtatDepot() {
		return etatDepot;
	}

	public void setEtatDepot(String etatDepot) {
		this.etatDepot = etatDepot;
	}

	public String getMotifDepotIncomplet() {
		return motifDepotIncomplet;
	}

	public void setMotifDepotIncomplet(String motifDepotIncomplet) {
		this.motifDepotIncomplet = motifDepotIncomplet;
	}

	public String getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(String dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public String getDateTreatReports() {
		return dateTreatReports;
	}

	public void setDateTreatReports(String dateTreatReports) {
		this.dateTreatReports = dateTreatReports;
	}
	
}

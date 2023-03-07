package com.esprit.gdp.dto;

import java.util.Date;


public class FichePFEForFicheDepotPFEDto2
{
	 
	private Date dateDepotReports;
	private Date dateSaisieRDV;
	private String etatTreatCP;
	private String sessionLabel;
	private String etatFiche;
	
	
	public FichePFEForFicheDepotPFEDto2() {}
	

	public FichePFEForFicheDepotPFEDto2(Date dateDepotReports, Date dateSaisieRDV, String etatTreatCP,
			String sessionLabel, String etatFiche) {
		this.dateDepotReports = dateDepotReports;
		this.dateSaisieRDV = dateSaisieRDV;
		this.etatTreatCP = etatTreatCP;
		this.sessionLabel = sessionLabel;
		this.etatFiche = etatFiche;
	}

	/****************************************************************************/
	
	public Date getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(Date dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public Date getDateSaisieRDV() {
		return dateSaisieRDV;
	}

	public void setDateSaisieRDV(Date dateSaisieRDV) {
		this.dateSaisieRDV = dateSaisieRDV;
	}

	public String getEtatTreatCP() {
		return etatTreatCP;
	}

	public void setEtatTreatCP(String etatTreatCP) {
		this.etatTreatCP = etatTreatCP;
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
	
}

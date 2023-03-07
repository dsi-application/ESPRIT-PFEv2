package com.esprit.gdp.dto;


public class FichePFETitreDateDepotStatusDto
{
	private String titreProjet;
	private String dateDepot;
	private String etat;
	
	private String dateRemise;
	private String fullDateDepot;
	
	public FichePFETitreDateDepotStatusDto() {}


	public FichePFETitreDateDepotStatusDto(String titreProjet, String dateDepot, String etat) {
		this.titreProjet = titreProjet;
		this.dateDepot = dateDepot;
		this.etat = etat;
	}
	
	public FichePFETitreDateDepotStatusDto(String titreProjet, String dateDepot, String etat, String fullDateDepot) {
		this.titreProjet = titreProjet;
		this.dateDepot = dateDepot;
		this.etat = etat;
		this.fullDateDepot = fullDateDepot;
	}
	
	public FichePFETitreDateDepotStatusDto(String fullDateDepot) {
		this.fullDateDepot = fullDateDepot;
	}

	public String getTitreProjet() {
		return titreProjet;
	}

	public void setTitreProjet(String titreProjet) {
		this.titreProjet = titreProjet;
	}

	public String getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(String dateDepot) {
		this.dateDepot = dateDepot;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getDateRemise() {
		return dateRemise;
	}

	public void setDateRemise(String dateRemise) {
		this.dateRemise = dateRemise;
	}

	public String getFullDateDepot() {
		return fullDateDepot;
	}

	public void setFullDateDepot(String fullDateDepot) {
		this.fullDateDepot = fullDateDepot;
	}

}

package com.esprit.gdp.dto;

public class TraitementFicheHistoryDto
{
	
	private String dateDepotFiche;
	private String dateSaisieDemande;
	private String dateTrtFiche;
	private String typeTrtFiche;
	private String typeTrtConv;
	private String etatTrt;
	private String description;
	private String motifRefus;
	private String cancellingAgreementPath;
	
	
	public TraitementFicheHistoryDto() {}


	public TraitementFicheHistoryDto(String dateDepotFiche, String dateSaisieDemande, String dateTrtFiche,
			String typeTrtFiche, String typeTrtConv, String etatTrt, String description, String motifRefus, String cancellingAgreementPath) {
		this.dateDepotFiche = dateDepotFiche;
		this.dateSaisieDemande = dateSaisieDemande;
		this.dateTrtFiche = dateTrtFiche;
		this.typeTrtFiche = typeTrtFiche;
		this.typeTrtConv = typeTrtConv;
		this.etatTrt = etatTrt;
		this.description = description;
		this.motifRefus = motifRefus;
		this.cancellingAgreementPath = cancellingAgreementPath;
	}


	/************************************************ Getters & Setters *************************************************/


	public String getDateSaisieDemande() {
		return dateSaisieDemande;
	}

	public String getDateDepotFiche() {
		return dateDepotFiche;
	}

	public void setDateDepotFiche(String dateDepotFiche) {
		this.dateDepotFiche = dateDepotFiche;
	}

	public void setDateSaisieDemande(String dateSaisieDemande) {
		this.dateSaisieDemande = dateSaisieDemande;
	}

	public String getDateTrtFiche() {
		return dateTrtFiche;
	}

	public void setDateTrtFiche(String dateTrtFiche) {
		this.dateTrtFiche = dateTrtFiche;
	}

	public String getTypeTrtFiche() {
		return typeTrtFiche;
	}

	public void setTypeTrtFiche(String typeTrtFiche) {
		this.typeTrtFiche = typeTrtFiche;
	}

	public String getTypeTrtConv() {
		return typeTrtConv;
	}

	public void setTypeTrtConv(String typeTrtConv) {
		this.typeTrtConv = typeTrtConv;
	}

	public String getEtatTrt() {
		return etatTrt;
	}

	public void setEtatTrt(String etatTrt) {
		this.etatTrt = etatTrt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMotifRefus() {
		return motifRefus;
	}

	public void setMotifRefus(String motifRefus) {
		this.motifRefus = motifRefus;
	}

	public String getCancellingAgreementPath() {
		return cancellingAgreementPath;
	}

	public void setCancellingAgreementPath(String cancellingAgreementPath) {
		this.cancellingAgreementPath = cancellingAgreementPath;
	}

}

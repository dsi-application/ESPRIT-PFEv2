package com.esprit.gdp.dto;

public class TraitementFicheDto
{
	
	private String dateAnnulationFiche;
	private String typeTrtFiche;
	private String typeTrtConv;
	private String description;
	private String etatTrt;

	
	public TraitementFicheDto() {}

	public TraitementFicheDto(String description, String etatTrt) {
		super();
		this.description = description;
		this.etatTrt = etatTrt;
	}
	

	public TraitementFicheDto(String dateAnnulationFiche, String description, String etatTrt, String typeTrtConv) {
		super();
		this.dateAnnulationFiche = dateAnnulationFiche;
		this.description = description;
		this.etatTrt = etatTrt;
		this.typeTrtConv = typeTrtConv;
	}


	public String getDateAnnulationFiche() {
		return dateAnnulationFiche;
	}

	public void setDateAnnulationFiche(String dateAnnulationFiche) {
		this.dateAnnulationFiche = dateAnnulationFiche;
	}

	public String getTypeTrtFiche() {
		return typeTrtFiche;
	}

	public void setTypeTrtFiche(String typeTrtFiche) {
		this.typeTrtFiche = typeTrtFiche;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEtatTrt() {
		return etatTrt;
	}

	public void setEtatTrt(String etatTrt) {
		this.etatTrt = etatTrt;
	}

	public String getTypeTrtConv() {
		return typeTrtConv;
	}

	public void setTypeTrtConv(String typeTrtConv) {
		this.typeTrtConv = typeTrtConv;
	}
	
}

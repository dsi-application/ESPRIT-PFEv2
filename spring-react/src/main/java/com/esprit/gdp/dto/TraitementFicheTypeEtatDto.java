package com.esprit.gdp.dto;

public class TraitementFicheTypeEtatDto
{
	
	private String typeTrtFiche;
	private String typeTrtConv;
	private String etatTrt;
	
	public TraitementFicheTypeEtatDto() {}


	public TraitementFicheTypeEtatDto(String typeTrtFiche, String typeTrtConv, String etatTrt)
	{
		this.typeTrtFiche = typeTrtFiche;
		this.typeTrtConv = typeTrtConv;
		this.etatTrt = etatTrt;
	}

	/*****************************************************************************************/

	
	public String getEtatTrt() {
		return etatTrt;
	}

	public void setEtatTrt(String etatTrt) {
		this.etatTrt = etatTrt;
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

	
}

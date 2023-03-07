package com.esprit.gdp.dto;

import java.util.Date;


public class FichePFEForFicheDepotPFEDto
{
	 
	private Date dateSaisieRDV;
	private String etatTreatAE;
	private String etatTreatCP;
	
	
	public FichePFEForFicheDepotPFEDto() {}
	

	public FichePFEForFicheDepotPFEDto(Date dateSaisieRDV, String etatTreatAE, String etatTreatCP)
	{
		this.dateSaisieRDV = dateSaisieRDV;
		this.etatTreatAE = etatTreatAE;
		this.etatTreatCP = etatTreatCP;
	}

	/****************************************************************************/
	
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

	public String getEtatTreatAE() {
		return etatTreatAE;
	}

	public void setEtatTreatAE(String etatTreatAE) {
		this.etatTreatAE = etatTreatAE;
	}

}

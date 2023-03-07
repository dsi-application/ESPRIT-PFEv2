package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public class GrilleAcademicEncadrantPK implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	private FichePFEPK fichePFEPK;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_SAISIE_GAE")
	private Date dateSaisieGAE;

	public GrilleAcademicEncadrantPK() {}

	public GrilleAcademicEncadrantPK(FichePFEPK fichePFEPK, Date dateSaisieGAE)
	{
		this.fichePFEPK = fichePFEPK;
		this.dateSaisieGAE = dateSaisieGAE;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateSaisieGAE == null) ? 0 : dateSaisieGAE.hashCode());
		result = prime * result + ((fichePFEPK == null) ? 0 : fichePFEPK.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrilleAcademicEncadrantPK other = (GrilleAcademicEncadrantPK) obj;
		if (dateSaisieGAE == null) {
			if (other.dateSaisieGAE != null)
				return false;
		} else if (!dateSaisieGAE.equals(other.dateSaisieGAE))
			return false;
		if (fichePFEPK == null) {
			if (other.fichePFEPK != null)
				return false;
		} else if (!fichePFEPK.equals(other.fichePFEPK))
			return false;
		return true;
	}
	
	/************************************ Getters & Setters ************************************/

	public FichePFEPK getFichePFEPK() {
		return fichePFEPK;
	}

	public void setFichePFEPK(FichePFEPK fichePFEPK) {
		this.fichePFEPK = fichePFEPK;
	}

	public Date getDateSaisieGAE() {
		return dateSaisieGAE;
	}

	public void setDateSaisieGAE(Date dateSaisieGAE) {
		this.dateSaisieGAE = dateSaisieGAE;
	}
	

}
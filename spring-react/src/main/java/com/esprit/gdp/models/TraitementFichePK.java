package com.esprit.gdp.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Saria Essid
 *
 */

@Embeddable
public class TraitementFichePK implements Serializable
{

	private static final long serialVersionUID = 816212041324278199L;

	
	private FichePFEPK fichePFEPK;

	@Column(name = "DATE_SAISIE_DEMANDE")
	private Timestamp dateSaisieDemande;
	
	public TraitementFichePK() {}


	public TraitementFichePK(FichePFEPK fichePFEPK, Timestamp dateSaisieDemande) {
		super();
		this.fichePFEPK = fichePFEPK;
		this.dateSaisieDemande = dateSaisieDemande;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateSaisieDemande == null) ? 0 : dateSaisieDemande.hashCode());
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
		TraitementFichePK other = (TraitementFichePK) obj;
		if (dateSaisieDemande == null) {
			if (other.dateSaisieDemande != null)
				return false;
		} else if (!dateSaisieDemande.equals(other.dateSaisieDemande))
			return false;
		if (fichePFEPK == null) {
			if (other.fichePFEPK != null)
				return false;
		} else if (!fichePFEPK.equals(other.fichePFEPK))
			return false;
		return true;
	}


	/**************************************************** Getters & Setters **************************************************/

	
	public FichePFEPK getFichePFEPK() {
		return fichePFEPK;
	}

	public void setFichePFEPK(FichePFEPK fichePFEPK) {
		this.fichePFEPK = fichePFEPK;
	}

	public Timestamp getDateSaisieDemande() {
		return dateSaisieDemande;
	}

	public void setDateSaisieDemande(Timestamp dateSaisieDemande) {
		this.dateSaisieDemande = dateSaisieDemande;
	}

}

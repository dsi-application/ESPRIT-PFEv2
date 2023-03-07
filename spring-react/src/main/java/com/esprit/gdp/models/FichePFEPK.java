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
public class FichePFEPK implements Serializable
{

	private static final long serialVersionUID = 816212041324278199L;

	
    private ConventionPK conventionPK;
	
	@Column(name = "DATE_DEPOT_FICHE")
	private Timestamp dateDepotFiche;
	
	
	public FichePFEPK(){}
	
	public FichePFEPK(ConventionPK conventionPK, Timestamp dateDepotFiche)
	{
		this.conventionPK = conventionPK;
		this.dateDepotFiche = dateDepotFiche;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conventionPK == null) ? 0 : conventionPK.hashCode());
		result = prime * result + ((dateDepotFiche == null) ? 0 : dateDepotFiche.hashCode());
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
		FichePFEPK other = (FichePFEPK) obj;
		if (conventionPK == null) {
			if (other.conventionPK != null)
				return false;
		} else if (!conventionPK.equals(other.conventionPK))
			return false;
		if (dateDepotFiche == null) {
			if (other.dateDepotFiche != null)
				return false;
		} else if (!dateDepotFiche.equals(other.dateDepotFiche))
			return false;
		return true;
	}

	
	/**************************************************** Getters & Setters **************************************************/


	public Timestamp getDateDepotFiche() {
		return dateDepotFiche;
	}

	public ConventionPK getConventionPK() {
		return conventionPK;
	}

	public void setConventionPK(ConventionPK conventionPK) {
		this.conventionPK = conventionPK;
	}

	public void setDateDepotFiche(Timestamp dateDepotFiche) {
		this.dateDepotFiche = dateDepotFiche;
	}

}

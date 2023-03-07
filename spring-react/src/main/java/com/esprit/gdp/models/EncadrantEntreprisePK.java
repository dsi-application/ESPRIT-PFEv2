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
public class EncadrantEntreprisePK implements Serializable
{

	private static final long serialVersionUID = 816212041324278199L;

	
    private FichePFEPK fichePFEPK;
	
	@Column(name = "ID_ENCADRANT", length = 20)
	private String idEncadrant;
	
	
	public EncadrantEntreprisePK(){}
	
	public EncadrantEntreprisePK(FichePFEPK fichePFEPK, String idEncadrant)
	{
		this.fichePFEPK = fichePFEPK;
		this.idEncadrant = idEncadrant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fichePFEPK == null) ? 0 : fichePFEPK.hashCode());
		result = prime * result + ((idEncadrant == null) ? 0 : idEncadrant.hashCode());
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
		EncadrantEntreprisePK other = (EncadrantEntreprisePK) obj;
		if (fichePFEPK == null) {
			if (other.fichePFEPK != null)
				return false;
		} else if (!fichePFEPK.equals(other.fichePFEPK))
			return false;
		if (idEncadrant == null) {
			if (other.idEncadrant != null)
				return false;
		} else if (!idEncadrant.equals(other.idEncadrant))
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

	public String getIdEncadrant() {
		return idEncadrant;
	}

	public void setIdEncadrant(String idEncadrant) {
		this.idEncadrant = idEncadrant;
	}

}

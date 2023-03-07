package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * @author Saria Essid
 *
 */

@Embeddable
public class ProblematiquePK implements Serializable
{

	private static final long serialVersionUID = 816212041324278199L;

	
	private FichePFEPK fichePFEPK;
	
	@Column(name = "NUM_ORDRE", length = 2)
	private Integer numOrdre;

	
	public ProblematiquePK(){}

	
	public ProblematiquePK(FichePFEPK fichePFEPK, Integer numOrdre) {
		super();
		this.fichePFEPK = fichePFEPK;
		this.numOrdre = numOrdre;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fichePFEPK == null) ? 0 : fichePFEPK.hashCode());
		result = prime * result + ((numOrdre == null) ? 0 : numOrdre.hashCode());
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
		ProblematiquePK other = (ProblematiquePK) obj;
		if (fichePFEPK == null) {
			if (other.fichePFEPK != null)
				return false;
		} else if (!fichePFEPK.equals(other.fichePFEPK))
			return false;
		if (numOrdre == null) {
			if (other.numOrdre != null)
				return false;
		} else if (!numOrdre.equals(other.numOrdre))
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

	public Integer getNumOrdre() {
		return numOrdre;
	}

	public void setNumOrdre(Integer numOrdre) {
		this.numOrdre = numOrdre;
	}

}

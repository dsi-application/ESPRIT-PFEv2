package com.esprit.gdp.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ESP_MODULE_PANIER_CLASSE_SAISO database table.
 * 
 */
@Embeddable
public class InscriptionPK implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	
	@Column(name="ID_ET", length = 10)
	private String idEt;

	@Column(name="ANNEE_DEB", length = 4)
	private String anneeDeb;


	public InscriptionPK() {
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anneeDeb == null) ? 0 : anneeDeb.hashCode());
		result = prime * result + ((idEt == null) ? 0 : idEt.hashCode());
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
		InscriptionPK other = (InscriptionPK) obj;
		if (anneeDeb == null) {
			if (other.anneeDeb != null)
				return false;
		} else if (!anneeDeb.equals(other.anneeDeb))
			return false;
		if (idEt == null) {
			if (other.idEt != null)
				return false;
		} else if (!idEt.equals(other.idEt))
			return false;
		return true;
	}


	/**************************************************** Getters & Setters **************************************************/
	
	
	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}
	
}
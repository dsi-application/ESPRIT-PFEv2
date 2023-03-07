package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Embeddable;


/**
 *
 * @author Saria Essid
 *
 */


@Embeddable
public class SettingsId implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPackage;
	private Integer codeNom;
	
	public SettingsId() {}

	public SettingsId(Integer idPackage, Integer codeNom)
	{
		super();
		this.idPackage = idPackage;
		this.codeNom = codeNom;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeNom == null) ? 0 : codeNom.hashCode());
		result = prime * result + ((idPackage == null) ? 0 : idPackage.hashCode());
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
		SettingsId other = (SettingsId) obj;
		if (codeNom == null) {
			if (other.codeNom != null)
				return false;
		} else if (!codeNom.equals(other.codeNom))
			return false;
		if (idPackage == null) {
			if (other.idPackage != null)
				return false;
		} else if (!idPackage.equals(other.idPackage))
			return false;
		return true;
	}

	/********************************************** Getters & Setters **********************************************/

	
	public Integer getCodeNom() {
		return codeNom;
	}

	public Integer getIdPackage() {
		return idPackage;
	}

	public void setIdPackage(Integer idPackage) {
		this.idPackage = idPackage;
	}

	public void setCodeNom(Integer codeNom) {
		this.codeNom = codeNom;
	}

	
}

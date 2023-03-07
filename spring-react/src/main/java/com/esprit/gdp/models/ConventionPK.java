package com.esprit.gdp.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConventionPK implements Serializable
{
	
	private static final long serialVersionUID = 2343680634816011300L;
	
	
	@Column(name = "ID_ET", length = 10)
	private String idEt;
	
	@Column(name = "DATE_CONVENTION")
	private Timestamp dateConvention;
	
	
	public ConventionPK() {}

	public ConventionPK(String idEt, Timestamp dateConvention) {
		this.idEt = idEt;
		this.dateConvention = dateConvention;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateConvention == null) ? 0 : dateConvention.hashCode());
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
		ConventionPK other = (ConventionPK) obj;
		if (dateConvention == null) {
			if (other.dateConvention != null)
				return false;
		} else if (!dateConvention.equals(other.dateConvention))
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

	public Timestamp getDateConvention() {
		return dateConvention;
	}
	
	public void setDateConvention(Timestamp dateConvention) {
		this.dateConvention = dateConvention;
	}
	
}

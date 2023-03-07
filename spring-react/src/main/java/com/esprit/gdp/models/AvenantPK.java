package com.esprit.gdp.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public class AvenantPK implements Serializable
{
	
	private static final long serialVersionUID = 6478099626169638959L;

	
	private ConventionPK conventionPK;
	
	@Column(name = "DATE_AVENANT")
	private Timestamp dateAvenant;

	
	public AvenantPK() {}
	
	
	public AvenantPK(ConventionPK conventionPK, Timestamp dateAvenant) {
		super();
		this.conventionPK = conventionPK;
		this.dateAvenant = dateAvenant;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conventionPK == null) ? 0 : conventionPK.hashCode());
		result = prime * result + ((dateAvenant == null) ? 0 : dateAvenant.hashCode());
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
		AvenantPK other = (AvenantPK) obj;
		if (conventionPK == null) {
			if (other.conventionPK != null)
				return false;
		} else if (!conventionPK.equals(other.conventionPK))
			return false;
		if (dateAvenant == null) {
			if (other.dateAvenant != null)
				return false;
		} else if (!dateAvenant.equals(other.dateAvenant))
			return false;
		return true;
	}


	/**************************************************** Getters & Setters **************************************************/

	
	public Timestamp getDateAvenant() {
		return dateAvenant;
	}

	public void setDateAvenant(Timestamp dateAvenant) {
		this.dateAvenant = dateAvenant;
	}

	public ConventionPK getConventionPK() {
		return conventionPK;
	}

	public void setConventionPK(ConventionPK conventionPK) {
		this.conventionPK = conventionPK;
	}
	
	
}

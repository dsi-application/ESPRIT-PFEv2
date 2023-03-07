package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class VisiteStagiairePK implements Serializable
{

	private static final long serialVersionUID = 8564278981512706190L;

	
	private FichePFEPK fichePFEPK;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_VISITE")
	private Date dateVisite;

	
	public VisiteStagiairePK() {}
	
	public VisiteStagiairePK(FichePFEPK fichePFEPK, Date dateVisite) {
		super();
		this.fichePFEPK = fichePFEPK;
		this.dateVisite = dateVisite;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateVisite == null) ? 0 : dateVisite.hashCode());
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
		VisiteStagiairePK other = (VisiteStagiairePK) obj;
		if (dateVisite == null) {
			if (other.dateVisite != null)
				return false;
		} else if (!dateVisite.equals(other.dateVisite))
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

	public Date getDateVisite() {
		return dateVisite;
	}

	public void setDateVisite(Date dateVisite) {
		this.dateVisite = dateVisite;
	}

}

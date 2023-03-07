package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public class RdvSuiviStagePK implements Serializable{
	
	private FichePFEPK fichePFEPK;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_Saisi_RDV")
	private Date dateSaisieRendezVous;

	public FichePFEPK getFichePFEPK() {
		return fichePFEPK;
	}

	public RdvSuiviStagePK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setFichePFEPK(FichePFEPK fichePFEPK) {
		this.fichePFEPK = fichePFEPK;
	}

	public Date getDateSaisieRendezVous() {
		return dateSaisieRendezVous;
	}

	public void setDateSaisieRendezVous(Date dateSaisieRendezVous) {
		this.dateSaisieRendezVous = dateSaisieRendezVous;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateSaisieRendezVous == null) ? 0 : dateSaisieRendezVous.hashCode());
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
		RdvSuiviStagePK other = (RdvSuiviStagePK) obj;
		if (dateSaisieRendezVous == null) {
			if (other.dateSaisieRendezVous != null)
				return false;
		} else if (!dateSaisieRendezVous.equals(other.dateSaisieRendezVous))
			return false;
		if (fichePFEPK == null) {
			if (other.fichePFEPK != null)
				return false;
		} else if (!fichePFEPK.equals(other.fichePFEPK))
			return false;
		return true;
	}

	public RdvSuiviStagePK(FichePFEPK fichePFEPK, Date dateSaisieRendezVous) {
		super();
		this.fichePFEPK = fichePFEPK;
		this.dateSaisieRendezVous = dateSaisieRendezVous;
	}
	
	
	

	
	
}

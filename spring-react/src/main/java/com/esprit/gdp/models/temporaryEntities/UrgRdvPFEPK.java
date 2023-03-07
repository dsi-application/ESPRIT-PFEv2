package com.esprit.gdp.models.temporaryEntities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esprit.gdp.models.FichePFEPK;


@Embeddable
public class UrgRdvPFEPK implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	private FichePFEPK fichePFEPK;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_Saisi_RDV_URG")
	private Date dateSaisieRDV;

	public UrgRdvPFEPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UrgRdvPFEPK(FichePFEPK fichePFEPK, Date dateSaisieRDV) {
		super();
		this.fichePFEPK = fichePFEPK;
		this.dateSaisieRDV = dateSaisieRDV;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateSaisieRDV == null) ? 0 : dateSaisieRDV.hashCode());
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
		UrgRdvPFEPK other = (UrgRdvPFEPK) obj;
		if (dateSaisieRDV == null) {
			if (other.dateSaisieRDV != null)
				return false;
		} else if (!dateSaisieRDV.equals(other.dateSaisieRDV))
			return false;
		if (fichePFEPK == null) {
			if (other.fichePFEPK != null)
				return false;
		} else if (!fichePFEPK.equals(other.fichePFEPK))
			return false;
		return true;
	}

	public FichePFEPK getFichePFEPK() {
		return fichePFEPK;
	}

	public void setFichePFEPK(FichePFEPK fichePFEPK) {
		this.fichePFEPK = fichePFEPK;
	}

	public Date getDateSaisieRDV() {
		return dateSaisieRDV;
	}

	public void setDateSaisieRDV(Date dateSaisieRDV) {
		this.dateSaisieRDV = dateSaisieRDV;
	}
	
}

package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Embeddable;


/**
 *
 * @author Saria Essid
 *
 */


@Embeddable
public class NomenclaturePaysId implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPays;
	private Integer codeNom;
	
	public NomenclaturePaysId() {}

	public NomenclaturePaysId(Integer idPays, Integer codeNom)
	{
		super();
		this.idPays = idPays;
		this.codeNom = codeNom;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeNom == null) ? 0 : codeNom.hashCode());
		result = prime * result + ((idPays == null) ? 0 : idPays.hashCode());
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
		NomenclaturePaysId other = (NomenclaturePaysId) obj;
		if (codeNom == null) {
			if (other.codeNom != null)
				return false;
		} else if (!codeNom.equals(other.codeNom))
			return false;
		if (idPays == null) {
			if (other.idPays != null)
				return false;
		} else if (!idPays.equals(other.idPays))
			return false;
		return true;
	}

	
	/********************************************** Getters & Setters **********************************************/

	
	public Integer getCodeNom() {
		return codeNom;
	}

	public Integer getIdPays() {
		return idPays;
	}

	public void setIdPays(Integer idPays) {
		this.idPays = idPays;
	}

	public void setCodeNom(Integer codeNom) {
		this.codeNom = codeNom;
	}

	
}

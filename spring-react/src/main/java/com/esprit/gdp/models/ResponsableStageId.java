package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;


@Embeddable
public class ResponsableStageId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -173171847713062100L;
	
	
	@Column(name = "ESP_ID_OPTION", length = 11)
	private String idOption;
	
	
	@Column(name = "ESP_ID_ENS", length = 10)
	private String idEns;
	
	@Column(name = "ANNEE_DEB", length = 4)
	private String anneeDeb;

	public String getIdOption() {
		return idOption;
	}

	public void setIdOption(String idOption) {
		this.idOption = idOption;
	}

	public String getIdEns() {
		return idEns;
	}

	public void setIdEns(String idEns) {
		this.idEns = idEns;
	}

	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anneeDeb == null) ? 0 : anneeDeb.hashCode());
		result = prime * result + ((idEns == null) ? 0 : idEns.hashCode());
		result = prime * result + ((idOption == null) ? 0 : idOption.hashCode());
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
		ResponsableStageId other = (ResponsableStageId) obj;
		if (anneeDeb == null) {
			if (other.anneeDeb != null)
				return false;
		} else if (!anneeDeb.equals(other.anneeDeb))
			return false;
		if (idEns == null) {
			if (other.idEns != null)
				return false;
		} else if (!idEns.equals(other.idEns))
			return false;
		if (idOption == null) {
			if (other.idOption != null)
				return false;
		} else if (!idOption.equals(other.idOption))
			return false;
		return true;
	}

	public ResponsableStageId() {
		super();
	}

}

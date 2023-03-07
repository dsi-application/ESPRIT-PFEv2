package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OptionPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE_OPTION", length = 11)
	private String codeOption;
	
	@Column(name = "ANNEE_DEB")
	private String anneeDeb;

	
	public OptionPK() {}
	
	public OptionPK(String codeOption, String anneeDeb) {
		super();
		this.codeOption = codeOption;
		this.anneeDeb = anneeDeb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anneeDeb == null) ? 0 : anneeDeb.hashCode());
		result = prime * result + ((codeOption == null) ? 0 : codeOption.hashCode());
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
		OptionPK other = (OptionPK) obj;
		if (anneeDeb == null) {
			if (other.anneeDeb != null)
				return false;
		} else if (!anneeDeb.equals(other.anneeDeb))
			return false;
		if (codeOption == null) {
			if (other.codeOption != null)
				return false;
		} else if (!codeOption.equals(other.codeOption))
			return false;
		return true;
	}

	public String getCodeOption() {
		return codeOption;
	}

	public void setCodeOption(String codeOption) {
		this.codeOption = codeOption;
	}

	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}
	
}

package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author Saria Essid
 *
 */


@Embeddable
public class CodeNomenclatureId implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "CODE_STR", length = 3)
	private String codeStr;

	@Column(name = "CODE_NOME", length = 10)
	private String codeNome;
	
	
	public CodeNomenclatureId() {}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeNome == null) ? 0 : codeNome.hashCode());
		result = prime * result + ((codeStr == null) ? 0 : codeStr.hashCode());
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
		CodeNomenclatureId other = (CodeNomenclatureId) obj;
		if (codeNome == null) {
			if (other.codeNome != null)
				return false;
		} else if (!codeNome.equals(other.codeNome))
			return false;
		if (codeStr == null) {
			if (other.codeStr != null)
				return false;
		} else if (!codeStr.equals(other.codeStr))
			return false;
		return true;
	}


	/**************************************************** Getters & Setters **************************************************/

	
	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

	public String getCodeNome() {
		return codeNome;
	}

	public void setCodeNome(String codeNome) {
		this.codeNome = codeNome;
	}
	
}

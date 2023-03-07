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
public class CodeNomenclatureTechnologyExpertiseId implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "CODE_STR_CEP", length = 10)
	private String codeStrTechnologyExpertise;

	@Column(name = "CODE_NOME_TE", length = 10)
	private String codeNomeTechnologyExpertise;
	
	@Column(name = "ANNEE_DEB", length = 4)
	private String annee_deb;
	
	public CodeNomenclatureTechnologyExpertiseId() {}

	public CodeNomenclatureTechnologyExpertiseId(String codeStrTechnologyExpertise, String codeNomeTechnologyExpertise,
			String annee_deb) {
		super();
		this.codeStrTechnologyExpertise = codeStrTechnologyExpertise;
		this.codeNomeTechnologyExpertise = codeNomeTechnologyExpertise;
		this.annee_deb = annee_deb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annee_deb == null) ? 0 : annee_deb.hashCode());
		result = prime * result + ((codeNomeTechnologyExpertise == null) ? 0 : codeNomeTechnologyExpertise.hashCode());
		result = prime * result + ((codeStrTechnologyExpertise == null) ? 0 : codeStrTechnologyExpertise.hashCode());
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
		CodeNomenclatureTechnologyExpertiseId other = (CodeNomenclatureTechnologyExpertiseId) obj;
		if (annee_deb == null) {
			if (other.annee_deb != null)
				return false;
		} else if (!annee_deb.equals(other.annee_deb))
			return false;
		if (codeNomeTechnologyExpertise == null) {
			if (other.codeNomeTechnologyExpertise != null)
				return false;
		} else if (!codeNomeTechnologyExpertise.equals(other.codeNomeTechnologyExpertise))
			return false;
		if (codeStrTechnologyExpertise == null) {
			if (other.codeStrTechnologyExpertise != null)
				return false;
		} else if (!codeStrTechnologyExpertise.equals(other.codeStrTechnologyExpertise))
			return false;
		return true;
	}

	
	public String getCodeStrTechnologyExpertise() {
		return codeStrTechnologyExpertise;
	}

	public void setCodeStrTechnologyExpertise(String codeStrTechnologyExpertise) {
		this.codeStrTechnologyExpertise = codeStrTechnologyExpertise;
	}

	public String getCodeNomeTechnologyExpertise() {
		return codeNomeTechnologyExpertise;
	}

	public void setCodeNomeTechnologyExpertise(String codeNomeTechnologyExpertise) {
		this.codeNomeTechnologyExpertise = codeNomeTechnologyExpertise;
	}

	public String getAnnee_deb() {
		return annee_deb;
	}

	public void setAnnee_deb(String annee_deb) {
		this.annee_deb = annee_deb;
	}

}

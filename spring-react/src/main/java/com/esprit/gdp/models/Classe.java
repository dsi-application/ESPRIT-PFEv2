package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SYN_CLASSE")
public class Classe  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CODE_CL", length = 10)
	private String codeCl;
	
	@Column(name = "CODE_SPECIALITE", length = 4)
	private String codeSpecialite;

	@Column(name = "LIB_SPECIALITE", length = 1000)
	private String libSpecialite;
	
	@OneToMany(mappedBy = "classe")
    public List<SaisonClasse> saisonClasse;

	
	
	public Classe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodeCl() {
		return codeCl;
	}

	public void setCodeCl(String codeCl) {
		this.codeCl = codeCl;
	}

	public String getCodeSpecialite() {
		return codeSpecialite;
	}

	public void setCodeSpecialite(String codeSpecialite) {
		this.codeSpecialite = codeSpecialite;
	}

	public String getLibSpecialite() {
		return libSpecialite;
	}

	public void setLibSpecialite(String libSpecialite) {
		this.libSpecialite = libSpecialite;
	}

	public List<SaisonClasse> getSaisonClasse() {
		return saisonClasse;
	}

	public void setSaisonClasse(List<SaisonClasse> saisonClasse) {
		this.saisonClasse = saisonClasse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeCl == null) ? 0 : codeCl.hashCode());
		result = prime * result + ((codeSpecialite == null) ? 0 : codeSpecialite.hashCode());
		result = prime * result + ((libSpecialite == null) ? 0 : libSpecialite.hashCode());
		result = prime * result + ((saisonClasse == null) ? 0 : saisonClasse.hashCode());
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
		Classe other = (Classe) obj;
		if (codeCl == null) {
			if (other.codeCl != null)
				return false;
		} else if (!codeCl.equals(other.codeCl))
			return false;
		if (codeSpecialite == null) {
			if (other.codeSpecialite != null)
				return false;
		} else if (!codeSpecialite.equals(other.codeSpecialite))
			return false;
		if (libSpecialite == null) {
			if (other.libSpecialite != null)
				return false;
		} else if (!libSpecialite.equals(other.libSpecialite))
			return false;
		if (saisonClasse == null) {
			if (other.saisonClasse != null)
				return false;
		} else if (!saisonClasse.equals(other.saisonClasse))
			return false;
		return true;
	}

	public Classe(String codeCl, String codeSpecialite, String libSpecialite, List<SaisonClasse> saisonClasse) {
		super();
		this.codeCl = codeCl;
		this.codeSpecialite = codeSpecialite;
		this.libSpecialite = libSpecialite;
		this.saisonClasse = saisonClasse;
	}
	
	

}

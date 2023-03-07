package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SaisonClassePK implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE_CL", length = 10)
	private String codeCl;

	@Column(name="ANNEE_DEB", length = 4)
	private String anneeDeb;
	
	@Column(name="SEMESTRE", length = 4)
	private String semester;

	
	
	public SaisonClassePK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodeCl() {
		return codeCl;
	}

	public void setCodeCl(String codeCl) {
		this.codeCl = codeCl;
	}

	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public SaisonClassePK(String codeCl, String anneeDeb, String semester) {
		super();
		this.codeCl = codeCl;
		this.anneeDeb = anneeDeb;
		this.semester = semester;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anneeDeb == null) ? 0 : anneeDeb.hashCode());
		result = prime * result + ((codeCl == null) ? 0 : codeCl.hashCode());
		result = prime * result + ((semester == null) ? 0 : semester.hashCode());
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
		SaisonClassePK other = (SaisonClassePK) obj;
		if (anneeDeb == null) {
			if (other.anneeDeb != null)
				return false;
		} else if (!anneeDeb.equals(other.anneeDeb))
			return false;
		if (codeCl == null) {
			if (other.codeCl != null)
				return false;
		} else if (!codeCl.equals(other.codeCl))
			return false;
		if (semester == null) {
			if (other.semester != null)
				return false;
		} else if (!semester.equals(other.semester))
			return false;
		return true;
	}
	
	
}

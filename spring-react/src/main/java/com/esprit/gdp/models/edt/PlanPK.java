package com.esprit.gdp.models.edt;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ESP_MODULE_PANIER_CLASSE_SAISO database table.
 * 
 */
@Embeddable
public class PlanPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE_MODULE")
	private String codeModule;

	@Column(name = "CODE_CL")
	private String codeCl;

	@Column(name = "ANNEE_DEB")
	private String anneeDeb;

	@Column(name = "NUM_SEMESTRE")
	private long numSemestre;

	public PlanPK() {
	}

	public String getCodeModule() {
		return this.codeModule;
	}

	public void setCodeModule(String codeModule) {
		this.codeModule = codeModule;
	}

	public String getCodeCl() {
		return this.codeCl;
	}

	public void setCodeCl(String codeCl) {
		this.codeCl = codeCl;
	}

	public String getAnneeDeb() {
		return this.anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	public long getNumSemestre() {
		return this.numSemestre;
	}

	public void setNumSemestre(long numSemestre) {
		this.numSemestre = numSemestre;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlanPK)) {
			return false;
		}
		PlanPK castOther = (PlanPK) other;
		return this.codeModule.equals(castOther.codeModule) && this.codeCl.equals(castOther.codeCl)
				&& this.anneeDeb.equals(castOther.anneeDeb) && (this.numSemestre == castOther.numSemestre);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codeModule.hashCode();
		hash = hash * prime + this.codeCl.hashCode();
		hash = hash * prime + this.anneeDeb.hashCode();
		hash = hash * prime + ((int) (this.numSemestre ^ (this.numSemestre >>> 32)));

		return hash;
	}
}
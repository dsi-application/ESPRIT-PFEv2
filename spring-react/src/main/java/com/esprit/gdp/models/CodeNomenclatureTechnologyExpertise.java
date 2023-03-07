package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CLASE database table.
 * 
 */

@Entity
@Table(name = "ESP_CODE_NOMENCLATURE_TE")
public class CodeNomenclatureTechnologyExpertise implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CodeNomenclatureTechnologyExpertiseId cntei;
	
	@Column(name = "LIB_NOME_TE", length = 200)
	private String libNomeTE;
	
	@ManyToOne
	@JoinColumns({ 
	    @JoinColumn(name = "ANNEE_DEB", referencedColumnName = "ANNEE_DEB", insertable = false, updatable = false),
	    @JoinColumn(name = "CODE_STR_CEP", referencedColumnName = "CODE_STR_CEP", insertable = false, updatable = false),
	})
	private StrNomeCEP strNomeCEP;

	
	public CodeNomenclatureTechnologyExpertise() {}
	
	public CodeNomenclatureTechnologyExpertiseId getCntei() {
		return cntei;
	}

	public void setCntei(CodeNomenclatureTechnologyExpertiseId cntei) {
		this.cntei = cntei;
	}

	public String getLibNomeTE() {
		return libNomeTE;
	}

	public void setLibNomeTE(String libNomeTE) {
		this.libNomeTE = libNomeTE;
	}

	public StrNomeCEP getStrNomeCEP() {
		return strNomeCEP;
	}

	public void setStrNomeCEP(StrNomeCEP strNomeCEP) {
		this.strNomeCEP = strNomeCEP;
	}

	
}

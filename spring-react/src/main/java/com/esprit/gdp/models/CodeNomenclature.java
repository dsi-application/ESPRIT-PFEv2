package com.esprit.gdp.models;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CLASE database table.
 * 
 */

@Entity
@Table(name = "SYN_CODE_NOMENCLATURE")
public class CodeNomenclature implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CodeNomenclatureId cni;
	
	@Column(name = "LIB_NOME", length = 100)
	private String libNome;
	
	@ManyToOne
	@JoinColumn(name="CODE_STR", updatable=false, insertable=false, nullable=false)
	private StrNome strNome;

	
	
	public CodeNomenclature() {}

	public CodeNomenclature(CodeNomenclatureId cni, String libNome, StrNome strNome) {
		super();
		this.cni = cni;
		this.libNome = libNome;
		this.strNome = strNome;
	}

	
	
	public CodeNomenclatureId getCni() {
		return cni;
	}

	public void setCni(CodeNomenclatureId cni) {
		this.cni = cni;
	}

	public String getLibNome() {
		return libNome;
	}

	public void setLibNome(String libNome) {
		this.libNome = libNome;
	}

	public StrNome getStrNome() {
		return strNome;
	}

	public void setStrNome(StrNome strNome) {
		this.strNome = strNome;
	}
	
}

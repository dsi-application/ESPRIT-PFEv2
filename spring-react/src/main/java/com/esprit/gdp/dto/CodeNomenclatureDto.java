package com.esprit.gdp.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.esprit.gdp.models.StrNome;

public class CodeNomenclatureDto
{

	private String codeStr;

	private String codeNome;
	
	private String libNome;

	
	public CodeNomenclatureDto() {}
	
	public CodeNomenclatureDto(String codeNome, String libNome) {
		this.codeNome = codeNome;
		this.libNome = libNome;
	}
	
	public CodeNomenclatureDto(String codeStr, String codeNome, String libNome) {
		super();
		this.codeStr = codeStr;
		this.codeNome = codeNome;
		this.libNome = libNome;
	}

	
	
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

	public String getLibNome() {
		return libNome;
	}

	public void setLibNome(String libNome) {
		this.libNome = libNome;
	}
	
}

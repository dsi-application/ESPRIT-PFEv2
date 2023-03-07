package com.esprit.gdp.dto;

import javax.persistence.Column;

public class InscriptionDto {
	
	private String idEt;

	private String idEns;
	
	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
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

	private String anneeDeb;

	public InscriptionDto(String idEt, String idEns, String anneeDeb) {
		super();
		this.idEt = idEt;
		this.idEns = idEns;
		this.anneeDeb = anneeDeb;
	}

	public InscriptionDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}

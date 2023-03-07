package com.esprit.gdp.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.esprit.gdp.models.FichePFE;

public class FicheETDetails {
	private String idEt;
	private String nomet;
	private String prenomet;
	private FichePFE FichePFE;
	public String getIdEt() {
		return idEt;
	}
	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}
	public String getNomet() {
		return nomet;
	}
	public void setNomet(String nomet) {
		this.nomet = nomet;
	}
	public String getPrenomet() {
		return prenomet;
	}
	public void setPrenomet(String prenomet) {
		this.prenomet = prenomet;
	}
	public FichePFE getFichePFE() {
		return FichePFE;
	}
	public void setFichePFE(FichePFE fichePFE) {
		FichePFE = fichePFE;
	}
	public FicheETDetails(String idEt, String nomet, String prenomet, com.esprit.gdp.models.FichePFE fichePFE) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		FichePFE = fichePFE;
	}
	public FicheETDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}

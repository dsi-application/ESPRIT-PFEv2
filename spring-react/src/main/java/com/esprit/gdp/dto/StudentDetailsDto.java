package com.esprit.gdp.dto;

import java.sql.Timestamp;

public class StudentDetailsDto {


	private String idEt;
	private String nomet;
	private String prenomet;
	private String adressemailesp;
	private String telet;

	public StudentDetailsDto() {
		super();
	}

	public StudentDetailsDto(String idEt, String nomet, String prenomet, String adressemailesp, String telet) {
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.adressemailesp = adressemailesp;
		this.telet = telet;
	}

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

	public String getAdressemailesp() {
		return adressemailesp;
	}

	public void setAdressemailesp(String adressemailesp) {
		this.adressemailesp = adressemailesp;
	}

	public String getTelet() {
		return telet;
	}

	public void setTelet(String telet) {
		this.telet = telet;
	}

}

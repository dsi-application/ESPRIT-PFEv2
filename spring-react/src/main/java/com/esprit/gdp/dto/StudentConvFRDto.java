package com.esprit.gdp.dto;

import org.springframework.data.jpa.repository.Query;

public class StudentConvFRDto
{
	private String fullName;
	
	
	private String datenaisset;
	private String lieunaiset;
	private String nationalite;
	private String adresseet;
	private String telet;
	private String adressemailesp;
	
	
	public StudentConvFRDto() {}
	
//	CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), 
//	u.datenaisset, 
//	u.lieunaiset, 
//	u.nationalite, 
//	u.adresseet, 
//	u.telet, 
//	u.adressemailesp
	
	public StudentConvFRDto(String fullName, String datenaisset, String lieunaiset, String nationalite,
			String adresseet, String telet, String adressemailesp) {
		this.fullName = fullName;
		this.datenaisset = datenaisset;
		this.lieunaiset = lieunaiset;
		this.nationalite = nationalite;
		this.adresseet = adresseet;
		this.telet = telet;
		this.adressemailesp = adressemailesp;
	}


	/************************************************ Getters & Setters *************************************************/

	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDatenaisset() {
		return datenaisset;
	}

	public void setDatenaisset(String datenaisset) {
		this.datenaisset = datenaisset;
	}

	public String getLieunaiset() {
		return lieunaiset;
	}

	public void setLieunaiset(String lieunaiset) {
		this.lieunaiset = lieunaiset;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getAdresseet() {
		return adresseet;
	}

	public void setAdresseet(String adresseet) {
		this.adresseet = adresseet;
	}

	public String getTelet() {
		return telet;
	}

	public void setTelet(String telet) {
		this.telet = telet;
	}

	public String getAdressemailesp() {
		return adressemailesp;
	}

	public void setAdressemailesp(String adressemailesp) {
		this.adressemailesp = adressemailesp;
	}

	
}

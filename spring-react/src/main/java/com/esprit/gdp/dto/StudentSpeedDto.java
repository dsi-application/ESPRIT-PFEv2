package com.esprit.gdp.dto;

public class StudentSpeedDto
{
	private String fullName;
	private String adressemailesp;
	private String emailet;
	private String adresseet;
	private String telet;
	private String dateModifyJwtPwd;
	
	
	public StudentSpeedDto() {}
	
	
	public StudentSpeedDto(String fullName, String adressemailesp, String emailet, String adresseet, String telet, String dateModifyJwtPwd)
	{
		this.fullName = fullName;
		this.adressemailesp = adressemailesp;
		this.emailet = emailet;
		this.adresseet = adresseet;
		this.telet = telet;
		this.dateModifyJwtPwd= dateModifyJwtPwd;
	}
	

	/************************************************ Getters & Setters *************************************************/


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(String dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

	public String getAdressemailesp() {
		return adressemailesp;
	}

	public void setAdressemailesp(String adressemailesp) {
		this.adressemailesp = adressemailesp;
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

	public String getEmailet() {
		return emailet;
	}

	public void setEmailet(String emailet) {
		this.emailet = emailet;
	}

}

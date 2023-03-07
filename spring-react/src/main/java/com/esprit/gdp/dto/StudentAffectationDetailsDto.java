package com.esprit.gdp.dto;

public class StudentAffectationDetailsDto {

	
	private String idEt;
	private String fullName;
	private String univerYear;
	private String classe;
	private String adressemailesp;
	private String phone;
	private String option;
	
	
	public StudentAffectationDetailsDto(String univerYear) {
		this.univerYear = univerYear;
	}
	
	public StudentAffectationDetailsDto(String idEt, String fullName, String univerYear, String classe,
			String adressemailesp, String phone, String option) {
		super();
		this.idEt = idEt;
		this.fullName = fullName;
		this.univerYear = univerYear;
		this.classe = classe;
		this.adressemailesp = adressemailesp;
		this.phone = phone;
		this.option = option;
	}

	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUniverYear() {
		return univerYear;
	}

	public void setUniverYear(String univerYear) {
		this.univerYear = univerYear;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getAdressemailesp() {
		return adressemailesp;
	}

	public void setAdressemailesp(String adressemailesp) {
		this.adressemailesp = adressemailesp;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	
}

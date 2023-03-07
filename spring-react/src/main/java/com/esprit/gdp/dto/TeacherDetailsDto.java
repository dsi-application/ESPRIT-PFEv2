package com.esprit.gdp.dto;

public class TeacherDetailsDto
{
	private String idEns;
	private String nomEns;
	private String mailEns;
	private String tel1;
	private String tel2;
	
	public TeacherDetailsDto() {}
	

	public TeacherDetailsDto(String idEns, String nomEns, String mailEns, String tel1, String tel2)
	{
		this.idEns = idEns;
		this.nomEns = nomEns;
		this.mailEns = mailEns;
		this.tel1 = tel1;
		this.tel2 = tel2;
	}

	
	/************************************************ Getters & Setters *************************************************/


	public String getIdEns() {
		return idEns;
	}

	public void setIdEns(String idEns) {
		this.idEns = idEns;
	}

	public String getNomEns() {
		return nomEns;
	}

	public void setNomEns(String nomEns) {
		this.nomEns = nomEns;
	}

	public String getMailEns() {
		return mailEns;
	}

	public void setMailEns(String mailEns) {
		this.mailEns = mailEns;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	

}

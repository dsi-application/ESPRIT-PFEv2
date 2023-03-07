package com.esprit.gdp.dto;

public class StudentMailTelDto
{
	private String mail;
	private String tel;
	
	public StudentMailTelDto() {}
	
	
	public StudentMailTelDto(String mail, String tel)
	{
		this.mail = mail;
		this.tel = tel;
	}
	
	
	/************************************************ Getters & Setters *************************************************/

	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}	

}

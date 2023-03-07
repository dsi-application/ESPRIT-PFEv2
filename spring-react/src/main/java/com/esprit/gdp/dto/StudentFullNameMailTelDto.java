package com.esprit.gdp.dto;

public class StudentFullNameMailTelDto
{
	
	private String id;
	private String fullName;
	private String mail;
	private String tel;
	private String affected;
	private String option;
	private String noteRestitution;
	
	public StudentFullNameMailTelDto() {}
	
	
	public StudentFullNameMailTelDto(String id, String fullName, String mail, String tel, String affected)
	{
		this.id = id;
		this.fullName = fullName;
		this.mail = mail;
		this.tel = tel;
		this.affected = affected;
	}
	
	public StudentFullNameMailTelDto(String id, String fullName, String mail, String tel, String affected, String option)
	{
		this.id = id;
		this.fullName = fullName;
		this.mail = mail;
		this.tel = tel;
		this.affected = affected;
		this.option = option;
	}
	
	public StudentFullNameMailTelDto(String id, String fullName, String mail, String tel, String affected, String option, String noteRestitution)
	{
		this.id = id;
		this.fullName = fullName;
		this.mail = mail;
		this.tel = tel;
		this.affected = affected;
		this.option = option;
		this.noteRestitution = noteRestitution;
	}
	/************************************************ Getters & Setters *************************************************/

	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAffected() {
		return affected;
	}

	public void setAffected(String affected) {
		this.affected = affected;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getNoteRestitution() {
		return noteRestitution;
	}

	public void setNoteRestitution(String noteRestitution) {
		this.noteRestitution = noteRestitution;
	}	

}

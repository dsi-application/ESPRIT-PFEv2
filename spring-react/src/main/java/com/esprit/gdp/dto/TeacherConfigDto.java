package com.esprit.gdp.dto;


public class TeacherConfigDto
{

	private String idTeacher;
	private String telTeacher;
	private String mailTeacher;
	private String pwdJWTTeacher;
	private String dtCreationPwdJWTTeacher;
	
	private String fullNameTeacher;
	private String upTeacher;

	public TeacherConfigDto() {
		super();
	}

	
	public TeacherConfigDto(String idTeacher, String telTeacher, String mailTeacher, String fullNameTeacher, String upTeacher) {
		super();
		this.idTeacher = idTeacher;
		this.telTeacher = telTeacher;
		this.mailTeacher = mailTeacher;
		this.fullNameTeacher = fullNameTeacher;
		this.upTeacher = upTeacher;
	}

	
	/************************************************ Getters & Setters *************************************************/
	
	public String getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(String idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getTelTeacher() {
		return telTeacher;
	}

	public void setTelTeacher(String telTeacher) {
		this.telTeacher = telTeacher;
	}

	public String getMailTeacher() {
		return mailTeacher;
	}

	public void setMailTeacher(String mailTeacher) {
		this.mailTeacher = mailTeacher;
	}

	public String getPwdJWTTeacher() {
		return pwdJWTTeacher;
	}

	public void setPwdJWTTeacher(String pwdJWTTeacher) {
		this.pwdJWTTeacher = pwdJWTTeacher;
	}

	public String getDtCreationPwdJWTTeacher() {
		return dtCreationPwdJWTTeacher;
	}

	public void setDtCreationPwdJWTTeacher(String dtCreationPwdJWTTeacher) {
		this.dtCreationPwdJWTTeacher = dtCreationPwdJWTTeacher;
	}

	public String getFullNameTeacher() {
		return fullNameTeacher;
	}

	public void setFullNameTeacher(String fullNameTeacher) {
		this.fullNameTeacher = fullNameTeacher;
	}

	public String getUpTeacher() {
		return upTeacher;
	}

	public void setUpTeacher(String upTeacher) {
		this.upTeacher = upTeacher;
	}
	
}

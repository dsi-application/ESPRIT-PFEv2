package com.esprit.gdp.dto;


public class StudentConfigDto
{

	private String idStudent;
	private String telStudent;
	private String mailStudent;
	private String pwdJWTStudent;
	private String dtCreationPwdJWTStudent;
	
	private String fullNameStudent;
	private String classeStudent;
	private String optionStudent;

	public StudentConfigDto() {
		super();
	}

	public StudentConfigDto(String idStudent, String telStudent, String mailStudent, String fullNameStudent, String classeStudent, String optionStudent) {
		super();
		this.idStudent = idStudent;
		this.telStudent = telStudent;
		this.mailStudent = mailStudent;
		this.fullNameStudent = fullNameStudent;
		this.classeStudent = classeStudent;
		this.optionStudent = optionStudent;
	}


	public StudentConfigDto(String idStudent, String telStudent, String mailStudent, String fullNameStudent, String classeStudent) {
		super();
		this.idStudent = idStudent;
		this.telStudent = telStudent;
		this.mailStudent = mailStudent;
		this.fullNameStudent = fullNameStudent;
		this.classeStudent = classeStudent;
	}

	public StudentConfigDto(String idStudent, String telStudent, String mailStudent, String fullNameStudent) {
		super();
		this.idStudent = idStudent;
		this.telStudent = telStudent;
		this.mailStudent = mailStudent;
		this.fullNameStudent = fullNameStudent;
	}
	
	/************************************************ Getters & Setters *************************************************/
	
	public String getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
	}

	public String getTelStudent() {
		return telStudent;
	}

	public void setTelStudent(String telStudent) {
		this.telStudent = telStudent;
	}

	public String getMailStudent() {
		return mailStudent;
	}

	public void setMailStudent(String mailStudent) {
		this.mailStudent = mailStudent;
	}

	public String getPwdJWTStudent() {
		return pwdJWTStudent;
	}

	public void setPwdJWTStudent(String pwdJWTStudent) {
		this.pwdJWTStudent = pwdJWTStudent;
	}

	public String getDtCreationPwdJWTStudent() {
		return dtCreationPwdJWTStudent;
	}

	public void setDtCreationPwdJWTStudent(String dtCreationPwdJWTStudent) {
		this.dtCreationPwdJWTStudent = dtCreationPwdJWTStudent;
	}

	public String getClasseStudent() {
		return classeStudent;
	}

	public void setClasseStudent(String classeStudent) {
		this.classeStudent = classeStudent;
	}

	public String getFullNameStudent() {
		return fullNameStudent;
	}

	public void setFullNameStudent(String fullNameStudent) {
		this.fullNameStudent = fullNameStudent;
	}

	public String getOptionStudent() {
		return optionStudent;
	}

	public void setOptionStudent(String optionStudent) {
		this.optionStudent = optionStudent;
	}

	
}

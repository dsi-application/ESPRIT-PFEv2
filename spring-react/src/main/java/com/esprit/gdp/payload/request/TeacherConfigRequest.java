package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;

public class TeacherConfigRequest
{
	@NotBlank
	private String telTeacher;

	@NotBlank
	private String mailTeacher;


	/*********************** Getters & Setters ***********************/

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

	
}

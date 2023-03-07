package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;

public class StudentConfigRequest
{
	@NotBlank
	private String telStudent;

	@NotBlank
	private String mailStudent;


	/*********************** Getters & Setters ***********************/

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

	
}

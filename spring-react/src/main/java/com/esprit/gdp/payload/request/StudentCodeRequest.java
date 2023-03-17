package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;

public class StudentCodeRequest
{
	@NotBlank
	private String studentCode;

	/*********************** Getters & Setters ***********************/

	public StudentCodeRequest() {}

	public StudentCodeRequest(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

}

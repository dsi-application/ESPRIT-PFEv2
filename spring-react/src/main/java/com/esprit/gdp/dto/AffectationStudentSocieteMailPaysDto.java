package com.esprit.gdp.dto;

public class AffectationStudentSocieteMailPaysDto
{
	private String emailSociete;

	private String paysSociete;

	
	public AffectationStudentSocieteMailPaysDto(){}


	public AffectationStudentSocieteMailPaysDto(String emailSociete, String paysSociete)
	{
		this.emailSociete = emailSociete;
		this.paysSociete = paysSociete;
	}


	public String getEmailSociete() {
		return emailSociete;
	}

	public void setEmailSociete(String emailSociete) {
		this.emailSociete = emailSociete;
	}

	public String getPaysSociete() {
		return paysSociete;
	}

	public void setPaysSociete(String paysSociete) {
		this.paysSociete = paysSociete;
	}

}
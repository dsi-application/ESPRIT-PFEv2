package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;

public class RespServiceStgConfigRequest
{
	@NotBlank
	private String telRespServiceStg;

	@NotBlank
	private String mailRespServiceStg;


	/*********************** Getters & Setters ***********************/

	public String getTelRespServiceStg() {
		return telRespServiceStg;
	}

	public void setTelRespServiceStg(String telRespServiceStg) {
		this.telRespServiceStg = telRespServiceStg;
	}

	public String getMailRespServiceStg() {
		return mailRespServiceStg;
	}

	public void setMailRespServiceStg(String mailRespServiceStg) {
		this.mailRespServiceStg = mailRespServiceStg;
	}

	
}

package com.esprit.gdp.dto;


public class RespServiceStgConfigDto
{

	private String idRespServiceStg;
	private String telRespServiceStg;
	private String mailRespServiceStg;
	private String fullNameRespServiceStg;

	public RespServiceStgConfigDto() {
		super();
	}
	
	public RespServiceStgConfigDto(String idRespServiceStg, String telRespServiceStg, String mailRespServiceStg,
			String fullNameRespServiceStg) {
		super();
		this.idRespServiceStg = idRespServiceStg;
		this.telRespServiceStg = telRespServiceStg;
		this.mailRespServiceStg = mailRespServiceStg;
		this.fullNameRespServiceStg = fullNameRespServiceStg;
	}


	/************************************************ Getters & Setters *************************************************/

	public String getIdRespServiceStg() {
		return idRespServiceStg;
	}

	public void setIdRespServiceStg(String idRespServiceStg) {
		this.idRespServiceStg = idRespServiceStg;
	}

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

	public String getFullNameRespServiceStg() {
		return fullNameRespServiceStg;
	}

	public void setFullNameRespServiceStg(String fullNameRespServiceStg) {
		this.fullNameRespServiceStg = fullNameRespServiceStg;
	}
	
}

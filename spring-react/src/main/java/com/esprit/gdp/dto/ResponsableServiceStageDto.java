package com.esprit.gdp.dto;

public class ResponsableServiceStageDto
{
	private String nomUserSce;
	private String dateModifyJwtPwd;
	private String mailUserSce;
	private String telUserSce;

	public ResponsableServiceStageDto() {}
	
	
	public ResponsableServiceStageDto(String nomUserSce, String dateModifyJwtPwd, String mailUserSce, String telUserSce)
	{
		this.nomUserSce = nomUserSce;
		this.dateModifyJwtPwd = dateModifyJwtPwd;
		this.mailUserSce = mailUserSce;
		this.telUserSce = telUserSce;
	}


	/************************************************ Getters & Setters *************************************************/

	public String getNomUserSce() {
		return nomUserSce;
	}

	public void setNomUserSce(String nomUserSce) {
		this.nomUserSce = nomUserSce;
	}

	public String getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}
		
	public void setDateModifyJwtPwd(String dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

	public String getMailUserSce() {
		return mailUserSce;
	}

	public void setMailUserSce(String mailUserSce) {
		this.mailUserSce = mailUserSce;
	}

	public String getTelUserSce() {
		return telUserSce;
	}

	public void setTelUserSce(String telUserSce) {
		this.telUserSce = telUserSce;
	}
	
}

package com.esprit.gdp.dto;


public class ChefCoordConfigDto
{

	private String idChefCoord;
	private String telChefCoord;
	private String fullNameChefCoord;
	private String privilegeChefCoord;

	public ChefCoordConfigDto() {
		super();
	}

	public ChefCoordConfigDto(String idChefCoord, String telChefCoord, String fullNameChefCoord,
			String privilegeChefCoord) {
		super();
		this.idChefCoord = idChefCoord;
		this.telChefCoord = telChefCoord;
		this.fullNameChefCoord = fullNameChefCoord;
		this.privilegeChefCoord = privilegeChefCoord;
	}
	

	/************************************************ Getters & Setters *************************************************/

	public String getIdChefCoord() {
		return idChefCoord;
	}

	public void setIdChefCoord(String idChefCoord) {
		this.idChefCoord = idChefCoord;
	}

	public String getTelChefCoord() {
		return telChefCoord;
	}

	public void setTelChefCoord(String telChefCoord) {
		this.telChefCoord = telChefCoord;
	}

	public String getFullNameChefCoord() {
		return fullNameChefCoord;
	}

	public void setFullNameChefCoord(String fullNameChefCoord) {
		this.fullNameChefCoord = fullNameChefCoord;
	}

	public String getPrivilegeChefCoord() {
		return privilegeChefCoord;
	}

	public void setPrivilegeChefCoord(String privilegeChefCoord) {
		this.privilegeChefCoord = privilegeChefCoord;
	}

}

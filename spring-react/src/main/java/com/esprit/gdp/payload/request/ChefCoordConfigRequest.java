package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;

public class ChefCoordConfigRequest
{
	@NotBlank
	private String telChefCoord;


	/*********************** Getters & Setters ***********************/

	public String getTelChefCoord() {
		return telChefCoord;
	}

	public void setTelChefCoord(String telChefCoord) {
		this.telChefCoord = telChefCoord;
	}

}

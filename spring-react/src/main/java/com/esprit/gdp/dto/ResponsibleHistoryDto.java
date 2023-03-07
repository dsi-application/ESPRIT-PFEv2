package com.esprit.gdp.dto;

public class ResponsibleHistoryDto
{
	
	private String firstName;
	private String lastName;
	private String numTelephone;
	private String email;
	
	public ResponsibleHistoryDto() {}
	
	public ResponsibleHistoryDto(String firstName, String lastName, String numTelephone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.numTelephone = numTelephone;
		this.email = email;
	}
	
	
	/************************************************ Getters & Setters *************************************************/

	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getNumTelephone() {
		return numTelephone;
	}
	
	public void setNumTelephone(String numTelephone) {
		this.numTelephone = numTelephone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}

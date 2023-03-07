package com.esprit.gdp.dto;

public class ResponsibleDto
{
	private String idResponsible;
	private String pwdResponsible;
	private String firstName;
	private String lastName;
	private String numTelephone;
	private String email;
	private String address;
	private String fonction;
	
	public ResponsibleDto() {}
	
	public ResponsibleDto(String firstName, String lastName, String numTelephone, String email, String address, String fonction) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.numTelephone = numTelephone;
		this.email = email;
		this.address = address;
		this.fonction = fonction;
	}
	
	public ResponsibleDto(String firstName, String lastName, String numTelephone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.numTelephone = numTelephone;
		this.email = email;
	}
	
	public ResponsibleDto(String idResponsible, String pwdResponsible, String firstName, String lastName, String numTelephone, String email,
			String address, String fonction) {
		super();
		this.idResponsible = idResponsible;
		this.pwdResponsible = pwdResponsible;
		this.firstName = firstName;
		this.lastName = lastName;
		this.numTelephone = numTelephone;
		this.email = email;
		this.address = address;
		this.fonction = fonction;
	}
	
	
	/************************************************ Getters & Setters *************************************************/

	
	public String getFirstName() {
		return firstName;
	}
	
	public String getIdResponsible() {
		return idResponsible;
	}

	public void setIdResponsible(String idResponsible) {
		this.idResponsible = idResponsible;
	}

	public String getPwdResponsible() {
		return pwdResponsible;
	}

	public void setPwdResponsible(String pwdResponsible) {
		this.pwdResponsible = pwdResponsible;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getFonction() {
		return fonction;
	}
	
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	
	
}

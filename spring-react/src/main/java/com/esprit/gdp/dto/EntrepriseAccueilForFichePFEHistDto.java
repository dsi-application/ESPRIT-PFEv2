package com.esprit.gdp.dto;


public class EntrepriseAccueilForFichePFEHistDto
{
	
	private String designation;
	private String siegeSocial;
	private String mail;
	private String phone;
	private String fax;
	private String nomPays;
	private String libelleSecteur;
	private String address;
	
	
	public EntrepriseAccueilForFichePFEHistDto() {}
	

	public EntrepriseAccueilForFichePFEHistDto(String designation, String siegeSocial, String mail, String phone,
			String fax, String address) {
		this.designation = designation;
		this.siegeSocial = siegeSocial;
		this.mail = mail;
		this.phone = phone;
		this.fax = fax;
		this.address = address;
	}

	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSiegeSocial() {
		return siegeSocial;
	}

	public void setSiegeSocial(String siegeSocial) {
		this.siegeSocial = siegeSocial;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNomPays() {
		return nomPays;
	}

	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}

	public String getLibelleSecteur() {
		return libelleSecteur;
	}

	public void setLibelleSecteur(String libelleSecteur) {
		this.libelleSecteur = libelleSecteur;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}

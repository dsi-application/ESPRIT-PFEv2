package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;

 
public class AddCompanyRequest
{
    @NotBlank
    //@Size(min = 1, max = 100)
    private String designation;
    
    @NotBlank
    //@Size(min = 6, max = 250)
    private String lol;
    
    @NotBlank
    //@Size(min = 6, max = 100)
    private String addressMail;
    
    // @NotBlank
    private String telephone;
    
    // @NotBlank
    private String fax;
    
    // @NotBlank
    private String paysLibelle;
    
    // @NotBlank
    private String sectorEntrepriseLibelle;
    
    // @NotBlank
    private String siegeSocial;
    
    
    
    /*********************** Getters & Setters ***********************/


	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAddressMail() {
		return addressMail;
	}

	public void setAddressMail(String addressMail) {
		this.addressMail = addressMail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLol() {
		return lol;
	}

	public void setLol(String lol) {
		this.lol = lol;
	}

	public String getPaysLibelle() {
		return paysLibelle;
	}

	public void setPaysLibelle(String paysLibelle) {
		this.paysLibelle = paysLibelle;
	}

	public String getSectorEntrepriseLibelle() {
		return sectorEntrepriseLibelle;
	}

	public void setSectorEntrepriseLibelle(String sectorEntrepriseLibelle) {
		this.sectorEntrepriseLibelle = sectorEntrepriseLibelle;
	}

	public String getSiegeSocial() {
		return siegeSocial;
	}

	public void setSiegeSocial(String siegeSocial) {
		this.siegeSocial = siegeSocial;
	}


}


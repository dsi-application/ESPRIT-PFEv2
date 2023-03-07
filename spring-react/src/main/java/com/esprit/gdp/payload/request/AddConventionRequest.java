package com.esprit.gdp.payload.request;
import java.util.Date;

import javax.validation.constraints.*;
 
public class AddConventionRequest
{

	@NotBlank
	//@Size(min = 2, max = 100)
	private String mail;
	 
    private Date dateDebut;
    
    private Date dateFin;
    
    @NotBlank
    //@Size(min = 5, max = 100)
    private String nomSociete;
    
    private String address;
    
    private String telephone;
    
    @NotBlank
    //@Size(min = 6, max = 100)
    private String responsable;
    
    
    /*********************** Getters & Setters ***********************/

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getNomSociete() {
		return nomSociete;
	}

	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
    
}

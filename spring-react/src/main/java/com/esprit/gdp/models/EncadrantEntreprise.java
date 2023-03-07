package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "ESP_ENCADRANT_ENTREPRISE")
public class EncadrantEntreprise implements Serializable
{

	private static final long serialVersionUID = 7839226972740647617L;
	
	@EmbeddedId
	private EncadrantEntreprisePK encadrantEntreprisePK;
	
	@Column(name = "PWD_JWT_ENCADRANT", length = 100)
	private String pwdJWTEncadrant;
	
	@Column(name = "DATE_MODIFY_JWT_PWD")
	private Date dateModifyJwtPwd;
	
	@Column(name = "FIRST_NAME", length = 50)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 50)
	private String lastName;
	
	@Column(name = "NUM_TELEPHONE", length = 20)
	private String numTelephone;
	
	@Column(name = "EMAIL", length = 50)
	private String email;
	
	
	public EncadrantEntreprise(){}
	
	
	public EncadrantEntreprise(String pwdJWTEncadrant, Date dateModifyJwtPwd)
	{
		this.pwdJWTEncadrant = pwdJWTEncadrant;
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}
	
	public EncadrantEntreprise(EncadrantEntreprisePK encadrantEntreprisePK, String firstName, String lastName, String numTelephone, String email) {
		this.encadrantEntreprisePK = encadrantEntreprisePK;
		this.firstName = firstName;
		this.lastName = lastName;
		this.numTelephone = numTelephone;
		this.email = email;
	}

	
	/**************************************************** Getters & Setters **************************************************/


	public String getPwdJWTEncadrant() {
		return pwdJWTEncadrant;
	}

	public void setPwdJWTEncadrant(String pwdJWTEncadrant) {
		this.pwdJWTEncadrant = pwdJWTEncadrant;
	}

	public Date getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(Date dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

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

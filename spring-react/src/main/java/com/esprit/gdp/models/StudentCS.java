/**
 * 
 */
package com.esprit.gdp.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saria Essid
 *
 */


@Entity
@Table(name = "SYN_ESP_ETUDIANT_CS")
public class StudentCS implements Serializable
{

	private static final long serialVersionUID = -6585510892343029935L;
	

	@Id
	@Column(name = "ID_ET", length = 10)
	private String idEt;
	
	@Column(name="NOM_ET")
	private String nomet;
	
	@Column(name="PNOM_ET")
	private String prenomet;
	
	@Column(name = "PWD_JWT_ETUDIANT", length = 255)
	private String pwdJWTEtudiantCS;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_MODIFY_JWT_PWD")
	private Date dateModifyJwtPwdCS;
	
	@Column(name="NATIONALITE")
	private String nationalite;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_NAIS_ET")
	private Date datenaisset;
	
	@Column(name="LIEU_NAIS_ET")
	private String lieunaiset;
	
	@Column(name="ADRESSE_ET")
	private String adresseet;
	
	@Column(name="TEL_ET")
	private String telet;
	
	@Column(name="E_MAIL_ET")
	private String emailet;
	
	@Column(name="CLASSE_COURANTE_ET")
	private String classecourantet;
	
	@Column(name="NUM_CIN_PASSEPORT")
	private String numcinpasseport;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_SAISIE")
	private Date datesaisie;
	
	@Column(name="ETAT")
	private String etat;
	
	@Column(name="TYPE_ET")
	private String typeet;
	
	@Column(name="ADRESSE_MAIL_ESP")
	private String adressemailesp;
	
	@Column(name="PWD_ET")
	private String pwdet;
	
	@Column(name = "TOKN_STUDENT")
	private String token;
	
	@Column(name = "DATE_CREATION_TOKEN", columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
	
	@OneToMany(mappedBy = "studentInscriptioncs")
	private List<InscriptionCS> inscriptions;
	
//	@OneToMany(mappedBy = "studentConventionCS")
//	private List<Convention> conventions;
	
	
	public StudentCS() {}

	
	/************************************************ Getters & Setters *************************************************/

	
	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public String getNomet() {
		return nomet;
	}

	public void setNomet(String nomet) {
		this.nomet = nomet;
	}

	public String getPrenomet() {
		return prenomet;
	}

	public void setPrenomet(String prenomet) {
		this.prenomet = prenomet;
	}

	public Date getDatenaisset() {
		return datenaisset;
	}

	public void setDatenaisset(Date datenaisset) {
		this.datenaisset = datenaisset;
	}

	public String getLieunaiset() {
		return lieunaiset;
	}

	public void setLieunaiset(String lieunaiset) {
		this.lieunaiset = lieunaiset;
	}

	public String getAdresseet() {
		return adresseet;
	}

	public void setAdresseet(String adresseet) {
		this.adresseet = adresseet;
	}

	public String getTelet() {
		return telet;
	}

	public void setTelet(String telet) {
		this.telet = telet;
	}

	public String getEmailet() {
		return emailet;
	}

	public void setEmailet(String emailet) {
		this.emailet = emailet;
	}

	public String getPwdJWTEtudiant() {
		return pwdJWTEtudiantCS;
	}

	public void setPwdJWTEtudiant(String pwdJWTEtudiant) {
		this.pwdJWTEtudiantCS = pwdJWTEtudiant;
	}

	public Date getDateModifyJwtPwd() {
		return dateModifyJwtPwdCS;
	}

	public void setDateModifyJwtPwd(Date dateModifyJwtPwd) {
		this.dateModifyJwtPwdCS = dateModifyJwtPwd;
	}

	@JsonIgnore
	public List<InscriptionCS> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<InscriptionCS> inscriptions) {
		this.inscriptions = inscriptions;
	}

	public String getClassecourantet() {
		return classecourantet;
	}

	public void setClassecourantet(String classecourantet) {
		this.classecourantet = classecourantet;
	}

	public String getNumcinpasseport() {
		return numcinpasseport;
	}

	public void setNumcinpasseport(String numcinpasseport) {
		this.numcinpasseport = numcinpasseport;
	}

	public Date getDatesaisie() {
		return datesaisie;
	}

	public void setDatesaisie(Date datesaisie) {
		this.datesaisie = datesaisie;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getTypeet() {
		return typeet;
	}

	public void setTypeet(String typeet) {
		this.typeet = typeet;
	}

	public String getAdressemailesp() {
		return adressemailesp;
	}

	public void setAdressemailesp(String adressemailesp) {
		this.adressemailesp = adressemailesp;
	}

	public String getPwdet() {
		return pwdet;
	}

	public void setPwdet(String pwdet) {
		this.pwdet = pwdet;
	}

	public String getPwdJWTEtudiantCS() {
		return pwdJWTEtudiantCS;
	}

	public void setPwdJWTEtudiantCS(String pwdJWTEtudiantCS) {
		this.pwdJWTEtudiantCS = pwdJWTEtudiantCS;
	}

	public Date getDateModifyJwtPwdCS() {
		return dateModifyJwtPwdCS;
	}

	public void setDateModifyJwtPwdCS(Date dateModifyJwtPwdCS) {
		this.dateModifyJwtPwdCS = dateModifyJwtPwdCS;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}


//	public List<Convention> getConventions() {
//		return conventions;
//	}
//
//
//	public void setConventions(List<Convention> conventions) {
//		this.conventions = conventions;
//	}
	
	

}

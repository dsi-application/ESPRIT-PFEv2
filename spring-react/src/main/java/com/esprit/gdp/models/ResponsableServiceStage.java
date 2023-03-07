package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Saria Essid
 *
 */

@Entity
@Table(name="ESP_USER_SCE_STAGE")
public class ResponsableServiceStage implements Serializable
{

	private static final long serialVersionUID = -5696578599241267153L;
	
	
	
	@TableGenerator(name = "Idt_ResponsableServiceStage", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_RESPONSABLE_SERVICE_STAGE", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_ResponsableServiceStage")
	@Column(name = "ID_USER_SCE", length = 10)
	private String idUserSce;
	
	@Column(name = "NOM_USER_SCE", length = 100)
	private String nomUserSce;
	
	@Column(name = "MAIL_USER_SCE", length = 100)
	private String mailUserSce;
	
	@Column(name = "PHONE_USER_SCE", length = 100)
	private String phoneUserSce;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_AFFECTATION")
	private Date dateAffectation;

	@Column(name = "LOGIN", length = 50)
	private String login;
	
	@Column(name = "MDP", length = 20)
	private String mdp;
	
	@Column(name = "PWD_JWT_RSS", length = 100)
	private String pwdJWTRSS;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_MODIFY_JWT_PWD")
	private Date dateModifyJwtPwd;
	
	@OneToMany(mappedBy = "responsableServiceStage")
	private List<Convention> conventions;
	
	
	public ResponsableServiceStage() {}

	
	public ResponsableServiceStage(String idUserSce, String nomUserSce, Date dateAffectation, String login,
			String mdp) {
		super();
		this.idUserSce = idUserSce;
		this.nomUserSce = nomUserSce;
		this.dateAffectation = dateAffectation;
		this.login = login;
		this.mdp = mdp;
	}




	/************************************************ Getters & Setters *************************************************/

	
	public String getIdUserSce() {
		return idUserSce;
	}

	public void setIdUserSce(String idUserSce) {
		this.idUserSce = idUserSce;
	}

	public String getNomUserSce() {
		return nomUserSce;
	}

	public void setNomUserSce(String nomUserSce) {
		this.nomUserSce = nomUserSce;
	}

	public Date getDateAffectation() {
		return dateAffectation;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	@JsonIgnore
	public List<Convention> getConventions() {
		return conventions;
	}

	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
	
	public String getPwdJWTRSS() {
		return pwdJWTRSS;
	}

	public void setPwdJWTRSS(String pwdJWTRSS) {
		this.pwdJWTRSS = pwdJWTRSS;
	}

	public Date getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(Date dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

	public String getMailUserSce() {
		return mailUserSce;
	}

	public void setMailUserSce(String mailUserSce) {
		this.mailUserSce = mailUserSce;
	}

	public String getPhoneUserSce() {
		return phoneUserSce;
	}

	public void setPhoneUserSce(String phoneUserSce) {
		this.phoneUserSce = phoneUserSce;
	}
	
}

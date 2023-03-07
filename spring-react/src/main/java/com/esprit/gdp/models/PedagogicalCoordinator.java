package com.esprit.gdp.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * @author Saria Essid
 *
 */
@Entity
@Table(name = "ESP_PEDAGOGICAL_COORDINATOR")
public class PedagogicalCoordinator implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ResponsableStageId responsableStageId  ;

	
	
	@Column(name = "DATE_AFFECTATION")
	private Date dateAffectation;
	
	@ManyToOne
	@JoinColumns({
	     @JoinColumn(name="ESP_ID_OPTION", referencedColumnName="CODE_OPTION", insertable=false, updatable=false),
	     @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false)
	})
    public Option option;
	
	@ManyToOne
	@JoinColumn(name = "ESP_ID_ENS", referencedColumnName="ID_ENS", updatable=false, insertable=false, nullable=false)
    public Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name = "ID_COORDINATOR", referencedColumnName="ID_ENS")
    public Teacher pedagogicalCoordinator;

	@Column(name = "FULL_NAME", length = 50)
	private String fullName;
	
	@Column(name = "NUM_PHONE", length = 50)
	private String phoneNumber;
	
	@Column(name = "ADDRESS", length = 50)
	private String adresse;
	
	@Column(name = "LOGIN", length = 50)
	private String login;
	
	@Column(name = "MDP", length = 20)
	private String mdp;
	
	@Column(name = "PWD_JWT_PC", length = 100)
	private String pwdJWTPC;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_MODIFY_JWT_PWD")
	private Date dateModifyJwtPwd;

	@Column(name = "PRIVILEGE", length = 20)
	private String privilege;

	private String token;
	@Column(name = "DATE_CREATION_TOKEN", columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
	
	public PedagogicalCoordinator() {}

	public PedagogicalCoordinator(ResponsableStageId responsableStageId, Date dateAffectation, Option option,
			Teacher teacher) {
		super();
		this.responsableStageId = responsableStageId;
		this.dateAffectation = dateAffectation;
		this.option = option;
		this.teacher = teacher;
	}
	
	
	/***************************************** Getters & Setters *****************************************/
	
	public ResponsableStageId getResponsableStageId() {
		return responsableStageId;
	}

	public void setResponsableStageId(ResponsableStageId responsableStageId) {
		this.responsableStageId = responsableStageId;
	}

	public Date getDateAffectation() {
		return dateAffectation;
	}


	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Teacher getPedagogicalCoordinator() {
		return pedagogicalCoordinator;
	}

	public void setPedagogicalCoordinator(Teacher pedagogicalCoordinator) {
		this.pedagogicalCoordinator = pedagogicalCoordinator;
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

	public String getPwdJWTPC() {
		return pwdJWTPC;
	}

	public void setPwdJWTPC(String pwdJWTPC) {
		this.pwdJWTPC = pwdJWTPC;
	}

	public Date getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(Date dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
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
	
	
	
}

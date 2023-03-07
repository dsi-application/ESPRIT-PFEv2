package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esprit.gdp.models.edt.WeekSchedule;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Saria Essid
 *
 */

@Entity
@Table(name="SYN_ESP_ENSEIGNANT")
public class Teacher implements Serializable
{

	private static final long serialVersionUID = -5696578599241267153L;
	
	
	@Id
	@Column(name = "ID_ENS", length = 10)
	private String idEns;
	
	@Column(name = "PWD_JWT_ENSEIGNANT", length = 255)
	private String pwdJWTEnseignant;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_MODIFY_JWT_PWD")
	private Date dateModifyJwtPwd;
	
	@Column(name = "CHEF_DEPT")
	private String chefDept;

	private BigDecimal chefcomite;

	private BigDecimal cin;

	@Column(name = "CNSS_ENS")
	private String cnssEns;

	@Column(name = "CODE_GRADE_ACTUEL")
	private String codeGradeActuel;

	@Column(name = "CODE_GRADE_ENTREE")
	private String codeGradeEntree;

	//@Temporal(TemporalType.DATE)
	
	@Column(name = "DATE_REC")
	private Date dateRec;
	
	private String etat;

	@Column(name = "ETAT_CIVIL_ENS")
	private String etatCivilEns;

	@Column(name = "FK_CMT")
	private BigDecimal fkCmt;

	@Column(name = "LIB_GRADE_ACTUELLE")
	private String libGradeActuelle;

	@Column(name = "LIB_GRADE_ENTREE")
	private String libGradeEntree;

	@Column(name = "MAIL_ENS")
	private String mailEns;

	private String niveau;

	@Column(name = "NOM_ENS")
	private String nomEns;

	private String observation;

	private String organisme;

	@Column(name = "PRENOM_ENS")
	private String prenomEns;

	@Column(name = "PWD_INIT")
	private String pwdInit;

	@Column(name = "SEXE_ENS")
	private String sexeEns;

	private String tel1;

	private String tel2;

	@Column(name = "TYPE_ENS")
	private String typeEns;

	@Column(name = "TYPE_UP")
	private String typeUp;

	@Column(name = "UP")
	private String up;
	
	@Column(name = "PWD_ENS")
	private String pwdEns;

	@Column(name = "ETAT_FROM_PE", length = 2)
	private String etatFromPE;
	
	private String token;
	@Column(name = "DATE_CREATION_TOKEN", columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
	
	@OneToMany(mappedBy = "presidentJuryEns")
	private List<FichePFE> fichePfes;
	
	@OneToMany(mappedBy = "encadrantPedagogique")
	private List<InscriptionCJ> inscriptions;
	
	@OneToMany(mappedBy = "encadrantPedagogiqueCS")
	private List<InscriptionCS> inscriptionCSs;
	
	@ManyToMany(mappedBy = "teachers")
	private List<WeekSchedule> weekSchedules;
	
	// @ManyToMany(fetch = FetchType.EAGER)
	/*@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "ESP_RESPONSABLE_STAGE", 
	           joinColumns = { @JoinColumn(name = "ESP_ID_ENS", referencedColumnName="ID_ENS") }, 
	           inverseJoinColumns = { @JoinColumn(name = "ESP_ID_OPTION", referencedColumnName="CODE_OPTION") })
	private List<Option> options;*/
	
	@OneToMany(mappedBy = "teacher")
	private List<PedagogicalCoordinator> responsableStages;
	
	@OneToMany(mappedBy = "pedagogicalCoordinator")
	private List<PedagogicalCoordinator> pedagogicalCoordinators;
	
	
	public Teacher() {}
	
	public Teacher(String idEns, String nomEns, String typeEns, String mailEns, String tel1, String action) {
		this.idEns = idEns;
		this.nomEns = nomEns;
		this.typeEns = typeEns;
		this.mailEns = mailEns;
		this.tel1 = tel1;
		this.up = "Pending";
	}

	/************************************************ Getters & Setters *************************************************/

	
	
	
	public String getIdEns() {
		return idEns;
	}
	
	public void setIdEns(String idEns) {
		this.idEns = idEns;
	}

	public String getPwdJWTEnseignant() {
		return pwdJWTEnseignant;
	}

	public void setPwdJWTEnseignant(String pwdJWTEnseignant) {
		this.pwdJWTEnseignant = pwdJWTEnseignant;
	}

	public Date getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(Date dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

	public String getChefDept() {
		return chefDept;
	}

	public void setChefDept(String chefDept) {
		this.chefDept = chefDept;
	}

	public BigDecimal getChefcomite() {
		return chefcomite;
	}

	public void setChefcomite(BigDecimal chefcomite) {
		this.chefcomite = chefcomite;
	}

	public BigDecimal getCin() {
		return cin;
	}

	public void setCin(BigDecimal cin) {
		this.cin = cin;
	}

	public String getCnssEns() {
		return cnssEns;
	}

	public void setCnssEns(String cnssEns) {
		this.cnssEns = cnssEns;
	}

	public String getCodeGradeActuel() {
		return codeGradeActuel;
	}

	public void setCodeGradeActuel(String codeGradeActuel) {
		this.codeGradeActuel = codeGradeActuel;
	}

	public String getCodeGradeEntree() {
		return codeGradeEntree;
	}

	public void setCodeGradeEntree(String codeGradeEntree) {
		this.codeGradeEntree = codeGradeEntree;
	}

	public Date getDateRec() {
		return dateRec;
	}

	public void setDateRec(Date dateRec) {
		this.dateRec = dateRec;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getEtatCivilEns() {
		return etatCivilEns;
	}

	public void setEtatCivilEns(String etatCivilEns) {
		this.etatCivilEns = etatCivilEns;
	}

	public BigDecimal getFkCmt() {
		return fkCmt;
	}

	public void setFkCmt(BigDecimal fkCmt) {
		this.fkCmt = fkCmt;
	}

	public String getLibGradeActuelle() {
		return libGradeActuelle;
	}

	public void setLibGradeActuelle(String libGradeActuelle) {
		this.libGradeActuelle = libGradeActuelle;
	}

	public String getLibGradeEntree() {
		return libGradeEntree;
	}

	public void setLibGradeEntree(String libGradeEntree) {
		this.libGradeEntree = libGradeEntree;
	}

	public String getMailEns() {
		return mailEns;
	}

	public void setMailEns(String mailEns) {
		this.mailEns = mailEns;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getNomEns() {
		return nomEns;
	}

	public void setNomEns(String nomEns) {
		this.nomEns = nomEns;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getOrganisme() {
		return organisme;
	}

	public void setOrganisme(String organisme) {
		this.organisme = organisme;
	}

	public String getPrenomEns() {
		return prenomEns;
	}

	public void setPrenomEns(String prenomEns) {
		this.prenomEns = prenomEns;
	}

	public String getPwdInit() {
		return pwdInit;
	}

	public void setPwdInit(String pwdInit) {
		this.pwdInit = pwdInit;
	}

	public String getSexeEns() {
		return sexeEns;
	}

	public void setSexeEns(String sexeEns) {
		this.sexeEns = sexeEns;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getTypeEns() {
		return typeEns;
	}

	public void setTypeEns(String typeEns) {
		this.typeEns = typeEns;
	}

	public String getTypeUp() {
		return typeUp;
	}

	public void setTypeUp(String typeUp) {
		this.typeUp = typeUp;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getPwdEns() {
		return pwdEns;
	}

	public void setPwdEns(String pwdEns) {
		this.pwdEns = pwdEns;
	}


	@JsonIgnore
	public List<FichePFE> getFichePfes() {
		return fichePfes;
	}



	public void setFichePfes(List<FichePFE> fichePfes) {
		this.fichePfes = fichePfes;
	}


	@JsonIgnore
	public List<InscriptionCJ> getInscriptions() {
		return inscriptions;
	}



	public void setInscriptions(List<InscriptionCJ> inscriptions) {
		this.inscriptions = inscriptions;
	}


	@JsonIgnore
	public List<PedagogicalCoordinator> getResponsableStages() {
		return responsableStages;
	}



	public void setResponsableStages(List<PedagogicalCoordinator> responsableStages) {
		this.responsableStages = responsableStages;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chefDept == null) ? 0 : chefDept.hashCode());
		result = prime * result + ((chefcomite == null) ? 0 : chefcomite.hashCode());
		result = prime * result + ((cin == null) ? 0 : cin.hashCode());
		result = prime * result + ((cnssEns == null) ? 0 : cnssEns.hashCode());
		result = prime * result + ((codeGradeActuel == null) ? 0 : codeGradeActuel.hashCode());
		result = prime * result + ((codeGradeEntree == null) ? 0 : codeGradeEntree.hashCode());
		result = prime * result + ((dateModifyJwtPwd == null) ? 0 : dateModifyJwtPwd.hashCode());
		result = prime * result + ((dateRec == null) ? 0 : dateRec.hashCode());
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((etatCivilEns == null) ? 0 : etatCivilEns.hashCode());
		result = prime * result + ((fichePfes == null) ? 0 : fichePfes.hashCode());
		result = prime * result + ((fkCmt == null) ? 0 : fkCmt.hashCode());
		result = prime * result + ((idEns == null) ? 0 : idEns.hashCode());
		result = prime * result + ((inscriptions == null) ? 0 : inscriptions.hashCode());
		result = prime * result + ((libGradeActuelle == null) ? 0 : libGradeActuelle.hashCode());
		result = prime * result + ((libGradeEntree == null) ? 0 : libGradeEntree.hashCode());
		result = prime * result + ((mailEns == null) ? 0 : mailEns.hashCode());
		result = prime * result + ((niveau == null) ? 0 : niveau.hashCode());
		result = prime * result + ((nomEns == null) ? 0 : nomEns.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((organisme == null) ? 0 : organisme.hashCode());
		result = prime * result + ((prenomEns == null) ? 0 : prenomEns.hashCode());
		result = prime * result + ((pwdEns == null) ? 0 : pwdEns.hashCode());
		result = prime * result + ((pwdInit == null) ? 0 : pwdInit.hashCode());
		result = prime * result + ((pwdJWTEnseignant == null) ? 0 : pwdJWTEnseignant.hashCode());
		result = prime * result + ((responsableStages == null) ? 0 : responsableStages.hashCode());
		result = prime * result + ((sexeEns == null) ? 0 : sexeEns.hashCode());
		result = prime * result + ((tel1 == null) ? 0 : tel1.hashCode());
		result = prime * result + ((tel2 == null) ? 0 : tel2.hashCode());
		result = prime * result + ((typeEns == null) ? 0 : typeEns.hashCode());
		result = prime * result + ((typeUp == null) ? 0 : typeUp.hashCode());
		result = prime * result + ((up == null) ? 0 : up.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (chefDept == null) {
			if (other.chefDept != null)
				return false;
		} else if (!chefDept.equals(other.chefDept))
			return false;
		if (chefcomite == null) {
			if (other.chefcomite != null)
				return false;
		} else if (!chefcomite.equals(other.chefcomite))
			return false;
		if (cin == null) {
			if (other.cin != null)
				return false;
		} else if (!cin.equals(other.cin))
			return false;
		if (cnssEns == null) {
			if (other.cnssEns != null)
				return false;
		} else if (!cnssEns.equals(other.cnssEns))
			return false;
		if (codeGradeActuel == null) {
			if (other.codeGradeActuel != null)
				return false;
		} else if (!codeGradeActuel.equals(other.codeGradeActuel))
			return false;
		if (codeGradeEntree == null) {
			if (other.codeGradeEntree != null)
				return false;
		} else if (!codeGradeEntree.equals(other.codeGradeEntree))
			return false;
		if (dateModifyJwtPwd == null) {
			if (other.dateModifyJwtPwd != null)
				return false;
		} else if (!dateModifyJwtPwd.equals(other.dateModifyJwtPwd))
			return false;
		if (dateRec == null) {
			if (other.dateRec != null)
				return false;
		} else if (!dateRec.equals(other.dateRec))
			return false;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		if (etatCivilEns == null) {
			if (other.etatCivilEns != null)
				return false;
		} else if (!etatCivilEns.equals(other.etatCivilEns))
			return false;
		if (fichePfes == null) {
			if (other.fichePfes != null)
				return false;
		} else if (!fichePfes.equals(other.fichePfes))
			return false;
		if (fkCmt == null) {
			if (other.fkCmt != null)
				return false;
		} else if (!fkCmt.equals(other.fkCmt))
			return false;
		if (idEns == null) {
			if (other.idEns != null)
				return false;
		} else if (!idEns.equals(other.idEns))
			return false;
		if (inscriptions == null) {
			if (other.inscriptions != null)
				return false;
		} else if (!inscriptions.equals(other.inscriptions))
			return false;
		if (libGradeActuelle == null) {
			if (other.libGradeActuelle != null)
				return false;
		} else if (!libGradeActuelle.equals(other.libGradeActuelle))
			return false;
		if (libGradeEntree == null) {
			if (other.libGradeEntree != null)
				return false;
		} else if (!libGradeEntree.equals(other.libGradeEntree))
			return false;
		if (mailEns == null) {
			if (other.mailEns != null)
				return false;
		} else if (!mailEns.equals(other.mailEns))
			return false;
		if (niveau == null) {
			if (other.niveau != null)
				return false;
		} else if (!niveau.equals(other.niveau))
			return false;
		if (nomEns == null) {
			if (other.nomEns != null)
				return false;
		} else if (!nomEns.equals(other.nomEns))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (organisme == null) {
			if (other.organisme != null)
				return false;
		} else if (!organisme.equals(other.organisme))
			return false;
		if (prenomEns == null) {
			if (other.prenomEns != null)
				return false;
		} else if (!prenomEns.equals(other.prenomEns))
			return false;
		if (pwdEns == null) {
			if (other.pwdEns != null)
				return false;
		} else if (!pwdEns.equals(other.pwdEns))
			return false;
		if (pwdInit == null) {
			if (other.pwdInit != null)
				return false;
		} else if (!pwdInit.equals(other.pwdInit))
			return false;
		if (pwdJWTEnseignant == null) {
			if (other.pwdJWTEnseignant != null)
				return false;
		} else if (!pwdJWTEnseignant.equals(other.pwdJWTEnseignant))
			return false;
		if (responsableStages == null) {
			if (other.responsableStages != null)
				return false;
		} else if (!responsableStages.equals(other.responsableStages))
			return false;
		if (sexeEns == null) {
			if (other.sexeEns != null)
				return false;
		} else if (!sexeEns.equals(other.sexeEns))
			return false;
		if (tel1 == null) {
			if (other.tel1 != null)
				return false;
		} else if (!tel1.equals(other.tel1))
			return false;
		if (tel2 == null) {
			if (other.tel2 != null)
				return false;
		} else if (!tel2.equals(other.tel2))
			return false;
		if (typeEns == null) {
			if (other.typeEns != null)
				return false;
		} else if (!typeEns.equals(other.typeEns))
			return false;
		if (typeUp == null) {
			if (other.typeUp != null)
				return false;
		} else if (!typeUp.equals(other.typeUp))
			return false;
		if (up == null) {
			if (other.up != null)
				return false;
		} else if (!up.equals(other.up))
			return false;
		return true;
	}
	@JsonIgnore
	public List<WeekSchedule> getWeekSchedules() {
		return weekSchedules;
	}

	public void setWeekSchedules(List<WeekSchedule> weekSchedules) {
		this.weekSchedules = weekSchedules;
	}

	public String getEtatFromPE() {
		return etatFromPE;
	}

	public void setEtatFromPE(String etatFromPE) {
		this.etatFromPE = etatFromPE;
	}
	
	@JsonIgnore
	public List<PedagogicalCoordinator> getPedagogicalCoordinators() {
		return pedagogicalCoordinators;
	}

	public void setPedagogicalCoordinators(List<PedagogicalCoordinator> pedagogicalCoordinators) {
		this.pedagogicalCoordinators = pedagogicalCoordinators;
	}

	@JsonIgnore
	public List<InscriptionCS> getInscriptionCSs() {
		return inscriptionCSs;
	}

	public void setInscriptionCSs(List<InscriptionCS> inscriptionCSs) {
		this.inscriptionCSs = inscriptionCSs;
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

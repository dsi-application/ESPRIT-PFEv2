package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ESP_CONVENTION")
public class Convention implements Serializable {

	private static final long serialVersionUID = 2047893257560587603L;

	@EmbeddedId
	private ConventionPK conventionPK;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_DEB")
	private Date dateDebut;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_FIN")
	private Date dateFin;

	@Column(name = "MAIL", length = 50)
	private String mail;
	 
	@Column(name = "Address", length = 250)
	private String address;

	@Column(name = "TELEPHONE", length = 25)
	private String telephone;
	
	@Column(name = "RESPONSABLE", length = 100)
	private String responsable;
	
	@Column(name = "MAIL_ENCADRANT_ENTREPRISE", length = 200)
	private String mailEncadrantEntreprise;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_SAISIE_DEMANDE")
	private Date dateSaisieDemande;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_VALIDATION_DEMANDE")
	private Date dateValidationDemande;
	
	@Column(name = "MOTIF_ANNULATION", length = 250)
	private String motifAnnulation;

	@Column(name = "TRAITER")
	private String traiter;
	
	@Column(name = "path_convention")
	private String pathConvention;
	
	@ManyToOne
	@JoinColumn(name="ID_ENTREPRISE")
    public EntrepriseAccueil entrepriseAccueilConvention;
	
	@OneToMany(mappedBy = "conventionAvenant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Avenant> avenants;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_MODIF_CONV_RSS")
	private Date dateModifConvRSS;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_SIGNED_CONV")
	private Date dateDepotSignedConvention;

	@Column(name = "path_signed_conv")
	private String pathSignedConvention;
	
	
	
//	@ManyToOne
//	@JoinColumn(name="ID_ET", referencedColumnName = "ID_ET", updatable=false, insertable=false, nullable=false)
//	private StudentCJ studentConvention;
	
//	@ManyToOne
//	@JoinColumn(name="ID_ET", referencedColumnName = "ID_ET", updatable=false, insertable=false, nullable=false)
//	private StudentCS studentConventionCS;
	
	@ManyToOne
	@JoinColumn(name="ID_USER_SCE", referencedColumnName = "ID_USER_SCE")
	private ResponsableServiceStage responsableServiceStage;
	
	private Integer dureeStageWeek;
	
	public Convention() {}
	
//	public Convention(ConventionPK conventionPK, String mail, Date dateDebut, Date dateFin,
//			String address, String telephone, String responsable, String traiter) {
//		super();
//		this.conventionPK = conventionPK;
//		this.mail = mail;
//		this.dateDebut = dateDebut;
//		this.dateFin = dateFin;
//		this.address = address;
//		this.telephone = telephone;
//		this.responsable = responsable;
//		this.traiter = traiter;
//	}
	
	public Convention(ConventionPK conventionPK, String mail, Date dateDebut, Date dateFin,
			String address, String telephone, String responsable, String traiter, Integer dureeStageWeek) {
		super();
		this.conventionPK = conventionPK;
		this.mail = mail;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.address = address;
		this.telephone = telephone;
		this.responsable = responsable;
		this.traiter = traiter;
		this.dureeStageWeek = dureeStageWeek;
	}
	
	
	public Convention(String traiter, EntrepriseAccueil entrepriseAccueilConvention) {
		this.traiter = traiter;
		this.entrepriseAccueilConvention = entrepriseAccueilConvention;
	}


	/**************************************************** Getters & Setters **************************************************/

	
	public ConventionPK getConventionPK() {
		return conventionPK;
	}

	public void setConventionPK(ConventionPK conventionPK) {
		this.conventionPK = conventionPK;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getTraiter() {
		return traiter;
	}

	public void setTraiter(String traiter) {
		this.traiter = traiter;
	}

	public EntrepriseAccueil getEntrepriseAccueilConvention() {
		return entrepriseAccueilConvention;
	}

	public void setEntrepriseAccueilConvention(EntrepriseAccueil entrepriseAccueilConvention) {
		this.entrepriseAccueilConvention = entrepriseAccueilConvention;
	}

	public List<Avenant> getAvenants() {
		return avenants;
	}

	public void setAvenants(List<Avenant> avenants) {
		this.avenants = avenants;
	}
	@JsonIgnore
	public ResponsableServiceStage getResponsableServiceStage() {
		return responsableServiceStage;
	}

	public void setResponsableServiceStage(ResponsableServiceStage responsableServiceStage) {
		this.responsableServiceStage = responsableServiceStage;
	}

//	@JsonIgnore
//	public StudentCJ getStudentConvention() {
//		return studentConvention;
//	}
//
//	public void setStudentConvention(StudentCJ studentConvention) {
//		this.studentConvention = studentConvention;
//	}
	
	public String getPathConvention() {
		return pathConvention;
	}

	public void setPathConvention(String pathConvention) {
		this.pathConvention = pathConvention;
	}

	public String getMotifAnnulation() {
		return motifAnnulation;
	}

	public void setMotifAnnulation(String motifAnnulation) {
		this.motifAnnulation = motifAnnulation;
	}

	public Date getDateSaisieDemande() {
		return dateSaisieDemande;
	}

	public void setDateSaisieDemande(Date dateSaisieDemande) {
		this.dateSaisieDemande = dateSaisieDemande;
	}

	public Date getDateValidationDemande() {
		return dateValidationDemande;
	}

	public void setDateValidationDemande(Date dateValidationDemande) {
		this.dateValidationDemande = dateValidationDemande;
	}

	public String getMailEncadrantEntreprise() {
		return mailEncadrantEntreprise;
	}

	public void setMailEncadrantEntreprise(String mailEncadrantEntreprise) {
		this.mailEncadrantEntreprise = mailEncadrantEntreprise;
	}

	public Date getDateModifConvRSS() {
		return dateModifConvRSS;
	}

	public void setDateModifConvRSS(Date dateModifConvRSS) {
		this.dateModifConvRSS = dateModifConvRSS;
	}

	public Integer getDureeStageWeek() {
		return dureeStageWeek;
	}

	public void setDureeStageWeek(Integer dureeStageWeek) {
		this.dureeStageWeek = dureeStageWeek;
	}

	public Date getDateDepotSignedConvention() {
		return dateDepotSignedConvention;
	}

	public void setDateDepotSignedConvention(Date dateDepotSignedConvention) {
		this.dateDepotSignedConvention = dateDepotSignedConvention;
	}

	public String getPathSignedConvention() {
		return pathSignedConvention;
	}

	public void setPathSignedConvention(String pathSignedConvention) {
		this.pathSignedConvention = pathSignedConvention;
	}

	
//	public StudentCS getStudentConventionCS() {
//		return studentConventionCS;
//	}
//
//	public void setStudentConventionCS(StudentCS studentConventionCS) {
//		this.studentConventionCS = studentConventionCS;
//	}

}

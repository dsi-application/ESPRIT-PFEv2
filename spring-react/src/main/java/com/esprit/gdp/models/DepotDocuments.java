package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;






@Entity
@Table(name="ESP_DEPOT_DOCUMENTS")
public class DepotDocuments implements Serializable
{
	
	private static final long serialVersionUID = -8252893373212587615L;


	@EmbeddedId
	private FichePFEPK idFichePFE;
	
	
	// Session
	@ManyToOne
	@JoinColumn(name="ID_SESSION")
	private Session session;
	
	@Column(name = "ETAT_FICHE", length = 2)
	private String etatFiche;
	
	@Column(name = "PATH_RAPPORT_VERSION_2", length = 100)
	private String pathRapportVersion2;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_DEPOT_RAPPORT_VERSION_2")
	private Date dateDepotRapportVersion2;
	
	@Column(name = "PATH_PLAGIAT", length = 100)
	private String pathPlagiat;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_DEPOT_PLAGIAT")
	private Date dateDepotPlagiat;
	
	@Column(name = "CONFIDENTIEL", length = 1)
	private Boolean confidentiel;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_SOUTENANCE")
	private Date dateSoutenance;

	@ManyToOne
	@JoinColumn(name="SALLE_SOUTENANCE")
	private Salle salle;
	
	@Column(name = "H_DEBUT_SOUTENANCE", length=5)
	private String heureDebut;
	
	@Column(name = "H_FIN_SOUTENANCE", length=5)
	private String heureFin;
	
	@Column(name = "PRESIDENT_JURY", length = 50)
	private String presidentJury;
	
	@ManyToOne
	@JoinColumn(name="ID_ENS_PRESIDENT")
	private Teacher presidentJuryEns;
	
	
	@OneToMany(mappedBy = "fichePFETraitementFichePFE")
	private List<TraitementFichePFE> traitementFichePFE;
	
	@OneToMany(mappedBy = "fichePFEEvaluationStage")
	private List<EvaluationStage> evaluationStages;
	
	
	@Column(name="VALID_DEPOT", length=2)
	private String validDepot;
	
	public DepotDocuments() {}


	/**************************************************** Getters & Setters **************************************************/


	public FichePFEPK getIdFichePFE() {
		return idFichePFE;
	}

	public void setIdFichePFE(FichePFEPK idFichePFE) {
		this.idFichePFE = idFichePFE;
	}

	@JsonIgnore
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public String getPathRapportVersion2() {
		return pathRapportVersion2;
	}

	public void setPathRapportVersion2(String pathRapportVersion2) {
		this.pathRapportVersion2 = pathRapportVersion2;
	}

	public String getPathPlagiat() {
		return pathPlagiat;
	}

	public void setPathPlagiat(String pathPlagiat) {
		this.pathPlagiat = pathPlagiat;
	}

	public Date getDateDepotRapportVersion2() {
		return dateDepotRapportVersion2;
	}

	public void setDateDepotRapportVersion2(Date dateDepotRapportVersion2) {
		this.dateDepotRapportVersion2 = dateDepotRapportVersion2;
	}

	public Date getDateDepotPlagiat() {
		return dateDepotPlagiat;
	}

	public void setDateDepotPlagiat(Date dateDepotPlagiat) {
		this.dateDepotPlagiat = dateDepotPlagiat;
	}

	public Boolean getConfidentiel() {
		return confidentiel;
	}

	public void setConfidentiel(Boolean confidentiel) {
		this.confidentiel = confidentiel;
	}

	public Date getDateSoutenance() {
		return dateSoutenance;
	}

	public void setDateSoutenance(Date dateSoutenance) {
		this.dateSoutenance = dateSoutenance;
	}
	@JsonIgnore
	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public String getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}

	public String getPresidentJury() {
		return presidentJury;
	}

	public void setPresidentJury(String presidentJury) {
		this.presidentJury = presidentJury;
	}
	@JsonIgnore
	public Teacher getPresidentJuryEns() {
		return presidentJuryEns;
	}

	public void setPresidentJuryEns(Teacher presidentJuryEns) {
		this.presidentJuryEns = presidentJuryEns;
	}
	
	@JsonIgnore
	public List<TraitementFichePFE> getTraitementFichePFE() {
		return traitementFichePFE;
	}

	public void setTraitementFichePFE(List<TraitementFichePFE> traitementFichePFE) {
		this.traitementFichePFE = traitementFichePFE;
	}

	public List<EvaluationStage> getEvaluationStages() {
		return evaluationStages;
	}

	public void setEvaluationStages(List<EvaluationStage> evaluationStages) {
		this.evaluationStages = evaluationStages;
	}

	public String getEtatFiche() {
		return etatFiche;
	}

	public void setEtatFiche(String etatFiche) {
		this.etatFiche = etatFiche;
	}

	public String getValidDepot() {
		return validDepot;
	}

	public void setValidDepot(String validDepot) {
		this.validDepot = validDepot;
	}

}

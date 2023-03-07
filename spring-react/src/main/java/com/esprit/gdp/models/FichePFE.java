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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esprit.gdp.models.temporaryEntities.UrgRdvPFE;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="ESP_FICHE_PFE")
public class FichePFE implements Serializable
{
	
	private static final long serialVersionUID = -8252893373212587615L;


	@EmbeddedId
	private FichePFEPK idFichePFE;
	
	@Column(name = "TITRE_PROJET", length = 100)
	private String titreProjet;
	
	@Column(name = "DESCRIPTION_PROJET", length = 500)
	private String descriptionProjet;
	
	@Column(name = "ANNEE_DEB", length = 4)
	private String anneeDeb;
	
	
	// Session
	@ManyToOne
	@JoinColumn(name="ID_SESSION")
	private Session session;
	
	
//	@ManyToOne
//	@JoinColumn(name="BINOME_PFE")
//	private Student binome;
	
	@Column(name = "MOTIF_REFUS", length = 5000)
	private String motifRefus;
	
	@Column(name = "BINOME_PFE", length = 10)
	private String binome;
	
	@Column(name = "ETAT_FICHE", length = 2)
	private String etatFiche;
	
	@Column(name = "PATH_BILAN_1", length = 500)
	private String pathBilan1;
	
	@Column(name = "PATH_BILAN_2", length = 500)
	private String pathBilan2;
	
	@Column(name = "PATH_BILAN_3", length = 500)
	private String pathBilan3;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_BILAN_1")
	private Date dateDepotBilan1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_BILAN_2")
	private Date dateDepotBilan2;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_BILAN_3")
	private Date dateDepotBilan3;
	
	@Column(name = "PATH_DIAGRAMME_GANTT", length = 100)
	private String pathDiagrammeGantt;
	
	@Column(name = "PATH_JOURNAL_STG", length = 100)
	private String pathJournalStg;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_JOURNAL_STG")
	private Date dateDepotJournalStg;
	
	@Column(name = "PATH_RAPPORT_VERSION_1", length = 500)
	private String pathRapportVersion1;
	
	@Column(name = "PATH_RAPPORT_VERSION_2", length = 500)
	private String pathRapportVersion2;
	
	@Column(name = "PATH_ATTESTATION_STAGE", length = 500)
	private String pathAttestationStage;
	
	@Column(name = "PATH_SUPPLEMENT_STAGE", length = 500)
	private String pathSupplement;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_RAPPORT_VERSION_1")
	private Date dateDepotRapportVersion1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_RAPPORT_VERSION_2")
	private Date dateDepotRapportVersion2;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_ATTESTATION_STAGE")
	private Date dateDepotAttestationStage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_DIAGRAMME_GANTT")
	private Date dateDepotGanttDiagram;
	
	@Column(name = "PATH_PLAGIAT", length = 500)
	private String pathPlagiat;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_PLAGIAT")
	private Date dateDepotPlagiat;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_SUPPLEMENT")
	private Date dateDepotSupplement;
	
	@Column(name = "CONFIDENTIEL", length = 1)
	private Boolean confidentiel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_REPORTS")
	private Date dateDepotReports;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_TREAT_REPORTS")
	private Date dateTreatReports;
	
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
	
	@ManyToOne
	@JoinColumn(name="ID_ENS_EXPERT")
	private Teacher expertEns;
	
	@ManyToOne
	@JoinColumn(name="ID_ENS_ENCADRANT_PEDA")
	private Teacher pedagogicalEncadrant;
	
	/* @OneToMany(fetch = FetchType.LAZY)
	@JoinColumns({ 
	      @JoinColumn(name = "B_SIREN", referencedColumnName = "A_SIREN"),
	      @JoinColumn(name = "B_NDA", referencedColumnName = "A_NDA") 
	    })*/
	@OneToMany(mappedBy = "fichePFEProblematic")
	private List<Problematique> problematics;
	
	
	@OneToMany(mappedBy = "fichePFEFunctionnality")
	private List<Fonctionnalite> functionnalities;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "ESP_FICHEPFE_TECHNOLOGIE", 
			joinColumns = {@JoinColumn(table = "ESP_FICHE_PFE", name = "DATE_CONVENTION"),
						   @JoinColumn(table = "ESP_FICHE_PFE", name = "ID_ET"),
						   @JoinColumn(table = "ESP_FICHE_PFE", name = "DATE_DEPOT_FICHE")
			},
			inverseJoinColumns = @JoinColumn(name = "ID_TECHNOLOGIE"))
	private List<Technologie> technologies;
	
	@OneToMany(mappedBy = "fichePFETraitementFichePFE")
	private List<TraitementFichePFE> traitementFichePFE;
	
	
	@OneToOne(mappedBy = "fichePFEVisiteStagiaire")
	private VisiteStagiaire visiteStagiaire;
	
	@OneToOne(mappedBy = "fichePFEUrgRdvPFE")
	private UrgRdvPFE urgRdvPFE;
	
	
	@OneToMany(mappedBy = "fichePFEEvaluationStage")
	private List<EvaluationStage> evaluationStages;
	
	
	@OneToMany(mappedBy = "fichePFErendezVous")
	private List<RdvSuiviStage> rdvSuiviStages;
	
	@OneToOne(mappedBy = "fichePFEGrille")
	private GrilleAcademicEncadrant grilleAcademicEncadrant;
	
	@Column(name="TRAINEE_KIND", length=2)
	private String traineeKind;
	
	@Column(name="VALID_DEPOT", length=2)
	private String validDepot;
	
	@Column(name = "OBSERVATION_DEPOT", length = 100)
	private String observationDepot;
	
	@Column(name = "NOTE_PE")
	private BigDecimal notePE;
	
	public FichePFE() {}


	/**************************************************** Getters & Setters **************************************************/


	public FichePFEPK getIdFichePFE() {
		return idFichePFE;
	}

	public void setIdFichePFE(FichePFEPK idFichePFE) {
		this.idFichePFE = idFichePFE;
	}

	public String getTitreProjet() {
		return titreProjet;
	}

	public void setTitreProjet(String titreProjet) {
		this.titreProjet = titreProjet;
	}

	public String getDescriptionProjet() {
		return descriptionProjet;
	}

	public void setDescriptionProjet(String descriptionProjet) {
		this.descriptionProjet = descriptionProjet;
	}

	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}
	@JsonIgnore
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	@JsonIgnore
	public String getBinome() {
		return binome;
	}

	public void setBinome(String binome) {
		this.binome = binome;
	}

	public String getPathBilan1() {
		return pathBilan1;
	}

	public void setPathBilan1(String pathBilan1) {
		this.pathBilan1 = pathBilan1;
	}

	public String getPathBilan2() {
		return pathBilan2;
	}

	public void setPathBilan2(String pathBilan2) {
		this.pathBilan2 = pathBilan2;
	}

	public String getPathBilan3() {
		return pathBilan3;
	}

	public void setPathBilan3(String pathBilan3) {
		this.pathBilan3 = pathBilan3;
	}

	public Date getDateDepotBilan1() {
		return dateDepotBilan1;
	}

	public void setDateDepotBilan1(Date dateDepotBilan1) {
		this.dateDepotBilan1 = dateDepotBilan1;
	}

	public Date getDateDepotBilan2() {
		return dateDepotBilan2;
	}

	public void setDateDepotBilan2(Date dateDepotBilan2) {
		this.dateDepotBilan2 = dateDepotBilan2;
	}

	public Date getDateDepotBilan3() {
		return dateDepotBilan3;
	}

	public void setDateDepotBilan3(Date dateDepotBilan3) {
		this.dateDepotBilan3 = dateDepotBilan3;
	}

	public String getPathJournalStg() {
		return pathJournalStg;
	}

	public void setPathJournalStg(String pathJournalStg) {
		this.pathJournalStg = pathJournalStg;
	}

	public Date getDateDepotJournalStg() {
		return dateDepotJournalStg;
	}

	public void setDateDepotJournalStg(Date dateDepotJournalStg) {
		this.dateDepotJournalStg = dateDepotJournalStg;
	}

	public String getPathRapportVersion1() {
		return pathRapportVersion1;
	}

	public void setPathRapportVersion1(String pathRapportVersion1) {
		this.pathRapportVersion1 = pathRapportVersion1;
	}

	public void setDateDepotRapportVersion1(Date dateDepotRapportVersion1) {
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
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

	public Date getDateDepotRapportVersion1() {
		return dateDepotRapportVersion1;
	}

	public void Bilan(Date dateDepotRapportVersion1) {
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
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
	
	public List<Problematique> getProblematics() {
		return problematics;
	}

	public void setProblematics(List<Problematique> problematics) {
		this.problematics = problematics;
	}

	public List<Fonctionnalite> getFunctionnalities() {
		return functionnalities;
	}

	public void setFunctionnalities(List<Fonctionnalite> functionnalities) {
		this.functionnalities = functionnalities;
	}

	public List<Technologie> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<Technologie> technologies) {
		this.technologies = technologies;
	}
	@JsonIgnore
	public List<TraitementFichePFE> getTraitementFichePFE() {
		return traitementFichePFE;
	}

	public void setTraitementFichePFE(List<TraitementFichePFE> traitementFichePFE) {
		this.traitementFichePFE = traitementFichePFE;
	}

	public VisiteStagiaire getVisiteStagiaire() {
		return visiteStagiaire;
	}

	public void setVisiteStagiaire(VisiteStagiaire visiteStagiaire) {
		this.visiteStagiaire = visiteStagiaire;
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

	public List<RdvSuiviStage> getRdvSuiviStages() {
		return rdvSuiviStages;
	}

	public void setRdvSuiviStages(List<RdvSuiviStage> rdvSuiviStages) {
		this.rdvSuiviStages = rdvSuiviStages;
	}

	public String getPathAttestationStage() {
		return pathAttestationStage;
	}

	public void setPathAttestationStage(String pathAttestationStage) {
		this.pathAttestationStage = pathAttestationStage;
	}

	public Date getDateDepotAttestationStage() {
		return dateDepotAttestationStage;
	}

	public void setDateDepotAttestationStage(Date dateDepotAttestationStage) {
		this.dateDepotAttestationStage = dateDepotAttestationStage;
	}

	public Teacher getExpertEns() {
		return expertEns;
	}

	public void setExpertEns(Teacher expertEns) {
		this.expertEns = expertEns;
	}

	public Teacher getPedagogicalEncadrant() {
		return pedagogicalEncadrant;
	}

	public void setPedagogicalEncadrant(Teacher pedagogicalEncadrant) {
		this.pedagogicalEncadrant = pedagogicalEncadrant;
	}

	public String getTraineeKind() {
		return traineeKind;
	}

	public void setTraineeKind(String traineeKind) {
		this.traineeKind = traineeKind;
	}

	public String getObservationDepot() {
		return observationDepot;
	}

	public void setObservationDepot(String observationDepot) {
		this.observationDepot = observationDepot;
	}

	public BigDecimal getNotePE() {
		return notePE;
	}

	public void setNotePE(BigDecimal notePE) {
		this.notePE = notePE;
	}

	public Date getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(Date dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public Date getDateTreatReports() {
		return dateTreatReports;
	}

	public void setDateTreatReports(Date dateTreatReports) {
		this.dateTreatReports = dateTreatReports;
	}

	@JsonIgnore
	public UrgRdvPFE getUrgRdvPFE() {
		return urgRdvPFE;
	}

	public void setUrgRdvPFE(UrgRdvPFE urgRdvPFE) {
		this.urgRdvPFE = urgRdvPFE;
	}

	public String getPathSupplement() {
		return pathSupplement;
	}

	public void setPathSupplement(String pathSupplement) {
		this.pathSupplement = pathSupplement;
	}

	public Date getDateDepotSupplement() {
		return dateDepotSupplement;
	}

	public void setDateDepotSupplement(Date dateDepotSupplement) {
		this.dateDepotSupplement = dateDepotSupplement;
	}

	public String getMotifRefus() {
		return motifRefus;
	}

	public void setMotifRefus(String motifRefus) {
		this.motifRefus = motifRefus;
	}

	public String getPathDiagrammeGantt() {
		return pathDiagrammeGantt;
	}

	public void setPathDiagrammeGantt(String pathDiagrammeGantt) {
		this.pathDiagrammeGantt = pathDiagrammeGantt;
	}

	public Date getDateDepotGanttDiagram() {
		return dateDepotGanttDiagram;
	}

	public void setDateDepotGanttDiagram(Date dateDepotGanttDiagram) {
		this.dateDepotGanttDiagram = dateDepotGanttDiagram;
	}

	public GrilleAcademicEncadrant getGrilleAcademicEncadrant() {
		return grilleAcademicEncadrant;
	}

	public void setGrilleAcademicEncadrant(GrilleAcademicEncadrant grilleAcademicEncadrant) {
		this.grilleAcademicEncadrant = grilleAcademicEncadrant;
	}

}

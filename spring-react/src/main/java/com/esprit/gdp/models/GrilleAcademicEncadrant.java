package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ESP_GRILLE_ACADEMIC_ENCADRANT")
public class GrilleAcademicEncadrant  implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	GrilleAcademicEncadrantPK  grilleAcademicEncadrantPK;
	
	@Column(name = "ETAT_GRILLE", length = 100)
	private String etatGrille;
	
	@Column(name = "NOTE_PLANNING_STG", length = 100)
	private BigDecimal notePlanningStage;

	@Column(name = "NOTE_BILANP_DEB_STG", length = 100)
	private BigDecimal noteBilanPeriodiqueDebutStage;

	@Column(name = "NOTE_BILANP_MIL_STG", length = 100)
	private BigDecimal noteBilanPeriodiqueMilieuStage;

	@Column(name = "NOTE_BILANP_FIN_STG", length = 100)
	private BigDecimal noteBilanPeriodiqueFinStage;

	@Column(name = "NOTE_JOURNAL_BORD", length = 100)
	private BigDecimal noteJournalBord;

	@Column(name = "NOTE_FICHE_EVAL_MIPARC", length = 100)
	private BigDecimal noteFicheEvaluationMiParcour;

	@Column(name = "NOTE_FICHE_EVAL_FINAL", length = 100)
	private BigDecimal noteFicheEvaluationFinale;

	@Column(name = "NOTE_RESTITUTION1", length = 100)
	private BigDecimal noteRestitution1;

	@Column(name = "NOTE_RESTITUTION2", length = 100)
	private BigDecimal noteRestitution2;
	
	@Column(name = "NOTE_RDV_PEDAGOGIQUE", length = 100)
	private BigDecimal noteRDVPedagogique;

	@Column(name = "NOTE_APPREC_GLOB", length = 100)
	private BigDecimal noteAppreciationGlobale;
	
	@Column(name = "NOTE_ACADEMIC_ENCADRANT", length = 100)
	private BigDecimal noteAcademicEncadrant;
	
	@Column(name = "NOTE_EXPERT", length = 100)
	private BigDecimal noteExpert;

	@Column(name = "NOTE_ENCADRANT_ENTREPRISE", length = 100)
	private BigDecimal noteEncadrantEntreprise;
	
	@Column(name = "NOTE_FINALE_ENCADREMENT", length = 100)
	private BigDecimal noteFinaleEncadrement;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_LAST_SAVE")
	private Date dateLastSave;
	
	@OneToOne
    @JoinColumns({ 
    	@JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET", insertable = false, updatable = false),
    	@JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
        @JoinColumn(name = "DATE_DEPOT_FICHE", referencedColumnName = "DATE_DEPOT_FICHE", insertable = false, updatable = false)
    })
	private FichePFE fichePFEGrille;
	
	
	public GrilleAcademicEncadrant() {}

	public GrilleAcademicEncadrant(GrilleAcademicEncadrantPK grilleAcademicEncadrantPK, String etatGrille,
			BigDecimal notePlanningStage, BigDecimal noteBilanPeriodiqueDebutStage,
			BigDecimal noteBilanPeriodiqueMilieuStage, BigDecimal noteBilanPeriodiqueFinStage,
			BigDecimal noteJournalBord, BigDecimal noteFicheEvaluationMiParcour, BigDecimal noteFicheEvaluationFinale,
			BigDecimal noteRestitution1, BigDecimal noteRestitution2, BigDecimal noteRDVPedagogique,
			BigDecimal noteAppreciationGlobale, BigDecimal noteAcademicEncadrant, BigDecimal noteExpert,
			BigDecimal noteEncadrantEntreprise, BigDecimal noteFinaleEncadrement)
	{
		this.grilleAcademicEncadrantPK = grilleAcademicEncadrantPK;
		this.etatGrille = etatGrille;
		this.notePlanningStage = notePlanningStage;
		this.noteBilanPeriodiqueDebutStage = noteBilanPeriodiqueDebutStage;
		this.noteBilanPeriodiqueMilieuStage = noteBilanPeriodiqueMilieuStage;
		this.noteBilanPeriodiqueFinStage = noteBilanPeriodiqueFinStage;
		this.noteJournalBord = noteJournalBord;
		this.noteFicheEvaluationMiParcour = noteFicheEvaluationMiParcour;
		this.noteFicheEvaluationFinale = noteFicheEvaluationFinale;
		this.noteRestitution1 = noteRestitution1;
		this.noteRestitution2 = noteRestitution2;
		this.noteRDVPedagogique = noteRDVPedagogique;
		this.noteAppreciationGlobale = noteAppreciationGlobale;
		this.noteAcademicEncadrant = noteAcademicEncadrant;
		this.noteExpert = noteExpert;
		this.noteEncadrantEntreprise = noteEncadrantEntreprise;
		this.noteFinaleEncadrement = noteFinaleEncadrement;
	}
	
	/***************************************** Getters & Setters *****************************************/

	public GrilleAcademicEncadrantPK getGrilleAcademicEncadrantPK() {
		return grilleAcademicEncadrantPK;
	}

	public void setGrilleAcademicEncadrantPK(GrilleAcademicEncadrantPK grilleAcademicEncadrantPK) {
		this.grilleAcademicEncadrantPK = grilleAcademicEncadrantPK;
	}

	public String getEtatGrille() {
		return etatGrille;
	}

	public void setEtatGrille(String etatGrille) {
		this.etatGrille = etatGrille;
	}

	public BigDecimal getNotePlanningStage() {
		return notePlanningStage;
	}

	public void setNotePlanningStage(BigDecimal notePlanningStage) {
		this.notePlanningStage = notePlanningStage;
	}

	public BigDecimal getNoteBilanPeriodiqueDebutStage() {
		return noteBilanPeriodiqueDebutStage;
	}

	public void setNoteBilanPeriodiqueDebutStage(BigDecimal noteBilanPeriodiqueDebutStage) {
		this.noteBilanPeriodiqueDebutStage = noteBilanPeriodiqueDebutStage;
	}

	public BigDecimal getNoteBilanPeriodiqueMilieuStage() {
		return noteBilanPeriodiqueMilieuStage;
	}

	public void setNoteBilanPeriodiqueMilieuStage(BigDecimal noteBilanPeriodiqueMilieuStage) {
		this.noteBilanPeriodiqueMilieuStage = noteBilanPeriodiqueMilieuStage;
	}

	public BigDecimal getNoteBilanPeriodiqueFinStage() {
		return noteBilanPeriodiqueFinStage;
	}

	public void setNoteBilanPeriodiqueFinStage(BigDecimal noteBilanPeriodiqueFinStage) {
		this.noteBilanPeriodiqueFinStage = noteBilanPeriodiqueFinStage;
	}

	public BigDecimal getNoteJournalBord() {
		return noteJournalBord;
	}

	public void setNoteJournalBord(BigDecimal noteJournalBord) {
		this.noteJournalBord = noteJournalBord;
	}

	public BigDecimal getNoteFicheEvaluationMiParcour() {
		return noteFicheEvaluationMiParcour;
	}

	public void setNoteFicheEvaluationMiParcour(BigDecimal noteFicheEvaluationMiParcour) {
		this.noteFicheEvaluationMiParcour = noteFicheEvaluationMiParcour;
	}

	public BigDecimal getNoteFicheEvaluationFinale() {
		return noteFicheEvaluationFinale;
	}

	public void setNoteFicheEvaluationFinale(BigDecimal noteFicheEvaluationFinale) {
		this.noteFicheEvaluationFinale = noteFicheEvaluationFinale;
	}

	public BigDecimal getNoteRestitution1() {
		return noteRestitution1;
	}

	public void setNoteRestitution1(BigDecimal noteRestitution1) {
		this.noteRestitution1 = noteRestitution1;
	}

	public BigDecimal getNoteRestitution2() {
		return noteRestitution2;
	}

	public void setNoteRestitution2(BigDecimal noteRestitution2) {
		this.noteRestitution2 = noteRestitution2;
	}

	public BigDecimal getNoteRDVPedagogique() {
		return noteRDVPedagogique;
	}

	public void setNoteRDVPedagogique(BigDecimal noteRDVPedagogique) {
		this.noteRDVPedagogique = noteRDVPedagogique;
	}

	public BigDecimal getNoteAppreciationGlobale() {
		return noteAppreciationGlobale;
	}

	public void setNoteAppreciationGlobale(BigDecimal noteAppreciationGlobale) {
		this.noteAppreciationGlobale = noteAppreciationGlobale;
	}

	public BigDecimal getNoteAcademicEncadrant() {
		return noteAcademicEncadrant;
	}

	public void setNoteAcademicEncadrant(BigDecimal noteAcademicEncadrant) {
		this.noteAcademicEncadrant = noteAcademicEncadrant;
	}

	public BigDecimal getNoteExpert() {
		return noteExpert;
	}

	public void setNoteExpert(BigDecimal noteExpert) {
		this.noteExpert = noteExpert;
	}

	public BigDecimal getNoteEncadrantEntreprise() {
		return noteEncadrantEntreprise;
	}

	public void setNoteEncadrantEntreprise(BigDecimal noteEncadrantEntreprise) {
		this.noteEncadrantEntreprise = noteEncadrantEntreprise;
	}

	public BigDecimal getNoteFinaleEncadrement() {
		return noteFinaleEncadrement;
	}

	public void setNoteFinaleEncadrement(BigDecimal noteFinaleEncadrement) {
		this.noteFinaleEncadrement = noteFinaleEncadrement;
	}

	public FichePFE getFichePFEGrille() {
		return fichePFEGrille;
	}

	public void setFichePFEGrille(FichePFE fichePFEGrille) {
		this.fichePFEGrille = fichePFEGrille;
	}

	public Date getDateLastSave() {
		return dateLastSave;
	}

	public void setDateLastSave(Date dateLastSave) {
		this.dateLastSave = dateLastSave;
	}

}

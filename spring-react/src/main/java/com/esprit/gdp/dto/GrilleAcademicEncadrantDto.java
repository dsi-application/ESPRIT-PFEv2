package com.esprit.gdp.dto;

import java.math.BigDecimal;

public class GrilleAcademicEncadrantDto
{

	private String etatGrille;
	private BigDecimal notePlanningStage;
	private BigDecimal noteBilanPeriodiqueDebutStage;
	private BigDecimal noteBilanPeriodiqueMilieuStage;
	private BigDecimal noteBilanPeriodiqueFinStage;
	private BigDecimal noteJournalBord;
	private BigDecimal noteFicheEvaluationMiParcour;
	private BigDecimal noteFicheEvaluationFinale;
	private BigDecimal noteRestitution1;
	private BigDecimal noteRestitution2;
	private BigDecimal noteRDVPedagogique;
	private BigDecimal noteAppreciationGlobale;
	private BigDecimal noteAcademicEncadrant;
	private BigDecimal noteExpert;
	private BigDecimal noteEncadrantEntreprise;
	private BigDecimal noteFinaleEncadrement;
	private String dateLastSave;
	private String emptyObject;
	
	
	public GrilleAcademicEncadrantDto() {}

	public GrilleAcademicEncadrantDto(String emptyObject)
	{
		this.emptyObject = emptyObject;
	}

	
	public GrilleAcademicEncadrantDto(String etatGrille,
			BigDecimal notePlanningStage, BigDecimal noteBilanPeriodiqueDebutStage,
			BigDecimal noteBilanPeriodiqueMilieuStage, BigDecimal noteBilanPeriodiqueFinStage,
			BigDecimal noteJournalBord, BigDecimal noteFicheEvaluationMiParcour, BigDecimal noteFicheEvaluationFinale,
			BigDecimal noteRestitution1, BigDecimal noteRestitution2, BigDecimal noteRDVPedagogique,
			BigDecimal noteAppreciationGlobale, BigDecimal noteAcademicEncadrant, BigDecimal noteExpert,
			BigDecimal noteEncadrantEntreprise, BigDecimal noteFinaleEncadrement)
	{
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

	public String getDateLastSave() {
		return dateLastSave;
	}

	public void setDateLastSave(String dateLastSave) {
		this.dateLastSave = dateLastSave;
	}

	public String getEmptyObject() {
		return emptyObject;
	}

	public void setEmptyObject(String emptyObject) {
		this.emptyObject = emptyObject;
	}

}

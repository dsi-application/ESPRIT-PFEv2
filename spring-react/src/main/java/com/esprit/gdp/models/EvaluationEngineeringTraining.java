package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ESP_EVALUATION_STG_ING")
public class EvaluationEngineeringTraining  implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_ET", length = 10)
	private String studentId;
	
	@Column(name = "ID_ENS")
	private String academicEncadrantId;

	@Column(name = "PATH_JOURNAL", length = 500)
	private String pathJournal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_UPLOAD_JOURNAL")
	private Date dateUploadJournal;
	
	@Column(name = "PATH_ATTESTATION", length = 500)
	private String pathAttestation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_UPLOAD_ATTESTATION")
	private Date dateUploadAttestation;

	@Column(name = "PATH_RAPPORT", length = 500)
	private String pathRapport;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_UPLOAD_RAPPORT")
	private Date dateUploadRapport;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_SAISIE_NOTE")
	private Date dateSaisieNote;
	
	@Column(name = "NOTE_STAGE_ING")
	private BigDecimal noteStagIngenieur;
	
	@Column(name = "ETAT_DEPOT", length = 2)
	private String etatDepot;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEMANDE_MAJ")
	private Date dateDemandeMAJ;
	
	@Column(name = "MOTIF_REFUS_DEPOT", length = 500)
	private String motifRefusDepot;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_CONFIRM_DEPOT")
	private Date dateConfirmDepot;
	
	
	public EvaluationEngineeringTraining() {}
	
	public EvaluationEngineeringTraining(String studentId)
	{
		this.studentId = studentId;
	}
	
	public EvaluationEngineeringTraining(String studentId, Date dateSaisieNote, BigDecimal noteStagIngenieur)
	{
		this.studentId = studentId;
		this.dateSaisieNote = dateSaisieNote;
		this.noteStagIngenieur = noteStagIngenieur;
	}

	public EvaluationEngineeringTraining(String studentId, String pathJournal, Date dateUploadJournal)
	{
		this.studentId = studentId;
		this.pathJournal = pathJournal;
		this.dateUploadJournal = dateUploadJournal;
	}

	/****************************************************************************************************************/
	
	
	public String getAcademicEncadrantId() {
		return academicEncadrantId;
	}

	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPathJournal() {
		return pathJournal;
	}

	public void setPathJournal(String pathJournal) {
		this.pathJournal = pathJournal;
	}

	public Date getDateUploadJournal() {
		return dateUploadJournal;
	}

	public void setDateUploadJournal(Date dateUploadJournal) {
		this.dateUploadJournal = dateUploadJournal;
	}

	public String getPathAttestation() {
		return pathAttestation;
	}

	public void setPathAttestation(String pathAttestation) {
		this.pathAttestation = pathAttestation;
	}

	public Date getDateUploadAttestation() {
		return dateUploadAttestation;
	}

	public void setDateUploadAttestation(Date dateUploadAttestation) {
		this.dateUploadAttestation = dateUploadAttestation;
	}

	public String getPathRapport() {
		return pathRapport;
	}

	public void setPathRapport(String pathRapport) {
		this.pathRapport = pathRapport;
	}

	public Date getDateUploadRapport() {
		return dateUploadRapport;
	}

	public void setDateUploadRapport(Date dateUploadRapport) {
		this.dateUploadRapport = dateUploadRapport;
	}

	public void setAcademicEncadrantId(String academicEncadrantId) {
		this.academicEncadrantId = academicEncadrantId;
	}

	public Date getDateSaisieNote() {
		return dateSaisieNote;
	}

	public void setDateSaisieNote(Date dateSaisieNote) {
		this.dateSaisieNote = dateSaisieNote;
	}

	public BigDecimal getNoteStagIngenieur() {
		return noteStagIngenieur;
	}

	public void setNoteStagIngenieur(BigDecimal noteStagIngenieur) {
		this.noteStagIngenieur = noteStagIngenieur;
	}

	public String getEtatDepot() {
		return etatDepot;
	}

	public void setEtatDepot(String etatDepot) {
		this.etatDepot = etatDepot;
	}

	public Date getDateDemandeMAJ() {
		return dateDemandeMAJ;
	}

	public void setDateDemandeMAJ(Date dateDemandeMAJ) {
		this.dateDemandeMAJ = dateDemandeMAJ;
	}

	public String getMotifRefusDepot() {
		return motifRefusDepot;
	}

	public void setMotifRefusDepot(String motifRefusDepot) {
		this.motifRefusDepot = motifRefusDepot;
	}

	public Date getDateConfirmDepot() {
		return dateConfirmDepot;
	}

	public void setDateConfirmDepot(Date dateConfirmDepot) {
		this.dateConfirmDepot = dateConfirmDepot;
	}
	
	
}

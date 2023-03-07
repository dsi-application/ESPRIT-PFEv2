package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "ESP_NOTE_STAGE_INGENIEUR")
public class NoteStageIngenieur implements Serializable
{
	
	private static final long serialVersionUID = -1539074517129557758L;

	
	@EmbeddedId
	private NoteStageIngenieurPK idNoteStageIngenieur;
	
	@Column(name = "DATE_SAISIE_NOTE")
	private Timestamp dateSaisieNote;
	
	@Column(name = "NOTE_STAGE_ING")
	private BigDecimal noteStgIng;
	
	
	public NoteStageIngenieur() {}
	
	
	public NoteStageIngenieur(NoteStageIngenieurPK idNoteStageIngenieur, BigDecimal noteStgIng, Timestamp dateSaisieNote)
	{
		this.idNoteStageIngenieur = idNoteStageIngenieur;
		this.noteStgIng = noteStgIng;
		this.dateSaisieNote = dateSaisieNote;
	}


	/*********************** Getters & Setters ***********************/

	
	public NoteStageIngenieurPK getIdNoteStageIngenieur() {
		return idNoteStageIngenieur;
	}

	public void setIdNoteStageIngenieur(NoteStageIngenieurPK idNoteStageIngenieur) {
		this.idNoteStageIngenieur = idNoteStageIngenieur;
	}

	public Timestamp getDateSaisieNote() {
		return dateSaisieNote;
	}

	public void setDateSaisieNote(Timestamp dateSaisieNote) {
		this.dateSaisieNote = dateSaisieNote;
	}

	public BigDecimal getNoteStgIng() {
		return noteStgIng;
	}

	public void setNoteStgIng(BigDecimal noteStgIng) {
		this.noteStgIng = noteStgIng;
	}

	
}

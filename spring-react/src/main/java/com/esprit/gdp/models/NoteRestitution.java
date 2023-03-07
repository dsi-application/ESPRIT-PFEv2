package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "ESP_NOTE_RESTITUTION")
public class NoteRestitution implements Serializable
{
	
	private static final long serialVersionUID = -1539074517129557758L;

	
	@EmbeddedId
	private NoteRestitutionPK idNoteRestitutionPK;
	
	@Column(name = "DATE_SAISIE_NOTE")
	private Timestamp dateSaisieNote;
	
	@Column(name = "NOTE_STAGE_REST")
	private BigDecimal noteRest;
	
	
	public NoteRestitution() {}
	
	
	public NoteRestitution(NoteRestitutionPK idNoteRestitutionPK, Timestamp dateSaisieNote, BigDecimal noteRest)
	{
		this.idNoteRestitutionPK = idNoteRestitutionPK;
		this.dateSaisieNote = dateSaisieNote;
		this.noteRest = noteRest;
	}


	/*********************** Getters & Setters ***********************/

	
	public NoteRestitutionPK getIdNoteRestitutionPK() {
		return idNoteRestitutionPK;
	}

	public void setIdNoteRestitutionPK(NoteRestitutionPK idNoteRestitutionPK) {
		this.idNoteRestitutionPK = idNoteRestitutionPK;
	}

	public Timestamp getDateSaisieNote() {
		return dateSaisieNote;
	}

	public void setDateSaisieNote(Timestamp dateSaisieNote) {
		this.dateSaisieNote = dateSaisieNote;
	}

	public BigDecimal getNoteRest() {
		return noteRest;
	}

	public void setNoteRest(BigDecimal noteRest) {
		this.noteRest = noteRest;
	}
	
}

package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
//import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saria Essid
 *
 */

@Entity
@Table(name="ESP_EVALUATION_STAGE")
public class EvaluationStage implements Serializable {

	private static final long serialVersionUID = 816212041324278199L;

	@EmbeddedId
	private EvaluationStagePK evaluationStagePK;

	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_EVALUATION")
	private Date dateEvaluation;
	
	//@Digits(integer=3, fraction=2)
	@Column(name = "NOTE_EVALUATION")
	private BigDecimal noteEvaluation;
	
	//@Digits(integer=3, fraction=2)
	@Column(name = "TAUX_EVALUATION")
	private BigDecimal tauxEvaluation;
	
	@Column(name="RESULTAT", length = 1)
	private String resultat;
	
	@ManyToOne
	@JoinColumns({ 
	    	@JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET", insertable = false, updatable = false),
	    	@JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
	        @JoinColumn(name = "DATE_DEPOT_FICHE", referencedColumnName = "DATE_DEPOT_FICHE", insertable = false, updatable = false)
	    })
	private FichePFE fichePFEEvaluationStage;
	
	
	@Override
	public String toString() {
		return "EvaluationStage [evaluationStagePK=" + evaluationStagePK + ", dateEvaluation=" + dateEvaluation
				+ ", noteEvaluation=" + noteEvaluation + ", tauxEvaluation=" + tauxEvaluation
				+ ", fichePFEEvaluationStage=" + fichePFEEvaluationStage + "]";
	}
	public EvaluationStage() {}
	public EvaluationStage(EvaluationStagePK evaluationStagePK, Date dateEvaluation,
			//@Digits(integer = 3, fraction = 2) BigDecimal noteEvaluation,
			//@Digits(integer = 3, fraction = 2) BigDecimal tauxEvaluation, 
			FichePFE fichePFEEvaluationStages) {
		super();
		this.evaluationStagePK = evaluationStagePK;
		this.dateEvaluation = dateEvaluation;
		this.noteEvaluation = noteEvaluation;
		this.tauxEvaluation = tauxEvaluation;
		this.fichePFEEvaluationStage = fichePFEEvaluationStages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateEvaluation == null) ? 0 : dateEvaluation.hashCode());
		result = prime * result + ((evaluationStagePK == null) ? 0 : evaluationStagePK.hashCode());
		result = prime * result + ((fichePFEEvaluationStage == null) ? 0 : fichePFEEvaluationStage.hashCode());
		result = prime * result + ((noteEvaluation == null) ? 0 : noteEvaluation.hashCode());
		result = prime * result + ((resultat == null) ? 0 : resultat.hashCode());
		result = prime * result + ((tauxEvaluation == null) ? 0 : tauxEvaluation.hashCode());
		return result;
	}


	


	public EvaluationStagePK getEvaluationStagePK() {
		return evaluationStagePK;
	}


	public void setEvaluationStagePK(EvaluationStagePK evaluationStagePK) {
		this.evaluationStagePK = evaluationStagePK;
	}


	public Date getDateEvaluation() {
		return dateEvaluation;
	}


	public void setDateEvaluation(Date dateEvaluation) {
		this.dateEvaluation = dateEvaluation;
	}


	public BigDecimal getNoteEvaluation() {
		return noteEvaluation;
	}


	public void setNoteEvaluation(BigDecimal noteEvaluation) {
		this.noteEvaluation = noteEvaluation;
	}


	public BigDecimal getTauxEvaluation() {
		return tauxEvaluation;
	}


	public void setTauxEvaluation(BigDecimal tauxEvaluation) {
		this.tauxEvaluation = tauxEvaluation;
	}


	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EvaluationStage other = (EvaluationStage) obj;
		if (dateEvaluation == null) {
			if (other.dateEvaluation != null)
				return false;
		} else if (!dateEvaluation.equals(other.dateEvaluation))
			return false;
		if (evaluationStagePK == null) {
			if (other.evaluationStagePK != null)
				return false;
		} else if (!evaluationStagePK.equals(other.evaluationStagePK))
			return false;
		if (fichePFEEvaluationStage == null) {
			if (other.fichePFEEvaluationStage != null)
				return false;
		} else if (!fichePFEEvaluationStage.equals(other.fichePFEEvaluationStage))
			return false;
		if (noteEvaluation == null) {
			if (other.noteEvaluation != null)
				return false;
		} else if (!noteEvaluation.equals(other.noteEvaluation))
			return false;
		if (resultat == null) {
			if (other.resultat != null)
				return false;
		} else if (!resultat.equals(other.resultat))
			return false;
		if (tauxEvaluation == null) {
			if (other.tauxEvaluation != null)
				return false;
		} else if (!tauxEvaluation.equals(other.tauxEvaluation))
			return false;
		return true;
	}
	
	@JsonIgnore
	public FichePFE getFichePFEEvaluationStage() {
		return fichePFEEvaluationStage;
	}
	public void setFichePFEEvaluationStage(FichePFE fichePFEEvaluationStage) {
		this.fichePFEEvaluationStage = fichePFEEvaluationStage;
	}
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public EvaluationStage(EvaluationStagePK evaluationStagePK, Date dateEvaluation, BigDecimal noteEvaluation,
			BigDecimal tauxEvaluation, String resultat) {
		super();
		this.evaluationStagePK = evaluationStagePK;
		this.dateEvaluation = dateEvaluation;
		this.noteEvaluation = noteEvaluation;
		this.tauxEvaluation = tauxEvaluation;
		this.resultat = resultat;
	}

	
	

	

}

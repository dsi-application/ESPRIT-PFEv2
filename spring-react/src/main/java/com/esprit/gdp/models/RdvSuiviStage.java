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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ESP_RDV_SUIVI_STAGE")
public class RdvSuiviStage  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	RdvSuiviStagePK  rdvSuiviStagePK ;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_RDV")
	private Date dateRendezVous;
	//R:realisé E: en attente A ;annulé T:Raté
	@Column(name = "TYPE_RDV", length = 1)
	private String typeRDV;
	
	@Column(name = "LIEU_RDV", length = 100)
	private String lieuRDV;
	
	@Column(name = "OBSERVATION", length = 2000)
	private String observation;
	
	
	@Column(name = "ETAT", length = 100)
	private String etat;
	
	@Column(name = "HEURE_DEBUT", length = 5)
	private String heureDebut;
	
	
	@Column(name = "URG_NOTE")
	private BigDecimal note;
	

	public RdvSuiviStage(RdvSuiviStagePK rdvSuiviStagePK, Date dateRendezVous, String typeRDV, String lieuRDV,
			String observation, String etat, String heureDebut) {
		super();
		this.rdvSuiviStagePK = rdvSuiviStagePK;
		this.dateRendezVous = dateRendezVous;
		this.typeRDV = typeRDV;
		this.lieuRDV = lieuRDV;
		this.observation = observation;
		this.etat = etat;
		this.heureDebut = heureDebut;
	}


	public RdvSuiviStage() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureDebut() {
		return heureDebut;
	}
	
	@ManyToOne
    @JoinColumns({ 
    	@JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET", insertable = false, updatable = false),
    	@JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
        @JoinColumn(name = "DATE_DEPOT_FICHE", referencedColumnName = "DATE_DEPOT_FICHE", insertable = false, updatable = false)
    })
	private FichePFE fichePFErendezVous;


	public RdvSuiviStagePK getRdvSuiviStagePK() {
		return rdvSuiviStagePK;
	}


	public void setRdvSuiviStagePK(RdvSuiviStagePK rdvSuiviStagePK) {
		this.rdvSuiviStagePK = rdvSuiviStagePK;
	}


	public String getTypeRDV() {
		return typeRDV;
	}


	public void setTypeRDV(String typeRDV) {
		this.typeRDV = typeRDV;
	}


	public String getLieuRDV() {
		return lieuRDV;
	}


	public void setLieuRDV(String lieuRDV) {
		this.lieuRDV = lieuRDV;
	}


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}

	@JsonIgnore
	public FichePFE getFichePFErendezVous() {
		return fichePFErendezVous;
	}


	public void setFichePFErendezVous(FichePFE fichePFErendezVous) {
		this.fichePFErendezVous = fichePFErendezVous;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public RdvSuiviStage(RdvSuiviStagePK rdvSuiviStagePK, String typeRDV, String lieuRDV, String observation,
			String etat, String heureDebut, FichePFE fichePFErendezVous) {
		super();
		this.rdvSuiviStagePK = rdvSuiviStagePK;
		this.typeRDV = typeRDV;
		this.lieuRDV = lieuRDV;
		this.observation = observation;
		this.etat = etat;
		this.heureDebut = heureDebut;
		this.fichePFErendezVous = fichePFErendezVous;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((fichePFErendezVous == null) ? 0 : fichePFErendezVous.hashCode());
		result = prime * result + ((heureDebut == null) ? 0 : heureDebut.hashCode());
		result = prime * result + ((lieuRDV == null) ? 0 : lieuRDV.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((rdvSuiviStagePK == null) ? 0 : rdvSuiviStagePK.hashCode());
		result = prime * result + ((typeRDV == null) ? 0 : typeRDV.hashCode());
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
		RdvSuiviStage other = (RdvSuiviStage) obj;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		if (fichePFErendezVous == null) {
			if (other.fichePFErendezVous != null)
				return false;
		} else if (!fichePFErendezVous.equals(other.fichePFErendezVous))
			return false;
		if (heureDebut == null) {
			if (other.heureDebut != null)
				return false;
		} else if (!heureDebut.equals(other.heureDebut))
			return false;
		if (lieuRDV == null) {
			if (other.lieuRDV != null)
				return false;
		} else if (!lieuRDV.equals(other.lieuRDV))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (rdvSuiviStagePK == null) {
			if (other.rdvSuiviStagePK != null)
				return false;
		} else if (!rdvSuiviStagePK.equals(other.rdvSuiviStagePK))
			return false;
		if (typeRDV == null) {
			if (other.typeRDV != null)
				return false;
		} else if (!typeRDV.equals(other.typeRDV))
			return false;
		return true;
	}


	public Date getDateRendezVous() {
		return dateRendezVous;
	}


	public void setDateRendezVous(Date dateRendezVous) {
		this.dateRendezVous = dateRendezVous;
	}


	public BigDecimal getNote() {
		return note;
	}


	public void setNote(BigDecimal note) {
		this.note = note;
	}



}

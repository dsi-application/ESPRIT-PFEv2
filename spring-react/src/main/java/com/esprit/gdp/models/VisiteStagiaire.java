package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="ESP_VISITE_STAGIAIRE")
public class VisiteStagiaire implements Serializable
{

	private static final long serialVersionUID = -6224837124174565451L;
	
	@EmbeddedId
	private VisiteStagiairePK visiteStagiairePK;
	
	@Column(name = "TYPE_VISITE", length = 1)
	private String typeVisite;
	
	@Column(name = "LIEU_VISITE", length = 250)
	private String lieuVisite;
	
	//changed from bigDecimal to string 
		@Column(name = "HEURE_DEBUT", length = 5)
		private String heureDebut;
		//changed from bigDecimal to string 	
		@Column(name = "HEURE_FIN", length = 5)
		private String heureFin;
	
	@Column(name = "OBSERVATION", length = 1000)
	private String observation;

	@JsonIgnore
	@OneToOne
    @JoinColumns({ 
    	@JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET", insertable = false, updatable = false),
    	@JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
        @JoinColumn(name = "DATE_DEPOT_FICHE", referencedColumnName = "DATE_DEPOT_FICHE", insertable = false, updatable = false)
    })
	private FichePFE fichePFEVisiteStagiaire;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fichePFEVisiteStagiaire == null) ? 0 : fichePFEVisiteStagiaire.hashCode());
		result = prime * result + ((heureDebut == null) ? 0 : heureDebut.hashCode());
		result = prime * result + ((heureFin == null) ? 0 : heureFin.hashCode());
		result = prime * result + ((lieuVisite == null) ? 0 : lieuVisite.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((typeVisite == null) ? 0 : typeVisite.hashCode());
		result = prime * result + ((visiteStagiairePK == null) ? 0 : visiteStagiairePK.hashCode());
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
		VisiteStagiaire other = (VisiteStagiaire) obj;
		if (fichePFEVisiteStagiaire == null) {
			if (other.fichePFEVisiteStagiaire != null)
				return false;
		} else if (!fichePFEVisiteStagiaire.equals(other.fichePFEVisiteStagiaire))
			return false;
		if (heureDebut == null) {
			if (other.heureDebut != null)
				return false;
		} else if (!heureDebut.equals(other.heureDebut))
			return false;
		if (heureFin == null) {
			if (other.heureFin != null)
				return false;
		} else if (!heureFin.equals(other.heureFin))
			return false;
		if (lieuVisite == null) {
			if (other.lieuVisite != null)
				return false;
		} else if (!lieuVisite.equals(other.lieuVisite))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (typeVisite == null) {
			if (other.typeVisite != null)
				return false;
		} else if (!typeVisite.equals(other.typeVisite))
			return false;
		if (visiteStagiairePK == null) {
			if (other.visiteStagiairePK != null)
				return false;
		} else if (!visiteStagiairePK.equals(other.visiteStagiairePK))
			return false;
		return true;
	}


	public VisiteStagiairePK getVisiteStagiairePK() {
		return visiteStagiairePK;
	}


	public void setVisiteStagiairePK(VisiteStagiairePK visiteStagiairePK) {
		this.visiteStagiairePK = visiteStagiairePK;
	}


	public String getTypeVisite() {
		return typeVisite;
	}


	public void setTypeVisite(String typeVisite) {
		this.typeVisite = typeVisite;
	}


	public String getLieuVisite() {
		return lieuVisite;
	}


	public void setLieuVisite(String lieuVisite) {
		this.lieuVisite = lieuVisite;
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


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}

	@JsonIgnore
	public FichePFE getFichePFEVisiteStagiaire() {
		return fichePFEVisiteStagiaire;
	}


	public void setFichePFEVisiteStagiaire(FichePFE fichePFEVisiteStagiaire) {
		this.fichePFEVisiteStagiaire = fichePFEVisiteStagiaire;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

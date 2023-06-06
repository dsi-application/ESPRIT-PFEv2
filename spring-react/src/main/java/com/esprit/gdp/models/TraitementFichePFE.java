package com.esprit.gdp.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* @author Saria Essid
*
*/

@Entity
@Table(name="ESP_TRAITEMENT_FICHE_PFE")
public class TraitementFichePFE implements Serializable
{
	
	private static final long serialVersionUID = -588102315498103785L;

	
	@EmbeddedId
	private TraitementFichePK traitementFichePK;
	
	@Column(name = "TYPE_TRT_FICHE", length = 2)
	private String typeTrtFiche;
	
	@Column(name = "DESCRIPTION", length = 750)
	private String description;
	
	@Column(name = "ETAT_TRT", length = 2)
	private String etatTrt;
	
	@Column(name = "TYPE_TRT_CONV", length = 3)
	private String typeTrtConv;
	
	@Column(name = "DATE_TRT_FICHE")
	private Timestamp dateTrtFiche;
	
	@Column(name = "MOTIF_REFUS", length = 500)
	private String motifRefus;
	
	@Column(name = "PATH_ACCORD_ANNULATION", length = 100)
	private String pathAccordAnnulation;
		
	@ManyToOne
	@JoinColumns({
	     @JoinColumn(name="ID_ET", referencedColumnName="ID_ET", insertable=false, updatable=false),
	     @JoinColumn(name="DATE_CONVENTION", referencedColumnName="DATE_CONVENTION", insertable=false, updatable=false),
	     @JoinColumn(name="DATE_DEPOT_FICHE", referencedColumnName="DATE_DEPOT_FICHE", insertable=false, updatable=false)
	})
	private FichePFE fichePFETraitementFichePFE;
	
	public TraitementFichePFE() {}


	/**************************************************** Getters & Setters **************************************************/

	
	public TraitementFichePK getTraitementFichePK() {
		return traitementFichePK;
	}

	public void setTraitementFichePK(TraitementFichePK traitementFichePK) {
		this.traitementFichePK = traitementFichePK;
	}

	public String getTypeTrtFiche() {
		return typeTrtFiche;
	}

	public void setTypeTrtFiche(String typeTrtFiche) {
		this.typeTrtFiche = typeTrtFiche;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEtatTrt() {
		return etatTrt;
	}

	public void setEtatTrt(String etatTrt) {
		this.etatTrt = etatTrt;
	}

	@JsonIgnore
	public FichePFE getFichePFETraitementFichePFE() {
		return fichePFETraitementFichePFE;
	}

	public void setFichePFETraitementFichePFE(FichePFE fichePFETraitementFichePFE) {
		this.fichePFETraitementFichePFE = fichePFETraitementFichePFE;
	}

	public String getTypeTrtConv() {
		return typeTrtConv;
	}

	public void setTypeTrtConv(String typeTrtConv) {
		this.typeTrtConv = typeTrtConv;
	}

	public Timestamp getDateTrtFiche() {
		return dateTrtFiche;
	}

	public void setDateTrtFiche(Timestamp dateTrtFiche) {
		this.dateTrtFiche = dateTrtFiche;
	}

	public String getMotifRefus() {
		return motifRefus;
	}

	public void setMotifRefus(String motifRefus) {
		this.motifRefus = motifRefus;
	}

	public String getPathAccordAnnulation() {
		return pathAccordAnnulation;
	}

	public void setPathAccordAnnulation(String pathAccordAnnulation) {
		this.pathAccordAnnulation = pathAccordAnnulation;
	}

}

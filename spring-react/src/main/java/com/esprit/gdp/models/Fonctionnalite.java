package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ESP_FONCTIONALITE")
public class Fonctionnalite implements Serializable {

	private static final long serialVersionUID = -7666570793181962421L;

	@EmbeddedId
	private FunctionnalityPK functionnalityPK;

	@Column(name = "NAME", length = 2000)
	private String name;

	@Column(name = "DESCRIPTION", length = 3000)
	private String description;

	
	
	@ManyToOne
	@JoinColumns({
	     @JoinColumn(name="ID_ET", referencedColumnName="ID_ET", insertable=false, updatable=false),
	     @JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
	     @JoinColumn(name="DATE_DEPOT_FICHE", referencedColumnName="DATE_DEPOT_FICHE", insertable=false, updatable=false)
	})
	private FichePFE fichePFEFunctionnality;

	
	
	
	public Fonctionnalite() {
	}

	
	
	
	public Fonctionnalite(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public FunctionnalityPK getFunctionnalityPK() {
		return functionnalityPK;
	}

	public void setFunctionnalityPK(FunctionnalityPK functionnalityPK) {
		this.functionnalityPK = functionnalityPK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public FichePFE getFichePFEFunctionnality() {
		return fichePFEFunctionnality;
	}

	public void setFichePFEFunctionnality(FichePFE fichePFEFunctionnality) {
		this.fichePFEFunctionnality = fichePFEFunctionnality;
	}

}

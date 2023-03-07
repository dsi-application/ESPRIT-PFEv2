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
@Table(name="ESP_PROBLEMATIQUE")
public class Problematique implements Serializable
{

	private static final long serialVersionUID = 2084058395594060331L;

	
	@EmbeddedId
	private ProblematiquePK problematicPK;
	
	@Column(name = "NAME", length = 3000)
	private String name;
	

	@ManyToOne
	@JoinColumns({
	     @JoinColumn(name="ID_ET", referencedColumnName="ID_ET", insertable=false, updatable=false),
	     @JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
	     @JoinColumn(name="DATE_DEPOT_FICHE", referencedColumnName="DATE_DEPOT_FICHE", insertable=false, updatable=false)
	})
	private FichePFE fichePFEProblematic;
	
	
	public Problematique() {}

	public Problematique(String name, FichePFE fichePFEProblematic) {
		this.name = name;
		this.fichePFEProblematic = fichePFEProblematic;
	}
	
	public Problematique(ProblematiquePK problematicPK, String name, FichePFE fichePFEProblematic) {
		super();
		this.problematicPK = problematicPK;
		this.name = name;
		this.fichePFEProblematic = fichePFEProblematic;
	}

	public ProblematiquePK getProblematicPK() {
		return problematicPK;
	}

	public void setProblematicPK(ProblematiquePK problematicPK) {
		this.problematicPK = problematicPK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public FichePFE getFichePFEProblematic() {
		return fichePFEProblematic;
	}

	public void setFichePFEProblematic(FichePFE fichePFEProblematic) {
		this.fichePFEProblematic = fichePFEProblematic;
	}

}

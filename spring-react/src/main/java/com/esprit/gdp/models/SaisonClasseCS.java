package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the CLASE database table.
 * 
 */

@Entity
@Table(name = "SYN_ESP_SAISON_CLASSE_CS")
public class SaisonClasseCS implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SaisonClasseCSPK id; 
	
	@Column(name = "POLE", length = 1)
	private String pole;

	@Column(name = "ANNEE_SCOLAIRE", length = 4)
	private String anneeScolaire;

	
	@OneToMany(mappedBy = "saisonClasse")
    public List<InscriptionCS> inscriptions;

	
	@ManyToOne
	@JoinColumn(name="CODE_CL", referencedColumnName = "CODE_CL", updatable=false, insertable=false, nullable=false)
    public Classe classe;
	
	
	@ManyToOne
    @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false,nullable=false)
	public SaisonUniversitaireCS saisonUniversitaire;
	
	public String getPole() {
		return pole;
	}


	public void setPole(String pole) {
		this.pole = pole;
	}


	public String getAnneeScolaire() {
		return anneeScolaire;
	}


	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

/*
	public List<Inscription> getInscriptions() {
		return inscriptions;
	}


	public void setInscriptions(List<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}
*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}

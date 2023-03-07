package com.esprit.gdp.models;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CLASE database table.
 * 
 */

@Entity
@Table(name = "SYN_ESP_SAISON_CLASSE")
public class SaisonClasse implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SaisonClassePK id; 
	
	@Column(name = "POLE", length = 1)
	private String pole;

	@Column(name = "ANNEE_SCOLAIRE", length = 4)
	private String anneeScolaire;

	
	@OneToMany(mappedBy = "saisonClasse")
    public List<InscriptionCJ> inscriptions;

	
	@ManyToOne
	@JoinColumn(name="CODE_CL", referencedColumnName = "CODE_CL", updatable=false, insertable=false, nullable=false)
    public Classe classe;
	
	
	@ManyToOne
    @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false,nullable=false)
	public SaisonUniversitaire saisonUniversitaire;
	
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

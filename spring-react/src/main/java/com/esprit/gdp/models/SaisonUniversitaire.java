package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SYN_ESP_SAISON_UNIVERSITAIRE")
public class SaisonUniversitaire implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ANNEE_DEB", length = 4)
	private String anneeDeb;
	

	@Column(name="ANNEE_FIN", length = 4)
	private String anneeFin;
	
	
	@Column(name="DESCRIPTION", length = 1000)
	private String description;
	
	
	@OneToMany(mappedBy = "saisonUniversitaire")
    public List<InscriptionCJ> inscriptions;
	
	
	@OneToMany(mappedBy = "saisonUniversitaire")
    public List<SaisonClasse> saisonClasses;
	
	
	

}

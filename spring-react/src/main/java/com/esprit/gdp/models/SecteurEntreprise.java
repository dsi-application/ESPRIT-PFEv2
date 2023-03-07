/**
 * 
 */
package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saria Essid
 *
 */

@Entity
@Table(name="ESP_SECTEUR_ENTREPRISE")
public class SecteurEntreprise implements Serializable
{

	private static final long serialVersionUID = 6704437859224963153L;
	
	
	
	@TableGenerator(name = "Idt_SecteurEntreprise", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_SECTEURENTREPRISE", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_SecteurEntreprise")
	@Column(name = "ID_SECTEUR", length = 2)
	private Integer idSecteur;
	
	@Column(name = "LIBELLE_SECTEUR", length = 100)
	private String libelleSecteur;
	
	
	@OneToMany(mappedBy = "secteurEntreprise")
	private List<EntrepriseAccueil> entreprisesAcceuil;


	public SecteurEntreprise() {}


	/**************************************************** Getters & Setters **************************************************/


	public Integer getIdSecteur() {
		return idSecteur;
	}

	public void setIdSecteur(Integer idSecteur) {
		this.idSecteur = idSecteur;
	}

	public String getLibelleSecteur() {
		return libelleSecteur;
	}

	public void setLibelleSecteur(String libelleSecteur) {
		this.libelleSecteur = libelleSecteur;
	}

	@JsonIgnore
	public List<EntrepriseAccueil> getEntreprisesAcceuil() {
		return entreprisesAcceuil;
	}

	public void setEntreprisesAcceuil(List<EntrepriseAccueil> entreprisesAcceuil) {
		this.entreprisesAcceuil = entreprisesAcceuil;
	}

}

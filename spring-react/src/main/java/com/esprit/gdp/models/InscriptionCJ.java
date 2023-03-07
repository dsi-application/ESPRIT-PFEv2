package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the ESP_MODULE_PANIER_CLASSE_SAISO database table.
 * 
 */

@Entity 
@Table(name = "SYN_ESP_INSCRIPTION")
public class InscriptionCJ implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InscriptionCJPK id;
	
	@Column(name="TYPE_INSC")
	private String typeInsc;
	
	@Column(name="CODE_DECISION_SESSION_P")
	private String codeDecisionSessionPrincipale;
	
	@Column(name="CODE_DECISION_SESSION_RAT")
	private String codeDecisionSessionRattrappage;

	@ManyToOne
	@JoinColumn(name="ID_ET", referencedColumnName = "ID_ET", updatable=false, insertable=false, nullable=false)
	private StudentCJ studentInscription;
	
	@ManyToOne
	@JoinColumn(name="ENCADRANT_PEDA", referencedColumnName = "ID_ENS", updatable=false, insertable=false, nullable=true)
	private Teacher encadrantPedagogique;
	
	@ManyToOne
	@JoinColumn(name = "EXPERT_PEDA", referencedColumnName="ID_ENS")
    public Teacher pedagogicalExpertCJ;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		 @JoinColumn(name="CODE_CL", referencedColumnName = "CODE_CL", updatable=false, insertable=false, nullable=false),
	     @JoinColumn(name="SEMESTRE", referencedColumnName="SEMESTRE", insertable=false, updatable=false, nullable=false),
	     @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false,nullable=false)
	})
	
    public SaisonClasse saisonClasse;
	
	
	@ManyToOne
    @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false,nullable=false)
	public SaisonUniversitaire saisonUniversitaire;
    
    
    
	@JsonIgnore
	public SaisonClasse getSaisonClasse() {
		return saisonClasse;
	}


	public void setSaisonClasse(SaisonClasse saisonClasse) {
		this.saisonClasse = saisonClasse;
	}


	public InscriptionCJ() {
	}


	public InscriptionCJPK getId() {
		return id;
	}

	public void setId(InscriptionCJPK id) {
		this.id = id;
	}


	public String getTypeInsc() {
		return typeInsc;
	}


	public void setTypeInsc(String typeInsc) {
		this.typeInsc = typeInsc;
	}


	public StudentCJ getStudentInscription() {
		return studentInscription;
	}


	public void setStudentInscription(StudentCJ studentInscription) {
		this.studentInscription = studentInscription;
	}

	@JsonIgnore
	public Teacher getEncadrantPedagogique() {
		return encadrantPedagogique;
	}


	public void setEncadrantPedagogique(Teacher encadrantPedagogique) {
		this.encadrantPedagogique = encadrantPedagogique;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encadrantPedagogique == null) ? 0 : encadrantPedagogique.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((studentInscription == null) ? 0 : studentInscription.hashCode());
		result = prime * result + ((typeInsc == null) ? 0 : typeInsc.hashCode());
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
		InscriptionCJ other = (InscriptionCJ) obj;
		if (encadrantPedagogique == null) {
			if (other.encadrantPedagogique != null)
				return false;
		} else if (!encadrantPedagogique.equals(other.encadrantPedagogique))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (studentInscription == null) {
			if (other.studentInscription != null)
				return false;
		} else if (!studentInscription.equals(other.studentInscription))
			return false;
		if (typeInsc == null) {
			if (other.typeInsc != null)
				return false;
		} else if (!typeInsc.equals(other.typeInsc))
			return false;
		return true;
	}

	public String getCodeDecisionSessionPrincipale() {
		return codeDecisionSessionPrincipale;
	}

	public void setCodeDecisionSessionPrincipale(String codeDecisionSessionPrincipale) {
		this.codeDecisionSessionPrincipale = codeDecisionSessionPrincipale;
	}

	public String getCodeDecisionSessionRattrappage() {
		return codeDecisionSessionRattrappage;
	}

	public void setCodeDecisionSessionRattrappage(String codeDecisionSessionRattrappage) {
		this.codeDecisionSessionRattrappage = codeDecisionSessionRattrappage;
	}

	public SaisonUniversitaire getSaisonUniversitaire() {
		return saisonUniversitaire;
	}
	
	public void setSaisonUniversitaire(SaisonUniversitaire saisonUniversitaire) {
		this.saisonUniversitaire = saisonUniversitaire;
	}

	public Teacher getPedagogicalExpertCJ() {
		return pedagogicalExpertCJ;
	}

	public void setPedagogicalExpertCJ(Teacher pedagogicalExpertCJ) {
		this.pedagogicalExpertCJ = pedagogicalExpertCJ;
	}


}


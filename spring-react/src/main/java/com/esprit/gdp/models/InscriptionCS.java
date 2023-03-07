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
@Table(name = "SYN_ESP_INSCRIPTION_CS")
public class InscriptionCS implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InscriptionCSPK id;
	
	@Column(name="TYPE_INSC")
	private String typeInsc;
	
	@Column(name="CODE_DECISION_SESSION_P")
	private String codeDecisionSessionPrincipale;
	
	@Column(name="CODE_DECISION_SESSION_RAT")
	private String codeDecisionSessionRattrappage;

	@ManyToOne
	@JoinColumn(name="ID_ET", referencedColumnName = "ID_ET", updatable=false, insertable=false, nullable=false)
	private StudentCS studentInscriptioncs;
	
	
	@ManyToOne
	@JoinColumn(name="ENCADRANT_PEDA", referencedColumnName = "ID_ENS", updatable=false, insertable=false, nullable=true)
	private Teacher encadrantPedagogiqueCS;
	
	@ManyToOne
	@JoinColumn(name = "EXPERT_PEDA", referencedColumnName="ID_ENS")
    public Teacher pedagogicalExpertCS;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		 @JoinColumn(name="CODE_CL", referencedColumnName = "CODE_CL", updatable=false, insertable=false, nullable=false),
	     @JoinColumn(name="SEMESTRE", referencedColumnName="SEMESTRE", insertable=false, updatable=false, nullable=false),
	     @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false,nullable=false)
	})
	
    public SaisonClasseCS saisonClasse;
	
	
	@ManyToOne
    @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false,nullable=false)
	public SaisonUniversitaireCS saisonUniversitaire;
    
    
    
	@JsonIgnore
	public SaisonClasseCS getSaisonClasse() {
		return saisonClasse;
	}


	public void setSaisonClasse(SaisonClasseCS saisonClasse) {
		this.saisonClasse = saisonClasse;
	}


	public InscriptionCS() {
	}


	public InscriptionCSPK getId() {
		return id;
	}

	public void setId(InscriptionCSPK id) {
		this.id = id;
	}


	public String getTypeInsc() {
		return typeInsc;
	}


	public void setTypeInsc(String typeInsc) {
		this.typeInsc = typeInsc;
	}

	@JsonIgnore
	public Teacher getEncadrantPedagogiqueCS(Teacher encadrantPedagogiqueCS) {
		return encadrantPedagogiqueCS;
	}


	public void setEncadrantPedagogiqueCS(Teacher encadrantPedagogiqueCS) {
		this.encadrantPedagogiqueCS = encadrantPedagogiqueCS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encadrantPedagogiqueCS == null) ? 0 : encadrantPedagogiqueCS.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((studentInscriptioncs == null) ? 0 : studentInscriptioncs.hashCode());
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
		InscriptionCS other = (InscriptionCS) obj;
		if (encadrantPedagogiqueCS == null) {
			if (other.encadrantPedagogiqueCS != null)
				return false;
		} else if (!encadrantPedagogiqueCS.equals(other.encadrantPedagogiqueCS))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (studentInscriptioncs == null) {
			if (other.studentInscriptioncs != null)
				return false;
		} else if (!studentInscriptioncs.equals(other.studentInscriptioncs))
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

	public StudentCS getStudentInscriptioncs() {
		return studentInscriptioncs;
	}


	public void setStudentInscriptioncs(StudentCS studentInscriptioncs) {
		this.studentInscriptioncs = studentInscriptioncs;
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

	public SaisonUniversitaireCS getSaisonUniversitaire() {
		return saisonUniversitaire;
	}
	
	public void setSaisonUniversitaire(SaisonUniversitaireCS saisonUniversitaire) {
		this.saisonUniversitaire = saisonUniversitaire;
	}

	public Teacher getEncadrantPedagogiqueCS() {
		return encadrantPedagogiqueCS;
	}

	public Teacher getPedagogicalExpertCS() {
		return pedagogicalExpertCS;
	}

	public void setPedagogicalExpertCS(Teacher pedagogicalExpertCS) {
		this.pedagogicalExpertCS = pedagogicalExpertCS;
	}


}


package com.esprit.gdp.models.edt;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the ESP_MODULE database table.
 * 
 */
@Entity
@Table(name = "SYN_ESP_MODULE")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODE_MODULE")
	private String codeModule;

	@Column(name = "A_EVALUER")
	private String aEvaluer;

	private BigDecimal coef;

	private String description;

	private String designation;

	private String etat;

	@Column(name = "NB_HEURES")
	private BigDecimal nbHeures;

	@Column(name = "NUM_PANIER")
	private String numPanier;

	@Column(name = "TYPE_EPREUVE")
	private String typeEpreuve;

	@Column(name = "\"UP\"")
	private String up;

	// bi-directional many-to-one association to Plan
	@OneToMany(mappedBy = "module")
	private List<Plan> espModulePanierClasseSaisos;

	public Module() {
	}

	
	/**************************************************** Getters & Setters **************************************************/

	
	public String getaEvaluer() {
		return aEvaluer;
	}

	public void setaEvaluer(String aEvaluer) {
		this.aEvaluer = aEvaluer;
	}

	public String getCodeModule() {
		return this.codeModule;
	}

	public void setCodeModule(String codeModule) {
		this.codeModule = codeModule;
	}

	public String getAEvaluer() {
		return this.aEvaluer;
	}

	public void setAEvaluer(String aEvaluer) {
		this.aEvaluer = aEvaluer;
	}

	public BigDecimal getCoef() {
		return this.coef;
	}

	public void setCoef(BigDecimal coef) {
		this.coef = coef;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public BigDecimal getNbHeures() {
		return this.nbHeures;
	}

	public void setNbHeures(BigDecimal nbHeures) {
		this.nbHeures = nbHeures;
	}

	public String getNumPanier() {
		return this.numPanier;
	}

	public void setNumPanier(String numPanier) {
		this.numPanier = numPanier;
	}

	public String getTypeEpreuve() {
		return this.typeEpreuve;
	}

	public void setTypeEpreuve(String typeEpreuve) {
		this.typeEpreuve = typeEpreuve;
	}

	public String getUp() {
		return this.up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public List<Plan> getEspModulePanierClasseSaisos() {
		return this.espModulePanierClasseSaisos;
	}

	public void setEspModulePanierClasseSaisos(List<Plan> espModulePanierClasseSaisos) {
		this.espModulePanierClasseSaisos = espModulePanierClasseSaisos;
	}

	public Plan addEspModulePanierClasseSaiso(
			Plan espModulePanierClasseSaiso) {
		getEspModulePanierClasseSaisos().add(espModulePanierClasseSaiso);
		espModulePanierClasseSaiso.setModule(this);

		return espModulePanierClasseSaiso;
	}

	public Plan removeEspModulePanierClasseSaiso(
			Plan espModulePanierClasseSaiso) {
		getEspModulePanierClasseSaisos().remove(espModulePanierClasseSaiso);
		espModulePanierClasseSaiso.setModule(null);

		return espModulePanierClasseSaiso;
	}


}
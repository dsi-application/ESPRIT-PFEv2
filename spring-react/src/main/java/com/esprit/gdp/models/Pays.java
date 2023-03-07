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


@Entity
@Table(name = "ESP_PAYS")
public class Pays implements Serializable
{
	
	private static final long serialVersionUID = -1539074517129557758L;

	
	@TableGenerator(name = "Idt_Pays", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_PAYS", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_Pays")
	@Column(name = "ID_PAYS", length = 10)
	private Integer idPays;
	
	@Column(name = "CODE_PAYS", length = 2)
	private String codePays;
	
	@Column(name = "NOM_PAYS", length = 50)
	private String nomPays;
	
	@Column(name = "PHONE_CODE", length = 50)
	private String phoneCode;
	
	@Column(name = "LANGUE_CODE", length = 5)
	private String langueCode;
	
	@OneToMany(mappedBy = "nomepays")
	private List<NomenclaturePays> states = new ArrayList<NomenclaturePays>();
	
	@OneToMany(mappedBy = "pays")
	private List<EntrepriseAccueil> entreprisesAcceuilPays = new ArrayList<EntrepriseAccueil>();


	public Pays() {}
	
	
	public Pays(String codePays, String nomPays, String phoneCode)
	{
		this.codePays = codePays;
		this.nomPays = nomPays;
		this.phoneCode = phoneCode;
	}
	

	/*********************** Getters & Setters ***********************/

	
	public Integer getIdPays() {
		return idPays;
	}

	public void setIdPays(Integer idPays) {
		this.idPays = idPays;
	}

	public String getCodePays() {
		return codePays;
	}

	public void setCodePays(String codePays) {
		this.codePays = codePays;
	}

	public String getNomPays() {
		return nomPays;
	}

	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}
	
	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	
	@JsonIgnore
	public List<EntrepriseAccueil> getEntreprisesAcceuilPays() {
		return entreprisesAcceuilPays;
	}

	public void setEntreprisesAcceuilPays(List<EntrepriseAccueil> entreprisesAcceuilPays) {
		this.entreprisesAcceuilPays = entreprisesAcceuilPays;
	}
	
	
	public List<NomenclaturePays> getStates() {
		return states;
	}

	public void setStates(List<NomenclaturePays> states) {
		this.states = states;
	}

	public String getLangueCode() {
		return langueCode;
	}

	public void setLangueCode(String langueCode) {
		this.langueCode = langueCode;
	}
	
}

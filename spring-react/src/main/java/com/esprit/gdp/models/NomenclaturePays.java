package com.esprit.gdp.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 *
 * @author Saria Essid
 *
 */


@Entity
@Table(name="NOMENCLATURE_PAYS")
public class NomenclaturePays
{
	@EmbeddedId
	private NomenclaturePaysId abi;
	
	@ManyToOne
	@JoinColumn(name="idPays", updatable=false, insertable=false, nullable=false)
	private Pays nomepays;
	
	@Column(name="codeNom", updatable=false, insertable=false, nullable=false)
	private Integer codeNom;
	
	private String libelleNom;
	
	public NomenclaturePays() {}


	public NomenclaturePays(Pays nomepays, Integer codeNom, String libelleNom)
	{
		super();
		this.abi = new NomenclaturePaysId(nomepays.getIdPays(), codeNom);
		this.nomepays = nomepays;
		this.codeNom = codeNom;
		this.libelleNom = libelleNom;
	}

	/********************************************** Getters & Setters **********************************************/

	public NomenclaturePaysId getAbi() {
		return abi;
	}
	
	@JsonIgnore
	public Pays getNomepays() {
		return nomepays;
	}


	public void setNomepays(Pays nomepays) {
		this.nomepays = nomepays;
	}


	public Integer getCodeNom() {
		return codeNom;
	}


	public void setCodeNom(Integer codeNom) {
		this.codeNom = codeNom;
	}


	public String getLibelleNom() {
		return libelleNom;
	}


	public void setLibelleNom(String libelleNom) {
		this.libelleNom = libelleNom;
	}


	public void setAbi(NomenclaturePaysId abi) {
		this.abi = abi;
	}
	
}

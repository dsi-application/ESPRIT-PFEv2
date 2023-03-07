package com.esprit.gdp.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 *
 * @author Saria Essid
 *
 */


@Entity
@Table(name="ESP_SETTINGS")
public class Settings
{
	@EmbeddedId
	private SettingsId abi;
	
	@ManyToOne
	@JoinColumn(name="idPackage", updatable=false, insertable=false, nullable=false)
	private Package pack;
	
	@Column(name="codeNom", updatable=false, insertable=false, nullable=false)
	private Integer codeNom;
	
	private String libelleNom;
	
	public Settings() {}


	public Settings(Package pack, Integer codeNom, String libelleNom)
	{
		super();
		// this.abi = new SettingsId(pack.getIdPays(), codeNom);
		this.pack = pack;
		this.codeNom = codeNom;
		this.libelleNom = libelleNom;
	}

	/********************************************** Getters & Setters **********************************************/

	public SettingsId getAbi() {
		return abi;
	}

	public void setAbi(SettingsId abi) {
		this.abi = abi;
	}

	public Package getPack() {
		return pack;
	}

	public void setPack(Package pack) {
		this.pack = pack;
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

}

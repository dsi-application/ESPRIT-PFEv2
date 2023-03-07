package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Saria Essid
 *
 */

@Entity
@Table(name="SYN_SOCIETE")
public class Societe implements Serializable
{
	
	private static final long serialVersionUID = -5743201463396845897L;

	
	@Id
	@Column(name = "CODE_SOC", length = 10)
	private String codeSoc;
	
	@Column(name = "DUREE_PFE", length = 2)
	private Integer dureePFE;
	
	
	public Societe() {}
	
	public Societe(String codeSoc, Integer dureePFE)
	{
		this.codeSoc = codeSoc;
		this.dureePFE = dureePFE;
	}


	/**************************************************** Getters & Setters **************************************************/


	public String getCodeSoc() {
		return codeSoc;
	}

	public void setCodeSoc(String codeSoc) {
		this.codeSoc = codeSoc;
	}

	public Integer getDureePFE() {
		return dureePFE;
	}

	public void setDureePFE(Integer dureePFE) {
		this.dureePFE = dureePFE;
	}
	
}

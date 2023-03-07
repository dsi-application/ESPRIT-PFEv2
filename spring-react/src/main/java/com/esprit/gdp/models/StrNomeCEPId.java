package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author Saria Essid
 *
 */


@Embeddable
public class StrNomeCEPId implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ANNEE_DEB", length = 4)
	private String anneeDeb;
	
	@Column(name = "CODE_STR_CEP", length = 10)
	private String codeStrCEP;
	
	
	public StrNomeCEPId() {}

	public StrNomeCEPId(String anneeDeb, String codeStrCEP) {
		super();
		this.anneeDeb = anneeDeb;
		this.codeStrCEP = codeStrCEP;
	}

	
	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	public String getCodeStrCEP() {
		return codeStrCEP;
	}

	public void setCodeStrCEP(String codeStrCEP) {
		this.codeStrCEP = codeStrCEP;
	}
	
}

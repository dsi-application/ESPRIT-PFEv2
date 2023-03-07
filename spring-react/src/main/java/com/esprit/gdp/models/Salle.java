package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SYN_SALLE")
public class Salle implements Serializable
{

	private static final long serialVersionUID = -8899968697607843994L;

	
	
	@Id
	@Column(name="CODE_SALLE", length = 20)
	private String codeSalle;
	
	@Column(name = "DESCRIPTION", length = 50)
	private String description;
	
	@Column(name = "POLE", length = 2)
	private String pole;
	
	@OneToMany(mappedBy = "salle")
	private List<FichePFE> fichePFEs;

	
	public Salle() {}
	
	public Salle(String codeSalle, String description, String pole, List<FichePFE> fichePFEs) {
		super();
		this.codeSalle = codeSalle;
		this.description = description;
		this.pole = pole;
		this.fichePFEs = fichePFEs;
	}

	public String getCodeSalle() {
		return codeSalle;
	}

	public void setCodeSalle(String codeSalle) {
		this.codeSalle = codeSalle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPole() {
		return pole;
	}

	public void setPole(String pole) {
		this.pole = pole;
	}

	public List<FichePFE> getFichePFEs() {
		return fichePFEs;
	}

	public void setFichePFEs(List<FichePFE> fichePFEs) {
		this.fichePFEs = fichePFEs;
	}
	
	
}


package com.esprit.gdp.models;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Technologie
 *
 */
@Entity 
@Table( name="ESP_TECHNOLOGIE")
public class Technologie implements Serializable
{

	private static final long serialVersionUID = -8998933256817684077L;

	
	
	@TableGenerator(name = "Idt_Technologie", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_TECHNOLOGIE", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_Technologie")
	@Column(name="ID_TECHNOLOGIE", length = 10)
	private Integer idTechnologie;
	
	@Column(name="NAME", length = 255)
	private String name;
	
	@Column(name="ETAT", length = 1)
	private String etat;
	
	
	public Technologie() {}
	
	
	public Technologie(String name)
	{
		this.name = name;
	}

	public Integer getIdTechnologie() {
		return idTechnologie;
	}

	public void setIdTechnologie(Integer idTechnologie) {
		this.idTechnologie = idTechnologie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
}

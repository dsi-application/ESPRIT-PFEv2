package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the CLASE database table.
 * 
 */

@Entity
@Table(name = "SYN_ESP_OPTION")
public class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OptionPK id;


	@Column(name = "LIB_OPTION")
	private String libOption;

	@Column(name = "DATE_CREATION")
	private String dateCreation;
	
	@Column(name = "DATE_MAJ")
	private String dateMaj;
	
	
	@OneToMany(mappedBy = "option")
	private List<PedagogicalCoordinator> responsableStages;


	public OptionPK getId() {
		return id;
	}


	public void setId(OptionPK id) {
		this.id = id;
	}


	public String getLibOption() {
		return libOption;
	}


	public void setLibOption(String libOption) {
		this.libOption = libOption;
	}


	public String getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}


	public String getDateMaj() {
		return dateMaj;
	}


	public void setDateMaj(String dateMaj) {
		this.dateMaj = dateMaj;
	}


	public List<PedagogicalCoordinator> getResponsableStages() {
		return responsableStages;
	}


	public void setResponsableStages(List<PedagogicalCoordinator> responsableStages) {
		this.responsableStages = responsableStages;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateMaj == null) ? 0 : dateMaj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libOption == null) ? 0 : libOption.hashCode());
		result = prime * result + ((responsableStages == null) ? 0 : responsableStages.hashCode());
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
		Option other = (Option) obj;
		if (dateCreation == null) {
			if (other.dateCreation != null)
				return false;
		} else if (!dateCreation.equals(other.dateCreation))
			return false;
		if (dateMaj == null) {
			if (other.dateMaj != null)
				return false;
		} else if (!dateMaj.equals(other.dateMaj))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (libOption == null) {
			if (other.libOption != null)
				return false;
		} else if (!libOption.equals(other.libOption))
			return false;
		if (responsableStages == null) {
			if (other.responsableStages != null)
				return false;
		} else if (!responsableStages.equals(other.responsableStages))
			return false;
		return true;
	}


	public Option() {
		super();
		// TODO Auto-generated constructor stub
	}





}

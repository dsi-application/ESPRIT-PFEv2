package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @author Oumeima 
 *
 */
@Entity
@Table(name = "ESP_RESPONSABLE_STAGE")
public class ResponsableStage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ResponsableStageId responsableStageId  ;

	
	
	@Column(name = "DATE_AFFECTATION")
	private Date dateAffectation;
	
	@ManyToOne
	@JoinColumns({
	     @JoinColumn(name="ESP_ID_OPTION", referencedColumnName="CODE_OPTION", insertable=false, updatable=false),
	     @JoinColumn(name="ANNEE_DEB", referencedColumnName="ANNEE_DEB", insertable=false, updatable=false)
	})
    public Option option;
	
	@ManyToOne
	@JoinColumn(name = "ESP_ID_ENS", referencedColumnName="ID_ENS", updatable=false, insertable=false, nullable=false)
    public Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name = "ID_COORDINATOR", referencedColumnName="ID_ENS")
    public Teacher pedagogicalCoordinator;
	

	public ResponsableStageId getResponsableStageId() {
		return responsableStageId;
	}

	public void setResponsableStageId(ResponsableStageId responsableStageId) {
		this.responsableStageId = responsableStageId;
	}

	public Date getDateAffectation() {
		return dateAffectation;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAffectation == null) ? 0 : dateAffectation.hashCode());
		result = prime * result + ((option == null) ? 0 : option.hashCode());
		result = prime * result + ((responsableStageId == null) ? 0 : responsableStageId.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
		ResponsableStage other = (ResponsableStage) obj;
		if (dateAffectation == null) {
			if (other.dateAffectation != null)
				return false;
		} else if (!dateAffectation.equals(other.dateAffectation))
			return false;
		if (option == null) {
			if (other.option != null)
				return false;
		} else if (!option.equals(other.option))
			return false;
		if (responsableStageId == null) {
			if (other.responsableStageId != null)
				return false;
		} else if (!responsableStageId.equals(other.responsableStageId))
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		return true;
	}
	
	public ResponsableStage() {}

	public ResponsableStage(ResponsableStageId responsableStageId, Date dateAffectation, Option option,
			Teacher teacher) {
		super();
		this.responsableStageId = responsableStageId;
		this.dateAffectation = dateAffectation;
		this.option = option;
		this.teacher = teacher;
	}

	public Teacher getPedagogicalCoordinator() {
		return pedagogicalCoordinator;
	}

	public void setPedagogicalCoordinator(Teacher pedagogicalCoordinator) {
		this.pedagogicalCoordinator = pedagogicalCoordinator;
	}

	
}

package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author Saria Essid
 *
 */

@Embeddable
public class NoteStageIngenieurPK implements Serializable
{

	private static final long serialVersionUID = 816212041324278199L;

	
	private String studentId;
	private String teacherId;
	private String anneeDeb;
	
	public NoteStageIngenieurPK() {}

	public NoteStageIngenieurPK(String teacherId, String studentId, String anneeDeb) {
		this.teacherId = teacherId;
		this.studentId = studentId;
		this.anneeDeb = anneeDeb;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anneeDeb == null) ? 0 : anneeDeb.hashCode());
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
		result = prime * result + ((teacherId == null) ? 0 : teacherId.hashCode());
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
		NoteStageIngenieurPK other = (NoteStageIngenieurPK) obj;
		if (anneeDeb == null) {
			if (other.anneeDeb != null)
				return false;
		} else if (!anneeDeb.equals(other.anneeDeb))
			return false;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		if (teacherId == null) {
			if (other.teacherId != null)
				return false;
		} else if (!teacherId.equals(other.teacherId))
			return false;
		return true;
	}

	
	/**************************************************** Getters & Setters **************************************************/

	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}


}

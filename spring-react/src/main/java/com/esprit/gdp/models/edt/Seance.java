package com.esprit.gdp.models.edt;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the SEANCE database table.
 * 
 */
@Entity
@Table(name = "SYN_SEANCE")
public class Seance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String codeseance;

	private String heuredeb;

	private String heurefin;

	@OneToMany(mappedBy = "seance")
	private List<WeekSchedule> emploisemaines;

	public Seance() {
	}

	public String getCodeseance() {
		return this.codeseance;
	}

	public void setCodeseance(String codeseance) {
		this.codeseance = codeseance;
	}

	public String getHeuredeb() {
		return this.heuredeb;
	}

	public void setHeuredeb(String heuredeb) {
		this.heuredeb = heuredeb;
	}

	public String getHeurefin() {
		return this.heurefin;
	}

	public void setHeurefin(String heurefin) {
		this.heurefin = heurefin;
	}

	public List<WeekSchedule> getEmploisemaines() {
		return emploisemaines;
	}

	public void setEmploisemaines(List<WeekSchedule> emploisemaines) {
		this.emploisemaines = emploisemaines;
	}

}
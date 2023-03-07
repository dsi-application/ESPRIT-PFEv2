package com.esprit.gdp.models.edt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.esprit.gdp.models.Salle;
import com.esprit.gdp.models.Teacher;


/**
 * The persistent class for the EMPLOITEMPS database table.
 * 
 */
@Entity
@Table(name = "SYN_EMPLOISEMAINE")
public class WeekSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pk_sequencesem", sequenceName = "EMPLOISEMAINE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequencesem")
	private long codemploi;

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "SYN_EMPLOISEMENS", joinColumns = { @JoinColumn(name = "CODEMPLOI") }, inverseJoinColumns = {
			@JoinColumn(name = "IDENS") })
	private List<Teacher> teachers;

	@Temporal(TemporalType.DATE)
	private Date datecour;

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "SYN_EMPSEM_SALLE", joinColumns = { @JoinColumn(name = "CODEMPLOI") }, inverseJoinColumns = {
			@JoinColumn(name = "CODESALLE") })
	private List<Salle> salles;

	@ManyToOne
	@JoinColumn(name = "CODESEANCE")
	private Seance seance;
	
	private long codplan;
	
	private String actif;
	
	private String anneedeb;
	
	private Integer semestre;

	public long getCodemploi() {
		return codemploi;
	}

	public Date getDatecour() {
		return datecour;
	}

	public void setDatecour(Date datecour) {
		this.datecour = datecour;
	}

	public void setCodemploi(long codemploi) {
		this.codemploi = codemploi;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

//	public Date getDateCour() {
//		return dateCour;
//	}
//
//	public void setDateCour(Date dateCour) {
//		this.dateCour = dateCour;
//	}

	public List<Salle> getSalles() {
		return salles;
	}

	public void setSalles(List<Salle> salles) {
		this.salles = salles;
	}

	public Seance getSeance() {
		return seance;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}

	public String getActif() {
		return actif;
	}

	public void setActif(String actif) {
		this.actif = actif;
	}

	public long getCodplan() {
		return codplan;
	}

	public void setCodplan(long codplan) {
		this.codplan = codplan;
	}

	public String getAnneedeb() {
		return anneedeb;
	}

	public void setAnneedeb(String anneedeb) {
		this.anneedeb = anneedeb;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

}
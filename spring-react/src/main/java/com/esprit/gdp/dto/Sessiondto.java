package com.esprit.gdp.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Sessiondto {

	
	
	
	private Integer idSession;
	private String libelleSession;
	private Date dateDebut;
	private Date dateFin;
	private Integer nbr ;
	
	
	
	public Sessiondto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Sessiondto(Integer idSession, String libelleSession, Date dateDebut, Date dateFin, Integer nbr) {
		super();
		this.idSession = idSession;
		this.libelleSession = libelleSession;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.nbr = nbr;
	}



	public Integer getIdSession() {
		return idSession;
	}



	public void setIdSession(Integer idSession) {
		this.idSession = idSession;
	}



	public String getLibelleSession() {
		return libelleSession;
	}



	public void setLibelleSession(String libelleSession) {
		this.libelleSession = libelleSession;
	}



	public Date getDateDebut() {
		return dateDebut;
	}



	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}



	public Date getDateFin() {
		return dateFin;
	}



	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}



	public Integer getNbr() {
		return nbr;
	}



	public void setNbr(Integer nbr) {
		this.nbr = nbr;
	}
	
	
	
}

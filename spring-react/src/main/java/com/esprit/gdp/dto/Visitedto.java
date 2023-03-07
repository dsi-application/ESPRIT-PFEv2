package com.esprit.gdp.dto;

import java.sql.Timestamp;
import java.util.Date;

public class Visitedto {

	private String idEt;
	private Timestamp dateDepotFiche;
	private Date dateVisite;
	private String typeVisite;
	private String lieuVisite;
	private String heureDebut;
	private String heureFin;
	private String observation;
	private String fullNameEt;
	
	public String getIdEt() {
		return idEt;
	}
	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}
	public Timestamp getDateDepotFiche() {
		return dateDepotFiche;
	}
	public void setDateDepotFiche(Timestamp dateDepotFiche) {
		this.dateDepotFiche = dateDepotFiche;
	}
	public Date getDateVisite() {
		return dateVisite;
	}
	public void setDateVisite(Date dateVisite) {
		this.dateVisite = dateVisite;
	}
	public String getTypeVisite() {
		return typeVisite;
	}
	public void setTypeVisite(String typeVisite) {
		this.typeVisite = typeVisite;
	}
	public String getLieuVisite() {
		return lieuVisite;
	}
	public void setLieuVisite(String lieuVisite) {
		this.lieuVisite = lieuVisite;
	}
	public String getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}
	public String getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public Visitedto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Visitedto(String idEt, Timestamp dateDepotFiche, Date dateVisite, String typeVisite, String lieuVisite,
			String heureDebut, String heureFin, String observation, String fullNameEt) {
		super();
		this.idEt = idEt;
		this.dateDepotFiche = dateDepotFiche;
		this.dateVisite = dateVisite;
		this.typeVisite = typeVisite;
		this.lieuVisite = lieuVisite;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.observation = observation;
		this.fullNameEt = fullNameEt;
	}
	public String getFullNameEt() {
		return fullNameEt;
	}
	public void setFullNameEt(String fullNameEt) {
		this.fullNameEt = fullNameEt;
	}
	
}

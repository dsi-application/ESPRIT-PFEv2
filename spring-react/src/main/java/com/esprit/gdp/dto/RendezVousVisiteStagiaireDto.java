package com.esprit.gdp.dto;

import java.util.Date;

public class RendezVousVisiteStagiaireDto
{
	
	private String kind;
	private String date;
	private String heureDebut;
	private String heureFin;
	private String lieu;
	private String observation;
	private String type;
	private Date dateDate;

	
	public RendezVousVisiteStagiaireDto() {}
	
	public RendezVousVisiteStagiaireDto(String kind, String date, String heureDebut, String heureFin, String lieu,
			String observation, String type, Date dateDate) 
	{
		this.kind = kind;
		this.date = date;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.lieu = lieu;
		this.observation = observation;
		this.type = type;
		this.dateDate = dateDate;
	}

	
	
	/************************************************ Getters & Setters *************************************************/

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateDate() {
		return dateDate;
	}

	public void setDateDate(Date dateDate) {
		this.dateDate = dateDate;
	}
	
}

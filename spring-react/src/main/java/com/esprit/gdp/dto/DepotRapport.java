package com.esprit.gdp.dto;

import java.sql.Timestamp;
import java.util.Date;

public class DepotRapport {
	private String idEt;
	private String fullName;
	private String nomet;
	private String prenomet;
	private String pathRapport;
	private String pathPlagiat;
	private String pathAttestationStage;
	private String pathDossierTechnique;
	private String EtatFiche ;
	private String EtatDepot ;

	private String dateDepotFiche;

	private Integer trainingDuration;
	private Date dateDepotReports;

	private String etatGrilleEncadrement;


	public DepotRapport() {
		super();
	}


	public DepotRapport(String idEt, String nomet, String prenomet, String pathRapport, String pathPlagiat,
						String pathAttestationStage, String dossierTechnique, String etatFiche, String etatDepot, String dateDepotFiche) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = dossierTechnique;
		this.EtatFiche = etatFiche;
		this.EtatDepot = etatDepot;
		this.dateDepotFiche = dateDepotFiche;
	}

	/*
		public DepotRapport(String idEt, String nomet, String prenomet, String pathRapport, String pathPlagiat,
			String pathAttestationStage, String pathDossierTechnique, String etatFiche, String etatDepot) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = pathDossierTechnique;
		EtatFiche = etatFiche;
		EtatDepot = etatDepot;
	}

	public DepotRapport(String idEt, String nomet, String prenomet, String pathRapport, String pathPlagiat,
			String pathAttestationStage, String etatFiche, String etatDepot) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		EtatFiche = etatFiche;
		EtatDepot = etatDepot;
	}

	public DepotRapport(String idEt, String nomet, String prenomet, String pathRapport, String pathPlagiat,
			String pathAttestationStage, String dossierTechnique, String etatFiche, String etatDepot, Timestamp dateFiche) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = dossierTechnique;
		EtatFiche = etatFiche;
		EtatDepot = etatDepot;
	}

	public DepotRapport(String idEt, String nomet, String prenomet, String pathRapport, String pathPlagiat,
						String pathAttestationStage, String pathDossierTechnique, String etatFiche, String etatDepot,
						Timestamp dateFiche, Integer trainingDuration, Date dateDepotReports) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = pathDossierTechnique;
		EtatFiche = etatFiche;
		EtatDepot = etatDepot;
		this.trainingDuration = trainingDuration;
		this.dateDepotReports = dateDepotReports;
	}

	public DepotRapport(String idEt, String fullName, String pathRapport, String pathPlagiat,
			String pathAttestationStage, String pathDossierTechnique, String etatFiche, String etatDepot,
			Timestamp dateFiche, Integer trainingDuration, Date dateDepotReports) {
		super();
		this.idEt = idEt;
		this.fullName = fullName;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = pathDossierTechnique;
		EtatFiche = etatFiche;
		EtatDepot = etatDepot;
		DateFiche = dateFiche;
		this.trainingDuration = trainingDuration;
		this.dateDepotReports = dateDepotReports;
	}

		public DepotRapport(String idEt, String nomet, String prenomet, String pathRapport, String pathPlagiat,
			String pathAttestationStage, String pathDossierTechnique, String etatFiche, String etatDepot, Date dateDepotReports) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = pathDossierTechnique;
		EtatFiche = etatFiche;
		EtatDepot = etatDepot;
		this.dateDepotReports = dateDepotReports;
	}

	public DepotRapport(String idEt, String nomet, String prenomet, String pathRapport, String pathPlagiat,
			String pathAttestationStage, String pathDossierTechnique, String etatFiche, String etatDepot,
			Timestamp dateFiche, Date dateDepotReports) {
		super();
		this.idEt = idEt;
		this.nomet = nomet;
		this.prenomet = prenomet;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = pathDossierTechnique;
		EtatFiche = etatFiche;
		EtatDepot = etatDepot;
		DateFiche = dateFiche;
		this.dateDepotReports = dateDepotReports;
	}
	*/

	public DepotRapport(String idEt, String fullName, String pathRapport, String pathPlagiat,
						String pathAttestationStage, String pathDossierTechnique, String etatFiche, String etatDepot,
						String dateDepotFiche, Integer trainingDuration, Date dateDepotReports) {
		super();
		this.idEt = idEt;
		this.fullName = fullName;
		this.pathRapport = pathRapport;
		this.pathPlagiat = pathPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.pathDossierTechnique = pathDossierTechnique;
		this.EtatFiche = etatFiche;
		this.EtatDepot = etatDepot;
		this.dateDepotFiche = dateDepotFiche;
		this.trainingDuration = trainingDuration;
		this.dateDepotReports = dateDepotReports;
	}


	/************************************************** Getters & Setters *********************************************/

	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public String getNomet() {
		return nomet;
	}

	public void setNomet(String nomet) {
		this.nomet = nomet;
	}

	public String getPrenomet() {
		return prenomet;
	}

	public void setPrenomet(String prenomet) {
		this.prenomet = prenomet;
	}

	public String getPathRapport() {
		return pathRapport;
	}

	public void setPathRapport(String pathRapport) {
		this.pathRapport = pathRapport;
	}

	public String getPathPlagiat() {
		return pathPlagiat;
	}

	public void setPathPlagiat(String pathPlagiat) {
		this.pathPlagiat = pathPlagiat;
	}

	public String getPathAttestationStage() {
		return pathAttestationStage;
	}

	public void setPathAttestationStage(String pathAttestationStage) {
		this.pathAttestationStage = pathAttestationStage;
	}

	public String getPathDossierTechnique() {
		return pathDossierTechnique;
	}

	public void setPathDossierTechnique(String pathDossierTechnique) {
		this.pathDossierTechnique = pathDossierTechnique;
	}

	public String getEtatFiche() {
		return EtatFiche;
	}

	public void setEtatFiche(String etatFiche) {
		EtatFiche = etatFiche;
	}

	public String getEtatDepot() {
		return EtatDepot;
	}

	public void setEtatDepot(String etatDepot) {
		EtatDepot = etatDepot;
	}

	public Integer getTrainingDuration() {
		return trainingDuration;
	}

	public void setTrainingDuration(Integer trainingDuration) {
		this.trainingDuration = trainingDuration;
	}

	public Date getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(Date dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEtatGrilleEncadrement() {
		return etatGrilleEncadrement;
	}

	public void setEtatGrilleEncadrement(String etatGrilleEncadrement) { this.etatGrilleEncadrement = etatGrilleEncadrement; }

	public String getDateDepotFiche() { return dateDepotFiche; }

	public void setDateDepotFiche(String dateDepotFiche) { this.dateDepotFiche = dateDepotFiche; }

}

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
	private Timestamp DateFiche ;
	
	private Integer trainingDuration;
	private Date dateDepotReports;
	
	private String etatGrilleEncadrement;
	
	
	public DepotRapport() {
		super();
	}
	
	public DepotRapport(String idEt)
	{
		this.idEt = idEt;
	}
	
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
		DateFiche = dateFiche;
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
		DateFiche = dateFiche;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DateFiche == null) ? 0 : DateFiche.hashCode());
		result = prime * result + ((EtatDepot == null) ? 0 : EtatDepot.hashCode());
		result = prime * result + ((EtatFiche == null) ? 0 : EtatFiche.hashCode());
		result = prime * result + ((idEt == null) ? 0 : idEt.hashCode());
		result = prime * result + ((nomet == null) ? 0 : nomet.hashCode());
		result = prime * result + ((pathAttestationStage == null) ? 0 : pathAttestationStage.hashCode());
		result = prime * result + ((pathPlagiat == null) ? 0 : pathPlagiat.hashCode());
		result = prime * result + ((pathRapport == null) ? 0 : pathRapport.hashCode());
		result = prime * result + ((prenomet == null) ? 0 : prenomet.hashCode());
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
		DepotRapport other = (DepotRapport) obj;
		if (DateFiche == null) {
			if (other.DateFiche != null)
				return false;
		} else if (!DateFiche.equals(other.DateFiche))
			return false;
		if (EtatDepot == null) {
			if (other.EtatDepot != null)
				return false;
		} else if (!EtatDepot.equals(other.EtatDepot))
			return false;
		if (EtatFiche == null) {
			if (other.EtatFiche != null)
				return false;
		} else if (!EtatFiche.equals(other.EtatFiche))
			return false;
		if (idEt == null) {
			if (other.idEt != null)
				return false;
		} else if (!idEt.equals(other.idEt))
			return false;
		if (nomet == null) {
			if (other.nomet != null)
				return false;
		} else if (!nomet.equals(other.nomet))
			return false;
		if (pathAttestationStage == null) {
			if (other.pathAttestationStage != null)
				return false;
		} else if (!pathAttestationStage.equals(other.pathAttestationStage))
			return false;
		if (pathPlagiat == null) {
			if (other.pathPlagiat != null)
				return false;
		} else if (!pathPlagiat.equals(other.pathPlagiat))
			return false;
		if (pathRapport == null) {
			if (other.pathRapport != null)
				return false;
		} else if (!pathRapport.equals(other.pathRapport))
			return false;
		if (prenomet == null) {
			if (other.prenomet != null)
				return false;
		} else if (!prenomet.equals(other.prenomet))
			return false;
		return true;
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

	public Timestamp getDateFiche() {
		return DateFiche;
	}

	public void setDateFiche(Timestamp dateFiche) {
		DateFiche = dateFiche;
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

	public void setEtatGrilleEncadrement(String etatGrilleEncadrement) {
		this.etatGrilleEncadrement = etatGrilleEncadrement;
	}
	
}

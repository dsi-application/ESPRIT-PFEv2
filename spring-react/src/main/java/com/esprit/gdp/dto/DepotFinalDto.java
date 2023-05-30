package com.esprit.gdp.dto;

import com.esprit.gdp.models.FichePFEPK;

import java.util.Date;

public class DepotFinalDto {

    private FichePFEPK fichePFEPK;
    private String idEt;
    private String fullName;
    private String pathRapport;
    private String pathPlagiat;
    private String pathAttestationStage;
    private String pathDossierTechnique;
    private String classeEt;
    private String optionEt;
    private Integer trainingDuration;
    private Date dateDepotReports;
    private String etatGrilleEncadrement;
    private String etatDepot;
    private String dateDepotFiche;

    public DepotFinalDto() {
        super();
    }

    public DepotFinalDto(FichePFEPK fichePFEPK, String idEt, String pathRapport, String pathPlagiat,
                         String pathAttestationStage, String pathDossierTechnique, String etatDepot, String dateDepotFiche, Date dateDepotReports) {
        super();
        this.fichePFEPK = fichePFEPK;
        this.idEt = idEt;
        this.pathRapport = pathRapport;
        this.pathPlagiat = pathPlagiat;
        this.pathAttestationStage = pathAttestationStage;
        this.pathDossierTechnique = pathDossierTechnique;
        this.etatDepot = etatDepot;
        this.dateDepotFiche = dateDepotFiche;
        this.dateDepotReports = dateDepotReports;
    }

    /************************************************** Getters & Setters *********************************************/

    public FichePFEPK getFichePFEPK() {
        return fichePFEPK;
    }

    public void setFichePFEPK(FichePFEPK fichePFEPK) {
        this.fichePFEPK = fichePFEPK;
    }

    public String getIdEt() {
        return idEt;
    }

    public void setIdEt(String idEt) {
        this.idEt = idEt;
    }

    public String getClasseEt() {
        return classeEt;
    }

    public void setClasseEt(String classeEt) {
        this.classeEt = classeEt;
    }

    public String getOptionEt() {
        return optionEt;
    }

    public void setOptionEt(String optionEt) {
        this.optionEt = optionEt;
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

    public String getEtatDepot() {
        return etatDepot;
    }

    public void setEtatDepot(String etatDepot) {
        this.etatDepot = etatDepot;
    }

    public void setDateDepotFiche(String dateDepotFiche) {
        this.dateDepotFiche = dateDepotFiche;
    }

    public String getDateDepotFiche() {
        return dateDepotFiche;
    }

}

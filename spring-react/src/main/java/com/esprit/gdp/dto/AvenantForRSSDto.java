package com.esprit.gdp.dto;

import com.esprit.gdp.models.EntrepriseAccueil;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public class AvenantForRSSDto {

    private String dateDepotAvenant;
    private String dateDepotRelatedConvention;
    private Date dateDebut;
    private Date dateFin;
    private String idEt;
    private String nomEt;
    private String departEt;
    private Boolean traiter;
    private String pathAvenant;
    private String pathSignedAvenant;
    private String currentClasse;
    private String responsableEntreprise;
    private String qualiteResponsable;


    public AvenantForRSSDto() {
    }

    public AvenantForRSSDto(String dateDepotAvenant, String dateDepotRelatedConvention, Date dateDebut, Date dateFin, String idEt, Boolean traiter, String pathAvenant, String pathSignedAvenant, String responsableEntreprise, String qualiteResponsable) {
        this.dateDepotAvenant = dateDepotAvenant;
        this.dateDepotRelatedConvention = dateDepotRelatedConvention;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idEt = idEt;
        this.traiter = traiter;
        this.pathAvenant = pathAvenant;
        this.pathSignedAvenant = pathSignedAvenant;
        this.responsableEntreprise = responsableEntreprise;
        this.qualiteResponsable = qualiteResponsable;
    }


    /********************************** Getters & Setters **********************************/

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

    public String getIdEt() {
        return idEt;
    }

    public void setIdEt(String idEt) {
        this.idEt = idEt;
    }

    public String getNomEt() {
        return nomEt;
    }

    public void setNomEt(String nomEt) {
        this.nomEt = nomEt;
    }

    public String getDepartEt() {
        return departEt;
    }

    public void setDepartEt(String departEt) {
        this.departEt = departEt;
    }

    public Boolean getTraiter() {
        return traiter;
    }

    public void setTraiter(Boolean traiter) {
        this.traiter = traiter;
    }

    public String getCurrentClasse() {
        return currentClasse;
    }

    public void setCurrentClasse(String currentClasse) {
        this.currentClasse = currentClasse;
    }

    public String getDateDepotAvenant() {
        return dateDepotAvenant;
    }

    public void setDateDepotAvenant(String dateDepotAvenant) {
        this.dateDepotAvenant = dateDepotAvenant;
    }

    public String getPathAvenant() {
        return pathAvenant;
    }

    public void setPathAvenant(String pathAvenant) {
        this.pathAvenant = pathAvenant;
    }

    public String getDateDepotRelatedConvention() {
        return dateDepotRelatedConvention;
    }

    public void setDateDepotRelatedConvention(String dateDepotRelatedConvention) {
        this.dateDepotRelatedConvention = dateDepotRelatedConvention;
    }

    public String getResponsableEntreprise() {
        return responsableEntreprise;
    }

    public void setResponsableEntreprise(String responsableEntreprise) {
        this.responsableEntreprise = responsableEntreprise;
    }

    public String getQualiteResponsable() {
        return qualiteResponsable;
    }

    public void setQualiteResponsable(String qualiteResponsable) {
        this.qualiteResponsable = qualiteResponsable;
    }

    public String getPathSignedAvenant() {
        return pathSignedAvenant;
    }

    public void setPathSignedAvenant(String pathSignedAvenant) {
        this.pathSignedAvenant = pathSignedAvenant;
    }

}

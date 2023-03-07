package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saria Essid
 *
 */

@Entity
@Table(name="ESP_AVENANT")
public class Avenant implements Serializable
{
	
	private static final long serialVersionUID = -5743201463396845897L;

	
	@EmbeddedId
	private AvenantPK avenantPK;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_DEB")
	private Date dateDebut;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_FIN")
	private Date dateFin;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_SIGNATURE_CONVENTION")
	private Date dateSignatureConvention;
	
	@Column(name = "TRAITER")
	private Boolean traiter;
	
	@Column(name = "RESPONSABLE_ENTREPRISE", length = 100)
	private String responsableEntreprise;
	
	@Column(name = "QUALITE_RESPONSABLE", length = 200)
	private String qualiteResponsable;
	
	@Column(name = "NUM_SIREN", length = 20)
	private String numSiren;
	
	@Column(name = "path_avenant")
	private String pathAvenant;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_DEPOT_SIGNED_AVEN")
	private Date dateDepotSignedAvenant;

	@Column(name = "path_signed_aven")
	private String pathSignedAvenant;
	
	
//	@ManyToOne
//	@JoinColumns({
//	     @JoinColumn(name="ID_ET", referencedColumnName="ID_ET"),
//	     @JoinColumn(name="DATE_CONVENTION", referencedColumnName="DATE_CONVENTION")
//	   })
//	private Convention convention;
	
//	 @ManyToOne(optional=true)
//		@PrimaryKeyJoinColumns({
//	        @PrimaryKeyJoinColumn(name="ID_ET", referencedColumnName="ID_ET"), 
//	        @PrimaryKeyJoinColumn(name="DATE_CONVENTION", referencedColumnName="DATE_CONVENTION")
//	    })
	 
	@ManyToOne
	@JoinColumns({ 
	    @JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET", insertable = false, updatable = false),
	    @JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
	})
	private Convention conventionAvenant;
	 
	
	public Avenant() {}
	
	
	public Avenant(AvenantPK avenantPK, Date dateSignatureConvention, Date dateDebut, Date dateFin, Boolean traiter, String responsableEntreprise,
			String qualiteResponsable, String numSiren) {
		super();
		this.avenantPK = avenantPK;
		this.dateSignatureConvention = dateSignatureConvention;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.traiter = traiter;
		this.responsableEntreprise = responsableEntreprise;
		this.qualiteResponsable = qualiteResponsable;
		this.numSiren = numSiren;
	}

	
	/**************************************************** Getters & Setters **************************************************/


	public AvenantPK getAvenantPK() {
		return avenantPK;
	}

	public void setAvenantPK(AvenantPK avenantPK) {
		this.avenantPK = avenantPK;
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

	public Date getDateSignatureConvention() {
		return dateSignatureConvention;
	}

	public void setDateSignatureConvention(Date dateSignatureConvention) {
		this.dateSignatureConvention = dateSignatureConvention;
	}

	public Boolean getTraiter() {
		return traiter;
	}

	public void setTraiter(Boolean traiter) {
		this.traiter = traiter;
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

	public String getNumSiren() {
		return numSiren;
	}

	public void setNumSiren(String numSiren) {
		this.numSiren = numSiren;
	}

	@JsonIgnore
	public Convention getConventionAvenant() {
		return conventionAvenant;
	}

	public void setConventionAvenant(Convention conventionAvenant) {
		this.conventionAvenant = conventionAvenant;
	}

	public String getPathAvenant() {
		return pathAvenant;
	}

	public void setPathAvenant(String pathAvenant) {
		this.pathAvenant = pathAvenant;
	}

	public Date getDateDepotSignedAvenant() {
		return dateDepotSignedAvenant;
	}

	public void setDateDepotSignedAvenant(Date dateDepotSignedAvenant) {
		this.dateDepotSignedAvenant = dateDepotSignedAvenant;
	}

	public String getPathSignedAvenant() {
		return pathSignedAvenant;
	}

	public void setPathSignedAvenant(String pathSignedAvenant) {
		this.pathSignedAvenant = pathSignedAvenant;
	}
	
}

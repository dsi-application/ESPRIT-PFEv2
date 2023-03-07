package com.esprit.gdp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ESP_RDV_SUIVI_STAGE")
public class URGRdvPFE  implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	RdvSuiviStagePK  rdvSuiviStagePK;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_RDV")
	private Date dateRendezVous;
	
	@Column(name = "TYPE_RDV", length = 1)
	private String typeRDV;
	
	@Column(name = "LIEU_RDV", length = 100)
	private String lieuRDV;
	
	@Column(name = "OBSERVATION", length = 2000)
	private String observation;
	
	
	@Column(name = "ETAT", length = 100)
	private String etat;
	
	@Column(name = "HEURE_DEBUT", length = 5)
	private String heureDebut;
	
	
	@Column(name = "URG_NOTE")
	private BigDecimal note;
	
	@Column(name = "URG_PLANNING_STG")
	private Boolean onPlanningStg;
	
	@Column(name = "URG_BILAN_PER_DEBUT_STG")
	private Boolean onBilanPerDebStg;
	
	@Column(name = "URG_BILAN_PER_MILIEU_STG")
	private Boolean onBilanPerMilStg;
	
	@Column(name = "URG_BILAN_PER_FIN_STG")
	private Boolean onBilanPerFinStg;
	
	@Column(name = "URG_FICHE_EVAL_MI_PARCOURS")
	private Boolean onFicheEvalMiPar;
	
	@Column(name = "URG_FICHE_EVAL_FIN")
	private Boolean onFicheEvalFin;
	
	@Column(name = "URG_JOURNAL_BORD")
	private Boolean onJournalBord;
	
	@Column(name = "URG_RAPPORT_STG")
	private Boolean onRapportStg;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "URG_DATE_RDV1")
	private Date dateRDV1;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "URG_DATE_RDV2")
	private Date dateRDV2;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "URG_DATE_RDV3")
	private Date dateRDV3;
	
	@Column(name = "URG_SATISFACTION_RDV1", length = 5)
	private String satisfactionRDV1;
	
	@Column(name = "URG_SATISFACTION_RDV2", length = 5)
	private String satisfactionRDV2;
	
	@Column(name = "URG_SATISFACTION_RDV3", length = 5)
	private String satisfactionRDV3;
	
	@Column(name = "URG_PRESENCE_RDV1")
	private Boolean presenceKindRDV1;
	
	@Column(name = "URG_PRESENCE_RDV2")
	private Boolean presenceKindRDV2;
	
	@Column(name = "URG_PRESENCE_RDV3")
	private Boolean presenceKindRDV3;
	
	

	public URGRdvPFE(BigDecimal note, Boolean onPlanningStg, Boolean onBilanPerDebStg, Boolean onBilanPerMilStg,
			Boolean onBilanPerFinStg, Boolean onFicheEvalMiPar, Boolean onFicheEvalFin, Boolean onJournalBord,
			Boolean onRapportStg, Date dateRDV1, Date dateRDV2, Date dateRDV3, String satisfactionRDV1,
			String satisfactionRDV2, String satisfactionRDV3, Boolean presenceKindRDV1, Boolean presenceKindRDV2,
			Boolean presenceKindRDV3) {
		this.note = note;
		this.onPlanningStg = onPlanningStg;
		this.onBilanPerDebStg = onBilanPerDebStg;
		this.onBilanPerMilStg = onBilanPerMilStg;
		this.onBilanPerFinStg = onBilanPerFinStg;
		this.onFicheEvalMiPar = onFicheEvalMiPar;
		this.onFicheEvalFin = onFicheEvalFin;
		this.onJournalBord = onJournalBord;
		this.onRapportStg = onRapportStg;
		this.dateRDV1 = dateRDV1;
		this.dateRDV2 = dateRDV2;
		this.dateRDV3 = dateRDV3;
		this.satisfactionRDV1 = satisfactionRDV1;
		this.satisfactionRDV2 = satisfactionRDV2;
		this.satisfactionRDV3 = satisfactionRDV3;
		this.presenceKindRDV1 = presenceKindRDV1;
		this.presenceKindRDV2 = presenceKindRDV2;
		this.presenceKindRDV3 = presenceKindRDV3;
	}


	public URGRdvPFE(RdvSuiviStagePK rdvSuiviStagePK, Date dateRendezVous, String typeRDV, String lieuRDV,
			String observation, String etat, String heureDebut) {
		super();
		this.rdvSuiviStagePK = rdvSuiviStagePK;
		this.dateRendezVous = dateRendezVous;
		this.typeRDV = typeRDV;
		this.lieuRDV = lieuRDV;
		this.observation = observation;
		this.etat = etat;
		this.heureDebut = heureDebut;
	}


	public URGRdvPFE() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureDebut() {
		return heureDebut;
	}
	
	@ManyToOne
    @JoinColumns({ 
    	@JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET", insertable = false, updatable = false),
    	@JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
        @JoinColumn(name = "DATE_DEPOT_FICHE", referencedColumnName = "DATE_DEPOT_FICHE", insertable = false, updatable = false)
    })
	private FichePFE fichePFErendezVous;


	public RdvSuiviStagePK getRdvSuiviStagePK() {
		return rdvSuiviStagePK;
	}


	public void setRdvSuiviStagePK(RdvSuiviStagePK rdvSuiviStagePK) {
		this.rdvSuiviStagePK = rdvSuiviStagePK;
	}


	public String getTypeRDV() {
		return typeRDV;
	}


	public void setTypeRDV(String typeRDV) {
		this.typeRDV = typeRDV;
	}


	public String getLieuRDV() {
		return lieuRDV;
	}


	public void setLieuRDV(String lieuRDV) {
		this.lieuRDV = lieuRDV;
	}


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}

	@JsonIgnore
	public FichePFE getFichePFErendezVous() {
		return fichePFErendezVous;
	}


	public void setFichePFErendezVous(FichePFE fichePFErendezVous) {
		this.fichePFErendezVous = fichePFErendezVous;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public URGRdvPFE(RdvSuiviStagePK rdvSuiviStagePK, String typeRDV, String lieuRDV, String observation,
			String etat, String heureDebut, FichePFE fichePFErendezVous) {
		super();
		this.rdvSuiviStagePK = rdvSuiviStagePK;
		this.typeRDV = typeRDV;
		this.lieuRDV = lieuRDV;
		this.observation = observation;
		this.etat = etat;
		this.heureDebut = heureDebut;
		this.fichePFErendezVous = fichePFErendezVous;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((fichePFErendezVous == null) ? 0 : fichePFErendezVous.hashCode());
		result = prime * result + ((heureDebut == null) ? 0 : heureDebut.hashCode());
		result = prime * result + ((lieuRDV == null) ? 0 : lieuRDV.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((rdvSuiviStagePK == null) ? 0 : rdvSuiviStagePK.hashCode());
		result = prime * result + ((typeRDV == null) ? 0 : typeRDV.hashCode());
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
		URGRdvPFE other = (URGRdvPFE) obj;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		if (fichePFErendezVous == null) {
			if (other.fichePFErendezVous != null)
				return false;
		} else if (!fichePFErendezVous.equals(other.fichePFErendezVous))
			return false;
		if (heureDebut == null) {
			if (other.heureDebut != null)
				return false;
		} else if (!heureDebut.equals(other.heureDebut))
			return false;
		if (lieuRDV == null) {
			if (other.lieuRDV != null)
				return false;
		} else if (!lieuRDV.equals(other.lieuRDV))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (rdvSuiviStagePK == null) {
			if (other.rdvSuiviStagePK != null)
				return false;
		} else if (!rdvSuiviStagePK.equals(other.rdvSuiviStagePK))
			return false;
		if (typeRDV == null) {
			if (other.typeRDV != null)
				return false;
		} else if (!typeRDV.equals(other.typeRDV))
			return false;
		return true;
	}


	public Date getDateRendezVous() {
		return dateRendezVous;
	}


	public void setDateRendezVous(Date dateRendezVous) {
		this.dateRendezVous = dateRendezVous;
	}


	public BigDecimal getNote() {
		return note;
	}


	public void setNote(BigDecimal note) {
		this.note = note;
	}


	public Boolean getOnPlanningStg() {
		return onPlanningStg;
	}


	public void setOnPlanningStg(Boolean onPlanningStg) {
		this.onPlanningStg = onPlanningStg;
	}


	public Boolean getOnBilanPerDebStg() {
		return onBilanPerDebStg;
	}


	public void setOnBilanPerDebStg(Boolean onBilanPerDebStg) {
		this.onBilanPerDebStg = onBilanPerDebStg;
	}


	public Boolean getOnBilanPerMilStg() {
		return onBilanPerMilStg;
	}


	public void setOnBilanPerMilStg(Boolean onBilanPerMilStg) {
		this.onBilanPerMilStg = onBilanPerMilStg;
	}


	public Boolean getOnBilanPerFinStg() {
		return onBilanPerFinStg;
	}


	public void setOnBilanPerFinStg(Boolean onBilanPerFinStg) {
		this.onBilanPerFinStg = onBilanPerFinStg;
	}


	public Boolean getOnFicheEvalMiPar() {
		return onFicheEvalMiPar;
	}


	public void setOnFicheEvalMiPar(Boolean onFicheEvalMiPar) {
		this.onFicheEvalMiPar = onFicheEvalMiPar;
	}


	public Boolean getOnFicheEvalFin() {
		return onFicheEvalFin;
	}


	public void setOnFicheEvalFin(Boolean onFicheEvalFin) {
		this.onFicheEvalFin = onFicheEvalFin;
	}


	public Boolean getOnJournalBord() {
		return onJournalBord;
	}


	public void setOnJournalBord(Boolean onJournalBord) {
		this.onJournalBord = onJournalBord;
	}


	public Boolean getOnRapportStg() {
		return onRapportStg;
	}


	public void setOnRapportStg(Boolean onRapportStg) {
		this.onRapportStg = onRapportStg;
	}


	public Date getDateRDV1() {
		return dateRDV1;
	}


	public void setDateRDV1(Date dateRDV1) {
		this.dateRDV1 = dateRDV1;
	}


	public Date getDateRDV2() {
		return dateRDV2;
	}


	public void setDateRDV2(Date dateRDV2) {
		this.dateRDV2 = dateRDV2;
	}


	public Date getDateRDV3() {
		return dateRDV3;
	}


	public void setDateRDV3(Date dateRDV3) {
		this.dateRDV3 = dateRDV3;
	}


	public String getSatisfactionRDV1() {
		return satisfactionRDV1;
	}


	public void setSatisfactionRDV1(String satisfactionRDV1) {
		this.satisfactionRDV1 = satisfactionRDV1;
	}


	public String getSatisfactionRDV2() {
		return satisfactionRDV2;
	}


	public void setSatisfactionRDV2(String satisfactionRDV2) {
		this.satisfactionRDV2 = satisfactionRDV2;
	}


	public String getSatisfactionRDV3() {
		return satisfactionRDV3;
	}


	public void setSatisfactionRDV3(String satisfactionRDV3) {
		this.satisfactionRDV3 = satisfactionRDV3;
	}


	public Boolean getPresenceKindRDV1() {
		return presenceKindRDV1;
	}


	public void setPresenceKindRDV1(Boolean presenceKindRDV1) {
		this.presenceKindRDV1 = presenceKindRDV1;
	}


	public Boolean getPresenceKindRDV2() {
		return presenceKindRDV2;
	}


	public void setPresenceKindRDV2(Boolean presenceKindRDV2) {
		this.presenceKindRDV2 = presenceKindRDV2;
	}


	public Boolean getPresenceKindRDV3() {
		return presenceKindRDV3;
	}


	public void setPresenceKindRDV3(Boolean presenceKindRDV3) {
		this.presenceKindRDV3 = presenceKindRDV3;
	}
	

}

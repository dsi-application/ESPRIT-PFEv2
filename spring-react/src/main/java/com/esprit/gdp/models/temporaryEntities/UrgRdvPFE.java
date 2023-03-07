package com.esprit.gdp.models.temporaryEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esprit.gdp.models.FichePFE;

@Entity
@Table(name="URG_RDV_SUIVI_STAGE")
public class UrgRdvPFE  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	UrgRdvPFEPK urgRdvPFEPK ;
	
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
	
	@Column(name = "URG_SATISFACTION_RDV1", length = 100)
	private String satisfactionRDV1;
	
	@Column(name = "URG_SATISFACTION_RDV2", length = 100)
	private String satisfactionRDV2;
	
	@Column(name = "URG_SATISFACTION_RDV3", length = 100)
	private String satisfactionRDV3;
	
	@Column(name = "URG_PRESENCE_RDV1", length = 3)
	private String presenceKindRDV1;
	
	@Column(name = "URG_PRESENCE_RDV2", length = 3)
	private String presenceKindRDV2;
	
	@Column(name = "URG_PRESENCE_RDV3", length = 3)
	private String presenceKindRDV3;
	
	@Column(name = "ETAT_TREAT_EA", length = 2)
	private String etatTreatEA;
	
	@Column(name = "ETAT_TREAT_CP", length = 2)
	private String etatTreatCP;
	
	
	
	@OneToOne
    @JoinColumns({ 
    	@JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET", insertable = false, updatable = false),
    	@JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION", insertable = false, updatable = false),
        @JoinColumn(name = "DATE_DEPOT_FICHE", referencedColumnName = "DATE_DEPOT_FICHE", insertable = false, updatable = false)
    })
	private FichePFE fichePFEUrgRdvPFE;
	
	
	public UrgRdvPFE(String etatTreatCP)
	{
		this.etatTreatCP = etatTreatCP;
	}
	

	public UrgRdvPFE(UrgRdvPFEPK urgRdvPFEPK, BigDecimal note, Boolean onPlanningStg, Boolean onBilanPerDebStg, Boolean onBilanPerMilStg,
			Boolean onBilanPerFinStg, Boolean onFicheEvalMiPar, Boolean onFicheEvalFin, Boolean onJournalBord,
			Boolean onRapportStg, Date dateRDV1, Date dateRDV2, Date dateRDV3, 
			String presenceKindRDV1, String presenceKindRDV2, String presenceKindRDV3,
			String satisfactionRDV1, String satisfactionRDV2, String satisfactionRDV3, String etatTreatEA)
	{
		this.urgRdvPFEPK = urgRdvPFEPK;
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
		this.presenceKindRDV1 = presenceKindRDV1;
		this.presenceKindRDV2 = presenceKindRDV2;
		this.presenceKindRDV3 = presenceKindRDV3;
		this.satisfactionRDV1 = satisfactionRDV1;
		this.satisfactionRDV2 = satisfactionRDV2;
		this.satisfactionRDV3 = satisfactionRDV3;
		this.etatTreatEA = etatTreatEA;
	}
	

	public UrgRdvPFE() {}

	
	/*************************************************** Getters & Setters ***************************************************/
	public UrgRdvPFEPK getUrgRdvPFEPK() {
		return urgRdvPFEPK;
	}

	public void setUrgRdvPFEPK(UrgRdvPFEPK urgRdvPFEPK) {
		this.urgRdvPFEPK = urgRdvPFEPK;
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

	public String getPresenceKindRDV1() {
		return presenceKindRDV1;
	}

	public void setPresenceKindRDV1(String presenceKindRDV1) {
		this.presenceKindRDV1 = presenceKindRDV1;
	}

	public String getPresenceKindRDV2() {
		return presenceKindRDV2;
	}

	public void setPresenceKindRDV2(String presenceKindRDV2) {
		this.presenceKindRDV2 = presenceKindRDV2;
	}

	public String getPresenceKindRDV3() {
		return presenceKindRDV3;
	}

	public void setPresenceKindRDV3(String presenceKindRDV3) {
		this.presenceKindRDV3 = presenceKindRDV3;
	}

	public FichePFE getFichePFEUrgRdvPFE() {
		return fichePFEUrgRdvPFE;
	}

	public void setFichePFEUrgRdvPFE(FichePFE fichePFEUrgRdvPFE) {
		this.fichePFEUrgRdvPFE = fichePFEUrgRdvPFE;
	}

	public String getEtatTreatEA() {
		return etatTreatEA;
	}

	public void setEtatTreatEA(String etatTreatEA) {
		this.etatTreatEA = etatTreatEA;
	}

	public String getEtatTreatCP() {
		return etatTreatCP;
	}

	public void setEtatTreatCP(String etatTreatCP) {
		this.etatTreatCP = etatTreatCP;
	}
	
}
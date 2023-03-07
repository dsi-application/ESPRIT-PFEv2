package com.esprit.gdp.models.temporaryEntities;

import java.math.BigDecimal;
import java.util.Date;

public class UrgRdvPFEDto
{
	//private Date dateSaisieRDV;
	private BigDecimal note;
	private Boolean onPlanningStg;
	private Boolean onBilanPerDebStg;
	private Boolean onBilanPerMilStg;
	private Boolean onBilanPerFinStg;
	private Boolean onFicheEvalMiPar;
	private Boolean onFicheEvalFin;
	private Boolean onJournalBord;
	private Boolean onRapportStg;
	private Date dateRDV1;
	private Date dateRDV2;
	private Date dateRDV3;
	private String statusKindRDV1;
	private String statusKindRDV2;
	private String statusKindRDV3;
	private String presenceKindRDV1;
	private String presenceKindRDV2;
	private String presenceKindRDV3;
	private String etatTreatEA;
	private String etatTreatCP;
	private String emptyDto;
	private String dateDepot;
	

	public UrgRdvPFEDto( String emptyDto )
	{
		this.emptyDto = emptyDto;
	}
	
	
	public UrgRdvPFEDto(
			//Date dateSaisieRDV, 
			BigDecimal note, Boolean onPlanningStg, Boolean onBilanPerDebStg, Boolean onBilanPerMilStg,
			Boolean onBilanPerFinStg, Boolean onFicheEvalMiPar, Boolean onFicheEvalFin, Boolean onJournalBord,
			Boolean onRapportStg, Date dateRDV1, Date dateRDV2, Date dateRDV3, 
			String presenceKindRDV1, String presenceKindRDV2, String presenceKindRDV3,
			String statusKindRDV1, String statusKindRDV2, String statusKindRDV3, String etatTreatEA, String dateDepot)
	{
		// this.dateSaisieRDV = dateSaisieRDV;
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
		this.statusKindRDV1 = statusKindRDV1;
		this.statusKindRDV2 = statusKindRDV2;
		this.statusKindRDV3 = statusKindRDV3;
		this.etatTreatEA = etatTreatEA;
		this.dateDepot = dateDepot;
	}
	

	public UrgRdvPFEDto() {}

	
	/*************************************************** Getters & Setters ***************************************************/
	
//	public Date getDateSaisieRDV() {
//		return dateSaisieRDV;
//	}
//
//	public void setDateSaisieRDV(Date dateSaisieRDV) {
//		this.dateSaisieRDV = dateSaisieRDV;
//	}

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

	public String getEmptyDto() {
		return emptyDto;
	}

	public void setEmptyDto(String emptyDto) {
		this.emptyDto = emptyDto;
	}

	public String getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(String dateDepot) {
		this.dateDepot = dateDepot;
	}

	public String getStatusKindRDV1() {
		return statusKindRDV1;
	}

	public void setStatusKindRDV1(String statusKindRDV1) {
		this.statusKindRDV1 = statusKindRDV1;
	}

	public String getStatusKindRDV2() {
		return statusKindRDV2;
	}

	public void setStatusKindRDV2(String statusKindRDV2) {
		this.statusKindRDV2 = statusKindRDV2;
	}

	public String getStatusKindRDV3() {
		return statusKindRDV3;
	}

	public void setStatusKindRDV3(String statusKindRDV3) {
		this.statusKindRDV3 = statusKindRDV3;
	}

}
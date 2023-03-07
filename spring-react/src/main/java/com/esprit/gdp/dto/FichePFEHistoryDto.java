package com.esprit.gdp.dto;

import java.util.List;

public class FichePFEHistoryDto
{
	
	private String titreProjet;
	private String descriptionProjet;
	private String dateDepotFiche;
	private String annee;
	private String etat;
	private EntrepriseAccueilForFichePFEHistDto companyDto;
	private List<EntrepriseSupervisorDto> companySupervisors;
	private List<ProblematicDto> problematics;
	private List<FunctionnalityDto> functionnalities;
	private List<TechnologyDto> technologies;
	private String pairFullName;
	private List<TraitementFicheHistoryDto> treatmentFiches;
	
	private String pathDiagrammeGantt;
	private String pathBilan1;
	private String dateDepotBilan1;
	private String pathBilan2;
	private String dateDepotBilan2;
	private String pathBilan3;
	private String dateDepotBilan3;
	private String pathJournalStg;
	private String dateDepotJournalStg;
	private String pathRapportVersion1;
	private String dateDepotRapportVersion1;
	private String pathRapportVersion2;
	private String dateDepotRapportVersion2;
	private String pathAttestationStage;
	private String dateDepotAttestationStage;
	private String pathSupplement;
	private String dateDepotSupplement;
	private String pathPlagiat;
	private String dateDepotPlagiat;
	private String dateDepotReports;
	private String dateTreatReports;

	
	public FichePFEHistoryDto() {}
	
	public FichePFEHistoryDto(String titreProjet, String descriptionProjet, String dateDepotFiche, String annee, String etat)
	{
		this.titreProjet = titreProjet;
		this.descriptionProjet = descriptionProjet;
		this.dateDepotFiche = dateDepotFiche;
		this.annee = annee;
		this.etat = etat;
	}
	
	public FichePFEHistoryDto(String titreProjet, String descriptionProjet, String dateDepotFiche, String annee, String etat, String pathDiagrammeGantt, 
			String pathBilan1, String dateDepotBilan1, String pathBilan2, String dateDepotBilan2, String pathBilan3, String dateDepotBilan3, 
			String pathJournalStg, String dateDepotJournalStg, 
			String pathRapportVersion1, String dateDepotRapportVersion1, String pathRapportVersion2, String dateDepotRapportVersion2, 
			String pathAttestationStage, String dateDepotAttestationStage, 
			String pathSupplement, String dateDepotSupplement, 
			String pathPlagiat, String dateDepotPlagiat, 
			String dateDepotReports, String dateTreatReports)
	{
		this.titreProjet = titreProjet;
		this.descriptionProjet = descriptionProjet;
		this.dateDepotFiche = dateDepotFiche;
		this.annee = annee;
		this.etat = etat;
		
		this.pathDiagrammeGantt = pathDiagrammeGantt;
		this.pathBilan1 = pathBilan1;
		this.dateDepotBilan1 = dateDepotBilan1;
		this.pathBilan2 = pathBilan2;
		this.dateDepotBilan2 = dateDepotBilan2;
		this.pathBilan3 = pathBilan3;
		this.dateDepotBilan3 = dateDepotBilan3;
		this.pathJournalStg = pathJournalStg;
		this.dateDepotJournalStg = dateDepotJournalStg;
		this.pathRapportVersion1 = pathRapportVersion1;
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
		this.pathRapportVersion2 = pathRapportVersion2;
		this.dateDepotRapportVersion2 = dateDepotRapportVersion2;
		this.pathAttestationStage = pathAttestationStage;
		this.dateDepotAttestationStage = dateDepotAttestationStage;
		this.pathSupplement = pathSupplement;
		this.dateDepotSupplement = dateDepotSupplement;
		this.pathPlagiat = pathPlagiat;
		this.dateDepotPlagiat = dateDepotPlagiat;
		this.dateDepotReports = dateDepotReports;
		this.dateTreatReports = dateTreatReports;
	}

	/****************************************************************************/
	
	public String getTitreProjet() {
		return titreProjet;
	}

	public String getDescriptionProjet() {
		return descriptionProjet;
	}

	public void setDescriptionProjet(String descriptionProjet) {
		this.descriptionProjet = descriptionProjet;
	}

	public String getDateDepotFiche() {
		return dateDepotFiche;
	}

	public void setDateDepotFiche(String dateDepotFiche) {
		this.dateDepotFiche = dateDepotFiche;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public EntrepriseAccueilForFichePFEHistDto getCompanyDto() {
		return companyDto;
	}

	public void setCompanyDto(EntrepriseAccueilForFichePFEHistDto companyDto) {
		this.companyDto = companyDto;
	}

	public List<EntrepriseSupervisorDto> getCompanySupervisors() {
		return companySupervisors;
	}

	public void setCompanySupervisors(List<EntrepriseSupervisorDto> companySupervisors) {
		this.companySupervisors = companySupervisors;
	}

	public List<ProblematicDto> getProblematics() {
		return problematics;
	}

	public void setProblematics(List<ProblematicDto> problematics) {
		this.problematics = problematics;
	}
	
	public List<FunctionnalityDto> getFunctionnalities() {
		return functionnalities;
	}

	public void setFunctionnalities(List<FunctionnalityDto> functionnalities) {
		this.functionnalities = functionnalities;
	}

	public List<TechnologyDto> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<TechnologyDto> technologies) {
		this.technologies = technologies;
	}

	public void setTitreProjet(String titreProjet) {
		this.titreProjet = titreProjet;
	}

	public String getPairFullName() {
		return pairFullName;
	}

	public void setPairFullName(String pairFullName) {
		this.pairFullName = pairFullName;
	}

	public List<TraitementFicheHistoryDto> getTreatmentFiches() {
		return treatmentFiches;
	}

	public void setTreatmentFiches(List<TraitementFicheHistoryDto> treatmentFiches) {
		this.treatmentFiches = treatmentFiches;
	}

	public String getPathBilan1() {
		return pathBilan1;
	}

	public void setPathBilan1(String pathBilan1) {
		this.pathBilan1 = pathBilan1;
	}

	public String getDateDepotBilan1() {
		return dateDepotBilan1;
	}

	public void setDateDepotBilan1(String dateDepotBilan1) {
		this.dateDepotBilan1 = dateDepotBilan1;
	}

	public String getPathBilan2() {
		return pathBilan2;
	}

	public void setPathBilan2(String pathBilan2) {
		this.pathBilan2 = pathBilan2;
	}

	public String getDateDepotBilan2() {
		return dateDepotBilan2;
	}

	public void setDateDepotBilan2(String dateDepotBilan2) {
		this.dateDepotBilan2 = dateDepotBilan2;
	}

	public String getPathBilan3() {
		return pathBilan3;
	}

	public void setPathBilan3(String pathBilan3) {
		this.pathBilan3 = pathBilan3;
	}

	public String getDateDepotBilan3() {
		return dateDepotBilan3;
	}

	public void setDateDepotBilan3(String dateDepotBilan3) {
		this.dateDepotBilan3 = dateDepotBilan3;
	}

	public String getPathJournalStg() {
		return pathJournalStg;
	}

	public void setPathJournalStg(String pathJournalStg) {
		this.pathJournalStg = pathJournalStg;
	}

	public String getDateDepotJournalStg() {
		return dateDepotJournalStg;
	}

	public void setDateDepotJournalStg(String dateDepotJournalStg) {
		this.dateDepotJournalStg = dateDepotJournalStg;
	}

	public String getPathRapportVersion1() {
		return pathRapportVersion1;
	}

	public void setPathRapportVersion1(String pathRapportVersion1) {
		this.pathRapportVersion1 = pathRapportVersion1;
	}

	public String getDateDepotRapportVersion1() {
		return dateDepotRapportVersion1;
	}

	public void setDateDepotRapportVersion1(String dateDepotRapportVersion1) {
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
	}

	public String getPathRapportVersion2() {
		return pathRapportVersion2;
	}

	public void setPathRapportVersion2(String pathRapportVersion2) {
		this.pathRapportVersion2 = pathRapportVersion2;
	}

	public String getDateDepotRapportVersion2() {
		return dateDepotRapportVersion2;
	}

	public void setDateDepotRapportVersion2(String dateDepotRapportVersion2) {
		this.dateDepotRapportVersion2 = dateDepotRapportVersion2;
	}

	public String getPathAttestationStage() {
		return pathAttestationStage;
	}

	public void setPathAttestationStage(String pathAttestationStage) {
		this.pathAttestationStage = pathAttestationStage;
	}

	public String getDateDepotAttestationStage() {
		return dateDepotAttestationStage;
	}

	public void setDateDepotAttestationStage(String dateDepotAttestationStage) {
		this.dateDepotAttestationStage = dateDepotAttestationStage;
	}

	public String getPathSupplement() {
		return pathSupplement;
	}

	public void setPathSupplement(String pathSupplement) {
		this.pathSupplement = pathSupplement;
	}

	public String getDateDepotSupplement() {
		return dateDepotSupplement;
	}

	public void setDateDepotSupplement(String dateDepotSupplement) {
		this.dateDepotSupplement = dateDepotSupplement;
	}

	public String getPathPlagiat() {
		return pathPlagiat;
	}

	public void setPathPlagiat(String pathPlagiat) {
		this.pathPlagiat = pathPlagiat;
	}

	public String getDateDepotPlagiat() {
		return dateDepotPlagiat;
	}

	public void setDateDepotPlagiat(String dateDepotPlagiat) {
		this.dateDepotPlagiat = dateDepotPlagiat;
	}

	public String getDateDepotReports() {
		return dateDepotReports;
	}

	public void setDateDepotReports(String dateDepotReports) {
		this.dateDepotReports = dateDepotReports;
	}

	public String getDateTreatReports() {
		return dateTreatReports;
	}

	public void setDateTreatReports(String dateTreatReports) {
		this.dateTreatReports = dateTreatReports;
	}

	public String getPathDiagrammeGantt() {
		return pathDiagrammeGantt;
	}

	public void setPathDiagrammeGantt(String pathDiagrammeGantt) {
		this.pathDiagrammeGantt = pathDiagrammeGantt;
	}

}

package com.esprit.gdp.dto;

import java.util.List;


public class FichePFEDto
{
	private String titreProjet;
	private String descriptionProjet;
	private String dateDepot;
	private String annee;
	private String etat;
	private StudentDto studentDto;
	private CompanyDto companyDto;
	private List<ResponsibleDto> companySupervisors;
	private List<ProblematicDto> problematics;
	private List<FunctionnalityDto> functionnalities;
	private List<TechnologyDto> technologies;
	private TraitementFicheDto annulationFicheDto;
	private ConventionDto conventionDto;
	private String dateConvention;
	private StudentIdFullNameDto pairDto;
	
	private TeacherDtoSTN2 presidentJuryEns;
	private String presidentJuryNotEns;
	private TeacherDtoSTN2 membre;
	private String salle;
	private String dateSoutenance;
	private String heureDebutSoutenance;
	private String heureFinSoutenance;
	
	private String ganttDiagramFullPath;
	
	
	public FichePFEDto() {}
	
	
//	public FichePFEDto(String titreProjet, 
//			String descriptionProjet, 
//			String dateDepot, 
//			String annee,
//			String etat, 
//			StudentDto studentDto, 
//			CompanyDto companyDto,
//			List<ResponsibleDto> companySupervisors,
//			List<ProblematicDto> problematics, List<FunctionnalityDto> functionnalities, List<TechnologyDto> technologies, ConventionDto conventionDto) {
//		this.titreProjet = titreProjet;
//		this.descriptionProjet = descriptionProjet;
//		this.dateDepot = dateDepot;
//		this.annee = annee;
//		this.etat = etat;
//		this.studentDto = studentDto;
//		this.companyDto = companyDto;
//		this.companySupervisors = companySupervisors;
//		this.problematics = problematics;
//		this.functionnalities = functionnalities;
//		this.technologies = technologies;
//		this.conventionDto = conventionDto;
//	}
	
	public FichePFEDto(String presidentJuryNotEns, TeacherDtoSTN2 presidentJuryEns, TeacherDtoSTN2 membre, String salle, String dateSoutenance, String heureDebutSoutenance, String heureFinSoutenance, String etat)
	{
		super();
		this.presidentJuryNotEns = presidentJuryNotEns;
		this.presidentJuryEns = presidentJuryEns;
		this.membre = membre;
		this.salle = salle;
		this.dateSoutenance = dateSoutenance;
		this.heureDebutSoutenance = heureDebutSoutenance;
		this.heureFinSoutenance = heureFinSoutenance;
		this.etat = etat;
	}
	
	public FichePFEDto(String titreProjet, String descriptionProjet, String dateDepot, String annee, String etat,
			StudentDto studentDto,
			List<ProblematicDto> problematics, List<FunctionnalityDto> functionnalities,
			List<TechnologyDto> technologies, StudentIdFullNameDto pairDto, String ganttDiagramFullPath) {
		super();
		this.titreProjet = titreProjet;
		this.descriptionProjet = descriptionProjet;
		this.dateDepot = dateDepot;
		this.annee = annee;
		this.etat = etat;
		this.studentDto = studentDto;
		this.problematics = problematics;
		this.functionnalities = functionnalities;
		this.technologies = technologies;
		this.pairDto = pairDto;
		this.ganttDiagramFullPath = ganttDiagramFullPath;
	}
	
//	public FichePFEDto(String titreProjet, String descriptionProjet, String dateDepot, String annee, String etat,
//			StudentDto studentDto,
//			List<ProblematicDto> problematics, List<FunctionnalityDto> functionnalities,
//			List<TechnologyDto> technologies, StudentIdFullNameDto pairDto, String dateConvention) {
//		super();
//		this.titreProjet = titreProjet;
//		this.descriptionProjet = descriptionProjet;
//		this.dateDepot = dateDepot;
//		this.annee = annee;
//		this.etat = etat;
//		this.studentDto = studentDto;
//		this.problematics = problematics;
//		this.functionnalities = functionnalities;
//		this.technologies = technologies;
//		this.pairDto = pairDto;
//		this.dateConvention = dateConvention;
//	}
	
	public FichePFEDto(String titreProjet, String descriptionProjet, String dateDepot, String annee, String etat,
			StudentDto studentDto, CompanyDto companyDto, List<ResponsibleDto> companySupervisors,
			List<ProblematicDto> problematics, List<FunctionnalityDto> functionnalities,
			List<TechnologyDto> technologies, StudentIdFullNameDto pairDto, String dateConvention) {
		super();
		this.titreProjet = titreProjet;
		this.descriptionProjet = descriptionProjet;
		this.dateDepot = dateDepot;
		this.annee = annee;
		this.etat = etat;
		this.studentDto = studentDto;
		this.companyDto = companyDto;
		this.companySupervisors = companySupervisors;
		this.problematics = problematics;
		this.functionnalities = functionnalities;
		this.technologies = technologies;
		this.pairDto = pairDto;
		this.dateConvention = dateConvention;
	}

	public FichePFEDto(String titreProjet, String descriptionProjet, String dateDepot, String annee, String etat,
			StudentDto studentDto, CompanyDto companyDto, List<ResponsibleDto> companySupervisors,
			List<ProblematicDto> problematics, List<FunctionnalityDto> functionnalities,
			List<TechnologyDto> technologies, TraitementFicheDto annulationFicheDto, StudentIdFullNameDto pairDto, String dateConvention) {
		super();
		this.titreProjet = titreProjet;
		this.descriptionProjet = descriptionProjet;
		this.dateDepot = dateDepot;
		this.annee = annee;
		this.etat = etat;
		this.studentDto = studentDto;
		this.companyDto = companyDto;
		this.companySupervisors = companySupervisors;
		this.problematics = problematics;
		this.functionnalities = functionnalities;
		this.technologies = technologies;
		this.annulationFicheDto = annulationFicheDto;
		this.pairDto = pairDto;
		this.dateConvention = dateConvention;
	}

	public FichePFEDto(String titreProjet, String descriptionProjet, String dateDepot, String annee, String etat,
			StudentDto studentDto, CompanyDto companyDto, List<ResponsibleDto> companySupervisors,
			List<ProblematicDto> problematics, List<FunctionnalityDto> functionnalities,
			List<TechnologyDto> technologies, TraitementFicheDto annulationFicheDto, ConventionDto conventionDto) {
		super();
		this.titreProjet = titreProjet;
		this.descriptionProjet = descriptionProjet;
		this.dateDepot = dateDepot;
		this.annee = annee;
		this.etat = etat;
		this.studentDto = studentDto;
		this.companyDto = companyDto;
		this.companySupervisors = companySupervisors;
		this.problematics = problematics;
		this.functionnalities = functionnalities;
		this.technologies = technologies;
		this.annulationFicheDto = annulationFicheDto;
		this.conventionDto = conventionDto;
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

	public String getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(String dateDepot) {
		this.dateDepot = dateDepot;
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

	public StudentDto getStudentDto() {
		return studentDto;
	}

	public void setStudentDto(StudentDto studentDto) {
		this.studentDto = studentDto;
	}

	public CompanyDto getCompanyDto() {
		return companyDto;
	}

	public void setCompanyDto(CompanyDto companyDto) {
		this.companyDto = companyDto;
	}

	public List<ResponsibleDto> getCompanySupervisors() {
		return companySupervisors;
	}

	public void setCompanySupervisors(List<ResponsibleDto> companySupervisors) {
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

	public TraitementFicheDto getAnnulationFicheDto() {
		return annulationFicheDto;
	}

	public void setAnnulationFicheDto(TraitementFicheDto annulationFicheDto) {
		this.annulationFicheDto = annulationFicheDto;
	}

	public ConventionDto getConventionDto() {
		return conventionDto;
	}

	public void setConventionDto(ConventionDto conventionDto) {
		this.conventionDto = conventionDto;
	}

	public StudentIdFullNameDto getPairDto() {
		return pairDto;
	}

	public void setPairDto(StudentIdFullNameDto pairDto) {
		this.pairDto = pairDto;
	}

	public String getDateConvention() {
		return dateConvention;
	}

	public void setDateConvention(String dateConvention) {
		this.dateConvention = dateConvention;
	}

	public TeacherDtoSTN2 getPresidentJuryEns() {
		return presidentJuryEns;
	}

	public void setPresidentJuryEns(TeacherDtoSTN2 presidentJuryEns) {
		this.presidentJuryEns = presidentJuryEns;
	}

	public String getPresidentJuryNotEns() {
		return presidentJuryNotEns;
	}

	public void setPresidentJuryNotEns(String presidentJuryNotEns) {
		this.presidentJuryNotEns = presidentJuryNotEns;
	}

	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public String getDateSoutenance() {
		return dateSoutenance;
	}

	public void setDateSoutenance(String dateSoutenance) {
		this.dateSoutenance = dateSoutenance;
	}

	public String getHeureDebutSoutenance() {
		return heureDebutSoutenance;
	}

	public void setHeureDebutSoutenance(String heureDebutSoutenance) {
		this.heureDebutSoutenance = heureDebutSoutenance;
	}

	public String getHeureFinSoutenance() {
		return heureFinSoutenance;
	}

	public void setHeureFinSoutenance(String heureFinSoutenance) {
		this.heureFinSoutenance = heureFinSoutenance;
	}

	public String getGanttDiagramFullPath() {
		return ganttDiagramFullPath;
	}

	public void setGanttDiagramFullPath(String ganttDiagramFullPath) {
		this.ganttDiagramFullPath = ganttDiagramFullPath;
	}

	public TeacherDtoSTN2 getMembre() {
		return membre;
	}

	public void setMembre(TeacherDtoSTN2 membre) {
		this.membre = membre;
	}
	
}

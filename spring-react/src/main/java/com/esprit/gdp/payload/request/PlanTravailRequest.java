package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class PlanTravailRequest {

	/*
	const planTravailRequest = {projectTitle: projectTitle, projectDescription: projectDescription,
			listOfProblematics: listOfProblematics, listOfFunctionnalities: listOfFunctionnalities,
			listSelectedLibelleTechnologies: listSelectedLibelleTechnologies, listOfSupervisors: listOfSupervisors,
			pairId: pairId, diagramGanttFullPath: diagramGanttFullPath}*/
	@NotBlank
	private String projectTitle;

	@NotBlank
	private String projectDescription;

	@NotBlank
	private List<String> listOfProblematics;

	@NotBlank
	private List<String> listOfFunctionnalities;

	@NotBlank
	private List<String> listSelectedLibelleTechnologies;

	@NotBlank
	private List<String> listOfSupervisors;

	@NotBlank
	private String diagramGanttFullPath;

	@NotBlank
	private String pairId;

	public PlanTravailRequest() {}

	/*********************** Getters & Setters ***********************/

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public List<String> getListOfProblematics() {
		return listOfProblematics;
	}

	public void setListOfProblematics(List<String> listOfProblematics) {
		this.listOfProblematics = listOfProblematics;
	}

	public List<String> getListOfFunctionnalities() {
		return listOfFunctionnalities;
	}

	public void setListOfFunctionnalities(List<String> listOfFunctionnalities) {
		this.listOfFunctionnalities = listOfFunctionnalities;
	}

	public List<String> getListSelectedLibelleTechnologies() {
		return listSelectedLibelleTechnologies;
	}

	public void setListSelectedLibelleTechnologies(List<String> listSelectedLibelleTechnologies) {
		this.listSelectedLibelleTechnologies = listSelectedLibelleTechnologies;
	}

	public List<String> getListOfSupervisors() {
		return listOfSupervisors;
	}

	public void setListOfSupervisors(List<String> listOfSupervisors) {
		this.listOfSupervisors = listOfSupervisors;
	}

	public String getDiagramGanttFullPath() {
		return diagramGanttFullPath;
	}

	public void setDiagramGanttFullPath(String diagramGanttFullPath) {
		this.diagramGanttFullPath = diagramGanttFullPath;
	}

	public String getPairId() {
		return pairId;
	}

	public void setPairId(String pairId) {
		this.pairId = pairId;
	}

}

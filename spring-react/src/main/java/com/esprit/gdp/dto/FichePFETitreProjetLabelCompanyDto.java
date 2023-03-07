package com.esprit.gdp.dto;


public class FichePFETitreProjetLabelCompanyDto
{
	
	private String projectTitle;
	private String companylabel;
	
	
	public FichePFETitreProjetLabelCompanyDto() {}

	public FichePFETitreProjetLabelCompanyDto(String projectTitle, String companylabel)
	{
		this.projectTitle = projectTitle;
		this.companylabel = companylabel;
	}

	
	/********************************** Getters & Setters ******************************************/
	
	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getCompanylabel() {
		return companylabel;
	}

	public void setCompanylabel(String companylabel) {
		this.companylabel = companylabel;
	}
	
}

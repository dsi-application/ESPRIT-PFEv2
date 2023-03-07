package com.esprit.gdp.dto;

public class StudentAdvancedPlanificationDto
{
	
	private String identifiant;
	private String fullName;
	private String department;
	private Integer expectedNbrSalles;
	
	
	public StudentAdvancedPlanificationDto() {}
	
	
	public StudentAdvancedPlanificationDto(String identifiant, String fullName, String department)
	{
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.department = department;
	}
	
	public StudentAdvancedPlanificationDto(String identifiant, String fullName, String department, Integer expectedNbrSalles)
	{
		this.identifiant = identifiant;
		this.fullName = fullName;
		this.department = department;
		this.expectedNbrSalles = expectedNbrSalles;
	}


	/************************************************ Getters & Setters *************************************************/

	
	public String getFullName() {
		return fullName;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getExpectedNbrSalles() {
		return expectedNbrSalles;
	}

	public void setExpectedNbrSalles(Integer expectedNbrSalles) {
		this.expectedNbrSalles = expectedNbrSalles;
	}

}

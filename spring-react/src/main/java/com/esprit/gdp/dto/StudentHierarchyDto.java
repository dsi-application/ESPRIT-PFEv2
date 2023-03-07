package com.esprit.gdp.dto;

public class StudentHierarchyDto
{
	private String coursesPole;
	private String department;
	private String option;
	private String classe;
	private String pedagogicalEncadrant;
	
	
	public StudentHierarchyDto() {}
	

	public StudentHierarchyDto(String coursesPole, String department, String option, String classe, String pedagogicalEncadrant) {
		super();
		this.coursesPole = coursesPole;
		this.department = department;
		this.option = option;
		this.classe = classe;
		this.pedagogicalEncadrant = pedagogicalEncadrant;
	}


	/************************************************ Getters & Setters *************************************************/


	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getCoursesPole() {
		return coursesPole;
	}

	public void setCoursesPole(String coursesPole) {
		this.coursesPole = coursesPole;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getPedagogicalEncadrant() {
		return pedagogicalEncadrant;
	}

	public void setPedagogicalEncadrant(String pedagogicalEncadrant) {
		this.pedagogicalEncadrant = pedagogicalEncadrant;
	}
	
}

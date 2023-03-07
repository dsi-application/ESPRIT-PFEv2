package com.esprit.gdp.dto;

public class StudentsByJuryActorForSTNDto
{
	private String studentId;
	private String studentFullName;
	private String studentPhone;
	private String studentMail;
	private String studentClasse;
	private String studentOption;
	
	public StudentsByJuryActorForSTNDto() {}
	
	public StudentsByJuryActorForSTNDto(String studentId, String studentFullName, String studentPhone,
			String studentMail, String studentClasse) {
		this.studentId = studentId;
		this.studentFullName = studentFullName;
		this.studentPhone = studentPhone;
		this.studentMail = studentMail;
		this.studentClasse = studentClasse;
	}

	
	/************************************************ Getters & Setters *************************************************/


	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentFullName() {
		return studentFullName;
	}

	public void setStudentFullName(String studentFullName) {
		this.studentFullName = studentFullName;
	}

	public String getStudentPhone() {
		return studentPhone;
	}

	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}

	public String getStudentMail() {
		return studentMail;
	}

	public void setStudentMail(String studentMail) {
		this.studentMail = studentMail;
	}

	public String getStudentClasse() {
		return studentClasse;
	}

	public void setStudentClasse(String studentClasse) {
		this.studentClasse = studentClasse;
	}

	public String getStudentOption() {
		return studentOption;
	}

	public void setStudentOption(String studentOption) {
		this.studentOption = studentOption;
	}
	
	

}

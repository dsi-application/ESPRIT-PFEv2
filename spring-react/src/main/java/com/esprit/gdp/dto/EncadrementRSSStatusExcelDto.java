package com.esprit.gdp.dto;

public class EncadrementRSSStatusExcelDto
{
	
	private String studentId;
	private String studentFullName;
	private String studentPhone;
	private String studentMail;
	private String studentClasse;
	private String studentOption;
	private String academicEncadId;
	private String academicEncadFullName;
	private String academicEncadMail;
	
	
	public EncadrementRSSStatusExcelDto() {}
	
	
	public EncadrementRSSStatusExcelDto(String studentId, String studentFullName, String studentPhone,
			String studentMail, String studentClasse, String studentOption, String academicEncadId, String academicEncadFullName, String academicEncadMail) {
		this.studentId = studentId;
		this.studentFullName = studentFullName;
		this.studentPhone = studentPhone;
		this.studentMail = studentMail;
		this.studentClasse = studentClasse;
		this.studentOption = studentOption;
		this.academicEncadId = academicEncadId;
		this.academicEncadFullName = academicEncadFullName;
		this.academicEncadMail = academicEncadMail;
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

	public String getAcademicEncadFullName() {
		return academicEncadFullName;
	}

	public void setAcademicEncadFullName(String academicEncadFullName) {
		this.academicEncadFullName = academicEncadFullName;
	}

	public String getAcademicEncadMail() {
		return academicEncadMail;
	}

	public void setAcademicEncadMail(String academicEncadMail) {
		this.academicEncadMail = academicEncadMail;
	}

	public String getAcademicEncadId() {
		return academicEncadId;
	}

	public void setAcademicEncadId(String academicEncadId) {
		this.academicEncadId = academicEncadId;
	}

	public String getStudentOption() {
		return studentOption;
	}

	public void setStudentOption(String studentOption) {
		this.studentOption = studentOption;
	}

}

package com.esprit.gdp.dto;

public class EncadrementStatusExcelDto
{
	private String studentId;
	private String studentFullName;
	private String studentPhone;
	private String studentMail;
	private String studentOption;
	private String studentClasse;
	private String academicEncadFullName;
	private String academicEncadMail;
	
	
	public EncadrementStatusExcelDto() {}
	
	
	public EncadrementStatusExcelDto(String studentId, String studentFullName, String studentPhone,
			String studentMail, String studentClasse, String academicEncadFullName, String academicEncadMail) {
		this.studentId = studentId;
		this.studentFullName = studentFullName;
		this.studentPhone = studentPhone;
		this.studentMail = studentMail;
		this.studentClasse = studentClasse;
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

	public String getStudentOption() {
		return studentOption;
	}

	public void setStudentOption(String studentOption) {
		this.studentOption = studentOption;
	}
	
}

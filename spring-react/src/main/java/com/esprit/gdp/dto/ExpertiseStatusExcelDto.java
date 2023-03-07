package com.esprit.gdp.dto;

public class ExpertiseStatusExcelDto
{
	private String studentId;
	private String studentFullName;
	private String studentPhone;
	private String studentMail;
	private String studentClasse;
	private String studentOption;
	private String Rest1MarkByExpert;
	private String expertFullName;
	private String expertMail;
	
	
	
	public ExpertiseStatusExcelDto() {}

	public ExpertiseStatusExcelDto(String studentId, String studentFullName, String studentPhone, String studentMail,
			String studentClasse, String expertFullName, String expertMail) {
		super();
		this.studentId = studentId;
		this.studentFullName = studentFullName;
		this.studentPhone = studentPhone;
		this.studentMail = studentMail;
		this.studentClasse = studentClasse;
		this.expertFullName = expertFullName;
		this.expertMail = expertMail;
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

	public String getRest1MarkByExpert() {
		return Rest1MarkByExpert;
	}

	public void setRest1MarkByExpert(String rest1MarkByExpert) {
		Rest1MarkByExpert = rest1MarkByExpert;
	}

	public String getExpertFullName() {
		return expertFullName;
	}

	public void setExpertFullName(String expertFullName) {
		this.expertFullName = expertFullName;
	}

	public String getExpertMail() {
		return expertMail;
	}

	public void setExpertMail(String expertMail) {
		this.expertMail = expertMail;
	}

	public String getStudentOption() {
		return studentOption;
	}

	public void setStudentOption(String studentOption) {
		this.studentOption = studentOption;
	}

	
}

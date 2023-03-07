package com.esprit.gdp.dto;

import java.math.BigDecimal;

public class StudentToBeExpertisedForTimelineDto
{
	private String studentId;
	private String studentFullName;
	private String studentPhone;
	private String studentMail;
	private String studentOption;
	private String studentClasse;
	private BigDecimal studentMarkRest;
	private String letChangeNoteRestitution;
	
	public StudentToBeExpertisedForTimelineDto() {}
	
	public StudentToBeExpertisedForTimelineDto(String studentId, String studentFullName, String studentPhone,
			String studentMail, String studentClasse, String letChangeNoteRestitution) {
		this.studentId = studentId;
		this.studentFullName = studentFullName;
		this.studentPhone = studentPhone;
		this.studentMail = studentMail;
		this.studentClasse = studentClasse;
		this.letChangeNoteRestitution = letChangeNoteRestitution;
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

	public BigDecimal getStudentMarkRest() {
		return studentMarkRest;
	}

	public void setStudentMarkRest(BigDecimal studentMarkRest) {
		this.studentMarkRest = studentMarkRest;
	}

	public String getLetChangeNoteRestitution() {
		return letChangeNoteRestitution;
	}

	public void setLetChangeNoteRestitution(String letChangeNoteRestitution) {
		this.letChangeNoteRestitution = letChangeNoteRestitution;
	}

	public String getStudentOption() {
		return studentOption;
	}

	public void setStudentOption(String studentOption) {
		this.studentOption = studentOption;
	}

}

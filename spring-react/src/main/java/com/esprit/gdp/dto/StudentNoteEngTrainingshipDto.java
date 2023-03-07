package com.esprit.gdp.dto;

import java.math.BigDecimal;

public class StudentNoteEngTrainingshipDto
{
	private String studentId;
	private String studentFullName;
	private String studentPhone;
	private String studentMail;
	private String studentClasse;
	private String studentOption;
	private BigDecimal studentMarkSTr;
	private String pathEvalStageINGFile;
	private String pathJournalStageINGFile;
	private String pathAttestationStageINGFile;
	private String pathRapportStageINGFile;
	private String etatDepotStageING;
	
	public StudentNoteEngTrainingshipDto() {}
	
	public StudentNoteEngTrainingshipDto(String studentId, String studentFullName, String studentPhone,
			String studentMail, String studentClasse, String etatDepotStageING) {
		this.studentId = studentId;
		this.studentFullName = studentFullName;
		this.studentPhone = studentPhone;
		this.studentMail = studentMail;
		this.studentClasse = studentClasse;
		this.etatDepotStageING = etatDepotStageING;
	}

	
//	public StudentNoteEngTrainingshipDto(String studentId, String studentFullName, String studentPhone,
//			String studentMail, String studentClasse, BigDecimal studentMarkSTr) {
//		this.studentId = studentId;
//		this.studentFullName = studentFullName;
//		this.studentPhone = studentPhone;
//		this.studentMail = studentMail;
//		this.studentClasse = studentClasse;
//		this.studentMarkSTr = studentMarkSTr;
//	}

	
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

	public BigDecimal getStudentMarkSTr() {
		return studentMarkSTr;
	}

	public void setStudentMarkSTr(BigDecimal studentMarkSTr) {
		this.studentMarkSTr = studentMarkSTr;
	}

	public String getPathEvalStageINGFile() {
		return pathEvalStageINGFile;
	}

	public void setPathEvalStageINGFile(String pathEvalStageINGFile) {
		this.pathEvalStageINGFile = pathEvalStageINGFile;
	}

	public String getPathJournalStageINGFile() {
		return pathJournalStageINGFile;
	}

	public void setPathJournalStageINGFile(String pathJournalStageINGFile) {
		this.pathJournalStageINGFile = pathJournalStageINGFile;
	}

	public String getPathAttestationStageINGFile() {
		return pathAttestationStageINGFile;
	}

	public void setPathAttestationStageINGFile(String pathAttestationStageINGFile) {
		this.pathAttestationStageINGFile = pathAttestationStageINGFile;
	}

	public String getPathRapportStageINGFile() {
		return pathRapportStageINGFile;
	}

	public void setPathRapportStageINGFile(String pathRapportStageINGFile) {
		this.pathRapportStageINGFile = pathRapportStageINGFile;
	}

	public String getEtatDepotStageING() {
		return etatDepotStageING;
	}

	public void setEtatDepotStageING(String etatDepotStageING) {
		this.etatDepotStageING = etatDepotStageING;
	}

	public String getStudentOption() {
		return studentOption;
	}

	public void setStudentOption(String studentOption) {
		this.studentOption = studentOption;
	}
	

}

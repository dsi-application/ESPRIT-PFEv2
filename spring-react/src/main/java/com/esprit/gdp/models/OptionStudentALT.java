package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ESP_OPTION_STUDENT_ALTNC")
public class OptionStudentALT implements Serializable
{
	
	private static final long serialVersionUID = 516725779505502640L;

	@EmbeddedId
	private OptionStudentALTPK idOptStuALT;
	
	@Column(name = "CODE_OPTION", length = 20)
	private String codeOption;
	
	@Column(name = "CODE_CLASSE", length = 20)
	private String codeClasse;

	public OptionStudentALT() {}

	public String getCodeOption() {
		return codeOption;
	}

	public void setCodeOption(String codeOption) {
		this.codeOption = codeOption;
	}

	public String getCodeClasse() {
		return codeClasse;
	}

	public void setCodeClasse(String codeClasse) {
		this.codeClasse = codeClasse;
	}
	
	
}

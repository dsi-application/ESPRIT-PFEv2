package com.esprit.gdp.dto;

public class StudentTimelineDto
{
	private Integer stepId;
	private String stepLabel;
	private String stepDate;
	private String stepPath;
	private String stepTitle;
	private String stepComment;
	
	public StudentTimelineDto() {}

	public StudentTimelineDto(String stepLabel, String stepDate, String stepPath) {
		this.stepLabel = stepLabel;
		this.stepDate = stepDate;
		this.stepPath = stepPath;
	}
	
	public StudentTimelineDto(Integer stepId, String stepLabel, String stepDate, String stepPath, String stepTitle, String stepComment) {
		this.stepId = stepId;
		this.stepLabel = stepLabel;
		this.stepDate = stepDate;
		this.stepPath = stepPath;
		this.stepTitle = stepTitle;
		this.stepComment = stepComment;
	}

	
	/***************************** Getters & Setters *****************************/
	
	public String getStepLabel() {
		return stepLabel;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public void setStepLabel(String stepLabel) {
		this.stepLabel = stepLabel;
	}

	public String getStepDate() {
		return stepDate;
	}

	public void setStepDate(String stepDate) {
		this.stepDate = stepDate;
	}

	public String getStepPath() {
		return stepPath;
	}

	public void setStepPath(String stepPath) {
		this.stepPath = stepPath;
	}

	public String getStepTitle() {
		return stepTitle;
	}

	public void setStepTitle(String stepTitle) {
		this.stepTitle = stepTitle;
	}

	public String getStepComment() {
		return stepComment;
	}

	public void setStepComment(String stepComment) {
		this.stepComment = stepComment;
	}
	
}

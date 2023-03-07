package com.esprit.gdp.dto;

public class DepotEvaluationStageINGDto
{
	
	private String pathEvaluationStageING;
	private String dateDepotEvaluationStageING;
	
	public DepotEvaluationStageINGDto() {}

	public DepotEvaluationStageINGDto(String pathEvaluationStageING, String dateDepotEvaluationStageING) {
		super();
		this.pathEvaluationStageING = pathEvaluationStageING;
		this.dateDepotEvaluationStageING = dateDepotEvaluationStageING;
	}

	/**************************************************************************************/
	
	public String getPathEvaluationStageING() {
		return pathEvaluationStageING;
	}

	public void setPathEvaluationStageING(String pathEvaluationStageING) {
		this.pathEvaluationStageING = pathEvaluationStageING;
	}

	public String getDateDepotEvaluationStageING() {
		return dateDepotEvaluationStageING;
	}

	public void setDateDepotEvaluationStageING(String dateDepotEvaluationStageING) {
		this.dateDepotEvaluationStageING = dateDepotEvaluationStageING;
	}

	
}

package com.esprit.gdp.dto;

public class DepotRapportINGDto
{
	
	private String pathRapportStageING;
	private String dateDepotRapportStageING;
	
	public DepotRapportINGDto() {}

	public DepotRapportINGDto(String pathRapportStageING, String dateDepotRapportStageING) {
		super();
		this.pathRapportStageING = pathRapportStageING;
		this.dateDepotRapportStageING = dateDepotRapportStageING;
	}

	/**************************************************************************************/
	
	public String getPathRapportStageING() {
		return pathRapportStageING;
	}

	public void setPathRapportStageING(String pathRapportStageING) {
		this.pathRapportStageING = pathRapportStageING;
	}

	public String getDateDepotRapportStageING() {
		return dateDepotRapportStageING;
	}

	public void setDateDepotRapportStageING(String dateDepotRapportStageING) {
		this.dateDepotRapportStageING = dateDepotRapportStageING;
	}

	
}

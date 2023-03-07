package com.esprit.gdp.dto;

public class DepotAttestationINGDto
{
	
	private String pathAttestationStageING;
	private String dateDepotAttestationStageING;
	
	public DepotAttestationINGDto() {}

	public DepotAttestationINGDto(String pathAttestationStageING, String dateDepotAttestationStageING) {
		super();
		this.pathAttestationStageING = pathAttestationStageING;
		this.dateDepotAttestationStageING = dateDepotAttestationStageING;
	}

	/**************************************************************************************/
	
	public String getPathAttestationStageING() {
		return pathAttestationStageING;
	}

	public void setPathAttestationStageING(String pathAttestationStageING) {
		this.pathAttestationStageING = pathAttestationStageING;
	}

	public String getDateDepotAttestationStageING() {
		return dateDepotAttestationStageING;
	}

	public void setDateDepotAttestationStageING(String dateDepotAttestationStageING) {
		this.dateDepotAttestationStageING = dateDepotAttestationStageING;
	}

	
}

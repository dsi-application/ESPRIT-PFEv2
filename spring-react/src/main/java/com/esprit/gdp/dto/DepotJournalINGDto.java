package com.esprit.gdp.dto;

public class DepotJournalINGDto
{
	
	private String pathJournalStageING;
	private String dateDepotJournalStageING;
	
	public DepotJournalINGDto() {}

	public DepotJournalINGDto(String pathJournalStageING, String dateDepotJournalStageING) {
		super();
		this.pathJournalStageING = pathJournalStageING;
		this.dateDepotJournalStageING = dateDepotJournalStageING;
	}

	/**************************************************************************************/
	
	public String getPathJournalStageING() {
		return pathJournalStageING;
	}

	public void setPathJournalStageING(String pathJournalStageING) {
		this.pathJournalStageING = pathJournalStageING;
	}

	public String getDateDepotJournalStageING() {
		return dateDepotJournalStageING;
	}

	public void setDateDepotJournalStageING(String dateDepotJournalStageING) {
		this.dateDepotJournalStageING = dateDepotJournalStageING;
	}

	
}

package com.esprit.gdp.dto;

public class DepotJournalDto
{
	private String pathJournal;
	private String dateDepotJournal;
	
	public DepotJournalDto() {}

	public DepotJournalDto(String pathJournal, String dateDepotJournal) {
		super();
		this.pathJournal = pathJournal;
		this.dateDepotJournal = dateDepotJournal;
	}

	public String getPathJournal() {
		return pathJournal;
	}

	public void setPathJournal(String pathJournal) {
		this.pathJournal = pathJournal;
	}

	public String getDateDepotJournal() {
		return dateDepotJournal;
	}

	public void setDateDepotJournal(String dateDepotJournal) {
		this.dateDepotJournal = dateDepotJournal;
	}

}

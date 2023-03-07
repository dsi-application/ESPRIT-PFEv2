package com.esprit.gdp.dto;

public class DepotSignedAvenDto
{
	private String pathSignedAven;
	private String dateDepotSignedAven;
	
	public DepotSignedAvenDto() {}

	public DepotSignedAvenDto(String pathSignedAven, String dateDepotSignedAven) {
		super();
		this.pathSignedAven = pathSignedAven;
		this.dateDepotSignedAven = dateDepotSignedAven;
	}

	
	public String getPathSignedAven() {
		return pathSignedAven;
	}

	public void setPathSignedAven(String pathSignedAven) {
		this.pathSignedAven = pathSignedAven;
	}

	public String getDateDepotSignedAven() {
		return dateDepotSignedAven;
	}

	public void setDateDepotSignedAven(String dateDepotSignedAven) {
		this.dateDepotSignedAven = dateDepotSignedAven;
	}

	
}

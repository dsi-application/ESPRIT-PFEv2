package com.esprit.gdp.dto;

public class DepotSignedConvDto
{
	private String pathSignedConv;
	private String dateDepotSignedConv;
	
	public DepotSignedConvDto() {}

	public DepotSignedConvDto(String pathSignedConv, String dateDepotSignedConv) {
		super();
		this.pathSignedConv = pathSignedConv;
		this.dateDepotSignedConv = dateDepotSignedConv;
	}

	
	public String getPathSignedConv() {
		return pathSignedConv;
	}

	public void setPathSignedConv(String pathSignedConv) {
		this.pathSignedConv = pathSignedConv;
	}

	public String getDateDepotSignedConv() {
		return dateDepotSignedConv;
	}

	public void setDateDepotSignedConv(String dateDepotSignedConv) {
		this.dateDepotSignedConv = dateDepotSignedConv;
	}

	
}

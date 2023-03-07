package com.esprit.gdp.dto;

import java.util.Date;

public class DepotBilanDto
{
	private String pathBilanVersion1;
	private String pathBilanVersion2;
	private String pathBilanVersion3;
	
	private String dateDepotBilanVersion1;
	private String dateDepotBilanVersion2;
	private String dateDepotBilanVersion3;
	
	public DepotBilanDto() {}

	public DepotBilanDto(String pathBilanVersion1, String pathBilanVersion2, String pathBilanVersion3,
			String dateDepotBilanVersion1, String dateDepotBilanVersion2, String dateDepotBilanVersion3) {
		super();
		this.pathBilanVersion1 = pathBilanVersion1;
		this.pathBilanVersion2 = pathBilanVersion2;
		this.pathBilanVersion3 = pathBilanVersion3;
		this.dateDepotBilanVersion1 = dateDepotBilanVersion1;
		this.dateDepotBilanVersion2 = dateDepotBilanVersion2;
		this.dateDepotBilanVersion3 = dateDepotBilanVersion3;
	}

	public String getPathBilanVersion1() {
		return pathBilanVersion1;
	}

	public void setPathBilanVersion1(String pathBilanVersion1) {
		this.pathBilanVersion1 = pathBilanVersion1;
	}

	public String getPathBilanVersion2() {
		return pathBilanVersion2;
	}

	public void setPathBilanVersion2(String pathBilanVersion2) {
		this.pathBilanVersion2 = pathBilanVersion2;
	}

	public String getPathBilanVersion3() {
		return pathBilanVersion3;
	}

	public void setPathBilanVersion3(String pathBilanVersion3) {
		this.pathBilanVersion3 = pathBilanVersion3;
	}

	public String getDateDepotBilanVersion1() {
		return dateDepotBilanVersion1;
	}

	public void setDateDepotBilanVersion1(String dateDepotBilanVersion1) {
		this.dateDepotBilanVersion1 = dateDepotBilanVersion1;
	}

	public String getDateDepotBilanVersion2() {
		return dateDepotBilanVersion2;
	}

	public void setDateDepotBilanVersion2(String dateDepotBilanVersion2) {
		this.dateDepotBilanVersion2 = dateDepotBilanVersion2;
	}

	public String getDateDepotBilanVersion3() {
		return dateDepotBilanVersion3;
	}

	public void setDateDepotBilanVersion3(String dateDepotBilanVersion3) {
		this.dateDepotBilanVersion3 = dateDepotBilanVersion3;
	}

}

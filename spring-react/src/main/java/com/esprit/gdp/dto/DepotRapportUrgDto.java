package com.esprit.gdp.dto;

import java.util.Date;

public class DepotRapportUrgDto
{
	private String pathRapportVersion1;
	private String dateDepotRapportVersion1;
	private String pathRapportVersion2;
	private String dateDepotRapportVersion2;
	private String pathPlagiat;
	private String dateDepotPlagiat;
	private String pathAttestationStage;
	private String dateDepotAttestationStage;
	private String pathSupplement;
	private String dateDepotSupplement;
	
	
	public DepotRapportUrgDto() {}


	public DepotRapportUrgDto(String pathRapportVersion1, String dateDepotRapportVersion1, 
			String pathRapportVersion2, String dateDepotRapportVersion2, String pathPlagiat,
			String dateDepotPlagiat, String pathAttestationStage, String dateDepotAttestationStage,
			String pathSupplement, String dateDepotSupplement) {
		super();
		this.pathRapportVersion1 = pathRapportVersion1;
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
		this.pathRapportVersion2 = pathRapportVersion2;
		this.dateDepotRapportVersion2 = dateDepotRapportVersion2;
		this.pathPlagiat = pathPlagiat;
		this.dateDepotPlagiat = dateDepotPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.dateDepotAttestationStage = dateDepotAttestationStage;
		this.pathSupplement = pathSupplement;
		this.dateDepotSupplement = dateDepotSupplement;
	}

	
	
	public String getPathRapportVersion1() {
		return pathRapportVersion1;
	}

	public void setPathRapportVersion1(String pathRapportVersion1) {
		this.pathRapportVersion1 = pathRapportVersion1;
	}

	public String getDateDepotRapportVersion1() {
		return dateDepotRapportVersion1;
	}

	public void setDateDepotRapportVersion1(String dateDepotRapportVersion1) {
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
	}

	public String getPathRapportVersion2() {
		return pathRapportVersion2;
	}

	public void setPathRapportVersion2(String pathRapportVersion2) {
		this.pathRapportVersion2 = pathRapportVersion2;
	}

	public String getDateDepotRapportVersion2() {
		return dateDepotRapportVersion2;
	}

	public void setDateDepotRapportVersion2(String dateDepotRapportVersion2) {
		this.dateDepotRapportVersion2 = dateDepotRapportVersion2;
	}

	public String getPathPlagiat() {
		return pathPlagiat;
	}

	public void setPathPlagiat(String pathPlagiat) {
		this.pathPlagiat = pathPlagiat;
	}

	public String getDateDepotPlagiat() {
		return dateDepotPlagiat;
	}

	public void setDateDepotPlagiat(String dateDepotPlagiat) {
		this.dateDepotPlagiat = dateDepotPlagiat;
	}

	public String getPathAttestationStage() {
		return pathAttestationStage;
	}

	public void setPathAttestationStage(String pathAttestationStage) {
		this.pathAttestationStage = pathAttestationStage;
	}

	public String getDateDepotAttestationStage() {
		return dateDepotAttestationStage;
	}

	public void setDateDepotAttestationStage(String dateDepotAttestationStage) {
		this.dateDepotAttestationStage = dateDepotAttestationStage;
	}

	public String getPathSupplement() {
		return pathSupplement;
	}

	public void setPathSupplement(String pathSupplement) {
		this.pathSupplement = pathSupplement;
	}

	public String getDateDepotSupplement() {
		return dateDepotSupplement;
	}

	public void setDateDepotSupplement(String dateDepotSupplement) {
		this.dateDepotSupplement = dateDepotSupplement;
	}


}

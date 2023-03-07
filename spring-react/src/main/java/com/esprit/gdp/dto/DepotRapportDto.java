package com.esprit.gdp.dto;

import java.util.Date;

public class DepotRapportDto
{
	private String pathRapportVersion1;
	private String pathRapportVersion2;
	private String dateDepotRapportVersion1;
	private String dateDepotRapportVersion2;
	private String pathPlagiat;
	private Date dateDepotPlagiat;
	private String pathAttestationStage;
	private String dateDepotAttestationStage;
	// private Boolean confidentiel;
	
	
	public DepotRapportDto() {}

	public DepotRapportDto(String pathRapportVersion1, String dateDepotRapportVersion1,
			String pathRapportVersion2, String dateDepotRapportVersion2, 
			String pathPlagiat, Date dateDepotPlagiat, 
			String pathAttestationStage, String dateDepotAttestationStage
			// , Boolean confidentiel
			) {
		super();
		this.pathRapportVersion1 = pathRapportVersion1;
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
		this.pathRapportVersion2 = pathRapportVersion2;
		this.dateDepotRapportVersion2 = dateDepotRapportVersion2;
		this.pathPlagiat = pathPlagiat;
		this.dateDepotPlagiat = dateDepotPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.dateDepotAttestationStage = dateDepotAttestationStage;
		// this.confidentiel = confidentiel;
	}
	
	public DepotRapportDto(String pathRapportVersion2, String dateDepotRapportVersion2, 
			String pathPlagiat, Date dateDepotPlagiat, 
			String pathAttestationStage, String dateDepotAttestationStage)
	{
		super();
		this.pathRapportVersion2 = pathRapportVersion2;
		this.dateDepotRapportVersion2 = dateDepotRapportVersion2;
		this.pathPlagiat = pathPlagiat;
		this.dateDepotPlagiat = dateDepotPlagiat;
		this.pathAttestationStage = pathAttestationStage;
		this.dateDepotAttestationStage = dateDepotAttestationStage;
	}

	public String getPathRapportVersion1() {
		return pathRapportVersion1;
	}

	public void setPathRapportVersion1(String pathRapportVersion1) {
		this.pathRapportVersion1 = pathRapportVersion1;
	}

	public String getPathRapportVersion2() {
		return pathRapportVersion2;
	}

	public void setPathRapportVersion2(String pathRapportVersion2) {
		this.pathRapportVersion2 = pathRapportVersion2;
	}

	public String getDateDepotRapportVersion1() {
		return dateDepotRapportVersion1;
	}

	public void setDateDepotRapportVersion1(String dateDepotRapportVersion1) {
		this.dateDepotRapportVersion1 = dateDepotRapportVersion1;
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

	public Date getDateDepotPlagiat() {
		return dateDepotPlagiat;
	}

	public void setDateDepotPlagiat(Date dateDepotPlagiat) {
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

	
//	public Boolean getConfidentiel() {
//		return confidentiel;
//	}
//
//	public void setConfidentiel(Boolean confidentiel) {
//		this.confidentiel = confidentiel;
//	}

}

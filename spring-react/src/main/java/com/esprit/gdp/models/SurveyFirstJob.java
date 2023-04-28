package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "ESP_SURVEY_FIRST_JOB")
public class SurveyFirstJob implements Serializable
{

	private static final long serialVersionUID = 2222614231462319290L;

	@TableGenerator(name = "Idt_SurveyFJ", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_SURVEYFJ", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_SurveyFJ")
	@Column(name = "ID_SURVEY", length = 10)
	private Integer idSurvey;

	private String idEt;

	private Boolean searchJob;

	@Temporal(TemporalType.DATE)
	private Date promesse1;
	private String promesse2;
	private String promesse3;

	@Temporal(TemporalType.DATE)
	private Date contrat1;
	private String contrat2;
	private String contrat3;

	@Temporal(TemporalType.DATE)
	private Date projet1;
	private String projet2;

	@Temporal(TemporalType.DATE)
	private Date etude1;
	private String etude2;
	private String etude3;


	private String anneeDeb;

	@Temporal(TemporalType.DATE)
	private Date dateDepotSurvey;


	public SurveyFirstJob() {}

	public SurveyFirstJob(String idEt, Boolean searchJob, String anneeDeb, Date dateDepotSurvey) {
		this.idEt = idEt;
		this.searchJob = searchJob;
		this.anneeDeb = anneeDeb;
		this.dateDepotSurvey = dateDepotSurvey;
	}

	public SurveyFirstJob(String idEt, Boolean searchJob, Date promesse1, String promesse2, String promesse3, String anneeDeb, Date dateDepotSurvey) {
		this.idEt = idEt;
		this.searchJob = searchJob;
		this.promesse1 = promesse1;
		this.promesse2 = promesse2;
		this.promesse3 = promesse3;
		this.anneeDeb = anneeDeb;
		this.dateDepotSurvey = dateDepotSurvey;
	}

	/***************************************************** Getters & Setters ************************************************/

	public Integer getIdSurvey() {
		return idSurvey;
	}

	public void setIdSurvey(Integer idSurvey) {
		this.idSurvey = idSurvey;
	}

	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public Boolean getSearchJob() {
		return searchJob;
	}

	public void setSearchJob(Boolean searchJob) {
		this.searchJob = searchJob;
	}

	public Date getPromesse1() {
		return promesse1;
	}

	public void setPromesse1(Date promesse1) {
		this.promesse1 = promesse1;
	}

	public String getPromesse2() {
		return promesse2;
	}

	public void setPromesse2(String promesse2) {
		this.promesse2 = promesse2;
	}

	public String getPromesse3() {
		return promesse3;
	}

	public void setPromesse3(String promesse3) {
		this.promesse3 = promesse3;
	}

	public Date getContrat1() {
		return contrat1;
	}

	public void setContrat1(Date contrat1) {
		this.contrat1 = contrat1;
	}

	public String getContrat2() {
		return contrat2;
	}

	public void setContrat2(String contrat2) {
		this.contrat2 = contrat2;
	}

	public String getContrat3() {
		return contrat3;
	}

	public void setContrat3(String contrat3) {
		this.contrat3 = contrat3;
	}

	public Date getProjet1() {
		return projet1;
	}

	public void setProjet1(Date projet1) {
		this.projet1 = projet1;
	}

	public String getProjet2() {
		return projet2;
	}

	public void setProjet2(String projet2) {
		this.projet2 = projet2;
	}

	public Date getEtude1() {
		return etude1;
	}

	public void setEtude1(Date etude1) {
		this.etude1 = etude1;
	}

	public String getEtude2() {
		return etude2;
	}

	public void setEtude2(String etude2) {
		this.etude2 = etude2;
	}

	public String getEtude3() {
		return etude3;
	}

	public void setEtude3(String etude3) {
		this.etude3 = etude3;
	}

	public String getAnneeDeb() {
		return anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	public Date getDateDepotSurvey() {
		return dateDepotSurvey;
	}

	public void setDateDepotSurvey(Date dateDepotSurvey) {
		this.dateDepotSurvey = dateDepotSurvey;
	}

}

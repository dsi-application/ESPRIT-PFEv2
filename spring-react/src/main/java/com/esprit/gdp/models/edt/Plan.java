package com.esprit.gdp.models.edt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ESP_MODULE_PANIER_CLASSE_SAISO database table.
 * 
 */

@Entity
// @Table(name = "ABS_SYNO_ESP_SAISON")
@Table(name = "SYN_ESP_PLAN")
public class Plan implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PlanPK id;

	private String acomptabiliser;

	// @SequenceGenerator(name = "seqplanc", sequenceName =
	// "ESP_MODULE_PANIER_CLASSE_SAIS", allocationSize = 1)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqplanc")
	// @Column(name="CODE_PLAN")
	//

	@TableGenerator(name = "Choice_Gen", table = "ID_CHOICE", pkColumnName = "CHOICE_NAME", valueColumnName = "CHOICE_VAL", pkColumnValue = "TaCho_Gen", initialValue = 10000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Choice_Gen")
	@Column(name = "CODE_PLAN")
	private long codePlan;
	
	private long concat;

	@Column(name = "ANNEE_FIN")
	private String anneeFin;

	@Column(name = "AP_CS")
	private BigDecimal apCs;

	@Column(name = "CHARGE_ENS1_P1")
	private BigDecimal chargeEns1P1;

	@Column(name = "CHARGE_ENS1_P2")
	private BigDecimal chargeEns1P2;

	@Column(name = "CHARGE_ENS2_P1")
	private BigDecimal chargeEns2P1;

	@Column(name = "CHARGE_ENS2_P2")
	private BigDecimal chargeEns2P2;

	@Column(name = "CHARGE_ENS3_P1")
	private BigDecimal chargeEns3P1;

	@Column(name = "CHARGE_ENS3_P2")
	private BigDecimal chargeEns3P2;

	@Column(name = "CHARGE_ENS4_P1")
	private BigDecimal chargeEns4P1;

	@Column(name = "CHARGE_ENS4_P2")
	private BigDecimal chargeEns4P2;

	@Column(name = "CHARGE_ENS5_P1")
	private BigDecimal chargeEns5P1;

	@Column(name = "CHARGE_ENS5_P2")
	private BigDecimal chargeEns5P2;

	@Column(name = "CHARGE_P1")
	private BigDecimal chargeP1;

	@Column(name = "CHARGE_P2")
	private BigDecimal chargeP2;

	@Column(name = "CODE_UE")
	private String codeUe;

	private BigDecimal coef;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_DEBUT")
	private Date dateDebut;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_EXAMEN")
	private Date dateExamen;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_FIN")
	private Date dateFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_RATTRAPAGE")
	private Date dateRattrapage;

	private String description;

	@Column(name = "HEURE_EXAM")
	private String heureExam;

	@Column(name = "ID_ENS")
	private String idEns;

	@Column(name = "ID_ENS2")
	private String idEns2;

	@Column(name = "ID_ENS3")
	private String idEns3;

	@Column(name = "ID_ENS4")
	private String idEns4;

	@Column(name = "ID_ENS5")
	private String idEns5;

	@Column(name = "NB_ECTS")
	private BigDecimal nbEcts;

	@Column(name = "NB_HEURES")
	private BigDecimal nbHeures;

	@Column(name = "NB_HEURES_ENS")
	private BigDecimal nbHeuresEns;

	@Column(name = "NB_HEURES_ENS2")
	private BigDecimal nbHeuresEns2;

	@Column(name = "NB_HORAIRE_REALISES")
	private BigDecimal nbHoraireRealises;

	@Column(name = "NUM_PANIER")
	private String numPanier;

	@Column(name = "NUM_PERIODFE")
	private BigDecimal numPeriodfe;

	private BigDecimal numpromotioncs;

	private BigDecimal periode;

	private BigDecimal nbheuradd;

	private BigDecimal chargep1add;
	private BigDecimal chargep2add;

	@Column(name = "SALLE_EXAM")
	private String salleExam;

	@Column(name = "SALLE_EXAM2")
	private String salleExam2;

	@Column(name = "SEANCE_UNIQUE")
	private String seanceUnique;

	private String surveillant;

	private String surveillant2;

	@Column(name = "TYPE_EPREUVE")
	private String typeEpreuve;

	// bi-directional many-to-one association to EspModule
	@ManyToOne
	@JoinColumn(name = "CODE_MODULE", insertable = false, updatable = false)
	private Module module;

	@Column(name = "SALLE")
	private String salle;

	public Plan() {
	}

	public PlanPK getId() {
		return this.id;
	}

	public void setId(PlanPK id) {
		this.id = id;
	}

	public String getAcomptabiliser() {
		return this.acomptabiliser;
	}

	public void setAcomptabiliser(String acomptabiliser) {
		this.acomptabiliser = acomptabiliser;
	}

	public String getAnneeFin() {
		return this.anneeFin;
	}

	public void setAnneeFin(String anneeFin) {
		this.anneeFin = anneeFin;
	}

	public BigDecimal getApCs() {
		return this.apCs;
	}

	public void setApCs(BigDecimal apCs) {
		this.apCs = apCs;
	}

	public BigDecimal getChargeEns1P1() {
		return this.chargeEns1P1;
	}

	public void setChargeEns1P1(BigDecimal chargeEns1P1) {
		this.chargeEns1P1 = chargeEns1P1;
	}

	public BigDecimal getChargeEns1P2() {
		return this.chargeEns1P2;
	}

	public void setChargeEns1P2(BigDecimal chargeEns1P2) {
		this.chargeEns1P2 = chargeEns1P2;
	}

	public BigDecimal getChargeEns2P1() {
		return this.chargeEns2P1;
	}

	public void setChargeEns2P1(BigDecimal chargeEns2P1) {
		this.chargeEns2P1 = chargeEns2P1;
	}

	public BigDecimal getChargeEns2P2() {
		return this.chargeEns2P2;
	}

	public void setChargeEns2P2(BigDecimal chargeEns2P2) {
		this.chargeEns2P2 = chargeEns2P2;
	}

	public BigDecimal getChargeEns3P1() {
		return this.chargeEns3P1;
	}

	public void setChargeEns3P1(BigDecimal chargeEns3P1) {
		this.chargeEns3P1 = chargeEns3P1;
	}

	public BigDecimal getChargeEns3P2() {
		return this.chargeEns3P2;
	}

	public void setChargeEns3P2(BigDecimal chargeEns3P2) {
		this.chargeEns3P2 = chargeEns3P2;
	}

	public BigDecimal getChargeEns4P1() {
		return this.chargeEns4P1;
	}

	public void setChargeEns4P1(BigDecimal chargeEns4P1) {
		this.chargeEns4P1 = chargeEns4P1;
	}

	public BigDecimal getChargeEns4P2() {
		return this.chargeEns4P2;
	}

	public void setChargeEns4P2(BigDecimal chargeEns4P2) {
		this.chargeEns4P2 = chargeEns4P2;
	}

	public BigDecimal getChargeEns5P1() {
		return this.chargeEns5P1;
	}

	public void setChargeEns5P1(BigDecimal chargeEns5P1) {
		this.chargeEns5P1 = chargeEns5P1;
	}

	public BigDecimal getChargeEns5P2() {
		return this.chargeEns5P2;
	}

	public void setChargeEns5P2(BigDecimal chargeEns5P2) {
		this.chargeEns5P2 = chargeEns5P2;
	}

	public BigDecimal getChargeP1() {
		return this.chargeP1;
	}

	public void setChargeP1(BigDecimal chargeP1) {
		this.chargeP1 = chargeP1;
	}

	public BigDecimal getChargeP2() {
		return this.chargeP2;
	}

	public void setChargeP2(BigDecimal chargeP2) {
		this.chargeP2 = chargeP2;
	}

	public String getCodeUe() {
		return this.codeUe;
	}

	public void setCodeUe(String codeUe) {
		this.codeUe = codeUe;
	}

	public BigDecimal getCoef() {
		return this.coef;
	}

	public void setCoef(BigDecimal coef) {
		this.coef = coef;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateExamen() {
		return this.dateExamen;
	}

	public void setDateExamen(Date dateExamen) {
		this.dateExamen = dateExamen;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Date getDateRattrapage() {
		return this.dateRattrapage;
	}

	public void setDateRattrapage(Date dateRattrapage) {
		this.dateRattrapage = dateRattrapage;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeureExam() {
		return this.heureExam;
	}

	public void setHeureExam(String heureExam) {
		this.heureExam = heureExam;
	}

	public String getIdEns() {
		return this.idEns;
	}

	public void setIdEns(String idEns) {
		this.idEns = idEns;
	}

	public String getIdEns2() {
		return this.idEns2;
	}

	public void setIdEns2(String idEns2) {
		this.idEns2 = idEns2;
	}

	public String getIdEns3() {
		return this.idEns3;
	}

	public void setIdEns3(String idEns3) {
		this.idEns3 = idEns3;
	}

	public String getIdEns4() {
		return this.idEns4;
	}

	public void setIdEns4(String idEns4) {
		this.idEns4 = idEns4;
	}

	public String getIdEns5() {
		return this.idEns5;
	}

	public void setIdEns5(String idEns5) {
		this.idEns5 = idEns5;
	}

	public BigDecimal getNbEcts() {
		return this.nbEcts;
	}

	public void setNbEcts(BigDecimal nbEcts) {
		this.nbEcts = nbEcts;
	}

	public BigDecimal getNbHeures() {
		return this.nbHeures;
	}

	public void setNbHeures(BigDecimal nbHeures) {
		this.nbHeures = nbHeures;
	}

	public BigDecimal getNbHeuresEns() {
		return this.nbHeuresEns;
	}

	public void setNbHeuresEns(BigDecimal nbHeuresEns) {
		this.nbHeuresEns = nbHeuresEns;
	}

	public BigDecimal getNbHeuresEns2() {
		return this.nbHeuresEns2;
	}

	public void setNbHeuresEns2(BigDecimal nbHeuresEns2) {
		this.nbHeuresEns2 = nbHeuresEns2;
	}

	public BigDecimal getNbHoraireRealises() {
		return this.nbHoraireRealises;
	}

	public void setNbHoraireRealises(BigDecimal nbHoraireRealises) {
		this.nbHoraireRealises = nbHoraireRealises;
	}

	public String getNumPanier() {
		return this.numPanier;
	}

	public void setNumPanier(String numPanier) {
		this.numPanier = numPanier;
	}

	public BigDecimal getNumPeriodfe() {
		return this.numPeriodfe;
	}

	public void setNumPeriodfe(BigDecimal numPeriodfe) {
		this.numPeriodfe = numPeriodfe;
	}

	public BigDecimal getNumpromotioncs() {
		return this.numpromotioncs;
	}

	public void setNumpromotioncs(BigDecimal numpromotioncs) {
		this.numpromotioncs = numpromotioncs;
	}

	public BigDecimal getPeriode() {
		return this.periode;
	}

	public void setPeriode(BigDecimal periode) {
		this.periode = periode;
	}

	public String getSalleExam() {
		return this.salleExam;
	}

	public void setSalleExam(String salleExam) {
		this.salleExam = salleExam;
	}

	public String getSalleExam2() {
		return this.salleExam2;
	}

	public void setSalleExam2(String salleExam2) {
		this.salleExam2 = salleExam2;
	}

	public String getSeanceUnique() {
		return this.seanceUnique;
	}

	public void setSeanceUnique(String seanceUnique) {
		this.seanceUnique = seanceUnique;
	}

	public String getSurveillant() {
		return this.surveillant;
	}

	public void setSurveillant(String surveillant) {
		this.surveillant = surveillant;
	}

	public String getSurveillant2() {
		return this.surveillant2;
	}

	public void setSurveillant2(String surveillant2) {
		this.surveillant2 = surveillant2;
	}

	public String getTypeEpreuve() {
		return this.typeEpreuve;
	}

	public void setTypeEpreuve(String typeEpreuve) {
		this.typeEpreuve = typeEpreuve;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public long getCodePlan() {
		return codePlan;
	}

	public void setCodePlan(long codePlan) {
		this.codePlan = codePlan;
	}

	public long getActif() {
		return actif;
	}

	public void setActif(long actif) {
		this.actif = actif;
	}

	public BigDecimal getNbheuradd() {
		return nbheuradd;
	}

	public void setNbheuradd(BigDecimal nbheuradd) {
		this.nbheuradd = nbheuradd;
	}

	public BigDecimal getChargep1add() {
		return chargep1add;
	}

	public void setChargep1add(BigDecimal chargep1add) {
		this.chargep1add = chargep1add;
	}

	public BigDecimal getChargep2add() {
		return chargep2add;
	}

	public void setChargep2add(BigDecimal chargep2add) {
		this.chargep2add = chargep2add;
	}

	private long actif;

	public long getConcat() {
		return concat;
	}

	public void setConcat(long concat) {
		this.concat = concat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
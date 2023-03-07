/**
 * 
 */
package com.esprit.gdp.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saria Essid
 *
 */


@Entity
@Table(name = "SYN_ESP_ETUDIANT")
public class StudentCJ implements Serializable
{

	private static final long serialVersionUID = -6585510892343029935L;
	

	@Id
	@Column(name = "ID_ET", length = 10)
	private String idEt;
	
	@Column(name="NOM_ET")
	private String nomet;
	
	@Column(name="PNOM_ET")
	private String prenomet;
	
	@Column(name = "PWD_JWT_ETUDIANT", length = 255)
	private String pwdJWTEtudiant;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_MODIFY_JWT_PWD")
	private Date dateModifyJwtPwd;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_NAIS_ET")
	private Date datenaisset;
	
	@Column(name="LIEU_NAIS_ET")
	private String lieunaiset;
	
	@Column(name="NATURE_ET")
	private String natureet;
	
	@Column(name="FONCTION_ET")
	private String fonctionet;
	
	@Column(name="ADRESSE_ET")
	private String adresseet;
	
	@Column(name="TEL_ET")
	private String telet;
	
	@Column(name="TEL_PARENT_ET")
	private String telparentet;
	
	@Column(name="E_MAIL_ET")
	private String emailet;
	
	@Column(name="CYCLE_ET")
	private String cycleet;
	
	@Column(name="NATURE_BAC")
	private String naturebac;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_BAC")
	private Date datebac;
	
	@Column(name="NUM_BAC_ET")
	private String numbacet;
	
	@Column(name="ETAB_BAC")
	private String etabbac;
	
	@Column(name="DIPLOME_SUP_ET")
	private String diplomesupet;
	
	@Column(name="NIVEAU_DIPLOME_SUP_ET")
	private String niveauDiplomeSupEt;
	
	@Column(name="ETAB_ORIGINE")
	private String etaborigine;
	
	@Column(name="SPECIALITE_ESP_ET")
	private String specialiteespet;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_ENTREE_ESP_ET")
	private Date dateentreeespet;
	
	@Column(name="ANNEE_ENTREE_ESP_ET")
	private String enneeentreeespet;
	
	@Column(name="CLASSE_COURANTE_ET")
	private String classecourantet;
	
	@Column(name="SITUATION_FINANCIERE_ET")
	private String situationfinanciereet;
	
	@Column(name="NIVEAU_COURANT_ET")
	private String niveaucourantet;
	
	@Column(name="MOYENNE_DERN_SEMESTRE_ET")
	private String moyennedernsemestreet;
	
	@Column(name="RESULTAT_FINAL_ET")
	private String resultatfinaleet;
	
	@Column(name="DIPLOME_OBTENU_ESP_ET")
	private String diplomeobtenuespet;
	
	@Column(name="OBSERVATION_ET")
	private String observationet;
	
	@Column(name="SEXE")
	private String sexe;
	
	@Column(name="NATIONALITE")
	private String nationalite;
	
	@Column(name="NUM_CIN_PASSEPORT")
	private String numcinpasseport;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_SAISIE")
	private Date datesaisie;
	
	@Column(name="AGENT")
	private String agent;
	
	@Column(name="NUM_ORD")
	private String numord;
	
	@Column(name="LIEU_DELIVRANCE")
	private String lieudelivrance;
	
	@Column(name="NIVEAU_ACCES")
	private String niveauacces;
	
	@Column(name="NATURE_COURS")
	private String naturecours;
	
	@Column(name="NATURE_PIECE_ID")
	private String naturepieceid;
	
	@Column(name="ADRESSE_PARENT")
	private String adresseparent;
	
	@Column(name="CP_PARENT")
	private String cpparent;
	
	@Column(name="VILLE_PARENT")
	private String villeparent;
	
	@Column(name="PAYS_PARENT")
	private String paysparent;
	
	@Column(name="CP_ET")
	private String cpet;
	
	@Column(name="VILLE_ET")
	private String villeet;
	
	@Column(name="PAYS_ET")
	private String payset;
	
	@Column(name="E_MAIL_PARENT")
	private String emailparent;
	
	@Column(name="TEL_PARENT")
	private String telparent;
	
	@Column(name="TYPE_ENREG_ET")
	private String typeenreget;
	
	@Column(name="DATE_LIEU_NAIS")
	private String datelieunaiss;
	
	@Column(name="CODE_ETAB_ORIGINE")
	private String codeetaborigine;
	
	@Column(name="CODE_SPEC_ORIGINE")
	private String codespecorigine;
	
	@Column(name="LIB_SPEC_ORIGINE")
	private String libspecorigine;
	
	@Column(name="CLASSE_PREC_ET")
	private String classeprecet;
	
	@Column(name="ID_ET_ORIGIN")
	private String idetorigin;
	
	@Column(name="ETAT")
	private String etat;
	
	@Column(name="JUSTIF_ETAT")
	private String justifetat;
	
	@Column(name="LIB_JUSTIF_ETAT")
	private String libjustifetat;
	
	@Column(name="ID_ET_NEW")
	private String idetnew;
	
	@Column(name="ID_ET_ORIGINE")
	private String idetorigine;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_SORTIE_ET")
	private Date datesortieet;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_DERN_MODIF")
	private Date datedernmodif;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_DELIVRANCE")
	private Date dateCr;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_LAST_CHANGE_ETAT")
	private Date datelastchangeetat;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_TRANSF_PREINSCRIT")
	private Date datetransfpreinscrit;
	
	@Column(name="DERN_UTILISATEUR")
	private String dernutilisateur;
	
	@Column(name="NUMCOMPTE")
	private String numcompte;
	
	@Column(name="BANQUE")
	private String banque;
	
	@Column(name="RIB_BANQUE")
	private String ribbanque;
	
	@Column(name="MP08")
	private String mp08;
	
	@Column(name="TYPE_ET")
	private String typeet;
	
	@Column(name="CODE_NATIONALITE")
	private String codenationalite;
	
	@Column(name="NUMPROMOTIONCS")
	private Integer numpromotions;
	
	@Column(name="CODE_DECISION_SESSION_P_AP1")
	private String codedecisionsessionpap1;
	
	@Column(name="LIB_DECISION_SESSION_P_AP1")
	private String libdecisionsessionpap1;
	
	@Column(name="CODE_DECISION_SESSION_R_AP1")
	private String codedecisionsessionrap1;
	
	@Column(name="LIB_DECISION_SESSION_R_AP1")
	private String libdecisionsessionrap1;
	
	@Column(name="CODE_DECISION_SESSION_P_AP2")
	private String codedecisionsessionpap2;
	
	@Column(name="LIB_DECISION_SESSION_P_AP2")
	private String libdecisionsessionpap2;
	
	@Column(name="CODE_DECISION_SESSION_R_AP2")
	private String codedecisionsessionrap2;
	
	@Column(name="LIB_DECISION_SESSION_R_AP2")
	private String libdecisionsessionrap2;
	
	@Column(name="CODE_DECISION_SESSION_P_AP3")
	private String codedecisionsessionpap3;
	
	@Column(name="LIB_DECISION_SESSION_P_AP3")
	private String libdecisionsessionpap3;
	
	@Column(name="CODE_DECISION_SESSION_R_AP3")
	private String codedecisionsessionrap3;
	
	@Column(name="LIB_DECISION_SESSION_R_AP3")
	private String libdecisionsessionrap3;
	
	@Column(name="MOY_P_AP1")
	private Integer moypap1;
	
	@Column(name="MOY_R_AP1")
	private Integer moyrap1;
	
	@Column(name="MOY_P_AP2")
	private Integer moypap2;
	
	@Column(name="MOY_R_AP2")
	private Integer moyrap2;
	
	@Column(name="MOY_P_AP3")
	private Integer moypap3;
	
	@Column(name="MOY_R_AP3")
	private Integer moyrap3;
	
	@Column(name="NB_IMP_RELEVE")
	private Integer nbimpreleve;
	
	@Column(name="CODE_BARRE")
	private String codebarre;
	
	@Column(name="MOY_BAC_ET")
	private String moybacet;
	
	@Column(name="LOGIN")
	private String login;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="FONCTION_PERE_ET")
	private String fonctionpereet;
	
	@Column(name="FONCTION_MERE_ET")
	private String fonctionmereet;
	
	@Column(name="TEL_MERE_ET")
	private String telmereet;
	
	@Column(name="TEL_PERE_ET")
	private String telpereet;
	
	@Column(name="EMAIL_PERE_ET")
	private String emailpereet;
	
	@Column(name="EMAIL_MERE_ET")
	private String emailmereet;
	
	@Column(name="NOM_PERE_ET")
	private String nomperete;
	
	@Column(name="NOM_MERE_ET")
	private String nommereet;
	
	@Column(name="ANNEE_BAC")
	private String anneebac;
	
	@Column(name="ANNEE_DIPLOME")
	private String anneediplome;
	
	@Column(name="ENS_ID_ENS")
	private String ensidens;
	
	@Column(name="FK_ENS")
	private String fkens;
	
	@Column(name="FK_ENSEI")
	private String fkensei;
	
	@Column(name="FILIERE_PREPA")
	private String filiereprepa;
	
	@Column(name="LIB_FILIERE_PREPA")
	private String libfiliereprepa;
	
	@Column(name="DATEENTR")
	private String dateentr;
	
	@Column(name="USER_TRANSF_PREINSCRIT")
	private String usertransfpreincrit;
	
	@Column(name="RECOMMANDATION")
	private String recommandation;
	
	@Column(name="GOUVERNORAT")
	private String gouvernorat;
	
	@Column(name="ADRESSE_MAIL_ESP")
	private String adressemailesp;
	
	@Column(name="PWD_ET")
	private String pwdet;
	
	@Column(name="PWD_ET_INIT")
	private String pwdetinit;
	
	@Column(name="SUIVI_PARTICULIER")
	private String suiviparticulier;
	
	@Column(name="NIVEAU_COURANT_ANG")
	private String niveaucourantang;
	
	@Column(name="NIVEAU_COURANT_FR")
	private String niveaucourantfr;
	
	@Column(name="ID_ELEVE_EDUSERV")
	private String ideleveeduserv;
	
	@Column(name="PWD_PARENT")
	private String pwdparent;
	

	@Column(name = "TOKN_STUDENT")
	private String token;
	
	@Column(name = "DATE_CREATION_TOKEN", columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
	
//	@OneToMany(mappedBy = "binome")
//	private List<FichePFE> fichePfes;
	
	
//	@OneToMany(mappedBy = "studentConvention")
//	private List<Convention> conventions;
	
	
	@OneToMany(mappedBy = "studentInscription")
	private List<InscriptionCJ> inscriptions;
	
	
	public StudentCJ() {}

	
	/************************************************ Getters & Setters *************************************************/

	
	public String getIdEt() {
		return idEt;
	}

	public void setIdEt(String idEt) {
		this.idEt = idEt;
	}

	public String getNomet() {
		return nomet;
	}

	public void setNomet(String nomet) {
		this.nomet = nomet;
	}

	public String getPrenomet() {
		return prenomet;
	}

	public void setPrenomet(String prenomet) {
		this.prenomet = prenomet;
	}

	public Date getDatenaisset() {
		return datenaisset;
	}

	public void setDatenaisset(Date datenaisset) {
		this.datenaisset = datenaisset;
	}

	public String getLieunaiset() {
		return lieunaiset;
	}

	public void setLieunaiset(String lieunaiset) {
		this.lieunaiset = lieunaiset;
	}

	public String getNatureet() {
		return natureet;
	}

	public void setNatureet(String natureet) {
		this.natureet = natureet;
	}

	public String getFonctionet() {
		return fonctionet;
	}

	public void setFonctionet(String fonctionet) {
		this.fonctionet = fonctionet;
	}

	public String getAdresseet() {
		return adresseet;
	}

	public void setAdresseet(String adresseet) {
		this.adresseet = adresseet;
	}

	public String getTelet() {
		return telet;
	}

	public void setTelet(String telet) {
		this.telet = telet;
	}

	public String getTelparentet() {
		return telparentet;
	}

	public void setTelparentet(String telparentet) {
		this.telparentet = telparentet;
	}

	public String getEmailet() {
		return emailet;
	}

	public void setEmailet(String emailet) {
		this.emailet = emailet;
	}

	public String getCycleet() {
		return cycleet;
	}

	public void setCycleet(String cycleet) {
		this.cycleet = cycleet;
	}

	public String getNaturebac() {
		return naturebac;
	}

	public void setNaturebac(String naturebac) {
		this.naturebac = naturebac;
	}

	public Date getDatebac() {
		return datebac;
	}

	public void setDatebac(Date datebac) {
		this.datebac = datebac;
	}

	public String getNumbacet() {
		return numbacet;
	}

	public void setNumbacet(String numbacet) {
		this.numbacet = numbacet;
	}

	public String getEtabbac() {
		return etabbac;
	}

	public void setEtabbac(String etabbac) {
		this.etabbac = etabbac;
	}

	public String getDiplomesupet() {
		return diplomesupet;
	}

	public void setDiplomesupet(String diplomesupet) {
		this.diplomesupet = diplomesupet;
	}

	public String getEtaborigine() {
		return etaborigine;
	}

	public void setEtaborigine(String etaborigine) {
		this.etaborigine = etaborigine;
	}

	public String getSpecialiteespet() {
		return specialiteespet;
	}

	public void setSpecialiteespet(String specialiteespet) {
		this.specialiteespet = specialiteespet;
	}

	public Date getDateentreeespet() {
		return dateentreeespet;
	}

	public void setDateentreeespet(Date dateentreeespet) {
		this.dateentreeespet = dateentreeespet;
	}

	public String getEnneeentreeespet() {
		return enneeentreeespet;
	}

	public void setEnneeentreeespet(String enneeentreeespet) {
		this.enneeentreeespet = enneeentreeespet;
	}

	public String getClassecourantet() {
		return classecourantet;
	}

	public void setClassecourantet(String classecourantet) {
		this.classecourantet = classecourantet;
	}

	public String getSituationfinanciereet() {
		return situationfinanciereet;
	}

	public void setSituationfinanciereet(String situationfinanciereet) {
		this.situationfinanciereet = situationfinanciereet;
	}

	public String getResultatfinaleet() {
		return resultatfinaleet;
	}

	public void setResultatfinaleet(String resultatfinaleet) {
		this.resultatfinaleet = resultatfinaleet;
	}

	public String getDiplomeobtenuespet() {
		return diplomeobtenuespet;
	}

	public void setDiplomeobtenuespet(String diplomeobtenuespet) {
		this.diplomeobtenuespet = diplomeobtenuespet;
	}

	public String getObservationet() {
		return observationet;
	}

	public void setObservationet(String observationet) {
		this.observationet = observationet;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getNumcinpasseport() {
		return numcinpasseport;
	}

	public void setNumcinpasseport(String numcinpasseport) {
		this.numcinpasseport = numcinpasseport;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getNumord() {
		return numord;
	}

	public void setNumord(String numord) {
		this.numord = numord;
	}

	public String getLieudelivrance() {
		return lieudelivrance;
	}

	public void setLieudelivrance(String lieudelivrance) {
		this.lieudelivrance = lieudelivrance;
	}

	public String getNaturecours() {
		return naturecours;
	}

	public void setNaturecours(String naturecours) {
		this.naturecours = naturecours;
	}

	public String getNaturepieceid() {
		return naturepieceid;
	}

	public void setNaturepieceid(String naturepieceid) {
		this.naturepieceid = naturepieceid;
	}

	public String getAdresseparent() {
		return adresseparent;
	}

	public void setAdresseparent(String adresseparent) {
		this.adresseparent = adresseparent;
	}

	public String getCpparent() {
		return cpparent;
	}

	public void setCpparent(String cpparent) {
		this.cpparent = cpparent;
	}

	public String getVilleparent() {
		return villeparent;
	}

	public void setVilleparent(String villeparent) {
		this.villeparent = villeparent;
	}

	public String getPaysparent() {
		return paysparent;
	}

	public void setPaysparent(String paysparent) {
		this.paysparent = paysparent;
	}

	public String getCpet() {
		return cpet;
	}

	public void setCpet(String cpet) {
		this.cpet = cpet;
	}

	public String getVilleet() {
		return villeet;
	}

	public void setVilleet(String villeet) {
		this.villeet = villeet;
	}

	public String getPayset() {
		return payset;
	}

	public void setPayset(String payset) {
		this.payset = payset;
	}

	public String getEmailparent() {
		return emailparent;
	}

	public void setEmailparent(String emailparent) {
		this.emailparent = emailparent;
	}

	public String getTelparent() {
		return telparent;
	}

	public void setTelparent(String telparent) {
		this.telparent = telparent;
	}

	public String getTypeenreget() {
		return typeenreget;
	}

	public void setTypeenreget(String typeenreget) {
		this.typeenreget = typeenreget;
	}

	public String getDatelieunaiss() {
		return datelieunaiss;
	}

	public void setDatelieunaiss(String datelieunaiss) {
		this.datelieunaiss = datelieunaiss;
	}

	public String getCodeetaborigine() {
		return codeetaborigine;
	}

	public void setCodeetaborigine(String codeetaborigine) {
		this.codeetaborigine = codeetaborigine;
	}

	public String getCodespecorigine() {
		return codespecorigine;
	}

	public void setCodespecorigine(String codespecorigine) {
		this.codespecorigine = codespecorigine;
	}

	public String getLibspecorigine() {
		return libspecorigine;
	}

	public void setLibspecorigine(String libspecorigine) {
		this.libspecorigine = libspecorigine;
	}

	public String getClasseprecet() {
		return classeprecet;
	}

	public void setClasseprecet(String classeprecet) {
		this.classeprecet = classeprecet;
	}

	public String getIdetorigin() {
		return idetorigin;
	}

	public void setIdetorigin(String idetorigin) {
		this.idetorigin = idetorigin;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getJustifetat() {
		return justifetat;
	}

	public void setJustifetat(String justifetat) {
		this.justifetat = justifetat;
	}

	public String getLibjustifetat() {
		return libjustifetat;
	}

	public void setLibjustifetat(String libjustifetat) {
		this.libjustifetat = libjustifetat;
	}

	public String getIdetnew() {
		return idetnew;
	}

	public void setIdetnew(String idetnew) {
		this.idetnew = idetnew;
	}

	public String getIdetorigine() {
		return idetorigine;
	}

	public void setIdetorigine(String idetorigine) {
		this.idetorigine = idetorigine;
	}

	public Date getDatesortieet() {
		return datesortieet;
	}

	public void setDatesortieet(Date datesortieet) {
		this.datesortieet = datesortieet;
	}

	public Date getDatesaisie() {
		return datesaisie;
	}

	public void setDatesaisie(Date datesaisie) {
		this.datesaisie = datesaisie;
	}

	public Date getDatedernmodif() {
		return datedernmodif;
	}

	public void setDatedernmodif(Date datedernmodif) {
		this.datedernmodif = datedernmodif;
	}

	public Date getDateCr() {
		return dateCr;
	}

	public void setDateCr(Date dateCr) {
		this.dateCr = dateCr;
	}

	public Date getDatelastchangeetat() {
		return datelastchangeetat;
	}

	public void setDatelastchangeetat(Date datelastchangeetat) {
		this.datelastchangeetat = datelastchangeetat;
	}

	public Date getDatetransfpreinscrit() {
		return datetransfpreinscrit;
	}

	public void setDatetransfpreinscrit(Date datetransfpreinscrit) {
		this.datetransfpreinscrit = datetransfpreinscrit;
	}

	public String getDernutilisateur() {
		return dernutilisateur;
	}

	public void setDernutilisateur(String dernutilisateur) {
		this.dernutilisateur = dernutilisateur;
	}

	public String getNumcompte() {
		return numcompte;
	}

	public void setNumcompte(String numcompte) {
		this.numcompte = numcompte;
	}

	public String getBanque() {
		return banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}

	public String getRibbanque() {
		return ribbanque;
	}

	public void setRibbanque(String ribbanque) {
		this.ribbanque = ribbanque;
	}

	public String getMp08() {
		return mp08;
	}

	public void setMp08(String mp08) {
		this.mp08 = mp08;
	}

	public String getTypeet() {
		return typeet;
	}

	public void setTypeet(String typeet) {
		this.typeet = typeet;
	}

	public String getCodenationalite() {
		return codenationalite;
	}

	public void setCodenationalite(String codenationalite) {
		this.codenationalite = codenationalite;
	}

	public String getCodedecisionsessionpap1() {
		return codedecisionsessionpap1;
	}

	public void setCodedecisionsessionpap1(String codedecisionsessionpap1) {
		this.codedecisionsessionpap1 = codedecisionsessionpap1;
	}

	public String getLibdecisionsessionpap1() {
		return libdecisionsessionpap1;
	}

	public void setLibdecisionsessionpap1(String libdecisionsessionpap1) {
		this.libdecisionsessionpap1 = libdecisionsessionpap1;
	}

	public String getCodedecisionsessionrap1() {
		return codedecisionsessionrap1;
	}

	public void setCodedecisionsessionrap1(String codedecisionsessionrap1) {
		this.codedecisionsessionrap1 = codedecisionsessionrap1;
	}

	public String getLibdecisionsessionrap1() {
		return libdecisionsessionrap1;
	}

	public void setLibdecisionsessionrap1(String libdecisionsessionrap1) {
		this.libdecisionsessionrap1 = libdecisionsessionrap1;
	}

	public String getCodedecisionsessionpap2() {
		return codedecisionsessionpap2;
	}

	public void setCodedecisionsessionpap2(String codedecisionsessionpap2) {
		this.codedecisionsessionpap2 = codedecisionsessionpap2;
	}

	public String getLibdecisionsessionpap2() {
		return libdecisionsessionpap2;
	}

	public void setLibdecisionsessionpap2(String libdecisionsessionpap2) {
		this.libdecisionsessionpap2 = libdecisionsessionpap2;
	}

	public String getCodedecisionsessionrap2() {
		return codedecisionsessionrap2;
	}

	public void setCodedecisionsessionrap2(String codedecisionsessionrap2) {
		this.codedecisionsessionrap2 = codedecisionsessionrap2;
	}

	public String getLibdecisionsessionrap2() {
		return libdecisionsessionrap2;
	}

	public void setLibdecisionsessionrap2(String libdecisionsessionrap2) {
		this.libdecisionsessionrap2 = libdecisionsessionrap2;
	}

	public String getCodedecisionsessionpap3() {
		return codedecisionsessionpap3;
	}

	public void setCodedecisionsessionpap3(String codedecisionsessionpap3) {
		this.codedecisionsessionpap3 = codedecisionsessionpap3;
	}

	public String getLibdecisionsessionpap3() {
		return libdecisionsessionpap3;
	}

	public void setLibdecisionsessionpap3(String libdecisionsessionpap3) {
		this.libdecisionsessionpap3 = libdecisionsessionpap3;
	}

	public String getCodedecisionsessionrap3() {
		return codedecisionsessionrap3;
	}

	public void setCodedecisionsessionrap3(String codedecisionsessionrap3) {
		this.codedecisionsessionrap3 = codedecisionsessionrap3;
	}

	public String getLibdecisionsessionrap3() {
		return libdecisionsessionrap3;
	}

	public void setLibdecisionsessionrap3(String libdecisionsessionrap3) {
		this.libdecisionsessionrap3 = libdecisionsessionrap3;
	}

	public String getCodebarre() {
		return codebarre;
	}

	public void setCodebarre(String codebarre) {
		this.codebarre = codebarre;
	}

	public String getMoybacet() {
		return moybacet;
	}

	public void setMoybacet(String moybacet) {
		this.moybacet = moybacet;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFonctionpereet() {
		return fonctionpereet;
	}

	public void setFonctionpereet(String fonctionpereet) {
		this.fonctionpereet = fonctionpereet;
	}

	public String getFonctionmereet() {
		return fonctionmereet;
	}

	public void setFonctionmereet(String fonctionmereet) {
		this.fonctionmereet = fonctionmereet;
	}

	public String getTelmereet() {
		return telmereet;
	}

	public void setTelmereet(String telmereet) {
		this.telmereet = telmereet;
	}

	public String getTelpereet() {
		return telpereet;
	}

	public void setTelpereet(String telpereet) {
		this.telpereet = telpereet;
	}

	public String getEmailpereet() {
		return emailpereet;
	}

	public void setEmailpereet(String emailpereet) {
		this.emailpereet = emailpereet;
	}

	public String getEmailmereet() {
		return emailmereet;
	}

	public void setEmailmereet(String emailmereet) {
		this.emailmereet = emailmereet;
	}

	public String getNomperete() {
		return nomperete;
	}

	public void setNomperete(String nomperete) {
		this.nomperete = nomperete;
	}

	public String getNommereet() {
		return nommereet;
	}

	public void setNommereet(String nommereet) {
		this.nommereet = nommereet;
	}

	public String getAnneebac() {
		return anneebac;
	}

	public void setAnneebac(String anneebac) {
		this.anneebac = anneebac;
	}

	public String getAnneediplome() {
		return anneediplome;
	}

	public void setAnneediplome(String anneediplome) {
		this.anneediplome = anneediplome;
	}

	public String getEnsidens() {
		return ensidens;
	}

	public void setEnsidens(String ensidens) {
		this.ensidens = ensidens;
	}

	public String getFkens() {
		return fkens;
	}

	public void setFkens(String fkens) {
		this.fkens = fkens;
	}

	public String getFkensei() {
		return fkensei;
	}

	public void setFkensei(String fkensei) {
		this.fkensei = fkensei;
	}

	public String getFiliereprepa() {
		return filiereprepa;
	}

	public void setFiliereprepa(String filiereprepa) {
		this.filiereprepa = filiereprepa;
	}

	public String getLibfiliereprepa() {
		return libfiliereprepa;
	}

	public void setLibfiliereprepa(String libfiliereprepa) {
		this.libfiliereprepa = libfiliereprepa;
	}

	public String getDateentr() {
		return dateentr;
	}

	public void setDateentr(String dateentr) {
		this.dateentr = dateentr;
	}

	public String getUsertransfpreincrit() {
		return usertransfpreincrit;
	}

	public void setUsertransfpreincrit(String usertransfpreincrit) {
		this.usertransfpreincrit = usertransfpreincrit;
	}

	public String getRecommandation() {
		return recommandation;
	}

	public void setRecommandation(String recommandation) {
		this.recommandation = recommandation;
	}

	public String getGouvernorat() {
		return gouvernorat;
	}

	public void setGouvernorat(String gouvernorat) {
		this.gouvernorat = gouvernorat;
	}

	public String getAdressemailesp() {
		return adressemailesp;
	}

	public void setAdressemailesp(String adressemailesp) {
		this.adressemailesp = adressemailesp;
	}

	public String getPwdet() {
		return pwdet;
	}

	public void setPwdet(String pwdet) {
		this.pwdet = pwdet;
	}

	public String getPwdetinit() {
		return pwdetinit;
	}

	public void setPwdetinit(String pwdetinit) {
		this.pwdetinit = pwdetinit;
	}

	public String getSuiviparticulier() {
		return suiviparticulier;
	}

	public void setSuiviparticulier(String suiviparticulier) {
		this.suiviparticulier = suiviparticulier;
	}

	public String getNiveaucourantang() {
		return niveaucourantang;
	}

	public void setNiveaucourantang(String niveaucourantang) {
		this.niveaucourantang = niveaucourantang;
	}

	public String getNiveaucourantfr() {
		return niveaucourantfr;
	}

	public void setNiveaucourantfr(String niveaucourantfr) {
		this.niveaucourantfr = niveaucourantfr;
	}

	public String getIdeleveeduserv() {
		return ideleveeduserv;
	}

	public void setIdeleveeduserv(String ideleveeduserv) {
		this.ideleveeduserv = ideleveeduserv;
	}

	public String getPwdparent() {
		return pwdparent;
	}

	public void setPwdparent(String pwdparent) {
		this.pwdparent = pwdparent;
	}

	public String getPwdJWTEtudiant() {
		return pwdJWTEtudiant;
	}

	public void setPwdJWTEtudiant(String pwdJWTEtudiant) {
		this.pwdJWTEtudiant = pwdJWTEtudiant;
	}

	public Date getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(Date dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

	public String getNiveauDiplomeSupEt() {
		return niveauDiplomeSupEt;
	}

	public void setNiveauDiplomeSupEt(String niveauDiplomeSupEt) {
		this.niveauDiplomeSupEt = niveauDiplomeSupEt;
	}

	public String getNiveaucourantet() {
		return niveaucourantet;
	}

	public void setNiveaucourantet(String niveaucourantet) {
		this.niveaucourantet = niveaucourantet;
	}

	public String getMoyennedernsemestreet() {
		return moyennedernsemestreet;
	}

	public void setMoyennedernsemestreet(String moyennedernsemestreet) {
		this.moyennedernsemestreet = moyennedernsemestreet;
	}

	public String getNiveauacces() {
		return niveauacces;
	}

	public void setNiveauacces(String niveauacces) {
		this.niveauacces = niveauacces;
	}

	public Integer getNumpromotions() {
		return numpromotions;
	}

	public void setNumpromotions(Integer numpromotions) {
		this.numpromotions = numpromotions;
	}

	public Integer getMoypap1() {
		return moypap1;
	}

	public void setMoypap1(Integer moypap1) {
		this.moypap1 = moypap1;
	}

	public Integer getMoyrap1() {
		return moyrap1;
	}

	public void setMoyrap1(Integer moyrap1) {
		this.moyrap1 = moyrap1;
	}

	public Integer getMoypap2() {
		return moypap2;
	}

	public void setMoypap2(Integer moypap2) {
		this.moypap2 = moypap2;
	}

	public Integer getMoyrap2() {
		return moyrap2;
	}

	public void setMoyrap2(Integer moyrap2) {
		this.moyrap2 = moyrap2;
	}

	public Integer getMoypap3() {
		return moypap3;
	}

	public void setMoypap3(Integer moypap3) {
		this.moypap3 = moypap3;
	}

	public Integer getMoyrap3() {
		return moyrap3;
	}

	public void setMoyrap3(Integer moyrap3) {
		this.moyrap3 = moyrap3;
	}

	public Integer getNbimpreleve() {
		return nbimpreleve;
	}

	public void setNbimpreleve(Integer nbimpreleve) {
		this.nbimpreleve = nbimpreleve;
	}

//	public List<FichePFE> getFichePfes() {
//		return fichePfes;
//	}
//
//	public void setFichePfes(List<FichePFE> fichePfes) {
//		this.fichePfes = fichePfes;
//	}

//	public List<Convention> getConventions() {
//		return conventions;
//	}
//
//	public void setConventions(List<Convention> conventions) {
//		this.conventions = conventions;
//	}

	@JsonIgnore
	public List<InscriptionCJ> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<InscriptionCJ> inscriptions) {
		this.inscriptions = inscriptions;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}

}

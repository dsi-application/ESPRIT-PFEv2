package com.esprit.gdp.controllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.esprit.gdp.payload.request.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.esprit.gdp.dto.AvenantDateAvDateDebutStageStatusDto;
import com.esprit.gdp.dto.AvenantDto;
import com.esprit.gdp.dto.BinomeIdFullNameDto;
import com.esprit.gdp.dto.BinomePVDetailsDto;
import com.esprit.gdp.dto.CompanyDto;
import com.esprit.gdp.dto.ConventionDateStatusDto;
import com.esprit.gdp.dto.ConventionDto;
import com.esprit.gdp.dto.DepotAttestationINGDto;
import com.esprit.gdp.dto.DepotBilanDto;
import com.esprit.gdp.dto.DepotGanttDiagramDto;
import com.esprit.gdp.dto.DepotJournalDto;
import com.esprit.gdp.dto.DepotJournalINGDto;
import com.esprit.gdp.dto.DepotRapportINGDto;
import com.esprit.gdp.dto.DepotRapportUrgDto;
import com.esprit.gdp.dto.DepotSignedAvenDto;
import com.esprit.gdp.dto.DepotSignedConvDto;
import com.esprit.gdp.dto.EntrepriseAccueilDesignationPaysByStudentDto;
import com.esprit.gdp.dto.FichePFEDateDepotReportsEtatReportsDto;
import com.esprit.gdp.dto.FichePFEDepotDto;
import com.esprit.gdp.dto.FichePFEDto;
import com.esprit.gdp.dto.FichePFEMultiplePlanificationDto;
import com.esprit.gdp.dto.FichePFEPlanificationSTNDto;
import com.esprit.gdp.dto.PaysDto;
import com.esprit.gdp.dto.PedagogicalCoordinatorDto;
import com.esprit.gdp.dto.RendezVousVisiteStagiaireDto;
import com.esprit.gdp.dto.ResponsableServiceStageDto;
import com.esprit.gdp.dto.ResponsibleDto;
import com.esprit.gdp.dto.SectorEntrepriseDto;
import com.esprit.gdp.dto.StudentAdvancedPlanificationDto;
import com.esprit.gdp.dto.StudentAffectationDetailsDto;
import com.esprit.gdp.dto.StudentConvFRDto;
import com.esprit.gdp.dto.StudentDto;
import com.esprit.gdp.dto.StudentExcelDto;
import com.esprit.gdp.dto.StudentFullNameMailTelDto;
import com.esprit.gdp.dto.StudentMailTelDto;
import com.esprit.gdp.dto.StudentMultiplePlanificationDto;
import com.esprit.gdp.dto.StudentPlanifySTNDto;
import com.esprit.gdp.dto.StudentSTNExcelDto;
import com.esprit.gdp.dto.StudentSpeedDto;
import com.esprit.gdp.dto.StudentTreatDepotDto;
import com.esprit.gdp.dto.TeacherDto;
import com.esprit.gdp.dto.TeacherDtoSTN;
import com.esprit.gdp.dto.TeacherDtoSTN2;
import com.esprit.gdp.dto.TeacherEncadrantPedaDto;
import com.esprit.gdp.dto.TeacherProfileDto;
import com.esprit.gdp.files.ConventionEN_PDF;
import com.esprit.gdp.files.ConventionFR_PDF;
import com.esprit.gdp.files.ConventionTN_PDF;
import com.esprit.gdp.files.FicheDepotPFE_PDF;
import com.esprit.gdp.files.PVEtudiant_PDF;
import com.esprit.gdp.files.PlanningSoutenanceByFilter_Excel;
import com.esprit.gdp.files.PlanningSoutenanceGlobal_Excel;
import com.esprit.gdp.googleServices.GoogleCalendarServices;
import com.esprit.gdp.googleServices.GoogleDriveServices;
import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.models.AvenantPK;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.ConventionPK;
import com.esprit.gdp.models.EncadrantEntreprise;
import com.esprit.gdp.models.EncadrantEntreprisePK;
import com.esprit.gdp.models.EntrepriseAccueil;
import com.esprit.gdp.models.EvaluationEngineeringTraining;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.Pays;
import com.esprit.gdp.models.PedagogicalCoordinator;
import com.esprit.gdp.models.Problematique;
import com.esprit.gdp.models.ProblematiquePK;
import com.esprit.gdp.models.ResponsableServiceStage;
import com.esprit.gdp.models.Salle;
import com.esprit.gdp.models.SecteurEntreprise;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.StudentCS;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.models.Technologie;
import com.esprit.gdp.models.edt.Seance;
import com.esprit.gdp.models.temporaryEntities.UrgRdvPFE;
import com.esprit.gdp.models.temporaryEntities.UrgRdvPFEDto;
import com.esprit.gdp.models.temporaryEntities.UrgRdvPFEPK;
import com.esprit.gdp.models.temporaryEntities.UrgRdvPFERepository;
import com.esprit.gdp.payload.response.MessageResponse;
import com.esprit.gdp.payload.response.TeacherResponse;
import com.esprit.gdp.payload.response.UserResponse;
import com.esprit.gdp.repository.AvenantRepository;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.EncadrantEntrepriseRepository;
import com.esprit.gdp.repository.EntrepriseAccueilRepository;
import com.esprit.gdp.repository.EvaluationEngineeringTrainingRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.NoteRestitutionRepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.OptionStudentALTRepository;
import com.esprit.gdp.repository.PaysRepository;
import com.esprit.gdp.repository.PedagogicalCoordinatorRepository;
import com.esprit.gdp.repository.ProblematicRepository;
import com.esprit.gdp.repository.RDVRepository;
import com.esprit.gdp.repository.ResponsableServiceStageRepository;
import com.esprit.gdp.repository.SalleRepository;
import com.esprit.gdp.repository.SeanceRepository;
import com.esprit.gdp.repository.SectorEntrepriseRepository;
import com.esprit.gdp.repository.SessionRepository;
import com.esprit.gdp.repository.SettingsRepository;
import com.esprit.gdp.repository.SocieteRepository;
import com.esprit.gdp.repository.StudentCSRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.repository.TechnologieRepository;
import com.esprit.gdp.repository.UrgRDVSuiviStage;
import com.esprit.gdp.repository.VisiteStagiareRepository;
import com.esprit.gdp.repository.WeekScheduleRepository;
import com.esprit.gdp.security.jwt.JwtProvider;
import com.esprit.gdp.security.services.PedagogicalCoordinatorDetailsImpl;
import com.esprit.gdp.security.services.ResponsableServiceStageDetailsImpl;
import com.esprit.gdp.security.services.StudentCSDetailsImpl;
import com.esprit.gdp.security.services.StudentDetailsImpl;
import com.esprit.gdp.security.services.TeacherDetailsImpl;
import com.esprit.gdp.services.SendMailSTNServices;
import com.esprit.gdp.services.StorageServices;
import com.esprit.gdp.services.UtilServices;
import com.google.common.collect.Sets;

//@RestController
//@CrossOrigin(origins = "http://193.95.99.194:8081")
//@RequestMapping("/api/auth")

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/auth")
public class MainController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtUtils;

	@Autowired
	EncadrantEntrepriseRepository responsibleRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentCSRepository studentCSRepository;

	@Autowired
	ConventionRepository conventionRepository;

	@Autowired
	AvenantRepository avenantRepository;

	@Autowired
	EntrepriseAccueilRepository entrepriseAccueilRepository;

	@Autowired
	SectorEntrepriseRepository sectorEntrepriseRepository;

	@Autowired
	PaysRepository paysRepository;

	@Autowired
	TechnologieRepository technologyRepository;

	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	ProblematicRepository problematicRepository;

	@Autowired
	CodeNomenclatureRepository codeNomenclatureRepository;

	@Autowired
	UrgRDVSuiviStage urgRDVSuiviStage;

	@Autowired
	InscriptionRepository inscriptionRepository;

	@Autowired
	ResponsableServiceStageRepository responsableServiceStageRepository;

	@Autowired
	SocieteRepository societeRepository;

	@Autowired
	WeekScheduleRepository weekScheduleRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	StorageServices storageService;

	@Autowired
	SeanceRepository seanceRepository;

	@Autowired
	SalleRepository salleRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	PedagogicalCoordinatorRepository pedagogicalCoordinatorRepository;

	@Autowired
	UtilServices utilServices;

	@Autowired
	SendMailSTNServices sendMailSTNServices;

	@Autowired
	OptionRepository optionRepository;

	@Autowired
	UrgRdvPFERepository urgRdvPFERepository;

	@Autowired
	GoogleCalendarServices calendarServices;

	@Autowired
	EncadrantEntrepriseRepository encadrantEntrepriseRepository;

	@Autowired
	RDVRepository rdvRepository;

	@Autowired
	VisiteStagiareRepository visiteStagiareRepository;

	@Autowired
	OptionStudentALTRepository optionStudentALTRepository;

	@Autowired
	SettingsRepository settingsRepository;

	@Autowired
	EvaluationEngineeringTrainingRepository evaluationEngTrRepository;

	@Autowired
	NoteRestitutionRepository noteRestitutionRepository;

	/******************************************************
	 * Convention
	 ******************************************************/
	@GetMapping("/traineeDuration")
	public Integer findTrainneeDuration() {
		return societeRepository.findTraineeDuration();
	}

	@PostMapping("/addConvention/{idEt}")
	public void addConvention(@Valid @RequestBody AddConventionRequest addConventionRequest, @PathVariable String idEt)
			throws UnsupportedEncodingException {

		/*
		System.out.println("-----> NomSociete: " + addConventionRequest.getNomSociete() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getNomSociete()));
		System.out.println("-----> getMail: " + addConventionRequest.getMail() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getMail()));
		System.out.println("-----> getAddress: " + addConventionRequest.getAddress() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getAddress()));
		System.out.println("-----> getTelephone: " + addConventionRequest.getTelephone() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getTelephone()));
		System.out.println("-----> getResponsable: " + addConventionRequest.getResponsable() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getResponsable()));
		*/
		ConventionPK conventionPK = new ConventionPK(idEt, new Timestamp(System.currentTimeMillis()));

		/********************************************************************************************************************/

		// System.out.println("---------------------------> Date Début : " + addConventionRequest.getDateDebut());
		// System.out.println("---------------------------> Date Fin : " + addConventionRequest.getDateFin());

		Integer trainingDuration = null;

		// SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
		// String dateBeforeString = assds.substring(0, assds.lastIndexOf("**"));
		// String dateAfterString = assds.substring(assds.lastIndexOf("**")+2,
		// assds.length());
		//
		// Date dateBefore = myFormat.parse(dateBeforeString);
		// Date dateAfter = myFormat.parse(dateAfterString);
		long difference = addConventionRequest.getDateFin().getTime() - addConventionRequest.getDateDebut().getTime();
		float daysBetween = (difference / (1000 * 60 * 60 * 24));

		trainingDuration = Math.round(daysBetween / 7);
		// System.out.println("---------------------------> AZERTY 4: " + trainingDuration);

		/********************************************************************************************************************/

		EntrepriseAccueil ea = new EntrepriseAccueil();
		ea = entrepriseAccueilRepository
				.findByDesignation(utilServices.decodeEncodedValue(addConventionRequest.getNomSociete()));

		Convention conv = new Convention(conventionPK, ea.getAddressMail(), addConventionRequest.getDateDebut(),
				addConventionRequest.getDateFin(), utilServices.decodeEncodedValue(addConventionRequest.getAddress()),
				utilServices.decodeEncodedValue(addConventionRequest.getTelephone()),
				utilServices.decodeEncodedValue(addConventionRequest.getResponsable()), "01", trainingDuration);

		// Convention conv = new Convention(conventionPK,
		// utilServices.decodeEncodedValue(addConventionRequest.getMail()),
		// addConventionRequest.getDateDebut(),
		// addConventionRequest.getDateFin(),
		// utilServices.decodeEncodedValue(addConventionRequest.getAddress()),
		// utilServices.decodeEncodedValue(addConventionRequest.getTelephone()),
		// utilServices.decodeEncodedValue(addConventionRequest.getResponsable()),
		// "01"
		// //, ea
		// );

		conv.setEntrepriseAccueilConvention(ea);

		String currentClass = utilServices.findCurrentClassByIdEt(idEt);
		ResponsableServiceStage respServStage = null;

		respServStage = utilServices.findResponsableServiceStageByClass(currentClass);

		// System.out.println("-------------------------------> Class: " + currentClass
		// + " Service: " + respServStage.getNomUserSce());

		conv.setResponsableServiceStage(respServStage);

		conventionRepository.save(conv);

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateConvention = dateFormata.format(conventionPK.getDateConvention());

		String subject = "Demande Convention";

		// String receiver = "no_reply@esprit.tn";
		String receiver = utilServices.findStudentMailById(idEt); // SERVER

		String content = "Vous avez envoyé une <strong><font color=grey> demande de Convention </font></strong> le <font color=blue> "
				+ dateConvention + " </font>." + "<br/> Merci de patienter jusqu'à le traitement de votre requête.";

		utilServices.sendMail(subject, receiver, content);

	}

	@PostMapping("/modifyConvention/{idEt}")
	public void modifyConvention(@Valid @RequestBody AddConventionRequest addConventionRequest,
								 @PathVariable String idEt) throws UnsupportedEncodingException {

		/*
		System.out.println("-----> NomSociete: " + addConventionRequest.getNomSociete() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getNomSociete()));
		System.out.println("-----> getMail: " + addConventionRequest.getMail() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getMail()));
		System.out.println("-----> getAddress: " + addConventionRequest.getAddress() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getAddress()));
		System.out.println("-----> getTelephone: " + addConventionRequest.getTelephone() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getTelephone()));
		System.out.println("-----> getResponsable: " + addConventionRequest.getResponsable() + " - "
				+ utilServices.decodeEncodedValue(addConventionRequest.getResponsable()));
		*/

		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);
		EntrepriseAccueil ea = new EntrepriseAccueil();
		ea = entrepriseAccueilRepository
				.findByDesignation(utilServices.decodeEncodedValue(addConventionRequest.getNomSociete()));

		convention.setAddress(utilServices.decodeEncodedValue(addConventionRequest.getAddress()));
		convention.setTelephone(ea.getTelephone());
		convention.setResponsable(utilServices.decodeEncodedValue(addConventionRequest.getResponsable()));
		convention.setDateDebut(addConventionRequest.getDateDebut());
		convention.setDateFin(addConventionRequest.getDateFin());
		convention.setMail(ea.getAddressMail());
		convention.setDateModifConvRSS(new Timestamp(System.currentTimeMillis()));

		/**************************************************************/
		/*
		 * ConventionPK conventionPK = new ConventionPK(idEt, new
		 * Timestamp(System.currentTimeMillis()));
		 *
		 * Convention conv = new Convention(conventionPK, ea.getAddressMail(),
		 * addConventionRequest.getDateDebut(), addConventionRequest.getDateFin(),
		 * utilServices.decodeEncodedValue(addConventionRequest.getAddress()),
		 * utilServices.decodeEncodedValue(addConventionRequest.getTelephone()),
		 * utilServices.decodeEncodedValue(addConventionRequest.getResponsable()), "01"
		 * //, ea );
		 */
		convention.setEntrepriseAccueilConvention(ea);

		String currentClass = utilServices.findCurrentClassByIdEt(idEt);
		ResponsableServiceStage respServStage = null;

		respServStage = utilServices.findResponsableServiceStageByClass(currentClass);

		// System.out.println("-------------------------------> Class: " + currentClass
		// + " Service: " + respServStage.getNomUserSce());

		convention.setResponsableServiceStage(respServStage);

		conventionRepository.save(convention);

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateConvention = dateFormata.format(new Date());

		String subject = "Modification Convention";

		// Server
		String studentMail = utilServices.findStudentMailById(idEt);
		String rssMail = responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT");

		// Local
		// String studentMail = "saria.essid@esprit.tn";
		// String rssMail = "ram.benouirane@mail.com";

		String content = "Nous voulons vous informer par le présent mail que Mme/M le Responsable Service Stage a modifié quelques informations "
				+ "concernant <strong><font color=grey>votre Convention</font></strong> et c'est le <font color=blue> "
				+ dateConvention + " </font>."
				+ "<br/>Une fois validée, vous pouvez consulter <strong><font color=grey>votre Nouvelle Convention</font></strong> via votre espace (Menu : Mes Documents).";

		utilServices.sendMailWithCC(subject, studentMail, rssMail, content);

	}


	@PostMapping("/modifyConventionByStudent/{idEt}")
	public void modifyConventionByStudent(@Valid @RequestBody AddConventionRequest addConventionRequest, @PathVariable String idEt) throws UnsupportedEncodingException
	{

		/*
		System.out.println("--MODIF STU---> NomSociete: " + addConventionRequest.getNomSociete() + " - " + utilServices.decodeEncodedValue(addConventionRequest.getNomSociete()));
		System.out.println("--MODIF STU---> getMail: " + addConventionRequest.getMail() + " - " + utilServices.decodeEncodedValue(addConventionRequest.getMail()));
		System.out.println("--MODIF STU---> getAddress: " + addConventionRequest.getAddress() + " - " + utilServices.decodeEncodedValue(addConventionRequest.getAddress()));
		System.out.println("--MODIF STU---> getTelephone: " + addConventionRequest.getTelephone() + " - " + utilServices.decodeEncodedValue(addConventionRequest.getTelephone()));
		System.out.println("--MODIF STU---> getResponsable: " + addConventionRequest.getResponsable() + " - " + utilServices.decodeEncodedValue(addConventionRequest.getResponsable()));
		*/

		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);
		EntrepriseAccueil ea = new EntrepriseAccueil();
		ea = entrepriseAccueilRepository.findByDesignation(utilServices.decodeEncodedValue(addConventionRequest.getNomSociete()));

		convention.setAddress(utilServices.decodeEncodedValue(addConventionRequest.getAddress()));
		convention.setTelephone(ea.getTelephone());
		convention.setResponsable(utilServices.decodeEncodedValue(addConventionRequest.getResponsable()));
		convention.setDateDebut(addConventionRequest.getDateDebut());
		convention.setDateFin(addConventionRequest.getDateFin());
		convention.setMail(ea.getAddressMail());
		convention.setDateModifConvRSS(new Timestamp(System.currentTimeMillis()));
		convention.setTraiter("01");

		/**************************************************************/
		/*
		ConventionPK conventionPK = new ConventionPK(idEt, new Timestamp(System.currentTimeMillis()));

		Convention conv = new Convention(conventionPK,
										 ea.getAddressMail(),
										 addConventionRequest.getDateDebut(),
										 addConventionRequest.getDateFin(),
										 utilServices.decodeEncodedValue(addConventionRequest.getAddress()),
										 utilServices.decodeEncodedValue(addConventionRequest.getTelephone()),
										 utilServices.decodeEncodedValue(addConventionRequest.getResponsable()),
										 "01"
										 //, ea
										 );
		*/
		convention.setEntrepriseAccueilConvention(ea);

		String currentClass = utilServices.findCurrentClassByIdEt(idEt);
		ResponsableServiceStage respServStage = null;

		respServStage = utilServices.findResponsableServiceStageByClass(currentClass);

		// System.out.println("-------------------------------> Class: " + currentClass + " Service: " + respServStage.getNomUserSce());

		convention.setResponsableServiceStage(respServStage);

		conventionRepository.save(convention);

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateConvention = dateFormata.format(new Date());

		String subject = "Modification Convention";

		String studentMail = utilServices.findStudentMailById(idEt);
		String rssMail = responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT");

		// Local
//		String studentMail = "no_reply@esprit.tn";
//		String rssMail = "raml.benouirane@mail.com";


		String content = "Nous voulons vous informer par le présent mail que Vous avez "
				+ "modifié quelques informations concernant <strong><font color=grey>votre Convention</font></strong> "
				+ "et c'est le <font color=blue> " + dateConvention + " </font>."
				+ "<br/>Une fois validée, vous pouvez consulter "
				+ "<strong><font color=grey>votre Nouvelle Convention</font></strong> via votre espace (Menu : Mes Documents).";

		utilServices.sendMailWithCC(subject, studentMail, rssMail, content);

	}


	@GetMapping("/convention/{idEt}")
	public Integer findConventionByStudent(@PathVariable String idEt) {
		Integer result = null;
		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);

		if (convention == null) {
			// // System.out.println("1 --------- Result : Conv not null ");
			result = 0;
		} else {
			// // System.out.println("2 --------- Result : Conv null ");
			result = 1;
		}
		// // System.out.println("--------- Result = " + result);
		return result;
	}

	@GetMapping("/conventionPath/{idEt}")
	public String findPathConventionByStudent(@PathVariable String idEt) {
		String conventionPath = conventionRepository.findPathConventionByIdEt(idEt).get(0);

		// if(convention == null)
		// {
		// // // System.out.println("1 --------- Result : Conv not null ");
		// result = 0;
		// }
		// else
		// {
		// // // System.out.println("2 --------- Result : Conv null ");
		// result = 1;
		// }
		// // // System.out.println("--------- Result = " + result);

		return conventionPath;
	}

	@GetMapping("/download/{idEt}/{dateConvention}")
	public ResponseEntity downloadStudentPV(@PathVariable String idEt, @PathVariable String dateConvention)
			throws IOException {

		Optional<Convention> conv = conventionRepository.getConventionById(idEt,
				dateConvention.substring(0, 19).replace("T", " "));

		System.out.println("-----> Convention: " + conv.get().getAddress());

		Date dat = new Date();
		String studentFullName = utilServices.findStudentFullNameById(idEt);

		String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
		String studentOption = utilServices.findOptionByClass(studentClasse,
				optionRepository.listOptionsByYear("2021"));
		String studentDepartment = utilServices.findDepartmentByClassForConventionDiplome(studentClasse);

		String path = "C:/ESP-DOCS/Conventions/" + studentFullName + "-" + dat.getTime() + ".pdf";

		if (conv.isPresent()) {

			String convCodePays = "--";
			if (conv.get().getEntrepriseAccueilConvention().getPays() != null) {
				convCodePays = conv.get().getEntrepriseAccueilConvention().getPays().getLangueCode();
			}

			if (convCodePays.equalsIgnoreCase("TN")) {
				ConventionTN_PDF convTN = new ConventionTN_PDF(conv, path, studentFullName, studentOption,
						studentDepartment);
				System.out.println("----------------- TN");
			}

			if (convCodePays.equalsIgnoreCase("EN") || convCodePays.equalsIgnoreCase("--")) {
				ConventionEN_PDF convEN = new ConventionEN_PDF(conv, path, studentFullName, studentOption,
						studentDepartment);
				System.out.println("----------------- EN");
			}
			if (convCodePays.equalsIgnoreCase("FR")) {

				StudentConvFRDto scf = utilServices.findStudentConvFRDtoById(idEt);

				ConventionFR_PDF convFR = new ConventionFR_PDF(conv, path, scf, studentOption, studentDepartment);
				System.out.println("----------------- FR");
			}

			conv.get().setPathConvention(path);
			conventionRepository.save(conv.get());

		}

		String fp = conv.get().getPathConvention();
		System.out.println("----------------- ############################################# SARS: " + fp);
		File file = new File(fp);
		String fileName = fp.substring(fp.lastIndexOf("/") + 1, fp.length());

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		// To Got Name Of File With Synchro
		header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		Path patha = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(patha));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

	}

	@GetMapping("/dateConventionDto/{idEt}")
	public ConventionDto findDateConventionDtoByStudent(@PathVariable String idEt) {
		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);

		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestampToString = dateFormata.format(convention.getConventionPK().getDateConvention());

		ConventionDto cd = new ConventionDto(timestampToString);

		return cd;
	}

	@GetMapping("/conventionDateStatus/{idEt}")
	public ConventionDateStatusDto findConventionDateStatusDtoByStudent(@PathVariable String idEt) {
		System.out.println("--------> 1: "
				+ conventionRepository.findConventionDateStatusDtoByIdEt(idEt).get(0).getDateConvention());
		System.out.println(
				"--------> 2: " + conventionRepository.findConventionDateStatusDtoByIdEt(idEt).get(0).getTraiter());
		return conventionRepository.findConventionDateStatusDtoByIdEt(idEt).get(0);
	}

	@GetMapping("/conventionDateAndEtat/{idEt}")
	public String findConventionDateAndEtat(@PathVariable String idEt) {
		String convDateAndEtat = conventionRepository.findDateAndEtatConventionByIdEt(idEt).get(0);
		String dateConv = convDateAndEtat.substring(0, convDateAndEtat.lastIndexOf("$"));
		String codeEtatConv = convDateAndEtat.substring(convDateAndEtat.lastIndexOf("$") + 1);

		String etatConv = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("100", codeEtatConv);

		String conventionDateAndEtat = dateConv + "$" + etatConv;
		return conventionDateAndEtat;
	}

	@GetMapping("/dateDebutConventionByStudent/{idEt}")
	public Date findDateDebutConventionByStudent(@PathVariable String idEt) {
		return conventionRepository.findDateDebutConventionByStudent(idEt).get(0);
	}

	@GetMapping("/findDepotReportsDateEtatByIdEt/{idEt}")
	public FichePFEDateDepotReportsEtatReportsDto findDepotReportsDateEtatByIdEt(@PathVariable String idEt) {
		return fichePFERepository.findDepotReportsDateEtatByIdEt(idEt).get(0);
	}

	@GetMapping("/conventionDto/{idEt}")
	public ConventionDto findConventionDtoByStudent(@PathVariable String idEt) {
		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);

		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestampToString = dateFormata.format(convention.getConventionPK().getDateConvention());

		EntrepriseAccueil ea = convention.getEntrepriseAccueilConvention();
		Pays p = ea.getPays();
		SecteurEntreprise se = ea.getSecteurEntreprise();

		PaysDto pDto = null;
		SectorEntrepriseDto seDto = null;
		if (se != null) {
			seDto = new SectorEntrepriseDto(se.getIdSecteur(), se.getLibelleSecteur());
			// // System.out.println("-----------------------> Sector NULL");
		}

		if (p != null) {
			pDto = new PaysDto(p.getCodePays(), p.getNomPays(), p.getCodePays());
			// // System.out.println("-----------------------> Pays NULL");
		}

		// List<EncadrantEntreprise> encadrantEntreprises =
		// convention.getEncadrantEntreprises();
		List<ResponsibleDto> lrds = new ArrayList<ResponsibleDto>();
		// for(EncadrantEntreprise ee : encadrantEntreprises)
		// {
		// ResponsibleDto rd = new ResponsibleDto(ee.getFirstName(), ee.getLastName(),
		// ee.getNumTelephone(), ee.getEmail());
		// lrds.add(rd);
		// }

		// CompanyDto companyDto = new CompanyDto();
		// if(seDto == null)
		// {
		// companyDto = new CompanyDto(ea.getIdEntreprise(), ea.getDesignation(),
		// ea.getAddressMail(), ea.getAddress(), ea.getTelephone(), ea.getFax(),
		// ea.getSiegeSocial(), pDto, lrds);
		// }
		CompanyDto companyDto = new CompanyDto(ea.getIdEntreprise(), ea.getDesignation(), ea.getAddressMail(),
				ea.getAddress(), ea.getTelephone(), ea.getFax(), ea.getSiegeSocial(), pDto, seDto, lrds);

		ConventionDto conventionDto = new ConventionDto(timestampToString, convention.getDateDebut(),
				convention.getDateFin(),
				// "FILLBLANKS",
				companyDto, convention.getMail(), convention.getResponsable(), convention.getAddress(),
				convention.getTelephone(), convention.getTraiter());

		// // System.out.println("-----------------------> Societe Convention: " +
		// convention.getAddress());

		return conventionDto;
	}

	@GetMapping("/findEntrepriseLabelByFichePFE/{idEt}")
	public String findEntrepriseLabelAndFichePFE(@PathVariable String idEt) {
		return fichePFERepository.findCompanyNameByStudent(idEt).get(0);
	}

	@GetMapping("/companyDtoByStudent/{idEt}")
	public CompanyDto findCompanyByStudent(@PathVariable String idEt) {
		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);

		EntrepriseAccueil ea = convention.getEntrepriseAccueilConvention();
		Pays p = ea.getPays();
		SecteurEntreprise se = ea.getSecteurEntreprise();

		PaysDto pDto = null;
		SectorEntrepriseDto seDto = null;
		if (se != null) {
			seDto = new SectorEntrepriseDto(se.getIdSecteur(), se.getLibelleSecteur());
		}

		if (p != null) {
			pDto = new PaysDto(p.getCodePays(), p.getNomPays(), p.getCodePays());
		}

		// List<EncadrantEntreprise> encadrantEntreprises =
		// convention.getEncadrantEntreprises();
		List<ResponsibleDto> lrds = new ArrayList<ResponsibleDto>();
		// for(EncadrantEntreprise ee : encadrantEntreprises)
		// {
		// ResponsibleDto rd = new ResponsibleDto(ee.getFirstName(), ee.getLastName(),
		// ee.getNumTelephone(), ee.getEmail());
		// lrds.add(rd);
		// }

		CompanyDto companyDto = new CompanyDto(ea.getIdEntreprise(), ea.getDesignation(), ea.getAddressMail(),
				ea.getAddress(), ea.getTelephone(), ea.getFax(), ea.getSiegeSocial(), pDto, seDto, lrds);

		return companyDto;
	}

	/******************************************************
	 * Avenant
	 *
	 * @throws UnsupportedEncodingException
	 ******************************************************/
	@PostMapping("/addAvenant/{idEt}")
	public void addAvenant(@Valid @RequestBody AddAvenantRequest addAvenantRequest, @PathVariable String idEt)
			throws UnsupportedEncodingException {
		// // System.out.println("---------------- add Avenant for Etudiant: " + idEt);

		System.out.println("-----> getResponsableEntreprise: " + addAvenantRequest.getResponsableEntreprise() + " - "
				+ utilServices.decodeEncodedValue(addAvenantRequest.getResponsableEntreprise()));
		System.out.println("-----> getQualiteResponsable: " + addAvenantRequest.getQualiteResponsable() + " - "
				+ utilServices.decodeEncodedValue(addAvenantRequest.getQualiteResponsable()));
		System.out.println("-----> getNumSiren: " + addAvenantRequest.getNumSiren() + " - "
				+ utilServices.decodeEncodedValue(addAvenantRequest.getNumSiren()));

		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);

		// // System.out.println("-------------------> Date Convention: " +
		// convention.getConventionPK().getDateConvention());
		AvenantPK avenantPK = new AvenantPK(convention.getConventionPK(), new Timestamp(System.currentTimeMillis()));

		Avenant avenant = new Avenant(avenantPK, addAvenantRequest.getDateSignatureConvention(),
				addAvenantRequest.getDateDebut(), addAvenantRequest.getDateFin(), false,
				utilServices.decodeEncodedValue(addAvenantRequest.getResponsableEntreprise()),
				utilServices.decodeEncodedValue(addAvenantRequest.getQualiteResponsable()),
				utilServices.decodeEncodedValue(addAvenantRequest.getNumSiren()));
		avenantRepository.save(avenant);

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateAvenant = dateFormata.format(new Date());

		String subject = "Demande Avenant";

		// String receiver = "saria.essid@esprit.tn";
		String receiver = utilServices.findStudentMailById(idEt); // DEPLOY_SERVER

		String content = "Nous voulons vous informer par le présent mail que vous avez envoyé une "
				+ "<strong><font color=grey> Demande d'Avenant </font></strong> et c'est " + "le <font color=red> "
				+ dateAvenant + " </font>.<br/>" + "Merci de patienter jusqu'à le traitement de ta requête.";

		utilServices.sendMail(subject, receiver, content);

		// // System.out.println("----------> Date Signature Convention: " +
		// addAvenantRequest.getDateSignatureConvention());
		// // System.out.println("----------> Date Debut: " +
		// addAvenantRequest.getDateDebut());
		// // System.out.println("----------> Date Fin: " +
		// addAvenantRequest.getDateFin());
		// // System.out.println("----------> Num Siren: " +
		// addAvenantRequest.getNumSiren());
		// // System.out.println("----------> Qualite Responsable: " +
		// addAvenantRequest.getQualiteResponsable());
		// // System.out.println("----------> Responsable Entreprise: " +
		// addAvenantRequest.getResponsableEntreprise());

	}

	// @GetMapping("/avenantDateAvDateDebutStageStatus/{idEt}")
	// public ResponseEntity<?>
	// findAvenantDateAvDateDebutStageStatusByIdEt(@PathVariable String idEt)
	// {
	// Convention conv = conventionRepository.findConventionByIdEt(idEt).get(0);
	// Avenant av = conv.getAvenants().get(0)
	// return
	// ResponseEntity.ok(conventionRepository.findConventionDateConvDateDebutStageStatusByIdEt(idEt));
	// }

	@GetMapping("/avenantDateAvDateDebutStageStatus/{idEt}")
	public ResponseEntity<?> findAvenantDateAvDateDebutStageStatusByStudentAndDateConv(@PathVariable String idEt) {
		System.out.println("----------------------------- LOL: 0 - " + idEt);
		// System.out.println("----------------------------- LOL: 1 - " +
		// conventionRepository.findDateConventionByIdEt(idEt).get(0));
		List<AvenantDateAvDateDebutStageStatusDto> avDtos = avenantRepository
				.findAvenantDateAvDateDebutStageStatusDtoByStudentAndConv(idEt,
						conventionRepository.findDateConventionByIdEt(idEt).get(0));
		// System.out.println("----------------------------- LOL: 2 - " +
		// avDto.getDateAvenant());
		// System.out.println("----------------------------- LOL: 3 - " +
		// avDto.getDateDebutStage());
		// System.out.println("----------------------------- LOL: 4 - " +
		// avDto.getTraiter());

		return ResponseEntity.ok(avDtos);
	}

	@GetMapping("/avenantDto/{idEt}/{dateConvention}")
	public AvenantDto findAvenantDtoByStudent(@PathVariable String idEt, @PathVariable String dateConvention) {
		System.out.println("----------------------------- LOL: " + idEt + " - " + dateConvention);
		Avenant avenant = avenantRepository.findAvenantByIdEtAndDateConvention(idEt, dateConvention).get(0);

		System.out.println("----------------------------- AVN: " + idEt + " - " + dateConvention);

		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestampToString = dateFormata.format(avenant.getAvenantPK().getConventionPK().getDateConvention());

		AvenantDto avenantDto = new AvenantDto(avenant.getAvenantPK().getDateAvenant(), avenant.getDateDebut(),
				avenant.getDateFin(), avenant.getTraiter(), avenant.getResponsableEntreprise(),
				avenant.getQualiteResponsable(), timestampToString);

		System.out.println("---------------------------------------> Avenant Date Convention: "
				+ avenant.getAvenantPK().getConventionPK().getDateConvention());

		return avenantDto;
	}

	@GetMapping("/avenantDto/{idEt}")
	public AvenantDto findAvenantDtoByStudent(@PathVariable String idEt) {
		Avenant avenant = avenantRepository.findAvenantByIdEt(idEt).get(0);

		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestampToString = dateFormata.format(avenant.getAvenantPK().getConventionPK().getDateConvention());

		AvenantDto avenantDto = new AvenantDto(avenant.getAvenantPK().getDateAvenant(), avenant.getDateDebut(),
				avenant.getDateFin(), avenant.getTraiter(), avenant.getResponsableEntreprise(),
				avenant.getQualiteResponsable(), timestampToString);

		// // System.out.println("---------------------------------------> Avenant Date
		// Convention: " +
		// avenant.getAvenantPK().getConventionPK().getDateConvention());

		return avenantDto;
	}

	/******************************************************
	 * Company
	 ******************************************************/

	@PostMapping("/addProjectCompany")
	public CompanyDto addProjectCompany(@Valid @RequestBody AddCompanyRequest addCompanyRequest)
			throws UnsupportedEncodingException {
		// // System.out.println("---------------- add a new Company: " +
		// addCompanyRequest.getDesignation() + " - "
		// + addCompanyRequest.getLol() + " - "
		// + addCompanyRequest.getAddressMail() + " - "
		// + addCompanyRequest.getTelephone()
		// + addCompanyRequest.getFax() + " - "
		// + addCompanyRequest.getPaysLibelle() + " - "
		// + addCompanyRequest.getSectorEntrepriseLibelle() + " - "
		// + addCompanyRequest.getSiegeSocial());

		System.out.println("-----------> Designation: " + addCompanyRequest.getDesignation() + " - "
				+ utilServices.decodeEncodedValue(addCompanyRequest.getDesignation()));
		System.out.println("-----------> Lol: " + addCompanyRequest.getLol() + " - "
				+ utilServices.decodeEncodedValue(addCompanyRequest.getLol()));
		System.out.println("-----------> AddressMail: " + addCompanyRequest.getAddressMail() + " - "
				+ utilServices.decodeEncodedValue(addCompanyRequest.getAddressMail()));
		System.out.println("-----------> Telephone: " + addCompanyRequest.getTelephone() + " - "
				+ utilServices.decodeEncodedValue(addCompanyRequest.getTelephone()));
		System.out.println("-----------> Fax: " + addCompanyRequest.getFax() + " - "
				+ utilServices.decodeEncodedValue(addCompanyRequest.getFax()));
		System.out.println("-----------> PaysLibelle: " + addCompanyRequest.getPaysLibelle() + " - "
				+ utilServices.decodeEncodedValue(addCompanyRequest.getPaysLibelle()));
		System.out.println("-----------> SectorEntrepriseLibelle: " + addCompanyRequest.getSectorEntrepriseLibelle()
				+ " - " + utilServices.decodeEncodedValue(addCompanyRequest.getSectorEntrepriseLibelle()));
		System.out.println("-----------> SiegeSocial: " + addCompanyRequest.getSiegeSocial() + " - "
				+ utilServices.decodeEncodedValue(addCompanyRequest.getSiegeSocial()));

		Pays pays = paysRepository
				.findPaysByNamePays(utilServices.decodeEncodedValue(addCompanyRequest.getPaysLibelle()));
		SecteurEntreprise secteurEntreprise = sectorEntrepriseRepository.findSecorEntrepriseByLibelleSecteur(
				utilServices.decodeEncodedValue(addCompanyRequest.getSectorEntrepriseLibelle()));

		// // // System.out.println("Sector --------------> 1. Get Sector Entity: " +
		// se.getLibelleSecteur());
		// SectorEntrepriseDto sectorEntrepriseDto = new
		// SectorEntrepriseDto(se.getIdSecteur(), se.getLibelleSecteur());

		EntrepriseAccueil entrepriseAccueil = new EntrepriseAccueil(
				utilServices.decodeEncodedValue(addCompanyRequest.getDesignation()),
				utilServices.decodeEncodedValue(addCompanyRequest.getAddressMail()),
				utilServices.decodeEncodedValue(addCompanyRequest.getLol()),
				utilServices.decodeEncodedValue(addCompanyRequest.getTelephone()),
				utilServices.decodeEncodedValue(addCompanyRequest.getFax()), pays, secteurEntreprise,
				utilServices.decodeEncodedValue(addCompanyRequest.getSiegeSocial()));

		entrepriseAccueilRepository.save(entrepriseAccueil);

		// CompanyDto companyDto = new CompanyDto(addCompanyRequest.getDesignation(),
		// addCompanyRequest.getAddressMail(), addCompanyRequest.getLol(),
		// addCompanyRequest.getTelephone(), addCompanyRequest.getFax(), pays);
		CompanyDto companyDto = new CompanyDto(entrepriseAccueil.getIdEntreprise(), entrepriseAccueil.getDesignation());

		// // System.out.println("---------------- Added Company is with id : " +
		// entrepriseAccueil.getIdEntreprise());

		return companyDto;
	}


	@PostMapping("/modifyProjectCompany")
	public CompanyDto modifyProjectCompany(@Valid @RequestBody AddCompanyRequest addCompanyRequest) throws UnsupportedEncodingException
	{
		// // System.out.println("---------------- add a new Company: " + addCompanyRequest.getDesignation() + " - "
//                												  + addCompanyRequest.getLol() + " - "
//	                                                              + addCompanyRequest.getAddressMail() + " - "
//	                                                              + addCompanyRequest.getTelephone()
//	                                                              + addCompanyRequest.getFax() + " - "
//	                                                              + addCompanyRequest.getPaysLibelle() + " - "
//	                                                              + addCompanyRequest.getSectorEntrepriseLibelle() + " - "
//	                                                              + addCompanyRequest.getSiegeSocial());

		System.out.println("-----------> Designation: " + addCompanyRequest.getDesignation() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getDesignation()));
		System.out.println("-----------> Lol: " + addCompanyRequest.getLol() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getLol()));
		System.out.println("-----------> AddressMail: " + addCompanyRequest.getAddressMail() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getAddressMail()));
		System.out.println("-----------> Telephone: " + addCompanyRequest.getTelephone() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getTelephone()));
		System.out.println("-----------> Fax: " + addCompanyRequest.getFax() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getFax()));
		System.out.println("-----------> PaysLibelle: " + addCompanyRequest.getPaysLibelle() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getPaysLibelle()));
		System.out.println("-----------> SectorEntrepriseLibelle: " + addCompanyRequest.getSectorEntrepriseLibelle() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getSectorEntrepriseLibelle()));
		System.out.println("-----------> SiegeSocial: " + addCompanyRequest.getSiegeSocial() + " - " + utilServices.decodeEncodedValue(addCompanyRequest.getSiegeSocial()));



		Pays pays = paysRepository.findPaysByNamePays(utilServices.decodeEncodedValue(addCompanyRequest.getPaysLibelle()));
		SecteurEntreprise secteurEntreprise = sectorEntrepriseRepository.findSecorEntrepriseByLibelleSecteur(utilServices.decodeEncodedValue(addCompanyRequest.getSectorEntrepriseLibelle()));

		System.out.println("Sector --------------> 1. Get Sector Entity: " + pays.getIdPays());
		// SectorEntrepriseDto sectorEntrepriseDto = new SectorEntrepriseDto(se.getIdSecteur(), se.getLibelleSecteur());

		EntrepriseAccueil entrepriseAccueil = entrepriseAccueilRepository.findByDesignation(utilServices.decodeEncodedValue(addCompanyRequest.getDesignation()));

		entrepriseAccueil.setAddressMail(utilServices.decodeEncodedValue(addCompanyRequest.getAddressMail()));
		entrepriseAccueil.setAddress(utilServices.decodeEncodedValue(addCompanyRequest.getLol()));
		entrepriseAccueil.setTelephone(utilServices.decodeEncodedValue(addCompanyRequest.getTelephone()));
		entrepriseAccueil.setFax(utilServices.decodeEncodedValue(addCompanyRequest.getFax()));
		entrepriseAccueil.setPays(pays);
		entrepriseAccueil.setSecteurEntreprise(secteurEntreprise);
		entrepriseAccueil.setSiegeSocial(utilServices.decodeEncodedValue(addCompanyRequest.getSiegeSocial()));

		entrepriseAccueilRepository.save(entrepriseAccueil);

		// CompanyDto companyDto = new CompanyDto(addCompanyRequest.getDesignation(), addCompanyRequest.getAddressMail(), addCompanyRequest.getLol(), addCompanyRequest.getTelephone(), addCompanyRequest.getFax(), pays);
		CompanyDto companyDto = new CompanyDto(entrepriseAccueil.getIdEntreprise(), entrepriseAccueil.getDesignation());

		// // System.out.println("---------------- Added Company is with id : " + entrepriseAccueil.getIdEntreprise());

		return companyDto;
	}


	@GetMapping("/companyByName/{designation}")
	public CompanyDto findFicheSocieteByName(@PathVariable String designation) {
		// // System.out.println("---------------- Get Company with designation : " +
		// designation);

		// Entre ficheSociete = ficheSocieteRepository.findByNomSociete(name);
		// CompanyDto companyDto = new CompanyDto(ficheSociete.getNomSociete(),
		// ficheSociete.getEmailSociete(), ficheSociete.getAdresseSociete(),
		// ficheSociete.getNumTelephone());

		EntrepriseAccueil entrepriseAccueil = entrepriseAccueilRepository
				.findByDesignation(utilServices.decodeEncodedValue(designation));

		Pays pays = entrepriseAccueil.getPays();
		SecteurEntreprise secteurEntreprise = entrepriseAccueil.getSecteurEntreprise();

		PaysDto paysDto = null;
		SectorEntrepriseDto sectorEntrepriseDto = null;

		if (pays != null) {
			paysDto = new PaysDto(pays.getCodePays(), pays.getNomPays(), pays.getPhoneCode());
		}

		if (secteurEntreprise != null) {
			sectorEntrepriseDto = new SectorEntrepriseDto(secteurEntreprise.getIdSecteur(),
					secteurEntreprise.getLibelleSecteur());
		}

		CompanyDto companyDto = new CompanyDto(entrepriseAccueil.getIdEntreprise(), entrepriseAccueil.getDesignation(),
				entrepriseAccueil.getAddressMail(), entrepriseAccueil.getAddress(), entrepriseAccueil.getTelephone(),
				entrepriseAccueil.getFax(), entrepriseAccueil.getSiegeSocial(), paysDto, sectorEntrepriseDto);

		return companyDto;
	}

	@GetMapping("/companyById/{idEntreprise}")
	public CompanyDto findFicheSocieteById(@PathVariable String idEntreprise) {
		Integer idEntrp = Integer.parseInt(idEntreprise);
		// // System.out.println("---------------- Get Company with designation : " +
		// idEntrp);

		// Entre ficheSociete = ficheSocieteRepository.findByNomSociete(name);
		// CompanyDto companyDto = new CompanyDto(ficheSociete.getNomSociete(),
		// ficheSociete.getEmailSociete(), ficheSociete.getAdresseSociete(),
		// ficheSociete.getNumTelephone());

		EntrepriseAccueil entrepriseAccueil = entrepriseAccueilRepository.findByIdEntreprise(idEntrp);

		Pays pays = entrepriseAccueil.getPays();
		SecteurEntreprise secteurEntreprise = entrepriseAccueil.getSecteurEntreprise();

		PaysDto paysDto = null;
		SectorEntrepriseDto sectorEntrepriseDto = null;

		if (pays != null) {
			paysDto = new PaysDto(pays.getCodePays(), pays.getNomPays(), pays.getPhoneCode());
		}

		if (secteurEntreprise != null) {
			sectorEntrepriseDto = new SectorEntrepriseDto(secteurEntreprise.getIdSecteur(),
					secteurEntreprise.getLibelleSecteur());
		}

		CompanyDto companyDto = new CompanyDto(entrepriseAccueil.getIdEntreprise(), entrepriseAccueil.getDesignation(),
				entrepriseAccueil.getAddressMail(), entrepriseAccueil.getAddress(), entrepriseAccueil.getTelephone(),
				entrepriseAccueil.getFax(), entrepriseAccueil.getSiegeSocial(), paysDto, sectorEntrepriseDto);

		return companyDto;
	}

	@GetMapping("/allCompanies")
	public List<String> findAllCompanies() {
		List<String> lfss = entrepriseAccueilRepository.findAllEntrepriseAccueil();
		// // System.out.println("--------- Size All Companies = " + lfss.size());
		return lfss;
	}

	/******************************************************
	 * Sector Entreprise
	 ******************************************************/

	@GetMapping("/sectors")
	public List<String> findAllSectors() {
		List<String> lses = sectorEntrepriseRepository.findAllLibelleSectorsEntreprise();
		// // System.out.println("------------------------------- Get All Sectors
		// Entreprise: " + lses.size());
		return lses;
	}

	@GetMapping("/sectors/{libelleSecteur}")
	public SectorEntrepriseDto findPaysByLibelleSecteur(@PathVariable String libelleSecteur) {
		// // System.out.println("Sector --------------> 0. Get Sector Entity: " +
		// libelleSecteur);
		SecteurEntreprise se = sectorEntrepriseRepository.findSecorEntrepriseByLibelleSecteur(libelleSecteur);
		// // System.out.println("Sector --------------> 1. Get Sector Entity: " +
		// se.getLibelleSecteur());
		SectorEntrepriseDto sectorEntrepriseDto = new SectorEntrepriseDto(se.getIdSecteur(), se.getLibelleSecteur());
		return sectorEntrepriseDto;
	}

	/******************************************************
	 * Pays
	 ******************************************************/

	@GetMapping("/pays")
	public List<String> findAllPays() {
		// // System.out.println("Get All Pays");
		List<String> pays = paysRepository.findAllLibellePays();

		// // System.out.println("Pays ----sdqsd----------> 1. Get Pays Entity: " +
		// pays.size());
		return pays;
	}

	@GetMapping("/pays/{nomPays}")
	public PaysDto findPaysByNomPays(@PathVariable String nomPays) {
		// // System.out.println("Pays --------------> 0. Get Pays Entity: " + nomPays);
		Pays pays = paysRepository.findPaysByNamePays(nomPays);

		// // System.out.println("Pays --------------> 1. Get Pays Entity: " +
		// pays.getCodePays());
		PaysDto teacherDto = new PaysDto(pays.getCodePays(), pays.getNomPays(), pays.getPhoneCode());
		return teacherDto;
	}

	@GetMapping("/pays/states/{nomPays}")
	public List<String> findStatesByPaysName(@PathVariable String nomPays) {
		// // System.out.println("States --------------> 0. Get States by nomPays: " +
		// nomPays);

		Pays pays = paysRepository.findPaysByNamePays(nomPays);
		// // System.out.println("States --------------> 1. States Size: " +
		// pays.getIdPays());

		List<String> states = paysRepository.findStatesByIdPays(pays.getIdPays());
		// // System.out.println("States --------------> 2. States Size: " +
		// states.size());

		// for(String s : states)
		// {
		// // // System.out.println("UNIT --------------> 3. State: " + s);
		// }

		return states;
	}

	/******************************************************
	 * Plan Travail
	 ******************************************************/

	@PostMapping("/addProjectTechnology/{name}")
	public Technologie addProjectTechnology(@PathVariable String name) {
		System.out.println("---------------- add a new Technologie 1: " + name);
		System.out.println("---------------- add a new Technologie 2: " + utilServices.decodeEncodedValue(name));

		Technologie technologie = new Technologie(utilServices.decodeEncodedValue(name));
		technologie.setEtat("A");
		;
		technologyRepository.save(technologie);

		// // System.out.println("---------- The StudentCJ who currently added the
		// convention is with code : " + technologie.getIdTechnologie());

		return technologie;
	}

	@PostMapping("/updateProjectTechnology/{oldTechnologyName}/{newTechnologyName}")
	public int updateProjectTechnology(@PathVariable String oldTechnologyName, @PathVariable String newTechnologyName) {
		System.out.println("--------1-------- update a Project Technologie from - " + oldTechnologyName + " - to - "
				+ newTechnologyName);
		System.out.println("--------2-------- update a Project Technologie from - "
				+ utilServices.decodeEncodedValue(oldTechnologyName) + " - to - "
				+ utilServices.decodeEncodedValue(newTechnologyName));

		Technologie technologie = technologyRepository.findByName(utilServices.decodeEncodedValue(oldTechnologyName));
		technologie.setName(utilServices.decodeEncodedValue(newTechnologyName));
		technologyRepository.save(technologie);

		// // System.out.println("---------- Update done for technology : " +
		// technologie.getIdTechnologie());
		return technologie.getIdTechnologie();
	}

	@DeleteMapping("/projectTechnologies/{name}")
	public void deleteProjectTechnology(@PathVariable("name") String name) {
		System.out.println("Delete Technology with id 1: " + name);
		System.out.println("Delete Technology with id 2: " + utilServices.decodeEncodedValue(name));
		technologyRepository.deleteTechnologyByName(utilServices.decodeEncodedValue(name));
	}

	public void sars(List<String> listOfProblematics, String idEt, String timestampToString1, FichePFE fichePFE) {

		for (String pi : listOfProblematics) {
			List<String> lfs = new ArrayList<String>();
			lfs = problematicRepository.findAllProblematicsByStudentAndDateDepotFiche(idEt, timestampToString1);
			Integer idProblematic = lfs.size() + 1;

			System.out.println("========HERE===========> lfs: " + lfs);

			Problematique problematic = new Problematique();
			ProblematiquePK problematicPK = new ProblematiquePK(fichePFE.getIdFichePFE(), idProblematic);

			System.out.println("===============HERE=*************===> TRY: " + problematicPK.getNumOrdre() + " _ "
					+ problematicPK.getFichePFEPK().getDateDepotFiche());
			problematic.setProblematicPK(problematicPK);
			problematic.setName(utilServices.decodeEncodedValue(pi));
			problematic.setFichePFEProblematic(fichePFE);
			System.out.println("======HERE==========********looooooooooooooooooooooool*****===> TRY: "
					+ problematicPK.getNumOrdre() + " _ " + problematicPK.getFichePFEPK().getDateDepotFiche() + " - "
					+ "lol" + problematicPK.getNumOrdre());
			problematicRepository.save(problematic);

			System.out.println("===******===> SAVED: " + problematicPK.getNumOrdre());

		}
	}

	@GetMapping("/probs")
	public void lol() throws UnsupportedEncodingException {

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent("183JFT0955").get(0);

		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestampToString1 = dateFormata.format(fichePFE.getIdFichePFE().getDateDepotFiche());

		List<String> listOfProblematics = new ArrayList<String>();
		listOfProblematics.add("aa");
		listOfProblematics.add("zz");
		listOfProblematics.add("ee");

		for (String pi : listOfProblematics) {
			List<String> lfs = new ArrayList<String>();
			lfs = problematicRepository.findAllProblematicsByStudentAndDateDepotFiche("183JFT0955", timestampToString1);
			Integer idProblematic = lfs.size() + 1;

			System.out.println("========gggg===========> lfs: " + lfs);

			Problematique problematic = new Problematique();
			ProblematiquePK problematicPK = new ProblematiquePK(fichePFE.getIdFichePFE(), idProblematic);

			System.out.println("===============hhhhh=*************===> TRY: " + problematicPK.getNumOrdre() + " _ "
					+ problematicPK.getFichePFEPK().getDateDepotFiche());
			problematic.setProblematicPK(problematicPK);
			problematic.setName(pi); // utilServices.decodeEncodedValue(pi)
			problematic.setFichePFEProblematic(fichePFE);
			System.out.println("================*************===> TRY: " + problematicPK.getNumOrdre() + " _ "
					+ problematicPK.getFichePFEPK().getDateDepotFiche() + " - " + "lol" + problematicPK.getNumOrdre());
			// problematicRepository.saveAndFlush(problematic);
			problematicRepository.save(problematic);

			System.out.println("===******===> SAVED: " + problematicPK.getNumOrdre());

		}
	}

	@GetMapping("/allTechnologies")
	public List<String> findAllTechnologies() {
		List<String> lts = technologyRepository.findAllTechnologiesByTypeAndStatus();
		// // System.out.println("--------- Size All Technologies {Etat = 1} = " +
		// lts.size());

		return lts;
	}

	@GetMapping("/deleteCompanySupervisorByMail/{email}")
	public void deleteCompanySupervisorByMail(@PathVariable String email) {
		encadrantEntrepriseRepository.deleteEncadrantSocieteByEmail(email);
	}

	@PostMapping("/updateStudent/{idEt}/{otherEmailet}/{otherAdresseet}/{othertelet}")
	public void updateStudent(@PathVariable String idEt, @PathVariable String otherEmailet,
							  @PathVariable String otherAdresseet, @PathVariable String othertelet) {
		List<String> activatedYears = settingsRepository.findActivatedYears();
		List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(idEt, activatedYears);
		List<String> studentsCS = studentRepository.findStudentsFullNameCS(idEt, activatedYears);

		// System.out.println("----------------------------------> StudentCJ FullName
		// CJ: " + studentsCJ + " --- CS: " + studentsCS);

		if (!studentsCJandALT.isEmpty()) {
			// // System.out.println("---------------- update F-PFE for User: " + idEt + " -
			// " + otherEmailet + " - " + otherAdresseet + " - " + othertelet);
			StudentCJ student = studentRepository.findByIdEt(idEt)
					.orElseThrow(() -> new RuntimeException("StudentCJ : Verify your credentials !."));

			System.out.println("---------------- Other Mail 1: " + otherEmailet + " - "
					+ utilServices.decodeEncodedValue(otherEmailet));
			System.out.println("---------------- Other @ 2: " + otherAdresseet + " - "
					+ utilServices.decodeEncodedValue(otherAdresseet));

			if (!otherEmailet.equalsIgnoreCase("--")) {
				student.setEmailet(utilServices.decodeEncodedValue(otherEmailet));
				// // System.out.println("---------------- Other Mail");
			}
			if (!otherAdresseet.equalsIgnoreCase("--")) {
				student.setAdresseet(utilServices.decodeEncodedValue(otherAdresseet));
				// // System.out.println("---------------- Adress");
			}

			if (!othertelet.equalsIgnoreCase("--")) {
				student.setTelet(othertelet);
				// // System.out.println("---------------- Num Tel");
			}

			studentRepository.save(student);

		}

		if (!studentsCS.isEmpty()) {
			// // System.out.println("---------------- update F-PFE for User: " + idEt + " -
			// " + otherEmailet + " - " + otherAdresseet + " - " + othertelet);
			StudentCS studentCS = studentCSRepository.findByIdEt(idEt)
					.orElseThrow(() -> new RuntimeException("StudentCJ : Verify your credentials !."));

			System.out.println("---------------- Other Mail 1: " + otherEmailet + " - "
					+ utilServices.decodeEncodedValue(otherEmailet));
			System.out.println("---------------- Other @ 2: " + otherAdresseet + " - "
					+ utilServices.decodeEncodedValue(otherAdresseet));

			if (!otherEmailet.equalsIgnoreCase("--")) {
				studentCS.setEmailet(utilServices.decodeEncodedValue(otherEmailet));
				// // System.out.println("---------------- Other Mail");
			}
			if (!otherAdresseet.equalsIgnoreCase("--")) {
				studentCS.setAdresseet(utilServices.decodeEncodedValue(otherAdresseet));
				// // System.out.println("---------------- Adress");
			}

			if (!othertelet.equalsIgnoreCase("--")) {
				studentCS.setTelet(othertelet);
				// // System.out.println("---------------- Num Tel");
			}

			studentCSRepository.save(studentCS);

		}
	}

	/******************************************************
	 * Supervisor
	 ******************************************************/
	// @PostMapping("/addProjectSupervisor/{idEntreprise}/{projectSupervisorFirstName}/{projectSupervisorLastName}/{projectSupervisorPhoneNumber}/{projectSupervisorEmail}")
	// public void addCompanySupervisor(@PathVariable String idEntreprise,
	// @PathVariable String projectSupervisorFirstName, @PathVariable String
	// projectSupervisorLastName, @PathVariable String projectSupervisorPhoneNumber,
	// @PathVariable String projectSupervisorEmail)
	@PostMapping("/addProjectSupervisor/{idEt}/{projectSupervisorFirstName}/{projectSupervisorLastName}/{projectSupervisorPhoneNumber}/{projectSupervisorEmail}")
	public void addCompanySupervisor(@PathVariable String idEt, @PathVariable String projectSupervisorFirstName,
									 @PathVariable String projectSupervisorLastName, @PathVariable String projectSupervisorPhoneNumber,
									 @PathVariable String projectSupervisorEmail) {
		System.out.println("---------------- add a new Supervisor: " + projectSupervisorFirstName + " - "
				+ projectSupervisorLastName + " - " + projectSupervisorPhoneNumber + " - " + projectSupervisorEmail
				+ " - " + idEt);
		// Integer idEntrp = Integer.parseInt(idEntreprise);
		/*
		 * private String idResponsible; private String pwdResponsible; private String
		 * firstName; private String lastName; private String numTelephone; private
		 * String email; private String address; private String fonction;
		 */

		// Responsible companySupervisor = new Responsible(projectSupervisorFirstName,
		// projectSupervisorLastName, projectSupervisorPhoneNumber,
		// projectSupervisorEmail);

		System.out.println("---------------- Add 1: " + projectSupervisorFirstName + " - "
				+ utilServices.decodeEncodedValue(projectSupervisorFirstName));
		System.out.println("---------------- Add 2: " + projectSupervisorLastName + " - "
				+ utilServices.decodeEncodedValue(projectSupervisorLastName));
		System.out.println("---------------- Add 3: " + projectSupervisorEmail + " - "
				+ utilServices.decodeEncodedValue(projectSupervisorEmail));

		List<String> lrs = new ArrayList<String>();

		lrs = responsibleRepository.findAllSupervisors();
		System.out.println("---------------- lrs: " + lrs.size());
		Integer idRespInt = 1;

		if (!lrs.isEmpty()) {
			Integer maxNbr = 0;
			List<Integer> lis = new ArrayList<Integer>();
			for (String s : lrs) {
				// COMPANY-SUPERV-73
				maxNbr = Integer.valueOf(s.substring(15));
				System.out.println("---------------- maxNbr: " + maxNbr);
				lis.add(maxNbr);
				Collections.sort(lis);
			}

			idRespInt = lis.get(lis.size() - 1) + 1;
			System.out.println("---------------- idRespInt: " + idRespInt);
		}

		String idRespStr = "COMPANY-SUPERV-" + idRespInt.toString();

		EncadrantEntreprisePK eePK = new EncadrantEntreprisePK(fichePFERepository.findFichePFEPKByStudent(idEt).get(0),
				idRespStr);
		EncadrantEntreprise companySupervisor = new EncadrantEntreprise(eePK,
				utilServices.decodeEncodedValue(projectSupervisorFirstName),
				utilServices.decodeEncodedValue(projectSupervisorLastName), projectSupervisorPhoneNumber,
				utilServices.decodeEncodedValue(projectSupervisorEmail));
		responsibleRepository.save(companySupervisor);

		System.out.println("-------ADD--------- idRespInt: " + idRespInt + " --> " + idRespStr);

	}

	@DeleteMapping("/companySupervisors/{email}")
	public void deleteCompanySupervisor(@PathVariable("email") String email) {
		// System.out.println("Delete CompanySupervisor with email " + email);
		responsibleRepository.deleteEncadrantSocieteByEmail(email);
		;
	}

	@PostMapping("/updateCompanySupervisor/{idEt}/{oldSupervMail}/{newSupervFirstName}/{newSupervLastName}/{newSupervPhoneNumber}/{newSupervEmail}")
	public ResponseEntity<?> updateCompanySupervisor(@PathVariable String idEt, @PathVariable String oldSupervMail,
													 @PathVariable String newSupervFirstName, @PathVariable String newSupervLastName,
													 @PathVariable String newSupervPhoneNumber, @PathVariable String newSupervEmail) {
		System.out.println("---------------- update Company Supervisor Profile with mail: " + oldSupervMail + " - "
				+ utilServices.decodeEncodedValue(oldSupervMail));
		System.out.println("---------------- update 1: " + newSupervFirstName + " - "
				+ utilServices.decodeEncodedValue(newSupervFirstName));
		System.out.println("---------------- update 2: " + newSupervLastName + " - "
				+ utilServices.decodeEncodedValue(newSupervLastName));
		System.out.println("---------------- update 3: " + newSupervEmail + " - "
				+ utilServices.decodeEncodedValue(newSupervEmail));

		EncadrantEntreprise encadrantSocieteToBeUpdated = responsibleRepository
				.findESByEmailAndStudent(utilServices.decodeEncodedValue(oldSupervMail), idEt);
		encadrantSocieteToBeUpdated.setFirstName(utilServices.decodeEncodedValue(newSupervFirstName));
		encadrantSocieteToBeUpdated.setLastName(utilServices.decodeEncodedValue(newSupervLastName));
		encadrantSocieteToBeUpdated.setEmail(utilServices.decodeEncodedValue(newSupervEmail));
		encadrantSocieteToBeUpdated.setNumTelephone(newSupervPhoneNumber);
		responsibleRepository.save(encadrantSocieteToBeUpdated);

		// // System.out.println("---------- The supervisor profile is updated
		// successfully - with id : " + encadrantSocieteToBeUpdated.getEmail());

		return ResponseEntity.ok(new MessageResponse("The supervisor profile is updated successfully"));
	}

	@GetMapping("/verifyExistMailEncadrantEntreprise/{idEt}/{supervMail}")
	public Boolean verifyExistMailEncadrantEntreprise(@PathVariable String idEt, @PathVariable String supervMail) {

		Boolean verifyEEE = false;
		String studentMail = utilServices.decodeEncodedValue(supervMail);
		System.out.println("---------- idEt: " + idEt);
		System.out.println("---------- supervMail: " + studentMail + " - " + studentMail.toLowerCase());

		EncadrantEntreprise encadrantSocieteToBeUpdated = responsibleRepository
				.findESByEmailAndStudent(studentMail.toLowerCase(), idEt);

		if (encadrantSocieteToBeUpdated != null) {
			verifyEEE = true;
		}
		System.out.println("---------- RESULT: " + verifyEEE);
		return verifyEEE;
	}

	@GetMapping("/companySupervisorsByStudent/{idEt}")
	public List<ResponsibleDto> companySupervisors(@PathVariable String idEt) {
		// Convention convention =
		// conventionRepository.findConventionByIdEt(idEt).get(0);
		//
		// List<EncadrantEntreprise> lees = convention.getEncadrantEntreprises();

		List<ResponsibleDto> lrds = new ArrayList<>();
		// for(EncadrantEntreprise ee : lees)
		// {
		// ResponsibleDto rd = new ResponsibleDto(ee.getFirstName(), ee.getLastName(),
		// ee.getNumTelephone(), ee.getEmail());
		// lrds.add(rd);
		// }
		return lrds;

		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		//
		// return dateFormat.format(fichePFE.getIdFichePFE().getDateDepotFiche());
	}

	@GetMapping("/fichePFETitle/{idEt}")
	public String findFichePFETitleByStudentCode(@PathVariable String idEt) {
		String titleFichePFE = fichePFERepository.findTitleFichePFEByStudent(idEt).get(0);
		// System.out.println("----------------------------------------------------->
		// SARS: " + titleFichePFE);
		return titleFichePFE;
	}

	@GetMapping("/fichePFEPlanifySoutenance/{idEt}")
	public FichePFEDto findFichePFEPlanifySoutenance(@PathVariable String idEt) {
		// System.out.println("----------------------------------------------------->
		// SARS - 1");
		List<Object[]> lofs = fichePFERepository.findFichePFEPlanifySoutenance(idEt);
		// System.out.println("----------------------------------------------------->
		// SARS - 2");
		FichePFEDto fichePFEDto = null;
		// System.out.println("----------------------------------------------------->
		// SARS - 3");
		for (int i = 0; i < lofs.size(); i++) {
			String presidentJury = (String) lofs.get(i)[0];
			String presidentJuryEnsId = (String) lofs.get(i)[1];
			String expertEnsId = (String) lofs.get(i)[2];
			String salle = (String) lofs.get(i)[3];
			String stnDate = (String) lofs.get(i)[5];
			String stnHD = (String) lofs.get(i)[6];
			String stnHF = (String) lofs.get(i)[7];
			String etatFiche = (String) lofs.get(i)[8];

			// System.out.println("------ RETRIEVE -----> 0: " + idEt);
			// System.out.println("------ RETRIEVE -----> 1: " + presidentJury);
			// System.out.println("------ RETRIEVE -----> 2: " + presidentJuryEnsId);
			// System.out.println("------ RETRIEVE -----> 3: " + expertEnsId);
			// System.out.println("------ RETRIEVE -----> 4: " + salle);
			//
			// System.out.println("------ RETRIEVE -----> dt: " + stnDate);
			// System.out.println("------ RETRIEVE -----> hd: " + stnHD);
			// System.out.println("------ RETRIEVE -----> hf: " + stnHF);
			// System.out.println("------ RETRIEVE -----> etat: " + etatFiche);

			TeacherDtoSTN2 presidentJuryDto = null;
			Teacher presidentJuryEns = teacherRepository.findByIdEns(presidentJuryEnsId);
			if (presidentJuryEns != null) {
				presidentJuryDto = new TeacherDtoSTN2(presidentJuryEnsId, presidentJuryEns.getUp(),
						presidentJuryEns.getNomEns(), presidentJuryEns.getTypeEns(), presidentJuryEns.getMailEns(),
						presidentJuryEns.getTel1());
				// System.out.println("------ RES-PLAN -----> 2: " + presidentJuryDto.getNom());
			}

			TeacherDtoSTN2 expertDto = null;
			Teacher expertEns = teacherRepository.findByIdEns(expertEnsId);
			if (expertEns != null) {
				expertDto = new TeacherDtoSTN2(expertEnsId, expertEns.getUp(), expertEns.getNomEns(),
						expertEns.getTypeEns(), expertEns.getMailEns(), expertEns.getTel1());
				// System.out.println("------ RES-PLAN -----> 3: " + expertDto.getNom());
			}

			// System.out.println("------ RES-PLAN -----> 1: " + presidentJury);
			// System.out.println("------ RES-PLAN -----> 4: " + salle);

			// if(stnDate != null)
			// {
			// System.out.println("------ RES-PLAN -----> 5: " + stnDate);
			// }
			// if(stnHD != null)
			// {
			// System.out.println("------ RES-PLAN -----> 6: " + stnHD);
			// }
			// if(stnHF != null)
			// {
			// System.out.println("------ RES-PLAN -----> 7: " + stnHF);
			// }

			fichePFEDto = new FichePFEDto(presidentJury, presidentJuryDto, expertDto, salle, stnDate, stnHD, stnHF,
					etatFiche);
		}
		// System.out.println("----------------------------------------------------->
		// SARS - 4");
		return fichePFEDto;
	}

	@GetMapping("/traineeKinds")
	public List<String> findTraineeKinds() {
		List<String> traineeKinds = codeNomenclatureRepository.findTraitementFichePFETypes("111");
		return traineeKinds;
	}

	@GetMapping("/upKinds")
	public List<String> getAllUPs() {
		List<String> upKinds = weekScheduleRepository.getAllUPs("2021", Long.valueOf(2));
		return upKinds;
	}

	@PostMapping("/upload/ganttDiagram/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadGanttDiagram(@PathVariable String currentUserCode,
															  @RequestParam("file") MultipartFile file) {

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			String ganttDiagramFP = storageService.storeGanttDiagram(file, currentUserCode);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Diagramme Gantt " + file.getOriginalFilename() + " a bien été déposé." + "$PATH$"
					+ ganttDiagramFP;
			// // System.out.println("-----------------------------1.3");//
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Diagramme Gantt " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/cancellationAgreement")
	public ResponseEntity<MessageResponse> uploadCancellationAgreement(@RequestParam("file") MultipartFile file) {
		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			String ganttDiagramFP = storageService.storeCAncellationAgreement(file);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Diagramme Gantt " + file.getOriginalFilename() + " a bien été déposé." + "$PATH$"
					+ ganttDiagramFP;
			// // System.out.println("-----------------------------1.3");//
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Diagramme Gantt " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	/******************************************************
	 * Report
	 ******************************************************/
	@PostMapping("/upload/reportv1/{currentUserCode}/{checked}")
	public ResponseEntity<MessageResponse> uploadReportV1(@PathVariable String currentUserCode,
														  @RequestParam("file") MultipartFile file, @PathVariable Boolean checked) {

		// // System.out.println("-----------------------------A: " + currentUserCode +
		// " - " + checked);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			storageService.storeReportVersion1(file, currentUserCode, checked);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Rapport " + file.getOriginalFilename() + " a été bien déposé.";
			// // System.out.println("-----------------------------1.3");

			/*****************************************
			 * Notification By Mail
			 *****************************************/
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDepot = dateFormata.format(new Date());

			String subject = "Dépôt Rapports";

			String studentMail = utilServices.findStudentMailById(currentUserCode); // DEPLOY_SERVER
			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode); // DEPLOY_SERVER

			// String studentMail =
			// utilServices.findStudentMailById(currentUserCode).substring(0,
			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
			// "@mail.tn";
			// String academicEncMail =
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
			// + "@mail.tn";

			String content = "Nous voulons vous informer par le présent mail que vous avez déposé "
					+ "votre <strong><font color=grey> Première Version du Rapport </font></strong> et c'est "
					+ "le <font color=red> " + dateDepot + " </font>.";

			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Rapport " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@GetMapping("/fichePFEAddBinome/{currentUserCode}/{pairId}")
	public String addFichePFEBinome(@PathVariable String currentUserCode, @PathVariable String pairId) {
		System.out.println("---------------------------------------->StudentCJ Id: " + pairId);
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
		fichePFE.setBinome(pairId);// se0109 studentRepository.findStudentById(pairId));
		fichePFERepository.save(fichePFE);
		return "OK";
	}

	@PostMapping("/upload/reportv2/{currentUserCode}/{traineeKind}/{checked}")
	public ResponseEntity<MessageResponse> uploadReportV2(@PathVariable String currentUserCode,
														  @PathVariable String traineeKind, @RequestParam("file") MultipartFile file, @PathVariable Boolean checked) {

		// System.out.println("-----------------------------A: " + currentUserCode + " -
		// " + traineeKind);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			// storageService.storeReportVersion2(file, currentUserCode, checked);
			storageService.storeReportVersion2Urgent(file, currentUserCode, traineeKind, checked);

			// // System.out.println("-----------------------------1.2");
			message = "Votre Rapport " + file.getOriginalFilename() + " a été bien déposé.";
			// Votre Rapport " + file.getOriginalFilename() + " a bien été déposé !.
			// // System.out.println("-----------------------------1.3");
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Rapport " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/plagiat/{currentUserCode}/{checked}")
	public ResponseEntity<MessageResponse> uploadPlagiat(@PathVariable String currentUserCode,
														 @RequestParam("file") MultipartFile file, @PathVariable Boolean checked) {

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			storageService.storePlagiat(file, currentUserCode, checked);
			// // System.out.println("-----------------------------1.2");
			message = "Votre URKUND " + file.getOriginalFilename() + " a bien été déposé.";
			// // System.out.println("-----------------------------1.3");//
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre URKUND " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/supplement/{currentUserCode}/{checked}")
	public ResponseEntity<MessageResponse> uploadSupplement(@PathVariable String currentUserCode,
															@RequestParam("file") MultipartFile file, @PathVariable Boolean checked) {

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			storageService.storeSupplement(file, currentUserCode, checked);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Supplément " + file.getOriginalFilename() + " a bien été déposé.";
			// // System.out.println("-----------------------------1.3");//
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Supplément " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/internshipCertificate/{currentUserCode}/{checked}")
	public ResponseEntity<MessageResponse> uploadInternshipCertificate(@PathVariable String currentUserCode,
																	   @RequestParam("file") MultipartFile file, @PathVariable Boolean checked) {
		// System.out.println("-----------------------------A: " + currentUserCode + " -
		// " + checked);
		String message = "";
		try {
			// System.out.println("-----------------------------1.1");
			// storageService.storeReportVersion2(file, currentUserCode, checked);
			storageService.storeAttestationStage(file, currentUserCode, checked);

			// System.out.println("-----------------------------1.2");
			message = "Votre Attestation " + file.getOriginalFilename() + " a bien été déposé.";
			// System.out.println("-----------------------------1.3");
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Attestation " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/rapportStageING/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadRapportStageIngenieur(@PathVariable String currentUserCode,
																	   @RequestParam("file") MultipartFile file) {

		System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			System.out.println("-----------------------------1.1");
			storageService.storeRapportStageING(file, currentUserCode);
			System.out.println("-----------------------------1.2");
			message = "Votre Rapport de Stage Ingénieur " + file.getOriginalFilename() + " a été bien déposé.";

			/*****************************************
			 * Notification By Mail
			 *****************************************/
//			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			String dateDepot = dateFormata.format(new Date());
//
//			String subject = "Dépôt Rapport Stage Ingénieur";
//
//			// String studentMail =
//			// utilServices.findStudentMailById(currentUserCode).substring(0,
//			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
//			// "@mail.tn";
//			// String academicEncMail =
//			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
//			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
//			// + "@mail.tn";
//
//			String studentMail = utilServices.findStudentMailById(currentUserCode);
//			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode);
//
//			String content = "Nous voulons vous informer par le présent mail que vous avez déposé "
//					+ "votre <strong><font color=grey> Rapport Stage Ingénieur </font></strong> "
//					+ "le <font color=blue> " + dateDepot + " </font>.";
//
//			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			System.out.println("----------------------------- CATCH");
			message = "Erreur de téléchargement de votre Fiche Evaluation Stage " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/journalStageING/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadJournalStageIngenieur(@PathVariable String currentUserCode,
																	   @RequestParam("file") MultipartFile file) {

		System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			System.out.println("-----------------------------1.1");
			storageService.storeJournalStageING(file, currentUserCode);
			System.out.println("-----------------------------1.2");
			message = "Votre Journal de Stage Ingénieur " + file.getOriginalFilename() + " a été bien déposé.";

			/*****************************************
			 * Notification By Mail
			 *****************************************/
//			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			String dateDepot = dateFormata.format(new Date());
//
//			String subject = "Dépôt Journal Stage Ingénieur";
//
//			// String studentMail =
//			// utilServices.findStudentMailById(currentUserCode).substring(0,
//			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
//			// "@mail.tn";
//			// String academicEncMail =
//			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
//			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
//			// + "@mail.tn";
//
//			String studentMail = utilServices.findStudentMailById(currentUserCode);
//			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode);
//
//			String content = "Nous voulons vous informer par le présent mail que vous avez déposé"
//					+ " votre <strong><font color=grey> Journal Stage Ingénieur </font></strong> "
//					+ "le <font color=blue> " + dateDepot + " </font>.";
//
//			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			System.out.println("----------------------------- CATCH");
			message = "Erreur de téléchargement de votre Fiche Evaluation Stage " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/attestationStageING/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadAttestationStageIngenieur(@PathVariable String currentUserCode,
																		   @RequestParam("file") MultipartFile file) {

		System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			System.out.println("-----------------------------1.1");
			storageService.storeAttestationStageING(file, currentUserCode);
			System.out.println("-----------------------------1.2");
			message = "Votre Attestation de Stage Ingénieur " + file.getOriginalFilename() + " a été bien déposé.";

			/*****************************************
			 * Notification By Mail
			 *****************************************/
//			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			String dateDepot = dateFormata.format(new Date());
//
//			String subject = "Dépôt Attestation Stage Ingénieur";
//
//			// String studentMail =
//			// utilServices.findStudentMailById(currentUserCode).substring(0,
//			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
//			// "@mail.tn";
//			// String academicEncMail =
//			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
//			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
//			// + "@mail.tn";
//
//			String studentMail = utilServices.findStudentMailById(currentUserCode);
//			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode);
//
//			String content = "Nous voulons vous informer par le présent mail que vous avez déposé"
//					+ " votre <strong><font color=grey> Attestation Stage Ingénieur </font></strong> "
//					+ "le <font color=blue> " + dateDepot + " </font>.";
//
//			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			System.out.println("----------------------------- CATCH");
			message = "Erreur de téléchargement de votre Fiche Evaluation Stage " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@GetMapping("/reports/{currentUserCode}")
	public ResponseEntity<List<String>> getListReports(@PathVariable String currentUserCode) {

		Integer index = 1;
		List<DepotRapportUrgDto> files = storageService.getAllReportsUrgence(currentUserCode).map(report -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/reports/")
					.path(index.toString()).toUriString();

			// // System.out.println("------------------------------------------->
			// SARS-DepotRap: " + fileDownloadUri);

			return report;
		}).collect(Collectors.toList());

		// System.out.println("-------------------------------------------> Size: " +
		// files.size());

		// fp.pathRapportVersion1, fp.pathRapportVersion2, fp.dateDepotRapportVersion1,
		// fp.dateDepotRapportVersion2, fp.pathPlagiat, fp.dateDepotPlagiat,
		// fp.confidentiel
		List<String> lss = new ArrayList<>();
		for (DepotRapportUrgDto drd : files) {
			String reportVersion1 = null;
			String reportVersion2 = null;
			String plagiat = null;
			String attestationStage = null;
			String supplement = null;

			String pathRv1 = drd.getPathRapportVersion1();
			String nameRepv1 = null;
			String dateRepv1 = drd.getDateDepotRapportVersion1();

			String pathRv2 = drd.getPathRapportVersion2();
			String nameRepv2 = null;
			String dateRepv2 = drd.getDateDepotRapportVersion2();

			String pathP = drd.getPathPlagiat();
			String nameP = null;
			String dateP = drd.getDateDepotPlagiat();

			String pathAS = drd.getPathAttestationStage();
			String nameAS = null;
			String dateAS = drd.getDateDepotAttestationStage();

			String pathS = drd.getPathSupplement();
			String nameS = null;
			String dateS = drd.getDateDepotSupplement();

			DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			System.out.println(
					"-----------------------------hjvhgh--------------> hi-1: " + drd.getPathRapportVersion1());
			System.out.println(
					"-----------------------------hjjhbh--------------> hi-2: " + drd.getPathRapportVersion2());

			if (drd.getPathRapportVersion1() != null) {
				System.out.println("-------------------dsddsd------------------------> lol-1 Report v1 NOT NULL");
				nameRepv1 = pathRv1.substring(pathRv1.indexOf("uploads") + 8, pathRv1.indexOf("espdsi2020"));
				System.out.println("-------------------------------------------> nameRepv1: " + nameRepv1);
				System.out.println("-------------------------------------------> dateRepv1: " + dateRepv1);
				reportVersion1 = nameRepv1 + "UNITR1RLV" + dateRepv1;
				lss.add(reportVersion1);
				System.out.println("-------------------------------------------> lol-2 Report v1 NOT NULL");
			}

			if (drd.getPathRapportVersion2() != null) {
				System.out
						.println("-------------------------ddddd-----hjguhghg-------------> lol-1 Report v2 NOT NULL");
				nameRepv2 = pathRv2.substring(pathRv2.indexOf("uploads") + 8, pathRv2.indexOf("espdsi2020"));
				System.out.println("-------------------------------------------> nameRepv2: " + nameAS);
				System.out.println("-------------------------------------------> dateRepv2: " + dateAS);
				reportVersion2 = nameRepv2 + "UNITR1RFV" + dateRepv2;
				System.out.println("-------------------------------------------> reportVersion2: " + nameAS);
				lss.add(reportVersion2);
				System.out.println("-------------------------------------------> lol-2 Report v2 NOT NULL");
			}

			if (drd.getPathPlagiat() != null) {
				nameP = pathP.substring(pathP.indexOf("uploads") + 8, pathP.indexOf("espdsi2020"));
				plagiat = nameP + "UNITR1RAP" + dateP;
				lss.add(plagiat);
				// System.out.println("-------------------------------------------> Plagiat NOT
				// NULL");
			}

			if (drd.getPathAttestationStage() != null) {
				nameAS = pathAS.substring(pathAS.indexOf("uploads") + 8, pathAS.indexOf("espdsi2020"));
				// System.out.println("-------------------------------------------> nameAS: " +
				// nameAS);
				// System.out.println("-------------------------------------------> dateAS: " +
				// dateAS);
				attestationStage = nameAS + "UNITR1ASP" + dateAS;
				// System.out.println("------------------------------------------->
				// attestationStage: " + attestationStage);
				lss.add(attestationStage);
				// System.out.println("-------------------------------------------> AS NOT
				// NULL");
			}

			if (drd.getPathSupplement() != null) {
				nameS = pathS.substring(pathS.indexOf("uploads") + 8, pathS.indexOf("espdsi2020"));
				// System.out.println("-------------------------------------------> nameAS: " +
				// nameAS);
				// System.out.println("-------------------------------------------> dateAS: " +
				// dateAS);
				supplement = nameS + "UNITR1DCS" + dateS;
				// System.out.println("------------------------------------------->
				// attestationStage: " + attestationStage);
				lss.add(supplement);
				// System.out.println("-------------------------------------------> AS NOT
				// NULL");
			}

		}

		// for(String s : lss)
		// {
		// System.out.println("--------------------> UNIT: " + s);
		// }

		return ResponseEntity.status(HttpStatus.OK).body(lss);
	}

	@GetMapping("/responsableServiceStageByStudent/{idStu}")
	public String findResponsableServiceStageByStudent(@PathVariable String idStu) {
		String rss = conventionRepository.findResponsableServiceStageByIdEt(idStu).get(0);

		return rss;
	}

	@GetMapping("/itsEM/{idStu}")
	public String itsEM(@PathVariable String idStu) {
		// System.out.println("------------------> ALLOW - 1: " + idStu);

		String result = "NO";
		String rss = conventionRepository.findResponsableServiceStageByIdEt(idStu).get(0);

		// System.out.println("------------------> ALLOW - 2: " + rss);

		if (rss.equalsIgnoreCase("SR-STG-EM")) {
			result = "YES";
		}

		// System.out.println("------------------> ALLOW - 3: " +
		// allowOnlyDepotPDFFilePriv);

		return result;
	}

	@GetMapping("/itsGCWithPair/{idStu}")
	public String itsGCWithPair(@PathVariable String idStu) {
		System.out.println("------------------> ALLOW - 1: " + idStu);
		String result = "NO";

		List<String> idBinomes = fichePFERepository.findBinomeIdByStudentId(idStu);
		String rss = conventionRepository.findResponsableServiceStageByIdEt(idStu).get(0);

		if (rss.equalsIgnoreCase("SR-STG-GC") && !idBinomes.isEmpty()) {
			result = "YES" + idBinomes.get(0);
		}
		System.out.println("------------------> ########################### BINOME: " + result);
		return result;
	}

	@GetMapping("/itsITandEM/{idStu}")
	public String itsITandEM(@PathVariable String idStu) {
		// System.out.println("------------------> ALLOW - 1: " + idStu);

		String result = "YES";
		String rss = conventionRepository.findResponsableServiceStageByIdEt(idStu).get(0);

		// System.out.println("------------------> ALLOW - 2: " + rss);

		if (rss.equalsIgnoreCase("SR-STG-GC")) {
			result = "NO";
		}

		// System.out.println("------------------> ALLOW - 3: " +
		// allowOnlyDepotPDFFilePriv);

		return result;
	}

	@GetMapping("/itsEMandGC/{idStu}")
	public String itsEMandGC(@PathVariable String idStu) {
		System.out.println("------------------> ALLOW - 1: " + idStu);

		String result = "YES";
		String rss = conventionRepository.findResponsableServiceStageByIdEt(idStu).get(0);

		System.out.println("------------------> ALLOW - 2: " + rss);

		if (rss.equalsIgnoreCase("SR-STG-IT")) {
			result = "NO";
		}

		System.out.println("------------------> ALLOW - 3: " + result);

		return result;
	}


	@GetMapping("/encryptedPWDJWTByMailId/{mailEns}/{idEns}")
	public String findEncryptedPWDJWTByMailId(@PathVariable String mailEns, @PathVariable String idEns)
	{
		System.out.println("---------------- Get Company with designation : jwt");
		String pwd = teacherRepository.findTeacherPWDJWTByMailAndId(utilServices.decodeEncodedValue(mailEns), utilServices.decodeEncodedValue(idEns));
		System.out.println("----- 1 " + pwd);
		String pwdJWT = pwd.substring(0, pwd.lastIndexOf("$$$$$"));
		System.out.println(".................... 1 " + pwdJWT);
		return pwdJWT;
	}

	@GetMapping("/encryptedPWDByMailId/{mailEns}/{idEns}")
	public String findEncryptedPWDByMailId(@PathVariable String mailEns, @PathVariable String idEns)
	{
		System.out.println("---------------- Get Company with designation : kkkk");
		String pwd = teacherRepository.findTeacherPWDByMailAndId(utilServices.decodeEncodedValue(mailEns), utilServices.decodeEncodedValue(idEns));
		System.out.println("---------------- Get Company with designation : " + pwd);
		return pwd;
	}

	@GetMapping("/encryptedPWDByMailIdPwd/{mailEns}/{idEns}/{pwdEns}")
	public String findEncryptedPWDByMailIdPwd(@PathVariable String mailEns, @PathVariable String idEns, @PathVariable String pwdEns)
	{
		System.out.println("---------------- Get Company with designation : kkkk");
		String pwd = teacherRepository.findTeacherPWDByMailAndIdAndPwd(utilServices.decodeEncodedValue(mailEns), utilServices.decodeEncodedValue(idEns), utilServices.decodeEncodedValue(pwdEns));
		System.out.println("---------------- Get Company with designation : " + pwd);
		return pwd;
	}


	@GetMapping("/sars2022/{studentId}")
	public void lol(@PathVariable String studentId) {
		System.out.println("--------------zzz-----> SARS 2022 1 : " + studentId);
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);
		System.out.println("-------------------> SARS 2022 2 : " + fichePFE.getEtatFiche());
	}

	@GetMapping("/fichePFEDepot/{idStu}")
	public String findFichePFEDepotByStudent(@PathVariable String idStu) {

		System.out.println("------------------------> SARS: " + fichePFERepository.findResultDepotReport(idStu).size());

		FichePFEDepotDto fichePFEDepotDto = fichePFERepository.findResultDepotReport(idStu).get(0);
		System.out.println(
				"-------------------------------------------------------> LOL 1: " + fichePFEDepotDto.getEtatFiche());
		System.out.println(
				"-------------------------------------------------------> LOL 2: " + fichePFEDepotDto.getEtatDepot());
		String result = "";

		if (fichePFEDepotDto.getEtatDepot().equalsIgnoreCase("01")) {
			result = "DEPOSEE" + fichePFEDepotDto.getDateDepotReports();
		}

		if (fichePFEDepotDto.getEtatDepot().equalsIgnoreCase("02")) {
			result = "VALIDEE" + fichePFEDepotDto.getDateTreatReports();
		}

		if (fichePFEDepotDto.getEtatDepot().equalsIgnoreCase("03")) {
			result = "INCOMPLET" + fichePFEDepotDto.getDateTreatReports() + fichePFEDepotDto.getMotifDepotIncomplet();
		}

		if (fichePFEDepotDto.getEtatDepot().equalsIgnoreCase("04")) {
			result = "METTRE A JOUR";
		}
		System.out.println("-------------------------------------------------------> LOL RES: " + result);
		return result;
	}

	/******************************************************
	 * Bilan
	 ******************************************************/
	@PostMapping("/upload/balanceSheetv1/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadBilanV1(@PathVariable String currentUserCode,
														 @RequestParam("file") MultipartFile file) {

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			storageService.storeBilanVersion1(file, currentUserCode);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Bilan " + file.getOriginalFilename() + " a été bien déposé.";
			// // System.out.println("-----------------------------1.3");

			/*****************************************
			 * Notification By Mail
			 *****************************************/
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDepot = dateFormata.format(new Date());

			String subject = "Dépôt Bilans";

			String studentMail = utilServices.findStudentMailById(currentUserCode); // DEPLOY_SERVER
			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode); // DEPLOY_SERVER

			// String studentMail =
			// utilServices.findStudentMailById(currentUserCode).substring(0,
			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
			// "@mail.tn";
			// String academicEncMail =
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
			// + "@mail.tn";

			String content = "Nous voulons vous informer par le présent mail que vous avez déposé "
					+ "votre <strong><font color=grey> Première Version du Bilan </font></strong> et c'est "
					+ "le <font color=red> " + dateDepot + " </font>.";

			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Bilan " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/balanceSheetv2/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadBilanV2(@PathVariable String currentUserCode,
														 @RequestParam("file") MultipartFile file) {

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			storageService.storeBilanVersion2(file, currentUserCode);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Bilan " + file.getOriginalFilename() + " a été bien déposé.";

			/*****************************************
			 * Notification By Mail
			 *****************************************/
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDepot = dateFormata.format(new Date());

			String subject = "Dépôt Bilans";

			String studentMail = utilServices.findStudentMailById(currentUserCode); // DEPLOY_SERVER
			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode); // DEPLOY_SERVER

			// String studentMail =
			// utilServices.findStudentMailById(currentUserCode).substring(0,
			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
			// "@mail.tn";
			// String academicEncMail =
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
			// + "@mail.tn";

			String content = "Nous voulons vous informer par le présent mail que vous avez déposé "
					+ "votre <strong><font color=grey> Deuxième Version du Bilan </font></strong> et c'est "
					+ "le <font color=red> " + dateDepot + " </font>.";

			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Bilan " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@PostMapping("/upload/balanceSheetv3/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadBilanV3(@PathVariable String currentUserCode,
														 @RequestParam("file") MultipartFile file) {

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			storageService.storeBilanVersion3(file, currentUserCode);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Bilan " + file.getOriginalFilename() + " a été bien déposé.";

			/*****************************************
			 * Notification By Mail
			 *****************************************/
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDepot = dateFormata.format(new Date());

			String subject = "Dépôt Bilans";

			String studentMail = utilServices.findStudentMailById(currentUserCode); // DEPLOY_SERVER
			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode); // DEPLOY_SERVER

			// String studentMail =
			// utilServices.findStudentMailById(currentUserCode).substring(0,
			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
			// "@mail.tn";
			// String academicEncMail =
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
			// + "@mail.tn";

			String content = "Nous voulons vous informer par le présent mail que vous avez déposé "
					+ "votre <strong><font color=grey> Troisième Version du Bilan </font></strong> et c'est "
					+ "le <font color=red> " + dateDepot + " </font>.";

			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Bilan " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@GetMapping("/loadEngineeringJournal/{currentUserCode}")
	public List<String> loadEngineeringJournal(@PathVariable String currentUserCode)
	{

		List<String> dates = evaluationEngTrRepository.findDateUploadJournalINGByStudent(currentUserCode);

		/****************************************************************************************************************************/
		List<String> lss = new ArrayList<>();

		if(!dates.isEmpty())
		{
			if(dates.get(0).toString() !=  null) {
				lss.add(dates.get(0));
			}
		}

		for (String s : lss) {
			System.out.println("---------------------***----------------------> UNIT: " + s);
		}

		return lss;
	}

	@GetMapping("/loadEngineeringAttestation/{currentUserCode}")
	public List<String> loadEngineeringAttestation(@PathVariable String currentUserCode)
	{
		List<DepotJournalINGDto> files = evaluationEngTrRepository.findJournalStageINGByStudent(currentUserCode);
		List<String> lss = new ArrayList<>();

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateValidDepot = dateFormata.format(new Date());

		List<String> dates = evaluationEngTrRepository.findDateUploadAttestationINGByStudent(currentUserCode);

		if(!dates.isEmpty())
		{
			if(dates.get(0).toString() !=  null) {
				lss.add(dates.get(0));

			}
		}

		for (String s : lss) {
			System.out.println("---------------------***----------------------> UNIT: " + s);
		}

		return lss;
	}

	@GetMapping("/loadEngineeringReport/{currentUserCode}")
	public List<String> loadEngineeringReport(@PathVariable String currentUserCode)
	{
		List<String> dates = evaluationEngTrRepository.findDateUploadReportINGByStudent(currentUserCode);

		/****************************************************************************************************************************/
		List<String> lss = new ArrayList<>();

		if(!dates.isEmpty())
		{
			if(dates.get(0).toString() !=  null) {
				lss.add(dates.get(0));
			}
		}

		for (String s : lss) {
			System.out.println("---------------------***----------------------> UNIT: " + s);
		}

		return lss;
	}

	@PostMapping("/jstging")
	public ResponseEntity<List<String>> sampledsi(@RequestBody StudentCodeRequest studentCodeReq)
	{

		System.out.println("ENCODED id Student : " + studentCodeReq.getStudentCode());

		/*
		// decrypt Base64
		byte[] decodedBytes = Base64.getDecoder().decode(studentCodeReq.getStudentCode());
		String decodedString = new String(decodedBytes);
		*/

		String mpCryptoPassword = "SALT";
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(mpCryptoPassword);

		String decodedString = decryptor.decrypt(studentCodeReq.getStudentCode());
		System.out.println("DECODED id Student : " + studentCodeReq.getStudentCode());


		List<DepotJournalINGDto> files = evaluationEngTrRepository.findJournalStageINGByStudent(decodedString);

		/*
		Integer index = 1;

	    List<DepotJournalINGDto> files = storageService.getJournalStageINGDto(currentUserCode).map(report -> {
	    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/journalStageING/").path(index.toString()).toUriString();

	    System.out.println("---------------------***----------------------> SARS-journalStageING: " + fileDownloadUri);

	      return report;
	    }).collect(Collectors.toList());


	    System.out.println("---------------------***----------------------> Size: " + files.size());


	    // fp.pathRapportVersion1, fp.pathRapportVersion2, fp.dateDepotRapportVersion1, fp.dateDepotRapportVersion2, fp.pathPlagiat, fp.dateDepotPlagiat, fp.confidentiel
	    */

		List<String> lss = new ArrayList<>();

		for(DepotJournalINGDto dbd : files)
		{
			String esING = null;

			String pathEvaluationStageING = dbd.getPathJournalStageING();
			String nameEvaluationStageING = null;
			String dateEvaluationStageING = dbd.getDateDepotJournalStageING();

			if(dbd.getPathJournalStageING() != null)
			{
				nameEvaluationStageING = pathEvaluationStageING.substring(pathEvaluationStageING.indexOf("uploads")+8, pathEvaluationStageING.indexOf("espdsi2020"));
				esING = nameEvaluationStageING + "UNITR1" + dateEvaluationStageING;
				lss.add(esING);
				// // System.out.println("-------------------------------------------> Bilan NOT NULL 1");
			}

		}

		for(String s : lss)
		{
			System.out.println("---------------------***----------------------> UNIT: " + s);
		}

		return ResponseEntity.status(HttpStatus.OK).body(lss);
	}

	@GetMapping("/balanceSheets/{currentUserCode}")
	public ResponseEntity<List<String>> getListBilans(@PathVariable String currentUserCode) {

		String mpCryptoPassword = "SALT";
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(mpCryptoPassword);


		Integer index = 1;
		List<DepotBilanDto> files = storageService.getAllBilans(currentUserCode).map(report -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/balanceSheets/")
					.path(index.toString()).toUriString();

			// // System.out.println("------------------------------------------->
			// SARS-DepotBilan: " + fileDownloadUri);

			return report;
		}).collect(Collectors.toList());

		// // System.out.println("-------------------------------------------> Size: " +
		// files.size());

		// fp.pathRapportVersion1, fp.pathRapportVersion2, fp.dateDepotRapportVersion1,
		// fp.dateDepotRapportVersion2, fp.pathPlagiat, fp.dateDepotPlagiat,
		// fp.confidentiel
		List<String> lss = new ArrayList<>();
		for (DepotBilanDto dbd : files) {
			String bilanVersion1 = null;
			String bilanVersion2 = null;
			String bilanVersion3 = null;

			String pathBv1 = dbd.getPathBilanVersion1();
			String nameBilanv1 = null;
			String dateBilanv1 = dbd.getDateDepotBilanVersion1();

			String pathBv2 = dbd.getPathBilanVersion2();
			String nameBilanv2 = null;
			String dateBilanv2 = dbd.getDateDepotBilanVersion2();

			String pathBv3 = dbd.getPathBilanVersion3();
			String nameBilanv3 = null;
			String dateBilanv3 = dbd.getDateDepotBilanVersion3();

			if (dbd.getPathBilanVersion1() != null) {
				nameBilanv1 = pathBv1.substring(pathBv1.indexOf("uploads") + 8, pathBv1.indexOf("espdsi2020"));
				bilanVersion1 = nameBilanv1 + "UNITBV1" + dateBilanv1;
				String encodedBilanV1 = decryptor.encrypt(bilanVersion1);
				lss.add(encodedBilanV1);
				// // System.out.println("-------------------------------------------> Bilan NOT
				// NULL 1");
			}

			if (dbd.getPathBilanVersion2() != null) {
				nameBilanv2 = pathBv2.substring(pathBv2.indexOf("uploads") + 8, pathBv2.indexOf("espdsi2020"));
				bilanVersion2 = nameBilanv2 + "UNITBV2" + dateBilanv2;
				String encodedBilanV2 = decryptor.encrypt(bilanVersion2);
				lss.add(encodedBilanV2);
				// // System.out.println("-------------------------------------------> Bilan NOT
				// NULL 2");
			}

			if (dbd.getPathBilanVersion3() != null) {
				nameBilanv3 = pathBv3.substring(pathBv3.indexOf("uploads") + 8, pathBv3.indexOf("espdsi2020"));
				bilanVersion3 = nameBilanv3 + "UNITBV3" + dateBilanv3;
				String encodedBilanV3 = decryptor.encrypt(bilanVersion3);
				lss.add(encodedBilanV3);
				// // System.out.println("-------------------------------------------> Bilan NOT
				// NULL 3");
			}

		}

		// for(String s : lss)
		// {
		// // System.out.println("-------------------------------------------> UNIT: " +
		// s);
		// }

		return ResponseEntity.status(HttpStatus.OK).body(lss);
	}

	/******************************************************
	 * Journal
	 ******************************************************/
	@PostMapping("/upload/newspaper/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadJournal(@PathVariable String currentUserCode,
														 @RequestParam("file") MultipartFile file) {

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try {
			// // System.out.println("-----------------------------1.1");
			storageService.storeJournal(file, currentUserCode);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Journal " + file.getOriginalFilename() + " a été bien déposé.";
			// // System.out.println("-----------------------------1.3");

			/*****************************************
			 * Notification By Mail
			 *****************************************/
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDepot = dateFormata.format(new Date());

			String subject = "Dépôt Journal du Bord";

			String studentMail = utilServices.findStudentMailById(currentUserCode); // DEPLOY_SERVER
			String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode); // DEPLOY_SERVER

			// String studentMail =
			// utilServices.findStudentMailById(currentUserCode).substring(0,
			// utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) +
			// "@mail.tn";
			// String academicEncMail =
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).substring(0,
			// utilServices.findMailPedagogicalEncadrant(currentUserCode).lastIndexOf("@"))
			// + "@mail.tn";

			String content = "Nous voulons vous informer par le présent mail que vous avez déposé "
					+ "votre <strong><font color=grey> Journal du Bord </font></strong> et c'est "
					+ "le <font color=red> " + dateDepot + " </font>.";

			utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			// // System.out.println("-----------------------------2");
			message = "Erreur de téléchargement de votre Journal " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@GetMapping("/newspaper/{currentUserCode}")
	public ResponseEntity<List<String>> getJournal(@PathVariable String currentUserCode) {

		String mpCryptoPassword = "SALT";
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(mpCryptoPassword);

		Integer index = 1;
		List<DepotJournalDto> files = storageService.getJournal(currentUserCode).map(report -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/newspaper/")
					.path(index.toString()).toUriString();

			// // System.out.println("------------------------------------------->
			// SARS-DepotBilan: " + fileDownloadUri);

			return report;
		}).collect(Collectors.toList());

		// // System.out.println("-------------------------------------------> Size: " +
		// files.size());

		// fp.pathRapportVersion1, fp.pathRapportVersion2, fp.dateDepotRapportVersion1,
		// fp.dateDepotRapportVersion2, fp.pathPlagiat, fp.dateDepotPlagiat,
		// fp.confidentiel
		List<String> lss = new ArrayList<>();
		for (DepotJournalDto dbd : files) {
			String journal = null;

			String pathJournal = dbd.getPathJournal();
			String nameJournal = null;
			String dateJournal = dbd.getDateDepotJournal();

			if (dbd.getPathJournal() != null) {
				nameJournal = pathJournal.substring(pathJournal.indexOf("uploads") + 8,
						pathJournal.indexOf("espdsi2020"));
				journal = nameJournal + "UNITR1" + dateJournal;
				String encodedJournal = decryptor.encrypt(journal);

				lss.add(encodedJournal);
				// // System.out.println("-------------------------------------------> Bilan NOT
				// NULL 1");
			}

		}

		// for(String s : lss)
		// {
		// // System.out.println("-------------------------------------------> UNIT: " +
		// s);
		// }

		return ResponseEntity.status(HttpStatus.OK).body(lss);
	}

	// @GetMapping("/evaluationStageING/{currentUserCode}")
	// public ResponseEntity<List<String>> getEvaluationStageING(@PathVariable
	// String currentUserCode)
	// {
	//
	// Integer index = 1;
	// List<DepotEvaluationStageINGDto> files =
	// storageService.getEvaluationStageING(currentUserCode).map(report -> {
	// String fileDownloadUri =
	// ServletUriComponentsBuilder.fromCurrentContextPath().path("/evaluationStageING/").path(index.toString()).toUriString();
	//
	// System.out.println("---------------------***---------------------->
	// SARS-evaluationStageING: " + fileDownloadUri);
	//
	// return report;
	// }).collect(Collectors.toList());
	//
	//
	// System.out.println("---------------------***----------------------> Size: " +
	// files.size());
	//
	//
	// // fp.pathRapportVersion1, fp.pathRapportVersion2,
	// fp.dateDepotRapportVersion1, fp.dateDepotRapportVersion2, fp.pathPlagiat,
	// fp.dateDepotPlagiat, fp.confidentiel
	// List<String> lss = new ArrayList<>();
	// for(DepotEvaluationStageINGDto dbd : files)
	// {
	// String esING = null;
	//
	// String pathEvaluationStageING = dbd.getPathEvaluationStageING();
	// String nameEvaluationStageING = null;
	// String dateEvaluationStageING = dbd.getDateDepotEvaluationStageING();
	//
	// if(dbd.getPathEvaluationStageING() != null)
	// {
	// nameEvaluationStageING =
	// pathEvaluationStageING.substring(pathEvaluationStageING.indexOf("uploads")+8,
	// pathEvaluationStageING.indexOf("espdsi2020"));
	// esING = nameEvaluationStageING + "UNITR1" + dateEvaluationStageING;
	// lss.add(esING);
	// // // System.out.println("-------------------------------------------> Bilan
	// NOT NULL 1");
	// }
	//
	// }
	////
	// for(String s : lss)
	// {
	// System.out.println("---------------------***----------------------> UNIT: " +
	// s);
	// }
	//
	// return ResponseEntity.status(HttpStatus.OK).body(lss);
	// }

	@GetMapping("/ganttDiagram/{currentUserCode}")
	public ResponseEntity<List<String>> getGanttDiagram(@PathVariable String currentUserCode) {

		Integer index = 1;
		List<DepotGanttDiagramDto> files = storageService.getGanttDiagram(currentUserCode).map(report -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/ganttDiagram/")
					.path(index.toString()).toUriString();

			// // System.out.println("------------------------------------------->
			// SARS-DepotBilan: " + fileDownloadUri);

			return report;
		}).collect(Collectors.toList());

		// // System.out.println("-------------------------------------------> Size: " +
		// files.size());

		// fp.pathRapportVersion1, fp.pathRapportVersion2, fp.dateDepotRapportVersion1,
		// fp.dateDepotRapportVersion2, fp.pathPlagiat, fp.dateDepotPlagiat,
		// fp.confidentiel
		List<String> lss = new ArrayList<>();
		for (DepotGanttDiagramDto dbd : files) {
			String pathJournal = dbd.getPathGanttDiagram();
			String nameJournal = null;

			if (dbd.getPathGanttDiagram() != null) {
				nameJournal = pathJournal.substring(pathJournal.indexOf("uploads") + 8,
						pathJournal.indexOf("espdsi2020"));
				// journal = nameJournal + "UNITR1" + dateJournal;
				lss.add(nameJournal);
				// // System.out.println("-------------------------------------------> Bilan NOT
				// NULL 1");
			}

		}

		// for(String s : lss)
		// {
		// // System.out.println("-------------------------------------------> UNIT: " +
		// s);
		// }

		return ResponseEntity.status(HttpStatus.OK).body(lss);
	}

	public static <T> Collection<T> intersect(Collection<? extends T> a, Collection<? extends T> b) {
		Collection<T> result = new ArrayList<T>();

		for (T t : a) {
			if (b.remove(t))
				result.add(t);
		}

		return result;
	}

	/******************************************************
	 * Multiple Planification
	 ******************************************************/
	@GetMapping("/affectExpertMP/{studentId}/{expertId}")
	public void affectExpertMP(@PathVariable String studentId, @PathVariable String expertId) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);
		fichePFE.setExpertEns(teacherRepository.findByIdEns(expertId));
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectJuryPresidentMP/{studentId}/{juryPresidentId}")
	public void affectJuryPresidentMP(@PathVariable String studentId, @PathVariable String juryPresidentId) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);
		fichePFE.setPresidentJuryEns(teacherRepository.findByIdEns(juryPresidentId));
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectSalleMP/{studentId}/{salleId}")
	public void affectSalleMP(@PathVariable String studentId, @PathVariable String salleId) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);
		fichePFE.setSalle(salleRepository.findByCodeSalle(salleId));

		if (salleId.equalsIgnoreCase("Remote")) {
			fichePFE.setTraineeKind("02");
		} else {
			fichePFE.setTraineeKind("01");
		}

		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectDateSoutenanceMP/{studentId}/{dateSoutenance}")
	public void affectDateSoutenanceMP(@PathVariable String studentId, @PathVariable String dateSoutenance)
			throws ParseException {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);

		Date dtSoutenance = new SimpleDateFormat("dd-MM-yyyy").parse(dateSoutenance);
		fichePFE.setDateSoutenance(dtSoutenance);

		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectDateSoutenanceMPWithInit/{studentId}/{dateSoutenance}")
	public void affectDateSoutenanceMPWithInit(@PathVariable String studentId, @PathVariable String dateSoutenance)
			throws ParseException {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);

		Date dtSoutenance = new SimpleDateFormat("dd-MM-yyyy").parse(dateSoutenance);
		fichePFE.setDateSoutenance(dtSoutenance);
		fichePFE.setHeureDebut("08:00");
		fichePFE.setHeureFin(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectStartHourSoutenanceMPWithInit/{studentId}/{startHourSoutenance}")
	public void affectStartHourSoutenanceMPWithInit(@PathVariable String studentId,
													@PathVariable String startHourSoutenance) throws ParseException {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);
		fichePFE.setHeureDebut(null);
		fichePFE.setHeureFin(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectStartHourSoutenanceMP/{studentId}/{startHourSoutenance}")
	public void affectStartHourSoutenanceMP(@PathVariable String studentId, @PathVariable String startHourSoutenance)
			throws ParseException {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);
		fichePFE.setHeureDebut(startHourSoutenance);
		fichePFE.setHeureFin(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectEndHourSoutenanceMP/{studentId}/{endHourSoutenance}")
	public void affectEndHourSoutenanceMP(@PathVariable String studentId, @PathVariable String endHourSoutenance)
			throws ParseException {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentId).get(0);
		fichePFE.setHeureFin(endHourSoutenance);
		fichePFERepository.save(fichePFE);
	}

	/******************************************************
	 * Disponibility
	 ******************************************************/

	@GetMapping("/allTeachersFromPEMP")
	public ResponseEntity<?> findAllTeachersFromPE() {
		List<String> allTeachers = weekScheduleRepository.findAllTeachersFromPEMP("2021", Long.valueOf(2));
		System.out.println("-ddddd---dd--> Size 1: " + allTeachers.size());

		List<Map<String, String>> rssStats = new ArrayList<>();
		Map<String, String> lms = new HashMap<>();

		for (String teaConcat : allTeachers) {
			lms.put(teaConcat.substring(0, teaConcat.lastIndexOf("$$")),
					teaConcat.substring(teaConcat.lastIndexOf("$$") + 2));
		}

		Map<String, String> result = lms.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		rssStats.add(result);
		return new ResponseEntity<>(rssStats, HttpStatus.OK);

	}

	@GetMapping("/allClassroomsMP")
	public List<String> findAllClassroomsMP() {
		return weekScheduleRepository.findAllClassroomsMP();
	}

	@GetMapping("/disponibility/teachers/{selectedDate}/{selectedStartHour}/{selectedEndHour}/{idPE}")
	public List<TeacherDtoSTN> findTeachersDisponibility(@PathVariable String selectedDate,
														 @PathVariable String selectedStartHour, @PathVariable String selectedEndHour, @PathVariable String idPE) {
		List<String> seances = new ArrayList<String>();

		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);

		System.out.println("------------------------------------------------------Treatment A: " + new Date());
		List<String> allTeachers = new ArrayList<String>();
		allTeachers = weekScheduleRepository.findAllTeachersFromPE("2023", Long.valueOf(2), idPE);
		System.out.println("------------------------------------------------------Treatment B: " + new Date());

		List<TeacherDtoSTN> teacherDtos = new ArrayList<TeacherDtoSTN>();
		if (!seances.isEmpty()) {
			List<String> teachers = new ArrayList<String>(allTeachers);
			List<String> juryPresidentsPFE = new ArrayList<String>(allTeachers);
			List<String> expertsPFE = new ArrayList<String>(allTeachers);
			List<String> pedEncsPFE = new ArrayList<String>(allTeachers);

			System.out.println("------------------------------------------------------Treatment 1.1: " + new Date());
			teachers.removeAll(weekScheduleRepository.findNotAvailableTeachers(selectedDate, seances));

			System.out.println("D/A------------------------------------------------------Treatment 1.2: "
					+ teachers.size() + " / " + allTeachers.size() + " - " + new Date());
			juryPresidentsPFE.removeAll(weekScheduleRepository.findAvailableJuryPresidentFichePFE(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("J/A------------------------------------------------------Treatment 1.3: "
					+ juryPresidentsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			expertsPFE.removeAll(weekScheduleRepository.findAvailableExpertsFichePFE(selectedDate, selectedStartHour,
					selectedEndHour));

			System.out.println("E/A------------------------------------------------------Treatment 1.4: "
					+ expertsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			pedEncsPFE.removeAll(
					weekScheduleRepository.findAvailablePEsFichePFE(selectedDate, selectedStartHour, selectedEndHour));

			System.out.println("P/A------------------------------------------------------Treatment 1.5: "
					+ pedEncsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			teacherDtos.addAll(teacherRepository.fillOne((List<String>) intersect(teachers,
					intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE)))));

			System.out.println("RESULT------------------------------------------------------Treatment 1.6: "
					+ teacherDtos.size() + " - " + new Date());
		}
		if (seances.isEmpty()) {
			List<String> juryPresidentsPFE = new ArrayList<String>(allTeachers);
			List<String> expertsPFE = new ArrayList<String>(allTeachers);
			List<String> pedEncsPFE = new ArrayList<String>(allTeachers);

			System.out.println("J/A------------------------------------------------------Treatment 2.1: " + new Date());
			juryPresidentsPFE.removeAll(weekScheduleRepository.findAvailableJuryPresidentFichePFE(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("J/A------------------------------------------------------Treatment 2.2: "
					+ juryPresidentsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			expertsPFE.removeAll(weekScheduleRepository.findAvailableExpertsFichePFE(selectedDate, selectedStartHour,
					selectedEndHour));

			System.out.println("E/A------------------------------------------------------Treatment 2.3: "
					+ expertsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			pedEncsPFE.removeAll(
					weekScheduleRepository.findAvailablePEsFichePFE(selectedDate, selectedStartHour, selectedEndHour));

			System.out.println("P/A------------------------------------------------------Treatment 2.4: "
					+ pedEncsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			teacherDtos.addAll(teacherRepository
					.fillOne((List<String>) intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE))));

			System.out.println("RESULT------------------------------------------------------Treatment 2.5: "
					+ teacherDtos.size() + " - " + new Date());
		}
		System.out.println("------------------------------------------------------Treatment C: " + new Date());
		teacherDtos.sort(Comparator.comparing(TeacherDtoSTN::getNom));
		System.out.println("------------------------------------------------------Treatment D: " + new Date());
		return teacherDtos;
	}

	@GetMapping("/findFichePFEStatusByStudent/{idEt}")
	public String findFichePFEStatusByStudent(@PathVariable String idEt) {
		List<String> etats = fichePFERepository.findFichePFEStatus(idEt);
		return etats.get(0);
	}

	@GetMapping("/teachersDisponibilityForAdvancedPlanifStep1/{selectedDate}/{selectedStartHour}/{selectedEndHour}/{checkedStudents}")
	public List<TeacherDtoSTN> findTeachersDisponibilityForAdvancedPlanifStep1(@PathVariable String selectedDate,
																			   @PathVariable String selectedStartHour, @PathVariable String selectedEndHour,
																			   @PathVariable List<String> checkedStudents) {
		List<String> seances = new ArrayList<String>();

		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);

		System.out.println("------------------------------------------------------Treatment A: " + new Date());
		List<String> allTeachers = new ArrayList<String>();
		allTeachers = weekScheduleRepository.findAllTeachersFromPEs("2021", Long.valueOf(2),
				fichePFERepository.getConcatPE_Stu(checkedStudents));
		System.out.println("------------------------------------------------------Treatment B: " + new Date());

		List<TeacherDtoSTN> teacherDtos = new ArrayList<TeacherDtoSTN>();
		if (!seances.isEmpty()) {
			List<String> teachers = new ArrayList<String>(allTeachers);
			List<String> juryPresidentsPFE = new ArrayList<String>(allTeachers);
			List<String> expertsPFE = new ArrayList<String>(allTeachers);
			List<String> pedEncsPFE = new ArrayList<String>(allTeachers);

			System.out.println("------------------------------------------------------Treatment 1.1: " + new Date());
			teachers.removeAll(weekScheduleRepository.findNotAvailableTeachers(selectedDate, seances));

			System.out.println("D/A------------------------------------------------------Treatment 1.2: "
					+ teachers.size() + " / " + allTeachers.size() + " - " + new Date());
			juryPresidentsPFE.removeAll(weekScheduleRepository.findAvailableJuryPresidentFichePFEForAdvancedPlanifStep1(
					selectedDate, selectedStartHour, selectedEndHour));

			System.out.println("J/A------------------------------------------------------Treatment 1.3: "
					+ juryPresidentsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			expertsPFE.removeAll(weekScheduleRepository.findAvailableExpertsFichePFEForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("E/A------------------------------------------------------Treatment 1.4: "
					+ expertsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			pedEncsPFE.removeAll(weekScheduleRepository.findAvailablePEsFichePFEForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("P/A------------------------------------------------------Treatment 1.5: "
					+ pedEncsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			teacherDtos.addAll(teacherRepository.fillOne((List<String>) intersect(teachers,
					intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE)))));

			System.out.println("RESULT------------------------------------------------------Treatment 1.6: "
					+ teacherDtos.size() + " - " + new Date());
		}
		if (seances.isEmpty()) {
			List<String> juryPresidentsPFE = new ArrayList<String>(allTeachers);
			List<String> expertsPFE = new ArrayList<String>(allTeachers);
			List<String> pedEncsPFE = new ArrayList<String>(allTeachers);

			System.out.println("J/A------------------------------------------------------Treatment 2.1: " + new Date());
			juryPresidentsPFE.removeAll(weekScheduleRepository.findAvailableJuryPresidentFichePFEForAdvancedPlanifStep1(
					selectedDate, selectedStartHour, selectedEndHour));

			System.out.println("J/A------------------------------------------------------Treatment 2.2: "
					+ juryPresidentsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			expertsPFE.removeAll(weekScheduleRepository.findAvailableExpertsFichePFEForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("E/A------------------------------------------------------Treatment 2.3: "
					+ expertsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			pedEncsPFE.removeAll(weekScheduleRepository.findAvailablePEsFichePFEForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("P/A------------------------------------------------------Treatment 2.4: "
					+ pedEncsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			teacherDtos.addAll(teacherRepository
					.fillOne((List<String>) intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE))));

			System.out.println("RESULT------------------------------------------------------Treatment 2.5: "
					+ teacherDtos.size() + " - " + new Date());
		}
		System.out.println("------------------------------------------------------Treatment C: " + new Date());
		teacherDtos.sort(Comparator.comparing(TeacherDtoSTN::getNom));
		System.out.println("------------------------------------------------------Treatment D: " + new Date());
		return teacherDtos;
	}

	@GetMapping("/teachersDisponibilityForAP/{selectedDate}/{selectedStartHour}/{selectedEndHour}/{checkedStudents}")
	public List<TeacherDtoSTN> findTeachersDisponibilityForAP(@PathVariable String selectedDate,
															  @PathVariable String selectedStartHour, @PathVariable String selectedEndHour,
															  @PathVariable List<String> checkedStudents) {
		List<String> seances = new ArrayList<String>();

		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);

		System.out.println("------------------------------------------------------Treatment A: " + new Date());
		List<String> allTeachers = new ArrayList<String>();
		allTeachers = weekScheduleRepository.findAllTeachersFromPEs("2021", Long.valueOf(2),
				fichePFERepository.getConcatPE_Stu(checkedStudents));
		System.out.println("------------------------------------------------------Treatment B: " + new Date());

		List<TeacherDtoSTN> teacherDtos = new ArrayList<TeacherDtoSTN>();
		if (!seances.isEmpty()) {
			List<String> teachers = new ArrayList<String>(allTeachers);
			List<String> juryPresidentsPFE = new ArrayList<String>(allTeachers);
			List<String> expertsPFE = new ArrayList<String>(allTeachers);
			List<String> pedEncsPFE = new ArrayList<String>(allTeachers);

			System.out.println("------------------------------------------------------Treatment 1.1: " + new Date());
			teachers.removeAll(weekScheduleRepository.findNotAvailableTeachers(selectedDate, seances));

			System.out.println("D/A------------------------------------------------------Treatment 1.2: "
					+ teachers.size() + " / " + allTeachers.size() + " - " + new Date());
			juryPresidentsPFE.removeAll(weekScheduleRepository.findAvailableJuryPresidentFichePFE(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("J/A------------------------------------------------------Treatment 1.3: "
					+ juryPresidentsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			expertsPFE.removeAll(weekScheduleRepository.findAvailableExpertsFichePFE(selectedDate, selectedStartHour,
					selectedEndHour));

			System.out.println("E/A------------------------------------------------------Treatment 1.4: "
					+ expertsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			pedEncsPFE.removeAll(
					weekScheduleRepository.findAvailablePEsFichePFE(selectedDate, selectedStartHour, selectedEndHour));

			System.out.println("P/A------------------------------------------------------Treatment 1.5: "
					+ pedEncsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			teacherDtos.addAll(teacherRepository.fillOne((List<String>) intersect(teachers,
					intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE)))));

			System.out.println("RESULT------------------------------------------------------Treatment 1.6: "
					+ teacherDtos.size() + " - " + new Date());
		}
		if (seances.isEmpty()) {
			List<String> juryPresidentsPFE = new ArrayList<String>(allTeachers);
			List<String> expertsPFE = new ArrayList<String>(allTeachers);
			List<String> pedEncsPFE = new ArrayList<String>(allTeachers);

			System.out.println("J/A------------------------------------------------------Treatment 2.1: " + new Date());
			juryPresidentsPFE.removeAll(weekScheduleRepository.findAvailableJuryPresidentFichePFE(selectedDate,
					selectedStartHour, selectedEndHour));

			System.out.println("J/A------------------------------------------------------Treatment 2.2: "
					+ juryPresidentsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			expertsPFE.removeAll(weekScheduleRepository.findAvailableExpertsFichePFE(selectedDate, selectedStartHour,
					selectedEndHour));

			System.out.println("E/A------------------------------------------------------Treatment 2.3: "
					+ expertsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			pedEncsPFE.removeAll(
					weekScheduleRepository.findAvailablePEsFichePFE(selectedDate, selectedStartHour, selectedEndHour));

			System.out.println("P/A------------------------------------------------------Treatment 2.4: "
					+ pedEncsPFE.size() + " / " + allTeachers.size() + " - " + new Date());
			teacherDtos.addAll(teacherRepository
					.fillOne((List<String>) intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE))));

			System.out.println("RESULT------------------------------------------------------Treatment 2.5: "
					+ teacherDtos.size() + " - " + new Date());
		}
		System.out.println("------------------------------------------------------Treatment C: " + new Date());
		teacherDtos.sort(Comparator.comparing(TeacherDtoSTN::getNom));
		System.out.println("------------------------------------------------------Treatment D: " + new Date());
		return teacherDtos;
	}

	// @GetMapping("/disponibility/teachers/{selectedDate}/{selectedStartHour}/{selectedEndHour}/{idPE}/{selectedUP}")
	// public List<String> findTeachersDisponibility(@PathVariable String
	// selectedDate, @PathVariable String selectedStartHour, @PathVariable String
	// selectedEndHour, @PathVariable String idPE, @PathVariable String selectedUP)
	// {
	// List<String> seances = new ArrayList<String>();
	//
	// checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);
	//
	// List<String> teachers = new ArrayList<String>();
	// List<String> notAvTe = new ArrayList<String>();
	// List<String> teacherDtos = new ArrayList<String>();
	// if(!seances.isEmpty())
	// {
	// System.out.println("------------------------------------------------------Treatment
	// 1.1: " + new Date());
	//
	// teachers = weekScheduleRepository.findAllTeachersFromPEUP("1988",
	// Long.valueOf(2), idPE, selectedUP);
	// System.out.println("------------------------------------------------------Treatment
	// 1.2: " + new Date());
	// notAvTe = weekScheduleRepository.findNotAvailableTeachersUP(selectedDate,
	// seances, selectedUP);
	// teachers.removeAll(notAvTe);
	//// System.out.println("------------------------------------------------------Treatment
	// 2.0: " + new Date());
	//
	//
	// List<String> juryPresidentsPFE =
	// weekScheduleRepository.findAvailableJuryPresidentFichePFEUP("1988",
	// Long.valueOf(2), selectedDate, selectedStartHour, selectedEndHour, idPE,
	// selectedUP);
	//// System.out.println("---------------------------------------Treatment 3: " +
	// new Date());
	// List<String> commonTJP = new ArrayList<String>(teachers);
	// commonTJP.retainAll(juryPresidentsPFE);
	//
	//
	//// System.out.println("---------------------------------------Treatment 4: " +
	// new Date());
	// List<String> expertsPFE =
	// weekScheduleRepository.findAvailableExpertsFichePFEUP("1988",
	// Long.valueOf(2), selectedDate, selectedStartHour, selectedEndHour, idPE,
	// selectedUP);
	//// System.out.println("---------------------------------------Treatment 5: " +
	// new Date());
	// List<String> commonTE = new ArrayList<String>(teachers);
	// commonTE.retainAll(expertsPFE);
	//// System.out.println("---------------------------------------Treatment 6: " +
	// new Date());
	//
	//
	// List<String> commonJPE = new ArrayList<String>(commonTJP);
	// commonJPE.retainAll(commonTE);
	//// System.out.println("---------------------------------------Treatment 7: " +
	// new Date());
	//
	//
	// List<String> pedEncsPFE =
	// weekScheduleRepository.findAvailablePEsFichePFEUP("1988", Long.valueOf(2),
	// selectedDate, selectedStartHour, selectedEndHour, selectedUP);
	//// System.out.println("---------------------------------------Treatment 8: " +
	// new Date());
	// List<String> commonTPE = new ArrayList<String>(teachers);
	// commonTPE.retainAll(pedEncsPFE);
	//// System.out.println("---------------------------------------Treatment 9: " +
	// new Date());
	//
	//
	// teacherDtos = new ArrayList<String>(commonJPE);
	// teacherDtos.retainAll(commonTPE);
	//
	//// System.out.println("---------------------------------------Treatment 10: "
	// + new Date());
	//
	//
	// System.out.println("------------------------------------------------------Treatment
	// 2.2: " + new Date());
	// }
	// if(seances.isEmpty())
	// {
	//
	// List<String> juryPresidentsPFE =
	// weekScheduleRepository.findAvailableJuryPresidentFichePFEUP("1988",
	// Long.valueOf(2), selectedDate, selectedStartHour, selectedEndHour, idPE,
	// selectedUP);
	//// System.out.println("---------------------------------------Treatment A: " +
	// new Date());
	//
	// List<String> expertsPFE =
	// weekScheduleRepository.findAvailableExpertsFichePFEUP("1988",
	// Long.valueOf(2), selectedDate, selectedStartHour, selectedEndHour, idPE,
	// selectedUP);
	//// System.out.println("---------------------------------------Treatment B: " +
	// new Date());
	//
	// List<String> commonJPE = new ArrayList<String>(juryPresidentsPFE);
	// commonJPE.retainAll(expertsPFE);
	//// System.out.println("---------------------------------------Treatment C: " +
	// new Date());
	//
	// List<String> pedEncsPFE =
	// weekScheduleRepository.findAvailablePEsFichePFEUP("1988", Long.valueOf(2),
	// selectedDate, selectedStartHour, selectedEndHour, selectedUP);
	//// System.out.println("---------------------------------------Treatment D: " +
	// new Date());
	//
	// teacherDtos = new ArrayList<String>(commonJPE);
	// teacherDtos.retainAll(pedEncsPFE);
	// System.out.println("------------------------------------------------------Treatment
	// A.1: " + new Date());
	//
	// System.out.println("------------------------------------------------------Treatment
	// A.2: " + new Date());
	// }
	// System.out.println("---------------------------------------Treatment 3-B: " +
	// new Date());
	// Collections.sort(teacherDtos);
	// System.out.println("---------------------------------------Treatment 4-C: " +
	// new Date());
	// return teacherDtos;
	// }

	@GetMapping("/disponibility/classrooms/{selectedDate}/{selectedStartHour}/{selectedEndHour}")
	public List<String> findClassroomsDisponibility(@PathVariable String selectedDate,
													@PathVariable String selectedStartHour, @PathVariable String selectedEndHour) {

		List<String> seances = new ArrayList<String>();
		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);
		List<String> common = new ArrayList<String>();
		// System.out.println("-----------------------------> Date: " +
		// selectedStartHour + " - " + selectedEndHour + " - " + seances.size());

		if (!seances.isEmpty()) {
			// System.out.println("--------SAL---------------------> PFE + EDT ");
			List<String> classrooms = weekScheduleRepository.findAvailableClassrooms(selectedDate, seances);
			List<String> classroomsPFE = weekScheduleRepository.findAvailableClassroomsFichePFE(selectedDate,
					selectedStartHour, selectedEndHour);

			common = new ArrayList<String>(classrooms);
			common.retainAll(classroomsPFE);
			// System.out.println("--------SAL---------------------> REsult: " +
			// classrooms.size() + " + " + classroomsPFE.size() + " = " + common.size());

			// for(String c : classroomsPFE)
			// {
			// System.out.println("--------SAL---------------------> Classroom: " + c);
			// }
		}
		if (seances.isEmpty()) {
			// System.out.println("--------SAL---------------------> PFE ");
			common = weekScheduleRepository.findAvailableClassroomsFichePFE(selectedDate, selectedStartHour,
					selectedEndHour);
		}

		Collections.sort(common);

		// Add PriorityClasses on the Top of the List
		List<String> priorityClasses = new ArrayList<String>();
		priorityClasses.add("C01");
		priorityClasses.add("C02");
		priorityClasses.add("C03");
		priorityClasses.add("C04");
		priorityClasses.add("C05");
		priorityClasses.add("C06");

		priorityClasses.add("A02");
		priorityClasses.add("A03");
		priorityClasses.add("A04");
		priorityClasses.add("A05");
		priorityClasses.add("A12");
		priorityClasses.add("A13");
		priorityClasses.add("A14");

		priorityClasses.add("E300");
		priorityClasses.add("E400");

		List<String> priorityClassesAvailable = new ArrayList<String>(priorityClasses);
		priorityClassesAvailable.retainAll(common);

		List<String> customizedList = new ArrayList<String>();
		common.removeAll(priorityClasses);

		customizedList.addAll(priorityClassesAvailable);
		customizedList.addAll(common);

		return customizedList;
	}

	@GetMapping("/disponibilityClassrooms/{idCheckedStuds}/{selectedDate}/{selectedStartHour}/{selectedEndHour}")
	public List<String> findClassroomsDisponibilityWithAECalc(@PathVariable List<String> idCheckedStuds,
															  @PathVariable String selectedDate, @PathVariable String selectedStartHour,
															  @PathVariable String selectedEndHour) {
		/*
		 * If AE has Many Students : Got ONLY ClassRooms Availables on - ALL INTERVAL
		 * (10-11, 11-12, ...) - NOT ONLY selectedStartHour & selectedEndHour : 10-11
		 */
		List<String> pedagogicalEncadrantsConcatStudents = fichePFERepository.getConcatPE_Stu(idCheckedStuds);

		for (String s1 : pedagogicalEncadrantsConcatStudents) {
			System.out.println(
					"$$$$$-----------###%%%%%%%%%%%%%%%%%%%%%%%%%####--------> pedagogicalEncadrantsConcatStudents: "
							+ s1);
		}

		// Got List of Available PE / list of Distinct PE
		List<String> availablePedagogicalEncadrants = new ArrayList<String>();

		List<Map<String, String>> lses = new ArrayList<>();
		Map<String, String> se = new HashMap<>();

		// System.out.println("-----------******************--------> HI: " +
		// pedagogicalEncadrantsConcatStudents);

		for (String concatES : pedagogicalEncadrantsConcatStudents) {
			String pe = concatES.substring(0, concatES.lastIndexOf(":"));
			String stu = concatES.substring(concatES.lastIndexOf(":") + 2);

			String resultDisponibilityPEForAdvacedPlanif = resultDisponibilityPEForAdvancedPlanifStep1(pe, stu,
					selectedDate, selectedStartHour, selectedEndHour);

			if (resultDisponibilityPEForAdvacedPlanif.equalsIgnoreCase("AVAILABLE")) {
				availablePedagogicalEncadrants.add(pe);
			}
			se.put(stu, pe);
		}
		lses.add(se);

		for (String s2 : availablePedagogicalEncadrants) {
			System.out
					.println("$$$$$-----------########################--------> availablePedagogicalEncadrants: " + s2);
		}

		// Got Concat S-E having same Encadrant
		//// Method 1: Just Got SYSOUT
		se.entrySet().stream()
				.collect(Collectors.groupingBy(Entry::getValue, Collectors.mapping(Entry::getKey, Collectors.toList())))
				.entrySet().stream().filter(e -> e.getValue().size() > 1).forEach(System.out::println);

		//// Method 2: Got My Purpose
		Pattern DELIMITER = Pattern.compile(":");
		Map<String, List<String>> locationMap = pedagogicalEncadrantsConcatStudents.stream().map(DELIMITER::split)
				.collect(Collectors.groupingBy(a -> a[0], Collectors.mapping(a -> a[1], Collectors.toList())));

		Set<String> pes = new HashSet<String>();

		Map<String, List<String>> trackPEwithManyStudents = new HashMap<>();
		Map<String, List<String>> trackPEwithOneStudent = new HashMap<>();

		for (Map.Entry<String, List<String>> entry : locationMap.entrySet()) {
			System.out.println("$$$$$Key : " + entry.getKey() + ", Value : " + entry.getValue());

			if (entry.getValue().size() > 1) {
				trackPEwithManyStudents.put(entry.getKey(), entry.getValue());
			}

			if (entry.getValue().size() == 1) {
				trackPEwithOneStudent.put(entry.getKey(), entry.getValue());
			}
			pes.add(entry.getKey());
		}

		Set<String> unikAvailableAE = new HashSet<>(availablePedagogicalEncadrants);
		Integer expectedNbrSalles = unikAvailableAE.size();
		System.out.println(
				"$$$$$########################*****************************************************************************************####################### RESULT 1: "
						+ expectedNbrSalles);

		// Got List of Students with Available PE
		System.out.println("$$$$$-------##########-------> HI 1: " + availablePedagogicalEncadrants.size());

		List<String> lshs = new ArrayList<String>();
		if (!availablePedagogicalEncadrants.isEmpty()) {
			for (Map.Entry<String, List<String>> entry : trackPEwithManyStudents.entrySet()) {
				System.out.println("$$$$$-------- One To Many : " + entry.getKey() + " - " + entry.getValue());

				if (availablePedagogicalEncadrants.contains(entry.getKey())) {
					for (int i = 0; i < entry.getValue().size(); i++) {
						LocalTime lt = LocalTime.parse(selectedStartHour);
						LocalTime calcSH = lt.plusHours(i);
						String nsh = calcSH.toString().substring(0, 5);

						lshs.add(nsh);
					}
				}
			}

		}

		/*************************************************************************************************************/

		// Get Availability
		List<String> hi = new ArrayList<String>();

		if (!lshs.isEmpty()) {
			List<List<String>> las = new ArrayList<List<String>>();
			for (int i = 0; i < lshs.size(); i++) {
				LocalTime lt = LocalTime.parse(selectedStartHour);
				LocalTime calcSH = lt.plusHours(i);
				String nsh = calcSH.toString().substring(0, 5);

				LocalTime calcEH = lt.plusHours(i + 1);
				String neh = calcEH.toString().substring(0, 5);

				System.out.println("$$$$$------------------> Mission 0: " + las.size());
				System.out.println("$$$$$------------------> Mission 1: " + nsh + " - " + neh + " ---> "
						+ lol(selectedDate, nsh, neh).size());

				las.add(lol(selectedDate, nsh, neh));

				System.out.println("$$$$$------------------> Mission 2: " + las.size());

				Set<String> result = Sets.newHashSet(las.get(0));
				for (List<String> numbers : las) {
					result = Sets.intersection(result, Sets.newHashSet(numbers));
				}

				hi = new ArrayList<>(result);
			}
		} else {
			hi = lol(selectedDate, selectedStartHour, selectedEndHour);
		}

		// for(String s : hi)
		// {
		// System.out.println("$$$$$------------------> Final Result For Mission: " +
		// s);
		// }

		return hi;

	}

	@GetMapping("/authorizedStudentsToSTN/{idSession}/{idRespServiceStage}")
	public List<StudentPlanifySTNDto> authorizedStudentsToSTN(@PathVariable Integer idSession,
															  @PathVariable String idRespServiceStage) {

		Set<String> idStudents = fichePFERepository.findAllAuthorizedStudentsForSoutenance(idSession,
				idRespServiceStage);
		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		System.out.println("-------------------> idStudents: " + idStudents);

		for (String idStudent : idStudents) {
			System.out.println("-------------------> UNIT-idStudent: " + idStudent);
			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if (fp.getDateDepotReports() != null) {
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
					fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance()));
		}

		return studentsDtos;

	}


	@GetMapping("/allAuthorizedStudentsToSTN/{idRespServiceStage}")
	public List<StudentPlanifySTNDto> allAuthorizedStudentsToSTN(@PathVariable String idRespServiceStage)
	{

		Set<String> idStudents = fichePFERepository.findAllAllAuthorizedStudentsForSoutenance(idRespServiceStage);
		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		System.out.println("-------------------> idStudents: " + idStudents);

		for(String idStudent : idStudents)
		{
			System.out.println("-------------------> UNIT-idStudent: " + idStudent);
			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if(fp.getDateDepotReports() != null)
			{
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111", fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent), currentClass,
					dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance()));
		}

		return studentsDtos;

	}


	public List<String> lol(String selectedDate, String selectedStartHour, String selectedEndHour) {
		List<String> seances = new ArrayList<String>();
		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);
		List<String> common = new ArrayList<String>();
		System.out.println("---------------SARS--------------> Date: " + selectedStartHour + " - " + selectedEndHour
				+ " - " + seances.size());

		if (!seances.isEmpty()) {
			// System.out.println("--------SAL---------------------> PFE + EDT ");
			List<String> classrooms = weekScheduleRepository.findAvailableClassrooms(selectedDate, seances);
			List<String> classroomsPFE = weekScheduleRepository.findAvailableClassroomsFichePFE(selectedDate,
					selectedStartHour, selectedEndHour);

			common = new ArrayList<String>(classrooms);
			common.retainAll(classroomsPFE);
			// System.out.println("--------SAL---------------------> REsult: " +
			// classrooms.size() + " + " + classroomsPFE.size() + " = " + common.size());

			// for(String c : classroomsPFE)
			// {
			// System.out.println("--------SAL---------------------> Classroom: " + c);
			// }
		}
		if (seances.isEmpty()) {
			// System.out.println("--------SAL---------------------> PFE ");
			common = weekScheduleRepository.findAvailableClassroomsFichePFE(selectedDate, selectedStartHour,
					selectedEndHour);
		}

		Collections.sort(common);

		// Add PriorityClasses on the Top of the List
		List<String> priorityClasses = new ArrayList<String>();
		priorityClasses.add("C01");
		priorityClasses.add("C02");
		priorityClasses.add("C03");
		priorityClasses.add("C04");
		priorityClasses.add("C05");
		priorityClasses.add("C06");

		priorityClasses.add("A02");
		priorityClasses.add("A03");
		priorityClasses.add("A04");
		priorityClasses.add("A05");
		priorityClasses.add("A12");
		priorityClasses.add("A13");
		priorityClasses.add("A14");

		priorityClasses.add("E300");
		priorityClasses.add("E400");

		List<String> priorityClassesAvailable = new ArrayList<String>(priorityClasses);
		priorityClassesAvailable.retainAll(common);

		List<String> customizedList = new ArrayList<String>();
		common.removeAll(priorityClasses);

		customizedList.addAll(priorityClassesAvailable);
		customizedList.addAll(common);

		return customizedList;
	}

	@GetMapping("/checkedStudentsToMultiplePlanifSTN/{idRSS}/{idCheckedStuds}")
	public List<StudentMultiplePlanificationDto> checkedStudentsToMultiplePlanifSTN(@PathVariable String idRSS,
																					@PathVariable List<String> idCheckedStuds) {
		System.out.println("--------Size-1-----------##################----------------> MP: " + idCheckedStuds.size());
		if (idRSS.equalsIgnoreCase("SR-STG-GC")) {
			List<String> idPairs = new ArrayList<String>();
			System.out.println("---------------ccccc---------> GC --->  " + idRSS);
			for (String stuGC : idCheckedStuds) {
				String pair = fichePFERepository.findBinomeIdByStudentId(stuGC).get(0);
				if (!pair.equalsIgnoreCase("--")) {
					System.out.println("------------------------> GC --->  " + stuGC + " - " + pair);
					idPairs.add(pair);
				}
			}

			System.out.println("Size ------> GC --->  " + idPairs.size());

			idCheckedStuds.removeAll(idPairs);
			for (String s1 : idCheckedStuds) {
				System.out.println("------> GC --->  " + s1);
			}
		}

		List<StudentMultiplePlanificationDto> studentsDtos = new ArrayList<StudentMultiplePlanificationDto>();

		System.out.println("--------Size-2-----------##################----------------> MP: " + idCheckedStuds.size());
		for (String idStudent : idCheckedStuds) {

			// String try1 = fichePFERepository.lol("183JMT2425");
			// System.out.println("------------------------------------> MP - 2: " + try1);
			//
			// String try2 = fichePFERepository.lol("183JMT3728");
			// System.out.println("------------------------------------> MP - 3: " + try2);

			System.out.println("----------UNIT----------##################----------------> MP - 1: " + idStudent);

			String department = utilServices.findAbbrevDeptByClass(utilServices.findCurrentClassByIdEt(idStudent));

			List<FichePFEMultiplePlanificationDto> lmps = fichePFERepository
					.findFichePFEMutiplePlanification(idStudent);
			FichePFEMultiplePlanificationDto mp = null;
			if (!lmps.isEmpty()) {
				mp = lmps.get(0);
			}

			String pedagogicalEncadrant = "--";
			System.out.println("###################################################### mp.getPedagogicalEncadrant(): "
					+ mp.getPedagogicalEncadrant());
			if (mp.getPedagogicalEncadrant() != null) {
				pedagogicalEncadrant = teacherRepository.findNameTeacherById(mp.getPedagogicalEncadrant());
			}

			String expert = "--";
			System.out.println(
					"###################################################### mp.getExpert(): " + mp.getExpert());
			if (mp.getExpert() != null) {
				expert = teacherRepository.findNameTeacherById(mp.getExpert());
			}

			String juryPresident = "--";
			System.out.println("###################################################### mp.getJuryPresident(): "
					+ mp.getJuryPresident());
			if (mp.getJuryPresident() != null) {
				juryPresident = teacherRepository.findNameTeacherById(mp.getJuryPresident());
			}

			Date dateSoutenance = new Date();
			if (mp.getDateSoutenance() != null) {
				dateSoutenance = mp.getDateSoutenance();
			}

			if (mp.getDateSoutenance() == null) {
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
				fichePFE.setDateSoutenance(new Date());
				fichePFE.setHeureDebut("08:00");
				fichePFERepository.save(fichePFE);
			}

			String startHourSoutenance = "--:--";
			if (mp.getHourStartSoutenance() != null) {
				startHourSoutenance = mp.getHourStartSoutenance();
			}

			String endHourSoutenance = "--:--";
			if (mp.getHourEndSoutenance() != null) {
				endHourSoutenance = mp.getHourEndSoutenance();
			}

			String salleSoutenance = "--";
			if (mp.getSalleSoutenance() != null) {
				salleSoutenance = mp.getSalleSoutenance();
			}

			// System.out.println("######################################################
			// expert: " + mp.getExpert());

			// studentsDtos.sort(Comparator.comparing(StudentTrainedByPCDto::getDateDepot,
			// Comparator.nullsLast(Comparator.naturalOrder())));

			Integer nbrStuTrainedByExpert = fichePFERepository.findListOfStudentsTrainedByExpert(mp.getExpert(), "2021")
					.size();
			Integer nbrStuTrainedByJuryPresident = fichePFERepository
					.findListOfStudentsTrainedByJuryPresident(mp.getJuryPresident(), "2021").size();

			System.out.println("----------------SARS123----------> 1: " + nbrStuTrainedByExpert);
			System.out.println("----------------SARS123----------> 2: " + nbrStuTrainedByJuryPresident);

			studentsDtos.add(new StudentMultiplePlanificationDto(idStudent,
					utilServices.findStudentFullNameById(idStudent), pedagogicalEncadrant, expert, juryPresident,
					dateSoutenance, startHourSoutenance, endHourSoutenance, salleSoutenance, department, "",
					nbrStuTrainedByExpert, nbrStuTrainedByJuryPresident));

			/****************************************
			 * Duplicate Planification to Binome GC
			 ****************************************/
			if (idRSS.equalsIgnoreCase("SR-STG-GC")) {
				String pairFullName = utilServices.findBinomeFullNameByStudentId(idStudent);

				if (pairFullName != null) {
					FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);

					String idBinome = fichePFE.getBinome(); // se0109 .getIdEt();
					FichePFE fichePFEBinome = fichePFERepository.findFichePFEByStudent(idBinome).get(0);

					fichePFEBinome.setExpertEns(fichePFE.getExpertEns());
					fichePFEBinome.setPresidentJury(fichePFE.getPresidentJury());
					fichePFEBinome.setPresidentJuryEns(fichePFE.getPresidentJuryEns());
					fichePFEBinome.setDateSoutenance(fichePFE.getDateSoutenance());
					fichePFEBinome.setHeureDebut(fichePFE.getHeureDebut());
					fichePFEBinome.setHeureFin(fichePFE.getHeureFin());
					fichePFEBinome.setSalle(fichePFE.getSalle());

					fichePFEBinome.setEtatFiche("08");
					fichePFERepository.save(fichePFEBinome);
				}
			}
			/****************************************
			 * Duplicate Planification to Binome GC
			 ****************************************/

			// List<Object[]> lofs = new ArrayList<Object[]>();
			// lofs = fichePFERepository.findFichePFEPlanifySoutenanceForMP(idStudent);
			//
			// String pedagogicalEncadrant = "";
			// String expertEns = "";
			// String presidentJuryEns = "";
			//
			// Date stnDate = null;
			//
			// String stnHD = "";
			// String stnHF = "";
			// String salle = "";
			//
			// for(int i = 0; i<lofs.size(); i++)
			// {
			// pedagogicalEncadrant = (String) lofs.get(i)[0];
			// expertEns = (String) lofs.get(i)[1];
			// presidentJuryEns = (String) lofs.get(i)[2];
			//
			// stnDate = (Date) lofs.get(i)[3];
			//
			// stnHD = (String) lofs.get(i)[4];
			// stnHF = (String) lofs.get(i)[5];
			// salle = (String) lofs.get(i)[6];
			//
			// }
			//
			// System.out.println("-**-------hhh mmm--///------> pedagogicalEncadrant: " +
			// pedagogicalEncadrant);
			// System.out.println("-**---------------> expertEns: " + expertEns);
			// System.out.println("-**---------------> presidentJuryEns: " +
			// presidentJuryEns);
			// System.out.println("-**---------------> stnDate: " + stnDate);
			// System.out.println("-**---------------> stnHD: " + stnHD);
			// System.out.println("-**---------------> stnHF: " + stnHF);
			// System.out.println("-**---------------> salle: " + salle);

			// studentsDtos.add(new StudentMultiplePlanificationDto(
			// idStudent,
			// utilServices.findStudentFullNameById(idStudent),
			// pedagogicalEncadrant,
			// expertEns,
			// presidentJuryEns,
			// stnDate,
			// stnHD,
			// stnHF,
			// salle,
			// department));

		}

		return studentsDtos;

	}

	@GetMapping("/checkAllFilledEntriesToBePlanifiedSTN/{idRSS}/{idCheckedStuds}")
	public Boolean checkAllFilledEntriesToBePlanifiedSTN(@PathVariable String idRSS,
														 @PathVariable List<String> idCheckedStuds) {

		if (idRSS.equalsIgnoreCase("SR-STG-GC")) {
			List<String> idPairs = new ArrayList<String>();

			for (String stuGC : idCheckedStuds) {
				String pair = fichePFERepository.findBinomeIdByStudentId(stuGC).get(0);
				if (!pair.equalsIgnoreCase("--")) {
					System.out.println("------------------------> GC --->  " + stuGC + " - " + pair);
					idPairs.add(pair);
				}
			}

			System.out.println("Size ------> GC --->  " + idPairs.size());

			idCheckedStuds.removeAll(idPairs);
			for (String s1 : idCheckedStuds) {
				System.out.println("------> GC --->  " + s1);
			}
		}

		Boolean giveOk = false;

		Integer nbrFilledFichePFEs = 0;
		for (String s : idCheckedStuds) {
			System.out.println("UNIT STU -------------------> HI-128: " + s);
			List<FichePFEMultiplePlanificationDto> lfps = fichePFERepository
					.gotNotNullFichePFEForPlanifiedSTNByIdStudent(s);

			if (!lfps.isEmpty()) {
				System.out.println("FILLED -------------------> HI-128: FILLED ");
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(s).get(0);
				fichePFE.setEtatFiche("08");
				fichePFERepository.save(fichePFE);

				nbrFilledFichePFEs++;
			}
		}

		System.out
				.println("COMPARE -------------------> HI-128: " + nbrFilledFichePFEs + " - " + idCheckedStuds.size());
		if (nbrFilledFichePFEs == idCheckedStuds.size()) {
			giveOk = true;
		}

		System.out.println("giveOk -------------------> HI-128: " + giveOk);

		return giveOk;
	}

	@GetMapping("/giveOkConfirmNotifyMultiplePlanif/{idCheckedStuds}")
	public Boolean giveOkConfirmNotifyMultiplePlanif(@PathVariable List<String> idCheckedStuds) {
		Boolean giveOk = false;
		Integer nbrStudentsWithPlanifiedSoutenances = fichePFERepository
				.nbrStudentsWithPlanifiedSoutenances(idCheckedStuds).size();
		if (idCheckedStuds.size() == nbrStudentsWithPlanifiedSoutenances) {
			giveOk = true;
		}
		return giveOk;
	}

	@GetMapping("/checkDisponibilityEncadrantForAP/{idRSS}/{idCheckedStuds}/{selectedDate}/{selectedStartHour}/{selectedEndHour}")
	public List<StudentAdvancedPlanificationDto> checkDisponibilityEncadrantForAP(@PathVariable String idRSS,
																				  @PathVariable List<String> idCheckedStuds, @PathVariable String selectedDate,
																				  @PathVariable String selectedStartHour, @PathVariable String selectedEndHour) throws ParseException {

		// Got Distinct PE for Checked Students
		System.out
				.println("-----------#######--------> Size - idCheckedStuds: " + idRSS + " - " + idCheckedStuds.size());

		if (idRSS.equalsIgnoreCase("SR-STG-GC")) {
			List<String> idPairs = new ArrayList<String>();

			for (String stuGC : idCheckedStuds) {
				String pair = fichePFERepository.findBinomeIdByStudentId(stuGC).get(0);
				if (!pair.equalsIgnoreCase("--")) {
					System.out.println("------------------------> GC --->  " + stuGC + " - " + pair);
					idPairs.add(pair);
				}
			}

			System.out.println("Size ------> GC --->  " + idPairs.size());

			idCheckedStuds.removeAll(idPairs);
			for (String s1 : idCheckedStuds) {
				System.out.println("------> GC --->  " + s1);
			}
		}

		List<StudentAdvancedPlanificationDto> studentsDtos = new ArrayList<StudentAdvancedPlanificationDto>();
		List<String> pedagogicalEncadrantsConcatStudents = fichePFERepository.getConcatPE_Stu(idCheckedStuds);

		for (String s1 : pedagogicalEncadrantsConcatStudents) {
			System.out.println(
					"-----------###%%%%%%%%%%%%%%%%%%%%%%%%%####--------> pedagogicalEncadrantsConcatStudents: " + s1);
		}

		// Got List of Available PE / list of Distinct PE
		List<String> availablePedagogicalEncadrants = new ArrayList<String>();

		List<Map<String, String>> lses = new ArrayList<>();
		Map<String, String> se = new HashMap<>();

		// System.out.println("-----------******************--------> HI: " +
		// pedagogicalEncadrantsConcatStudents);

		for (String concatES : pedagogicalEncadrantsConcatStudents) {
			String pe = concatES.substring(0, concatES.lastIndexOf(":"));
			String stu = concatES.substring(concatES.lastIndexOf(":") + 2);

			String resultDisponibilityPEForAdvacedPlanif = resultDisponibilityPEForAdvancedPlanifStep1(pe, stu,
					selectedDate, selectedStartHour, selectedEndHour);

			if (resultDisponibilityPEForAdvacedPlanif.equalsIgnoreCase("AVAILABLE")) {
				availablePedagogicalEncadrants.add(pe);
			}
			se.put(stu, pe);
		}
		lses.add(se);

		for (String s2 : availablePedagogicalEncadrants) {
			System.out.println("-----------########################--------> availablePedagogicalEncadrants: " + s2);
		}

		// Got Concat S-E having same Encadrant
		//// Method 1: Just Got SYSOUT
		se.entrySet().stream()
				.collect(Collectors.groupingBy(Entry::getValue, Collectors.mapping(Entry::getKey, Collectors.toList())))
				.entrySet().stream().filter(e -> e.getValue().size() > 1).forEach(System.out::println);

		//// Method 2: Got My Purpose
		Pattern DELIMITER = Pattern.compile(":");
		Map<String, List<String>> locationMap = pedagogicalEncadrantsConcatStudents.stream().map(DELIMITER::split)
				.collect(Collectors.groupingBy(a -> a[0], Collectors.mapping(a -> a[1], Collectors.toList())));

		Set<String> pes = new HashSet<String>();

		Map<String, List<String>> trackPEwithManyStudents = new HashMap<>();
		Map<String, List<String>> trackPEwithOneStudent = new HashMap<>();

		for (Map.Entry<String, List<String>> entry : locationMap.entrySet()) {
			System.out.println("Key : " + entry.getKey() + ", Value : " + entry.getValue());

			if (entry.getValue().size() > 1) {
				trackPEwithManyStudents.put(entry.getKey(), entry.getValue());
			}

			if (entry.getValue().size() == 1) {
				trackPEwithOneStudent.put(entry.getKey(), entry.getValue());
			}
			pes.add(entry.getKey());
		}

		// Integer expectedNbrSalles = trackPEwithOneStudent.size() +
		// trackPEwithManyStudents.size();
		Set<String> unikAvailableAE = new HashSet<>(availablePedagogicalEncadrants);
		Integer expectedNbrSalles = unikAvailableAE.size();
		System.out.println(
				"########################*****************************************************************************************####################### RESULT 1: "
						+ expectedNbrSalles);

		List<String> studentsAlreadyOnlyAffectDateANdHours = fichePFERepository.findStudentsWithOnlyAffectDateHours(
				idCheckedStuds, new ArrayList<>(pes), selectedDate, selectedStartHour, selectedEndHour);

		System.out.println("############################################### RESULT 0: " + selectedDate);
		System.out.println("############################################### RESULT 1: " + pes);
		System.out.println("############################################### RESULT 2: "
				+ studentsAlreadyOnlyAffectDateANdHours.size());

		// Got List of Students with Available PE
		System.out.println("-------##########-------> HI 1: " + availablePedagogicalEncadrants.size());

		if (!availablePedagogicalEncadrants.isEmpty()) {
			for (Map.Entry<String, List<String>> entry : trackPEwithOneStudent.entrySet()) {
				System.out.println("-------- One To One : " + entry.getKey() + " - " + entry.getValue());

				if (availablePedagogicalEncadrants.contains(entry.getKey())) {
					for (String studentUnit : entry.getValue()) {
						FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(studentUnit).get(0);
						fichePFE.setDateSoutenance(new SimpleDateFormat("dd-MM-yyyy").parse(selectedDate));
						fichePFE.setHeureDebut(selectedStartHour);
						fichePFE.setHeureFin(selectedEndHour);

						fichePFERepository.save(fichePFE);
					}
				}
			}

			for (Map.Entry<String, List<String>> entry : trackPEwithManyStudents.entrySet()) {
				System.out.println("-------- One To Many : " + entry.getKey() + " - " + entry.getValue());

				if (availablePedagogicalEncadrants.contains(entry.getKey())) {
					for (int i = 0; i < entry.getValue().size(); i++) {
						LocalTime lt = LocalTime.parse(selectedStartHour);
						LocalTime calcSH = lt.plusHours(i);
						String nsh = calcSH.toString().substring(0, 5);

						LocalTime calcEH = lt.plusHours(i + 1);
						String neh = calcEH.toString().substring(0, 5);

						System.out.println("-----------> Unit : " + i + " - " + entry.getValue().get(i));
						FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(entry.getValue().get(i)).get(0);
						fichePFE.setDateSoutenance(new SimpleDateFormat("dd-MM-yyyy").parse(selectedDate));
						fichePFE.setHeureDebut(nsh);
						fichePFE.setHeureFin(neh);

						fichePFERepository.save(fichePFE);
					}
				}
			}

			/****************/
			List<String> listStudentsWithAvailablePE = fichePFERepository
					.getStudentsByAvailablePEs(availablePedagogicalEncadrants, idCheckedStuds);
			System.out.println("-------##########-------> HI 2");
			for (String idStudent : listStudentsWithAvailablePE) {
				System.out.println("-------##########-------> listStudentsWithAvailablePE: " + idStudent);

				String department = utilServices.findAbbrevDeptByClass(utilServices.findCurrentClassByIdEt(idStudent));

				studentsDtos.add(new StudentAdvancedPlanificationDto(idStudent,
						utilServices.findStudentFullNameById(idStudent), department, expectedNbrSalles));
			}
			System.out.println("-------##########-------> HI 3");
		}

		if (availablePedagogicalEncadrants.isEmpty()
				&& studentsAlreadyOnlyAffectDateANdHours.size() == idCheckedStuds.size()) {

			List<String> listStudentsWithAvailablePE = fichePFERepository
					.getStudentsByAvailablePEs(new ArrayList<>(pes), idCheckedStuds);
			System.out.println("-------##########----ALREADY DONE---> HI 2");
			for (String idStudent : listStudentsWithAvailablePE) {
				System.out.println("-------##########----ALREADY DONE---> listStudentsWithAvailablePE: " + idStudent);

				String department = utilServices.findAbbrevDeptByClass(utilServices.findCurrentClassByIdEt(idStudent));

				studentsDtos.add(new StudentAdvancedPlanificationDto(idStudent,
						utilServices.findStudentFullNameById(idStudent), department, expectedNbrSalles));
			}
			System.out.println("-------##########---ALREADY DONE----> HI 3");
		}

		return studentsDtos;
	}

	// hi2109
	@GetMapping("/handleAutoPlanifSallesExpertsJuryPresidentsForAP/{idRSS}/{idCheckedStuds}/{selectedSalles}/{selectedExperts}/{selectedJuryPresidents}")
	public void handleAutoPlanifSallesExpertsJuryPresidentsForAP(@PathVariable String idRSS,
																 @PathVariable List<String> idCheckedStuds, @PathVariable List<String> selectedSalles,
																 @PathVariable List<String> selectedExperts, @PathVariable List<String> selectedJuryPresidents) {

		if (idRSS.equalsIgnoreCase("SR-STG-GC")) {
			List<String> idPairs = new ArrayList<String>();

			for (String stuGC : idCheckedStuds) {
				String pair = fichePFERepository.findBinomeIdByStudentId(stuGC).get(0);
				if (!pair.equalsIgnoreCase("--")) {
					System.out.println("------------------------> GC --->  " + stuGC + " - " + pair);
					idPairs.add(pair);
				}
			}

			System.out.println("Size ------> GC --->  " + idPairs.size());

			idCheckedStuds.removeAll(idPairs);
			for (String s1 : idCheckedStuds) {
				System.out.println("------> GC --->  " + s1);
			}
		}

		// Got Distinct PE for Checked Students
		// System.out.println("-----------#######--------> Size - idCheckedStuds: " +
		// idCheckedStuds.size());
		List<String> pedagogicalEncadrantsConcatStudents = fichePFERepository.getConcatPE_Stu(idCheckedStuds);

		//// Method 2: Got My Purpose
		Pattern DELIMITER = Pattern.compile(":");
		Map<String, List<String>> OnePEconcManyStus = pedagogicalEncadrantsConcatStudents.stream().map(DELIMITER::split)
				.collect(Collectors.groupingBy(a -> a[0], Collectors.mapping(a -> a[1], Collectors.toList())));

		List<String> lpes = new ArrayList<String>();
		List<String> lstus = new ArrayList<String>();

		// Order PE : From PE has MIN TrainingStudents --> PE has MAX TrainingStudents
		List<Entry<String, List<String>>> lista = new LinkedList<Map.Entry<String, List<String>>>(
				OnePEconcManyStus.entrySet());

		Collections.sort(lista, new Comparator<Entry<String, List<String>>>() {
			public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
				return (Integer.valueOf(o2.getValue().size())).compareTo(Integer.valueOf(o1.getValue().size()));
			}
		});

		for (Map.Entry<String, List<String>> n : lista) {
			lpes.add(n.getKey());
			lstus.addAll(n.getValue());
			System.out.println(
					"-------------------> ORDER PE: " + findTeacherByIdEns(n.getKey()).getNom() + " - " + n.getValue());
		}

		// Order Experts : From Exp has MAX TrainingStudents --> Exp has MIN
		// TrainingStudents
		Map<String, Integer> expertWithTrStuds = new HashMap<>();
		List<String> sortedSelectedExps = new ArrayList<String>();
		for (String exp : selectedExperts) {
			expertWithTrStuds.put(exp, fichePFERepository.findListOfStudentsTrainedByExpert(exp, "2021").size());
		}

		Map<String, Integer> msi = utilServices.sortByValue_S_I(expertWithTrStuds);

		for (Map.Entry<String, Integer> n : msi.entrySet()) {
			sortedSelectedExps.add((String) n.getKey());
			// System.out.println("------------------------------------> ORDER EXP: " +
			// findTeacherByIdEns(n.getKey()) + " - " + n.getValue());
		}

		// Order JuryPresidents : From JP has MAX TrainingStudents --> JP has MIN
		// TrainingStudents
		Map<String, Integer> jpWithTrStuds = new HashMap<>();
		List<String> sortedSelectedJPs = new ArrayList<String>();
		for (String jp : selectedJuryPresidents) {
			jpWithTrStuds.put(jp, fichePFERepository.findListOfStudentsTrainedByJuryPresident(jp, "2021").size());
		}

		Map<String, Integer> mjp = utilServices.sortByValue_S_I(jpWithTrStuds);

		for (Map.Entry<String, Integer> n : mjp.entrySet()) {
			sortedSelectedJPs.add((String) n.getKey());
			System.out.println("---------------------------------------------------------> ORDER JP: "
					+ findTeacherByIdEns(n.getKey()).getNom() + " - " + n.getValue());
		}

		System.out.println("LOL --------------> START: " + selectedSalles.size() + " - " + lpes.size() + " - "
				+ sortedSelectedExps.size() + " - " + sortedSelectedJPs.size());

		Iterator sallesIterator = selectedSalles.iterator();
		Iterator pedEncsIterator = lpes.iterator();
		Iterator expsIterator = sortedSelectedExps.iterator();
		Iterator juryPressIterator = sortedSelectedJPs.iterator();

		while (sallesIterator.hasNext() && pedEncsIterator.hasNext() && expsIterator.hasNext()
				&& juryPressIterator.hasNext()) {

			System.out.println("WHILE --------------> START: " + new Date());

			String sal = (String) sallesIterator.next();
			String pe = (String) pedEncsIterator.next();
			String exp = (String) expsIterator.next();
			String jp = (String) juryPressIterator.next();

			System.out.println("--------------> LINEAR AFFECT - START: " + sal + " - "
					+ teacherRepository.findByIdEns(pe).getNomEns() + " - "
					+ teacherRepository.findByIdEns(exp).getNomEns() + " - "
					+ teacherRepository.findByIdEns(jp).getNomEns());

			List<FichePFE> fichePFEs = fichePFERepository.findFichePFEWithAffectedPEByStudents(pe, lstus);

			List<String> alreadyAffectedForStudsForJP = new ArrayList<String>();
			List<String> alreadyAffectedForStudsForExp = new ArrayList<String>();

			// String azerty = "lol";
			// if(azerty.equalsIgnoreCase("lol"))
			for (int i = 0; i < fichePFEs.size(); i++) {
				// public List<String> findClassroomsDisponibility(@PathVariable String
				// selectedDate, @PathVariable String selectedStartHour, @PathVariable String
				// selectedEndHour)
				fichePFEs.get(i).setSalle(salleRepository.findByCodeSalle(sal));
				fichePFEs.get(i).setEtatFiche("08");
				// System.out.println("JP - " + nbrStuTrainedByJuryPresident.size() + " -->
				// AFFECT StudentCJ: " +
				// studentRepository.findStudentPrenomById(fichePFEs.get(i).getIdFichePFE().getIdEt()).getNomet());

				Integer nbrTrainingJP = fichePFERepository.findListOfStudentsTrainedByJuryPresident(jp, "2021").size();
				System.out.println("JP - " + nbrTrainingJP
						+ " --------------------------------------------------------------> AFFECT JP: "
						+ teacherRepository.findByIdEns(jp).getNomEns() + " -- " + utilServices
						.findStudentFullNameById(fichePFEs.get(i).getIdFichePFE().getConventionPK().getIdEt()));

				if (nbrTrainingJP < 6) {
					fichePFEs.get(i).setPresidentJuryEns(teacherRepository.findByIdEns(jp));
				}

				List<String> listToRemove = new ArrayList<String>();
				List<String> sortedSelectedJPsCopy = new ArrayList<String>();
				sortedSelectedJPsCopy.addAll(sortedSelectedJPs);
				sortedSelectedJPsCopy.remove(jp);
				if (nbrTrainingJP == 6) {
					System.out.println(
							"JP Start =========================***=================================================================> GOT LIMIT: "
									+ nbrTrainingJP + " ------> " + sortedSelectedJPsCopy);
					for (String s : sortedSelectedJPsCopy) {

						if (lstus.size() > alreadyAffectedForStudsForJP.size()) {
							Integer nbrTrJP = fichePFERepository.findListOfStudentsTrainedByJuryPresident(s, "2021")
									.size();
							System.out.println("JP ------> PREPARE: " + teacherRepository.findByIdEns(s).getNomEns()
									+ " - " + nbrTrJP + " ------> " + sortedSelectedJPsCopy);

							System.out.println("JP ************** BEFORE lss-las: " + lstus.size() + " - "
									+ alreadyAffectedForStudsForJP.size() + " ==> " + lstus + " - "
									+ alreadyAffectedForStudsForJP);
							lstus.removeAll(alreadyAffectedForStudsForJP);
							System.out.println("JP ************** AFTEER lss-las: " + lstus.size() + " - "
									+ alreadyAffectedForStudsForJP.size() + " ==> " + lstus + " - "
									+ alreadyAffectedForStudsForJP);
							if (!lstus.isEmpty()) {
								List<FichePFE> fichePFE1s = fichePFERepository.findFichePFEWithAffectedPEByStudents(pe,
										lstus);
								System.out.println("JP ************** fichePFE1s: " + fichePFE1s.size());
								for (int j = 0; j < fichePFE1s.size(); j++) {
									System.out.println("JP ---> nbrTrJP-0: " + nbrTrJP);
									if (nbrTrJP < 6 && j < fichePFE1s.size()) {
										System.out.println(
												"JP .....> TRY: " + teacherRepository.findByIdEns(s).getNomEns());
										alreadyAffectedForStudsForJP.clear();
										do {
											System.out.println("JP ........................> AFFECT: " + j + " - "
													+ teacherRepository.findByIdEns(s).getNomEns() + " -> "
													+ utilServices.findStudentFullNameById(fichePFE1s.get(j)
													.getIdFichePFE().getConventionPK().getIdEt())
													+ " ("
													+ fichePFE1s.get(j).getIdFichePFE().getConventionPK().getIdEt()
													+ ")");
											// System.out.println("N ...> i: " +
											// teacherRepository.findByIdEns(s).getNomEns());
											fichePFE1s.get(j).setPresidentJuryEns(teacherRepository.findByIdEns(s));
											fichePFERepository.save(fichePFE1s.get(j));
											alreadyAffectedForStudsForJP
													.add(fichePFE1s.get(j).getIdFichePFE().getConventionPK().getIdEt());
											nbrTrJP++;
											j++;
										} while (nbrTrJP < 6 && j < fichePFE1s.size());
									}
									System.out.println("JP ---> nbrTrJP-1: " + nbrTrJP);
									if (nbrTrJP == 6) {
										System.out.println(
												"JP -> REMOVE: " + teacherRepository.findByIdEns(s).getNomEns());
										listToRemove.add(s);
									}
									System.out.println("JP ---> nbrTrJP-2: " + nbrTrJP);
								}
							}
						}
					}

					sortedSelectedJPsCopy.removeAll(listToRemove);
					System.out.println(
							"JP End ===========================***===============================================================> GOT LIMIT: "
									+ nbrTrainingJP + " ------> " + sortedSelectedJPsCopy);

				}
				fichePFERepository.save(fichePFEs.get(i));
				alreadyAffectedForStudsForJP.add(fichePFEs.get(i).getIdFichePFE().getConventionPK().getIdEt());
				System.out.println("END ######################### SAVE ... JP");
			}

			System.out.println(
					" <-- JP ########################################################################################################################## EXP -->");

			for (int i = 0; i < fichePFEs.size(); i++) {
				/** Stop After Affect */
				List<String> allStus1 = new ArrayList<String>();
				for (Map.Entry<String, List<String>> n : lista) {
					allStus1.addAll(n.getValue());
				}

				List<String> nbrAffectedExps1 = fichePFERepository.filledExp(allStus1);
				String giveOK1 = "NO1";
				if (allStus1.size() == nbrAffectedExps1.size()) {
					giveOK1 = "YES1";
				}
				System.out.println("EXP ............$$............> NBR: "
						+ utilServices
						.findStudentFullNameById(fichePFEs.get(i).getIdFichePFE().getConventionPK().getIdEt())
						+ " <== " + allStus1.size() + " - " + nbrAffectedExps1.size() + " => " + giveOK1 + " | "
						+ nbrAffectedExps1);

				if (giveOK1.equalsIgnoreCase("NO1")) {
					lstus.clear();
					for (Map.Entry<String, List<String>> n : lista) {
						lstus.addAll(n.getValue());
					}

					Integer nbrTrainingExp = fichePFERepository.findListOfStudentsTrainedByExpert(exp, "2021").size();
					System.out.println("EXP - " + nbrTrainingExp
							+ " --------------------------------------------------------------> AFFECT EXP: "
							+ teacherRepository.findByIdEns(exp).getNomEns() + " - "
							+ utilServices.findStudentFullNameById(
							fichePFEs.get(i).getIdFichePFE().getConventionPK().getIdEt()));

					if (nbrTrainingExp < 6) {
						fichePFEs.get(i).setExpertEns(teacherRepository.findByIdEns(exp));
					}

					List<String> listToRemove = new ArrayList<String>();
					List<String> sortedSelectedExpsCopy = new ArrayList<String>();
					sortedSelectedExpsCopy.addAll(sortedSelectedExps);

					sortedSelectedExpsCopy.remove(exp);
					if (nbrTrainingExp == 6) {
						System.out.println("EXP =========================================================> GOT LIMIT: "
								+ nbrTrainingExp + " ------> " + sortedSelectedExpsCopy);
						for (String s : sortedSelectedExpsCopy) {
							/** Stop After Affect **/
							List<String> allStus = new ArrayList<String>();
							for (Map.Entry<String, List<String>> n : lista) {
								allStus.addAll(n.getValue());
							}
							List<String> nbrAffectedExps = fichePFERepository.filledExp(allStus);
							String giveOK = "NO";

							if (allStus.size() == nbrAffectedExps.size()) {
								giveOK = "YES";
							}

							System.out.println("EXP ...........> NBR: "
									+ utilServices.findStudentFullNameById(
									fichePFEs.get(i).getIdFichePFE().getConventionPK().getIdEt())
									+ " <== " + allStus.size() + " - " + nbrAffectedExps.size() + " => " + giveOK
									+ " | " + nbrAffectedExps);
							/*****/

							if (giveOK.equalsIgnoreCase("NO")) {
								Integer nbrTrEXP = fichePFERepository.findListOfStudentsTrainedByExpert(s, "2021")
										.size();
								System.out
										.println("EXP ------> PREPARE: " + teacherRepository.findByIdEns(s).getNomEns()
												+ " - " + nbrTrEXP + " ------> " + sortedSelectedExpsCopy);
								System.out.println("EXP ************** BEFORE lss-las: " + lstus.size() + " - "
										+ alreadyAffectedForStudsForExp.size() + " ==> " + lstus + " - "
										+ alreadyAffectedForStudsForExp);
								lstus.removeAll(alreadyAffectedForStudsForExp);
								System.out.println("EXP ************** AFTEER lss-las: " + lstus.size() + " - "
										+ alreadyAffectedForStudsForExp.size() + " ==> " + lstus + " - "
										+ alreadyAffectedForStudsForExp);
								if (!lstus.isEmpty()) {
									List<FichePFE> fichePFE1s = fichePFERepository
											.findFichePFEWithAffectedPEByStudents(pe, lstus);
									System.out.println("EXP ************** fichePFE1s: " + fichePFE1s.size());
									for (int j = 0; j < fichePFE1s.size(); j++) {
										System.out.println("EXP ---> nbrTrEXP-0: " + nbrTrEXP);
										if (nbrTrEXP < 6 && j < fichePFE1s.size()) {
											System.out.println(
													"EXP .....> TRY: " + teacherRepository.findByIdEns(s).getNomEns());
											alreadyAffectedForStudsForExp.clear();

											do {
												System.out.println("EXP ........................> AFFECT: " + j + " - "
														+ teacherRepository.findByIdEns(s).getNomEns() + " -> "
														+ utilServices.findStudentFullNameById(fichePFE1s.get(j)
														.getIdFichePFE().getConventionPK().getIdEt())
														+ " ("
														+ fichePFE1s.get(j).getIdFichePFE().getConventionPK().getIdEt()
														+ ")");
												fichePFE1s.get(j).setExpertEns(teacherRepository.findByIdEns(s));
												fichePFERepository.save(fichePFE1s.get(j));
												alreadyAffectedForStudsForExp.add(
														fichePFE1s.get(j).getIdFichePFE().getConventionPK().getIdEt());
												nbrTrEXP++;
												j++;
											} while (nbrTrEXP < 6 && j < fichePFE1s.size());
										}
										System.out.println("EXP ---> nbrTrEXP-1: " + nbrTrEXP);
										if (nbrTrEXP == 6) {
											System.out.println(
													"EXP -> REMOVE: " + teacherRepository.findByIdEns(s).getNomEns());
											listToRemove.add(s);
										}

										System.out.println("EXP ---> nbrTrEXP-2: " + nbrTrEXP);
									}
								}
							}
						}
						sortedSelectedExpsCopy.removeAll(listToRemove);
						System.out
								.println("EXP End ========================================================> GOT LIMIT: "
										+ nbrTrainingExp + " ------> " + sortedSelectedExpsCopy);
					}
					fichePFERepository.save(fichePFEs.get(i));
					alreadyAffectedForStudsForExp.add(fichePFEs.get(i).getIdFichePFE().getConventionPK().getIdEt());
				}

				System.out.println("END ######################### SAVE ... EXP");

			}
			// }
			System.out.println("--------------> LINEAR AFFECT - END: " + sal + " - "
					+ teacherRepository.findByIdEns(pe).getNomEns() + " - "
					+ teacherRepository.findByIdEns(exp).getNomEns() + " - "
					+ teacherRepository.findByIdEns(jp).getNomEns());
			System.out.println("WHILE --------------> END: " + new Date());

			System.out.println("LOL 1 ----------hh----> START: " + selectedSalles.size() + " - " + lpes.size() + " - "
					+ sortedSelectedExps.size() + " - " + sortedSelectedJPs.size());

		}

		System.out.println("LOL 2 -----hghjg---------> START: " + selectedSalles + " - " + lpes + " - "
				+ sortedSelectedExps + " - " + sortedSelectedJPs);

	}

	@GetMapping("/checkedStudentsToAdvancedPlanifSTN/{idCheckedStuds}")
	public List<StudentAdvancedPlanificationDto> checkedStudentsToAdvancedPlanifSTN(
			@PathVariable List<String> idCheckedStuds) {

		List<StudentAdvancedPlanificationDto> studentsDtos = new ArrayList<StudentAdvancedPlanificationDto>();

		for (String idStudent : idCheckedStuds) {

			System.out.println(
					"--------------qqqqqqqqqqqqqqqq------##################----------------> MP - 1: " + idStudent);

			String department = utilServices.findAbbrevDeptByClass(utilServices.findCurrentClassByIdEt(idStudent));

			// List<FichePFEMultiplePlanificationDto> lmps =
			// fichePFERepository.findFichePFEMutiplePlanification(idStudent);
			// FichePFEMultiplePlanificationDto mp = null;
			// if(!lmps.isEmpty())
			// {
			// mp = lmps.get(0);
			// }
			//
			// String pedagogicalEncadrant = "--";
			// System.out.println("######################################################
			// mp.getPedagogicalEncadrant(): " + mp.getPedagogicalEncadrant());
			// if(mp.getPedagogicalEncadrant() != null)
			// {
			// pedagogicalEncadrant =
			// teacherRepository.findNameTeacherById(mp.getPedagogicalEncadrant());
			// }

			// System.out.println("######################################################
			// expert: " + mp.getExpert());

			// studentsDtos.sort(Comparator.comparing(StudentTrainedByPCDto::getDateDepot,
			// Comparator.nullsLast(Comparator.naturalOrder())));
			studentsDtos.add(new StudentAdvancedPlanificationDto(idStudent,
					utilServices.findStudentFullNameById(idStudent), department));

		}

		return studentsDtos;

	}

	@GetMapping("/pedagogicalCoordinatorFullName/{pcMail}")
	public String findPedagogicalCoordinatorFullNameById(@PathVariable String pcMail) {
		return pedagogicalCoordinatorRepository.findPedagogicalCoordinatorFullNameByMail(pcMail);
	}

	@GetMapping("/nbrAuthorizedStudentsToSTN/{idSession}/{idRespServiceStage}")
	public Integer nbrAuthorizedStudentsToSTN(@PathVariable Integer idSession,
											  @PathVariable String idRespServiceStage) {
		Set<String> idStudents = fichePFERepository.findAllAuthorizedStudentsForSoutenance(idSession,
				idRespServiceStage);
		return idStudents.size();
	}

	@GetMapping("/nbrAllStudentsDoneSoutenance/{idSession}/{idRespServiceStage}")
	public Integer nbrAllStudentsDoneSoutenance(@PathVariable Integer idSession,
												@PathVariable String idRespServiceStage) {
		Set<String> idStudents = fichePFERepository.findAllStudentsDoneSoutenance(idSession, idRespServiceStage);
		return idStudents.size();
	}

	@GetMapping("/nbrAllStudentsPlanifiedSoutenance/{idSession}/{idRespServiceStage}")
	public Integer nbrAllStudentsPlanifiedSoutenance(@PathVariable Integer idSession,
													 @PathVariable String idRespServiceStage) {
		Set<String> idStudents = fichePFERepository.findAllStudentsPlanifiedSoutenance(idSession, idRespServiceStage);
		return idStudents.size();
	}

	@GetMapping("/soutenancesNotifiées/{idSession}/{idRespServiceStage}")
	public List<StudentPlanifySTNDto> findAllStudentsDoneSoutenance(@PathVariable Integer idSession,
																	@PathVariable String idRespServiceStage) {

		Set<String> idStudents = fichePFERepository.findAllStudentsDoneSoutenance(idSession, idRespServiceStage);
		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		System.out.println("--> idStudents: " + idStudents);

		for (String idStudent : idStudents) {

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			System.out.println("---------> TITLE: " + fichePFE.getTitreProjet() + " - " + fichePFE.getEtatFiche());

			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if (fp.getDateDepotReports() != null) {
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
					fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance(),
					urgRDVSuiviStage.findEtatTrtFicheDepotByAEForStudent(idStudent)));
		}

		return studentsDtos;

	}


	@GetMapping("/soutenancesAllNotifiées/{idRespServiceStage}")
	public List<StudentPlanifySTNDto> findAllStudentsDoneSoutenance(@PathVariable String idRespServiceStage) {

		String idServiceStage = null;
		if(idRespServiceStage.equalsIgnoreCase("SR-STG-IT4") || idRespServiceStage.equalsIgnoreCase("SR-STG-IT2") || idRespServiceStage.equalsIgnoreCase("SR-STG-IT1"))
		//if(idRespServiceStageFULL.startsWith("SR-STG-IT"))
		{
			idServiceStage = "SR-STG-IT";
		}
		else
		{
			idServiceStage = idRespServiceStage;
		}

		Set<String> idStudents = fichePFERepository.findAllStudentsAllDoneSoutenance(idServiceStage);
		System.out.println("--> PIKA STN : " + idServiceStage + " - " + idStudents.size() );

		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		System.out.println("--> idStudents: " + idStudents);

		for (String idStudent : idStudents) {

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			System.out.println("---------> TITLE: " + fichePFE.getTitreProjet() + " - " + fichePFE.getEtatFiche());

			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if (fp.getDateDepotReports() != null) {
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
					fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance(),
					urgRDVSuiviStage.findEtatTrtFicheDepotByAEForStudent(idStudent)));
		}

		return studentsDtos;

	}


	@GetMapping("/soutenancesAllNotifiéesForCPS")
	public List<StudentPlanifySTNDto> findAllStudentsAllDoneSoutenanceForCPS() {

		Set<String> idStudents = fichePFERepository.findAllStudentsAllDoneSoutenanceForCPS();

		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		System.out.println("--> idStudents: " + idStudents);

		for (String idStudent : idStudents) {

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			System.out.println("---------> TITLE: " + fichePFE.getTitreProjet() + " - " + fichePFE.getEtatFiche());

			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if (fp.getDateDepotReports() != null) {
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
					fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance(),
					urgRDVSuiviStage.findEtatTrtFicheDepotByAEForStudent(idStudent)));
		}

		return studentsDtos;

	}

	@GetMapping("/soutenancesNotifiées/{idRespServiceStage}")
	public List<StudentPlanifySTNDto> findAllStudentsAllDoneSoutenance(@PathVariable String idRespServiceStage) {

		Set<String> idStudents = fichePFERepository.findAllStudentsAllDoneSoutenance(idRespServiceStage);
		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		System.out.println("--> idStudents: " + idStudents);

		for (String idStudent : idStudents) {

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			System.out.println("---------> TITLE: " + fichePFE.getTitreProjet() + " - " + fichePFE.getEtatFiche());

			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if (fp.getDateDepotReports() != null) {
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
					fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance(),
					urgRDVSuiviStage.findEtatTrtFicheDepotByAEForStudent(idStudent)));
		}

		return studentsDtos;

	}

	@GetMapping("/soutenancesPlanifiées/{idSession}/{idRespServiceStage}")
	public List<StudentPlanifySTNDto> findAllStudentsWithPlanifiedSoutenances(@PathVariable Integer idSession,
																			  @PathVariable String idRespServiceStage) {

		Set<String> idStudents = fichePFERepository.findAllStudentsPlanifiedSoutenance(idSession, idRespServiceStage);
		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		for (String idStudent : idStudents) {
			// FichePFE fichePFE =
			// fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			// System.out.println("---------> TITLE: " + fichePFE.getTitreProjet() + " - " +
			// fichePFE.getEtatFiche());

			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			// System.out.println("---------> LOL-DT 1: " + fichePFE.getDateDepotReports());
			// System.out.println("---------> LOL-DT 2: " + fp.getDateDepotReports());
			// System.out.println("---------> LOL-DT 2: " + fp.getTrainingKind());

			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if (fp.getDateDepotReports() != null) {
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
					fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance(),
					urgRDVSuiviStage.findEtatTrtFicheDepotByAEForStudent(idStudent)));
		}

		return studentsDtos;

	}


	@GetMapping("/soutenancesAllPlanifiées/{idRespServiceStage}")
	public List<StudentPlanifySTNDto> findAllStudentsWithAllPlanifiedSoutenances(@PathVariable String idRespServiceStage) {

		Set<String> idStudents = fichePFERepository.findAllAllStudentsPlanifiedSoutenance(idRespServiceStage);
		List<StudentPlanifySTNDto> studentsDtos = new ArrayList<StudentPlanifySTNDto>();

		for (String idStudent : idStudents) {
			// FichePFE fichePFE =
			// fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			// System.out.println("---------> TITLE: " + fichePFE.getTitreProjet() + " - " +
			// fichePFE.getEtatFiche());

			FichePFEPlanificationSTNDto fp = fichePFERepository.findFichePFEForPlanSTNByStudent(idStudent);
			// System.out.println("---------> LOL-DT 1: " + fichePFE.getDateDepotReports());
			// System.out.println("---------> LOL-DT 2: " + fp.getDateDepotReports());
			// System.out.println("---------> LOL-DT 2: " + fp.getTrainingKind());

			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String dateDepot = "--";

			if (fp.getDateDepotReports() != null) {
				dateDepot = fp.getDateDepotReports();
			}

			String traineeKind = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
					fp.getTrainingKind());

			studentsDtos.add(new StudentPlanifySTNDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, fp.getLibelleSession(), traineeKind, fp.getDateSoutenance(),
					urgRDVSuiviStage.findEtatTrtFicheDepotByAEForStudent(idStudent)));
		}

		return studentsDtos;

	}

	// @GetMapping("/authorizedStudentsToSTN/{idSession}/{idRespServiceStage}")
	// public List<StudentPlanifySTNDto> authorizedStudentsToSTN(@PathVariable
	// Integer idSession, @PathVariable String idRespServiceStage)
	// {
	//// System.out.println("----------planify-------> idSession: " + idSession);
	// List<String> idStudents =
	// fichePFERepository.findAllAuthorizedStudentsForSoutenance(idSession,
	// idRespServiceStage);
	//// System.out.println("---------planify--------> Size - idSession: " +
	// idStudents.size());
	// List<StudentPlanifySTNDto> studentsDtos = new
	// ArrayList<StudentPlanifySTNDto>();
	//
	// for(String idStudent : idStudents)
	// {
	// FichePFE fichePFE =
	// fichePFERepository.findFichePFEByStudent(idStudent).get(0);
	// //String statusFichePFE =
	// codeNomenclatureRepository.findlibNomeTFByCodeStrAndCodeNome("101",
	// fichePFE.getEtatFiche());
	//
	//// if(fichePFE.getPresidentJuryEns() != null || fichePFE.getPresidentJury() !=
	// null)
	//// {
	//// // System.out.println("-----------------> statusAffectPJ: " +
	// fichePFE.getPresidentJury().);
	//// }
	//
	//
	//// String statusAffectPJ = "";
	//// if(
	//// (fichePFE.getPresidentJuryEns() != null || fichePFE.getPresidentJury() !=
	// null)
	//// &&
	//// (fichePFE.getExpertEns() != null)
	//// &&
	//// (fichePFE.getSalle() != null)
	//// &&
	//// (fichePFE.getDateSoutenance() != null && fichePFE.getHeureDebut() != null
	// && fichePFE.getHeureFin() != null)
	//// )
	//// {
	//// statusAffectPJ = "DONE";
	//// }
	//// else
	//// {
	//// statusAffectPJ = "NOTYET";
	//// }
	////
	//// System.out.println("---------planify--------> statusAffectPJ: " +
	// statusAffectPJ);
	//
	// String statusPlanifySTN = "";
	// if(fichePFE.getEtatFiche().equalsIgnoreCase("06") ||
	// fichePFE.getEtatFiche().equalsIgnoreCase("07"))
	// {
	// statusPlanifySTN = "TRAITE";
	// }
	// else
	// {
	// statusPlanifySTN = "EN ATTENTE";
	// }
	//
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
	// String currentClass =
	// inscriptionRepository.utilServices.findCurrentClassByIdEt(idStudent).get(0);
	// String dateDepot = "";
	//
	// if(fichePFE.getDateDepotReports() != null)
	// {
	// dateDepot = dateFormata.format(fichePFE.getDateDepotReports());
	// }
	// else
	// {
	// dateDepot = "";
	// }
	//
	// String etatTreatDepotFichePFE = findStatusDepotFichePFEByPC(idStudent);
	//
	// String traineeKind =
	// codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
	// fichePFE.getTraineeKind());
	// String sessionLabel =
	// sessionRepository.findByIdSession(fichePFE.getSession().getIdSession()).getLibelleSession();
	//
	// studentsDtos.add(new StudentPlanifySTNDto(idStudent,
	// studentRepository.utilServices.findStudentFullNameById(idStudent),
	// currentClass, dateDepot, sessionLabel, statusPlanifySTN, traineeKind,
	// etatTreatDepotFichePFE));
	// }
	//
	//// for(StudentDto sd : studentsDtos)
	//// {
	//// System.out.println("-----------------------------> AZE: " +
	// sd.getPrenomet() + " - " + sd.getStatusPlanifySTN());
	//// }
	//
	// return studentsDtos;
	// }
	//

	@GetMapping("/affectJuryPresidentEns/{idEt}/{idJuryPresident}/{date}/{startHour}/{endHour}")
	public void affectJuryPresidentEns(@PathVariable String idEt, @PathVariable String idJuryPresident,
									   @PathVariable String date, @PathVariable String startHour, @PathVariable String endHour)
			throws ParseException {

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

		// System.out.println("------------ IDS-1: " + date);

		Date dateSoutenance = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		// System.out.println("------------ IDS-2: " + dateSoutenance);

		Teacher juryPresident = teacherRepository.findByIdEns(idJuryPresident);

		fichePFE.setPresidentJuryEns(juryPresident);
		fichePFE.setPresidentJury(null);
		fichePFE.setDateSoutenance(dateSoutenance);
		fichePFE.setHeureDebut(startHour);
		fichePFE.setHeureFin(endHour);

		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectExpertEns/{idEt}/{idExpert}/{date}/{startHour}/{endHour}")
	public void affectExpertEns(@PathVariable String idEt, @PathVariable String idExpert, @PathVariable String date,
								@PathVariable String startHour, @PathVariable String endHour) throws ParseException {

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

		Date dateSoutenance = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		System.out.println("--------------------lol--------- IDS-2: " + date + " - " + dateSoutenance);
		// System.out.println("-----Expert------- IDS: " + idEt + " - " + idExpert);

		Teacher expertEns = teacherRepository.findByIdEns(idExpert);

		fichePFE.setExpertEns(expertEns);
		fichePFE.setPresidentJury(null);
		fichePFE.setDateSoutenance(dateSoutenance);
		fichePFE.setHeureDebut(startHour);
		fichePFE.setHeureFin(endHour);

		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/affectJuryPresidentNotEns/{idEt}/{fullNameJuryPresident}/{date}/{startHour}/{endHour}")
	public void affectJuryPresidentNotEns(@PathVariable String idEt, @PathVariable String fullNameJuryPresident,
										  @PathVariable String date, @PathVariable String startHour, @PathVariable String endHour)
			throws ParseException {

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		Date dateSoutenance = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		// System.out.println("------------ IDS-2: " + dateSoutenance);

		// System.out.println("------------ IDS: " + idEt + " - " +
		// fullNameJuryPresident);

		fichePFE.setPresidentJury(fullNameJuryPresident);
		fichePFE.setPresidentJuryEns(null);
		fichePFE.setDateSoutenance(dateSoutenance);
		fichePFE.setHeureDebut(startHour);
		fichePFE.setHeureFin(endHour);

		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/findEncadrantPedagogiqueByStudent/{idEt}")
	public TeacherEncadrantPedaDto findEncadrantPedagogiqueByStudent(@PathVariable String idEt) {
		return utilServices.findEncadrantPedagogiqueByStudentId(idEt);
	}

	@GetMapping("/applyForUpdatingMyDepot/{idEt}")
	public String applyForUpdatingMyDepot(@PathVariable String idEt)
	{
		EvaluationEngineeringTraining esi = evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(idEt);
		esi.setEtatDepot("03");
		esi.setDateDemandeMAJ(new Date());
		evaluationEngTrRepository.save(esi);

		// **************************************************************************** Notification by mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateDepotDocsStageING = dateFormata.format(new Date());

		String subject = "Demande Mettre à Jour Dépôt Stage Ingénieur";

		// Server
		String studentMail = utilServices.findStudentMailById(idEt);
		String academicEncadrantMail = utilServices.findEncadrantPedagogiqueByStudentId(idEt).getMail();
		String academicEncadrantFullName = utilServices.findFullNamePedagogicalEncadrant(idEt);

		String content = "Nous voulons vous informer par le présent mail que vous avez envoyé une "
				+ "<strong><font color=grey>Demande de Modification de votre Dépôt</font></strong> "
				+ "et c'est le <font color=blue> " + dateDepotDocsStageING + " </font>.<br/>"
				+ "Votre Encadrant(e) Académique Madame/Monsieur <strong><font color=grey>" + academicEncadrantFullName + "</font></strong> va traiter "
				+ "votre demande le plutôt possible.";

		utilServices.sendMailWithCC(subject, studentMail, academicEncadrantMail, content);

		return esi.getEtatDepot();
	}

	@GetMapping("/confirmMyDepotStageING/{idEt}")
	public String confirmMyDepotStageING(@PathVariable String idEt)
	{
		EvaluationEngineeringTraining esi = evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(idEt);
		esi.setEtatDepot("02");
		esi.setDateConfirmDepot(new Date());
		evaluationEngTrRepository.save(esi);

		// **************************************************************************** Notification by mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateConfirmationDepotDocsStageING = dateFormata.format(new Date());

		String subject = "Dépôt Stage Ingénieur";

		// Server
		String studentMail = utilServices.findStudentMailById(idEt); //.substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
		String academicEncMail = utilServices.findMailPedagogicalEncadrant(idEt); //.substring(0, utilServices.findMailPedagogicalEncadrant(idEt).lastIndexOf("@")) + "@mail.tn";

		String academicEncadrantFullName = utilServices.findFullNamePedagogicalEncadrant(idEt);

		String content = "Nous voulons vous informer par le présent mail que vous avez déposé vos Documents pour Stage Ingénieur : "
				+ "votre <strong><font color=grey> Journal </font></strong>, "
				+ "votre <strong><font color=grey> Attestation </font></strong> "
				+ "ainsi que votre <strong><font color=grey> Rapport </font></strong>, "
				+ "et c'est le <font color=blue> " + dateConfirmationDepotDocsStageING + " </font>. <br/>"
				+ "Votre Encadrant(e) Académique Madame/Monsieur <strong><font color=grey>" + academicEncadrantFullName + "</font></strong> va attribuer "
				+ "la <font color=red>Note Stage Ingénieur</font> en se basant sur votre Dépôt.";

		utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);

		return "02";
	}


	@GetMapping("/findTeacherByIdEns/{idEns}")
	public TeacherDto findTeacherByIdEns(@PathVariable String idEns) {
		Teacher teacher = teacherRepository.findByIdEns(idEns);

		TeacherDto teacherDto = new TeacherDto(teacher.getIdEns(), teacher.getNomEns(), teacher.getTypeEns(),
				teacher.getMailEns(), teacher.getTel1());
		return teacherDto;
	}

	@GetMapping("/hi/{idEns}")
	public TeacherDtoSTN2 hi(@PathVariable String idEns) {
		return teacherRepository.getTeacherDetails(idEns);
	}

	@GetMapping("/pedagogicalEncadrantDisponibility/{idPedagocialEncadrant}/{idEt}/{selectedDate}/{selectedStartHour}/{selectedEndHour}")
	public Boolean findPedagogicalEncadrantDisponibility(@PathVariable String idPedagocialEncadrant,
														 @PathVariable String idEt, @PathVariable String selectedDate, @PathVariable String selectedStartHour,
														 @PathVariable String selectedEndHour) {
		Boolean isAvailablePE = false;
		List<String> seances = new ArrayList<String>();

		checkDisponibilityByUnit(isAvailablePE, seances, selectedStartHour, selectedEndHour);

		String resultDisponibilityPE = resultDisponibilityPE(idPedagocialEncadrant, idEt, selectedDate,
				selectedStartHour, selectedEndHour);

		if (resultDisponibilityPE.equalsIgnoreCase("AVAILABLE")) {
			isAvailablePE = true;
		} else {
			isAvailablePE = false;
		}

		return isAvailablePE;
	}

	public String resultDisponibilityPE(String idPedagogicalEncadrant, String idEt, String selectedDate,
										String selectedStartHour, String selectedEndHour) {
		String result = null;

		List<String> seances = new ArrayList<String>();

		List<String> pairs = fichePFERepository.findBinomeIdByStudentId(idEt);
		String idPr = null;
		if (!pairs.isEmpty()) {
			idPr = pairs.get(0);
		}

		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);

		if (!seances.isEmpty()) {
			List<String> teachers = weekScheduleRepository.findAvailibilityPEfromEDT(selectedDate, seances,
					idPedagogicalEncadrant);
			List<String> juryPresidentsPFE = weekScheduleRepository.findAvailibilityPEasJP(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> expertsPFE = weekScheduleRepository.findAvailibilityPEasEXP(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant);
			List<String> pedEncsPFE = weekScheduleRepository.findAvailibilityPEasPENew(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant, idEt, idPr);

			List<String> commonAll = (List<String>) intersect(teachers,
					intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE)));

			if (commonAll.isEmpty()) {
				result = "BUSY";
			}
			if (!commonAll.isEmpty()) {
				result = "AVAILABLE";
			}
		}
		if (seances.isEmpty()) {
			List<String> juryPresidentsPFE = weekScheduleRepository.findAvailibilityPEasJP(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> expertsPFE = weekScheduleRepository.findAvailibilityPEasEXP(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant);
			List<String> pedEncsPFE = weekScheduleRepository.findAvailibilityPEasPENew(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant, idEt, idPr);

			List<String> commonAll = (List<String>) intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE));

			if (commonAll.isEmpty()) {
				result = "BUSY";
			}
			if (!commonAll.isEmpty()) {
				result = "AVAILABLE";
			}

		}

		return result;
	}

	public String resultDisponibilityPEForAdvacedPlanif(String idPedagogicalEncadrant, String idEt, String selectedDate,
														String selectedStartHour, String selectedEndHour) {
		String result = null;

		List<String> seances = new ArrayList<String>();

		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);

		if (!seances.isEmpty()) {
			List<String> teachers = weekScheduleRepository.findAvailibilityPEfromEDT(selectedDate, seances,
					idPedagogicalEncadrant);
			List<String> juryPresidentsPFE = weekScheduleRepository.findAvailibilityPEasJP(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> expertsPFE = weekScheduleRepository.findAvailibilityPEasEXP(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant);
			List<String> pedEncsPFE = weekScheduleRepository.findAvailibilityPEasPE(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant, idEt);

			List<String> commonAll = (List<String>) intersect(teachers,
					intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE)));

			if (commonAll.isEmpty()) {
				result = "BUSY";
			}
			if (!commonAll.isEmpty()) {
				result = "AVAILABLE";
			}
		}
		if (seances.isEmpty()) {
			List<String> juryPresidentsPFE = weekScheduleRepository.findAvailibilityPEasJP(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> expertsPFE = weekScheduleRepository.findAvailibilityPEasEXP(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant);
			List<String> pedEncsPFE = weekScheduleRepository.findAvailibilityPEasPE(selectedDate, selectedStartHour,
					selectedEndHour, idPedagogicalEncadrant, idEt);

			List<String> commonAll = (List<String>) intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE));

			if (commonAll.isEmpty()) {
				result = "BUSY";
			}
			if (!commonAll.isEmpty()) {
				result = "AVAILABLE";
			}

		}

		return result;
	}

	public String resultDisponibilityPEForAdvancedPlanifStep1(String idPedagogicalEncadrant, String idEt,
															  String selectedDate, String selectedStartHour, String selectedEndHour) {
		String result = null;

		List<String> seances = new ArrayList<String>();

		checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);

		if (!seances.isEmpty()) {
			List<String> teachers = weekScheduleRepository.findAvailibilityPEfromEDT(selectedDate, seances,
					idPedagogicalEncadrant);
			List<String> juryPresidentsPFE = weekScheduleRepository.findAvailibilityPEasJPForAdvancedPlanifStep1(
					selectedDate, selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> expertsPFE = weekScheduleRepository.findAvailibilityPEasEXPForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> pedEncsPFE = weekScheduleRepository.findAvailibilityPEasPEForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant, idEt);

			List<String> commonAll = (List<String>) intersect(teachers,
					intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE)));

			if (commonAll.isEmpty()) {
				result = "BUSY";
			}
			if (!commonAll.isEmpty()) {
				result = "AVAILABLE";
			}
		}
		if (seances.isEmpty()) {
			List<String> juryPresidentsPFE = weekScheduleRepository.findAvailibilityPEasJPForAdvancedPlanifStep1(
					selectedDate, selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> expertsPFE = weekScheduleRepository.findAvailibilityPEasEXPForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
			List<String> pedEncsPFE = weekScheduleRepository.findAvailibilityPEasPEForAdvancedPlanifStep1(selectedDate,
					selectedStartHour, selectedEndHour, idPedagogicalEncadrant, idEt);

			List<String> commonAll = (List<String>) intersect(juryPresidentsPFE, intersect(expertsPFE, pedEncsPFE));

			if (commonAll.isEmpty()) {
				result = "BUSY";
			}
			if (!commonAll.isEmpty()) {
				result = "AVAILABLE";
			}

		}

		return result;
	}

	//
	// public String resultDisponibilityPE(String idPedagogicalEncadrant, String
	// idEt, String selectedDate, String selectedStartHour, String selectedEndHour)
	// {
	// String result = null;
	//
	// List<String> seances = new ArrayList<String>();
	//
	// checkDisponibilityByUnit(false, seances, selectedStartHour, selectedEndHour);
	//
	// // System.out.println("----------------er-------------> Result: " +
	// selectedDate );
	//
	//// System.out.println("----------------er--------lol-----> Result 1: " +
	// selectedDate);
	//// System.out.println("----------------er--------lol-----> Result 2: " +
	// selectedStartHour + " - " + selectedEndHour);
	////
	//// System.out.println("----------------er--------lol-----> Result 3: " +
	// seances);
	//
	// List<String> teachers = new ArrayList<String>();
	// List<TeacherDto> teacherDtos = new ArrayList<TeacherDto>();
	// if(!seances.isEmpty())
	// {
	//// System.out.println("Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£
	// Seances NOT EMPTY: " + seances.size() + " - " + idPedagogicalEncadrant);
	//// System.out.println("---------------------------------------Treatment 1: " +
	// new Date());
	// teachers = weekScheduleRepository.loli1(selectedDate, seances,
	// idPedagogicalEncadrant);
	//// System.out.println("---------------------------------------Treatment 2: " +
	// new Date() + " - " + teachers.size());
	//
	//
	// List<String> juryPresidentsPFE = weekScheduleRepository.loli2(selectedDate,
	// selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
	//// System.out.println("---------------------------------------Treatment 3: " +
	// new Date() + " - " + juryPresidentsPFE.size());
	//// List<String> allTeachers = new ArrayList<String>();
	//// allTeachers.addAll(teachers);
	//// allTeachers.addAll(juryPresidentsPFE);
	//// System.out.println("---------------------------------------Treatment 4: " +
	// new Date() + " - " + allTeachers.size());
	//
	// List<String> commonTJP = new ArrayList<String>(teachers);
	// commonTJP.retainAll(juryPresidentsPFE);
	//
	//
	//// System.out.println("---------------------------------------Treatment 5: " +
	// new Date() + " - " + commonTJP.size());
	// List<String> expertsPFE = weekScheduleRepository.loli3(selectedDate,
	// selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
	//// System.out.println("---------------------------------------Treatment 6: " +
	// new Date() + " - " + expertsPFE.size());
	//
	// List<String> commonTE = new ArrayList<String>(teachers);
	// commonTE.retainAll(expertsPFE);
	//
	//
	//// System.out.println("---------------------------------------Treatment 7: " +
	// new Date() + " - " + commonTE.size());
	// List<String> commonJPE = new ArrayList<String>(commonTJP);
	// commonJPE.retainAll(commonTE);
	//// System.out.println("---------------------------------------Treatment 8: " +
	// new Date() + " - " + commonJPE.size());
	//
	//
	//
	//
	//
	//
	// List<String> pedEncsPFE = weekScheduleRepository.hi(selectedDate,
	// selectedStartHour, selectedEndHour, idPedagogicalEncadrant, idEt);
	//// System.out.println("---------------------------------------Treatment 9: " +
	// new Date() + " - " + pedEncsPFE.size());
	//
	// List<String> commonTPE = new ArrayList<String>(teachers);
	// commonTPE.retainAll(pedEncsPFE);
	//
	//
	// List<String> commonAll = new ArrayList<String>(commonJPE);
	// commonAll.retainAll(commonTPE);
	//
	//
	//// System.out.println("--------------------------------- DISPO PE
	// ------------> Size " + commonAll.size());
	// if(commonAll.isEmpty())
	// {
	//// System.out.println("--------------------------------- DISPO PE
	// ------------> BUSY ");
	// result = "BUSY";
	// }
	// if(!commonAll.isEmpty())
	// {
	//// System.out.println("--------------------------------- DISPO PE
	// ------------> t-ALLAVAILABLE ");
	// result = "AVAILABLE";
	// }
	//// System.out.println("--------------------------------- DISPO PE
	// ------------> Result " + result);
	//
	//// System.out.println("---------------------------------------Treatment 10: "
	// + new Date());
	// }
	// if(seances.isEmpty())
	// {
	//// System.out.println("Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£Â£
	// Seances EMPTY: " + seances.size());
	//
	// List<String> juryPresidentsPFE = weekScheduleRepository.loli2(selectedDate,
	// selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
	//
	//// System.out.println("----------------------> PASS LOL: " +
	// juryPresidentsPFE.size());
	//
	// List<String> expertsPFE = weekScheduleRepository.loli3(selectedDate,
	// selectedStartHour, selectedEndHour, idPedagogicalEncadrant);
	//
	// List<String> commonJPE = new ArrayList<String>(juryPresidentsPFE);
	// commonJPE.retainAll(expertsPFE);
	//
	//
	//
	// List<String> pedEncsPFE = weekScheduleRepository.hi(selectedDate,
	// selectedStartHour, selectedEndHour, idPedagogicalEncadrant, idEt);
	//// System.out.println("---------------------------------------Treatment 9: " +
	// new Date() + " - " + pedEncsPFE.size());
	//
	// List<String> commonAll = new ArrayList<String>(commonJPE);
	// commonAll.retainAll(pedEncsPFE);
	//
	//
	//// System.out.println("--------------------------------- DISPO PE
	// $------------> Size " + commonAll.size());
	// if(commonAll.isEmpty())
	// {
	//// System.out.println("--------------------------------- DISPO PE
	// $------------> BUSY ");
	// result = "BUSY";
	// }
	// if(!commonAll.isEmpty())
	// {
	//// System.out.println("--------------------------------- DISPO PE
	// $------------> t-ALLAVAILABLE ");
	// result = "AVAILABLE";
	// }
	//// System.out.println("--------------------------------- DISPO PE
	// $------------> Result " + result);
	//
	// }
	//// System.out.println("---------------------------------------Treatment 11: "
	// + new Date());
	// teacherDtos.sort(Comparator.comparing(TeacherDto::getNom));
	//// System.out.println("---------------------------------------Treatment 12: "
	// + new Date());
	//
	//
	// return result;
	// }
	//

	// @GetMapping("/pedagogicalEncadrantDisponibility/{idPedagocialEncadrant}/{selectedDate}/{selectedStartHour}/{selectedEndHour}")
	// public Boolean findPedagogicalEncadrantDisponibility(@PathVariable String
	// idPedagocialEncadrant, @PathVariable String selectedDate, @PathVariable
	// String selectedStartHour, @PathVariable String selectedEndHour)
	// {
	// idPedagocialEncadrant = "P-06-06";
	//// // System.out.println("--------------------> 1: " + idPedagocialEncadrant);
	//// // System.out.println("--------------------> 2: " + selectedDate);
	//// // System.out.println("--------------------> 3: " + selectedStartHour + " -
	// " + selectedEndHour);
	//
	// //inscriptionRepository.findEncadrantPedagogiqueByStudent(idEt).get(0);
	// //// System.out.println("--------------------> 4: " +
	// idEncadrantPedagogique);
	// // String idPedagocialEncadrant =
	// teacherRepository.findByIdEns(idEncadrantPedagogique).getIdEns();
	//
	// //TeacherDto pedagogicalEncadrantDto = new
	// TeacherDto(pedagogicalEncadrant.getIdEns(), pedagogicalEncadrant.getNomEns(),
	// pedagogicalEncadrant.getTypeEns(), pedagogicalEncadrant.getUp(),
	// pedagogicalEncadrant.getTypeUp(), pedagogicalEncadrant.getMailEns(),
	// pedagogicalEncadrant.getTel1());
	//
	// Boolean isAvailablePE = false;
	// List<String> seances = new ArrayList<String>();
	//
	// checkDisponibilityByUnit(isAvailablePE, seances, selectedStartHour,
	// selectedEndHour);
	//
	//// // System.out.println("----------------------> Result-1: " + selectedDate);
	//// // System.out.println("----------------------> Result-3: " +
	// idPedagocialEncadrant);
	//
	// Teacher pedagogicalEncadrant = null;
	// if(!seances.isEmpty())
	// {
	// pedagogicalEncadrant =
	// weekScheduleRepository.findAvailabilityPedagogicalEncadrant(selectedDate,
	// seances, idPedagocialEncadrant);
	// }
	//
	//// // System.out.println("----------------------> Result-4: " +
	// pedagogicalEncadrant);
	//
	// if(pedagogicalEncadrant == null)
	// {
	// isAvailablePE = true;
	// }
	// else
	// {
	// isAvailablePE = false;
	// }
	//
	//// // System.out.println("----------------------> Result: " + isAvailablePE);
	//
	// // System.out.println("------> Result: " + selectedStartHour + " - " +
	// selectedEndHour + " *** " + seances + " ==> " + isAvailablePE);
	//
	// return isAvailablePE;
	// }

	@GetMapping("/affectClassRoom/{idEt}/{codeSalle}/{date}/{startHour}/{endHour}")
	public void affectClassRoom(@PathVariable String idEt, @PathVariable String codeSalle, @PathVariable String date,
								@PathVariable String startHour, @PathVariable String endHour) throws ParseException {

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

		// System.out.println("------------ IDS: " + idEt + " - " + codeSalle + " - " +
		// date);

		Salle salle = salleRepository.findByCodeSalle(codeSalle);

		Date dateSoutenance = new SimpleDateFormat("dd-MM-yyyy").parse(date);

		fichePFE.setDateSoutenance(dateSoutenance);
		fichePFE.setHeureDebut(startHour);
		fichePFE.setHeureFin(endHour);
		fichePFE.setSalle(salle);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/turnSoutenanceToPlanified/{idEtToSTN}")
	public void turnSoutenanceToPlanified(@PathVariable String idEtToSTN) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEtToSTN).get(0);
		fichePFE.setEtatFiche("06");
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/initiliazeSoutenanceTime/{idEt}")
	public void initiliazeSoutenanceTime(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setDateSoutenance(null);
		fichePFE.setEtatFiche("03");
		fichePFE.setExpertEns(null);
		fichePFE.setHeureDebut(null);
		fichePFE.setHeureFin(null);
		fichePFE.setPresidentJury(null);
		fichePFE.setPresidentJuryEns(null);
		fichePFE.setTraineeKind("01");
		fichePFE.setSalle(null);

		// if(fichePFE.getSalle() != null)
		// {
		// if(!fichePFE.getSalle().getCodeSalle().equalsIgnoreCase("Remote"))
		// {
		// fichePFE.setSalle(null);
		// }
		// }
		fichePFERepository.save(fichePFE);

	}

	@GetMapping("/findDepotFichePFECP/{idEt}")
	public String findDepotFichePFECP(@PathVariable String idEt) {
		String result = "INIT";
		String etatTreatCP = urgRdvPFERepository.findEtatTreatCPForUrgRdvPFEByStudent(idEt);

		System.out.println("--------------------------------> TREAT: " + etatTreatCP);

		if (etatTreatCP != null) {
			result = urgRdvPFERepository.findEtatTreatCPForUrgRdvPFEByStudent(idEt);
		}

		return result;
	}

	@GetMapping("/findDepotFichePFE/{idEt}")
	public UrgRdvPFEDto findDepotFichePFE(@PathVariable String idEt) {
		UrgRdvPFEDto urgRdvPFEDto = null;

		List<Date> depotFichePFEDates = fichePFERepository.findExistDepotFichePFEByStudent(idEt);

		if (!depotFichePFEDates.isEmpty()) {
			urgRdvPFEDto = urgRdvPFERepository.findUrgRdvPFEDtoByStudent(idEt);
			System.out.println("--------------------------> 1: " + urgRdvPFEDto.getNote());
			System.out.println("--------------------------> 2: " + urgRdvPFEDto.getEtatTreatEA());
			System.out.println("--------------------------> 3: " + urgRdvPFEDto.getOnBilanPerDebStg());
			System.out.println("--------------------------> 4: " + urgRdvPFEDto.getOnBilanPerMilStg());
			System.out.println("--------------------------> 5: " + urgRdvPFEDto.getOnBilanPerFinStg());
			System.out.println("--------------------------> 6: " + urgRdvPFEDto.getOnFicheEvalFin());
			System.out.println("--------------------------> 7: " + urgRdvPFEDto.getOnFicheEvalMiPar());
			System.out.println("--------------------------> 8: " + urgRdvPFEDto.getOnJournalBord());
			System.out.println("--------------------------> 9: " + urgRdvPFEDto.getOnPlanningStg());
			System.out.println("--------------------------> 10: " + urgRdvPFEDto.getOnRapportStg());
			System.out.println("--------------------------> 11: " + urgRdvPFEDto.getPresenceKindRDV1());
			System.out.println("--------------------------> 12: " + urgRdvPFEDto.getPresenceKindRDV2());
			System.out.println("--------------------------> 13: " + urgRdvPFEDto.getPresenceKindRDV3());
			System.out.println("--------------------------> 14: " + urgRdvPFEDto.getStatusKindRDV1());
			System.out.println("--------------------------> 15: " + urgRdvPFEDto.getStatusKindRDV2());
			System.out.println("--------------------------> 16: " + urgRdvPFEDto.getStatusKindRDV3());
			System.out.println("--------------------------> 17: " + urgRdvPFEDto.getEtatTreatEA());
		} else {
			urgRdvPFEDto = new UrgRdvPFEDto("EMPTY");
		}

		return urgRdvPFEDto;

	}

	public UrgRdvPFEDto findFilledFicheDepotPFE(String idEt) {
		UrgRdvPFEDto urgRdvPFEDto = urgRdvPFERepository.findUrgRdvPFEDtoByStudent(idEt);
		System.out.println("--------------------------> 1: " + urgRdvPFEDto.getNote());
		System.out.println("--------------------------> 2: " + urgRdvPFEDto.getEtatTreatEA());
		System.out.println("--------------------------> 3: " + urgRdvPFEDto.getOnBilanPerDebStg());
		System.out.println("--------------------------> 4: " + urgRdvPFEDto.getOnBilanPerMilStg());
		System.out.println("--------------------------> 5: " + urgRdvPFEDto.getOnBilanPerFinStg());
		System.out.println("--------------------------> 6: " + urgRdvPFEDto.getOnFicheEvalFin());
		System.out.println("--------------------------> 7: " + urgRdvPFEDto.getOnFicheEvalMiPar());
		System.out.println("--------------------------> 8: " + urgRdvPFEDto.getOnJournalBord());
		System.out.println("--------------------------> 9: " + urgRdvPFEDto.getOnPlanningStg());
		System.out.println("--------------------------> 10: " + urgRdvPFEDto.getOnRapportStg());
		System.out.println("--------------------------> 11: " + urgRdvPFEDto.getPresenceKindRDV1());
		System.out.println("--------------------------> 12: " + urgRdvPFEDto.getPresenceKindRDV2());
		System.out.println("--------------------------> 13: " + urgRdvPFEDto.getPresenceKindRDV3());
		System.out.println("--------------------------> 14: " + urgRdvPFEDto.getStatusKindRDV1());
		System.out.println("--------------------------> 15: " + urgRdvPFEDto.getStatusKindRDV2());
		System.out.println("--------------------------> 16: " + urgRdvPFEDto.getStatusKindRDV3());
		System.out.println("--------------------------> 17: " + urgRdvPFEDto.getEtatTreatEA());

		return urgRdvPFEDto;

	}

	public String findStatusDepotFichePFE(@PathVariable String idEt) {
		String statusDepotFichePFE;

		List<Date> depotFichePFEDates = fichePFERepository.findExistDepotFichePFEByStudent(idEt);

		if (!depotFichePFEDates.isEmpty()) {
			statusDepotFichePFE = "FILLED";
		} else {
			statusDepotFichePFE = "EMPTY";
		}

		return statusDepotFichePFE;
	}

	public String findStatusDepotFichePFEByPC(@PathVariable String idEt) {
		String statusDepotFichePFEByPC;

		List<String> depotFichePFEDates = fichePFERepository.findExistDepotFichePFEWithPCTreatByStudent(idEt);

		if (!depotFichePFEDates.isEmpty()) {
			statusDepotFichePFEByPC = "FILLED";
		} else {
			statusDepotFichePFEByPC = "EMPTY";
		}

		return statusDepotFichePFEByPC;
	}

	@GetMapping("/fillFicheDepotPFE/{idEt}/{notePE}/{onPlanningStg}/{onBilanPerDebStg}/{onBilanPerMilStg}/{onBilanPerFinStg}/{onFicheEvalMiPar}/{onFicheEvalFin}/{onJournalBord}/{onRapportStg}/{dateRDV1}/{dateRDV2}/{dateRDV3}/{presenceKindRDV1}/{presenceKindRDV2}/{presenceKindRDV3}/{satisfactionRDV1}/{satisfactionRDV2}/{satisfactionRDV3}/{etatTreatEA}")
	public void fillFicheDepotPFE(@PathVariable String idEt, @PathVariable BigDecimal notePE,
								  @PathVariable Boolean onPlanningStg, @PathVariable Boolean onBilanPerDebStg,
								  @PathVariable Boolean onBilanPerMilStg, @PathVariable Boolean onBilanPerFinStg,
								  @PathVariable Boolean onFicheEvalMiPar, @PathVariable Boolean onFicheEvalFin,
								  @PathVariable Boolean onJournalBord, @PathVariable Boolean onRapportStg, @PathVariable String dateRDV1,
								  @PathVariable String dateRDV2, @PathVariable String dateRDV3, @PathVariable String presenceKindRDV1,
								  @PathVariable String presenceKindRDV2, @PathVariable String presenceKindRDV3,
								  @PathVariable String satisfactionRDV1, @PathVariable String satisfactionRDV2,
								  @PathVariable String satisfactionRDV3, @PathVariable String etatTreatEA) throws ParseException {
		System.out.println("---------------------------> notePE: " + notePE);
		System.out.println("---------------------------> onPlanningStg: " + onPlanningStg);
		System.out.println("---------------------------> dateRDV1: " + dateRDV1);
		System.out.println("---------------------------> presenceKindRDV1: " + presenceKindRDV1);

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

		if (satisfactionRDV1.equalsIgnoreCase("--")) {
			satisfactionRDV1 = "";
		}
		if (satisfactionRDV2.equalsIgnoreCase("--")) {
			satisfactionRDV2 = "";
		}
		if (satisfactionRDV3.equalsIgnoreCase("--")) {
			satisfactionRDV3 = "";
		}

		UrgRdvPFE urgRdvPFE = null;
		if (fichePFE.getUrgRdvPFE() == null) {
			UrgRdvPFEPK urgRdvPFEPK = new UrgRdvPFEPK(fichePFE.getIdFichePFE(), new Date());
			urgRdvPFE = new UrgRdvPFE(urgRdvPFEPK, notePE, onPlanningStg, onBilanPerDebStg, onBilanPerMilStg,
					onBilanPerFinStg, onFicheEvalMiPar, onFicheEvalFin, onJournalBord, onRapportStg,
					new SimpleDateFormat("dd-MM-yyyy").parse(dateRDV1),
					new SimpleDateFormat("dd-MM-yyyy").parse(dateRDV2),
					new SimpleDateFormat("dd-MM-yyyy").parse(dateRDV3), presenceKindRDV1, presenceKindRDV2,
					presenceKindRDV3, satisfactionRDV1, satisfactionRDV2, satisfactionRDV3, etatTreatEA);
		} else {
			urgRdvPFE = fichePFE.getUrgRdvPFE();
			urgRdvPFE.setNote(notePE);
			urgRdvPFE.setOnPlanningStg(onPlanningStg);
			urgRdvPFE.setOnBilanPerDebStg(onBilanPerDebStg);
			urgRdvPFE.setOnBilanPerMilStg(onBilanPerMilStg);
			urgRdvPFE.setOnBilanPerFinStg(onBilanPerFinStg);
			urgRdvPFE.setOnFicheEvalMiPar(onFicheEvalMiPar);
			urgRdvPFE.setOnFicheEvalFin(onFicheEvalFin);
			urgRdvPFE.setOnJournalBord(onJournalBord);
			urgRdvPFE.setOnRapportStg(onRapportStg);
			urgRdvPFE.setDateRDV1(new SimpleDateFormat("dd-MM-yyyy").parse(dateRDV1));
			urgRdvPFE.setDateRDV2(new SimpleDateFormat("dd-MM-yyyy").parse(dateRDV2));
			urgRdvPFE.setDateRDV3(new SimpleDateFormat("dd-MM-yyyy").parse(dateRDV3));
			urgRdvPFE.setPresenceKindRDV1(presenceKindRDV1);
			urgRdvPFE.setPresenceKindRDV2(presenceKindRDV2);
			urgRdvPFE.setPresenceKindRDV3(presenceKindRDV3);
			urgRdvPFE.setSatisfactionRDV1(satisfactionRDV1);
			urgRdvPFE.setSatisfactionRDV2(satisfactionRDV2);
			urgRdvPFE.setSatisfactionRDV3(satisfactionRDV3);
			urgRdvPFE.setEtatTreatEA(etatTreatEA);
		}

		urgRdvPFERepository.save(urgRdvPFE);

		// fichePFERepository.save(fichePFE);
	}

	@GetMapping("/validateFicheDepotPFEBtCP/{idEt}")
	public void validateFicheDepotPFEBtCP(@PathVariable String idEt) throws ParseException {
		System.out.println("---------------------------> idEt: " + idEt);

		UrgRdvPFE urgRdvPFE = urgRdvPFERepository.findUrgRdvPFEByStudent(idEt);
		urgRdvPFE.setEtatTreatCP("01");

		urgRdvPFERepository.save(urgRdvPFE);
	}

	/******************************************************
	 * Download Files
	 ******************************************************/

	@GetMapping("/downloadStudentPV/{idEt}/{nomet}/{classe}/{juryPresident}/{membre}/{pedagogicalEncadrant}/{stnDate}")
	public ResponseEntity downloadStudentPV(@PathVariable String idEt, @PathVariable String nomet,
											@PathVariable String classe, @PathVariable String juryPresident, @PathVariable String membre,
											@PathVariable String pedagogicalEncadrant, @PathVariable String stnDate) throws IOException {
		// System.out.println("-------------PV---------------------> idEt: " + idEt);
		// System.out.println("-------------PV---------------------> nomet: " + nomet);
		// System.out.println("-------------PV---------------------> classe: " +
		// classe);
		// System.out.println("-------------PV---------------------> juryPresident: " +
		// juryPresident);
		// System.out.println("-------------PV---------------------> expert: " +
		// expert);
		// System.out.println("-------------PV--------------------->
		// pedagogicalEncadrant: " + pedagogicalEncadrant);
		// System.out.println("-------------PV---------------------> stnDate: " +
		// stnDate);

//		String expert = utilServices.findFullNamePedagogicalExpert(idEt);
//		System.out.println(
//				"-------------#########################################---------------------> expert: " + expert);

		String PVPath = "C:\\ESP-DOCS\\";
		String PVName = "Procès Verbal " + nomet + ".pdf";
		String PVFile = PVPath + PVName;

		// AffectationStudentSocieteSujetSocieteDto ass =
		// affectationStudentSocieteRepository.findAffectationStudentSocieteSujetSociete(idEt);

		// System.out.println("-------------PV---------------------> titre: " +
		// ass.getTitreProjet());
		// System.out.println("-------------PV---------------------> societe: " +
		// ass.getLibelleSociete());

		// System.out.println("-------------PV--------------------1-> " + idEt);
		// System.out.println("-------------PV--------------------2-> " + nomet);
		// System.out.println("-------------PV--------------------3-> " + classe);
		// System.out.println("-------------PV--------------------4-> " +
		// ass.getTitreProjet());
		// System.out.println("-------------PV--------------------5-> " +
		// ass.getLibelleSociete());
		// System.out.println("-------------PV--------------------6-> " +
		// juryPresident);
		// System.out.println("-------------PV--------------------7-> " + expert);
		// System.out.println("-------------PV--------------------8-> " +
		// pedagogicalEncadrant);
		// System.out.println("-------------PV--------------------9-> " + stnDate);
		// System.out.println("-------------PV--------------------10-> " + PVFile);

		String expert = utilServices.findFullNamePedagogicalExpert(idEt);

		PVEtudiant_PDF pv = new PVEtudiant_PDF(idEt, nomet, classe,
				fichePFERepository.findTitleFichePFEByStudent(idEt).get(0),
				conventionRepository.findCompanyNameByIdEt(idEt).get(0), expert, juryPresident, membre,
				pedagogicalEncadrant, stnDate, PVFile);

		File file = new File(PVFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PVName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

	}

	@GetMapping("/downloadBinomePV/{idStu}/{idEt}/{nomet}/{classe}/{juryPresident}/{membre}/{pedagogicalEncadrant}/{stnDate}")
	public ResponseEntity downloadBinomePV(@PathVariable String idStu, @PathVariable String idEt,
										   @PathVariable String nomet, @PathVariable String classe, @PathVariable String juryPresident,
										   @PathVariable String membre, @PathVariable String pedagogicalEncadrant, @PathVariable String stnDate)
			throws IOException {
		// System.out.println("-------------PV---------------------> idEt: " + idEt);
		// System.out.println("-------------PV---------------------> nomet: " + nomet);
		// System.out.println("-------------PV---------------------> classe: " +
		// classe);
		// System.out.println("-------------PV---------------------> juryPresident: " +
		// juryPresident);
		// System.out.println("-------------PV---------------------> expert: " +
		// expert);
		// System.out.println("-------------PV--------------------->
		// pedagogicalEncadrant: " + pedagogicalEncadrant);
		// System.out.println("-------------PV---------------------> stnDate: " +
		// stnDate);

		String PVPath = "C:\\ESP-DOCS\\";
		String PVName = "Procès Verbal " + nomet + ".pdf";
		String PVFile = PVPath + PVName;

		// AffectationStudentSocieteSujetSocieteDto ass =
		// affectationStudentSocieteRepository.findAffectationStudentSocieteSujetSociete(idStu);

		// System.out.println("-------------PV---------------------> titre: " +
		// ass.getTitreProjet());
		// System.out.println("-------------PV---------------------> societe: " +
		// ass.getLibelleSociete());

		// System.out.println("-------------PV--------------------1-> " + idEt);
		// System.out.println("-------------PV--------------------2-> " + nomet);
		// System.out.println("-------------PV--------------------3-> " + classe);
		// System.out.println("-------------PV--------------------4.0-> " +
		// fichePFERepository.findTitleFichePFEByStudent(idStu).size());
		// System.out.println("-------------PV--------------------4-> " +
		// fichePFERepository.findTitleFichePFEByStudent(idStu).get(0));
		// System.out.println("-------------PV--------------------5-> " +
		// conventionRepository.findCompanyNameByIdEt(idStu).get(0));
		// System.out.println("-------------PV--------------------6-> " +
		// juryPresident);
		// System.out.println("-------------PV--------------------7-> " + expert);
		// System.out.println("-------------PV--------------------8-> " +
		// pedagogicalEncadrant);
		// System.out.println("-------------PV--------------------9-> " + stnDate);
		// System.out.println("-------------PV--------------------10-> " + PVFile);

		String expert = utilServices.findFullNamePedagogicalExpert(idEt);

		PVEtudiant_PDF pv = new PVEtudiant_PDF(idEt, nomet, classe,
				fichePFERepository.findTitleFichePFEByStudent(idEt).get(0),
				conventionRepository.findCompanyNameByIdEt(idEt).get(0), expert, juryPresident, membre,
				pedagogicalEncadrant, stnDate, PVFile);

		File file = new File(PVFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PVName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

	}

	@GetMapping("/downloadPlanningSoutenanceExcel/{idSession}/{idRespServiceStage}")
	public ResponseEntity downloadPlanningSoutenanceExcel(@PathVariable Integer idSession,
														  @PathVariable String idRespServiceStage) throws IOException {
		String sessionLabel = sessionRepository.findByIdSession(1).getLibelleSession();

		// System.out.println("---------- Download Excel -------> idSession: " +
		// idSession);
		Set<String> idStudents = fichePFERepository.findAllAuthorizedStudentsForSoutenance(idSession,
				idRespServiceStage);
		// System.out.println("--------- Download Excel --------> Size - idSession: " +
		// idStudents.size());
		List<StudentSTNExcelDto> studentsDtos = new ArrayList<StudentSTNExcelDto>();

		for (String idStudent : idStudents) {
			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String idEncadrantPedagogique = utilServices.findIdEncadrantPedagogiqueByStudent(idStudent);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateDepot = dateFormat.format(fichePFE.getIdFichePFE().getDateDepotFiche());

			String dateSoutenance = "--";
			String heureSoutenance = "--";
			if (fichePFE.getDateSoutenance() != null) {
				dateSoutenance = dateFormat.format(fichePFE.getDateSoutenance());// teacherRepository.findByIdEns(idEns);
				heureSoutenance = fichePFE.getHeureDebut();
			}

			String juryPresident = null;
			String mailJuryPresident = null;
			if (fichePFE.getPresidentJuryEns() != null) {
				juryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getNomEns();
				mailJuryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns())
						.getMailEns();
				if (teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getMailEns() == null) {
					mailJuryPresident = "--";
				}
			}
			if (fichePFE.getPresidentJury() != null) {
				juryPresident = fichePFE.getPresidentJury();
				mailJuryPresident = "--";
			}
			if (fichePFE.getPresidentJuryEns() == null && fichePFE.getPresidentJury() == null) {
				juryPresident = "--";
				mailJuryPresident = "--";
			}

			String expert = "--";
			String mailExpert = "--";
			if (fichePFE.getExpertEns() != null) {
				expert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getNomEns();
				mailExpert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getMailEns();
			}

			String pedagogicalEncadrant = "--";
			String mailPedagogicalEncadrant = "--";
			if (idEncadrantPedagogique != null) {
				pedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getNomEns();
				mailPedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getMailEns();
			}

			String salle = "--";
			if (fichePFE.getSalle() != null) {
				salle = fichePFE.getSalle().getCodeSalle();
			}

			StudentExcelDto stu = utilServices.findStudentExcelDtoById(idStudent);

			System.out.println("UNIT STU ------------> idStudent: " + idStudent);

			String studentMail = "--";
			if (stu.getMail() != null) {
				studentMail = stu.getMail();
			}
			System.out.println("UNIT STU ------------> studentMail: " + studentMail);

			String studentPhone = "--";
			if (stu.getPhone() != null) {
				studentPhone = stu.getPhone();
			}
			System.out.println("UNIT STU ------------> studentPhone: " + studentPhone);

			String motifDepotIncomplet = "--";
			if (fichePFE.getValidDepot().equalsIgnoreCase("03") && fichePFE.getObservationDepot() != null) {
				motifDepotIncomplet = fichePFE.getObservationDepot();
			}

			// AffectationStudentSocieteMailPaysDto amp =
			// affectationStudentSocieteRepository.findAffectationStudentSocieteMailPays(idStudent);
			//
			// String pays = "--";
			// String mailSociete = "--";
			// if(amp != null)
			// {
			// pays = amp.getPaysSociete();
			// mailSociete = amp.getEmailSociete();
			// }

			EntrepriseAccueilDesignationPaysByStudentDto eadp = entrepriseAccueilRepository
					.findDesignationPaysByStudent(idStudent);

			String pays = "--";
			String mailSociete = "--";
			if (eadp != null) {
				pays = eadp.getNomPays();
				mailSociete = eadp.getMailEntreprise();
			}
			System.out.println(
					"--------------------------------> SARS: " + idStudent + " - " + pays + " - " + mailSociete);

			System.out.println("UNIT STU ------------> studentFullName: " + stu.getNom() + " - " + stu.getPrenom());

			studentsDtos.add(new StudentSTNExcelDto(idStudent, stu.getNom(), stu.getPrenom(), studentMail, studentPhone,
					currentClass, dateDepot, dateSoutenance, heureSoutenance, salle, juryPresident, mailJuryPresident,
					pedagogicalEncadrant, mailPedagogicalEncadrant, expert, mailExpert, motifDepotIncomplet, pays,
					mailSociete));

		}

		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "Planning Soutenance.xls";
		String PSFile = PSPath + PSName;

		PlanningSoutenanceGlobal_Excel psg = new PlanningSoutenanceGlobal_Excel(studentsDtos, sessionLabel, PSFile);

		File file = new File(PSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@GetMapping("/downloadPlanningSoutenanceNotifiéesExcel/{idSession}/{idRespServiceStage}")
	public ResponseEntity downloadPlanningSoutenanceNotifiéesExcel(@PathVariable Integer idSession,
																   @PathVariable String idRespServiceStage) throws IOException {
		String sessionLabel = sessionRepository.findByIdSession(1).getLibelleSession();

		// System.out.println("---------- Download Excel -------> idSession: " +
		// idSession);
		Set<String> idStudents = fichePFERepository.findAllStudentsDoneSoutenance(idSession, idRespServiceStage);
		// System.out.println("--------- Download Excel --------> Size - idSession: " +
		// idStudents.size());
		List<StudentSTNExcelDto> studentsDtos = new ArrayList<StudentSTNExcelDto>();

		for (String idStudent : idStudents) {
			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String idEncadrantPedagogique = utilServices.findIdEncadrantPedagogiqueByStudent(idStudent);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateDepot = dateFormat.format(fichePFE.getIdFichePFE().getDateDepotFiche());

			String dateSoutenance = "--";
			String heureSoutenance = "--";
			if (fichePFE.getDateSoutenance() != null) {
				dateSoutenance = dateFormat.format(fichePFE.getDateSoutenance());// teacherRepository.findByIdEns(idEns);
				heureSoutenance = fichePFE.getHeureDebut();
			}

			String juryPresident = null;
			String mailJuryPresident = null;
			if (fichePFE.getPresidentJuryEns() != null) {
				juryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getNomEns();
				mailJuryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns())
						.getMailEns();
				if (teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getMailEns() == null) {
					mailJuryPresident = "--";
				}
			}
			if (fichePFE.getPresidentJury() != null) {
				juryPresident = fichePFE.getPresidentJury();
				mailJuryPresident = "--";
			}
			if (fichePFE.getPresidentJuryEns() == null && fichePFE.getPresidentJury() == null) {
				juryPresident = "--";
				mailJuryPresident = "--";
			}

			String expert = "--";
			String mailExpert = "--";
			if (fichePFE.getExpertEns() != null) {
				expert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getNomEns();
				mailExpert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getMailEns();
			}

			String pedagogicalEncadrant = "--";
			String mailPedagogicalEncadrant = "--";
			if (idEncadrantPedagogique != null) {
				pedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getNomEns();
				mailPedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getMailEns();
			}

			String salle = "--";
			if (fichePFE.getSalle() != null) {
				salle = fichePFE.getSalle().getCodeSalle();
			}

			StudentExcelDto stu = utilServices.findStudentExcelDtoById(idStudent);

			String studentMail = "--";
			if (stu.getMail() != null) {
				studentMail = stu.getMail();
			}

			String studentPhone = "--";
			if (stu.getPhone() != null) {
				studentPhone = stu.getPhone();
			}

			String motifDepotIncomplet = "--";
			if (fichePFE.getValidDepot().equalsIgnoreCase("03") && fichePFE.getObservationDepot() != null) {
				motifDepotIncomplet = fichePFE.getObservationDepot();
			}

			// AffectationStudentSocieteMailPaysDto amp =
			// affectationStudentSocieteRepository.findAffectationStudentSocieteMailPays(idStudent);
			//
			// String pays = "--";
			// String mailSociete = "--";
			// if(amp != null)
			// {
			// pays = amp.getPaysSociete();
			// mailSociete = amp.getEmailSociete();
			// }

			EntrepriseAccueilDesignationPaysByStudentDto eadp = entrepriseAccueilRepository
					.findDesignationPaysByStudent(idStudent);

			String pays = "--";
			String mailSociete = "--";
			if (eadp != null) {
				pays = eadp.getNomPays();
				mailSociete = eadp.getMailEntreprise();
			}
			System.out.println(
					"--------------------------------> SARS: " + idStudent + " - " + pays + " - " + mailSociete);

			studentsDtos.add(new StudentSTNExcelDto(idStudent, stu.getNom(), stu.getPrenom(), studentMail, studentPhone,
					currentClass, dateDepot, dateSoutenance, heureSoutenance, salle, juryPresident, mailJuryPresident,
					pedagogicalEncadrant, mailPedagogicalEncadrant, expert, mailExpert, motifDepotIncomplet, pays,
					mailSociete));

		}

		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "Planning Soutenances Notifiées.xls";
		String PSFile = PSPath + PSName;

		PlanningSoutenanceGlobal_Excel psg = new PlanningSoutenanceGlobal_Excel(studentsDtos, sessionLabel, PSFile);

		File file = new File(PSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@GetMapping("/downloadPlanningSoutenancePlanifiéesExcel/{idSession}/{idRespServiceStage}")
	public ResponseEntity downloadPlanningSoutenancePlanifiéesExcel(@PathVariable Integer idSession,
																	@PathVariable String idRespServiceStage) throws IOException {
		String sessionLabel = sessionRepository.findByIdSession(1).getLibelleSession();

		// System.out.println("---------- Download Excel -------> idSession: " +
		// idSession);
		Set<String> idStudents = fichePFERepository.findAllStudentsPlanifiedSoutenance(idSession, idRespServiceStage);
		// System.out.println("--------- Download Excel --------> Size - idSession: " +
		// idStudents.size());
		List<StudentSTNExcelDto> studentsDtos = new ArrayList<StudentSTNExcelDto>();

		for (String idStudent : idStudents) {
			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String idEncadrantPedagogique = utilServices.findIdEncadrantPedagogiqueByStudent(idStudent);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateDepot = dateFormat.format(fichePFE.getIdFichePFE().getDateDepotFiche());

			String dateSoutenance = "--";
			String heureSoutenance = "--";
			if (fichePFE.getDateSoutenance() != null) {
				dateSoutenance = dateFormat.format(fichePFE.getDateSoutenance());// teacherRepository.findByIdEns(idEns);
				heureSoutenance = fichePFE.getHeureDebut();
			}

			String juryPresident = null;
			String mailJuryPresident = null;
			if (fichePFE.getPresidentJuryEns() != null) {
				juryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getNomEns();
				mailJuryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns())
						.getMailEns();
				if (teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getMailEns() == null) {
					mailJuryPresident = "--";
				}
			}
			if (fichePFE.getPresidentJury() != null) {
				juryPresident = fichePFE.getPresidentJury();
				mailJuryPresident = "--";
			}
			if (fichePFE.getPresidentJuryEns() == null && fichePFE.getPresidentJury() == null) {
				juryPresident = "--";
				mailJuryPresident = "--";
			}

			String expert = "--";
			String mailExpert = "--";
			if (fichePFE.getExpertEns() != null) {
				expert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getNomEns();
				mailExpert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getMailEns();
			}

			String pedagogicalEncadrant = "--";
			String mailPedagogicalEncadrant = "--";
			if (idEncadrantPedagogique != null) {
				pedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getNomEns();
				mailPedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getMailEns();
			}

			String salle = "--";
			if (fichePFE.getSalle() != null) {
				salle = fichePFE.getSalle().getCodeSalle();
			}

			StudentExcelDto stu = utilServices.findStudentExcelDtoById(idStudent);

			String studentMail = "--";
			if (stu.getMail() != null) {
				studentMail = stu.getMail();
			}

			String studentPhone = "--";
			if (stu.getPhone() != null) {
				studentPhone = stu.getPhone();
			}

			String motifDepotIncomplet = "--";
			if (fichePFE.getValidDepot().equalsIgnoreCase("03") && fichePFE.getObservationDepot() != null) {
				motifDepotIncomplet = fichePFE.getObservationDepot();
			}

			// AffectationStudentSocieteMailPaysDto amp =
			// affectationStudentSocieteRepository.findAffectationStudentSocieteMailPays(idStudent);

			EntrepriseAccueilDesignationPaysByStudentDto eadp = entrepriseAccueilRepository
					.findDesignationPaysByStudent(idStudent);

			String pays = "--";
			String mailSociete = "--";
			if (eadp != null) {
				pays = eadp.getNomPays();
				mailSociete = eadp.getMailEntreprise();
			}
			System.out.println(
					"--------------------------------> SARS: " + idStudent + " - " + pays + " - " + mailSociete);

			studentsDtos.add(new StudentSTNExcelDto(idStudent, stu.getNom(), stu.getPrenom(), studentMail, studentPhone,
					currentClass, dateDepot, dateSoutenance, heureSoutenance, salle, juryPresident, mailJuryPresident,
					pedagogicalEncadrant, mailPedagogicalEncadrant, expert, mailExpert, motifDepotIncomplet, pays,
					mailSociete));

		}

		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "Planning Soutenances PLanifiées.xls";
		String PSFile = PSPath + PSName;

		PlanningSoutenanceGlobal_Excel psg = new PlanningSoutenanceGlobal_Excel(studentsDtos, sessionLabel, PSFile);

		File file = new File(PSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@GetMapping("/downloadPlanningSoutenanceExcelByFilter/{filteredIdentifiants}")
	public ResponseEntity downloadPlanningSoutenanceExcelByFilter(@PathVariable List<String> filteredIdentifiants)
			throws IOException {
		String sessionLabel = sessionRepository.findByIdSession(1).getLibelleSession();

		List<StudentSTNExcelDto> studentsDtos = new ArrayList<StudentSTNExcelDto>();

		for (String idStudent : filteredIdentifiants) {
			// System.out.println("----------------------------------------------->
			// StudentCJ: " + idStudent);

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			String idEncadrantPedagogique = utilServices.findIdEncadrantPedagogiqueByStudent(idStudent);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateDepot = dateFormat.format(fichePFE.getIdFichePFE().getDateDepotFiche());

			String dateSoutenance = "--";
			String heureSoutenance = "--";
			if (fichePFE.getDateSoutenance() != null) {
				dateSoutenance = dateFormat.format(fichePFE.getDateSoutenance());// teacherRepository.findByIdEns(idEns);
				heureSoutenance = fichePFE.getHeureDebut();
			}

			String juryPresident = null;
			String mailJuryPresident = null;
			if (fichePFE.getPresidentJuryEns() != null) {
				juryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getNomEns();
				mailJuryPresident = teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns())
						.getMailEns();
				if (teacherRepository.findByIdEns(fichePFE.getPresidentJuryEns().getIdEns()).getMailEns() == null) {
					mailJuryPresident = "--";
				}
			}
			if (fichePFE.getPresidentJury() != null) {
				juryPresident = fichePFE.getPresidentJury();
				mailJuryPresident = "--";
			}
			if (fichePFE.getPresidentJuryEns() != null && fichePFE.getPresidentJury() != null) {
				juryPresident = "--";
				mailJuryPresident = "--";
			}

			String expert = "--";
			String mailExpert = "--";
			if (fichePFE.getExpertEns() != null) {
				expert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getNomEns();
				mailExpert = teacherRepository.findByIdEns(fichePFE.getExpertEns().getIdEns()).getMailEns();
			}

			String pedagogicalEncadrant = "--";
			String mailPedagogicalEncadrant = "--";
			if (idEncadrantPedagogique != null) {
				pedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getNomEns();
				mailPedagogicalEncadrant = teacherRepository.findByIdEns(idEncadrantPedagogique).getMailEns();
			}

			String salle = "--";
			if (fichePFE.getSalle() != null) {
				salle = fichePFE.getSalle().getCodeSalle();
			}

			StudentExcelDto stu = utilServices.findStudentExcelDtoById(idStudent);

			String studentMail = "--";
			if (stu.getMail() != null) {
				studentMail = stu.getMail();
			}

			String studentPhone = "--";
			if (stu.getPhone() != null) {
				studentPhone = stu.getPhone();
			}

			String motifDepotIncomplet = "--";
			if (fichePFE.getValidDepot().equalsIgnoreCase("03") && fichePFE.getObservationDepot() != null) {
				motifDepotIncomplet = fichePFE.getObservationDepot();
			}

			// AffectationStudentSocieteMailPaysDto amp =
			// affectationStudentSocieteRepository.findAffectationStudentSocieteMailPays(idStudent);
			//
			// String pays = "--";
			// String mailSociete = "--";
			// if(amp != null)
			// {
			// pays = amp.getPaysSociete();
			// mailSociete = amp.getEmailSociete();
			// }

			EntrepriseAccueilDesignationPaysByStudentDto eadp = entrepriseAccueilRepository
					.findDesignationPaysByStudent(idStudent);

			String pays = "--";
			String mailSociete = "--";
			if (eadp != null) {
				pays = eadp.getNomPays();
				mailSociete = eadp.getMailEntreprise();
			}
			System.out.println(
					"--------------------------------> SARS: " + idStudent + " - " + pays + " - " + mailSociete);

			studentsDtos.add(new StudentSTNExcelDto(idStudent, stu.getNom(), stu.getPrenom(), studentMail, studentPhone,
					currentClass, dateDepot, dateSoutenance, heureSoutenance, salle, juryPresident, mailJuryPresident,
					pedagogicalEncadrant, mailPedagogicalEncadrant, expert, mailExpert, motifDepotIncomplet, pays,
					mailSociete));

		}

		/************************************************************************************/

		// String filename = "C:\\PlanningSoutenanceSuivantFiltre.xls";
		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "Planning Soutenances suivant Filtre.xls";
		String PSFile = PSPath + PSName;

		PlanningSoutenanceByFilter_Excel psf = new PlanningSoutenanceByFilter_Excel(studentsDtos, sessionLabel, PSFile);

		File file = new File(PSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@GetMapping("/studentTreatDepot/{idEt}/{classe}")
	public StudentTreatDepotDto findStudentTreatDepotById(@PathVariable String idEt, @PathVariable String classe) {

		String department = utilServices.findDepartmentByClass(classe);
		String option = utilServices.findOptionByClass(classe, optionRepository.listOptionsByYear("2021"));

		String juryPresidentExpert = fichePFERepository.findJuryPresidentExpertByStudent(idEt).get(0);
		System.out.println("-----------------------> juryPresidentExpert: " + juryPresidentExpert);

		String jpmn = null;
		String expn = null;
		String jpnn = juryPresidentExpert.substring(0, juryPresidentExpert.lastIndexOf("JPE"));
		String jpei = juryPresidentExpert.substring(juryPresidentExpert.lastIndexOf("JPE") + 4,
				juryPresidentExpert.lastIndexOf("EXP"));
		String expi = juryPresidentExpert.substring(juryPresidentExpert.lastIndexOf("EXP") + 4,
				juryPresidentExpert.length());

		if (!jpnn.equalsIgnoreCase("")) {
			jpmn = jpnn;
		}
		if (jpnn.equalsIgnoreCase("")) {
			jpmn = teacherRepository.findNameTeacherById(jpei);
		}

		expn = teacherRepository.findNameTeacherById(expi);

		// System.out.println("------------LOL---------------------GOT ENTITY: " +
		// department + " - " + option + " - " + societeLibelle + " - " + titreProjet +
		// " - " + ass.getDateDebutStage() + " - " + ass.getDateFinStage());

		// String dateDebutDateFinStg =
		// conventionRepository.findDateAndEtatConventionByIdEt(idEt).get(0);

		// findConventionCompanyLabelDateDebutFinStageDtoByIdEt

		// ConventionCompanyLabelDateDebutFinStageDto convCLDDF =
		// conventionRepository.findConventionCompanyLabelDateDebutFinStageDtoByIdEt(idEt).get(0);

		String companyName = conventionRepository.findCompanyNameByIdEt(idEt).get(0);

		List<Object[]> lcs = conventionRepository.findConventionDateDebutFinStage(idEt);
		List<Object[]> las = avenantRepository.findAvenantDateDebutFinStage(idEt);

		String dateDebutStage = "--";
		String dateFinStage = "--";
		if (las.isEmpty()) {
			for (int i = 0; i < lcs.size(); i++) {
				dateDebutStage = (String) lcs.get(i)[0];
				dateFinStage = (String) lcs.get(i)[1];
			}
		}

		if (!las.isEmpty()) {
			for (int i = 0; i < las.size(); i++) {
				dateDebutStage = (String) las.get(i)[0];
				dateFinStage = (String) las.get(i)[1];
			}
		}

		StudentTreatDepotDto studentTreatDepotDto = new StudentTreatDepotDto(department, option, companyName,
				fichePFERepository.findTitleFichePFEByStudent(idEt).get(0), jpmn, expn, dateDebutStage, dateFinStage);

		return studentTreatDepotDto;
	}

	@GetMapping("/downloadFicheDepotPFE/{idEt}/{classe}/{nomet}/{pairClass}")
	public ResponseEntity downloadFicheDepotPFE(@PathVariable String idEt, @PathVariable String classe,
												@PathVariable String nomet, @PathVariable String pairClass) throws IOException {
		// System.out.println("-------------PV---------------------> idEt: " + idEt);
		// System.out.println("-------------PV---------------------> nomet: " + nomet);
		// System.out.println("-------------PV---------------------> classe: " +
		// classe);
		// System.out.println("-------------PV---------------------> juryPresident: " +
		// juryPresident);
		// System.out.println("-------------PV---------------------> expert: " +
		// expert);
		// System.out.println("-------------PV--------------------->
		// pedagogicalEncadrant: " + pedagogicalEncadrant);
		// System.out.println("-------------PV---------------------> stnDate: " +
		// stnDate);

		String FDPPath = "C:\\ESP-DOCS\\";
		String FDPName = "Fiche Dépôt PFE " + nomet + ".pdf";
		String FDPFile = FDPPath + FDPName;

		UrgRdvPFEDto urgRdvPFEDto = urgRdvPFERepository.findUrgRdvPFEDtoByStudent(idEt);

		String studentClass = classe;
		if (!pairClass.equalsIgnoreCase("--")) {
			studentClass = pairClass;
		}

		StudentTreatDepotDto studentTreatDepotDto = findStudentTreatDepotByHisId(idEt, studentClass);

		// System.out.println("-------------PV---------------------> 1: " +
		// urgRdvPFEDto.getOnPlanningStg());
		// System.out.println("-------------PV---------------------> 2: " +
		// urgRdvPFEDto.getOnBilanPerDebStg());
		// System.out.println("-------------PV---------------------> 3: " +
		// urgRdvPFEDto.getOnBilanPerMilStg());
		// System.out.println("-------------PV---------------------> 4: " +
		// urgRdvPFEDto.getOnFicheEvalMiPar());
		// System.out.println("-------------PV---------------------> 5: " +
		// urgRdvPFEDto.getOnFicheEvalFin());
		// System.out.println("-------------PV---------------------> 6: " +
		// urgRdvPFEDto.getOnBilanPerFinStg());
		// System.out.println("-------------PV---------------------> 7: " +
		// urgRdvPFEDto.getOnJournalBord());
		// System.out.println("-------------PV---------------------> 8: " +
		// urgRdvPFEDto.getOnRapportStg());
		// System.out.println("-------------PV---------------------> titre: " +
		// ass.getTitreProjet());
		// System.out.println("-------------PV---------------------> societe: " +
		// ass.getLibelleSociete());

		// System.out.println("-------------PV--------------------1-> " + idEt);
		// System.out.println("-------------PV--------------------2-> " + nomet);
		// System.out.println("-------------PV--------------------3-> " + classe);
		// System.out.println("-------------PV--------------------4-> " +
		// ass.getTitreProjet());
		// System.out.println("-------------PV--------------------5-> " +
		// ass.getLibelleSociete());
		// System.out.println("-------------PV--------------------6-> " +
		// juryPresident);
		// System.out.println("-------------PV--------------------7-> " + expert);
		// System.out.println("-------------PV--------------------8-> " +
		// pedagogicalEncadrant);
		// System.out.println("-------------PV--------------------9-> " + stnDate);
		// System.out.println("-------------PV--------------------10-> " + PVFile);

		// System.out.println("-------------PV--------------------1-> " +
		// inscriptionRepository.findEncadrantPedagogiqueByStudent(idEt).get(0));
		// System.out.println("------PV--------2-> " +
		// teacherRepository.findNameTeacherById(utilServices.findIdEncadrantPedagogiqueByStudent(idEt)));
		// System.out.println("------PV--------3-> " +
		// studentTreatDepotDto.getOption());
		// System.out.println("------PV--------4-> " +
		// pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByCodeOption(studentTreatDepotDto.getOption()));
		// System.out.println("------PV--------5-> " +
		// teacherRepository.findNameTeacherById(pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByCodeOption(studentTreatDepotDto.getOption())));

		String pedagogicalEncadrant = teacherRepository
				.findNameTeacherById(utilServices.findIdEncadrantPedagogiqueByStudent(idEt));
		String academicCoordiantor = teacherRepository
				.findNameTeacherById(pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByCodeOption(
						utilServices.findOptionByClass(classe, optionRepository.listOptionsByYear("2021"))));

		// System.out.println("-------------PV----------------------------------6-> " +
		// pedagogicalEncadrant);
		// System.out.println("-------------PV----------------------------------7-> " +
		// academicCoordiantor);

		System.out.println("-------PV------1-> " + idEt);
		System.out.println("-------PV------2-> " + nomet);
		System.out.println("-------PV------3-> " + studentClass);
		System.out.println("-------PV------4-> " + pedagogicalEncadrant);
		System.out.println("-------PV------5-> " + academicCoordiantor);
		System.out.println("-------PV------6-> " + studentTreatDepotDto.getSocieteLibelle());
		System.out.println("-------PV------7-> " + urgRdvPFEDto.getDateDepot());
		System.out.println("-------PV------8-> " + FDPFile);

		FicheDepotPFE_PDF fdp = new FicheDepotPFE_PDF(idEt, nomet, studentClass, pedagogicalEncadrant,
				academicCoordiantor, studentTreatDepotDto, urgRdvPFEDto, FDPFile);

		File file = new File(FDPFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FDPName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

	}

	@GetMapping("/etatFicheByStudent/{idEt}")
	public String findEtatFicheByStudent(@PathVariable String idEt) {
		List<String> efs = fichePFERepository.findFichePFEStatus(idEt);
		String etat = "";
		if (!efs.isEmpty()) {
			etat = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("101", efs.get(0));
		}
		return etat;
	}

	@GetMapping("/etatFicheByCode/{codeStatus}")
	public String findEtatFicheByCode(@PathVariable String codeStatus) {
		return codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("101", codeStatus);
	}

	/******************************************************
	 * Pedagogical Encadrant
	 ******************************************************/
	// @GetMapping("/studentsTrainedByPE/{mailEns}")
	// public List<StudentTrainedByAEDto> findStudentsByPE(@PathVariable String
	// mailEns)
	// {
	// List<String> idStudentCJs =
	// teacherRepository.findStudentsByPE(teacherRepository.findIdTeacherByMailEns(mailEns));
	// List<String> idStudentCSs =
	// teacherRepository.findStudentsCSByPE(teacherRepository.findIdTeacherByMailEns(mailEns));
	//
	// System.out.println("------- Encadrant Pédagogique Nbr Students CDJ: " +
	// idStudentCJs.size());
	// System.out.println("------- Encadrant Pédagogique Nbr Students CDS: " +
	// idStudentCSs.size());
	//
	// idStudentCJs.addAll(idStudentCSs);
	//
	// System.out.println("------- Encadrant Pédagogique Nbr Students CDJ + CDS: " +
	// idStudentCJs.size());
	//
	// List<StudentTrainedByAEDto> studentsDtos = new
	// ArrayList<StudentTrainedByAEDto>();
	//
	// for(String idStudent : idStudentCJs)
	// {
	//
	// String currentClass = null;
	// List<String> currentClassCJ =
	// inscriptionRepository.findCurrentClassByIdEt(idStudent);
	// List<String> currentClassCS =
	// inscriptionRepository.findCurrentClassCSByIdEt(idStudent);
	//
	// System.out.println("----------------------------------> Class CJ: " +
	// currentClassCJ.size() + " --- CS: " + currentClassCS.size());
	//
	// if(!currentClassCJ.isEmpty())
	// {
	// currentClass = currentClassCJ.get(0);
	// }
	// if(!currentClassCS.isEmpty())
	// {
	// currentClass = currentClassCS.get(0);
	// }
	//
	// System.out.println("---------------------------------------------> STU: " +
	// idStudent);
	// List<FichePFE> lfps = fichePFERepository.findFichePFEByStudent(idStudent);
	//
	// // System.out.println("---------------------------------------------> STU-1:
	// " + lfps.size());
	//
	// String sessionLabel = "--";
	// String etatTraitementDepot = "EN ATTENTE";
	// String etatDepotFiche = "PAS ENCORE";
	// Date dateDepot = null;
	//
	// if(!lfps.isEmpty())
	// {
	// FichePFE fichePFE =
	// fichePFERepository.findFichePFEByStudent(idStudent).get(0);
	//
	// if(fichePFE.getDateDepotReports() != null)
	// {
	// dateDepot = fichePFE.getDateDepotReports();
	// }
	//
	// if(fichePFE.getUrgRdvPFE() != null)
	// {
	// if(fichePFE.getUrgRdvPFE().getEtatTreatEA().equalsIgnoreCase("02"))
	// {
	// etatTraitementDepot = "TRAITE";
	// }
	// }
	//
	// System.out.println("-------------------> LOL: " +
	// fichePFE.getIdFichePFE().getConventionPK().getIdEt() + " - " +
	// fichePFE.getValidDepot());
	//
	//// if(
	//// (
	//// fichePFE.getEtatFiche().equalsIgnoreCase("03")
	//// ||
	//// fichePFE.getEtatFiche().equalsIgnoreCase("06")
	//// ||
	//// fichePFE.getEtatFiche().equalsIgnoreCase("07")
	//// ||
	//// fichePFE.getEtatFiche().equalsIgnoreCase("08")
	//// )
	//// &&
	//// fichePFE.getValidDepot() == null
	//// )
	//// {
	//// etatDepotFiche = "PAS ENCORE";
	//// }
	// if (fichePFE.getValidDepot() != null)
	// {
	// if
	// (
	// (
	// fichePFE.getEtatFiche().equalsIgnoreCase("03")
	// ||
	// fichePFE.getEtatFiche().equalsIgnoreCase("06")
	// ||
	// fichePFE.getEtatFiche().equalsIgnoreCase("07")
	// ||
	// fichePFE.getEtatFiche().equalsIgnoreCase("08")
	// )
	// &&
	// fichePFE.getValidDepot().equalsIgnoreCase("01")
	// )
	// {
	// etatDepotFiche = "DEPOSE";
	// }
	// if
	// (
	// (
	// fichePFE.getEtatFiche().equalsIgnoreCase("03")
	// ||
	// fichePFE.getEtatFiche().equalsIgnoreCase("06")
	// ||
	// fichePFE.getEtatFiche().equalsIgnoreCase("07")
	// ||
	// fichePFE.getEtatFiche().equalsIgnoreCase("08")
	// )
	// &&
	// fichePFE.getValidDepot().equalsIgnoreCase("02")
	// )
	// {
	// etatDepotFiche = "VALIDE";
	// }
	// }
	//// else
	//// {
	//// etatDepotFiche = "UNKNOWN";
	//// }
	//
	// System.out.println("-------------------> RES: " + etatDepotFiche);
	//
	// if(fichePFE.getSession() != null)
	// {
	// sessionLabel =
	// sessionRepository.findByIdSession(fichePFE.getSession().getIdSession()).getLibelleSession();
	// }
	//
	//
	// }
	//
	// System.out.println("---------------------> SARS-1: " + idStudent + " - " +
	// dateDepot + " - " + etatDepotFiche + " - " + etatTraitementDepot);
	//
	// studentsDtos.add(new StudentTrainedByAEDto(idStudent,
	// utilServices.findStudentFullNameById(idStudent), currentClass, dateDepot,
	// sessionLabel, etatDepotFiche, etatTraitementDepot));
	//
	//
	// }
	// studentsDtos.sort(Comparator.comparing(StudentTrainedByAEDto::getFullName));
	// return studentsDtos;
	// }

	// @GetMapping("/studentsTrainedByPCE/{option}")
	// public List<StudentTrainedByPEDto> findStudentsByPCE(@PathVariable String
	// option)
	// {
	// System.out.println("1-----------------------UNIT-Option--> " + option);
	// System.out.println("1-----------------------UNIT-Option--> " + option);
	// String pureOpt = option.replace("_01", "");
	// System.out.println("2-----------------------UNIT-pureOpt--> " + pureOpt);
	//
	// List<Object[]> objs = optionRepository.findStudentsByCodeOption(pureOpt);
	//
	// List<String> idStudents =
	// optionRepository.utilServices.findStudentsByOption(pureOpt);
	//
	//
	// for(int i = 0; i<objs.size(); i++)
	// {
	// System.out.println("3-----------------------UNIT---> " + (String)
	// objs.get(i)[0] + " - " + (String) objs.get(i)[1] + "----->" +
	// objs.size() + " - " + idStudents.size());
	// }
	//
	//
	// List<StudentTrainedByPEDto> studentsDtos = new
	// ArrayList<StudentTrainedByPEDto>();
	// for(String idStudent : idStudents)
	// {
	//
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
	// String currentClass =
	// inscriptionRepository.utilServices.findCurrentClassByIdEt(idStudent).get(0);
	//
	//
	// //System.out.println("---------------------------------------------> STU: " +
	// idStudent);
	// List<String> lfps = fichePFERepository.findFichePFEByStudentUNIT(idStudent);
	//
	// // System.out.println("---------------------------------------------> STU-1:
	// " + lfps.size());
	//
	// String sessionLabel = "--";
	// String etatTraitementDepot = "EN ATTENTE";
	// String etatDepotFiche = "NON DEPOSEE";
	// String dateDepot = "--";
	//
	// if(!lfps.isEmpty())
	// {
	// FichePFE fichePFE =
	// fichePFERepository.findFichePFEByStudent(idStudent).get(0);
	//
	// if(fichePFE.getDateDepotReports() != null)
	// {
	// dateDepot = dateFormata.format(fichePFE.getDateDepotReports());
	// }
	//
	// if(fichePFE.getUrgRdvPFE() != null)
	// {
	// if(fichePFE.getUrgRdvPFE().getEtatTreatCP() != null)
	// {
	// etatTraitementDepot = "TRAITÉ";
	// }
	// }
	//
	// etatDepotFiche =
	// codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("101",
	// fichePFE.getEtatFiche());
	//
	// sessionLabel =
	// sessionRepository.findByIdSession(fichePFE.getSession().getIdSession()).getLibelleSession();
	// }
	//
	// System.out.println("4---------------------> SARS-1: " + idStudent + " - " +
	// dateDepot + " - " + etatDepotFiche + " - " + etatTraitementDepot);
	//
	// studentsDtos.add(new StudentTrainedByPEDto(idStudent,
	// studentRepository.utilServices.findStudentFullNameById(idStudent),
	// currentClass, dateDepot, sessionLabel, etatDepotFiche, etatTraitementDepot));
	// System.out.println("5-----------------------UNIT-pureOpt--> ");
	// }
	// System.out.println("6-----------------------UNIT-pureOpt--> ");
	// return studentsDtos;
	// }

	@GetMapping("/updateBilanFirstVersion/{idEt}")
	public void updateBilanFirstVersion(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathBilan1(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateBilanSecondVersion/{idEt}")
	public void updateBilanSecondVersion(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathBilan2(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateBilanThirdVersion/{idEt}")
	public void updateBilanThirdVersion(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathBilan3(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateFichePFEDepot/{idEt}")
	public void updatefichePFEDepot(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathRapportVersion2(null);
		fichePFE.setPathPlagiat(null);
		fichePFE.setPathAttestationStage(null);
		fichePFE.setPathSupplement(null);
		fichePFE.setValidDepot("04");

		fichePFERepository.save(fichePFE);

	}

	@GetMapping("/updateReportFinalVersion/{idEt}")
	public void updatefichePFEDepotRFV(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathRapportVersion2(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateReportLaunchVersion/{idEt}")
	public void updateReportLaunchVersion(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathRapportVersion1(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateReportAntiPlagiat/{idEt}")
	public void updatefichePFEDepotRAP(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathPlagiat(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateAttestationStagePFE/{idEt}")
	public void updatefichePFEDepotASP(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathAttestationStage(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateSupplement/{idEt}")
	public void updatefichePFEDepotSupplement(@PathVariable String idEt) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setPathSupplement(null);
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/updateTraineeKind/{idEt}/{traineeKind}")
	public String updateTraineeKind(@PathVariable String idEt, @PathVariable String traineeKind) {
		String result = "";

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

		String typeTrtFiche = codeNomenclatureRepository.findcodeNomeTFByCodeStrAndLibNome("111", traineeKind);
		fichePFE.setTraineeKind(typeTrtFiche);

		if (typeTrtFiche.equalsIgnoreCase("01")) {
			fichePFE.setSalle(null);
		}
		if (typeTrtFiche.equalsIgnoreCase("02")) {
			fichePFE.setSalle(salleRepository.findByCodeSalle("Remote"));
			result = fichePFE.getSalle().getCodeSalle();

		}
		fichePFERepository.save(fichePFE);
		System.out
				.println("-----------------------------------> Got: " + idEt + " - " + traineeKind + " --> " + result);

		return result;
	}

	@GetMapping("/binomeFullName/{idEt}")
	public String gotBinomeFullName(@PathVariable String idEt) {
		return utilServices.findBinomeFullNameByStudentId(idEt);
	}

	@GetMapping("/binomeDetails/{idEt}")
	public BinomePVDetailsDto gotBinomeDetails(@PathVariable String idEt) {

		// System.out.println("------------------> ALLOW - 2: " + rss);
		BinomePVDetailsDto result = null;

		BinomeIdFullNameDto binomeIdFullNameDto = utilServices.findBinomeIdFullNameByStudentId(idEt);
		System.out.println("-> Got Binome 1: " + binomeIdFullNameDto);
		if (binomeIdFullNameDto != null) {
			System.out.println("-> Got Binome 2: " + binomeIdFullNameDto.getIdentifiant());
			result = new BinomePVDetailsDto(binomeIdFullNameDto.getIdentifiant(), binomeIdFullNameDto.getFullName(),
					utilServices.findCurrentClassByIdEt(binomeIdFullNameDto.getIdentifiant()));
		}
		System.out.println("-----------------------------------> Got Binome: " + result.getIdentifiant());
		System.out.println("-> Got Binome 3: " + result);

		return result;

		// String rss =
		// conventionRepository.findResponsableServiceStageByIdEt(idEt).get(0);
		//
		// System.out.println("------------------> ALLOW - 2: " + rss);
		// BinomePVDetailsDto result = new BinomePVDetailsDto();
		//
		// if(rss.equalsIgnoreCase("SR-STG-EM"))
		// {
		// BinomeIdFullNameDto binomeIdFullNameDto =
		// utilServices.findBinomeIdFullNameByStudentId(idEt);
		//
		// if(binomeIdFullNameDto != null)
		// {
		// result = new BinomePVDetailsDto(binomeIdFullNameDto.getIdentifiant(),
		// binomeIdFullNameDto.getFullName(),
		// utilServices.findCurrentClassByIdEt(binomeIdFullNameDto.getIdentifiant()));
		// }
		// System.out.println("-----------------------------------> Got Binome: " +
		// result.getIdentifiant());
		// }
		//
		// return result;

	}

	@GetMapping("/binomePVDetails/{idEt}")
	public BinomePVDetailsDto gotBinomePVDetails(@PathVariable String idEt) {
		BinomePVDetailsDto result = null;

		BinomeIdFullNameDto binomeIdFullNameDto = utilServices.findBinomeIdFullNameByStudentId(idEt);
		if (binomeIdFullNameDto != null) {
			result = new BinomePVDetailsDto(binomeIdFullNameDto.getIdentifiant(), binomeIdFullNameDto.getFullName(),
					utilServices.findCurrentClassByIdEt(binomeIdFullNameDto.getIdentifiant()));
		}

		System.out.println("-----------------------------------> Got Binome: " + result.getIdentifiant());

		return result;
	}

	@GetMapping("/responsableServiceStage/planifiedSoutenances/{idRSS}")
	public List<Integer> loadStatPlanifiedSoutenance(@PathVariable String idRSS) {

		System.out.println("-----------------hg------------------> Got DEPOT STAT - 1: " + idRSS);

		List<Date> soutenancesDays = fichePFERepository.findDaysOfSoutenances(1);
		List<Integer> statNbrPlanifiedSoutenancesByDay = new ArrayList<Integer>();

		for (Date drd : soutenancesDays) {
			System.out.println("-----------------UNIT-------------------------------> Got DEPOT STAT - 1: " + drd);

			Integer nbrSoutenances = fichePFERepository.findPlanifiedSoutenanceByDay(1, idRSS, drd).size();
			statNbrPlanifiedSoutenancesByDay.add(nbrSoutenances);
		}

		System.out.println("------------ LOL 123: " + statNbrPlanifiedSoutenancesByDay);
		return statNbrPlanifiedSoutenancesByDay;
	}

	@GetMapping("/responsableServiceStage/unplanifiedSoutenances/{idRSS}")
	public List<Integer> loadStatUnplanifiedSoutenance(@PathVariable String idRSS) {

		System.out.println("-----------------hg------------------> Got DEPOT STAT - 1: " + idRSS);

		List<Date> soutenancesDays = fichePFERepository.findDaysOfSoutenances(1);
		List<Integer> statNbrUnplanifiedSoutenancesByDay = new ArrayList<Integer>();

		for (Date drd : soutenancesDays) {
			System.out.println("-----------------UNIT-------------------------------> Got DEPOT STAT - 1: " + drd);

			Integer nbrSoutenances = fichePFERepository.findUnplanifiedSoutenanceByDay(1, idRSS, drd).size();
			statNbrUnplanifiedSoutenancesByDay.add(nbrSoutenances);
		}

		System.out.println("------------ LOL 123: " + statNbrUnplanifiedSoutenancesByDay);
		return statNbrUnplanifiedSoutenancesByDay;
	}

	@GetMapping("/responsableServiceStage/uploadedDepotReports/{idRSS}")
	public List<Integer> loadStatUploadedDepotReports(@PathVariable String idRSS) {

		System.out.println("-----------------hg------------------> Got DEPOT STAT - 1: " + idRSS);

		List<Date> depotReportsDays = fichePFERepository.findDaysOfDepotReports(1);
		List<Integer> statNbrDepotReportsByDay = new ArrayList<Integer>();

		for (Date drd : depotReportsDays) {
			System.out.println("-----------------UNIT-------------------------------> Got DEPOT STAT - 1: " + drd);

			Integer nbrDepotReports = fichePFERepository.findStudentsByDepotStatusAndDay(1, idRSS, "01", drd).size();
			statNbrDepotReportsByDay.add(nbrDepotReports);
		}

		System.out.println("------------ LOL 123: " + statNbrDepotReportsByDay);
		return statNbrDepotReportsByDay;
	}

	@GetMapping("/responsableServiceStage/validatedDepotReports/{idRSS}")
	public List<Integer> loadStatValidatedDepotReports(@PathVariable String idRSS) {

		System.out.println("-----------------hg------------------> Got DEPOT STAT - 1: " + idRSS);

		List<Date> depotReportsDays = fichePFERepository.findDaysOfDepotReports(1);
		List<Integer> statNbrDepotReportsByDay = new ArrayList<Integer>();

		for (Date drd : depotReportsDays) {
			System.out.println("-----------------UNIT-------------------------------> Got DEPOT STAT - 1: " + drd);

			Integer nbrDepotReports = fichePFERepository.findStudentsByDepotStatusAndDay(1, idRSS, "02", drd).size();
			statNbrDepotReportsByDay.add(nbrDepotReports);
		}

		System.out.println("------------ LOL 123: " + statNbrDepotReportsByDay);
		return statNbrDepotReportsByDay;
	}

	@GetMapping("/responsableServiceStage/refusedDepotReports/{idRSS}")
	public List<Integer> loadStatRefusedDepotReports(@PathVariable String idRSS) {

		System.out.println("-----------------hg------------------> Got DEPOT STAT - 1: " + idRSS);

		List<Date> depotReportsDays = fichePFERepository.findDaysOfDepotReports(1);
		List<Integer> statNbrDepotReportsByDay = new ArrayList<Integer>();

		for (Date drd : depotReportsDays) {
			System.out.println("-----------------UNIT-------------------------------> Got DEPOT STAT - 1: " + drd);

			Integer nbrDepotReports = fichePFERepository.findStudentsByDepotStatusAndDay(1, idRSS, "03", drd).size();
			statNbrDepotReportsByDay.add(nbrDepotReports);
		}

		System.out.println("------------ LOL 123: " + statNbrDepotReportsByDay);
		return statNbrDepotReportsByDay;
	}

	@GetMapping("/responsableServiceStage/statDoneSoutenancesByDay/{idRSS}")
	public ResponseEntity<?> loadDatesSoutenances(@PathVariable String idRSS) {

		List<Date> datesSoutenances = fichePFERepository.datesSoutenances(idRSS);
		try {
			List<Map<Date, Integer>> rssStats = new ArrayList<>();
			Map<Date, Integer> lms = new TreeMap<>();
			for (Date dt : datesSoutenances) {
				System.out.println("------------------------ LOAD DT 2.5: " + dt);
				lms.put(dt, fichePFERepository.findNumberOfSoutenancesByDay(idRSS, dt).size());
			}
			rssStats.add(lms);
			if (rssStats.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(rssStats, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/responsableServiceStage/uploadedDepot/{idRSS}")
	public Integer nbrUploadedDepot(@PathVariable String idRSS) {
		return fichePFERepository.findStatByEtatFicheAndValidDepot(1, idRSS, "03", "01").size();
	}

	@GetMapping("/responsableServiceStage/validatedDepot/{idRSS}")
	public Integer nbrValidatedDepot(@PathVariable String idRSS) {
		return fichePFERepository.findStatValidetedDepot(1, idRSS, "02").size();
	}

	@GetMapping("/responsableServiceStage/incompletedDepot/{idRSS}")
	public Integer nbrIncompletedDepot(@PathVariable String idRSS) {
		return fichePFERepository.findStatByEtatFicheAndValidDepot(1, idRSS, "03", "03").size();
	}

	@GetMapping("/responsableServiceStage/depotStat/{idRSS}")
	public ResponseEntity<?> loadResponsableServiceStageDepotStat(@PathVariable String idRSS) {

		System.out.println("-----------------------------------> Got DEPOT STAT - 1: " + idRSS);

		try {
			List<String> studentsWithUploadDepot = fichePFERepository.findStudentsByDepotStatus(1, idRSS, "01");
			List<String> studentsWithValidatedDepot = fichePFERepository.findStatValidetedDepot(1, idRSS, "02");
			List<String> studentsWithRefusedDepot = fichePFERepository.findStudentsByDepotStatus(1, idRSS, "03");

			System.out.println(
					"-----------------------------------> Got DEPOT STAT - 2: " + studentsWithUploadDepot.size() + " - "
							+ studentsWithValidatedDepot.size() + " - " + studentsWithRefusedDepot.size() + " - ");

			List<Map<String, Integer>> rssStats = new ArrayList<>();
			Map<String, Integer> lms = new HashMap<>();
			lms.put("nbrStudentsWithUploadedDepot", studentsWithUploadDepot.size());
			lms.put("nbrStudentsWithValidatedDepot", studentsWithValidatedDepot.size());
			lms.put("nbrStudentsWithRefusedDepot", studentsWithRefusedDepot.size());

			rssStats.add(lms);

			if (rssStats.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(rssStats, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @GetMapping("/updateFichePFEDepot/{idEt}")
	// public void updatefichePFEDepot(@PathVariable String idEt)
	// {
	// FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
	// fichePFE.setPathRapportVersion2(null);
	// fichePFE.setPathPlagiat(null);
	// fichePFE.setPathAttestationStage(null);
	//
	// fichePFERepository.save(fichePFE);
	//
	// System.out.println("--------------------------------RESET: " +
	// fichePFE.getPathAttestationStage());
	//
	// }

	/*
	 * @GetMapping("/authorizedStudentsToSTN/{idSession}/{idRespServiceStage}")
	 * public List<StudentPlanifySTNDto> lol(@PathVariable Integer
	 * idSession, @PathVariable String idRespServiceStage) { List<String> idStudents
	 * = fichePFERepository.findAllAuthorizedStudentsForSoutenance(idSession,
	 * idRespServiceStage); List<StudentPlanifySTNDto> studentsDtos = new
	 * ArrayList<StudentPlanifySTNDto>();
	 *
	 * for(String idStudent : idStudents) { FichePFE fichePFE =
	 * fichePFERepository.findFichePFEByStudent(idStudent).get(0);
	 *
	 * String traineeKind =
	 * codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("111",
	 * fichePFE.getTraineeKind()); String sessionLabel =
	 * sessionRepository.findByIdSession(fichePFE.getSession().getIdSession()).
	 * getLibelleSession();
	 *
	 * studentsDtos.add(new StudentPlanifySTNDto(idStudent,
	 * studentRepository.utilServices.findStudentFullNameById(idStudent),
	 * currentClass, dateDepot, sessionLabel, statusPlanifySTN, traineeKind)); }
	 *
	 * return studentsDtos; }
	 */

	@GetMapping("/teacherFullName/{mailEns}")
	public String findTeacherFullNameByMail(@PathVariable String mailEns) {
		String teacherFullName = teacherRepository.findTeacherFullNameByMail(mailEns).get(0);
		System.out.println(
				"######################################## CONNECTED TEA: " + teacherFullName + " - " + new Date());
		return teacherFullName;
	}

	@GetMapping("/respServStgFullName/{idUserSce}")
	public String findRespServStgFullNameById(@PathVariable String idUserSce) {
		String respServStgFullName = responsableServiceStageRepository.findRespServStgFullNameById(idUserSce);
		System.out.println(
				"######################################## CONNECTED RSS: " + respServStgFullName + " - " + new Date());
		return respServStgFullName;
	}

	@GetMapping("/student/{idEt}")
	public StudentDto findStudentById(@PathVariable String idEt) {
		// // System.out.println("StudentCJ --------------> 0. Get StudentCJ Entity: " +
		// idEt);
		// StudentCJ student = studentRepository.findByIdEt(idEt).orElseThrow(() -> new
		// RuntimeException("StudentCJ : Verify your credentials !."));

		// // System.out.println("StudentCJ --------------> 1. Get StudentCJ Entity: " +
		// student.getIdEt());
		StudentDto etudiantDto = utilServices.findStudentDtoById(idEt);

		return etudiantDto;
	}

	@GetMapping("/pedagogicalCoordinator/{pcMail}")
	public PedagogicalCoordinatorDto findPedagogicalCoordinatorById(@PathVariable String pcMail) {
		// // System.out.println("StudentCJ --------------> 0. Get StudentCJ Entity: " +
		// idEt);
		// StudentCJ student = studentRepository.findByIdEt(idEt).orElseThrow(() -> new
		// RuntimeException("StudentCJ : Verify your credentials !."));

		// // System.out.println("StudentCJ --------------> 1. Get StudentCJ Entity: " +
		// student.getIdEt());
		PedagogicalCoordinatorDto pedagogicalCoordinatorDto = pedagogicalCoordinatorRepository
				.findPedagogicalCoordinatorDtoByPedaCoordMail(pcMail);

		return pedagogicalCoordinatorDto;
	}

	@GetMapping("/studentSpeed/{idEt}")
	public StudentSpeedDto findStudentSpeedById(@PathVariable String idEt) {
		StudentSpeedDto StudentSpeedDto = null;
		List<StudentSpeedDto> StudentSpeedCJDtos = studentRepository.findStudentSpeedCJDtoById(idEt);
		List<StudentSpeedDto> StudentSpeedCSDtos = studentRepository.findStudentSpeedCSDtoById(idEt);

		if (!StudentSpeedCJDtos.isEmpty()) {
			StudentSpeedDto = StudentSpeedCJDtos.get(0);
		}

		if (!StudentSpeedCSDtos.isEmpty()) {
			StudentSpeedDto = StudentSpeedCSDtos.get(0);
		}
		return StudentSpeedDto;
	}

	@GetMapping("/studentMailEsp/{idEt}")
	public String findStudentMailEspById(@PathVariable String idEt) {
		String studentEspMail = "--";
		List<String> studentCJEspMail = studentRepository.findStudentCJMailEspById(idEt);
		List<String> studentCSEspMail = studentRepository.findStudentCSMailEspById(idEt);

		if (!studentCJEspMail.isEmpty()) {
			studentEspMail = studentCJEspMail.get(0);
		}

		if (!studentCSEspMail.isEmpty()) {
			studentEspMail = studentCSEspMail.get(0);
		}
		return studentEspMail;
	}

	@GetMapping("/studentMailTel/{idEt}")
	public StudentMailTelDto findStudentMailTelById(@PathVariable String idEt) {
		return utilServices.findStudentMailTelDtoById(idEt);
	}

	@GetMapping("/studentJWTPWD/{idEt}")
	public String findStudentJWTPWDById(@PathVariable String idEt) {
		return utilServices.findStudentJWTPWDByHisId(idEt);
	}

	@GetMapping("/responsableServiceStage/{idResponsableServiceStage}")
	public ResponsableServiceStageDto findResponsibleById(@PathVariable String idResponsableServiceStage) {
		return responsableServiceStageRepository.findResponsableServiceStageDtoById(idResponsableServiceStage);
	}

	@GetMapping("/pedagogicalCoordinatorJWTPWD/{idPedaCoord}")
	public String pedagogicalCoordinatorJWTPWD(@PathVariable String idPedaCoord) {
		// // System.out.println("StudentCJ --------------> 0. Get StudentCJ Entity: " +
		// idEt);
		return pedagogicalCoordinatorRepository.findPedagogicalCoordinatorJWTPWDById(idPedaCoord);

	}

	@GetMapping("/responsableServiceStageJWTPWD/{idRespSrvStg}")
	public String responsableServiceStageJWTPWD(@PathVariable String idRespSrvStg) {
		// // System.out.println("StudentCJ --------------> 0. Get StudentCJ Entity: " +
		// idEt);
		return responsableServiceStageRepository.findResponsableServiceStageJWTPWDById(idRespSrvStg);

	}

	@GetMapping("/teacher/{mailEns}")
	public TeacherDto findTeacherByMail(@PathVariable String mailEns) {
		System.out.println("Teacher --------------> 0. Get Teacher Entity: " + mailEns);
		Teacher teacher = teacherRepository.findEnsByMailEns(mailEns.toLowerCase());

		TeacherDto teacherDto = new TeacherDto(teacher.getIdEns(), teacher.getNomEns(), teacher.getTel1());
		// System.out.println("Teacher --------------> 3. Get Teacher Entity: " +
		// teacherDto.getNomEns());
		return teacherDto;
	}

	@GetMapping("/teacherProfile/{mailEns}")
	public TeacherProfileDto findTeacherProfileByMail(@PathVariable String mailEns) {
		return teacherRepository.getTeacherProfile(mailEns);
	}

	@GetMapping("/teacherJWTPWD/{idEns}")
	public String teacherJWTPWD(@PathVariable String idEns) {
		// // System.out.println("StudentCJ --------------> 0. Get StudentCJ Entity: " +
		// idEt);
		return teacherRepository.findTeacherJWTPWDById(idEns);

	}

	/******************************************************
	 * RDV
	 ******************************************************/

	// @GetMapping("/rdvsByStudents/{idEns}")
	// public List<RdvSuiviStage> findRdvsByStudent(@PathVariable String idEns)
	// {
	// // // System.out.println("StudentCJ --------------> 0. Get StudentCJ Entity:
	// " + idEt);
	// return rdvRepository.findRdvsByStudent(idEns);
	// }

	// @GetMapping("/rdvsAndVisiteStgByStudent/{idEt}")
	// public ResponseEntity<?>
	// findRendezVousVisiteStagiaireDtosByStudent(@PathVariable String idEt)
	// {
	// List<RendezVousVisiteStagiaireDto> lRdvAndVSs = new
	// ArrayList<RendezVousVisiteStagiaireDto>();
	// lRdvAndVSs.addAll(rdvRepository.findRendezVousByStudent(idEt));
	// lRdvAndVSs.addAll(visiteStagiareRepository.findVisiteStagiaireByStudent(idEt));
	// return ResponseEntity.ok(lRdvAndVSs);
	// }

	@GetMapping("/rdvsAndVisiteStgByStudent/{idEt}")
	public List<RendezVousVisiteStagiaireDto> findRendezVousVisiteStagiaireDtosByStudent(@PathVariable String idEt) {
		List<RendezVousVisiteStagiaireDto> lRdvAndVSs = new ArrayList<RendezVousVisiteStagiaireDto>();
		// lRdvAndVSs.addAll(rdvRepository.findRendezVousByStudent(idEt));
		// lRdvAndVSs.addAll(visiteStagiareRepository.findVisiteStagiaireByStudent(idEt));

		// List<RdvSuiviStage> lol = rdvRepository.hi(idEt);

		List<String> lastDateDepotFichePFEs = fichePFERepository.findNewestDateDepotFichePFEByStudent(idEt);

		if (!lastDateDepotFichePFEs.isEmpty()) {
			String lastDateDepotFichePFE = fichePFERepository.findNewestDateDepotFichePFEByStudent(idEt).get(0);
			System.out.println("------------------------------> CALC-0: " + lastDateDepotFichePFE);
			lRdvAndVSs.addAll(rdvRepository.findRendezVousByStudent(idEt, lastDateDepotFichePFE));
			lRdvAndVSs.addAll(visiteStagiareRepository.findVisiteStagiaireByStudent(idEt, lastDateDepotFichePFE));

			System.out.println("------------------------------> CALC-1: " + lRdvAndVSs.size());

			lRdvAndVSs.sort(Comparator.comparing(RendezVousVisiteStagiaireDto::getDateDate));
		}

		return lRdvAndVSs;
	}

	/******************************************************
	 * Commons
	 ******************************************************/

	public void checkDisponibilityByUnit(Boolean isAvailablePE, List<String> seances, String selectedStartHour,
										 String selectedEndHour) {
		System.out.println("--------------SARS----------------> CALC-1: " + seances);
		System.out.println("--------------SARS----------------> CALC-2: " + selectedStartHour);
		System.out.println("--------------SARS----------------> CALC-3: " + selectedEndHour);

		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm");

		DateTime ssh = df.parseLocalTime(selectedStartHour).toDateTimeToday();
		DateTime seh = df.parseLocalTime(selectedEndHour).toDateTimeToday();// .plusDays(1);

		Seance s1 = seanceRepository.findSeanceByCodeseance("S1");
		DateTime s1sh = df.parseLocalTime(s1.getHeuredeb().replace("H", "")).toDateTimeToday();
		DateTime s1eh = df.parseLocalTime(s1.getHeurefin().replace("H", "")).toDateTimeToday();

		Seance s2 = seanceRepository.findSeanceByCodeseance("S2");
		DateTime s2sh = df.parseLocalTime(s2.getHeuredeb().replace("H", "")).toDateTimeToday();
		DateTime s2eh = df.parseLocalTime(s2.getHeurefin().replace("H", "")).toDateTimeToday();

		Seance s4 = seanceRepository.findSeanceByCodeseance("S4");
		DateTime s4sh = df.parseLocalTime(s4.getHeuredeb().replace("H", "")).toDateTimeToday();
		DateTime s4eh = df.parseLocalTime(s4.getHeurefin().replace("H", "")).toDateTimeToday();

		Seance s5 = seanceRepository.findSeanceByCodeseance("S5");
		DateTime s5sh = df.parseLocalTime(s5.getHeuredeb().replace("H", "")).toDateTimeToday();
		DateTime s5eh = df.parseLocalTime(s5.getHeurefin().replace("H", "")).toDateTimeToday();

		// System.out.println("------------------------------> CALC-1: " + ssh + " - " +
		// seh);
		// System.out.println("------------------------------> CALC-2: " + s1sh + " - "
		// + s1eh);
		// System.out.println("------------------------------> CALC-3: " + s2sh + " - "
		// + s2eh);
		// System.out.println("------------------------------> CALC-4: " + s4sh + " - "
		// + s4eh);
		// System.out.println("------------------------------> CALC-5: " + s5sh + " - "
		// + s5eh);

		if (ssh.isBefore(s1sh) && seh.isBefore(s1sh)) {
			seances = new ArrayList<String>();
		}
		if ((ssh.isEqual(s1sh)) || (ssh.isEqual(s1eh)) || (seh.isEqual(s1sh)) || (seh.isEqual(s1eh))
				|| (ssh.isAfter(s1sh) && ssh.isBefore(s1eh)) || (seh.isAfter(s1sh) && seh.isBefore(s1eh))
				|| (ssh.isBefore(s1sh) && seh.isAfter(s1eh))) {
			seances.add("S1");
			// System.out.println("------------------------- CALC -----> S1");
		}
		if ((ssh.isEqual(s2sh)) || (ssh.isEqual(s2eh)) || (seh.isEqual(s2sh)) || (seh.isEqual(s2eh))
				|| (ssh.isAfter(s2sh) && ssh.isBefore(s2eh)) || (seh.isAfter(s2sh) && seh.isBefore(s2eh))
				|| (ssh.isBefore(s2sh) && seh.isAfter(s2eh))) {
			seances.add("S2");
			// System.out.println("------------------------- CALC -----> S2");
		}
		if ((ssh.isEqual(s1sh)) || (ssh.isEqual(s1eh)) || (seh.isEqual(s1sh)) || (seh.isEqual(s1eh))
				|| (ssh.isAfter(s1sh) && ssh.isBefore(s1eh)) || (seh.isAfter(s1sh) && seh.isBefore(s1eh))
				|| (ssh.isEqual(s2sh)) || (ssh.isEqual(s2eh)) || (seh.isEqual(s2sh)) || (seh.isEqual(s2eh))
				|| (ssh.isAfter(s2sh) && ssh.isBefore(s2eh)) || (seh.isAfter(s2sh) && seh.isBefore(s2eh))
				|| (ssh.isAfter(s1sh) && ssh.isBefore(s2eh)) || (seh.isAfter(s1sh) && seh.isBefore(s2eh))
				|| (ssh.isBefore(s1sh) && seh.isAfter(s2eh))) {
			seances.add("S1S2");
			// System.out.println("------------------------ CALC ------> S1S2");
		}
		if ((ssh.isEqual(s4sh)) || (ssh.isEqual(s4eh)) || (seh.isEqual(s4sh)) || (seh.isEqual(s4eh))
				|| (ssh.isAfter(s4sh) && ssh.isBefore(s4eh)) || (seh.isAfter(s4sh) && seh.isBefore(s4eh))
				|| (ssh.isBefore(s4sh) && seh.isAfter(s4eh))) {
			seances.add("S4");
			// System.out.println("----------------------- CALC -------> S4");
		}
		if ((ssh.isEqual(s5sh)) || (ssh.isEqual(s5eh)) || (seh.isEqual(s5sh)) || (seh.isEqual(s5eh))
				|| (ssh.isAfter(s5sh) && ssh.isBefore(s5eh)) || (seh.isAfter(s5sh) && seh.isBefore(s5eh))
				|| (ssh.isBefore(s5sh) && seh.isAfter(s5eh))) {
			seances.add("S5");
			// System.out.println("------------------------ CALC ------> S5");
		}
		if ((ssh.isEqual(s4sh)) || (ssh.isEqual(s4eh)) || (seh.isEqual(s4sh)) || (seh.isEqual(s4eh))
				|| (ssh.isAfter(s4sh) && ssh.isBefore(s4eh)) || (seh.isAfter(s4sh) && seh.isBefore(s4eh))
				|| (ssh.isEqual(s5sh)) || (ssh.isEqual(s5eh)) || (seh.isEqual(s5sh)) || (seh.isEqual(s5eh))
				|| (ssh.isAfter(s5sh) && ssh.isBefore(s5eh)) || (seh.isAfter(s5sh) && seh.isBefore(s5eh))
				|| (ssh.isAfter(s4sh) && ssh.isBefore(s5eh)) || (seh.isAfter(s4sh) && seh.isBefore(s5eh))
				|| (ssh.isBefore(s4sh) && seh.isAfter(s5eh))) {
			seances.add("S4S5");
			// System.out.println("----------------------- CALC -------> S4S5");
		}
		if (ssh.isAfter(s5eh) && seh.isAfter(s5eh)) {
			seances = new ArrayList<String>();
		}

		// System.out.println("-----------------------------> ORIGINAL: " + seances);

		System.out.println("--------------SARS----------------> CALC-4: " + seances);

	}

	public StudentTreatDepotDto findStudentTreatDepotByHisId(String idEt, String classe) {

		String department = utilServices.findDepartmentByClass(classe);
		String option = utilServices.findOptionByClass(classe, optionRepository.listOptionsByYear("2021"));

		String societeLibelle = "--";
		String titreProjet = "--";

		String juryPresidentExpert = fichePFERepository.findJuryPresidentExpertByStudent(idEt).get(0);
		System.out.println("-------------------dsfvsdfv----> juryPresidentExpert: " + juryPresidentExpert);

		String jpmn = null;
		String expn = null;
		String jpnn = juryPresidentExpert.substring(0, juryPresidentExpert.lastIndexOf("JPE"));
		String jpei = juryPresidentExpert.substring(juryPresidentExpert.lastIndexOf("JPE") + 4,
				juryPresidentExpert.lastIndexOf("EXP"));
		String expi = juryPresidentExpert.substring(juryPresidentExpert.lastIndexOf("EXP") + 4,
				juryPresidentExpert.length());

		if (!jpnn.equalsIgnoreCase("")) {
			jpmn = jpnn;
		}
		if (jpnn.equalsIgnoreCase("")) {
			jpmn = teacherRepository.findNameTeacherById(jpei);
		}

		expn = teacherRepository.findNameTeacherById(expi);

		// AffectationStudentSocieteSujetSocieteDto ass =
		// affectationStudentSocieteRepository.findAffectationStudentSocieteSujetSociete(idEt);
		// if(ass != null)
		// {
		// societeLibelle = ass.getLibelleSociete();
		// titreProjet = ass.getTitreProjet();
		// }

		System.out.println("--------------------------sdvsdv-------GOT ENTITY: " + department + " - " + option + " - "
				+ societeLibelle + " - " + titreProjet);

		StudentTreatDepotDto studentTreatDepotDto = new StudentTreatDepotDto(department, option,
				conventionRepository.findCompanyNameByIdEt(idEt).get(0),
				fichePFERepository.findTitleFichePFEByStudent(idEt).get(0), jpmn, expn,
				fichePFERepository.findTitleFichePFEByStudent(idEt).get(0),
				conventionRepository.findCompanyNameByIdEt(idEt).get(0));

		return studentTreatDepotDto;
	}

	/******************************************************
	 * Send Mail
	 ******************************************************/

	@GetMapping("/upload/validateMyDepot/{idStu}")
	public void validateMyDepot(@PathVariable String idStu) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStu).get(0);
		// System.out.println("------------------------------------------------------->
		// G");

		// fichePFE.setEtatFiche("03");
		fichePFE.setDateDepotReports(new Date());
		fichePFE.setValidDepot("01");

		fichePFERepository.save(fichePFE);

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateValidDepot = dateFormata.format(new Date());

		String subject = "Dépôt Rapport";
		String content = "Nous voulons vous informer par le présent mail que vous avez déposé "
				+ "votre <strong><font color=grey>Rapport Version Finale</font></strong>, "
				+ "votre <strong><font color=grey>Rapport Anti-Plagiat</font></strong>, "
				+ "ainsi que votre <strong><font color=grey>Attestation Stage PFE</font></strong> "
				+ "et c'est le <font color=blue>" + dateValidDepot + " </font>."
				+ "<br/>Merci de patienter jusqu'à le traitement de votre requête.";

		// localNotificationValidateDepot(idStu, subject, content); // LOCAL
		serverNotificationValidateDepot(idStu, subject, content); // SERVER
	}


	@GetMapping("/findAllAffectedStudentsToAExpByYear/{idAE}/{selectedYear}")
	public List<StudentAffectationDetailsDto> findAllAffectedStudentsToAExpByYear(@PathVariable String idAE, @PathVariable String selectedYear)
	{
		List<StudentAffectationDetailsDto> las = utilServices.findStudentsDtoTrainedByPExpAndYear(idAE, selectedYear);

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$: " + las.size());

		for(StudentAffectationDetailsDto sd : las)
		{
			sd.setUniverYear(sd.getUniverYear() + "-" + (Integer.parseInt(sd.getUniverYear()) + 1));
			if(sd.getClasse().contains("4ALINFO"))
			{
				sd.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(sd.getIdEt(), selectedYear));
			}
			else
			{
				sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear(selectedYear)).replace("_01", ""));
			}
		}

		return las;
	}

	public void localNotificationValidateDepot(String idStu, String subject, String content) {
		String receiver = "saria.essid@gmail.com";

		// String mailPair = fichePFERepository.findBinomeMailByStudentId(idStu).get(0);
		String mailPair = utilServices.findStudentMailById(idStu); // Binome

		System.out.println("------------------------------------------> Pair - 1: " + mailPair);
		if (mailPair != null) {
			List<String> pairs = new ArrayList<String>();
			pairs.add("no_reply@esprit.tn");
			pairs.add("rosa.natali123@gmail.com");
			receiver = pairs.stream().collect(Collectors.joining(", "));
			System.out.println("------------------------------------------> Pair - 2: " + mailPair);
		}

		String idTeacher = null;
		List<String> idTeachersCJ = inscriptionRepository.findEncadrantPedagogiqueByStudent(idStu);
		List<String> idTeachersCS = inscriptionRepository.findEncadrantPedagogiqueCSByStudent(idStu);

		if (!idTeachersCJ.isEmpty()) {
			idTeacher = idTeachersCJ.get(0);
		}
		if (!idTeachersCS.isEmpty()) {
			idTeacher = idTeachersCS.get(0);
		}

		String mailRSS = responsableServiceStageRepository
				.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(idStu).get(0));
		String mailPE = teacherRepository.findMailTeacherById(idTeacher);

		String currentClass = null;
		List<String> currentClassesCJ = studentRepository.findCurrentClassCJByIdEt(idStu);
		List<String> currentClassesCS = studentRepository.findCurrentClassCSByIdEt(idStu);

		if (!currentClassesCJ.isEmpty()) {
			currentClass = currentClassesCJ.get(0);
		}

		if (!currentClassesCS.isEmpty()) {
			currentClass = currentClassesCS.get(0);
		}

		System.out.println("-------------------------> HOSTS DR: " + currentClass);
		String codeOption = utilServices.findOptionByClass(currentClass, optionRepository.listOptionsByYear("2021"));
		System.out.println("----------------> Code Option: " + codeOption);

		String idAcademicCoordinator = pedagogicalCoordinatorRepository
				.findPedagogicalCoordinatorByCodeOption(codeOption);
		System.out.println("----------------> Academic Coordinator: " + idAcademicCoordinator);
		String mailAcademicCoordinator = teacherRepository.findMailTeacherById(idAcademicCoordinator);

		List<String> receiversCC = new ArrayList<String>();
		receiversCC.add("no_reply@esprit.tn");
		receiversCC.add("saria.essid@esprit.tn");
		String responsibles = receiversCC.stream().collect(Collectors.joining(", "));

		System.out.println("-------------------------> HOSTS DR: " + responsibles + "--->" + receiver + " - " + mailRSS
				+ " - " + mailAcademicCoordinator + " - " + mailPE);
		utilServices.sendMailWithManyTOandManyCC(subject, receiver, responsibles, content);

	}

	public void serverNotificationValidateDepot(String idStu, String subject, String content) {
		String receiver = utilServices.findStudentMailById(idStu); // UPDATE

		String mailPair = utilServices.findStudentMailById(idStu); // Binome
		System.out.println("------------------------------------------> Pair - 1: " + mailPair);
		if (mailPair != null) {
			List<String> pairs = new ArrayList<String>();
			pairs.add(receiver);
			pairs.add(mailPair);
			receiver = pairs.stream().collect(Collectors.joining(", "));
			System.out.println("--------------------ROSE15112022----------------------> Pair - 2: " + mailPair);
		}

		String idTeacher = utilServices.findIdEncadrantPedagogiqueByStudent(idStu);

		String mailRSS = responsableServiceStageRepository
				.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(idStu).get(0));
		String mailPE = teacherRepository.findMailTeacherById(idTeacher);

		String currentClass = utilServices.findCurrentClassByIdEt(idStu);

		System.out.println("-------------------------> HOSTS DR: " + currentClass);


		String studentOption = null;
		if(currentClass.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idStu) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(currentClass, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));
		}

		System.out.println("----------------> Code Option: " + studentOption);

		//String mailAcademicCoordinator = pedagogicalCoordinatorRepository
		//		.findPedagogicalCoordinatorMailByCodeOption(studentOption);

		String idCPS = pedagogicalCoordinatorRepository.gotIdEnsPedagogicalCoordinatorByOption(studentOption.toLowerCase());

		String mailCPS = "saria.essid@esprit.tn";
		if(
				studentOption.toUpperCase().contains("SIM") || studentOption.toUpperCase().contains("GAMIX") ||
						studentOption.toUpperCase().contains("WIN") || studentOption.toUpperCase().contains("IOSYS")
		)
		{
			if(studentOption.toUpperCase().contains("SIM"))
			{
				mailCPS = "CPS_SIM@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("GAMIX"))
			{
				mailCPS = "CPS_GAMIX@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("WIN"))
			{
				mailCPS = "CPS_WIN@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("TWIN"))
			{
				mailCPS = "CPS_TWIN@esprit.tn";
			}

			if(studentOption.toUpperCase().contains("IOSYS"))
			{
				mailCPS = "CPS_IOSYS@esprit.tn";
			}
		}
		else
		{
			mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorByIdNew(idCPS);
		}


		// String idAcademicCoordinator =
		// pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByCodeOption(codeOption);
		// System.out.println("----------------> Academic Coordinator: " +
		// idAcademicCoordinator);
		// String mailAcademicCoordinator =
		// teacherRepository.findMailTeacherById(idAcademicCoordinator);

		List<String> receiversCC = new ArrayList<String>();
		receiversCC.add(mailPE);
		receiversCC.add(mailCPS);
		receiversCC.add(mailRSS);
		String responsibles = receiversCC.stream().collect(Collectors.joining(", "));

		System.out.println("-------------------------> HOSTS DR: " + responsibles + "--->" + receiver + " - " + mailRSS
				+ " - " + mailCPS + " - " + mailPE);
		utilServices.sendMailWithManyTOandManyCC(subject, receiver, responsibles, content);

	}

	@GetMapping("/validateUniquePlanification/{idEtToSTN}")
	public void validateUniquePlanification(@PathVariable String idEtToSTN) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEtToSTN).get(0);
		fichePFE.setEtatFiche("08");
		fichePFERepository.save(fichePFE);
	}

	@GetMapping("/validateAndNotifySoutenanceActors/{idEtToSTN}/{selectedDate}/{selectedStartHour}/{selectedEndHour}/{salle}/{pedagogicalEncadrant}/{presjury}/{expertEns}")
	public void validateAndNotifySoutenanceActors(@PathVariable String idEtToSTN, @PathVariable String selectedDate,
												  @PathVariable String selectedStartHour, @PathVariable String selectedEndHour, @PathVariable String salle,
												  @PathVariable String pedagogicalEncadrant, @PathVariable String presjury, @PathVariable String expertEns)
			throws IOException, GeneralSecurityException {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEtToSTN).get(0);
		fichePFE.setEtatFiche("06");
		fichePFERepository.save(fichePFE);

		/*******************************************************************************************Session********/
		Date date1;
		try
		{
			date1 = new SimpleDateFormat("dd/MM/yy").parse(selectedDate);
			List<com.esprit.gdp.models.Session> sessions = sessionRepository.findAll();
			// System.out.println("CON-------------- STN-3: " + sessions.size());
			com.esprit.gdp.models.Session relatedSession = null;
			for(com.esprit.gdp.models.Session s : sessions)
			{
				if(date1.after(s.getDateDebut()) && date1.before(s.getDateFin()))
				{
					// System.out.println("CON-------------- STN-4");
					relatedSession = s;
				}
			}
			// System.out.println("CON-------------- STN-5: " + relatedSession.getIdSession());
			fichePFE.setSession(relatedSession);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**********************************************************************************************************/

		String nameRSS = conventionRepository.findNameResponsableServiceStageByIdEt(idEtToSTN).get(0);

		String studentMail = utilServices.findStudentMailById(idEtToSTN); // SERVER
		// String studentMail = "saria.essid@esprit.tn"; // LOCAL

		String pairMail;
		String pairFullName;
		// List<String> lpairMailFullName=
		// fichePFERepository.findBinomeMailFullNameByStudentId(idEtToSTN);

		// String binomeFullName =
		// utilServices.findBinomeFullNameByStudentId(idEtToSTN);
		StudentDto binome = utilServices.findStudentDtoById(idEtToSTN);
		String binomeFullName = binome.getPrenomet() + " " + binome.getNomet();

		// System.out.println("------------------------------------------> Pair - 4: " +
		// lpairMailFullName.size());
		if (binomeFullName != null) {

			/****************************************
			 * Duplicate Planification to Binome GC
			 ****************************************/
			String rss = conventionRepository.findResponsableServiceStageByIdEt(idEtToSTN).get(0);

			if (rss.equalsIgnoreCase("SR-STG-GC")) {
				String idBinome = fichePFE.getBinome();// se0109 .getIdEt();
				FichePFE fichePFEBinome = fichePFERepository.findFichePFEByStudent(idBinome).get(0);

				fichePFEBinome.setExpertEns(fichePFE.getExpertEns());
				fichePFEBinome.setPresidentJury(fichePFE.getPresidentJury());
				fichePFEBinome.setPresidentJuryEns(fichePFE.getPresidentJuryEns());
				fichePFEBinome.setDateSoutenance(fichePFE.getDateSoutenance());
				fichePFEBinome.setHeureDebut(fichePFE.getHeureDebut());
				fichePFEBinome.setHeureFin(fichePFE.getHeureFin());
				fichePFEBinome.setSalle(fichePFE.getSalle());

				fichePFEBinome.setEtatFiche("06");
				fichePFERepository.save(fichePFEBinome);
			}
			/***********************************************************************************************************************/

			// String pairMailFullName = lpairMailFullName.get(0);
			// pairFullName =
			// pairMailFullName.substring(pairMailFullName.lastIndexOf("$$$")+3);

			pairMail = binome.getAdressemailesp(); // SERVER
			// pairMail = "rosa.natali123@gmail.com"; // LOCAL

			serverSendNotificationThroughoutGmailGCandGDWithPair(selectedDate, studentMail, pairMail, idEtToSTN,
					binomeFullName, selectedStartHour, selectedEndHour, salle, presjury, pedagogicalEncadrant,
					expertEns, nameRSS); // SERVER

		} else {
			System.out.println("------------------------------------------> Lonely ");
			serverSendNotificationThroughoutGmailGCandGDLonely(selectedDate, studentMail, idEtToSTN, selectedStartHour, // SERVER
					selectedEndHour, salle, presjury, pedagogicalEncadrant, expertEns, nameRSS);
		}

	}

	@GetMapping("/validateAndNotifySoutenanceActorsForMP/{idEtToSTN}")
	public void validateAndNotifySoutenanceActorsForMP(@PathVariable String idEtToSTN)
			throws IOException, GeneralSecurityException {

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEtToSTN).get(0);
		fichePFE.setEtatFiche("06");


		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDateSoutenance = dateFormata.format(fichePFE.getDateSoutenance());

		/*******************************************************************************************Session********/
		Date date1;
		try
		{
			date1 = new SimpleDateFormat("dd/MM/yy").parse(formattedDateSoutenance);
			List<com.esprit.gdp.models.Session> sessions = sessionRepository.findAll();
			// System.out.println("CON-------------- STN-3: " + sessions.size());
			com.esprit.gdp.models.Session relatedSession = null;
			for(com.esprit.gdp.models.Session s : sessions)
			{
				if(date1.after(s.getDateDebut()) && date1.before(s.getDateFin()))
				{
					// System.out.println("CON-------------- STN-4");
					relatedSession = s;
				}
			}
			// System.out.println("CON-------------- STN-5: " + relatedSession.getIdSession());
			fichePFE.setSession(relatedSession);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**********************************************************************************************************/

		fichePFERepository.save(fichePFE);

		String nameRSS = conventionRepository.findNameResponsableServiceStageByIdEt(idEtToSTN).get(0);

		String studentMail = utilServices.findStudentMailById(idEtToSTN); // SERVER
		// String studentMail = "saria.essid@esprit.tn"; // LOCAL

		String pairMail;
		String pairFullName = utilServices.findBinomeFullNameByStudentId(idEtToSTN);

		if (pairFullName != null) {
			/****************************************
			 * Duplicate Planification to Binome GC
			 ****************************************/
			String rss = conventionRepository.findResponsableServiceStageByIdEt(idEtToSTN).get(0);

			if (rss.equalsIgnoreCase("SR-STG-GC")) {
				String idBinome = fichePFE.getBinome(); // se0109 .getIdEt();
				FichePFE fichePFEBinome = fichePFERepository.findFichePFEByStudent(idBinome).get(0);

				fichePFEBinome.setExpertEns(fichePFE.getExpertEns());
				fichePFEBinome.setPresidentJury(fichePFE.getPresidentJury());
				fichePFEBinome.setPresidentJuryEns(fichePFE.getPresidentJuryEns());
				fichePFEBinome.setDateSoutenance(fichePFE.getDateSoutenance());
				fichePFEBinome.setHeureDebut(fichePFE.getHeureDebut());
				fichePFEBinome.setHeureFin(fichePFE.getHeureFin());
				fichePFEBinome.setSalle(fichePFE.getSalle());

				fichePFEBinome.setEtatFiche("06");
				fichePFERepository.save(fichePFEBinome);
			}
			/***********************************************************************************************************************/

			// List<String> lpairMailFullName=
			// fichePFERepository.findBinomeMailFullNameByStudentId(idEtToSTN);
			// String pairMailFullName = lpairMailFullName.get(0);
			// pairMail = pairMailFullName.substring(0,
			// pairMailFullName.lastIndexOf("$$$")); // SERVER
			StudentDto binome = utilServices.findStudentDtoById(idEtToSTN);
			String pairMailFullName = binome.getPrenomet() + " " + binome.getNomet();
			pairMail = binome.getAdressemailesp();

			// pairMail = "rosa.natali123@gmail.com"; // LOCAL

			// localSendNotificationThroughoutGmailGCandGDWithPair(formattedDateSoutenance,
			// studentMail, pairMail, idEtToSTN,
			// pairFullName, fichePFE.getHeureDebut(), fichePFE.getHeureFin(),
			// fichePFE.getSalle().getCodeSalle(),
			// fichePFE.getPresidentJuryEns().getIdEns(),
			// fichePFE.getPedagogicalEncadrant().getIdEns(),
			// fichePFE.getExpertEns().getIdEns(), nameRSS); // LOCAL

			serverSendNotificationThroughoutGmailGCandGDWithPair(formattedDateSoutenance, studentMail, pairMail,
					idEtToSTN, pairFullName, fichePFE.getHeureDebut(), fichePFE.getHeureFin(),
					fichePFE.getSalle().getCodeSalle(), fichePFE.getPresidentJuryEns().getIdEns(),
					fichePFE.getPedagogicalEncadrant().getIdEns(), fichePFE.getExpertEns().getIdEns(), nameRSS); // SERVER

		} else {
			System.out.println("------------------------------------------> Lonely ");

			// localSendNotificationThroughoutGmailGCandGDLonely(formattedDateSoutenance,
			// studentMail, idEtToSTN, fichePFE.getHeureDebut(), // LOCAL
			// fichePFE.getHeureFin(), fichePFE.getSalle().getCodeSalle(),
			// fichePFE.getPresidentJuryEns().getIdEns(),
			// fichePFE.getPedagogicalEncadrant().getIdEns(),
			// fichePFE.getExpertEns().getIdEns(), nameRSS);

			serverSendNotificationThroughoutGmailGCandGDLonely(formattedDateSoutenance, studentMail, idEtToSTN,
					fichePFE.getHeureDebut(), // SERVER
					fichePFE.getHeureFin(), fichePFE.getSalle().getCodeSalle(),
					fichePFE.getPresidentJuryEns().getIdEns(), fichePFE.getPedagogicalEncadrant().getIdEns(),
					fichePFE.getExpertEns().getIdEns(), nameRSS);
		}

	}

	@GetMapping("/listClassesByOption/{pcMail}")
	public List<String> listClassesByOption(@PathVariable String pcMail) {

		String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
		List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);

		List<String> lclasses = new ArrayList<String>();
		for (String opt : options) {
			System.out.println("--PC-------------------------------> Option: " + opt);
			lclasses.addAll(utilServices.findClassesByOption(opt.toLowerCase()));

			if (opt.equalsIgnoreCase("ARCTIC") || opt.equalsIgnoreCase("DS") || opt.equalsIgnoreCase("SAE")
					|| opt.equalsIgnoreCase("TWIN") || opt.equalsIgnoreCase("ALINFO")) {
				System.out.println("---> HERE ...");
				lclasses.add("4ALINFO1");
				lclasses.add("4ALINFO2");
				lclasses.add("4ALINFO3");
			}
		}

		List<String> unikClasses = lclasses.stream().distinct().collect(Collectors.toList());

		unikClasses.sort(Comparator.naturalOrder());
		// lclasses.stream().sorted().collect(Collectors.toList());

		for (String s : unikClasses) {
			System.out.println("---------> Class: " + s);
		}

		return unikClasses;

	}

	// PARAMSARIA
	@GetMapping("/listClassesByOptionAndByYear/{pcMail}/{year}")
	public List<String> listClassesByOptionAndByYear(@PathVariable String pcMail, @PathVariable String year)
	{
		System.out.println("-------- pcMail: " + pcMail);
		String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
		System.out.println("-------- idPC: " + idPC);
		List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);

		List<String> lclasses = new ArrayList<String>();
		for(String opt : options)
		{
			System.out.println("==> Opt: " + opt);
			lclasses.addAll(utilServices.findClassesByYearAndOption(year, opt.toLowerCase()));

			if(
					opt.equalsIgnoreCase("ARCTIC") || opt.equalsIgnoreCase("DS") ||
							opt.equalsIgnoreCase("SAE") ||opt.equalsIgnoreCase("TWIN") || opt.equalsIgnoreCase("ALINFO") ||
							opt.equalsIgnoreCase("NIDS") || opt.equalsIgnoreCase("ALINFO") || opt.equalsIgnoreCase("ERP-BI")

			)
			{
				System.out.println("---> HERE ...");
				lclasses.addAll(inscriptionRepository.findALTClassesByYear(year));
			}
		}

		List<String> unikClasses = lclasses.stream().distinct().collect(Collectors.toList());

		unikClasses.sort(Comparator.naturalOrder());
		// lclasses.stream().sorted().collect(Collectors.toList());

		for(String s : unikClasses)
		{
			System.out.println("---------> Class: " + s);
		}

		return unikClasses;

	}

	@GetMapping("/listClassesByOptionByYear/{pcMail}/{year}")
	public List<String> listClassesByOptionByYear(@PathVariable String pcMail, @PathVariable String year) {

		String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
		List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);

		List<String> lclasses = new ArrayList<String>();
		for (String opt : options) {
			System.out.println("--PC-------------------------------> Option: " + opt);
			lclasses.addAll(utilServices.findClassesByYearAndOption(year, opt.toLowerCase()));

			if (opt.equalsIgnoreCase("ARCTIC") || opt.equalsIgnoreCase("DS") || opt.equalsIgnoreCase("SAE")
					|| opt.equalsIgnoreCase("TWIN") || opt.equalsIgnoreCase("ALINFO")) {
				System.out.println("---> HERE ...");
				lclasses.add("4ALINFO1");
				lclasses.add("4ALINFO2");
				lclasses.add("4ALINFO3");
			}
		}

		List<String> unikClasses = lclasses.stream().distinct().collect(Collectors.toList());

		unikClasses.sort(Comparator.naturalOrder());
		// lclasses.stream().sorted().collect(Collectors.toList());

		for (String s : unikClasses) {
			System.out.println("---------> Class: " + s);
		}

		return unikClasses;

	}

	@GetMapping("/listClassesByYearAndOption/{pcMail}/{selectedYear}")
	public List<String> listClassesByYearAndOption(@PathVariable String pcMail, @PathVariable String selectedYear) {
		System.out.println("--PC-------------------gggg------------> selectedYear: " + selectedYear);
		String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
		List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);

		List<String> lclasses = new ArrayList<String>();
		for (String opt : options) {
			System.out.println("--PC-------------------gggg------------> Option: " + opt);
			System.out.println("--PC-------------------lol------------> Size: "
					+ utilServices.findClassesByYearAndOption(selectedYear, opt.toLowerCase()).size());

			lclasses.addAll(utilServices.findClassesByYearAndOption(selectedYear, opt.toLowerCase()));

			if (opt.equalsIgnoreCase("ARCTIC") || opt.equalsIgnoreCase("DS") || opt.equalsIgnoreCase("SAE")
					|| opt.equalsIgnoreCase("TWIN") || opt.equalsIgnoreCase("ALINFO")) {
				System.out.println("---> HERE ...");
				lclasses.add("4ALINFO1");
				lclasses.add("4ALINFO2");
				lclasses.add("4ALINFO3");
			}
		}

		List<String> unikClasses = lclasses.stream().distinct().collect(Collectors.toList());

		unikClasses.sort(Comparator.naturalOrder());
		// lclasses.stream().sorted().collect(Collectors.toList());

		for (String s : unikClasses) {
			System.out.println("---------> Class: " + s);
		}

		return unikClasses;

	}

	@GetMapping("/hi2022/{codeClass}/{pcMail}")
	public void lol(@PathVariable String codeClass, @PathVariable String pcMail) {
		List<String> stuALINFOs = optionStudentALTRepository.findStudentsALTByClasse(codeClass);

		System.out.println("----> Size : " + stuALINFOs.size());

		for (String s : stuALINFOs) {
			System.out.println("----> id : " + s);
			System.out.println("---------------------------------------------------------------> " + codeClass + ": "
					+ utilServices.findStudentFullNameById(s));
			// lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEt(s));
		}
	}

	@GetMapping("/listStudentsByClass/{codeClass}/{pcMail}")
	public List<StudentFullNameMailTelDto> listStudentsByClass(@PathVariable String codeClass,
															   @PathVariable String pcMail) {
		System.out.println("--PC----------------> class . pcMail: " + codeClass + " . " + pcMail);

		List<StudentFullNameMailTelDto> lStudents = new ArrayList<StudentFullNameMailTelDto>();
		if (!codeClass.contains("4ALINFO")) {
			System.out.println("------------------------> NOT ALINFO");

			lStudents = utilServices.findStudentsFullNameMailTelByClass(codeClass);

			for (StudentFullNameMailTelDto s : lStudents) {
				if (utilServices.findIdEncadrantPedagogiqueByStudent(s.getId()) != null) {
					s.setAffected("AFFECTEE");
				}
			}
		}

		if (codeClass.contains("4ALINFO")) {
			if (pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn")) {
				System.out.println("------------------------> IS ALINFO CD");

				List<String> stuALINFOs = optionStudentALTRepository.findStudentsALTByClasse(codeClass);

				System.out.println("----> Size ùùùù: " + stuALINFOs.size());

				for (String s : stuALINFOs) {
					System.out
							.println("----> " + codeClass + ": " + utilServices.findStudentFullNameById(s) + " - " + s);
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEt(s));
				}

				for (StudentFullNameMailTelDto s : lStudents) {
					if (utilServices.findIdEncadrantPedagogiqueByStudent(s.getId()) != null) {
						s.setAffected("AFFECTEE");
					}
				}
			} else {
				System.out.println("------------------------> IS ALINFO CPS");

				String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
				System.out.println("------------------------> idPC: " + idPC);
				List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);
				System.out.println("------------------------> options: " + options.size());

				List<String> stuALINFOs = new ArrayList<String>();
				for (String opt : options) {
					System.out.println("UNIT -------> opt . " + "class = " + opt + " . " + codeClass);
					stuALINFOs.addAll(optionStudentALTRepository.findStudentsALTByOptionAndClasse(opt, codeClass));
				}

				for (String s : stuALINFOs) {
					System.out.println("----> " + codeClass + ": " + utilServices.findStudentFullNameById(s));
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEt(s));
				}

				for (StudentFullNameMailTelDto s : lStudents) {
					if (utilServices.findIdEncadrantPedagogiqueByStudent(s.getId()) != null) {
						s.setAffected("AFFECTEE");
					}
				}
			}

		}

		lStudents.sort(Comparator.comparing(StudentFullNameMailTelDto::getFullName));
		return lStudents;
	}

	@GetMapping("/listStudentsByYearAndClass/{selectedYear}/{codeClass}/{pcMail}")
	public List<StudentFullNameMailTelDto> listStudentsByYearAndClass(@PathVariable String selectedYear,
																	  @PathVariable String codeClass, @PathVariable String pcMail) {

		System.out.println("--PC-------ccc---------> year . class . pcMail: " + selectedYear + " - " + codeClass + " - " + pcMail);

		List<StudentFullNameMailTelDto> lStudents = new ArrayList<StudentFullNameMailTelDto>();
		if(!codeClass.contains("4ALINFO"))
		{
			System.out.println("------------------------> NOT ALINFO");

			lStudents = utilServices.findStudentsFullNameMailTelByYearAndClass(selectedYear, codeClass);

			for(StudentFullNameMailTelDto s : lStudents)
			{
				s.setOption(utilServices.findOptionByClass(codeClass, optionRepository.listOptionsByYear(selectedYear)).replace("_01", ""));

				if(utilServices.findIdEncadrantPedagogiqueByStudent(s.getId()) != null)
				{
					s.setAffected("AFFECTEE");
				}
			}
		}
		if(codeClass.contains("4ALINFO"))
		{
			if(pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn"))
			{
				System.out.println("------------------------> IS ALINFO CD");

				List<String> stuALINFOs = optionStudentALTRepository.findStudentsALTByYearAndClasse(selectedYear, codeClass);

				System.out.println("----> Size ùùùù: " + stuALINFOs.size());

				for(String s : stuALINFOs)
				{
					System.out.println("----> " + codeClass + ": " + utilServices.findStudentFullNameByIdYear(s, selectedYear) + " - " + s);
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEtWithActivatedYears(s));
				}

				for(StudentFullNameMailTelDto s : lStudents)
				{
					s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), selectedYear));
					if(utilServices.findIdEncadrantPedagogiqueByStudent(s.getId()) != null)
					{
						s.setAffected("AFFECTEE");
					}
				}
			}
			else
			{
				System.out.println("------------------------> IS ALINFO CPS");

				String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
				System.out.println("------------------------> idPC: " + idPC);
				List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);
				System.out.println("------------------------> options: " + options.size());

				List<String> stuALINFOs = new ArrayList<String>();
				for(String opt : options)
				{
					System.out.println("UNIT -------> opt . " + "class = " + opt + " . " + codeClass);
					stuALINFOs.addAll(optionStudentALTRepository.findStudentsALTByYearAndOptionAndClasse(selectedYear, opt, codeClass));
				}

				for(String s : stuALINFOs)
				{
					System.out.println("--sssssssss--> " + codeClass + ": " + utilServices.findStudentFullNameByIdYear(s, selectedYear));
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEtWithYear(s, selectedYear));
				}

				for(StudentFullNameMailTelDto s : lStudents)
				{
					s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), selectedYear));
					if(utilServices.findIdEncadrantPedagogiqueByStudentByYear(s.getId(), selectedYear) != null)
					{
						s.setAffected("AFFECTEE");
					}
				}
			}

		}

		lStudents.sort(Comparator.comparing(StudentFullNameMailTelDto::getFullName));
		return lStudents;

	}

	@GetMapping("/listStudentsByClassForExp/{codeClass}/{pcMail}")
	public List<StudentFullNameMailTelDto> listStudentsByClassForExp(@PathVariable String codeClass,
																	 @PathVariable String pcMail) {
		System.out.println("--PC----------------> class . pcMail: " + codeClass + " . " + pcMail);

		List<StudentFullNameMailTelDto> lStudents = new ArrayList<StudentFullNameMailTelDto>();
		if (!codeClass.contains("4ALINFO")) {
			System.out.println("------------------------> NOT ALINFO : " + codeClass);

			lStudents = utilServices.findStudentsFullNameMailTelByClass(codeClass);

			for (StudentFullNameMailTelDto s : lStudents) {
				if (utilServices.findIdExpertByStudent(s.getId()) != null) {
					s.setAffected("AFFECTEE");
				}
			}
		}

		if (codeClass.contains("4ALINFO")) {
			if (pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn")) {
				System.out.println("------------------------> IS ALINFO CD");

				List<String> stuALINFOs = optionStudentALTRepository.findStudentsALTByClasse(codeClass);

				System.out.println("----> Size ùùùù: " + stuALINFOs.size());

				for (String s : stuALINFOs) {
					System.out
							.println("----> " + codeClass + ": " + utilServices.findStudentFullNameById(s) + " - " + s);
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEt(s));
				}

				for (StudentFullNameMailTelDto s : lStudents) {
					if (utilServices.findIdExpertByStudent(s.getId()) != null) {
						s.setAffected("AFFECTEE");
					}
				}
			} else {
				System.out.println("------------------------> IS ALINFO CPS");

				String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
				System.out.println("------------------------> idPC: " + idPC);
				List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);
				System.out.println("------------------------> options: " + options.size());

				List<String> stuALINFOs = new ArrayList<String>();
				for (String opt : options) {
					System.out.println("UNIT -------> opt . " + "class = " + opt + " . " + codeClass);
					stuALINFOs.addAll(optionStudentALTRepository.findStudentsALTByOptionAndClasse(opt, codeClass));
				}

				for (String s : stuALINFOs) {
					System.out.println("----> " + codeClass + ": " + utilServices.findStudentFullNameById(s));
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEt(s));
				}

				for (StudentFullNameMailTelDto s : lStudents) {
					if (utilServices.findIdExpertByStudent(s.getId()) != null) {
						s.setAffected("AFFECTEE");
					}
				}
			}

		}

		lStudents.sort(Comparator.comparing(StudentFullNameMailTelDto::getFullName));
		return lStudents;
	}

	@GetMapping("/listStudentsByClassAndYearForExp/{codeClass}/{pcMail}")
	public List<StudentFullNameMailTelDto> listStudentsByClassAndYearForExp(@PathVariable String codeClass, @PathVariable String pcMail)
	{

		String year = "2021";
		System.out.println("--PC----------------> class . pcMail: " + codeClass + " . " + pcMail);

		List<StudentFullNameMailTelDto> lStudents = new ArrayList<StudentFullNameMailTelDto>();
		if(!codeClass.contains("4ALINFO"))
		{
			System.out.println("------------------------> NOT ALINFO : " + codeClass);

			lStudents = utilServices.findStudentsFullNameMailTelByYearAndClass(year, codeClass);

			for(StudentFullNameMailTelDto s : lStudents)
			{

				// Note Expert (Note Restitution)
				String idExpert = utilServices.findIdExpertByStudent(s.getId());
				if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021") != null)
				{
					System.out.println("-$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> idExpert : " + idExpert + " - " + noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021").toString());
					s.setNoteRestitution(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021").toString());
				}
				else
				{
					s.setNoteRestitution("--");
				}

				s.setOption(utilServices.findOptionByClass(s.getId(), optionRepository.listOptionsByYear(year)).replace("_01", ""));
				if(utilServices.findIdExpertByStudentAndYear(s.getId(), year) != null)
				{
					s.setAffected("AFFECTEE");
				}
			}
		}

		if(codeClass.contains("4ALINFO"))
		{
			if(pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn"))
			{
				System.out.println("------------------------> IS ALINFO CD");

				List<String> stuALINFOs = optionStudentALTRepository.findStudentsALTByYearAndClasse(year, codeClass);

				System.out.println("----> Size ùùùù: " + stuALINFOs.size());

				for(String s : stuALINFOs)
				{
					System.out.println("----> " + codeClass + ": " + utilServices.findStudentFullNameById(s) + " - " + s);
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEtAndYear(s, year));
				}

				for(StudentFullNameMailTelDto s : lStudents)
				{

					// Note Expert (Note Restitution)
					String idExpert = utilServices.findIdExpertByStudent(s.getId());
					if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021") != null)
					{
						System.out.println("-$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> idExpert : " + idExpert + " - " + noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021").toString());
						s.setNoteRestitution(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021").toString());
					}
					else
					{
						s.setNoteRestitution("--");
					}

					s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), year));
					if(utilServices.findIdExpertByStudentAndYear(s.getId(), year) != null)
					{
						s.setAffected("AFFECTEE");
					}
				}
			}
			else
			{
				System.out.println("------------------------> IS ALINFO CPS");

				String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
				System.out.println("------------------------> idPC: " + idPC);
				List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);
				System.out.println("------------------------> options: " + options.size());

				List<String> stuALINFOs = new ArrayList<String>();
				for(String opt : options)
				{
					System.out.println("UNIT -------> opt . " + "class = " + opt + " . " + codeClass);
					stuALINFOs.addAll(optionStudentALTRepository.findStudentsALTByYearAndOptionAndClasse(year, opt, codeClass));
				}

				for(String s : stuALINFOs)
				{
					System.out.println(s + "----> " + codeClass + ": " + utilServices.findStudentFullNameById(s));
					lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEtAndYear(s, "2021"));
				}

				for(StudentFullNameMailTelDto s : lStudents)
				{

					// Note Expert (Note Restitution)
					String idExpert = utilServices.findIdExpertByStudent(s.getId());
					if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021") != null)
					{
						System.out.println("-$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> idExpert : " + idExpert + " - " + noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021").toString());
						s.setNoteRestitution(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), "2021").toString());
					}
					else
					{
						s.setNoteRestitution("--");
					}

					s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), year));
					if(utilServices.findIdExpertByStudentAndYear(s.getId(), year) != null)
					{
						s.setAffected("AFFECTEE");
					}
				}
			}

		}

		lStudents.sort(Comparator.comparing(StudentFullNameMailTelDto::getFullName));
		return lStudents;

	}

	@GetMapping("/listStudentsByClassAndYearForExpPARAM/{year}/{codeClass}/{pcMail}")
	public List<StudentFullNameMailTelDto> listStudentsByClassAndYearForExpPARAM(@PathVariable String year, @PathVariable String codeClass, @PathVariable String pcMail)
	{
		{

			System.out.println("--PC----------------> class . pcMail: " + codeClass + " . " + pcMail);

			List<StudentFullNameMailTelDto> lStudents = new ArrayList<StudentFullNameMailTelDto>();
			if(!codeClass.contains("4ALINFO"))
			{
				System.out.println("------------------------> NOT ALINFO : " + codeClass);

				lStudents = utilServices.findStudentsFullNameMailTelByYearAndClass(year, codeClass);

				for(StudentFullNameMailTelDto s : lStudents)
				{
					// Note Expert (Note Restitution)
					String idExpert = utilServices.findIdExpertByStudent(s.getId());
					if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year) != null)
					{
						System.out.println("-$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> idExpert : " + idExpert + " - " + noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year).toString());
						s.setNoteRestitution(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year).toString());
					}
					else
					{
						s.setNoteRestitution("--");
					}

					//s.setOption(utilServices.findOptionByClass(s.getId(), optionRepository.listOptionsByYear(year)).replace("_01", ""));
					if(codeClass.contains("4ALINFO"))
					{
						s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), year));
						System.out.println("--> 2.1");
					}
					else
					{
						s.setOption(utilServices.findOptionByClass(codeClass, optionRepository.listOptionsByYear(year)).replace("_01", ""));
						System.out.println("--> 2.2");
					}
					if(utilServices.findIdExpertByStudentAndYear(s.getId(), year) != null)
					{
						s.setAffected("AFFECTEE");
					}
				}
			}

			if(codeClass.contains("4ALINFO"))
			{
				if(pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn"))
				{
					System.out.println("------------------------> IS ALINFO CD");

					List<String> stuALINFOs = optionStudentALTRepository.findStudentsALTByYearAndClasse(year, codeClass);

					System.out.println("----> Size ùùùù: " + stuALINFOs.size());

					for(String s : stuALINFOs)
					{
						System.out.println("----> " + codeClass + ": " + utilServices.findStudentFullNameById(s) + " - " + s);
						lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEtAndYear(s, year));
					}

					for(StudentFullNameMailTelDto s : lStudents)
					{
						// Note Expert (Note Restitution)
						String idExpert = utilServices.findIdExpertByStudent(s.getId());
						if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year) != null)
						{
							System.out.println("-$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> idExpert : " + idExpert + " - " + noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year).toString());
							s.setNoteRestitution(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year).toString());
						}
						else
						{
							s.setNoteRestitution("--");
						}

						//s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), year));
						if(codeClass.contains("4ALINFO"))
						{
							s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), year));
							System.out.println("--> 2.1");
						}
						else
						{
							s.setOption(utilServices.findOptionByClass(codeClass, optionRepository.listOptionsByYear(year)).replace("_01", ""));
							System.out.println("--> 2.2");
						}
						if(utilServices.findIdExpertByStudentAndYear(s.getId(), year) != null)
						{
							s.setAffected("AFFECTEE");
						}
					}
				}
				else
				{
					System.out.println("------------------------> IS ALINFO CPS");

					String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
					System.out.println("------------------------> idPC: " + idPC);
					List<String> options = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);
					System.out.println("------------------------> options: " + options.size());

					List<String> stuALINFOs = new ArrayList<String>();
					for(String opt : options)
					{
						System.out.println("UNIT -------> opt . " + "class = " + opt + " . " + codeClass);
						stuALINFOs.addAll(optionStudentALTRepository.findStudentsALTByYearAndOptionAndClasse(year, opt, codeClass));
					}

					for(String s : stuALINFOs)
					{
						System.out.println(s + "----> " + codeClass + ": " + utilServices.findStudentFullNameById(s));
						lStudents.addAll(utilServices.findStudentsFullNameMailTelByIdEtAndYear(s, year));
					}

					for(StudentFullNameMailTelDto s : lStudents)
					{
						// Note Expert (Note Restitution)
						String idExpert = utilServices.findIdExpertByStudent(s.getId());
						if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year) != null)
						{
							System.out.println("-$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> idExpert : " + idExpert + " - " + noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year).toString());
							s.setNoteRestitution(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExpert, s.getId(), year).toString());
						}
						else
						{
							s.setNoteRestitution("--");
						}

						//s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), year));
						if(codeClass.contains("4ALINFO"))
						{
							s.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(s.getId(), year));
							System.out.println("--> 2.1");
						}
						else
						{
							s.setOption(utilServices.findOptionByClass(codeClass, optionRepository.listOptionsByYear(year)).replace("_01", ""));
							System.out.println("--> 2.2");
						}
						if(utilServices.findIdExpertByStudentAndYear(s.getId(), year) != null)
						{
							s.setAffected("AFFECTEE");
						}
					}
				}

			}

			lStudents.sort(Comparator.comparing(StudentFullNameMailTelDto::getFullName));
			return lStudents;
		}
	}

	// @GetMapping("/sars")
	// public List<String> sars()
	// {
	//
	// System.out.println("------------------------> 1: " +
	// inscriptionRepository.sars2());
	//
	// return inscriptionRepository.sars();
	// }

	@GetMapping("/findAllAffectedStudentsToAE/{idAE}")
	public List<StudentAffectationDetailsDto> findAllAffectedStudentsToAE(@PathVariable String idAE) {
		List<StudentAffectationDetailsDto> las = utilServices.findStudentsDtoTrainedByPE(idAE);

		for (StudentAffectationDetailsDto sd : las) {
			sd.setUniverYear(sd.getUniverYear() + "-" + (Integer.parseInt(sd.getUniverYear()) + 1));
			if (sd.getClasse().contains("4ALINFO")) {
				sd.setOption(optionStudentALTRepository.findOptionByStudentALT(sd.getIdEt()));
			} else {
				sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021"))
						.replace("_01", ""));
			}
		}

		return las;
	}

	@GetMapping("/findAllAffectedStudentsToAEByYear/{idAE}/{year}")
	public List<StudentAffectationDetailsDto> findAllAffectedStudentsToAEByYear(@PathVariable String idAE,
																				@PathVariable String year) {
		List<StudentAffectationDetailsDto> las = utilServices.findStudentsDtoTrainedByPEAndYearNew(idAE, year);
		System.out.println("--------------------------------------------------- ALL: " + las.size());

		for (StudentAffectationDetailsDto sd : las) {
			System.out.println("----> UNIT: " + sd.getIdEt() + " - " + sd.getFullName());
			sd.setUniverYear(sd.getUniverYear() + "-" + (Integer.parseInt(sd.getUniverYear()) + 1));
			if (sd.getClasse().contains("4ALINFO")) {
				sd.setOption(optionStudentALTRepository.findOptionByStudentALTAndYear(sd.getIdEt(), year));
				System.out.println("--> 2.1");
			} else {
				sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear(year))
						.replace("_01", ""));
				System.out.println("--> 2.2");
			}
		}

		return las;
	}

	@GetMapping("/findAllAffectedStudentsToAExp/{idAE}")
	public List<StudentAffectationDetailsDto> findAllAffectedStudentsToAExp(@PathVariable String idAE) {
		List<StudentAffectationDetailsDto> las = utilServices.findStudentsDtoTrainedByPExp(idAE);

		System.out.println(
				"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$: " + las.size());

		for (StudentAffectationDetailsDto sd : las) {
			sd.setUniverYear(sd.getUniverYear() + "-" + (Integer.parseInt(sd.getUniverYear()) + 1));
			if (sd.getClasse().contains("4ALINFO")) {
				sd.setOption(optionStudentALTRepository.findOptionByStudentALT(sd.getIdEt()));
			} else {
				sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021"))
						.replace("_01", ""));
			}
		}

		return las;
	}

	private void serverSendNotificationThroughoutGmailGCandGDWithPair(String selectedDate, String studentMail,
																	  String pairMail, String idEtToSTN, String pairFullName, String selectedStartHour, String selectedEndHour,
																	  String salle, String presjury, String pedagogicalEncadrant, String expertEns, String nameRSS)
			throws IOException, GeneralSecurityException {

		String dateSoutenance = selectedDate;
		String mailPE = teacherRepository.findMailTeacherById(pedagogicalEncadrant);
		String mailEXP = teacherRepository.findMailTeacherById(expertEns);

		String nomPE = teacherRepository.findNameTeacherById(pedagogicalEncadrant);
		String nomEXP = teacherRepository.findNameTeacherById(expertEns);

		String mailJP = null;
		String nomJP = null;
		List<String> juryHosts = new ArrayList<String>();
		List<String> respHosts = new ArrayList<String>();

		List<String> calendarHosts = new ArrayList<String>();

		if (presjury.contains("EXTERNE")) {
			nomJP = presjury.substring(0, presjury.lastIndexOf("EXTERNE"));
			juryHosts.add(mailPE);
			juryHosts.add(mailEXP);

			calendarHosts.add(mailPE);
			calendarHosts.add(mailEXP);
			calendarHosts.add(utilServices.findStudentMailById(idEtToSTN));
			calendarHosts.add(pairMail);

			System.out.println("PAIR - JP EXTERN ######################################## HOSTS GC -> mail: " + mailPE
					+ " - " + mailEXP + " - " + utilServices.findStudentMailById(idEtToSTN) + " - " + pairMail);
			System.out.println("PAIR - JP EXTERN ######################################## HOSTS JM -> mail: " + mailPE
					+ " - " + mailEXP);

		} else {
			mailJP = teacherRepository.findMailTeacherById(presjury);
			nomJP = teacherRepository.findNameTeacherById(presjury);
			juryHosts.add(mailJP);
			juryHosts.add(mailPE);
			juryHosts.add(mailEXP);

			calendarHosts.add(mailJP);
			calendarHosts.add(mailPE);
			calendarHosts.add(mailEXP);
			calendarHosts.add(utilServices.findStudentMailById(idEtToSTN));
			calendarHosts.add(pairMail);

			System.out.println("PAIR - JP EXTERN ######################################## HOSTS GC -> mail: " + mailJP
					+ " - " + mailPE + " - " + mailEXP + " - " + utilServices.findStudentMailById(idEtToSTN) + " - "
					+ pairMail);
			System.out.println("PAIR - JP EXTERN ######################################## HOSTS JM -> mail: " + mailJP
					+ " - " + mailPE + " - " + mailEXP);

		}

		String juryMembers = juryHosts.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"')
				.collect(Collectors.joining(", "));

		/******************************************************************************************* Mail CPS ***/
		String currentClass = utilServices.findCurrentClassByIdEt(idEtToSTN);
		String studentOption = null;
		if(currentClass.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idEtToSTN) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(currentClass, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));
		}

		System.out.println("----------------> Code Option: " + studentOption);

		//String mailAcademicCoordinator = pedagogicalCoordinatorRepository
		//		.findPedagogicalCoordinatorMailByCodeOption(studentOption);

		String idCPS = pedagogicalCoordinatorRepository.gotIdEnsPedagogicalCoordinatorByOption(studentOption.toLowerCase());

		String mailCPS = "saria.essid@esprit.tn";
		if(
				studentOption.toUpperCase().contains("SIM") || studentOption.toUpperCase().contains("GAMIX") ||
						studentOption.toUpperCase().contains("WIN") || studentOption.toUpperCase().contains("IOSYS")
		)
		{
			if(studentOption.toUpperCase().contains("SIM"))
			{
				mailCPS = "CPS_SIM@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("GAMIX"))
			{
				mailCPS = "CPS_GAMIX@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("WIN"))
			{
				mailCPS = "CPS_WIN@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("TWIN"))
			{
				mailCPS = "CPS_TWIN@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("IOSYS"))
			{
				mailCPS = "CPS_IOSYS@esprit.tn";
			}
		}
		else
		{
			mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorByIdNew(idCPS);
		}



		/******************************************************************************************* Mail RSS ***/
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(idEtToSTN).get(0));


		// Add respHosts : CPS + RSS
		respHosts.add(mailCPS);respHosts.add(mailRSS);
		String respMembers = respHosts.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));

		// Notify GC
		String selectedStartHourGC = utilServices.convertHourToGCH(selectedStartHour);
		String selectedEndHourGC = utilServices.convertHourToGCH(selectedEndHour);
		String fullStartDateTime = utilServices.gotFormattedDate(selectedDate) + "T" + selectedStartHourGC + ":00.000Z";
		String fullEndDateTime = utilServices.gotFormattedDate(selectedDate) + "T" + selectedEndHourGC + ":00.000Z";

		// System.out.println("------------With Pair-------------> HOSTS STN - 1: " +
		// fullStartDateTime + " - " + selectedDate);
		// System.out.println("-----------With Pair--------------> HOSTS STN - 2: " +
		// fullEndDateTime);

		String meetLink = calendarServices.notifyWithGoogleCalendar("Soutenance de PFE", salle,
				"Invitation pour Soutenance de PFE", fullStartDateTime, fullEndDateTime, calendarHosts);

		// Notify Mail
		List<String> studentPair = new ArrayList<String>();
		studentPair.add(studentMail);
		studentPair.add(pairMail);
		String pairsMail = studentPair.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"')
				.collect(Collectors.joining(", "));
		String subject = "Soutenance de PFE";

		sendMailSTNServices.sendMailPairWithRSSandCPS(subject, pairsMail, respMembers, dateSoutenance, selectedStartHour, selectedEndHour, meetLink ,nomJP, nomPE, nomEXP, salle, nameRSS);

//		sendMailSTNServices.sendMailPair(subject, pairsMail, dateSoutenance, selectedStartHour, selectedEndHour,
//				meetLink, nomJP, nomPE, nomEXP, salle, nameRSS);

		// Get Path Rapport
		String pathRapport = fichePFERepository.findPathRapportVersion2ByStudent(idEtToSTN).get(0);

		// System.out.println("MAIN------------------With
		// Pair-----------------------------> Google Drive Start: " + pathRapport + " -
		// " + new Date());
		String sharedStudentESPReport = GoogleDriveServices.getSharedFileLinkAfterCreatingInGoogleDrive(pathRapport,
				calendarHosts);

		// System.out.println("MAIN------------------With
		// Pair-----------------------------> Google Drive End : " + new Date());
		sendMailSTNServices.sendMailJuryPresidentPairWithRSSandCPS(subject, utilServices.findStudentFullNameById(idEtToSTN), pairFullName, juryMembers, respMembers, dateSoutenance, selectedStartHour, selectedEndHour, meetLink ,nomJP, nomPE, nomEXP, salle, sharedStudentESPReport, nameRSS);

		sendMailSTNServices.sendMailToScolarityForPair("Réservation Salle pour Soutenance PFE",
				utilServices.findStudentFullNameById(idEtToSTN), pairFullName, dateSoutenance, selectedStartHour,
				selectedEndHour, salle, nameRSS);

	}

	private void serverSendNotificationThroughoutGmailGCandGDLonely(String selectedDate, String studentMail,
																	String idEtToSTN, String selectedStartHour, String selectedEndHour, String salle, String presjury,
																	String pedagogicalEncadrant, String expertEns, String nameRSS)
			throws IOException, GeneralSecurityException {

		String dateSoutenance = selectedDate;
		String mailPE = teacherRepository.findMailTeacherById(pedagogicalEncadrant);
		String mailEXP = teacherRepository.findMailTeacherById(expertEns);

		String nomPE = teacherRepository.findNameTeacherById(pedagogicalEncadrant);
		String nomEXP = teacherRepository.findNameTeacherById(expertEns);

		String mailJP = null;
		String nomJP = null;
		List<String> juryHosts = new ArrayList<String>();
		List<String> respHosts = new ArrayList<String>();
		List<String> calendarHosts = new ArrayList<String>();

		if (presjury.contains("EXTERNE")) {
			nomJP = presjury.substring(0, presjury.lastIndexOf("EXTERNE"));
			juryHosts.add(mailPE);
			juryHosts.add(mailEXP);

			calendarHosts.add(mailPE);
			calendarHosts.add(mailEXP);
			calendarHosts.add(utilServices.findStudentMailById(idEtToSTN));

			System.out.println("LONELY - JP EXTERN ######################################## HOSTS GC -> mail: " + mailPE
					+ " - " + mailEXP + " - " + utilServices.findStudentMailById(idEtToSTN));
			System.out.println("LONELY - JP EXTERN ######################################## HOSTS JM -> mail: " + mailPE
					+ " - " + mailEXP);

		} else {
			mailJP = teacherRepository.findMailTeacherById(presjury);
			nomJP = teacherRepository.findNameTeacherById(presjury);
			juryHosts.add(mailJP);
			juryHosts.add(mailPE);
			juryHosts.add(mailEXP);

			calendarHosts.add(mailJP);
			calendarHosts.add(mailPE);
			calendarHosts.add(mailEXP);
			calendarHosts.add(utilServices.findStudentMailById(idEtToSTN));

			System.out.println("LONELY - JP INTERN ######################################## HOSTS GC -> mail: " + mailJP
					+ " - " + mailPE + " - " + mailEXP + " - " + utilServices.findStudentMailById(idEtToSTN));
			System.out.println("LONELY - JP INTERN ######################################## HOSTS JM -> mail: " + mailJP
					+ " - " + mailPE + " - " + mailEXP);

		}

		String juryMembers = juryHosts.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"')
				.collect(Collectors.joining(", "));

		/******************************************************************************************* Mail CPS ***/
		String currentClass = utilServices.findCurrentClassByIdEt(idEtToSTN);
		String studentOption = null;
		if(currentClass.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idEtToSTN) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(currentClass, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));
		}

		System.out.println("----------------> Code Option: " + studentOption);

		//String mailAcademicCoordinator = pedagogicalCoordinatorRepository
		//		.findPedagogicalCoordinatorMailByCodeOption(studentOption);

		String idCPS = pedagogicalCoordinatorRepository.gotIdEnsPedagogicalCoordinatorByOption(studentOption.toLowerCase());

		String mailCPS = "saria.essid@esprit.tn";
		if(
				studentOption.toUpperCase().contains("SIM") || studentOption.toUpperCase().contains("GAMIX") ||
						studentOption.toUpperCase().contains("WIN") || studentOption.toUpperCase().contains("IOSYS")
		)
		{
			if(studentOption.toUpperCase().contains("SIM"))
			{
				mailCPS = "CPS_SIM@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("GAMIX"))
			{
				mailCPS = "CPS_GAMIX@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("WIN"))
			{
				mailCPS = "CPS_WIN@esprit.tn";
			}
			if(studentOption.toUpperCase().contains("TWIN"))
			{
				mailCPS = "CPS_TWIN@esprit.tn";
			}

			if(studentOption.toUpperCase().contains("IOSYS"))
			{
				mailCPS = "CPS_IOSYS@esprit.tn";
			}
		}
		else
		{
			mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorByIdNew(idCPS);
		}



		/******************************************************************************************* Mail RSS ***/
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(idEtToSTN).get(0));


		// Add respHosts : CPS + RSS
		respHosts.add(mailCPS);respHosts.add(mailRSS);
		String respMembers = respHosts.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));


		// Notify GC
		String selectedStartHourGC = utilServices.convertHourToGCH(selectedStartHour);
		String selectedEndHourGC = utilServices.convertHourToGCH(selectedEndHour);
		String fullStartDateTime = utilServices.gotFormattedDate(selectedDate) + "T" + selectedStartHourGC + ":00.000Z";
		String fullEndDateTime = utilServices.gotFormattedDate(selectedDate) + "T" + selectedEndHourGC + ":00.000Z";

		// System.out.println("---------------Lonely----------> HOSTS STN - 1: " +
		// fullStartDateTime + " - " + selectedDate);
		// System.out.println("-------------Lonely------------> HOSTS STN - 2: " +
		// fullEndDateTime);

		String meetLink = calendarServices.notifyWithGoogleCalendar("Soutenance de PFE", salle,
				"Invitation pour Soutenance de PFE", fullStartDateTime, fullEndDateTime, calendarHosts);

		// Notify Mail
		String subject = "Soutenance de PFE";
		sendMailSTNServices.sendMailStudentWithRSSandCPS(subject, studentMail, respMembers, dateSoutenance, selectedStartHour, selectedEndHour,
				meetLink, nomJP, nomPE, nomEXP, salle, nameRSS);

		// Get Path Rapport
		String pathRapport = fichePFERepository.findPathRapportVersion2ByStudent(idEtToSTN).get(0);

		// System.out.println("MAIN-----------------------Lonely------------------------>
		// Google Drive Start: " + pathRapport + " - " + new Date());
		String sharedStudentESPReport = GoogleDriveServices.getSharedFileLinkAfterCreatingInGoogleDrive(pathRapport,
				calendarHosts);

		String technicalFolder = fichePFERepository.findPathTechnicalFolderByStudent(idEtToSTN).get(0);
		System.out.println("MAIN--------------------" + technicalFolder + "--");

		if (technicalFolder != null) {
			String sharedStudentTechnicalFolder = GoogleDriveServices
					.getSharedFileLinkAfterCreatingInGoogleDrive(technicalFolder, calendarHosts);
			sendMailSTNServices.sendMailJuryPresidentGC(subject, utilServices.findStudentFullNameById(idEtToSTN),
					juryMembers, dateSoutenance, selectedStartHour, selectedEndHour, meetLink, nomJP, nomPE, nomEXP,
					salle, sharedStudentESPReport, sharedStudentTechnicalFolder, nameRSS);
			sendMailSTNServices.sendMailToScolarity("Réservation Salle pour Soutenance PFE",
					utilServices.findStudentFullNameById(idEtToSTN), dateSoutenance, selectedStartHour, selectedEndHour,
					salle, nameRSS);
		}

		if (technicalFolder == null) {
			sendMailSTNServices.sendMailJuryPresidentWithRSSandCPS(subject, utilServices.findStudentFullNameById(idEtToSTN),
					juryMembers, respMembers, dateSoutenance, selectedStartHour, selectedEndHour, meetLink, nomJP, nomPE, nomEXP,
					salle, sharedStudentESPReport, nameRSS);
			sendMailSTNServices.sendMailToScolarity("Réservation Salle pour Soutenance PFE",
					utilServices.findStudentFullNameById(idEtToSTN), dateSoutenance, selectedStartHour, selectedEndHour,
					salle, nameRSS);
		}

		// System.out.println("MAIN-----------------------Lonely------------------------>
		// Google Drive End : " + new Date());

	}

	/******************************************************
	 * Registration & Authentication
	 ******************************************************/

	@PostMapping("/simpleSigninStudent")
	public ResponseEntity<?> simpleSigninStudent(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("Student *************SARS*****************> Sign-In with: " + loginRequest.getId() + " - "
				+ loginRequest.getPassword());

		List<String> activatedYears = settingsRepository.findActivatedYears();

		List<String> studentsCJ = studentRepository.findCJALTStudents(activatedYears);
		List<String> studentsCS = studentRepository.findCSStudents(activatedYears);

		System.out.println("=............................=CJ=> Sign-In with: " + studentsCJ.size());
		System.out.println("=............................=CS=> Sign-In with: " + studentsCS.size());

		for (int i = 0; i < 5; i++) {
			System.out.println("---***---> " + i);
		}

		for (int i = 0; i < studentsCJ.size(); i++) {
			System.out.println(i + " -----------> " + studentsCJ.get(i) + " - " + loginRequest.getId());
			if (studentsCJ.get(i).equalsIgnoreCase(loginRequest.getId())) {
				System.out.println("--------GOT IT-----> " + studentsCJ.get(i));
			}

			// break;
		}

		UserResponse pstudent = new UserResponse();

		if (!studentsCJ.contains(loginRequest.getId()) && !studentsCS.contains(loginRequest.getId())) {
			System.out.println("NO USERNAME - NO PASSWORD ===> Sign-In ERROR");

			StudentCS studentfa = studentRepository.firstAuthenticationCS(loginRequest.getId(),
					loginRequest.getPassword()).orElseThrow(() -> new RuntimeException("ERROR ! : Verify your credentials."));
			if(studentfa != null && studentfa.getPwdJWTEtudiant() == null)
			{
				pstudent = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
			}

			StudentCJ studentfaj= studentRepository.firstAuthentication(loginRequest.getId(),
					loginRequest.getPassword()).orElseThrow(() -> new RuntimeException("ERROR ! : Verify your credentials."));
			if(studentfaj != null && studentfaj.getPwdJWTEtudiant() == null)
			{
				pstudent = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
			}
		}

		if (studentsCJ.contains(loginRequest.getId())) {
			System.out.println("Student ******************************> CDJour");

			String stuPwdJwt = studentRepository.findStudentJWTPWDById(loginRequest.getId());

			if (stuPwdJwt == null) {
				System.out.println("Student --------------> 0. First Login: " + pstudent.getId());
				StudentCJ studentfa = studentRepository
						.firstAuthentication(loginRequest.getId(), loginRequest.getPassword())
						.orElseThrow(() -> new RuntimeException("ERROR ! : Verify your credentials."));
				if (studentfa != null && studentfa.getPwdJWTEtudiant() == null) {
					pstudent = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
					System.out.println("Student --------------> 1. First Login: " + pstudent.getId());
				}
			}

			if (stuPwdJwt != null) {
				System.out.println("Student --------------> 0. Second Login 0: " + loginRequest.getId() + " - "
						+ loginRequest.getPassword());

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

				System.out.println("Student --------------> 0. Second Login 1 ");

				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("Student --------------> 0. Second Login 2 ");
				String jwt = jwtUtils.generateStudentJwtToken(authentication);
				System.out.println("Student --------------> 0. Second Login 3 ");
				StudentDetailsImpl studentDetails = (StudentDetailsImpl) authentication.getPrincipal();
				System.out.println("Student --------------> 0. Second Login 4: " + studentDetails.getPassword());
				pstudent = new UserResponse(jwt, studentDetails.getId(), studentDetails.getPassword());
				System.out.println("Student --------------> 0. Second Login 5 ");
			}
		}

		if (studentsCS.contains(loginRequest.getId())) {
			System.out.println("Student ******************************> CDSoir");

			String stuPwdJwt = studentRepository.findStudentJWTPWDCSById(loginRequest.getId());

			System.out.println("Student ******************************> CDSoir - stuPwdJwt: " + stuPwdJwt);

			if (stuPwdJwt == null) {
				System.out.println("Student --------------> 0. First Login: " + pstudent.getId());
				StudentCS studentfa = studentRepository
						.firstAuthenticationCS(loginRequest.getId(), loginRequest.getPassword())
						.orElseThrow(() -> new RuntimeException("ERROR ! : Verify your credentials."));
				if (studentfa != null && studentfa.getPwdJWTEtudiant() == null) {
					pstudent = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
					System.out.println("Student --------------> 1. First Login: " + pstudent.getId());
				}
			}

			if (stuPwdJwt != null) {
				System.out.println("Student --------------> 0. Second Login 0: " + loginRequest.getId() + " - "
						+ loginRequest.getPassword());

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

				System.out.println("Student --------------> 0. Second Login 1 ");

				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("Student --------------> 0. Second Login 2 ");
				String jwt = jwtUtils.generateStudentCSJwtToken(authentication);
				System.out.println("Student --------------> 0. Second Login 3 ");
				StudentCSDetailsImpl studentDetails = (StudentCSDetailsImpl) authentication.getPrincipal();
				System.out.println("Student --------------> 0. Second Login 4: " + studentDetails.getPassword());
				pstudent = new UserResponse(jwt, studentDetails.getId(), studentDetails.getPassword());
				System.out.println("Student --------------> 0. Second Login 5 ");
			}
		}

		/**********************************************************************************************************************/

		System.out.println("Authentication DONE ******************************>" + pstudent.getId() + " - "
				+ pstudent.getPassword());

		return ResponseEntity.ok(pstudent);
	}

	@PostMapping("/simpleSigninResponsableServiceStage")
	public ResponseEntity<?> simpleSigninInternshipServices(@Valid @RequestBody LoginRequest loginRequest) {
		// System.out.println("InternshipServices --------------> Sign-In with: " +
		// loginRequest.getId() + " - " + loginRequest.getPassword());

		String rSSPwdJwt = responsableServiceStageRepository
				.findResponsableServiceStageJWTPWDById(loginRequest.getId());
		UserResponse pRSS = new UserResponse();

		if (rSSPwdJwt == null) {
			// System.out.println("ResponsableServiceStage --------------> 0. First Login: "
			// + pRSS.getId());
			ResponsableServiceStage responsableServiceStagefa = responsableServiceStageRepository
					.firstAuthentication(loginRequest.getId(), loginRequest.getPassword())
					.orElseThrow(() -> new RuntimeException("ERROR ! : Verify your credentials."));
			if (responsableServiceStagefa != null && responsableServiceStagefa.getPwdJWTRSS() == null) {
				pRSS = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
				// System.out.println("ResponsableServiceStage --------------> 1. First Login: "
				// + pRSS.getId());
			}
		}

		if (rSSPwdJwt != null) {
			// System.out.println("ResponsableServiceStage --------------> 0. Second Login
			// 0: " + loginRequest.getId() + " - " + loginRequest.getPassword());

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

			// System.out.println("ResponsableServiceStage --------------> 0. Second Login 1
			// ");

			SecurityContextHolder.getContext().setAuthentication(authentication);
			// System.out.println("ResponsableServiceStage --------------> 0. Second Login 2
			// ");
			String jwt = jwtUtils.generateResponsableServiceStageJwtToken(authentication);
			// System.out.println("ResponsableServiceStage --------------> 0. Second Login 3
			// ");
			ResponsableServiceStageDetailsImpl responsableServiceStageDetails = (ResponsableServiceStageDetailsImpl) authentication
					.getPrincipal();
			// System.out.println("ResponsableServiceStage --------------> 0. Second Login
			// 4: " + responsableServiceStageDetails.getPassword());
			pRSS = new UserResponse(jwt, responsableServiceStageDetails.getId(),
					responsableServiceStageDetails.getPassword());
			// System.out.println("ResponsableServiceStage --------------> 0. Second Login 5
			// ");
		}
		return ResponseEntity.ok(pRSS);
	}


	@GetMapping("/validateJWT/{jwt}")
	public String validateJWT(@PathVariable String jwt)
	{
		System.out.println("TOKEN --------------> 1: " + jwt);
		String result = "NO";
		if (jwt!=null && jwtUtils.validateJwtToken(jwt)) {
			// String id = jwtUtils.getUserNameFromJwtToken(jwt);
			System.out.println("TOKEN --------------> 2 OK");
			result = "YES";
		}
		System.out.println("TOKEN --------------> 3: " + result);
		return result;
	}

	@GetMapping("/pwdESPTea/{mailEns}/{idEns}")
	public String findDecodedIdEns(@PathVariable String mailEns, @PathVariable String idEns)
	{
		String pwd = "NOTYET";
		System.out.println(")))))))))))))))))) 1 : " + mailEns);

		String decMailEns = utilServices.decodeEncodedValue(mailEns);
		System.out.println(")))))))))))))))))) 2 : " + decMailEns);

		String decIdEns = utilServices.decodeEncodedValue(idEns);
		System.out.println(")))))))))))))))))) 2 : " + decIdEns);

		if(teacherRepository.findTeacherJWTPWDByMailAndId(decMailEns, decIdEns) != null)
		{
			String fullPwd = teacherRepository.findTeacherJWTPWDByMailAndId(decMailEns, decIdEns);
			System.out.println(")))))))))))))))))) 3 : " + idEns);
			pwd = fullPwd.substring(0, fullPwd.lastIndexOf("$$$$$"));
			System.out.println(")))))))))))))))))) 4 : " + idEns);
		}

		return pwd;
	}

	@PostMapping("/simpleSigninTeacher")
	public ResponseEntity<?> simpleSigninTeacher(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("Teacher --------------> Sign-In with: " + loginRequest.getId() + " -- "
				+ loginRequest.getId() + " - " + loginRequest.getPassword());

		Teacher teacher = teacherRepository.findTeacherByMailEns(loginRequest.getId().toLowerCase().toLowerCase()).orElseThrow(() -> new RuntimeException("Teacher : Verify your credentials !."));
		TeacherResponse pteacher = new TeacherResponse();

		System.out.println("Teacher -----***********---------> Sign-In with: " + teacher.getNomEns());

		// List<String> teachersCJ = studentRepository.findCJStudents();
		// List<String> teachersCS = studentRepository.findCSStudents();
		//
		// UserResponse pstudent = new UserResponse();
		//
		// if(studentsCJ.contains(loginRequest.getId()))
		// {

		if (teacher != null && teacher.getPwdJWTEnseignant() == null) {
			System.out.println("Teacher --------------> 0. First Login: " + pteacher.getId());
			Teacher teacherfa = teacherRepository.firstAuthenticationTeacher(loginRequest.getId().toLowerCase(), loginRequest.getPassword())
					.orElseThrow(() -> new RuntimeException("ERROR ! : Verify your credentials."));
			if (teacherfa != null && teacherfa.getPwdJWTEnseignant() == null) {
				pteacher = new TeacherResponse(loginRequest.getId(), loginRequest.getPassword(), teacherfa.getIdEns());
				System.out.println("Teacher --------------> 1. First Login: " + pteacher.getId());
			}
		}

		if (teacher != null && teacher.getPwdJWTEnseignant() != null) {
			System.out.println("Teacher --------------> 0. Second Login 0.1: " + loginRequest.getId() + " - "
					+ loginRequest.getPassword());
			Integer nbrEncByTeacher = utilServices.findNbrEncadrementByAE(teacher.getIdEns());
			// PedagogicalCoordinator rs =
			// pedagogicalCoordinatorRepository.findResponsableStage(teacher.getIdEns());

			List<PedagogicalCoordinator> pedagogicalCoordinators = pedagogicalCoordinatorRepository
					.findPedagogicalCoordinator(teacher.getIdEns());

			List<String> ListOptionsByPC = new ArrayList<String>();
			for (PedagogicalCoordinator pc : pedagogicalCoordinators) {
				ListOptionsByPC.add(pc.getOption().getId().getCodeOption());
			}

			// System.out.println("Teacher --------------------------------> 0. Second Login
			// 0.2: " + rs.getOption().getId().getCodeOption() + " - " + nbrEncByTeacher);
			// System.out.println("Teacher ------------------------------------------------
			// result -----> 0. Second Login 0.2: " + rs);

			System.out.println("Teacher -----lol------------------------- result -----> 0. Second Login 0.1: "
					+ pedagogicalCoordinators.size() + " - " + nbrEncByTeacher);

			if (pedagogicalCoordinators.isEmpty() && nbrEncByTeacher == 0) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

				System.out.println("Teacher --------------> 0. Second Login 0  _  -PC -ENC");

				SecurityContextHolder.getContext().setAuthentication(authentication);
				// System.out.println("Teacher --------------> 0. Second Login 2 ");
				String jwt = jwtUtils.generateTeacherJwtToken(authentication);
				// System.out.println("Teacher --------------> 0. Second Login 3 ");
				TeacherDetailsImpl teacherDetails = (TeacherDetailsImpl) authentication.getPrincipal();
				// System.out.println("Teacher --------------> 0. Second Login 4: " +
				// teacherDetails.getPassword());
				pteacher = new TeacherResponse(jwt, loginRequest.getId(), teacherDetails.getPassword(),
						teacher.getIdEns(), null, nbrEncByTeacher, null);
				// System.out.println("Teacher --------------> 0. Second Login 5 ");
			}
			if (pedagogicalCoordinators.isEmpty() && nbrEncByTeacher != 0) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

				System.out.println("Teacher --------------> 0. Second Login 1  _  -PC +ENC");

				SecurityContextHolder.getContext().setAuthentication(authentication);
				// System.out.println("Teacher --------------> 0. Second Login 2 ");
				String jwt = jwtUtils.generateTeacherJwtToken(authentication);
				// System.out.println("Teacher --------------> 0. Second Login 3 ");
				TeacherDetailsImpl teacherDetails = (TeacherDetailsImpl) authentication.getPrincipal();
				// System.out.println("Teacher --------------> 0. Second Login 4: " +
				// teacherDetails.getPassword());
				pteacher = new TeacherResponse(jwt, loginRequest.getId(), teacherDetails.getPassword(),
						teacher.getIdEns(), null, nbrEncByTeacher, null);
				// System.out.println("Teacher --------------> 0. Second Login 5 ");
			}
			if (!pedagogicalCoordinators.isEmpty() && nbrEncByTeacher != 0) {
				System.out.println("Teacher --------------> 0. Second Login 2  _  +PC +ENC");

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

				System.out.println("Teacher --------------> 0. Second Login 1 ");

				SecurityContextHolder.getContext().setAuthentication(authentication);
				// System.out.println("Teacher --------------> 0. Second Login 2 ");
				String jwt = jwtUtils.generateTeacherJwtToken(authentication);
				// System.out.println("Teacher --------------> 0. Second Login 3 ");
				TeacherDetailsImpl teacherDetails = (TeacherDetailsImpl) authentication.getPrincipal();
				// System.out.println("Teacher --------------> 0. Second Login 4: " +
				// teacherDetails.getPassword());
				pteacher = new TeacherResponse(jwt, loginRequest.getId(), teacherDetails.getPassword(),
						teacher.getIdEns(), null, nbrEncByTeacher, ListOptionsByPC);
				// System.out.println("Teacher --------------> 0. Second Login 5 ");
			}
			if (!pedagogicalCoordinators.isEmpty() && nbrEncByTeacher == 0) {
				System.out.println("Teacher --------------> 0. Second Login 2  _  +PC -ENC");

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

				System.out.println("Teacher --------------> 0. Second Login 1 ");

				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("Teacher --------------> 0. Second Login 2 ");
				String jwt = jwtUtils.generateTeacherJwtToken(authentication);
				System.out.println("Teacher --------------> 0. Second Login 3 ");
				TeacherDetailsImpl teacherDetails = (TeacherDetailsImpl) authentication.getPrincipal();
				System.out.println("Teacher --------------> 0. Second Login 4: " + teacherDetails.getPassword());
				pteacher = new TeacherResponse(jwt, loginRequest.getId(), teacherDetails.getPassword(),
						teacher.getIdEns(), null, nbrEncByTeacher, ListOptionsByPC);
				System.out.println("Teacher --------------> 0. Second Login 5 ");
			}
			/*
			 * if(rs == null && nbrEncByTeacher != 0) { Authentication authentication =
			 * authenticationManager.authenticate( new
			 * UsernamePasswordAuthenticationToken(loginRequest.getId(),
			 * loginRequest.getPassword()));
			 *
			 * System.out.println("Teacher --------------> 0. Second Login 1 ");
			 *
			 * SecurityContextHolder.getContext().setAuthentication(authentication);
			 * System.out.println("Teacher --------------> 0. Second Login 2 "); String jwt
			 * = jwtUtils.generateTeacherJwtToken(authentication);
			 * System.out.println("Teacher --------------> 0. Second Login 3 ");
			 * TeacherDetailsImpl teacherDetails = (TeacherDetailsImpl)
			 * authentication.getPrincipal();
			 * System.out.println("Teacher --------------> 0. Second Login 4: " +
			 * teacherDetails.getPassword()); pteacher = new TeacherResponse(jwt,
			 * loginRequest.getId(), teacherDetails.getPassword(), teacher.getIdEns(), null,
			 * nbrEncByTeacher);
			 * System.out.println("Teacher --------------> 0. Second Login 5 "); } if(rs !=
			 * null && nbrEncByTeacher != 0) { Authentication authentication =
			 * authenticationManager.authenticate( new
			 * UsernamePasswordAuthenticationToken(loginRequest.getId(),
			 * loginRequest.getPassword()));
			 *
			 * System.out.println("Teacher --------------> 0. Second Login 1 ");
			 *
			 * SecurityContextHolder.getContext().setAuthentication(authentication);
			 * System.out.println("Teacher --------------> 0. Second Login 2 "); String jwt
			 * = jwtUtils.generateTeacherJwtToken(authentication);
			 * System.out.println("Teacher --------------> 0. Second Login 3 ");
			 * TeacherDetailsImpl teacherDetails = (TeacherDetailsImpl)
			 * authentication.getPrincipal();
			 * System.out.println("Teacher --------------> 0. Second Login 4: " +
			 * teacherDetails.getPassword()); pteacher = new TeacherResponse(jwt,
			 * loginRequest.getId(), teacherDetails.getPassword(), teacher.getIdEns(),
			 * rs.getOption().getId().getCodeOption(), nbrEncByTeacher);
			 * System.out.println("Teacher --------------> 0. Second Login 5 "); } if(rs !=
			 * null && nbrEncByTeacher == 0) { Authentication authentication =
			 * authenticationManager.authenticate( new
			 * UsernamePasswordAuthenticationToken(loginRequest.getId(),
			 * loginRequest.getPassword()));
			 *
			 * System.out.println("Teacher --------------> 0. Second Login 1 ");
			 *
			 * SecurityContextHolder.getContext().setAuthentication(authentication);
			 * System.out.println("Teacher --------------> 0. Second Login 2 "); String jwt
			 * = jwtUtils.generateTeacherJwtToken(authentication);
			 * System.out.println("Teacher --------------> 0. Second Login 3 ");
			 * TeacherDetailsImpl teacherDetails = (TeacherDetailsImpl)
			 * authentication.getPrincipal();
			 * System.out.println("Teacher --------------> 0. Second Login 4: " +
			 * teacherDetails.getPassword()); pteacher = new TeacherResponse(jwt,
			 * loginRequest.getId(), teacherDetails.getPassword(), teacher.getIdEns(),
			 * rs.getOption().getId().getCodeOption(), nbrEncByTeacher);
			 * System.out.println("Teacher --------------> 0. Second Login 5 "); }
			 */

		}
		return ResponseEntity.ok(pteacher);
	}

	@PostMapping("/simpleSigninPedagogicalCoordinator")
	public ResponseEntity<?> simpleSigninPedagogicalCoordinator(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("PedagogicalCoordinator --------------> Sign-In with: " + loginRequest.getId() + " - "
				+ loginRequest.getPassword());

		String pcPwdJwt = pedagogicalCoordinatorRepository.findPedagogicalCoordinatorJWTPWDById(loginRequest.getId());
		System.out.println("PedagogicalCoordinator --------------> pcPwdJwt: " + pcPwdJwt);

		UserResponse pRSS = new UserResponse();

		if (pcPwdJwt == null) {
			System.out.println("PedagogicalCoordinator --------------> 0. First Login: " + pRSS.getId());
			PedagogicalCoordinator pedagogicalCoordinatorfa = pedagogicalCoordinatorRepository
					.firstAuthentication(loginRequest.getId(), loginRequest.getPassword())
					.orElseThrow(() -> new RuntimeException("ERROR ! : Verify your credentials."));
			if (pedagogicalCoordinatorfa != null && pedagogicalCoordinatorfa.getPwdJWTPC() == null) {
				pRSS = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
				System.out.println("PedagogicalCoordinator --------------> 1. First Login: " + pRSS.getId());
			}
		}

		if (pcPwdJwt != null) {
			System.out.println("PedagogicalCoordinator --------------> 0. Second Login 0: " + loginRequest.getId()
					+ " - " + loginRequest.getPassword());

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

			System.out.println("PedagogicalCoordinator --------------> 0. Second Login 1 ");

			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("PedagogicalCoordinator --------------> 0. Second Login 2 ");
			String jwt = jwtUtils.generatePedagogicalCoordinatorJwtToken(authentication);
			System.out.println("PedagogicalCoordinator --------------> 0. Second Login 3 ");
			PedagogicalCoordinatorDetailsImpl pedagogicalCoordinatorDetails = (PedagogicalCoordinatorDetailsImpl) authentication
					.getPrincipal();
			System.out.println("PedagogicalCoordinator --------------> 0. Second Login 4: "
					+ pedagogicalCoordinatorDetails.getPassword());
			pRSS = new UserResponse(jwt, pedagogicalCoordinatorDetails.getId(),
					pedagogicalCoordinatorDetails.getPassword());
			// pRSS = new PedagogicalCoordinatorResponse(jwt,
			// pedagogicalCoordinatorDetails.getId(),
			// pedagogicalCoordinatorDetails.getPassword(), );
			System.out.println("PedagogicalCoordinator --------------> 0. Second Login 5 ");
		}
		return ResponseEntity.ok(pRSS);
	}

	@PostMapping("/forgotStudentPassword")
	public ResponseEntity<?> forgotStudentPassword(@Valid @RequestBody LoginRequest loginRequest) {
		// // System.out.println("Student --------------> Get Details Student: " +
		// loginRequest.getId());

		String stuPwdJwt = utilServices.findStudentJWTPWDByHisId(loginRequest.getId());
		UserResponse pstudent = new UserResponse();

		if (stuPwdJwt != null) {
			// // System.out.println("Student --------------> 0. Forgot Password: " +
			// pstudent.getId());
			pstudent = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
			// // System.out.println("Student --------------> 1. Forgot Password: " +
			// pstudent.getId());
		}

		return ResponseEntity.ok(pstudent);
	}

	@PostMapping("/forgotResponsableServiceStagePassword")
	public ResponseEntity<?> forgotResponsableServiceStagePassword(@Valid @RequestBody LoginRequest loginRequest) {
		// System.out.println("ResponsableServiceStage --------------> Get Details
		// ResponsableServiceStage: " + loginRequest.getId());

		String rSSPwdJwt = responsableServiceStageRepository
				.findResponsableServiceStageJWTPWDById(loginRequest.getId());
		UserResponse pResponsableServiceStage = new UserResponse();

		if (rSSPwdJwt != null) {
			// System.out.println("ResponsableServiceStage --------------> 0. Forgot
			// Password: " + pResponsableServiceStage.getId());

			pResponsableServiceStage = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
			// System.out.println("ResponsableServiceStage --------------> 1. Forgot
			// Password: " + pResponsableServiceStage.getId());
		}

		return ResponseEntity.ok(pResponsableServiceStage);
	}

	@PostMapping("/forgotTeacherPassword")
	public ResponseEntity<?> forgotTeacherPassword(@Valid @RequestBody LoginRequest loginRequest) {
		// // System.out.println("Teacher --------------> Get Details Student: " +
		// loginRequest.getId());

		Teacher teacher = teacherRepository.firstAuthenticationTeacher(loginRequest.getId().toLowerCase(), loginRequest.getPassword())
				.orElseThrow(() -> new RuntimeException("Teacher : Verify your credentials !."));
		TeacherResponse pteacher = new TeacherResponse();

		if (teacher != null && teacher.getPwdJWTEnseignant() != null) {
			// // System.out.println("Teacher --------------> 0. Forgot Password: " +
			// pteacher.getId());

			pteacher = new TeacherResponse(loginRequest.getId(), loginRequest.getPassword(), teacher.getIdEns());
			// // System.out.println("Teacher --------------> 1. Forgot Password: " +
			// pteacher.getId());
		}

		return ResponseEntity.ok(pteacher);
	}

	@PostMapping("/forgotPedagogicalCoordinatorPassword")
	public ResponseEntity<?> forgotPedagogicalCoordinatorPassword(@Valid @RequestBody LoginRequest loginRequest) {
		// System.out.println("ResponsableServiceStage --------------> Get Details
		// ResponsableServiceStage: " + loginRequest.getId());

		String rSSPwdJwt = pedagogicalCoordinatorRepository.findPedagogicalCoordinatorJWTPWDById(loginRequest.getId());
		UserResponse pPedagogicalCoordinator = new UserResponse();

		if (rSSPwdJwt != null) {
			// System.out.println("ResponsableServiceStage --------------> 0. Forgot
			// Password: " + pResponsableServiceStage.getId());

			pPedagogicalCoordinator = new UserResponse(loginRequest.getId(), loginRequest.getPassword());
			// System.out.println("ResponsableServiceStage --------------> 1. Forgot
			// Password: " + pResponsableServiceStage.getId());
		}

		return ResponseEntity.ok(pPedagogicalCoordinator);
	}

	@PostMapping("/secureStudentPassword/{idEt}/{pwdJWTEtudiant}")
	public ResponseEntity<?> secureStudentPassword(@PathVariable String idEt, @PathVariable String pwdJWTEtudiant)
			throws UnsupportedEncodingException {

		String decodedPwdJWTEtudiant = utilServices.decodeEncodedValue(pwdJWTEtudiant);

		// // System.out.println("Student --------------> Secure Password 1: " + idEt +
		// " - " + pwdJWTEtudiant);

		/***************************************************************************/
		String mpCryptoPassword = "salt";

		/* Encrypt */
		//// // System.out.println(nativePassword);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(mpCryptoPassword);
		// String encryptedPassword = encryptor.encrypt(nativePassword);
		//// // System.out.println(encryptedPassword);

		/*
		 * Decrypt StandardPBEStringEncryptor decryptor = new
		 * StandardPBEStringEncryptor(); decryptor.setPassword(mpCryptoPassword); // //
		 * System.out.println(decryptor.decrypt(encryptedPassword));
		 */
		/***************************************************************************/

		// Create new student's account

		List<String> activatedYears = settingsRepository.findActivatedYears();

		List<String> studentsCJALT = studentRepository.findCJALTStudents(activatedYears);
		List<String> studentsCS = studentRepository.findCSStudents(activatedYears);

		// System.out.println("Student --------------> Size: " + idEt + " --> " +
		// studentsCJ.size() + " - " + studentsCS.size());

		if (studentsCJALT.contains(idEt)) {
			// System.out.println("Student --------------> Student CJ");
			StudentCJ student = studentRepository.findByIdEt(idEt)
					.orElseThrow(() -> new RuntimeException("Verify your credentials !."));
			student.setPwdJWTEtudiant(
					encoder.encode(decodedPwdJWTEtudiant) + "$$$$$" + encryptor.encrypt(decodedPwdJWTEtudiant));
			student.setDateModifyJwtPwd(new Date());

			studentRepository.save(student);
		}

		if (studentsCS.contains(idEt)) {
			// System.out.println("Student --------------> Student CS");
			StudentCS studentcs = studentCSRepository.findByIdEt(idEt)
					.orElseThrow(() -> new RuntimeException("Verify your credentials !."));
			studentcs.setPwdJWTEtudiant(encoder.encode(pwdJWTEtudiant) + "$$$$$" + encryptor.encrypt(pwdJWTEtudiant));
			studentcs.setDateModifyJwtPwd(new Date());

			studentCSRepository.save(studentcs);
		}

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateChangeMdp = dateFormata.format(new Date());

		String subject = "Changement Mot de Passe";

		// String receiver = "saria.essid@esprit.tn";
		String receiver = utilServices.findStudentMailById(idEt); // DEPLOY_SERVER

		String content = "Nous voulons vous informer par le présent mail que vous avez changé "
				+ "votre <strong><font color=grey> Mot de Passe </font></strong> et c'est " + "le <font color=red> "
				+ dateChangeMdp + " </font>." + "<br/><br/>" + "<font color=grey fontStyle=italic>"
				+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
				+ "<br/>" + "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";

		utilServices.sendMail(subject, receiver, content);
		/********************************************************************************************************/

		// // System.out.println("Student --------------> Secure Password 2");

		return ResponseEntity.ok(new MessageResponse("Student : Student registered successfully!"));
	}

	@PostMapping("/secureResponsableServiceStagePassword/{idUserSce}/{pwdJWTResponsableServiceStage}")
	public ResponseEntity<?> secureResponsableServiceStagePassword(@PathVariable String idUserSce,
																   @PathVariable String pwdJWTResponsableServiceStage) throws UnsupportedEncodingException {

		// System.out.println("ResponsableServiceStage --------------> Secure Password
		// 1: " + idUserSce + " - " + pwdJWTResponsableServiceStage);

		String decodedPwdJWTResponsableServiceStage = utilServices.decodeEncodedValue(pwdJWTResponsableServiceStage);

		/***************************************************************************/
		String mpCryptoPassword = "salt";

		/* Encrypt */
		//// // System.out.println(nativePassword);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(mpCryptoPassword);
		// String encryptedPassword = encryptor.encrypt(nativePassword);
		//// // System.out.println(encryptedPassword);

		/*
		 * Decrypt StandardPBEStringEncryptor decryptor = new
		 * StandardPBEStringEncryptor(); decryptor.setPassword(mpCryptoPassword); // //
		 * System.out.println(decryptor.decrypt(encryptedPassword));
		 */
		/***************************************************************************/

		// Create new student's account
		ResponsableServiceStage responsableServiceStage = responsableServiceStageRepository.findByIdUserSce(idUserSce)
				.orElseThrow(() -> new RuntimeException("Verify your credentials !."));

		responsableServiceStage.setPwdJWTRSS(encoder.encode(decodedPwdJWTResponsableServiceStage) + "$$$$$"
				+ encryptor.encrypt(decodedPwdJWTResponsableServiceStage));
		responsableServiceStage.setDateModifyJwtPwd(new Date());

		responsableServiceStageRepository.save(responsableServiceStage);

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateChangeMdp = dateFormata.format(new Date());

		String subject = "Changement Mot de Passe";

		// String receiver = "saria.essid@esprit.tn";
		String receiver = responsableServiceStageRepository.findRespServStgMailById(idUserSce); // DEPLOY_SERVER

		String content = "Nous voulons vous informer par le présent mail que vous avez changé "
				+ "votre <strong><font color=grey> Mot de Passe </font></strong> et c'est " + "le <font color=red> "
				+ dateChangeMdp + " </font>." + "<br/><br/>" + "<font color=grey fontStyle=italic>"
				+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
				+ "<br/>" + "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";

		utilServices.sendMail(subject, receiver, content);
		/********************************************************************************************************/

		// System.out.println("ResponsableServiceStage --------------> Secure Password
		// 2");

		return ResponseEntity
				.ok(new MessageResponse("ResponsableServiceStage : ResponsableServiceStage registered successfully!"));
	}

	@PostMapping("/secureTeacherPassword/{mailEns}/{pwdJWTEnseignantEncoded}")
	public ResponseEntity<?> secureTeacherPassword(@PathVariable String mailEns,
												   @PathVariable String pwdJWTEnseignantEncoded) throws UnsupportedEncodingException {

		String pwdJWTEnseignant = URLDecoder.decode(pwdJWTEnseignantEncoded, StandardCharsets.UTF_8.toString());

		System.out.println("Teacher --------------> Secure Password 1: " + mailEns + " | " + pwdJWTEnseignantEncoded
				+ " -> " + pwdJWTEnseignant);

		/***************************************************************************/
		String mpCryptoPassword = "salt";

		/* Encrypt */
		//// // System.out.println(nativePassword);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(mpCryptoPassword);
		// String encryptedPassword = encryptor.encrypt(nativePassword);
		//// // System.out.println(encryptedPassword);

		/*
		 * Decrypt StandardPBEStringEncryptor decryptor = new
		 * StandardPBEStringEncryptor(); decryptor.setPassword(mpCryptoPassword); // //
		 * System.out.println(decryptor.decrypt(encryptedPassword));
		 */
		/***************************************************************************/

		// Create new student's account
		Teacher teacher = teacherRepository.findTeacherByMailEns(mailEns).orElseThrow(() -> new RuntimeException("Teacher : Verify your credentials !."));

		teacher.setPwdJWTEnseignant(encoder.encode(pwdJWTEnseignant) + "$$$$$" + encryptor.encrypt(pwdJWTEnseignant));
		teacher.setDateModifyJwtPwd(new Date());

		teacherRepository.save(teacher);

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateChangeMdp = dateFormata.format(new Date());

		String subject = "Changement Mot de Passe";

		String content = "Nous voulons vous informer par le présent mail que vous avez changé "
				+ "votre <strong><font color=grey> Mot de Passe </font></strong> et c'est " + "le <font color=red> "
				+ dateChangeMdp + " </font>." + "<br/><br/>" + "<font color=grey fontStyle=italic>"
				+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
				+ "<br/>" + "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";

		utilServices.sendMail(subject, mailEns, content);
		/********************************************************************************************************/

		// // System.out.println("Teacher --------------> Secure Password 2");

		return ResponseEntity.ok(new MessageResponse("Teacher : Responsible registered successfully !."));
	}

	@PostMapping("/securePedagogicalCoordinatorPassword/{idUserSce}/{pwdJWTPedagogicalCoordinator}")
	public ResponseEntity<?> securePedagogicalCoordinatorPassword(@PathVariable String idUserSce,
																  @PathVariable String pwdJWTPedagogicalCoordinator) throws UnsupportedEncodingException {

		// System.out.println("ResponsableServiceStage --------------> Secure Password
		// 1: " + idUserSce + " - " + pwdJWTResponsableServiceStage);

		String decodedPwdJWTPedagogicalCoordinator = utilServices.decodeEncodedValue(pwdJWTPedagogicalCoordinator);

		/***************************************************************************/
		String mpCryptoPassword = "salt";

		/* Encrypt */
		//// // System.out.println(nativePassword);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(mpCryptoPassword);
		// String encryptedPassword = encryptor.encrypt(nativePassword);
		//// // System.out.println(encryptedPassword);

		/*
		 * Decrypt StandardPBEStringEncryptor decryptor = new
		 * StandardPBEStringEncryptor(); decryptor.setPassword(mpCryptoPassword); // //
		 * System.out.println(decryptor.decrypt(encryptedPassword));
		 */
		/***************************************************************************/

		// Create new student's account
		PedagogicalCoordinator pedagogicalCoordinator = pedagogicalCoordinatorRepository
				.findPedagogicalCoordinatorByLogin(idUserSce)
				.orElseThrow(() -> new RuntimeException("Verify your credentials !."));

		pedagogicalCoordinator.setPwdJWTPC(encoder.encode(decodedPwdJWTPedagogicalCoordinator) + "$$$$$"
				+ encryptor.encrypt(decodedPwdJWTPedagogicalCoordinator));
		pedagogicalCoordinator.setDateModifyJwtPwd(new Date());

		pedagogicalCoordinatorRepository.save(pedagogicalCoordinator);

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateChangeMdp = dateFormata.format(new Date());

		String subject = "Changement Mot de Passe";

		String content = "Nous voulons vous informer par le présent mail que vous avez changé "
				+ "votre <strong><font color=grey> Mot de Passe </font></strong> et c'est " + "le <font color=red> "
				+ dateChangeMdp + " </font>." + "<br/><br/>" + "<font color=grey fontStyle=italic>"
				+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
				+ "<br/>" + "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";

		utilServices.sendMail(subject, idUserSce, content);
		/********************************************************************************************************/

		// System.out.println("ResponsableServiceStage --------------> Secure Password
		// 2");

		return ResponseEntity
				.ok(new MessageResponse("PedagogicalCoordinator : PedagogicalCoordinator registered successfully!"));
	}


	/************************************************** Reset Password ***********************************/
	@PostMapping("/applyToInitializeMyOwnPassword")
	public String applyToInitializeMyOwnPassword(@Valid @RequestBody ForgotPwdWithEmailRequest loginRequest)
	{
		String response = utilServices.forgotPassword(loginRequest.getEmail());
		System.out.println("----------------PIKA1811-------> AZERTY 2: " + response);

		String result = "INIT";
		if (!response.startsWith("Invalid"))
		{
			System.out.println("-;--------------PIKA1811--------> AZERTY 3: " + response);

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDemandeInitPwd = dateFormata.format(new Date());

			String content = "Nous voulons vous informer par le présent mail que Vous pouvez changer votre Mot de passe en utilisant le lien  "
					+ "<font color=blue> <a href=\"https://pfe.esprit.tn/renewMyTeacherPassword\">initialiser mon Mot de Passe</a> </font>.<br/>"
					+ "Votre<font color=green>Token</font> est : <font color=green>" + response + "</font><br/>"
					+ "Notez bien : ce Token <font color=red>n'est plus valable</font> que pendant 30 minutes dès la présente date <font color=grey>" + dateDemandeInitPwd + "</font>";
			utilServices.sendMail("Récupération Mot de Passe pour Espace Enseignant", loginRequest.getEmail().toLowerCase().trim(), content);


			result = "ALLOW";
		}
		else
		{
			result = "DENY";
		}

		System.out.println("-;--------------PIKA1811--------> result 3: " + result);
		return result;
	}

	@GetMapping("/resetMyOwnPassword/{token}/{password}")
	public String resetMyOwnPassword(@PathVariable String token, @PathVariable String password) throws UnsupportedEncodingException
	{
		System.out.println("---------***--------------> encoded password 1: " + password);
		System.out.println("---------***--------------> encoded token: " + token);
		String decodedPwd = utilServices.decodeEncodedValue(password);
		String decodedTkn = utilServices.decodeEncodedValue(token);
		System.out.println("---------***--------------> decoded password 2: " + decodedPwd);
		System.out.println("---------***--------------> decoded token: " + decodedTkn);
		return utilServices.resetPassword(decodedTkn, decodedPwd);
	}


	/************************************************** Reset Password Etudiant ***********************************/
	@PostMapping("/applyToInitializeMyStudentPassword")
	public String applyToInitializeMyStudentPassword(@Valid @RequestBody ForgotPwdWithEmailRequest loginRequest)
	{
		System.out.println("----------HI-------> AZERTY 1");
		String response = utilServices.forgotStudentPassword(loginRequest.getEmail());
		System.out.println("----------HI-------> AZERTY 2: " + response);

		String result = "INIT";
		if (!response.startsWith("Invalid") && !response.equalsIgnoreCase("NOTEXIST"))
		{
			System.out.println("-;--------------HI--------> AZERTY 3: " + response);

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDemandeInitPwd = dateFormata.format(new Date());

			String content = "Nous voulons vous informer par le présent mail que Vous pouvez changer votre Mot de passe en utilisant le lien  "
					+ "<font color=blue> <a href=\"https://pfe.esprit.tn/renewMyStudentPassword\">initialiser mon Mot de Passe</a> </font>.<br/>"
					+ "Votre<font color=green>Token</font> est : <font color=green>" + response + "</font><br/>"
					+ "Notez bien : ce Token <font color=red>n'est plus valable</font> que pendant 30 minutes dès la présente date <font color=grey>" + dateDemandeInitPwd + "</font>";
			utilServices.sendMail("Récupération Mot de Passe", utilServices.findStudentMailById(loginRequest.getEmail()).toLowerCase().trim(), content);

			result = "ALLOW";
		}
		else
		{
			result = "DENY";
		}

		System.out.println("-;--------------PIKA1811--------> result 3: " + result);
		return result;
	}

	@GetMapping("/resetMyStudentPassword/{token}/{password}")
	public String resetMyStudentPassword(@PathVariable String token, @PathVariable String password) throws UnsupportedEncodingException
	{

		System.out.println("---------STU--------------> encoded password 1: " + password);
		System.out.println("---------STU--------------> encoded token: " + token);
		String decodedPwd = utilServices.decodeEncodedValue(password);
		String decodedTkn = utilServices.decodeEncodedValue(token);
		System.out.println("---------STU--------------> decoded password 2: " + decodedPwd);
		System.out.println("---------STU--------------> decoded token: " + decodedTkn);
		return utilServices.resetStudentPassword(decodedTkn, decodedPwd);
	}


	/************************************************** Reset Password CD/CPS ***********************************/
	@PostMapping("/applyToInitializeMyCDCPSPassword")
	public String applyToInitializeMyCDCPSPassword(@Valid @RequestBody ForgotPwdWithEmailRequest loginRequest)
	{
		System.out.println("----------HI-------> AZERTY 1");
		String response = utilServices.forgotCDCPSPassword(loginRequest.getEmail());
		System.out.println("----------HI-------> AZERTY 2: " + response);

		String result = "INIT";
		if (!response.startsWith("Invalid") && !response.equalsIgnoreCase("NOTEXIST"))
		{
			System.out.println("-;--------------HI--------> AZERTY 3: " + response);

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDemandeInitPwd = dateFormata.format(new Date());

			String content = "Nous voulons vous informer par le présent mail que Vous pouvez changer votre Mot de passe en utilisant le lien  "
					+ "<font color=blue> <a href=\"https://pfe.esprit.tn/renewMyStudentPassword\">initialiser mon Mot de Passe</a> </font>.<br/>"
					+ "Votre<font color=green>Token</font> est : <font color=green>" + response + "</font><br/>"
					+ "Notez bien : ce Token <font color=red>n'est plus valable</font> que pendant 30 minutes dès la présente date <font color=grey>" + dateDemandeInitPwd + "</font>";
			utilServices.sendMail("Récupération Mot de Passe", loginRequest.getEmail().toLowerCase().trim(), content);

			result = "ALLOW";
		}
		else
		{
			result = "DENY";
		}

		System.out.println("-;--------------PIKA1811--------> result 3: " + result);
		return result;
	}

	@GetMapping("/resetMyCDCPSPassword/{token}/{password}")
	public String resetMyCDCPSPassword(@PathVariable String token, @PathVariable String password) throws UnsupportedEncodingException
	{

		System.out.println("---------STU--------------> encoded password 1: " + password);
		System.out.println("---------STU--------------> encoded token: " + token);
		String decodedPwd = utilServices.decodeEncodedValue(password);
		String decodedTkn = utilServices.decodeEncodedValue(token);
		System.out.println("---------STU--------------> decoded password 2: " + decodedPwd);
		System.out.println("---------STU--------------> decoded token: " + decodedTkn);
		return utilServices.resetCDCPSPassword(decodedTkn, decodedPwd);
	}


	@GetMapping("/signedConvention/{currentUserCode}")
	public ResponseEntity<List<String>> getSignedConvention(@PathVariable String currentUserCode)
	{

		String mpCryptoPassword = "SALT";
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(mpCryptoPassword);

		Integer index = 1;
		List<DepotSignedConvDto> files = storageService.getSignedConv(currentUserCode).map(report -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/signedConvention/").path(index.toString()).toUriString();

			return report;
		}).collect(Collectors.toList());
		List<String> lss = new ArrayList<>();
		for(DepotSignedConvDto dbd : files)
		{
			String signedConv = null;

			String pathSignedConv = dbd.getPathSignedConv();
			String nameSignedConv = null;
			String dateSignedConv = dbd.getDateDepotSignedConv();

			if(dbd.getPathSignedConv() != null)
			{
				nameSignedConv = pathSignedConv.substring(pathSignedConv.indexOf("uploads")+8, pathSignedConv.indexOf("espdsi2020"));
				signedConv = nameSignedConv + "UNITR1" + dateSignedConv;
				String encodedConv = decryptor.encrypt(signedConv);

				lss.add(encodedConv);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(lss);
	}

	@PostMapping("/upload/signedConv/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadSignedConv(@PathVariable String currentUserCode, @RequestParam("file") MultipartFile file)
	{

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try
		{
			// // System.out.println("-----------------------------1.1");
			storageService.storeSignedConvention(file, currentUserCode);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Convention Signée " + file.getOriginalFilename() + " a été bien déposé.";
			// // System.out.println("-----------------------------1.3");


			/***************************************** Notification By Mail *****************************************/
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDepot = dateFormata.format(new Date());

			String subject = "Dépôt Convention Signée";

			// String studentMail = utilServices.findStudentMailById(currentUserCode);   DEPLOY_SERVER
			// String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode); DEPLOY_SERVER

			String studentMail = utilServices.findStudentMailById(currentUserCode);//.substring(0, utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) + "@mail.tn";
			String mailRSS = responsableServiceStageRepository.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(currentUserCode).get(0));

			String content = "Nous voulons vous informer par le présent mail que vous avez déposé"
					+ " votre <strong><font color=grey> Convention Signée </font></strong> "
					+ "le <font color=red> " + dateDepot + " </font>.";

			utilServices.sendMailWithCC(subject, studentMail, mailRSS, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		}
		catch (Exception e)
		{
			message = "Erreur de téléchargement de votre Convention Signée " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}


	@GetMapping("/signedAvenant/{currentUserCode}")
	public ResponseEntity<List<String>> getSignedAvenant(@PathVariable String currentUserCode)
	{
		Integer index = 1;
		List<DepotSignedAvenDto> files = storageService.getSignedAven(currentUserCode).map(report -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/signedAvenant/").path(index.toString()).toUriString();

			return report;
		}).collect(Collectors.toList());
		List<String> lss = new ArrayList<>();
		for(DepotSignedAvenDto dbd : files)
		{
			String signedConv = null;

			String pathSignedAven = dbd.getPathSignedAven();
			String nameSignedAven = null;
			String dateSignedAven = dbd.getDateDepotSignedAven();

			if(dbd.getPathSignedAven() != null)
			{
				nameSignedAven = pathSignedAven.substring(pathSignedAven.indexOf("uploads")+8, pathSignedAven.indexOf("espdsi2020"));
				signedConv = nameSignedAven + "UNITR1" + dateSignedAven;
				lss.add(signedConv);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(lss);
	}

	@PostMapping("/upload/signedAven/{currentUserCode}")
	public ResponseEntity<MessageResponse> uploadSignedAven(@PathVariable String currentUserCode, @RequestParam("file") MultipartFile file)
	{

		// // System.out.println("-----------------------------A: " + currentUserCode);
		String message = "";
		try
		{
			// // System.out.println("-----------------------------1.1");
			storageService.storeSignedAvenant(file, currentUserCode);
			// // System.out.println("-----------------------------1.2");
			message = "Votre Avenant Signé " + file.getOriginalFilename() + " a été bien déposé.";
			// // System.out.println("-----------------------------1.3");

			/***************************************** Notification By Mail *****************************************/
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateDepot = dateFormata.format(new Date());

			String subject = "Dépôt Avenant Signé";

			// String studentMail = utilServices.findStudentMailById(currentUserCode);   DEPLOY_SERVER
			// String academicEncMail = utilServices.findMailPedagogicalEncadrant(currentUserCode); DEPLOY_SERVER

			String studentMail = utilServices.findStudentMailById(currentUserCode).substring(0, utilServices.findStudentMailById(currentUserCode).lastIndexOf("@")) + "@mail.tn";
			String mailRSS = responsableServiceStageRepository.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(currentUserCode).get(0));

			String content = "Nous voulons vous informer par le présent mail que vous avez déposé"
					+ " votre <strong><font color=grey> Avenant Signé </font></strong> "
					+ "le <font color=red> " + dateDepot + " </font>.";

			utilServices.sendMailWithCC(subject, studentMail, mailRSS, content);
			/********************************************************************************************************/

			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		}
		catch (Exception e)
		{
			message = "Erreur de téléchargement de votre Avenant Signé " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}


}
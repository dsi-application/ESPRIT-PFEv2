package com.esprit.gdp.controllers;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.gdp.dto.ConventionForRSSDto;
import com.esprit.gdp.dto.ConventionsValidatedForRSSDto;
import com.esprit.gdp.dto.DepotRapport;
import com.esprit.gdp.dto.StudentConvFRDto;
import com.esprit.gdp.dto.StudentForSoutenance;
import com.esprit.gdp.dto.StudentIdFullNameDto;
import com.esprit.gdp.dto.StudentIdNomPrenomDto;
import com.esprit.gdp.dto.StudentPFEDepasse;
import com.esprit.gdp.files.ConventionEN_PDF;
import com.esprit.gdp.files.ConventionFR_PDF;
import com.esprit.gdp.files.ConventionTN_PDF;
import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.TraitementFichePFE;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.GrilleAcademicEncadrantRepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.OptionStudentALTRepository;
import com.esprit.gdp.repository.PedagogicalCoordinatorRepository;
import com.esprit.gdp.repository.ResponsableServiceStageRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.repository.TraitementFicheRepository;
import com.esprit.gdp.services.ServiceStageService;
import com.esprit.gdp.services.UtilServices;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/serviceStage")
public class ServiceStageController {

	@Autowired
	ServiceStageService serviceStageService;

	@Autowired
	StudentRepository etudiantRepository;

	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	TraitementFicheRepository traitementFicheRepository;

	@Autowired
	private UtilServices utilServices;

	@Autowired
	InscriptionRepository inscriptionRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	ResponsableServiceStageRepository responsableServiceStageRepository;

	@Autowired
	ConventionRepository conventionRepository;

	@Autowired
	PedagogicalCoordinatorRepository pedagogicalCoordinatorRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	OptionRepository optionRepository;

	@Autowired
	private CodeNomenclatureRepository codeNomenclatureRepository;
	
	@Autowired
	OptionStudentALTRepository optionStudentALTRepository;
	
	@Autowired
	private GrilleAcademicEncadrantRepository grilleAcademicEncadrantRepository;
	
	
	@GetMapping("/StudentPFEDepasseList")
	public ResponseEntity<?> getStudentPFEDepasseList(@RequestParam("id") String id,
			@RequestParam("nbrMois") int nbrMois) {
		System.out.println("nbrMois" + nbrMois);
		System.out.println("id" + id);
		try {
			List<StudentPFEDepasse> StudentPFEDepasseList = serviceStageService.getEtudiantPFEDepasse(id, nbrMois);
			if (StudentPFEDepasseList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(StudentPFEDepasseList, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/EtudiantbySession")
	public ResponseEntity<?> getEtudiantbySessionList(@RequestParam("id") String id,
			@RequestParam("sessionid") int sessionid) {
		try {
			List<StudentForSoutenance> StudentPFEDepasseList = serviceStageService.getEtudiantbySession(id, sessionid);

			if (StudentPFEDepasseList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(StudentPFEDepasseList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/conventionList")
	public ResponseEntity<List<Convention>> getconventionList(@RequestParam("id") String id) {
		try {
			List<Convention> ConventionList = serviceStageService.getConventions(id);
			if (ConventionList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(ConventionList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Got All Conventions : show Top to GotConvs By Service Stage PERSONLIZED
	@GetMapping("/allConventionListForRSS")
	public ResponseEntity<List<ConventionForRSSDto>> getconventionListDto() {
		try {
			List<ConventionForRSSDto> ConventionList = serviceStageService.getAllConventionsDtoForRSS();
			if (ConventionList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(ConventionList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Got All Conventions By RSS : show Top to GotConvs By Service Stage
	// PERSONLIZED
	@GetMapping("/allConventionListByRSS")
	public ResponseEntity<List<ConventionForRSSDto>> getconventionListDto(@RequestParam("idRSS") String idRSS) {
		try {
			List<ConventionForRSSDto> ConventionList = serviceStageService.getAllConventionsDtoByRSS(idRSS);
			if (ConventionList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(ConventionList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Got All Conventions : show Top to GotConvs By Service Stage
	@GetMapping("/allConventionList")
	public ResponseEntity<List<Convention>> getconventionList() {
		try {
			List<Convention> ConventionList = serviceStageService.getAllConventions();
			if (ConventionList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(ConventionList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Got All Demandes Annulation Convention
	@GetMapping("/allDemandesAnnulationConventionList")
	public ResponseEntity<List<Convention>> getDemandesAnnulationConventionList() {
		try {
			List<Convention> ConventionList = serviceStageService.getAllDemandesAnnulationConvention();
			if (ConventionList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(ConventionList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateConventionStateFD", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ConventionForRSSDto> updateConventionState(@RequestParam("idET") String idEt,
			@RequestParam("date") String dateOFF) throws Exception {

		System.out.println("------THIS-1---------- idEt: " + idEt);
		String date = dateOFF.trim();
		System.out.println("------THIS-2.1---------- date$" + dateOFF + "$");
		System.out.println("------THIS-2.2---------- date$" + date + "$");

		// System.out.println("------THIS-1---------- idEt: " + idEt);
		// System.out.println("------THIS-2---------- date: " + date);

		// String convDt = date;
		// String convDt = date.substring(0, 19).replace("T", " ");

		if (conventionRepository.getConventionByIdFormedDate(idEt, date).isPresent()) {
			System.out.println("------THIS-3---------- date: " + date);
			Optional<Convention> conv = conventionRepository.getConventionByIdFormedDate(idEt, date);

			Date dat = new Date();
			// String pattern = "dd-MM-yyyy";
			// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			// String datenow = simpleDateFormat.format(new Date());

			String studentFullName = utilServices.findStudentFullNameById(idEt);
			String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
			String studentOption = utilServices.findOptionByClass(studentClasse,
					optionRepository.listOptionsByYear("2021"));
			String studentDepartment = utilServices.findDepartmentByClassForConventionDiplome(studentClasse);

			String path = "C:/ESP-DOCS/Conventions/" + studentFullName + "-" + dat.getTime() + ".pdf";

			String convCodePays = "--";
			if (conv.get().getEntrepriseAccueilConvention().getPays() != null) {
				convCodePays = conv.get().getEntrepriseAccueilConvention().getPays().getLangueCode();
			}

			if (convCodePays.equalsIgnoreCase("TN")) {
				ConventionTN_PDF convTN = new ConventionTN_PDF(conv, path, studentFullName, studentOption,
						studentDepartment);
				System.out.println("----------------- TN");

				/**********************************************
				 * Notify Mail
				 *******************************************************/
				System.out.println("----------------- Current Convention: " + convCodePays);
				conv.get().setPathConvention(path);
				conventionRepository.save(conv.get());

				// Send Notification by Mail
				DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String convValidationDate = dateFormata.format(new Date());

				String content = "Nous voulons vous informer par le présent mail que votre <strong><font color=grey> "
						+ "Demande de Convention </font></strong> a été bien validée par le Responsable des Stages le <font color=blue> "
						+ convValidationDate + " </font>.<br/>";
				// + "Ci-joint votre Convention. "
				// + "Prière de la signer avant de l'envoyer à l'entreprise.";

				String conventionPath = conv.get().getPathConvention();
				String conventionLabel = idEt + "_Convention_" + idEt + ".pdf";

				String studentMail = utilServices.findStudentMailById(idEt); // Server DEPLOY_SERVER
				// String studentMail = "saria.essid@esprit.tn"; // Local

				System.out.println("---------------------------> Start");

				utilServices.sendMail("Validation de Convention", studentMail, content);
				// utilServices.sendMailWithAttachment(studentMail, "Validation de Convention",
				// content, conventionPath, conventionLabel);
			}

			if (convCodePays.equalsIgnoreCase("EN") || convCodePays.equalsIgnoreCase("--")) {
				ConventionEN_PDF convEN = new ConventionEN_PDF(conv, path, studentFullName, studentOption,
						studentDepartment);
				System.out.println("----------------- EN");

				/**********************************************
				 * Notify Mail
				 *******************************************************/
				System.out.println("----------------- Current Convention: " + convCodePays);
				conv.get().setPathConvention(path);
				conventionRepository.save(conv.get());

				// Send Notification by Mail
				DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String convValidationDate = dateFormata.format(new Date());

				String content = "Nous voulons vous informer par le présent mail que votre "
						+ "<strong><font color=grey> Demande de Convention </font></strong> "
						+ "a été bien validée par le Responsable des Stages le <font color=blue> " + convValidationDate
						+ " </font>.<br/>";
				// + "Ci-joint votre Convention. "
				// + "Prière de la signer avant de l'envoyer à l'entreprise.";

				String conventionPath = conv.get().getPathConvention();
				String conventionLabel = idEt + "_Convention_" + idEt + ".pdf";

				String studentMail = utilServices.findStudentMailById(idEt); // Server DEPLOY_SERVER
				// String studentMail = "saria.essid@esprit.tn"; // Local

				System.out.println("---------------------------> Start");

				utilServices.sendMail("Validation de Convention", studentMail, content);
				// utilServices.sendMailWithAttachment(studentMail, "Validation de Convention",
				// content, conventionPath, conventionLabel);
			}
			if (convCodePays.equalsIgnoreCase("FR")) {
				StudentConvFRDto scf = utilServices.findStudentConvFRDtoById(idEt);

				ConventionFR_PDF convFR = new ConventionFR_PDF(conv, path, scf, studentOption, studentDepartment);
				System.out.println("----------------- FR");

				/**********************************************
				 * Notify Mail
				 *******************************************************/
				System.out.println("----------------- Current Convention: " + convCodePays);
				conv.get().setPathConvention(path);
				conventionRepository.save(conv.get());

				// Send Notification by Mail
				DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String convValidationDate = dateFormata.format(new Date());

				String content = "Nous voulons vous informer par le présent mail que votre "
						+ "<strong><font color=grey> Demande de Convention </font></strong> "
						+ "a été bien validée par le Responsable des Stages le " + "<font color=blue> "
						+ convValidationDate + " </font>.<br/>" + "Prière de vous présenter au bureau du stage "
						+ "<font color=grey>Bloc A, Rez-de-Chaussée</font> pour récupérer votre Convention.";

				String studentMail = utilServices.findStudentMailById(idEt); // Server DEPLOY_SERVER
				// String studentMail = "saria.essid@esprit.tn"; // Local

				System.out.println("---------------------------> Start");

				utilServices.sendMail("Validation de Convention", studentMail, content);
			}

		}

		// serviceStageService.GenerateConvention(idEt, date.substring(0,
		// 19).replace("T", " "));
		return new ResponseEntity<>(serviceStageService.UpdateConventionStateFormedDate(idEt, date), HttpStatus.OK);

	}


	
	@PutMapping(value = "/refuseConventionStateFD", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ConventionForRSSDto>refuseConventionState(@RequestParam("idET") String idEt,
			@RequestParam("date") String dateOFF) throws Exception {

		System.out.println("------THIS-1---------- idEt: " + idEt);
		String date = dateOFF.trim();
		System.out.println("------THIS-2.1---------- date$" + dateOFF + "$");
		System.out.println("------THIS-2.2---------- date$" + date + "$");

		// System.out.println("------THIS-1---------- idEt: " + idEt);
		// System.out.println("------THIS-2---------- date: " + date);

		// String convDt = date;
		// String convDt = date.substring(0, 19).replace("T", " ");

		if (conventionRepository.getConventionByIdFormedDate(idEt, date).isPresent()) {
			System.out.println("------THIS-3---------- date: " + date);
			Optional<Convention> conv = conventionRepository.getConventionByIdFormedDate(idEt, date);

			Date dat = new Date();
			// String pattern = "dd-MM-yyyy";
			// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			// String datenow = simpleDateFormat.format(new Date());

			String studentFullName = utilServices.findStudentFullNameById(idEt);
			String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
			String studentOption = utilServices.findOptionByClass(studentClasse,
					optionRepository.listOptionsByYear("2021"));
			String studentDepartment = utilServices.findDepartmentByClassForConventionDiplome(studentClasse);

			String path = "C:/ESP-DOCS/Conventions/" + studentFullName + "-" + dat.getTime() + ".pdf";

			String convCodePays = "--";
			if (conv.get().getEntrepriseAccueilConvention().getPays() != null) {
				convCodePays = conv.get().getEntrepriseAccueilConvention().getPays().getLangueCode();
			}

			if (convCodePays.equalsIgnoreCase("TN")) {
				ConventionTN_PDF convTN = new ConventionTN_PDF(conv, path, studentFullName, studentOption,
						studentDepartment);
				System.out.println("----------------- TN");

				/**********************************************
				 * Notify Mail
				 *******************************************************/
				System.out.println("----------------- Current Convention: " + convCodePays);
				conv.get().setPathConvention(path);
				conventionRepository.save(conv.get());

				// Send Notification by Mail
				DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String convValidationDate = dateFormata.format(new Date());

				String content = "Nous voulons vous informer par le présent mail que votre <strong><font color=grey> "
						+ "Demande de Convention </font></strong> a été refusée par le Responsable des Stages le <font color=blue> "
						+ convValidationDate + " </font>.<br/>";
				// + "Ci-joint votre Convention. "
				// + "Prière de la signer avant de l'envoyer à l'entreprise.";

				String conventionPath = conv.get().getPathConvention();
				String conventionLabel = idEt + "_Convention_" + idEt + ".pdf";

				String studentMail = utilServices.findStudentMailById(idEt); // Server DEPLOY_SERVER
				// String studentMail = "saria.essid@esprit.tn"; // Local

				System.out.println("---------------------------> Start");

				utilServices.sendMail("Validation de Convention", studentMail, content);
				// utilServices.sendMailWithAttachment(studentMail, "Validation de Convention",
				// content, conventionPath, conventionLabel);
			}

			if (convCodePays.equalsIgnoreCase("EN") || convCodePays.equalsIgnoreCase("--")) {
				ConventionEN_PDF convEN = new ConventionEN_PDF(conv, path, studentFullName, studentOption,
						studentDepartment);
				System.out.println("----------------- EN");

				/**********************************************
				 * Notify Mail
				 *******************************************************/
				System.out.println("----------------- Current Convention: " + convCodePays);
				conv.get().setPathConvention(path);
				conventionRepository.save(conv.get());

				// Send Notification by Mail
				DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String convValidationDate = dateFormata.format(new Date());

				String content = "Nous voulons vous informer par le présent mail que votre "
						+ "<strong><font color=grey> Demande de Convention </font></strong> "
						+ "a été bien validée par le Responsable des Stages le <font color=blue> " + convValidationDate
						+ " </font>.<br/>";
				// + "Ci-joint votre Convention. "
				// + "Prière de la signer avant de l'envoyer à l'entreprise.";

				String conventionPath = conv.get().getPathConvention();
				String conventionLabel = idEt + "_Convention_" + idEt + ".pdf";

				String studentMail = utilServices.findStudentMailById(idEt); // Server DEPLOY_SERVER
				// String studentMail = "saria.essid@esprit.tn"; // Local

				System.out.println("---------------------------> Start");

				utilServices.sendMail("Validation de Convention", studentMail, content);
				// utilServices.sendMailWithAttachment(studentMail, "Validation de Convention",
				// content, conventionPath, conventionLabel);
			}
			if (convCodePays.equalsIgnoreCase("FR")) {
				StudentConvFRDto scf = utilServices.findStudentConvFRDtoById(idEt);

				ConventionFR_PDF convFR = new ConventionFR_PDF(conv, path, scf, studentOption, studentDepartment);
				System.out.println("----------------- FR");

				/**********************************************
				 * Notify Mail
				 *******************************************************/
				System.out.println("----------------- Current Convention: " + convCodePays);
				conv.get().setPathConvention(path);
				conventionRepository.save(conv.get());

				// Send Notification by Mail
				DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String convValidationDate = dateFormata.format(new Date());

				String content = "Nous voulons vous informer par le présent mail que votre "
						+ "<strong><font color=grey> Demande de Convention </font></strong> "
						+ "a été bien validée par le Responsable des Stages le " + "<font color=blue> "
						+ convValidationDate + " </font>.<br/>" + "Prière de vous présenter au bureau du stage "
						+ "<font color=grey>Bloc A, Rez-de-Chaussée</font> pour récupérer votre Convention.";

				String studentMail = utilServices.findStudentMailById(idEt); // Server DEPLOY_SERVER
				// String studentMail = "saria.essid@esprit.tn"; // Local

				System.out.println("---------------------------> Start");

				utilServices.sendMail("Validation de Convention", studentMail, content);
			}

		}

		// serviceStageService.GenerateConvention(idEt, date.substring(0,
		// 19).replace("T", " "));
		return new ResponseEntity<>(serviceStageService.RefuseConventionStateFormedDate(idEt, date), HttpStatus.OK);

	}

	//
	// @PutMapping(value = "/updateConventionStateForRSS", produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// @ResponseStatus(HttpStatus.OK)
	// public ResponseEntity<Convention>
	// updateConventionStateForRSS(@RequestParam("idET") String idEt,
	// @RequestParam("date") String date) throws Exception
	// {
	//
	// System.out.println("------THIS-1---------- idEt: " + idEt);
	// System.out.println("------THIS-2---------- date: " + date);
	//
	// String convDt = date.substring(0, 19).replace("T", " ");
	//
	// if (conventionRepository.getConventionById(idEt, convDt).isPresent())
	// {
	// System.out.println("------THIS-3---------- date: " + convDt);
	// Optional<Convention> conv = conventionRepository.getConventionById(idEt,
	// convDt);
	//
	// Date dat = new Date();
	//// String pattern = "dd-MM-yyyy";
	//// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	//// String datenow = simpleDateFormat.format(new Date());
	//
	// String studentFullName = utilServices.findStudentFullNameById(idEt);
	// String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
	// String studentOption = utilServices.findOptionByClass(studentClasse,
	// optionRepository.listOptionsByYear("1988"));
	// String studentDepartment = utilServices.findDepartmentByClass(studentClasse);
	//
	//
	// String path = "C:/ESP-DOCS/Conventions/" + studentFullName + "-" +
	// dat.getTime() + ".pdf";
	//
	// String convCodePays = "--";
	// if(conv.get().getEntrepriseAccueilConvention().getPays() != null)
	// {
	// convCodePays =
	// conv.get().getEntrepriseAccueilConvention().getPays().getLangueCode();
	// }
	//
	//
	// if(convCodePays.equalsIgnoreCase("TN"))
	// {
	// ConventionTN_PDF convTN = new ConventionTN_PDF(conv, path, studentFullName,
	// studentOption, studentDepartment);
	// System.out.println("----------------- TN");
	//
	// /********************************************** Notify Mail
	// *******************************************************/
	// System.out.println("----------------- Current Convention: " + convCodePays);
	// conv.get().setPathConvention(path);
	// conventionRepository.save(conv.get());
	//
	//
	// // Send Notification by Mail
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// String convValidationDate = dateFormata.format(new Date());
	//
	// String content = "Nous voulons vous informer par le présent mail que votre
	// <strong><font color=grey> "
	// + "Demande de Convention </font></strong> a été bien validée par le
	// Responsable des Stages le <font color=red> "
	// + convValidationDate + " </font>.<br/>";
	//// + "Ci-joint votre Convention. "
	//// + "Prière de la signer avant de l'envoyer à l'entreprise.";
	//
	// String conventionPath = conv.get().getPathConvention();
	// String conventionLabel = idEt + "_Convention_" + idEt + ".pdf";
	//
	// // String studentMail = utilServices.findStudentMailById(idEt); // Server
	// DEPLOY_SERVER
	// String studentMail = "saria.essid@esprit.tn"; // Local
	//
	// System.out.println("---------------------------> Start");
	//
	// utilServices.sendMail("Validation de Convention", studentMail, content);
	// //utilServices.sendMailWithAttachment(studentMail, "Validation de
	// Convention", content, conventionPath, conventionLabel);
	// }
	//
	// if(convCodePays.equalsIgnoreCase("EN") ||
	// convCodePays.equalsIgnoreCase("--"))
	// {
	// ConventionEN_PDF convEN = new ConventionEN_PDF(conv, path, studentFullName,
	// studentOption, studentDepartment);
	// System.out.println("----------------- EN");
	//
	// /********************************************** Notify Mail
	// *******************************************************/
	// System.out.println("----------------- Current Convention: " + convCodePays);
	// conv.get().setPathConvention(path);
	// conventionRepository.save(conv.get());
	//
	//
	// // Send Notification by Mail
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// String convValidationDate = dateFormata.format(new Date());
	//
	// String content = "Nous voulons vous informer par le présent mail que votre
	// <strong><font color=grey> Demande de Convention </font></strong> a été bien
	// validée par le Responsable des Stages le <font color=red> "
	// + convValidationDate + " </font>.<br/>";
	//// + "Ci-joint votre Convention. "
	//// + "Prière de la signer avant de l'envoyer à l'entreprise.";
	//
	// String conventionPath = conv.get().getPathConvention();
	// String conventionLabel = idEt + "_Convention_" + idEt + ".pdf";
	//
	// // String studentMail = utilServices.findStudentMailById(idEt); // Server
	// DEPLOY_SERVER
	// String studentMail = "saria.essid@esprit.tn"; // Local
	//
	// System.out.println("---------------------------> Start");
	//
	// utilServices.sendMail("Validation de Convention", studentMail, content);
	// // utilServices.sendMailWithAttachment(studentMail, "Validation de
	// Convention", content, conventionPath, conventionLabel);
	// }
	// if(convCodePays.equalsIgnoreCase("FR"))
	// {
	// StudentConvFRDto scf = utilServices.findStudentConvFRDtoById(idEt);
	//
	// ConventionFR_PDF convFR = new ConventionFR_PDF(conv, path, scf,
	// studentOption, studentDepartment);
	// System.out.println("----------------- FR");
	//
	// /********************************************** Notify Mail
	// *******************************************************/
	// System.out.println("----------------- Current Convention: " + convCodePays);
	// conv.get().setPathConvention(path);
	// conventionRepository.save(conv.get());
	//
	//
	// // Send Notification by Mail
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// String convValidationDate = dateFormata.format(new Date());
	//
	// String content = "Nous voulons vous informer par le présent mail que votre
	// <strong><font color=grey> Demande de Convention </font></strong> a été bien
	// validée par le Responsable des Stages le <font color=red> "
	// + convValidationDate + " </font>.<br/>"
	// + "Prière de vous présenter au bureau du stage "
	// + "<font color=grey>Bloc A, Rez-de-Chaussée</font> pour récupérer votre
	// Convention.";
	//
	// // String studentMail = utilServices.findStudentMailById(idEt); // Server
	// DEPLOY_SERVER
	// String studentMail = "saria.essid@esprit.tn"; // Local
	//
	// System.out.println("---------------------------> Start");
	//
	// utilServices.sendMail("Validation de Convention", studentMail, content);
	// }
	//
	// }
	//
	// // serviceStageService.GenerateConvention(idEt, date.substring(0,
	// 19).replace("T", " "));
	// return new ResponseEntity<>(
	// serviceStageService.UpdateConventionState(idEt, date.substring(0,
	// 19).replace("T", " ")),
	// HttpStatus.OK);
	//
	// }
	//

	@PutMapping(value = "/updateDemandeAnnulationConventionState", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Convention> updateDemandeAnnulationConventionState(@RequestParam("idET") String idEt, @RequestParam("date") String date) throws Exception
	{

		System.out.println("------THIS-1---------- idEt: " + idEt);
		System.out.println("------THIS-2.1---------- date: " + date);

		String convDt = date.substring(0, 19).replace("T", " ");
		System.out.println("------THIS-2.2---------- convDt: " + convDt);

		// if (conventionRepository.getConventionById(idEt, convDt).isPresent())
		if (conventionRepository.getConventionByIdFormedDateNew(idEt, convDt).isPresent())
		{
			System.out.println("------THIS-3---------- date: " + convDt);

			/*********************************** STAR : ANNULL ESP File after ANNULL Convention /***********************************/
			Optional<Convention> conv = conventionRepository.getConventionByIdFormedDateNew(idEt, convDt);
			//Optional<Convention> conv = conventionRepository.getConventionById(idEt, convDt);

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			// Save in Plan Travail
			if(!fichePFERepository.findFichePFEByStudent(idEt).isEmpty())
			{
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
				fichePFE.setEtatFiche("05");
				fichePFERepository.save(fichePFE);

				//System.out.println("-----------------------------$$$$$----------------------------> SARS-0: " + conv.get().getDateSaisieDemande());
				/*
				// Save in Convention
				if(typeTrtConvention.equalsIgnoreCase("Oui"))
				{
					DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					String dateConvention = dateFormata.format(fichePFE.getIdFichePFE().getConventionPK().getDateConvention());
					Convention c = conventionRepository.findConventionByStudentAndDateConvention(idEt, dateConvention);
					c.setTraiter("04");
					conventionRepository.save(c);
				}
				*/

				//System.out.println("-----------------------------$$$$$----------------------------> SARS-0: " + conv.get().getDateSaisieDemande());

				// Save in Traitement Plan Travail for History
				String dateDepotESPFileSTR = dateFormata.format(fichePFE.getIdFichePFE().getDateDepotFiche());
				String dateSaisieDemandeSTR = dateFormata.format(conv.get().getDateSaisieDemande());

				System.out.println("---------------------------------------------------------> SARS-1: " + dateDepotESPFileSTR);
				System.out.println("---------------------------------------------------------> SARS-2: " + dateSaisieDemandeSTR);

				TraitementFichePFE afp = traitementFicheRepository.
						findTraitementFicheByStudentAndFicheAndDateSaisieDemandeTrt(idEt, dateDepotESPFileSTR, dateSaisieDemandeSTR);
				System.out.println("---------------------------------------------------------> SARS-3: " + afp.getDescription());

				afp.setEtatTrt("02");  // WRONG CODE ALTERTED ON 03.01.22 [02 -> 12]
				afp.setDateTrtFiche(new Timestamp(System.currentTimeMillis()));
				traitementFicheRepository.save(afp);

				System.out.println("-------------ffffffffffffffffffffff---> Verify OK VDM: " + afp.getEtatTrt());

				/*********************************** END : ANNULL ESP File after ANNULL Convention /***********************************/

			}

			// Send Notification by Mail
			String convValidationDate = dateFormata.format(new Date());

			String content = "Nous voulons vous informer par le présent mail que votre <strong><font color=grey> Demande Annulation de Convention </font></strong> "
					+ "a été bien validée par le Responsable des Stages le <font color=red>"
					+ convValidationDate + " </font>.<br/>"
					+ "Par conséquent, Votre Plan Travail a été annulé <font color=red> automatiquement</font> .";

			// String studentMail = utilServices.findStudentMailById(idEt);  // Server  DEPLOY_SERVER
			String studentMail = "student@esprit.tn";   // Local

			String AEMail = utilServices.findMailPedagogicalEncadrant(idEt);     //DEPLOY_SERVER
			// String AEMail = "saria.essid@esprit.tn";

			String EEMail = conv.get().getEntrepriseAccueilConvention().getAddressMail();     //DEPLOY_SERVER
			// String EEMail = "saria.essid@esprit.tn";

			String mailRSS = responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"); // Server  DEPLOY_SERVER
			// String mailRSS = "raa@esprit.tn";// Local

			System.out.println("START---------------------------------------------------> Mail SENT TO: " + AEMail);
			System.out.println("==>StudentCJ Mail : " + utilServices.findStudentMailById(idEt));
			System.out.println("==>AEMail : " + utilServices.findMailPedagogicalEncadrant(idEt));
			// System.out.println("==>EEMail : " + conv.get().getEntrepriseAccueilConvention().getAddressMail());
			System.out.println("==>mailRSS : " + responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"));
			System.out.println("END -----------------------------------------------------------------> *");


			List<String> receiversCC = new ArrayList<String>();
			receiversCC.add(AEMail); receiversCC.add(mailRSS); receiversCC.add(EEMail);
			String responsibles = receiversCC.stream().collect(Collectors.joining(", "));

			utilServices.sendMailWithManyCC("Validation Demande Annulation de Convention", studentMail, responsibles, content);
//			utilServices.sendMailWithCC(subject, receiver, receiverCC, content);
		}

		return new ResponseEntity<>(
				serviceStageService.UpdateDemandeAnnulationConventionState(idEt, date.substring(0, 19).replace("T", " ")),
				HttpStatus.OK);

	}

	@GetMapping("/avenantList")
	public ResponseEntity<List<Avenant>> getAvenantList(@RequestParam("id") String id) {
		try {
			List<Avenant> AvenantList = serviceStageService.getAvenants(id);
			if (AvenantList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AvenantList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Got All Avenants List : Show Top, To Got Avns by Service Stage
	@GetMapping("/allAvenantList")
	public ResponseEntity<List<Avenant>> getAllAvenantList() {
		try {
			List<Avenant> AvenantList = serviceStageService.getAllAvenants();
			if (AvenantList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AvenantList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateAvenantState", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Avenant> updateAvenantState(@RequestParam("idET") String idET,
			@RequestParam("dateConvention") String dateConvention, @RequestParam("dateAvenant") String DateAvenant)
			throws Exception {
		serviceStageService.GenerateAvenant(idET, dateConvention.substring(0, 19).replace("T", " "),
				DateAvenant.substring(0, 19).replace("T", " "));
		return new ResponseEntity<>(serviceStageService.UpdateAvenantState(idET,
				dateConvention.substring(0, 19).replace("T", " "), DateAvenant.substring(0, 19).replace("T", " ")),
				HttpStatus.OK);
	}

	@GetMapping("/etudiantListbydep")
	public ResponseEntity<List<StudentCJ>> getEtudiantListbydep(@RequestParam("id") String idServiceStage) {
		try {
			List<StudentCJ> StudentList = etudiantRepository.getEtudiantbyDep(idServiceStage);
			if (StudentList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(StudentList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @GetMapping("/ficheListbydep") public ResponseEntity<List<FichePFE>>
	 * ficheListbydep(@RequestParam("id") String idServiceStage) { try {
	 * List<FichePFE> FichePFEList =
	 * fichePFERepository.getFichesBydept(idServiceStage); if
	 * (FichePFEList.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 * 
	 * return new ResponseEntity<>(FichePFEList, HttpStatus.OK); } catch (Exception
	 * e) {
	 * 
	 * return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@GetMapping("/FichesForDepot")
	public ResponseEntity<?> FichesForDepot(@RequestParam("id") String idServiceStage) {

		try {
			System.out.println("---------------------------> AZERTY 1: " + idServiceStage);

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			List<FichePFE> FichePFEList = fichePFERepository.getFichesForDepot(idServiceStage);
			System.out.println("---------------------------> AZERTY 2: " + FichePFEList.size());
			if (FichePFEList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("---------------------------> AZERTY 3");
			List<DepotRapport> FichePFEListFinale = new ArrayList<DepotRapport>();
			System.out.println("---------------------------> AZERTY 4");
			for (FichePFE F : FichePFEList) {
				StudentIdFullNameDto S = findStudentIdFullNameOFF(F.getIdFichePFE().getConventionPK().getIdEt());
				System.out.println("-----------------------------------------------------------------> AZERTY 2: "
						+ F.getIdFichePFE().getConventionPK().getIdEt());

				String dateConvention = dateFormata.format(F.getIdFichePFE().getConventionPK().getDateConvention());
				Integer trainingDuration = conventionRepository.findDureeStageWeekByStudentAndDateConvention(
						F.getIdFichePFE().getConventionPK().getIdEt(), dateConvention);

				System.out.println("---------------------------> AZERTY 5: " + trainingDuration);

				DepotRapport D = new DepotRapport(F.getIdFichePFE().getConventionPK().getIdEt(), S.getFullName(),
						F.getPathRapportVersion2(), F.getPathPlagiat(), F.getPathAttestationStage(),
						F.getPathSupplement(), codeNomenclatureRepository.findEtatFiche(F.getEtatFiche()),
						codeNomenclatureRepository.findEtatDepot(F.getValidDepot()),
						F.getIdFichePFE().getDateDepotFiche(), trainingDuration,
						// dateFormata.format(F.getDateDepotReports())
						F.getDateDepotReports());

				FichePFEListFinale.add(D);
				System.out.println("================= lol 2 =============================> SIZE AZERTY: "
						+ FichePFEListFinale.size());

			}

			System.out.println("-----*************hi****lol1******END**ddd***************--> AZERTY - 2: "
					+ FichePFEListFinale.size());

			// StudentList.sort(Comparator.comparing(StudentDetails::getAnneeDebInscription).reversed());
			FichePFEListFinale.sort(Comparator.comparing(DepotRapport::getDateDepotReports).reversed());
			return new ResponseEntity<>(FichePFEListFinale, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/FichesForDepotVal")
	public ResponseEntity<?> FichesForDepotVal(@RequestParam("id") String idServiceStageFULL) {
		try {
			
			String idServiceStage = null;
			if(idServiceStageFULL.equalsIgnoreCase("SR-STG-IT4") || idServiceStageFULL.equalsIgnoreCase("SR-STG-IT2") || idServiceStageFULL.equalsIgnoreCase("SR-STG-IT1"))
			//if(idServiceStageFULL.startsWith("SR-STG-IT"))
			{
				idServiceStage = "SR-STG-IT";
			}
			else
			{
				idServiceStage = idServiceStageFULL;
			}
			
			
			System.out.println("----------------------> PIKA 1: " + idServiceStage);
			List<FichePFE> FichePFEList = fichePFERepository.getFichesForDepotVal(idServiceStage);
			System.out.println("----------------------> PIKA 2: " + FichePFEList.size());

			if (FichePFEList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			List<DepotRapport> FichePFEListFinale = new ArrayList<DepotRapport>();
			for (FichePFE F : FichePFEList) {
				StudentIdNomPrenomDto S = findStudentIdFullName(F.getIdFichePFE().getConventionPK().getIdEt());
				DepotRapport D = new DepotRapport(F.getIdFichePFE().getConventionPK().getIdEt(), S.getNomet(),
						S.getPrenomet(), F.getPathRapportVersion2(), F.getPathPlagiat(), F.getPathAttestationStage(),
						F.getPathSupplement(), codeNomenclatureRepository.findEtatFiche(F.getEtatFiche()),
						codeNomenclatureRepository.findEtatDepot(F.getValidDepot()),
						F.getIdFichePFE().getDateDepotFiche());

				if(grilleAcademicEncadrantRepository.findGrilleByFiche(F.getIdFichePFE()) != null)
				{
					if(grilleAcademicEncadrantRepository.findGrilleByFiche(F.getIdFichePFE()).getEtatGrille().equalsIgnoreCase("02"))
					{
						D.setEtatGrilleEncadrement("DONE");
					}
					else
					{
						D.setEtatGrilleEncadrement("NOTYET");
					}
				}
				else
				{
					D.setEtatGrilleEncadrement("NOTYET");
				}
				
				FichePFEListFinale.add(D);
			}
			return new ResponseEntity<>(FichePFEListFinale, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/FichesDepotValALL")
	public ResponseEntity<?> FichesForDepotVal() {
		try {
			
			List<FichePFE> FichePFEList = fichePFERepository.getFichesDepotValAll();
			System.out.println("----------------------> PIKA 2: " + FichePFEList.size());

			if (FichePFEList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			List<DepotRapport> FichePFEListFinale = new ArrayList<DepotRapport>();
			for (FichePFE F : FichePFEList) {
				StudentIdNomPrenomDto S = findStudentIdFullName(F.getIdFichePFE().getConventionPK().getIdEt());
				DepotRapport D = new DepotRapport(F.getIdFichePFE().getConventionPK().getIdEt(), S.getNomet(),
						S.getPrenomet(), F.getPathRapportVersion2(), F.getPathPlagiat(), F.getPathAttestationStage(),
						F.getPathSupplement(), codeNomenclatureRepository.findEtatFiche(F.getEtatFiche()),
						codeNomenclatureRepository.findEtatDepot(F.getValidDepot()),
						F.getIdFichePFE().getDateDepotFiche());

				if(grilleAcademicEncadrantRepository.findGrilleByFiche(F.getIdFichePFE()) != null)
				{
					if(grilleAcademicEncadrantRepository.findGrilleByFiche(F.getIdFichePFE()).getEtatGrille().equalsIgnoreCase("02"))
					{
						D.setEtatGrilleEncadrement("DONE");
					}
					else
					{
						D.setEtatGrilleEncadrement("NOTYET");
					}
				}
				else
				{
					D.setEtatGrilleEncadrement("NOTYET");
				}
				
				FichePFEListFinale.add(D);
			}
			return new ResponseEntity<>(FichePFEListFinale, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/FichesForDepotInc")
	public ResponseEntity<?> FichesForDepotInc(@RequestParam("id") String idServiceStage) {
		try {
			List<FichePFE> FichePFEList = fichePFERepository.getFichesForDepotInc(idServiceStage);
			if (FichePFEList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			List<DepotRapport> FichePFEListFinale = new ArrayList<DepotRapport>();
			for (FichePFE F : FichePFEList) {
				StudentIdNomPrenomDto S = findStudentIdFullName(F.getIdFichePFE().getConventionPK().getIdEt());
				DepotRapport D = new DepotRapport(F.getIdFichePFE().getConventionPK().getIdEt(), S.getNomet(),
						S.getPrenomet(), F.getPathRapportVersion2(), F.getPathPlagiat(), F.getPathAttestationStage(),
						F.getPathSupplement(), codeNomenclatureRepository.findEtatFiche(F.getEtatFiche()),
						codeNomenclatureRepository.findEtatDepot(F.getValidDepot()),
						F.getIdFichePFE().getDateDepotFiche());
				FichePFEListFinale.add(D);
			}
			return new ResponseEntity<>(FichePFEListFinale, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateDepotToVALIDE", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> updateDepotToVALIDE(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") String dateFiche) {

		String receiver = findStudentMailById(idET);

		String idPair = fichePFERepository.findBinomeIdByStudentId(idET).get(0); // Binome
		String mailPair = findStudentMailById(idPair);

		System.out.println("------------------------------------------> Pair A: " + mailPair);
		if (mailPair != null) {
			List<String> pairs = new ArrayList<String>();
			pairs.add(receiver);
			pairs.add(mailPair);
			receiver = pairs.stream().collect(Collectors.joining(", "));
			System.out.println("------------------------------------------> Pair B: " + mailPair);
		}
		String mailRSS = responsableServiceStageRepository
				.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(idET).get(0));

		String studentClasse = utilServices.findCurrentClassByIdEt(idET);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idET) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

		System.out.println("-----------> Student : " + studentClasse + " - " + studentOption.toLowerCase());

		List<String> idCPSs = pedagogicalCoordinatorRepository
				.gotIdPedagogicalCoordinatorByOptionNew(studentOption.toLowerCase());

		String idCPS = null;
		String mailCPS = "saria.essid@esprit.tn";
		// List<String> idExistCPSs = new ArrayList<String>();
		for (String s : idCPSs) {
			// ********************** Exceptional Case where same CPS has 2 Units : just for
			// Current University year
			if (studentOption.toUpperCase().contains("SIM") || studentOption.toUpperCase().contains("GAMIX")
					|| studentOption.toUpperCase().contains("WIN") || studentOption.toUpperCase().contains("IOSYS")) {
				if (studentOption.toUpperCase().contains("SIM")) {
					mailCPS = "CPS_SIM@esprit.tn";
				}
				if (studentOption.toUpperCase().contains("GAMIX")) {
					mailCPS = "CPS_GAMIX@esprit.tn";
				}
				if (studentOption.toUpperCase().contains("WIN")) {
					mailCPS = "CPS_WIN@esprit.tn";
				}
				if (studentOption.toUpperCase().contains("IOSYS")) {
					mailCPS = "CPS_IOSYS@esprit.tn";
				}
			} else {
				if (pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorByIdNew(s) != null) {
					mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorByIdNew(s);
				}
			}
			/***********************************************************************************************************/
			// if(pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByIdEns(s) !=
			// null)
			// {
			// idCPS =
			// pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByIdEns(s);
			// // idExistCPSs
			// }
		}

		System.out.println("-----------> idCPS : " + idCPS);

		System.out.println("-----------> mailCPS : " + mailCPS);
		List<String> mailsCPSRSS = new ArrayList<String>();
		mailsCPSRSS.add(utilServices.findMailPedagogicalEncadrant(idET));
		mailsCPSRSS.add(mailCPS);
		mailsCPSRSS.add(mailRSS);
		String mailsCPSRSS_STR = mailsCPSRSS.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"')
				.collect(Collectors.joining(", "));

		/*
		 * String mailPE = findMailAcademicEncadrantByStudent(idET);
		 * 
		 * String codeOption =
		 * utilServices.findOptionByClass(findCurrentClassByIdEt(idET),
		 * optionRepository.listOptionsByYear("2021"));
		 * 
		 * System.out.println("-------------> OPTION " + codeOption);
		 * System.out.println("-------------> lol " + responsableStageRepository.lol());
		 * 
		 * String mailAcademicCoordinator =
		 * teacherRepository.findMailTeacherById(responsableStageRepository.
		 * findPedagogicalCoordinatorByCodeOption(codeOption));
		 * 
		 * List<String> receiversCC = new ArrayList<String>(); receiversCC.add(mailPE);
		 * receiversCC.add(mailAcademicCoordinator); receiversCC.add(mailRSS); String
		 * responsibles = receiversCC.stream().collect(Collectors.joining(", "));
		 * 
		 * System.out.
		 * println("------------------------------------------> VALIDATE 1: HOSTS VDR - VALIDATE: "
		 * + responsibles + "--->" + mailRSS + " - " + mailAcademicCoordinator + " - " +
		 * mailPE + " *** " + receiver);
		 */

		// ****************************************************************************
		// Notification by mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateValidateDepot = dateFormata.format(new Date());

		String subject = "Traitement Dépôt Rapport";

		String content = "Nous voulons vous informer par le présent mail que votre "
				+ "<strong><font color=grey>Dépôt</font></strong> a été "
				+ "<strong><font color=green>validé</font></strong> par le Service des Stages "
				+ "et c'est le <font color=blue> " + dateValidateDepot + " </font>.<br/>"
				+ "Merci de patienter jusqu'à la planification de votre soutenance.";

		utilServices.sendMailWithManyCC(subject, receiver, mailsCPSRSS_STR, content);

		return new ResponseEntity<>(
				serviceStageService.UpdateRepotToVALIDE(idET, dateFiche.substring(0, 19).replace("T", " ")),
				HttpStatus.OK);

	}

	@PutMapping(value = "/updateDepotToREFUSE", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> updateDepotToREFUSE(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") String dateFiche, @RequestParam("cancelMotif") String encodedCancelMotif) {

		String cancelMotif = utilServices.decodeEncodedValue(encodedCancelMotif);
		String receiver = findStudentMailById(idET);

		String mailPair = utilServices.findStudentMailById(idET); // Binome

		if (mailPair != null) {
			List<String> pairs = new ArrayList<String>();
			pairs.add(receiver);
			pairs.add(mailPair);
			receiver = pairs.stream().collect(Collectors.joining(", "));
		}

		System.out.println("------------------------------------------> receiver: " + receiver);

		String mailRSS = responsableServiceStageRepository
				.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(idET).get(0));

		String studentClasse = utilServices.findCurrentClassByIdEt(idET);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idET) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

		System.out.println("-----------> Student : " + studentClasse + " - " + studentOption.toLowerCase());

		String idCPS = pedagogicalCoordinatorRepository.gotIdEnsPedagogicalCoordinatorByOption(studentOption.toLowerCase());
    	System.out.println("--------------> idCPS: " + idCPS);
    	// ********************** Exceptional Case where same CPS has 2 Units : just for Current University year
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
        	if(studentOption.toUpperCase().contains("IOSYS"))
        	{
        		mailCPS = "CPS_IOSYS@esprit.tn";
        	}
    	}
    	else
    	{
    		mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorByIdNew(idCPS);
    	}
    	/***********************************************************************************************************/
    	
//		List<PedagogicalCoordinator> CPSs = pedagogicalCoordinatorRepository
//				.gotAllIdsPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//		System.out.println("-----------> Size CPSs : " + CPSs.size());
//		String mailCPS = null;
//		for (PedagogicalCoordinator pc : CPSs) {
//			if (pedagogicalCoordinatorRepository
//					.gotMailPedagogicalCoordinatorByIdEns(pc.getTeacher().getIdEns()) != null) {
//				mailCPS = pedagogicalCoordinatorRepository
//						.gotMailPedagogicalCoordinatorByIdEns(pc.getTeacher().getIdEns());
//			}
//		}
//		System.out.println("-----****************------> MAils : " + mailCPS + " - " + mailRSS);

		// String mailRSS =
		// responsableServiceStageRepository.findRespServStgMailById(conventionRepository.findResponsableServiceStageByIdEt(idET).get(0));
		//
		// String studentClasse = utilServices.findCurrentClassByIdEt(idET);
		// String studentOption = utilServices.findOptionByClass(studentClasse,
		// optionRepository.listOptionsByYear("2021"));
		//
		// System.out.println("-----------> Student : " + studentClasse + " - " +
		// studentOption.toLowerCase());
		//
		// String idCPS =
		// pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByOption(studentOption.toLowerCase());
		// String mailCPS =
		// pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorById(idCPS).get(0);

		List<String> mailsCPSRSS = new ArrayList<String>();
		mailsCPSRSS.add(utilServices.findMailPedagogicalEncadrant(idET));
		mailsCPSRSS.add(mailCPS);
		mailsCPSRSS.add(mailRSS);
		String mailsCPSRSS_STR = mailsCPSRSS.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"')
				.collect(Collectors.joining(", "));
		/*
		 * String mailPE = findMailAcademicEncadrantByStudent(idET);
		 * 
		 * String codeOption =
		 * utilServices.findOptionByClass(findCurrentClassByIdEt(idET),
		 * optionRepository.listOptionsByYear("2021"));
		 * 
		 * String mailAcademicCoordinator =
		 * teacherRepository.findMailTeacherById(responsableStageRepository.
		 * findPedagogicalCoordinatorByCodeOption(codeOption));
		 * 
		 * List<String> receiversCC = new ArrayList<String>(); receiversCC.add(mailPE);
		 * receiversCC.add(mailAcademicCoordinator); receiversCC.add(mailRSS); String
		 * responsibles = receiversCC.stream().collect(Collectors.joining(", "));
		 * 
		 * System.out.println("------------------------------------------> REFUSE 2: " +
		 * responsibles + "--->" + mailRSS + " - " + mailAcademicCoordinator + " - " +
		 * mailPE + " *** " + receiver);
		 */

		// ****************************************************************************
		// Notification by mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateValidateDepot = dateFormata.format(new Date());

		String subject = "Traitement Dépôt Rapport";

		String content = "Nous voulons vous informer par le présent mail que votre "
				+ "<strong><font color=grey>Dépôt</font></strong> a été marqué comme "
				+ "<strong><font color=red>Incomplet</font></strong> par le Service des Stages "
				+ "avec le motif <strong><font color=red> " + cancelMotif + "</font></strong> , "
				+ "et c'est le <font color=blue> " + dateValidateDepot + " </font>.<br/>"
				+ "Veuillez consulter votre espace pour mettre à jour votre Dépôt.";

		utilServices.sendMailWithManyCC(subject, receiver, mailsCPSRSS_STR, content);

		return new ResponseEntity<>(serviceStageService.UpdateRepotToREFUSE(idET,
				dateFiche.substring(0, 19).replace("T", " "), cancelMotif), HttpStatus.OK);

	}

	@PutMapping(value = "/allConventionValidatedListByRSS")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<ConventionsValidatedForRSSDto>> getconventionValidatedListDto(
			@RequestParam("idRSS") String idRSS, @RequestParam("labelMonth") String labelMonth) {
		String idMonth = null;
		if (labelMonth.equalsIgnoreCase("Janvier")) {
			idMonth = "01";
		}
		if (labelMonth.equalsIgnoreCase("Fevrier")) {
			idMonth = "02";
		} // 25-02-2022 25-02-2022 21:22:23
		if (labelMonth.equalsIgnoreCase("Mars")) {
			idMonth = "03";
		}
		if (labelMonth.equalsIgnoreCase("Avril")) {
			idMonth = "04";
		}
		if (labelMonth.equalsIgnoreCase("Mai")) {
			idMonth = "05";
		}
		if (labelMonth.equalsIgnoreCase("Juin")) {
			idMonth = "06";
		}
		if (labelMonth.equalsIgnoreCase("Juillet")) {
			idMonth = "07";
		}
		if (labelMonth.equalsIgnoreCase("Aout")) {
			idMonth = "08";
		}
		if (labelMonth.equalsIgnoreCase("Septembre")) {
			idMonth = "09";
		}
		if (labelMonth.equalsIgnoreCase("Octobre")) {
			idMonth = "10";
		}
		if (labelMonth.equalsIgnoreCase("Novembre")) {
			idMonth = "11";
		}
		if (labelMonth.equalsIgnoreCase("Decembre")) {
			idMonth = "12";
		}

		try {
			// List<ConventionsValidatedForRSSDto> ConventionList =
			// serviceStageService.getAllConventionsValidatedDtoByRSS(idRSS, idMonth);
			// 25/02/22 21:12:03,493000000
			List<ConventionsValidatedForRSSDto> ConventionList = serviceStageService
					.getAllConventionsValidatedDtoByRSS(idRSS, idMonth);

			System.out.println("--------------ddddd 3-----> SARS VAL: " + ConventionList.size());
			if (ConventionList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(ConventionList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public String findMailAcademicEncadrantByStudent(String idET) {
		String idTeacher = null;
		List<String> idTeachersCJ = inscriptionRepository.findEncadrantPedagogiqueByStudent(idET);
		List<String> idTeachersCS = inscriptionRepository.findEncadrantPedagogiqueCSByStudent(idET);

		if (!idTeachersCJ.isEmpty()) {
			idTeacher = idTeachersCJ.get(0);
		}
		if (!idTeachersCS.isEmpty()) {
			idTeacher = idTeachersCS.get(0);
		}
		return teacherRepository.findMailTeacherById(idTeacher);
	}

	public String findCurrentClassByIdEt(String idET) {
		String currentClass = null;
		List<String> currentClassCJ = inscriptionRepository.findCurrentClassByIdEt(idET);
		List<String> currentClassCS = inscriptionRepository.findCurrentClassCSByIdEt(idET);

		System.out.println("----------------------------------> Class CJ: " + currentClassCJ.size());
		System.out.println("----------------------------------> Class CS: " + currentClassCS.size());

		if (!currentClassCJ.isEmpty()) {
			currentClass = currentClassCJ.get(0);
		}
		if (!currentClassCS.isEmpty()) {
			currentClass = currentClassCS.get(0);
		}

		return currentClass;
	}

	public String findStudentMailById(String idEt) {

		String mailStudent = null;
		List<String> mailsCJ = studentRepository.findStudentMailCJById(idEt);
		List<String> mailsCS = studentRepository.findStudentMailCSById(idEt);

		System.out.println("----------------------------------> Mail CJ: " + mailsCJ.size());
		System.out.println("----------------------------------> Mail CS: " + mailsCS.size());

		if (!mailsCJ.isEmpty()) {
			if (mailsCJ.get(0) != null) {
				mailStudent = mailsCJ.get(0);
			}
		}
		if (!mailsCS.isEmpty()) {
			if (mailsCS.get(0) != null) {
				mailStudent = mailsCS.get(0);
			}
		}

		return mailStudent;
	}

	public StudentIdNomPrenomDto findStudentIdFullName(String idEt) {
		StudentIdNomPrenomDto studentIdFullNameDto = null;
		StudentIdNomPrenomDto studentCJIdFullNameDto = studentRepository.findStudentCJIdFullName(idEt);
		StudentIdNomPrenomDto studentCSIdFullNameDto = studentRepository.findStudentCSIdFullName(idEt);

		if (studentCJIdFullNameDto != null) {
			studentIdFullNameDto = studentCJIdFullNameDto;
			System.out.println("----------------------------> CDJ: " + idEt);
		}
		if (studentCSIdFullNameDto != null) {
			studentIdFullNameDto = studentCSIdFullNameDto;
			System.out.println("----------------------------> CDS: " + idEt);
		}

		return studentIdFullNameDto;
	}

	public StudentIdFullNameDto findStudentIdFullNameOFF(String idEt) {
		StudentIdFullNameDto studentIdFullNameDto = null;
		StudentIdFullNameDto studentCJIdFullNameDto = studentRepository.findStudentCJIdFullNameOFF(idEt);
		StudentIdFullNameDto studentCSIdFullNameDto = studentRepository.findStudentCSIdFullNameOFF(idEt);

		if (studentCJIdFullNameDto != null) {
			studentIdFullNameDto = studentCJIdFullNameDto;
			// System.out.println("----------------------------> CDJ: " + idEt);
		}
		if (studentCSIdFullNameDto != null) {
			studentIdFullNameDto = studentCSIdFullNameDto;
			// System.out.println("----------------------------> CDS: " + idEt);
		}

		return studentIdFullNameDto;
	}

}

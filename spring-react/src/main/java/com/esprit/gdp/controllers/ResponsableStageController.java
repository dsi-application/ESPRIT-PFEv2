package com.esprit.gdp.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.esprit.gdp.dto.FicheETDetails;
import com.esprit.gdp.dto.StudentDetails;
import com.esprit.gdp.dto.StudentDto;
import com.esprit.gdp.dto.StudentExcelDto;
import com.esprit.gdp.dto.TeacherDetailsDto;
import com.esprit.gdp.dto.TeacherValidation;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.models.TraitementFichePFE;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.StudentCSRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.services.EncadrementPFEService;
import com.esprit.gdp.services.ResponsableStageService;
import com.esprit.gdp.services.UtilServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/responsableStage")
public class ResponsableStageController {

	@Autowired
	private EncadrementPFEService encadrementPFEService;

	@Autowired
	ResponsableStageService responsableStageService;

	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	StudentRepository etudiantRepository;

	@Autowired
	StudentCSRepository etudiantCSRepository;

	@Autowired
	TeacherRepository enseignantRepository;

	@Autowired
	InscriptionRepository inscriptionRepository;

	@Autowired
	private UtilServices utilServices;

	private String decode(String value) throws UnsupportedEncodingException {
		return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
	}

	@GetMapping("/ListEns")
	public ResponseEntity<List<Teacher>> getALLEns() {
		try {
			List<Teacher> TeacherList = enseignantRepository.findAll();
			if (TeacherList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(TeacherList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/ ")
	public ResponseEntity<List<StudentCJ>> getListEtudiantbyOptionbyEns(@RequestParam("code") String codeOption,
			@RequestParam("idEns") String idEns) {
		List<StudentCJ> AllTerminalStudent = new ArrayList<StudentCJ>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			List<String> strings = mapper.readValue(decode(codeOption), List.class);
			for (String C : strings) {

				String Option = C.replaceFirst("_[0-9][0-9]", "");
				List<StudentCJ> StudentList = etudiantRepository.getEtudiantEncadrésbyOption(idEns, Option);
				AllTerminalStudent.addAll(StudentList);

			}

			if (AllTerminalStudent.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AllTerminalStudent, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/FichePFEList")
	public ResponseEntity<?> getFichePFEList(@RequestParam("codeOption") String codeOption) {
		try {
			// System.out.println("slm" + decode(codeOption));
			ObjectMapper mapper = new ObjectMapper();
			List<String> strings = mapper.readValue(decode(codeOption), List.class);
			// System.out.println(strings);
			List<FichePFE> FichePFEList = responsableStageService.getFicheListbyOption(strings);

			List<FicheETDetails> FicheETDetailsList = new ArrayList<FicheETDetails>();
			for (FichePFE F : FichePFEList) {
				StudentExcelDto S = utilServices.findStudentExcelDtoById(F.getIdFichePFE().getConventionPK().getIdEt());
				FicheETDetails FF = new FicheETDetails(F.getIdFichePFE().getConventionPK().getIdEt(), S.getNom(),
						S.getPrenom(), F);

				FicheETDetailsList.add(FF);
			}

			if (FicheETDetailsList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(FicheETDetailsList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getEtudiant")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getEtudiant(@RequestParam("idET") String idET) {

		StudentDto studentDto = utilServices.findStudentDtoById(idET);

		if (studentDto == null) {
			return new ResponseEntity("{}", HttpStatus.OK);
		}

		int valeur = Integer.parseInt(findCurrentAnnneInscriptiobByIdEt(idET).get(0)) + 1;
		StudentDetails SNF = new StudentDetails(idET, studentDto.getNomet(), studentDto.getPrenomet(),
				studentDto.getAdressemailesp(), studentDto.getEmailet(), studentDto.getAdresseet(),
				studentDto.getTelet(), getOptionForEtudiant(idET).get(0),
				findCurrentAnnneInscriptiobByIdEt(idET).get(0) + " - " + String.valueOf(valeur));

		return new ResponseEntity<>(SNF, HttpStatus.OK);

		// oif if (etudiantRepository.findById(idET) == null) {
		// return new ResponseEntity("{}", HttpStatus.OK);
		//
		// }
		// Optional<StudentCJ> S = etudiantRepository.findById(idET);
		// int valeur =
		// Integer.parseInt(inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(S.get().getIdEt()).get(0))
		// + 1;
		// StudentDetails SNF = new StudentDetails(S.get().getIdEt(),
		// S.get().getNomet(), S.get().getPrenomet(),
		// S.get().getAdressemailesp(), S.get().getEmailet(), S.get().getAdresseet(),
		// S.get().getTelet(),
		// etudiantRepository.getOptionForEtudiant(S.get().getIdEt()).get(0),
		// inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(S.get().getIdEt()).get(0)
		// + " - "
		// + String.valueOf(valeur));
		//
		// return new ResponseEntity<>(SNF, HttpStatus.OK);

	}

	@GetMapping(value = "/getFiche")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<FichePFE> getFiche(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") String dateFiche) {

		if (fichePFERepository.getFichebyId(idET, dateFiche.substring(0, 19).replace("T", " ")) == null) {
			return new ResponseEntity("{}", HttpStatus.OK);
		} else
			return new ResponseEntity<>(
					fichePFERepository.getFichebyId(idET, dateFiche.substring(0, 19).replace("T", " ")), HttpStatus.OK);
	}

	@PutMapping(value = "/updateFichePFEVALIDEE", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> updateFichePFEStateVALIDEE(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") String dateFiche) {

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
		String dateTraitement = dateFormata.format(new Date());
		String subject = "Traitement Plan Travail";

		// String receiver = "saria.essid@esprit.tn";
		String receiver = utilServices.findStudentMailById(idET); // SDEPLOY_SERVER

		String content = "Nous voulons vous informer par le présent mail que "
				+ "votre Plan Travail <strong><font color=green> a été validé </font></strong> par "
				+ "le <strong><font color=grey> Coordinateur Pédagogique </font></strong> " + "le <font color=blue> "
				+ dateTraitement + " </font>.";

		utilServices.sendMail(subject, receiver, content);

		return new ResponseEntity<>(
				responsableStageService.UpdateFicheToVALIDEE(idET, dateFiche.substring(0, 19).replace("T", " ")),
				HttpStatus.OK);
	}

	@PutMapping(value = "/updateFichePFEREFUSEE", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> updateFichePFEStateREFUSEE(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") String dateFiche) {

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
		String dateTraitement = dateFormata.format(new Date());

		String subject = "Traitement Plan Travail";

		// String receiver = "saria.essid@esprit.tn";
		String receiver = utilServices.findStudentMailById(idET); // SERVERDEPLOY_SERVER
		String content = "Nous voulons vous informer par le présent mail que votre Plan Travail a été refusé "
				+ "par le <strong><font color=grey> Coordinateur Pédagogique </font></strong> "
				+ "le <font color=blue> " + dateTraitement + " </font>.";

		utilServices.sendMail(subject, receiver, content);

		return new ResponseEntity<>(
				responsableStageService.UpdateFicheToREFUSEE(idET, dateFiche.substring(0, 19).replace("T", " ")),
				HttpStatus.OK);
	}

	@GetMapping("/TraitementFichePFEList")
	public ResponseEntity<List<TraitementFichePFE>> getTraitementFichePFEList(
			@RequestParam("codeOption") String codeOption) {
		List<TraitementFichePFE> AllTerminalStudent = new ArrayList<>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			List<String> strings = mapper.readValue(decode(codeOption), List.class);
			for (String C : strings) {

				String Option = C.replaceFirst("_[0-9][0-9]", "");
				List<TraitementFichePFE> TraitementFichePFEList = responsableStageService
						.getTraitementFichePFEList(Option);
				AllTerminalStudent.addAll(TraitementFichePFEList);

			}
			if (AllTerminalStudent.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AllTerminalStudent, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/traiteDemandeModif", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<TraitementFichePFE> traiteDemandeModif(@RequestParam("idET") String idET,
			@RequestParam("dateDepotFiche") String dateDepotFiche, @RequestParam("dateTrtFiche") String dateTrtFiche

	) {

		// System.out.println("--------------------------------------- SATRt 0416");

		responsableStageService.UpdateFicheToDEPOSEE(idET, dateDepotFiche.substring(0, 19).replace("T", " "));

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
		String dateSaisi = dateFormata.format(new Date());

		String subject = "Traitement demande Plan Travail";

		// String receiver = "saria.essid@esprit.tn";
		String receiver = utilServices.findStudentMailById(idET); // SERVERSE061DEPLOY_SERVER
		String content = "Nous voulons vous informer par le présent mail que votre "
				+ "<strong><font color=grey> Demande de Modification Plan Travail </font></strong> "
				+ "<strong><font color=green> a été validé </font></strong> par le <strong><font color=grey> Coordinateur Pédagogique </font></strong>"
				+ "le <font color=red> " + dateSaisi + " </font>." + "<br/>Merci de consulter votre espace.";

		utilServices.sendMail(subject, receiver, content);
		/********************************************************************************************************/

		return new ResponseEntity<>(responsableStageService.UpdateTraitementFichePFE(idET,
				dateDepotFiche.substring(0, 19).replace("T", " "), dateTrtFiche.substring(0, 19).replace("T", " ")),
				HttpStatus.OK);
		// oif responsableStageService.UpdateFicheToDEPOSEE(idET,
		// dateDepotFiche.substring(0, 19).replace("T", " "));
		// return new ResponseEntity<>(
		// responsableStageService.UpdateTraitementFichePFE(idET,
		// dateDepotFiche.substring(0, 19).replace("T", " "),
		// dateFormata2.format(dateTrtFiche)), HttpStatus.OK);

	}

	@PutMapping(value = "/traiteDemandeAnnule", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<TraitementFichePFE> traiteDemandeAnnule(@RequestParam("idET") String idET,
			@RequestParam("dateDepotFiche") String dateDepotFiche, @RequestParam("dateTrtFiche") String dateTrtFiche) {
		StudentCJ S = encadrementPFEService.getStudentbyId(idET);
		DateFormat dateFormata2 = new SimpleDateFormat("dd-MM-yyyy");

		responsableStageService.UpdateFicheToANNULE(idET, dateDepotFiche.substring(0, 19).replace("T", " "));

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
		String dateSaisi = dateFormata.format(new Date());

		String subject = "Traitement demande Plan Travail";

		// String receiver = "saria.essid@esprit.tn";
		String receiver = utilServices.findStudentMailById(idET); // DEPLOY_SERVER
		String content = "Nous voulons vous informer par le présent mail que votre "
				+ "<strong><font color=grey> Demande d'Annulation Plan Travail </font></strong> "
				+ "<strong><font color=green> a été validé </font></strong> par le <strong><font color=grey> Coordinateur Pédagogique </font></strong>"
				+ "le <font color=red> " + dateSaisi + " </font>." + "<br/>Merci de consulter votre espace.";

		utilServices.sendMail(subject, receiver, content);
		/********************************************************************************************************/

		return new ResponseEntity<>(responsableStageService.UpdateTraitementFichePFE(idET,
				dateDepotFiche.substring(0, 19).replace("T", " "), dateTrtFiche.substring(0, 19).replace("T", " ")),
				HttpStatus.OK);

		// oif return new ResponseEntity<>(
		// responsableStageService.UpdateTraitementFichePFE(idET,
		// dateDepotFiche.substring(0, 19).replace("T", " "),
		// dateFormata2.format(dateTrtFiche)), HttpStatus.OK);
	}

	@GetMapping(value = "/getEncadrantByEtudiant")
	public ResponseEntity<TeacherDetailsDto> getEncadrantByEtudiant(@RequestParam("idEt") String idEt) {

		TeacherDetailsDto T = encadrementPFEService.getEncadrantByEtudiant(idEt);
		// oif Teacher T = encadrementPFEService.getEnseigantEncadrésbyET(idEt);

		if (T == null) {
			return new ResponseEntity("{}", HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<TeacherDetailsDto>(T, HttpStatus.OK);
	}

//	@PutMapping(value = "/updateEncadrantpeda", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public int updateEncadrantpeda(@RequestParam("idEt") String idEt, @RequestParam("idEns") String idEns) {
//
//		StudentCJ S = encadrementPFEService.getStudentbyId(idEt);
//		encadrementPFEService.sendSimpleMessage(S.getAdressemailesp(), "Changement encadrant pédagogique",
//				"Votre encadrant pédagigqiue a été changé , veuillez consulter votre espace .");
//		return inscriptionRepository.AffecteEncadrantPeda(idEns, idEt);
//	}

	@GetMapping("/EtudiantSansFicheList")
	public ResponseEntity<?> getEudiantSansFicheListbyOption(@RequestParam("codeOption") String codeOption) {
		try {
			List<StudentDetails> AllTerminalStudent = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			List<String> strings = mapper.readValue(decode(codeOption), List.class);
			// System.out.println("----------------------------- EtudiantSansFicheList: " + strings);

			for (String C : strings) {

				String Option = C.replaceFirst("_[0-9][0-9]", "");
				List<StudentDetails> StudentList = responsableStageService.getStudentSansFichebyOption(Option);
				AllTerminalStudent.addAll(StudentList);

			}

			if (AllTerminalStudent.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AllTerminalStudent, HttpStatus.OK);
		} catch (Exception e) {
			// System.out.println("exception===" + e);
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/TeacherWithNumberEncadrement")
	public ResponseEntity<?> getTeacherWithNumberEncadrement(@RequestParam("annee") String annee) {
		try {
			if (annee.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			} else {

				List<TeacherValidation> TeacherList = responsableStageService.getTeacherWithNumberEncad(annee);
				if (TeacherList.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}

				return new ResponseEntity<>(TeacherList, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/EtatValidationFiche")
	public ResponseEntity<?> getEtatValidationFiche(@RequestParam("codeOption") String codeOption,
			@RequestParam("annee") String annee) {
		List<Map<String, String>> AllTerminalStudent = new ArrayList<>();
		// System.out.println("-----> Start");
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<String> strings = mapper.readValue(decode(codeOption), List.class);
			for (String C : strings) {

				String Option = C.replaceFirst("_[0-9][0-9]", "");

				System.out.println("-----> FINAL opt: " + C + " - " + Option);

				List<Map<String, String>> FicheList = responsableStageService.getFicheListbyOptionbyAnnee(Option,
						annee);
				AllTerminalStudent.addAll(FicheList);

			}

			System.out.println("-------------------------------> FINAL: " + AllTerminalStudent.size());

			if (AllTerminalStudent.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AllTerminalStudent, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<Object[]> getAllTerminalStudent(String Option) {

		System.out.println("----------> SARS 0.0: " + Option);

		List<Object[]> allTerminalStudent = new ArrayList<Object[]>();

		List<Object[]> AllTerminalStudentCJ = etudiantRepository.getEtudiantTerminalLowerCJ(Option.toLowerCase());
		List<Object[]> AllTerminalStudentCS = etudiantRepository.getEtudiantTerminalLowerCS(Option.toLowerCase());

		System.out.println("----------> SARS 0.1: " + AllTerminalStudentCJ.size());

		System.out.println("----------> SARS 0.2: " + AllTerminalStudentCS.size());

		allTerminalStudent.addAll(AllTerminalStudentCJ);
		allTerminalStudent.addAll(AllTerminalStudentCS);

		// for(int i = 0; i<allTerminalStudent.size(); i++)
		// {
		// String presidentJury = (String) allTerminalStudent.get(i)[0];
		// }

		return allTerminalStudent;
	}

	public List<String> findCurrentAnnneInscriptiobByIdEt(String idEt) {
		List<String> findCurrentAnnneInscriptiobByIdEt = new ArrayList<String>();

		List<String> findCurrentAnnneInscriptiobByIdEtCJ = inscriptionRepository
				.findCurrentAnnneInscriptiobByIdEt(idEt);
		List<String> findCurrentAnnneInscriptiobByIdEtCS = inscriptionRepository
				.findCurrentAnnneInscriptiobCSByIdEt(idEt);

		findCurrentAnnneInscriptiobByIdEt.addAll(findCurrentAnnneInscriptiobByIdEtCJ);
		findCurrentAnnneInscriptiobByIdEt.addAll(findCurrentAnnneInscriptiobByIdEtCS);

		return findCurrentAnnneInscriptiobByIdEt;
	}

	public List<String> getOptionForEtudiant(String idEt) {

		List<String> getOptionForEtudiant = new ArrayList<String>();

		List<String> getOptionForEtudiantCJ = etudiantRepository.getOptionForEtudiant(idEt);
		List<String> getOptionForEtudiantCS = etudiantCSRepository.getOptionForEtudiantCS(idEt);

		getOptionForEtudiant.addAll(getOptionForEtudiantCJ);
		getOptionForEtudiant.addAll(getOptionForEtudiantCS);

		return getOptionForEtudiant;
	}

	@GetMapping(value = "/getListEtudiantbyOption")
	public ResponseEntity<?> getListEtudiantbyOption(@RequestParam("code") String codeOption) {
		List<Object[]> AllTerminalStudent = new ArrayList<Object[]>();

		List<StudentDetails> StudentDetailsList = new ArrayList<StudentDetails>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<String> strings = mapper.readValue(decode(codeOption), List.class);
			for (String C : strings) {

				// System.out.println("''''''''''''''''''''''''> Enacdrants Etudiants 1: " + C);
				String Option = C.replaceFirst("_[0-9][0-9]", "");

				List<Object[]> StudentList = getAllTerminalStudent(Option);
				// oif
				// List<Object[]> StudentList = etudiantRepository.getEtudiantTerminal(Option);

				AllTerminalStudent.addAll(StudentList);
				// System.out.println("''''''''''''''''''''''''> Enacdrants Etudiants 2: " +
				// AllTerminalStudent.size());

			}

			if (AllTerminalStudent.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			// System.out.println("''''''''''''''''''''''''> Enacdrants Etudiants 3");

			for (int i = 0; i < AllTerminalStudent.size(); i++) {

				int valeur = Integer
						.parseInt(findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]).get(0)) + 1;
				// oif
				// int valeur =
				// Integer.parseInt(inscriptionRepository.findCurrentAnnneInscriptiobByIdEt((String)
				// AllTerminalStudent.get(i)[0]).get(0)) + 1;

				// System.out.println("$$$$$$$$$$$$$$$$$$$$$--PS--$$$$$$$$$$$$$$$$$$$$$$$ ID: "
				// + (String) AllTerminalStudent.get(i)[0]);
				System.out.println("$$$$$$$$$$$$$$$$$$$$$--PS--$$$$$$$$$$$$$$$$$$$$$$$ Class: "
						+ getOptionForEtudiant((String) AllTerminalStudent.get(i)[0]).get(0));

				StudentDetails SNF = new StudentDetails((String) AllTerminalStudent.get(i)[0],
						(String) AllTerminalStudent.get(i)[1], (String) AllTerminalStudent.get(i)[2],
						(String) AllTerminalStudent.get(i)[3], (String) AllTerminalStudent.get(i)[4],

						getOptionForEtudiant((String) AllTerminalStudent.get(i)[0]).get(0),
						// oif
						// etudiantRepository.getOptionForEtudiant((String)
						// AllTerminalStudent.get(i)[0]).get(0),

						findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]).get(0) + " - "
								+ String.valueOf(valeur));
				// oif
				// inscriptionRepository.findCurrentAnnneInscriptiobByIdEt((String)
				// AllTerminalStudent.get(i)[0]).get(0) + " - " + String.valueOf(valeur));

				StudentDetailsList.add(SNF);

			}
			// System.out.println("''''''''''''''''''''''''> Enacdrants Etudiants 4: " +
			// StudentDetailsList.size());

			return new ResponseEntity<>(StudentDetailsList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

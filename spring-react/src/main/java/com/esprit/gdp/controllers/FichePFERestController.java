package com.esprit.gdp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.gdp.dto.StudentConvFRDto;
import com.esprit.gdp.dto.StudentDetails;
import com.esprit.gdp.dto.StudentForSoutenance;
import com.esprit.gdp.dto.Visitedto;
import com.esprit.gdp.files.ConventionEN_PDF;
import com.esprit.gdp.files.ConventionFR_PDF;
import com.esprit.gdp.files.ConventionTN_PDF;
import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.EntrepriseAccueil;
import com.esprit.gdp.models.EvaluationStage;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.RdvSuiviStage;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.models.VisiteStagiaire;
import com.esprit.gdp.repository.AvenantRepository;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.EntrepriseAccueilRepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.RDVRepository;
import com.esprit.gdp.repository.VisiteStagiareRepository;
import com.esprit.gdp.services.EncadrementPFEService;
import com.esprit.gdp.services.UtilServices;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "https://pfe.esprit.tn")
@RequestMapping(value = "/api/encadrement")
public class FichePFERestController {

	@Autowired
	private EncadrementPFEService encadrementPFEService;

	@Autowired
	private VisiteStagiareRepository visiteStagiareRepository;

	@Autowired
	private AvenantRepository avenantRepository;

	@Autowired
	private EntrepriseAccueilRepository entrepriseRepository;

	@Autowired
	private CodeNomenclatureRepository codeNomenclatureRepository;

	@Autowired
	private RDVRepository rDVRepository;

	@Autowired
	private UtilServices utilServices;

	@Autowired
	ConventionRepository conventionRepository;

	@Autowired
	OptionRepository optionRepository;

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
		String studentDepartment = utilServices.findDepartmentByClassForConv(studentClasse);

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

		Path patha = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(patha));

		// serviceStageService.UpdateConventionState(idEt, date.substring(0,
		// 19).replace("T", " "));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

		/*************************************************************************************************/
		// System.out.println("-------------Download---------------------> fileName: " +
		// filePath);
		//
		// File file = new File(filePath);
		//
		// String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,
		// filePath.length());
		//
		// HttpHeaders header = new HttpHeaders();
		// header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
		// fileName);
		// header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		// header.add("Pragma", "no-cache");
		// header.add("Expires", "0");
		//
		// Path path = Paths.get(file.getAbsolutePath());
		// ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		//
		// // serviceStageService.UpdateConventionState(idEt, date.substring(0,
		// 19).replace("T", " "));
		//
		// return ResponseEntity.ok()
		// .headers(header)
		// .contentLength(file.length())
		// .contentType(MediaType.parseMediaType("application/octet-stream"))
		// .body(resource);
	}

	@GetMapping("/downloadFD/{idEt}/{dateConvention}")
	public ResponseEntity downloadStudentPVFormedDate(@PathVariable String idEt, @PathVariable String dateConvention)
			throws IOException {

		Optional<Convention> conv = conventionRepository.getConventionByIdFD(idEt, dateConvention);

		System.out.println("-----> Convention: " + conv.get().getAddress());

		Date dat = new Date();
		String studentFullName = utilServices.findStudentFullNameById(idEt);

		String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
		String studentOption = utilServices.findOptionByClass(studentClasse,
				optionRepository.listOptionsByYear("2021"));
		String studentDepartment = utilServices.findDepartmentByClassForConv(studentClasse);

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

		Path patha = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(patha));

		// serviceStageService.UpdateConventionState(idEt, date.substring(0,
		// 19).replace("T", " "));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

		/*************************************************************************************************/
		// System.out.println("-------------Download---------------------> fileName: " +
		// filePath);
		//
		// File file = new File(filePath);
		//
		// String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,
		// filePath.length());
		//
		// HttpHeaders header = new HttpHeaders();
		// header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
		// fileName);
		// header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		// header.add("Pragma", "no-cache");
		// header.add("Expires", "0");
		//
		// Path path = Paths.get(file.getAbsolutePath());
		// ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		//
		// // serviceStageService.UpdateConventionState(idEt, date.substring(0,
		// 19).replace("T", " "));
		//
		// return ResponseEntity.ok()
		// .headers(header)
		// .contentLength(file.length())
		// .contentType(MediaType.parseMediaType("application/octet-stream"))
		// .body(resource);
	}

	@GetMapping("/download")
	public ResponseEntity downloadAvenantFile(@RequestParam("fileName") String fileName) throws IOException {

		String decodedFN = utilServices.decodeEncodedValue(fileName);
		
		System.out.println("----------------> 1 " + fileName);
		System.out.println("----------------> 2 " + decodedFN);
		
		String MIFile = decodedFN.replace("/", "\\");
		File file = new File(MIFile);
		
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Download");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path patha = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(patha));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@GetMapping("/downloadPDF")
	public ResponseEntity downloadFileFromLocal(@RequestParam("fileName") String fileName) {
		
		String decodedFN = utilServices.decodeEncodedValue(fileName);
		
		System.out.println("----------------> 1 " + fileName);
		System.out.println("----------------> 2 " + decodedFN);
		
		String MIFile = decodedFN.replace("/", "\\");
		File file = new File(MIFile);
		
		Path path = Paths.get(file.getAbsolutePath());
		UrlResource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/downloadPDF1")
	public ResponseEntity downloadFileFromLocal1(@RequestParam("fileName") String fileName) {
		
		String fn = utilServices.decodeEncodedValue(fileName);
		System.out.println("-------------------------- DOWNLOAD 13.11 1.1 : " + fileName);
		System.out.println("-------------------------- DOWNLOAD 13.11 1.2 : " + fn);
		Path path = Paths.get(fn);
		UrlResource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/downloadPDF2")
	public ResponseEntity downloadFileFromLocal2(@RequestParam("fileName") String fileName) {
		
		String fn = utilServices.decodeEncodedValue(fileName);
		System.out.println("-------------------------- DOWNLOAD 13.11 2.1 : " + fileName);
		System.out.println("-------------------------- DOWNLOAD 13.11 2.2 : " + fn);
		Path path = Paths.get(fn);
		UrlResource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/downloadPDF3")
	public ResponseEntity downloadFileFromLocal3(@RequestParam("fileName") String fileName) {
		
		//String fn = utilServices.decodeEncodedValue(fileName);
		System.out.println("-------------------------- DOWNLOAD 13.11 3.1 : " + fileName);
		Path path = Paths.get(fileName);
		UrlResource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/GetListVisitbyEtudiant")
	public ResponseEntity<List<VisiteStagiaire>> getListVisitbyEtudiant(@RequestParam("dateFiche") String dateFiche,
			@RequestParam("idET") String idEt) {

		try {
			List<VisiteStagiaire> listVisiteStagiairebyE = visiteStagiareRepository
					.getVisiteStagiarebyEtudiant(dateFiche.substring(0, 19).replace("T", " "), idEt);

			if (listVisiteStagiairebyE.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listVisiteStagiairebyE, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/StudentList/{idEns}")
	public ResponseEntity<?> getEtudiantEncadrés(@PathVariable("idEns") String idEns) {
		try {
			System.out.println("-------> StudentSupervision 0");
			List<StudentDetails> StudentList = encadrementPFEService.getEtudiantEncadrésSer(idEns);
			System.out.println("-------> StudentSupervision 4: " + StudentList.size());

			for (StudentDetails s : StudentList) {
				System.out.println("-------> UNIT BEFORE: " + s.getAnneeDebInscription());
			}

			// .thenComparing(Comparator.comparingInt(StudentCJ::getID).reversed())
			// StudentList.sort(Comparator.comparing(StudentDetails::getAnneeDebInscription).reversed());
			// list.sort(Comparator.comparing(StudentCJ::getAge).reversed());
			StudentList.sort(Comparator.comparing(StudentDetails::getAnneeDebInscription).reversed());

			// for(StudentDetails s : StudentList)
			// {
			// System.out.println("---------------------------> UNIT AFTER 1: " +
			// s.getAnneeDebInscription());
			// }
			//
			// StudentList.sort(Comparator.comparing(StudentDetails::getAnneeDebInscription));
			//
			// for(StudentDetails s : StudentList)
			// {
			// System.out.println("---------------------------> UNIT AFTER 2: " +
			// s.getAnneeDebInscription());
			// }

			if (StudentList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(StudentList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/avenantList")
	public ResponseEntity<List<Avenant>> getavenantListbyIdEt(@RequestParam("idET") String idET) {
		try {
			List<Avenant> AvenantList = avenantRepository.findAvenantByIdEt(idET);
			if (AvenantList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AvenantList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/entreprisebyEt")
	public ResponseEntity<List<EntrepriseAccueil>> getentreprisebyEt(@RequestParam("idET") String idET) {
		try {
			List<EntrepriseAccueil> EntrepriseAccueilList = entrepriseRepository.getEntreprisebyEt(idET);
			if (EntrepriseAccueilList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(EntrepriseAccueilList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/GetEtatfichePFE", method = RequestMethod.GET, produces = "application/json")
	public Map<String, String> getEtatFiche(@RequestParam("etat") String etat) {

		String etatfichePFE = codeNomenclatureRepository.findEtatFiche(etat);
		return Collections.singletonMap("etat", etatfichePFE);

	}

	@GetMapping("/GetListFichebyEt/{idET}")
	public ResponseEntity<List<FichePFE>> getListFichebyEtudiant(@PathVariable("idET") String idET) {

		try {
			List<FichePFE> listfichebyEt = encadrementPFEService.getFichesbyEtudiant(idET);

			if (listfichebyEt.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listfichebyEt, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/GetListConventionbyEt/{idET}")
	public ResponseEntity<List<Convention>> getListConventionbyEtudiant(@PathVariable("idET") String idET) {

		try {
			List<Convention> listConventionbyEt = encadrementPFEService.getConventionsbyEtudiant(idET);

			if (listConventionbyEt.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listConventionbyEt, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/saisiEvalEncadrantPedaNote", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EvaluationStage> SaisiEncadrantPedaNote(@RequestBody EvaluationStage EvalStage) {
		return new ResponseEntity<>(encadrementPFEService.SaisiEncadrantPedaNote(EvalStage), HttpStatus.OK);
	}

	@PutMapping(value = "/updateEvalEncadrantPedaNote", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EvaluationStage> updateEvalEncadrantPedaNote(@RequestBody EvaluationStage EvalStage,
			@RequestParam("idET") String idET, @RequestParam("dateFiche") String dateFiche,
			@RequestParam("code") String code) {
		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new ResponseEntity<>(
				encadrementPFEService.UpdateEval(EvalStage, idET, dateFiche.substring(0, 19).replace("T", " "), code),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/GetTypeNote", method = RequestMethod.GET, produces = "application/json")
	public Map<String, String> getTypeNote(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date dateFiche,
			@RequestParam("code") String code) {

		String typeNote = encadrementPFEService.getTypeNote(idET, dateFiche, code);
		return Collections.singletonMap("etat", typeNote);

	}

	/*
	 * @GetMapping("/EvaluationStageListbyEt") public
	 * ResponseEntity<List<EvaluationStage>>
	 * getEvaluationStagebyEt(@RequestParam("idET") String idET,
	 * 
	 * @RequestParam("dateFiche") String dateFiche) { try { DateFormat dateFormata =
	 * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); List<EvaluationStage>
	 * EvaluationStageList = encadrementPFEService.getEvaluationStagebyEt(idET,
	 * dateFiche.substring(0, 19).replace("T", " ")); if
	 * (EvaluationStageList.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 * 
	 * return new ResponseEntity<>(EvaluationStageList, HttpStatus.OK); } catch
	 * (Exception e) {
	 * 
	 * return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@DeleteMapping(value = "/deteleEvaluation", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void deteleEvaluation(@RequestParam("idET") String idET, @RequestParam("dateFiche") String dateFiche,
			@RequestParam("code") String code) {
		encadrementPFEService.deleteEvaluationStage(idET, dateFiche.substring(0, 19).replace("T", " "), code);

	}

	@PostMapping(path = "/planVisiteStagiaire", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<VisiteStagiaire> PlanVisiteStagiaire(@RequestBody VisiteStagiaire Visite) {
		VisiteStagiaire v = encadrementPFEService.AddVisiteForStagiaire(Visite);
		// StudentCJ S =
		// encadrementPFEService.getStudentbyId(v.getVisiteStagiairePK().getFichePFEPK().getIdEt());
		// Teacher T =
		// encadrementPFEService.getEnseigantEncadrésbyET(v.getVisiteStagiairePK().getFichePFEPK().getIdEt());
		// S.getAdressemailesp(),
		// utilServices.sendMail( "Intern visit", "oumeima.ibnelfekih@esprit.tn",
		// "Your supervisor has planned an internship visit for you, don't forget to
		// consult the plateform for more informations");
		// T.getMailEns()
		// utilServices.sendMail("Intern visit", "oumeima.ibnelfekih@esprit.tn",
		// "You have planned a trainee visit to " + S.getNomet());

		return new ResponseEntity<>(v, HttpStatus.OK);
	}

	@PutMapping(value = "/updateVisiteStagiaire", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<VisiteStagiaire> updateVisiteStagiaire(@RequestBody VisiteStagiaire VisiteStagiaire,
			@RequestParam("idET") String idEt, @RequestParam("dateFiche") String dateFiche,
			@RequestParam("dateVisite") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateVisite) {
		System.out.println("dateVisite" + dateVisite);
		DateFormat dateFormata2 = new SimpleDateFormat("dd-MM-yyyy");
		// Date d =new SimpleDateFormat("yyyy-MM-dd").parse(dateVisite);

		System.out.println("dateVisite" + dateFormata2.format(dateVisite));

		return new ResponseEntity<>(encadrementPFEService.UpdateVisiteForStagiaire(VisiteStagiaire,
				dateFormata2.format(dateVisite), dateFiche.substring(0, 19).replace("T", " "), idEt), HttpStatus.OK);
	}

	@GetMapping(value = "/getvisite")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Optional<VisiteStagiaire>> getvisite(@RequestParam("idET") String idEt,
			@RequestParam("dateFiche") String dateFiche,
			@RequestParam("dateVisite") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateVisite) {

		DateFormat dateFormata2 = new SimpleDateFormat("dd-MM-yyyy");

		if (visiteStagiareRepository.getVisiteStagiarebyId(dateFormata2.format(dateVisite),
				dateFiche.substring(0, 19).replace("T", " "), idEt) == null) {
			return new ResponseEntity("{}", HttpStatus.OK);
		} else
			return new ResponseEntity<Optional<VisiteStagiaire>>(
					visiteStagiareRepository.getVisiteStagiarebyId(dateFormata2.format(dateVisite),
							dateFiche.substring(0, 19).replace("T", " "), idEt),
					HttpStatus.OK);
	}

	@PutMapping(value = "/updateVisiteDate", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<VisiteStagiaire> updateVisiteDate(
			@RequestParam("newDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date newDate,
			@RequestParam("idET") String idEt, @RequestParam("dateFiche") String dateFiche,
			@RequestParam("dateVisite") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateVisite) {

		DateFormat dateFormata2 = new SimpleDateFormat("dd-MM-yyyy");

		encadrementPFEService.UpdateVisiteDate(newDate, dateFormata2.format(dateVisite),
				dateFiche.substring(0, 19).replace("T", " "), idEt);
		return new ResponseEntity<VisiteStagiaire>(visiteStagiareRepository
				.getVisiteStagiarebyId(dateFormata2.format(newDate), dateFiche.substring(0, 19).replace("T", " "), idEt)
				.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "/GetEtatVisiteStagiaire", method = RequestMethod.GET, produces = "application/json")
	public Map<String, String> getEtatVisiteStagiaire(@RequestParam("idET") String idEt,
			@RequestParam("dateFiche") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date dateFiche,
			@RequestParam("dateVisite") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateVisite) {

		String etatVisite = encadrementPFEService.getEtatVisite(dateVisite, dateFiche, idEt);
		return Collections.singletonMap("etat", etatVisite);

	}

	@GetMapping("/GetListVisitbyEns/{idEns}")
	public ResponseEntity<?> getListVisitbyEns(@PathVariable("idEns") String idEns) {

		try {
			List<VisiteStagiaire> listVisiteStagiairebyEns = encadrementPFEService.getVisiteByEns(idEns);
			List<Visitedto> listVisiteStagiairebyEnsF = new ArrayList<Visitedto>();
			for (VisiteStagiaire V : listVisiteStagiairebyEns) {
				String studentFullName = utilServices
						.findStudentFullNameById(V.getVisiteStagiairePK().getFichePFEPK().getConventionPK().getIdEt());

				System.out.println("------------> SARS: " + studentFullName);

				Visitedto VV = new Visitedto(V.getVisiteStagiairePK().getFichePFEPK().getConventionPK().getIdEt(),
						V.getVisiteStagiairePK().getFichePFEPK().getDateDepotFiche(),
						V.getVisiteStagiairePK().getDateVisite(), V.getTypeVisite(), V.getLieuVisite(),
						V.getHeureDebut(), V.getHeureFin(), V.getObservation(), studentFullName);
				listVisiteStagiairebyEnsF.add(VV);
			}

			listVisiteStagiairebyEnsF.sort(Comparator.comparing(Visitedto::getDateVisite).reversed());

			if (listVisiteStagiairebyEnsF.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listVisiteStagiairebyEnsF, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// public ResponseEntity<List<VisiteStagiaire>>
	// getListVisitbyEns(@PathVariable("idEns") String idEns) {
	//
	// try {
	// List<VisiteStagiaire> listVisiteStagiairebyEns =
	// encadrementPFEService.getVisiteByEns(idEns);
	//
	// if (listVisiteStagiairebyEns.isEmpty()) {
	// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	// }
	// return new ResponseEntity<>(listVisiteStagiairebyEns, HttpStatus.OK);
	// } catch (Exception e) {
	//
	// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	// }

	@DeleteMapping(value = "/deleteVisite", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void deteleVisit(@RequestParam("idET") String idEt, @RequestParam("dateFiche") String dateFiche,
			@RequestParam("dateVisite") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateVisite) {

		DateFormat dateFormata2 = new SimpleDateFormat("dd-MM-yyyy");

		encadrementPFEService.deleteVisit(dateFormata2.format(dateVisite), dateFiche.substring(0, 19).replace("T", " "),
				idEt);
	}

	/**********************
	 * Rendez vous de suivi
	 *************************************/

	@PostMapping(path = "/planRendezVous", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RdvSuiviStage> planRendezVous(@RequestBody RdvSuiviStage RDV) {
		RdvSuiviStage r = encadrementPFEService.AddRDv(RDV);
		StudentCJ S = encadrementPFEService
				.getStudentbyId(r.getRdvSuiviStagePK().getFichePFEPK().getConventionPK().getIdEt());
		Teacher T = encadrementPFEService
				.getEnseigantEncadrésbyET(r.getRdvSuiviStagePK().getFichePFEPK().getConventionPK().getIdEt());
		// S.getAdressemailesp(),
		// utilServices.sendMail("Rendez Vous de Suivi", "oumeima.ibnelfekih@esprit.tn",
		// 26-08BACK
		// "Votre encadrant vous a plannifier un rendez vous de suivi Ã 
		// "+r.getHeureDebut() +". Ce rendez vous va Ãªtre Ã  "+r.getLieuRDV() );
		// T.getMailEns(),
		// utilServices.sendMail("Rendez Vous de Suivi", "oumeima.ibnelfekih@esprit.tn",
		// "Vous avez plannifier un rendez vous de suivi pour " + S.getNomet() +"
		// "+S.getPrenomet());

		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@GetMapping("/RdvSuiviStageListbyEt")
	public ResponseEntity<List<RdvSuiviStage>> getRdvSuiviStagebyEt(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") String dateFiche) {
		try {
			DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<RdvSuiviStage> RdvSuiviStageList = encadrementPFEService.getRdvSuiviStagebyEt(idET,
					dateFiche.substring(0, 19).replace("T", " "));
			if (RdvSuiviStageList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(RdvSuiviStageList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateRdvSuiviStage", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RdvSuiviStage> updateRdvSuiviStage(@RequestBody RdvSuiviStage RDV,
			@RequestParam("idET") String idEt, @RequestParam("dateFiche") String dateFiche,
			@RequestParam("dateSaisiRDV") String dateSaisiRDV) {
		encadrementPFEService.UpdateRdvSuiviStage(RDV, dateSaisiRDV.substring(0, 19).replace("T", " "),
				dateFiche.substring(0, 19).replace("T", " "), idEt);
		return new ResponseEntity<RdvSuiviStage>(
				rDVRepository.getRdvSuiviStagebyId(dateSaisiRDV.substring(0, 19).replace("T", " "),
						dateFiche.substring(0, 19).replace("T", " "), idEt),
				HttpStatus.OK);
	}

	@PutMapping(value = "/updateRdvSuiviStageInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RdvSuiviStage> updateRdvSuiviStageinfo(@RequestBody RdvSuiviStage RDV,
			@RequestParam("idET") String idEt, @RequestParam("dateFiche") String dateFiche,
			@RequestParam("dateSaisiRDV") String dateSaisiRDV) {
		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		encadrementPFEService.UpdateRdvSuiviStageinfo(RDV, dateSaisiRDV.substring(0, 19).replace("T", " "),
				dateFiche.substring(0, 19).replace("T", " "), idEt);
		return new ResponseEntity<RdvSuiviStage>(
				rDVRepository.getRdvSuiviStagebyId(dateSaisiRDV.substring(0, 19).replace("T", " "),
						dateFiche.substring(0, 19).replace("T", " "), idEt),
				HttpStatus.OK);
	}

	@GetMapping("/GetlistStudentForSoutenance")
	public ResponseEntity<List<StudentForSoutenance>> getStudentForSoutenance(@RequestParam("idEns") String idEns) {

		try {
			List<StudentForSoutenance> listStudentForSoutenance = encadrementPFEService.getStudentForSoutenance(idEns);

			if (listStudentForSoutenance.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listStudentForSoutenance, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/****************** Evaluation New form **********************/

	@PostMapping(path = "/saisiEncadrantPedaEval", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> SaisiEncadrantPedaEval(@RequestBody List<EvaluationStage> EvalStage) {

		return new ResponseEntity<>(encadrementPFEService.SaisiEncadrantPedaEvaluation(EvalStage), HttpStatus.OK);
	}

	@GetMapping("/EncadrantPedaEvalList")
	public ResponseEntity<List<EvaluationStage>> getEncadrantPedaEvalList(@RequestParam("idET") String idET,
			@RequestParam("dateFiche") String dateFiche) {
		try {

			List<EvaluationStage> EvaluationStageList = encadrementPFEService.getEvaluationStagebyEt(idET,
					dateFiche.substring(0, 19).replace("T", " "));
			if (EvaluationStageList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(EvaluationStageList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

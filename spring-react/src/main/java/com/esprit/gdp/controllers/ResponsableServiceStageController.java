package com.esprit.gdp.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.esprit.gdp.dto.*;
import com.esprit.gdp.files.*;
import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.repository.*;
import com.esprit.gdp.services.ServiceStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.esprit.gdp.services.UtilServices;

//@RestController
//@CrossOrigin(origins = "http://193.95.99.194:8081")
//@RequestMapping("/api/respServStg")

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/respServStg")
public class ResponsableServiceStageController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	UtilServices utilServices;

	@Autowired
	OptionRepository optionRepository;
	
	@Autowired
	OptionStudentALTRepository optionStudentALTRepository;

	@Autowired
	ServiceStageService serviceStageService;

	@Autowired
	GrilleAcademicEncadrantRepository grilleAcademicEncadrantRepository;

	@Autowired
	CodeNomenclatureRepository codeNomenclatureRepository;

	/******************************************************
	 * Methods
	 ******************************************************/

	// @GetMapping("/downloadDemandeStage/{idEt}")
	// public ResponseEntity downloadDemandeStage(@PathVariable String idEt) throws
	// IOException
	// {
	//
	// System.out.println("------------------> idEt: " + idEt);
	//
	// StudentDemandeStageDto studentDemStg =
	// utilServices.findStudentDemandeStgByStudentId(idEt);
	//
	// // String path = "C:/ESP-DOCS/Conventions/" + studentFullName + "-" +
	// dat.getTime() + ".pdf";
	// String DSPath = "C:\\ESP-DOCS\\";
	// String DSName = "Demande Stage " + studentDemStg.getFullName() + ".pdf";
	// String DSFile = DSPath + DSName;
	//
	// new DemandeStage_PDF(DSFile, studentDemStg.getFullName(),
	// studentDemStg.getClasse());
	//
	// File file = new File(DSFile);
	//
	// HttpHeaders header = new HttpHeaders();
	// header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
	// DSName);
	// header.add("Cache-Control", "no-cache, no-store, must-revalidate");
	// header.add("Pragma", "no-cache");
	// header.add("Expires", "0");
	//
	// // To Got Name Of File With Synchro
	// header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
	// HttpHeaders.CONTENT_DISPOSITION);
	//
	// Path patha = Paths.get(file.getAbsolutePath());
	// ByteArrayResource resource = new
	// ByteArrayResource(Files.readAllBytes(patha));
	//
	// return ResponseEntity.ok()
	// .headers(header)
	// .contentLength(file.length())
	// .contentType(MediaType.parseMediaType("application/octet-stream"))
	// .body(resource);
	//
	// }

	@GetMapping("/listStudentsByDept/{codeDept}")
	public List<EncadrementStatusExcelDto> listStudentsByDept(@PathVariable String codeDept) {
		// System.out.println("--PC----------------> pcMail: " + codeDept);
		List<String> lowerOpts = utilServices.findStudentsByDept(codeDept);

		List<EncadrementStatusExcelDto> ess = new ArrayList<EncadrementStatusExcelDto>();
		for (String opt : lowerOpts) {
			ess.addAll(studentRepository.findEncadrementStatusCJByOption(opt));
			ess.addAll(studentRepository.findEncadrementStatusCSByOption(opt));

			if (opt.equalsIgnoreCase("alinfo")) {
				ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
			}
		}

		for (EncadrementStatusExcelDto es : ess) {
			// es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadFullName()));
			// System.out.println("--PC----------------> SARIA: " +
			// teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()));
			// es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()));

			if (teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()) == null) {
				es.setAcademicEncadFullName("--");
			} else {
				es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()));
			}
		}

		ess.sort(Comparator.comparing(EncadrementStatusExcelDto::getStudentClasse)
				.thenComparing(EncadrementStatusExcelDto::getStudentFullName));

		return ess;

	}

	@GetMapping("/downloadAllEncadrementStatus")
	public ResponseEntity downloadAllEncadrementStatus() throws IOException {

		System.out
				.println("---------------------------------> START Treatment Download Etat Encdrement: " + new Date());

		List<EncadrementRSSStatusExcelDto> ess = new ArrayList<EncadrementRSSStatusExcelDto>();

		ess.addAll(studentRepository.findEncadrementStatusCJ());
		// System.out.println("-------> Step 1: " + new Date());
		ess.addAll(studentRepository.findEncadrementStatusCJALT());
		// System.out.println("-------> Step 2: " + new Date());
		ess.addAll(studentRepository.findEncadrementStatusCS());
		// System.out.println("-------> Step 3: " + new Date());

		for (EncadrementRSSStatusExcelDto es : ess) {

			// System.out.println("---> UNIT: " + es.getAcademicEncadId() + " - " +
			// teacherRepository.findTeacherFullNameById(es.getAcademicEncadId()));

			es.setStudentOption(
					utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear("2021")));
			es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadId()));
			es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadId()));
		}
		// System.out.println("-------> Step 4: " + new Date());

		ess.sort(Comparator.comparing(EncadrementRSSStatusExcelDto::getAcademicEncadFullName)
				.thenComparing(EncadrementRSSStatusExcelDto::getStudentFullName));
		// System.out.println("-------> Step 5: " + new Date());

		/************************************************************************************/

		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Encadrements Global.xls";
		String PSFile = PSPath + PSName;

		new EtatEncadrementsGlobal_Excel(ess, PSFile);

		File file = new File(PSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		// To Got Name Of File With Synchro
		header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		System.out.println("---------------------------------> END Treatment Download Etat Encdrement: " + new Date());

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	

	@GetMapping("/downloadAllEncadrementStatusByYear/{selectedYear}")
	public ResponseEntity downloadAllEncadrementStatusByYear(@PathVariable String selectedYear) throws IOException
	{
		
		System.out.println("---------------------------------> START Treatment Download Etat Encdrement: " + new Date());
	
		List<EncadrementRSSStatusExcelDto> ess = new ArrayList<EncadrementRSSStatusExcelDto>();
		
		ess.addAll(studentRepository.findEncadrementStatusCJALTByYear(selectedYear));
		System.out.println("-------> Step 1: " + new Date());
		ess.addAll(studentRepository.findEncadrementStatusCSByYear(selectedYear));
		System.out.println("-------> Step 3: " + new Date());
		
		for(EncadrementRSSStatusExcelDto es : ess)
		{
			
			System.out.println("----> UNIT: " + es.getStudentId() + " - " + es.getStudentFullName());
			if(es.getStudentClasse().contains("4ALINFO"))
			{
				es.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(es.getStudentId(), selectedYear));
				System.out.println("--> 2.1");
			}
			else
			{
				es.setStudentOption(utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear(selectedYear)).replace("_01", ""));
				System.out.println("--> 2.2");
			}
			
			// Got Option
			// es.setStudentOption(utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear(selectedYear)));
			
			es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadId()));
			es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadId()));
		}
		System.out.println("-------> Step 4: " + new Date());
		
		ess.sort(Comparator.comparing(EncadrementRSSStatusExcelDto::getAcademicEncadFullName).thenComparing(EncadrementRSSStatusExcelDto::getStudentFullName));
		System.out.println("-------> Step 5: " + new Date());
		
		/************************************************************************************/
		
		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Encadrements Global.xls";
		String PSFile = PSPath + PSName;
		
		new EtatEncadrementsDetailedByYear_Excel(selectedYear, ess, PSFile);
		
		File file = new File(PSFile);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        
        // To Got Name Of File With Synchro
        header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        System.out.println("---------------------------------> END Treatment Download Etat Encdrement: " + new Date());
        
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}
	

	@GetMapping("/downloadAllExpertiseStatusByYear/{selectedYear}")
	public ResponseEntity downloadAllExpertiseStatusByYear(@PathVariable String selectedYear) throws IOException
	{
		
		System.out.println("----------xx-------------EXP----------> START Treatment Download Etat Encdrement: " + selectedYear);
	
		List<EncadrementRSSStatusExcelDto> ess = new ArrayList<EncadrementRSSStatusExcelDto>();
		
		ess.addAll(studentRepository.findExpertiseStatusCJALTByYear(selectedYear));
		System.out.println("-------> Step 1: " + ess.size());
		ess.addAll(studentRepository.findExpertiseStatusCSByYear(selectedYear));
		System.out.println("-------> Step 3: " + ess.size());
		
		for(EncadrementRSSStatusExcelDto es : ess)
		{
			
			// System.out.println("---> UNIT: " + es.getAcademicEncadId() + " - " + teacherRepository.findTeacherFullNameById(es.getAcademicEncadId()));
			if(es.getStudentClasse().contains("4ALINFO"))
			{
				es.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(es.getStudentId(), selectedYear)+ "_01");
				System.out.println("--> 2.1");
			}
			else
			{
				es.setStudentOption(utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear(selectedYear)));
				System.out.println("--> 2.2");
			}
			
			es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadId()));
			es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadId()));
		}
		System.out.println("-------> Step 4: " + new Date());
		
		ess.sort(Comparator.comparing(EncadrementRSSStatusExcelDto::getAcademicEncadFullName).thenComparing(EncadrementRSSStatusExcelDto::getStudentFullName));
		System.out.println("-------> Step 5: " + new Date());
		
		/************************************************************************************/
		
		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Expertises Global.xls";
		String PSFile = PSPath + PSName;
		
		new EtatExpertisesDetailedByYear_Excel(selectedYear, ess, PSFile);
		
		File file = new File(PSFile);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        
        // To Got Name Of File With Synchro
        header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        System.out.println("---------------------------------> END Treatment Download Etat Encdrement: " + new Date());
        
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}
	

	@GetMapping("/downloadAllEncadrementAndExpertiseStatusByYear/{selectedYear}")
	public ResponseEntity downloadAllEncadrementAndExpertiseStatusByYear(@PathVariable String selectedYear) throws IOException
	{
		
		System.out.println("---------------------------------> START Treatment Download Etat Encdrement: " + new Date());
	
		List<TeacherQuotaEncadrementExpertiseDto> academicEncadrants = teacherRepository.allAcademicEncadrantsAndExperts(selectedYear);
		
		for(TeacherQuotaEncadrementExpertiseDto ae : academicEncadrants)
		{
			ae.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPEAndYear(ae.getIdentifiant(), selectedYear));
			ae.setNbrExpertises(utilServices.findNbrStudentsTrainedByEXPAndYear(ae.getIdentifiant(), selectedYear));
		}
		
		System.out.println("-------> Step 4: " + new Date());
		
		academicEncadrants.sort(Comparator.comparing(TeacherQuotaEncadrementExpertiseDto::getNom));
		System.out.println("-------> Step 5: " + new Date());
		
		/************************************************************************************/
		
		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Encadrements et Expertises.xls";
		String PSFile = PSPath + PSName;
		
		new EtatEncadrementsAndExpertisesByYear_Excel(selectedYear, academicEncadrants, PSFile);
		
		File file = new File(PSFile);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        
        // To Got Name Of File With Synchro
        header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        System.out.println("---------------------------------> END Treatment Download Etat Encdrement: " + new Date());
        
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}
	

	@GetMapping("/downloadAPresidenceAndMembrejuryForSTNStatusByYear/{selectedSession}")
	public ResponseEntity downloadAPresidenceAndMembrejuryForSTNStatusByYear(@PathVariable String selectedSession) throws IOException
	{
		
		String yearFromSession = Integer.toString(Integer.parseInt(selectedSession.substring(selectedSession.length()-4))-1);
		if(
			  selectedSession.contains("Septembre") || selectedSession.contains("Octobre") || 
			  selectedSession.contains("Novembre") || selectedSession.contains("Decembre")
		)
		{
			yearFromSession = selectedSession.substring(selectedSession.length()-4);
		}
		
		System.out.println("-------------- START Treatment État Encadrement: " +  yearFromSession + " - " + selectedSession);
		
		Integer idSession = sessionRepository.findIdSessionByLabelSession(selectedSession);
		
		List<TeacherQuotaPresidenceMembreDto> teachersForSTN = teacherRepository.allTeachersForSTNByYear(yearFromSession);
		
		for(TeacherQuotaPresidenceMembreDto t : teachersForSTN)
		{
			
			t.setNbrPresidences(fichePFERepository.findStudentsIdByJuryPresident(t.getIdentifiant(), idSession).size());
			t.setNbrMembres(fichePFERepository.findStudentsIdByJuryMembre(t.getIdentifiant(), idSession).size());
		}
		
		teachersForSTN.sort(Comparator.comparing(TeacherQuotaPresidenceMembreDto::getNom));
		System.out.println("-------> Step 5: " + new Date());
		
		/************************************************************************************/
		
		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Présidences et Membres de Jury pour Soutennaces.xls";
		String PSFile = PSPath + PSName;
		
		new EtatPresidencesAndMembresBySession_Excel(selectedSession, teachersForSTN, PSFile);
		
		File file = new File(PSFile);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PSName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        
        // To Got Name Of File With Synchro
        header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        System.out.println("---------------------------------> END Treatment Download Etat Encdrement: " + new Date());
        
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}

	@GetMapping("/allOptionsForActivatedYears/{idRSS}")
	public List<String> allOptionsForActivatedYearsByRSS(@PathVariable String idRSS)
	{
		List<String> options = new ArrayList<String>();

		if(idRSS.contains("IT"))
		{
			options = optionRepository.allOptionsForActivatedYears();
			options.replaceAll(s-> s.replace("_01", ""));
			options.remove("CINFO-ARC");options.remove("CINFO-BI");options.remove("CINFO-GL");options.remove("ME21-GC");
			options.remove("ALINFO");options.remove("EE");options.remove("PC");options.remove("SO");options.remove("OG");
			options.remove("CEM");options.remove("MÉCAT");options.remove("OGI");options.remove("GL");
		}
		else if (idRSS.contains("EM"))
		{
			options.add("EM");
		}
		else
		{
			options.add("GC");
		}
		return options;
	}

	@PutMapping(value = "/allValidatedConventionsListByOptionForRSS")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<ConventionsValidatedForRSSDto>> getconventionValidatedListDto(@RequestParam("idRSS") String idRSS, @RequestParam("yearLabel") String yearLabel, @RequestParam("optionLabel") String optionLabel)
	{
		System.out.println("--------------1805-----> HELLO : " + idRSS);

		List<String> idStudents = new ArrayList<String>();
		idStudents.addAll(utilServices.findStudentsByYearAndGroupedOption(yearLabel, optionLabel.toLowerCase()));

		try
		{
			// List<ConventionsValidatedForRSSDto> ConventionList = serviceStageService.getAllConventionsValidatedDtoByRSS(idRSS, idMonth);
			List<ConventionsValidatedForRSSDto> conventionList = serviceStageService.getAllConventionsValidatedDtoByStudentsForRSS(idRSS, idStudents);

			System.out.println("--------------ddddd 3-----> SARS VAL: " + conventionList.size());
			if (conventionList.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(conventionList, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping(value = "/allConventionsListByOptionForRSS")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<ConventionForRSSDto>> getconventionsListDto(@RequestParam("idRSS") String idRSS, @RequestParam("yearLabel") String yearLabel)
	{
		System.out.println("--------------PRIM : " + idRSS + " : " + yearLabel);

		try
		{
			List<ConventionForRSSDto> conventionList = utilServices.findNotTreatedConventionsByYear(yearLabel);

			if (conventionList.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(conventionList, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/allDemandesAnnulationsConventionsListByOptionForRSS")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<ConventionForRSSDto>> getDemandesAnnulationsConventionsListDto(@RequestParam("idRSS") String idRSS, @RequestParam("yearLabel") String yearLabel)
	{
		System.out.println("--------------PRIM : " + idRSS + " : " + yearLabel);

		try
		{
			List<ConventionForRSSDto> conventionList = utilServices.findDemandesAnnulationConventionsByYear(yearLabel);
			// List<ConventionsValidatedForRSSDto> ConventionList = serviceStageService.getAllConventionsValidatedDtoByRSS(idRSS, idMonth);

			if (conventionList.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(conventionList, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/allAvenantsList")
	public ResponseEntity<List<AvenantForRSSDto>> getAllAvenantList(@RequestParam("idRSS") String idRSS, @RequestParam("yearLabel") String yearLabel)
	{
		System.out.println("-------------- Avenant : " + idRSS + " : " + yearLabel);
		try {
			List<AvenantForRSSDto> AvenantList = serviceStageService.findAllAvenantDTOByYear(idRSS, yearLabel);
			if (AvenantList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(AvenantList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateAvenantStatus", produces = MediaType.APPLICATION_JSON_VALUE) // 22052023
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Avenant> updateAvenantState(@RequestParam("idET") String idET,
													  @RequestParam("dateConvention") String dateConvention,
													  @RequestParam("dateAvenant") String dateAvenant)
			throws Exception {

		System.out.println("----------------------------> idET: " + idET);
		System.out.println("----------------------------> dateConvention: " + dateConvention);
		System.out.println("----------------------------> dateAvenant: " + dateAvenant);

		serviceStageService.GenerateAvenant(idET,dateConvention, dateAvenant);
		return new ResponseEntity<>(serviceStageService.UpdateAvenantState(idET, dateConvention, dateAvenant), HttpStatus.OK);
	}

	@GetMapping("/loadValidatedDepots")
	public ResponseEntity<?> loadValidatedDepots(@RequestParam("idRSS") String idRSS, @RequestParam("yearLabel") String yearLabel, @RequestParam("optionLabel") String optionLabel) {
		try {

			System.out.println("--------------1805-----> HELLO : " + idRSS);

			List<String> idStudents = new ArrayList<String>();
			idStudents.addAll(utilServices.findStudentsByYearAndGroupedOption(yearLabel, optionLabel.toLowerCase()));

			/****************************************************************************************************************************************/

			String idServiceStage = null;
			if(idRSS.equalsIgnoreCase("SR-STG-IT4") || idRSS.equalsIgnoreCase("SR-STG-IT2") || idRSS.equalsIgnoreCase("SR-STG-IT1"))
			//if(idServiceStageFULL.startsWith("SR-STG-IT"))
			{
				idServiceStage = "SR-STG-IT";
			}
			else
			{
				idServiceStage = idRSS;
			}

			System.out.println("----------------------> PIKA 1: " + idServiceStage);

			List<DepotFinalDto> plansTravailCJ = fichePFERepository.loadFichesForDepotValByStudents(idStudents);
			// List<ConventionsValidatedForRSSDto> conventionList = serviceStageService.getAllConventionsValidatedDtoByStudentsForRSS(idRSS, idStudents);
			//List<DepotFinalDto> depotsFinal = utilServices.loadPlanTravailByYear(yearLabel, idServiceStage);

			System.out.println("----------------------> PIKA 2: " + plansTravailCJ.size());

			for (DepotFinalDto df : plansTravailCJ) {

				/*
				DepotRapport D = new DepotRapport(F.getIdFichePFE().getConventionPK().getIdEt(),
						S.getNomet(), S.getPrenomet(), F.getPathRapportVersion2(), F.getPathPlagiat(),
						F.getPathAttestationStage(), F.getPathSupplement(),
						codeNomenclatureRepository.findEtatFiche(F.getEtatFiche()),
						codeNomenclatureRepository.findEtatDepot(F.getValidDepot()),
						F.getIdFichePFE().getDateDepotFiche());
				 */

				df.setFullName(utilServices.findStudentFullNameById(df.getIdEt()));
				df.setClasseEt(utilServices.findCurrentClassByIdEt(df.getIdEt()));
				// df.setEtatFiche(codeNomenclatureRepository.findEtatFiche(df.getEtatFiche()));
				// df.setEtatDepot(codeNomenclatureRepository.findEtatDepot(df.getEtatDepot()));
				// df.setEtatDepot();

				System.out.println("--------------> FichePFEPK : " + df.getFichePFEPK().getConventionPK().getIdEt());
				//df.setEtatGrilleEncadrement("NOTYET");

				if(!grilleAcademicEncadrantRepository.findEtatGrilleByFiche(df.getFichePFEPK()).isEmpty())
				{
					if(grilleAcademicEncadrantRepository.findEtatGrilleByFiche(df.getFichePFEPK()).get(0).equalsIgnoreCase("02"))
					{
						df.setEtatGrilleEncadrement("DONE");
					}
					else
					{
						df.setEtatGrilleEncadrement("NOTYET");
					}
				}
				else
				{
					df.setEtatGrilleEncadrement("NOTYET");
				}

			}

			if (plansTravailCJ.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(plansTravailCJ, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/loadNotYetTreatedDepots")
	public ResponseEntity<?> loadNotYetTreatedDepots(@RequestParam("idRSS") String idRSS, @RequestParam("yearLabel") String yearLabel)
	{
		try {

			System.out.println("--------------1805-----> HELLO : " + idRSS);


			/****************************************************************************************************************************************/

			String idServiceStage = null;
			if(idRSS.equalsIgnoreCase("SR-STG-IT4") || idRSS.equalsIgnoreCase("SR-STG-IT2") || idRSS.equalsIgnoreCase("SR-STG-IT1"))
			{
				idServiceStage = "SR-STG-IT";
			}
			else
			{
				idServiceStage = idRSS;
			}

			System.out.println("----------------------> PIKA 1: " + idServiceStage);

			List<DepotFinalDto> depotFinalDtos = utilServices.loadFichesForDepotNotYetTreatedByYear(yearLabel, idServiceStage);

			System.out.println("----------------------> PIKA 2: " + depotFinalDtos.size());

			for (DepotFinalDto df : depotFinalDtos)
			{

				System.out.println("############################################> Date Depot Fiche: " + df.getDateDepotFiche());

				String classeEt = utilServices.findCurrentClassByIdEt(df.getIdEt());

				df.setEtatDepot(codeNomenclatureRepository.findEtatDepot(df.getEtatDepot()));
				df.setFullName(utilServices.findStudentFullNameById(df.getIdEt()));
				df.setClasseEt(classeEt);

				if(!classeEt.contains("4ALINFO"))
				{
					df.setOptionEt(utilServices.findOptionByClass(classeEt, optionRepository.listOptionsByYear(yearLabel)).replace("_01", ""));
				}
				if(classeEt.contains("4ALINFO"))
				{
					df.setOptionEt(optionStudentALTRepository.findOptionByStudentALTAndYear(df.getIdEt(), yearLabel));
				}

				System.out.println("--------------> FichePFEPK : " + df.getFichePFEPK().getConventionPK().getIdEt());

				if(!grilleAcademicEncadrantRepository.findEtatGrilleByFiche(df.getFichePFEPK()).isEmpty())
				{
					if(grilleAcademicEncadrantRepository.findEtatGrilleByFiche(df.getFichePFEPK()).get(0).equalsIgnoreCase("02"))
					{
						df.setEtatGrilleEncadrement("DONE");
					}
					else
					{
						df.setEtatGrilleEncadrement("NOTYET");
					}
				}
				else
				{
					df.setEtatGrilleEncadrement("NOTYET");
				}

			}

			if (depotFinalDtos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(depotFinalDtos, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

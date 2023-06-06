package com.esprit.gdp.controllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.esprit.gdp.models.*;
import com.esprit.gdp.payload.request.PlanTravailRequest;
import com.esprit.gdp.payload.request.SurveyFirstJobRequest;
import com.esprit.gdp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.esprit.gdp.dto.AvenantDateAvDateDebutStageStatusDto;
import com.esprit.gdp.dto.AvenantHistoryDto;
import com.esprit.gdp.dto.ConventionDateConvDateDebutStageStatusDto;
import com.esprit.gdp.dto.ConventionHistoriqueDto;
import com.esprit.gdp.dto.ConventionPaysSecteurDto;
import com.esprit.gdp.dto.EntrepriseAccueilForFichePFEHistDto;
import com.esprit.gdp.dto.EntrepriseSupervisorDto;
import com.esprit.gdp.dto.FichePFEDateDepotReportsEtatReportsDto;
import com.esprit.gdp.dto.FichePFEDto;
import com.esprit.gdp.dto.FichePFEHistoryDto;
import com.esprit.gdp.dto.FichePFETitreDateDepotStatusDto;
import com.esprit.gdp.dto.FunctionnalityDto;
import com.esprit.gdp.dto.ProblematicDto;
import com.esprit.gdp.dto.StudentConvFRDto;
import com.esprit.gdp.dto.StudentDemandeStageDto;
import com.esprit.gdp.dto.StudentDto;
import com.esprit.gdp.dto.StudentHierarchyDto;
import com.esprit.gdp.dto.StudentIdFullNameDto;
import com.esprit.gdp.dto.StudentJustificatifStageDto;
import com.esprit.gdp.dto.StudentMandatoryInternshipDto;
import com.esprit.gdp.dto.StudentSTNGradDto;
import com.esprit.gdp.dto.StudentTimelineDto;
import com.esprit.gdp.dto.TechnologyDto;
import com.esprit.gdp.dto.TraitementFicheDto;
import com.esprit.gdp.dto.TraitementFicheHistoryDto;
import com.esprit.gdp.dto.TraitementFicheTypeEtatDto;
import com.esprit.gdp.files.ConventionEN_PDF;
import com.esprit.gdp.files.ConventionFR_PDF;
import com.esprit.gdp.files.ConventionTN_PDF;
import com.esprit.gdp.files.DemandeStage_PDF;
import com.esprit.gdp.files.JustificatifStage_PDF;
import com.esprit.gdp.files.LettreAffectation_PDF;
import com.esprit.gdp.files.MandatoryInternship_PDF;
import com.esprit.gdp.files.PlanTravail_PDF;
import com.esprit.gdp.services.UtilServices;

//@RestController
//@CrossOrigin(origins = "http://193.95.99.194:8081")
//@RequestMapping("/api/student")

@RestController
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/student")
public class StudentController {

	@Autowired
	EncadrantEntrepriseRepository responsibleRepository;

	@Autowired
	ConventionRepository conventionRepository;

	@Autowired
	AvenantRepository avenantRepository;

	@Autowired
	TechnologieRepository technologyRepository;

	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	FunctionnalityRepository functionnalityRepository;

	@Autowired
	ProblematicRepository problematicRepository;

	@Autowired
	TraitementFicheRepository traitementFicheRepository;

	@Autowired
	CodeNomenclatureRepository codeNomenclatureRepository;

	@Autowired
	ResponsableServiceStageRepository responsableServiceStageRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	UtilServices utilServices;

	@Autowired
	OptionRepository optionRepository;

	@Autowired
	EncadrantEntrepriseRepository encadrantEntrepriseRepository;

	@Autowired
	EvaluationEngineeringTrainingRepository evalEngTrRepository;

	@Autowired
	SurveyFirstJobRepository surveyFirstJobRepository;

	/******************************************************
	 * Methods
	 ******************************************************/

	@GetMapping("/downloadDemandeStage/{idEt}")
	public ResponseEntity downloadDemandeStage(@PathVariable String idEt) throws IOException {

		// System.out.println("------------------> idEt: " + idEt);

		StudentDemandeStageDto studentDemStg = utilServices.findStudentDemandeStgByStudentId(idEt);

		// String path = "C:/ESP-DOCS/Conventions/" + studentFullName + "-" +
		// dat.getTime() + ".pdf";
		String DSPath = "C:\\ESP-DOCS\\";
		String DSName = "Demande Stage " + studentDemStg.getFullName() + ".pdf";
		String DSFile = DSPath + DSName;

		new DemandeStage_PDF(DSFile, studentDemStg.getFullName(), studentDemStg.getClasse());

		File file = new File(DSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + DSName);
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

	@GetMapping("/downloadMandatoryInternship/{idEt}")
	public ResponseEntity downloadMandatoryInternship(@PathVariable String idEt) throws IOException {

		StudentMandatoryInternshipDto studentManInternDto = utilServices
				.findStudentMandatoryInternshipByStudentId(idEt);

		String MIPath = "C:\\ESP-DOCS\\";
		String MIName = "Mandatory Internship " + studentManInternDto.getFullName() + ".pdf";
		String MIFile = MIPath + MIName;

		new MandatoryInternship_PDF(MIFile, studentManInternDto.getFullName(), studentManInternDto.getBirthDay());

		File file = new File(MIFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + MIName);
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

	@GetMapping("/downloadJustificatifStage/{idEt}")
	public ResponseEntity downloadJustificatifStage(@PathVariable String idEt) throws IOException {

		StudentJustificatifStageDto studentJustifStgDto = utilServices.findStudentJustificatifStageByStudentId(idEt);

		String JSPath = "C:\\ESP-DOCS\\";
		String JSName = "Justificatif Stage " + studentJustifStgDto.getFullName() + ".pdf";
		String JSFile = JSPath + JSName;

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

		new JustificatifStage_PDF(JSFile, studentJustifStgDto, companyName, dateDebutStage, dateFinStage);

		File file = new File(JSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + JSName);
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

	@GetMapping("/downloadAllFilesTypes/{pathFile}")
	public ResponseEntity downloadAllFilesTypes(@PathVariable String pathFile) throws IOException
	{

		// System.out.println("---------------------> encoded path: " + pathFile);
		String gdFullPath = utilServices.decodeEncodedValue(pathFile);

		// System.out.println("---------------------> decoded path: " + gdFullPath);

		String PTPath = gdFullPath.replace("/", "\\");

		File file = new File(PTPath); //"C:\\ESP\\uploads\\sars essid.docx");

		// String all = "C:\\ESP\\uploads\\sars essid.docx";
		//String fileName = all.substring()

		String fileName = PTPath.substring(15, PTPath.length());

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		// To Got Name Of File With Synchro
		header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		Path patha = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(patha));

		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);

	}

	@GetMapping("/downloadLettreAffectation/{idEt}")
	public ResponseEntity downloadLettreAffectation(@PathVariable String idEt) throws IOException {

		String studentFullName = utilServices.findStudentFullNameById(idEt);
		String department = utilServices.findDepartmentByClass(utilServices.findCurrentClassByIdEt(idEt));
		String companyName = conventionRepository.findCompanyNameByIdEt(idEt).get(0);

		String JSPath = "C:\\ESP-DOCS\\";
		String JSName = "Lettre Affectation " + studentFullName + ".pdf";
		String JSFile = JSPath + JSName;

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

		new LettreAffectation_PDF(JSFile, studentFullName, department, companyName, dateDebutStage, dateFinStage);

		File file = new File(JSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + JSName);
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

	@GetMapping("/downloadConventionStage/{idEt}")
	public ResponseEntity downloadConventionStage(@PathVariable String idEt) throws IOException {

		List<Optional<Convention>> lconvs = conventionRepository.findConventionOPTByIdEt(idEt);
		String studentFullName = utilServices.findStudentFullNameById(idEt);
		String deptLabel = utilServices.findDepartmentByClassForConventionDiplome(utilServices.findCurrentClassByIdEt(idEt));
		String companyLabel = conventionRepository.findCompanyNameByIdEt(idEt).get(0);
		String optionLabel = utilServices.findOptionByClass(utilServices.findCurrentClassByIdEt(idEt),
				optionRepository.listOptionsByYear("2021"));

		String CSPath = "C:\\ESP-DOCS\\";
		String CSName = "Convention Stage " + studentFullName + ".pdf";
		String CSFile = CSPath + CSName;

		if (!lconvs.isEmpty()) {
			Optional<Convention> conv = lconvs.get(0);

			String convCodePays = "--";
			if (conv.get().getEntrepriseAccueilConvention().getPays() != null) {
				convCodePays = conv.get().getEntrepriseAccueilConvention().getPays().getLangueCode();
			}

			if (convCodePays.equalsIgnoreCase("TN")) {
				new ConventionTN_PDF(conv, CSFile, studentFullName, optionLabel, deptLabel);
				// System.out.println("----------------- TN");
			}

			if (convCodePays.equalsIgnoreCase("EN") || convCodePays.equalsIgnoreCase("--")) {
				new ConventionEN_PDF(conv, CSFile, studentFullName, optionLabel, deptLabel);
				// System.out.println("----------------- EN");
			}
			if (convCodePays.equalsIgnoreCase("FR")) {
				StudentConvFRDto scf = utilServices.findStudentConvFRDtoById(idEt);
				new ConventionFR_PDF(conv, CSFile, scf, optionLabel, deptLabel);
				// System.out.println("----------------- FR");
			}

		}

		File file = new File(CSFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + CSName);
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

	@GetMapping("/downloadGanttDiagram/{idEt}")
	public ResponseEntity downloadGanttDiagram(@PathVariable String idEt) throws IOException {
		String lastGanttDiagram = fichePFERepository.findGanttDiagramms(idEt).get(0);

		System.out.println("------------------> idEt: " + idEt + " - " + lastGanttDiagram + " - "
				+ utilServices.fileLabel(lastGanttDiagram));
		File file = new File(lastGanttDiagram);
		System.out.println("----------2--------> idEt: " + file.getAbsolutePath());

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + utilServices.fileLabel(lastGanttDiagram));
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

	@GetMapping("/downloadGanttDiagramByUnit/{gdDecodedFullPath}")
	public ResponseEntity downloadGanttDiagramByUnit(@PathVariable String gdDecodedFullPath)
			throws IOException {

		String gdFullPath = utilServices.decodeEncodedValue(gdDecodedFullPath);

		File file = new File(gdFullPath);
		System.out.println("----------2-----#######PIKO15---> idEt: " + file.getAbsolutePath() + " - " + gdFullPath);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + utilServices.fileLabel(gdFullPath));
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

	@GetMapping("/existConventionByTRAITEE/{idEt}")
	public String checkExistConventionByTRAITEE(@PathVariable String idEt) throws IOException {

		String result = "00";
		List<String> lts = conventionRepository.findExistConventionTRAITEEByIdEt(idEt);

		System.out.println("----------------- Size: " + lts.size());
		for (String s : lts) {
			System.out.println("--- UNIT: " + s);
		}

		if (!lts.isEmpty()) {
			result = lts.get(0);
		}

		System.out.println("----------------- RESULT: " + result);

		// /*****/
		// String convCodePays = "--";
		// if(conv.get().getEntrepriseAccueilConvention().getPays() != null)
		// {
		// convCodePays =
		// conv.get().getEntrepriseAccueilConvention().getPays().getLangueCode();
		// }
		//
		// if(convCodePays.equalsIgnoreCase("TN"))
		// {
		// new ConventionTN_PDF(conv, CSFile, studentFullName, optionLabel, deptLabel);
		// System.out.println("----------------- TN");
		// }
		//
		// if(convCodePays.equalsIgnoreCase("EN") ||
		// convCodePays.equalsIgnoreCase("--"))
		// {
		// new ConventionEN_PDF(conv, CSFile, studentFullName, optionLabel, deptLabel);
		// System.out.println("----------------- EN");
		// }
		// if(convCodePays.equalsIgnoreCase("FR"))
		// {
		// StudentConvFRDto scf = utilServices.findStudentConvFRDtoById(idEt);
		// new ConventionFR_PDF(conv, CSFile, scf, optionLabel, deptLabel);
		// System.out.println("----------------- FR");
		// }

		return result;

	}

	@GetMapping("/dateDepotFicheById/{idEt}")
	public String findDateDepotFicheById(@PathVariable String idEt) {
		System.out.println(
				"------------> Date Depot Plan Travail: " + fichePFERepository.findDateDepotFicheByIdEt(idEt).get(0));
		return fichePFERepository.findDateDepotFicheByIdEt(idEt).get(0);
	}

	@GetMapping("/downloadNewestPlanTravail")
	public ResponseEntity downloadNewestPlanTravail(@RequestParam("studentId") String studentId) throws IOException {

		System.out.println("---------------------> studentId: " + studentId);

		FichePFEHistoryDto fichePFEHistoryDto = findNewestFichePFEDto(studentId);

		String studentFN = utilServices.findStudentFullNameById(studentId);

		String PTPath = "C:\\ESP-DOCS\\";
		String PTName = "Plan Travail " + studentFN + ".pdf";
		String PTFile = PTPath + PTName;

		new PlanTravail_PDF(PTFile, studentFN, fichePFEHistoryDto);

		File file = new File(PTFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PTName);
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

	@GetMapping("/downloadPlanTravail")
	public ResponseEntity downloadPlanTravail(@RequestParam("studentId") String studentId,
			@RequestParam("dateDepotPT") String dateDepotPT) throws IOException {

		System.out.println("---------------------> studentId: " + studentId);
		System.out.println("---------------------> dateDepotPT: " + dateDepotPT);

		FichePFEHistoryDto fichePFEHistoryDto = findFichePFEHistoryDto(studentId, dateDepotPT);

		String studentFN = utilServices.findStudentFullNameById(studentId);

		String PTPath = "C:\\ESP-DOCS\\";
		String PTName = "Plan Travail " + studentFN + ".pdf";
		String PTFile = PTPath + PTName;

		new PlanTravail_PDF(PTFile, studentFN, fichePFEHistoryDto);

		File file = new File(PTFile);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + PTName);
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

	public FichePFEHistoryDto findFichePFEHistoryDto(String studentId, String dateDepotPT) {

		FichePFEHistoryDto fs = fichePFERepository.findFichePFEsByStudentAndDateDepot(studentId, dateDepotPT);

		// System.out.println("----------------> SARS: " + fs.size());

		// Company
		// EntrepriseAccueilForConvHistDto companyHistoryDto =
		// conventionRepository.findEntrepriseAccueilForConvHistDtoByIdEt(idEt).get(0);

		EntrepriseAccueilForFichePFEHistDto companyHistoryDto = conventionRepository
				.findEntrepriseAccueilForFichePFEHistDtoByIdEtAndFichePFE(studentId, fs.getDateDepotFiche());

		ConventionPaysSecteurDto convPaysSecteur = conventionRepository
				.findConventionByStudentAndDateDepotFiche(studentId, fs.getDateDepotFiche());

		System.out.println("-----------------------> SARS-1: " + fs.getTitreProjet());
		System.out.println("-----------------------> SARS-2: " + fs.getPathJournalStg());

		if (convPaysSecteur != null) {
			if (convPaysSecteur.getCompanyNomPays() != null) {
				companyHistoryDto.setNomPays(convPaysSecteur.getCompanyNomPays());
			}

			if (convPaysSecteur.getCompanyLibelleSecteur() != null) {
				companyHistoryDto.setLibelleSecteur(convPaysSecteur.getCompanyLibelleSecteur());
			}
		} else {
			companyHistoryDto.setLibelleSecteur("--");
		}

		// Problematics
		List<ProblematicDto> problematics = new ArrayList<ProblematicDto>();
		List<String> lps = problematicRepository.findAllProblematicsByStudentAndFichePFE(studentId,
				fs.getDateDepotFiche());
		for (String p : lps) {
			ProblematicDto probUnit = new ProblematicDto(p);
			problematics.add(probUnit);
		}

		// Functionnalities
		List<FunctionnalityDto> functionnalities = new ArrayList<FunctionnalityDto>();
		List<Object[]> lofs = functionnalityRepository.findAllFunctionnalitiesByStudentAndFichePFE(studentId,
				fs.getDateDepotFiche());
		for (int j = 0; j < lofs.size(); j++) {
			String funcName = (String) lofs.get(j)[0];
			String funcDesc = (String) lofs.get(j)[1];

			FunctionnalityDto funcUnit = new FunctionnalityDto(funcName, funcDesc);
			functionnalities.add(funcUnit);
		}

		// Technologies
		System.out.println("-----" + studentId + " - " + fs.getDateDepotFiche());
		List<Technologie> lts = fichePFERepository.findTechnologiesByStudentAndFichePFE(studentId,
				fs.getDateDepotFiche());
		System.out.println("---------##########################---------------> Size technologies 1 : " + lts.size());
		List<TechnologyDto> technologies = new ArrayList<TechnologyDto>();
		for (Technologie t : lts) {
			TechnologyDto techUnit = new TechnologyDto(t.getName(), "A");
			technologies.add(techUnit);
		}

		System.out.println(
				"---------##########################---------------> Size technologies 2 : " + technologies.size());
		for (TechnologyDto t : technologies) {
			System.out.println("-----" + t.getName());
		}

		// List<Technologie> l1 = fichePFERepository.lol1(studentId);
		// List<String> l2 = fichePFERepository.lol2(studentId);
		// List<String> l3 = fichePFERepository.lol3(studentId, fs.getDateDepotFiche());
		//
		// System.out.println("------******-------------> HI 1: " + l1.size());
		// System.out.println("------******-------------> HI 2: " + l2.size());
		// System.out.println("------******-------------> HI 3: " + l3.size());

		// Supervisors
		List<EntrepriseSupervisorDto> lesds = encadrantEntrepriseRepository
				.findEntrepriseSupervisorsByStudentAndFiche(studentId, fs.getDateDepotFiche());

		// Treatments
		List<TraitementFicheHistoryDto> treatmentFiches = encadrantEntrepriseRepository
				.findTraitementFicheHistoryDtosByStudentAndFiche(studentId, fs.getDateDepotFiche());
		System.out.println("------------------------> Size: " + treatmentFiches.size());

		fs.setPairFullName(utilServices.findBinomeFullNameByStudentId(studentId));
		fs.setCompanyDto(companyHistoryDto);
		fs.setCompanySupervisors(lesds);
		fs.setProblematics(problematics);
		fs.setFunctionnalities(functionnalities);
		fs.setTechnologies(technologies);
		fs.setTreatmentFiches(treatmentFiches);

		// System.out.println("------------------------> 1: " +
		// lfs.get(i).getCompanyDto().getDesignation());

		return fs;

	}

	public FichePFEHistoryDto findNewestFichePFEDto(String studentId) {

		FichePFEHistoryDto fs = fichePFERepository.findNewestFichePFEsByStudent(studentId);

		// System.out.println("----------------> SARS: " + fs.size());

		// Company
		// EntrepriseAccueilForConvHistDto companyHistoryDto =
		// conventionRepository.findEntrepriseAccueilForConvHistDtoByIdEt(idEt).get(0);

		EntrepriseAccueilForFichePFEHistDto companyHistoryDto = conventionRepository
				.findEntrepriseAccueilForFichePFEHistDtoByIdEtAndFichePFE(studentId, fs.getDateDepotFiche());

		ConventionPaysSecteurDto convPaysSecteur = conventionRepository
				.findConventionByStudentAndDateDepotFiche(studentId, fs.getDateDepotFiche());

		System.out.println("-----------------------> SARS-1: " + fs.getTitreProjet());
		System.out.println("-----------------------> SARS-2: " + fs.getPathJournalStg());

		if (convPaysSecteur != null) {
			if (convPaysSecteur.getCompanyNomPays() != null) {
				companyHistoryDto.setNomPays(convPaysSecteur.getCompanyNomPays());
			}

			if (convPaysSecteur.getCompanyLibelleSecteur() != null) {
				companyHistoryDto.setLibelleSecteur(convPaysSecteur.getCompanyLibelleSecteur());
			}
		} else {
			companyHistoryDto.setLibelleSecteur("--");
		}

		// Problematics
		List<ProblematicDto> problematics = new ArrayList<ProblematicDto>();
		List<String> lps = problematicRepository.findAllProblematicsByStudentAndFichePFE(studentId,
				fs.getDateDepotFiche());
		for (String p : lps) {
			ProblematicDto probUnit = new ProblematicDto(p);
			problematics.add(probUnit);
		}

		// Functionnalities
		List<FunctionnalityDto> functionnalities = new ArrayList<FunctionnalityDto>();
		List<Object[]> lofs = functionnalityRepository.findAllFunctionnalitiesByStudentAndFichePFE(studentId,
				fs.getDateDepotFiche());
		for (int j = 0; j < lofs.size(); j++) {
			String funcName = (String) lofs.get(j)[0];
			String funcDesc = (String) lofs.get(j)[1];

			FunctionnalityDto funcUnit = new FunctionnalityDto(funcName, funcDesc);
			functionnalities.add(funcUnit);
		}

		// Technologies
		System.out.println("-----" + studentId + " - " + fs.getDateDepotFiche());
		List<Technologie> lts = fichePFERepository.findTechnologiesByStudentAndFichePFE(studentId,
				fs.getDateDepotFiche());
		System.out.println("---------##########################---------------> Size technologies 1 : " + lts.size());
		List<TechnologyDto> technologies = new ArrayList<TechnologyDto>();
		for (Technologie t : lts) {
			TechnologyDto techUnit = new TechnologyDto(t.getName(), "A");
			technologies.add(techUnit);
		}

		System.out.println(
				"---------##########################---------------> Size technologies 2 : " + technologies.size());
		for (TechnologyDto t : technologies) {
			System.out.println("-----" + t.getName());
		}

		// List<Technologie> l1 = fichePFERepository.lol1(studentId);
		// List<String> l2 = fichePFERepository.lol2(studentId);
		// List<String> l3 = fichePFERepository.lol3(studentId, fs.getDateDepotFiche());
		//
		// System.out.println("------******-------------> HI 1: " + l1.size());
		// System.out.println("------******-------------> HI 2: " + l2.size());
		// System.out.println("------******-------------> HI 3: " + l3.size());

		// Supervisors
		List<EntrepriseSupervisorDto> lesds = encadrantEntrepriseRepository
				.findEntrepriseSupervisorsByStudentAndFiche(studentId, fs.getDateDepotFiche());

		// Treatments
		List<TraitementFicheHistoryDto> treatmentFiches = encadrantEntrepriseRepository
				.findTraitementFicheHistoryDtosByStudentAndFiche(studentId, fs.getDateDepotFiche());
		System.out.println("------------------------> Size: " + treatmentFiches.size());

		fs.setPairFullName(utilServices.findBinomeFullNameByStudentId(studentId));
		fs.setCompanyDto(companyHistoryDto);
		fs.setCompanySupervisors(lesds);
		fs.setProblematics(problematics);
		fs.setFunctionnalities(functionnalities);
		fs.setTechnologies(technologies);
		fs.setTreatmentFiches(treatmentFiches);

		// System.out.println("------------------------> 1: " +
		// lfs.get(i).getCompanyDto().getDesignation());

		return fs;

	}

	@GetMapping("/activeStudentTimelineStep/{idEt}")
	public Integer findActiveStudentTimelineStep(@PathVariable String idEt) throws ParseException {
		Integer asts = -1;

		/******************************************************************/

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// Date Dépôt et Status Reports
		List<FichePFEDateDepotReportsEtatReportsDto> reports = fichePFERepository.findDepotReportsDateEtatByIdEt(idEt);

		String dateRemiseFichePFE = null;
		List<String> lddfs = fichePFERepository.findDateDepotFicheByIdEtOFF(idEt);
		if (!lddfs.isEmpty()) {
			dateRemiseFichePFE = lddfs.get(0);
		}

		String dateDepotReports = "--";
		String statusDepotReports = "--";

		if (!reports.isEmpty()) {
			dateDepotReports = reports.get(0).getDateDepotReports();
			statusDepotReports = reports.get(0).getEtatDepotReports();
		} else {
			statusDepotReports = "null";
		}

		// Date & Status Convention
		List<ConventionDateConvDateDebutStageStatusDto> convDetails = conventionRepository
				.findValidatedConventionDateConvDateDebutStageStatusByIdEt(idEt);

		String statusConvention = null;
		String dateConvention = "--";
		String dateDepotNewspaperFormed = null;
		String dateDepotBalanceSheetFormedV1 = null;
		String dateDepotBalanceSheetFormedV2 = null;
		String dateDepotBalanceSheetFormedV3 = null;
		String dateDepotRapportFormedV1 = null;
		String dateDepotRapportFormedV2 = null;
		String dateNewestDebutStageConvAv = null;
		String dateLancementPresentation1 = null;
		String dateLancementPresentation2 = null;
		String dateLancementVisiteMiParcour = null;

		if (convDetails.isEmpty()) {
			System.out.println("----------------------> 0 CONV");
			// Newspaper
			dateDepotNewspaperFormed = null;

			// Balance Sheets
			dateDepotBalanceSheetFormedV1 = null;
			dateDepotBalanceSheetFormedV2 = null;
			dateDepotBalanceSheetFormedV3 = null;

			// Reports
			dateDepotRapportFormedV1 = null;
			dateDepotRapportFormedV2 = null;
		}

		if (!convDetails.isEmpty()) {
			System.out.println("----------------------> N CONV");
			if (convDetails.size() == 1) {
				System.out.println("----------------------> 1* CONV");

				dateConvention = convDetails.get(0).getDateConvention();
				statusConvention = convDetails.get(0).getTraiter();

				dateNewestDebutStageConvAv = dateFormat.format(convDetails.get(0).getDateDebutStage());

				System.out.println(
						"----------------------> 1* CONV  dateNewestDebutStageConvAv: " + dateNewestDebutStageConvAv);
			}

			if (convDetails.size() > 1) {
				System.out.println("----------------------> " + convDetails.size() + " CONV");

				dateConvention = convDetails.get(0).getDateConvention();
				statusConvention = convDetails.get(0).getTraiter();

				dateNewestDebutStageConvAv = dateFormat.format(convDetails.get(0).getDateDebutStage());
			}

			// Date & Status Avenant
			List<AvenantDateAvDateDebutStageStatusDto> avDtos = avenantRepository
					.findAvenantDateAvDateDebutStageStatusDtoByStudentAndConv(idEt,
							conventionRepository.findDateConventionByIdEt(idEt).get(0));

			Integer avsLength = avDtos.size();
			// if (avsLength != 0)
			// {
			// System.out.println("----------------------> N AVN");
			//
			// Integer lastTreatedIndex = 1;
			// String dateAvenant = "--";
			// Boolean statusAvenant = false;
			// // String dateNewestDebutStageConvAv = "--";
			//
			// if (avsLength == 1 && avDtos.get(0).getTraiter() == true)
			// {
			// System.out.println("----------------------> 1* AVN");
			//
			// dateAvenant = avDtos.get(0).getDateAvenant();
			// statusAvenant = avDtos.get(0).getTraiter();
			// dateNewestDebutStageConvAv =
			// dateFormat.format(avDtos.get(0).getDateDebutStage());
			// //avDtos.get(0).getDateDebutStage();
			// }
			//
			// if (avsLength > 1 && avDtos.get(lastTreatedIndex).getTraiter() == true)
			// {
			// System.out.println("----------------------> " + avsLength + " AVN");
			//
			// if (avDtos.get(0).getTraiter() == true)
			// {
			// lastTreatedIndex = 0;
			// }
			//
			// dateAvenant = avDtos.get(lastTreatedIndex).getDateAvenant();
			// statusAvenant = avDtos.get(lastTreatedIndex).getTraiter();
			//
			// dateNewestDebutStageConvAv =
			// dateFormat.format(avDtos.get(lastTreatedIndex).getDateDebutStage());
			// //avDtos.get(lastTreatedIndex).getDateDebutStage();
			// }
			//
			// }
		}

		System.out.println("Planning --------------> Date Demande Convention: " + dateNewestDebutStageConvAv);
		System.out.println("Planning --------------> Date Remise Plan Travail: " + dateRemiseFichePFE);
		System.out.println("Planning --------------> Lancement Stage PFE: " + dateNewestDebutStageConvAv);

		List<FichePFE> lfs = fichePFERepository.findFichePFEByStudent(idEt);
		if (!lfs.isEmpty()) {
			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

			if (fichePFE.getPathJournalStg() != null) {
				dateDepotNewspaperFormed = dateFormat.format(fichePFE.getDateDepotJournalStg());
			}

			if (fichePFE.getPathBilan1() != null) {
				dateDepotBalanceSheetFormedV1 = dateFormat.format(fichePFE.getDateDepotBilan1());
			}

			if (fichePFE.getPathBilan2() != null) {
				dateDepotBalanceSheetFormedV2 = dateFormat.format(fichePFE.getDateDepotBilan2());
			}

			if (fichePFE.getPathRapportVersion1() != null) {
				dateDepotRapportFormedV1 = dateFormat.format(fichePFE.getDateDepotRapportVersion1());
			}

			if (fichePFE.getPathBilan3() != null) {
				dateDepotBalanceSheetFormedV3 = dateFormat.format(fichePFE.getDateDepotBilan3());
			}

			if (fichePFE.getPathRapportVersion2() != null) {
				dateDepotRapportFormedV2 = dateFormat.format(fichePFE.getDateDepotRapportVersion2());
			}

			System.out.println("Planning --------------> Dépôt Journal de Bord: " + dateDepotNewspaperFormed + " - "
					+ fichePFE.getDateDepotJournalStg());
			System.out.println("Planning --------------> Dépôt Bilan Version 1: " + dateDepotBalanceSheetFormedV1
					+ " - " + fichePFE.getDateDepotBilan1());
			System.out.println("Planning --------------> Dépôt Bilan Version 2: " + dateDepotBalanceSheetFormedV2
					+ " - " + fichePFE.getDateDepotBilan2());
			System.out.println("Planning --------------> Dépôt Rapport Version 1: " + dateDepotRapportFormedV1 + " - "
					+ fichePFE.getDateDepotRapportVersion1());
			System.out.println("Planning --------------> Dépôt Bilan Version 3: " + dateDepotBalanceSheetFormedV3
					+ " - " + fichePFE.getDateDepotBilan3());
			System.out.println("Planning --------------> Dépôt Rapport Version 2: " + dateDepotRapportFormedV2 + " - "
					+ fichePFE.getDateDepotRapportVersion2());

		}

		// System.out.println("Planning -----##########################---------> 1: " +
		// dateNewestDebutStageConvAv + " - " + dateNewestDebutStageConvAv);
		if (dateNewestDebutStageConvAv != null) {
			asts = 0;
		}
		// System.out.println("Planning -----##########################---------> 2: " +
		// dateNewestDebutStageConvAv + " - " + dateRemiseFichePFE);
		if (dateNewestDebutStageConvAv != null && dateRemiseFichePFE == null) {
			asts = 1;
		}

		if (dateRemiseFichePFE != null && dateDepotNewspaperFormed == null) {
			asts = 2;
		}

		if (dateDepotNewspaperFormed != null && dateDepotBalanceSheetFormedV1 == null) {
			asts = 3;
		}

		if (dateDepotBalanceSheetFormedV1 != null && dateDepotBalanceSheetFormedV2 == null) {
			asts = 4;
		}

		if (dateDepotBalanceSheetFormedV2 != null && dateDepotRapportFormedV1 == null) {
			asts = 7;
		}

		if (dateDepotRapportFormedV1 != null && dateDepotBalanceSheetFormedV3 == null) {
			asts = 8;
		}

		if (dateDepotBalanceSheetFormedV3 != null && dateDepotRapportFormedV2 == null) {
			asts = 10;
		}

		if (dateDepotRapportFormedV2 != null) {
			asts = 11;
		}

		System.out.println("--------------------------> ASTS: " + asts);

		/******************************************************************/

		return asts;
	}

	// @GetMapping("/studentTimelineByIdEt/{idEt}")
	// public List<StudentTimelineDto> findStudentTimelineByIdEt(@PathVariable
	// String idEt) throws ParseException
	// {
	// DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	//
	// // Date Dépôt et Status Reports
	// List<FichePFEDateDepotReportsEtatReportsDto> reports =
	// fichePFERepository.findDepotReportsDateEtatByIdEt(idEt);
	//
	// List<String> ldrfs = fichePFERepository.findDateDepotFicheByIdEtOFF(idEt);
	// String dateRemiseFichePFE = "--";
	// if(!ldrfs.isEmpty())
	// {
	// dateRemiseFichePFE = ldrfs.get(0);
	// System.out.println("###########################################################>>>>>>
	// " + dateRemiseFichePFE);
	// }
	//
	// String dateDepotReports = "--";
	// String statusDepotReports = "--";
	//
	// if (!reports.isEmpty())
	// {
	// dateDepotReports = reports.get(0).getDateDepotReports();
	// statusDepotReports = reports.get(0).getEtatDepotReports();
	// }
	// else
	// {
	// statusDepotReports = "null";
	// }
	//
	//
	// // Date & Status Convention
	// List<ConventionDateConvDateDebutStageStatusDto> convDetails =
	// conventionRepository.findValidatedConventionDateConvDateDebutStageStatusByIdEt(idEt);
	//
	// String statusConvention = null;
	// String dateConvention = "--";
	// String dateDepotNewspaperFormed = "--";
	// String dateDepotBalanceSheetFormedV1 = "--";
	// String dateDepotBalanceSheetFormedV2 = "--";
	// String dateDepotBalanceSheetFormedV3 = "--";
	// String dateDepotRapportFormedV1 = "--";
	// String dateDepotRapportFormedV2 = "--";
	// String dateNewestDebutStageConvAv = "--";
	//
	// if (convDetails.isEmpty())
	// {
	// System.out.println("----------------------> 0 CONV");
	// // Newspaper
	// dateDepotNewspaperFormed = "--";
	//
	// // Balance Sheets
	// dateDepotBalanceSheetFormedV1 = "--";
	// dateDepotBalanceSheetFormedV2 = "--";
	// dateDepotBalanceSheetFormedV3 = "--";
	//
	// // Reports
	// dateDepotRapportFormedV1 = "--";
	// dateDepotRapportFormedV2 = "--";
	// }
	//
	// if (!convDetails.isEmpty())
	// {
	// System.out.println("----------------------> N CONV");
	// if (convDetails.size() == 1)
	// {
	// System.out.println("----------------------> 1* CONV");
	//
	// dateConvention = convDetails.get(0).getDateConvention();
	// statusConvention = convDetails.get(0).getTraiter();
	//
	//
	// dateNewestDebutStageConvAv =
	// dateFormat.format(convDetails.get(0).getDateDebutStage());
	//
	// System.out.println("----------------------> 1* CONV
	// dateNewestDebutStageConvAv: " + dateNewestDebutStageConvAv);
	// }
	//
	// if (convDetails.size() > 1)
	// {
	// System.out.println("----------------------> " + convDetails.size() + "
	// CONV");
	//
	// dateConvention = convDetails.get(0).getDateConvention();
	// statusConvention = convDetails.get(0).getTraiter();
	//
	// dateNewestDebutStageConvAv =
	// dateFormat.format(convDetails.get(0).getDateDebutStage());
	// }
	// }
	//
	// // Date & Status Avenant
	// List<String> lcs = conventionRepository.findDateConventionByIdEt(idEt);
	// List<AvenantDateAvDateDebutStageStatusDto> avDtos = new
	// ArrayList<AvenantDateAvDateDebutStageStatusDto>();
	// if(!lcs.isEmpty())
	// {
	// avDtos =
	// avenantRepository.findAvenantDateAvDateDebutStageStatusDtoByStudentAndConv(idEt,
	// lcs.get(0));
	// }
	//
	// Integer avsLength = avDtos.size();
	// if (avsLength != 0)
	// {
	// System.out.println("----------------------> N AVN");
	//
	// Integer lastTreatedIndex = 1;
	// String dateAvenant = "--";
	// Boolean statusAvenant = false;
	// // String dateNewestDebutStageConvAv = "--";
	//
	// if (avsLength == 1 && avDtos.get(0).getTraiter() == true)
	// {
	// System.out.println("----------------------> 1* AVN");
	//
	// dateAvenant = avDtos.get(0).getDateAvenant();
	// statusAvenant = avDtos.get(0).getTraiter();
	// dateNewestDebutStageConvAv =
	// dateFormat.format(avDtos.get(0).getDateDebutStage());
	// //avDtos.get(0).getDateDebutStage();
	// }
	//
	// if (avsLength > 1 && avDtos.get(lastTreatedIndex).getTraiter() == true)
	// {
	// System.out.println("----------------------> " + avsLength + " AVN");
	//
	// if (avDtos.get(0).getTraiter() == true)
	// {
	// lastTreatedIndex = 0;
	// }
	//
	// dateAvenant = avDtos.get(lastTreatedIndex).getDateAvenant();
	// statusAvenant = avDtos.get(lastTreatedIndex).getTraiter();
	//
	// dateNewestDebutStageConvAv =
	// dateFormat.format(avDtos.get(lastTreatedIndex).getDateDebutStage());
	// //avDtos.get(lastTreatedIndex).getDateDebutStage();
	// }
	//
	// }
	//
	// // Timeline by the Newest Date Debut Stage By Convention - Avenant
	// if (!dateNewestDebutStageConvAv.equalsIgnoreCase("--"))
	// {
	//
	// Date startTrDate = formatter.parse(dateNewestDebutStageConvAv);
	//
	// // Newspaper
	// dateDepotNewspaperFormed = dateFormat.format(utilServices.addDAYStoTODAY(2*7,
	// startTrDate));
	//
	// // Balance Sheets
	// dateDepotBalanceSheetFormedV1 =
	// dateFormat.format(utilServices.addDAYStoTODAY(6*7, startTrDate));
	// dateDepotBalanceSheetFormedV2 =
	// dateFormat.format(utilServices.addDAYStoTODAY(14*7, startTrDate));
	// dateDepotBalanceSheetFormedV3 =
	// dateFormat.format(utilServices.addDAYStoTODAY(23*7, startTrDate));
	//
	// // Reports
	// dateDepotRapportFormedV1 =
	// dateFormat.format(utilServices.addDAYStoTODAY(18*7, startTrDate));
	// dateDepotRapportFormedV2 =
	// dateFormat.format(utilServices.addDAYStoTODAY(24*7, startTrDate));
	//
	//
	// // Planning
	// System.out.println("Planning --------------> Date Demande Convention: " +
	// dateNewestDebutStageConvAv);
	// System.out.println("Planning --------------> Date Remise Plan de Travail: " +
	// dateRemiseFichePFE);
	// System.out.println("Planning --------------> Lancement Stage PFE: " +
	// dateNewestDebutStageConvAv);
	// System.out.println("Planning --------------> Dépôt Journal de Bord: " +
	// dateDepotNewspaperFormed);
	// System.out.println("Planning --------------> Dépôt Bilan Version 1: " +
	// dateDepotBalanceSheetFormedV1);
	// System.out.println("Planning --------------> Dépôt Bilan Version 2: " +
	// dateDepotBalanceSheetFormedV2);
	// System.out.println("Planning --------------> Dépôt Rapport Version 1: " +
	// dateDepotRapportFormedV1);
	// System.out.println("Planning --------------> Dépôt Bilan Version 3: " +
	// dateDepotBalanceSheetFormedV3);
	// System.out.println("Planning --------------> Dépôt Rapport Version 2: " +
	// dateDepotRapportFormedV2);
	// }
	//
	// List<StudentTimelineDto> stds = new ArrayList<StudentTimelineDto>();
	// stds.add(new StudentTimelineDto("Lancement\n" + " " + " " + " " + " " + "
	// Stage PFE " + " " + " " + " ", dateNewestDebutStageConvAv, "/myDocuments"));
	// stds.add(new StudentTimelineDto("Demande\n" + " " + " " + " " + " " + "
	// Convention " + " " + " " + " ", dateConvention, "/submitAgreement"));
	// stds.add(new StudentTimelineDto("Remise\n" + " " + " " + " " + " " + " Plan
	// de Travail " + " " + " " + " ", dateRemiseFichePFE, "/addESPFile"));
	// stds.add(new StudentTimelineDto("Dépôt\n Journal de Bord",
	// dateDepotNewspaperFormed, "/uploadNewspaper"));
	// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 1",
	// dateDepotBalanceSheetFormedV1, "/uploadBalanceSheet"));
	// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 2",
	// dateDepotBalanceSheetFormedV2, "/uploadBalanceSheet"));
	// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 1",
	// dateDepotRapportFormedV1, "/uploadReport"));
	// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 3",
	// dateDepotBalanceSheetFormedV3, "/uploadBalanceSheet"));
	// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 2",
	// dateDepotRapportFormedV2, "/uploadReport"));
	//
	//// List<String> steps = new ArrayList<String>();
	//// steps.add(". " + " " + " " + " " + " " + " " + " " + dateConvention + " " +
	// " " + " " + " " + " " + " " + " .\n\nDemande\nConvention");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " + dateRemiseFichePFE + "
	// " + " " + " " + " " + " " + " " + " .\n\nRemise\nPlan de Travail");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " +
	// dateNewestDebutStageConvAv + " " + " " + " " + " " + " " + " " + "
	// .\n\nLancement\nStage PFE");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " +
	// dateDepotNewspaperFormed + " " + " " + " " + " " + " " + " " + "
	// .\n\nDépôt\nJournal de Bord");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " +
	// dateDepotBalanceSheetFormedV1 + " " + " " + " " + " " + " " + " " + "
	// .\n\nDépôt\nBilan Version 1");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " +
	// dateDepotBalanceSheetFormedV2 + " " + " " + " " + " " + " " + " " + "
	// .\n\nDépôt\nBilan Version 2");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " +
	// dateDepotRapportFormedV1 + " " + " " + " " + " " + " " + " " + "
	// .\n\nDépôt\nRapport Version 1");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " +
	// dateDepotBalanceSheetFormedV3 + " " + " " + " " + " " + " " + " " + "
	// .\n\nDépôt\nBilan Version 3");
	//// steps.add(". " + " " + " " + " " + " " + " " + " " +
	// dateDepotRapportFormedV2 + " " + " " + " " + " " + " " + " " + "
	// .\n\nDépôt\nRapport Version 2");
	//
	//
	// return stds;
	// }

	@GetMapping("/studentTimelineByIdEt/{idEt}")
	public List<StudentTimelineDto> findStudentTimelineByIdEt(@PathVariable String idEt) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		// Date Dépôt et Status Reports
		List<FichePFEDateDepotReportsEtatReportsDto> reports = fichePFERepository.findDepotReportsDateEtatByIdEt(idEt);

		List<String> ldrfs = fichePFERepository.findDateDepotFicheByIdEtOFF(idEt);
		String dateRemiseFichePFE = "--";
		if (!ldrfs.isEmpty()) {
			dateRemiseFichePFE = ldrfs.get(0);
			System.out
					.println("###########################################################>>>>>> " + dateRemiseFichePFE);
		}

		String dateDepotReports = "--";
		String statusDepotReports = "--";

		if (!reports.isEmpty()) {
			dateDepotReports = reports.get(0).getDateDepotReports();
			statusDepotReports = reports.get(0).getEtatDepotReports();
		} else {
			statusDepotReports = "null";
		}

		// Date & Status Convention
		List<ConventionDateConvDateDebutStageStatusDto> convDetails = conventionRepository
				.findValidatedConventionDateConvDateDebutStageStatusByIdEt(idEt);

		String statusConvention = null;
		String dateConvention = "--";
		String dateRemiseBilanDebutStage = "--";
		String dateLancementPresentation1 = "--";
		String dateLancementVisiteMiParcours = "--";
		String dateRemiseBilanMilieuStage = "--";
		String dateDepotRapportV1 = "--";
		String dateLancementPresentation2 = "--";
		String dateRemiseBilanFinStage = "--";
		String dateDepotRapportVFinale = "--";
		String dateDepotNewspaperFormed = "--";
		// String dateDepotBalanceSheetFormedV1 = "--";
		// String dateDepotBalanceSheetFormedV2 = "--";
		// String dateDepotBalanceSheetFormedV3 = "--";
		// String dateDepotRapportFormedV1 = "--";
		// String dateDepotRapportFormedV2 = "--";
		String dateNewestDebutStageConvAv = "--";

		if (convDetails.isEmpty()) {
			System.out.println("----------------------> 0 CONV");
			// PlanTravail
			dateDepotNewspaperFormed = "--";

			// Balance Sheets
			dateRemiseBilanDebutStage = "--";
			dateRemiseBilanMilieuStage = "--";
			dateRemiseBilanFinStage = "--";

			// Presentations & Visite
			dateLancementPresentation1 = "--";
			dateLancementPresentation2 = "--";
			dateLancementVisiteMiParcours = "--";

			// Reports
			dateDepotRapportV1 = "--";
			dateDepotRapportVFinale = "--";
		}

		if (!convDetails.isEmpty()) {
			System.out.println("----------------------> N CONV");
			if (convDetails.size() == 1) {
				System.out.println("----------------------> 1* CONV");

				dateConvention = convDetails.get(0).getDateConvention();
				statusConvention = convDetails.get(0).getTraiter();

				dateNewestDebutStageConvAv = dateFormat.format(convDetails.get(0).getDateDebutStage());

				System.out.println(
						"----------------------> 1* CONV  dateNewestDebutStageConvAv: " + dateNewestDebutStageConvAv);
			}

			if (convDetails.size() > 1) {
				System.out.println("----------------------> " + convDetails.size() + " CONV");

				dateConvention = convDetails.get(0).getDateConvention();
				statusConvention = convDetails.get(0).getTraiter();

				dateNewestDebutStageConvAv = dateFormat.format(convDetails.get(0).getDateDebutStage());
			}
		}

		// Date & Status Avenant
		List<String> lcs = conventionRepository.findDateConventionByIdEt(idEt);
		List<AvenantDateAvDateDebutStageStatusDto> avDtos = new ArrayList<AvenantDateAvDateDebutStageStatusDto>();
		if (!lcs.isEmpty()) {
			avDtos = avenantRepository.findAvenantDateAvDateDebutStageStatusDtoByStudentAndConv(idEt, lcs.get(0));
		}

		// Integer avsLength = avDtos.size();
		// if (avsLength != 0)
		// {
		// System.out.println("----------------------> N AVN");
		//
		// Integer lastTreatedIndex = 1;
		// String dateAvenant = "--";
		// Boolean statusAvenant = false;
		// // String dateNewestDebutStageConvAv = "--";
		//
		// if (avsLength == 1 && avDtos.get(0).getTraiter() == true)
		// {
		// System.out.println("----------------------> 1* AVN");
		//
		// dateAvenant = avDtos.get(0).getDateAvenant();
		// statusAvenant = avDtos.get(0).getTraiter();
		// dateNewestDebutStageConvAv =
		// dateFormat.format(avDtos.get(0).getDateDebutStage());
		// //avDtos.get(0).getDateDebutStage();
		// }
		//
		// if (avsLength > 1 && avDtos.get(lastTreatedIndex).getTraiter() == true)
		// {
		// System.out.println("----------------------> " + avsLength + " AVN");
		//
		// if (avDtos.get(0).getTraiter() == true)
		// {
		// lastTreatedIndex = 0;
		// }
		//
		// dateAvenant = avDtos.get(lastTreatedIndex).getDateAvenant();
		// statusAvenant = avDtos.get(lastTreatedIndex).getTraiter();
		//
		// dateNewestDebutStageConvAv =
		// dateFormat.format(avDtos.get(lastTreatedIndex).getDateDebutStage());
		// //avDtos.get(lastTreatedIndex).getDateDebutStage();
		// }
		//
		// }

		// Timeline by the Newest Date Debut Stage By Convention - Avenant
		if (!dateNewestDebutStageConvAv.equalsIgnoreCase("--")) {

			Date startTrDate = formatter.parse(dateNewestDebutStageConvAv);

			// // PlanTravail
			// datePreparationPlanTravail =
			// dateFormat.format(utilServices.addDAYStoTODAY(1*7, startTrDate));

			// Newspaper
			dateDepotNewspaperFormed = dateFormat.format(utilServices.addDAYStoTODAY(2 * 7, startTrDate));

			// Balance Sheets
			dateRemiseBilanDebutStage = dateFormat.format(utilServices.addDAYStoTODAY(6 * 7, startTrDate));
			dateRemiseBilanMilieuStage = dateFormat.format(utilServices.addDAYStoTODAY(15 * 7, startTrDate));
			dateRemiseBilanFinStage = dateFormat.format(utilServices.addDAYStoTODAY(25 * 7, startTrDate));

			// Presentations & Visite
			dateLancementPresentation1 = dateFormat.format(utilServices.addDAYStoTODAY(9 * 7, startTrDate));
			dateLancementPresentation2 = dateFormat.format(utilServices.addDAYStoTODAY(21 * 7, startTrDate));
			dateLancementVisiteMiParcours = dateFormat.format(utilServices.addDAYStoTODAY(13 * 7, startTrDate));

			// Reports
			dateDepotRapportV1 = dateFormat.format(utilServices.addDAYStoTODAY(20 * 7, startTrDate));
			dateDepotRapportVFinale = dateFormat.format(utilServices.addDAYStoTODAY(26 * 7, startTrDate));

			// Planning
			System.out.println("Planning --------------> Date Demande Convention: " + dateNewestDebutStageConvAv);
			System.out.println("Planning --------------> Date Remise Plan Travail: " + dateRemiseFichePFE);
			System.out.println("Planning --------------> Lancement Stage PFE: " + dateNewestDebutStageConvAv);
			System.out.println("Planning --------------> Date Preparation Plan Travail: " + dateDepotNewspaperFormed);
			System.out.println("Planning --------------> Dépôt Bilan Version 1: " + dateRemiseBilanDebutStage);
			System.out.println("Planning --------------> Dépôt Bilan Version 2: " + dateRemiseBilanMilieuStage);
			System.out.println("Planning --------------> Dépôt Rapport Version 1: " + dateDepotRapportV1);
			System.out.println("Planning --------------> Dépôt Bilan Version 3: " + dateRemiseBilanFinStage);
			System.out.println("Planning --------------> Dépôt Rapport Version 2: " + dateDepotRapportVFinale);
			System.out.println("Planning --------------> Date Lancement Restitution 1: " + dateLancementPresentation1);
			System.out.println("Planning --------------> Date Lancement Restitution 2: " + dateLancementPresentation2);
			System.out.println(
					"Planning --------------> Date Lancement Visite Mi-Parcours: " + dateLancementVisiteMiParcours);
		}

		List<StudentTimelineDto> stds = new ArrayList<StudentTimelineDto>();
		stds.add(new StudentTimelineDto("Lancement\n" + " " + " " + " " + " " + " Stage PFE " + " " + " " + " ",
				dateNewestDebutStageConvAv, "/myDocuments"));
		stds.add(new StudentTimelineDto("Demande\n" + " " + " " + " " + " " + " Convention " + " " + " " + " ",
				dateConvention, "/submitAgreement"));
		stds.add(new StudentTimelineDto("Remise\n" + " " + " " + " " + " " + " Plan Travail " + " " + " " + " ",
				dateRemiseFichePFE, "/addESPFile"));
		stds.add(new StudentTimelineDto("Dépôt\n Journal de Bord", dateDepotNewspaperFormed, "/uploadNewspaper"));
		stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 1", dateRemiseBilanDebutStage, "/uploadBalanceSheet"));
		stds.add(new StudentTimelineDto("Lancement\n" + " " + " " + " Restitution 1", dateLancementPresentation1,
				"/synopsisAndNews"));
		stds.add(new StudentTimelineDto("Lancement\n Visite Mi-Parcours*", dateLancementVisiteMiParcours,
				"/synopsisAndNews"));
		stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 2", dateRemiseBilanMilieuStage, "/uploadBalanceSheet"));
		stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 1", dateDepotRapportV1, "/uploadReport"));
		stds.add(new StudentTimelineDto("Lancement\n Restitution 2*", dateLancementPresentation2, "/synopsisAndNews"));
		stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 3", dateRemiseBilanFinStage, "/uploadBalanceSheet"));
		stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 2", dateDepotRapportVFinale, "/uploadReport"));
		stds.add(new StudentTimelineDto("Enquête\n Premier Emploi", dateDepotRapportVFinale, "/synopsisAndNews"));

		// List<String> steps = new ArrayList<String>();
		// steps.add(". " + " " + " " + " " + " " + " " + " " + dateConvention + " " + "
		// " + " " + " " + " " + " " + " .\n\nDemande\nConvention");
		// steps.add(". " + " " + " " + " " + " " + " " + " " + dateRemiseFichePFE + " "
		// + " " + " " + " " + " " + " " + " .\n\nRemise\nPlan de Travail");
		// steps.add(". " + " " + " " + " " + " " + " " + " " +
		// dateNewestDebutStageConvAv + " " + " " + " " + " " + " " + " " + "
		// .\n\nLancement\nStage PFE");
		// steps.add(". " + " " + " " + " " + " " + " " + " " + dateDepotNewspaperFormed
		// + " " + " " + " " + " " + " " + " " + " .\n\nDépôt\nJournal de Bord");
		// steps.add(". " + " " + " " + " " + " " + " " + " " +
		// dateDepotBalanceSheetFormedV1 + " " + " " + " " + " " + " " + " " + "
		// .\n\nDépôt\nBilan Version 1");
		// steps.add(". " + " " + " " + " " + " " + " " + " " +
		// dateDepotBalanceSheetFormedV2 + " " + " " + " " + " " + " " + " " + "
		// .\n\nDépôt\nBilan Version 2");
		// steps.add(". " + " " + " " + " " + " " + " " + " " + dateDepotRapportFormedV1
		// + " " + " " + " " + " " + " " + " " + " .\n\nDépôt\nRapport Version 1");
		// steps.add(". " + " " + " " + " " + " " + " " + " " +
		// dateDepotBalanceSheetFormedV3 + " " + " " + " " + " " + " " + " " + "
		// .\n\nDépôt\nBilan Version 3");
		// steps.add(". " + " " + " " + " " + " " + " " + " " + dateDepotRapportFormedV2
		// + " " + " " + " " + " " + " " + " " + " .\n\nDépôt\nRapport Version 2");

		return stds;
	}

	@GetMapping("/myStudentTBSTimelineByIdEt/{idEt}")
	public List<StudentTimelineDto> findMyStudentTBSTimelineByIdEt(@PathVariable String idEt) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		// Date Dépôt et Status Reports
		List<FichePFEDateDepotReportsEtatReportsDto> reports = fichePFERepository.findDepotReportsDateEtatByIdEt(idEt);

		String dateRemiseFichePFE = "--";
		List<String> ldrfs = fichePFERepository.findDateDepotFicheByIdEtOFF(idEt);

		if (!ldrfs.isEmpty()) {
			dateRemiseFichePFE = fichePFERepository.findDateDepotFicheByIdEtOFF(idEt).get(0);
		}

		String dateDepotReports = "--";
		String statusDepotReports = "--";

		if (!reports.isEmpty()) {
			dateDepotReports = reports.get(0).getDateDepotReports();
			statusDepotReports = reports.get(0).getEtatDepotReports();
		} else {
			statusDepotReports = "null";
		}

		// Date & Status Convention
		List<ConventionDateConvDateDebutStageStatusDto> convDetails = conventionRepository
				.findValidatedConventionDateConvDateDebutStageStatusByIdEt(idEt);

		String statusConvention = null;
		String dateConvention = "--";
		String datePreparationPlanTravail = "--";
		String dateRemiseBilanDebutStage = "--";
		String dateLancementPresentation1 = "--";
		String dateLancementVisiteMiParcours = "--";
		String dateRemiseBilanMilieuStage = "--";
		String dateDepotRapportV1 = "--";
		String dateLancementPresentation2 = "--";
		String dateRemiseBilanFinStage = "--";
		String dateDepotRapportVFinale = "--";
		String dateDepotNewspaperFormed = "--";
		// String dateDepotBalanceSheetFormedV1 = "--";
		// String dateDepotBalanceSheetFormedV2 = "--";
		// String dateDepotBalanceSheetFormedV3 = "--";
		// String dateDepotRapportFormedV1 = "--";
		// String dateDepotRapportFormedV2 = "--";
		String dateNewestDebutStageConvAv = "--";

		if (convDetails.isEmpty()) {
			System.out.println("----------------------> 0 CONV");
			// PlanTravail
			datePreparationPlanTravail = "--";

			// Newspaper
			dateDepotNewspaperFormed = "--";

			// Balance Sheets
			dateRemiseBilanDebutStage = "--";
			dateRemiseBilanMilieuStage = "--";
			dateRemiseBilanFinStage = "--";

			// Presentations & Visite
			dateLancementPresentation1 = "--";
			dateLancementPresentation2 = "--";
			dateLancementVisiteMiParcours = "--";

			// Reports
			dateDepotRapportV1 = "--";
			dateDepotRapportVFinale = "--";
		}

		if (!convDetails.isEmpty()) {
			System.out.println("----------------------> N CONV");
			if (convDetails.size() == 1) {
				System.out.println("----------------------> 1* CONV");

				dateConvention = convDetails.get(0).getDateConvention();
				statusConvention = convDetails.get(0).getTraiter();

				dateNewestDebutStageConvAv = dateFormat.format(convDetails.get(0).getDateDebutStage());

				System.out.println(
						"----------------------> 1* CONV  dateNewestDebutStageConvAv: " + dateNewestDebutStageConvAv);
			}

			if (convDetails.size() > 1) {
				System.out.println("----------------------> " + convDetails.size() + " CONV");

				dateConvention = convDetails.get(0).getDateConvention();
				statusConvention = convDetails.get(0).getTraiter();

				dateNewestDebutStageConvAv = dateFormat.format(convDetails.get(0).getDateDebutStage());
			}
		}

		// Date & Status Avenant
		Integer avsLength = null;

		List<String> lss = conventionRepository.findDateConventionByIdEt(idEt);
		Integer validatedAVN = 0;
		if (!lss.isEmpty()) {
			validatedAVN = avenantRepository.findValidatedAVNByStudentAndConv(idEt, lss.get(0)).size();
			List<AvenantDateAvDateDebutStageStatusDto> avDtos = avenantRepository
					.findAvenantDateAvDateDebutStageStatusDtoByStudentAndConv(idEt, lss.get(0));

			avsLength = avDtos.size();
			// if (avsLength != 0)
			// {
			// System.out.println("----------------------> N AVN");
			//
			// Integer lastTreatedIndex = 1;
			// String dateAvenant = "--";
			// Boolean statusAvenant = false;
			// // String dateNewestDebutStageConvAv = "--";
			//
			// if (avsLength == 1 && avDtos.get(0).getTraiter() == true)
			// {
			// System.out.println("----------------------> 1* AVN");
			//
			// dateAvenant = avDtos.get(0).getDateAvenant();
			// statusAvenant = avDtos.get(0).getTraiter();
			// dateNewestDebutStageConvAv =
			// dateFormat.format(avDtos.get(0).getDateDebutStage());
			// //avDtos.get(0).getDateDebutStage();
			// }
			//
			// if (avsLength > 1 && avDtos.get(lastTreatedIndex).getTraiter() == true)
			// {
			// System.out.println("----------------------> " + avsLength + " AVN");
			//
			// if (avDtos.get(0).getTraiter() == true)
			// {
			// lastTreatedIndex = 0;
			// }
			//
			// dateAvenant = avDtos.get(lastTreatedIndex).getDateAvenant();
			// statusAvenant = avDtos.get(lastTreatedIndex).getTraiter();
			//
			// dateNewestDebutStageConvAv =
			// dateFormat.format(avDtos.get(lastTreatedIndex).getDateDebutStage());
			// //avDtos.get(lastTreatedIndex).getDateDebutStage();
			// }
			//
			// }
		}

		// Timeline by the Newest Date Debut Stage By Convention - Avenant
		if (!dateNewestDebutStageConvAv.equalsIgnoreCase("--")) {

			Date startTrDate = formatter.parse(dateNewestDebutStageConvAv);

			// PlanTravail
			datePreparationPlanTravail = dateFormat.format(utilServices.addDAYStoTODAY(1 * 7, startTrDate));

			// Newspaper
			dateDepotNewspaperFormed = dateFormat.format(utilServices.addDAYStoTODAY(2 * 7, startTrDate));

			// Balance Sheets
			dateRemiseBilanDebutStage = dateFormat.format(utilServices.addDAYStoTODAY(6 * 7, startTrDate));
			dateRemiseBilanMilieuStage = dateFormat.format(utilServices.addDAYStoTODAY(15 * 7, startTrDate));
			dateRemiseBilanFinStage = dateFormat.format(utilServices.addDAYStoTODAY(25 * 7, startTrDate));

			// Presentations & Visite
			dateLancementPresentation1 = dateFormat.format(utilServices.addDAYStoTODAY(9 * 7, startTrDate));
			dateLancementPresentation2 = dateFormat.format(utilServices.addDAYStoTODAY(21 * 7, startTrDate));
			dateLancementVisiteMiParcours = dateFormat.format(utilServices.addDAYStoTODAY(13 * 7, startTrDate));

			// Reports
			dateDepotRapportV1 = dateFormat.format(utilServices.addDAYStoTODAY(20 * 7, startTrDate));
			dateDepotRapportVFinale = dateFormat.format(utilServices.addDAYStoTODAY(26 * 7, startTrDate));

			// Planning
			System.out.println("Planning --------------> Date Demande Convention: " + dateNewestDebutStageConvAv);
			System.out.println("Planning --------------> Date Remise Plan Travail: " + dateRemiseFichePFE);
			System.out.println("Planning --------------> Lancement Stage PFE: " + dateNewestDebutStageConvAv);
			System.out.println("Planning --------------> Date Preparation Plan Travail: " + datePreparationPlanTravail);
			System.out.println("Planning --------------> Dépôt Bilan Version 1: " + dateRemiseBilanDebutStage);
			System.out.println("Planning --------------> Dépôt Bilan Version 2: " + dateRemiseBilanMilieuStage);
			System.out.println("Planning --------------> Dépôt Rapport Version 1: " + dateDepotRapportV1);
			System.out.println("Planning --------------> Dépôt Bilan Version 3: " + dateRemiseBilanFinStage);
			System.out.println("Planning --------------> Dépôt Rapport Version 2: " + dateDepotRapportVFinale);
			System.out.println("Planning --------------> Date Lancement Restitution 1: " + dateLancementPresentation1);
			System.out.println("Planning --------------> Date Lancement Restitution 2: " + dateLancementPresentation2);
			System.out.println(
					"Planning --------------> Date Lancement Visite Mi-Parcours: " + dateLancementVisiteMiParcours);
		}

		// List<StudentTimelineDto> stds = new ArrayList<StudentTimelineDto>();
		// stds.add(new StudentTimelineDto("Demande\n" + " " + " " + " " + " " + "
		// Convention " + " " + " " + " ", dateConvention, "submitAgreement"));
		// stds.add(new StudentTimelineDto("Lancement\n" + " " + " " + " " + " " + "
		// Stage PFE " + " " + " " + " ", dateNewestDebutStageConvAv, "myDocuments"));
		// stds.add(new StudentTimelineDto("Remise\n" + " " + " " + " " + " " + " Plan
		// de Travail " + " " + " " + " ", dateRemiseFichePFE, "consultESPFile"));
		// stds.add(new StudentTimelineDto("Dépôt\n Journal de Bord",
		// dateDepotNewspaperFormed, "uploadNewspaper"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 1",
		// dateDepotBalanceSheetFormedV1, "uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 2",
		// dateDepotBalanceSheetFormedV2, "uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 1",
		// dateDepotRapportFormedV1, "uploadReport"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 3",
		// dateDepotBalanceSheetFormedV3, "uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 2",
		// dateDepotRapportFormedV2, "uploadReport"));

		String nbrConvAven = convDetails.size() + "-" + validatedAVN;

		System.out.println("ùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùù-----> " + nbrConvAven);

		List<StudentTimelineDto> stds = new ArrayList<StudentTimelineDto>();
		stds.add(new StudentTimelineDto(1, "Lancement\n" + " " + " " + " " + " " + " Stage PFE " + " " + " ",
				dateNewestDebutStageConvAv, "myDocuments", "Lancement Stage PFE", ""));
		stds.add(new StudentTimelineDto(2, "Demande\n" + " " + " " + " " + " " + " Convention " + " " + " " + " ",
				dateConvention, "submitAgreement", "Historique Conventions", nbrConvAven));
		stds.add(new StudentTimelineDto(3, "Remise\n" + " " + " " + " " + " " + " Plan Travail " + " " + " " + " ",
				dateRemiseFichePFE, "consultESPFile", "Historique Plans Travail", ""));
		stds.add(new StudentTimelineDto(4, "Dépôt\n Journal de Bord", dateDepotNewspaperFormed, "uploadNewspaper",
				"Historique Journaux de Bord", ""));
		stds.add(new StudentTimelineDto(5, "Dépôt\n Bilan Version 1", dateRemiseBilanDebutStage, "uploadBalanceSheet",
				"Historique Bilans", ""));
		stds.add(new StudentTimelineDto(6, "Lancement\n" + " " + " " + " Restitution 1", dateLancementPresentation1,
				"myStudentTimeline", "Détails Première Restitution", ""));
		stds.add(new StudentTimelineDto(7, "Lancement\n Visite Mi-Parcours*", dateLancementVisiteMiParcours,
				"myStudentTimeline", "Détails Visite Mi-Parcours", ""));
		stds.add(new StudentTimelineDto(8, "Dépôt\n Bilan Version 2", dateRemiseBilanMilieuStage, "uploadBalanceSheet",
				"Historique Bilans", ""));
		stds.add(new StudentTimelineDto(9, "Dépôt\n Rapport Version 1", dateDepotRapportV1, "uploadReport",
				"Historique Rapports", ""));
		stds.add(new StudentTimelineDto(10, "Lancement\n Restitution 2*", dateLancementPresentation2,
				"myStudentTimeline", "Détails Deuxième Restitution", ""));
		stds.add(new StudentTimelineDto(11, "Dépôt\n Bilan Version 3", dateRemiseBilanFinStage, "uploadBalanceSheet",
				"Historique Bilans", ""));
		stds.add(new StudentTimelineDto(12, "Dépôt\n Rapport Version 2", dateDepotRapportVFinale, "uploadReport",
				"Historique Rapports", ""));
		stds.add(new StudentTimelineDto(13, "Enquête\n Premier Emploi", dateDepotRapportVFinale, "myStudentTimeline", "Enquête Premier Emploi", ""));

		// stds.add(new StudentTimelineDto("Lancement\n" + " " + " " + " " + " " + "
		// Stage PFE " + " " + " " + " ", dateNewestDebutStageConvAv, "/myDocuments"));
		// stds.add(new StudentTimelineDto("Demande\n" + " " + " " + " " + " " + "
		// Convention " + " " + " " + " ", dateConvention, "/submitAgreement"));
		// stds.add(new StudentTimelineDto("Remise\n" + " " + " " + " " + " " + " Plan
		// Travail " + " " + " " + " ", dateRemiseFichePFE, "/addESPFile"));
		// stds.add(new StudentTimelineDto("Dépôt\n Journal de Bord",
		// dateDepotNewspaperFormed, "/uploadNewspaper"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 1",
		// dateRemiseBilanDebutStage, "/uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Lancement\n Restitution 1*",
		// dateLancementPresentation1, "/synopsisAndNews"));
		// stds.add(new StudentTimelineDto("Lancement\n Visite Mi-Parcours*",
		// dateLancementVisiteMiParcours, "/synopsisAndNews"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 2",
		// dateRemiseBilanMilieuStage, "/uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 1",
		// dateDepotRapportV1, "/uploadReport"));
		// stds.add(new StudentTimelineDto("Lancement\n Restitution 2*",
		// dateLancementPresentation2, "/synopsisAndNews"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 3",
		// dateRemiseBilanFinStage, "/uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 2",
		// dateDepotRapportVFinale, "/uploadReport"));

		// List<StudentTimelineDto> stds = new ArrayList<StudentTimelineDto>();
		// stds.add(new StudentTimelineDto("Lancement\n" + " " + " " + " " + " " + "
		// Stage PFE " + " " + " " + " ", dateNewestDebutStageConvAv, "/myDocuments"));
		// stds.add(new StudentTimelineDto("Demande\n" + " " + " " + " " + " " + "
		// Convention " + " " + " " + " ", dateConvention, "/submitAgreement"));
		// stds.add(new StudentTimelineDto("Remise\n" + " " + " " + " " + " " + " Plan
		// de Travail " + " " + " " + " ", dateRemiseFichePFE, "/addESPFile"));
		// // stds.add(new StudentTimelineDto("Preparation\n Plan Travail*",
		// datePreparationPlanTravail, "/uploadNewspaper"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 1",
		// dateRemiseBilanDebutStage, "/uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Lancement\n Restitution 1*",
		// dateLancementPresentation1, "/synopsisAndNews"));
		// stds.add(new StudentTimelineDto("Lancement\n Visite Mi-Parcours*",
		// dateLancementVisiteMiParcours, "/synopsisAndNews"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 2",
		// dateRemiseBilanMilieuStage, "/uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 1",
		// dateDepotRapportV1, "/uploadReport"));
		// stds.add(new StudentTimelineDto("Lancement\n Restitution 2*",
		// dateLancementPresentation2, "/synopsisAndNews"));
		// stds.add(new StudentTimelineDto("Dépôt\n Bilan Version 3",
		// dateRemiseBilanFinStage, "/uploadBalanceSheet"));
		// stds.add(new StudentTimelineDto("Dépôt\n Rapport Version 2",
		// dateDepotRapportVFinale, "/uploadReport"));

		return stds;
	}

	@GetMapping("/applyForCancelAgreement/{idEt}/{cancellingMotif}")
	public void applyForCancelAgreement(@PathVariable String idEt, @PathVariable String cancellingMotif) {

		System.out.println("--------------------------> idEt: " + idEt);
		System.out.println(
				"--------------------------> cancellingMotif: " + utilServices.decodeEncodedValue(cancellingMotif));

		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);
		System.out.println("--------------------------> Convention: " + convention.getTraiter());

		Timestamp dateSD = new Timestamp(System.currentTimeMillis());

		convention.setTraiter("04");
		convention.setMotifAnnulation(utilServices.decodeEncodedValue(cancellingMotif));
		convention.setDateSaisieDemande(dateSD);
		conventionRepository.save(convention);

		if(!fichePFERepository.findFichePFEByStudent(idEt).isEmpty())
    	{
        	FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
    		
//    		FichePFE fichePFE1 = fichePFERepository.sars(idEt).get(0);
    		// // System.out.println("---------------------------------------------- type-1: " + libTRTFiche);
    		
    		TraitementFichePFE afp = new TraitementFichePFE();
    		
//    		List<String> las = annulationFicheRepository.findAllAnnulationFiches(idEt
//    				//, fichePFE.getIdFichePFE().getDateDepotFiche()
//    				);
//    		Integer idAnnulationFiche = las.size() + 1;
    		//String typeTrtFiche = codeNomenclatureRepository.findcodeNomeTFByCodeStrAndLibNome("102", libTRTFiche);
    		  System.out.println("------------------------fffffffffffffffffffff-------- END - 1 => Date Depot Plan Travail: " + fichePFE.getIdFichePFE().getDateDepotFiche());
    		
    		TraitementFichePK annulationFichePK = new TraitementFichePK(fichePFE.getIdFichePFE(), dateSD);
    		
    		afp.setTraitementFichePK(annulationFichePK);
    		afp.setTypeTrtFiche("12");
    		afp.setTypeTrtConv("AUT");
    		afp.setDescription("Annulation AUTOMATIQUE de Plan de Travail");
    		afp.setEtatTrt("01");
    		afp.setFichePFETraitementFichePFE(fichePFE);
    		
    		traitementFicheRepository.save(afp);
    		
    	}

		// ***********************************************************Send Notification
		// by Mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String convSendRequestDate = dateFormata.format(new Date());

		// String csfno = mailCompSuperv.substring(0, mailCompSuperv.lastIndexOf("@"));
		// String csfnn = csfno.substring(0, csfno.lastIndexOf(".")).toUpperCase();
		// String cslnn = csfno.substring(csfno.lastIndexOf(".") + 1).toUpperCase();
		// String csfn = csfnn + " " + cslnn;

		String content = "Nous voulons vous informer par le présent mail que vous avez <strong><font color=grey> envoyé une Demande Annulation de Convention </font></strong>"
				+ "le <font color=red>" + convSendRequestDate + " </font>.<br/>"
				+ "<font color=grey>Remarque: L'Annulation de votre Convention sera suivie par une Annulation AUTOMATIQUE de votre Plan de Travail.</font><br/>"
				+ "Merci de patienter jusqu'à le traitement de votre demande par Mme/M le Responsable Services des Stage.";

		String studentMail = utilServices.findStudentMailById(idEt); // Server DEPLOY_SERVER
		// String studentMail = utilServices.findStudentMailById(idEt).substring(0,
		// utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";

		String AEMail = utilServices.findMailPedagogicalEncadrant(idEt); // DEPLOY_SERVER
		// String AEMail = "saria.essid@esprit.tn";

		// String EEMail = conv.get().getEntrepriseAccueilConvention().getAddressMail();
		// //DEPLOY_SERVER
		// StriDEPLOY_SERVERia.essid@esprit.tn";

		String mailRSS = responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"); // Server DEPLOY_SERVER
																									// RSS =
																									// "ramben@esprit.tn";

		System.out.println(
				"START-------------------------ssssssssssss--------------------------> Mail SENT TO: " + AEMail); // DEPLOY_SERVERtln("==>StudentCJ
																													// Mail
																													// :
																													// "
																													// +
																													// utilServices.findStudentMailById(idEt));
		System.out.println("==>AEMail : " + utilServices.findMailPedagogicalEncadrant(idEt));
		System.out.println("==>mailRSS : " + responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"));
		System.out.println("END -----------------------------------------------------------------> *");

		List<String> receiversCC = new ArrayList<String>();
		receiversCC.add(AEMail);
		receiversCC.add(mailRSS);
		String responsibles = receiversCC.stream().collect(Collectors.joining(", "));

		utilServices.sendMailWithManyCC("Demande Annulation de Convention", studentMail, responsibles, content);

		/*
		 * // ***********************************************************Send
		 * Notification by Mail DateFormat dateFormata = new
		 * SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); String convSendRequestDate =
		 * dateFormata.format(new Date());
		 * 
		 * String csfno = mailCompSuperv.substring(0, mailCompSuperv.lastIndexOf("@"));
		 * String csfnn = csfno.substring(0, csfno.lastIndexOf(".")).toUpperCase();
		 * String cslnn = csfno.substring(csfno.lastIndexOf(".") + 1).toUpperCase();
		 * String csfn = csfnn + " " + cslnn;
		 * 
		 * String content =
		 * "Nous voulons vous informer par le présent mail que vous avez <strong><font color=grey> envoyé une Demande Annulation de Convention </font></strong>"
		 * + "le <font color=red>" + convSendRequestDate + " </font>.\r\n" +
		 * "Par conséquent, pour que Mme/M le Responsable Services des Stage puisse traiter cette demande, "
		 * + "On invite votre Encadrant Entreprise Mme/M " + csfn +
		 * " pour la confirmer via son espace http://localhost:1501/login " + "" +
		 * "PFE a été annulée <font color=red> automatiquement</font> .";
		 * 
		 * // String studentMail = utilServices.findStudentMailById(idEt); // Server
		 * DEPLOY_SERVER String studentMail = "student@esprit.tn";
		 * 
		 * // String AEMail = utilServices.findMailPedagogicalEncadrant(idEt);
		 * //DEPLOY_SERVER String AEMail = "saria.essiDEPLOY_SERVER // String EEMail =
		 * conv.get().getEntrepriseAccueilConvention().getAddressMail(); //DEPLOY_SERVER
		 * String EEMail =DEPLOY_SERVERprit.tn";
		 * 
		 * // String mailRSS =
		 * responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"); //
		 * Server DEPLOY_SERVER StriDEPLOY_SERVERmla@esprit.tn";
		 * 
		 * System.out.
		 * println("START---------------------------------------------------> Mail SENT TO: "
		 * + AEMail); System.out.println(DEPLOY_SERVERil : " +
		 * utilServices.findStudentMailById(idEt)); System.out.println("==>AEMail : " +
		 * utilServices.findMailPedagogicalEncadrant(idEt));
		 * System.out.println("==>EEMail : " +
		 * conv.get().getEntrepriseAccueilConvention().getAddressMail());
		 * System.out.println("==>mailRSS : " +
		 * responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"));
		 * System.out.
		 * println("END -----------------------------------------------------------------> *"
		 * );
		 * 
		 * 
		 * List<String> receiversCC = new ArrayList<String>(); receiversCC.add(AEMail);
		 * receiversCC.add(mailRSS); receiversCC.add(EEMail); String responsibles =
		 * receiversCC.stream().collect(Collectors.joining(", "));
		 * 
		 * utilServices.sendMailWithManyCC("Validation Demande Annulation de Convention"
		 * , studentMail, responsibles, content);
		 */
	}

	// @GetMapping("/applyForCancelAgreement/{idEt}/{cancellingMotif}/{mailCompSuperv}")
	// public void applyForCancelAgreement(@PathVariable String idEt, @PathVariable
	// String cancellingMotif, @PathVariable String mailCompSuperv)
	// {
	//
	// System.out.println("--------------------------> idEt: " + idEt);
	// System.out.println("--------------------------> cancellingMotif: " +
	// utilServices.decodeEncodedValue(cancellingMotif));
	// System.out.println("--------------------------> mailCompSuperv: " +
	// utilServices.decodeEncodedValue(mailCompSuperv));
	//
	// Convention convention =
	// conventionRepository.findConventionByIdEt(idEt).get(0);
	// System.out.println("--------------------------> Convention: " +
	// convention.getTraiter());
	//
	// Timestamp dateSD = new Timestamp(System.currentTimeMillis());
	//
	// convention.setTraiter("04");
	// convention.setMotifAnnulation(utilServices.decodeEncodedValue(cancellingMotif));
	// convention.setDateSaisieDemande(dateSD);
	// convention.setMailEncadrantEntreprise(utilServices.decodeEncodedValue(mailCompSuperv));
	// conventionRepository.save(convention);
	//
	//
	// FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
	//
	//// FichePFE fichePFE1 = fichePFERepository.sars(idEt).get(0);
	// // // System.out.println("----------------------------------------------
	// type-1: " + libTRTFiche);
	//
	// TraitementFichePFE afp = new TraitementFichePFE();
	//
	//// List<String> las = annulationFicheRepository.findAllAnnulationFiches(idEt
	//// //, fichePFE.getIdFichePFE().getDateDepotFiche()
	//// );
	//// Integer idAnnulationFiche = las.size() + 1;
	// //String typeTrtFiche =
	// codeNomenclatureRepository.findcodeNomeTFByCodeStrAndLibNome("102",
	// libTRTFiche);
	// System.out.println("------------------------fffffffffffffffffffff-------- END
	// - 1 => Date Depot Plan Travail: " +
	// fichePFE.getIdFichePFE().getDateDepotFiche());
	//
	// TraitementFichePK annulationFichePK = new
	// TraitementFichePK(fichePFE.getIdFichePFE(), dateSD);
	//
	// afp.setTraitementFichePK(annulationFichePK);
	// afp.setTypeTrtFiche("12");
	// afp.setTypeTrtConv("AUT");
	// afp.setDescription("CANCEL CONV -> AUTO -> CANCEL FICHE");
	// afp.setEtatTrt("01");
	// afp.setFichePFETraitementFichePFE(fichePFE);
	//
	// traitementFicheRepository.save(afp);
	//
	// /*
	// // ***********************************************************Send
	// Notification by Mail
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// String convSendRequestDate = dateFormata.format(new Date());
	//
	// String csfno = mailCompSuperv.substring(0, mailCompSuperv.lastIndexOf("@"));
	// String csfnn = csfno.substring(0, csfno.lastIndexOf(".")).toUpperCase();
	// String cslnn = csfno.substring(csfno.lastIndexOf(".") + 1).toUpperCase();
	// String csfn = csfnn + " " + cslnn;
	//
	// String content = "Nous voulons vous informer par le présent mail que vous
	// avez <strong><font color=grey> envoyé une Demande Annulation de Convention
	// </font></strong>"
	// + "le <font color=red>" + convSendRequestDate + " </font>.\r\n"
	// + "Par conséquent, pour que Mme/M le Responsable Services des Stage puisse
	// traiter cette demande, "
	// + "On invite votre Encadrant Entreprise Mme/M " + csfn
	// + " pour la confirmer via son espace http://localhost:1501/login "
	// + ""
	// + "PFE a été annulée <font color=red> automatiquement</font> .";
	//
	// // String studentMail = utilServices.findStudentMailById(idEt); // Server
	// DEPLOY_SERVER
	// String studentMail = "student@esprit.tn";
	//
	// // String AEMail = utilServices.findMailPedagogicalEncadrant(idEt);
	// //DEPLOY_SERVER
	// String AEMail = "saria.esDEPLOY_SERVER
	//
	// // String EEMail =
	// conv.get().getEntrepriseAccueilConvention().getAddressMail(); //DEPLOY_SERVER
	// String EEMailDEPLOY_SERVEResprit.tn";
	//
	// // String mailRSS =
	// responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"); //
	// Server DEPLOY_SERVER
	// StDEPLOY_SERVERramben@esprit.tn";
	//
	// System.out.println("START--------------------------------------------------->
	// Mail SENT TO: " + AEMail);
	// System.out.printlDEPLOY_SERVERMail : " +
	// utilServices.findStudentMailById(idEt));
	// System.out.println("==>AEMail : " +
	// utilServices.findMailPedagogicalEncadrant(idEt));
	// System.out.println("==>EEMail : " +
	// conv.get().getEntrepriseAccueilConvention().getAddressMail());
	// System.out.println("==>mailRSS : " +
	// responsableServiceStageRepository.findRespServStgMailById("SR-STG-IT"));
	// System.out.println("END
	// -----------------------------------------------------------------> *");
	//
	//
	// List<String> receiversCC = new ArrayList<String>();
	// receiversCC.add(AEMail); receiversCC.add(mailRSS); receiversCC.add(EEMail);
	// String responsibles = receiversCC.stream().collect(Collectors.joining(", "));
	//
	// utilServices.sendMailWithManyCC("Validation Demande Annulation de
	// Convention", studentMail, responsibles, content);
	// */
	// }

	@GetMapping("/nbrValidatedConvention")
	public Integer findNbrValidatedConvention() {
		return conventionRepository.getAllValidatedConvention().size();
	}

	@GetMapping("/nbrDemandesAnnulationConvention")
	public Integer findNbrDemandesAnnulationConvention() {
		return conventionRepository.getAllDemandesAnnulationConvention().size();
	}

	@GetMapping("/nbrDemandesAnnulationConventionNotTreated")
	public Integer findNbrDemandesAnnulationConventionNotTreated() {
		return conventionRepository.getAllDemandesAnnulationConventionNotTreated().size();
	}

	@GetMapping("/nbrDepositedConvention")
	public Integer findNbrDepositedConvention() {
		return conventionRepository.getAllDepositedConvention().size();
	}

	@GetMapping("/ficheTitreDateEtatByIdEt/{idEt}")
	public FichePFETitreDateDepotStatusDto findFicheTitreDateEtatByIdEt(@PathVariable String idEt) {
		List<FichePFETitreDateDepotStatusDto> lfddss = fichePFERepository.findFicheTitreDateEtatByIdEt(idEt);
		FichePFETitreDateDepotStatusDto fdds = new FichePFETitreDateDepotStatusDto();

		if (!lfddss.isEmpty()) {
			fdds = lfddss.get(0);

			System.out.println("_______________STATUS_____________ 1: " + fdds.getEtat());

			if (!fdds.getEtat().equalsIgnoreCase("SAUVEGARDEE")) {
				// Got Full Timestamp Nbr caracters
				String beforeFS = fdds.getFullDateDepot();
				Integer notAccountedNbr = 0;
				String rest0Nbrs = "";
				if (beforeFS.length() < 9) {
					notAccountedNbr = 9 - (beforeFS.length());

					for (int i = 0; i < notAccountedNbr; i++) {
						System.out.println(i);
						rest0Nbrs = rest0Nbrs.concat("0");
					}
				}
				String fullTimeStamp = beforeFS + rest0Nbrs;

				System.out.println("____________________________ 1: " + beforeFS);
				System.out.println("____________________________ 2: " + Timestamp.valueOf(fdds.getFullDateDepot()));

				// Date Remise
				Timestamp dateRemiseFichePFE = traitementFicheRepository
						.findDateRemiseFichePFEByStudentAndFicheSTR(idEt, fullTimeStamp).get(0);

				DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
				String dateRemiseSTR = dateFormata.format(dateRemiseFichePFE);

				fdds.setDateRemise(dateRemiseSTR);
			}
		}
		return fdds;
	}

	@GetMapping("/studentFullName/{idEt}")
	public String findStudentFullNameById(@PathVariable String idEt) {
		System.out.println("----------------------------------> StudentCJ ID CJ-ALT: " + idEt);
		return utilServices.findStudentFullNameById(idEt);
	}

	@GetMapping("/studentConventionsHistoric/{idEt}")
	public List<ConventionHistoriqueDto> studentConventionsHistoric(@PathVariable String idEt) {
		List<ConventionHistoriqueDto> lcs = conventionRepository.findConventionsDtoHistoriqueByIdEt(idEt);

		for (ConventionHistoriqueDto ch : lcs) {
			EntrepriseAccueil ea = conventionRepository.findentrepriseAccueilByConvention(idEt, ch.getDateConvention());
			System.out.println("-------------LOL-----< " + ea.getDesignation());

			List<AvenantHistoryDto> avenantsHistoryDto = avenantRepository.findAvenantDtoByIdEtAndDateConvention(idEt,
					ch.getDateConvention());

			if (ea.getPays() == null) {
				ch.setCompanyNomPays("--");
			} else {
				ch.setCompanyNomPays(ea.getPays().getNomPays());
			}

			if (ea.getSecteurEntreprise() == null) {
				ch.setCompanyLibelleSecteur("--");
			} else {
				ch.setCompanyLibelleSecteur(ea.getSecteurEntreprise().getLibelleSecteur());
			}

			ch.setAvenantsHistoryDto(avenantsHistoryDto);

		}

		return lcs;
	}

	@GetMapping("/fichePFE/{idEt}")
	public FichePFEDto findFichePFEByStudentCode(@PathVariable String idEt) {

		StudentDto sd = utilServices.findStudentDtoById(idEt);

		List<Object[]> opfes = fichePFERepository.findFichePFE(idEt);

		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);
		EntrepriseAccueil ea = convention.getEntrepriseAccueilConvention();

		String titre = null;
		String description = null;
		String anneeDeb = null;
		String dateDepotFiche = null;

		String etatFiche = null;

		String dateConvention = null;
		String pair = null;

		String ganttDiagFP = null;

		for (int i = 0; i < opfes.size(); i++) {
			titre = (String) opfes.get(i)[0];
			description = (String) opfes.get(i)[1];
			anneeDeb = (String) opfes.get(i)[2];
			dateDepotFiche = (String) opfes.get(i)[3];
			// eaa = (EntrepriseAccueil) opfes.get(i)[4];
			// codeACT = (Integer) opfes.get(i)[5];
			// codeNom = (Integer) opfes.get(i)[6];
			// // convention = (Convention) opfes.get(i)[7];
			// dateConvention = (String) opfes.get(i)[7];
			etatFiche = (String) opfes.get(i)[4];
			pair = (String) opfes.get(i)[5];
			ganttDiagFP = (String) opfes.get(i)[6];
		}

		System.out.println(
				"------$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$----------> SARS: "
						+ ganttDiagFP);

		List<Technologie> lts = fichePFERepository.findAllTechnologiesByStudentAndFichePFE(idEt, dateDepotFiche);

		StudentIdFullNameDto pairdto = null;
		if (pair != null) {
			pairdto = new StudentIdFullNameDto(pair, utilServices.findStudentFullNameById(pair));
		}

		Integer s = 1;
		FichePFEDto fichePFEDto = new FichePFEDto();
		if (s == 1) {
			List<TechnologyDto> ltds = new ArrayList<TechnologyDto>();
			for (Technologie t : lts) {
				TechnologyDto techUnit = new TechnologyDto(t.getName(), "A");
				ltds.add(techUnit);
			}

			List<FunctionnalityDto> lfds = new ArrayList<FunctionnalityDto>();
			List<Object[]> lofs = functionnalityRepository.findAllFuncs(idEt, dateDepotFiche);
			for (int i = 0; i < lofs.size(); i++) {
				String funcName = (String) lofs.get(i)[0];
				String funcDesc = (String) lofs.get(i)[1];

				FunctionnalityDto funcUnit = new FunctionnalityDto(funcName, funcDesc);
				lfds.add(funcUnit);
			}

			List<ProblematicDto> lpds = new ArrayList<ProblematicDto>();
			List<String> lps = problematicRepository.findAllProblematics(idEt, dateDepotFiche);
			for (String p : lps) {
				ProblematicDto probUnit = new ProblematicDto(p);
				lpds.add(probUnit);
			}

			// List<EncadrantEntreprise> lrs = convention.getEncadrantEntreprises();
			// List<ResponsibleDto> lsds = new ArrayList<ResponsibleDto>();
			// for(EncadrantEntreprise e : lrs)
			// {
			// ResponsibleDto supervUnit = new ResponsibleDto(e.getFirstName(),
			// e.getLastName(), e.getNumTelephone(), e.getEmail());
			// lsds.add(supervUnit);
			// }

			fichePFEDto = new FichePFEDto(titre, description, dateDepotFiche, anneeDeb.toString(), etatFiche, sd, lpds,
					lfds, ltds, pairdto, ganttDiagFP);
		}

		System.out.println(
				"------$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$--------fff--> SARS: "
						+ fichePFEDto.getGanttDiagramFullPath());

		return fichePFEDto;
	}

	@GetMapping("/studentFichePFEsHistoric/{idEt}")
	public List<FichePFEHistoryDto> findFichePFEHistoryDto(@PathVariable String idEt) {
		List<FichePFEHistoryDto> lfs = fichePFERepository.findAllFichePFEsByStudent(idEt);

		System.out.println("----------------> SARS: " + lfs.size());

		// Company
		// EntrepriseAccueilForConvHistDto companyHistoryDto =
		// conventionRepository.findEntrepriseAccueilForConvHistDtoByIdEt(idEt).get(0);

		for (int i = 0; i < lfs.size(); i++) {

			EntrepriseAccueilForFichePFEHistDto companyHistoryDto = conventionRepository
					.findEntrepriseAccueilForFichePFEHistDtoByIdEtAndFichePFE(idEt, lfs.get(i).getDateDepotFiche());

			ConventionPaysSecteurDto convPaysSecteur = conventionRepository
					.findConventionByStudentAndDateDepotFiche(idEt, lfs.get(i).getDateDepotFiche());

			System.out.println("-----------------------> SARS-1: " + lfs.get(i).getTitreProjet());
			System.out.println("-----------------------> SARS-2: " + lfs.get(i).getPathJournalStg());

			if (convPaysSecteur != null) {
				if (convPaysSecteur.getCompanyNomPays() != null) {
					companyHistoryDto.setNomPays(convPaysSecteur.getCompanyNomPays());
				}

				if (convPaysSecteur.getCompanyLibelleSecteur() != null) {
					companyHistoryDto.setLibelleSecteur(convPaysSecteur.getCompanyLibelleSecteur());
				}
			} else {
				companyHistoryDto.setLibelleSecteur("--");
			}

			// Problematics
			List<ProblematicDto> problematics = new ArrayList<ProblematicDto>();
			List<String> lps = problematicRepository.findAllProblematicsByStudentAndFichePFE(idEt,
					lfs.get(i).getDateDepotFiche());
			for (String p : lps) {
				ProblematicDto probUnit = new ProblematicDto(p);
				problematics.add(probUnit);
			}

			// Functionnalities
			List<FunctionnalityDto> functionnalities = new ArrayList<FunctionnalityDto>();
			List<Object[]> lofs = functionnalityRepository.findAllFunctionnalitiesByStudentAndFichePFE(idEt,
					lfs.get(i).getDateDepotFiche());
			for (int j = 0; j < lofs.size(); j++) {
				String funcName = (String) lofs.get(j)[0];
				String funcDesc = (String) lofs.get(j)[1];

				FunctionnalityDto funcUnit = new FunctionnalityDto(funcName, funcDesc);
				functionnalities.add(funcUnit);
			}

			// Technologies
			List<Technologie> lts = fichePFERepository.findTechnologiesByStudentAndFichePFE(idEt,
					lfs.get(i).getDateDepotFiche());
			List<TechnologyDto> technologies = new ArrayList<TechnologyDto>();
			for (Technologie t : lts) {
				TechnologyDto techUnit = new TechnologyDto(t.getName(), "A");
				technologies.add(techUnit);
			}

			// Supervisors
			List<EntrepriseSupervisorDto> lesds = encadrantEntrepriseRepository
					.findEntrepriseSupervisorsByStudentAndFiche(idEt, lfs.get(i).getDateDepotFiche());

			// Treatments
			List<TraitementFicheHistoryDto> treatmentFiches = encadrantEntrepriseRepository
					.findTraitementFicheHistoryDtosByStudentAndFiche(idEt, lfs.get(i).getDateDepotFiche());
			System.out.println("------------------------> Size: " + treatmentFiches.size());

			lfs.get(i).setPairFullName(utilServices.findBinomeFullNameByStudentId(idEt));
			lfs.get(i).setCompanyDto(companyHistoryDto);
			lfs.get(i).setCompanySupervisors(lesds);
			lfs.get(i).setProblematics(problematics);
			lfs.get(i).setFunctionnalities(functionnalities);
			lfs.get(i).setTechnologies(technologies);
			lfs.get(i).setTreatmentFiches(treatmentFiches);

			// System.out.println("------------------------> 1: " +
			// lfs.get(i).getCompanyDto().getDesignation());

		}

		return lfs;
	}

	@GetMapping("/conventionDateConvDateDebutStageStatus/{idEt}")
	public ResponseEntity<?> findConventionDateConvDateDebutStageStatusByIdEt(@PathVariable String idEt) {
		return ResponseEntity.ok(conventionRepository.findConventionDateConvDateDebutStageStatusByIdEt(idEt));
	}

	@GetMapping("/findDepotReportsStatusDateByIdEt/{idEt}")
	public FichePFEDateDepotReportsEtatReportsDto findDepotReportsDateEtatByIdEt(@PathVariable String idEt) {
		FichePFEDateDepotReportsEtatReportsDto f = new FichePFEDateDepotReportsEtatReportsDto();

		if (!fichePFERepository.findDatesDepotFichePFEByStudent(idEt).isEmpty()) {
			System.out.println("--------------d------ ENTER 1");
			String dateDepotFichePFE = fichePFERepository.findDatesDepotFichePFEByStudent(idEt).get(0);
			List<FichePFEDateDepotReportsEtatReportsDto> reportsDateDepotEtat = fichePFERepository
					.findDepotReportsDateEtatByIdEtAndFichePFE(idEt, dateDepotFichePFE);

			if (!reportsDateDepotEtat.isEmpty()) {
				f = reportsDateDepotEtat.get(0);
				System.out.println("-------------------- ENTER - IF");
			}
			System.out.println("-------------------- ENTER 2");
		}

		return f;
	}

	// @GetMapping("/fichePFEStatus/{idEt}")
	// public String findFichePFEByStudentCode(@PathVariable String idEt)
	// {
	// return fichePFERepository.findFichePFEStatus(idEt).get(0);
	// }

	@GetMapping("/fichePFEStatus/{idEt}")
	public String findFichePFEStatus(@PathVariable String idEt) {
		String ESPFileStatus = "";
		List<String> lss = fichePFERepository.findFichePFEStatus(idEt);
		if (!lss.isEmpty()) {
			ESPFileStatus = lss.get(0);
		}

		return ESPFileStatus;
	}

	@GetMapping("/studentHierarchy/{idEt}")
	public StudentHierarchyDto findStudentHierarchyDtoById(@PathVariable String idEt) {

		String coursesPole = utilServices.findCoursesPoleByIdEt(idEt);
		String classe = utilServices.findCurrentClassByIdEt(idEt);
		String department = utilServices.findDepartmentByClass(classe);
		String option = utilServices.findOptionByClass(classe, optionRepository.listOptionsByYear("2021"));
		String pedagogicalEncadrant = utilServices.findFullNamePedagogicalEncadrant(idEt);

		StudentHierarchyDto studentHierarchyDto = new StudentHierarchyDto(coursesPole, department,
				option.replace("_01", ""), classe, pedagogicalEncadrant);

		return studentHierarchyDto;
	}

	@GetMapping("/entrepriseSupervisorsByStudentAndFiche/{idEt}/{dateDepot}")
	public List<EntrepriseSupervisorDto> findEntrepriseSupervisorsByStudentAndFiche(@PathVariable String idEt,
			@PathVariable String dateDepot) {

		List<EntrepriseSupervisorDto> lesds = encadrantEntrepriseRepository
				.findEntrepriseSupervisorsByStudentAndFiche(idEt, dateDepot);
		for (EntrepriseSupervisorDto ee : lesds) {
			System.out.println("------------------------> isd: " + ee.getFirstName());
		}

		return lesds;

		// List<EntrepriseSupervisorDto> lesds = new
		// ArrayList<EntrepriseSupervisorDto>();
		// for(EncadrantEntreprise ee : lees)
		// {
		// lesds.add(new EntrepriseSupervisorDto(ee.getFirstName(), ee.getLastName(),
		// ee.getNumTelephone(), ee.getEmail()));
		// }
		//
		// return lesds;

	}

	@PostMapping("/addFichePFE/{idEt}")
	public void addFichePFE(@PathVariable String idEt, @RequestBody PlanTravailRequest planTravailRequest)
	{

		System.out.println("---------------> ");

		FichePFE fichePFE = new FichePFE();
		fichePFE.setAnneeDeb("2021");

		fichePFE.setDescriptionProjet(planTravailRequest.getProjectDescription());

		fichePFE.setEtatFiche("01");
		fichePFE.setTraineeKind("01");

		fichePFE.setTitreProjet(planTravailRequest.getProjectTitle());

		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);

		FichePFEPK fichePFEPK= new FichePFEPK(convention.getConventionPK(), new Timestamp(System.currentTimeMillis()));
		fichePFE.setIdFichePFE(fichePFEPK);

		fichePFE.setPedagogicalEncadrant(teacherRepository.findByIdEns(utilServices.findIdEncadrantPedagogiqueByStudent(idEt)));

		List<EncadrantEntreprise> listEncadrantEntreprises = new ArrayList<EncadrantEntreprise>();
		for(String es : planTravailRequest.getListOfSupervisors())
		{

			String combSE = es;

			String combSEFN = combSE.substring(3, combSE.lastIndexOf("LN-"));
			String combSELN = combSE.substring(combSE.lastIndexOf("LN-") + 3, combSE.lastIndexOf("NT-"));
			String combSENT = combSE.substring(combSE.lastIndexOf("NT-") + 3, combSE.lastIndexOf("EM-"));
			String combSEEM = combSE.substring(combSE.lastIndexOf("EM-") + 3);

			List<String> lrs = new ArrayList<String>();

			lrs = responsibleRepository.findAllSupervisors();
			Integer idRespInt = 1;

			if(!lrs.isEmpty())
			{
				Integer maxNbr = 0;
				List<Integer> lis = new ArrayList<Integer>();
				for(String s : lrs)
				{
					maxNbr = Integer.valueOf(s.substring(15));
					lis.add(maxNbr);
					Collections.sort(lis);
				}

				idRespInt = lis.get(lis.size()-1) + 1;
			}


			String idRespStr = "COMPANY-SUPERV-" + idRespInt.toString();

			EncadrantEntreprisePK eePK = new EncadrantEntreprisePK(fichePFEPK, idRespStr);

			EncadrantEntreprise companySupervisor = new EncadrantEntreprise(eePK, combSEFN, combSELN, combSENT, combSEEM);

			responsibleRepository.save(companySupervisor);
		}

		List<Technologie> listSelectedTechs = new ArrayList<Technologie>();
		for(String t : planTravailRequest.getListSelectedLibelleTechnologies())
		{
			Technologie tech = technologyRepository.findTechnologyByLabel(t).get(0);
			// Technologie tech = technologyRepository.findByName(t);
			listSelectedTechs.add(tech);
		}
		fichePFE.setTechnologies(listSelectedTechs);

		fichePFE.setBinome(planTravailRequest.getPairId());

		/***************************************************************************************************** Session *****/
		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date conventionDt = convention.getDateDebut();

		int noOfDays = 168;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(conventionDt);
		calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
		Date expectedDateForSTN = calendar.getTime();

		List<com.esprit.gdp.models.Session> sessions = sessionRepository.findAll();
		com.esprit.gdp.models.Session relatedSession = null;
		for(com.esprit.gdp.models.Session s : sessions)
		{
			if(expectedDateForSTN.after(s.getDateDebut()) && expectedDateForSTN.before(s.getDateFin()))
			{
				relatedSession = s;
			}
		}
		fichePFE.setSession(relatedSession);

		/*********************************************/
		String dbadd = "C:/ESP/uploads/";

		fichePFE.setPathDiagrammeGantt(dbadd + planTravailRequest.getDiagramGanttFullPath());
		fichePFE.setDateDepotGanttDiagram(new Date());

		fichePFERepository.save(fichePFE);

		for(String pi : planTravailRequest.getListOfProblematics())
		{
			String timestampToString1 = dateFormata.format(fichePFE.getIdFichePFE().getDateDepotFiche());

			List<String> lfs = new ArrayList<String>();
			lfs = problematicRepository.findAllProblematicsByStudentAndDateDepotFiche(idEt, timestampToString1);
			Integer idProblematic = lfs.size() + 1;

			Problematique problematic = new Problematique();
			ProblematiquePK problematicPK = new ProblematiquePK(fichePFEPK, idProblematic);

			problematic.setProblematicPK(problematicPK);
			problematic.setName(pi);
			problematic.setFichePFEProblematic(fichePFE);
			problematicRepository.save(problematic);

		}

		for(String fi : planTravailRequest.getListOfFunctionnalities())
		{
			String funcLib = fi.substring(0, fi.lastIndexOf("20espdsi21"));
			String funcDesc = fi.substring(fi.lastIndexOf("20espdsi21")+10);

			Fonctionnalite functionnality = new Fonctionnalite();

			List<String> lfs = new ArrayList<String>();
			lfs = functionnalityRepository.findAllFonctionalitiesLibelleByFichePFE(idEt, fichePFE.getIdFichePFE().getDateDepotFiche());
			Integer idFonctionnality = lfs.size() + 1;
			FunctionnalityPK functionnalityPK = new FunctionnalityPK(fichePFE.getIdFichePFE(), idFonctionnality);

			functionnality.setFunctionnalityPK(functionnalityPK);
			functionnality.setName(funcLib);
			functionnality.setDescription(funcDesc);
			functionnality.setFichePFEFunctionnality(fichePFE);

			functionnalityRepository.save(functionnality);
		}

		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = new TraitementFichePFE();
		TraitementFichePK annulationFichePK = new TraitementFichePK(fichePFE.getIdFichePFE(), fichePFE.getIdFichePFE().getDateDepotFiche());
		afp.setTraitementFichePK(annulationFichePK);
		afp.setTypeTrtFiche("01");
		afp.setFichePFETraitementFichePFE(fichePFE);
		traitementFicheRepository.save(afp);

		/***************************************** Notification By Mail *****************************************/

		DateFormat dateFormatz = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateDepotESPFile = dateFormatz.format(new Date());

		String subject = "Sauvegarde Plan de Travail";

		String studentMail = utilServices.findStudentMailById(idEt);  // DEPLOY_SERVER

		String content = "Nous voulons vous informer par le présent mail que vous avez sauvegardé une "
				+ "première version de votre "
				+ "<strong><font color=grey> Plan de Travail </font></strong> "
				+ "le <font color=red> " + dateDepotESPFile + " </font>."
				+ "<br/>Cette Version peut être modifiée à tout moment et elle est accessible uniquement par Vous."
				+ "<br/>Une fois, vous êtes sûrs de vos données insérées, vous pouvez confirmer votre Plan de Travail "
				+ "pour que votre "
				+ "<strong><font color=grey> Encadrant Académique </font></strong> "
				+ "puisse le traiter.";

		utilServices.sendMail(subject, studentMail, content);
		/********************************************************************************************************/

	}

	@PostMapping("/updateFichePFE/{idEt}")
	public void updateFichePFE(@PathVariable String idEt, @RequestBody PlanTravailRequest planTravailRequest)
	{

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

		// // System.out.println("---------------------------------------------- DATE: " + fichePFE.getIdFichePFE().getDateDepotFiche());

		DateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestampToString = dateFormata.format(fichePFE.getIdFichePFE().getDateDepotFiche());

		List<String> lops = new ArrayList<String>();
		lops = problematicRepository.findAllProblematicsByStudentAndDateDepotFiche(idEt, timestampToString);

		//List<String> pdt = problematicRepository.lol1(idEt);

		// // System.out.println("---------------------------------------------- DATE PROB - 1: " + timestampToString);
		//// System.out.println("---------------------------------------------- DATE PROB - 2: " + pdt.get(0));

		for(int i=1; i<lops.size()+1; i++)
		{
			// // System.out.println("---------------------------------------------- ID PROB: " + lops.size() + " - " + i);
			problematicRepository.deleteAllProblematicsByIdFichePFE(idEt, timestampToString);
			// ProblematicPK problematicPK = new ProblematicPK(idEt, fichePFE.getIdFichePFE().getDateDepotFiche(), i);
			// problematicRepository.deleteById(problematicPK);
		}

		// // System.out.println("---------------------------------------------- AFTER DELETE PROBS: " + lops.size());

		List<String> lofs = new ArrayList<String>();
		lofs = functionnalityRepository.findAllFonctionalitiesLibelleByFichePFE(idEt, timestampToString);
		for(int i=1; i<lofs.size()+1; i++)
		{
			// // System.out.println("---------------------------------------------- ID FUNC: " + lofs.size() + " - " + i);
			functionnalityRepository.deleteAllFunctionnalitiesByIdFichePFE(idEt, timestampToString);
			// FunctionnalityPK functionnalityPK = new FunctionnalityPK(idEt, fichePFE.getIdFichePFE().getDateDepotFiche(), i);
			// functionnalityRepository.deleteById(functionnalityPK);
		}

		// // System.out.println("---------------------------------------------- AFTER DELETE FUNCS: " + lofs.size());

		// // System.out.println("---------> StudentCJ to be updated is with code: " + idEt);
		// // System.out.println("---------> Project Title: " + projectTitle);
		// // System.out.println("---------> project Description: " + projectDescription);
		// // System.out.println("---------> project Traineeship Company: " + traineeshipCompany);

//		for(String pi : listOfProblematics)
//		{
//			// System.out.println(listOfProblematics.size() + "---------> problem Item Unit: " + pi);
//		}

		for(String fi : planTravailRequest.getListOfFunctionnalities())
		{
			String funcLib = fi.substring(0, fi.lastIndexOf("20espdsi21"));
			String funcDesc = fi.substring(fi.lastIndexOf("20espdsi21")+10);
			// // System.out.println(listOfFunctionnalities.size() + "---------> functionnality Item Unit: " + funcLib + " - " + funcDesc);
		}

//		for(String slt : listSelectedLibelleTechnologies)
//		{
//			// System.out.println(listSelectedLibelleTechnologies.size() + "---------> selected Libelle Technology: " + slt);
//		}

//		for(String si : listOfSupervisors)
//		{
//			// System.out.println(listOfSupervisors.size() + "---------> supervisor  Unit: " + si);
//		}

		// // System.out.println(" 2 ------------------------------------------------------------------------------------------------ update Plan de Travail");

		fichePFE.setAnneeDeb("2021");

//		FichePFEPK idFichePFE= new FichePFEPK(idEt, fichePFE.getIdFichePFE().getDateDepotFiche());
//		fichePFE.setIdFichePFE(idFichePFE);

		fichePFE.setDescriptionProjet(planTravailRequest.getProjectDescription());

		fichePFE.setTitreProjet(planTravailRequest.getProjectTitle());



		Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);
		// // System.out.println(" LOL.1 ------");
		List<EncadrantEntreprise> listEncadrantEntreprises = new ArrayList<EncadrantEntreprise>();
		// // System.out.println(" LOL.2 ------");
		for(String t : planTravailRequest.getListOfSupervisors())
		{
			System.out.println(" LOL.3 ----^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^--");
			EncadrantEntreprise es = responsibleRepository.findByEmail(t).get(0);
			// // System.out.println(" LOL.4 ------");
			listEncadrantEntreprises.add(es);
			// // System.out.println(" LOL.5 ------");
		}
		// // System.out.println(" LOL.6 ------");
		//convention.setEncadrantEntreprises(listEncadrantEntreprises);

		List<Technologie> listSelectedTechs = new ArrayList<Technologie>();
		for(String t : planTravailRequest.getListSelectedLibelleTechnologies())
		{
			Technologie tech = technologyRepository.findTechnologyByLabel(t).get(0);
			// Technologie tech = technologyRepository.findByName(t);
			listSelectedTechs.add(tech);
		}
		fichePFE.setTechnologies(null);
		fichePFE.setTechnologies(listSelectedTechs);

		// se0109 StudentCJ pair = studentRepository.findByIdEt(pairId).orElseThrow(() -> new RuntimeException("StudentCJ : Verify your credentials !."));
		fichePFE.setBinome(planTravailRequest.getPairId());

		/*********************************************/
		String dbadd = "C:/ESP/uploads/";


		String fileNameLabelExt = planTravailRequest.getDiagramGanttFullPath();
//		String fileNameLab = fileNameLabelExt.substring(0, fileNameLabelExt.lastIndexOf("."));
//		String fileNameExt = fileNameLabelExt.substring(fileNameLabelExt.lastIndexOf("."));
//
//		String formedNameFile = fileNameLab + "espdsi2020" + new Date().getTime() + fileNameExt;

		fichePFE.setPathDiagrammeGantt(dbadd + fileNameLabelExt);
		fichePFE.setDateDepotGanttDiagram(new Date());

		System.out.println("-------####################**************########################-----------> " + fileNameLabelExt);
//		System.out.println("-------############################################-----------> " + fileNameLab);
//		System.out.println("-------############################################-----------> " + fileNameExt);
//		System.out.println("-------############################################-----------> " + formedNameFile);
//		System.out.println("-------############################################-----------> " + dbadd + formedNameFile);

		fichePFERepository.save(fichePFE);

		System.out.println("-------###########********ùùù******########-----------> " + fichePFERepository.findGanttDiagramms(idEt).get(0));

		// *** fichePFERepository.save(fichePFE);

		//List<Fonctionnalite> lfs = new ArrayList<Fonctionnalite>();
		for(String fi : planTravailRequest.getListOfFunctionnalities())
		{
			// // System.out.println("-------------- 2 TO DO : Add a new Functionnality : " + fi + " - " + fichePFE.getIdFichePFE().getIdEt());
			String funcLib = fi.substring(0, fi.lastIndexOf("20espdsi21"));
			String funcDesc = fi.substring(fi.lastIndexOf("20espdsi21")+10);

			Fonctionnalite functionnality = new Fonctionnalite();

			List<String> lfs = new ArrayList<String>();
			lfs = functionnalityRepository.findAllFonctionalitiesLibelleByFichePFE(idEt, fichePFE.getIdFichePFE().getDateDepotFiche());
			Integer idFonctionnality = lfs.size() + 1;
			FunctionnalityPK functionnalityPK = new FunctionnalityPK(fichePFE.getIdFichePFE(), idFonctionnality);

			functionnality.setFunctionnalityPK(functionnalityPK);
			functionnality.setName(funcLib);
			functionnality.setDescription(funcDesc);
			functionnality.setFichePFEFunctionnality(fichePFE);

			functionnalityRepository.save(functionnality);
			//lfs.add(functionnality);
		}
		//fichePFE.addFonctionaliteToFiche(lfs);


		//List<Problematique> lps = new ArrayList<Problematique>();
		for(String pi : planTravailRequest.getListOfProblematics())
		{
			// // System.out.println(" UNIT --------------> " + fichePFE.getIdFichePFE().getIdEt() + " - " + pi);
//
//			List<String> lfs = new ArrayList<String>();
//			lfs = functionnalityRepository.findAllFonctionalitiesLibelleByFichePFE(idEt, fichePFE.getIdFichePFE().getDateDepotFiche());
//			Integer idFonctionnality = lfs.size() + 1;
//			FunctionnalityPK functionnalityPK = new FunctionnalityPK(idEt, fichePFE.getIdFichePFE().getDateDepotFiche(), idFonctionnality);
//
//			functionnality.setFunctionnalityPK(functionnalityPK);
//			functionnality.setName(funcLib);
//			functionnality.setDescription(funcDesc);
//			functionnality.setFichePFEFunctionnality(fichePFE);
//
//			functionnalityRepository.save(functionnality);



			List<String> lps = new ArrayList<String>();
			lps = problematicRepository.findAllProblematicsByStudentAndDateDepotFiche(idEt, timestampToString);
			Integer idProblematic = lps.size() + 1;
			ProblematiquePK problematicPK = new ProblematiquePK(fichePFE.getIdFichePFE(), idProblematic);

//			functionnality.setFunctionnalityPK(functionnalityPK);
//			functionnality.setName(funcLib);
//			functionnality.setDescription(funcDesc);
//			functionnality.setFichePFEFunctionnality(fichePFE);
//
//			functionnalityRepository.save(functionnality);

			Problematique problematic = new Problematique(problematicPK, pi, fichePFE);
			//lps.add(problematique);

			problematicRepository.save(problematic);
		}
		//fichePFE.addProblematiqueToFiche(lps);

		// // System.out.println("-------------- 1: " + fichePFE.getIdFichePFE().getDateDepotFiche());



		// // System.out.println("-------------- 6 Update a new Plan de Travail Dependencies : " + fichePFE.getIdFichePFE().getDateDepotFiche());

		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = new TraitementFichePFE();
		TraitementFichePK annulationFichePK = new TraitementFichePK(fichePFE.getIdFichePFE(), new Timestamp(System.currentTimeMillis()));
		afp.setTraitementFichePK(annulationFichePK);
		afp.setTypeTrtFiche("01");
		afp.setFichePFETraitementFichePFE(fichePFE);
		traitementFicheRepository.save(afp);


		/***************************************** Notification By Mail *****************************************/
		DateFormat dateFormatz = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateDepotESPFile = dateFormatz.format(new Date());

		String subject = "Sauvegarde Plan de Travail";

		String studentMail = utilServices.findStudentMailById(idEt).substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
		// String studentMail = utilServices.findStudentMailById(idEt);  // DEPLOY_SERVER

		String content = "Nous voulons vous informer par le présent mail que vous avez effectué des modifications à votre "
				+ "<strong><font color=grey> Plan de Travail </font></strong> "
				+ "le <font color=red> " + dateDepotESPFile + " </font>."
				+ "<br/>Cette Version peut être modifiée à tout moment et elle est accessible uniquement par Vous."
				+ "<br/>Une fois, vous êtes sûrs de vos données insérées, vous pouvez confirmer votre Plan de Travail "
				+ "pour que votre "
				+ "<strong><font color=grey> Encadrant Académique </font></strong> "
				+ "puisse le traiter.";

		//utilServices.sendMail(subject, studentMail, content);
		/********************************************************************************************************/

	}

	@PostMapping("/sauvegarderFichePFE/{idEt}")
	public void sauvegarderFichePFE(@PathVariable String idEt) {
		// StudentCJ stu = studentRepository.findByIdEt(idEt);
		// // System.out.println("----------------------> StudentCJ: " + idEt);
		// StudentCJ student = studentRepository.findByIdEt(idEt).orElseThrow(() -> new
		// RuntimeException("StudentCJ 1 : Verify your credentials !."));

		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		// fichePFE.setEtat("SAUVGARDEE");

		fichePFE.setEtatFiche("02");

		fichePFERepository.save(fichePFE);

		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = new TraitementFichePFE();
		TraitementFichePK annulationFichePK = new TraitementFichePK(fichePFE.getIdFichePFE(),
				new Timestamp(System.currentTimeMillis()));
		afp.setTraitementFichePK(annulationFichePK);
		afp.setTypeTrtFiche("02");
		afp.setFichePFETraitementFichePFE(fichePFE);
		traitementFicheRepository.save(afp);

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateDepotESPFile = dateFormata.format(new Date());

		String subject = "Remise Plan de Travail";

		// String studentMail = utilServices.findStudentMailById(idEt).substring(0,
		// utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
		String studentMail = utilServices.findStudentMailById(idEt); // DEPLOY_SERVER

		// String academicEncMail =
		// utilServices.findMailPedagogicalEncadrant(idEt).substring(0,
		// utilServices.findMailPedagogicalEncadrant(idEt).lastIndexOf("@")) +
		// "@mail.tn";
		String academicEncMail = utilServices.findMailPedagogicalEncadrant(idEt); // DEPLOY_SERVER

		String content = "Nous voulons vous informer par le présent mail que vous avez déposé votre "
				+ "<strong><font color=grey> Plan de Travail </font></strong> " + "le <font color=red> "
				+ dateDepotESPFile + " </font>."
				// + "<br/>À ce moment, la mise à jour de votre Plan n'est plus autorisée."
				+ "<br/>Votre requête est en cours de traitement par votre "
				+ "<strong><font color=grey> Encadrant Académique </font></strong>.";

		utilServices.sendMailWithCC(subject, studentMail, academicEncMail, content);
		/********************************************************************************************************/

	}

	// @GetMapping("/entrepriseAccueilForFichePFEHistDtoByIdEt/{idEt}")
	// public EntrepriseAccueilForFichePFEHistDto
	// findEntrepriseAccueilForFichePFEHistDtoByIdEt(@PathVariable String idEt)
	// {
	//
	// System.out.println("------------------------> isd: "+ idEt);
	// return
	// conventionRepository.findEntrepriseAccueilForFichePFEHistDtoByIdEt(idEt);
	//
	// }

	// /****NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO*/
	// @GetMapping("/entrepriseSupervisorsByStudentAndFiche/{idEt}")
	// public List<EntrepriseSupervisorDto>
	// findEntrepriseSupervisorsByStudentAndFiche(@PathVariable String idEt)
	// {
	//
	// List<EntrepriseSupervisorDto> lesds =
	// encadrantEntrepriseRepository.findEntrepriseSupervisorsByStudent(idEt);
	// for(EntrepriseSupervisorDto ee : lesds)
	// {
	// System.out.println("------------------------> isd: "+ ee.getFirstName());
	// }
	//
	// return lesds;
	//
	//// List<EntrepriseSupervisorDto> lesds = new
	// ArrayList<EntrepriseSupervisorDto>();
	//// for(EncadrantEntreprise ee : lees)
	//// {
	//// lesds.add(new EntrepriseSupervisorDto(ee.getFirstName(), ee.getLastName(),
	// ee.getNumTelephone(), ee.getEmail()));
	//// }
	////
	//// return lesds;
	//
	// }

	@GetMapping("/traitementFichePFEType")
	public List<String> findTraitementFichePFETypeByStudent() {
		List<String> typesTraitementFichePFE = codeNomenclatureRepository.findTraitementFichePFETypes("102");

		return typesTraitementFichePFE;
	}

	@GetMapping("/traitementFichePFE/{idEt}")
	public TraitementFicheDto findTraitementFichePFEByStudent(@PathVariable String idEt) {
		String dateDEpotFiche = fichePFERepository.findDateDepotFichePFE(idEt).get(0);

		System.out.println("Sector --sss---- ################# ANN --------> 0: " + dateDEpotFiche);
		List<Object[]> oafpfes = traitementFicheRepository.findAnnulationFichePFEByStudentAndFichePFE(idEt,
				dateDEpotFiche);
		System.out.println("Sector ------ ################# ANN --------> 1 " + oafpfes.size());

		String description = null;
		String etatTraiter = null;
		// String typeTraiter = null;
		String dateDepotFiche1 = null;
		String dateAnnulationFiche = null;
		String typeTrtConv = null;

		for (int i = 0; i < oafpfes.size(); i++) {
			System.out.println("UNIT-------> " + (String) oafpfes.get(i)[1]);

			description = (String) oafpfes.get(i)[0];
			etatTraiter = (String) oafpfes.get(i)[1];
			// typeTraiter = (String) oafpfes.get(i)[2];
			dateDepotFiche1 = (String) oafpfes.get(i)[2];
			dateAnnulationFiche = (String) oafpfes.get(i)[3];
			typeTrtConv = (String) oafpfes.get(i)[4];
		}

		System.out.println("--1------------------- DATE: " + dateDEpotFiche);
		System.out.println("--2------------------- DATE: " + dateDepotFiche1);
		System.out.println("--3--------------gggg----- DATE: " + oafpfes.size());

		// // System.out.println("Sector ------ ################# ANN -------->
		// description: " + description);

		TraitementFicheDto annulationFicheDto = new TraitementFicheDto(dateAnnulationFiche, description, etatTraiter,
				typeTrtConv);
		return annulationFicheDto;
	}

	@PostMapping(value = "/treatFichePFE", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> validateESPFile(@RequestParam("idEt") String idEt,
			@RequestParam("libTRTFiche") String libTRTFiche, @RequestParam("libTRTConv") String libTRTConv,
			@RequestParam("treatmentDescription") String treatmentDescription,
			@RequestParam("diagramGanttFullPath") String diagramGanttFullPath) {
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);

		System.out.println("----- libTRTFiche: " + libTRTFiche + " - " + utilServices.decodeEncodedValue(libTRTFiche));
		System.out.println("----- libTRTConv: " + libTRTConv + " - " + utilServices.decodeEncodedValue(libTRTConv));
		System.out.println("----- treatmentDescription: " + treatmentDescription + " - " + utilServices.decodeEncodedValue(treatmentDescription));
		System.out.println("----- diagramGanttFullPath: " + diagramGanttFullPath);

		// FichePFE fichePFE1 = fichePFERepository.sars(idEt).get(0);
		// // System.out.println("---------------------------------------------- type-1:
		// " + libTRTFiche);

		// FOR TEST 16.03.22
		if (libTRTConv.equalsIgnoreCase("Oui")) {
			Convention convention = conventionRepository.findConventionByIdEt(idEt).get(0);
			convention.setMotifAnnulation(
					"Annulation de Convention AUTOMATIQUE suite à une Demande d'Annulation du Plan de Travail demandée par l'Étudiant.");
		}

		TraitementFichePFE afp = new TraitementFichePFE();

		// List<String> las = annulationFicheRepository.findAllAnnulationFiches(idEt
		// //, fichePFE.getIdFichePFE().getDateDepotFiche()
		// );
		// Integer idAnnulationFiche = las.size() + 1;
		String typeTrtFiche = codeNomenclatureRepository.findcodeNomeTFByCodeStrAndLibNome("102", libTRTFiche);
		// // System.out.println("-------------------------------- END - 1 => Date Depot
		// Plan Travail: " + fichePFE.getIdFichePFE().getDateDepotFiche());

		// Store

		TraitementFichePK annulationFichePK = new TraitementFichePK(fichePFE.getIdFichePFE(),
				new Timestamp(System.currentTimeMillis()));

		afp.setTraitementFichePK(annulationFichePK);
		afp.setTypeTrtFiche(typeTrtFiche);
		afp.setTypeTrtConv(libTRTConv);
		afp.setDescription(treatmentDescription);
		afp.setEtatTrt("01");
		afp.setFichePFETraitementFichePFE(fichePFE);
		afp.setPathAccordAnnulation("C:/ESP/uploads/" + diagramGanttFullPath);

		traitementFicheRepository.save(afp);
		// // System.out.println("-------------------------------- Add Annulation Plan
		// de Travail with Id: " + idAnnulationFiche
		// //+ " =EQ= " + afp.getAnnulationFichePK().getLol()
		// );

		// fichePFE.setAnnulationFichePFE(afp);
		// fichePFERepository.save(fichePFE);

		// // System.out.println("-------------------------------- END - 1 => Date Depot
		// Plan Travail: " + afp.getDescription());
		// // System.out.println("-------------------------------- END - 2 => Date Depot
		// Plan Travail: " + fichePFE.getAnnulationFichePFE().getCause());

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		System.out.println("--------------------------------> SEND MAIL 1");
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateDemande = dateFormata.format(new Date());

		String subject = "Demande " + libTRTFiche + " Plan de Travail";

		String studentMail = utilServices.findStudentMailById(idEt); // DEPLOY_SERVER
		String academicEncMail = utilServices.findMailPedagogicalEncadrant(idEt);

		// String studentMail = utilServices.findStudentMailById(idEt).substring(0,
		// utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
		// DEPLOY_SERVERcEncMail =
		// utilServices.findMailPedagogicalEncadrant(idEt).substring(0,
		// utilServices.findMailPedagogicalEncadrant(idEt).lastIndexOf("@")) +
		// "@mail.tn";

		String content = "Vous avez envoyé une <strong><font color=grey> " + subject + " </font></strong> "
				+ "le <font color=red> " + dateDemande + " </font>."
				+ "<br/> Merci de patienter jusqu'à le traitement de votre requête.";

		utilServices.sendMailWithManyCC(subject, studentMail, academicEncMail, content);
		System.out.println("--------------------------------> SEND MAIL 1");
		/********************************************************************************************************/

		//// System.out.println("-------------------------------- END : Update Plan de
		//// Travail with id: " +
		//// fichePFE.getAnnulationFichePFE().getAnnulationFichePK().getIdEt());
		return new ResponseEntity<>("VALIDATING Plan de Travail is DONE", HttpStatus.OK);
	}

	@GetMapping("/traitementFichePFETypeEtat/{idEt}")
	public TraitementFicheTypeEtatDto findTraitementFichePFEEtatTypeByStudent(@PathVariable String idEt) {

		List<TraitementFicheTypeEtatDto> ltfs = traitementFicheRepository.findTraitementFicheTypeEtat(idEt);
		TraitementFicheTypeEtatDto tfte = null;
		System.out.println("-----**--------------> Size: " + ltfs.size());
		if (!ltfs.isEmpty()) {
			tfte = ltfs.get(0);
			System.out.println("-----**--------------> 1: " + tfte.getTypeTrtFiche());
			System.out.println("-----**--------------> 2: " + tfte.getTypeTrtConv());
			System.out.println("-----**--------------> 3: " + tfte.getEtatTrt());
		}
		return tfte;
	}

	@GetMapping("/downloadPDFFile")
	public ResponseEntity downloadFileFromLocal(@RequestParam("filePath") String filePath) throws IOException {

		System.out.println("------------------> SARIA: " + filePath);

		/************************************************************************************************/
		File file = new File(filePath);
		String DSName = filePath.substring(filePath.indexOf("uploads") + 8, filePath.indexOf("espdsi2020"));

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + DSName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		// To Got Name Of File With Synchro
		header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

		Path patha = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(patha));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		/************************************************************************************************/

	}

	@GetMapping("/findStudentSTNCongratDtoByStudent/{idEt}")
	public StudentSTNGradDto findStudentSTNCongratDtoByStudent(@PathVariable String idEt) throws IOException {

		StudentSTNGradDto studentGrad = fichePFERepository.findStudentSTNCongratDtoByStudent(idEt).get(0);

		List<Object[]> lofs = new ArrayList<Object[]>();
		lofs = fichePFERepository.findStudentJuryMembers(idEt);

		String presidentJuryEns = "";
		String expert = "";
		String academicncadrant = "";

		for (int i = 0; i < lofs.size(); i++) {
			presidentJuryEns = (String) lofs.get(i)[0];
			expert = (String) lofs.get(i)[1];
			academicncadrant = (String) lofs.get(i)[2];
		}

		if (studentGrad.getJuryPresident() == null) {
			studentGrad.setJuryPresident(presidentJuryEns);
			studentGrad.setExpert(expert);
			studentGrad.setPedagogicalEncadrant(academicncadrant);
		}

		return studentGrad;
	}

	@GetMapping("/verifyAffectPEtoStudent/{idEt}")
	public String verifyAffectPEtoStudent(@PathVariable String idEt) {
		return utilServices.findIdEncadrantPedagogiqueByStudent(idEt);
	}

	@GetMapping("/findEtatDepotStageIngenieur/{idEt}")
	public String findEtatDepotStageIngenieur(@PathVariable String idEt) throws IOException {
		return evalEngTrRepository.findEtatDepotStageIngenieur(idEt);
	}
	
	@GetMapping("/downloadMyPDF/{idEt}")
	public ResponseEntity downloadMyPDF(@PathVariable String idEt) throws IOException
	{
    	// System.out.println("----------------ff----------1-> " + idEt);
    	// System.out.println("--------------------------2-> " + utilServices.decodeEncodedValue(idEt));
    	
    	//StudentMandatoryInternshipDto studentManInternDto = utilServices.findStudentMandatoryInternshipByStudentId(idEt);
    	
    	String hello = utilServices.decodeEncodedValue(idEt);
    	
    	System.out.println("----------------f###############f----------1-> " + hello);
    	
    	String MIFile = hello.replace("/", "\\");
    	
//    	String MIPath = "C:\\ESP-DOCS\\";
//		String MIName = "Mandatory Internship " + studentManInternDto.getFullName() + ".pdf";
//		String MIFile = MIPath + MIName;
		
		//new MandatoryInternship_PDF(MIFile, studentManInternDto.getFullName(), studentManInternDto.getBirthDay());
		
	    File file = new File(MIFile);
	    
	    HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "PDF File");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        
        // To Got Name Of File With Synchro
        header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        Path patha = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(patha));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

	}

	@GetMapping("/queryYesSearchJob/{idEt}")
	public String queryYesSearchJob(@PathVariable String idEt) throws ParseException
	{
		SurveyFirstJob surveyFirstJob = new SurveyFirstJob(idEt, true, "2022", new Date());
		surveyFirstJobRepository.save(surveyFirstJob);

		return "SURVEY YES FINISHED";
	}

	@PostMapping("/queryNOSearchJobPromesse/{idEt}")
	public String queryNOSearchJobPromesse(@PathVariable String idEt, @RequestBody SurveyFirstJobRequest surveyFirstJobRequest)
	{
		System.out.println("--------------> 1: " + surveyFirstJobRequest.getDateShoosenCriteria());
		System.out.println("--------------> 2: " + surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		System.out.println("--------------> 3: " + surveyFirstJobRequest.getEntrySecondShoosenCriteria());

		SurveyFirstJob surveyFirstJob = new SurveyFirstJob(idEt, false, "2022", new Date());
		surveyFirstJob.setPromesse1(surveyFirstJobRequest.getDateShoosenCriteria());
		surveyFirstJob.setPromesse2(surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		surveyFirstJob.setPromesse3(surveyFirstJobRequest.getEntrySecondShoosenCriteria());
		surveyFirstJobRepository.save(surveyFirstJob);

		return "SURVEY YES FINISHED PROMESSE";
	}

	@PostMapping("/queryNOSearchJobContrat/{idEt}")
	public String queryNOSearchJobContrat(@PathVariable String idEt, @RequestBody SurveyFirstJobRequest surveyFirstJobRequest)
	{
		System.out.println("--------------> 1: " + surveyFirstJobRequest.getDateShoosenCriteria());
		System.out.println("--------------> 2: " + surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		System.out.println("--------------> 3: " + surveyFirstJobRequest.getEntrySecondShoosenCriteria());

		SurveyFirstJob surveyFirstJob = new SurveyFirstJob(idEt, false, "2022", new Date());
		surveyFirstJob.setContrat1(surveyFirstJobRequest.getDateShoosenCriteria());
		surveyFirstJob.setContrat2(surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		surveyFirstJob.setContrat3(surveyFirstJobRequest.getEntrySecondShoosenCriteria());
		surveyFirstJobRepository.save(surveyFirstJob);

		return "SURVEY YES FINISHED CONTRAT";
	}

	@PostMapping("/queryNOSearchJobProjet/{idEt}")
	public String queryNOSearchJobProjet(@PathVariable String idEt, @RequestBody SurveyFirstJobRequest surveyFirstJobRequest)
	{
		System.out.println("--------------> 1: " + surveyFirstJobRequest.getDateShoosenCriteria());
		System.out.println("--------------> 2: " + surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		System.out.println("--------------> 3: " + surveyFirstJobRequest.getEntrySecondShoosenCriteria());

		SurveyFirstJob surveyFirstJob = new SurveyFirstJob(idEt, false, "2022", new Date());
		surveyFirstJob.setProjet1(surveyFirstJobRequest.getDateShoosenCriteria());
		surveyFirstJob.setProjet2(surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		surveyFirstJobRepository.save(surveyFirstJob);

		return "SURVEY YES FINISHED PROJET";
	}

	@PostMapping("/queryNOSearchJobEtude/{idEt}")
	public String queryNOSearchJobEtude(@PathVariable String idEt, @RequestBody SurveyFirstJobRequest surveyFirstJobRequest)
	{
		System.out.println("--------------> 1: " + surveyFirstJobRequest.getDateShoosenCriteria());
		System.out.println("--------------> 2: " + surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		System.out.println("--------------> 3: " + surveyFirstJobRequest.getEntrySecondShoosenCriteria());

		SurveyFirstJob surveyFirstJob = new SurveyFirstJob(idEt, false, "2022", new Date());
		surveyFirstJob.setEtude1(surveyFirstJobRequest.getDateShoosenCriteria());
		surveyFirstJob.setEtude2(surveyFirstJobRequest.getEntryFirstShoosenCriteria());
		surveyFirstJob.setEtude3(surveyFirstJobRequest.getEntrySecondShoosenCriteria());
		surveyFirstJobRepository.save(surveyFirstJob);

		return "SURVEY YES FINISHED ETUDE";
	}

	@GetMapping("/verifySurveyFirstJobDONE/{idEt}")
	public String verifySurveyFirstJobDONE(@PathVariable String idEt)
	{

		List<Integer> lis = surveyFirstJobRepository.findIdSurveyByStudent(idEt);

		String surveyFirstJob = "NOTYET";
		if(!lis.isEmpty())
		{
			surveyFirstJob = "DONE";
		}

		return surveyFirstJob;
	}

}

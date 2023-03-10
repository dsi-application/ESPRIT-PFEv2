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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.gdp.dto.EncadrementRSSStatusExcelDto;
import com.esprit.gdp.dto.EncadrementStatusExcelDto;
import com.esprit.gdp.dto.TeacherQuotaEncadrementExpertiseDto;
import com.esprit.gdp.dto.TeacherQuotaPresidenceMembreDto;
import com.esprit.gdp.files.EtatEncadrementsAndExpertisesByYear_Excel;
import com.esprit.gdp.files.EtatEncadrementsDetailedByYear_Excel;
import com.esprit.gdp.files.EtatEncadrementsGlobal_Excel;
import com.esprit.gdp.files.EtatExpertisesDetailedByYear_Excel;
import com.esprit.gdp.files.EtatPresidencesAndMembresBySession_Excel;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.OptionStudentALTRepository;
import com.esprit.gdp.repository.SessionRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
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
		System.out.println("--PC----------------> pcMail: " + codeDept);
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
		System.out.println("-------> Step 1: " + new Date());
		ess.addAll(studentRepository.findEncadrementStatusCJALT());
		System.out.println("-------> Step 2: " + new Date());
		ess.addAll(studentRepository.findEncadrementStatusCS());
		System.out.println("-------> Step 3: " + new Date());

		for (EncadrementRSSStatusExcelDto es : ess) {

			// System.out.println("---> UNIT: " + es.getAcademicEncadId() + " - " +
			// teacherRepository.findTeacherFullNameById(es.getAcademicEncadId()));

			es.setStudentOption(
					utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear("2021")));
			es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadId()));
			es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadId()));
		}
		System.out.println("-------> Step 4: " + new Date());

		ess.sort(Comparator.comparing(EncadrementRSSStatusExcelDto::getAcademicEncadFullName)
				.thenComparing(EncadrementRSSStatusExcelDto::getStudentFullName));
		System.out.println("-------> Step 5: " + new Date());

		/************************************************************************************/

		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "??tat Encadrements Global.xls";
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
		String PSName = "??tat Encadrements Global.xls";
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
		String PSName = "??tat Expertises Global.xls";
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
		String PSName = "??tat Encadrements et Expertises.xls";
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
		
		System.out.println("-------------- START Treatment ??tat Encadrement: " +  yearFromSession + " - " + selectedSession);
		
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
		String PSName = "??tat Pr??sidences et Membres de Jury pour Soutennaces.xls";
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
	
}

package com.esprit.gdp.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.esprit.gdp.dto.EncadrementStatusExcelDto;
import com.esprit.gdp.dto.ExpertDto;
import com.esprit.gdp.dto.ExpertiseStatusExcelDto;
import com.esprit.gdp.dto.FichePFEForFicheDepotPFEDto;
import com.esprit.gdp.dto.FichePFEWithoutFicheDepotPFEDto;
import com.esprit.gdp.dto.StudentTrainedByPCDto;
import com.esprit.gdp.dto.TeacherAffectationAcademicEncadrantDto;
import com.esprit.gdp.dto.TeacherAffectationExpertDto;
import com.esprit.gdp.dto.TeacherDtoSTN;
import com.esprit.gdp.dto.TeacherQuotaEncadrementExpertiseDto;
import com.esprit.gdp.dto.TeacherQuotaPresidenceMembreDto;
import com.esprit.gdp.files.EtatEncadrementByOption_Excel;
import com.esprit.gdp.files.EtatExpertiseByOption_Excel;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.NoteRestitutionRepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.OptionStudentALTRepository;
import com.esprit.gdp.repository.PedagogicalCoordinatorRepository;
import com.esprit.gdp.repository.SessionRepository;
import com.esprit.gdp.repository.SettingsRepository;
import com.esprit.gdp.repository.StrNomeCEPRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.services.UtilServices;

//@RestController
//@CrossOrigin(origins = "http://193.95.99.194:8081")
//@RequestMapping("/api/pedaCoord")

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/pedaCoord")
public class PedagogicalCoordinatorController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	InscriptionRepository inscriptionRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	PedagogicalCoordinatorRepository pedagogicalCoordinatorRepository;

	@Autowired
	UtilServices utilServices;

	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	OptionStudentALTRepository optionStudentALTRepository;

	@Autowired
	StrNomeCEPRepository strNomeCEPRepository;

	@Autowired
	NoteRestitutionRepository noteRestitutionRepository;

	@Autowired
	SettingsRepository settingsRepository;
	
	@Autowired
	OptionRepository optionRepository;

	/******************************************************
	 * Methods
	 ******************************************************/
	@GetMapping("/sample")
	public String findTrainneeDuration() {
		return "Message";
	}

	@GetMapping("/downloadEncadrementStatusByOption/{pcMail}")
	public ResponseEntity downloadEncadrementStatusByOption(@PathVariable String pcMail) throws IOException {
		String optDept = "";

		if (pcMail.contains("CPS_")) {
			optDept = "Option " + pcMail.substring(4, pcMail.lastIndexOf("@esprit.tn"));
		}

		if (pcMail.contains("CD_INF")) {
			optDept = "Département Informatique";
		}

		if (pcMail.contains("CD_TEL")) {
			optDept = "Département Télécommunications";
		}

		if (pcMail.contains("CD_EM")) {
			optDept = "Département Électromécanique";
		}

		if (pcMail.contains("CD_GC")) {
			optDept = "Département Génie Civil";
		}

		if (pcMail.contains("CD-S-Tic")) {
			optDept = "Département Informatique & Télécommunications";
		}

		if (pcMail.contains("CD-S-GC-EM")) {
			optDept = "Département Électromécanique & Génie Civil";
		}

		if (pcMail.contains("CD-Alt")) {
			optDept = "Département Alternances";
		}

		List<EncadrementStatusExcelDto> ess = new ArrayList<EncadrementStatusExcelDto>();

		if (pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn")) 
		{
			ess.addAll(optionStudentALTRepository.findEncadrementStatusCJALT());
			for (EncadrementStatusExcelDto es : ess) {
				es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadFullName()));
				es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()));

				// System.out.println("------**----------> Etat Encadrement: " +
				// es.getStudentFullName() + " - " + es.getAcademicEncadFullName());
			}
		} else {
			String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
			List<String> options = pedagogicalCoordinatorRepository.listLowerOptionsByPedagogicalCoordinator(idPC);

			for (String opt : options) {

				System.out.println("-------------------------------> OPT: " + opt);

				ess.addAll(studentRepository.findEncadrementStatusCJByOption(opt));
				ess.addAll(studentRepository.findEncadrementStatusCSByOption(opt));

				// if(opt.equalsIgnoreCase("alinfo"))
				// {
				// ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
				// }

				if (opt.equalsIgnoreCase("arctic") || opt.equalsIgnoreCase("ds") || opt.equalsIgnoreCase("sae")
						|| opt.equalsIgnoreCase("twin")) {
					System.out.println("------------> OPT *: "
							+ optionStudentALTRepository.findEncadrementStatusCJALTByOption(opt.toLowerCase()).size());
					// ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
					ess.addAll(optionStudentALTRepository.findEncadrementStatusCJALTByOption(opt.toLowerCase()));
				}
			}

			System.out.println("---> enc Download ExcelFile  " + optDept);

			for (EncadrementStatusExcelDto es : ess) {
				es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadFullName()));
				es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()));

				// System.out.println("------**----------> Etat Encadrement: " +
				// es.getStudentFullName() + " - " + es.getAcademicEncadFullName());
			}

		}

		ess.sort(Comparator.comparing(EncadrementStatusExcelDto::getStudentClasse)
				.thenComparing(EncadrementStatusExcelDto::getStudentFullName));

		/************************************************************************************/

		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Encadrement - " + optDept + ".xls";
		String PSFile = PSPath + PSName;

		new EtatEncadrementByOption_Excel(ess, optDept, PSFile, "");

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

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@GetMapping("/downloadEncadrementStatusByOptionAndYear/{pcMail}/{year}")
	public ResponseEntity downloadEncadrementStatusByOptionAndYear(@PathVariable String pcMail,
			@PathVariable String year) throws IOException {

		String optDept = "";
		
		if(pcMail.contains("CPS_"))
	    {
			optDept = "Option " + pcMail.substring(4, pcMail.lastIndexOf("@esprit.tn"));
		}

	    if(pcMail.contains("CD_INF"))
	    {
	    	optDept = "Département Informatique";
	    }

	    if(pcMail.contains("CD_TEL"))
	    {
	    	optDept = "Département Télécommunications";
	    }

	    if(pcMail.contains("CD_EM"))
		{
	    	optDept = "Département Électromécanique";
		}

		if(pcMail.contains("CD_GC"))
		{
			optDept = "Département Génie Civil";
		}
		
		if(pcMail.contains("CD-S-Tic"))
	    {
	    	optDept = "Département Informatique & Télécommunications";
	    }
		
		if(pcMail.contains("CD-S-GC-EM"))
	    {
	    	optDept = "Département Électromécanique & Génie Civil";
	    }
		
		if(pcMail.contains("CD-Alt"))
	    {
	    	optDept = "Département Alternances";
	    }
		

		List<EncadrementStatusExcelDto> ess = new ArrayList<EncadrementStatusExcelDto>();
		
		if(pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn"))
		{
			ess.addAll(optionStudentALTRepository.findEncadrementStatusCJALTByYear(year));
			for(EncadrementStatusExcelDto es : ess)
			{
				es.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(es.getStudentId(), year));
				
				es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadFullName()));
				es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()));
				
				//System.out.println("------**----------> Etat Encadrement: " + es.getStudentFullName() + " - " + es.getAcademicEncadFullName());
			}
		}
		else
		{
			String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
			List<String> options = pedagogicalCoordinatorRepository.listLowerOptionsByPedagogicalCoordinator(idPC);
			
			for(String opt : options)
			{
				
				System.out.println("-------------------------------> OPT: " + opt);
				
				ess.addAll(studentRepository.findEncadrementStatusCJByYearAndOption(year, opt));
				
//				if(opt.equalsIgnoreCase("alinfo"))
//				{
//					ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
//				}
				
				// PIKATCHO
				if(
						opt.equalsIgnoreCase("arctic") || opt.equalsIgnoreCase("ds") ||
						opt.equalsIgnoreCase("sae") || opt.equalsIgnoreCase("twin") ||
						opt.equalsIgnoreCase("nids") || opt.equalsIgnoreCase("erp-bi")
				  )
				{
					System.out.println("------------> OPT *: "  + optionStudentALTRepository.findEncadrementStatusCJALTByYearAndOption(year, opt.toLowerCase()).size());
					//ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
					ess.addAll(optionStudentALTRepository.findEncadrementStatusCJALTByYearAndOption(year, opt.toLowerCase()));
				}
			}
			
			System.out.println("---> enc Download ExcelFile  " + optDept);
			
			for(EncadrementStatusExcelDto es : ess)
			{
				
				if(es.getStudentClasse().contains("4ALINFO"))
				{
					es.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(es.getStudentId(), year));
					System.out.println("--> 2.1");
				}
				else
				{
					es.setStudentOption(utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear(year)).replace("_01", ""));
					System.out.println("--> 2.2");
				}
				
				es.setAcademicEncadMail(teacherRepository.findTeacherMailById(es.getAcademicEncadFullName()));
				es.setAcademicEncadFullName(teacherRepository.findTeacherFullNameById(es.getAcademicEncadFullName()));
				
				//System.out.println("------**----------> Etat Encadrement: " + es.getStudentFullName() + " - " + es.getAcademicEncadFullName());
			}
			
		}
		
		ess.sort(Comparator.comparing(EncadrementStatusExcelDto::getStudentClasse).thenComparing(EncadrementStatusExcelDto::getStudentFullName));
		
		
		/************************************************************************************/
		
		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Encadrement - " + optDept + ".xls";
		String PSFile = PSPath + PSName;
		
		new EtatEncadrementByOption_Excel(ess, optDept, PSFile, year);
		
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

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	
	}

	@GetMapping("/downloadExpertiseStatusByOption/{pcMail}")
	public ResponseEntity downloadExpertiseStatusByOption(@PathVariable String pcMail) throws IOException 
	{

		
		String optDept = "";
		
		if(pcMail.contains("CPS_"))
	    {
			optDept = "Option " + pcMail.substring(4, pcMail.lastIndexOf("@esprit.tn"));
		}

	    if(pcMail.contains("CD_INF"))
	    {
	    	optDept = "Département Informatique";
	    }

	    if(pcMail.contains("CD_TEL"))
	    {
	    	optDept = "Département Télécommunications";
	    }

	    if(pcMail.contains("CD_EM"))
		{
	    	optDept = "Département Électromécanique";
		}

		if(pcMail.contains("CD_GC"))
		{
			optDept = "Département Génie Civil";
		}
		
		if(pcMail.contains("CD-S-Tic"))
	    {
	    	optDept = "Département Informatique & Télécommunications";
	    }
		
		if(pcMail.contains("CD-S-GC-EM"))
	    {
	    	optDept = "Département Électromécanique & Génie Civil";
	    }
		
		if(pcMail.contains("CD-Alt"))
	    {
	    	optDept = "Département Alternances";
	    }

		List<ExpertiseStatusExcelDto> ess = new ArrayList<ExpertiseStatusExcelDto>();
		
		if(pcMail.equalsIgnoreCase("CD-Alt@esprit.tn"))
		{
			ess.addAll(optionStudentALTRepository.findExpertiseStatusCJALT());
			for(ExpertiseStatusExcelDto es : ess)
			{
				es.setExpertMail(teacherRepository.findTeacherMailById(es.getExpertFullName()));
				es.setExpertFullName(teacherRepository.findTeacherFullNameById(es.getExpertFullName()));
				
				String StuPhone = "--";
				if(es.getStudentPhone() != null)
				{
					StuPhone = es.getStudentPhone().trim();
				}
				es.setStudentPhone(StuPhone);
				
				String markRest1 = "--";
				if(noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), "2021") != null)
				{
					markRest1 = noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), "2021").toString();
					// System.out.println("---r---2-----> markRest1: " + es.getStudentId() + " : " + es.getStudentFullName() + " --> " + markRest1);
				}
				es.setRest1MarkByExpert(markRest1);
				
				if(es.getStudentClasse().contains("4ALINFO"))
				{
					es.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(es.getStudentId(), "2021"));
					System.out.println("--> 2.1");
				}
				else
				{
					es.setStudentOption(utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));
					System.out.println("--> 2.2");
				}
				
			}
		}
		else
		{
			String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
			List<String> options = pedagogicalCoordinatorRepository.listLowerOptionsByPedagogicalCoordinator(idPC);
			
			for(String opt : options)
			{
				
				System.out.println("-------------------------------> OPT: " + opt);
				
				ess.addAll(studentRepository.findExpertisetatusCJByOption(opt));
				ess.addAll(studentRepository.findExpertiseStatusCSByOption(opt));
				
//				if(opt.equalsIgnoreCase("alinfo"))
//				{
//					ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
//				}
				
				if(
						opt.equalsIgnoreCase("arctic") || opt.equalsIgnoreCase("ds") ||
						opt.equalsIgnoreCase("sae") ||opt.equalsIgnoreCase("twin")
				  )
				{
					System.out.println("------------> OPT *: "  + optionStudentALTRepository.findExpertiseStatusCJALTByOption(opt.toLowerCase()).size());
					//ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
					ess.addAll(optionStudentALTRepository.findExpertiseStatusCJALTByOption(opt.toLowerCase()));
				}
			}
			
			System.out.println("---> ***** Download ExcelFile Expertise " + optDept);
			
			for(ExpertiseStatusExcelDto es : ess)
			{
				es.setExpertMail(teacherRepository.findTeacherMailById(es.getExpertFullName()));
				es.setExpertFullName(teacherRepository.findTeacherFullNameById(es.getExpertFullName()));

				// String hi = noteRestitutionRepository.findNoteRestitutionByStu("171JMT1971", "1988").toString();
				
				String StuPhone = "--";
				if(es.getStudentPhone() != null)
				{
					StuPhone = es.getStudentPhone().trim();
				}
				es.setStudentPhone(StuPhone);
				
				// System.out.println("---r---2-----> StuPhone: " + es.getStudentPhone().trim() + "." + StuPhone + ".");
				
				String markRest1 = "--";
				if(noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), "2021") != null)
				{
					markRest1 = noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), "2021").toString();
					// System.out.println("---r---2-----> markRest1: " + es.getStudentId() + " : " + es.getStudentFullName() + " --> " + markRest1);
				}
				es.setRest1MarkByExpert(markRest1);
				
				
				if(es.getStudentClasse().contains("4ALINFO"))
				{
					es.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(es.getStudentId(), "2021"));
					System.out.println("--> 2.1");
				}
				else
				{
					es.setStudentOption(utilServices.findOptionByClass(es.getStudentClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));
					System.out.println("--> 2.2");
				}
				
				
			}
			
		}
		
		ess.sort(Comparator.comparing(ExpertiseStatusExcelDto::getStudentClasse).thenComparing(ExpertiseStatusExcelDto::getStudentFullName));
		
		
		/************************************************************************************/
		
		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Expertise - " + optDept + ".xls";
		String PSFile = PSPath + PSName;
		
		new EtatExpertiseByOption_Excel(ess, optDept, PSFile, "");
		
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

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	
	
	}

	@GetMapping("/downloadExpertiseStatusByOptionAndYear/{pcMail}/{selectedYear}")
	public ResponseEntity downloadExpertiseStatusByOptionAndYear(@PathVariable String pcMail, @PathVariable String selectedYear) throws IOException
	{

		String optDept = "";

		if(pcMail.contains("CPS_"))
		{
			optDept = "Option " + pcMail.substring(4, pcMail.lastIndexOf("@esprit.tn"));
		}

		if(pcMail.contains("CD_INF"))
		{
			optDept = "Département Informatique";
		}

		if(pcMail.contains("CD_TEL"))
		{
			optDept = "Département Télécommunications";
		}

		if(pcMail.contains("CD_EM"))
		{
			optDept = "Département Électromécanique";
		}

		if(pcMail.contains("CD_GC"))
		{
			optDept = "Département Génie Civil";
		}

		if(pcMail.contains("CD-S-Tic"))
		{
			optDept = "Département Informatique & Télécommunications";
		}

		if(pcMail.contains("CD-S-GC-EM"))
		{
			optDept = "Département Électromécanique & Génie Civil";
		}

		if(pcMail.contains("CD-Alt"))
		{
			optDept = "Département Alternances";
		}

		List<ExpertiseStatusExcelDto> ess = new ArrayList<ExpertiseStatusExcelDto>();

		if(pcMail.equalsIgnoreCase("CD-Alt@esprit.tn") || pcMail.equalsIgnoreCase("CPS-Alt@esprit.tn"))
		{
			ess.addAll(optionStudentALTRepository.findExpertiseStatusCJALTAndYear(selectedYear));
			for(ExpertiseStatusExcelDto es : ess)
			{
				es.setExpertMail(teacherRepository.findTeacherMailById(es.getExpertFullName()));
				es.setExpertFullName(teacherRepository.findTeacherFullNameById(es.getExpertFullName()));

				String StuPhone = "--";
				if(es.getStudentPhone() != null)
				{
					StuPhone = es.getStudentPhone().trim();
				}
				es.setStudentPhone(StuPhone);

				String markRest1 = "--";
				if(noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), selectedYear) != null)
				{
					markRest1 = noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), selectedYear).toString();
					// System.out.println("---r---2-----> markRest1: " + es.getStudentId() + " : " + es.getStudentFullName() + " --> " + markRest1);
				}
				es.setRest1MarkByExpert(markRest1);

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

			}
		}
		else
		{
			String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(pcMail).get(0);
			List<String> options = pedagogicalCoordinatorRepository.listLowerOptionsByPedagogicalCoordinator(idPC);

			for(String opt : options)
			{

				System.out.println("-------------------------------> OPT: " + opt);

				ess.addAll(studentRepository.findExpertisetatusCJByYearAndOption(selectedYear, opt));
				ess.addAll(studentRepository.findExpertiseStatusCSByYearAndOption(selectedYear, opt));

//				if(opt.equalsIgnoreCase("alinfo"))
//				{
//					ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
//				}

				if(
						opt.equalsIgnoreCase("arctic") || opt.equalsIgnoreCase("ds") ||
								opt.equalsIgnoreCase("sae") ||opt.equalsIgnoreCase("twin")
				)
				{
					System.out.println("------------> OPT *: "  + optionStudentALTRepository.findExpertiseStatusCJALTByYearAndOption(selectedYear, opt.toLowerCase()).size());
					//ess.addAll(studentRepository.findEncadrementStatusCJALTByOption());
					ess.addAll(optionStudentALTRepository.findExpertiseStatusCJALTByYearAndOption(selectedYear, opt.toLowerCase()));
				}
			}

			System.out.println("---> ***** Download ExcelFile Expertise " + optDept);

			for(ExpertiseStatusExcelDto es : ess)
			{
				es.setExpertMail(teacherRepository.findTeacherMailById(es.getExpertFullName()));
				es.setExpertFullName(teacherRepository.findTeacherFullNameById(es.getExpertFullName()));

				// String hi = noteRestitutionRepository.findNoteRestitutionByStu("171JMT1971", "1988").toString();

				String StuPhone = "--";
				if(es.getStudentPhone() != null)
				{
					StuPhone = es.getStudentPhone().trim();
				}
				es.setStudentPhone(StuPhone);

				// System.out.println("---r---2-----> StuPhone: " + es.getStudentPhone().trim() + "." + StuPhone + ".");

				String markRest1 = "--";
				if(noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), selectedYear) != null)
				{
					markRest1 = noteRestitutionRepository.findNoteRestitutionByStu(es.getStudentId(), selectedYear).toString();
					// System.out.println("---r---2-----> markRest1: " + es.getStudentId() + " : " + es.getStudentFullName() + " --> " + markRest1);
				}
				es.setRest1MarkByExpert(markRest1);


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


			}

		}

		ess.sort(Comparator.comparing(ExpertiseStatusExcelDto::getStudentClasse).thenComparing(ExpertiseStatusExcelDto::getStudentFullName));


		/************************************************************************************/

		String PSPath = "C:\\ESP-DOCS\\";
		String PSName = "État Expertise - " + optDept + ".xls";
		String PSFile = PSPath + PSName;

		new EtatExpertiseByOption_Excel(ess, optDept, selectedYear, PSFile);

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

		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);

	}

	@GetMapping("/findAllAcademicEncadrants")
	public List<TeacherDtoSTN> findAllAcademicEncadrants() {

		System.out.println("-------------- START Treatment État Encadrement: " + new Date());
		// Long numSemestre = new Long(1);

		List<TeacherDtoSTN> academicEncadrants = teacherRepository.allAcademicEncadrants("2021");

		for (TeacherDtoSTN ae : academicEncadrants) {
			ae.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPE(ae.getIdentifiant()));
		}

		System.out.println("-------------- End Treatment État Encadrement: " + new Date());
		return academicEncadrants;

	}

	@GetMapping("/findAllAcademicEncadrantsByYear/{year}")
	public List<TeacherDtoSTN> findAllAcademicEncadrants(@PathVariable String year) {

		System.out.println("-------------- START Treatment État Encadrement: " + new Date());
		// Long numSemestre = new Long(1);

		List<TeacherDtoSTN> academicEncadrants = teacherRepository.allAcademicEncadrants(year);

		for (TeacherDtoSTN ae : academicEncadrants) {
			ae.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPEAndYear(ae.getIdentifiant(), year));
		}

		System.out.println("-------------- End Treatment État Encadrement: " + new Date());
		return academicEncadrants;

	}

	@GetMapping("/findAllAcademicExperts")
	public List<TeacherDtoSTN> findAllExperts() {

		System.out.println("-------------- START Treatment État Experts: " + new Date());
		// Long numSemestre = new Long(1);

		List<TeacherDtoSTN> academicExperts = teacherRepository.allAcademicEncadrants("2021");

		for (TeacherDtoSTN ae : academicExperts) {
			ae.setNbrEncadrements(utilServices.findNbrStudentsTrainedByEXP(ae.getIdentifiant()));
		}

		System.out.println("-------------- End Treatment État Experts: " + new Date());
		return academicExperts;

	}

	@GetMapping("/findAllAcademicExpertsByYear/{year}")
	public List<TeacherDtoSTN> findAllExpertsByYear(@PathVariable String year)
	{

		System.out.println("-------------- START Treatment État Experts: " +  new Date() + " - " + year);
		// Long numSemestre = new Long(1);
		
		List<TeacherDtoSTN> academicExperts = teacherRepository.allAcademicEncadrants(year);
		
		for(TeacherDtoSTN ae : academicExperts)
		{
			ae.setNbrEncadrements(utilServices.findNbrStudentsTrainedByEXPAndYear(ae.getIdentifiant(), year));
		}
		
		System.out.println("-------------- End Treatment État Experts: " + new Date());
		return academicExperts;
	
	}
	
	@GetMapping("/findPedagogicalEncadrantByStudentAndYear/{idEt}/{year}")
	public TeacherAffectationAcademicEncadrantDto findPedagogicalEncadrantByStudentAndYear(@PathVariable String idEt,
			@PathVariable String year) {
		System.out.println("--PC----------------> pcMail: " + idEt + " - " + year);
		String idAE = utilServices.findIdEncadrantPedagogiqueByStudent(idEt);

		System.out.println("--PC----------------> idAE: " + idAE);
		TeacherAffectationAcademicEncadrantDto tad = teacherRepository
				.getTeacherAffectationAcademicEncadrantDetails(idAE);
		tad.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPEAndYear(idAE, year));

		return tad;
	}

	@GetMapping("/findPedagogicalEncadrantByStudent/{idEt}")
	public TeacherAffectationAcademicEncadrantDto findPedagogicalEncadrantByStudent(@PathVariable String idEt) {
		System.out.println("--PC----------------> pcMail: " + idEt);
		String idAE = utilServices.findIdEncadrantPedagogiqueByStudent(idEt);

		System.out.println("--PC----------------> idAE: " + idAE);
		TeacherAffectationAcademicEncadrantDto tad = teacherRepository
				.getTeacherAffectationAcademicEncadrantDetails(idAE);
		tad.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPE(idAE));

		return tad;
	}

	@GetMapping("/findPedagogicalEncadrantByStudentOptimized/{idEt}")
	public TeacherAffectationAcademicEncadrantDto findPedagogicalEncadrantByStudentOptimized(
			@PathVariable String idEt) {
		System.out.println("--PC---------18.08-------> pcMail: " + idEt);
		String idAE = utilServices.findIdEncadrantPedagogiqueByStudent(idEt);

		// System.out.println("--PC------18.08----------> idAE: " + idAE);

		TeacherAffectationAcademicEncadrantDto tad = new TeacherAffectationAcademicEncadrantDto();
		if (teacherRepository.getTeacherAffectationAcademicEncadrantDetails(idAE) != null) {
			tad = teacherRepository.getTeacherAffectationAcademicEncadrantDetails(idAE);
			tad.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPE(idAE));
		}

		return tad;
	}

	@GetMapping("/findPedagogicalEncadrantByStudentAndYearOptimized/{idEt}/{selectedYear}")
	public TeacherAffectationAcademicEncadrantDto findPedagogicalEncadrantByStudentAndYearOptimized(@PathVariable String idEt, @PathVariable String selectedYear)
	{
		System.out.println("--PC---------18.08-------> pcMail: " + idEt);
		String idAE = utilServices.findIdEncadrantPedagogiqueByStudent(idEt);

		// System.out.println("--PC------18.08----------> idAE: " + idAE);

		TeacherAffectationAcademicEncadrantDto tad = new TeacherAffectationAcademicEncadrantDto();
		if(teacherRepository.getTeacherAffectationAcademicEncadrantDetails(idAE) != null)
		{
			tad = teacherRepository.getTeacherAffectationAcademicEncadrantDetails(idAE);
			tad.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPEAndYear(idAE, selectedYear));
		}

		return tad;
	}

	@GetMapping("/findAllSessions")
	public List<String> findAllSessions() {
		List<String> activatedYears = settingsRepository.findActivatedYears();
		System.out.println("-------------- ENCODED: " + activatedYears.size());
		return activatedYears;
	}

	@GetMapping("/findAllExperts/{labelCEP}")
	public List<ExpertDto> findAllExperts(@PathVariable String labelCEP) {
		System.out.println("-------------- ENCODED: " + labelCEP);
		String decodedLabelCEP = utilServices.decodeEncodedValue(labelCEP);
		System.out.println("-------------- DECODED: " + decodedLabelCEP);

		System.out.println("-------------- START Treatment État Experts: " + new Date());

		// List<String> experts = teacherRepository.allExperts("1988");
		List<ExpertDto> experts = teacherRepository.allExperts("2021", decodedLabelCEP);

		for (ExpertDto exp : experts) {
			exp.setNom(teacherRepository.findTeacherFullNameById(exp.getIdentifiant()));
			exp.setNbrExpertises(utilServices.findNbrStudentsTrainedByEXP(exp.getIdentifiant()));
		}

		experts.sort(Comparator.comparing(ExpertDto::getNom));

		System.out.println("-------------- End Treatment État Experts: " + new Date());
		return experts;

	}

	@GetMapping("/findAllExpertsByCEPAndYear/{labelCEP}/{selectedYear}")
	public List<ExpertDto> findAllExpertsByCEPAndYear(@PathVariable String labelCEP, @PathVariable String selectedYear)
	{
		System.out.println("-------------- ENCODED: " +  labelCEP);
		String decodedLabelCEP = utilServices.decodeEncodedValue(labelCEP);
		System.out.println("-------------- DECODED: " +  decodedLabelCEP);

		System.out.println("-------------- START Treatment État Experts: " +  new Date());

		// List<String> experts = teacherRepository.allExperts("1988");
		List<ExpertDto> experts = teacherRepository.allExperts(selectedYear, decodedLabelCEP);

		for(ExpertDto exp : experts)
		{
			exp.setNom(teacherRepository.findTeacherFullNameById(exp.getIdentifiant()));
			exp.setNbrExpertises(utilServices.findNbrStudentsTrainedByEXPAndYear(exp.getIdentifiant(), selectedYear));
		}

		experts.sort(Comparator.comparing(ExpertDto::getNom));

		System.out.println("---------nn----- End Treatment État Experts: " + new Date());
		return experts;

	}

	@GetMapping("/findExpertByStudent/{idEt}")
	public TeacherAffectationExpertDto findExpertByStudent(@PathVariable String idEt) {
		TeacherAffectationExpertDto tad = null; // new TeacherAffectationExpertDto();
		System.out.println("--EXP----------------> pcMail: " + idEt);
		String idExp = utilServices.findIdExpertByStudent(idEt);

		System.out.println("--EXP----------------> idAE: " + idExp);
		if (teacherRepository.getTeacherAffectationExpertDetails(idExp) != null) {
			tad = teacherRepository.getTeacherAffectationExpertDetails(idExp);
			tad.setNbrExpertises(utilServices.findNbrStudentsTrainedByEXP(idExp));
		}

		return tad;
	}

	@GetMapping("/findExpertByStudentAndyear/{idEt}/{selectedYear}")
	public TeacherAffectationExpertDto findExpertByStudent(@PathVariable String idEt, @PathVariable String selectedYear)
	{
		TeacherAffectationExpertDto tad = null; //new TeacherAffectationExpertDto();
		System.out.println("--EXP----------------> pcMail: " + idEt);
		String idExp = utilServices.findIdExpertByStudent(idEt);

		System.out.println("--EXP----------------> idAE: " + idExp);
		if(teacherRepository.getTeacherAffectationExpertDetails(idExp) != null)
		{
			tad = teacherRepository.getTeacherAffectationExpertDetails(idExp);
			tad.setNbrExpertises(utilServices.findNbrStudentsTrainedByEXPAndYear(idExp, selectedYear));
		}

		return tad;
	}

	@GetMapping("/affectAcademicEncadrantToStudent/{idEns}/{idEt}/{mailPC}")
	public String affectAcademicEncadrantToStudent(@PathVariable String idEns, @PathVariable String idEt,
			@PathVariable String mailPC) {

		System.out.println("------------------------> AFFECT AE-Stu: " + idEns + " - " + idEt);
		List<String> activatedYears = settingsRepository.findActivatedYears();
		List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(idEt, activatedYears);
		List<String> studentsCS = studentRepository.findStudentsFullNameCS(idEt, activatedYears);

		// System.out.println("----------------------------------> StudentCJ FullName
		// CJ: " + studentsCJ + " --- CS: " + studentsCS);

		if (!studentsCJandALT.isEmpty()) {
			System.out.println("------------------------> CDJ-ALT");
			inscriptionRepository.affectAcademicEncadrantToStudentCJandALT(idEns, idEt);
		}
		if (!studentsCS.isEmpty()) {
			System.out.println("------------------------> CDS");
			inscriptionRepository.affectAcademicEncadrantToStudentCS(idEns, idEt);
		}

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		// Send Mail to Pedagogical Coordinator
		List<String> toMails = new ArrayList<String>();

		// String studentMail = "saria.essid@esprit.tn";
		String studentMail = utilServices.findStudentMailById(idEt); // DEPLOY_SERVER

		// String academicEncadrantMail = "saria.essid@esprit.tn";
		String academicEncadrantMail = teacherRepository.findMailTeacherById(idEns); // DEPLOY_SERVER
		String academicEncadrantFullName = teacherRepository.findNameTeacherById(idEns);

		Integer nbrAE = 0;
		if (academicEncadrantMail != null) {
			if (utilServices.isValidEmailAddress(academicEncadrantMail)) {
				toMails.add(academicEncadrantMail);
				nbrAE = nbrAE + 1;
			}
		}

		Integer nbrST = 0;
		if (studentMail != null) {
			if (utilServices.isValidEmailAddress(studentMail)) {
				toMails.add(studentMail);
				nbrST = nbrST + 1;
			}
		}

		String receiver = toMails.stream().collect(Collectors.joining(", "));

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateAffectation = dateFormata.format(new Date());

		String subject = "Affectation Encadrant Académique";
		String studentFullName = utilServices.findStudentFullNameById(idEt);

		String content = "Nous voulons vous informer par le présent mail que " + "Mme/M <strong><font color=grey> "
				+ academicEncadrantFullName + "</font></strong> " + " a été affecté en tant que "
				+ "<font color=grey> Encadrant Académique </font> " + "à l'Étudiant <strong><font color=grey> "
				+ studentFullName + " </font></strong> " + "le <font color=red> " + dateAffectation + " </font>.";

		utilServices.sendMailWithManyTOandManyCC(subject, receiver, mailPC, content);
		/********************************************************************************************************/

		String result = "";
		if (nbrAE == 1 && nbrST == 1) {
			result = "YES";
			System.out.println("--------------> Result YES: AE+ ST+");
		}
		if (nbrAE == 1 && nbrST == 0) {
			result = "NO-ST";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrST == 1) {
			result = "NO-AE";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrST == 0) {
			result = "NO-AEST";
			System.out.println("--------------> Result NO: " + result);
		}

		return result;
	}

	@GetMapping("/affectExpertToStudent/{idEns}/{idEt}/{mailPC}")
	public String affectExpertToStudent(@PathVariable String idEns, @PathVariable String idEt,
			@PathVariable String mailPC) {

		System.out.println("------------------------> AFFECT AE-Stu: " + idEns + " - " + idEt);
		List<String> activatedYears = settingsRepository.findActivatedYears();
		List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(idEt, activatedYears);
		List<String> studentsCS = studentRepository.findStudentsFullNameCS(idEt, activatedYears);

		// System.out.println("----------------------------------> StudentCJ FullName
		// CJ: " + studentsCJ + " --- CS: " + studentsCS);

		if (!studentsCJandALT.isEmpty()) {
			System.out.println("------------------------> CDJ-ALT");
			inscriptionRepository.affectExpertToStudentCJ(idEns, idEt);
		}
		if (!studentsCS.isEmpty()) {
			System.out.println("------------------------> CDS");
			inscriptionRepository.affectExpertToStudentCS(idEns, idEt);
		}

		/*****************************************
		 * Notification By Mail
		 *****************************************/

		// Send Mail to Pedagogical Coordinator
		List<String> toMails = new ArrayList<String>();
		List<String> ccMails = new ArrayList<String>();

		// String studentMail = "saria.essid@esprit.tn";
		String studentMail = utilServices.findStudentMailById(idEt); // DEPLOY_SERVER
		ccMails.add(studentMail);
		ccMails.add(mailPC);
		String ccReceps = ccMails.stream().collect(Collectors.joining(", "));

		// String academicExpertMail = "saria.essid@esprit.tn";

		String academicExpertMail = teacherRepository.findMailTeacherById(idEns); // DEPLOY_SERVER

		String expertFullName = teacherRepository.findNameTeacherById(idEns);

		String academicEncadrantMail = utilServices.findMailPedagogicalEncadrant(idEt);
		// findMailPedagogicalEncadrant
		Integer nbrAE = 0;
		if (academicExpertMail != null) {
			if (utilServices.isValidEmailAddress(academicExpertMail)) {
				toMails.add(academicExpertMail);
				nbrAE = nbrAE + 1;
			}
		}

		// Integer nbrST = 0;
		// if(studentMail != null)
		// {
		// if(utilServices.isValidEmailAddress(studentMail))
		// {
		// toMails.add(studentMail);
		// nbrST = nbrST + 1;
		// }
		// }

		Integer nbrEXP = 0;
		if (academicEncadrantMail != null) {
			if (utilServices.isValidEmailAddress(academicEncadrantMail)) {
				toMails.add(academicEncadrantMail);
				nbrEXP = nbrEXP + 1;
			}
		}

		String receiver = toMails.stream().collect(Collectors.joining(", "));

		// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		// String dateAffectation = dateFormata.format(new Date());

		String subject = "Affectation Expert";
		String studentFullName = utilServices.findStudentFullNameById(idEt);
		
		String content = "Nous vous informons par le présent mail que " + "Mme/M <strong><font color=grey> "
				+ expertFullName + "</font></strong> " + " a été affecté(e) en tant qu'"
				+ "<font color=grey>Expert </font> " + "à l'Étudiant(e) <strong><font color=grey> " + studentFullName + " </font></strong>.";
				
				
		if(!fichePFERepository.findTitleFichePFEByStudent(idEt).isEmpty())
		{
			String sujetPFETitle = fichePFERepository.findTitleFichePFEByStudent(idEt).get(0);
			content = "Nous vous informons par le présent mail que " + "Mme/M <strong><font color=grey> "
					+ expertFullName + "</font></strong> " + " a été affecté(e) en tant qu'"
					+ "<font color=grey>Expert </font> " + "à l'Étudiant(e) <strong><font color=grey> " + studentFullName + " </font></strong>. <br/>"
					+ "<font color=blue>Titre Sujet PFE : </font> " + sujetPFETitle;
		}
		

		utilServices.sendMailWithManyTOandManyCC(subject, receiver, ccReceps, content);
		/********************************************************************************************************/

		String result = "";
		if (nbrAE == 1 && nbrEXP == 1) {
			result = "YES";
			System.out.println("--------------> Result YES: AE+ EXP+");
		}
		if (nbrAE == 1 && nbrEXP == 0) {
			result = "NO-EXP";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrEXP == 1) {
			result = "NO-AE";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrEXP == 0) {
			result = "NO-AEEXP";
			System.out.println("--------------> Result NO: " + result);
		}

		return result;
	}

	@GetMapping("/cancelAffectAcademicEncadrantToStudent/{idEns}/{idEt}/{mailPC}/{justifCancelling}")
	public String cancelAffectAcademicEncadrantToStudent(@PathVariable String idEns, @PathVariable String idEt,
			@PathVariable String mailPC, @PathVariable String justifCancelling) {

		System.out.println("------------------------> CANCEL AFFECT AE-Stu: " + idEns + " - " + idEt);
		List<String> activatedYears = settingsRepository.findActivatedYears();
		List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(idEt, activatedYears);
		List<String> studentsCS = studentRepository.findStudentsFullNameCS(idEt, activatedYears);

		if (!studentsCJandALT.isEmpty()) {
			System.out.println("------------------------> CDJ-ALT");
			inscriptionRepository.affectAcademicEncadrantToStudentCJandALT(null, idEt);
		}
		if (!studentsCS.isEmpty()) {
			System.out.println("------------------------> CDS");
			inscriptionRepository.affectAcademicEncadrantToStudentCS(null, idEt);
		}

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		// Send Mail to Pedagogical Coordinator
		List<String> toMails = new ArrayList<String>();

		// String studentMail = "saria.essid@esprit.tn";System.out.println("---->
		// StudentCJ Mail: " + utilServices.findStudentMailById(idEt));
		String studentMail = utilServices.findStudentMailById(idEt); // DEPLOY_SERVER

		// String academicEncadrantMail =
		// "saria.essid@esprit.tn";System.out.println("----> Academic EncadrantMail
		// Mail: " + teacherRepository.findMailTeacherById(idEns));
		String academicEncadrantMail = teacherRepository.findMailTeacherById(idEns); // DEPLOY_SERVER
		String academicEncadrantFullName = teacherRepository.findNameTeacherById(idEns);

		Integer nbrAE = 0;
		if (academicEncadrantMail != null) {
			if (utilServices.isValidEmailAddress(academicEncadrantMail)) {
				toMails.add(academicEncadrantMail);
				nbrAE = nbrAE + 1;
			}
		}

		Integer nbrST = 0;
		if (studentMail != null) {
			if (utilServices.isValidEmailAddress(studentMail)) {
				toMails.add(studentMail);
				nbrST = nbrST + 1;
			}
		}

		String receiver = toMails.stream().collect(Collectors.joining(", "));

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateAffectation = dateFormata.format(new Date());

		String subject = "Annulation Affectation Encadrant Académique";
		String studentFullName = utilServices.findStudentFullNameById(idEt);

		String content = "Nous voulons vous informer par le présent mail que " + "Mme/M <strong><font color=grey> "
				+ academicEncadrantFullName + "</font></strong> "
				+ "<strong><font color=red> n'est plus </font></strong> "
				+ "<font color=grey> l'Encadrant Académique </font> " + "de l'Étudiant <strong><font color=grey> "
				+ studentFullName + " </font></strong> " + "dès le <font color=blue> " + dateAffectation
				+ " </font>.<br/>" + "<font color=red> Motif Annulation: </font> " + justifCancelling + ".";

		utilServices.sendMailWithManyTOandManyCC(subject, receiver, mailPC, content);
		/********************************************************************************************************/

		String result = "";
		if (nbrAE == 1 && nbrST == 1) {
			result = "YES";
			System.out.println("--------------> Result YES: AE+ ST+");
		}
		if (nbrAE == 1 && nbrST == 0) {
			result = "NO-ST";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrST == 1) {
			result = "NO-AE";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrST == 0) {
			result = "NO-AEST";
			System.out.println("--------------> Result NO: " + result);
		}

		return result;
	}

	@GetMapping("/cancelAffectExpertToStudent/{idEns}/{idEt}/{mailPC}/{justifCancelling}")
	public String cancelAffectExpertToStudent(@PathVariable String idEns, @PathVariable String idEt,
			@PathVariable String mailPC, @PathVariable String justifCancelling) {

		System.out.println("------------------------> CANCEL AFFECT EXP-Stu: " + idEns + " - " + idEt);
		List<String> activatedYears = settingsRepository.findActivatedYears();
		List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(idEt, activatedYears);
		List<String> studentsCS = studentRepository.findStudentsFullNameCS(idEt, activatedYears);

		if (!studentsCJandALT.isEmpty()) {
			System.out.println("------------------------> CDJ-ALT");
			inscriptionRepository.affectExpertToStudentCJ(null, idEt);
		}
		if (!studentsCS.isEmpty()) {
			System.out.println("------------------------> CDS");
			inscriptionRepository.affectExpertToStudentCS(null, idEt);
		}

		/*****************************************
		 * Notification By Mail
		 *****************************************/
		// Send Mail to Pedagogical Coordinator
		List<String> toMails = new ArrayList<String>();

		// String academicExpertMail = "saria.essid@esprit.tn";
		String academicExpertMail = teacherRepository.findMailTeacherById(idEns); // DEPLOY_SERVER

		String expertFullName = teacherRepository.findNameTeacherById(idEns);

		String academicEncadrantMail = utilServices.findMailPedagogicalEncadrant(idEt);
		// findMailPedagogicalEncadrant
		Integer nbrAE = 0;
		if (academicExpertMail != null) {
			if (utilServices.isValidEmailAddress(academicExpertMail)) {
				toMails.add(academicExpertMail);
				nbrAE = nbrAE + 1;
			}
		}

		// Integer nbrST = 0;
		// if(studentMail != null)
		// {
		// if(utilServices.isValidEmailAddress(studentMail))
		// {
		// toMails.add(studentMail);
		// nbrST = nbrST + 1;
		// }
		// }

		Integer nbrEXP = 0;
		if (academicEncadrantMail != null) {
			if (utilServices.isValidEmailAddress(academicEncadrantMail)) {
				toMails.add(academicEncadrantMail);
				nbrEXP = nbrEXP + 1;
			}
		}

		String receiver = toMails.stream().collect(Collectors.joining(", "));

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateAffectation = dateFormata.format(new Date());

		String subject = "Annulation Affectation Expert";
		String studentFullName = utilServices.findStudentFullNameById(idEt);
		String content = "Nous voulons vous informer par le présent mail que " + "Mme/M <strong><font color=grey> "
				+ expertFullName + "</font></strong> " + "<strong><font color=red> n'est plus </font></strong> "
				+ "<font color=grey> l'Expert </font> " + "de l'Étudiant <strong><font color=grey> " + studentFullName
				+ " </font></strong> " + "dès le <font color=blue> " + dateAffectation + " </font>.<br/>"
				+ "<font color=red> Motif Annulation: </font> " + justifCancelling + ".";

		utilServices.sendMailWithManyTOandManyCC(subject, receiver, mailPC, content);
		/********************************************************************************************************/

		String result = "";
		if (nbrAE == 1 && nbrEXP == 1) {
			result = "YES";
			System.out.println("--------------> Result YES: AE+ EXP+");
		}
		if (nbrAE == 1 && nbrEXP == 0) {
			result = "NO-EXP";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrEXP == 1) {
			result = "NO-AE";
			System.out.println("--------------> Result NO: " + result);
		}
		if (nbrAE == 0 && nbrEXP == 0) {
			result = "NO-AEEXP";
			System.out.println("--------------> Result NO: " + result);
		}

		return result;
	}

	@GetMapping("/listLibCEPByYear")
	public List<String> listLibCEPByYear() throws IOException {
		List<String> libCEPs = strNomeCEPRepository.listLibCEPByYear("2021");
		return libCEPs;
	}

	@GetMapping("/listLibCEPByYearPARAM/{year}")
	public List<String> listLibCEPByYearPARAM(@PathVariable String year) throws IOException
	{
		List<String> libCEPs = strNomeCEPRepository.listLibCEPByYear(year);
		return libCEPs;
	}

	@GetMapping("/studentsTrainedByPCE/{options}")
	public List<StudentTrainedByPCDto> findStudentsByPCE(@PathVariable String options) {
		System.out.println("====== Current Date --> " + new Date());

		System.out.println("====== Options => " + options);

		String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(options).get(0);
		List<String> opts = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);

		// List<Object[]> objs = optionRepository.findStudentsByCodeOption(pureOpt);
		List<String> lowerOptions = new ArrayList<String>();
		for (String opt : opts) {
			String pureOpt = opt.replace("_01", "");
			lowerOptions.add(pureOpt.toLowerCase());
			System.out.println(
					"------sdf--------***sdv**-------- UNIT-pureOpt ------> " + opt + " - " + pureOpt.toLowerCase());
		}

		List<String> idStudents = new ArrayList<String>();
		for (String o : lowerOptions) {
			idStudents.addAll(utilServices.findStudentsByOption(o));
		}

		// List<String> idStudents = optionRepository.findStudentsByListOptions();

		// for(int i = 0; i<objs.size(); i++)
		// {
		// System.out.println("3-----------------------UNIT---> " + (String)
		// objs.get(i)[0] + " - " + (String) objs.get(i)[1] + "----->" +
		// objs.size() + " - " + idStudents.size());
		// }

		List<StudentTrainedByPCDto> studentsDtos = new ArrayList<StudentTrainedByPCDto>();
		for (String idStudent : idStudents) {

			// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);

			// System.out.println("---------------------------------------------> STU: " +
			// idStudent);
			List<String> lfps = fichePFERepository.findFichePFEByStudentUNIT(idStudent);

			// System.out.println("---------------------------------------------> STU-1: " +
			// lfps.size());

			String sessionLabel = "--";
			String etatTraitementDepotAE = "EN ATTENTE";
			String etatDepotFiche = "PAS ENCORE";
			Date dateDepot = null;

			if (!lfps.isEmpty()) {

				List<FichePFEWithoutFicheDepotPFEDto> lfs = fichePFERepository
						.findFichePFEWithoutFicheDepotPFEDto(idStudent);
				System.out.println("-------------> idStudent: " + idStudent + " - " + lfs.size());

				FichePFEWithoutFicheDepotPFEDto fichePFEWithoutFDP = new FichePFEWithoutFicheDepotPFEDto(null, "--", "",
						"");
				if (!lfs.isEmpty()) {
					System.out.println("-------------> PC 1: " + fichePFEWithoutFDP.getEtatFiche());
					System.out.println("-------------> PC 2: " + fichePFEWithoutFDP.getEtatValidDepot());

					fichePFEWithoutFDP = fichePFERepository.findFichePFEWithoutFicheDepotPFEDto(idStudent).get(0);
				}

				FichePFEForFicheDepotPFEDto fichePFEForFDP = fichePFERepository
						.findFichePFEForFicheDepotPFEDtoByStudent(idStudent);
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);

				if (fichePFE.getGrilleAcademicEncadrant() != null) {
					if (fichePFE.getGrilleAcademicEncadrant().getEtatGrille().equalsIgnoreCase("02")) {
						etatTraitementDepotAE = "TRAITE";
					}
				}

				if (fichePFEWithoutFDP.getDateDepotReports() != null) {
					dateDepot = fichePFEWithoutFDP.getDateDepotReports();
				}

				System.out.println(
						"----------***AZE---------------> etatDepotFiche: " + fichePFEWithoutFDP.getEtatValidDepot());
				if ((fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("03")
						|| fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("06")
						|| fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("07")
						|| fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("08"))
						&& fichePFEWithoutFDP.getEtatValidDepot().equalsIgnoreCase("01")) {
					etatDepotFiche = "DEPOSE";
				}
				if ((fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("03")
						|| fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("06")
						|| fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("07")
						|| fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("08"))
						&& fichePFEWithoutFDP.getEtatValidDepot().equalsIgnoreCase("02")) {
					etatDepotFiche = "VALIDE";
				}

				System.out.println(
						"----------***AZE-1--------------> etatDepotFiche: " + fichePFEWithoutFDP.getEtatValidDepot());
				System.out.println("----------***AZE-2--------------> etatDepotFiche: " + etatDepotFiche);
				System.out.println(
						"----------***AZE-3--------------> etatDepotFiche: " + fichePFEWithoutFDP.getEtatFiche());

				sessionLabel = fichePFEWithoutFDP.getSessionLabel();

			}

			studentsDtos.add(new StudentTrainedByPCDto(idStudent, utilServices.findStudentFullNameById(idStudent),
					currentClass, dateDepot, sessionLabel, etatDepotFiche, etatTraitementDepotAE));
		}

		System.out.println("=========hh====================================cccccccccc===============END=> " + new Date()
				+ " - " + studentsDtos.size());

		studentsDtos.sort(Comparator.comparing(StudentTrainedByPCDto::getFullName));

		return studentsDtos; // new ArrayList<>();
	}
	

	@GetMapping("/studentsTrainedByPCEAndYear/{options}/{year}")
	public List<StudentTrainedByPCDto> findStudentsByPCE(@PathVariable String options, @PathVariable String year)
	{

		System.out.println("====== year --> " + year);
		
		System.out.println("====== Options => " + options);
		
		String idPC = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinator(options).get(0);
		System.out.println("====== idPC => " + idPC);
		List<String> opts = pedagogicalCoordinatorRepository.listOptionsByPedagogicalCoordinator(idPC);//listOptionsByPedagogicalCoordinatorAndYear(idPC, year);
		System.out.println("====== Options => " + opts);
		// List<Object[]> objs = optionRepository.findStudentsByCodeOption(pureOpt);
		List<String> lowerOptions = new ArrayList<String>();
		for(String opt : opts)
		{
			String pureOpt = opt.replace("_01", "");
			lowerOptions.add(pureOpt.toLowerCase());
			System.out.println("------sdf--------***sdv**-------- UNIT-pureOpt ------> " + opt + " - " + pureOpt.toLowerCase());
		}
		
		List<String> idStudents = new ArrayList<String>();
		
		for(String o : lowerOptions)
		{
			if(o.equalsIgnoreCase("alinfo"))
			{
				System.out.println("######################################### ALINFO");
				List<String> optsALT = optionStudentALTRepository.findOptionALTByYear(year);
				System.out.println("3-----**---------- OPT ALT :" + optsALT);
				for(String oALT : optsALT)
				{
					idStudents.addAll(utilServices.findStudentsALTByYearAndOption(year, oALT));
				}
			}
			else
			{
				System.out.println("######################################### NOT ALINFO");
				idStudents.addAll(utilServices.findStudentsByYearAndOption(year, o));
			}
		}
		//List<String> idStudents = optionRepository.findStudentsByListOptions();
		
		
//		for(int i = 0; i<objs.size(); i++)
//		{
//			System.out.println("3-----------------------UNIT---> " +  (String) objs.get(i)[0] + " - " + (String) objs.get(i)[1] + "----->" + 
//										objs.size() + " - " + idStudents.size());
//		}

		List<StudentTrainedByPCDto> studentsDtos = new ArrayList<StudentTrainedByPCDto>();
		for(String idStudent : idStudents)
		{

			// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");  
			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
			
			// System.out.println("---------------------------------------------> STU: " + idStudent);
			List<String> lfps = fichePFERepository.findFichePFEByStudentUNIT(idStudent);
			
			// System.out.println("---------------------------------------------> STU-1: " + lfps.size());
			
			String sessionLabel = "--";
			String etatTraitementDepotAE = "EN ATTENTE";
			String etatDepotFiche = "PAS ENCORE";
			Date dateDepot = null;
			
			if(!lfps.isEmpty())
			{
				
				List<FichePFEWithoutFicheDepotPFEDto> lfs = fichePFERepository.findFichePFEWithoutFicheDepotPFEDto(idStudent);
				System.out.println("-------------> idStudent: " + idStudent + " - " + lfs.size());
				
				FichePFEWithoutFicheDepotPFEDto fichePFEWithoutFDP = new FichePFEWithoutFicheDepotPFEDto(null, "--", "", "");
				if(!lfs.isEmpty())
				{
					System.out.println("-------------> PC 1: " + fichePFEWithoutFDP.getEtatFiche());
					System.out.println("-------------> PC 2: " + fichePFEWithoutFDP.getEtatValidDepot());
					
					fichePFEWithoutFDP = fichePFERepository.findFichePFEWithoutFicheDepotPFEDto(idStudent).get(0);
				}
				
				FichePFEForFicheDepotPFEDto fichePFEForFDP = fichePFERepository.findFichePFEForFicheDepotPFEDtoByStudent(idStudent);
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
				
				if(fichePFE.getGrilleAcademicEncadrant() != null)
				{
					if(fichePFE.getGrilleAcademicEncadrant().getEtatGrille().equalsIgnoreCase("02"))
					{
						etatTraitementDepotAE = "TRAITE";
					}
				}

				if(fichePFEWithoutFDP.getDateDepotReports() != null)
				{
					dateDepot = fichePFEWithoutFDP.getDateDepotReports();
				}
				
				System.out.println("----------***AZE---------------> etatDepotFiche: " + fichePFEWithoutFDP.getEtatValidDepot());
				if
				(
					(
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("03")
						||
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("06")
						||
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("07")
						||
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("08")
					)
					&& 
					fichePFEWithoutFDP.getEtatValidDepot().equalsIgnoreCase("01")
				)
				{
					etatDepotFiche = "DEPOSE";
				}
				if
				(
					(
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("03")
						||
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("06")
						||
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("07")
						||
						fichePFEWithoutFDP.getEtatFiche().equalsIgnoreCase("08")
					)
					&& 
					fichePFEWithoutFDP.getEtatValidDepot().equalsIgnoreCase("02")
				)
				{
					etatDepotFiche = "VALIDE";
				}
				
				System.out.println("----------***AZE-1--------------> etatDepotFiche: " + fichePFEWithoutFDP.getEtatValidDepot());
				System.out.println("----------***AZE-2--------------> etatDepotFiche: " + etatDepotFiche);
				System.out.println("----------***AZE-3--------------> etatDepotFiche: " + fichePFEWithoutFDP.getEtatFiche());
				
					
				sessionLabel = fichePFEWithoutFDP.getSessionLabel();

			}
			

			String currentOption = "";
			if(currentClass.contains("4ALINFO"))
			{
				currentOption = optionStudentALTRepository.findOptionByStudentALTAndYear(idStudent, year);
				System.out.println("--> 2.1");
			}
			else
			{
				currentOption = utilServices.findOptionByClass(currentClass, optionRepository.listOptionsByYear(year)).replace("_01", "");
				System.out.println("--> 2.2");
			}
			
				
			studentsDtos.add(new StudentTrainedByPCDto(idStudent, utilServices.findStudentFullNameById(idStudent), currentOption, currentClass, dateDepot, sessionLabel, etatDepotFiche, etatTraitementDepotAE));
		}
		
		System.out.println("=========hh====================================cccccccccc===============END=> " + new Date() + " - " + studentsDtos.size());
		
		studentsDtos.sort(Comparator.comparing(StudentTrainedByPCDto::getFullName));
		
		System.out.println("=====>>>>= year --> " + year + " - " + studentsDtos.size());
		
		return studentsDtos; //new ArrayList<>();
	
	}
	
	
	@GetMapping("/findAllAcademicEncadrantsAndExpertsByYear/{year}")
	public List<TeacherQuotaEncadrementExpertiseDto> findAllAcademicEncadrantsAndExpertsByYear(@PathVariable String year)
	{

		System.out.println("-------------- START Treatment État Encadrement: " +  new Date());
		// Long numSemestre = new Long(1);
		
		List<TeacherQuotaEncadrementExpertiseDto> academicEncadrants = teacherRepository.allAcademicEncadrantsAndExperts(year);
		
		for(TeacherQuotaEncadrementExpertiseDto ae : academicEncadrants)
		{
			ae.setNbrEncadrements(utilServices.findNbrStudentsTrainedByPEAndYear(ae.getIdentifiant(), year));
			ae.setNbrExpertises(utilServices.findNbrStudentsTrainedByEXPAndYear(ae.getIdentifiant(), year));
		}
		
		System.out.println("-------------- End Treatment État Encadrement: " + new Date());
		return academicEncadrants;
	
	}
	
	@GetMapping("/findAllTeachersForPresidenceAndMembreByYear/{selectedSession}")
	public List<TeacherQuotaPresidenceMembreDto> findAllTeachersForPresidenceAndMembreByYear(@PathVariable String selectedSession)
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
		
		System.out.println("-------------- End Treatment État Encadrement: " + new Date());
		return teachersForSTN;
	
	}


}

package com.esprit.gdp.controllers;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.gdp.dto.GrilleAcademicEncadrantDto;
import com.esprit.gdp.dto.StudentGrilleAcademicEncadrantDto;
import com.esprit.gdp.dto.StudentNoteEngTrainingshipDto;
import com.esprit.gdp.dto.StudentToBeExpertisedForTimelineDto;
import com.esprit.gdp.dto.StudentToBeSupervisedForTimelineDto;
import com.esprit.gdp.dto.StudentTrainedByAEDto;
import com.esprit.gdp.dto.StudentsByJuryActorForSTNDto;
import com.esprit.gdp.files.GrilleEncadrantAcademique_PDF;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.EvaluationEngineeringTraining;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.FichePFEPK;
import com.esprit.gdp.models.GrilleAcademicEncadrant;
import com.esprit.gdp.models.GrilleAcademicEncadrantPK;
import com.esprit.gdp.models.NoteRestitution;
import com.esprit.gdp.models.NoteRestitutionPK;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.models.TraitementFichePFE;
import com.esprit.gdp.models.TraitementFichePK;
import com.esprit.gdp.payload.request.ForgotPwdWithEmailRequest;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.EvaluationEngineeringTrainingRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.GrilleAcademicEncadrantRepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.NoteRestitutionRepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.OptionStudentALTRepository;
import com.esprit.gdp.repository.PedagogicalCoordinatorRepository;
import com.esprit.gdp.repository.SessionRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.repository.TraitementFicheRepository;
import com.esprit.gdp.services.UtilServices;

//@RestController
//@CrossOrigin(origins = "http://193.95.99.194:8081")
//@RequestMapping("/api/academicEncadrant")

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "192.168.3.11:8081", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/academicEncadrant")
public class AcademicEncadrantController
{

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	ConventionRepository conventionRepository;
	
	@Autowired
	FichePFERepository fichePFERepository;
	
	@Autowired
	TraitementFicheRepository traitementFicheRepository;
	
	@Autowired
	InscriptionRepository inscriptionRepository;
	
	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	PedagogicalCoordinatorRepository pedagogicalCoordinatorRepository;
	
	@Autowired
	UtilServices utilServices;
	
	@Autowired
	OptionRepository optionRepository;
	
	@Autowired
	GrilleAcademicEncadrantRepository grilleAcademicEncadrantRepository;
	
	@Autowired
	NoteRestitutionRepository noteRestitutionRepository;
	
	@Autowired
	EvaluationEngineeringTrainingRepository evaluationEngTrRepository;
	
	@Autowired
	OptionStudentALTRepository optionStudentALTRepository;
	
	
	/****************************************************** Methods ******************************************************/

	@GetMapping("/findStudentsCJByAEAndYear/{mailEns}/{year}")
	public List<StudentNoteEngTrainingshipDto> findStudentsCJByAEAndYear(@PathVariable String mailEns, @PathVariable String year)
	{

		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
		// System.out.println("------------xwxwx--------> Id: " + idEns);
		
		List<StudentNoteEngTrainingshipDto> studentsDtoCJs = studentRepository.findStudentNoteEngTrainingshipCJByAEAndYear(idEns, year);

		// System.out.println("--------------------> 1: " + studentsDtoCJs.size());
		
		for(StudentNoteEngTrainingshipDto sn : studentsDtoCJs)
		{
			// System.out.println("--------**************************------------> Student Full Name: " + sn.getStudentFullName());
			
			BigDecimal noteEvalStgIng = null;
			
			if(evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(sn.getStudentId()) != null)
			{
				noteEvalStgIng = evaluationEngTrRepository.findNoteStageIngenieurByAEandStu(sn.getStudentId());
				sn.setStudentMarkSTr(noteEvalStgIng);
				sn.setPathJournalStageINGFile(evaluationEngTrRepository.findPathJournalStageING(sn.getStudentId()));
				sn.setPathAttestationStageINGFile(evaluationEngTrRepository.findPathAttestationStageING(sn.getStudentId()));
				sn.setPathRapportStageINGFile(evaluationEngTrRepository.findPathRapportStageING(sn.getStudentId()));
				sn.setEtatDepotStageING(evaluationEngTrRepository.findEtatDepotStageIngenieur(sn.getStudentId()));
			}
			
			if(sn.getStudentClasse().contains("4ALINFO"))
			{
				sn.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), year));
				// System.out.println("--> 2.1 : " + optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), year));
			}
			else
			{
				sn.setStudentOption(utilServices.findOptionByClass(sn.getStudentClasse(), optionRepository.listOptionsByYear(year)).replace("_01", ""));
				// System.out.println("--> 2.2");
			}
			
		}
		
		return studentsDtoCJs;
	
		/*
		 * String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
		
		System.out.println("------------xwxwx--------> Id: " + idEns);

		List<StudentNoteEngTrainingshipDto> studentsDtoCJs = studentRepository
				.findStudentNoteEngTrainingshipCJByAEAndYear(idEns, year);

		System.out.println("--------------------> 1: " + studentsDtoCJs.size());

		for (StudentNoteEngTrainingshipDto sn : studentsDtoCJs) {
			System.out.println(
					"--------**************************------------> Student Full Name: " + sn.getStudentFullName());

			BigDecimal noteEvalStgIng = null;

			if (evaluationStageINGRepository.findEvaluationStageIngenieurByStudent(sn.getStudentId()) != null) {
				System.out.println("--------**************************------------> PATH: "
						+ evaluationStageINGRepository.findPathStageIngenieurFile(sn.getStudentId()));
				noteEvalStgIng = evaluationStageINGRepository.findNoteStageIngenieurByAEandStu(sn.getStudentId());
				sn.setStudentMarkSTr(noteEvalStgIng);
				sn.setPathEvalStageINGFile(evaluationStageINGRepository.findPathStageIngenieurFile(sn.getStudentId()));
			}

		}

		return studentsDtoCJs;
		*/
	}

//	@GetMapping("/findStudentsCJByAE/{mailEns}")
//	public List<StudentNoteEngTrainingshipDto> findStudentsCJByAE(@PathVariable String mailEns)
//	{
//		
////		List<String> idStudents = new ArrayList<String>();
////				
////		List<String> idStudentCJs = teacherRepository.findStudentsByPE(teacherRepository.findIdTeacherByMailEns(mailEns));
////		List<String> idStudentALTs = teacherRepository.findStudentsALTByPE(teacherRepository.findIdTeacherByMailEns(mailEns));
////		List<String> idStudentCSs = teacherRepository.findStudentsCSByPE(teacherRepository.findIdTeacherByMailEns(mailEns));
////		idStudents.addAll(idStudentCJs);idStudents.addAll(idStudentALTs);idStudents.addAll(idStudentCSs);
////		
////		System.out.println("------- Encadrant Pédagogique Nbr Students CDJ: " + idStudentCJs.size());
////		System.out.println("------- Encadrant Pédagogique Nbr Students ALT: " + idStudentALTs.size());
////		System.out.println("------- Encadrant Pédagogique Nbr Students CDS: " + idStudentCSs.size());
////		
////		System.out.println("------- Encadrant Pédagogique Nbr Students CDJ + CDS: " + idStudentCJs.size());
////		
////		List<EncadrementStatusExcelDto> studentsDtos = new ArrayList<EncadrementStatusExcelDto>();
////		
////		for(String idStudent : idStudents)
////		{
////
////			String currentClass = utilServices.findCurrentClassByIdEt(idStudent);
////			
////			System.out.println("---------------------------------------------> STU: " + idStudent);
////			
////			String etatGiveMarkSTr = "EN ATTENTE";
////			BigDecimal markSTr = null;
////			String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
////			NoteStageIngenieur note = noteStageIngenieurRepository.findNoteStageIngenieurByAEandStu(idEns, idStudent);
////			
////			if(note != null)
////			{
////				etatGiveMarkSTr = "TRAITEE";
////				markSTr = note.getNoteStgIng();
////				System.out.println("--------------> STU: " + note.getNoteStgIng());
////				
////			}
////			if(note == null)
////			{
////				etatGiveMarkSTr = "EN ATTENTE";
////				System.out.println("--------------> EN ATTENTE: ");
////				
////			}
////			
////			studentsDtos.add(new EncadrementStatusExcelDto(idStudent, utilServices.findStudentFullNameById(idStudent), currentClass, etatGiveMarkSTr, markSTr));
////		}
//		
//		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
//		System.out.println("------------xwxwx--------> Id: " + idEns);
//		//List<StudentNoteEngTrainingshipDto> studentsDtos = new ArrayList<StudentNoteEngTrainingshipDto>();
//		
//		List<StudentNoteEngTrainingshipDto> studentsDtoCJs = studentRepository.findStudentNoteEngTrainingshipCJByAE(idEns);
//		/*List<StudentNoteEngTrainingshipDto> studentsDtoCJALTs = studentRepository.findStudentNoteEngTrainingshipCJALTByAE(idEns);
//		List<StudentNoteEngTrainingshipDto> studentsDtoCSs = studentRepository.findStudentNoteEngTrainingshipCSByAE(idEns);
//		
//		System.out.println("--------------------> 1: " + studentsDtoCJs.size());
//		System.out.println("--------------------> 2: " + studentsDtoCJALTs.size());
//		System.out.println("--------------------> 3: " + studentsDtoCSs.size());
//		
//		studentsDtos.addAll(studentsDtoCJs);studentsDtos.addAll(studentsDtoCJALTs);studentsDtos.addAll(studentsDtoCSs);*/
//		
//		
//		for(StudentNoteEngTrainingshipDto sn : studentsDtoCJs)
//		{
//			NoteStageIngenieur note = noteStageIngenieurRepository.findNoteStageIngenieurByAEandStu(idEns, sn.getStudentId(), "2021");
//
//			if(note != null)
//			{
//				sn.setStudentMarkSTr(note.getNoteStgIng());
//			}
//			if(note == null)
//			{
//				sn.setStudentMarkSTr(null);
//			}
//		}
//		
//		return studentsDtoCJs;
//	}
//	
	

	@GetMapping("/downloadJournalStgING")
	public ResponseEntity downloadJournalStgINGFromLocal(@RequestParam("fileName") String fileName)
	{
		String fn = utilServices.decodeEncodedValue(fileName);
	  	Path path = Paths.get(fn);
	  	UrlResource resource = null;
	  	try
	  	{
	  		resource = new UrlResource(path.toUri());
	  	}
	  	catch (MalformedURLException e)
	  	{
	  		e.printStackTrace();
	  	}
	  	return ResponseEntity.ok()
	  			.contentType(MediaType.parseMediaType("application/octet-stream"))
	  			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	  			.body(resource);
	}
	
	@GetMapping("/downloadAttestationStgING")
	public ResponseEntity downloadAttestationStgINGFromLocal(@RequestParam("fileName") String fileName)
	{
		String fn = utilServices.decodeEncodedValue(fileName);
	  	Path path = Paths.get(fn);
	  	UrlResource resource = null;
	  	try
	  	{
	  		resource = new UrlResource(path.toUri());
	  	}
	  	catch (MalformedURLException e)
	  	{
	  		e.printStackTrace();
	  	}
	  	return ResponseEntity.ok()
	  			.contentType(MediaType.parseMediaType("application/octet-stream"))
	  			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	  			.body(resource);
	}

	@GetMapping("/downloadRapportStgING")
	public ResponseEntity downloadRapportStgINGFromLocal(@RequestParam("fileName") String fileName)
	{
		String fn = utilServices.decodeEncodedValue(fileName);
	  	Path path = Paths.get(fn);
	  	UrlResource resource = null;
	  	try
	  	{
	  		resource = new UrlResource(path.toUri());
	  	}
	  	catch (MalformedURLException e)
	  	{
	  		e.printStackTrace();
	  	}
	  	return ResponseEntity.ok()
	  			.contentType(MediaType.parseMediaType("application/octet-stream"))
	  			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	  			.body(resource);
	}
	
	
	@GetMapping("/downloadFicheEvalStgING")
	public ResponseEntity downloadFicheEvalStgINGFromLocal(@RequestParam("fileName") String fileName)
	{
	  	Path path = Paths.get(fileName);
	  	UrlResource resource = null;
	  	try
	  	{
	  		resource = new UrlResource(path.toUri());
	  	}
	  	catch (MalformedURLException e)
	  	{
	  		e.printStackTrace();
	  	}
	  	return ResponseEntity.ok()
	  			.contentType(MediaType.parseMediaType("application/octet-stream"))
	  			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	  			.body(resource);
	}

    @GetMapping("/giveMarkforStudentEngTrainingship/{mailEns}/{idEt}/{noteStageIng}")
	public String giveMarkforStudentEngTrainingship(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteStageIng)
	{
    	
    	String mailAE = utilServices.decodeEncodedValue(mailEns);
    	
    	System.out.println("---------------b-----> Mail AE: " + mailAE);
    	
    	String idEns = teacherRepository.findIdTeacherByMailEns(mailAE);
    	
//    	NoteStageIngenieurPK noteStgIngPK = new NoteStageIngenieurPK(idEns, idEt, "2021");
//    	NoteStageIngenieur noteStageIngenieur = new NoteStageIngenieur(noteStgIngPK, noteStageIng, new Timestamp(System.currentTimeMillis()));
    	
//    	EvaluationStageIngenieurPK evaluationStageIngenieurPK = new EvaluationStageIngenieurPK(idEt, new Date());
//    	EvaluationStageIngenieur evaluationStageIngenieur = new EvaluationStageIngenieur(evaluationStageIngenieurPK, new Date(), noteStageIng);
    	
    	EvaluationEngineeringTraining esi = null;
    	if(evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(idEt) != null)
    	{
    		esi = evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(idEt);
    		esi.setDateSaisieNote(new Date());
    		esi.setNoteStagIngenieur(noteStageIng);
    		esi.setAcademicEncadrantId(idEns);
    		esi.setEtatDepot("04");
    	}
//    	else
//    	{
//        	esi = new EvaluationStageIngenieur(evaluationStageIngenieurPK, new Date(), noteStageIng);
//        	esi.setDateSaisieNote(new Date());
//    		esi.setNoteStagIngenieur(noteStageIng);
//    		esi.setAcademicEncadrantId(idEns);
//    	}
    	
    	evaluationEngTrRepository.save(esi);
    	
    	/***************************************** Notification By Mail *****************************************/
		// Send Mail to Pedagogical Coordinator
		List<String> toMails = new ArrayList<String>();
		
		// String studentMail = "saria.essidbb@esprit.tn";System.out.println("---JJJJ-> StudentCJ Mail: " + utilServices.findStudentMailById(idEt));
		// String studentMail = utilServices.findStudentMailById(idEt);  // DEPLOY_SERVER
		
		// String academicEncadrantMail = "saria.essid@esprit.tn";System.out.println("--vv--> Academic EncadrantMail Mail: " + teacherRepository.findMailTeacherById(idEns));
		// String academicEncadrantMail = teacherRepository.findMailTeacherById(idEns);  // DEPLOY_SERVER
		// String academicEncadrantFullName = teacherRepository.findNameTeacherById(idEns);
		
		
		
		Integer nbrAE = 0;
		if(mailAE != null)
		{
			if(utilServices.isValidEmailAddress(mailAE))
			{
				toMails.add(mailAE);
				nbrAE = nbrAE + 1;
			}
		}
		
//		Integer nbrST = 0;
//		if(studentMail != null)
//		{
//			if(utilServices.isValidEmailAddress(studentMail))
//			{
//				toMails.add(studentMail);
//				nbrST = nbrST + 1;
//			}
//		}
		
		
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateSaisieNoteStgIng = dateFormata.format(new Date());
						
		String subject = "Saisie Note Stage Ingénieur";
								
	    String content = "Nous voulons vous informer par le présent mail que Vous en tant que Encadrant Académique "
	    		       + "avez attribué la note <strong><font color=red> " + noteStageIng + "/20</font></strong> comme <strong><font color=grey> Note Stage Ingénieur </font></strong> "
	    		       + "à l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt) + "</font></strong> "
	    		       + "et c'est le <font color=grey> " + dateSaisieNoteStgIng + " </font>.";
	    
		/********************************************************************************************************/
		
		String result = "";
		if(nbrAE == 1) //&& nbrST == 1)
		{
			result = "YES";
			
			// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);
			utilServices.sendMail(subject, mailAE, content);  // DEPLOY_SERVER
			
			System.out.println("----1----------> Result YES: AE+ ST+");
		}
//		if(nbrAE == 1 && nbrST == 0)
//		{
//			result = "NO-ST";
//			System.out.println("--------------> Result NO: " + result);
//		}
		if(nbrAE == 0)// && nbrST == 1)
		{
			result = "NO-AE";
			System.out.println("--------------> Result NO: " + result);
		}
//		if(nbrAE == 0 && nbrST == 0)
//		{
//			result = "NO-AEST";
//			System.out.println("--------------> Result NO: " + result);
//		}
		
		return result;
	}
	
//    @GetMapping("/giveMarkforStudentEngTrainingship/{mailEns}/{idEt}/{noteStageIng}")
//	public String giveMarkforStudentEngTrainingship(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteStageIng)
//	{
//    	
//    	String mailAE = utilServices.decodeEncodedValue(mailEns);
//    	
//    	System.out.println("---------------b-----> Mail AE: " + mailAE);
//    	
//    	String idEns = teacherRepository.findIdTeacherByMailEns(mailAE);
//    	
//    	NoteStageIngenieurPK noteStgIngPK = new NoteStageIngenieurPK(idEns, idEt, "2021");
//    	NoteStageIngenieur noteStageIngenieur = new NoteStageIngenieur(noteStgIngPK, noteStageIng, new Timestamp(System.currentTimeMillis()));
//    	
//    	noteStageIngenieurRepository.save(noteStageIngenieur);
//    	
//    	/***************************************** Notification By Mail *****************************************/
//		// Send Mail to Pedagogical Coordinator
//		List<String> toMails = new ArrayList<String>();
//		
//		// String studentMail = "saria.essidbb@esprit.tn";System.out.println("---JJJJ-> StudentCJ Mail: " + utilServices.findStudentMailById(idEt));
//		// String studentMail = utilServices.findStudentMailById(idEt);  // DEPLOY_SERVER
//		
//		// String academicEncadrantMail = "saria.essid@esprit.tn";System.out.println("--vv--> Academic EncadrantMail Mail: " + teacherRepository.findMailTeacherById(idEns));
//		// String academicEncadrantMail = teacherRepository.findMailTeacherById(idEns);  // DEPLOY_SERVER
//		// String academicEncadrantFullName = teacherRepository.findNameTeacherById(idEns);
//		
//		
//		
//		Integer nbrAE = 0;
//		if(mailAE != null)
//		{
//			if(utilServices.isValidEmailAddress(mailAE))
//			{
//				toMails.add(mailAE);
//				nbrAE = nbrAE + 1;
//			}
//		}
//		
////		Integer nbrST = 0;
////		if(studentMail != null)
////		{
////			if(utilServices.isValidEmailAddress(studentMail))
////			{
////				toMails.add(studentMail);
////				nbrST = nbrST + 1;
////			}
////		}
//		
//		
//		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		String dateSaisieNoteStgIng = dateFormata.format(new Date());
//						
//		String subject = "Saisie Note Stage Ingénieur";
//								
//	    String content = "Nous voulons vous informer par le présent mail que Vous en tant que Encadrant Académique "
//	    		       + "a attribué la note <strong><font color=red> " + noteStageIng + "/20</font></strong> comme <strong><font color=grey> Note Stage Ingénieur </font></strong> "
//	    		       + "à l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt)+ "</font></strong> "
//	    		       + "et c'est le <font color=grey> " + dateSaisieNoteStgIng + " </font>.";
//	    
//		/********************************************************************************************************/
//		
//		String result = "";
//		if(nbrAE == 1) //&& nbrST == 1)
//		{
//			result = "YES";
//			
//			utilServices.sendMail(subject, "saria.essid@esprit.tn", content);
//			// utilServices.sendMail(subject, mailAE, content);  // DEPLOY_SERVER
//			
//			System.out.println("----bbb----------> Result YES: AE+ ST+");
//		}
////		if(nbrAE == 1 && nbrST == 0)
////		{
////			result = "NO-ST";
////			System.out.println("--------------> Result NO: " + result);
////		}
//		if(nbrAE == 0)// && nbrST == 1)
//		{
//			result = "NO-AE";
//			System.out.println("--------------> Result NO: " + result);
//		}
////		if(nbrAE == 0 && nbrST == 0)
////		{
////			result = "NO-AEST";
////			System.out.println("--------------> Result NO: " + result);
////		}
//		
//		return result;
//	}
    

    @GetMapping("/modifyMarkforStudentEngTrainingship/{mailEns}/{idEt}/{noteStageIng}")
	public String modifyMarkforStudentEngTrainingship(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteStageIng)
	{
    	System.out.println("----------------> mailEns: " + mailEns);
    	
    	String mailAE = utilServices.decodeEncodedValue(mailEns);
    	String idEns = teacherRepository.findIdTeacherByMailEns(mailAE);
    	
    	System.out.println("----------------> mailAE: " + mailAE);
    	System.out.println("----------------> idEns: " + idEns);
    	System.out.println("----------------> idEt: " + idEt);

    	
    	EvaluationEngineeringTraining esi = evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(idEt);
    	
    	BigDecimal firstMark = esi.getNoteStagIngenieur();
    	
    	esi.setDateSaisieNote(new Date());
		esi.setNoteStagIngenieur(noteStageIng);
		esi.setAcademicEncadrantId(idEns);
		evaluationEngTrRepository.save(esi);
    	
    	/*
    	NoteStageIngenieur note = noteStageIngenieurRepository.findNoteStageIngenieurByAEandStu(idEns, idEt, "2021");
    	BigDecimal firstMark = note.getNoteStgIng();
    	System.out.println("----------------> note: " + note.getIdNoteStageIngenieur());
    	note.setNoteStgIng(noteStageIng);
    	note.setDateSaisieNote(new Timestamp(System.currentTimeMillis()));
    	noteStageIngenieurRepository.save(note);
    	*/
    	
    	/***************************************** Notification By Mail *****************************************/
		// Send Mail to Pedagogical Coordinator
		List<String> toMails = new ArrayList<String>();
		
		Integer nbrAE = 0;
		if(mailAE != null)
		{
			if(utilServices.isValidEmailAddress(mailAE))
			{
				toMails.add(mailAE);
				nbrAE = nbrAE + 1;
			}
		}
		
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateSaisieNoteStgIng = dateFormata.format(new Date());
						
		String subject = "Modification Note Stage Ingénieur";
								
		String content = "Nous voulons vous informer par le présent mail que Vous en tant que Encadrant Académique "
 		       + "a changé la <strong><font color=grey> Note Stage Ingénieur </font></strong> "
 		       + "pour l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt)+ "</font></strong> "
 		       + "de <strong><font color=red> " + firstMark + "/20</font></strong> "
 		       + "à <strong><font color=red> " + noteStageIng + "/20</font></strong> "
 		       + "et c'est le <font color=grey> " + dateSaisieNoteStgIng + " </font>.";
	    
		/********************************************************************************************************/
		
		String result = "";
		if(nbrAE == 1) //&& nbrST == 1)
		{
			result = "YES";
			
			// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);
			utilServices.sendMail(subject, mailAE, content);  // DEPLOY_SERVER
			
			System.out.println("----2----------> Result YES: AE+ ST+");
		}

		if(nbrAE == 0)
		{
			result = "NO-AE";
			System.out.println("--------------> Result NO: " + result);
		}

		return result;
	}
    
	
//    @GetMapping("/modifyMarkforStudentEngTrainingship/{mailEns}/{idEt}/{noteStageIng}")
//	public String modifyMarkforStudentEngTrainingship(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteStageIng)
//	{
//    	System.out.println("----------------> mailEns: " + mailEns);
//    	
//    	String mailAE = utilServices.decodeEncodedValue(mailEns);
//    	String idEns = teacherRepository.findIdTeacherByMailEns(mailAE);
//    	
//    	System.out.println("----------------> mailAE: " + mailAE);
//    	System.out.println("----------------> idEns: " + idEns);
//    	System.out.println("----------------> idEt: " + idEt);
//    	
//    	NoteStageIngenieur note = noteStageIngenieurRepository.findNoteStageIngenieurByAEandStu(idEns, idEt, "2021");
//    	
//    	BigDecimal firstMark = note.getNoteStgIng();
//    	
//    	System.out.println("----------------> note: " + note.getIdNoteStageIngenieur());
//
//    	note.setNoteStgIng(noteStageIng);
//    	note.setDateSaisieNote(new Timestamp(System.currentTimeMillis()));
//    	
//    	noteStageIngenieurRepository.save(note);
//    	
//    	/***************************************** Notification By Mail *****************************************/
//		// Send Mail to Pedagogical Coordinator
//		List<String> toMails = new ArrayList<String>();
//		
//		Integer nbrAE = 0;
//		if(mailAE != null)
//		{
//			if(utilServices.isValidEmailAddress(mailAE))
//			{
//				toMails.add(mailAE);
//				nbrAE = nbrAE + 1;
//			}
//		}
//		
//		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		String dateSaisieNoteStgIng = dateFormata.format(new Date());
//						
//		String subject = "Modification Note Stage Ingénieur";
//								
//		String content = "Nous voulons vous informer par le présent mail que Vous en tant que Encadrant Académique "
// 		       + "a changé la <strong><font color=grey> Note Stage Ingénieur </font></strong> "
// 		       + "pour l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt)+ "</font></strong> "
// 		       + "de <strong><font color=red> " + firstMark + "/20</font></strong> "
// 		       + "à <strong><font color=red> " + noteStageIng + "/20</font></strong> "
// 		       + "et c'est le <font color=grey> " + dateSaisieNoteStgIng + " </font>.";
//	    
//		/********************************************************************************************************/
//		
//		String result = "";
//		if(nbrAE == 1) //&& nbrST == 1)
//		{
//			result = "YES";
//			
//			utilServices.sendMail(subject, "saria.essid@esprit.tn", content);
//			// utilServices.sendMail(subject, mailAE, content);  // DEPLOY_SERVER
//			
//			System.out.println("----bbb----------> Result YES: AE+ ST+");
//		}
//
//		if(nbrAE == 0)
//		{
//			result = "NO-AE";
//			System.out.println("--------------> Result NO: " + result);
//		}
//
//		return result;
//	}
   
    @GetMapping("/giveMarkforStudentByExpert/{mailEns}/{idEt}/{noteRest}")
	public String giveMarkforStudentByExpert(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteRest)
	{

    	String mailAExp = utilServices.decodeEncodedValue(mailEns);
    	String mailAEnc = utilServices.findMailPedagogicalEncadrant(idEt);
    	
    	System.out.println("--------------> Result NO: " + mailAExp + " - " + mailAEnc);
//    	String mailAExp = "mohamedamine.chebbi@esprit.tn";
//    	String mailAEnc = "hend.benmoussa@esprit.tn";
    	
    	String idAExp = teacherRepository.findIdTeacherByMailEns(mailAExp);
    	System.out.println("--------------> idAExp: " + idAExp);
    	
    	String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idEt) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

    	System.out.println("--------------> studentClasse: " + studentClasse);
    	System.out.println("--------------> studentOption: " + studentOption);
    	
    	// String mailCPS = pedagogicalCoordinatorRepository.gotMailPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String idCPS = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorById(idCPS).get(0);
//    	
//    	System.out.println("--------------> idCPS: " + idCPS);
//    	System.out.println("--------------> mailCPS: " + mailCPS);

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
    	
    	List<String> mailsAECPS = new ArrayList<String>();mailsAECPS.add(mailAEnc);mailsAECPS.add(mailCPS);
		String mailsAECPS_STR = mailsAECPS.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));
    	
    	
    	System.out.println("------1-----> idAExp: " + studentClasse);
    	System.out.println("------2-----> idAExp: " + studentOption);
    	System.out.println("------3-----> idAExp: " + mailCPS);
    	
    	System.out.println("------x---s-----2sss-----> idAExp: " + idAExp);
    	System.out.println("---------------2-----> mailAExp: " + mailAExp);
		System.out.println("---------------2-----> Mail: " + mailAEnc);
		
		Integer nbrAExp = 0;
		String result = "";
		if(idAExp == null)
		{
			result = "NO-AExp";
		}
		else
		{
			NoteRestitutionPK noteResPK = new NoteRestitutionPK(idAExp, idEt, "2021");
	    	NoteRestitution noteRestitution = new NoteRestitution(noteResPK, new Timestamp(System.currentTimeMillis()), noteRest);
	    	
	    	noteRestitutionRepository.save(noteRestitution);

	    	// Synchronize with Grille Evaluation
	    	if(grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt) != null)
	    	{
	  			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
	  			GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
	    					
	    		System.out.println("1 ---------> studentMarkRest: " + gae.getNoteExpert());
	    		gae.setNoteExpert(noteRest);
	    		grilleAcademicEncadrantRepository.save(gae);
	    		System.out.println("2 ---------> studentMarkRest: " + gae.getNoteExpert());
	    	}
	    	
	    	/***************************************** Notification By Mail *****************************************/
	    	
			if(mailAExp != null)
			{
				if(utilServices.isValidEmailAddress(mailAExp))
				{
					nbrAExp = nbrAExp + 1;
				}
			}
			
			Integer nbrAEnc = 0;
			if(mailAEnc != null)
			{
				if(utilServices.isValidEmailAddress(mailAEnc))
				{
					nbrAEnc = nbrAEnc + 1;
				}
			}
			
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateSaisieNoteRest = dateFormata.format(new Date());
							
			String subject = "Saisie Note Restitution";
									
		    String content = "Nous vous informons par le présent mail que vous avez "
		    		       + "attribué la note <strong><font color=red> " + noteRest + "/20</font></strong> comme <strong><font color=grey> Note Restitution </font></strong> "
		    		       + "à l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt)+ "</font></strong> "
		    		       + "le <font color=grey> " + dateSaisieNoteRest + " </font>.";
		    
			/********************************************************************************************************/
			
			result = "";
			if(nbrAExp == 1) //&& nbrST == 1)
			{
				result = "YES";
				
				// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);
				// utilServices.sendMailWithCC(subject, mailAExp, mailsAECPS_STR, content);
				// utilServices.sendMail(subject, mailAE, content);  // DEPLOY_SERVER
				
				utilServices.sendMailWithManyCC(subject, mailAExp, mailsAECPS_STR, content);
				
				System.out.println("----3----------> Result YES: AExp+ AEnc+");
			}
			if(nbrAExp == 1 && nbrAEnc == 0)
			{
				result = "NO-AEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0)
			{
				result = "NO-AExp";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0 && nbrAEnc == 0)
			{
				result = "NO-AExpAEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			
		}
		
		return result;
	
	}
    

    // MIG
    @GetMapping("/giveMarkforStudentByExpertAndYear/{mailEns}/{idEt}/{noteRest}/{year}")
	public String giveMarkforStudentByExpertAndYear(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteRest, @PathVariable String year)
	{

    	String mailAExp = utilServices.decodeEncodedValue(mailEns);
    	String mailAEnc = utilServices.findMailPedagogicalEncadrant(idEt);
    	
    	System.out.println("--------------> Result NO: " + mailAExp + " - " + mailAEnc);
//    	String mailAExp = "mohamedamine.chebbi@esprit.tn";
//    	String mailAEnc = "hend.benmoussa@esprit.tn";
    	
    	String idAExp = teacherRepository.findIdTeacherByMailEns(mailAExp);
    	System.out.println("--------------> idAExp: " + idAExp);
    	
    	String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALTAndYear(idEt, year) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear(year));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

    	System.out.println("--------------> studentClasse: " + studentClasse);
    	System.out.println("--------------> studentOption: " + studentOption);
    	
    	// String mailCPS = pedagogicalCoordinatorRepository.gotMailPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String idCPS = pedagogicalCoordinatorRepository.gotIdEnsPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	System.out.println("--------------> idCPS: " + idCPS);
//    	String mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorByIdNew(idCPS);

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
    	
    	System.out.println("--------------> mailCPS: " + mailCPS);
    	
    	List<String> mailsAECPS = new ArrayList<String>();mailsAECPS.add(mailAEnc);mailsAECPS.add(mailCPS);
		String mailsAECPS_STR = mailsAECPS.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));
    	
    	
    	System.out.println("------1-----> idAExp: " + studentClasse);
    	System.out.println("------2-----> idAExp: " + studentOption);
    	System.out.println("------3-----> idAExp: " + mailCPS);
    	
    	System.out.println("------x---s-----2sss-----> idAExp: " + idAExp);
    	System.out.println("---------------2-----> mailAExp: " + mailAExp);
		System.out.println("---------------2-----> Mail: " + mailAEnc);
		
		Integer nbrAExp = 0;
		String result = "";
		if(idAExp == null)
		{
			result = "NO-AExp";
		}
		else
		{
			NoteRestitutionPK noteResPK = new NoteRestitutionPK(idAExp, idEt, year);
	    	NoteRestitution noteRestitution = new NoteRestitution(noteResPK, new Timestamp(System.currentTimeMillis()), noteRest);
	    	
	    	noteRestitutionRepository.save(noteRestitution);

			// Synchronize with Grille Evaluation giveMarkforStudentByExpertAndYear
			if(grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt) != null)
			{
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
				GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
				
				System.out.println("1 ---------> studentMarkRest: " + gae.getNoteExpert());
				gae.setNoteExpert(noteRest);
				grilleAcademicEncadrantRepository.save(gae);
				System.out.println("2 ---------> studentMarkRest: " + gae.getNoteExpert());
			}
			
	    	/***************************************** Notification By Mail *****************************************/
	    	
			if(mailAExp != null)
			{
				if(utilServices.isValidEmailAddress(mailAExp))
				{
					nbrAExp = nbrAExp + 1;
				}
			}
			
			Integer nbrAEnc = 0;
			if(mailAEnc != null)
			{
				if(utilServices.isValidEmailAddress(mailAEnc))
				{
					nbrAEnc = nbrAEnc + 1;
				}
			}
			
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateSaisieNoteRest = dateFormata.format(new Date());
							
			String subject = "Saisie Note Restitution";
									
		    String content = "Nous vous informons par le présent mail que vous avez "
		    		       + "attribué la note <strong><font color=red> " + noteRest + "/20</font></strong> comme <strong><font color=grey> Note Restitution </font></strong> "
		    		       + "à l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt)+ "</font></strong> "
		    		       + "le <font color=grey> " + dateSaisieNoteRest + " </font>.";
		    
			/********************************************************************************************************/
			
			result = "";
			if(nbrAExp == 1) //&& nbrST == 1)
			{
				result = "YES";
				
				// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);
				// utilServices.sendMailWithCC(subject, mailAExp, mailsAECPS_STR, content);
				// utilServices.sendMail(subject, mailAE, content);  // DEPLOY_SERVER
				
				utilServices.sendMailWithManyCC(subject, mailAExp, mailsAECPS_STR, content);
				
				System.out.println("----3----------> Result YES: AExp+ AEnc+");
			}
			if(nbrAExp == 1 && nbrAEnc == 0)
			{
				result = "NO-AEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0)
			{
				result = "NO-AExp";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0 && nbrAEnc == 0)
			{
				result = "NO-AExpAEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			
		}
		
		return result;
	
	}
    
    @GetMapping("/sars")
	public String sars()
	{
    	String mailAExp = "amal.hammami@esprit.tn";
    	String mailAEnc = "mohamedamine.chebbi@esprit.tn";
    	
    	String idAExp = teacherRepository.findIdTeacherByMailEns(mailAExp);
    	
    	String mailCPS = "saria.essid@esprit.tn";
    	
    	List<String> mailsAECPS = new ArrayList<String>();mailsAECPS.add(mailAEnc);mailsAECPS.add(mailCPS);
    	String mailsAECPS_STR = mailsAECPS.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));
    	
    	System.out.println("------x---s-----2sss-----> idAExp: " + idAExp);
    	System.out.println("---------------2-----> mailAExp: " + mailAExp);
		System.out.println("---------------2-----> Mail: " + mailAEnc);
		
		Integer nbrAExp = 0;
		String result = "";
		if(idAExp == null)
		{
			result = "NO-AExp";
		}
		else
		{
			if(mailAExp != null)
			{
				if(utilServices.isValidEmailAddress(mailAExp))
				{
					nbrAExp = nbrAExp + 1;
				}
			}
			
			Integer nbrAEnc = 0;
			if(mailAEnc != null)
			{
				if(utilServices.isValidEmailAddress(mailAEnc))
				{
					nbrAEnc = nbrAEnc + 1;
				}
			}
			
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateSaisieNoteRest = dateFormata.format(new Date());
							
			String subject = "Modification Note Restitution";
			
			String content = "TEST ...";
		    
			/********************************************************************************************************/
			
			result = "";
			if(nbrAExp == 1) //&& nbrST == 1)
			{
				result = "YES";
				utilServices.sendMailWithManyCC(subject, mailAExp, mailsAECPS_STR, content);
				System.out.println("----3----------> Result YES: AExp+ AEnc+");
			}
			if(nbrAExp == 1 && nbrAEnc == 0)
			{
				result = "NO-AEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0)
			{
				result = "NO-AExp";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0 && nbrAEnc == 0)
			{
				result = "NO-AExpAEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			
		}
		
		return result;
	}
	
    @GetMapping("/modifyMarkforStudentByExpert/{mailEns}/{idEt}/{noteRest}")
	public String modifyMarkforStudentByExpert(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteRest)
	{

    	String mailAExp = utilServices.decodeEncodedValue(mailEns);
    	String mailAEnc = utilServices.findMailPedagogicalEncadrant(idEt);
    	
    	String idAExp = teacherRepository.findIdTeacherByMailEns(mailAExp);
    	
    	String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idEt) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

    	// String mailCPS = pedagogicalCoordinatorRepository.gotMailPedagogicalCoordinatorByOption(studentOption.toLowerCase());

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

    	
    	List<String> mailsAECPS = new ArrayList<String>();mailsAECPS.add(mailAEnc);mailsAECPS.add(mailCPS);
    	String mailsAECPS_STR = mailsAECPS.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));
    	
    	System.out.println("------x---s-----2sss-----> idAExp: " + idAExp);
    	System.out.println("---------------2-----> mailAExp: " + mailAExp);
		System.out.println("---------------2-----> Mail: " + mailAEnc);
		
		Integer nbrAExp = 0;
		String result = "";
		if(idAExp == null)
		{
			result = "NO-AExp";
		}
		else
		{
			
			NoteRestitution note = noteRestitutionRepository.findNoteRestitutionByAEandStu(idAExp, idEt, "2021");
	    	BigDecimal firstMark = note.getNoteRest();
	    	
	    	note.setNoteRest(noteRest);
	    	note.setDateSaisieNote(new Timestamp(System.currentTimeMillis()));
	    	
	    	noteRestitutionRepository.save(note);
	    	
	    	/***************************************** Notification By Mail *****************************************/
	    	
			if(mailAExp != null)
			{
				if(utilServices.isValidEmailAddress(mailAExp))
				{
					nbrAExp = nbrAExp + 1;
				}
			}
			
			Integer nbrAEnc = 0;
			if(mailAEnc != null)
			{
				if(utilServices.isValidEmailAddress(mailAEnc))
				{
					nbrAEnc = nbrAEnc + 1;
				}
			}
			
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateSaisieNoteRest = dateFormata.format(new Date());
							
			String subject = "Modification Note Restitution";
			
			String content = "Nous vous informons par le présent mail que vous avez "
	 		       + "changé la <strong><font color=grey> Note Restitution </font></strong> "
	 		       + "pour l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt)+ "</font></strong> "
	 		       + "de <strong><font color=red> " + firstMark + "/20</font></strong> "
	 		       + "à <strong><font color=red> " + noteRest + "/20</font></strong> "
	 		       + "le <font color=grey> " + dateSaisieNoteRest + " </font>.";
		    
			/********************************************************************************************************/
			
			result = "";
			if(nbrAExp == 1) //&& nbrST == 1)
			{
				result = "YES";
				utilServices.sendMailWithManyCC(subject, mailAExp, mailsAECPS_STR, content);
				System.out.println("----3----------> Result YES: AExp+ AEnc+");
			}
			if(nbrAExp == 1 && nbrAEnc == 0)
			{
				result = "NO-AEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0)
			{
				result = "NO-AExp";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0 && nbrAEnc == 0)
			{
				result = "NO-AExpAEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			
		}
		
		return result;
	}
  
    // MIG
    @GetMapping("/modifyMarkforStudentByExpertAndYear/{mailEns}/{idEt}/{noteRest}/{year}")
	public String modifyMarkforStudentByExpertAndYear(@PathVariable String mailEns, @PathVariable String idEt, @PathVariable BigDecimal noteRest, @PathVariable String year)
	{

    	String mailAExp = utilServices.decodeEncodedValue(mailEns);
    	String mailAEnc = utilServices.findMailPedagogicalEncadrant(idEt);
    	
//    	String mailAExp = "mohamedamine.chebbi@esprit.tn";
//    	String mailAEnc = "hend.benmoussa@esprit.tn";
    	
    	String idAExp = teacherRepository.findIdTeacherByMailEns(mailAExp);
    	
    	String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALTAndYear(idEt, year) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear(year));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

    	// String mailCPS = pedagogicalCoordinatorRepository.gotMailPedagogicalCoordinatorByOption(studentOption.toLowerCase());

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

    	
    	List<String> mailsAECPS = new ArrayList<String>();mailsAECPS.add(mailAEnc);mailsAECPS.add(mailCPS);
		String mailsAECPS_STR = mailsAECPS.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));
    	
    	System.out.println("------x---s-----2sss-----> idAExp: " + idAExp);
    	System.out.println("---------------2-----> mailAExp: " + mailAExp);
		System.out.println("---------------2-----> Mail: " + mailAEnc);
		
		Integer nbrAExp = 0;
		String result = "";
		if(idAExp == null)
		{
			result = "NO-AExp";
		}
		else
		{
			
			NoteRestitution note = noteRestitutionRepository.findNoteRestitutionByAEandStu(idAExp, idEt, year);
	    	BigDecimal firstMark = note.getNoteRest();
	    	
	    	note.setNoteRest(noteRest);
	    	note.setDateSaisieNote(new Timestamp(System.currentTimeMillis()));
	    	
	    	noteRestitutionRepository.save(note);

	    	// Synchronize with Grille Evaluation modifyMarkforStudentByExpertAndYear
	    	if(grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt) != null)
	    	{
	  			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
	  			GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
	    					
	    		System.out.println("1 ---------> studentMarkRest: " + gae.getNoteExpert());
	    		gae.setNoteExpert(noteRest);
	    		grilleAcademicEncadrantRepository.save(gae);
	    		System.out.println("2 ---------> studentMarkRest: " + gae.getNoteExpert());
	    	}
	    	
	    	/***************************************** Notification By Mail *****************************************/
	    	
			if(mailAExp != null)
			{
				if(utilServices.isValidEmailAddress(mailAExp))
				{
					nbrAExp = nbrAExp + 1;
				}
			}
			
			Integer nbrAEnc = 0;
			if(mailAEnc != null)
			{
				if(utilServices.isValidEmailAddress(mailAEnc))
				{
					nbrAEnc = nbrAEnc + 1;
				}
			}
			
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateSaisieNoteRest = dateFormata.format(new Date());
							
			String subject = "Modification Note Restitution";
			
			String content = "Nous vous informons par le présent mail que vous avez "
	 		       + "changé la <strong><font color=grey> Note Restitution </font></strong> "
	 		       + "pour l'Étudiant(e) <strong><font color=grey> " + utilServices.findStudentFullNameById(idEt)+ "</font></strong> "
	 		       + "de <strong><font color=red> " + firstMark + "/20</font></strong> "
	 		       + "à <strong><font color=red> " + noteRest + "/20</font></strong> "
	 		       + "le <font color=grey> " + dateSaisieNoteRest + " </font>.";
		    
			/********************************************************************************************************/
			
			result = "";
			if(nbrAExp == 1) //&& nbrST == 1)
			{
				result = "YES";
				
				// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);
				utilServices.sendMailWithManyCC(subject, mailAExp, mailsAECPS_STR, content);
				// utilServices.sendMail(subject, mailAE, content);  // DEPLOY_SERVER
				
				System.out.println("----3----------> Result YES: AExp+ AEnc+");
			}
			if(nbrAExp == 1 && nbrAEnc == 0)
			{
				result = "NO-AEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0)
			{
				result = "NO-AExp";
				System.out.println("--------------> Result NO: " + result);
			}
			if(nbrAExp == 0 && nbrAEnc == 0)
			{
				result = "NO-AExpAEnc";
				System.out.println("--------------> Result NO: " + result);
			}
			
		}
		
		return result;
	}
    
//    @GetMapping("/requestForExpert/{idEt}")
//	public void requestForExpert(@PathVariable String idEt)
//	{
//    	
//    	System.out.println("-----------------------> ID: " + idEt);
//    	
//    	String acadEncFullName = utilServices.findEncadrantPedagogiqueByStudentId(idEt).getNom();
//    	String acadEncMail = utilServices.findEncadrantPedagogiqueByStudentId(idEt).getMail();
//    	
//    	String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
//    	String studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("1988"));
//    	
//    	String idCPS = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorById(idCPS).get(0);
//    	
//    	String subject = "Demande Affectation Expert";
//    	
//		String content = "Nous voulons vous informer par le présent mail que l'Encadrant(e) Académique M/Mme "
//					   + "<font color=red> " + acadEncFullName + "</font>"
//					   + " a besoin d'un "
//				       + "<strong><font color=grey> Expert </font></strong> "
//				       + "pour son Étudiant à encadrer <font color=red> " + utilServices.findStudentFullNameById(idEt) + " </font> qui affecté(e) à l'option "
//				       + "<strong><font color=grey> " + studentOption.replace("_01", "") + "</font></strong>.";
//        
//		// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);  // LOCAL
//		utilServices.sendMailWithCC(subject, mailCPS, acadEncMail, content);  // SERVER
//    	
//    	System.out.println("-----------------------> studentClasse: " + studentClasse);
//    	System.out.println("-----------------------> studentOption: " + studentOption);
//    	System.out.println("-----------------------> acadEncFullName: " + acadEncFullName);
//    	System.out.println("-----------------------> mailCPS: " + mailCPS);
//    	
//	}
    

    @PutMapping(value = "/requestForExpertFormik", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> requestForExpertFormik(@RequestParam("idEt") String idEt, 
													@RequestParam("themTags") List<String> themTags,
													@RequestParam("techTags") List<String> techTags,
													@RequestParam("descTags") List<String> descTags)
	{

		System.out.println("-----------------------> idEt: " + idEt);
		System.out.println("-----------------------> themTags: " + themTags);
		System.out.println("-----------------------> techTags: " + techTags);
		System.out.println("-----------------------> descTags: " + descTags);

		List<String> decThemTags = new ArrayList<String>();
		List<String> decTechTags = new ArrayList<String>();
		List<String> decDescTags = new ArrayList<String>();

		for(String t : themTags)
		{
			String u = utilServices.decodeEncodedValue(t);
			decThemTags.add(u);
		}

		for(String t : techTags)
		{
			String u = utilServices.decodeEncodedValue(t);
			decTechTags.add(u);
		}

		for(String t : descTags)
		{
			String u = utilServices.decodeEncodedValue(t);
			decDescTags.add(u);
		}
    	    	
    	String acadEncFullName = utilServices.findEncadrantPedagogiqueByStudentId(idEt).getNom();
    	String acadEncMail = utilServices.findEncadrantPedagogiqueByStudentId(idEt).getMail();
    	
    	String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idEt) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

//    	String idCPS = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorById(idCPS).get(0);
    	
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
    	    	/***********************************************************************************************************/

    	
    	String subject = "Demande Affectation Expert";
    	
		String content = "Nous voulons vous informer par le présent mail que l'Encadrant(e) Académique M/Mme "
					   + "<font color=red> " + acadEncFullName + "</font>"
					   + " a besoin d'un "
				       + "<strong><font color=grey> Expert </font></strong> "
				       + "pour son Étudiant à encadrer <font color=red> " + utilServices.findStudentFullNameById(idEt) + " </font> qui affecté(e) à l'option "
				       + "<strong><font color=grey> " + studentOption.replace("_01", "") + "</font></strong>.<br/><br/>"
				       + "Ci-dessous quelques Tags qui peuvent vous aider :<br/>"
				       + "- <strong><font color=grey> Thématique(s)</font></strong> : " + decThemTags.stream().map(Object::toString).collect(Collectors.joining(", ")) + "<br/>"
				       + "- <strong><font color=grey> Technologie(s)</font></strong> : " + decTechTags.stream().map(Object::toString).collect(Collectors.joining(", ")) + "<br/>"
				       + "- <strong><font color=grey> Descipline(s)</font></strong> : " + decDescTags.stream().map(Object::toString).collect(Collectors.joining(", "));
        
		// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);  // LOCAL
		utilServices.sendMailWithCC(subject, mailCPS, acadEncMail, content);  // SERVER
    	
    	System.out.println("-----------------------> studentClasse: " + studentClasse);
    	System.out.println("---------xx--------------> studentOption: " + studentOption);
    	System.out.println("-----------------------> acadEncFullName: " + acadEncFullName);
    	System.out.println("-----------------------> mailCPS: " + mailCPS);
    	
    	return new ResponseEntity<>("Request FOR Expert is DONE", HttpStatus.OK);
		
	}
    
    
    @GetMapping("/requestForExpert/{idEt}")
	public void requestForExpert(@PathVariable String idEt)
	{
    	
    	System.out.println("-----------------------> ID: " + idEt);
    	
    	String acadEncFullName = utilServices.findEncadrantPedagogiqueByStudentId(idEt).getNom();
    	String acadEncMail = utilServices.findEncadrantPedagogiqueByStudentId(idEt).getMail();
    	
    	String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idEt) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

//    	String idCPS = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorById(idCPS).get(0);
    	
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
    	    	/***********************************************************************************************************/

    	
    	String subject = "Demande Affectation Expert";
    	
		String content = "Nous voulons vous informer par le présent mail que l'Encadrant(e) Académique M/Mme "
					   + "<font color=red> " + acadEncFullName + "</font>"
					   + " a besoin d'un "
				       + "<strong><font color=grey> Expert </font></strong> "
				       + "pour son Étudiant à encadrer <font color=red> " + utilServices.findStudentFullNameById(idEt) + " </font> qui affecté(e) à l'option "
				       + "<strong><font color=grey> " + studentOption.replace("_01", "") + "</font></strong>.";
        
		// utilServices.sendMail(subject, "saria.essid@esprit.tn", content);  // LOCAL
		utilServices.sendMailWithCC(subject, mailCPS, acadEncMail, content);  // SERVER
    	
    	System.out.println("-----------------------> studentClasse: " + studentClasse);
    	System.out.println("-----------------------> studentOption: " + studentOption);
    	System.out.println("-----------------------> acadEncFullName: " + acadEncFullName);
    	System.out.println("-----------------------> mailCPS: " + mailCPS);
    	
	}


	@GetMapping("/findStudentsTBSByAE/{mailEns}")
	public List<StudentToBeSupervisedForTimelineDto> findStudentsTBSByAE(@PathVariable String mailEns)
	{
	
		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
		System.out.println("------------xwxwx--------> Id: " + idEns);
		List<StudentToBeSupervisedForTimelineDto> studentsTBSDtos = new ArrayList<StudentToBeSupervisedForTimelineDto>();
		
		List<StudentToBeSupervisedForTimelineDto> studentsTBSDtoCJandALTs = studentRepository.findStudentToBeSupervisedForTimelineDtoCJandALTByAE(idEns);
		List<StudentToBeSupervisedForTimelineDto> studentsTBSDtoCSs = studentRepository.findStudentToBeSupervisedForTimelineDtoCSByAE(idEns);
		
		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
		
		studentsTBSDtos.addAll(studentsTBSDtoCJandALTs);studentsTBSDtos.addAll(studentsTBSDtoCSs);
		
		return studentsTBSDtos;
	}

	@GetMapping("/findStudentsTBSByAEAndYear/{mailEns}/{year}")
	public List<StudentToBeSupervisedForTimelineDto> findStudentsTBSByAEAndYear(@PathVariable String mailEns, @PathVariable String year)
	{
		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
		System.out.println("------------xwxwx--------> Id: " + idEns);
		List<StudentToBeSupervisedForTimelineDto> studentsTBSDtos = new ArrayList<StudentToBeSupervisedForTimelineDto>();
		
		List<StudentToBeSupervisedForTimelineDto> studentsTBSDtoCJandALTs = studentRepository.findStudentToBeSupervisedForTimelineDtoCJandALTByAEAndYear(idEns, year);
		List<StudentToBeSupervisedForTimelineDto> studentsTBSDtoCSs = studentRepository.findStudentToBeSupervisedForTimelineDtoCSByAEAndYear(idEns, year);
		
		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
		
		studentsTBSDtos.addAll(studentsTBSDtoCJandALTs);studentsTBSDtos.addAll(studentsTBSDtoCSs);

		for(StudentToBeSupervisedForTimelineDto sn : studentsTBSDtos)
		{
			if(sn.getStudentClasse().contains("4ALINFO"))
			{
				sn.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), year));
				System.out.println("--> 2.1");
			}
			else
			{
				sn.setStudentOption(utilServices.findOptionByClass(sn.getStudentClasse(), optionRepository.listOptionsByYear(year)).replace("_01", ""));
				System.out.println("--> 2.2");
			}
		}
		
		return studentsTBSDtos;
	}
	
	@GetMapping("/studentsForExpertiseByExpert/{mailEns}")
	public List<StudentToBeExpertisedForTimelineDto> studentsForExpertiseByExpert(@PathVariable String mailEns)
	{

		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
		System.out.println("------------EXPERT--------> Id: " + idEns);
		List<StudentToBeExpertisedForTimelineDto> studentsTBEDtos = new ArrayList<StudentToBeExpertisedForTimelineDto>();
		
		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCJandALTs = studentRepository.findStudentToBeExpertsedForTimelineDtoCJandALTByAE(idEns);
		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCSs = studentRepository.findStudentToBeExpertisedForTimelineDtoCSByAE(idEns);
		
		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
		
		studentsTBEDtos.addAll(studentsTBSDtoCJandALTs);studentsTBEDtos.addAll(studentsTBSDtoCSs);
		
		for(StudentToBeExpertisedForTimelineDto sn : studentsTBEDtos)
		{
			String idEt = sn.getStudentId();
			sn.setLetChangeNoteRestitution("YES");
			BigDecimal studentMarkRest = noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idEns, idEt, "2021");
			System.out.println("---------studentMarkRest: " + studentMarkRest);
			
			// Disable Modify Note Restitution if Grille Evaluation is VALIDATE
			if(grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt) != null)
			{
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
				GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
				
				
				if(gae.getEtatGrille().equalsIgnoreCase("02"))
				{
					sn.setLetChangeNoteRestitution("NO");
				}
			}
			/****************************************/ 
			

			if(studentMarkRest != null)
			{
				sn.setStudentMarkRest(studentMarkRest);
			}
			if(studentMarkRest == null)
			{
				sn.setStudentMarkRest(null);
			}
		}
		
		return studentsTBEDtos;
	
//		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
//		System.out.println("------------EXPERT--------> Id: " + idEns);
//		List<StudentToBeExpertisedForTimelineDto> studentsTBEDtos = new ArrayList<StudentToBeExpertisedForTimelineDto>();
//		
//		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCJandALTs = studentRepository.findStudentToBeExpertsedForTimelineDtoCJandALTByAE(idEns);
//		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCSs = studentRepository.findStudentToBeExpertisedForTimelineDtoCSByAE(idEns);
//		
//		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
//		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
//		
//		studentsTBEDtos.addAll(studentsTBSDtoCJandALTs);studentsTBEDtos.addAll(studentsTBSDtoCSs);
//		
//		for(StudentToBeExpertisedForTimelineDto sn : studentsTBEDtos)
//		{
//			NoteRestitution note = noteRestitutionRepository.findNoteRestitutionByAEandStu(idEns, sn.getStudentId(), "2021");
//
//			if(note != null)
//			{
//				sn.setStudentMarkRest(note.getNoteRest());
//			}
//			if(note == null)
//			{
//				sn.setStudentMarkRest(null);
//			}
//		}
//		
//		return studentsTBEDtos;
	}
	

	@GetMapping("/studentsForExpertiseByExpertAndYear/{mailEns}/{year}")
	public List<StudentToBeExpertisedForTimelineDto> studentsForExpertiseByExpertAndYear(@PathVariable String mailEns, @PathVariable String year)
	{

		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
		System.out.println("------------EXPERT--------> Id: " + idEns);
		List<StudentToBeExpertisedForTimelineDto> studentsTBEDtos = new ArrayList<StudentToBeExpertisedForTimelineDto>();
		
		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCJandALTs = studentRepository.findStudentToBeExpertsedForTimelineDtoCJandALTByAEAndYear(idEns, year);
		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCSs = studentRepository.findStudentToBeExpertisedForTimelineDtoCSByAEAndYear(idEns, year);
		
		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
		
		studentsTBEDtos.addAll(studentsTBSDtoCJandALTs);studentsTBEDtos.addAll(studentsTBSDtoCSs);
		
		for(StudentToBeExpertisedForTimelineDto sn : studentsTBEDtos)
		{
			String idEt = sn.getStudentId();
			BigDecimal studentMarkRest = noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idEns, idEt, year);
			System.out.println("---------studentMarkRest: " + studentMarkRest);
			
			// Disable Modify Note Restitution if Grille Evaluation is VALIDATE
			if(grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt) != null)
			{
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
				GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
				
				if(gae.getEtatGrille().equalsIgnoreCase("02"))
				{
					sn.setLetChangeNoteRestitution("NO");
				}
			}
			/****************************************/ 
			

			if(studentMarkRest != null)
			{
				sn.setStudentMarkRest(studentMarkRest);
			}
			if(studentMarkRest == null)
			{
				sn.setStudentMarkRest(null);
			}
			
			if(sn.getStudentClasse().contains("4ALINFO"))
			{
				sn.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), year));
				System.out.println("--> 2.1");
			}
			else
			{
				sn.setStudentOption(utilServices.findOptionByClass(sn.getStudentClasse(), optionRepository.listOptionsByYear(year)).replace("_01", ""));
				System.out.println("--> 2.2");
			}
			
		}
		
		return studentsTBEDtos;
	
//		String idEns = teacherRepository.findIdTeacherByMailEns(mailEns);
//		System.out.println("------------EXPERT--------> Id: " + idEns);
//		List<StudentToBeExpertisedForTimelineDto> studentsTBEDtos = new ArrayList<StudentToBeExpertisedForTimelineDto>();
//		
//		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCJandALTs = studentRepository.findStudentToBeExpertsedForTimelineDtoCJandALTByAEAndYear(idEns, year);
//		List<StudentToBeExpertisedForTimelineDto> studentsTBSDtoCSs = studentRepository.findStudentToBeExpertisedForTimelineDtoCSByAEAndYear(idEns, year);
//		
//		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
//		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
//		
//		studentsTBEDtos.addAll(studentsTBSDtoCJandALTs);studentsTBEDtos.addAll(studentsTBSDtoCSs);
//		
//		for(StudentToBeExpertisedForTimelineDto sn : studentsTBEDtos)
//		{
//			NoteRestitution note = noteRestitutionRepository.findNoteRestitutionByAEandStu(idEns, sn.getStudentId(), year);
//
//			if(note != null)
//			{
//				sn.setStudentMarkRest(note.getNoteRest());
//			}
//			if(note == null)
//			{
//				sn.setStudentMarkRest(null);
//			}
//		}
//		
//		return studentsTBEDtos;
	}
	
	// Traitement Plan de Travail : Validation - Refus
	@PutMapping(value = "/validateESPFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> validateESPFile(@RequestParam("idEt") String idEt, @RequestParam("mailAE") String mailAE)
	{
		
		System.out.println("----------------> StudentCJ Id: " + idEt);
		// Save in Plan de Travail
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setEtatFiche("03");
		fichePFERepository.save(fichePFE);
		
		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = new TraitementFichePFE();
		Timestamp dateRemiseFichePFE = traitementFicheRepository.findDateRemiseFichePFEByStudentAndFiche(idEt, fichePFE.getIdFichePFE().getDateDepotFiche()).get(0);
		
//		Timestamp sdkn = dateRemiseFichePFE;
//		System.out.println("---------------1----> : " + sdkn);
//		sdkn.setNanos(999999999);
//		System.out.println("---------------2----> : " + sdkn);
		
		System.out.println("---------------h----> Before: " + dateRemiseFichePFE);
		dateRemiseFichePFE.setNanos(999999000);
		System.out.println("-------------------> After: " + dateRemiseFichePFE);
		
		TraitementFichePK annulationFichePK = new TraitementFichePK(fichePFE.getIdFichePFE(), dateRemiseFichePFE);
		afp.setTraitementFichePK(annulationFichePK);
		afp.setTypeTrtFiche("03");
		afp.setDateTrtFiche(new Timestamp(System.currentTimeMillis()));
		afp.setFichePFETraitementFichePFE(fichePFE);
		traitementFicheRepository.save(afp);

		
		// Notification By Mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateTraitement = dateFormata.format(new Date());
		String subject = "Traitement Plan de Travail";
		

		 String studentMail = utilServices.findStudentMailById(idEt);   // DEPLOY_SERVER
		// String academicEncMail = mailAE;     // DEPLOY_SERVER
		
//		String studentMail = utilServices.findStudentMailById(idEt).substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
//		String academicEncMail = mailAE.substring(0, mailAE.lastIndexOf("@")) + "@mail.tn";
		
		 String content = "Nous vous informons par le présent mail que "
			       + "votre <strong><font color=grey> Encadrant(e) Académique </font></strong>"
			       + "<strong><font color=green> a validé </font></strong>"
			       + "votre "
			       + "<strong><font color=grey> Plan de Travail </font></strong>, "
			       + "et c'est le <font color=blue> " + dateTraitement + " </font>.";
		 
		utilServices.sendMailWithCC(subject, studentMail, mailAE, content);
			
		return new ResponseEntity<>("VALIDATING Plan de Travail is DONE", HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/refuseESPFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> refuseESPFile(@RequestParam("idEt") String idEt, @RequestParam("motifRefusESPFile") String motifRefusESPFile, @RequestParam("mailAE") String mailAE)
	{
		
		System.out.println("----------------> StudentCJ Id: " + idEt);
		System.out.println("----------------> motifRefusESPFile: " + motifRefusESPFile);
		
		// Save Plan de Travail
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		fichePFE.setEtatFiche("04");
		fichePFE.setMotifRefus(motifRefusESPFile);
		fichePFERepository.save(fichePFE);

		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = new TraitementFichePFE();
		Timestamp dateRemiseFichePFE = traitementFicheRepository.findDateRemiseFichePFEByStudentAndFiche(idEt, fichePFE.getIdFichePFE().getDateDepotFiche()).get(0);
		
		System.out.println("---------------h----> Before: " + dateRemiseFichePFE);
		dateRemiseFichePFE.setNanos(999999000);
		System.out.println("-------------------> After: " + dateRemiseFichePFE);
		
		TraitementFichePK annulationFichePK = new TraitementFichePK(fichePFE.getIdFichePFE(), dateRemiseFichePFE);
		afp.setTraitementFichePK(annulationFichePK);
		afp.setTypeTrtFiche("04");
		afp.setDateTrtFiche(new Timestamp(System.currentTimeMillis()));
		afp.setFichePFETraitementFichePFE(fichePFE);
		traitementFicheRepository.save(afp);

		
		// Notification By Mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateTraitement = dateFormata.format(new Date());
		String subject = "Traitement Plan de Travail";
		
		 String studentMail = utilServices.findStudentMailById(idEt);   // DEPLOY_SERVER
		// String academivEncMail = mailAE;     DEPLOY_SERVER
				
//		String studentMail = utilServices.findStudentMailById(idEt).substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
//		String academicEncMail = mailAE.substring(0, mailAE.lastIndexOf("@")) + "@mail.tn";
		
		
		String content = "Nous voulons vous informer par le présent mail que votre Plan de Travail"
					   + "<strong><font color=red> a été réfusé </font></strong>"
					   + "par votre "
					   + "<strong><font color=grey> Encadrant Académique </font></strong> le <font color=blue> " + dateTraitement + " </font>.<br/>"
					   + "<strong><font color=red>Motif de Refus </font></strong> : " + motifRefusESPFile +  " .";
        
		utilServices.sendMailWithCC(subject, studentMail, mailAE, content);
			
		return new ResponseEntity<>("REFUSING Plan de Travail is DONE", HttpStatus.OK);
		
	}
	
	
	// Traitement Demande Modification Plan de Travail : Validation - Refus
	@PutMapping(value = "/validateDemandeModifESPFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> validateDemandeModifESPFile(@RequestParam("idEt") String idEt, @RequestParam("mailAE") String mailAE, @RequestParam("dateDepotFiche") String dateDepotFiche, @RequestParam("dateSaisieDemande") String dateSaisieDemande)
	{
		
		System.out.println("----------------> StudentCJ Id: " + idEt);
		System.out.println("----------------> dateDepotFiche: " + dateDepotFiche);
		System.out.println("----------------> dateSaisieDemande: " + dateSaisieDemande);
		
		// Save in Plan de Travail
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudentAndDateDepotFiche(idEt, dateDepotFiche);
		fichePFE.setEtatFiche("01");
		fichePFERepository.save(fichePFE);
		
		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = traitementFicheRepository.findTraitementFicheByStudentAndFicheAndDateSaisieDemandeTrt(idEt, dateDepotFiche, dateSaisieDemande);
		afp.setEtatTrt("02");
		afp.setDateTrtFiche(new Timestamp(System.currentTimeMillis()));
		traitementFicheRepository.save(afp);
		
		System.out.println("----------------> Verify OK VDM: " + afp.getEtatTrt());

		// Notification By Mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateTraitement = dateFormata.format(new Date());
		String subject = "Traitement Demande Modification Plan de Travail";
		
		 String studentMail = utilServices.findStudentMailById(idEt);   // DEPLOY_SERVER
		// String academicEncMail = mailAE;     DEPLOY_SERVER
		
//		String studentMail = utilServices.findStudentMailById(idEt).substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
//		String academicEncMail = mailAE.substring(0, mailAE.lastIndexOf("@")) + "@mail.tn";
		
		String content = "Nous voulons vous informer par le présent mail que votre Demande de Modification "
					   + "du Plan de Travail <strong><font color=green> a été validée </font></strong> par "
				       + "votre <strong><font color=grey> Encadrant Académique </font></strong> le <font color=blue> " + dateTraitement + " </font>.";
        
		utilServices.sendMailWithCC(subject, studentMail, mailAE, content);
			
		return new ResponseEntity<>("VALIDATING Demande Modification Plan de Travail is DONE", HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/refuseDemandeModifESPFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> refuseDemandeModifESPFile(@RequestParam("idEt") String idEt, @RequestParam("motifRefusDemandeModif") String motifRefusDemandeModif, @RequestParam("mailAE") String mailAE, @RequestParam("dateDepotFiche") String dateDepotFiche, @RequestParam("dateSaisieDemande") String dateSaisieDemande)
	{
		
		System.out.println("----------------> StudentCJ Id: " + idEt);
		System.out.println("----------------> motifRefusESPFile: " + motifRefusDemandeModif);
		
		// Save Plan de Travail
//		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
//		fichePFE.setEtatFiche("04");
//		fichePFE.setMotifRefus(motifRefusESPFile);
//		fichePFERepository.save(fichePFE);

		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = traitementFicheRepository.findTraitementFicheByStudentAndFicheAndDateSaisieDemandeTrt(idEt, dateDepotFiche, dateSaisieDemande);
		afp.setEtatTrt("03");
		afp.setMotifRefus(motifRefusDemandeModif);
		afp.setDateTrtFiche(new Timestamp(System.currentTimeMillis()));
		traitementFicheRepository.save(afp);

		System.out.println("----------------> Verify OK RDM: " + afp.getEtatTrt());
		
		// Notification By Mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateTraitement = dateFormata.format(new Date());
		String subject = "Traitement Demande Modification Plan de Travail";
		
		 String studentMail = utilServices.findStudentMailById(idEt);   // DEPLOY_SERVER
		// String academicEncMail = mailAE;     DEPLOY_SERVER
		
//		String studentMail = utilServices.findStudentMailById(idEt).substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
//		String academicEncMail = mailAE.substring(0, mailAE.lastIndexOf("@")) + "@mail.tn";
		
		String content = "Nous voulons vous informer par le présent mail que votre Demande de Modification du Plan de Travail a été réfusée par votre "
					   + "<strong><font color=grey> Encadrant Académique </font></strong> le <font color=blue> " + dateTraitement + " </font>.<br/>"
					   + "<strong><font color=red>Motif de Refus </font></strong> : " + motifRefusDemandeModif +  " .";
        
		utilServices.sendMailWithCC(subject, studentMail, mailAE, content);
			
		return new ResponseEntity<>("REFUSING Demande Modification Plan de Travail is DONE", HttpStatus.OK);
		
	}
	
	
	// Traitement Demande Annulation Plan de Travail : Validation - Refus
	@PutMapping(value = "/validateDemandeAnnulESPFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> validateDemandeAnnulESPFile(@RequestParam("idEt") String idEt, @RequestParam("mailAE") String mailAE, @RequestParam("dateDepotFiche") String dateDepotFiche, @RequestParam("dateSaisieDemande") String dateSaisieDemande, @RequestParam("typeTrtConvention") String typeTrtConvention)
	{
		
		System.out.println("----------------> StudentCJ Id: " + idEt);
		System.out.println("----------------> dateDepotFiche: " + dateDepotFiche);
		System.out.println("----------------> dateSaisieDemande: " + dateSaisieDemande);
		System.out.println("----------------> typeTrtConvention: " + typeTrtConvention);
		
		// Save in Plan de Travail
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudentAndDateDepotFiche(idEt, dateDepotFiche);
		fichePFE.setEtatFiche("05");
		fichePFERepository.save(fichePFE);
		
		
		// Save in Convention
		if(typeTrtConvention.equalsIgnoreCase("Oui"))
		{
			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
			String dateConvention = dateFormata.format(fichePFE.getIdFichePFE().getConventionPK().getDateConvention());
			Convention c = conventionRepository.findConventionByStudentAndDateConvention(idEt, dateConvention);
			c.setTraiter("04");
			conventionRepository.save(c);
		}
		
		
		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = traitementFicheRepository.findTraitementFicheByStudentAndFicheAndDateSaisieDemandeTrt(idEt, dateDepotFiche, dateSaisieDemande);
		afp.setEtatTrt("02");  // WRONG CODE ALTERTED ON 03.01.22 [02 -> 12]
		afp.setDateTrtFiche(new Timestamp(System.currentTimeMillis()));
		traitementFicheRepository.save(afp);
		
		System.out.println("----------******************************------> Verify OK VDM: " + afp.getEtatTrt());

		// Notification By Mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateTraitement = dateFormata.format(new Date());
		String subject = "Traitement Demande Annulation Plan de Travail";
		
		 String studentMail = utilServices.findStudentMailById(idEt);   // DEPLOY_SERVER
		// String academicEncMail = mailAE;     DEPLOY_SERVER
		
//		String studentMail = utilServices.findStudentMailById(idEt).substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
//		String academicEncMail = mailAE.substring(0, mailAE.lastIndexOf("@")) + "@mail.tn";
		
		String content = "Nous voulons vous informer par le présent mail que votre Demande "
				       + "d'Annulation du Plan de Travail <strong><font color=green> a été validée </font></strong> par "
				       + "votre <strong><font color=grey> Encadrant Académique </font></strong> le <font color=blue> " + dateTraitement + " </font>.";
        
		utilServices.sendMailWithCC(subject, studentMail, mailAE, content);
			
		return new ResponseEntity<>("VALIDATING Demande Annulation Plan de Travail is DONE", HttpStatus.OK);
		
	}
	
	
	@PutMapping(value = "/refuseDemandeAnnulESPFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> refuseDemandeAnnulESPFile(@RequestParam("idEt") String idEt, @RequestParam("motifRefusDemandeAnnul") String motifRefusDemandeAnnul, @RequestParam("mailAE") String mailAE, @RequestParam("dateDepotFiche") String dateDepotFiche, @RequestParam("dateSaisieDemande") String dateSaisieDemande)
	{
		
		System.out.println("----------------> StudentCJ Id: " + idEt);
		System.out.println("----------------> motifRefusESPFile: " + motifRefusDemandeAnnul);
		
		// Save Plan de Travail
//		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
//		fichePFE.setEtatFiche("04");
//		fichePFE.setMotifRefus(motifRefusESPFile);
//		fichePFERepository.save(fichePFE);

		// Save in Traitement Plan de Travail for History
		TraitementFichePFE afp = traitementFicheRepository.findTraitementFicheByStudentAndFicheAndDateSaisieDemandeTrt(idEt, dateDepotFiche, dateSaisieDemande);
		afp.setEtatTrt("03");
		afp.setMotifRefus(motifRefusDemandeAnnul);
		afp.setDateTrtFiche(new Timestamp(System.currentTimeMillis()));
		traitementFicheRepository.save(afp);

		System.out.println("----------------> Verify OK RDM: " + afp.getEtatTrt());
		
		// Notification By Mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateTraitement = dateFormata.format(new Date());
		String subject = "Traitement Demande Annulation Plan de Travail";
		
		 String studentMail = utilServices.findStudentMailById(idEt);   // DEPLOY_SERVER
		// String academicEncMail = mailAE;     DEPLOY_SERVER
		
//		String studentMail = utilServices.findStudentMailById(idEt).substring(0, utilServices.findStudentMailById(idEt).lastIndexOf("@")) + "@mail.tn";
//		String academicEncMail = mailAE.substring(0, mailAE.lastIndexOf("@")) + "@mail.tn";
		
		String content = "Nous voulons vous informer par le présent mail que votre Demande d'Annulation du Plan de Travail a été réfusée par votre "
					   + "<strong><font color=grey> Encadrant Académique </font></strong> le <font color=blue> " + dateTraitement + " </font>.<br/>"
					   + "<strong><font color=red>Motif de Refus </font></strong> : " + motifRefusDemandeAnnul +  " .";
        
		utilServices.sendMailWithCC(subject, studentMail, mailAE, content);
			
		return new ResponseEntity<>("REFUSING Demande Annulation Plan de Travail is DONE", HttpStatus.OK);
		
	}
	
	@GetMapping("/downloadGrilleAE/{idEt}/{selectedYear}")
	public ResponseEntity downloadGrilleAE(@PathVariable String idEt, @PathVariable String selectedYear) throws IOException
	{
    	
		StudentGrilleAcademicEncadrantDto stuGrilleAcademicEncadrantDto = utilServices.findStudentTreatDepotByHisId(idEt, selectedYear);
		
		FichePFEPK fichePFEPK = fichePFERepository.findFichePFEPKByStudent(idEt).get(0);
		GrilleAcademicEncadrant grilleAcademicEncadrant = grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFEPK);

		String fullNameAE = utilServices.findFullNamePedagogicalEncadrant(idEt);
		
    	String DSPath = "C:\\ESP-DOCS\\";
		String DSName = "Grille Encadrant Académique " + utilServices.findStudentFullNameById(idEt) + ".pdf";
		String DSFile = DSPath + DSName;
		
		new GrilleEncadrantAcademique_PDF(idEt, stuGrilleAcademicEncadrantDto, grilleAcademicEncadrant, fullNameAE, DSFile);
		
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

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

	}
	

	@GetMapping("/studentGrilleEncadrement/{idEt}/{selectedYear}")
	public StudentGrilleAcademicEncadrantDto findStudentGrilleEncadrementById(@PathVariable String idEt, @PathVariable String selectedYear)
	{
		StudentGrilleAcademicEncadrantDto stuGrilleAcademicEncadrantDto = utilServices.findStudentTreatDepotByHisId(idEt, selectedYear);
		
		return stuGrilleAcademicEncadrantDto;
	}

	@GetMapping("/notifyAEToFillGrille/{idEt}")
	public void notifyAEToFillGrille(@PathVariable String idEt)
	{

		System.out.println("--------------------##############################---> SARS ");
		// **************************************************************************** Notification by mail
		String studentFullName = utilServices.findStudentFullNameById(idEt);
		
		String subject = "Alerte Remplissage Grille Encadrant Académique";
		// dddd
		// Local
		String academicEncadrantMail = utilServices.findMailPedagogicalEncadrant(idEt);
    	/******************************************** Mail CPS *******************************************************/
				String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
				System.out.println("-----------cc---> studentClasse: " + studentClasse);
				System.out.println("-----xx---------> listOptionsByYear: " + optionRepository.listOptionsByYear("2021"));
				
				
				String studentOption = null;
				if(studentClasse.contains("4ALINFO"))
				{
					studentOption = optionStudentALTRepository.findOptionByStudentALTAndYear(idEt, "2021") + "_01";
				}
				else
				{
					studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
					// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
				}
				
		    	
		    	System.out.println("--------------> studentOption: " + studentOption);
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
    	
		
		String content = "Nous voulons vous informer par le présent mail que Vous - en tant que Encadrant(e) Académique <strong><font color=red>n'avez pas encore remplis</font></strong> "
					   + "la <strong><font color=grey>Grille Encadrement</font></strong> propre à votre Étudiant <strong><font color=grey>" + studentFullName + "</font></strong>.<br/>"
					   + "Nous vous prions de procéder à cette tâche.";
		
		String mailCD = utilServices.findChefDepartmentMailByClasse(studentClasse);
		
		List<String> mailsCPSCD = new ArrayList<String>(); mailsCPSCD.add(mailCPS);mailsCPSCD.add(mailCD);
		String mailsCPSCD_STR = mailsCPSCD.stream().map(plain -> '"' + StringEscapeUtils.escapeJava(plain) + '"').collect(Collectors.joining(", "));
    	
		System.out.println("-----------cc---> SARS: " + academicEncadrantMail + " - " + mailsCPSCD_STR);
		
		utilServices.sendMailWithManyCC(subject, academicEncadrantMail, mailsCPSCD_STR, content);
	}
	
	@GetMapping("/updateMyPhoneNumber/{idEns}/{phoneNbr}")
	public String updateMyPhoneNumber(@PathVariable String idEns, @PathVariable String phoneNbr)
	{
		//String decodedPhoneNbr = utilServices.decodeEncodedValue(encodedPhoneNbr);
		Teacher tr = teacherRepository.findByIdEns(idEns);
		tr.setTel1(phoneNbr);
		teacherRepository.save(tr);
		return "DONE";
	}
	
	@GetMapping("/grilleEncadrementDto/{idEt}")
	public GrilleAcademicEncadrantDto findGrilleEncadrementDto(@PathVariable String idEt)
	{

		GrilleAcademicEncadrantDto grilleAcademicEncadrantDto = new GrilleAcademicEncadrantDto("EMPTY");
		
		BigDecimal studentMarkRest = noteRestitutionRepository.findNoteRestitutionByStu(idEt, "2021");
		System.out.println("---------studentMarkRest: " + studentMarkRest);
		
		if(grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt) != null)
		{
			if(studentMarkRest != null)
			{
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
				GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
				System.out.println("1 ---------> studentMarkRest: " + gae.getNoteExpert());
				gae.setNoteExpert(studentMarkRest);
				grilleAcademicEncadrantRepository.save(gae);
				System.out.println("2 ---------> studentMarkRest: " + gae.getNoteExpert());
			}
			System.out.println("---------SARS NOT NULL");
			grilleAcademicEncadrantDto = grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt);
			grilleAcademicEncadrantDto.setEmptyObject("FILLED");
		}
		
		return grilleAcademicEncadrantDto;
		
	
		
//		GrilleAcademicEncadrantDto grilleAcademicEncadrantDto = new GrilleAcademicEncadrantDto("EMPTY");
//		
//		if(grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt) != null)
//		{
//			System.out.println("---------SARS NOT NULL");
//			grilleAcademicEncadrantDto = grilleAcademicEncadrantRepository.findGrilleEncadrementDtoByFiche(idEt);
//			grilleAcademicEncadrantDto.setEmptyObject("FILLED");
//		}
//		
//		return grilleAcademicEncadrantDto;
		
	}
	
	@GetMapping("/studentsTrainedByPE/{mailEns}")
	public List<StudentTrainedByAEDto> findStudentsByPE(@PathVariable String mailEns)
	{
		List<String> idStudentCJs = teacherRepository.findStudentsByPE(teacherRepository.findIdTeacherByMailEns(mailEns));
		List<String> idStudentCSs = teacherRepository.findStudentsCSByPE(teacherRepository.findIdTeacherByMailEns(mailEns));
		
		System.out.println("------- Encadrant Pédagogique Nbr Students CDJ: " + idStudentCJs.size());
		System.out.println("------- Encadrant Pédagogique Nbr Students CDS: " + idStudentCSs.size());
		
		idStudentCJs.addAll(idStudentCSs);
		
		System.out.println("------- Encadrant Pédagogique Nbr Students CDJ + CDS: " + idStudentCJs.size());
		
		List<StudentTrainedByAEDto> studentsDtos = new ArrayList<StudentTrainedByAEDto>();
		
		for(String idStudent : idStudentCJs)
		{

			String currentClass = null;
			List<String> currentClassCJ = inscriptionRepository.findCurrentClassByIdEt(idStudent);
			List<String> currentClassCS = inscriptionRepository.findCurrentClassCSByIdEt(idStudent);
			
			// System.out.println("----------------------------------> Class CJ: " + currentClassCJ.size() + " --- CS: " + currentClassCS.size());
			
			if(!currentClassCJ.isEmpty())
			{
				currentClass = currentClassCJ.get(0);
			}
			if(!currentClassCS.isEmpty())
			{
				currentClass = currentClassCS.get(0);
			}
			
			// System.out.println("---------------------------------------------> STU: " + idStudent);
			List<FichePFE> lfps = fichePFERepository.findFichePFEByStudent(idStudent);
			
			// System.out.println("---------------------------------------------> STU-1: " + lfps.size());
			
			String sessionLabel = "--";
			String etatTraitementDepot = "EN ATTENTE";
			String etatDepotFiche = "PAS ENCORE";
			Date dateDepot = null;
			
			if(!lfps.isEmpty())
			{
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
				
				if(fichePFE.getDateDepotReports() != null)
				{
					dateDepot = fichePFE.getDateDepotReports();
				}
				
				if(fichePFE.getGrilleAcademicEncadrant() != null)
				{
					if(fichePFE.getGrilleAcademicEncadrant().getEtatGrille().equalsIgnoreCase("02"))
					{
						etatTraitementDepot = "TRAITE";
					}
				}
				
				System.out.println("-------------------> LOL: " + fichePFE.getIdFichePFE().getConventionPK().getIdEt() + " - " + fichePFE.getValidDepot());
				
//				if(
//						(
//							fichePFE.getEtatFiche().equalsIgnoreCase("03")
//							||
//							fichePFE.getEtatFiche().equalsIgnoreCase("06")
//							||
//							fichePFE.getEtatFiche().equalsIgnoreCase("07")
//							||
//							fichePFE.getEtatFiche().equalsIgnoreCase("08")
//						)
//						&& 
//						fichePFE.getValidDepot() == null
//				  )
//				{
//					etatDepotFiche = "PAS ENCORE";
//				}
				
				System.out.println("================================================1===> " + fichePFE.getValidDepot());
				System.out.println("================================================2===> " + fichePFE.getEtatFiche());
				
				if (fichePFE.getValidDepot() != null)
				{
					if
					(
							(
								fichePFE.getEtatFiche().equalsIgnoreCase("03")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("06")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("07")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("08")
							)
							&& 
							fichePFE.getValidDepot().equalsIgnoreCase("01")
					  )
					{
						etatDepotFiche = "DEPOSE";
					}
					if
					(
							(
								fichePFE.getEtatFiche().equalsIgnoreCase("03")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("06")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("07")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("08")
							)
							&& 
							fichePFE.getValidDepot().equalsIgnoreCase("02")
					  )
					{
						etatDepotFiche = "VALIDE";
					}
				}
//				else
//				{
//					etatDepotFiche = "UNKNOWN";
//				}
				
				System.out.println("-------------------> RES: " + etatDepotFiche);
				
				if(fichePFE.getSession() != null)
				{
					sessionLabel = sessionRepository.findByIdSession(fichePFE.getSession().getIdSession()).getLibelleSession();
				}
				
				
			}
			
			System.out.println("---------------------> SARS-1: " + idStudent + " - " + dateDepot + " - " + etatDepotFiche + " - " + etatTraitementDepot);
			
			studentsDtos.add(new StudentTrainedByAEDto(idStudent, utilServices.findStudentFullNameById(idStudent), currentClass, dateDepot, sessionLabel, etatDepotFiche, etatTraitementDepot));
			
		
		}
		studentsDtos.sort(Comparator.comparing(StudentTrainedByAEDto::getFullName));
		return studentsDtos;
	}

	@GetMapping("/studentsTrainedByPEAndYear/{mailEns}/{year}")
	public List<StudentTrainedByAEDto> findStudentsByPEAndYear(@PathVariable String mailEns, @PathVariable String year)
	{
		List<String> idStudentCJs = teacherRepository.findStudentsByPEAndYear(year, teacherRepository.findIdTeacherByMailEns(mailEns));
		List<String> idStudentCSs = teacherRepository.findStudentsCSByPEAndYear(year, teacherRepository.findIdTeacherByMailEns(mailEns));
		
		System.out.println("----lol--- Encadrant Pédagogique Nbr Students CDJ: " + idStudentCJs.size());
		System.out.println("------- Encadrant Pédagogique Nbr Students CDS: " + idStudentCSs.size());
		
		idStudentCJs.addAll(idStudentCSs);
		
		System.out.println("------- Encadrant Pédagogique Nbr Students CDJ + CDS: " + idStudentCJs.size());
		
		List<StudentTrainedByAEDto> studentsDtos = new ArrayList<StudentTrainedByAEDto>();
		
		for(String idStudent : idStudentCJs)
		{
			// OPTIM2909
			String currentClass = null;
			List<String> currentClassCJ = inscriptionRepository.findCurrentClassByIdEt(idStudent);
			List<String> currentClassCS = inscriptionRepository.findCurrentClassCSByIdEt(idStudent);
			
			// System.out.println("----------------------------------> Class CJ: " + currentClassCJ.size() + " --- CS: " + currentClassCS.size());
			
			if(!currentClassCJ.isEmpty())
			{
				currentClass = currentClassCJ.get(0);
			}
			if(!currentClassCS.isEmpty())
			{
				currentClass = currentClassCS.get(0);
			}
			
			// System.out.println("---------------------------------------------> STU: " + idStudent);
			List<FichePFE> lfps = fichePFERepository.findFichePFEByStudent(idStudent);
			
			// System.out.println("---------------------------------------------> STU-1: " + lfps.size());
			
			String sessionLabel = "--";
			String etatTraitementDepot = "EN ATTENTE";
			String etatDepotFiche = "PAS ENCORE";
			Date dateDepot = null;
			
			if(!lfps.isEmpty())
			{
				FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idStudent).get(0);
				
				if(fichePFE.getDateDepotReports() != null)
				{
					dateDepot = fichePFE.getDateDepotReports();
				}
				
				if(fichePFE.getGrilleAcademicEncadrant() != null)
				{
					if(fichePFE.getGrilleAcademicEncadrant().getEtatGrille().equalsIgnoreCase("02"))
					{
						etatTraitementDepot = "TRAITE";
					}
				}
				
				System.out.println("-------------------> LOL: " + fichePFE.getIdFichePFE().getConventionPK().getIdEt() + " - " + fichePFE.getValidDepot());
				
				if (fichePFE.getValidDepot() != null)
				{
					if
					(
							(
								fichePFE.getEtatFiche().equalsIgnoreCase("03")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("06")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("07")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("08")
							)
							&& 
							fichePFE.getValidDepot().equalsIgnoreCase("01")
					  )
					{
						etatDepotFiche = "DEPOSE";
					}
					if
					(
							(
								fichePFE.getEtatFiche().equalsIgnoreCase("03")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("06")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("07")
								||
								fichePFE.getEtatFiche().equalsIgnoreCase("08")
							)
							&& 
							fichePFE.getValidDepot().equalsIgnoreCase("02")
					  )
					{
						etatDepotFiche = "VALIDE";
					}
				}
//				else
//				{
//					etatDepotFiche = "UNKNOWN";
//				}
				
				System.out.println("-------------------> RES: " + etatDepotFiche);
				
				if(fichePFE.getSession() != null)
				{
					sessionLabel = sessionRepository.findByIdSession(fichePFE.getSession().getIdSession()).getLibelleSession();
				}
				
				
			}
			
			System.out.println("---------------------> SARS-1: " + idStudent + " - " + dateDepot + " - " + etatDepotFiche + " - " + etatTraitementDepot);
			
			String currentOption = null;
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
			
			System.out.println("---------------------> SARS-1: " + idStudent + " - " + dateDepot + " - " + etatDepotFiche + " - " + etatTraitementDepot);
			
			studentsDtos.add(new StudentTrainedByAEDto(idStudent, utilServices.findStudentFullNameById(idStudent), currentClass, currentOption, dateDepot, sessionLabel, etatDepotFiche, etatTraitementDepot));
			
		}
		studentsDtos.sort(Comparator.comparing(StudentTrainedByAEDto::getFullName));
		return studentsDtos;
	}


	@GetMapping("/saveGrilleAE/{idEt}/{noteGEA1}/{noteGEA2}/{noteGEA3}/{noteGEA4}/{noteGEA5}/{noteGEA6}/{noteGEAa}/{noteGEAz}/{noteGEAe}/{noteGEAr}/{noteGEAt}/{noteGEAy}/{noteGEAu}/{noteGEAi}/{noteGEAo}/{etatGrille}")
	public void saveGrilleAE(@PathVariable String idEt,
							 @PathVariable BigDecimal noteGEA1, @PathVariable BigDecimal noteGEA2, @PathVariable BigDecimal noteGEA3, @PathVariable BigDecimal noteGEA4, @PathVariable BigDecimal noteGEA5, @PathVariable BigDecimal noteGEA6, 
							 @PathVariable BigDecimal noteGEAa, @PathVariable BigDecimal noteGEAz, @PathVariable BigDecimal noteGEAe, @PathVariable BigDecimal noteGEAr, 
							 @PathVariable BigDecimal noteGEAt, @PathVariable BigDecimal noteGEAy, @PathVariable BigDecimal noteGEAu, @PathVariable BigDecimal noteGEAi, 
							 @PathVariable BigDecimal noteGEAo, @PathVariable String etatGrille) throws ParseException
	{
		
		System.out.println("---------------------------> academicEncadrantMail: " + utilServices.findMailPedagogicalEncadrant(idEt));
		
		System.out.println("---------------------------> noteGEA1: " + noteGEA1);
		System.out.println("---------------------------> noteGEA2: " + noteGEA2);
		System.out.println("---------------------------> noteGEA3: " + noteGEA3);
		System.out.println("---------------------------> noteGEA4: " + noteGEA4);
		System.out.println("---------------------------> noteGEA5: " + noteGEA5);
		System.out.println("---------------------------> noteGEA6: " + noteGEA6);
		
		System.out.println("---------------------------> noteGEAa: " + noteGEAa);
		System.out.println("---------------------------> noteGEAz: " + noteGEAz);
		System.out.println("---------------------------> noteGEAe: " + noteGEAe);
		System.out.println("---------------------------> noteGEAr: " + noteGEAr);
		System.out.println("---------------------------> noteGEAt: " + noteGEAt);
		System.out.println("---------------------------> noteGEAy: " + noteGEAy);
		System.out.println("---------------------------> noteGEAu: " + noteGEAu);
		System.out.println("---------------------------> noteGEAi: " + noteGEAi);
		System.out.println("---------------------------> noteGEAo: " + noteGEAo);
		
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		
		GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
		// System.out.println("----------------> SARS : " + gae.getNoteFicheEvaluationMiParcour());
		
		GrilleAcademicEncadrantPK grilleAcademicEncadrantPK = new GrilleAcademicEncadrantPK(fichePFE.getIdFichePFE(), new Timestamp(System.currentTimeMillis()));
		
		if(gae == null)
		{
			GrilleAcademicEncadrant grilleAcademicEncadrant = new GrilleAcademicEncadrant(grilleAcademicEncadrantPK, etatGrille, noteGEAa, noteGEAz, noteGEAe, noteGEAr, noteGEAt, 
					                                          noteGEAy, noteGEAu, noteGEAi, noteGEAo, noteGEA1, noteGEA2, noteGEA3, noteGEA4, noteGEA5, noteGEA6);
			grilleAcademicEncadrantRepository.save(grilleAcademicEncadrant);
		}
		else
		{
			gae.setNotePlanningStage(noteGEAa);
			gae.setNoteBilanPeriodiqueDebutStage(noteGEAz);
			gae.setNoteBilanPeriodiqueMilieuStage(noteGEAe);
			gae.setNoteBilanPeriodiqueFinStage(noteGEAr);
			gae.setNoteJournalBord(noteGEAt);
			gae.setNoteFicheEvaluationMiParcour(noteGEAy);
			gae.setNoteFicheEvaluationFinale(noteGEAu);
			gae.setNoteRestitution1(noteGEAi);
			gae.setNoteRestitution2(noteGEAo);
			gae.setNoteRDVPedagogique(noteGEA1);
			gae.setNoteAppreciationGlobale(noteGEA2);
			gae.setNoteAcademicEncadrant(noteGEA3);
			gae.setNoteExpert(noteGEA4);
			gae.setNoteEncadrantEntreprise(noteGEA5);
			gae.setNoteFinaleEncadrement(noteGEA6);
			gae.setDateLastSave(new Timestamp(System.currentTimeMillis()));
			gae.setEtatGrille(etatGrille);
			
			grilleAcademicEncadrantRepository.save(gae);
		}
		
		
		
		
		// **************************************************************************** Notification by mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateSaveGrilleAE = dateFormata.format(new Date());
		
		// Student CPS
		String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALT(idEt) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear("2021"));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

//    	String idCPS = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorById(idCPS).get(0);

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

    	
		String studentFullName = utilServices.findStudentFullNameById(idEt);
		
		String subject = "Traitement Grille Encadrant Académique";
		
		// Local
		//String academicEncadrantMail = "saria.essid@esprit.tn";
		
		// Server
		String academicEncadrantMail = utilServices.findMailPedagogicalEncadrant(idEt);
		
		String actionType = "sauvegardé";
		if(etatGrille.equalsIgnoreCase("02"))
		{
			actionType = "validé";
		}
		
		String content = "Nous voulons vous informer par le présent mail que Vous - en tant que Encadrant Académique - avez " + actionType + " la <strong><font color=grey>Grille Encadrement</font></strong> "
					   + "pour votre Étudiant(e) <strong><font color=grey>" + studentFullName + "</font></strong> "
					   + "et c'est le <font color=blue> " + dateSaveGrilleAE + " </font>.";
        
		utilServices.sendMailWithCC(subject, academicEncadrantMail, mailCPS, content);
		
	}
	
	@GetMapping("/saveGrilleAEAndYear/{idEt}/{noteGEA1}/{noteGEA2}/{noteGEA3}/{noteGEA4}/{noteGEA5}/{noteGEA6}/{noteGEAa}/{noteGEAz}/{noteGEAe}/{noteGEAr}/{noteGEAt}/{noteGEAy}/{noteGEAu}/{noteGEAi}/{noteGEAo}/{etatGrille}/{year}")
	public void saveGrilleAEAndYear(@PathVariable String idEt,
							 @PathVariable BigDecimal noteGEA1, @PathVariable BigDecimal noteGEA2, @PathVariable BigDecimal noteGEA3, @PathVariable BigDecimal noteGEA4, @PathVariable BigDecimal noteGEA5, @PathVariable BigDecimal noteGEA6, 
							 @PathVariable BigDecimal noteGEAa, @PathVariable BigDecimal noteGEAz, @PathVariable BigDecimal noteGEAe, @PathVariable BigDecimal noteGEAr, 
							 @PathVariable BigDecimal noteGEAt, @PathVariable BigDecimal noteGEAy, @PathVariable BigDecimal noteGEAu, @PathVariable BigDecimal noteGEAi, 
							 @PathVariable BigDecimal noteGEAo, @PathVariable String etatGrille, @PathVariable String year) throws ParseException
	{
		
		System.out.println("---------------------------> academicEncadrantMail: " + utilServices.findMailPedagogicalEncadrant(idEt));
		
		System.out.println("---------------------------> noteGEA1: " + noteGEA1);
		System.out.println("---------------------------> noteGEA2: " + noteGEA2);
		System.out.println("---------------------------> noteGEA3: " + noteGEA3);
		System.out.println("---------------------------> noteGEA4: " + noteGEA4);
		System.out.println("---------------------------> noteGEA5: " + noteGEA5);
		System.out.println("---------------------------> noteGEA6: " + noteGEA6);
		
		System.out.println("---------------------------> noteGEAa: " + noteGEAa);
		System.out.println("---------------------------> noteGEAz: " + noteGEAz);
		System.out.println("---------------------------> noteGEAe: " + noteGEAe);
		System.out.println("---------------------------> noteGEAr: " + noteGEAr);
		System.out.println("---------------------------> noteGEAt: " + noteGEAt);
		System.out.println("---------------------------> noteGEAy: " + noteGEAy);
		System.out.println("---------------------------> noteGEAu: " + noteGEAu);
		System.out.println("---------------------------> noteGEAi: " + noteGEAi);
		System.out.println("---------------------------> noteGEAo: " + noteGEAo);
		
		FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idEt).get(0);
		
		GrilleAcademicEncadrant gae =  grilleAcademicEncadrantRepository.findGrilleByFiche(fichePFE.getIdFichePFE());
		// System.out.println("----------------> SARS : " + gae.getNoteFicheEvaluationMiParcour());
		
		GrilleAcademicEncadrantPK grilleAcademicEncadrantPK = new GrilleAcademicEncadrantPK(fichePFE.getIdFichePFE(), new Timestamp(System.currentTimeMillis()));
		
		String idExp = utilServices.findIdExpertByStudent(idEt);
		if(gae == null)
		{
			GrilleAcademicEncadrant grilleAcademicEncadrant = new GrilleAcademicEncadrant(grilleAcademicEncadrantPK, etatGrille, noteGEAa, noteGEAz, noteGEAe, noteGEAr, noteGEAt, 
					                                          noteGEAy, noteGEAu, noteGEAi, noteGEAo, noteGEA1, noteGEA2, noteGEA3, noteGEA4, noteGEA5, noteGEA6);

			if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExp, idEt, year) != null)
			{
				grilleAcademicEncadrant.setNoteExpert(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExp, idEt, year));
			}
			
			grilleAcademicEncadrantRepository.save(grilleAcademicEncadrant);
		}
		else
		{
			gae.setNotePlanningStage(noteGEAa);
			gae.setNoteBilanPeriodiqueDebutStage(noteGEAz);
			gae.setNoteBilanPeriodiqueMilieuStage(noteGEAe);
			gae.setNoteBilanPeriodiqueFinStage(noteGEAr);
			gae.setNoteJournalBord(noteGEAt);
			gae.setNoteFicheEvaluationMiParcour(noteGEAy);
			gae.setNoteFicheEvaluationFinale(noteGEAu);
			gae.setNoteRestitution1(noteGEAi);
			gae.setNoteRestitution2(noteGEAo);
			gae.setNoteRDVPedagogique(noteGEA1);
			gae.setNoteAppreciationGlobale(noteGEA2);
			gae.setNoteAcademicEncadrant(noteGEA3);
			gae.setNoteExpert(noteGEA4);
			gae.setNoteEncadrantEntreprise(noteGEA5);
			gae.setNoteFinaleEncadrement(noteGEA6);
			gae.setDateLastSave(new Timestamp(System.currentTimeMillis()));
			gae.setEtatGrille(etatGrille);

			if(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExp, idEt, year) != null)
			{
				gae.setNoteExpert(noteRestitutionRepository.findNoteRestitutionMarkByAEandStu(idExp, idEt, year));
			}
			
			grilleAcademicEncadrantRepository.save(gae);
		}
		
		
		
		
		// **************************************************************************** Notification by mail
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateSaveGrilleAE = dateFormata.format(new Date());
		
		// Student CPS
		String studentClasse = utilServices.findCurrentClassByIdEt(idEt);

    	String studentOption = null;
		if(studentClasse.contains("4ALINFO"))
		{
			studentOption = optionStudentALTRepository.findOptionByStudentALTAndYear(idEt, year) + "_01";
		}
		else
		{
			studentOption = utilServices.findOptionByClass(studentClasse, optionRepository.listOptionsByYear(year));
			// sd.setOption(utilServices.findOptionByClass(sd.getClasse(), optionRepository.listOptionsByYear("2021")).replace("_01", ""));	
		}

//    	String idCPS = pedagogicalCoordinatorRepository.gotIdPedagogicalCoordinatorByOption(studentOption.toLowerCase());
//    	String mailCPS = pedagogicalCoordinatorRepository.gotAllMAilsPedagogicalCoordinatorById(idCPS).get(0);
		
    	String idCPS = pedagogicalCoordinatorRepository.gotIdEnsPedagogicalCoordinatorByOption(studentOption.toLowerCase());
    	System.out.println("--------------> idCPS: " + idCPS);
    	

    	// ********************** Exceptional Case where same CPS has 2 Units : just for Current University year
    	    	String mailCPS = "saria.essid@esprit.tn";
    	    	if(
						studentOption.toUpperCase().contains("SIM") || studentOption.toUpperCase().contains("GAMIX")
						|| (studentOption.toUpperCase().contains("WIN") && studentClasse.toUpperCase().contains("5WIN"))
						|| studentOption.toUpperCase().contains("IOSYS")
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

		String studentFullName = utilServices.findStudentFullNameById(idEt);
		
		String subject = "Traitement Grille Encadrant Académique";
		
		// Local
		//String academicEncadrantMail = "saria.essid@esprit.tn";
		
		// Server
		String academicEncadrantMail = utilServices.findMailPedagogicalEncadrant(idEt);
		
		String actionType = "sauvegardé";
		if(etatGrille.equalsIgnoreCase("02"))
		{
			actionType = "validé";
		}
		
		String content = "Nous voulons vous informer par le présent mail que Vous - en tant que Encadrant Académique - avez " + actionType + " la <strong><font color=grey>Grille Encadrement</font></strong> "
					   + "pour votre Étudiant(e) <strong><font color=grey>" + studentFullName + "</font></strong> "
					   + "et c'est le <font color=blue> " + dateSaveGrilleAE + " </font>.";
        
		utilServices.sendMailWithCC(subject, academicEncadrantMail, mailCPS, content);
		
	}
	

	@GetMapping("/refuseDepotStageIngenieur/{idEt}/{motifRefusDepot}")
	public void refuseDepotStageIngenieur(@PathVariable String idEt, @PathVariable String motifRefusDepot)
	{
		
		System.out.println("-----------c-----> ID : " + idEt);
		System.out.println("----------------> mail : " + utilServices.findMailPedagogicalEncadrant(idEt));
		
		EvaluationEngineeringTraining eet = evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(idEt);
		
		System.out.println("----------------> getPathJournal : " + eet.getPathJournal());
		
		eet.setPathJournal(null);
		eet.setPathAttestation(null);
		eet.setPathRapport(null);
		eet.setEtatDepot("05");
		eet.setMotifRefusDepot(utilServices.decodeEncodedValue(motifRefusDepot));
		evaluationEngTrRepository.save(eet);
		
		/*************************************** Notification By Mail ***************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateRefusEngTr = dateFormata.format(new Date());
		
		// utilServices.findMailPedagogicalEncadrant(idEt);
		System.out.println("----------------Mail AE : " + utilServices.findMailPedagogicalEncadrant(idEt));
		
		String studentFullName = utilServices.findStudentFullNameById(idEt);
		
		String subject = "Refus Dépôt Stage Ingénieur";
		
		String academicEncadrantMail = utilServices.findMailPedagogicalEncadrant(idEt); // "saria.essid@esprit.tn";
		String studentMail = utilServices.findStudentMailById(idEt); // "saria.essid@esprit.tn";
		
		String content = "Nous voulons vous informer par le présent mail que Vous - en tant que Encadrant(e) Académique - avez <font color=red>refusé</font> le <strong><font color=grey>Dépôt Stage Ingénieur</font></strong> "
					   + "pour votre Étudiant(e) <strong><font color=grey>" + studentFullName + "</font></strong> "
					   + "et c'est le <font color=blue> " + dateRefusEngTr + " </font>.<br/>"
					   + "<strong><font color=red>Motif</font></strong> : " + utilServices.decodeEncodedValue(motifRefusDepot);
        
		utilServices.sendMailWithCC(subject, academicEncadrantMail, studentMail, content);
	}

	@GetMapping("/acceptDemandeModifDepotStageIngenieur/{idEt}/{year}")
	public String acceptDemandeModifDepotStageIngenieur(@PathVariable String idEt, @PathVariable String year)
	{
		
		System.out.println("-----------c-----> ID : " + idEt);
		//System.out.println("----------------> mail : " + utilServices.findMailPedagogicalEncadrant(idEt));
		
		EvaluationEngineeringTraining eet = evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(idEt);
		
		System.out.println("----------------> getPathJournal : " + eet.getPathJournal());
		
		eet.setPathJournal(null);
		eet.setPathAttestation(null);
		eet.setPathRapport(null);
		eet.setEtatDepot("06");
		evaluationEngTrRepository.save(eet);
		
		System.out.println("-------------1210---> EtatDepot : " + eet.getEtatDepot());
		
		/*************************************** Notification By Mail ***************************************/
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateAcceptDemEngTr = dateFormata.format(new Date());
		
		//utilServices.findMailPedagogicalEncadrant(idEt);
		//System.out.println("----------------Mail AE : " + utilServices.findMailPedagogicalEncadrant(idEt));
		
		String subject = "Validation Demande Modification Dépôt Stage Ingénieur";
		
		// LocalutilServices.findStudentMailById(idEt);
		String academicEncadrantMail = utilServices.findMailPedagogicalEncadrant(idEt);
		String studentMail = utilServices.findStudentMailById(idEt);
		
		String content = "Nous voulons vous informer par le présent mail que Votre Demande de Modification de <strong><font color=grey>Dépôt Stage Ingénieur</font></strong> a été <font color=green>accepté</font> par votre  "
					   + "<strong><font color=grey> Encadrant(e) académique </font></strong> "
					   + "et c'est le <font color=blue> " + dateAcceptDemEngTr + " </font>.<br/>"
					   + "Merci de procéder à mettre à jour vos documents.";
        
		utilServices.sendMailWithCC(subject, studentMail, academicEncadrantMail, content);//(subject, academicEncadrantMail, content);
		return "06";
		
	}
	

	@GetMapping("/studentsByJuryMemberForSTN/{mailMmbr}/{sessionLabel}")
	public List<StudentsByJuryActorForSTNDto> studentsByJuryMemberForSTN(@PathVariable String mailMmbr, @PathVariable String sessionLabel)
	{
	
		String idMmbr = teacherRepository.findIdTeacherByMailEns(mailMmbr);
		Integer idSession = sessionRepository.findIdSessionByLabelSession(sessionLabel);
		
		System.out.println("------------EXPERT--------> Id: " + sessionLabel);
		List<StudentsByJuryActorForSTNDto> studentsTBEDtos = new ArrayList<StudentsByJuryActorForSTNDto>();
		
		// "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'
		List<StudentsByJuryActorForSTNDto> studentsTBSDtoCJandALTs = fichePFERepository.findStudentCJALTByJuryMemberForSTN(idMmbr, idSession);
		List<StudentsByJuryActorForSTNDto> studentsTBSDtoCSs = fichePFERepository.findStudentCSByJuryMemberForSTN(idMmbr, idSession);
		
		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
		
		studentsTBEDtos.addAll(studentsTBSDtoCJandALTs);studentsTBEDtos.addAll(studentsTBSDtoCSs);
		
		String yearFromSession = Integer.toString(Integer.parseInt(sessionLabel.substring(sessionLabel.length()-4))-1);
		if(
				sessionLabel.contains("Septembre") || sessionLabel.contains("Octobre") || 
				sessionLabel.contains("Novembre") || sessionLabel.contains("Decembre")
		)
		{
			yearFromSession = sessionLabel.substring(sessionLabel.length()-4);
		}
		
		for(StudentsByJuryActorForSTNDto sn : studentsTBEDtos)
		{
			System.out.println("--------------------> 2: " + sn.getStudentId());
			if(sn.getStudentClasse().contains("4ALINFO"))
			{
				sn.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), yearFromSession));
				System.out.println("--> 2.1 : " + optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), yearFromSession));
			}
			else
			{
				sn.setStudentOption(utilServices.findOptionByClass(sn.getStudentClasse(), optionRepository.listOptionsByYear(yearFromSession)).replace("_01", ""));
				System.out.println("--> 2.2");
			}
		}
		
		
		return studentsTBEDtos;
	}
	

	@GetMapping("/studentsByJuryPresidenceForSTN/{mailPres}/{sessionLabel}")
	public List<StudentsByJuryActorForSTNDto> studentsByJuryPresidenceForSTN(@PathVariable String mailPres, @PathVariable String sessionLabel)
	{
	
		String idPres = teacherRepository.findIdTeacherByMailEns(mailPres);
		Integer idSession = sessionRepository.findIdSessionByLabelSession(sessionLabel);
		
		System.out.println("------------EXPERT--------> Id: " + sessionLabel);
		List<StudentsByJuryActorForSTNDto> studentsTBEDtos = new ArrayList<StudentsByJuryActorForSTNDto>();
		
		// "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'
		List<StudentsByJuryActorForSTNDto> studentsTBSDtoCJandALTs = fichePFERepository.findStudentCJALTByJuryPresidentForSTN(idPres, idSession);
		List<StudentsByJuryActorForSTNDto> studentsTBSDtoCSs = fichePFERepository.findStudentCSByJuryPresidentForSTN(idPres, idSession);
		
		System.out.println("--------------------> 1: " + studentsTBSDtoCJandALTs.size());
		System.out.println("--------------------> 2: " + studentsTBSDtoCSs.size());
		
		studentsTBEDtos.addAll(studentsTBSDtoCJandALTs);studentsTBEDtos.addAll(studentsTBSDtoCSs);
		
		String yearFromSession = Integer.toString(Integer.parseInt(sessionLabel.substring(sessionLabel.length()-4))-1);
		if(
				sessionLabel.contains("Septembre") || sessionLabel.contains("Octobre") || 
				sessionLabel.contains("Novembre") || sessionLabel.contains("Decembre")
		)
		{
			yearFromSession = sessionLabel.substring(sessionLabel.length()-4);
		}
		
		for(StudentsByJuryActorForSTNDto sn : studentsTBEDtos)
		{
			System.out.println("--------------------> 2: " + sn.getStudentId());
			if(sn.getStudentClasse().contains("4ALINFO"))
			{
				sn.setStudentOption(optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), yearFromSession));
				System.out.println("--> 2.1 : " + optionStudentALTRepository.findOptionByStudentALTAndYear(sn.getStudentId(), yearFromSession));
			}
			else
			{
				sn.setStudentOption(utilServices.findOptionByClass(sn.getStudentClasse(), optionRepository.listOptionsByYear(yearFromSession)).replace("_01", ""));
				System.out.println("--> 2.2");
			}
		}
		
		return studentsTBEDtos;
	}	
	
}

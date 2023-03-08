package com.esprit.gdp.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import com.esprit.gdp.dto.ChefCoordConfigDto;
import com.esprit.gdp.dto.RespServiceStgConfigDto;
import com.esprit.gdp.dto.SectorEntrepriseDto;
import com.esprit.gdp.dto.Sessiondto;
import com.esprit.gdp.dto.StudentConfigDto;
import com.esprit.gdp.dto.StudentDetails;
import com.esprit.gdp.dto.TeacherConfigDto;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.PedagogicalCoordinator;
import com.esprit.gdp.models.ResponsableServiceStage;
import com.esprit.gdp.models.SecteurEntreprise;
import com.esprit.gdp.models.Session;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.StudentCS;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.models.Technologie;
import com.esprit.gdp.payload.request.ChefCoordConfigRequest;
import com.esprit.gdp.payload.request.RespServiceStgConfigRequest;
import com.esprit.gdp.payload.request.StudentConfigRequest;
import com.esprit.gdp.payload.request.TeacherConfigRequest;
import com.esprit.gdp.repository.ClasseRepository;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.OptionStudentALTRepository;
import com.esprit.gdp.repository.PedagogicalCoordinatorRepository;
import com.esprit.gdp.repository.ResponsableServiceStageRepository;
import com.esprit.gdp.repository.SaisonUniversitaireRepository;
import com.esprit.gdp.repository.SectorEntrepriseRepository;
import com.esprit.gdp.repository.SessionRepository;
import com.esprit.gdp.repository.StudentCSRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.repository.TechnologieRepository;
import com.esprit.gdp.services.EncadrementPFEService;
import com.esprit.gdp.services.ResponsableStageService;
import com.esprit.gdp.services.ServiceStageService;
import com.esprit.gdp.services.UtilServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
//@CrossOrigin(origins = "https://pfe.esprit.tn", allowedHeaders = "*")
//@CrossOrigin(origins = "https://pfe.esprit.tn")
@CrossOrigin(origins = "*")
@RequestMapping("/api/config")
public class ConfigController
{

	@Autowired
	TechnologieRepository TechnologieRepository;

	@Autowired
	TeacherRepository enseignantRepository;

	@Autowired
	private EncadrementPFEService encadrementPFEService;

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private InscriptionRepository inscriptionRepository;

	@Autowired
	private SectorEntrepriseRepository sectorEntrepriseRepository;

	@Autowired
	private ResponsableStageService responsableStageService;

	@Autowired
	private ServiceStageService serviceStageService;

	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	ClasseRepository classeRepository;

	@Autowired
	SaisonUniversitaireRepository saisonUniversitaireRepository;

	@Autowired
	CodeNomenclatureRepository codeNomenclatureRepository;

	@Autowired
	StudentCSRepository studentCSRepository;
	
	@Autowired
	ResponsableServiceStageRepository responsableServiceStageRepository;

	@Autowired
	PedagogicalCoordinatorRepository pedagogicalCoordinatorRepository;
	
	@Autowired
	OptionStudentALTRepository optionStudentALTRepository;
	
	@Autowired
	OptionRepository optionRepository;
	
	@Autowired
	UtilServices utilServices;
	
	@Autowired
	EntityManager em;

	
	private String decode(String value) throws UnsupportedEncodingException {
		return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
	}


	/************************************************ Students ************************************************/
	@GetMapping("/studentConfigs")
	public ResponseEntity<List<?>> getAllStudents()
	{
		String year = "2021";
		try 
		{
			List<StudentConfigDto> students = utilServices.findStudentsConfigDtoForConfig(year);
			System.out.println("-----------------------------> LOL : " + students.size());

			if (students.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else
			{
				for(StudentConfigDto sd : students)
				{
					String cls = sd.getClasseStudent();
					if(cls.startsWith("5ME21"))
					{
						String opt = cls.substring(cls.lastIndexOf("-") + 1);
						sd.setOptionStudent(opt);
						System.out.println("---> " + opt);
					}
					else if(cls.contains("4ALINFO"))
					{
						sd.setOptionStudent(optionStudentALTRepository.findOptionByStudentALTAndYear(sd.getIdStudent(), year));
//						System.out.println("--> 2.1");
					}
					else
					{
						sd.setOptionStudent(utilServices.findOptionByClass(sd.getClasseStudent(), optionRepository.listOptionsByYear(year)).replace("_01", ""));
//						System.out.println("--> 2.2");
					}
				}
				// students.sort(Comparator.comparing(StudentConfigDto::getOptionStudent).reversed().thenComparing(StudentConfigDto::getClasseStudent).reversed());
				students.sort(Comparator.comparing(StudentConfigDto::getOptionStudent));
				
				return new ResponseEntity<>(students, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/studentConfigs/{selectedYear}")
	public ResponseEntity<List<?>> getAllStudentsBySelectedYear(@PathVariable("selectedYear") String selectedYear)
	{
		try 
		{
			List<StudentConfigDto> students = utilServices.findStudentsConfigDtoForConfig(selectedYear);
			System.out.println("-------------------------YEEEEEEEEEEAR : " + students.size());

			if (students.isEmpty())
			{
				System.out.println("------EMPTY: " + students.size());
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else
			{
				for(StudentConfigDto sd : students)
				{
					String cls = sd.getClasseStudent();
					if(cls.startsWith("5ME21"))
					{
						String opt = cls.substring(cls.lastIndexOf("-") + 1);
						sd.setOptionStudent(opt);
						System.out.println("---> " + opt);
					}
					else if(cls.contains("4ALINFO"))
					{
						sd.setOptionStudent(optionStudentALTRepository.findOptionByStudentALTAndYear(sd.getIdStudent(), selectedYear));
						System.out.println("--> 2.1");
					}
					else
					{
						System.out.println(":--> 2.2.1: " + sd.getIdStudent() + " - " + sd.getClasseStudent());
						System.out.println(":--> 2.2.2: " + utilServices.findOptionByClass(sd.getClasseStudent(), optionRepository.listOptionsByYear(selectedYear)).replace("_01", ""));
						sd.setOptionStudent(utilServices.findOptionByClass(sd.getClasseStudent(), optionRepository.listOptionsByYear(selectedYear)).replace("_01", ""));
						
					}
					System.out.println("------lol: " + students.size());
				}
				System.out.println("------pika: " + students.size());
				// students.sort(Comparator.comparing(StudentConfigDto::getOptionStudent).reversed().thenComparing(StudentConfigDto::getClasseStudent).reversed());
				students.sort(Comparator.comparing(StudentConfigDto::getOptionStudent));
				System.out.println("------FILLED: " + students.size());
				return new ResponseEntity<>(students, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println("------*****EXCEPTION");
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/studentConfigs/updateStudentContact/{idEt}/{idRSS}/{selectedYear}")
	public ResponseEntity<?> updateStudentConfig(@PathVariable("idEt") String idEt, @PathVariable("idRSS") String idRSS, @PathVariable("selectedYear") String selectedYear, @RequestBody StudentConfigRequest stuReq) {
		
	    System.out.println("###   Id: " + idEt);
		List<String> studentsCJALT = studentRepository.findStudentCJMailEspById(idEt);
		List<String> studentsCS = studentRepository.findStudentCSMailEspById(idEt);
		
		System.out.println("#####   CJ/ALT: " + studentsCJALT.size());
		System.out.println("#####   CS: " + studentsCS.size());
		
		if(!studentsCJALT.isEmpty())
		{
			System.out.println("###########################   CJ");
			Optional<StudentCJ> stuObj = studentRepository.findById(idEt);

			StudentCJ stu = stuObj.get();
			stu.setTelet(stuReq.getTelStudent().trim());
			stu.setAdressemailesp(stuReq.getMailStudent().trim());
			studentRepository.save(stu);
			
			String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
			StudentConfigDto stuMailTelDto = new StudentConfigDto(stu.getIdEt(), stu.getTelet(), stu.getAdressemailesp(), stu.getPrenomet().trim() + " " + stu.getNomet().trim(), studentClasse, utilServices.findOptionByClasseStudent(idEt, studentClasse, selectedYear));
			
			sendMailChangeContactForStudent(idEt, idRSS);
			return new ResponseEntity<>(stuMailTelDto, HttpStatus.OK);
		}
		else if(!studentsCS.isEmpty())
		{
			System.out.println("###########################   CS");
			Optional<StudentCS> stuObj = studentCSRepository.findById(idEt);

			StudentCS stu = stuObj.get();
			stu.setTelet(stuReq.getTelStudent().trim());
			stu.setAdressemailesp(stuReq.getMailStudent().trim());
			studentCSRepository.save(stu);
			
			String studentClasse = utilServices.findCurrentClassByIdEt(idEt);
			StudentConfigDto stuMailTelDto = new StudentConfigDto(stu.getIdEt(), stu.getTelet(), stu.getAdressemailesp(), stu.getPrenomet().trim() + " " + stu.getNomet().trim(), studentClasse, utilServices.findOptionByClasseStudent(idEt, studentClasse, selectedYear));
			
			sendMailChangeContactForStudent(idEt, idRSS);
			
			return new ResponseEntity<>(stuMailTelDto, HttpStatus.OK);
		}
		else
		{
			System.out.println("###########################   NOT CJ + NOT CS");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/studentConfigs/initializePwdPFE/{idEt}/{idRSS}")
	public ResponseEntity<?> initializePwdPFEForStudent(@PathVariable("idEt") String idEt, @PathVariable("idRSS") String idRSS) {
		   
		// String year = "2021";
	    System.out.println("###   Id: " + idEt);
		List<String> studentsCJALT = studentRepository.findStudentCJMailEspById(idEt);
		List<String> studentsCS = studentRepository.findStudentCSMailEspById(idEt);
		
		System.out.println("#####   CJ/ALT: " + studentsCJALT.size());
		System.out.println("#####   CS: " + studentsCS.size());
		
		if(!studentsCJALT.isEmpty())
		{
			System.out.println("###########################   CJ");
			Optional<StudentCJ> stuObj = studentRepository.findById(idEt);

			StudentCJ stu = stuObj.get();
			stu.setPwdJWTEtudiant(null);
			stu.setDateModifyJwtPwd(null);
			studentRepository.save(stu);
			
			sendMailInitPwsPFePlatformForStudent(idEt, idRSS);
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else if(!studentsCS.isEmpty())
		{
			System.out.println("###########################   CS");
			Optional<StudentCS> stuObj = studentCSRepository.findById(idEt);

			StudentCS stu = stuObj.get();
			stu.setPwdJWTEtudiant(null);
			stu.setDateModifyJwtPwd(null);
			studentCSRepository.save(stu);
			
			sendMailInitPwsPFePlatformForStudent(idEt, idRSS);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			System.out.println("###########################   NOT CJ + NOT CS");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public void sendMailChangeContactForStudent(String idEt, String idRSS)
	{

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Modification Contacts Étudiant";
		
		String mailEt = utilServices.findStudentMailById(idEt);
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
		String fullNameRSS = responsableServiceStageRepository.findRespServStgFullNameById(idRSS);
		
//		String content = "Nous voulons vous informer par le présent mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a mis à jour </font></strong> "
//					   + "vos <strong><font color=grey>coordonnées de contact</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.";

		String content = "Nous tenons à vous informer que vos coordonnées <strong><font color=green>ont été mises à jour</font></strong> le <font color=grey> " + dateInitPwdPFE + " </font>"
		           + "par Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong> .";
		
		utilServices.sendMailWithCC(subject, mailEt, mailRSS, content);
	}
	
	public void sendMailInitPwsPFePlatformForStudent(String idEt, String idRSS)
	{

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Initialisation Mot de Passe Plateforme PFE";
		
		String mailEt = utilServices.findStudentMailById(idEt);
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
		
//		String content = "Nous voulons vous informer par le présent mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a initialisé</font></strong> "
//					   + "vote <strong><font color=grey>Mot de Passe</font></strong> "
//					   + "propre à <strong><font color=grey>la Plateforme PFE</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.<br/>"
//				       + "Vous pouvez accéder à votre Espace Étudiant en utilisant: <br/>"
//				       + "<strong><font color=blue>- Identifiant: </font></strong> " + idEt + " <br/>"
//				       + "<strong><font color=blue>- Mot de Passe: </font></strong> votre Mot de Passe Esprit Online (Celle de l'Espace Intranet)";
        
		String content = "Nous tenons à vous informer que votre Mot de Passe propre à <strong><font color=grey>la Plateforme PFE</font></strong> "
				       + "<strong><font color=green>a été initialisé</font></strong> le <font color=grey> " + dateInitPwdPFE + " </font>.<br/>"
				       + "Vous pouvez accéder à votre Espace Étudiant en utilisant: <br/>"
				       + "<strong><font color=blue>- Identifiant: </font></strong> " + idEt + " <br/>"
				       + "<strong><font color=blue>- Mot de Passe: </font></strong> votre Mot de Passe Esprit Online (Celle de l'Espace Intranet)";
		
		utilServices.sendMailWithCC(subject, mailEt, mailRSS, content);
	}


	/************************************************ Teacher ************************************************/
	@GetMapping("/teacherConfigs")
	public ResponseEntity<List<?>> getAllTeachers()
	{
		String year = "2021";
		try 
		{
			List<TeacherConfigDto> teachers = enseignantRepository.getActiveTeacherConfigFromPE(year);
			System.out.println("-----------------------------> LOL : " + teachers.size());

			if (teachers.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else
			{
				return new ResponseEntity<>(teachers, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/teacherConfigs/{selectedYear}")
	public ResponseEntity<List<?>> getAllTeachersBySelectedYear(@PathVariable("selectedYear") String selectedYear)
	{
		try 
		{
			List<TeacherConfigDto> teachers = enseignantRepository.getActiveTeacherConfigFromPE(selectedYear);
			System.out.println("-----------------------------> LOL : " + teachers.size());

			if (teachers.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else
			{
				return new ResponseEntity<>(teachers, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println("------*****EXCEPTION");
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/teacherConfigs/updateTeacherContact/{idEns}/{idRSS}")
	public ResponseEntity<?> updateTeacherConfig(@PathVariable("idEns") String idEns, @PathVariable("idRSS") String idRSS, @RequestBody TeacherConfigRequest stuReq) {
		
		System.out.println("###   Id: " + idRSS);
		
	    Optional<Teacher> teaObj = enseignantRepository.findById(idEns);
	    
		if(teaObj.isPresent())
		{
			System.out.println("###########################   CJ");
			Teacher tea = teaObj.get();
			tea.setTel1(stuReq.getTelTeacher().trim());
			tea.setMailEns(stuReq.getMailTeacher().toLowerCase().trim());
			enseignantRepository.save(tea);
			
			sendMailChangeContactForTeacher(idEns, idRSS);
			TeacherConfigDto teaMailTelDto = new TeacherConfigDto(tea.getIdEns(), tea.getTel1(), tea.getMailEns(), tea.getNomEns(), tea.getUp());
			System.out.println("###########################   teaMailTelDto: " + teaMailTelDto.getMailTeacher());
			return new ResponseEntity<>(teaMailTelDto, HttpStatus.OK);
		}
		else
		{
			System.out.println("###########################   NOT CJ + NOT CS");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/teacherConfigs/initializePwdPFE/{idEns}/{idRSS}")
	public ResponseEntity<?> initializePwdPFEForTeacher(@PathVariable("idEns") String idEns, @PathVariable("idRSS") String idRSS) {
		   
		
		Optional<Teacher> teaObj = enseignantRepository.findById(idEns);
	    
		if(teaObj.isPresent())
		{
			System.out.println("###########################   CJ");
			Teacher tea = teaObj.get();
			tea.setPwdJWTEnseignant(null);
			tea.setDateModifyJwtPwd(null);
			enseignantRepository.save(tea);
			
			sendMailInitPwsPFePlatformForTeacher(idEns, idRSS);
			System.out.println("####################***************#######   OK");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			System.out.println("###########################   ERROR");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public void sendMailChangeContactForTeacher(String idEns, String idRSS)
	{

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Modification Contacts Enseignant";
		
		String mailEns = enseignantRepository.findMailTeacherById(idEns);
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
//		String fullNameRSS = responsableServiceStageRepository.findRespServStgFullNameById(idRSS);
		
//		String content = "Nous voulons vous informer par le présent mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a mis à jour </font></strong> "
//					   + "vos <strong><font color=grey>coordonnées de contact</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.";
        
		String content = "Nous tenons à vous informer que vos coordonnées <strong><font color=green>ont été mises à jour</font></strong> le <font color=grey> " + dateInitPwdPFE + " </font> .";
//		               + "par Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong> .";
		utilServices.sendMailWithCC(subject, mailEns, mailRSS, content);
	}
	
	public void sendMailInitPwsPFePlatformForTeacher(String idEns, String idRSS)
	{

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Initialisation Mot de Passe Plateforme PFE pour Enseignant";
		
		String mailEns = enseignantRepository.findMailTeacherById(idEns);
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
//		String fullNameRSS = responsableServiceStageRepository.findRespServStgFullNameById(idRSS);
		
//		String content = "Nous voulons vous informer par le présent mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a initialisé</font></strong> "
//					   + "vote <strong><font color=grey>Mot de Passe</font></strong> "
//					   + "propre à <strong><font color=grey>la Plateforme PFE</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.<br/>"
//				       + "Vous pouvez accéder à votre Espace Étudiant en utilisant: <br/>"
//				       + "<strong><font color=blue>- Adresse E-Mail: </font></strong> " + mailEns + " <br/>"
//				       + "<strong><font color=blue>- Mot de Passe: </font></strong> votre Mot de Passe Esprit Online (Celle de l'Espace Intranet)";
        
		String content = "Nous tenons à vous informer que votre Mot de Passe propre à <strong><font color=grey>la Plateforme PFE</font></strong> "
					       + "<strong><font color=green>a été initialisé</font></strong> le <font color=grey> " + dateInitPwdPFE + " </font> .<br/>"
//			               + "par Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>.<br/>"
					       + "Vous pouvez accéder à votre Espace Étudiant en utilisant: <br/>"
					       + "<strong><font color=blue>- Adresse E-Mail: </font></strong> " + mailEns + " <br/>"
					       + "<strong><font color=blue>- Mot de Passe: </font></strong> votre Mot de Passe Esprit Online (Celle de l'Espace Intranet)";
		
		utilServices.sendMailWithCC(subject, mailEns, mailRSS, content);
	}


	/************************************************ RespServiceStg ************************************************/
	@GetMapping("/respServiceStgConfigs")
	public ResponseEntity<List<?>> getAllRespServiceStgs()
	{
		try 
		{
			List<RespServiceStgConfigDto> respServiceStgs = responsableServiceStageRepository.findRespServiceStgConfigDtoById();
			System.out.println("-----------------------------> LOL : " + respServiceStgs.size());

			if (respServiceStgs.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else
			{
				return new ResponseEntity<>(respServiceStgs, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/respServiceStgConfigs/updateRespServiceStgContact/{idResServiceStg}/{idRSS}")
	public ResponseEntity<?> updateRespServiceStgConfig(@PathVariable("idResServiceStg") String idResServiceStg, @PathVariable("idRSS") String idRSS, @RequestBody RespServiceStgConfigRequest respServStgReq)	
	{	
		System.out.println("###   Id: " + idRSS + " - " + idRSS);
		
	    Optional<ResponsableServiceStage> teaObj = responsableServiceStageRepository.findByIdUserSce(idResServiceStg);
	    
		if(teaObj.isPresent())
		{
			System.out.println("###########################   CJ");
			ResponsableServiceStage resp = teaObj.get();
			resp.setPhoneUserSce(respServStgReq.getTelRespServiceStg().trim());
			resp.setMailUserSce(respServStgReq.getMailRespServiceStg().toLowerCase().trim());
			responsableServiceStageRepository.save(resp);
			
			sendMailChangeContactForRespServiceStg(idResServiceStg, idRSS);
			RespServiceStgConfigDto teaMailTelDto = new RespServiceStgConfigDto(resp.getIdUserSce(), resp.getPhoneUserSce(), resp.getMailUserSce(), resp.getNomUserSce());
			System.out.println("###########################   teaMailTelDto: " + teaMailTelDto.getMailRespServiceStg());
			return new ResponseEntity<>(teaMailTelDto, HttpStatus.OK);
		}
		else
		{
			System.out.println("###########################   NOT CJ + NOT CS");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/respServiceStgConfigs/initializePwdPFE/{idResServiceStg}/{idRSS}")
	public ResponseEntity<?> initializePwdPFEForResponsableServiceStages(@PathVariable("idResServiceStg") String idResServiceStg, @PathVariable("idRSS") String idRSS) {
		   
	    Optional<ResponsableServiceStage> teaObj = responsableServiceStageRepository.findById(idResServiceStg);

	    if(teaObj.isPresent())
		{
	    	ResponsableServiceStage resp = teaObj.get();
	    	resp.setPwdJWTRSS(null);
	    	resp.setDateModifyJwtPwd(null);
			responsableServiceStageRepository.save(resp);
			
			sendMailInitPwsPFePlatformForRespServiceStg(idResServiceStg, idRSS);
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		else
		{
			System.out.println("###########################   ERROR");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public void sendMailChangeContactForRespServiceStg(String idRespServiceStage, String idRSS)
	{
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Modification Contacts Responsable Services Stages";
		
		String mailRespServiceStg = responsableServiceStageRepository.findRespServStgMailById(idRespServiceStage);
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
//		String fullNameRSS = responsableServiceStageRepository.findRespServStgFullNameById(idRSS);
		
//		String content = "Nous tenons à vous informer que vos coordonnées ont été mis à jour le <font color=grey> " + dateInitPwdPFE + " </font>."
//				+ "par mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a mis à jour </font></strong> "
//					   + "vos <strong><font color=grey>coordonnées de contact</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.";
		
		String content = "Nous tenons à vous informer que vos coordonnées <strong><font color=grey>ont été mises à jour</font></strong> le <font color=grey> " + dateInitPwdPFE + " </font> .";
//			           + "par Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong> .";
        
		utilServices.sendMailWithCC(subject, mailRespServiceStg, mailRSS, content);
	}
	
	public void sendMailInitPwsPFePlatformForRespServiceStg(String idRespServiceStg, String idRSS)
	{

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Initialisation Mot de Passe Plateforme PFE pour Enseignant";
		
		String mailRespServiceStg = responsableServiceStageRepository.findRespServStgMailById(idRespServiceStg);
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
//		String fullNameRSS = responsableServiceStageRepository.findRespServStgFullNameById(idRSS);
		
//		String content = "Nous voulons vous informer par le présent mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a initialisé</font></strong> "
//					   + "vote <strong><font color=grey>Mot de Passe</font></strong> "
//					   + "propre à <strong><font color=grey>la Plateforme PFE</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.<br/>"
//				       + "Vous pouvez accéder à votre Espace Étudiant en utilisant: <br/>"
//				       + "<strong><font color=blue>- Adresse E-Mail: </font></strong> " + mailRespServiceStg + " <br/>"
//				       + "<strong><font color=blue>- Mot de Passe: </font></strong> votre Mot de Passe Esprit Online (Celle de l'Espace Intranet)";
        
		String content = "Nous tenons à vous informer que votre Mot de Passe propre à <strong><font color=grey>la Plateforme PFE</font></strong> "
				       + "<strong><font color=grey>a été initialisé</font></strong> le <font color=grey> " + dateInitPwdPFE + " </font> .";
//		               + "par Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>";
		
		utilServices.sendMailWithCC(subject, mailRespServiceStg, mailRSS, content);
	}


	/************************************************ Chef Département & CPS ************************************************/
	@GetMapping("/chefCoordConfigs")
	public ResponseEntity<List<?>> getAllChefCoords()
	{
		try 
		{
			List<ChefCoordConfigDto> chefCoords = pedagogicalCoordinatorRepository.findChefCoordConfigDto();
			System.out.println("-----------------------------> LOL : " + chefCoords.size());

			if (chefCoords.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else
			{
				return new ResponseEntity<>(chefCoords, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/chefCoordConfigs/updateChefCoordContact/{mailChefCoord}/{idRSS}")
	public ResponseEntity<?> updateChefCoordConfig(@PathVariable("mailChefCoord") String mailChefCoord, @PathVariable("idRSS") String idRSS, @RequestBody ChefCoordConfigRequest respServStgReq)	
	{	
		System.out.println("###   Id: " + mailChefCoord + " - " + idRSS);
		
	    Optional<PedagogicalCoordinator> chefCoordObj = pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByLogin(mailChefCoord);
	    
		if(chefCoordObj.isPresent())
		{
			System.out.println("###########################   CJ");
			PedagogicalCoordinator resp = chefCoordObj.get();
			resp.setPhoneNumber(respServStgReq.getTelChefCoord().trim());
			pedagogicalCoordinatorRepository.save(resp);
			
			System.out.println("###########################   NOT CJ + NOT CS");
			
			sendMailChangeContactForChefCoord(mailChefCoord, idRSS);
			ChefCoordConfigDto chefCoordMailTelDto = new ChefCoordConfigDto(resp.getLogin(), resp.getPhoneNumber(), resp.getFullName(), resp.getPrivilege());
			return new ResponseEntity<>(chefCoordMailTelDto, HttpStatus.OK);
		}
		else
		{
			System.out.println("###########################   NOT CJ + NOT CS");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/chefCoordConfigs/initializePwdPFE/{mailChefCoord}/{idRSS}")
	public ResponseEntity<?> initializePwdPFE(@PathVariable("mailChefCoord") String mailChefCoord, @PathVariable("idRSS") String idRSS) {
		   
	    Optional<PedagogicalCoordinator> chefCoordObj = pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByLogin(mailChefCoord);

	    if(chefCoordObj.isPresent())
		{
	    	PedagogicalCoordinator resp = chefCoordObj.get();
	    	resp.setPwdJWTPC(null);
	    	resp.setDateModifyJwtPwd(null);
	    	pedagogicalCoordinatorRepository.save(resp);
			
	    	sendMailInitPwsPFePlatformForChefCoord(mailChefCoord, idRSS);
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		else
		{
			System.out.println("###########################   ERROR");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public void sendMailChangeContactForChefCoord(String mailChefCoord, String idRSS)
	{
		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Modification Contacts Responsable Services Stages";
		
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
//		String fullNameRSS = responsableServiceStageRepository.findRespServStgFullNameById(idRSS);
		
//		String content = "Nous tenons à vous informer que vos coordonnées ont été mis à jour le <font color=grey> " + dateInitPwdPFE + " </font>."
//				+ "par mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a mis à jour </font></strong> "
//					   + "vos <strong><font color=grey>coordonnées de contact</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.";
		
		String content = "Nous tenons à vous informer que vos coordonnées <strong><font color=grey> ont été mis à jour </font></strong> le <font color=grey> " + dateInitPwdPFE + " </font> .";
//			           + "par Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong> .";
        
		System.out.println("--------------------------------------> Chang : " + mailChefCoord + " - " + mailRSS);
		
		utilServices.sendMailWithCC(subject, mailChefCoord, mailRSS, content);
	}
	
	public void sendMailInitPwsPFePlatformForChefCoord(String mailChefCoord, String idRSS)
	{

		DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		String dateInitPwdPFE = dateFormata.format(new Date());
		
		String subject = "Initialisation Mot de Passe Plateforme PFE pour Enseignant";
		
		String mailRSS = responsableServiceStageRepository.findRespServStgMailById(idRSS);
//		String fullNameRSS = responsableServiceStageRepository.findRespServStgFullNameById(idRSS);
		
//		String content = "Nous voulons vous informer par le présent mail que Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>le Responsable Services Stages "
//					   + "<strong><font color=green>a initialisé</font></strong> "
//					   + "vote <strong><font color=grey>Mot de Passe</font></strong> "
//					   + "propre à <strong><font color=grey>la Plateforme PFE</font></strong>, "
//					   + "et c'est fait le <font color=grey> " + dateInitPwdPFE + " </font>.<br/>"
//				       + "Vous pouvez accéder à votre Espace Étudiant en utilisant: <br/>"
//				       + "<strong><font color=blue>- Adresse E-Mail: </font></strong> " + mailChefCoord + " <br/>"
//				       + "<strong><font color=blue>- Mot de Passe: </font></strong> votre Mot de Passe Esprit Online (Celle de l'Espace Intranet)";
        
		String content = "Nous tenons à vous informer que votre Mot de Passe propre à <strong><font color=grey>la Plateforme PFE</font></strong> "
				       + "<strong><font color=grey> ont été initialisée </font></strong> le <font color=grey> " + dateInitPwdPFE + " </font> .";
//		               + "par Mme/M <strong><font color=grey> " + fullNameRSS + " </font></strong>";
		
		System.out.println("--------------------------------------> Init : " + mailChefCoord + " - " + mailRSS);
		utilServices.sendMailWithCC(subject, mailChefCoord, mailRSS, content);
	}

	
	@GetMapping("/sessions")
	public ResponseEntity<List<?>> getAllSessions() {
		try {
			List<Session> sessions = sessionRepository.findAll();

			List<Sessiondto> NewSessiondto = new ArrayList<Sessiondto>();
			if (sessions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			for (Session S : sessions) {
				Sessiondto newS = new Sessiondto(S.getIdSession(), S.getLibelleSession(), S.getDateDebut(),
						S.getDateFin(), S.getFichePfes().size());
				NewSessiondto.add(newS);
			}

			return new ResponseEntity<>(NewSessiondto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sessionsLabel")
	public List<String> getAllSessionsLabel() 
	{
		System.out.println("------------------> SARS : " + sessionRepository.findLibelleSessions());
		return sessionRepository.findLibelleSessions();
	}

	@PostMapping("/sessions")
	public ResponseEntity<Sessiondto> createSession(@RequestBody Session s) {
		try {

			Session _s = sessionRepository.save(s);
			Sessiondto newS = new Sessiondto(_s.getIdSession(), _s.getLibelleSession(), _s.getDateDebut(),
					_s.getDateFin(), 0);

			return new ResponseEntity<>(newS, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/sessions/{id}")
	public ResponseEntity<?> updateSession(@PathVariable("id") Integer id, @RequestBody Session s) {
		Optional<Session> SessionData = sessionRepository.findById(id);

		if (SessionData.isPresent()) {
			Session _s = SessionData.get();
			_s.setDateDebut(s.getDateDebut());
			_s.setDateFin(s.getDateFin());
			_s.setLibelleSession(s.getLibelleSession());
			sessionRepository.save(_s);
			Sessiondto newS = new Sessiondto(_s.getIdSession(), _s.getLibelleSession(), _s.getDateDebut(),
					_s.getDateFin(), _s.getFichePfes().size());
			return new ResponseEntity<>(newS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/sessions/{id}")
	public ResponseEntity<HttpStatus> deleteSession(@PathVariable("id") Integer id) {
		try {
			sessionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/secteurs")
	public ResponseEntity<List<SectorEntrepriseDto>> getAllSecteurEntreprises() {
		try {
			List<SecteurEntreprise> SecteurEntreprises = sectorEntrepriseRepository.findAll();
			List<SectorEntrepriseDto> NewSecteurEntreprises = new ArrayList<SectorEntrepriseDto>();
			if (SecteurEntreprises.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			for (SecteurEntreprise S : SecteurEntreprises) {
				SectorEntrepriseDto newS = new SectorEntrepriseDto(S.getIdSecteur(), S.getLibelleSecteur(),
						S.getEntreprisesAcceuil().size());
				NewSecteurEntreprises.add(newS);
			}
			return new ResponseEntity<>(NewSecteurEntreprises, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/secteurs")
	public ResponseEntity<?> createSecteurEntreprise(@RequestBody SecteurEntreprise s) {
		try {
			SecteurEntreprise _s = sectorEntrepriseRepository.save(s);
			SectorEntrepriseDto newS = new SectorEntrepriseDto(_s.getIdSecteur(), _s.getLibelleSecteur(), 0);

			return new ResponseEntity<>(newS, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/secteurs/{id}")
	public ResponseEntity<?> updatesectorEntreprise(@PathVariable("id") Integer id, @RequestBody SecteurEntreprise s) {
		Optional<SecteurEntreprise> SecteurEntrepriseData = sectorEntrepriseRepository.findById(id);

		if (SecteurEntrepriseData.isPresent()) {
			SecteurEntreprise _s = SecteurEntrepriseData.get();
			_s.setLibelleSecteur(s.getLibelleSecteur());
			SecteurEntreprise _s2 = sectorEntrepriseRepository.save(_s);
			SectorEntrepriseDto newS = new SectorEntrepriseDto(_s2.getIdSecteur(), _s2.getLibelleSecteur(),
					_s2.getEntreprisesAcceuil().size());
			return new ResponseEntity<>(newS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/secteurs/{id}")
	public ResponseEntity<HttpStatus> deleteSecteurEntrepris(@PathVariable("id") Integer id) {
		try {
			sectorEntrepriseRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/responsableStat")
	public ResponseEntity<?> getResponsableStat(@RequestParam("code") String code) {

		try {
			List<Map<String, String>> responsableStatF = new ArrayList<Map<String, String>>();

			ObjectMapper mapper = new ObjectMapper();
			List<String> strings = mapper.readValue(decode(code), List.class);

			for (String C : strings) {

				System.out.println("// ----------------> HomeManager 1: " + C);

				String Option = C.replaceFirst("_[0-9][0-9]", "");
				List<Map<String, String>> responsableStat = responsableStageService.getSomeStat(Option, strings);
				responsableStatF.addAll(responsableStat);

			}
			System.out.println("// ----------------> HomeManager 11: " + responsableStatF.size());

			// Integer nombreAllFiche = 0;
			// Integer nombreEtudiantSansFiche = 0;
			// for (int i = 0 ; i < responsableStatF.size() ; i++) {
			// Map<String, String> myMap = responsableStatF.get(i);
			// System.out.println("Data For Map" + i);
			//
			// for (Entry<String, String> entrySet : myMap.entrySet())
			// {
			// System.out.println("--------------> 0: Key = " + entrySet.getKey() + " ,
			// Value = " + entrySet.getValue());
			//
			// if(entrySet.getKey().equalsIgnoreCase("nombreAllFiche"))
			// {
			// System.out.println("---> 1: Key = " + entrySet.getKey() + " , Value = " +
			// entrySet.getValue());
			// nombreAllFiche = nombreAllFiche + Integer.parseInt(entrySet.getValue());
			// }
			//
			// if(entrySet.getKey().equalsIgnoreCase("nombreEtudiantSansFiche"))
			// {
			// System.out.println("---> 2: Key = " + entrySet.getKey() + " , Value = " +
			// entrySet.getValue());
			// nombreEtudiantSansFiche = nombreEtudiantSansFiche +
			// Integer.parseInt(entrySet.getValue());
			// }
			//
			// }
			// }
			//
			// List<Map<String, String>> sumValues = new ArrayList<Map<String,String>>();
			// Map<String, String> finalCombined = new HashMap<>();
			// finalCombined.put("nombreAllFiche", String.valueOf(nombreAllFiche));
			// finalCombined.put("nombreEtudiantSansFiche",
			// String.valueOf(nombreEtudiantSansFiche));
			//
			// sumValues.add(finalCombined);

			if (responsableStatF.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("// ----------------> HomeManager 12");
			return new ResponseEntity<>(responsableStatF, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/ServiceStat")
	public ResponseEntity<?> getServiceStat(@RequestParam("id") String id) {
		try {
			List<Map<String, String>> ServiceStat = serviceStageService.getSomeStat(id);

			if (ServiceStat.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(ServiceStat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/EnseignantStat")
	public ResponseEntity<?> getEnseignantStat(@RequestParam("id") String id) {
		try {
			List<Map<String, String>> ServiceStat = encadrementPFEService.getSomeStat(id);

			if (ServiceStat.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(ServiceStat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/EnseignantStat2")
	public ResponseEntity<?> getEnseignantStat2(@RequestParam("id") String idEns) {
		System.out.println("--------------------------> EnseignantStat 1");
		try {
			System.out.println("--------------------------> EnseignantStat 2");
			List<Map<String, String>> EnsStat = encadrementPFEService.getSomeStat2(idEns);
			if (EnsStat.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(EnsStat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/rechercheFicheAvance", method = RequestMethod.POST)
	public ResponseEntity<?> rechercheFicheAvance(@RequestParam("annee") String annee,
			@RequestParam("classe") String classe, @RequestBody List<String> Techno, @RequestParam("etat") String etat,
			@RequestParam("codeFilere") String codeFilere) {
		List<StudentDetails> StudentDetailsList = new ArrayList<StudentDetails>();
		try {
			if (annee.equals("") && classe.equals("") && etat.equals("") && codeFilere.equals("")
					&& (Techno.size() == 0 || Techno == null)) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {

				List<FichePFE> FichePFEs = new ArrayList<FichePFE>();
				List<Object[]> FichePFEs2 = null;
				if (Techno.size() == 0) {
					FichePFEs = fichePFERepository.findFichePFEAvance(annee, classe, etat, codeFilere);

					if (FichePFEs.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
					for (FichePFE S : FichePFEs) {
						Optional<StudentCJ> e = studentRepository
								.findById(S.getIdFichePFE().getConventionPK().getIdEt());
						int valeur = Integer.parseInt(
								inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(e.get().getIdEt()).get(0)) + 1;
						Teacher t = enseignantRepository
								.getEncadrantByEtudiant(S.getIdFichePFE().getConventionPK().getIdEt());

						StudentDetails SNF = new StudentDetails(S.getIdFichePFE().getConventionPK().getIdEt(),
								e.get().getNomet(), e.get().getPrenomet(), e.get().getAdressemailesp(),
								e.get().getEmailet(), e.get().getAdresseet(), e.get().getTelet(),
								studentRepository.getOptionForEtudiant(e.get().getIdEt()).get(0),
								inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(e.get().getIdEt()).get(0)
										+ " - " + String.valueOf(valeur),
								t.getNomEns(), S.getIdFichePFE().getDateDepotFiche());
						StudentDetailsList.add(SNF);
					}

					return new ResponseEntity<>(StudentDetailsList, HttpStatus.OK);

				} else {

					FichePFEs2 = fichePFERepository.findFichePFEAvancewithTechno(annee, classe, Techno, etat,
							codeFilere);

					if (FichePFEs2.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
					for (int i = 0; i < FichePFEs2.size(); i++) {
						Optional<StudentCJ> e = studentRepository.findById((String) FichePFEs2.get(i)[0]);
						int valeur = Integer.parseInt(
								inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(e.get().getIdEt()).get(0)) + 1;
						Teacher t = enseignantRepository.getEncadrantByEtudiant((String) FichePFEs2.get(i)[0]);

						StudentDetails SNF = new StudentDetails((String) FichePFEs2.get(i)[0], e.get().getNomet(),
								e.get().getPrenomet(), e.get().getAdressemailesp(), e.get().getEmailet(),
								e.get().getAdresseet(), e.get().getTelet(),
								studentRepository.getOptionForEtudiant(e.get().getIdEt()).get(0),
								inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(e.get().getIdEt()).get(0)
										+ " - " + String.valueOf(valeur),
								t.getNomEns(), (Timestamp) FichePFEs2.get(i)[1]);
						StudentDetailsList.add(SNF);
					}

					return new ResponseEntity<>(StudentDetailsList, HttpStatus.OK);
				}

			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllClasse")
	public ResponseEntity<?> getAllClasse() {
		try {
			List<String> Classes = classeRepository.findDistinctcodeCl();
			if (Classes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Classes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllTechnologie")
	public ResponseEntity<?> getAllTechnologie() {
		try {
			List<Technologie> Technologies = TechnologieRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
			if (Technologies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Technologies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllFilére")
	public ResponseEntity<?> getAllFilére() {
		try {
			List<Object[]> Filéres = classeRepository.findDistinctlibSpecialites();
			if (Filéres.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Filéres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllAnnee")
	public ResponseEntity<?> getAllAnnee() {
		try {
			List<Object[]> Annees = saisonUniversitaireRepository.findDistinctAnnee();
			if (Annees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Annees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findVisitesTypes")
	public ResponseEntity<?> getVisitesTypes() {
		try {
			List<Object[]> VisitesTypes = codeNomenclatureRepository.findVisitesTypes();
			if (VisitesTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(VisitesTypes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findRendezVousSuiviTypes")
	public ResponseEntity<?> getRendezVousSuiviTypes() {
		try {
			List<Object[]> RendezVousSuiviType = codeNomenclatureRepository.findRendezVousSuiviTypes();
			if (RendezVousSuiviType.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(RendezVousSuiviType, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findEvaluationTypes")
	public ResponseEntity<?> getfindEvaluationTypes() {
		try {
			List<Object[]> EvaluationTypes = codeNomenclatureRepository.findEvaluationTypes();
			if (EvaluationTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(EvaluationTypes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findCompetencesTypes")
	public ResponseEntity<?> findCompetencesTypes() {
		try {
			List<Object[]> CompetencesTypes = codeNomenclatureRepository.findCompetencesTypes();
			if (CompetencesTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(CompetencesTypes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @GetMapping(value="/rechercheFicheAvanceCriteria") public List<FichePFE>
	 * rechercheFicheAvanceCriteria(
	 * 
	 * @RequestParam("annee") String annee,
	 * 
	 * @RequestParam("etat") String etat ,@RequestParam("Techno") String Techno)
	 * 
	 * {
	 * 
	 * 
	 * CriteriaBuilder cb = em.getCriteriaBuilder(); CriteriaQuery<FichePFE> cq =
	 * cb.createQuery(FichePFE.class); CriteriaQuery<InscriptionCJ> cqInscription =
	 * cb.createQuery(InscriptionCJ.class); Root<FichePFE> FichePFE =
	 * cq.from(FichePFE.class); // Predicate FichePFEPredicate =
	 * cb.equal(FichePFE.get("etatFiche"), etat);
	 * 
	 * // Join<FichePFE, Technologie> join = FichePFE.join("technologies",
	 * JoinType.INNER); // Predicate FichePFEPredicate2 =
	 * cb.like(join.get("name").as(String.class), Techno); // Join<FichePFE,
	 * InscriptionCJ> joinInscription =
	 * FichePFE.join("idFichePFE.conventionPK.idEt",JoinType.INNER);
	 * Root<InscriptionCJ> InscriptionCJ = cqInscription.from(InscriptionCJ.class);
	 * Predicate joinPredicate = cb.equal(FichePFE.get("idFichePFE").get("idEt"),
	 * InscriptionCJ.get("id").get("idEt"));
	 * 
	 * cq.select(FichePFE).where( cb.and( joinPredicate ,
	 * cb.equal(joinPredicate.get("id").get("anneeDeb"), annee)) );
	 * 
	 * // Predicate finalPredicate= cb.and(FichePFEPredicate, FichePFEPredicate2);
	 * //cq.where(finalPredicate); List<FichePFE> items =
	 * em.createQuery(cq).getResultList();
	 * 
	 * return items;
	 * 
	 * }
	 */

}

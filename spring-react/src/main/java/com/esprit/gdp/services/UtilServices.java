package com.esprit.gdp.services;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.esprit.gdp.dto.*;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.esprit.gdp.models.PedagogicalCoordinator;
import com.esprit.gdp.models.ResponsableServiceStage;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.StudentCS;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.NoteRestitutionRepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.OptionStudentALTRepository;
import com.esprit.gdp.repository.PedagogicalCoordinatorRepository;
import com.esprit.gdp.repository.ResponsableServiceStageRepository;
import com.esprit.gdp.repository.SettingsRepository;
import com.esprit.gdp.repository.StudentCSRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;

@Service
public class UtilServices {

	@Autowired
	CodeNomenclatureRepository codeNomenclatureRepository;

	@Autowired
	ResponsableServiceStageRepository responsableServiceStageRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentCSRepository studentCSRepository;

	@Autowired
	FichePFERepository fichePFERepository;

	@Autowired
	InscriptionRepository inscriptionRepository;

	@Autowired
	OptionRepository optionRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	OptionStudentALTRepository optionStudentALTRepository;

	@Autowired
	ConventionRepository conventionRepository;

	@Autowired
	NoteRestitutionRepository noteRestitutionRepository;
	
	@Autowired
	PedagogicalCoordinatorRepository pedagogicalCoordinatorRepository;

	@Autowired
	SettingsRepository settingsRepository;

	@Autowired
	PasswordEncoder encoder;
	
	/*********************************************
	 * Mailing
	 **********************************************/


	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	private String mailGoogleReception() {
		String gai = settingsRepository.findGoogleAccountId();
		return gai.substring(0, gai.lastIndexOf("@esprit.tn") + 10);
	}

	private String pwdGoogleReception() {
		String gai = settingsRepository.findGoogleAccountId();
		return gai.substring(gai.indexOf("@esprit.tn") + 10, gai.length());
	}

	public void sendMail(String subject, String receiver, String content) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour Madame/Monsieur,<br/><br/>";

			String footer = "<br/><br/>Bonne r&eacute;ception.<br/><br/>--------------------------------<br/>"
					+ "Admin Services Stages<br/>" + "<img src=\"cid:image\" height=\"70\" width=\"150\">";

			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailWithCC(String subject, String receiver, String receiverCC, String content) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(receiverCC));

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour Mesdames/Messieurs,<br/><br/>";

			String footer = "<br/><br/>Bonne r&eacute;ception.<br/><br/>--------------------------------<br/>"
					+ "Admin Services Stages<br/>" + "<img src=\"cid:image\" height=\"70\" width=\"150\">";

			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailWithManyCC(String subject, String receiver, String receiversCC, String content) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mailGoogleReception()));
			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(receiver));

			InternetAddress[] parseto = InternetAddress.parse(receiver, true);
			message.addRecipients(Message.RecipientType.TO, parseto);

			InternetAddress[] parsecc = InternetAddress.parse(receiversCC, true);
			message.addRecipients(Message.RecipientType.CC, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour,<br/><br/>";

			String footer = "<br/><br/>Bonne r&eacute;ception.<br/><br/>---------------------------------<br/>"
					+ "Admin Services Stages<br/>" + "<img src=\"cid:image\" height=\"70\" width=\"150\">";

			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailWithManyTOandManyCC(String subject, String receiver, String receiversCC, String content) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			// message.setFrom(new InternetAddress(mailGoogleReception()));
			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(receiver));

			System.out.println("Receiver ------------------------------> " + receiver);
			InternetAddress[] parseto = InternetAddress.parse(receiver, true);
			message.addRecipients(Message.RecipientType.TO, parseto);

			InternetAddress[] parsecc = InternetAddress.parse(receiversCC, true);
			message.addRecipients(Message.RecipientType.CC, parsecc);

			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour Mesdames/Messieurs,<br/><br/>";

			String footer = "<br/><br/>Bonne r&eacute;ception.<br/><br/>--------------------------------<br/>"
					+ "Admin Services Stages<br/>" + "<img src=\"cid:image\" height=\"70\" width=\"150\">";

			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailWithManyTO(String subject, String receiver, String content) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			// message.setFrom(new InternetAddress(mailGoogleReception()));
			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(receiver));

			System.out.println("Receiver ------------------------------> " + receiver);
			InternetAddress[] parseto = InternetAddress.parse(receiver, true);
			message.addRecipients(Message.RecipientType.TO, parseto);
			MimeMultipart multipart = new MimeMultipart("related");

			BodyPart messageBodyPart = new MimeBodyPart();
			String header = "Bonjour Mesdames/Messieurs,<br/><br/>";

			String footer = "<br/><br/>Bonne r&eacute;ception.<br/><br/>--------------------------------<br/>"
					+ "Admin Services Stages<br/>" + "<img src=\"cid:image\" height=\"70\" width=\"150\">";

			String allContent = header + content + footer;

			messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName("ESPRIT.png");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMailWithAttachment(String receiver, String subject, String content, String fileToAttach,
			String filename) throws AddressException, MessagingException {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailGoogleReception(), pwdGoogleReception());
			}
		});

		MimeMessage message = new MimeMessage(session);
		message.setSubject(subject);
		message.setFrom(new InternetAddress(mailGoogleReception()));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

		MimeMultipart multipart = new MimeMultipart("related");

		BodyPart messageBodyPart = new MimeBodyPart();
		String header = "Bonjour Madame/Monsieur,<br/><br/>";

		String footer = "<br/><br/>Bonne r&eacute;ception.<br/><br/>--------------------------------<br/>"
				+ "Admin Services Stages<br/>" + "<img src=\"cid:image\" height=\"70\" width=\"150\">";

		String allContent = header + content + footer;

		messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
		multipart.addBodyPart(messageBodyPart);

		MimeBodyPart messageBodyPartReport = new MimeBodyPart();
		DataSource fds1 = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
		messageBodyPartReport.setDataHandler(new DataHandler(fds1));
		messageBodyPartReport.setHeader("Content-ID", "<image>");
		messageBodyPartReport.setFileName("esprit.png");
		multipart.addBodyPart(messageBodyPartReport);

		// Report
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		DataSource source = new FileDataSource(fileToAttach);
		messageBodyPart2.setDataHandler(new DataHandler(source));
		messageBodyPart2.setFileName(filename);
		multipart.addBodyPart(messageBodyPart2);

		message.setContent(multipart);

		Transport.send(message);

	}

	/*********************************************
	 * Student
	 **********************************************/

	public String findStudentFullNameById(@PathVariable String idEt) {

		System.out.println("----------------------------------> StudentCJ ID CJ-ALT: " + idEt);

		String studentFullName = null;
		List<String> activatedYears = settingsRepository.findActivatedYears();
		List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(idEt, activatedYears); // MIGRATE
		List<String> studentsCS = studentRepository.findStudentsFullNameCS(idEt, activatedYears); // MIGRATE

		System.out.println("----------------------------------> StudentCJ FullName CJ-ALT: " + studentsCJandALT.size()
				+ " --- CS: " + studentsCS.size());

		if (!studentsCJandALT.isEmpty()) {
			studentFullName = studentsCJandALT.get(0);
		}
		if (!studentsCS.isEmpty()) {
			studentFullName = studentsCS.get(0);
		}

		return studentFullName;

	}


	public String findStudentFullNameByIdYear(String idEt, String year)
	{
		
		System.out.println("----------------------------------> StudentCJ ID CJ-ALT: " + idEt);
		
		String studentFullName = null;

		List<String> studentsCJandALT =  studentRepository.findStudentsFullNameCJandALTYear(idEt, year); // MIGRATE
		List<String> studentsCS =  studentRepository.findStudentsFullNameCSYear(idEt, year); // MIGRATE
		
		System.out.println("----------------------------------> StudentCJ FullName CJ-ALT: " + studentsCJandALT.size() + " --- CS: " + studentsCS.size());
		
		if(!studentsCJandALT.isEmpty())
		{
			studentFullName = studentsCJandALT.get(0);
		}
		if(!studentsCS.isEmpty())
		{
			studentFullName = studentsCS.get(0);
		}
		
		return studentFullName;

	}
	
	public String findBinomeFullNameByStudentId(String idEt) {
		String binomeId = null;
		String binomeFullName = null;

		List<String> binomeIds = fichePFERepository.findBinomeIdByStudentId(idEt);

		if (!binomeIds.isEmpty()) {
			binomeId = fichePFERepository.findBinomeIdByStudentId(idEt).get(0);
			List<String> activatedYears = settingsRepository.findActivatedYears();
			List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(binomeId, activatedYears);
			List<String> studentsCS = studentRepository.findStudentsFullNameCS(binomeId, activatedYears);

			// System.out.println("----------------------------------> Student FullName CJ:
			// " + studentsCJ + " --- CS: " + studentsCS);

			if (!studentsCJandALT.isEmpty()) {
				binomeFullName = studentsCJandALT.get(0);
			}
			if (!studentsCS.isEmpty()) {
				binomeFullName = studentsCS.get(0);
			}

		}

		return binomeFullName;
	}

	public BinomeIdFullNameDto findBinomeIdFullNameByStudentId(String idEt) {
		String binomeId = null;
		BinomeIdFullNameDto binomeIdFullNameDto = null;

		List<String> binomeIds = fichePFERepository.findBinomeIdByStudentId(idEt);

		if (!binomeIds.isEmpty()) {
			binomeId = fichePFERepository.findBinomeIdByStudentId(idEt).get(0);

			String binomeFullName = null;
			List<String> activatedYears = settingsRepository.findActivatedYears();
			List<String> studentsCJandALT = studentRepository.findStudentsFullNameCJandALT(binomeId, activatedYears);
			List<String> studentsCS = studentRepository.findStudentsFullNameCS(binomeId, activatedYears);

			// System.out.println("----------------------------------> Student FullName CJ:
			// " + studentsCJ + " --- CS: " + studentsCS);

			if (!studentsCJandALT.isEmpty()) {
				binomeFullName = studentsCJandALT.get(0);
			}
			if (!studentsCS.isEmpty()) {
				binomeFullName = studentsCS.get(0);
			}
			binomeIdFullNameDto = new BinomeIdFullNameDto(binomeId, binomeFullName);
		}

		return binomeIdFullNameDto;
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

	public String findStudentJWTPWDByHisId(String idEt) {

		String students = null;
		String studentsCJ = studentRepository.findStudentJWTPWDById(idEt);
		String studentsCS = studentRepository.findStudentJWTPWDCSById(idEt);

		System.out.println("---------------------**-------------> JWT NOT SET CJ: " + studentsCJ);
		System.out.println("---------------------**-------------> JWT NOT SET CS: " + studentsCS);

		if (studentsCJ != null) {
			students = studentsCJ;
		}
		if (studentsCS != null) {
			students = studentsCS;
		}

		return students;
	}

	public String findCoursesPoleByIdEt(String idEt) {
		String coursesPole = null;
		List<String> currentClassCJ = inscriptionRepository.findCurrentClassByIdEt(idEt);
		List<String> currentClassCS = inscriptionRepository.findCurrentClassCSByIdEt(idEt);

		System.out.println("----------------------------------> Class CJ: " + currentClassCJ.size());
		System.out.println("----------------------------------> Class CS: " + currentClassCS.size());

		if (!currentClassCJ.isEmpty()) {
			coursesPole = "Cours de Jour";
		}
		if (!currentClassCS.isEmpty()) {
			coursesPole = "Cours de Soir";
		}

		return coursesPole;
	}

	public String findDepartmentByClass(String codeClOff) {
		String codeCl = codeClOff.toUpperCase();
		System.out.println("--------------------------------- CLASS: " + codeCl);
		String codeDept = "";
		if (codeCl.contains("BI") || codeCl.contains("DS") || codeCl.contains("GL") || codeCl.contains("INFINI")
				|| codeCl.contains("5TWIN") || codeCl.contains("INFOB") || codeCl.contains("SIGMA")
				|| codeCl.contains("SE") || codeCl.contains("SIM") || codeCl.contains("SAE") || codeCl.contains("NIDS")
				|| codeCl.contains("ARC") || codeCl.contains("CINFO-ARC") || codeCl.contains("SLEAM")
				|| codeCl.contains("CINFO-BI") || codeCl.contains("CINFO-GL")) {
			codeDept = "01"; // Informatique
		}

		if (codeCl.contains("IOSYS") || codeCl.contains("5WIN")) {
			codeDept = "02"; // Télécommunication
		}

		if (codeCl.contains("EM") || codeCl.contains("MÉCAT") || codeCl.contains("OGI") || codeCl.contains("CEMMEC")
				|| codeCl.contains("CEMOGI")) {
			codeDept = "04"; // Electromecanique
		}

		if (codeCl.contains("GC")) {
			codeDept = "03"; // Génie-Civil
		}

		if (codeCl.contains("ALINFO")) {
			codeDept = "12"; // IT
		}

		System.out.println("--------------------------------- codeDept: " + codeDept);

		String departmentCod = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("27", codeDept);

		System.out.println("--------------------------------- typeTrtFiche: " + departmentCod);

		return departmentCod;

	}

	public String findDepartmentByClassForConventionDiplome(String codeClOff)
	{
		String codeCl = codeClOff.toUpperCase();
		System.out.println("---------------------------->>>>>>>>>>>>>>>>>>>----- CLASS: " + codeClOff);
		String codeDept = "";
		if(
				codeCl.contains("SE") || codeCl.contains("SAE") || codeCl.contains("DS") ||
						codeCl.contains("SIM") || codeCl.contains("SLEAM") ||
						codeCl.contains("GAMIX") || codeCl.contains("NIDS") ||
						codeCl.contains("5TWIN") || codeCl.contains("INFINI") ||
						codeCl.contains("BI") || codeCl.contains("CINFO-BI") ||
						codeCl.contains("ARC") || codeCl.contains("CINFO-ARC") ||
						// OLD
						codeCl.contains("GL") || codeCl.contains("CINFO-GL") || codeCl.contains("SIGMA") || codeCl.contains("INFOB")
		)
		{
			codeDept = "01"; // Informatique
		}

		if(
				codeCl.contains("IOSYS") || codeCl.contains("5WIN")
		)
		{
			codeDept = "02"; // Télécommunication
		}

		if(
				codeCl.contains("EM") || codeCl.contains("MÉCAT") ||
						codeCl.contains("OGI") || codeCl.contains("CEMMEC") || codeCl.contains("CEMOGI")
		)
		{
			codeDept = "04"; // Electromécanique
		}

		if(
				codeCl.contains("GC")
		)
		{
			codeDept = "03"; // Génie-Civil
		}

		if(
				codeCl.contains("ALINFO")
		)
		{
			codeDept = "12"; // IT
		}

		System.out.println("-------------------11-------------- codeDept: " + codeDept);

		String departmentLabel = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("27", codeDept);

		System.out.println("--------------------------------- typeTrtFiche: " + departmentLabel);

		return departmentLabel;

	}

	public String findDepartmentByClassForConv(String codeClOff)
	{
		String codeCl = codeClOff.toUpperCase();
		System.out.println("---------------------------->>>>>>>>>>>>>>>>>>>----- CLASS: " + codeClOff);
		String codeDept = "";
		if(
				codeCl.contains("5TWIN") || codeCl.contains("BI") || 
				codeCl.contains("SE") || codeCl.contains("SAE") || 
				codeCl.contains("DS") || codeCl.contains("INFINI") || 
				codeCl.contains("GL") || codeCl.contains("INFOB") || 
				codeCl.contains("SIGMA") || codeCl.contains("CINFO-BI") || 
				codeCl.contains("CINFO-GL")
		  )
		{
			codeDept = "01"; // Informatique
		}
		
		if(
				codeCl.contains("IOSYS") || codeCl.contains("5WIN") || 
				codeCl.contains("NIDS") || codeCl.contains("SLEAM") || codeCl.contains("CINFO-ARC") || 
				codeCl.contains("SIM") || codeCl.contains("ARC") || codeCl.contains("GAMIX")
		  )
		{
			codeDept = "02"; // Télécommunication
		}
		
		if(
				codeCl.contains("EM") || codeCl.contains("MÉCAT") || 
				codeCl.contains("OGI") || 
				codeCl.contains("CEMMEC") || codeCl.contains("CEMOGI")
		  )
		{
			codeDept = "04"; // Electromecanique
		}
		
		if(
				codeCl.contains("GC")
		  )
		{
			codeDept = "03"; // Génie-Civil
		}
		
		if(
				codeCl.contains("ALINFO")
		  )
		{
			codeDept = "12"; // IT
		}
		
		System.out.println("-------------------11-------------- codeDept: " + codeDept);
		
		String departmentLabel = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("27", codeDept);
		
		System.out.println("--------------------------------- typeTrtFiche: " + departmentLabel);

		return departmentLabel;
		
	}

	public String findDepartmentByStudent(String idEt) {
		String codeCl = findCurrentClassByIdEt(idEt);

		System.out.println("--------------------------------- CLASS: " + codeCl);
		String codeDept = "";
		if (codeCl.contains("BI") || codeCl.contains("DS") || codeCl.contains("NIDS") || codeCl.contains("GL")
				|| codeCl.contains("INFINI") || codeCl.contains("SIGMA") || codeCl.contains("SIM")
				|| codeCl.contains("5TWIN") || codeCl.contains("INFOB") || codeCl.contains("SE")
				|| codeCl.contains("ARC") || codeCl.contains("CINFO-ARC") || codeCl.contains("SLEAM")
				|| codeCl.contains("SAE")) {
			codeDept = "01"; // Informatique
		}

		if (codeCl.contains("IoSys") || codeCl.contains("5WIN")) {
			codeDept = "02"; // Télécommunication
		}

		if (codeCl.contains("EM") || codeCl.contains("MÉCAT") || codeCl.contains("OGI")) {
			codeDept = "04"; // Electromecanique
		}

		if (codeCl.contains("GC")) {
			codeDept = "03"; // Génie-Civil
		}

		System.out.println("--------------------------------- codeDept: " + codeDept);

		String typeTrtFiche = codeNomenclatureRepository.findLibNomenclatureByCodeStrAndCodeNome("27", codeDept);

		System.out.println("--------------------------------- typeTrtFiche: " + typeTrtFiche);

		return typeTrtFiche;

	}

	public String findChefDepartmentMailByClasse(String codeCl)
	{
		String mailChefDept = "saria.essid@esprit.tn";
		
//		System.out.println("--------------------------------- CLASS: " + codeCl);
		String codeDept = "";
		if(
				codeCl.contains("BI") || codeCl.contains("DS") || codeCl.contains("NIDS") ||
				codeCl.contains("GL") || codeCl.contains("INFINI") || 
				codeCl.contains("SIM") || codeCl.contains("5TWIN") || codeCl.contains("INFOB") || 
				codeCl.contains("ARC") || codeCl.contains("CINFO-ARC") ||
				codeCl.contains("SLEAM") ||
				codeCl.contains("SIGMA") || codeCl.contains("SE") || codeCl.contains("SAE")
		  )
		{
			mailChefDept = "CD_INFO@esprit.tn"; // Informatique
		}
		
		if(
				codeCl.contains("IoSys") || codeCl.contains("5WIN")
		  )
		{
			mailChefDept = "CD_TEL@esprit.tn"; // Télécommunication
		}
		
		if(
				codeCl.contains("EM") || codeCl.contains("MÉCAT") || 
				codeCl.contains("OGI")
		  )
		{
			mailChefDept = "CD_EM@esprit.tn"; // Electromecanique
		}
		
		if(
				codeCl.contains("GC")
		  )
		{
			mailChefDept = "CD_GC@esprit.tn"; // Génie-Civil
		}
		if(
				codeCl.contains("ALINFO")
		  )
		{
			mailChefDept = "CD-Alt@esprit.tn"; // Génie-Civil
		}
		
		return mailChefDept;
		
	}
	
	public String findAbbrevDeptByClass(String codeClLower) {
		String codeCl = codeClLower.toUpperCase();
		System.out.println("--------------------------------- CLASS: " + codeCl);
		String abbrevDept = "";
		if (codeCl.contains("BI") || codeCl.contains("DS") || codeCl.contains("GL") || codeCl.contains("INFINI")
				|| codeCl.contains("5TWIN") || codeCl.contains("INFOB") || codeCl.contains("SIGMA")
				|| codeCl.contains("SE") || codeCl.contains("SIM") || codeCl.contains("SAE") || codeCl.contains("NIDS")
				|| codeCl.contains("ARC") || codeCl.contains("CINFO-ARC") || codeCl.contains("SLEAM")
				|| codeCl.contains("CINFO-BI") || codeCl.contains("CINFO-GL")) {
			abbrevDept = "INFO"; // Informatique
		}

		if (codeCl.contains("IOSYS") || codeCl.contains("5WIN")) {
			abbrevDept = "TÉL"; // Télécommunication
		}

		if (codeCl.contains("EM") || codeCl.contains("MÉCAT") || codeCl.contains("OGI")) {
			abbrevDept = "EM"; // Electromecanique
		}

		if (codeCl.contains("GC")) {
			abbrevDept = "GC"; // Génie-Civil
		}

		return abbrevDept;

	}

	// public String findFullLabelDeptByClass(String codeClLower)
	// {
	// String codeCl = codeClLower.toUpperCase();
	// System.out.println("--------------------------------- CLASS: " + codeCl);
	// String abbrevDept = "";
	// if(
	// codeCl.contains("BI") || codeCl.contains("DS") ||
	// codeCl.contains("GL") || codeCl.contains("INFINI") ||
	// codeCl.contains("5TWIN") || codeCl.contains("INFOB") ||
	// codeCl.contains("SIGMA") || codeCl.contains("SE") ||
	// codeCl.contains("SAE") ||
	// codeCl.contains("CINFO-BI") || codeCl.contains("CINFO-GL")
	// )
	// {
	// abbrevDept = "INFOMATIQUE"; // Informatique
	// }
	//
	// if(
	// codeCl.contains("IoSyS") || codeCl.contains("IRT")||
	// codeCl.contains("ARC") || codeCl.contains("NIDS") ||
	// codeCl.contains("SIM") || codeCl.contains("SLEAM") ||
	// codeCl.contains("ISEM") || codeCl.contains("5WIN") ||
	// codeCl.contains("CINFO-ARC")
	// )
	// {
	// abbrevDept = "TÉLÉCOMMUNICATION"; // Télécommunication
	// }
	//
	// if(
	// codeCl.contains("EM") || codeCl.contains("MÉCAT") ||
	// codeCl.contains("OGI") ||
	// codeCl.contains("CEMMEC") || codeCl.contains("CEMOGI")
	// )
	// {
	// abbrevDept = "ÉLECTROMÉCANIQUE"; // Electromecanique
	// }
	//
	// if(
	// codeCl.contains("GC")
	// )
	// {
	// abbrevDept = "GÉNIE CIVIL"; // Génie-Civil
	// }
	//
	// return abbrevDept;
	//
	// }

	public String findOptionByStudentALT(String idEt) {
		return optionStudentALTRepository.findOptionByStudentALT(idEt);
	}

	public String findOptionByClass(String classe, List<String> los) {
		// System.out.println(los);
		String formattedCl = classe.replaceAll("[0123456789]", "");
		// System.out.println("----------------------------------------> COMPARE-1: " +
		// classe + " - " + formattedCl);
		String option = "--";
		String lowerOption = null;

		for (String o : los) {
			if (o.contains("_01")) {
				lowerOption = o.substring(0, o.lastIndexOf("_01"));
			}

			// System.out.println("----------------------------------------> COMPARE-2: " +
			// formattedCl.toLowerCase() + " - " + lowerOption.toLowerCase());

			// System.out.println("----------------------------------------> COMPARE-3: " +
			// formattedCl.length() + " - " + lowerOption.length());

			List<String> opts = new ArrayList<String>();
			opts.add("cinfo-bi");
			opts.add("cinfo-arc");
			opts.add("cinfo-gl");
			opts.add("cemmec");
			opts.add("cemogi");
			if (!opts.contains(formattedCl.toLowerCase())
					&& formattedCl.toLowerCase().equalsIgnoreCase(lowerOption.toLowerCase())) {
				option = o;
				break;
			}

			if (formattedCl.toLowerCase().equalsIgnoreCase("cinfo-bi")) {
				option = "ERP-BI_01";
			}
			if (formattedCl.toLowerCase().equalsIgnoreCase("cinfo-arc")) {
				option = "ARCTIC_01";
			}
			if (formattedCl.toLowerCase().equalsIgnoreCase("cinfo-gl")) {
				// System.out.println("######################### HERE-1: " +
				// formattedCl.toLowerCase());
				option = "SAE_01";
				// System.out.println("######################### HERE-2: " + option);
			}
			if (formattedCl.toLowerCase().equalsIgnoreCase("cemmec")) {
				option = "MÉCAT_01";
			}
			if (formattedCl.toLowerCase().equalsIgnoreCase("cemogi")) {
				option = "OGI_01";
			}

		}

		// System.out.println("-------------------> option: " + option);

		return option;
	}

	public String findOptionByStudent(String idET, List<String> los) {
		String classe = findCurrentClassByIdEt(idET);
		String formattedCl = classe.replaceAll("[0123456789]", "");
		System.out.println("----------------------------------------> COMPARE: " + classe + " - " + formattedCl);
		String option = "--";
		String lowerOption = null;

		for (String o : los) {
			if (o.contains("_01")) {
				lowerOption = o.substring(0, o.lastIndexOf("_01"));
			}
			if (formattedCl.toLowerCase().contains(lowerOption.toLowerCase())) {
				option = o;
				break;
			}
		}
		return option;
	}

	public String findCurrentClassByIdEt(String idET) {
		String currentClass = null;
		List<String> currentClassCJ = inscriptionRepository.findCurrentClassByIdEt(idET);
		List<String> currentClassCS = inscriptionRepository.findCurrentClassCSByIdEt(idET);

		System.out.println("-----------------------------------------> STU: " + idET);
		System.out.println("----------> Class CJ: " + currentClassCJ.size() + " - " + currentClassCJ);
		System.out.println("----------> Class CS: " + currentClassCS.size() + " - " + currentClassCS);

		if (!currentClassCJ.isEmpty()) {
			currentClass = currentClassCJ.get(0);
		}
		if (!currentClassCS.isEmpty()) {
			currentClass = currentClassCS.get(0);
		}

		System.out.println("----------> Class : " + currentClass);

		return currentClass;
	}

	public List<String> findStudentsByOption(String option) {

		List<String> students = new ArrayList<String>();
		List<String> studentsCJ = optionRepository.findStudentsByOption(option);
		List<String> studentsCJALT = optionStudentALTRepository.findIdStudentsALTByOption(option);
		List<String> studentsCS = optionRepository.findStudentsCSByOption(option);

		System.out.println("-----------------------------------------------------------------> option: " + option);
		System.out.println("----------------------------------> StudentCJ CJ: " + studentsCJ.size());
		System.out.println("----------------------------------> StudentCJ ALT: " + studentsCJALT.size());
		System.out.println("----------------------------------> StudentCJ CS: " + studentsCS.size());

		if (!studentsCJ.isEmpty()) {
			students.addAll(studentsCJ);
		}
		if (!studentsCJALT.isEmpty()) {
			students.addAll(studentsCJALT);
		}
		if (!studentsCS.isEmpty()) {
			students.addAll(studentsCS);
		}

		return students;
	}

	public List<String> findStudentsByYearAndGroupedOption(String year, String option)
	{

		System.out.println("---------------------------------------------------> option: " + option);
		List<String> students = new ArrayList<String>();
		if(option.equalsIgnoreCase("em"))
		{
			List<String> studentsEMCJ = optionRepository.findStudentsByYearAndOptionForEMCJ(year);
			List<String> studentsEMCS = optionRepository.findStudentsByYearAndOptionForEMCS(year);

			System.out.println("----------------------------------> Student EM CJ: " + studentsEMCJ.size());
			System.out.println("----------------------------------> Student EM CS: " + studentsEMCS.size());

			if(!studentsEMCJ.isEmpty())
			{
				students.addAll(studentsEMCJ);
			}
			if(!studentsEMCS.isEmpty())
			{
				students.addAll(studentsEMCS);
			}
		}
		if(option.equalsIgnoreCase("gc"))
		{
			List<String> studentsGCCJ = optionRepository.findStudentsByYearAndOptionForGCCJ(year);

			System.out.println("----------------------------------> Student GC CJ: " + studentsGCCJ.size());

			if(!studentsGCCJ.isEmpty())
			{
				students.addAll(studentsGCCJ);
			}
		}
		if(option.equalsIgnoreCase("arctic"))
		{
			List<String> studentsCJ = optionRepository.findStudentsByYearAndOption(year, option);
			List<String> studentsCJALT = optionStudentALTRepository.findIdStudentsALTByYearAndOption(year, option);
			List<String> studentsARCCS = optionRepository.findStudentsARCCSByYearAndOption(year);

			System.out.println("----------------------------------> Student ARC CJ: " + studentsCJ.size());
			System.out.println("----------------------------------> Student ARC ALT: " + studentsCJALT.size());
			System.out.println("----------------------------------> Student ARC CS: " + studentsARCCS.size());

			if(!studentsCJ.isEmpty())
			{
				students.addAll(studentsCJ);
			}
			if(!studentsCJALT.isEmpty())
			{
				students.addAll(studentsCJALT);
			}
			if(!studentsARCCS.isEmpty())
			{
				students.addAll(studentsARCCS);
			}
		}
		if(option.equalsIgnoreCase("erp-bi"))
		{
			List<String> studentsCJ = optionRepository.findStudentsByYearAndOption(year, option);
			List<String> studentsCJALT = optionStudentALTRepository.findIdStudentsALTByYearAndOption(year, option);
			List<String> studentsBICS = optionRepository.findStudentsBICSByYearAndOption(year);

			System.out.println("----------------------------------> Student BI CJ: " + studentsCJ.size());
			System.out.println("----------------------------------> Student BI ALT: " + studentsCJALT.size());
			System.out.println("----------------------------------> Student BI CS: " + studentsBICS.size());

			if(!studentsCJ.isEmpty())
			{
				students.addAll(studentsCJ);
			}
			if(!studentsCJALT.isEmpty())
			{
				students.addAll(studentsCJALT);
			}
			if(!studentsBICS.isEmpty())
			{
				students.addAll(studentsBICS);
			}
		}
		if(option.equalsIgnoreCase("sae"))
		{
			List<String> studentsCJ = optionRepository.findStudentsByYearAndOption(year, option);
			List<String> studentsCJALT = optionStudentALTRepository.findIdStudentsSAEALTByYearAndOption(year);
			List<String> studentSAECS = optionRepository.findStudentsSAECSByYearAndOption(year);

			System.out.println("--------jj--------------------------> Student SAE CJ: " + studentsCJ.size());
			System.out.println("----------------------------------> Student SAE ALT: " + studentsCJALT.size());
			System.out.println("----------------------------------> Student SAE CS: " + studentSAECS.size());

			if(!studentsCJ.isEmpty())
			{
				students.addAll(studentsCJ);
			}
			if(!studentsCJALT.isEmpty())
			{
				students.addAll(studentsCJALT);
			}
			if(!studentSAECS.isEmpty())
			{
				students.addAll(studentSAECS);
			}
		}
		else
		{
			List<String> studentsCJ = optionRepository.findStudentsCJByYearAndOption(year, option);
			List<String> studentsCJALT = optionStudentALTRepository.findIdStudentsALTByYearAndOption(year, option);
			List<String> studentsCS = optionRepository.findStudentsCSByYearAndOption(year, option);

			System.out.println("----------------------------------> Student CJ: " + studentsCJ.size());
			System.out.println("----------------------------------> Student ALT: " + studentsCJALT.size());
			System.out.println("----------------------------------> Student CS: " + studentsCS.size());

			if(!studentsCJ.isEmpty())
			{
				students.addAll(studentsCJ);
			}
			if(!studentsCJALT.isEmpty())
			{
				students.addAll(studentsCJALT);
			}
			if(!studentsCS.isEmpty())
			{
				students.addAll(studentsCS);
			}
		}

		return students;
	}

	public List<ConventionForRSSDto> findDemandesAnnulationConventionsByYear(String year)
	{

		List<ConventionForRSSDto> demandesAnnulationConventions = new ArrayList<ConventionForRSSDto>();
		System.out.println("---------------------------------------------------> year: " + year);

		List<ConventionForRSSDto> demandesAnnulationConventionsCJ = optionRepository.findStudentsCJWithDemandesAnnulationsConventionsByYear(year);
		List<ConventionForRSSDto> demandesAnnulationConventionsALT = optionRepository.findStudentsALTWithDemandesAnnulationsConventionsByYear(year);
		List<ConventionForRSSDto> demandesAnnulationConventionsCS = optionRepository.findStudentsCSWithDemandesAnnulationsConventionsByYear(year);

		System.out.println("----------------------------------> Student CJ: " + demandesAnnulationConventionsCJ.size());
		System.out.println("----------------------------------> Student ALT: " + demandesAnnulationConventionsALT.size());
		System.out.println("----------------------------------> Student CS: " + demandesAnnulationConventionsCS.size());

		if(!demandesAnnulationConventionsCJ.isEmpty())
		{
			demandesAnnulationConventions.addAll(demandesAnnulationConventionsCJ);
		}
		if(!demandesAnnulationConventionsALT.isEmpty())
		{
			demandesAnnulationConventions.addAll(demandesAnnulationConventionsALT);
		}
		if(!demandesAnnulationConventionsCS.isEmpty())
		{
			demandesAnnulationConventions.addAll(demandesAnnulationConventionsCS);
		}

		for(ConventionForRSSDto c : demandesAnnulationConventions)
		{
			// System.out.println("--------------------------> DATE: " + c.getDateConvention());
			String idEt = c.getIdEt();
			String classe = findCurrentClassByIdEt(idEt);

			/*String convCodePays = c.getPaysConvention();
			if(convCodePays.equalsIgnoreCase("--"))
			{
				c.setPaysConvention("EN");
			}
			else
			{
				c.setPaysConvention(convCodePays);
			}*/

			c.setNomEt(findStudentFullNameById(idEt));
			//c.setDepartEt(findDepartmentAbbByClassWithStat(classe));
			// List<String> los = optionRepository.listOptionsByYear("2021");
			// c.setDepartEt(findOptionByStudent(idEt, los).replaceAll("_01", ""));

			if(!classe.contains("4ALINFO"))
			{
				c.setOptionEt(findOptionByClass(classe, optionRepository.listOptionsByYear(year)).replace("_01", ""));
			}
			if(classe.contains("4ALINFO"))
			{
				c.setOptionEt(optionStudentALTRepository.findOptionByStudentALTAndYear(idEt, year));
			}

			c.setCurrentClasse(classe);
		}

		return demandesAnnulationConventions;
	}

	public List<ConventionForRSSDto> findNotTreatedConventionsByYear(String year)
	{

		List<ConventionForRSSDto> notTreatedConventions = new ArrayList<ConventionForRSSDto>();
		System.out.println("---------------------------------------------------> year: " + year);

		List<ConventionForRSSDto> notTreatedConventionsCJ = conventionRepository.findStudentsCJWithNotTreatedConventionsByYear(year);
		List<ConventionForRSSDto> notTreatedConventionsALT = conventionRepository.findStudentsALTWithNotTreatedConventionsByYear(year);
		List<ConventionForRSSDto> notTreatedConventionsCS = conventionRepository.findStudentsCSWithNotTreatedConventionsByYear(year);

		System.out.println("----------------------------------> Student CJ: " + notTreatedConventionsCJ.size());
		System.out.println("----------------------------------> Student ALT: " + notTreatedConventionsALT.size());
		System.out.println("----------------------------------> Student CS: " + notTreatedConventionsCS.size());

		if(!notTreatedConventionsCJ.isEmpty())
		{
			notTreatedConventions.addAll(notTreatedConventionsCJ);
		}
		if(!notTreatedConventionsALT.isEmpty())
		{
			notTreatedConventions.addAll(notTreatedConventionsALT);
		}
		if(!notTreatedConventionsCS.isEmpty())
		{
			notTreatedConventions.addAll(notTreatedConventionsCS);
		}

		for(ConventionForRSSDto c : notTreatedConventions)
		{
			// System.out.println("--------------------------> DATE: " + c.getDateConvention());
			String idEt = c.getIdEt();
			String classe = findCurrentClassByIdEt(idEt);

			/*String convCodePays = c.getPaysConvention();
			if(convCodePays.equalsIgnoreCase("--"))
			{
				c.setPaysConvention("EN");
			}
			else
			{
				c.setPaysConvention(convCodePays);
			}*/

			c.setNomEt(findStudentFullNameById(idEt));
			//c.setDepartEt(findDepartmentAbbByClassWithStat(classe));
			List<String> los = optionRepository.listOptionsByYear("2021");
			// c.setDepartEt(findOptionByStudent(idEt, los).replaceAll("_01", ""));
			c.setCurrentClasse(classe);

			if(!classe.contains("4ALINFO"))
			{
				c.setOptionEt(findOptionByClass(classe, optionRepository.listOptionsByYear(year)).replace("_01", ""));
			}
			if(classe.contains("4ALINFO"))
			{
				c.setOptionEt(optionStudentALTRepository.findOptionByStudentALTAndYear(idEt, year));
			}
		}

		return notTreatedConventions;
	}

	public List<String> findStudentsByYearAndOption(String year, String option)
	{
		
		List<String> students = new ArrayList<String>();
		List<String> studentsCJ = optionRepository.findStudentsByYearAndOption(year, option);
		List<String> studentsCJALT = optionStudentALTRepository.findIdStudentsALTByYearAndOption(year, option);
		List<String> studentsCS = optionRepository.findStudentsCSByYearAndOption(year, option);
		
		System.out.println("---------------------------------------------------> option: " + option);
		System.out.println("----------------------------------> StudentCJ CJ: " + studentsCJ.size());
		System.out.println("----------------------------------> StudentCJ ALT: " + studentsCJALT.size());
		System.out.println("----------------------------------> StudentCJ CS: " + studentsCS.size());
		
		if(!studentsCJ.isEmpty())
		{
			students.addAll(studentsCJ);
		}
		if(!studentsCJALT.isEmpty())
		{
			students.addAll(studentsCJALT);
		}
		if(!studentsCS.isEmpty())
		{
			students.addAll(studentsCS);
		}
		
		return students;
	}
	
	public List<String> findStudentsALTByYearAndOption(String year, String option)
	{
		
		List<String> studentsCJALT = optionStudentALTRepository.findIdStudentsALTByYearAndOption(year, option);
		
//		System.out.println("---------------------------------------------------> option: " + option);
//		System.out.println("----------------------------------> StudentCJ ALT: " + studentsCJALT.size());
		
		return studentsCJALT;
	}
	
	public List<String> findClassesByOption(String option) {

		List<String> classes = new ArrayList<String>();
		List<String> classesCJ = optionRepository.findClassesCJByOption(option);
		List<String> classesCS = optionRepository.findClassesCSByOption(option);

		// System.out.println("----------------------------------> Classes CJ: " +
		// classesCJ.size());
		// System.out.println("----------------------------------> Classes CS: " +
		// classesCS.size());

		if (!classesCJ.isEmpty()) {
			classes.addAll(classesCJ);
		}
		if (!classesCS.isEmpty()) {
			classes.addAll(classesCS);
		}

		// Excptional Case
		if (option.equalsIgnoreCase("arctic")) {
			classes.add("4CINFO-ARC");
		}

		System.out.println("----------------------------------> All Classes: " + option);

		for (String s : classes) {
			System.out.println("-----> CLS: " + s);
		}

		return classes;
	}

	public List<String> findClassesByYearAndOption(String year, String option) {

		List<String> classes = new ArrayList<String>();
		List<String> classesCJ = optionRepository.findClassesCJByYearAndOption(year, option);
		List<String> classesCS = optionRepository.findClassesCSByYearAndOption(year, option);

		if (!classesCJ.isEmpty()) {
			classes.addAll(classesCJ);
		}
		if (!classesCS.isEmpty()) {
			classes.addAll(classesCS);
		}

		// Excptional Case
		if (option.equalsIgnoreCase("arctic")) {
			classes.add("4CINFO-ARC");
		}

		System.out.println("-------------------> All Classes: " + option);

		for (String s : classes) {
			System.out.println("-----> CLS: " + s);
		}

		return classes;
	}

	public List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByClass(String codeClass) {

		System.out.println("---------------------**-------------> codeClass: " + codeClass);

		List<StudentFullNameMailTelDto> students = null;
		List<StudentFullNameMailTelDto> studentsCJ = studentRepository.findStudentsFullNameMailTelByClassCJ(codeClass);
		List<StudentFullNameMailTelDto> studentsCS = studentRepository
				.findStudentsFullNameMailTelByClassCS(codeClass.toLowerCase());

		System.out.println("------**-------------> STU CJ: " + studentsCJ);
		System.out.println("------**-------------> STU CS: " + studentsCS);

		if (!studentsCJ.isEmpty()) {
			students = studentsCJ;
		}
		if (!studentsCS.isEmpty()) {
			students = studentsCS;
		}

		return students;
	}

	public List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByYearAndClass(String year, String codeClass) {

		System.out.println("---------------------**-------------> codeClass: " + year + " - " + codeClass);

		List<StudentFullNameMailTelDto> students = null;
		List<StudentFullNameMailTelDto> studentsCJ = studentRepository.findStudentsFullNameMailTelByYearAndClassCJ(year,
				codeClass.toLowerCase());
		List<StudentFullNameMailTelDto> studentsCS = studentRepository.findStudentsFullNameMailTelByYearAndClassCS(year,
				codeClass.toLowerCase());

		System.out.println("------**-------------> STU CJ: " + studentsCJ);
		System.out.println("------**-------------> STU CS: " + studentsCS);

		if (!studentsCJ.isEmpty()) {
			students = studentsCJ;
		}
		if (!studentsCS.isEmpty()) {
			students = studentsCS;
		}

		return students;
	}

	public List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdEt(String idEt) {
		List<StudentFullNameMailTelDto> students = null;
		List<StudentFullNameMailTelDto> studentsCJ = studentRepository.findStudentsFullNameMailTelByIdETCJALT(idEt);
		List<StudentFullNameMailTelDto> studentsCS = studentRepository.findStudentsFullNameMailTelByIdETCS(idEt);

		System.out.println("---------------------**-------------> JWT NOT SET CJ: " + studentsCJ);
		System.out.println("---------------------**-------------> JWT NOT SET CS: " + studentsCS);

		if (!studentsCJ.isEmpty()) {
			students = studentsCJ;
		}
		if (!studentsCS.isEmpty()) {
			students = studentsCS;
		}

		return students;
	}

	public List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdEtWithActivatedYears(String idEt) {
		List<String> activatedYears = settingsRepository.findActivatedYears();

		List<StudentFullNameMailTelDto> students = null;
		List<StudentFullNameMailTelDto> studentsCJ = studentRepository
				.findStudentsFullNameMailTelByIdETCJALTWithActivatedYears(activatedYears, idEt);
		List<StudentFullNameMailTelDto> studentsCS = studentRepository
				.findStudentsFullNameMailTelByIdETCSWithActivatedYears(activatedYears, idEt);

		System.out.println("---------------------**-------------> JWT NOT SET CJ: " + studentsCJ);
		System.out.println("---------------------**-------------> JWT NOT SET CS: " + studentsCS);

		if (!studentsCJ.isEmpty()) {
			students = studentsCJ;
		}
		if (!studentsCS.isEmpty()) {
			students = studentsCS;
		}

		return students;
	}

	public List<String> findStudentsByDept(String deptLabel) {

		System.out.println("--------------------------------- Dept Label: " + deptLabel);
		List<String> options = new ArrayList<String>();

		if (deptLabel.equalsIgnoreCase("IT")) {
			options.add("CINFO-BI");
			options.add("CINFO-GL");
			options.add("CINFO-ARC");
			options.add("ERP-BI");
			options.add("DS");
			options.add("INFINI");
			options.add("IoSyS");
			options.add("ARCTIC");
			options.add("NIDS");
			options.add("SIM");
			options.add("SLEAM");
			options.add("GAMIX");
			options.add("SE");
			options.add("SAE");
			options.add("TWIN");
			options.add("WIN");
			// options.add("5TWIN");options.add("5WIN");
		}

		if (deptLabel.equalsIgnoreCase("EM")) {
			options.add("EM");
			options.add("MÉCAT");
			options.add("OGI");
			options.add("CEMMEC");
			options.add("CEMOGI");
		}

		if (deptLabel.equalsIgnoreCase("GC")) {
			options.add("GC");
		}

		if (deptLabel.equalsIgnoreCase("ALT")) {
			options.add("ALINFO");
		}

		List<String> lowerOpts = options.stream().map(String::toLowerCase).collect(Collectors.toList());

		return lowerOpts;
	}

	public StudentDto findStudentDtoById(String idEt) {

		StudentDto student = null;
		StudentDto studentCJ = studentRepository.findStudentCJDtoById(idEt);
		StudentDto studentCS = studentRepository.findStudentCSDtoById(idEt);

		System.out.println("---------------------**-------------> JWT NOT SET CJ: " + studentCJ);
		System.out.println("---------------------**-------------> JWT NOT SET CS: " + studentCS);

		if (studentCJ != null) {
			student = studentCJ;
		}
		if (studentCS != null) {
			student = studentCS;
		}

		return student;
	}

	public StudentExcelDto findStudentExcelDtoById(String idStu) {
		List<String> activatedYears = settingsRepository.findActivatedYears();

		List<String> studentsCJ = studentRepository.findCJALTStudents(activatedYears);
		List<String> studentsCS = studentRepository.findCSStudents(activatedYears);

		// System.out.println("StudentCJ --------------> Size: " + idStu + " --> " +
		// studentsCJ.size() + " - " + studentsCS.size());

		StudentExcelDto studentExcelCJDto = null;

		if (studentsCJ.contains(idStu)) {
			studentExcelCJDto = studentRepository.findStudentExcelCJ(idStu);
		}

		if (studentsCS.contains(idStu)) {
			studentExcelCJDto = studentRepository.findStudentExcelCS(idStu);
		}

		return studentExcelCJDto;
	}

	public StudentConvFRDto findStudentConvFRDtoById(String idStu) {
		List<String> activatedYears = settingsRepository.findActivatedYears();

		List<String> studentsCJ = studentRepository.findCJALTStudents(activatedYears);
		List<String> studentsCS = studentRepository.findCSStudents(activatedYears);

		// System.out.println("StudentCJ --------------> Size: " + idStu + " --> " +
		// studentsCJ.size() + " - " + studentsCS.size());

		StudentConvFRDto studentConvFRDto = null;

		if (studentsCJ.contains(idStu)) {
			studentConvFRDto = studentRepository.findStudentConvFRCJ(idStu);
		}

		if (studentsCS.contains(idStu)) {
			studentConvFRDto = studentRepository.findStudentConvFRCS(idStu);
		}

		return studentConvFRDto;
	}

	public StudentMailTelDto findStudentMailTelDtoById(String idStu) {
		List<String> activatedYears = settingsRepository.findActivatedYears();

		List<String> studentsCJ = studentRepository.findCJALTStudents(activatedYears);
		List<String> studentsCS = studentRepository.findCSStudents(activatedYears);

		StudentMailTelDto studentMailTelDto = null;

		if (studentsCJ.contains(idStu)) {
			studentMailTelDto = studentRepository.findStudentMailTelCJ(idStu);
		}

		if (studentsCS.contains(idStu)) {
			studentMailTelDto = studentRepository.findStudentMailTelCS(idStu);
		}

		return studentMailTelDto;
	}

	public Integer findNbrStudentsTrainedByPEAndYear(String idPE, String year) {
		// List<String> nbrStuCJ =
		// inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCJ(idPE,
		// "1988");
		// List<String> nbrStuCJALT =
		// inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCJALT(idPE,
		// "1988");

		List<String> nbrStuCJandALT = inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCJandALT(idPE,
				year);
		List<String> nbrStuCS = inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCS(idPE, year);

		return nbrStuCJandALT.size() + nbrStuCS.size();
	}

	public Integer findNbrStudentsTrainedByPE(String idPE) {
		// List<String> nbrStuCJ =
		// inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCJ(idPE,
		// "1988");
		// List<String> nbrStuCJALT =
		// inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCJALT(idPE,
		// "1988");

		List<String> nbrStuCJandALT = inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCJandALT(idPE,
				"2021");
		List<String> nbrStuCS = inscriptionRepository.findListOfStudentsByPedagogicalEncadrantCS(idPE, "2021");

		return nbrStuCJandALT.size() + nbrStuCS.size();
	}

	public Integer findNbrStudentsTrainedByEXP(String idExpert) {
		List<String> nbrStuCJandALT = inscriptionRepository.findListOfStudentsByExpertCJandALT(idExpert, "2021");
		List<String> nbrStuCS = inscriptionRepository.findListOfStudentsByExpertCS(idExpert, "2021");

		return nbrStuCJandALT.size() + nbrStuCS.size();
	}
	

	public Integer findNbrStudentsTrainedByEXPAndYear(String idExpert, String year)
	{
		System.out.println("-------------------> AZER 1: " + idExpert + " - " + year);
		List<String> nbrStuCJandALT = inscriptionRepository.findListOfStudentsByExpertCJandALT(idExpert, year);
		System.out.println("-------------------> AZER 2: " + nbrStuCJandALT.size());
		List<String> nbrStuCS = inscriptionRepository.findListOfStudentsByExpertCS(idExpert, year);
		System.out.println("-------------------> AZER 3: " + nbrStuCS.size());
		return nbrStuCJandALT.size() + nbrStuCS.size();
	}
	

	public Integer sars(String idPE) {
		return inscriptionRepository.sars1(idPE, "2021").size();
	}

	public List<StudentAffectationDetailsDto> findStudentsDtoTrainedByPE(String idPE) {
		List<StudentAffectationDetailsDto> affStuCJ = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCJ(idPE, "2021");
		List<StudentAffectationDetailsDto> affStuCJALT = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCJALT(idPE, "2021");
		List<StudentAffectationDetailsDto> affStuCS = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCS(idPE, "2021");

		List<StudentAffectationDetailsDto> affStu = new ArrayList<StudentAffectationDetailsDto>();
		affStu.addAll(affStuCJ);
		affStu.addAll(affStuCJALT);
		affStu.addAll(affStuCS);

		affStu.sort(Comparator.comparing(StudentAffectationDetailsDto::getFullName));

		return affStu;
	}

	public List<StudentAffectationDetailsDto> findStudentsDtoTrainedByPEAndYear(String idPE, String year) {
		List<StudentAffectationDetailsDto> affStuCJ = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCJ(idPE, year);
		List<StudentAffectationDetailsDto> affStuCJALT = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCJALT(idPE, year);
		List<StudentAffectationDetailsDto> affStuCS = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCS(idPE, year);

		List<StudentAffectationDetailsDto> affStu = new ArrayList<StudentAffectationDetailsDto>();
		affStu.addAll(affStuCJ);
		affStu.addAll(affStuCJALT);
		affStu.addAll(affStuCS);

		affStu.sort(Comparator.comparing(StudentAffectationDetailsDto::getFullName));

		return affStu;
	}

	public List<StudentAffectationDetailsDto> findStudentsDtoTrainedByPEAndYearNew(String idPE, String year) {
		List<StudentAffectationDetailsDto> affStuCJ = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCJTC(idPE, year);
		List<StudentAffectationDetailsDto> affStuCJALT = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCJALT(idPE, year);
		List<StudentAffectationDetailsDto> affStuCS = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalEncadrantCS(idPE, year);

		List<StudentAffectationDetailsDto> affStu = new ArrayList<StudentAffectationDetailsDto>();
		affStu.addAll(affStuCJ);
		affStu.addAll(affStuCJALT);
		affStu.addAll(affStuCS);

		affStu.sort(Comparator.comparing(StudentAffectationDetailsDto::getFullName));

		return affStu;
	}

	/*********************************************
	 * Actors
	 **********************************************/

	public String findIdEncadrantPedagogiqueByStudent(String idStu) {
		String idEncadrantPedagogique = null;
		System.out.println("----------------------------------> idStu: " + idStu);
		List<String> idTeachersCJ = inscriptionRepository.findEncadrantPedagogiqueByStudent(idStu);
		List<String> idTeachersCS = inscriptionRepository.findEncadrantPedagogiqueCSByStudent(idStu);
		System.out.println("----------------------------------> idTeachersCJ: " + idTeachersCJ.size());
		System.out.println("----------------------------------> idTeachersCS: " + idTeachersCS.size());

		if (!idTeachersCJ.isEmpty()) {
			for (String itj : idTeachersCJ) {
				if (itj != null) {
					idEncadrantPedagogique = itj;
					System.out.println("-----------------> idAE CDJ: " + idEncadrantPedagogique);
				}
			}
		}
		if (!idTeachersCS.isEmpty()) {
			for (String its : idTeachersCS) {
				if (its != null) {
					idEncadrantPedagogique = its;
					System.out.println("-----------------> idAE CDS: " + idEncadrantPedagogique);
				}
			}
		}

		System.out.println("----------------------------------> idAE RESULT: " + idEncadrantPedagogique);

		return idEncadrantPedagogique;
	}

	public String findIdExpertByStudent(String idStu) {
		String idExpert = null;
		List<String> idTeachersCJ = inscriptionRepository.findExpertCJByStudent(idStu);
		List<String> idTeachersCS = inscriptionRepository.findExpertCSByStudent(idStu);
		System.out.println("------------------------------------> idTeachersCS: " + idTeachersCS);

		if (!idTeachersCJ.isEmpty()) {
			idExpert = idTeachersCJ.get(0);
			System.out.println("------------------------------------> idExpert CDJ: " + idExpert);
		}
		if (!idTeachersCS.isEmpty()) {
			idExpert = idTeachersCS.get(0);
			System.out.println("------------------------------------> idExpert CDS: " + idExpert);
		}

		System.out.println("------------------------------------> idExpert RESULT: " + idExpert);

		return idExpert;
	}

	public List<StudentAffectationDetailsDto> findStudentsDtoTrainedByPExp(String idPE) {
		List<StudentAffectationDetailsDto> affStuCJ = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalExpertCJ(idPE, "2021");
		List<StudentAffectationDetailsDto> affStuCJALT = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalExpertCJALT(idPE, "2021");
		List<StudentAffectationDetailsDto> affStuCS = inscriptionRepository
				.findListOfStudentsDtoByPedagogicalExpertCS(idPE, "2021");

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ CDJ: " + affStuCJ.size());
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ALT: " + affStuCJALT.size());
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ CDS: " + affStuCS.size());

		List<StudentAffectationDetailsDto> affStu = new ArrayList<StudentAffectationDetailsDto>();
		affStu.addAll(affStuCJ);
		affStu.addAll(affStuCJALT);
		affStu.addAll(affStuCS);

		affStu.sort(Comparator.comparing(StudentAffectationDetailsDto::getFullName));

		return affStu;
	}

	public String findFullNamePedagogicalEncadrant(String idStudent) {
		String idEncadrantPedagogique = null;
		String nomEncadrantPedagogique = "--";
		List<String> idEncadrantPedagogiqueCJ = inscriptionRepository.findEncadrantPedagogiqueByStudent(idStudent);
		List<String> idEncadrantPedagogiqueCS = inscriptionRepository.findEncadrantPedagogiqueCSByStudent(idStudent);
		// System.out.println("---------------------------------------------------> I");

		if (!idEncadrantPedagogiqueCJ.isEmpty()) {
			idEncadrantPedagogique = idEncadrantPedagogiqueCJ.get(0);
		}
		if (!idEncadrantPedagogiqueCS.isEmpty()) {
			idEncadrantPedagogique = idEncadrantPedagogiqueCS.get(0);
		}

		if (idEncadrantPedagogique != null) {
			nomEncadrantPedagogique = teacherRepository.findByIdEns(idEncadrantPedagogique).getNomEns();
		}

		return nomEncadrantPedagogique;
	}

	public TeacherEncadrantPedaDto findEncadrantPedagogiqueByStudentId(String idEt) {

		TeacherEncadrantPedaDto teacherEncadrantPedaDto = null;
		List<TeacherEncadrantPedaDto> teachersCJ = inscriptionRepository.findEncadrantPedagogiqueByStudentCJ(idEt);
		List<TeacherEncadrantPedaDto> teachersCS = inscriptionRepository.findEncadrantPedagogiqueByStudentCS(idEt);

		// System.out.println("----------------------------------> Teacher EncadrantPeda CJ: " + teachersCJ.size());
		// System.out.println("----------------------------------> Teacher EncadrantPeda CS: " + teachersCS.size());

		if (!teachersCJ.isEmpty()) {
			teacherEncadrantPedaDto = teachersCJ.get(0);
		}
		if (!teachersCS.isEmpty()) {
			teacherEncadrantPedaDto = teachersCS.get(0);
		}

		return teacherEncadrantPedaDto;
	}

	public StudentJustificatifStageDto findStudentJustificatifStageByStudentId(String idEt) {

		// System.out.println("----------------------------------> Teacher EncadrantPeda ID: " + idEt);

		StudentJustificatifStageDto studentDemandeStgDto = null;
		List<StudentJustificatifStageDto> teachersCJ = inscriptionRepository.findStudentJustificatifStageDtoCJ(idEt,
				"2021");
		List<StudentJustificatifStageDto> teachersCS = inscriptionRepository.findStudentJustificatifStageDtoCS(idEt,
				"2021");

		// System.out.println("----------------------------------> Teacher EncadrantPeda CJ: " + teachersCJ.size());
		// System.out.println("----------------------------------> Teacher EncadrantPeda CS: " + teachersCS.size());

		if (!teachersCJ.isEmpty()) {
			studentDemandeStgDto = teachersCJ.get(0);
		}
		if (!teachersCS.isEmpty()) {
			studentDemandeStgDto = teachersCS.get(0);
		}

		return studentDemandeStgDto;
	}

	public StudentMandatoryInternshipDto findStudentMandatoryInternshipByStudentId(String idEt) {

		// System.out.println("----------------------------------> Teacher EncadrantPeda ID: " + idEt);

		StudentMandatoryInternshipDto studentDemandeStgDto = null;
		List<StudentMandatoryInternshipDto> teachersCJ = inscriptionRepository.findStudentMandatoryInternshipDtoCJ(idEt,
				"2021");
		List<StudentMandatoryInternshipDto> teachersCS = inscriptionRepository.findStudentMandatoryInternshipDtoCS(idEt,
				"2021");

		// System.out.println("----------------------------------> Teacher EncadrantPeda CJ: " + teachersCJ.size());
		// System.out.println("----------------------------------> Teacher EncadrantPeda CS: " + teachersCS.size());

		if (!teachersCJ.isEmpty()) {
			studentDemandeStgDto = teachersCJ.get(0);
		}
		if (!teachersCS.isEmpty()) {
			studentDemandeStgDto = teachersCS.get(0);
		}

		return studentDemandeStgDto;
	}

	public String findMailPedagogicalEncadrant(String idStudent) {
		String idEncadrantPedagogique = null;
		String mailEncadrantPedagogique = "--";
		List<String> idEncadrantPedagogiqueCJ = inscriptionRepository.findEncadrantPedagogiqueByStudent(idStudent);
		List<String> idEncadrantPedagogiqueCS = inscriptionRepository.findEncadrantPedagogiqueCSByStudent(idStudent);
		// System.out.println("---------------------------------------------------> I");

		if (!idEncadrantPedagogiqueCJ.isEmpty()) {
			idEncadrantPedagogique = idEncadrantPedagogiqueCJ.get(0);
		}
		if (!idEncadrantPedagogiqueCS.isEmpty()) {
			idEncadrantPedagogique = idEncadrantPedagogiqueCS.get(0);
		}

		if (idEncadrantPedagogique != null) {
			mailEncadrantPedagogique = teacherRepository.findTeacherMailById(idEncadrantPedagogique);
		}

		return mailEncadrantPedagogique;
	}

	public String findFullNamePedagogicalExpert(String idStudent) {
		String idPedagogicalExpert = null;
		String fullNamePedagogicalExpert = "--";
		List<String> idPedagogicalExpertCJ = inscriptionRepository.findExpertPedagogiqueByStudent(idStudent);
		List<String> idPedagogicalExpertCS = inscriptionRepository.findExpertPedagogiqueCSByStudent(idStudent);
		// System.out.println("---------------------------------------------------> I");

		if (!idPedagogicalExpertCJ.isEmpty()) {
			idPedagogicalExpert = idPedagogicalExpertCJ.get(0);
		}
		if (!idPedagogicalExpertCS.isEmpty()) {
			idPedagogicalExpert = idPedagogicalExpertCS.get(0);
		}

		if (idPedagogicalExpert != null) {
			fullNamePedagogicalExpert = teacherRepository.findTeacherFullNameById(idPedagogicalExpert);
		}

		return fullNamePedagogicalExpert;
	}

	public StudentDemandeStageDto findStudentDemandeStgByStudentId(String idEt) {

		// System.out.println("----------------------------------> Teacher EncadrantPeda ID: " + idEt);

		StudentDemandeStageDto studentDemandeStgDto = null;
		List<StudentDemandeStageDto> teachersCJ = inscriptionRepository.findStudentDemandeStageDtoCJ(idEt, "2021");
		List<StudentDemandeStageDto> teachersCS = inscriptionRepository.findStudentDemandeStageDtoCS(idEt, "2021");

		// System.out.println("----------------------------------> Teacher EncadrantPeda CJ: " + teachersCJ.size());
		// System.out.println("----------------------------------> Teacher EncadrantPeda CS: " + teachersCS.size());

		if (!teachersCJ.isEmpty()) {
			studentDemandeStgDto = teachersCJ.get(0);
		}
		if (!teachersCS.isEmpty()) {
			studentDemandeStgDto = teachersCS.get(0);
		}

		return studentDemandeStgDto;
	}

	public ResponsableServiceStage findResponsableServiceStageByClass(String currentClassOff) {
		String currentClass = currentClassOff.toUpperCase();

		ResponsableServiceStage respServStage = null;
		if (currentClass.contains("IOSYS") || currentClass.contains("IRT") || currentClass.contains("ARC")
				|| currentClass.contains("NIDS") || currentClass.contains("SIM") || currentClass.contains("SLEAM")
				|| currentClass.contains("ISEM") || currentClass.contains("5WIN") || currentClass.contains("BI")
				|| currentClass.contains("DS") || currentClass.contains("GL") || currentClass.contains("INFINI")
				|| currentClass.contains("5TWIN") || currentClass.contains("INFO") || currentClass.contains("SIGMA")
				|| currentClass.contains("SE") || currentClass.contains("SAE") || currentClass.contains("ERP")) {
			respServStage = responsableServiceStageRepository.findByIdrss("SR-STG-IT");
			// specialite = "03"; // 01 Informatique
		}

		if (currentClass.contains("EM") || currentClass.contains("MÉCAT") || currentClass.contains("OGI")) {
			respServStage = responsableServiceStageRepository.findByIdrss("SR-STG-EM");
			// specialite = "05"; // 04 Electromecanique
		}

		if (currentClass.contains("GC")) {
			respServStage = responsableServiceStageRepository.findByIdrss("SR-STG-GC");
			// specialite = "06"; // 03 Génie-Civil
		}

		return respServStage;
	}

	public String findDepartmentAbbByClassWithStat(String codeClOff) {
		String codeCl = codeClOff.toUpperCase();
		System.out.println("--------------------------------- CLASS: " + codeCl);
		String abbDept = "";
		if (codeCl.contains("BI") || codeCl.contains("DS") || codeCl.contains("GL") || codeCl.contains("INFINI")
				|| codeCl.contains("5TWIN") || codeCl.contains("INFOB") || codeCl.contains("SIGMA")
				|| codeCl.contains("SE") || codeCl.contains("SIM") || codeCl.contains("SAE") || codeCl.contains("NIDS")
				|| codeCl.contains("ARC") || codeCl.contains("CINFO-ARC") || codeCl.contains("SLEAM")
				|| codeCl.contains("CINFO-BI") || codeCl.contains("CINFO-GL")) {
			abbDept = "INFO"; // Informatique
		}

		if (codeCl.contains("IOSYS") || codeCl.contains("5WIN")) {
			abbDept = "TEL"; // Télécommunication
		}

		if (codeCl.contains("EM") || codeCl.contains("MÉCAT") || codeCl.contains("OGI") || codeCl.contains("CEMMEC")
				|| codeCl.contains("CEMOGI")) {
			abbDept = "EM"; // Electromecanique
		}

		if (codeCl.contains("GC")) {
			abbDept = "GC"; // Génie-Civil
		}

		if (codeCl.contains("ALINFO")) {
			abbDept = "ALT"; // IT
		}

		return abbDept;

	}

	public StudentGrilleAcademicEncadrantDto findStudentTreatDepotByHisId(String idEt, String selectedYear) {

		String fullName = findStudentFullNameById(idEt);
		String classe = findCurrentClassByIdEt(idEt);
		String department = findDepartmentByClass(classe);
		String option = findOptionByClass(classe, optionRepository.listOptionsByYear("2021"));

		BigDecimal studentMarkRest = noteRestitutionRepository.findNoteRestitutionByStu(idEt, selectedYear);

		if (studentMarkRest == null) {
			studentMarkRest = new BigDecimal("-1");
		}

		System.out.println("------- studentMarkRest --------> studentMarkRest: " + studentMarkRest);

		String societeLibelle = "--";
		String titreProjet = "--";

		FichePFETitreProjetLabelCompanyDto companyHistoryDto = conventionRepository
				.findTitreProjectAndLabelCompanyByStudent(idEt);
		if (companyHistoryDto != null) {
			societeLibelle = companyHistoryDto.getCompanylabel();
			titreProjet = companyHistoryDto.getProjectTitle();
		}

		StudentGrilleAcademicEncadrantDto studentGAE = new StudentGrilleAcademicEncadrantDto(fullName, department,
				option, "dateDepot", societeLibelle, titreProjet, studentMarkRest.toString());

		return studentGAE;

	}
	

	/********************************************* Reset Password **************************************/

	public String forgotPassword(String email) {

		System.out.println("-----------------------> AZERTY 1.1.1: " + email);
		Optional<Teacher> teacherOptional = teacherRepository.findByMailEns(email);
		//System.out.println("-----------------------> AZERTY 1.1.2: " + TeacherOptional.get().getEmail());
		if (!teacherOptional.isPresent())
		{
			System.out.println("-----------------------> AZERTY 1.1.3");
			return "Invalid email id.";
		}
		else
		{
			System.out.println("-----------------------> AZERTY 1.1.4");
			Teacher teacher = teacherOptional.get();
			System.out.println("-----------------------> AZERTY 1.1.5");
			teacher.setToken(generateToken());
			System.out.println("-----------------------> AZERTY 1.16");
			teacher.setTokenCreationDate(LocalDateTime.now());
			System.out.println("-----------------------> AZERTY 1.1.7");

			teacher = teacherRepository.save(teacher);
			System.out.println("-----------------------> AZERTY 1.1.8");
			return teacher.getToken();
		}
		
	}

	public String resetPassword(String token, String password) throws UnsupportedEncodingException
	{

		String result = "TOKENINVALID";
		
		System.out.println("-------------RESET PWD TEACHER----------> AZERTY 1.2.1");
		Optional<Teacher> teacherOptional = Optional.ofNullable(teacherRepository.findByToken(token));
		System.out.println("-----------------------> AZERTY 1.2.2");
		if (!teacherOptional.isPresent()) {
			System.out.println("-----------------------> AZERTY 1.2.3.0: " + result);
			result = "TOKENINVALID";
			System.out.println("-----------------------> AZERTY 1.2.3.1: " + result);
		}
		else
		{
			System.out.println("-----------------------> AZERTY 1.2.4: " + result);
			LocalDateTime tokenCreationDate = teacherOptional.get().getTokenCreationDate();
			System.out.println("-----------------------> AZERTY 1.2.5");
			if (isTokenExpired(tokenCreationDate))
			{
				System.out.println("-----------------------> AZERTY 1.2.6");
				result = "TOKENEXPIRED";
			}
			else
			{
				System.out.println("-----------------------> AZERTY 1.2.7");
				Teacher teacher = teacherOptional.get();
				System.out.println("-----------------------> AZERTY 1.2.8");
				
				
				/*****************************************************************************************************************/
				String pwdJWTEnseignant = URLDecoder.decode(password, StandardCharsets.UTF_8.toString());
				String mpCryptoPassword = "salt";
				
		        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		        encryptor.setPassword(mpCryptoPassword);
				
				// Teacher.setPwdJWTEnseignant(password);
				teacher.setPwdJWTEnseignant(encoder.encode(pwdJWTEnseignant) + "$$$$$" + encryptor.encrypt(pwdJWTEnseignant));
				teacher.setDateModifyJwtPwd(new Date());
				
				teacher.setToken(null);
				System.out.println("-----------------------> AZERTY 1.2.10");
				teacher.setTokenCreationDate(null);
				teacher.setDateModifyJwtPwd(new Date());
				System.out.println("-----------------------> AZERTY 1.2.11");
				teacherRepository.save(teacher);  // HELP1811
				System.out.println("-----------------------> AZERTY 1.2.12");
				
				System.out.println("-----------####################------------> pwdJWTEnseignant: " + encoder.encode(pwdJWTEnseignant) + "$$$$$" + encryptor.encrypt(pwdJWTEnseignant));
				

				/***************************************** Notification By Mail *****************************************/
		    	DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		    	String dateChangeMdp = dateFormata.format(new Date());
		    					
		   		String subject = "Changement Mot de Passe pour Espace Enseignant";
		    			
	    		String content = "Nous voulons vous informer par le présent mail que vous avez changé"
		    					+ " votre <strong><font color=grey> Mot de Passe </font></strong> "
		    					+ "le <font color=blue> " + dateChangeMdp + " </font>."
		    					+ "<br/><br/>"
		    					+ "<font color=grey fontStyle=italic>"
		    					+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
		    					+ "<br/>"
		    					+ "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";
		    			
		    	sendMail(subject, teacher.getMailEns().toLowerCase().trim(), content);
		    	/********************************************************************************************************/

				result = "TOKENEXIST";
			}
		}
		
		return result;
	
	}


	/********************************************* Reset Password Etudiant **************************************/

	public String forgotStudentPassword(String idEt)
	{
		
		List<String> activatedYears = settingsRepository.findActivatedYears();
		
		String token = "NOTEXIST";
		List<String> idCJs = studentRepository.findIdCJStudentByIdWithActivatedYears(idEt, activatedYears);
		List<String> idCSs = studentRepository.findIdCSStudentByIdWithActivatedYears(idEt, activatedYears);
		
		System.out.println("-------------------HI CJ----> AZERTY 1.1.0.1: " + idCJs.size());
		System.out.println("-------------------HI CS----> AZERTY 1.1.0.2: " + idCSs.size());
		
		if(!idCJs.isEmpty())
		{
			Optional<StudentCJ> studentOptional = studentRepository.findCJStudentByIdWithActivatedYears(idEt, activatedYears);
			System.out.println("-------------------HI CJ----> AZERTY 1.1.1: " + idEt);
//			Optional<StudentCJ> studentOptional = studentRepository.findCJStudentByMailWithActivatedYears(email, activatedYears);
//			Optional<StudentCS> studentOptional = studentRepository.findCSStudentByMailWithActivatedYears(email, activatedYears);

			if (!studentOptional.isPresent())
			{
				System.out.println("--------------HI CJ---------> AZERTY 1.1.3");
				token = "Invalid email id.";
			}
			else
			{
				System.out.println("------------HI CJ-----------> AZERTY 1.1.4");
				StudentCJ studentCJ = studentOptional.get();
				System.out.println("-----------------HI CJ------> AZERTY 1.1.5");
				studentCJ.setToken(generateToken());
				System.out.println("--------------------HI CJ---> AZERTY 1.16");
				studentCJ.setTokenCreationDate(LocalDateTime.now());
				System.out.println("------------HI CJ-----------> AZERTY 1.1.7");

				studentCJ = studentRepository.save(studentCJ);
				System.out.println("-----------------HI CJ------> AZERTY 1.1.8");
				token = studentCJ.getToken();
			}
		}
		
		if(!idCSs.isEmpty())
		{
			Optional<StudentCS> studentOptional = studentRepository.findCSStudentByIdWithActivatedYears(idEt, activatedYears);
			System.out.println("-----------------HI CS------> AZERTY 1.1.1: " + idEt);
//			Optional<StudentCJ> studentOptional = studentRepository.findCJStudentByMailWithActivatedYears(email, activatedYears);
//			Optional<StudentCS> studentOptional = studentRepository.findCSStudentByMailWithActivatedYears(email, activatedYears);

			if (!studentOptional.isPresent())
			{
				System.out.println("-------------HI CS----------> AZERTY 1.1.3");
				token = "Invalid email id.";
			}
			else
			{
				System.out.println("------------------HI CS-----> AZERTY 1.1.4");
				StudentCS studentCS = studentOptional.get();
				System.out.println("--------HI CS---------------> AZERTY 1.1.5");
				studentCS.setToken(generateToken());
				System.out.println("-------------HI CS----------> AZERTY 1.16");
				studentCS.setTokenCreationDate(LocalDateTime.now());
				System.out.println("--------HI CS---------------> AZERTY 1.1.7");

				studentCS = studentCSRepository.save(studentCS);
				System.out.println("-------------HI CS----------> AZERTY 1.1.8");
				token = studentCS.getToken();
			}
		}
		return token;
	}

	public String resetStudentPassword(String token, String password) throws UnsupportedEncodingException
	{

		
		String result = "TOKENINVALID";
		
		System.out.println("-------------RESET PWD STUDENT----------> !");
		
		List<String> activatedYears = settingsRepository.findActivatedYears();
		
		List<String> idCJs = studentRepository.findIdCJStudentByTokenWithActivatedYears(token, activatedYears);
		List<String> idCSs = studentRepository.findIdCSStudentByTokenWithActivatedYears(token, activatedYears);
		
		System.out.println("-----------HI CJ------------> AZERTY 1.2.0.1: " + idCJs.size());
		System.out.println("-----------HI CJ------------> AZERTY 1.2.0.2: " + idCSs.size());
		System.out.println("-----------HI CJ------------> AZERTY 1.2.1");
		Optional<StudentCJ> studentOptCJ = Optional.ofNullable(studentRepository.findByToken(token));
		System.out.println("------------HI CJ-----------> AZERTY 1.2.2");
		if (!studentOptCJ.isPresent()) {
			System.out.println("--------HI CJ---------------> AZERTY 1.2.3");
			result = "TOKENINVALID";
		}
		
		System.out.println("----------HI CS-------------> AZERTY 1.2.1");
		Optional<StudentCS> studentOptCS = Optional.ofNullable(studentCSRepository.findByToken(token));
		System.out.println("---------------HI CS--------> AZERTY 1.2.2");
		if (!studentOptCS.isPresent()) {
			System.out.println("----------------HI CS-------> AZERTY 1.2.3");
			result = "TOKENINVALID";
		}
		
		if(!idCJs.isEmpty())
		{
			System.out.println("----------HI CJ-------------> AZERTY 1.2.4");
			LocalDateTime tokenCreationDate = studentOptCJ.get().getTokenCreationDate();
			System.out.println("----------HI CJ-------------> AZERTY 1.2.5");
			if (isTokenExpired(tokenCreationDate))
			{
				System.out.println("---------HI CJ--------------> AZERTY 1.2.6");
				result = "TOKENEXPIRED";
			}
			else
			{
				System.out.println("----------HI CJ-------------> AZERTY 1.2.7");
				StudentCJ stuCJ = studentOptCJ.get();
				System.out.println("---------HI CJ--------------> AZERTY 1.2.8");
				
				/*****************************************************************************************************************/
				String pwdJWTStudentCJ = URLDecoder.decode(password, StandardCharsets.UTF_8.toString());
				String mpCryptoPassword = "salt";
				
		        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		        encryptor.setPassword(mpCryptoPassword);
				
				// Teacher.setPwdJWTEnseignant(password);
		        stuCJ.setPwdJWTEtudiant(encoder.encode(pwdJWTStudentCJ) + "$$$$$" + encryptor.encrypt(pwdJWTStudentCJ));
		        stuCJ.setDateModifyJwtPwd(new Date());
				
				stuCJ.setToken(null);
				System.out.println("--------------HI CJ---------> AZERTY 1.2.10");
				stuCJ.setTokenCreationDate(null);
				stuCJ.setDateModifyJwtPwd(new Date());
				System.out.println("------HI CJ-----------------> AZERTY 1.2.11");
				studentRepository.save(stuCJ);  // HELP1811
				System.out.println("-----------HI CJ------------> AZERTY 1.2.12");
				

				/***************************************** Notification By Mail *****************************************/
		    	DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		    	String dateChangeMdp = dateFormata.format(new Date());
		    					
		   		String subject = "Changement Mot de Passe pour Espace Étudiant";
		    			
	    		String content = "Nous voulons vous informer par le présent mail que vous avez changé"
		    					+ " votre <strong><font color=grey> Mot de Passe </font></strong> "
		    					+ "le <font color=red> " + dateChangeMdp + " </font>."
		    					+ "<br/><br/>"
		    					+ "<font color=grey fontStyle=italic>"
		    					+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
		    					+ "<br/>"
		    					+ "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";
		    			
		    	sendMail(subject, stuCJ.getAdressemailesp().toLowerCase().trim(), content);
		    	/********************************************************************************************************/

				result = "TOKENEXIST";
			}			
		}
		if(!idCSs.isEmpty())
		{
			System.out.println("--------------HI CS---------> AZERTY 1.2.4");
			LocalDateTime tokenCreationDate = studentOptCS.get().getTokenCreationDate();
			System.out.println("------------HI CS-----------> AZERTY 1.2.5");
			if (isTokenExpired(tokenCreationDate))
			{
				System.out.println("-------------HI CS----------> AZERTY 1.2.6");
				result = "TOKENEXPIRED";
			}
			else
			{
				System.out.println("--------HI CS---------------> AZERTY 1.2.7");
				StudentCS stuCS = studentOptCS.get();
				System.out.println("-------------HI CS----------> AZERTY 1.2.8");
				
				/*****************************************************************************************************************/
				String pwdJWTStudentCS = URLDecoder.decode(password, StandardCharsets.UTF_8.toString());
				String mpCryptoPassword = "salt";
				
		        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		        encryptor.setPassword(mpCryptoPassword);
				
				// Teacher.setPwdJWTEnseignant(password);
		        stuCS.setPwdJWTEtudiant(encoder.encode(pwdJWTStudentCS) + "$$$$$" + encryptor.encrypt(pwdJWTStudentCS));
		        stuCS.setDateModifyJwtPwd(new Date());
				
				stuCS.setToken(null);
				System.out.println("----------HI CS-------------> AZERTY 1.2.10");
				stuCS.setTokenCreationDate(null);
				stuCS.setDateModifyJwtPwd(new Date());
				System.out.println("---------------HI CS--------> AZERTY 1.2.11");
				studentCSRepository.save(stuCS);  // HELP1811
				System.out.println("--------HI CS---------------> AZERTY 1.2.12");
				
				/***************************************** Notification By Mail *****************************************/
		    	DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		    	String dateChangeMdp = dateFormata.format(new Date());
		    					
		   		String subject = "Changement Mot de Passe pour Espace Étudiant";
		    			
	    		String content = "Nous voulons vous informer par le présent mail que vous avez changé"
		    					+ " votre <strong><font color=grey> Mot de Passe </font></strong> "
		    					+ "le <font color=red> " + dateChangeMdp + " </font>."
		    					+ "<br/><br/>"
		    					+ "<font color=grey fontStyle=italic>"
		    					+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
		    					+ "<br/>"
		    					+ "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";
		    			
		    	sendMail(subject, stuCS.getAdressemailesp().toLowerCase().trim(), content);
		    	/********************************************************************************************************/

				result = "TOKENEXIST";
			}
		}
		
		return result;
	
	}


	/********************************************* Reset Password CD/CPS **************************************/

	public String forgotCDCPSPassword(String email)
	{
		System.out.println("-----------------------> AZERTY 1.1.1: " + email);
		Optional<PedagogicalCoordinator> pedagogicalCoord = pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByLogin(email);
		//System.out.println("-----------------------> AZERTY 1.1.2: " + TeacherOptional.get().getEmail());
		if (!pedagogicalCoord.isPresent())
		{
			System.out.println("-----------------------> AZERTY 1.1.3");
			return "Invalid email id.";
		}
		else
		{
			System.out.println("-----------------------> AZERTY 1.1.4");
			PedagogicalCoordinator pedaCoord = pedagogicalCoord.get();
			System.out.println("-----------------------> AZERTY 1.1.5");
			pedaCoord.setToken(generateToken());
			System.out.println("-----------------------> AZERTY 1.16");
			pedaCoord.setTokenCreationDate(LocalDateTime.now());
			System.out.println("-----------------------> AZERTY 1.1.7");

			pedaCoord = pedagogicalCoordinatorRepository.save(pedaCoord);
			System.out.println("-----------------------> AZERTY 1.1.8");
			return pedaCoord.getToken();
		}
		
	}

	public String resetCDCPSPassword(String token, String password) throws UnsupportedEncodingException
	{
		String result = "TOKENINVALID";
		
		System.out.println("-------------RESET PWD TEACHER----------> AZERTY 1.2.1");
		Optional<PedagogicalCoordinator> pedaCoordOptional = Optional.ofNullable(pedagogicalCoordinatorRepository.findByToken(token));
		System.out.println("-----------------------> AZERTY 1.2.2");
		if (!pedaCoordOptional.isPresent()) {
			System.out.println("-----------------------> AZERTY 1.2.3.0: " + result);
			result = "TOKENINVALID";
			System.out.println("-----------------------> AZERTY 1.2.3.1: " + result);
		}
		else
		{
			System.out.println("-----------------------> AZERTY 1.2.4: " + result);
			LocalDateTime tokenCreationDate = pedaCoordOptional.get().getTokenCreationDate();
			System.out.println("-----------------------> AZERTY 1.2.5");
			if (isTokenExpired(tokenCreationDate))
			{
				System.out.println("-----------------------> AZERTY 1.2.6");
				result = "TOKENEXPIRED";
			}
			else
			{
				System.out.println("-----------------------> AZERTY 1.2.7");
				PedagogicalCoordinator pedaCoord = pedaCoordOptional.get();
				System.out.println("-----------------------> AZERTY 1.2.8");
				
				
				/*****************************************************************************************************************/
				String pwdJWTEnseignant = URLDecoder.decode(password, StandardCharsets.UTF_8.toString());
				String mpCryptoPassword = "salt";
				
		        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		        encryptor.setPassword(mpCryptoPassword);
				
				// Teacher.setPwdJWTEnseignant(password);
				pedaCoord.setPwdJWTPC(encoder.encode(pwdJWTEnseignant) + "$$$$$" + encryptor.encrypt(pwdJWTEnseignant));
				pedaCoord.setDateModifyJwtPwd(new Date());
				
				pedaCoord.setToken(null);
				System.out.println("-----------------------> AZERTY 1.2.10");
				pedaCoord.setTokenCreationDate(null);
				pedaCoord.setDateModifyJwtPwd(new Date());
				System.out.println("-----------------------> AZERTY 1.2.11");
				pedagogicalCoordinatorRepository.save(pedaCoord);  // HELP1811
				System.out.println("-----------------------> AZERTY 1.2.12");
				
				System.out.println("-----------####################------------> pwdJWTEnseignant: " + encoder.encode(pwdJWTEnseignant) + "$$$$$" + encryptor.encrypt(pwdJWTEnseignant));
				

				/***************************************** Notification By Mail *****************************************/
		    	DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
		    	String dateChangeMdp = dateFormata.format(new Date());
		    					
		   		String subject = "Changement Mot de Passe pour Espace Coordinateur Pédagogique";
		    			
	    		String content = "Nous voulons vous informer par le présent mail que vous avez changé"
		    					+ " votre <strong><font color=grey> Mot de Passe </font></strong> "
		    					+ "le <font color=red> " + dateChangeMdp + " </font>."
		    					+ "<br/><br/>"
		    					+ "<font color=grey fontStyle=italic>"
		    					+ "Remarque: Pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !."
		    					+ "<br/>"
		    					+ "Par conséquent, vous devez créer un nouveau qui sera crypté.</font>";
		    			
		    	sendMail(subject, pedaCoord.getLogin().toLowerCase().trim(), content);
		    	/********************************************************************************************************/

				result = "TOKENEXIST";
			}
		}
		
		return result;
	}

	/**
	 * Generate unique token. You may add multiple parameters to create a strong
	 * token.
	 * 
	 * @return unique token
	 */
	private String generateToken() {
		System.out.println("-----------------------> AZERTY 1.3.1");
		StringBuilder token = new StringBuilder();
		System.out.println("-----------------------> AZERTY 1.3.2");
		return token.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
	}

	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
		System.out.println("-----------------------> AZERTY 1.3.2");
		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);
		System.out.println("-----------------------> AZERTY 1.3.2");
		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}

	

	/*********************************************
	 * Divers
	 **********************************************/

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue_S_I(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	public String decodeEncodedValue(String encodedValue) {
		String result = "";
		try {
			result = URLDecoder.decode(encodedValue, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String gotFormattedDate(String date) {
		String s[] = date.split("-");
		String ans = "";
		for (int i = s.length - 1; i >= 0; i--) {
			ans += s[i] + "-";
		}
		return ans.substring(0, ans.length() - 1);
	}

	public String convertHourToGCH(String fullHour) {
		String part1FH = fullHour.substring(0, 2);
		String part2FH = fullHour.substring(2, 5);

		// System.out.println("-------------------- RES 0: " + part1FH + " - " +
		// part2FH);

		Integer part1FHI = Integer.parseInt(part1FH);

		// System.out.println("-------------------- RES 1: " + part1FHI);

		Integer part1FHIGC = part1FHI - 1;

		// System.out.println("-------------------- RES 2: " + part1FHIGC);

		Integer p1Length = (int) (Math.log10(part1FHIGC) + 1);

		String formattedSH = null;
		if (p1Length == 1) {
			formattedSH = "0" + part1FHIGC + part2FH;
		}
		if (p1Length == 2) {
			formattedSH = part1FHIGC + part2FH;
		}

		// System.out.println("---------------------------------- RES 3: " +
		// formattedSH);

		return formattedSH;

	}

	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public Date addDAYStoTODAY(Integer nbrDaysToAdd, Date startStg) {
		DateTime dayOff = new DateTime(startStg);
		DateTime dayPlus = dayOff.plusDays(nbrDaysToAdd);
		Date newDate = dayPlus.toDate();

		return newDate;
	}

	public String fileLabel(String ganDiagFP) {
		// String ganDiagFP = "C:/ESP/uploads/1espdsi20201646413605885.png";

		String gdName = ganDiagFP.substring(ganDiagFP.lastIndexOf("C:/ESP/uploads/") + 15,
				ganDiagFP.lastIndexOf("espdsi2020"));
		String gdExt = ganDiagFP.substring(ganDiagFP.lastIndexOf("."));

		String gdFN = gdName + gdExt;

		return gdFN;
	}

	public String findIdExpertByStudentAndYear(String idStu, String year) {
		String idExpert = null;
		List<String> idTeachersCJ = inscriptionRepository.findExpertCJByStudentAndYear(idStu, year);
		List<String> idTeachersCS = inscriptionRepository.findExpertCSByStudentAndYear(idStu, year);
		System.out.println("----------------------------------> idTeachersCS: " + idTeachersCS);

		if (!idTeachersCJ.isEmpty()) {
			idExpert = idTeachersCJ.get(0);
			System.out.println("----------------------------------> idExpert CDJ: " + idExpert);
		}
		if (!idTeachersCS.isEmpty()) {
			idExpert = idTeachersCS.get(0);
			System.out.println("----------------------------------> idExpert CDS: " + idExpert);
		}

		System.out.println("----------------------------------> idExpert RESULT: " + idExpert);

		return idExpert;
	}

	public List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdEtAndYear(String idEt, String year) {
		List<StudentFullNameMailTelDto> students = null;
		List<StudentFullNameMailTelDto> studentsCJ = studentRepository
				.findStudentsFullNameMailTelByIdETAndYearCJALT(idEt, year);
		List<StudentFullNameMailTelDto> studentsCS = studentRepository.findStudentsFullNameMailTelByIdETAndYearCS(idEt,
				year);

		System.out.println("---------------------**-------------> JWT NOT SET CJ: " + studentsCJ);
		System.out.println("---------------------**-------------> JWT NOT SET CS: " + studentsCS);

		if (!studentsCJ.isEmpty()) {
			students = studentsCJ;
		}
		if (!studentsCS.isEmpty()) {
			students = studentsCS;
		}

		return students;
	}


	public List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdEtWithYear(String idEt, String year)
	{
		
		List<StudentFullNameMailTelDto> students = null;
		List<StudentFullNameMailTelDto> studentsCJ = studentRepository.findStudentsFullNameMailTelByIdETCJALTWithYear(year, idEt);
		List<StudentFullNameMailTelDto> studentsCS = studentRepository.findStudentsFullNameMailTelByIdETCSWithYear(year, idEt);
		
		System.out.println("---------------------**-------------> JWT NOT SET CJ: " + studentsCJ);
		System.out.println("---------------------**-------------> JWT NOT SET CS: " + studentsCS);
		
		if(!studentsCJ.isEmpty())
		{
			students = studentsCJ;
		}
		if(!studentsCS.isEmpty())
		{
			students = studentsCS;
		}
		
		return students;
	}


	public List<DepotFinalDto> loadFichesForDepotNotYetTreatedByYear(String year, String idServiceStage)
	{

		List<DepotFinalDto> students = new ArrayList<DepotFinalDto>();

		System.out.println("---------------------------------------------------> year: " + year);
		List<DepotFinalDto> depotNYT_CJ = fichePFERepository.loadFichesForDepotNotYetTreatedForStudents_CJ(year, idServiceStage);
		List<DepotFinalDto> depotNYT_ALT = fichePFERepository.loadFichesForDepotNotYetTreatedForStudents_ALT(year, idServiceStage);
		List<DepotFinalDto> depotNYT_CS = fichePFERepository.loadFichesForDepotNotYetTreatedForStudents_CS(year, idServiceStage);

		System.out.println("----------------------------------> depotNYT_CJ: " + depotNYT_CJ.size());
		System.out.println("----------------------------------> depotNYT_ALT: " + depotNYT_ALT.size());
		System.out.println("----------------------------------> depotNYT_CS: " + depotNYT_CS.size());

		if(!depotNYT_CJ.isEmpty())
		{
			students.addAll(depotNYT_CJ);
		}
		if(!depotNYT_ALT.isEmpty())
		{
			students.addAll(depotNYT_ALT);
		}
		if(!depotNYT_CS.isEmpty())
		{
			students.addAll(depotNYT_CS);
		}

		return students;
	}

	public String findIdEncadrantPedagogiqueByStudentByYear(String idStu, String year)
	{
		String idEncadrantPedagogique = null;
		System.out.println("----------------------------------> idStu: " + idStu);
		List<String> idTeachersCJ = inscriptionRepository.findEncadrantPedagogiqueByStudentByYear(idStu, year);
		List<String> idTeachersCS = inscriptionRepository.findEncadrantPedagogiqueCSByStudentByYear(idStu, year);
		System.out.println("----------------------------------> idTeachersCJ: " + idTeachersCJ.size());
		System.out.println("----------------------------------> idTeachersCS: " + idTeachersCS.size());
		
		if(!idTeachersCJ.isEmpty())
		{
			for(String itj : idTeachersCJ)
			{
				if(itj != null)
				{
					idEncadrantPedagogique = itj;
					System.out.println("-----------------> idAE CDJ: " + idEncadrantPedagogique);
				}
			}
		}
		if(!idTeachersCS.isEmpty())
		{
			for(String its : idTeachersCS)
			{
				if(its != null)
				{
					idEncadrantPedagogique = its;
					System.out.println("-----------------> idAE CDS: " + idEncadrantPedagogique);
				}
			}
		}
		
		System.out.println("----------------------------------> idAE RESULT: " + idEncadrantPedagogique);
		
		return idEncadrantPedagogique;
	}
	
	public List<StudentAffectationDetailsDto> findStudentsDtoTrainedByPExpAndYear(String idPE, String year)
	{
		List<StudentAffectationDetailsDto> affStuCJ = inscriptionRepository.findListOfStudentsDtoByPedagogicalExpertCJ(idPE, year);
		List<StudentAffectationDetailsDto> affStuCJALT = inscriptionRepository.findListOfStudentsDtoByPedagogicalExpertCJALT(idPE, year);
		List<StudentAffectationDetailsDto> affStuCS = inscriptionRepository.findListOfStudentsDtoByPedagogicalExpertCS(idPE, year);
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ CDJ: " + affStuCJ.size());
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ALT: " + affStuCJALT.size());
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ CDS: " + affStuCS.size());
		
		List<StudentAffectationDetailsDto> affStu = new ArrayList<StudentAffectationDetailsDto>();
		affStu.addAll(affStuCJ);affStu.addAll(affStuCJALT);affStu.addAll(affStuCS);
		
		affStu.sort(Comparator.comparing(StudentAffectationDetailsDto::getFullName));
		
		return affStu;
	}
	

	public List<StudentConfigDto> findStudentsConfigDtoForConfig(String year)
	{		
		List<StudentConfigDto> students = new ArrayList<StudentConfigDto>();
		List<StudentConfigDto> studentsCJALT = studentRepository.findStudentsCJALTConfigByYear(year);
		List<StudentConfigDto> studentsCS = studentRepository.findStudentsCSConfigByYear(year);
		
//		System.out.println("----CONFIG--------> JWT NOT SET CJ: " + studentsCJALT);
//		System.out.println("----CONFIG--------> JWT NOT SET CS: " + studentsCS);
		
		students.addAll(studentsCJALT);
		students.addAll(studentsCS);
		
		students.sort(Comparator.comparing(StudentConfigDto::getClasseStudent).thenComparing(StudentConfigDto::getFullNameStudent));

		return students;
	}
	

	public String findOptionByClasseStudent(String idEt, String classe, String year)
	{
		String opt = "INIT";
		if(classe.startsWith("5ME21"))
		{
			opt = classe.substring(classe.lastIndexOf("-") + 1);
		}
		else if(classe.contains("4ALINFO"))
		{
			opt = optionStudentALTRepository.findOptionByStudentALTAndYear(idEt, year);
		}
		else
		{
			opt = findOptionByClass(classe, optionRepository.listOptionsByYear(year)).replace("_01", "");
		}
		return opt;
	}

	public Integer findNbrEncadrementByAE(String idEns)
	{
		Integer nbrEncByTeacherCJ = inscriptionRepository.findNbrEncadrementCJByAE(idEns).size();
		Integer nbrEncByTeacherCS = inscriptionRepository.findNbrEncadrementCSByAE(idEns).size();
		
		Integer nbrEncByTeacher = nbrEncByTeacherCJ + nbrEncByTeacherCS;
		
		return nbrEncByTeacher;
	}

	public StudentDetailsDto findStudentDetailsDtoById(String idEt)
	{
		StudentDetailsDto student = null;
		StudentDetailsDto studentCJALT = studentRepository.findStudentDetailsDtoDtoById_CJALT(idEt);
		StudentDetailsDto studentCS = studentRepository.findStudentDetailsDtoDtoById_CS(idEt);

		System.out.println("---------------------**-------------> JWT NOT SET CJ+ALT: " + studentCJALT);
		System.out.println("---------------------**-------------> JWT NOT SET CS: " + studentCS);

		if(studentCJALT != null)
		{
			student = studentCJALT;
		}
		if(studentCS != null)
		{
			student = studentCS;
		}

		return student;
	}

}

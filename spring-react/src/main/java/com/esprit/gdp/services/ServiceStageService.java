package com.esprit.gdp.services;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.gdp.dto.ConventionForRSSDto;
import com.esprit.gdp.dto.ConventionsValidatedForRSSDto;
import com.esprit.gdp.dto.DepotRapport;
import com.esprit.gdp.dto.StudentForSoutenance;
import com.esprit.gdp.dto.StudentIdNomPrenomDto;
import com.esprit.gdp.dto.StudentPFEDepasse;
import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.EntrepriseAccueil;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.repository.AvenantRepository;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.EntrepriseAccueilRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.OptionRepository;
import com.esprit.gdp.repository.SettingsRepository;
import com.esprit.gdp.repository.StudentCSRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Service
public class ServiceStageService {

	@Autowired
	private TeacherRepository enseignantRepository;

	@Autowired
	private ConventionRepository conventionRepository;

	@Autowired
	private FichePFERepository fichePFERepository;

	@Autowired
	private AvenantRepository avenantRepository;

	@Autowired
	private StudentRepository etudiantRepository;

	@Autowired
	private StudentCSRepository etudiantRepositoryCS;

	@Autowired
	private EntrepriseAccueilRepository entrepriseRepository;

	@Autowired
	CodeNomenclatureRepository codeNomenclatureRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	UtilServices utilServices;

	@Autowired
	OptionRepository optionRepository;

	public List<Convention> getConventions(String id) {
		List<Convention> listConvention = conventionRepository.getConventionsByServiceStage(id);
		return listConvention;
	}

	// Got All Conv
	public List<Convention> getAllConventions() {
		List<Convention> listConvention = conventionRepository.getAllConventions();
		return listConvention;
	}

	// Got All Conv DTO
	public List<ConventionForRSSDto> getAllConventionsDtoForRSS() {
		List<ConventionForRSSDto> listConventions = conventionRepository.getAllConventionsDtoForRSS();

		for (ConventionForRSSDto c : listConventions) {
			System.out.println("--------------------------> DATE: " + c.getDateConvention());
			String idEt = c.getIdEt();
			String classe = utilServices.findCurrentClassByIdEt(idEt);

			String convCodePays = c.getPaysConvention();
			if (convCodePays.equalsIgnoreCase("--")) {
				c.setPaysConvention("EN");
			} else {
				c.setPaysConvention(convCodePays);
			}

			c.setNomEt(utilServices.findStudentFullNameById(idEt));
			c.setDepartEt(utilServices.findDepartmentAbbByClassWithStat(classe));
		}

		return listConventions;
	}

	// Got All Conv By RSS DTO
	public List<ConventionForRSSDto> getAllConventionsDtoByRSS(String idRSS) {
		String kindRSS = idRSS.replace("SR-STG-", "");

		System.out.println("------------####################--------------> idRSS: " + idRSS + " - " + kindRSS);

		List<ConventionForRSSDto> listConventions = null;
		if (idRSS.contains("IT")) {
			listConventions = conventionRepository.getAllConventionsDtoForRSS();
			System.out.println("-----####################-----> IT: " + listConventions.size());
		}
		if (idRSS.contains("EM") || idRSS.contains("GC")) {
			listConventions = conventionRepository.getAllConventionsDtoByRSS(kindRSS);
			System.out.println("-----####################-----> EM-GC: " + listConventions.size());
		}

		for (ConventionForRSSDto c : listConventions) {
			// System.out.println("--------------------------> DATE: " +
			// c.getDateConvention());
			String idEt = c.getIdEt();
			String classe = utilServices.findCurrentClassByIdEt(idEt);

			String convCodePays = c.getPaysConvention();
			if (convCodePays.equalsIgnoreCase("--")) {
				c.setPaysConvention("EN");
			} else {
				c.setPaysConvention(convCodePays);
			}

			c.setNomEt(utilServices.findStudentFullNameById(idEt));
			c.setDepartEt(utilServices.findDepartmentAbbByClassWithStat(classe));
		}

		return listConventions;
	}

	// Got All Conv Validated By RSS DTO - By Option
	public List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDtoByStudentsForRSS(String idRSS, List<String> students)
	{

		String kindRSS = idRSS.replace("SR-STG-", "");
		// System.out.println("----- 34 -------####################-------- VALIDATED ------> idRSS: " + idRSS + " - " + kindRSS);

		List<ConventionsValidatedForRSSDto> listConventions = null;
		//if(idRSS.contains("IT"))
		//{
		listConventions = conventionRepository.getAllConventionsValidatedDtoByStudentsForRSS(students);
		// System.out.println("-----####################-----> IT: " + listConventions.size());
		//}
		/*if (idRSS.contains("EM") || idRSS.contains("GC"))
		{
			listConventions = conventionRepository.getAllConventionsValidatedDtoByStudentsForSpeceficRSS(kindRSS, students);
			// System.out.println("-----####################-----> EM-GC: " + listConventions.size());
		}*/

		for(ConventionsValidatedForRSSDto c : listConventions)
		{
			// System.out.println("--------------------------> DATE: " + c.getDateConvention());
			String idEt = c.getIdEt();
			String classe = utilServices.findCurrentClassByIdEt(idEt);

			String convCodePays = c.getPaysConvention();
			if(convCodePays.equalsIgnoreCase("--"))
			{
				c.setPaysConvention("EN");
			}
			else
			{
				c.setPaysConvention(convCodePays);
			}
//
//		    System.out.println("--------------------------> findStudentFullNameById: " + utilServices.findStudentFullNameById(idEt));
//			System.out.println("--------------------------> findDepartmentAbbByClassWithStat: " + utilServices.findDepartmentAbbByClassWithStat(classe));

			c.setNomEt(utilServices.findStudentFullNameById(idEt));
			c.setDepartEt(utilServices.findDepartmentAbbByClassWithStat(classe));
			c.setCurrentClasse(classe);
		}

		// ess.sort(Comparator.comparing(EncadrementStatusExcelDto::getStudentClasse).thenComparing(EncadrementStatusExcelDto::getStudentFullName));
		return listConventions;
	}

	// Got All Conv Validated By RSS DTO
	public List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDtoByRSS(String idRSS, String idMonth) {
		String kindRSS = idRSS.replace("SR-STG-", "");

		System.out.println("----- 34 -------####################-------- VALIDATED ------> idRSS: " + idRSS + " - "
				+ kindRSS + " - " + idMonth);

		List<ConventionsValidatedForRSSDto> listConventions = null;
		if (idRSS.contains("IT")) {
			listConventions = conventionRepository.getAllConventionsValidatedDto2ForRSS(idMonth);
			System.out.println("-----####################-----> IT: " + listConventions.size());
		}
		if (idRSS.contains("EM") || idRSS.contains("GC")) {
			listConventions = conventionRepository.getAllConventionsValidatedDto2ByRSS(kindRSS, idMonth);
			System.out.println("-----####################-----> EM-GC: " + listConventions.size());
		}

		for (ConventionsValidatedForRSSDto c : listConventions) {
			System.out.println("--------------------------> DATE: " + c.getDateConvention());
			String idEt = c.getIdEt();
			String classe = utilServices.findCurrentClassByIdEt(idEt);

			String convCodePays = c.getPaysConvention();
			if (convCodePays.equalsIgnoreCase("--")) {
				c.setPaysConvention("EN");
			} else {
				c.setPaysConvention(convCodePays);
			}
			//
			// System.out.println("--------------------------> findStudentFullNameById: " +
			// utilServices.findStudentFullNameById(idEt));
			// System.out.println("-------------------------->
			// findDepartmentAbbByClassWithStat: " +
			// utilServices.findDepartmentAbbByClassWithStat(classe));

			c.setNomEt(utilServices.findStudentFullNameById(idEt));
			c.setDepartEt(utilServices.findDepartmentAbbByClassWithStat(classe));

		}

		return listConventions;
	}

	// Got All Demandes Annulation Convention
	public List<Convention> getAllDemandesAnnulationConvention() {
		List<Convention> listConvention = conventionRepository.getAllDemandesAnnulationConvention();
		return listConvention;
	}

	public List<Avenant> getAvenants(String id) {
		List<Avenant> listAvenant = avenantRepository.getConventionsByServiceStage(id);
		return listAvenant;
	}

	// Got All Avenants List
	public List<Avenant> getAllAvenants() {
		List<Avenant> listAvenant = avenantRepository.getAllAvenants();
		return listAvenant;
	}

	public Convention UpdateConventionState(String idET, String date) {

		if (conventionRepository.getConventionById(idET, date).isPresent()) {

			Convention existingConvention = conventionRepository.getConventionById(idET, date).get();
			existingConvention.setTraiter("02");
			return conventionRepository.save(existingConvention);
		} else
			return null;
	}

	public Convention UpdateDemandeAnnulationConventionState(String idET, String date) {

		if (conventionRepository.getConventionById(idET, date).isPresent()) {

			Convention existingConvention = conventionRepository.getConventionById(idET, date).get();
			existingConvention.setTraiter("03");
			return conventionRepository.save(existingConvention);
		} else
			return null;
	}

	public Avenant UpdateAvenantState(String idET, String dateConvention, String DateAvenant) {

		if (avenantRepository.getAvenant(idET, dateConvention, DateAvenant) != null) {
			Avenant existingAvenant = avenantRepository.getAvenant(idET, dateConvention, DateAvenant);
			existingAvenant.setTraiter(true);
			return avenantRepository.save(existingAvenant);
		} else
			return null;
	}

	/*******************
	 * Convention generation by etudiant PDF
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 *************************/

	public ConventionForRSSDto UpdateConventionStateFormedDate(String idET, String date) {

		System.out.println(
				"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL: " + idET + " - " + date);
		if (conventionRepository.getConventionByIdFormeeDate(idET, date).isPresent()) {

			Convention existingConvention = conventionRepository.getConventionByIdFormeeDate(idET, date).get();
			existingConvention.setTraiter("02");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-1: " + existingConvention.getTraiter());
			conventionRepository.save(existingConvention);

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateConvention = dateFormata.format(existingConvention.getConventionPK().getDateConvention());

			System.out.println(
					"$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-2: " + existingConvention.getConventionPK().getDateConvention());
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-3: " + dateConvention);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-4: " + date);
			// dd-mm-yyyy HH24:MI:SS

			ConventionForRSSDto c = conventionRepository.findConventionsByStudentDateDepot(idET, dateConvention);
			String convCodePays = c.getPaysConvention();
			if (convCodePays.equalsIgnoreCase("--")) {
				c.setPaysConvention("EN");
			} else {
				c.setPaysConvention(convCodePays);
			}
			String classe = utilServices.findCurrentClassByIdEt(idET);
			c.setNomEt(utilServices.findStudentFullNameById(idET));
			c.setDepartEt(utilServices.findDepartmentAbbByClassWithStat(classe));

			return c;
		} else
			return null;
	}

	public ConventionForRSSDto RefuseConventionStateFormedDate(String idET, String date) {

		System.out.println(
				"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL: " + idET + " - " + date);
		if (conventionRepository.getConventionByIdFormeeDate(idET, date).isPresent()) {

			Convention existingConvention = conventionRepository.getConventionByIdFormeeDate(idET, date).get();
			existingConvention.setTraiter("05");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-1: " + existingConvention.getTraiter());
			conventionRepository.save(existingConvention);

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String dateConvention = dateFormata.format(existingConvention.getConventionPK().getDateConvention());

			System.out.println(
					"$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-2: " + existingConvention.getConventionPK().getDateConvention());
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-3: " + dateConvention);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$> LOL-4: " + date);
			// dd-mm-yyyy HH24:MI:SS

			ConventionForRSSDto c = conventionRepository.findConventionsByStudentDateDepot(idET, dateConvention);
			String convCodePays = c.getPaysConvention();
			if (convCodePays.equalsIgnoreCase("--")) {
				c.setPaysConvention("EN");
			} else {
				c.setPaysConvention(convCodePays);
			}
			String classe = utilServices.findCurrentClassByIdEt(idET);
			c.setNomEt(utilServices.findStudentFullNameById(idET));
			c.setDepartEt(utilServices.findDepartmentAbbByClassWithStat(classe));

			return c;
		} else
			return null;
	}

	// public void sendMailWithAttachment(String to, String subject, String body,
	// String fileToAttach, String filename) throws AddressException,
	// MessagingException
	// {
	// System.out.println("------------------> START");
	// sendMail(fileToAttach);
	// System.out.println("------------------> END");
	// }
	// {
	// MimeMessagePreparator preparator = new MimeMessagePreparator()
	// {
	// public void prepare(MimeMessage mimeMessage) throws Exception
	// {
	// System.out.println("---------------------------> 1");
	// mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	// mimeMessage.setFrom(new InternetAddress(mail));
	// mimeMessage.setSubject(subject);
	// mimeMessage.setText(body);
	// FileSystemResource file = new FileSystemResource(new File(fileToAttach));
	// MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	// helper.setText("", true);
	// helper.addAttachment(filename, file);
	//
	//
	//
	// System.out.println("---------------------------> 2");
	//
	// MimeMultipart multipart = new MimeMultipart("related");
	// System.out.println("---------------------------> 3");
	// BodyPart messageBodyPart = new MimeBodyPart();
	// String header = "Bonjour,<br/><br/>";
	//
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
	// String convValidDate = dateFormata.format(new Date());
	//
	// String content = "Votre <strong><font color=grey> Convention </font></strong>
	// a été bien validé par le Responsable Services des Stages le <font color=red>
	// " + convValidDate + " </font>."
	// + "<br/> Nous vous souhaitons une excellente chance.";
	//
	// String footer = "<br/><br/>Bonne
	// r&eacute;ception.<br/><br/>------------------------------------<br/>"
	// + "Admin Services Stages<br/>"
	// + "<img src=\"cid:image\" height=\"30\" width=\"100\">";
	//
	// String allContent = header + content + footer;
	//
	// messageBodyPart.setContent(allContent, "text/html; charset=UTF-8");
	// multipart.addBodyPart(messageBodyPart);
	// System.out.println("---------------------------> 4");
	//// messageBodyPart = new MimeBodyPart();
	//// DataSource fds = new FileDataSource("C:\\ESP-DOCS\\esprit.png");
	//// messageBodyPart.setDataHandler(new DataHandler(fds));
	//// messageBodyPart.setHeader("Content-ID","<image>");
	// System.out.println("---------------------------> 5");
	// multipart.addBodyPart(messageBodyPart);
	// mimeMessage.setContent(multipart);
	// System.out.println("---------------------------> 6");
	// }
	// };
	//
	// try
	// {
	// System.out.println("---------------------------> 7");
	// emailSender.send(preparator);
	// System.out.println("---------------------------> 8");
	// } catch (MailException ex) {
	// System.out.println("---------------------------> 9");
	// }
	// }

	// public String GenerateConvention(String idET, String date) throws Exception {
	// String pattern = "dd-MM-yyyy";
	// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	// String datenow = simpleDateFormat.format(new Date());
	// if (conventionRepository.getConventionById(idET, date).isPresent()) {
	//
	// Optional<Convention> C = conventionRepository.getConventionById(idET, date);
	//
	// List<String> los = optionRepository.listOptionsByYear("1988");
	// String Option = utilServices.findOptionByStudent(idET, los);
	// // String Option = etudiantRepository.getOptionForEtudiant(idET).get(0); //
	// oif
	//
	// String Departement = utilServices.findDepartmentByStudent(idET);
	// System.out.println("departement" + Departement);
	//
	// String studentFullName = utilServices.findStudentFullNameById(idET);
	// // Optional<Student> S = etudiantRepository.findById(idET); oif
	//
	// Optional<EntrepriseAccueil> E = entrepriseRepository
	// .findById(C.get().getEntrepriseAccueilConvention().getIdEntreprise());
	// Integer mois = 0;
	//
	// Integer y1 = C.get().getDateDebut().getYear() + 1900;
	// Integer y2 = C.get().getDateFin().getYear() + 1900;
	// Integer m1 = C.get().getDateDebut().getMonth() + 1;
	// Integer m2 = C.get().getDateFin().getMonth() + 1;
	// System.out.println("----------> " + y1 + " - " + y2 + " - " + m1 + " - " +
	// m2);
	// if (y1 == y2) {
	// mois = (m2 - m1);
	// System.out.println("----------> 1:" + y1 + " - " + y2 + " - " + m1 + " - " +
	// m2);
	// } else {
	// int nbryears = (y2 - y1);
	// mois = (12 - m1) + (((nbryears - 1) * 12) + m2);
	// System.out.println("----------> 2:" + y1 + " - " + y2 + " - " + m1 + " - " +
	// m2);
	// }
	// System.out.println("----------> 3:" + mois);
	// // Calendar c = Calendar.getInstance();
	// // Integer aze = c.getTime().getMonth();
	// // System.out.println("----**---> aze: " + aze);
	// // if (d.getYear() == c.getTime().getYear()) {
	// // mois = ((d.getMonth()+1) - aze);
	// //
	// // } else {
	// // int nbryears = (d.getYear() - c.getTime().getYear());
	// // mois = (12 - aze) + ((nbryears - 1) * 12) + (d.getMonth()+1);
	// //
	// // }
	// // int mois = (C.get().getDateFin().getMonth() -
	// // C.get().getDateDebut().getMonth());
	// String Adresse = C.get().getAddress();
	// String Téléphone = C.get().getTelephone();
	// String Responsable = C.get().getResponsable();
	// String Mail = C.get().getMail();
	// try {
	// // Create PdfReader instance.
	// PdfReader pdfReader = new PdfReader("C:\\ESP\\Convention.pdf");
	// // Create PdfStamper instance.
	// PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(
	// "C:\\ESP\\uploadsConventions\\" + idET + "_Convention_" + datenow + ".pdf"));
	// C.get().setPathConvention("C:/ESP/uploadsConventions/" + idET +
	// "_Convention_" + datenow + ".pdf");
	// conventionRepository.save(C.get());
	// // Create BaseFont instance.
	// BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN,
	// BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	// PdfContentByte pageContentByte1 = pdfStamper.getOverContent(1);
	// pageContentByte1.beginText();
	// pageContentByte1.setFontAndSize(baseFont, 11);
	// pageContentByte1.setTextMatrix(100, 688);
	// pageContentByte1.showText(E.get().getDesignation());
	// if (Adresse == null) {
	// pageContentByte1.setTextMatrix(90, 668);
	// pageContentByte1.showText("--");
	//
	// } else {
	// pageContentByte1.setTextMatrix(90, 668);
	// pageContentByte1.showText(Adresse);
	// }
	// if (Téléphone == null) {
	// pageContentByte1.setTextMatrix(100, 655);
	// pageContentByte1.showText("--");
	//
	// } else {
	// pageContentByte1.setTextMatrix(100, 655);
	// pageContentByte1.showText(Téléphone);
	//
	// }
	// if (Responsable == null) {
	// pageContentByte1.setTextMatrix(100, 655);
	// pageContentByte1.showText("--");
	//
	// } else {
	// pageContentByte1.setTextMatrix(130, 642);
	// pageContentByte1.showText(C.get().getResponsable());
	//
	// }
	// if (Mail == null) {
	// pageContentByte1.setTextMatrix(100, 655);
	// pageContentByte1.showText("--");
	//
	// } else {
	// pageContentByte1.setTextMatrix(150, 629);
	// pageContentByte1.showText(C.get().getMail());
	//
	// }
	// pageContentByte1.setTextMatrix(150, 594);
	// pageContentByte1.showText(studentFullName);
	// pageContentByte1.setTextMatrix(270, 574);
	// pageContentByte1.showText(Option);
	// if (Departement == null) {
	// pageContentByte1.setTextMatrix(290, 555);
	// pageContentByte1.showText("--");
	//
	// } else {
	// pageContentByte1.setTextMatrix(290, 555);
	// pageContentByte1.showText(Departement);
	//
	// }
	//
	// pageContentByte1.endText();
	// PdfContentByte pageContentByte2 = pdfStamper.getOverContent(2);
	// pageContentByte2.beginText();
	// pageContentByte2.setFontAndSize(baseFont, 11);
	// pageContentByte2.setTextMatrix(225, 686);
	// pageContentByte2.showText(simpleDateFormat.format(C.get().getDateDebut()));
	// pageContentByte2.setTextMatrix(350, 686);
	// pageContentByte2.showText(simpleDateFormat.format(C.get().getDateFin()));
	// pageContentByte2.setTextMatrix(290, 480);
	// pageContentByte2.showText(datenow.toString());
	// pageContentByte2.endText();
	// PdfContentByte pageContentByte3 = pdfStamper.getOverContent(3);
	// pageContentByte3.beginText();
	// pageContentByte3.setFontAndSize(baseFont, 11);
	// pageContentByte3.setTextMatrix(405, 659);
	// pageContentByte3.showText(datenow.toString());
	// pageContentByte3.setTextMatrix(150, 595);
	// pageContentByte3.showText(E.get().getDesignation());
	// pageContentByte3.setTextMatrix(150, 550);
	// pageContentByte3.showText(studentFullName);
	// if (Departement == null) {
	// pageContentByte3.setTextMatrix(285, 492);
	//
	// pageContentByte3.showText("--");
	//
	// } else {
	// pageContentByte3.setTextMatrix(285, 492);
	// pageContentByte3.showText(Departement);
	//
	// }
	//
	// pageContentByte3.setTextMatrix(150, 457);
	// pageContentByte3.showText(E.get().getDesignation());
	// pageContentByte3.setTextMatrix(410, 457);
	// System.out.println("--------------------> AZERTY: " + String.valueOf(mois) +
	// " - " + mois + " - "
	// + mois.toString());
	// pageContentByte3.showText(String.valueOf(mois));
	// pageContentByte3.setTextMatrix(470, 457);
	// pageContentByte3.showText(simpleDateFormat.format(C.get().getDateDebut()));
	// pageContentByte3.setTextMatrix(100, 425);
	// pageContentByte3.showText(simpleDateFormat.format(C.get().getDateFin()));
	// pageContentByte3.endText();
	// // Close the pdfStamper.
	// pdfStamper.close();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy");
	// String convValidationDate = dateFormata.format(new Date());
	//
	// String content = "Nous voulons vous informer par le présent mail que votre
	// <strong><font color=grey> Demande de Convention </font></strong> a été bien
	// validée par le Responsable des Stages le <font color=red> "
	// + convValidationDate + " </font>.";
	//
	// String conventionPath = "C:\\ESP\\uploadsConventions\\" + idET +
	// "_Convention_" + datenow + ".pdf";
	// String conventionLabel = idET + "_Convention_" + datenow + ".pdf";
	//
	// //String studentMail = utilServices.findStudentMailById(idET); // Server
	// SERVERSE06102DEPLOY_SERVER studentMail = "saria.essid@esprit.tn"; // Local
	//
	// System.out.println("---------------------------> Start");
	// sendMailWithAttachment(studentMail, "Validation de Convention", content,
	// conventionPath, conventionLabel);
	//
	// System.out.println("---------------------------> End Send Mail of Convention:
	// " + utilServices.findStudentMailById(idET));
	// return "PDF modified successfully.";
	//
	// } else
	// return null;
	// }

	public List<String> getOptionForEtudiant(String idEt) {

		List<String> getOptionForEtudiant = new ArrayList<String>();

		List<String> getOptionForEtudiantCJ = etudiantRepository.getOptionForEtudiant(idEt);
		List<String> getOptionForEtudiantCS = etudiantRepositoryCS.getOptionForEtudiantCS(idEt);

		getOptionForEtudiant.addAll(getOptionForEtudiantCJ);
		getOptionForEtudiant.addAll(getOptionForEtudiantCS);

		return getOptionForEtudiant;
	}

	public String GenerateAvenant(String idET, String dateconvention, String dateAvenant) throws Exception {

		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String datenow = simpleDateFormat.format(new Date());
		if (avenantRepository.getAvenant(idET, dateconvention, dateAvenant) != null) {
			Optional<Convention> C = conventionRepository.getConventionById(idET, dateconvention);
			Avenant A = avenantRepository.getAvenant(idET, dateconvention, dateAvenant);

			List<String> los = optionRepository.listOptionsByYear("2021");
			String Option = utilServices.findOptionByStudent(idET, los);
			// oif String Option = etudiantRepository.getOptionForEtudiant(idET).get(0);

			String Departement = utilServices.findDepartmentByStudent(idET);
			// oif List<String> Departement =
			// etudiantRepository.getdepartementForEtudiant(idET);

			Optional<EntrepriseAccueil> E = entrepriseRepository
					.findById(C.get().getEntrepriseAccueilConvention().getIdEntreprise());

			String Adresse = C.get().getAddress();
			String Téléphone = C.get().getTelephone();
			String Responsable = A.getResponsableEntreprise();
			String Mail = C.get().getMail();
			String numSiren = A.getNumSiren();
			try {
				// Create PdfReader instance. C:\\ESP-DOCS\\Avenants
				PdfReader pdfReader = new PdfReader("C:\\ESP-DOCS\\Avenants\\Avenant.pdf");
				// Create PdfStamper instance.
				PdfStamper pdfStamper = new PdfStamper(pdfReader,
						new FileOutputStream("C:\\ESP-DOCS\\Avenants\\" + idET + "_Avenant_" + datenow + ".pdf"));
				// Create BaseFont instance.
				A.setPathAvenant("C:/ESP-DOCS/Avenants/" + idET + "_Avenant_" + datenow + ".pdf");
				avenantRepository.save(A);
				BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				PdfContentByte pageContentByte1 = pdfStamper.getOverContent(1);
				pageContentByte1.beginText();
				pageContentByte1.setFontAndSize(baseFont, 11);
				pageContentByte1.setTextMatrix(120, 560);
				pageContentByte1.showText(E.get().getDesignation());
				if (Adresse == null) {
					pageContentByte1.setTextMatrix(120, 525);
					pageContentByte1.showText("--");

				} else {
					pageContentByte1.setTextMatrix(120, 525);
					pageContentByte1.showText(C.get().getAddress());
				}
				if (Téléphone == null) {
					pageContentByte1.setTextMatrix(150, 500);
					pageContentByte1.showText("--");

				} else {
					pageContentByte1.setTextMatrix(150, 500);
					pageContentByte1.showText(C.get().getTelephone());

				}
				if (Responsable == null) {
					pageContentByte1.setTextMatrix(155, 475);
					pageContentByte1.showText("--");

				} else {
					pageContentByte1.setTextMatrix(155, 475);
					pageContentByte1.showText(A.getResponsableEntreprise());

				}
				if (Mail == null) {
					pageContentByte1.setTextMatrix(162, 450);
					pageContentByte1.showText("--");

				} else {
					pageContentByte1.setTextMatrix(162, 450);
					pageContentByte1.showText(C.get().getMail());

				}
				if (numSiren == null) {
					pageContentByte1.setTextMatrix(160, 425);
					pageContentByte1.showText("--");

				} else {
					pageContentByte1.setTextMatrix(160, 425);
					pageContentByte1.showText(A.getNumSiren());

				}

				pageContentByte1.setTextMatrix(300, 315);
				pageContentByte1.showText(Option);

				if (Departement == null) {
					pageContentByte1.setTextMatrix(305, 285);
					pageContentByte1.showText("--");

				} else {
					pageContentByte1.setTextMatrix(305, 285);
					pageContentByte1.showText(Departement);

				}

				pageContentByte1.endText();
				PdfContentByte pageContentByte2 = pdfStamper.getOverContent(2);
				pageContentByte2.beginText();
				pageContentByte2.setFontAndSize(baseFont, 11);
				pageContentByte2.setTextMatrix(420, 681);
				pageContentByte2.showText(simpleDateFormat.format(A.getDateSignatureConvention()));
				pageContentByte2.setTextMatrix(150, 625);
				pageContentByte2.showText(simpleDateFormat.format(A.getDateDebut()));
				pageContentByte2.setTextMatrix(290, 625);
				pageContentByte2.showText(simpleDateFormat.format(A.getDateFin()));
				pageContentByte2.setTextMatrix(270, 480);
				pageContentByte2.showText(datenow);
				pageContentByte2.endText();
				// Close the pdfStamper.
				pdfStamper.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			DateFormat dateFormata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String aveValidationDate = dateFormata.format(new Date());

			String content = "Nous voulons vous informer par le présent mail que votre <strong><font color=grey> Demande d'Avenant </font></strong> a été bien validée par le Responsable des Stages le <font color=red> "
					+ aveValidationDate + " </font>.";

			String avenantPath = "C:\\ESP-DOCS\\Avenants\\" + idET + "_Avenant_" + datenow + ".pdf";
			String avenantLabel = idET + "_Avenant_" + datenow + ".pdf";

			String studentMail = utilServices.findStudentMailById(idET); // Server DEPLOY_SERVER
			// String studentMail = "saria.essid@esprit.tn"; // Local

			System.out.println("---------------------------> Start");
			utilServices.sendMailWithAttachment(studentMail, "Validation d'Avenant", content, avenantPath,
					avenantLabel);

			System.out.println("---------------------------> End  Send Mail of Avenant: "
					+ utilServices.findStudentMailById(idET));

			return "PDF modified successfully.";

		} else
			return null;

	}

	/*******************
	 * Traitement Depot rapport
	 *************************/

	public DepotRapport UpdateRepotToVALIDE(String idET, String dateFiche) {

		if (fichePFERepository.getFichebyId(idET, dateFiche) != null) {
			FichePFE existingFichePFE = fichePFERepository.getFichebyId(idET, dateFiche);
			existingFichePFE.setValidDepot("02");
			existingFichePFE.setDateTreatReports(new Date());
			// existingFichePFE.setEtatFiche("06");
			fichePFERepository.saveAndFlush(existingFichePFE);

			// Student S =
			// etudiantRepository.getOne(existingFichePFE.getIdFichePFE().getIdEt());
			StudentIdNomPrenomDto S = findStudentIdFullName(
					existingFichePFE.getIdFichePFE().getConventionPK().getIdEt());

			DepotRapport D = new DepotRapport(existingFichePFE.getIdFichePFE().getConventionPK().getIdEt(),
					S.getNomet(), S.getPrenomet(), existingFichePFE.getPathRapportVersion2(),
					existingFichePFE.getPathPlagiat(), existingFichePFE.getPathAttestationStage(),
					existingFichePFE.getPathSupplement(),
					codeNomenclatureRepository.findEtatFiche(existingFichePFE.getEtatFiche()),
					codeNomenclatureRepository.findEtatDepot(existingFichePFE.getValidDepot()),
					existingFichePFE.getIdFichePFE().getDateDepotFiche());

			return D;
		} else
			return null;
	}

	public DepotRapport UpdateRepotToREFUSE(String idET, String dateFiche, String observation) {

		if (fichePFERepository.getFichebyId(idET, dateFiche) != null) {
			FichePFE existingFichePFE = fichePFERepository.getFichebyId(idET, dateFiche);
			existingFichePFE.setValidDepot("03");
			existingFichePFE.setDateTreatReports(new Date());
			existingFichePFE.setObservationDepot(observation);
			fichePFERepository.save(existingFichePFE);

			// Student S =
			// etudiantRepository.getOne(existingFichePFE.getIdFichePFE().getIdEt());
			StudentIdNomPrenomDto S = findStudentIdFullName(
					existingFichePFE.getIdFichePFE().getConventionPK().getIdEt());

			DepotRapport D = new DepotRapport(existingFichePFE.getIdFichePFE().getConventionPK().getIdEt(),
					S.getNomet(), S.getPrenomet(), existingFichePFE.getPathRapportVersion2(),
					existingFichePFE.getPathPlagiat(), existingFichePFE.getPathAttestationStage(),
					existingFichePFE.getPathSupplement(),
					codeNomenclatureRepository.findEtatFiche(existingFichePFE.getEtatFiche()),
					codeNomenclatureRepository.findEtatDepot(existingFichePFE.getValidDepot()),
					existingFichePFE.getIdFichePFE().getDateDepotFiche());

			return D;
		} else
			return null;
	}

	public List<Map<String, String>> getSomeStat(String id) {
		List<Map<String, String>> newList = new ArrayList<>();
		List<Convention> listAllConvention = conventionRepository.getConventionsByServiceStage(id);
		List<Avenant> listAllAvenant = avenantRepository.getConventionsByServiceStage(id);
		List<Convention> listAllConventionTraiter = new ArrayList<>();
		List<Convention> listAllConventionNonTraiter = new ArrayList<>();
		List<Convention> listAllConventionAnnuller = new ArrayList<>();
		List<Avenant> listAllAvenantTraiter = new ArrayList<>();
		List<Avenant> listAllAvenantNonTraiter = new ArrayList<>();
		Map<String, String> combined = new HashMap<>();
		combined.put("nombreAllConvention", String.valueOf(listAllConvention.size()));
		combined.put("nombreAllAvenant", String.valueOf(listAllAvenant.size()));

		for (Convention T : listAllConvention) {
			// non traiter
			if (T.getTraiter().equals("01")) {
				listAllConventionNonTraiter.add(T);
			}
			// traiter
			if (T.getTraiter().equals("02")) {
				listAllConventionTraiter.add(T);
			}
			// Annuller
			if (T.getTraiter().equals("03")) {
				listAllConventionAnnuller.add(T);
			}

		}

		for (Avenant T : listAllAvenant) {
			if (T.getTraiter() == false) {
				listAllAvenantNonTraiter.add(T);
			}
			if (T.getTraiter() == true) {
				listAllAvenantTraiter.add(T);
			}

		}
		combined.put("nombreConventionNontraite", String.valueOf(listAllConventionNonTraiter.size()));
		combined.put("nombreConventionTraite", String.valueOf(listAllConventionTraiter.size()));
		combined.put("nombreConventionAnnuler", String.valueOf(listAllConventionAnnuller.size()));
		combined.put("nombreAvenantTraite", String.valueOf(listAllAvenantTraiter.size()));
		combined.put("nombreAvenantNonTraite", String.valueOf(listAllAvenantNonTraiter.size()));
		newList.add(combined);
		return newList;

	}

	public List<StudentPFEDepasse> getEtudiantPFEDepasse(String id, int nbrMois) {

		List<Convention> listConvention = conventionRepository.getConventionsByServiceStage(id);
		List<StudentPFEDepasse> listStudentPFEDepasse = new ArrayList<StudentPFEDepasse>();
		for (Convention con : listConvention) {

			Date d = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(con.getDateDebut());

			int finStageDays = 168;
			c.add(Calendar.DAY_OF_YEAR, finStageDays);
			int mois = 0;
			// System.out.println("d.getMonth()"+d.getMonth());
			// System.out.println("c.getTime().getMonth()"+c.getTime().getMonth());
			Integer aze = c.getTime().getMonth();
			System.out.println("----**---> aze: " + aze);
			if (d.getYear() == c.getTime().getYear()) {
				mois = ((d.getMonth() + 1) - aze);

			} else {
				int nbryears = (d.getYear() - c.getTime().getYear());
				mois = (12 - aze) + ((nbryears - 1) * 12) + (d.getMonth() + 1);

			}

			System.out.println(
					"--------------**-------------> DIFF: " + (d.getMonth() + 1) + " - " + (c.getTime().getMonth()));
			// }

			// System.out.println("--------------------------------> 0: " +
			// con.getConventionPK().getIdEt());
			List<FichePFE> listFichePFE = fichePFERepository
					.findFichePFEByStudentNotDEPOSEE(con.getConventionPK().getIdEt());
			// System.out.println("nbrMois"+nbrMois);
			System.out.println("--------------**-------------> RESULT: " + con.getConventionPK().getIdEt() + " --> "
					+ listFichePFE.size() + " - " + nbrMois + " | " + mois);
			if (!listFichePFE.isEmpty()) {
				if (!listFichePFE.get(0).getEtatFiche().equals("07") && con.getTraiter().equals("02")
						&& mois >= nbrMois) {
					// System.out.println("-----ezzzzzzzzzzzzzzzzzzzzz------------> 1: ");
					StudentCJ S = etudiantRepository.getOne(con.getConventionPK().getIdEt());
					System.out.println("-----------------> UNIT: " + S.getIdEt());
					StudentPFEDepasse St = new StudentPFEDepasse(con.getConventionPK().getIdEt(), S.getNomet(),
							S.getPrenomet(), S.getAdressemailesp(), S.getEmailet(), S.getAdresseet(), S.getTelet(),
							etudiantRepository.getOptionForEtudiant(S.getIdEt()).get(0),
							codeNomenclatureRepository.findEtatFiche(listFichePFE.get(0).getEtatFiche()),
							con.getConventionPK().getDateConvention(),
							listFichePFE.get(0).getIdFichePFE().getDateDepotFiche(), con.getDateDebut());
					// System.out.println("-----------------> 3: ");
					listStudentPFEDepasse.add(St);
					// System.out.println("-----------------> 4: ");
				}
			}

		}
		return listStudentPFEDepasse;

	}

	public List<StudentForSoutenance> getEtudiantbySession(String id, Integer sessionid) {
		List<StudentForSoutenance> listStudentPFEDepasse = new ArrayList<StudentForSoutenance>();
		List<Convention> listConvention = conventionRepository.getConventionsByServiceStage(id);

		for (Convention con : listConvention) {
			if (con.getTraiter().equals("02")) {
				List<FichePFE> listFichePFE = fichePFERepository
						.findFichePFEByStudentNotDEPOSEE(con.getConventionPK().getIdEt());

				if ((listFichePFE.get(0).getEtatFiche().equals("07") || listFichePFE.get(0).getEtatFiche().equals("06"))
						&& listFichePFE.get(0).getSession().getIdSession() == sessionid) {

					StudentCJ S = etudiantRepository.getOne(con.getConventionPK().getIdEt());

					Teacher t = enseignantRepository.getEncadrantByEtudiant(con.getConventionPK().getIdEt());

					StudentForSoutenance St = new StudentForSoutenance(con.getConventionPK().getIdEt(), S.getNomet(),
							"", S.getPrenomet(), S.getAdressemailesp(), t.getNomEns(),
							codeNomenclatureRepository.findEtatFiche(listFichePFE.get(0).getEtatFiche()),
							listFichePFE.get(0).getPathRapportVersion2());
					listStudentPFEDepasse.add(St);
				}
			}

		}
		return listStudentPFEDepasse;
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

}

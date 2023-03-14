package com.esprit.gdp.services;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.esprit.gdp.dto.DepotAttestationINGDto;
import com.esprit.gdp.dto.DepotBilanDto;
import com.esprit.gdp.dto.DepotGanttDiagramDto;
import com.esprit.gdp.dto.DepotJournalDto;
import com.esprit.gdp.dto.DepotJournalINGDto;
import com.esprit.gdp.dto.DepotRapportDto;
import com.esprit.gdp.dto.DepotRapportINGDto;
import com.esprit.gdp.dto.DepotRapportUrgDto;
import com.esprit.gdp.dto.DepotSignedAvenDto;
import com.esprit.gdp.dto.DepotSignedConvDto;
import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.EvaluationEngineeringTraining;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.repository.AvenantRepository;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.EvaluationEngineeringTrainingRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.ResponsableServiceStageRepository;
import com.esprit.gdp.repository.SalleRepository;
import com.esprit.gdp.repository.SessionRepository;
import com.esprit.gdp.repository.SettingsRepository;
import com.esprit.gdp.repository.TeacherRepository;

@Service
public class StorageServices {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	private final Path rootLocation = Paths.get("C:\\ESP\\uploads\\");
	private final String dbadd = "C:/ESP/uploads/";

	@Autowired
	private FichePFERepository fichePFERepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	EvaluationEngineeringTrainingRepository evaluationEngTrRepository;

	@Autowired
	SettingsRepository settingsRepository;
	
	@Autowired
	ConventionRepository conventionRepository;
	
	@Autowired
	AvenantRepository avenantRepository;
	
	

	private String mailGoogleReception() {
		String gai = settingsRepository.findGoogleAccountId();
		return gai.substring(0, gai.lastIndexOf("@esprit.tn") + 10);
	}

	private String pwdGoogleReception() {
		String gai = settingsRepository.findGoogleAccountId();
		return gai.substring(gai.indexOf("@esprit.tn") + 10, gai.length());
	}

	public void storeReportVersion1(MultipartFile file, String currentUserCode, Boolean checked) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathRapportVersion1(fullPath);
			fichePFE.setDateDepotRapportVersion1(new Date());

			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE FILE 1: " + fullPath);
			fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");
			// Etudiant et = etudiantRepository.findByCode(currentUserCode);
			// et.setDepotRap(depotRap);
			// etudiantRepository.save(et);

			// // System.out.println("------------> DONE SAVE FILE 2");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeReportVersion2(MultipartFile file, String currentUserCode, Boolean checked) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathRapportVersion2(fullPath);
			fichePFE.setDateDepotRapportVersion2(new Date());
			fichePFE.setConfidentiel(checked);

			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE FILE 2: " + fullPath);
			fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");
			// Etudiant et = etudiantRepository.findByCode(currentUserCode);
			// et.setDepotRap(depotRap);
			// etudiantRepository.save(et);

			// // System.out.println("------------> DONE SAVE FILE 2");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeReportVersion2Urgent(MultipartFile file, String currentUserCode, String traineeKind,
			Boolean checked) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);

			fichePFE.setPathRapportVersion2(fullPath);
			fichePFE.setDateDepotRapportVersion2(new Date());
			fichePFE.setConfidentiel(checked);

			com.esprit.gdp.models.Session session = sessionRepository.findByIdSession(1);
			fichePFE.setSession(session);
			fichePFERepository.save(fichePFE);

		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeAttestationStage(MultipartFile file, String currentUserCode, Boolean checked) {
		// System.out.println("----------------------------- SAVE");
		try {
			// System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathAttestationStage(fullPath);
			fichePFE.setDateDepotAttestationStage(new Date());
			fichePFE.setConfidentiel(checked);
			// fichePFE.setValidDepot("01");
			// System.out.println("------------------------------------------------------->
			// H");

			/*
			 * ALERT: Add AE in FichePFE // Modified on 24-08-1988 String
			 * idEncadrantPedagogique = null; List<String> idEncadrantPedagogiqueCJ =
			 * inscriptionRepository.findEncadrantPedagogiqueByStudent(currentUserCode);
			 * List<String> idEncadrantPedagogiqueCS =
			 * inscriptionRepository.findEncadrantPedagogiqueCSByStudent(currentUserCode);
			 * // System.out.
			 * println("-------------------------------------------------------> I");
			 * 
			 * 
			 * if(!idEncadrantPedagogiqueCJ.isEmpty()) { idEncadrantPedagogique =
			 * idEncadrantPedagogiqueCJ.get(0); } if(!idEncadrantPedagogiqueCS.isEmpty()) {
			 * idEncadrantPedagogique = idEncadrantPedagogiqueCS.get(0); } //
			 * findEncadrantPedagogiqueCSByStudent
			 * 
			 * 
			 * Teacher pedagogicalEncadrant =
			 * teacherRepository.findByIdEns(idEncadrantPedagogique);
			 * fichePFE.setPedagogicalEncadrant(pedagogicalEncadrant);
			 */

			// System.out.println("------------------------------------------------------->
			// H DONE SAVE FILE 2: " + fullPath);
			fichePFERepository.save(fichePFE);
			// System.out.println("------------------------------------------------------->
			// I");
			// Etudiant et = etudiantRepository.findByCode(currentUserCode);
			// et.setDepotRap(depotRap);
			// etudiantRepository.save(et);

			// System.out.println("------------> DONE SAVE FILE 2");
		} catch (Exception e) {
			// System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storePlagiat(MultipartFile file, String currentUserCode, Boolean checked) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathPlagiat(fullPath);
			fichePFE.setDateDepotPlagiat(new Date());
			// fichePFE.setValidDepot("01");
			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE FILE 2: " + fullPath);
			fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");
			// Etudiant et = etudiantRepository.findByCode(currentUserCode);
			// et.setDepotRap(depotRap);
			// etudiantRepository.save(et);

			// // System.out.println("------------> DONE SAVE FILE 2");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeSupplement(MultipartFile file, String currentUserCode, Boolean checked) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathSupplement(fullPath);
			fichePFE.setDateDepotSupplement(new Date());
			// fichePFE.setValidDepot("01");
			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE FILE 2: " + fullPath);
			fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");
			// Etudiant et = etudiantRepository.findByCode(currentUserCode);
			// et.setDepotRap(depotRap);
			// etudiantRepository.save(et);

			// // System.out.println("------------> DONE SAVE FILE 2");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeBilanVersion1(MultipartFile file, String currentUserCode) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathBilan1(fullPath);
			fichePFE.setDateDepotBilan1(new Date());

			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE Bilan 1: " + fullPath);
			fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");

			// // System.out.println("------------> DONE SAVE Bilan 1");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeBilanVersion2(MultipartFile file, String currentUserCode) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathBilan2(fullPath);
			fichePFE.setDateDepotBilan2(new Date());

			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE Bilan 2: " + fullPath);
			fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");

			// // System.out.println("------------> DONE SAVE Bilan 2");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeBilanVersion3(MultipartFile file, String currentUserCode) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			fichePFE.setPathBilan3(fullPath);
			fichePFE.setDateDepotBilan3(new Date());

			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE Bilan 3: " + fullPath);
			fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");

			// // System.out.println("------------> DONE SAVE Bilan 3");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeJournalStageING(String fileBase64, String fileName, String currentUserCode)
	{
		System.out.println("----------------------------- SAVE");
		try
		{
			System.out.println("-------------------------------------------------------> A" + currentUserCode);
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			System.out.println("-------------------------------------------------------> B");
			String fileNameLabel = fileName.substring(0,fileName.lastIndexOf("."));
			System.out.println("-------------------------------------------------------> C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			System.out.println("-------------------------------------------------------> D");
			String fullPath = dbadd + formedNameFile;

			byte[] data = DatatypeConverter.parseBase64Binary(fileBase64);
// String path = dbadd + decodedFileName;  // C:/ESP/uploads/"
			File file = new File(fullPath);
			OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			outputStream.write(data);

			System.out.println("----------------------------- SAVE-YES");



			System.out.println("-------------------------------------------------------> E: " + fullPath);




/*
//Path copied = Paths.get("C:\\ESP\\uploads\\" + formedNameFile);  // Paths.get("C:\\ESP\\uploads\\")
//Path originalPath = original.toPath();

// Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
//Files.copy(Paths.get(fullPath), this.rootLocation.resolve(formedNameFile));

//InputStream input = new FileInputStream("C:\\ESP\\uploads\\" + formedNameFile);

//File copied = new File(file);
InputStream in = new BufferedInputStream(new FileInputStream(file));

Files.copy(in, this.rootLocation.resolve(formedNameFile));
//System.out.println("-------------------------------------------------------> F: " + );
*/

			EvaluationEngineeringTraining evaluationStageIngenieur = new EvaluationEngineeringTraining();
			if(evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(currentUserCode) == null)
			{
				evaluationStageIngenieur = new EvaluationEngineeringTraining(currentUserCode);
				evaluationStageIngenieur.setPathJournal(fullPath);
				evaluationStageIngenieur.setDateUploadJournal(new Date());
				evaluationStageIngenieur.setEtatDepot("01");
			}
			else
			{
				evaluationStageIngenieur = evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(currentUserCode);
				evaluationStageIngenieur.setPathJournal(fullPath);
				evaluationStageIngenieur.setDateUploadJournal(new Date());
				evaluationStageIngenieur.setEtatDepot("01");
			}

			System.out.println("-------------------------------------------------------> G");

			System.out.println("-------------------------------------------------------> H DONE SAVE Journal Stage ING File : " + fullPath);
			evaluationEngTrRepository.save(evaluationStageIngenieur);
			System.out.println("-------------------------------------------------------> I");

			System.out.println("------------> DONE SAVE Journal Stage ING File.");
		}
		catch (Exception e)
		{
// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeAttestationStageING(MultipartFile file, String currentUserCode) {
		System.out.println("----------------------------- SAVE");
		try {
			System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			System.out.println("-------------------------------------------------------> A: " + currentUserCode);
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			System.out.println("-------------------------------------------------------> B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			System.out.println("-------------------------------------------------------> C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			System.out.println("-------------------------------------------------------> D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			System.out.println("-------------------------------------------------------> E");
			String fullPath = dbadd + formedNameFile;
			System.out.println("-------------------------------------------------------> F");

			EvaluationEngineeringTraining evaluationStageIngenieur = new EvaluationEngineeringTraining();
			if (evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(currentUserCode) == null) {
				evaluationStageIngenieur = new EvaluationEngineeringTraining(currentUserCode);
				evaluationStageIngenieur.setPathAttestation(fullPath);
				evaluationStageIngenieur.setDateUploadAttestation(new Date());
				evaluationStageIngenieur.setEtatDepot("01");
			} else {
				evaluationStageIngenieur = evaluationEngTrRepository
						.findEvaluationStageIngenieurByStudent(currentUserCode);
				evaluationStageIngenieur.setPathAttestation(fullPath);
				evaluationStageIngenieur.setDateUploadAttestation(new Date());
				evaluationStageIngenieur.setEtatDepot("01");
			}

			System.out.println("-------------------------------------------------------> G");

			System.out.println(
					"-------------------------------------------------------> H DONE SAVE Attestation Stage ING File : "
							+ fullPath);
			evaluationEngTrRepository.save(evaluationStageIngenieur);
			System.out.println("-------------------------------------------------------> I");

			System.out.println("------------> DONE SAVE Attestation Stage ING File.");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeRapportStageING(MultipartFile file, String currentUserCode) {
		System.out.println("----------------------------- SAVE");
		try {
			System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			System.out.println("-------------------------------------------------------> A: " + currentUserCode);
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			System.out.println("-------------------------------------------------------> B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			System.out.println("-------------------------------------------------------> C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			System.out.println("-------------------------------------------------------> D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			System.out.println("-------------------------------------------------------> E");
			String fullPath = dbadd + formedNameFile;
			System.out.println("-------------------------------------------------------> F");

			EvaluationEngineeringTraining evaluationStageIngenieur = new EvaluationEngineeringTraining();
			if (evaluationEngTrRepository.findEvaluationStageIngenieurByStudent(currentUserCode) == null) {
				evaluationStageIngenieur = new EvaluationEngineeringTraining(currentUserCode);
				evaluationStageIngenieur.setPathRapport(fullPath);
				evaluationStageIngenieur.setDateUploadRapport(new Date());
				evaluationStageIngenieur.setEtatDepot("01");
			} else {
				evaluationStageIngenieur = evaluationEngTrRepository
						.findEvaluationStageIngenieurByStudent(currentUserCode);
				evaluationStageIngenieur.setPathRapport(fullPath);
				evaluationStageIngenieur.setDateUploadRapport(new Date());
				evaluationStageIngenieur.setEtatDepot("01");
			}

			System.out.println("-------------------------------------------------------> G");

			System.out.println(
					"-------------------------------------------------------> H DONE SAVE Rapport Stage ING File : "
							+ fullPath);
			evaluationEngTrRepository.save(evaluationStageIngenieur);
			System.out.println("-------------------------------------------------------> I");

			System.out.println("------------> DONE SAVE Attestation Stage ING File.");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeJournal(MultipartFile file, String currentUserCode) {
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			String fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			if (fichePFE.getPathJournalStg() != null) {
				File oldJournal = new File(fichePFE.getPathJournalStg());
				FileSystemUtils.deleteRecursively(oldJournal);
			}
			fichePFE.setPathJournalStg(fullPath);
			fichePFE.setDateDepotJournalStg(new Date());

			// //
			// System.out.println("------------------------------------------------------->
			// H DONE SAVE Journal 3: " + fullPath);
			fichePFERepository.save(fichePFE);

			// //
			// System.out.println("------------------------------------------------------->
			// I");

			// // System.out.println("------------> DONE SAVE Journal 3");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}


	public void storeSignedConvention(MultipartFile file, String currentUserCode)
	{
		// // System.out.println("----------------------------- SAVE");
		try
		{
			// // System.out.println("----------------------------- SAVE-YES");
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// // System.out.println("-------------------------------------------------------> A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// // System.out.println("-------------------------------------------------------> B");
			String fileNameLabel = fileName.substring(0,fileName.lastIndexOf("."));
			// // System.out.println("-------------------------------------------------------> C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// // System.out.println("-------------------------------------------------------> D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// // System.out.println("-------------------------------------------------------> E");
			String fullPath = dbadd + formedNameFile;
			// // System.out.println("-------------------------------------------------------> F");
			
			Convention convention = conventionRepository.findConventionByIdEt(currentUserCode).get(0);	
			// // System.out.println("-------------------------------------------------------> G");
			
			if (convention.getPathSignedConvention() != null)
			{
				File oldSignedConv = new File(convention.getPathSignedConvention());
				FileSystemUtils.deleteRecursively(oldSignedConv);
			}
			convention.setPathSignedConvention(fullPath);
			convention.setDateDepotSignedConvention(new Date());
			
			// // System.out.println("-------------------------------------------------------> H DONE SAVE Journal 3: " + fullPath);
			conventionRepository.save(convention);
			
			
			
			// // System.out.println("-------------------------------------------------------> I");

			// // System.out.println("------------> DONE SAVE Journal 3");
		}
		catch (Exception e)
		{
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeSignedAvenant(MultipartFile file, String currentUserCode)
	{
		// // System.out.println("----------------------------- SAVE");
		try
		{
			// // System.out.println("----------------------------- SAVE-YES");
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// // System.out.println("-------------------------------------------------------> A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// // System.out.println("-------------------------------------------------------> B");
			String fileNameLabel = fileName.substring(0,fileName.lastIndexOf("."));
			// // System.out.println("-------------------------------------------------------> C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// // System.out.println("-------------------------------------------------------> D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// // System.out.println("-------------------------------------------------------> E");
			String fullPath = dbadd + formedNameFile;
			// // System.out.println("-------------------------------------------------------> F");
			
			//Convention convention = conventionRepository.findConventionByIdEt(currentUserCode).get(0);	
			// // System.out.println("-------------------------------------------------------> G");
			Avenant avenant = avenantRepository.findAvenantByIdEt(currentUserCode).get(0);
			if (avenant.getPathSignedAvenant() != null)
			{
				File oldSignedAven = new File(avenant.getPathSignedAvenant());
				FileSystemUtils.deleteRecursively(oldSignedAven);
			}
			avenant.setPathSignedAvenant(fullPath);
			avenant.setDateDepotSignedAvenant(new Date());
			
			// // System.out.println("-------------------------------------------------------> H DONE SAVE Journal 3: " + fullPath);
			avenantRepository.save(avenant);
			
			
			
			// // System.out.println("-------------------------------------------------------> I");

			// // System.out.println("------------> DONE SAVE Journal 3");
		}
		catch (Exception e)
		{
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}
	}
	
	// public void storePlagiat(MultipartFile file, String currentUserCode)
	// {
	// // System.out.println("----------------------------- SAVE");
	// try
	// {
	// // System.out.println("----------------------------- SAVE-YES");
	//
	// String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	//
	// String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
	// String fileNameLabel = fileName.substring(0,fileName.lastIndexOf("."));
	//
	// String formedNameFile = fileNameLabel +
	// "espdsi2020" +
	// new Date().getTime() + fileNameExtention;
	//
	// Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
	//
	// String fullPath = rootLocation + "\\" + formedNameFile;
	//
	//
	// FichePFE fichePFE =
	// fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
	// List<DepotRapport> ldrs =
	// depotRapportRepository.findAllFilesByStudentCodeAndFichePFE(currentUserCode,
	// fichePFE.getIdFichePFE().getDateDepotFiche());
	//
	//// DepotRapportPK depotRapportPK = new DepotRapportPK(currentUserCode,
	// fichePFE.getIdFichePFE().getDateDepotFiche(), new Date());
	//// DepotRapport depotRap = new DepotRapport(depotRapportPK, null, , null,
	// fullPath, false);
	//
	// ldrs.get(0).setNamePlagiat(formedNameFile);
	// ldrs.get(0).setPathPlagiat(fullPath);
	//// ldrs.get(0).setNameRapport(ldrs.get(0).getNameRapport());
	//// ldrs.get(0).setPathRapport(ldrs.get(0).getPathRapport());
	//
	// // System.out.println("------------> DONE SAVE FILE 1: " + fullPath);
	//
	// depotRapportRepository.save(ldrs.get(0));
	//
	//// Etudiant et = etudiantRepository.findByCode(currentUserCode);
	//// et.setDepotRap(depotRap);
	//// etudiantRepository.save(et);
	//
	// // System.out.println("------------> DONE SAVE FILE 2");
	// }
	// catch (Exception e)
	// {
	// // System.out.println("----------------------------- SAVE-NO");
	// throw new RuntimeException("FAIL!");
	// }
	// }

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			// // System.out.println("-------------------- File: " + file.toUri());
			// // System.out.println("-------------------- Resource: " + resource);
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage !...");
		}
	}

	/*******************************************************************************/

	// public DepotRapport getFile(Integer idRap)
	// {
	// return depotRapportRepository.findById(idRap).get();
	// }

	// public void deleteFile(Integer id)
	// {
	// depotRapportRepository.deleteById(id);
	// }

	public Stream<DepotRapportDto> getAllReports(String idEt) {
		List<Object[]> lrs = fichePFERepository.findAllReports(idEt);

		String pathRapportVersion1 = null;
		String pathRapportVersion2 = null;
		String dateDepotRapportVersion1 = null;
		String dateDepotRapportVersion2 = null;
		String pathPlagiat = null;
		Date dateDepotPlagiat = null;
		String pathAttestationStage = null;
		String dateAttestationStage = null;

		for (int i = 0; i < lrs.size(); i++) {
			pathRapportVersion1 = (String) lrs.get(i)[0];
			dateDepotRapportVersion1 = (String) lrs.get(i)[2];
			pathRapportVersion2 = (String) lrs.get(i)[1];
			dateDepotRapportVersion2 = (String) lrs.get(i)[3];
			pathPlagiat = (String) lrs.get(i)[4];
			dateDepotPlagiat = (Date) lrs.get(i)[5];
			pathAttestationStage = (String) lrs.get(i)[6];
			dateAttestationStage = (String) lrs.get(i)[7];
		}

		DepotRapportDto drd = new DepotRapportDto(pathRapportVersion1, dateDepotRapportVersion1, pathRapportVersion2,
				dateDepotRapportVersion2, pathPlagiat, dateDepotPlagiat, pathAttestationStage, dateAttestationStage);
		List<DepotRapportDto> lrds = new ArrayList<DepotRapportDto>();
		lrds.add(drd);
		return lrds.stream();
	}

	public Stream<DepotRapportUrgDto> getAllReportsUrgence(String idEt) {
		List<Object[]> lrs = fichePFERepository.findAllReportsUrg(idEt);

		String pathRapportVersion1 = null;
		String dateDepotRapportVersion1 = null;
		String pathRapportVersion2 = null;
		String dateDepotRapportVersion2 = null;
		String pathPlagiat = null;
		String dateDepotPlagiat = null;
		String pathAttestationStage = null;
		String dateAttestationStage = null;
		String pathSupplementStage = null;
		String dateSupplementStage = null;

		for (int i = 0; i < lrs.size(); i++) {
			pathRapportVersion1 = (String) lrs.get(i)[0];
			dateDepotRapportVersion1 = (String) lrs.get(i)[1];
			pathRapportVersion2 = (String) lrs.get(i)[2];
			dateDepotRapportVersion2 = (String) lrs.get(i)[3];
			pathPlagiat = (String) lrs.get(i)[4];
			dateDepotPlagiat = (String) lrs.get(i)[5];
			pathAttestationStage = (String) lrs.get(i)[6];
			dateAttestationStage = (String) lrs.get(i)[7];
			pathSupplementStage = (String) lrs.get(i)[8];
			dateSupplementStage = (String) lrs.get(i)[9];
		}

		DepotRapportUrgDto drd = new DepotRapportUrgDto(pathRapportVersion1, dateDepotRapportVersion1,
				pathRapportVersion2, dateDepotRapportVersion2, pathPlagiat, dateDepotPlagiat, pathAttestationStage,
				dateAttestationStage, pathSupplementStage, dateSupplementStage);
		List<DepotRapportUrgDto> lrds = new ArrayList<DepotRapportUrgDto>();
		lrds.add(drd);

		for (DepotRapportUrgDto s : lrds) {
			System.out.println("-----------SERV-1----xxxxxxxxx----> UNIT: " + s.getDateDepotRapportVersion1());
			System.out.println("-----------SERV-2---ddddddd-----> UNIT: " + s.getDateDepotRapportVersion2());
		}

		return lrds.stream();
	}

	public Stream<DepotBilanDto> getAllBilans(String idEt) {
		List<Object[]> lbs = fichePFERepository.findAllBilans(idEt);

		String pathBilanVersion1 = null;
		String pathBilanVersion2 = null;
		String pathBilanVersion3 = null;
		String dateDepotBilanVersion1 = null;
		String dateDepotBilanVersion2 = null;
		String dateDepotBilanVersion3 = null;

		for (int i = 0; i < lbs.size(); i++) {
			pathBilanVersion1 = (String) lbs.get(i)[0];
			pathBilanVersion2 = (String) lbs.get(i)[1];
			pathBilanVersion3 = (String) lbs.get(i)[2];
			dateDepotBilanVersion1 = (String) lbs.get(i)[3];
			dateDepotBilanVersion2 = (String) lbs.get(i)[4];
			dateDepotBilanVersion3 = (String) lbs.get(i)[5];
		}

		DepotBilanDto drd = new DepotBilanDto(pathBilanVersion1, pathBilanVersion2, pathBilanVersion3,
				dateDepotBilanVersion1, dateDepotBilanVersion2, dateDepotBilanVersion3);
		List<DepotBilanDto> lbds = new ArrayList<DepotBilanDto>();
		lbds.add(drd);
		return lbds.stream();
	}
	

	public Stream<DepotSignedConvDto> getSignedConv(String idEt)
	{
		List<Object[]> ljs = conventionRepository.findSignedConvention(idEt);
		
		String pathSignedConv = null;
		String dateDepotSignedConv = null;
		
		
		for(int i = 0; i<ljs.size(); i++)
		{
			pathSignedConv = (String) ljs.get(i)[0];
			dateDepotSignedConv = (String) ljs.get(i)[1];
		}
		
		DepotSignedConvDto djd = new DepotSignedConvDto(pathSignedConv, dateDepotSignedConv);
		List<DepotSignedConvDto> ljds = new ArrayList<DepotSignedConvDto>();
		ljds.add(djd);
	    return ljds.stream();
	}

	public Stream<DepotSignedAvenDto> getSignedAven(String idEt)
	{
		List<Object[]> ljs = avenantRepository.findSignedAvenant(idEt);
		
		String pathSignedAven = null;
		String dateDepotSignedAven = null;
		
		
		for(int i = 0; i<ljs.size(); i++)
		{
			pathSignedAven = (String) ljs.get(i)[0];
			dateDepotSignedAven = (String) ljs.get(i)[1];
		}
		
		DepotSignedAvenDto djd = new DepotSignedAvenDto(pathSignedAven, dateDepotSignedAven);
		List<DepotSignedAvenDto> ljds = new ArrayList<DepotSignedAvenDto>();
		ljds.add(djd);
	    return ljds.stream();
	}
	

	public Stream<DepotJournalDto> getJournal(String idEt) {
		List<Object[]> ljs = fichePFERepository.findJournal(idEt);

		String pathJournal = null;
		String dateDepotJournal = null;

		for (int i = 0; i < ljs.size(); i++) {
			pathJournal = (String) ljs.get(i)[0];
			dateDepotJournal = (String) ljs.get(i)[1];
		}

		DepotJournalDto djd = new DepotJournalDto(pathJournal, dateDepotJournal);
		List<DepotJournalDto> ljds = new ArrayList<DepotJournalDto>();
		ljds.add(djd);
		return ljds.stream();
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
			String header = "Bonjour,<br/><br/>";

			String footer = "<br/><br/>Bonne r&eacute;ception.<br/><br/>------------------------------------<br/>"
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

	public Stream<DepotGanttDiagramDto> getGanttDiagram(String idEt) {
		List<String> lgds = fichePFERepository.findGanttDiagramms(idEt);

		DepotGanttDiagramDto dgdd = new DepotGanttDiagramDto(lgds.get(0));
		List<DepotGanttDiagramDto> lgdds = new ArrayList<DepotGanttDiagramDto>();
		lgdds.add(dgdd);
		return lgdds.stream();
	}

	public String storeGanttDiagram(MultipartFile file, String currentUserCode) {
		String fullPath = null;
		// // System.out.println("----------------------------- SAVE");
		try {
			// // System.out.println("----------------------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// //
			// System.out.println("------------------------------------------------------->
			// A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			// //
			// System.out.println("------------------------------------------------------->
			// C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			// //
			// System.out.println("------------------------------------------------------->
			// D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			// //
			// System.out.println("------------------------------------------------------->
			// E");
			fullPath = dbadd + formedNameFile;
			// //
			// System.out.println("------------------------------------------------------->
			// F");

			// FichePFE fichePFE =
			// fichePFERepository.findFichePFEByStudent(currentUserCode).get(0);
			// //
			// System.out.println("------------------------------------------------------->
			// G");

			// fichePFE.setPathDiagrammeGantt(fullPath);
			// fichePFE.setDateDepotGanttDiagram(new Date());
			// fichePFERepository.save(fichePFE);
			// //
			// System.out.println("------------------------------------------------------->
			// I");
			// Etudiant et = etudiantRepository.findByCode(currentUserCode);
			// et.setDepotRap(depotRap);
			// etudiantRepository.save(et);

			// // System.out.println("------------> DONE SAVE FILE 2");
		} catch (Exception e) {
			// // System.out.println("----------------------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}

		return fullPath;

	}

	public String storeCAncellationAgreement(MultipartFile file) {
		String fullPath = null;
		System.out.println("-------------CA---------------- SAVE");
		try {
			System.out.println("-------------CA---------------- SAVE-YES");

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			System.out.println("-------------CA------------------------------------------> A");
			String fileNameExtention = fileName.substring(fileName.lastIndexOf("."));
			System.out.println("-------------CA------------------------------------------> B");
			String fileNameLabel = fileName.substring(0, fileName.lastIndexOf("."));
			System.out.println("-------------CA------------------------------------------> C");
			String formedNameFile = fileNameLabel + "espdsi2020" + new Date().getTime() + fileNameExtention;
			System.out.println("-------------CA------------------------------------------> D");
			Files.copy(file.getInputStream(), this.rootLocation.resolve(formedNameFile));
			System.out.println("-------------CA------------------------------------------> E");
			fullPath = dbadd + formedNameFile;

			System.out.println("------CA------> DONE SAVE FILE 2");
		} catch (Exception e) {
			System.out.println("-------------CA---------------- SAVE-NO");
			throw new RuntimeException("FAIL!");
		}

		return fullPath;

	}

	public Stream<DepotRapportINGDto> getRapportStageINGDto(String idEt) {

		List<Object[]> lesis = evaluationEngTrRepository.findRapportStageING(idEt);

		String pathEvaluationStageING = null;
		String dateDepotEvaluationStageING = null;

		for (int i = 0; i < lesis.size(); i++) {
			pathEvaluationStageING = (String) lesis.get(i)[0];
			dateDepotEvaluationStageING = (String) lesis.get(i)[1];
		}

		System.out.println("-------------------------------------------> AZERTY: " + pathEvaluationStageING + " "
				+ dateDepotEvaluationStageING);

		DepotRapportINGDto desid = new DepotRapportINGDto(pathEvaluationStageING, dateDepotEvaluationStageING);
		List<DepotRapportINGDto> lesids = new ArrayList<DepotRapportINGDto>();
		lesids.add(desid);
		return lesids.stream();
	}

	public Stream<DepotAttestationINGDto> getAttestationStageINGDto(String idEt) {

		List<Object[]> lesis = evaluationEngTrRepository.findAttestationStageING(idEt);

		String pathEvaluationStageING = null;
		String dateDepotEvaluationStageING = null;

		for (int i = 0; i < lesis.size(); i++) {
			pathEvaluationStageING = (String) lesis.get(i)[0];
			dateDepotEvaluationStageING = (String) lesis.get(i)[1];
		}

		System.out.println("-------------------------------------------> AZERTY: " + pathEvaluationStageING + " "
				+ dateDepotEvaluationStageING);

		DepotAttestationINGDto desid = new DepotAttestationINGDto(pathEvaluationStageING, dateDepotEvaluationStageING);
		List<DepotAttestationINGDto> lesids = new ArrayList<DepotAttestationINGDto>();
		lesids.add(desid);
		return lesids.stream();
	}

	public Stream<DepotJournalINGDto> getJournalStageINGDto(String idEt) {

		List<Object[]> lesis = evaluationEngTrRepository.findJournalStageING(idEt);

		String pathEvaluationStageING = null;
		String dateDepotEvaluationStageING = null;

		for (int i = 0; i < lesis.size(); i++) {
			pathEvaluationStageING = (String) lesis.get(i)[0];
			dateDepotEvaluationStageING = (String) lesis.get(i)[1];
		}

		System.out.println("-------------------------------------------> AZERTY: " + pathEvaluationStageING + " "
				+ dateDepotEvaluationStageING);

		DepotJournalINGDto desid = new DepotJournalINGDto(pathEvaluationStageING, dateDepotEvaluationStageING);
		List<DepotJournalINGDto> lesids = new ArrayList<DepotJournalINGDto>();
		lesids.add(desid);
		return lesids.stream();
	}

}
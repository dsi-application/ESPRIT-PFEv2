package com.esprit.gdp.services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.gdp.dto.FicheETDetails;
import com.esprit.gdp.dto.StudentDetails;
import com.esprit.gdp.dto.StudentExcelDto;
import com.esprit.gdp.dto.TeacherValidation;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.TraitementFichePFE;
import com.esprit.gdp.repository.CodeNomenclatureRepository;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.StudentCSRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.repository.TraitementFicheRepository;

@Service
public class ResponsableStageService {

	@Autowired
	private InscriptionRepository inscriptionRepository;

	@Autowired
	private ConventionRepository conventionRepository;

	@Autowired
	private FichePFERepository fichePFERepository;

	@Autowired
	private TraitementFicheRepository tRT_FicheRepository;

	@Autowired
	private StudentRepository etudiantRepository;

	@Autowired
	private StudentCSRepository etudiantCSRepository;

	@Autowired
	TeacherRepository enseignantRepository;

	@Autowired
	CodeNomenclatureRepository codeNomenclatureRepository;

	@Autowired
	private UtilServices utilServices;

	public List<Object[]> getAllTerminalStudent(String Option) {
		List<Object[]> allTerminalStudent = new ArrayList<Object[]>();

		List<Object[]> AllTerminalStudentCJ = etudiantRepository.getEtudiantTerminal(Option);
		List<Object[]> AllTerminalStudentCS = etudiantCSRepository.getEtudiantTerminalCS(Option);

		// System.out.println("----------> SARS 0.1: " + AllTerminalStudentCJ.size());

		// System.out.println("----------> SARS 0.2: " + AllTerminalStudentCS.size());

		allTerminalStudent.addAll(AllTerminalStudentCJ);
		allTerminalStudent.addAll(AllTerminalStudentCS);

		return allTerminalStudent;
	}

	public List<Object[]> getAllTerminalStudentLOwer(String Option) {
		List<Object[]> allTerminalStudent = new ArrayList<Object[]>();

		System.out.println("----------> Option: " + Option);

		List<Object[]> AllTerminalStudentCJ = etudiantRepository.getEtudiantTerminalLowerCJ(Option);
		List<Object[]> AllTerminalStudentCS = etudiantRepository.getEtudiantTerminalLowerCS(Option);

		System.out.println("----------> SARS 0.1: " + AllTerminalStudentCJ.size());

		System.out.println("----------> SARS 0.2: " + AllTerminalStudentCS.size());

		allTerminalStudent.addAll(AllTerminalStudentCJ);
		allTerminalStudent.addAll(AllTerminalStudentCS);

		return allTerminalStudent;
	}

	public List<Object[]> getAllTerminalStudent2020(String Option) {
		List<Object[]> allTerminalStudent = new ArrayList<Object[]>();

		List<Object[]> AllTerminalStudentCJ = etudiantRepository.getEtudiantTerminal2020(Option);
		List<Object[]> AllTerminalStudentCS = etudiantCSRepository.getEtudiantTerminalCS2020(Option);

		System.out.println("----------> SARS 0.1: " + AllTerminalStudentCJ.size());

		System.out.println("----------> SARS 0.2: " + AllTerminalStudentCS.size());

		allTerminalStudent.addAll(AllTerminalStudentCJ);
		allTerminalStudent.addAll(AllTerminalStudentCS);

		return allTerminalStudent;
	}

	public List<Object[]> getAllTerminalStudentNew(String Option) {
		List<Object[]> allTerminalStudent = new ArrayList<Object[]>();

		List<Object[]> AllTerminalStudentCJ = etudiantRepository.getEtudiantTerminalNewCJ(Option);
		List<Object[]> AllTerminalStudentCS = etudiantRepository.getEtudiantTerminalNewCS(Option);

		allTerminalStudent.addAll(AllTerminalStudentCJ);
		allTerminalStudent.addAll(AllTerminalStudentCS);

		return allTerminalStudent;
	}

	public List<FichePFE> getFicheListbyOption(List<String> codeOption) {
		List<Object[]> AllTerminalStudent = new ArrayList<Object[]>();
		for (String C : codeOption) {

			String Option = C.replaceFirst("_[0-9][0-9]", "");

			// System.out.println("--------------------------> SARS 1: " + Option);
			AllTerminalStudent.addAll(getAllTerminalStudentLOwer(Option.toLowerCase()));

		}
		List<FichePFE> AllFiche = new ArrayList<FichePFE>();
		List<FichePFE> AllFichebyEtudiant = new ArrayList<FichePFE>();
		for (int i = 0; i < AllTerminalStudent.size(); i++) {
			AllFichebyEtudiant = fichePFERepository.findFichePFEByStudentPFE((String) AllTerminalStudent.get(i)[0]);
			for (FichePFE F : AllFichebyEtudiant) {
				AllFiche.add(F);

			}

		}
		return AllFiche;

	}

	public List<String> getOptionForEtudiant(String idEt) {

		List<String> getOptionForEtudiant = new ArrayList<String>();

		List<String> getOptionForEtudiantCJ = etudiantRepository.getOptionForEtudiant(idEt);
		// System.out.println("------------------> CDJ: " + );
		List<String> getOptionForEtudiantCS = etudiantCSRepository.getOptionForEtudiantCS(idEt);

		getOptionForEtudiant.addAll(getOptionForEtudiantCJ);
		getOptionForEtudiant.addAll(getOptionForEtudiantCS);

		return getOptionForEtudiant;
	}

	public List<String> findCurrentAnnneInscriptiobByIdEt(String idEt) {
		List<String> findCurrentAnnneInscriptiobByIdEt = new ArrayList<String>();

		List<String> findCurrentAnnneInscriptiobByIdEtCJ = inscriptionRepository
				.findCurrentAnnneInscriptiobByIdEt(idEt);
		List<String> findCurrentAnnneInscriptiobByIdEtCS = inscriptionRepository
				.findCurrentAnnneInscriptiobCSByIdEt(idEt);

		findCurrentAnnneInscriptiobByIdEt.addAll(findCurrentAnnneInscriptiobByIdEtCJ);
		findCurrentAnnneInscriptiobByIdEt.addAll(findCurrentAnnneInscriptiobByIdEtCS);

		Collections.sort(findCurrentAnnneInscriptiobByIdEt, Collections.reverseOrder());

		return findCurrentAnnneInscriptiobByIdEt;
	}

	public List<Map<String, String>> getFicheListbyOptionbyAnnee(String codeOption, String annee)
			throws ParseException {
		List<Map<String, String>> newList = new ArrayList<>();
		String Option = codeOption.replaceAll("_[0-9][0-9]", "");

		List<Object[]> AllTerminalStudent = getAllTerminalStudentLOwer(Option.toLowerCase()); // getAllTerminalStudent(Option);
																								// //etudiantRepository.getEtudiantTerminal(Option);
																								// //1034-1167
																								// getAllTerminalStudent(Option);
																								// //
		System.out.println("------------------> 30-08: " + AllTerminalStudent.size());

		List<Object[]> AllFichebyEtudiant = new ArrayList<>();
		for (int i = 0; i < AllTerminalStudent.size(); i++) {
			// System.out.println("-----------> year-student: " + annee + " - " + (String)
			// AllTerminalStudent.get(i)[1]);

			if (annee.length() == 0) {
				AllFichebyEtudiant = fichePFERepository
						.findFichePFEByStudentNotDEPOSEEbyannee2((String) AllTerminalStudent.get(i)[0]);
				// System.out.println("---> 1: " + AllFichebyEtudiant.size());
			} else {
				AllFichebyEtudiant = fichePFERepository
						.findFichePFEByStudentbyAnnee((String) AllTerminalStudent.get(i)[0], annee);
				System.out.println("---> 2: " + AllFichebyEtudiant.size());
			}

			for (int j = 0; j < AllFichebyEtudiant.size(); j++) {

				// System.out.println("***********************************> " + (String)
				// AllTerminalStudent.get(i)[1]);

				Map<String, String> combined = new HashMap<>();
				combined.put("nom", (String) AllTerminalStudent.get(i)[1]);
				combined.put("prenom", (String) AllTerminalStudent.get(i)[2]);
				combined.put("idEt", (String) AllTerminalStudent.get(i)[0]);
				combined.put("email", (String) AllTerminalStudent.get(i)[3]);

				// oif combined.put("classe", etudiantRepository.getOptionForEtudiant((String)
				// AllTerminalStudent.get(i)[0]).get(0));
				combined.put("classe", getOptionForEtudiant((String) AllTerminalStudent.get(i)[0]).get(0));

				int valeur = Integer
						.parseInt(findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]).get(0)) + 1;

				combined.put("anneeValueDeb",
						findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]).get(0));

				combined.put("anneeDeb", findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]).get(0)
						+ "-" + String.valueOf(valeur));
				Date date = (Date) AllFichebyEtudiant.get(j)[0];
				SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
				Date datee = DateFor.parse(DateFor.format(date));

				String Etat = codeNomenclatureRepository.findEtatFiche((String) AllFichebyEtudiant.get(j)[1]);

				combined.put("etatFiche", Etat);
				combined.put("dateFiche", DateFor.format(date));
				newList.add(combined);

			}

		}

		return newList;

	}

	public List<String> getEtudiantEncadrésbyAnnee(String idEns, String annee) {

		List<String> getEtudiantEncadrésbyAnnee = new ArrayList<String>();

		List<String> getEtudiantEncadrésbyAnneeCJ = etudiantRepository.getEtudiantEncadrésbyAnnee(idEns, annee);
		List<String> getEtudiantEncadrésbyAnneeCS = etudiantCSRepository.getEtudiantEncadrésbyAnneeCS(idEns, annee);

		getEtudiantEncadrésbyAnnee.addAll(getEtudiantEncadrésbyAnneeCJ);
		getEtudiantEncadrésbyAnnee.addAll(getEtudiantEncadrésbyAnneeCS);

		return getEtudiantEncadrésbyAnnee;
	}

	public List<TeacherValidation> getTeacherWithNumberEncad(String annee) {
		System.out.println(new Date());
		List<Object[]> AllTeacher = enseignantRepository.getTeachers(annee);
		List<TeacherValidation> newListTeacherValidation = new ArrayList<>();

		for (int i = 0; i < AllTeacher.size(); i++) {
			List<String> AllEncadeStudent = getEtudiantEncadrésbyAnnee((String) AllTeacher.get(i)[0], annee);
			// oif List<String> AllEncadeStudent =
			// etudiantRepository.getEtudiantEncadrésbyAnnee( (String) AllTeacher.get(i)[0],
			// annee);

			System.out.println(AllEncadeStudent.size());
			TeacherValidation newT = new TeacherValidation((String) AllTeacher.get(i)[0], (String) AllTeacher.get(i)[1],
					AllEncadeStudent.size());
			newListTeacherValidation.add(newT);
		}
		System.out.println("fin");
		System.out.println(new Date());
		return newListTeacherValidation;

	}

	public List<StudentDetails> getStudentSansFichebyOption(String codeOption) {
		System.out.println("// ----------------> HomeManager 2.1: " + codeOption);

		String Option = codeOption.replaceAll("_[0-9][0-9]", "");

		// List<Object[]> AllTerminalStudent = getAllTerminalStudent2020(Option); // ME
		// oif etudiantRepository.getAllTerminalStudent
		List<Object[]> AllTerminalStudent = getAllTerminalStudentNew(Option);

		System.out.println("// ----------------> HomeManager 2.2: " + AllTerminalStudent.size());
		List<StudentDetails> NewEtudiantList = new ArrayList<StudentDetails>();
		List<String> AllFichebyEtudiant = new ArrayList<String>();
		for (int i = 0; i < AllTerminalStudent.size(); i++) {
			AllFichebyEtudiant = fichePFERepository
					.findFichePFEByStudentNotDEPOSEEsize((String) AllTerminalStudent.get(i)[0]);

			// System.out.println("-> 2.3: " + i + " - " + AllFichebyEtudiant.size());
			if (AllFichebyEtudiant.size() == 0) {
				// oif int valeur = Integer.parseInt(
				// inscriptionRepository.findCurrentAnnneInscriptiobByIdEt((String)
				// AllTerminalStudent.get(i)[0]).get(0)) + 1;
				int valeur = Integer
						.parseInt(findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]).get(0)) + 1;
				// System.out.println("valeur========= List:
				// "+findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]));
				// System.out.println("valeur=========Year: "+ valeur);
				// oif StudentDetails SNF = new StudentDetails((String)
				// AllTerminalStudent.get(i)[0],(String) AllTerminalStudent.get(i)[1], (String)
				// AllTerminalStudent.get(i)[2],(String) AllTerminalStudent.get(i)[3],
				// oif (String) AllTerminalStudent.get(i)[4],
				// etudiantRepository.getOptionForEtudiant((String)
				// AllTerminalStudent.get(i)[0]).get(0),
				// oif inscriptionRepository.findCurrentAnnneInscriptiobByIdEt((String)
				// AllTerminalStudent.get(i)[0]).get(0) +" - " + String.valueOf(valeur));

				// System.out.println("size====BEFORE=====UNIT: " + i + " - " + (String)
				// AllTerminalStudent.get(i)[0] + " - " + NewEtudiantList.size());

				// System.out.println("--------------sdfb-----------> 2: " +
				// AllTerminalStudent.get(i)[0]);
				// System.out.println("-------------------------> 3: " +
				// AllTerminalStudent.get(i)[1]);
				// System.out.println("-------------------------> 4: " +
				// AllTerminalStudent.get(i)[2]);
				// System.out.println("-------------------------> 5: " +
				// AllTerminalStudent.get(i)[3]);
				// System.out.println("-------------------------> 6: " +
				// AllTerminalStudent.get(i)[4]);
				// System.out.println("--------------d-----------> 1: " +
				// getOptionForEtudiant((String) AllTerminalStudent.get(i)[0]).get(0));

				StudentDetails SNF = new StudentDetails((String) AllTerminalStudent.get(i)[0],
						(String) AllTerminalStudent.get(i)[1], (String) AllTerminalStudent.get(i)[2],
						(String) AllTerminalStudent.get(i)[3], (String) AllTerminalStudent.get(i)[4],
						getOptionForEtudiant((String) AllTerminalStudent.get(i)[0]).get(0),
						findCurrentAnnneInscriptiobByIdEt((String) AllTerminalStudent.get(i)[0]).get(0) + " - "
								+ String.valueOf(valeur));

				// System.out.println("size====BEFORE=====UNIT: " + i + " - " + (String)
				// AllTerminalStudent.get(i)[0] + " - " + NewEtudiantList.size());
				// System.out.println("size=========Class: " + getOptionForEtudiant((String)
				// AllTerminalStudent.get(i)[0]).get(0));
				NewEtudiantList.add(SNF);
				// System.out.println("size====AFTER=====UNIT: " + NewEtudiantList.size());
			}
			// System.out.println("size=========Size: "+NewEtudiantList.size());
		}
		// System.out.println("// END----------------> HomeManager 2.4");
		return NewEtudiantList;

	}

	public List<TraitementFichePFE> getTraitementFichePFEList(String codeOption) {
		String Option = codeOption.replaceAll("_[0-9][0-9]", "");

		// oif List<Object[]> AllTerminalStudent =
		// etudiantRepository.getEtudiantTerminal(Option);
		List<Object[]> AllTerminalStudent = getAllTerminalStudentLOwer(Option.toLowerCase());

		List<TraitementFichePFE> AllTraitementFichePFE = new ArrayList<TraitementFichePFE>();
		List<TraitementFichePFE> listTraitementFichePFE = new ArrayList<TraitementFichePFE>();

		for (int i = 0; i < AllTerminalStudent.size(); i++) {

			listTraitementFichePFE = tRT_FicheRepository
					.getTraitementFichesByEtudiant((String) AllTerminalStudent.get(i)[0]);

			for (TraitementFichePFE T : listTraitementFichePFE) {
				AllTraitementFichePFE.add(T);

			}
		}

		return AllTraitementFichePFE;
	}

	public FicheETDetails UpdateFicheToVALIDEE(String idET, String dateFiche) {

		if (fichePFERepository.getFichebyId(idET, dateFiche) != null) {
			FichePFE existingFichePFE = fichePFERepository.getFichebyId(idET, dateFiche);
			existingFichePFE.setEtatFiche("03");
			fichePFERepository.save(existingFichePFE);
			StudentExcelDto S = utilServices
					.findStudentExcelDtoById(existingFichePFE.getIdFichePFE().getConventionPK().getIdEt());
			return new FicheETDetails(existingFichePFE.getIdFichePFE().getConventionPK().getIdEt(), S.getNom(),
					S.getPrenom(), existingFichePFE);

		} else
			return null;
	}

	public FicheETDetails UpdateFicheToREFUSEE(String idET, String dateFiche) {

		if (fichePFERepository.getFichebyId(idET, dateFiche) != null) {
			FichePFE existingFichePFE = fichePFERepository.getFichebyId(idET, dateFiche);
			existingFichePFE.setEtatFiche("04");
			fichePFERepository.save(existingFichePFE);
			StudentExcelDto S = utilServices
					.findStudentExcelDtoById(existingFichePFE.getIdFichePFE().getConventionPK().getIdEt());
			return new FicheETDetails(existingFichePFE.getIdFichePFE().getConventionPK().getIdEt(), S.getNom(),
					S.getPrenom(), existingFichePFE);

		} else
			return null;
	}

	public FichePFE UpdateFicheToDEPOSEE(String idET, String dateFiche) {

		// FichePFE fichePFE = fichePFERepository.findFichePFEByStudent(idET).get(0);
		// System.out.println("-----------------h---------> UpdateFicheToDEPOSEE: " +
		// dateFiche + " - " + fichePFE.getIdFichePFE().getDateDepotFiche());
		//

		if (fichePFERepository.getFichebyId(idET, dateFiche) != null) {
			FichePFE existingFichePFE = fichePFERepository.getFichebyId(idET, dateFiche);
			existingFichePFE.setEtatFiche("01");
			System.out.println("-----------------h--------->  OK: " + dateFiche);

			return fichePFERepository.save(existingFichePFE);
		} else
			return null;
	}

	public FichePFE UpdateFicheToANNULE(String idET, String dateFiche) {

		if (fichePFERepository.getFichebyId(idET, dateFiche) != null) {
			FichePFE existingFichePFE = fichePFERepository.getFichebyId(idET, dateFiche);
			existingFichePFE.setEtatFiche("05");

			Convention convention = conventionRepository.findConventionByIdEt(idET).get(0);
			convention.setTraiter("03");
			conventionRepository.save(convention);

			System.out.println("HEEEEEEEERE: " + convention.getTraiter());

			return fichePFERepository.save(existingFichePFE);
		} else
			return null;
	}

	public TraitementFichePFE UpdateTraitementFichePFE(String idET, String dateDepotFiche, String dateTrtFiche) {

		// System.out.println("----------------------> TRT 0439 1: " + );
		// System.out.println("----------------------> TRT 0439 1: " + );

		System.out.println("----------------------> TRT 0439 1: "
				+ tRT_FicheRepository.getTraitementFichePFEById(idET, dateDepotFiche, dateTrtFiche));

		if (tRT_FicheRepository.getTraitementFichePFEById(idET, dateDepotFiche, dateTrtFiche).isPresent()) {
			TraitementFichePFE existingTraitementFichePFE = tRT_FicheRepository
					.getTraitementFichePFEById(idET, dateDepotFiche, dateTrtFiche).get();

			System.out.println("----------------------> TRT 0439 2: " + existingTraitementFichePFE.getEtatTrt());

			existingTraitementFichePFE.setEtatTrt("02");
			return tRT_FicheRepository.save(existingTraitementFichePFE);
		} else
			return null;
	}

	private String decode(String value) throws UnsupportedEncodingException {
		return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
	}

	public List<Map<String, String>> getSomeStat(String codeOption, List<String> strings) throws ParseException {
		System.out.println("// ----------------> HomeManager 2");
		List<Map<String, String>> newList = new ArrayList<>();
		String Option = codeOption.replaceAll("_[0-9][0-9]", "");
		List<StudentDetails> StudentSansFichebyOption = getStudentSansFichebyOption(codeOption);
		System.out.println("// ----------------> HomeManager 3: " + StudentSansFichebyOption.size());
		List<FichePFE> AllFichebyEtudiant = getFicheListbyOption(strings);
		System.out.println("// ----------------> HomeManager 4: " + AllFichebyEtudiant.size());
		List<Object[]> AllTerminalStudent = getAllTerminalStudent(Option);
		// oif List<Object[]> AllTerminalStudent =
		// etudiantRepository.getEtudiantTerminal(Option);
		System.out.println("// ----------------> HomeManager 5: " + AllTerminalStudent.size());
		List<TraitementFichePFE> AllTraitementFichePFEList = getTraitementFichePFEList(codeOption);
		System.out.println("// ----------------> HomeManager 6: " + AllTraitementFichePFEList.size());
		List<TraitementFichePFE> AllAnnuleList = new ArrayList<>();
		List<TraitementFichePFE> AllModifList = new ArrayList<>();
		List<FichePFE> AllFichePFENontraite = new ArrayList<FichePFE>();
		Map<String, String> combined = new HashMap<>();
		combined.put("nombreAllFiche", String.valueOf(AllFichebyEtudiant.size()));
		combined.put("nombreEtudiantSansFiche", String.valueOf(StudentSansFichebyOption.size()));

		System.out.println("---------------------// ----------------> HomeManager 7 - nombreAllFiche: "
				+ AllFichebyEtudiant.size());
		System.out.println("---------------------// ----------------> HomeManager 7 - nombreEtudiantSansFiche: "
				+ StudentSansFichebyOption.size());

		for (int i = 0; i < AllTerminalStudent.size(); i++) {
			// System.out.println("// ----------------> HomeManager 7: " + (String)
			// AllTerminalStudent.get(i)[0]);
			AllFichebyEtudiant = fichePFERepository
					.findFichePFEByStudentNotDEPOSEE((String) AllTerminalStudent.get(i)[0]);
			// System.out.println("// ----------------> HomeManager 8: " +
			// AllFichebyEtudiant.size());
			for (FichePFE F : AllFichebyEtudiant) {
				if (F.getEtatFiche().equals("02")) {
					AllFichePFENontraite.add(F);
				}

			}
		}
		for (TraitementFichePFE T : AllTraitementFichePFEList) {
			if (T.getTypeTrtFiche().equals("02")) {
				AllAnnuleList.add(T);
			}
			if (T.getTypeTrtFiche().equals("01")) {
				AllModifList.add(T);
			}

		}

		System.out.println("---------------------// ----------------> HomeManager 7 - nombreFicheNontraite: "
				+ AllFichePFENontraite.size());
		System.out.println("---------------------// ----------------> HomeManager 7 - nombreDemandeAnnulation: "
				+ AllAnnuleList.size());
		System.out.println("---------------------// ----------------> HomeManager 7 - nombreDemandeModification: "
				+ AllModifList.size());

		newList.add(combined);

		return newList;

	}
}

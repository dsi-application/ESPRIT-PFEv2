package com.esprit.gdp.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.gdp.dto.StudentDetails;
import com.esprit.gdp.dto.StudentDto;
import com.esprit.gdp.dto.StudentForSoutenance;
import com.esprit.gdp.dto.TeacherDetailsDto;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.EvaluationStage;
import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.InscriptionCJ;
import com.esprit.gdp.models.RdvSuiviStage;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.StudentCS;
import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.models.VisiteStagiaire;
import com.esprit.gdp.repository.ConventionRepository;
import com.esprit.gdp.repository.EvaluationRepository;
import com.esprit.gdp.repository.FichePFERepository;
import com.esprit.gdp.repository.InscriptionRepository;
import com.esprit.gdp.repository.RDVRepository;
import com.esprit.gdp.repository.StudentCSRepository;
import com.esprit.gdp.repository.StudentRepository;
import com.esprit.gdp.repository.TeacherRepository;
import com.esprit.gdp.repository.VisiteStagiareRepository;

@Service
public class EncadrementPFEService {

	@Autowired
	private FichePFERepository fichePFERepository;

	@Autowired
	private StudentRepository etudiantRepository;
	
	@Autowired
	private StudentCSRepository etudiantRepositoryCS;

	@Autowired
	private EvaluationRepository evaluationRepository;

	@Autowired
	private InscriptionRepository inscriptionRepository;
	
	@Autowired
	private VisiteStagiareRepository visiteStagiareRepository;

	@Autowired
	private TeacherRepository enseignantRepository;

	@Autowired
	private ConventionRepository conventionRepository;
	
	@Autowired
	private RDVRepository rDVRepository;
	
	@Autowired
	private UtilServices utilServices;
	

	public StudentCJ getStudentbyId(String idEt) {
		StudentCJ s = etudiantRepository.getOne(idEt);
		return s;
	}
	
	public TeacherDetailsDto getEncadrantByEtudiant(String idEt)
	{
		
		TeacherDetailsDto teacherDetailsDto = null;
		TeacherDetailsDto teacherDetailsDtoCJ = enseignantRepository.getEncadrantByEtudiantCJ(idEt);
		TeacherDetailsDto teacherDetailsDtoCS = enseignantRepository.getEncadrantByEtudiantCS(idEt);
		
		if(teacherDetailsDtoCJ != null)
		{
			teacherDetailsDto = teacherDetailsDtoCJ;
		}
		
		if(teacherDetailsDtoCS != null)
		{
			teacherDetailsDto = teacherDetailsDtoCS;
		}
		
		return teacherDetailsDto;
	}

	public Teacher getEnseigantEncadrésbyET(String idEt) {
		Teacher t = enseignantRepository.getEncadrantByEtudiant(idEt);
		return t;
	}
	
	public List<StudentDetails> getEtudiantEncadrés(String idEns)
	{
		System.out.println("-------> StudentSupervision 0.1");
		List<StudentDetails> getEtudiantEncadrés = new ArrayList<StudentDetails>();
		System.out.println("-------> StudentSupervision 0.2");
		List<StudentDetails> getEtudiantEncadrésCJ = new ArrayList<StudentDetails>();
		
		for(StudentCJ s: etudiantRepository.getEtudiantEncadrés(idEns))
		{
			StudentDetails scj = new StudentDetails(s.getIdEt(), s.getNomet(), s.getPrenomet(), s.getAdressemailesp(), s.getEmailet(), s.getAdresseet(), 
					s.getTelet(), "", "", "");
			getEtudiantEncadrésCJ.add(scj);
		}
		
		System.out.println("----hhh---> StudentSupervision 0.3: " + getEtudiantEncadrésCJ.size());
		List<StudentDetails> getEtudiantEncadrésCS = new ArrayList<StudentDetails>();
		for(StudentCS s: etudiantRepositoryCS.getEtudiantEncadrésCS(idEns))
		{
			StudentDetails scs = new StudentDetails(s.getIdEt(), s.getNomet(), s.getPrenomet(), s.getAdressemailesp(), s.getEmailet(), s.getAdresseet(), 
					s.getTelet(), "", "", "");
			getEtudiantEncadrésCS.add(scs);
		}
		
		System.out.println("-------> StudentSupervision 0.4: " + getEtudiantEncadrésCS.size());
		
		getEtudiantEncadrés.addAll(getEtudiantEncadrésCJ);
		getEtudiantEncadrés.addAll(getEtudiantEncadrésCS);
		
		System.out.println("-------> StudentSupervision 0.5: " + getEtudiantEncadrés.size());
		
		return getEtudiantEncadrés;
	}

	public List<String> getOptionForEtudiant(String idEt)
	{
		
		List<String> getOptionForEtudiant = new ArrayList<String>();
		
		List<String> getOptionForEtudiantCJ = etudiantRepository.getOptionForEtudiant(idEt);
		List<String> getOptionForEtudiantCS = etudiantRepositoryCS.getOptionForEtudiantCS(idEt);
		
		getOptionForEtudiant.addAll(getOptionForEtudiantCJ);
		getOptionForEtudiant.addAll(getOptionForEtudiantCS);
		
		return getOptionForEtudiant;
	}
	
	
	public List<String> findCurrentAnnneInscriptiobByIdEt(String idEt)
	{
		List<String> findCurrentAnnneInscriptiobByIdEt = new ArrayList<String>();
		
		List<String> findCurrentAnnneInscriptiobByIdEtCJ = inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(idEt);
		List<String> findCurrentAnnneInscriptiobByIdEtCS = inscriptionRepository.findCurrentAnnneInscriptiobCSByIdEt(idEt);
		
		findCurrentAnnneInscriptiobByIdEt.addAll(findCurrentAnnneInscriptiobByIdEtCJ);
		findCurrentAnnneInscriptiobByIdEt.addAll(findCurrentAnnneInscriptiobByIdEtCS);
		
		return findCurrentAnnneInscriptiobByIdEt;
	}
	
	
	public List<StudentDetails> getEtudiantEncadrésSer(String idEns)
	{
		List<StudentDetails>Students = getEtudiantEncadrés(idEns); // oif etudiantRepository.getEtudiantEncadrés(idEns);
		System.out.println("-------> StudentSupervision 1: " + Students.size());
		List<StudentDetails> NewEtudiantList = new ArrayList<StudentDetails>();
		for (StudentDetails S : Students) {
			
		int valeur = Integer.parseInt( findCurrentAnnneInscriptiobByIdEt(S.getIdEt()).get(0)) + 1;
		// oif int valeur = Integer.parseInt( inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(S.getIdEt()).get(0)) + 1;

		
// oif		StudentDetails SNF = new StudentDetails(S.getIdEt(), S.getNomet(), S.getPrenomet(), S.getAdressemailesp(),
//				S.getEmailet(), S.getAdresseet(), S.getTelet(), etudiantRepository.getOptionForEtudiant(S.getIdEt()).get(0),
//				inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(S.getIdEt()).get(0)	+" - " + String.valueOf(valeur));
		
		StudentDetails SNF = new StudentDetails(S.getIdEt(), S.getNomet(), S.getPrenomet(), S.getAdressemailesp(),
				S.getEmailet(), S.getAdresseet(), S.getTelet(), getOptionForEtudiant(S.getIdEt()).get(0),
				findCurrentAnnneInscriptiobByIdEt(S.getIdEt()).get(0)	+" - " + String.valueOf(valeur));
		
		
		NewEtudiantList.add(SNF);
		System.out.println("-------> StudentSupervision 2: " + NewEtudiantList.size());
		}
		return NewEtudiantList;
	}

	public List<FichePFE> getFichesbyEtudiant(String idEt) {
		List<FichePFE> listFiche = fichePFERepository.findFichePFEByStudentPFE(idEt);
		return listFiche;
	}

	public List<Convention> getConventionsbyEtudiant(String idEt) {
		List<Convention> listConvention = conventionRepository.findConventionByIdEt(idEt);
		return listConvention;
	}

	public EvaluationStage SaisiEncadrantPedaNote(EvaluationStage EvalStage) {
		Date date = new Date();
		EvalStage.setDateEvaluation(date);
		return evaluationRepository.save(EvalStage);
	}
	
	public List<EvaluationStage> SaisiEncadrantPedaEvaluation(List<EvaluationStage> EvalStages) {
		Date date = new Date();
		int ResultatFinale=0;
		for (EvaluationStage E : EvalStages) { 
			if(E.getResultat().equals("A")) {
				ResultatFinale=ResultatFinale+5;
			}
			if(E.getResultat().equals("B"))  {
				ResultatFinale =ResultatFinale+4;
			}
			if(E.getResultat().equals("C"))  {
				ResultatFinale =ResultatFinale + 2;
			}
			if(E.getResultat().equals("D"))  {
				ResultatFinale =ResultatFinale + 1 ;
			}
		
			
		}
		for (EvaluationStage E : EvalStages) { 
			E.setDateEvaluation(date);
			E.setNoteEvaluation(BigDecimal.valueOf(ResultatFinale));
		}
		
		
		return evaluationRepository.saveAll(EvalStages);
	}
	
	public EvaluationStage UpdateEval(EvaluationStage newEvalStage, String idET, String dateFiche, String code) {

		if (evaluationRepository.getEvaluationStagebyId(idET, dateFiche, code) != null) {
			EvaluationStage existingNote = evaluationRepository.getEvaluationStagebyId(idET, dateFiche, code);
			Date date = new Date();
			existingNote.setDateEvaluation(date);
			existingNote.setTauxEvaluation(newEvalStage.getTauxEvaluation());
			existingNote.setNoteEvaluation(newEvalStage.getNoteEvaluation());
			existingNote.setTauxEvaluation(newEvalStage.getTauxEvaluation());
			return evaluationRepository.save(existingNote);
		} else
			return null;
	}

	public String getTypeNote(String idET, Date dateFiche, String code) {
		String TypeNote = evaluationRepository.getTypeEvaluation(idET, dateFiche, code);
		return TypeNote;
	}

	public List<EvaluationStage> getEvaluationStagebyEt(String idET, String dateFiche) {
		List<EvaluationStage> listEvaluationStage = evaluationRepository.getEvaluationStagebyEt(idET, dateFiche);
		return listEvaluationStage;
	}

	public String deleteEvaluationStage(String idET, String dateFiche, String code) {
		
		if (evaluationRepository.getEvaluationStagebyId(idET, dateFiche, code) != null) {

			evaluationRepository.deleteEvaluationStage(idET, dateFiche, code);
			return "EvaluationStage supprimé";

		} else
			return "EvaluationStage non supprimé";
	}

	public VisiteStagiaire AddVisiteForStagiaire(VisiteStagiaire Visite) {
		return visiteStagiareRepository.save(Visite);
	}

	public VisiteStagiaire UpdateVisiteForStagiaire(VisiteStagiaire Visite, String dateVisite, String dateFiche,
			String idEt) {
		System.out.println("----------------> first : " + visiteStagiareRepository.getVisiteStagiarebyId(dateVisite, dateFiche, idEt).isPresent());
		if (visiteStagiareRepository.getVisiteStagiarebyId(dateVisite, dateFiche, idEt).isPresent())
		{
			VisiteStagiaire existingVisite = visiteStagiareRepository.getVisiteStagiarebyId(dateVisite, dateFiche, idEt).get();
			existingVisite.setHeureDebut(Visite.getHeureDebut());
			
			existingVisite.setHeureFin(Visite.getHeureFin());
			existingVisite.setLieuVisite(Visite.getLieuVisite());
			existingVisite.setTypeVisite(Visite.getTypeVisite());
			existingVisite.setObservation(Visite.getObservation());
			visiteStagiareRepository.save(existingVisite);
			visiteStagiareRepository.updateVisite(dateVisite, idEt, dateFiche, Visite.getVisiteStagiairePK().getDateVisite());
			DateFormat dateFormata2 = new SimpleDateFormat("dd-MM-yyyy");
			
			System.out.println("----------------> dateFormata 1: " + dateFormata2.format(Visite.getVisiteStagiairePK().getDateVisite()));
			System.out.println("----------------> dateFormata 2: " + dateFiche);
			System.out.println("----------------> dateFormata 3: " + idEt);
			
			System.out.println("----------------> dateFormata 4: " + visiteStagiareRepository.getVisiteStagiarebyId(dateFormata2.format(Visite.getVisiteStagiairePK().getDateVisite()), dateFiche, idEt).get());
			
			return visiteStagiareRepository.getVisiteStagiarebyId(dateFormata2.format(Visite.getVisiteStagiairePK().getDateVisite()), dateFiche, idEt).get();
		}
		else
		{
			return null;
		}
	}
	public int UpdateVisiteDate(Date newDate, String oldDate, String dateFiche, String idEt) {
		return visiteStagiareRepository.updateVisite(oldDate, idEt, dateFiche, newDate);

	}

	public String getEtatVisite(Date dateVisite, Date dateFiche, String idEt) {
		String EtatVisite = visiteStagiareRepository.getVisiteEtat(dateVisite, dateFiche, idEt);
		return EtatVisite;
	}

	public List<VisiteStagiaire> getVisiteByEns(String idEns)
	{
		List<VisiteStagiaire> getVisiteByEns = new ArrayList<VisiteStagiaire>();
		List<VisiteStagiaire> getVisiteByEnsCJ = visiteStagiareRepository.getVisitlistForEns(idEns);
		List<VisiteStagiaire> getVisiteByEnsCS = visiteStagiareRepository.getVisitlistForEnsCS(idEns);
		
		getVisiteByEns.addAll(getVisiteByEnsCJ);
		getVisiteByEns.addAll(getVisiteByEnsCS);
		
		return getVisiteByEns;
	}

	public String deleteVisit(String dateVisite, String dateFiche, String idEt) {
		
		if (visiteStagiareRepository.getVisiteStagiarebyId(dateVisite, dateFiche, idEt).isPresent()) {
			visiteStagiareRepository.deleteVisite(dateVisite, dateFiche, idEt);
			return "visite supprimé";

		} else
			return "visite non supprimé";
	}

	/*-----------------------RDV SUIVI -------------------*/
	public RdvSuiviStage AddRDv(RdvSuiviStage RdvSuiviStage) {
		Date date = new Date();
		RdvSuiviStage.setEtat("04");
		RdvSuiviStage.getRdvSuiviStagePK().setDateSaisieRendezVous(date);
		;
		return rDVRepository.save(RdvSuiviStage);
	}

	public List<RdvSuiviStage> getRdvSuiviStagebyEt(String idET, String dateFiche) {
		List<RdvSuiviStage> listRdvSuiviStage = rDVRepository.getRDVStagebyEt(idET, dateFiche);
		return listRdvSuiviStage;
	}

	public RdvSuiviStage UpdateRdvSuiviStage(RdvSuiviStage RdvSuiviStage, String dateSaisiRDV, String datefiche,
			String idEt) {

		if (rDVRepository.getRdvSuiviStagebyId(dateSaisiRDV, datefiche, idEt) != null) {
			RdvSuiviStage existingRDV = rDVRepository.getRdvSuiviStagebyId(dateSaisiRDV, datefiche, idEt);
			existingRDV.setObservation(RdvSuiviStage.getObservation());
			existingRDV.setEtat(RdvSuiviStage.getEtat());
			return rDVRepository.save(existingRDV);
		} else
			return null;
	}

	public RdvSuiviStage UpdateRdvSuiviStageinfo(RdvSuiviStage RdvSuiviStage, String dateSaisiRDV, String datefiche,
			String idEt) {

		if (rDVRepository.getRdvSuiviStagebyId(dateSaisiRDV, datefiche, idEt) != null) {
			RdvSuiviStage existingRDV = rDVRepository.getRdvSuiviStagebyId(dateSaisiRDV, datefiche, idEt);
			existingRDV.setTypeRDV(RdvSuiviStage.getTypeRDV());
			;
			existingRDV.setHeureDebut(RdvSuiviStage.getHeureDebut());
			existingRDV.setLieuRDV(RdvSuiviStage.getLieuRDV());
			existingRDV.setObservation(RdvSuiviStage.getObservation());
			existingRDV.setDateRendezVous(RdvSuiviStage.getDateRendezVous());
			return rDVRepository.save(existingRDV);
		} else
			return null;
	}

	public List<StudentForSoutenance> getStudentForSoutenance(String idEns)
	{
		
		List<FichePFE> AllFichePFE = fichePFERepository.findFichePFEForSoutenance(idEns);
		List<StudentForSoutenance> StudentForSoutenanceList = new ArrayList<>();
		
		for (FichePFE F : AllFichePFE)
		{
			StudentDto studentDto = utilServices.findStudentDtoById(F.getIdFichePFE().getConventionPK().getIdEt());

			int valeur = Integer.parseInt( findCurrentAnnneInscriptiobByIdEt(studentDto.getIdEt()).get(0)) + 1;
			
			StudentForSoutenance s = new StudentForSoutenance(studentDto.getIdEt(), studentDto.getNomet(), 
					findCurrentAnnneInscriptiobByIdEt(studentDto.getIdEt()).get(0) +"-" +  String.valueOf(valeur) ,studentDto.getPrenomet(),
					studentDto.getAdressemailesp(), utilServices.findFullNamePedagogicalEncadrant(studentDto.getIdEt()) , F.getPathRapportVersion2());
			StudentForSoutenanceList.add(s);
		}

		return StudentForSoutenanceList;
		
// oif		List<FichePFE> AllFichePFE = fichePFERepository.findFichePFEForSoutenance(idEns);
//		List<StudentForSoutenance> StudentForSoutenanceList = new ArrayList<>();
//		
//		for (FichePFE F : AllFichePFE) {
//			
//			Student etu = etudiantRepository.findByIdEt(F.getIdFichePFE().getIdEt()).get();
//			int valeur = Integer.parseInt( inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(etu.getIdEt()).get(0)) + 1;
//			
//			Teacher t = enseignantRepository.getEncadrantByEtudiant(F.getIdFichePFE().getIdEt());
//			
//			StudentForSoutenance s = new StudentForSoutenance(etu.getIdEt(), etu.getNomet(), inscriptionRepository.findCurrentAnnneInscriptiobByIdEt(etu.getIdEt()).get(0) +"-" +  String.valueOf(valeur) ,etu.getPrenomet(),
//					etu.getAdressemailesp(), t.getNomEns() , F.getPathRapportVersion2());
//			StudentForSoutenanceList.add(s);
//		}
//
//		return StudentForSoutenanceList;

	}

	public List<Map<String, String>> getSomeStat(String idEns) {
		List<Map<String, String>> newList = new ArrayList<>();
		List<String> listAllStudent = etudiantRepository.getEtudiantEncadrés2(idEns);
		
		List<FichePFE> AllFichebyEtudiant = new ArrayList<FichePFE>();
		List<FichePFE> AllFicheSoutenu = new ArrayList<FichePFE>();

		for (String S : listAllStudent) {

			AllFichebyEtudiant = fichePFERepository.findFichePFEByStudentNotDEPOSEE(S);
			
			for (FichePFE F : AllFichebyEtudiant) {
				if (F.getEtatFiche().equals("07")) {
					AllFicheSoutenu.add(F);
				}

			}
		}
		Map<String, String> combined = new HashMap<>();
		combined.put("nombreEncadrementAchevé", String.valueOf(AllFicheSoutenu.size()));
		combined.put("nombreEncadrementAllStudent", String.valueOf(listAllStudent.size()));
		newList.add(combined);
		return newList;

	}
	
	List<InscriptionCJ> getAllEnseignantEncad(String idEns)
	{
		System.out.println("--------------------------> EnseignantStat 2.2");
		List<InscriptionCJ> InscriptionList = new ArrayList<InscriptionCJ>();
		List<InscriptionCJ> InscriptionListCJ = inscriptionRepository.getAllEnseignantEncad(idEns);
		System.out.println("-----------x---------------> EnseignantStat 2.3: " + InscriptionListCJ.size());
		List<InscriptionCJ> InscriptionListCS = inscriptionRepository.getAllEnseignantEncadCS(idEns);
		System.out.println("--------------------------> InscriptionListCS 2.4: " + InscriptionList.size());
		
		InscriptionList.addAll(InscriptionListCJ);
		InscriptionList.addAll(InscriptionListCS);
		
		System.out.println("--------------------------> EnseignantStat 2.5: " + InscriptionList.size());
		
		return InscriptionList;
	}

	public List<Map<String, String>> getSomeStat2(String idEns)
	{
		System.out.println("--------------------------> EnseignantStat 2.1");
		List<Map<String, String>> newList = new ArrayList<>();
		List<InscriptionCJ> InscriptionList = getAllEnseignantEncad(idEns); // oif inscriptionRepository.getAllEnseignantEncad(idEns);
		
		System.out.println("--------------------------> EnseignantStat 3");
		
		int size = InscriptionList.size() - 1;
		
		while(size >=0 ) {
			Map<String, String> combined = new HashMap<>();
			String annne = InscriptionList.get(0).getId().getAnneeDeb();
		
			int valeur = Integer.parseInt( InscriptionList.get(0).getId().getAnneeDeb()) + 1;
			combined.put("annee", InscriptionList.get(0).getId().getAnneeDeb() +"-" +  String.valueOf(valeur) );
			int nbr = 0;
			int j=0;
			while(j<=size)
			{
				if(InscriptionList.get(j).getId().getAnneeDeb().equals(annne)) {
					nbr++;
					InscriptionList.remove(j);
					size--;
					
				}else {
					j++;
				}
				combined.put("nbr", String.valueOf(nbr));
				
			}
			newList.add(combined);
		}
	
		return newList;

	}

}

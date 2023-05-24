
package com.esprit.gdp.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.esprit.gdp.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.FichePFE;
import com.esprit.gdp.models.FichePFEPK;
import com.esprit.gdp.models.Technologie;

@Repository
public interface FichePFERepository extends JpaRepository<FichePFE, FichePFEPK> 
{

	 @Query("Select new com.esprit.gdp.dto.FichePFEHistoryDto(fp.titreProjet, fp.descriptionProjet, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS'), fp.anneeDeb, fp.etatFiche, fp.pathDiagrammeGantt, "
	 		+ "fp.pathBilan1, "
	 		+ "FUNCTION('to_char', fp.dateDepotBilan1,'dd-mm-yyyy HH24:MI:SS'), fp.pathBilan2, "
	 		+ "FUNCTION('to_char', fp.dateDepotBilan2,'dd-mm-yyyy HH24:MI:SS'), "
	 		+ "fp.pathBilan3, "
	 		+ "FUNCTION('to_char', fp.dateDepotBilan3,'dd-mm-yyyy HH24:MI:SS'), "
	 		+ "fp.pathJournalStg, "
	 		+ "FUNCTION('to_char', fp.dateDepotJournalStg,'dd-mm-yyyy HH24:MI:SS'), "
			+ "fp.pathRapportVersion1, "
			+ "FUNCTION('to_char', fp.dateDepotRapportVersion1,'dd-mm-yyyy HH24:MI:SS'), fp.pathRapportVersion2, "
			+ "FUNCTION('to_char', fp.dateDepotRapportVersion2,'dd-mm-yyyy HH24:MI:SS'), "
			+ "fp.pathAttestationStage, "
			+ "FUNCTION('to_char', fp.dateDepotAttestationStage,'dd-mm-yyyy HH24:MI:SS'), "
			+ "fp.pathSupplement, "
			+ "FUNCTION('to_char', fp.dateDepotSupplement,'dd-mm-yyyy HH24:MI:SS'), "
			+ "fp.pathPlagiat, "
			+ "FUNCTION('to_char', fp.dateDepotPlagiat,'dd-mm-yyyy HH24:MI:SS'), "
			+ "FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy HH24:MI:SS'), "
			+ "FUNCTION('to_char', fp.dateTreatReports,'dd-mm-yyyy HH24:MI:SS')) "
			+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 and fp.etatFiche != '01' " //and fp.etatFiche != '01' "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFEHistoryDto> findAllFichePFEsByStudent(String idEns);
	 
	 @Query("Select new com.esprit.gdp.dto.FichePFEHistoryDto(fp.titreProjet, fp.descriptionProjet, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS'), fp.anneeDeb, fp.etatFiche, fp.pathDiagrammeGantt, "
		 		+ "fp.pathBilan1, "
		 		+ "FUNCTION('to_char', fp.dateDepotBilan1,'dd-mm-yyyy HH24:MI:SS'), fp.pathBilan2, "
		 		+ "FUNCTION('to_char', fp.dateDepotBilan2,'dd-mm-yyyy HH24:MI:SS'), "
		 		+ "fp.pathBilan3, "
		 		+ "FUNCTION('to_char', fp.dateDepotBilan3,'dd-mm-yyyy HH24:MI:SS'), "
		 		+ "fp.pathJournalStg, "
		 		+ "FUNCTION('to_char', fp.dateDepotJournalStg,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathRapportVersion1, "
				+ "FUNCTION('to_char', fp.dateDepotRapportVersion1,'dd-mm-yyyy HH24:MI:SS'), fp.pathRapportVersion2, "
				+ "FUNCTION('to_char', fp.dateDepotRapportVersion2,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathAttestationStage, "
				+ "FUNCTION('to_char', fp.dateDepotAttestationStage,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathSupplement, "
				+ "FUNCTION('to_char', fp.dateDepotSupplement,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathPlagiat, "
				+ "FUNCTION('to_char', fp.dateDepotPlagiat,'dd-mm-yyyy HH24:MI:SS'), "
				+ "FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy HH24:MI:SS'), "
				+ "FUNCTION('to_char', fp.dateTreatReports,'dd-mm-yyyy HH24:MI:SS')) "
				+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 "
				+ "and fp.etatFiche != '01' "
				+ "and FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 " //and fp.etatFiche != '01' "
				+ "order by fp.idFichePFE.dateDepotFiche desc")
	FichePFEHistoryDto findFichePFEsByStudentAndDateDepot(String idEns, String dateDepotPT);
	 
	@Query("Select new com.esprit.gdp.dto.FichePFEHistoryDto(fp.titreProjet, fp.descriptionProjet, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS'), fp.anneeDeb, fp.etatFiche, fp.pathDiagrammeGantt, "
		 		+ "fp.pathBilan1, "
		 		+ "FUNCTION('to_char', fp.dateDepotBilan1,'dd-mm-yyyy HH24:MI:SS'), fp.pathBilan2, "
		 		+ "FUNCTION('to_char', fp.dateDepotBilan2,'dd-mm-yyyy HH24:MI:SS'), "
		 		+ "fp.pathBilan3, "
		 		+ "FUNCTION('to_char', fp.dateDepotBilan3,'dd-mm-yyyy HH24:MI:SS'), "
		 		+ "fp.pathJournalStg, "
		 		+ "FUNCTION('to_char', fp.dateDepotJournalStg,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathRapportVersion1, "
				+ "FUNCTION('to_char', fp.dateDepotRapportVersion1,'dd-mm-yyyy HH24:MI:SS'), fp.pathRapportVersion2, "
				+ "FUNCTION('to_char', fp.dateDepotRapportVersion2,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathAttestationStage, "
				+ "FUNCTION('to_char', fp.dateDepotAttestationStage,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathSupplement, "
				+ "FUNCTION('to_char', fp.dateDepotSupplement,'dd-mm-yyyy HH24:MI:SS'), "
				+ "fp.pathPlagiat, "
				+ "FUNCTION('to_char', fp.dateDepotPlagiat,'dd-mm-yyyy HH24:MI:SS'), "
				+ "FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy HH24:MI:SS'), "
				+ "FUNCTION('to_char', fp.dateTreatReports,'dd-mm-yyyy HH24:MI:SS')) "
				+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 "
				+ "and fp.etatFiche != '01' "
				+ "order by fp.idFichePFE.dateDepotFiche desc")
	FichePFEHistoryDto findNewestFichePFEsByStudent(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.StudentSTNGradDto(FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy'), fp.heureDebut, fp.salle.codeSalle, "
//		 		+ "fp.presidentJury, fp.expertEns.nomEns, fp.pedagogicalEncadrant.nomEns) "
				+ "fp.presidentJury, '', '') "
		 		+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 and fp.etatFiche = '06' "
				+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<StudentSTNGradDto> findStudentSTNCongratDtoByStudent(String idEns);
	
	@Query("Select fp.pedagogicalEncadrant.nomEns "
	 		+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 and fp.etatFiche = '06' "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
    List<String> findJuryPresidentEnsByStudent(String idEns);
	
	@Query("select "
			//+ "FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy'), fp.heureDebut, fp.salle.codeSalle, "
			+ "fp.presidentJuryEns.nomEns, fp.expertEns.nomEns, fp.pedagogicalEncadrant.nomEns "
			+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 and "
			+ "fp.etatFiche = '06' "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findStudentJuryMembers(String idEt);
	
	@Query("select new com.esprit.gdp.dto.FichePFEDateDepotReportsEtatReportsDto(FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy'), cn.libNome) "
			+ "from FichePFE fp, CodeNomenclature cn "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and cn.cni.codeStr = '108' and cn.cni.codeNome = fp.validDepot "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	List<FichePFEDateDepotReportsEtatReportsDto> findDepotReportsDateEtatByIdEt(String idEt); // dateTreatReports
	
	@Query("select new com.esprit.gdp.dto.FichePFEDateDepotReportsEtatReportsDto(FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy'), cn.libNome) "
			+ "from FichePFE fp, CodeNomenclature cn "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 "
			+ "and cn.cni.codeStr = '108' and cn.cni.codeNome = fp.validDepot "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	List<FichePFEDateDepotReportsEtatReportsDto> findDepotReportsDateEtatByIdEtAndFichePFE(String idEt, String dateDepotFichePFE); // dateTreatReports
	
	@Query("select "
			+ "FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy'), cn.libNome "
			+ "from FichePFE fp, CodeNomenclature cn "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and cn.cni.codeStr = '108' and cn.cni.codeNome = fp.validDepot "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findDepotReportsDateEtatByIdEtTimeline(String idEt);
	
//	@Query("select "
//			+ "fp.dateDepotReports, cn.libNome "
//			+ "from FichePFE fp, CodeNomenclature cn "
//			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
//			+ "and cn.cni.codeStr = '108' and cn.cni.codeNome = fp.validDepot "
//			+ "order by fp.idFichePFE.dateDepotFiche asc")
//	List<Object[]> hi(String idEt);
//	
//	@Query("select "
//			+ "FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy HH24:MI:SS') "
//			+ "from FichePFE fp "
//			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
//			+ "order by fp.idFichePFE.dateDepotFiche asc")
//	List<String> hi1(String idEt);
//	
//	@Query("select "
//			+ "FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy HH24:MI:SS') "
//			+ "from FichePFE fp "
//			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
//			+ "order by fp.idFichePFE.dateDepotFiche asc")
//	List<String> hi2(String idEt);
//	
//	@Query("select cn.libNome "
//			+ "from FichePFE fp, CodeNomenclature cn "
//			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
//			+ "and FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy HH24:MI:SS')=?2 "
//			+ "and cn.cni.codeStr = '108' and cn.cni.codeNome = fp.validDepot "
//			+ "order by fp.idFichePFE.dateDepotFiche desc")
//	String hi2(String idEt, String dateDepotReports);
	
	@Query("select fp.etatFiche from FichePFE fp "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findFichePFEStatus(String idEt);
	
	// FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS.FF9')
	@Query("select new com.esprit.gdp.dto.FichePFETitreDateDepotStatusDto(fp.titreProjet, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy'), cn.libNome, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS.FF9')) "
			+ "from FichePFE fp, CodeNomenclature cn "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and cn.cni.codeStr = '101' and cn.cni.codeNome = fp.etatFiche "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFETitreDateDepotStatusDto> findFicheTitreDateEtatByIdEt(String idEt);
	
//	@Query("select fp.idFichePFE.dateDepotFiche "
//			+ "from FichePFE fp, CodeNomenclature cn "
//			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
//			+ "and cn.cni.codeStr = '101' and cn.cni.codeNome = fp.etatFiche "
//			+ "order by fp.idFichePFE.dateDepotFiche desc")
//	List<Timestamp> findLastDateDepotFicheyIdEt(String idEt);
		
//	@Query("select fp.idFichePFE.conventionPK.entrepriseAccueilConvention.designation "
//			+ "from FichePFE fp "
//			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
//			+ "order by fp.idFichePFE.dateDepotFiche desc")
//	List<String> findCompanyNameByStudent(String idEt);
	
	@Query("select c.entrepriseAccueilConvention.designation "
			+ "from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK = c.conventionPK and "
			+ "fp.idFichePFE.conventionPK.idEt=?1 "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findCompanyNameByStudent(String idEt);
	
	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy') "
			+ "from FichePFE fp, CodeNomenclature cn "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and cn.cni.codeStr = '101' and cn.cni.codeNome = fp.etatFiche "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findDateDepotFicheByIdEt(String idEt);
	
	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy') "
			+ "from FichePFE fp "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and fp.etatFiche in ('03', '06', '07', '08') "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findDateDepotFicheByIdEtOFF(String idEt);
	
	@Query("Select fp.dateSoutenance from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and c.responsableServiceStage.idUserSce =?1 and fp.dateSoutenance is not null "
			+ "order by fp.dateSoutenance asc")
	List<Date> datesSoutenances(String idUserSce);
	
	@Query("Select fp.idFichePFE.dateDepotFiche from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and c.responsableServiceStage.idUserSce =?1 and fp.dateSoutenance =?2")
	List<Date> findNumberOfSoutenancesByDay(String idRSS, Date day);
	
	@Query("Select new com.esprit.gdp.dto.FichePFEDepotDto(fp.etatFiche, fp.validDepot, fp.observationDepot, "
			+ "FUNCTION('to_char', fp.dateDepotReports,'dd-mm-yyyy'), "
			+ "FUNCTION('to_char', fp.dateTreatReports,'dd-mm-yyyy')) "
			+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and fp.validDepot is not null "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	List<FichePFEDepotDto> findResultDepotReport(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.FichePFEPlanificationSTNDto( "
			+ "FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy'), "
			+ "FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy'), "
			+ "fp.traineeKind, fp.session.libelleSession) from FichePFE fp "
			+ "where (fp.etatFiche ='03' or fp.etatFiche ='06' or fp.etatFiche ='08') and fp.idFichePFE.conventionPK.idEt=?1 "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	FichePFEPlanificationSTNDto findFichePFEForPlanSTNByStudent(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.FichePFEMultiplePlanificationDto( "
			+ "fp.pedagogicalEncadrant.idEns, "
			+ "fp.expertEns.idEns, "
			+ "fp.presidentJuryEns.idEns, "
			+ "fp.dateSoutenance, "
			+ "fp.heureDebut, "
			+ "fp.heureFin, "
			+ "fp.salle.codeSalle) from FichePFE fp "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 and fp.etatFiche in ('03', '08') "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	List<FichePFEMultiplePlanificationDto> findFichePFEMutiplePlanification(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.FichePFEMultiplePlanificationDto( "
			+ "fp.pedagogicalEncadrant.idEns, "
			+ "fp.expertEns.idEns, "
			+ "fp.presidentJuryEns.idEns, "
			+ "fp.dateSoutenance, "
			+ "fp.heureDebut, "
			+ "fp.heureFin, "
			+ "fp.salle.codeSalle) from FichePFE fp "
			+ "where fp.expertEns is not null "
			+ "and fp.presidentJuryEns is not null "
			+ "and fp.dateSoutenance is not null "
			+ "and fp.heureDebut is not null "
			+ "and fp.heureFin not like '--:--'"
			+ "and fp.salle is not null "
			+ "and fp.idFichePFE.conventionPK.idEt=?1 "
			+ "order by fp.idFichePFE.dateDepotFiche asc")
	List<FichePFEMultiplePlanificationDto> gotNotNullFichePFEForPlanifiedSTNByIdStudent(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.FichePFEMultiplePlanificationDto( "
			+ "fp.pedagogicalEncadrant.idEns, "
			+ "fp.expertEns.idEns, "
			+ "fp.presidentJuryEns.idEns, "
			+ "fp.dateSoutenance, "
			+ "fp.heureDebut, "
			+ "fp.heureFin, "
			+ "fp.salle.codeSalle) from FichePFE fp "
			+ "where fp.anneeDeb=?1")
	List<FichePFEMultiplePlanificationDto> findFichePFEMutiplePlanificationByYear(String anneeDeb);
	
	@Query("Select fp.idFichePFE.conventionPK.idEt from FichePFE fp where fp.expertEns.idEns=?1 and fp.anneeDeb =?2")
	List<String> findListOfStudentsTrainedByExpert(String idEns, String anneeDeb);
	
	@Query("Select fp.idFichePFE.conventionPK.idEt from FichePFE fp where fp.presidentJuryEns.idEns=?1 and fp.anneeDeb =?2")
	List<String> findListOfStudentsTrainedByJuryPresident(String idEns, String anneeDeb);
	
	
	@Query("select fp.pedagogicalEncadrant.nomEns, "
			+ "fp.expertEns.idEns, "
			+ "fp.presidentJuryEns.idEns, "
			+ "fp.dateSoutenance, "
			+ "fp.heureDebut, "
			+ "fp.heureFin, "
			+ "fp.salle.codeSalle "
			+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findFichePFEPlanifySoutenanceForMP(String idEt);
	
	// presidentJury   presidentJuryEns   expertEns
	@Query("select fp.presidentJury, fp.presidentJuryEns.idEns, fp.expertEns.idEns, fp.salle.codeSalle, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS'), FUNCTION('to_char', fp.dateSoutenance,'yyyy-mm-dd'), fp.heureDebut, fp.heureFin, fp.etatFiche "
			+ " from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') asc")
	List<Object[]> findFichePFEPlanifySoutenance(String idEt);
	
	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS') "
			+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findDateDepotFichePFE(String idEt);
	
	@Query("select fp.technologies from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 and FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS') =?2")
	List<Technologie> findAllTechnologiesByStudentAndFichePFE(String idEt, String dateDepotFiche);
	
	@Query("select fp.technologies from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 and FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS') =?2")
	List<Technologie> findTechnologiesByStudentAndFichePFE(String idEt, String dateDepotFiche);
 
//	@Query("select new com.esprit.gdp.dto.FichePFEForFicheDepotPFEDto(fp.dateDepotReports, fp.urgRdvPFE.urgRdvPFEPK.dateSaisieRDV, fp.urgRdvPFE.etatTreatCP, fp.session.libelleSession, fp.etatFiche) from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
//	List<FichePFEForFicheDepotPFEDto> findFichePFEForFicheDepotPFEDtoByStudent(String idEt);
	
	@Query("select new com.esprit.gdp.dto.FichePFEForFicheDepotPFEDto(fp.urgRdvPFE.urgRdvPFEPK.dateSaisieRDV, fp.urgRdvPFE.etatTreatEA ,fp.urgRdvPFE.etatTreatCP) from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1")
	FichePFEForFicheDepotPFEDto findFichePFEForFicheDepotPFEDtoByStudent(String idEt);
	
	@Query("select new com.esprit.gdp.dto.FichePFEWithoutFicheDepotPFEDto "
			+ "(fp.dateDepotReports, fp.session.libelleSession, fp.etatFiche, fp.validDepot) "
			+ "from FichePFE fp "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 and fp.dateDepotReports is not null and fp.session.libelleSession is not null and "
			+ "fp.validDepot is not null "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFEWithoutFicheDepotPFEDto> findFichePFEWithoutFicheDepotPFEDto(String idEt);
	
//	@Query("select fp.dateDepotReports, fp.session.libelleSession, fp.etatFiche, fp.validDepot from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
//	List<Object[]> findFichePFEWithoutFicheDepotPFEDto(String idEt);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findFichePFEByStudentUNIT(String idEt);
	
	@Query("select fp from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFE> findFichePFEByStudent(String idEt);
	
	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS') from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findDatesDepotFichePFEByStudent(String idEt);
	
	@Query("select fp from FichePFE fp where "
			+ "fp.idFichePFE.conventionPK.idEt=?1 and FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 ")
	FichePFE findFichePFEByStudentAndDateDepotFiche(String idEt, String dateDepotFiche);
	
	@Query("select fp.idFichePFE from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFEPK> findFichePFEPKByStudent(String idEt);
	
	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findNewestDateDepotFichePFEByStudent(String idEt);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findExistFichePFEByStudent(String idEt);
	
	@Query("select fp.urgRdvPFE.urgRdvPFEPK.dateSaisieRDV from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<Date> findExistDepotFichePFEByStudent(String idEt);
	
	@Query("select fp.urgRdvPFE.etatTreatCP from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 and fp.urgRdvPFE.etatTreatCP = '01' order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findExistDepotFichePFEWithPCTreatByStudent(String idEt);
	
	@Query("select fp.idFichePFE from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFEPK> findExistFichePFEPKByStudent(String idEt);
	
	// 'fp.presidentJury, 'JPE:', fp.presidentJuryEns.idEns, 'EXP:', ufp.expertEns.idEns
	@Query("select CONCAT(fp.presidentJury,'JPE:',CONCAT(fp.presidentJuryEns.idEns, 'EXP:', fp.expertEns.idEns)) from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findJuryPresidentExpertByStudent(String idEt);
	
	@Query("select fp.pathRapportVersion2 from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findPathRapportVersion2ByStudent(String idEt);
	
	@Query("select fp.pathSupplement from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findPathTechnicalFolderByStudent(String idEt);
	
	@Query("select fp.titreProjet from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findTitleFichePFEByStudent(String idEt);
	
	@Query("select fp from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFE> findFichePFEStatusByStudent(String idEt);
	
//	@Query("select fp.idFichePFE.dateDepotFiche from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1")
//	Date hi0(String idEt);
//	
//	@Query("select fp.idFichePFE.dateDepotFiche from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1")
//	String hi1(String idEt);
//	
//	@Query("select trunc(fp.idFichePFE.dateDepotFiche) from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1")
//	String hi2(String idEt);
//	
//	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-MM-dd HH:mm:ss') from FichePFE fp") 
//	String hi3(String idEt);
//	
//	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd hh:mm:ss') from FichePFE fp") 
//	String hi4(String idEt);
//	
//	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH:mi:ss') from FichePFE fp") 
//	String hi5(String idEt);
//	
//	@Query("select FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') from FichePFE fp") 
//	String hi6(String idEt);
//	
//	@Query("select fp.titreProjet, fp.technologies from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1")
//	List<Object[]> saria1(String idEt);
//	
//	@Query("select fp.titreProjet from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1")
//	String saria2(String idEt);

	
//	@Query("Select distinct fp.idFiche, fp.titreProjet, fp.descriptionProjet, fp.dateDepot, fp.annee, fp.etat, fp.societe"
//			+ ", techs"
//			+ ", funcs"
//			+ ", probs"
//			+ ", supervs"
//			+ " from FichePFE fp "
//			+ "LEFT JOIN fp.technologies techs "
//			+ "LEFT JOIN fp.fonctionalites funcs "
//			+ "LEFT JOIN fp.problematiques probs "
//			+ "LEFT JOIN fp.encadrantSocietes supervs "
//			+ "where fp.etudiant=?1")
//	List<Object[]> sars1(StudentCJ student);

//	// select e, m.id from SomeEntity e LEFT JOIN e.manyToMany m
//	@Query("Select fp.societe.nomSociete from FichePFE fp where fp.etudiant=?1")
//	List<Object[]> sars2(StudentCJ student);
	
	@Query("select fp.pathBilan1, fp.pathBilan2, fp.pathBilan3, FUNCTION('to_char', fp.dateDepotBilan1,'dd-mm-yyyy'), FUNCTION('to_char', fp.dateDepotBilan2,'dd-mm-yyyy'), FUNCTION('to_char', fp.dateDepotBilan3,'dd-mm-yyyy')"
			+ " from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findAllBilans(String idEt);
	
	@Query("select fp.pathJournalStg, FUNCTION('to_char', fp.dateDepotJournalStg, 'dd-mm-yyyy')"
			+ " from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findJournal(String idEt);
	
	@Query("select fp.pathRapportVersion1, FUNCTION('to_char', fp.dateDepotRapportVersion1,'dd-mm-yyyy'), "
			+ "fp.pathRapportVersion2, FUNCTION('to_char', fp.dateDepotRapportVersion2,'dd-mm-yyyy'), "
			+ "fp.pathPlagiat, fp.dateDepotPlagiat, "
			+ "fp.pathAttestationStage, FUNCTION('to_char', fp.dateDepotAttestationStage,'dd-mm-yyyy'), "
			+ "fp.confidentiel "
			+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findAllReports(String idEt);
	
	
	@Query("select fp.pathRapportVersion1, FUNCTION('to_char', fp.dateDepotRapportVersion1,'dd-mm-yyyy'), "
			+ "fp.pathRapportVersion2, FUNCTION('to_char', fp.dateDepotRapportVersion2,'dd-mm-yyyy'), "
			+ "fp.pathPlagiat, FUNCTION('to_char', fp.dateDepotPlagiat,'dd-mm-yyyy'), "
			+ "fp.pathAttestationStage, FUNCTION('to_char', fp.dateDepotAttestationStage,'dd-mm-yyyy'), "
			+ "fp.pathSupplement, FUNCTION('to_char', fp.dateDepotSupplement,'dd-mm-yyyy'), "
			+ "fp.confidentiel "
			+ "from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findAllReportsUrg(String idEt);
	
	@Query("select fp from FichePFE fp where  fp.presidentJuryEns.idEns=:idEns order by fp.anneeDeb desc")
	List<FichePFE> findFichePFEForSoutenance(@Param("idEns") String idEns);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and "
			+ "fp.etatFiche ='03' and fp.validDepot = '02' and "
			+ "fp.session.idSession=?1 "
			+ "and c.responsableServiceStage.idUserSce =?2 order by fp.idFichePFE.dateDepotFiche desc")
	Set<String> findAllAuthorizedStudentsForSoutenance(Integer idSession, String idRespServiceStage);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.etatFiche ='06' and fp.validDepot = '02' and fp.session.idSession=?1 "
			+ "and c.responsableServiceStage.idUserSce =?2 order by fp.idFichePFE.dateDepotFiche desc")
	Set<String> findAllStudentsDoneSoutenance(Integer idSession, String idRespServiceStage);

	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.etatFiche ='06' "
			+ "and fp.validDepot = '02' "
			+ "and c.responsableServiceStage.idUserSce =?1 order by fp.idFichePFE.dateDepotFiche desc")
	Set<String> findAllStudentsAllDoneSoutenance(String idRespServiceStage);

	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.etatFiche ='06' "
			+ "and fp.validDepot = '02' "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	Set<String> findAllStudentsAllDoneSoutenanceForCPS();

	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and "
			+ "fp.etatFiche ='03' and fp.validDepot = '02' and "
			+ "c.responsableServiceStage.idUserSce =?1 order by fp.idFichePFE.dateDepotFiche desc")
	Set<String> findAllAllAuthorizedStudentsForSoutenance(String idRespServiceStage);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.etatFiche ='08' and fp.validDepot = '02' and fp.session.idSession=?1 "
			+ "and c.responsableServiceStage.idUserSce =?2 order by fp.idFichePFE.dateDepotFiche desc")
	Set<String> findAllStudentsPlanifiedSoutenance(Integer idSession, String idRespServiceStage);
	

	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and "
			+ "fp.etatFiche ='08' and fp.validDepot = '02' "
			+ "and c.responsableServiceStage.idUserSce =?1 order by fp.idFichePFE.dateDepotFiche desc")
	Set<String> findAllAllStudentsPlanifiedSoutenance(String idRespServiceStage);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.validDepot=?3 and fp.etatFiche ='03' and fp.session.idSession=?1 "
			+ "and c.responsableServiceStage.idUserSce=?2 order by FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') asc")
	List<String> findStudentsByDepotStatus(Integer idSession, String idRespServiceStage, String depotStatus);
	
	
	@Query("select distinct fp.dateDepotReports from FichePFE fp " // (SYSOUT: 1988-06-24) (DB: 24/06/21)
			+ "where fp.dateDepotReports is not null and fp.session.idSession=?1 order by fp.dateDepotReports")
	List<Date> findDaysOfDepotReports(Integer idSession);
	
	@Query("select distinct fp.dateSoutenance from FichePFE fp " // (SYSOUT: 1988-06-24) (DB: 24/06/21)
			+ "where fp.dateSoutenance is not null and fp.session.idSession=?1 order by fp.dateSoutenance")
	List<Date> findDaysOfSoutenances(Integer idSession);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.validDepot=?3 and fp.session.idSession=?1 "
			+ "and fp.dateDepotReports=?4 "
			+ "and c.responsableServiceStage.idUserSce=?2 order by FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') asc")
	List<String> findStudentsByDepotStatusAndDay(Integer idSession, String idRespServiceStage, String depotStatus, Date day);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and "
			+ "fp.etatFiche=?3 and fp.validDepot=?4 and fp.session.idSession=?1 "
			+ "and c.traiter = '02' and c.responsableServiceStage.idUserSce=?2")
	List<String> findStatByEtatFicheAndValidDepot(Integer idSession, String idRespServiceStage, String etatFiche, String validDepot);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.etatFiche!='02' "
			+ "and fp.validDepot=?3 and fp.session.idSession=?1 "
			+ "and c.traiter = '02' and c.responsableServiceStage.idUserSce=?2")
	List<String> findStatValidetedDepot(Integer idSession, String idRespServiceStage, String validDepot);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.etatFiche='06' and fp.session.idSession=?1 "
			+ "and fp.dateSoutenance=?3 "
			+ "and c.responsableServiceStage.idUserSce=?2 order by FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') asc")
	List<String> findPlanifiedSoutenanceByDay(Integer idSession, String idRespServiceStage, Date day);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp, Convention c "
			+ "where fp.idFichePFE.conventionPK.idEt = c.conventionPK.idEt and fp.etatFiche='03' and fp.validDepot='02' and fp.session.idSession=?1 "
			+ "and fp.dateSoutenance=?3 "
			+ "and c.responsableServiceStage.idUserSce=?2 order by FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') asc")
	List<String> findUnplanifiedSoutenanceByDay(Integer idSession, String idRespServiceStage, Date day);
	
	@Query("SELECT f from FichePFE f, Convention c "
			+ "where c.conventionPK.idEt=f.idFichePFE.conventionPK.idEt "
			+ "and " 
			+ "f.validDepot='01' and f.etatFiche ='03' and "
			+ "c.traiter = '02' and c.responsableServiceStage.idUserSce=?1 "
			+ "order by f.dateDepotReports desc")
	List<FichePFE> getFichesForDepot(String idServiceStage);
	
	@Query("SELECT CONCAT(fp.pedagogicalEncadrant.idEns, ':', fp.idFichePFE.conventionPK.idEt) from FichePFE fp "
			+ "where fp.etatFiche in ('03', '08') and fp.pedagogicalEncadrant.idEns in "
			+ "(select distinct f.pedagogicalEncadrant.idEns from FichePFE f where fp.idFichePFE.conventionPK.idEt IN (?1))")
	List<String> getConcatPE_Stu(List<String> idStudents);
	
	@Query("select fp.idFichePFE.conventionPK.idEt from FichePFE fp "
			+ "where fp.etatFiche = '08' and fp.validDepot = '02' "
			+ "and fp.idFichePFE.conventionPK.idEt IN (?1)")
	List<String> nbrStudentsWithPlanifiedSoutenances(List<String> idStudents);
	

	@Query("SELECT fp.salle.codeSalle from FichePFE fp where fp.idFichePFE.conventionPK.idEt IN (?1)")
	List<String> findAffectedSallesByStudents(List<String> students);
	
	@Query("SELECT fp from FichePFE fp where fp.etatFiche in ('03', '08') and fp.pedagogicalEncadrant.idEns=?1 and fp.idFichePFE.conventionPK.idEt IN (?2)")
	List<FichePFE> findFichePFEWithAffectedPEByStudents(String idPE, List<String> students);
	
	@Query("SELECT fp.expertEns.idEns from FichePFE fp where fp.expertEns.idEns is not null and fp.idFichePFE.conventionPK.idEt IN (?1)")
	List<String> filledExp(List<String> students);
	
	
	@Query("SELECT fp.idFichePFE.conventionPK.idEt from FichePFE fp "
			+ "where fp.etatFiche in ('03', '08') "
			+ "and fp.expertEns is null "
			+ "and fp.presidentJuryEns is null "
			+ "and fp.salle is null "
			+ "and FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy') =?3 "
			+ "and fp.heureDebut =?4 "
			+ "and fp.heureFin =?5 "
			+ "and fp.idFichePFE.conventionPK.idEt IN (?1) "
			+ "and fp.pedagogicalEncadrant.idEns IN (?2)")
	List<String> findStudentsWithOnlyAffectDateHours(List<String> idStudents, List<String> idPEs, String dateSoutenance, String startHour, String endHour);
	
	
	@Query("SELECT fp.idFichePFE.conventionPK.idEt from FichePFE fp where fp.etatFiche in ('03', '08') and fp.pedagogicalEncadrant.idEns IN (?1) and fp.idFichePFE.conventionPK.idEt IN (?2)")
	List<String> getStudentsByAvailablePEs(List<String> idPedagogicalEncadrants, List<String> idCheckedStuds);
	
	
	@Query(value="SELECT f from FichePFE f " + 
			"where (FUNCTION('to_char', f.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:dateFiche and f.idFichePFE.conventionPK.idEt =:idET)")
	FichePFE getFichebyId(@Param("idET") String idET, @Param("dateFiche") String dateFiche);
	
	@Query("select fp from FichePFE fp "
			+ "where fp.idFichePFE.conventionPK.idEt=:idEt and fp.etatFiche not in ('01', '04', '05') order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFE> findFichePFEByStudentNotDEPOSEE(@Param("idEt") String idEt);
	
	@Query("select fp.etatFiche from FichePFE fp "
			+ "where fp.idFichePFE.conventionPK.idEt=:idEt and fp.etatFiche not in ('01', '04', '05') order by fp.idFichePFE.dateDepotFiche desc")
	List<String> azerty(@Param("idEt") String idEt);
	
	@Query("SELECT f from FichePFE f "
			+"join Convention c "
			+ "on c.conventionPK.idEt=f.idFichePFE.conventionPK.idEt "
			+ " where (  ( f.etatFiche ='03' or  f.etatFiche ='06' or  f.etatFiche ='07' ) "
			+ "and c.responsableServiceStage.idUserSce=:idServiceStage and c.traiter !='03' ) ")
	List<FichePFE> getFichesBydept(@Param("idServiceStage") String idServiceStage);
	
	
	
//	@Query("SELECT f from FichePFE f, Convention c "
//			+ "where c.conventionPK.idEt=f.idFichePFE.conventionPK.idEt "
//			+ "and " 
//			+ "f.validDepot='01' and "
//			+ "f.etatFiche ='03' and "
//			+ "c.responsableServiceStage.idUserSce=:idServiceStage order by f.dateDepotReports desc")
//	List<FichePFE> getFichesForDepot(@Param("idServiceStage") String idServiceStage);
	
	@Query("SELECT f from FichePFE f "
			+"join Convention c "
			+ "on c.conventionPK.idEt=f.idFichePFE.conventionPK.idEt "
			+ "where " 
			+ "f.validDepot='02' and " //+ "f.etatFiche ='03' and "
			+ "c.traiter = '02' and c.responsableServiceStage.idUserSce=:idServiceStage "
			+ "order by f.dateDepotReports desc")
	List<FichePFE> getFichesForDepotVal(@Param("idServiceStage") String idServiceStage);
	
	@Query("SELECT f from FichePFE f "
			+"join Convention c "
			+ "on c.conventionPK.idEt=f.idFichePFE.conventionPK.idEt "
			+ "where " 
			+ "f.validDepot='02' and " //+ "f.etatFiche ='03' and "
			+ "c.traiter = '02' "
			+ "order by f.dateDepotReports desc")
	List<FichePFE> getFichesDepotValAll();
	
	@Query("SELECT f from FichePFE f "
			+"join Convention c "
			+ "on c.conventionPK.idEt=f.idFichePFE.conventionPK.idEt "
			+ "where " 
			+ "f.validDepot='03' and f.etatFiche ='03' and "
			+ "c.traiter = '02' and c.responsableServiceStage.idUserSce=:idServiceStage "
			+ "order by f.dateDepotReports desc")
	List<FichePFE> getFichesForDepotInc(@Param("idServiceStage") String idServiceStage);
	
	/*@Query("select DISTINCT fp from FichePFE fp  join InscriptionCJ i on i.studentInscription.idEt=fp.idFichePFE.conventionPK.idEt "
			+ "join fp.technologies p "
			+ "where ( p.name IN (:Techno) ) "
			+ " and ( :annee IS NULL or  :annee='' or i.id.anneeDeb=:annee ) "
			+ " and ( :classe IS NULL or  :classe='' or i.saisonClasse.id.codeCl=:classe ) "
			+ " and ( :etat IS NULL or  :etat='' or fp.etatFiche=:etat )"
			+ " and ( :codeFilere IS NULL or  :codeFilere='' or i.saisonClasse.classe.codeSpecialite=:codeFilere )  order by fp.idFichePFE.dateDepotFiche desc" 
			)*/
	@Query(value="select DISTINCT f.ID_ET, f.DATE_DEPOT_FICHE from  ESP_FICHE_PFE f  join SYN_ESP_INSCRIPTION i on f.ID_ET= i.ID_ET "
			+ "join  SYN_CLASSE c on i.CODE_CL=c.CODE_CL "
			+ "join ESP_FICHEPFE_TECHNOLOGIE FT on FT.DATE_DEPOT_FICHE=f.DATE_DEPOT_FICHE "
			+ "join ESP_TECHNOLOGIE T on T.ID_TECHNOLOGIE=FT.ID_TECHNOLOGIE"
			+ " where ( T.NAME IN ( :Techno ) ) "
			+ " and ( :annee IS NULL or  :annee='' or i.ANNEE_DEB=:annee ) "
			+ " and ( :classe IS NULL or  :classe='' or i.CODE_CL=:classe ) "
			+ " and ( :etat IS NULL or  :etat='' or f.ETAT_FICHE=:etat )"
			+ " and ( :codeFilere IS NULL or  :codeFilere='' or c.CODE_SPECIALITE=:codeFilere ) " ,
			nativeQuery = true)
	List<Object[]> findFichePFEAvancewithTechno(@Param("annee") String annee
			,@Param("classe") String classe ,
			@Param("Techno") List<String> Technos,
			@Param("etat") String etat,
			@Param("codeFilere") String codeFilere);
	
	/*@Query("select fp from FichePFE fp  join InscriptionCJ i on i.studentInscription.idEt=fp.idFichePFE.conventionPK.idEt "
			+ " where ( :annee IS NULL or  :annee='' or i.id.anneeDeb=:annee ) "
			+ " and ( :classe IS NULL or  :classe='' or i.saisonClasse.id.codeCl=:classe ) "
			+ " and ( :etat IS NULL or  :etat='' or fp.etatFiche=:etat )"
			+ " and ( :codeFilere IS NULL or  :codeFilere='' or i.saisonClasse.classe.codeSpecialite=:codeFilere )  order by fp.idFichePFE.dateDepotFiche desc" 
			)*/
	@Query(value="select * from  ESP_FICHE_PFE f  join SYN_ESP_INSCRIPTION i on f.ID_ET= i.ID_ET "
			+ "join  SYN_CLASSE c on i.CODE_CL=c.CODE_CL "
			+ " where ( :annee IS NULL or  :annee='' or i.ANNEE_DEB=:annee ) "
			+ " and ( :classe IS NULL or  :classe='' or i.CODE_CL=:classe ) "
			+ " and ( :etat IS NULL or  :etat='' or f.ETAT_FICHE=:etat )"
			+ " and ( :codeFilere IS NULL or  :codeFilere='' or c.CODE_SPECIALITE=:codeFilere ) " ,
			nativeQuery = true)
	List<FichePFE> findFichePFEAvance(@Param("annee") String annee
			,@Param("classe") String classe ,
			@Param("etat") String etat,
			@Param("codeFilere") String codeFilere);
	
	@Query("select fp from FichePFE fp where ( fp.idFichePFE.conventionPK.idEt=:idEt and fp.etatFiche !='01'  ) order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFE> findFichePFEByStudentPFE(@Param("idEt") String idEt);
	
	@Query("select fp.idFichePFE.dateDepotFiche,fp.etatFiche from FichePFE fp where ( fp.idFichePFE.conventionPK.idEt=:idEt and fp.etatFiche !='01' and fp.etatFiche !='05' ) order by fp.idFichePFE.dateDepotFiche desc")
	List<Object[]> findFichePFEByStudentNotDEPOSEEbyannee(@Param("idEt") String idEt);
	
	@Query("select fp.idFichePFE.dateDepotFiche,fp.etatFiche from FichePFE fp  where fp.idFichePFE.conventionPK.idEt=:idEt and fp.anneeDeb=:annee order by fp.idFichePFE.dateDepotFiche desc")
	List<Object[]> findFichePFEByStudentbyAnnee(@Param("idEt") String idEt,@Param("annee") String annee);
	
	@Query("select fp.titreProjet from FichePFE fp "
			+ "where ( fp.idFichePFE.conventionPK.idEt=:idEt and fp.etatFiche not in ('01', '04', '05') ) "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findFichePFEByStudentNotDEPOSEEsize(@Param("idEt") String idEt);
	
	
	/***/
	@Query("Select fp.binome from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findBinomeIdByStudentId(String idEt);
	
	@Query("select fp.pathDiagrammeGantt from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findGanttDiagramms(String idEt);
			
	@Query("select fp.titreProjet, fp.descriptionProjet, fp.anneeDeb, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')"
			+ " ,fp.etatFiche"
			+ " ,fp.binome"
			+ " ,fp.pathDiagrammeGantt"
			+ " from FichePFE fp where fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche asc")
	List<Object[]> findFichePFE(String idEt);
	
	@Query("select fp.idFichePFE.dateDepotFiche,fp.etatFiche from FichePFE fp where fp.idFichePFE.conventionPK.idEt=:idEt order by fp.idFichePFE.dateDepotFiche desc")
	List<Object[]> findFichePFEByStudentNotDEPOSEEbyannee2(@Param("idEt") String idEt);
	
	@Query("Select fp.idFichePFE.conventionPK.idEt from FichePFE fp where fp.etatFiche = '03' and fp.validDepot = '03' order by fp.idFichePFE.dateDepotFiche desc")
	List<String> findStudentsWithIncompletedDepot();
	


	@Query("Select new com.esprit.gdp.dto.StudentsByJuryActorForSTNDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			//+ "FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy'), fp.heureDebut, fp.salle.codeSalle, "
			+ "from StudentCJ u, InscriptionCJ y, FichePFE fp where "
			+ "u.idEt = y.id.idEt and u.idEt = fp.idFichePFE.conventionPK.idEt and "
			+ "(y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') and "
			+ "fp.presidentJuryEns.idEns =?1 and "
			+ "fp.session.idSession =?2 "
			+ "order by fp.dateSoutenance asc")
	List<StudentsByJuryActorForSTNDto> findStudentCJALTByJuryPresidentForSTN(String idMbr, Integer sessionId);
	
	@Query("Select new com.esprit.gdp.dto.StudentsByJuryActorForSTNDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			//+ "FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy'), fp.heureDebut, fp.salle.codeSalle, "
			+ "from StudentCS u, InscriptionCS y, FichePFE fp where "
			+ "u.idEt = y.id.idEt and u.idEt = fp.idFichePFE.conventionPK.idEt and "
			+ "y.saisonClasse.id.codeCl like '4%' and "
			+ "fp.presidentJuryEns.idEns =?1 and "
			+ "fp.session.idSession =?2 "
			+ "order by fp.dateSoutenance asc")
	List<StudentsByJuryActorForSTNDto> findStudentCSByJuryPresidentForSTN(String idMbr, Integer sessionId);

	
	@Query("Select new com.esprit.gdp.dto.StudentsByJuryActorForSTNDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			//+ "FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy'), fp.heureDebut, fp.salle.codeSalle, "
			+ "from StudentCJ u, InscriptionCJ y, FichePFE fp where "
			+ "u.idEt = y.id.idEt and u.idEt = fp.idFichePFE.conventionPK.idEt and "
			+ "(y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') and "
			+ "fp.expertEns.idEns =?1 and "
			+ "fp.session.idSession =?2 "
			+ "order by fp.dateSoutenance asc")
	List<StudentsByJuryActorForSTNDto> findStudentCJALTByJuryMemberForSTN(String idMbr, Integer sessionId);
	
	@Query("Select new com.esprit.gdp.dto.StudentsByJuryActorForSTNDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			//+ "FUNCTION('to_char', fp.dateSoutenance,'dd-mm-yyyy'), fp.heureDebut, fp.salle.codeSalle, "
			+ "from StudentCS u, InscriptionCS y, FichePFE fp where "
			+ "u.idEt = y.id.idEt and u.idEt = fp.idFichePFE.conventionPK.idEt and "
			+ "y.saisonClasse.id.codeCl like '4%' and "
			+ "fp.expertEns.idEns =?1 and "
			+ "fp.session.idSession =?2 "
			+ "order by fp.dateSoutenance asc")
	List<StudentsByJuryActorForSTNDto> findStudentCSByJuryMemberForSTN(String idMbr, Integer sessionId);
	
	@Query("Select distinct fp.idFichePFE.conventionPK.idEt from FichePFE fp where "
			+ "fp.presidentJuryEns.idEns =?1 and "
			+ "fp.session.idSession =?2")
	List<String> findStudentsIdByJuryPresident(String idPres, Integer sessionId);
	
	@Query("Select distinct fp.idFichePFE.conventionPK.idEt from FichePFE fp where "
			+ "fp.expertEns.idEns =?1 and "
			+ "fp.session.idSession =?2")
	List<String> findStudentsIdByJuryMembre(String idMbr, Integer sessionId);

	@Query("SELECT new com.esprit.gdp.dto.DepotFinalDto(f.idFichePFE, c.conventionPK.idEt, f.pathRapportVersion2, f.pathPlagiat, f.pathAttestationStage, f.pathSupplement, f.validDepot, FUNCTION('to_char', f.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS'), f.idFichePFE.dateDepotFiche) "
			+ "from FichePFE f join Convention c on c.conventionPK.idEt = f.idFichePFE.conventionPK.idEt "
			+ "where "
			+ "f.validDepot='02' and " //+ "f.etatFiche ='03' and "
			+ "c.traiter = '02' and "
			+ "f.idFichePFE.conventionPK.idEt in (?1) "
			// + "c.responsableServiceStage.idUserSce =?1 "
			+ "order by f.dateDepotReports desc")
	List<DepotFinalDto> loadFichesForDepotValByStudents(List<String> students); //;(String idServiceStage);

	@Query("SELECT new com.esprit.gdp.dto.DepotFinalDto(f.idFichePFE, c.conventionPK.idEt, f.pathRapportVersion2, f.pathPlagiat, f.pathAttestationStage, f.pathSupplement, f.validDepot, FUNCTION('to_char', f.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS'), f.idFichePFE.dateDepotFiche) "
			+ "from FichePFE f join Convention c on c.conventionPK.idEt = f.idFichePFE.conventionPK.idEt "
			+ "where "
			+ "f.validDepot='01' and f.etatFiche ='03' and " //+ "f.etatFiche ='03' and "
			+ "c.traiter = '02' and "
			+ "f.idFichePFE.conventionPK.idEt in (?1) "
			// + "c.responsableServiceStage.idUserSce =?1 "
			+ "order by f.dateDepotReports desc")
	List<DepotFinalDto> loadFichesForDepotNotYetTreatedByStudents(List<String> students);

}

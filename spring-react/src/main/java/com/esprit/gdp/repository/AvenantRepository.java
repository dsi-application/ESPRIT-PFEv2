package com.esprit.gdp.repository;
import java.util.Date;
import java.util.List;

import com.esprit.gdp.dto.AvenantForRSSDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.AvenantDateAvDateDebutStageStatusDto;
import com.esprit.gdp.dto.AvenantDto;
import com.esprit.gdp.dto.AvenantHistoryDto;
import com.esprit.gdp.models.Avenant;


@Repository
public interface AvenantRepository extends JpaRepository<Avenant, Integer>
{
	//Optional<Avenant> findById(Integer id);
	
	@Query("select a from Avenant a where a.avenantPK.conventionPK.idEt =?1 order by a.avenantPK.dateAvenant desc")
	List<Avenant> findAvenantByIdEt(String idEt);

	@Query("select c.pathSignedAvenant, FUNCTION('to_char', c.dateDepotSignedAvenant, 'dd-mm-yyyy')"
			+ " from Avenant c where c.avenantPK.conventionPK.idEt=?1 order by c.avenantPK.dateAvenant asc")
	List<Object[]> findSignedAvenant(String idEt);
	
	@Query("select FUNCTION('to_char', a.dateDebut,'dd-mm-yyyy'), FUNCTION('to_char', a.dateFin,'dd-mm-yyyy') from Avenant a "
			+ "where a.avenantPK.conventionPK.idEt =?1 "
			+ "order by a.avenantPK.dateAvenant desc")
	List<Object[]> findAvenantDateDebutFinStage(String idEt);
	
	@Query("select a from Avenant a where a.avenantPK.conventionPK.idEt =?1 and FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention,'yyyy-mm-dd HH24:MI:SS') =?2 order by a.avenantPK.dateAvenant desc")
	List<Avenant> findAvenantByIdEtAndDateConvention(String idEt, String dateConvention);
	
	@Query("select new com.esprit.gdp.dto.AvenantHistoryDto(FUNCTION('to_char', a.avenantPK.dateAvenant,'dd-mm-yyyy HH24:MI:SS'), "
			+ "FUNCTION('to_char', a.dateDebut,'dd-mm-yyyy'), "
			+ "FUNCTION('to_char', a.dateFin,'dd-mm-yyyy'), "
			+ "a.responsableEntreprise, a.qualiteResponsable) "
			+ "from Avenant a where "
			+ "a.avenantPK.conventionPK.idEt =?1 and "
			+ "a.traiter = '1' and "
			+ "FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') =?2 "
			+ "order by a.avenantPK.dateAvenant desc")
	List<AvenantHistoryDto> findAvenantDtoByIdEtAndDateConvention(String idEt, String dateConvention);
	
	@Query("select new com.esprit.gdp.dto.AvenantDateAvDateDebutStageStatusDto(FUNCTION('to_char', a.avenantPK.dateAvenant,'dd-mm-yyyy HH24:MI:SS'), a.dateDebut, a.traiter) "
			+ "from Avenant a "
			+ "where a.avenantPK.conventionPK.idEt =?1 and "
			+ "FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') =?2 "
			+ "order by a.avenantPK.dateAvenant desc")
	List<AvenantDateAvDateDebutStageStatusDto> findAvenantDateAvDateDebutStageStatusDtoByStudentAndConv(String idEt, String dateConvention);
	
	@Query("select a.responsableEntreprise "
			+ "from Avenant a "
			+ "where a.avenantPK.conventionPK.idEt =?1 and "
			+ "a.traiter = '1' and "
			+ "FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') =?2 "
			+ "order by a.avenantPK.dateAvenant desc")
	List<String> findValidatedAVNByStudentAndConv(String idEt, String dateConvention);
	
	@Query(value="SELECT a from Avenant a "
			+ " where ( FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention,'yyyy-mm-dd HH24:MI:SS')=:dateConvention "
			+ " and a.avenantPK.conventionPK.idEt=:idET "
			+ "and FUNCTION('to_char', a.avenantPK.dateAvenant,'yyyy-mm-dd HH24:MI:SS')=:DateAvenant ) ")
	Avenant getAvenant(@Param("idET") String idET ,@Param("dateConvention") String dateConvention ,@Param("DateAvenant") String DateAvenant);
	
	@Query(value="SELECT a from Avenant a  join Convention c "
			+ "on (a.avenantPK.conventionPK.idEt = c.conventionPK.idEt "
			+ "and a.avenantPK.conventionPK.dateConvention =c.conventionPK.dateConvention)"
			+ "where  c.responsableServiceStage.idUserSce=:idServiceStage")
	List<Avenant> getConventionsByServiceStage(@Param("idServiceStage") String idServiceStage);
	
	// Got All Avns List
	@Query(value="SELECT a from Avenant a  join Convention c "
			+ "on (a.avenantPK.conventionPK.idEt = c.conventionPK.idEt "
			+ "and a.avenantPK.conventionPK.dateConvention =c.conventionPK.dateConvention)"
			+ " order by a.avenantPK.dateAvenant desc")
	List<Avenant> getAllAvenants();

	@Query(value="SELECT new com.esprit.gdp.dto.AvenantForRSSDto(FUNCTION('to_char', a.avenantPK.dateAvenant,'dd-mm-yyyy HH24:MI:SS'), FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention, 'dd-mm-yyyy HH24:MI:SS'), a.dateDebut, a.dateFin, a.avenantPK.conventionPK.idEt, a.traiter, a.pathAvenant, a.responsableEntreprise, a.qualiteResponsable) " +
			"from InscriptionCJ y, Avenant a  join Convention c " +
			"on (a.avenantPK.conventionPK.idEt = c.conventionPK.idEt and a.avenantPK.conventionPK.dateConvention = c.conventionPK.dateConvention)" +
			"where a.avenantPK.conventionPK.idEt = y.id.idEt " +
			"and y.saisonClasse.id.codeCl like '5%'" +
			"and c.responsableServiceStage.idUserSce =?1 " +
			"and y.id.anneeDeb =?2 " +
			"order by a.avenantPK.dateAvenant desc")
	List<AvenantForRSSDto> findAllAvenantsCJByYear(String idRSS, String year);

	@Query(value="SELECT new com.esprit.gdp.dto.AvenantForRSSDto(FUNCTION('to_char', a.avenantPK.dateAvenant,'dd-mm-yyyy HH24:MI:SS'), FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention, 'dd-mm-yyyy HH24:MI:SS'), a.dateDebut, a.dateFin, a.avenantPK.conventionPK.idEt, a.traiter, a.pathAvenant, a.responsableEntreprise, a.qualiteResponsable) " +
			"from OptionStudentALT o, Avenant a  join Convention c " +
			"on (a.avenantPK.conventionPK.idEt = c.conventionPK.idEt and a.avenantPK.conventionPK.dateConvention = c.conventionPK.dateConvention)" +
			"where a.avenantPK.conventionPK.idEt = o.idOptStuALT.idEt " +
			"and c.responsableServiceStage.idUserSce =?1 " +
			"and o.idOptStuALT.anneeDeb =?2 " +
			"order by a.avenantPK.dateAvenant desc")
	List<AvenantForRSSDto> findAllAvenantsALTByYear(String idRSS, String year);

	@Query(value="SELECT new com.esprit.gdp.dto.AvenantForRSSDto(FUNCTION('to_char', a.avenantPK.dateAvenant,'dd-mm-yyyy HH24:MI:SS'), FUNCTION('to_char', a.avenantPK.conventionPK.dateConvention, 'dd-mm-yyyy HH24:MI:SS'), a.dateDebut, a.dateFin, a.avenantPK.conventionPK.idEt, a.traiter, a.pathAvenant, a.responsableEntreprise, a.qualiteResponsable) " +
			"from InscriptionCS y, Avenant a  join Convention c " +
			"on (a.avenantPK.conventionPK.idEt = c.conventionPK.idEt and a.avenantPK.conventionPK.dateConvention = c.conventionPK.dateConvention)" +
			"where a.avenantPK.conventionPK.idEt = y.id.idEt " +
			"and y.saisonClasse.id.codeCl like '4%'" +
			"and c.responsableServiceStage.idUserSce =?1 " +
			"and y.id.anneeDeb =?2 " +
			"order by a.avenantPK.dateAvenant desc")
	List<AvenantForRSSDto> findAllAvenantsCSByYear(String idRSS, String year);

}

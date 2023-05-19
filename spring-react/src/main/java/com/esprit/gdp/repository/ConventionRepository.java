package com.esprit.gdp.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.ConventionCompanyLabelDateDebutFinStageDto;
import com.esprit.gdp.dto.ConventionDateConvDateDebutStageStatusDto;
import com.esprit.gdp.dto.ConventionDateStatusDto;
import com.esprit.gdp.dto.ConventionForRSSDto;
import com.esprit.gdp.dto.ConventionHistoriqueDto;
import com.esprit.gdp.dto.ConventionPaysSecteurDto;
import com.esprit.gdp.dto.ConventionsValidatedForRSSDto;
import com.esprit.gdp.dto.EntrepriseAccueilForConvHistDto;
import com.esprit.gdp.dto.EntrepriseAccueilForFichePFEHistDto;
import com.esprit.gdp.dto.FichePFETitreProjetLabelCompanyDto;
import com.esprit.gdp.models.Convention;
import com.esprit.gdp.models.ConventionPK;
import com.esprit.gdp.models.EntrepriseAccueil;


@Repository
public interface ConventionRepository extends JpaRepository<Convention, ConventionPK>
{	
	
	@Query("select c from Convention c where "
			+ "c.conventionPK.idEt =?1 and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') =?2")
	Convention findConventionByStudentAndDateConvention(String idEt, String dateConvention);

	@Query("select c.pathSignedConvention, FUNCTION('to_char', c.dateDepotSignedConvention, 'dd-mm-yyyy')"
			+ " from Convention c where c.conventionPK.idEt=?1 order by c.conventionPK.dateConvention asc")
	List<Object[]> findSignedConvention(String idEt);
	
	@Query("select new com.esprit.gdp.dto.ConventionPaysSecteurDto(c.entrepriseAccueilConvention.pays.nomPays, c.entrepriseAccueilConvention.secteurEntreprise.libelleSecteur) from "
			+ "Convention c, FichePFE fp where "
			+ "c.conventionPK.dateConvention = fp.idFichePFE.conventionPK.dateConvention and "
			+ "c.conventionPK.idEt =?1 and "
			+ "FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS') =?2")
	ConventionPaysSecteurDto findConventionByStudentAndDateDepotFiche(String idEt, String dateDepotFiche);
	
	@Query("select c.dureeStageWeek from Convention c where "
			+ "c.conventionPK.idEt =?1 and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') =?2")
	Integer findDureeStageWeekByStudentAndDateConvention(String idEt, String dateConvention);
	
//	@Query("select new com.esprit.gdp.dto.ConventionHistoriqueDto (FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy'), "
//			+ "FUNCTION('to_char', c.dateDebut,'dd-mm-yyyy'), FUNCTION('to_char', c.dateFin,'dd-mm-yyyy'), c.responsable, c.address, c.telephone, c.traiter, "
//			+ "c.entrepriseAccueilConvention.designation, "
//	        + "c.entrepriseAccueilConvention.siegeSocial, c.entrepriseAccueilConvention.addressMail, c.entrepriseAccueilConvention.fax, "
//	        + "c.entrepriseAccueilConvention.pays.nomPays, c.entrepriseAccueilConvention.secteurEntreprise.libelleSecteur, c.motifAnnulation ) "
//			+ "from Convention c " HH24:MI:SS
//			+ "where c.conventionPK.idEt=?1 "
//			+ "order by c.conventionPK.dateConvention desc")
//	List<ConventionHistoriqueDto> findConventionsDtoHistoriqueByIdEt(String idEt);
	
	@Query("select new com.esprit.gdp.dto.ConventionHistoriqueDto (FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), "
			+ "FUNCTION('to_char', c.dateDebut,'dd-mm-yyyy'), FUNCTION('to_char', c.dateFin,'dd-mm-yyyy'), c.responsable, c.address, c.telephone, c.traiter, "
			+ "c.entrepriseAccueilConvention.designation, "
	        + "c.entrepriseAccueilConvention.addressMail, c.entrepriseAccueilConvention.siegeSocial, " + 
	        " c.entrepriseAccueilConvention.fax, "
	        + "c.motifAnnulation ) "
			+ "from Convention c "
			+ "where c.conventionPK.idEt=?1 and "
			+ "c.traiter != '01' "
			+ "order by c.conventionPK.dateConvention desc")
	List<ConventionHistoriqueDto> findConventionsDtoHistoriqueByIdEt(String idEt);
	
	@Query("Select CONCAT(FUNCTION('to_char', c.dateDebut,'dd-mm-yyyy'), '**', FUNCTION('to_char', c.dateFin,'dd-mm-yyyy')) from Convention c where c.conventionPK.idEt =?1")
	String findStudentDateDebutFinStageByIdEt(String idEt);
	
	@Query("select c.entrepriseAccueilConvention "
			+ "from Convention c "
			+ "where c.conventionPK.idEt=?1 and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS')=?2")
	EntrepriseAccueil findentrepriseAccueilByConvention(String idEt, String dateConvention);
	
//	@Query("select new com.esprit.gdp.dto.ConventionHistoriqueDto (FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy')) "
//			+ "from Convention c "
//			+ "where c.conventionPK.idEt=?1 "
//			+ "order by c.conventionPK.dateConvention desc")
//	List<ConventionHistoriqueDto> findConventionsDtoHistoriqueByIdEt(String idEt);
	
	@Query("select new com.esprit.gdp.dto.EntrepriseAccueilForConvHistDto (c.entrepriseAccueilConvention.designation, c.responsable, "
	        + "c.entrepriseAccueilConvention.siegeSocial, c.entrepriseAccueilConvention.addressMail, c.entrepriseAccueilConvention.fax, "
	        + "c.entrepriseAccueilConvention.pays.nomPays, c.entrepriseAccueilConvention.secteurEntreprise.libelleSecteur, c.motifAnnulation ) "
			+ "from Convention c "
			+ "where c.conventionPK.idEt=?1 "
			+ "order by c.conventionPK.dateConvention desc")
	List<EntrepriseAccueilForConvHistDto> findEntrepriseAccueilForConvHistDtoByIdEt(String idEt);
	
//	@Query("select new com.esprit.gdp.dto.EntrepriseAccueilForFichePFEHistDto (c.entrepriseAccueilConvention.designation, c.entrepriseAccueilConvention.siegeSocial, c.entrepriseAccueilConvention.addressMail, "
//			+ "c.entrepriseAccueilConvention.telephone, c.entrepriseAccueilConvention.fax, "
//	        + "c.entrepriseAccueilConvention.pays.nomPays, c.entrepriseAccueilConvention.secteurEntreprise.libelleSecteur, c.entrepriseAccueilConvention.address) "
//			+ "from FichePFE f, Convention c where "
//			+ "f.idFichePFE.conventionPK.idEt=?1 and "
//			+ "f.idFichePFE.conventionPK = c.conventionPK and "
//			+ "FUNCTION('to_char', f.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2")
//	EntrepriseAccueilForFichePFEHistDto findEntrepriseAccueilForFichePFEHistDtoByIdEtAndFichePFE(String idEt, String dateDepotFiche);
	
	@Query("select new com.esprit.gdp.dto.EntrepriseAccueilForFichePFEHistDto (c.entrepriseAccueilConvention.designation, c.entrepriseAccueilConvention.siegeSocial, c.entrepriseAccueilConvention.addressMail, "
			+ "c.entrepriseAccueilConvention.telephone, c.entrepriseAccueilConvention.fax, "
	        + "c.entrepriseAccueilConvention.address) "
			+ "from FichePFE f, Convention c where "
			+ "f.idFichePFE.conventionPK.idEt=?1 and "
			+ "f.idFichePFE.conventionPK = c.conventionPK and "
			+ "FUNCTION('to_char', f.idFichePFE.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2")
	EntrepriseAccueilForFichePFEHistDto findEntrepriseAccueilForFichePFEHistDtoByIdEtAndFichePFE(String idEt, String dateDepotFiche);
	
	@Query("select new com.esprit.gdp.dto.ConventionCompanyLabelDateDebutFinStageDto (c.entrepriseAccueilConvention.designation, FUNCTION('to_char', c.dateDebut,'dd-mm-yyyy'), FUNCTION('to_char', c.dateFin,'dd-mm-yyyy')) "
			+ "from Convention c "
			+ "where c.conventionPK.idEt=?1 "
			+ "order by c.conventionPK.dateConvention asc")
	List<ConventionCompanyLabelDateDebutFinStageDto> findConventionCompanyLabelDateDebutFinStageDtoByIdEt(String idEt);
	
	@Query("select new com.esprit.gdp.dto.FichePFETitreProjetLabelCompanyDto (f.titreProjet, c.entrepriseAccueilConvention.designation) "
			+ "from FichePFE f, Convention c where "
			+ "f.idFichePFE.conventionPK = c.conventionPK and "
			+ "f.idFichePFE.conventionPK.idEt=?1 and "
			+ "f.etatFiche in ('03', '06', '07', '08')"
			+ "order by f.idFichePFE.dateDepotFiche")
	FichePFETitreProjetLabelCompanyDto findTitreProjectAndLabelCompanyByStudent(String idEt);
	
	@Query("select c.entrepriseAccueilConvention.designation "
			+ "from Convention c "
			+ "where c.conventionPK.idEt=?1 "
			+ "order by c.conventionPK.dateConvention desc")  // DONT FORGOT
	List<String> findCompanyNameByIdEt(String idEt);
	
	@Query("select FUNCTION('to_char', c.dateDebut,'dd-mm-yyyy'), FUNCTION('to_char', c.dateFin,'dd-mm-yyyy') from Convention c "
			+ "where c.conventionPK.idEt=?1 "
			+ "order by c.conventionPK.dateConvention desc")
	List<Object[]> findConventionDateDebutFinStage(String idEt);
	
	@Query("select new com.esprit.gdp.dto.ConventionDateStatusDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy'), c.traiter) "
			+ "from Convention c "
			+ "where c.conventionPK.idEt=?1 "
			+ "order by c.conventionPK.dateConvention desc")
	List<ConventionDateStatusDto> findConventionDateStatusDtoByIdEt(String idEt);
	
	@Query("select CONCAT(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy'), '$', c.traiter) "
			+ "from Convention c where c.conventionPK.idEt =?1 "
			+ "order by c.conventionPK.dateConvention desc")
	List<String> findDateAndEtatConventionByIdEt(String idEt);
	
	@Query("select FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') "
			+ "from Convention c where c.conventionPK.idEt =?1 "
			+ "order by c.conventionPK.dateConvention desc")
	List<String> findDateConventionByIdEt(String idEt);
	
	@Query("select new com.esprit.gdp.dto.ConventionDateConvDateDebutStageStatusDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy'), c.dateDebut, c.traiter) "
			+ "from Convention c where c.conventionPK.idEt =?1 "
			+ "order by c.conventionPK.dateConvention desc")
	List<ConventionDateConvDateDebutStageStatusDto> findConventionDateConvDateDebutStageStatusByIdEt(String idEt);
	
	@Query("select new com.esprit.gdp.dto.ConventionDateConvDateDebutStageStatusDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy'), c.dateDebut, c.traiter) "
			+ "from Convention c where c.conventionPK.idEt =?1 and c.traiter in ('02', '04') "
			+ "order by c.conventionPK.dateConvention desc")
	List<ConventionDateConvDateDebutStageStatusDto> findValidatedConventionDateConvDateDebutStageStatusByIdEt(String idEt);
	
	@Query("select c from Convention c where c.conventionPK.idEt =?1 order by c.conventionPK.dateConvention desc")
	List<Convention> findConventionByIdEt(String idEt);
	
	@Query("select c.pathConvention from Convention c where c.conventionPK.idEt =?1 order by c.conventionPK.dateConvention desc")
	List<String> findPathConventionByIdEt(String idEt);
	
//	@Query("select c from new com.esprit.gdp.dto.ResponsibleHistoryDto() from Convention c where c.conventionPK.idEt =?1 order by c.conventionPK.dateConvention desc")
//	List<ResponsibleHistoryDto> dd(String idEt);
	
	@Query("select CONCAT(c.traiter, '$', c.entrepriseAccueilConvention.pays.langueCode) from Convention c where c.conventionPK.idEt =?1 order by c.conventionPK.dateConvention desc")
	List<String> findExistConventionTRAITEEByIdEt(String idEt);
	
	@Query("select c from Convention c where c.conventionPK.idEt =?1 order by c.conventionPK.dateConvention desc")
	List<Optional<Convention>> findConventionOPTByIdEt(String idEt);
	
	@Query("select c.dateDebut from Convention c where c.conventionPK.idEt =?1 order by c.conventionPK.dateConvention desc")
	List<Date> findDateDebutConventionByStudent(String idEt);
//	@Query("select FUNCTION('to_char', c.dateDebut,'dd-mm-yyyy') from Convention c where c.conventionPK.idEt =?1 order by c.conventionPK.dateConvention desc")
//	List<String> findDateDebutConventionByStudent(String idEt);
	
	@Query("select c.responsableServiceStage.idUserSce from Convention c where c.conventionPK.idEt =?1 order by FUNCTION('to_char', c.conventionPK.dateConvention,'yyyy-mm-dd HH24:MI:SS') desc")
	List<String> findResponsableServiceStageByIdEt(String idEt);

	@Query("select c.responsableServiceStage.nomUserSce from Convention c where c.conventionPK.idEt =?1 order by FUNCTION('to_char', c.conventionPK.dateConvention,'yyyy-mm-dd HH24:MI:SS') desc")
	List<String> findNameResponsableServiceStageByIdEt(String idEt);
	
	@Query(value="SELECT c from Convention c "
			+ " where (c.conventionPK.idEt=:idET and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'yyyy-mm-dd HH24:MI:SS')=:date )")
	Optional<Convention> getConventionById(@Param("idET") String idET , @Param("date") String date);
	
	@Query(value="SELECT c from Convention c "
			+ " where (c.conventionPK.idEt=:idET and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS')=:date )")
	Optional<Convention> getConventionByIdFD(@Param("idET") String idET , @Param("date") String date);
	
	@Query(value="SELECT c from Convention c "
			+ " where (c.conventionPK.idEt=:idET and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS')=:date )")
	Optional<Convention> getConventionByIdFormeeDate(@Param("idET") String idET , @Param("date") String date);
	
	@Query(value="SELECT c from Convention c "
			+ " where (c.conventionPK.idEt=:idET and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS')=:date )")
	Optional<Convention> getConventionByIdFormedDate(@Param("idET") String idET , @Param("date") String date);
	
	@Query(value="SELECT c from Convention c where c.responsableServiceStage.idUserSce=:idServiceStage")
	List<Convention> getConventionsByServiceStage(@Param("idServiceStage") String idServiceStage);
	
	/*
	private String dateConvention;
	private Date dateDebut;
	private Date dateFin;
	private String idEt;
	private String nomEt;
	private String departEt;
	private String paysConvention;
	*/
	// Got All Conv DTO for RSS
	// @Query(value="SELECT new com.esprit.gdp.dto.ConventionForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention.pays.langueCode, c.traiter, c.pathConvention, c.entrepriseAccueilConvention) from Convention c order by c.conventionPK.dateConvention desc")
	@Query(value="SELECT new com.esprit.gdp.dto.ConventionForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention.pays.langueCode, c.traiter, c.pathConvention, c.entrepriseAccueilConvention) from Convention c where c.traiter = '01' order by c.conventionPK.dateConvention desc")
	List<ConventionForRSSDto> getAllConventionsDtoForRSS();

	// Got All Conv Non Traitées DTO for RSS
	@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.traiter, c.entrepriseAccueilConvention.pays.langueCode, c.pathConvention, c.entrepriseAccueilConvention) "
			    + "from Convention c where c.traiter = '02' "
			    + "and FUNCTION('to_char', c.conventionPK.dateConvention,'mm')=?1 "
			    + "and c.conventionPK.idEt not in ('171JMT1867', '181SMT2040') "
			    // + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 00:39:16', '25-02-2022 01:57:37', '25-02-2022 02:03:12', '25-02-2022 15:29:19', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
			    // + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 15:39:29', '25-02-2022 15:48:49', '25-02-2022 16:03:43', '25-02-2022 16:06:28', '25-02-2022 17:28:12', '25-02-2022 18:02:35', '25-02-2022 18:03:51', '25-02-2022 18:06:26', '25-02-2022 18:25:52', '25-02-2022 18:36:23', '25-02-2022 18:59:31', '25-02-2022 20:32:28', '25-02-2022 21:12:03', '25-02-2022 21:22:23', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
			    // + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 00:39:16', '25-02-2022 01:57:37', '25-02-2022 02:03:12', '25-02-2022 15:29:19', '25-02-2022 15:39:29', '25-02-2022 15:48:49', '25-02-2022 16:03:43', '25-02-2022 16:06:28', '25-02-2022 17:28:12', '25-02-2022 18:02:35', '25-02-2022 18:03:51', '25-02-2022 18:06:26', '25-02-2022 18:25:52', '25-02-2022 18:36:23', '25-02-2022 18:59:31', '25-02-2022 20:32:28', '25-02-2022 21:12:03', '25-02-2022 21:22:23', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
			    + "order by c.conventionPK.dateConvention desc")
	//@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention) from Convention c where c.traiter = '02' order by c.conventionPK.dateConvention desc")
	List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDtoForRSS(String idMonth);

	@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.traiter, c.entrepriseAccueilConvention.pays.langueCode, c.pathConvention, c.pathSignedConvention, c.entrepriseAccueilConvention) "
		    + "from Convention c where c.traiter = '02' "
		    + "and FUNCTION('to_char', c.conventionPK.dateConvention,'mm')=?1 "
		    + "and c.conventionPK.idEt not in ('171JMT1867', '181SMT2040') "
		    // + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 00:39:16', '25-02-2022 01:57:37', '25-02-2022 02:03:12', '25-02-2022 15:29:19', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
		    // + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 15:39:29', '25-02-2022 15:48:49', '25-02-2022 16:03:43', '25-02-2022 16:06:28', '25-02-2022 17:28:12', '25-02-2022 18:02:35', '25-02-2022 18:03:51', '25-02-2022 18:06:26', '25-02-2022 18:25:52', '25-02-2022 18:36:23', '25-02-2022 18:59:31', '25-02-2022 20:32:28', '25-02-2022 21:12:03', '25-02-2022 21:22:23', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
		    // + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 00:39:16', '25-02-2022 01:57:37', '25-02-2022 02:03:12', '25-02-2022 15:29:19', '25-02-2022 15:39:29', '25-02-2022 15:48:49', '25-02-2022 16:03:43', '25-02-2022 16:06:28', '25-02-2022 17:28:12', '25-02-2022 18:02:35', '25-02-2022 18:03:51', '25-02-2022 18:06:26', '25-02-2022 18:25:52', '25-02-2022 18:36:23', '25-02-2022 18:59:31', '25-02-2022 20:32:28', '25-02-2022 21:12:03', '25-02-2022 21:22:23', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
		    + "order by c.conventionPK.dateConvention desc")
	//@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention) from Convention c where c.traiter = '02' order by c.conventionPK.dateConvention desc")
	List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDto2ForRSS(String idMonth);

	// Got All Conv Validated DTO By RSS
	@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.traiter, c.entrepriseAccueilConvention.pays.langueCode, c.pathConvention, c.entrepriseAccueilConvention) "
			+ "from Convention c where c.traiter = '02' "
			+ "and c.responsableServiceStage.idUserSce like CONCAT('SR-STG-', ?1) "
		    + "and FUNCTION('to_char', c.conventionPK.dateConvention,'mm')=?2 "
		    + "and c.conventionPK.idEt not in ('171JMT1867', '181SMT2040') "
			+ "order by c.conventionPK.dateConvention desc")
	//@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention) from Convention c where c.traiter = '02' and c.responsableServiceStage.idUserSce like CONCAT('SR-STG-', ?1) order by c.conventionPK.dateConvention desc")
	List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDtoByRSS(String kindRSS, String idMonth);

	// Got All Conv Validated DTO By RSS
	@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.traiter, c.entrepriseAccueilConvention.pays.langueCode, c.pathConvention, c.pathSignedConvention, c.entrepriseAccueilConvention) "
			+ "from Convention c where c.traiter = '02' "
			+ "and c.responsableServiceStage.idUserSce like CONCAT('SR-STG-', ?1) "
		    + "and FUNCTION('to_char', c.conventionPK.dateConvention,'mm')=?2 "
		    + "and c.conventionPK.idEt not in ('171JMT1867', '181SMT2040') "
			+ "order by c.conventionPK.dateConvention desc")
	//@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention) from Convention c where c.traiter = '02' and c.responsableServiceStage.idUserSce like CONCAT('SR-STG-', ?1) order by c.conventionPK.dateConvention desc")
	List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDto2ByRSS(String kindRSS, String idMonth);
	
	// Got All Conv DTO By RSS
	@Query(value="SELECT new com.esprit.gdp.dto.ConventionForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention.pays.langueCode, c.traiter, c.pathConvention, c.entrepriseAccueilConvention) from Convention c where c.traiter = '01' and c.responsableServiceStage.idUserSce like CONCAT('SR-STG-', ?1) order by c.conventionPK.dateConvention desc")
	List<ConventionForRSSDto> getAllConventionsDtoByRSS(String idRSS);
		
	@Query(value="SELECT new com.esprit.gdp.dto.ConventionForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention.pays.langueCode, c.traiter, c.pathConvention, c.entrepriseAccueilConvention) from Convention c where c.conventionPK.idEt =?1 and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') =?2")
	ConventionForRSSDto findConventionsByStudentDateDepot(String idEt, String dateDepotConv);
		
	// Got All Conv
	@Query(value="SELECT c from Convention c order by c.conventionPK.dateConvention desc")
	List<Convention> getAllConventions();
	
	// Got All Demandes Annulation Convention
	@Query(value="SELECT c from Convention c where c.traiter in ('03', '04') order by c.conventionPK.dateConvention desc")
//	@Query(value="SELECT c from Convention c order by c.conventionPK.dateConvention desc")
	List<Convention> getAllDemandesAnnulationConvention();
	
	@Query(value="SELECT c from Convention c where c.traiter = '04' order by c.conventionPK.dateConvention desc")
	List<Convention> getAllDemandesAnnulationConventionNotTreated();
	
	@Query(value="SELECT c from Convention c where c.traiter = '01' order by c.conventionPK.dateConvention desc")
	List<Convention> getAllDepositedConvention();
	
	@Query(value="SELECT c from Convention c where c.traiter='02' ")
	List<Convention> getConventionsForMailing();
	
	@Query(value="SELECT c from Convention c where c.traiter = '02' order by c.conventionPK.dateConvention desc")
	List<Convention> getAllValidatedConvention();

	@Query(value="SELECT c from Convention c "
			+ " where (c.conventionPK.idEt=:idET and "
			+ "FUNCTION('to_char', c.conventionPK.dateConvention,'yyyy-mm-dd HH24:MI:SS')=:date )")
	Optional<Convention> getConventionByIdFormedDateNew(@Param("idET") String idET , @Param("date") String date);

	@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.traiter, c.entrepriseAccueilConvention.pays.langueCode, c.pathConvention, c.pathSignedConvention, c.entrepriseAccueilConvention) "
			+ "from Convention c where c.traiter = '02' "
			+ "and c.conventionPK.idEt IN ?1 "
			+ "and c.conventionPK.idEt not in ('171JMT1867', '181SMT2040') "
			// + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 00:39:16', '25-02-2022 01:57:37', '25-02-2022 02:03:12', '25-02-2022 15:29:19', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
			// + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 15:39:29', '25-02-2022 15:48:49', '25-02-2022 16:03:43', '25-02-2022 16:06:28', '25-02-2022 17:28:12', '25-02-2022 18:02:35', '25-02-2022 18:03:51', '25-02-2022 18:06:26', '25-02-2022 18:25:52', '25-02-2022 18:36:23', '25-02-2022 18:59:31', '25-02-2022 20:32:28', '25-02-2022 21:12:03', '25-02-2022 21:22:23', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
			// + "and FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS') in ('25-02-2022 00:39:16', '25-02-2022 01:57:37', '25-02-2022 02:03:12', '25-02-2022 15:29:19', '25-02-2022 15:39:29', '25-02-2022 15:48:49', '25-02-2022 16:03:43', '25-02-2022 16:06:28', '25-02-2022 17:28:12', '25-02-2022 18:02:35', '25-02-2022 18:03:51', '25-02-2022 18:06:26', '25-02-2022 18:25:52', '25-02-2022 18:36:23', '25-02-2022 18:59:31', '25-02-2022 20:32:28', '25-02-2022 21:12:03', '25-02-2022 21:22:23', '25-02-2022 21:47:21', '25-02-2022 22:06:42', '25-02-2022 23:06:58') "
			+ "order by c.conventionPK.dateConvention desc")
		//@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention) from Convention c where c.traiter = '02' order by c.conventionPK.dateConvention desc")
	List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDtoByStudentsForRSS(List<String> students);

	// Got All Conv Validated DTO By RSS
	@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.traiter, c.entrepriseAccueilConvention.pays.langueCode, c.pathConvention, c.pathSignedConvention, c.entrepriseAccueilConvention) "
			+ "from Convention c where c.traiter = '02' "
			+ "and c.responsableServiceStage.idUserSce like CONCAT('SR-STG-', ?1) "
			+ "and c.conventionPK.idEt IN ?2 "
			+ "and c.conventionPK.idEt not in ('171JMT1867', '181SMT2040') "
			+ "order by c.conventionPK.dateConvention desc")
	//@Query(value="SELECT new com.esprit.gdp.dto.ConventionsValidatedForRSSDto(FUNCTION('to_char', c.conventionPK.dateConvention,'dd-mm-yyyy HH24:MI:SS'), c.dateDebut, c.dateFin, c.conventionPK.idEt, c.entrepriseAccueilConvention) from Convention c where c.traiter = '02' and c.responsableServiceStage.idUserSce like CONCAT('SR-STG-', ?1) order by c.conventionPK.dateConvention desc")
	List<ConventionsValidatedForRSSDto> getAllConventionsValidatedDtoByStudentsForSpeceficRSS(String kindRSS, List<String> students);

}
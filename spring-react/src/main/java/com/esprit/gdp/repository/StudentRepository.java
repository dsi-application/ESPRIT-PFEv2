package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.EncadrementRSSStatusExcelDto;
import com.esprit.gdp.dto.EncadrementStatusExcelDto;
import com.esprit.gdp.dto.ExpertiseStatusExcelDto;
import com.esprit.gdp.dto.StudentConfigDto;
import com.esprit.gdp.dto.StudentConvFRDto;
import com.esprit.gdp.dto.StudentDto;
import com.esprit.gdp.dto.StudentExcelDto;
import com.esprit.gdp.dto.StudentFullNameMailTelDto;
import com.esprit.gdp.dto.StudentIdFullNameDto;
import com.esprit.gdp.dto.StudentIdNomPrenomDto;
import com.esprit.gdp.dto.StudentMailTelDto;
import com.esprit.gdp.dto.StudentNoteEngTrainingshipDto;
import com.esprit.gdp.dto.StudentSpeedDto;
import com.esprit.gdp.dto.StudentToBeExpertisedForTimelineDto;
import com.esprit.gdp.dto.StudentToBeSupervisedForTimelineDto;
import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.models.StudentCS;


@Repository
public interface StudentRepository extends JpaRepository<StudentCJ, String>
{
	
	StudentCJ findByToken(String token);

	@Query("Select u from StudentCS u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCS y where y.saisonClasse.id.anneeDeb= '2021' "
			+ "and y.saisonClasse.id.codeCl like '4%')")
	Optional<StudentCS> findCSStudent(String idEt);
	
	@Query("Select u from StudentCS u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCS y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and y.saisonClasse.id.codeCl like '4%')")
	Optional<StudentCS> findCSStudentWithActivatedYears(String idEt, List<String> activatedYears);
	
	@Query("Select u from StudentCJ u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.anneeDeb= '2021' "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'))")
	Optional<StudentCJ> findCJStudent(String idEt);
	
	@Query("Select u from StudentCJ u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'))")
	Optional<StudentCJ> findCJStudentWithActivatedYears(String idEt, List<String> activatedYears);
	
	
	Optional<StudentCJ> findByIdEt(String idEt);
	
	@Query("Select u from StudentCJ u where u.idEt=?1")
	StudentCJ findStudentById(String idEt);
	
	@Query("Select u.adressemailesp from StudentCJ u where u.idEt=?1")
	String findStudentCJById(String idEt);
	
	@Query("Select u.adressemailesp from StudentCS u where u.idEt=?1")
	String findStudentCSById(String idEt);
	
	@Query("Select u.adressemailesp from StudentCJ u where u.idEt=?1")
	String findStudentMailById(String idEt);
	
	@Query("Select u.adressemailesp from StudentCJ u where u.idEt=?1")
	List<String> findStudentMailCJById(String idEt);
	

	@Query("Select u from StudentCJ u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'))")
	Optional<StudentCJ> findCJStudentByIdWithActivatedYears(String mail, List<String> activatedYears);

	@Query("Select u from StudentCJ u where u.token =?1")
	Optional<StudentCJ> findStudentByToken(String token);
	
	@Query("Select u from StudentCS u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCS y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and y.saisonClasse.id.codeCl like '4%')")
	Optional<StudentCS> findCSStudentByIdWithActivatedYears(String mail, List<String> activatedYears);
	
	@Query("Select u.idEt from StudentCJ u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'))")
	List<String> findIdCJStudentByIdWithActivatedYears(String mail, List<String> activatedYears);

	@Query("Select u.idEt from StudentCS u where u.idEt=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCS y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and y.saisonClasse.id.codeCl like '4%')")
	List<String> findIdCSStudentByIdWithActivatedYears(String mail, List<String> activatedYears);
	
	
	@Query("Select u.idEt from StudentCJ u where u.token=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'))")
	List<String> findIdCJStudentByTokenWithActivatedYears(String mail, List<String> activatedYears);

	@Query("Select u.idEt from StudentCS u where u.token=?1 and u.idEt in "
			+ "(select y.id.idEt from InscriptionCS y where y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and y.saisonClasse.id.codeCl like '4%')")
	List<String> findIdCSStudentByTokenWithActivatedYears(String mail, List<String> activatedYears);
		
	@Query("Select u.adressemailesp from StudentCS u where u.idEt=?1")
	List<String> findStudentMailCSById(String idEt);
	
	@Query("Select u.pwdJWTEtudiant from StudentCJ u where u.idEt=?1")
	String findStudentJWTPWDById(String idEt);
	
	@Query("Select u.pwdJWTEtudiantCS from StudentCS u where u.idEt=?1")
	String findStudentJWTPWDCSById(String idEt);
	
//	@Query("Select u.idEt from StudentCS u where u.idEt in "
//			+ "(select y.id.idEt from InscriptionCS y where y.saisonClasse.id.anneeDeb= '2021' "
//			+ "and y.saisonClasse.id.codeCl like '4%')")
//	List<String> findCSStudents();
//	
//	@Query("Select u.idEt from StudentCJ u where u.idEt in "
//			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.anneeDeb= '2021' "
//			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'))")
//	List<String> findCJALTStudents();
	
	@Query("Select u.idEt from StudentCS u where u.idEt in "
			+ "(select y.id.idEt from InscriptionCS y where y.saisonClasse.id.anneeDeb IN (?1) " //IN ('2021', '2022') "
			+ "and y.saisonClasse.id.codeCl like '4%')")
	List<String> findCSStudents(List<String> activatedYears);
	
	@Query("Select u.idEt from StudentCJ u where u.idEt in "
			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.anneeDeb IN (?1) " //IN ('2021', '2022') "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%'))")
	List<String> findCJALTStudents(List<String> activatedYears);
	
	//**  y.codeCl IN (?1)
	//** @Query("Select u from StudentCJ u where u.idEt=?1 and FS_CRYPT_DECRYPT(u.pwdet)=?2 and u.classecourantet like '5%'")
	// @Query("Select u from StudentCJ u where u.idEt=?1 and FS_CRYPT_DECRYPT(u.pwdet)=?2")
	// @Query("Select u from StudentCJ u where u.idEt=?1 and FS_CRYPT_DECRYPT(u.pwdet)=?2 and u.idEt in (select y.id.idEt from InscriptionCJ y where y.id.anneeDeb = '2021' and y.codeCl like '5%')")
	@Query("Select u from StudentCJ u where u.idEt=?1 and FS_CRYPT_DECRYPT(u.pwdet)=?2 and u.idEt "
			+ "in (select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%' "
			//+ "and (y.codeDecisionSessionPrincipale = '06' or y.codeDecisionSessionRattrappage = '06')"
			+ ")")
	Optional<StudentCJ> firstAuthentication(String idEt, String pwdet);
	
	@Query("Select u from StudentCS u where u.idEt=?1 and u.pwdet=?2 and u.idEt "
			+ "in (select y.id.idEt from InscriptionCS y where y.saisonClasse.id.codeCl like '4%' "
			+ ")")
	Optional<StudentCS> firstAuthenticationCS(String idEt, String pwdet);
	
	@Query("Select u from StudentCJ u where u.idEt=?1")
	Optional<StudentCJ> forgotPassword(String idEt);
	
	
	@Query("Select u.idEt from StudentCJ u where u.idEt=?1")
	String lol(String idEt);
	
	@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) from StudentCJ u where u.idEt=?1")
	String findStudentFullNameById(String idEt);
	
	@Query("Select trim(u.prenomet) from StudentCJ u where u.idEt=?1")
	String findStudentPrenomById(String idEt);
	
	
	// StudentCJ Full Name
	@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and u.idEt=?1 "
			+ "and y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%')")
	List<String> findStudentsFullNameCJandALT(String idEt, List<String> activatedYears);
	
	@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and u.idEt=?1 "
			+ "and y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<String> findStudentsFullNameCS(String idEt, List<String> activatedYears);
	
	/*@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) from StudentCJ u "
			+ "where u.idEt=?1 and u.idEt "
			+ "in (select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.codeCl like '5%' "
			+ "and y.saisonClasse.id.anneeDeb= '2021')")
	String findStudentCJFullNameById(String idEt);
	
	@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) from StudentCJ u "
			+ "where u.idEt=?1 and u.idEt "
			+ "in (select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.codeCl like '4ALINFO%' "
			+ "and y.saisonClasse.id.anneeDeb= '2021')")
	String findStudentCJALTFullNameById(String idEt);
	
	@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) from StudentCS u "
			+ "where u.idEt=?1 and u.idEt "
			+ "in (select y.id.idEt from InscriptionCS y where y.saisonClasse.id.codeCl like '4%' "
			+ "and y.saisonClasse.id.anneeDeb= '2021')")
	String findStudentCSFullNameById(String idEt);
	*/
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb =?1 and lower(y.saisonClasse.id.codeCl) =?2 "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByYearAndClassCJ(String year, String codeCl);
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb = '2021' and y.saisonClasse.id.codeCl =?1 "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByClassCJ(String codeCl);
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb = '2021' and u.idEt =?1 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETCJALT(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb = '2021' and u.idEt =?1 "
			+ "and y.saisonClasse.id.codeCl like '4%' "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETCS(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb = '2021' and lower(y.saisonClasse.id.codeCl) =?1 "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByClassCS(String codeClass);
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb =?1 and lower(y.saisonClasse.id.codeCl) =?2 "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByYearAndClassCS(String year, String codeClass);
	
	
	// Find Encadrement Status By Option
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogique.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5', ?1, '%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCJByOption(String optionlabel);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogique.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5', ?2, '%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCJByYearAndOption(String year, String optionlabel);
	
	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCJ.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5', ?1, '%')")
	List<ExpertiseStatusExcelDto> findExpertisetatusCJByOption(String optionlabel);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogique.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCJALTByOption();
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogiqueCS.idEns, '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogiqueCS.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?1, '%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCSByOption(String optionlabel);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogiqueCS.idEns, '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogiqueCS.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?2, '%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCSByYearAndOption(String year, String optionlabel);
	
	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCS.idEns, '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogiqueCS.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?1, '%')")
	List<ExpertiseStatusExcelDto> findExpertiseStatusCSByOption(String optionlabel);
	
	// Find Encadrement Status Global
	@Query("Select new com.esprit.gdp.dto.EncadrementRSSStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '', y.encadrantPedagogique.idEns, '', '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns is not null "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and y.saisonClasse.id.codeCl like '5%'")
	List<EncadrementRSSStatusExcelDto> findEncadrementStatusCJ();
	
	@Query("Select new com.esprit.gdp.dto.EncadrementRSSStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '', y.encadrantPedagogique.idEns, '', '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns is not null "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<EncadrementRSSStatusExcelDto> findEncadrementStatusCJALT();
	
	@Query("Select new com.esprit.gdp.dto.EncadrementRSSStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '', y.encadrantPedagogiqueCS.idEns, '', '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogiqueCS.idEns is not null "
			//+ "and y.encadrantPedagogiqueCS.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<EncadrementRSSStatusExcelDto> findEncadrementStatusCS();
	
	// find StudentCJ Note Eng Trainingship By AE
	@Query("Select new com.esprit.gdp.dto.StudentToBeSupervisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentToBeSupervisedForTimelineDto> findStudentToBeSupervisedForTimelineDtoCJandALTByAE(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.StudentToBeSupervisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb =?2 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentToBeSupervisedForTimelineDto> findStudentToBeSupervisedForTimelineDtoCJandALTByAEAndYear(String idEns, String year);

	@Query("Select new com.esprit.gdp.dto.StudentToBeSupervisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogiqueCS.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<StudentToBeSupervisedForTimelineDto> findStudentToBeSupervisedForTimelineDtoCSByAE(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.StudentToBeSupervisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl) "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogiqueCS.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb =?2 "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<StudentToBeSupervisedForTimelineDto> findStudentToBeSupervisedForTimelineDtoCSByAEAndYear(String idEns, String year);
	
	// find StudentCJ Note Eng Trainingship By Expert
	@Query("Select new com.esprit.gdp.dto.StudentToBeExpertisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, 'YES') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.pedagogicalExpertCJ.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentToBeExpertisedForTimelineDto> findStudentToBeExpertsedForTimelineDtoCJandALTByAE(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.StudentToBeExpertisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, 'YES') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.pedagogicalExpertCJ.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb =?2 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentToBeExpertisedForTimelineDto> findStudentToBeExpertsedForTimelineDtoCJandALTByAEAndYear(String idEns, String year);
	
	@Query("Select new com.esprit.gdp.dto.StudentToBeExpertisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, 'YES') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.pedagogicalExpertCS.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<StudentToBeExpertisedForTimelineDto> findStudentToBeExpertisedForTimelineDtoCSByAE(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.StudentToBeExpertisedForTimelineDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, 'YES') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.pedagogicalExpertCS.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb =?2 "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<StudentToBeExpertisedForTimelineDto> findStudentToBeExpertisedForTimelineDtoCSByAEAndYear(String idEns, String year);
	
	// find StudentCJ Note Eng Trainingship By AE
	@Query("Select new com.esprit.gdp.dto.StudentNoteEngTrainingshipDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb IN (?2) "
			+ "and y.saisonClasse.id.codeCl like '5%' order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentNoteEngTrainingshipDto> findStudentNoteEngTrainingshipCJByAE(String idEns, List<String> activatedYears);
	
	@Query("Select new com.esprit.gdp.dto.StudentNoteEngTrainingshipDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb =?2 "
			+ "and y.saisonClasse.id.codeCl like '5%' order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentNoteEngTrainingshipDto> findStudentNoteEngTrainingshipCJByAEAndYear(String idEns, String year);
	
	@Query("Select new com.esprit.gdp.dto.StudentNoteEngTrainingshipDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and y.saisonClasse.id.codeCl like '4ALINFO%'")
	List<StudentNoteEngTrainingshipDto> findStudentNoteEngTrainingshipCJALTByAE(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.StudentNoteEngTrainingshipDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogiqueCS.idEns =?1 "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<StudentNoteEngTrainingshipDto> findStudentNoteEngTrainingshipCSByAE(String idEns);
	
	
	@Query("Select new com.esprit.gdp.dto.StudentExcelDto(trim(u.nomet), trim(u.prenomet), u.telet, u.adressemailesp) from StudentCJ u where u.idEt=?1")
	StudentExcelDto findStudentExcelCJ(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentExcelDto(trim(u.nomet), trim(u.prenomet), u.telet, u.adressemailesp) from StudentCS u where u.idEt=?1")
	StudentExcelDto findStudentExcelCS(String idEt);
	
	
	
	@Query("Select new com.esprit.gdp.dto.StudentConvFRDto(CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), FUNCTION('to_char', u.datenaisset,'dd-mm-yyyy'), u.lieunaiset, u.nationalite, u.adresseet, u.telet, u.adressemailesp) from StudentCJ u where u.idEt=?1")
	StudentConvFRDto findStudentConvFRCJ(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentConvFRDto(CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), FUNCTION('to_char', u.datenaisset,'dd-mm-yyyy'), u.lieunaiset, u.nationalite, u.adresseet, u.telet, u.adressemailesp) from StudentCS u where u.idEt=?1")
	StudentConvFRDto findStudentConvFRCS(String idEt);
	
	
	@Query("Select new com.esprit.gdp.dto.StudentIdNomPrenomDto(u.idEt, u.nomet, u.prenomet) from StudentCJ u where u.idEt=?1")
	StudentIdNomPrenomDto findStudentCJIdFullName(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentIdNomPrenomDto(u.idEt, u.nomet, u.prenomet) from StudentCS u where u.idEt=?1")
	StudentIdNomPrenomDto findStudentCSIdFullName(String idEt);
	
	
	@Query("Select new com.esprit.gdp.dto.StudentIdFullNameDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet))) from StudentCJ u where u.idEt=?1")
	StudentIdFullNameDto findStudentCJIdFullNameOFF(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentIdFullNameDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet))) from StudentCS u where u.idEt=?1")
	StudentIdFullNameDto findStudentCSIdFullNameOFF(String idEt);
	
	
	@Query("Select new com.esprit.gdp.dto.StudentMailTelDto(u.adressemailesp, u.telet) from StudentCJ u where u.idEt=?1")
	StudentMailTelDto findStudentMailTelCJ(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentMailTelDto(u.adressemailesp, u.telet) from StudentCS u where u.idEt=?1")
	StudentMailTelDto findStudentMailTelCS(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentSpeedDto(CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.emailet, u.adresseet, u.telet, "
			+ "FUNCTION('to_char', u.dateModifyJwtPwd,'dd-mm-yyyy HH24:MI:SS')) from StudentCJ u where u.idEt=?1")
	StudentSpeedDto findStudentSpeedDtoById(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentDto(u.idEt, trim(u.nomet), trim(u.prenomet), u.adressemailesp, u.emailet, u.adresseet, u.telet) "
			+ "from StudentCJ u where u.idEt=?1")
	StudentDto findStudentCJDtoById(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentDto(u.idEt, trim(u.nomet), trim(u.prenomet), u.adressemailesp, u.emailet, u.adresseet, u.telet) "
			+ "from StudentCS u where u.idEt=?1")
	StudentDto findStudentCSDtoById(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentSpeedDto(CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.emailet, u.adresseet, u.telet, "
			+ "FUNCTION('to_char', u.dateModifyJwtPwd,'dd-mm-yyyy HH24:MI:SS')) "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') and y.id.idEt=?1 "
			+ "order by y.saisonClasse.id.anneeDeb desc")
	List<StudentSpeedDto> findStudentSpeedCJDtoById(String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentSpeedDto(CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.emailet, u.adresseet, u.telet, "
			+ "FUNCTION('to_char', u.dateModifyJwtPwdCS,'dd-mm-yyyy HH24:MI:SS')) "
			+ "from StudentCS u, InscriptionCS y where u.idEt = y.id.idEt and y.saisonClasse.id.codeCl like '4%' and y.id.idEt=?1 order by y.saisonClasse.id.anneeDeb desc")
	List<StudentSpeedDto> findStudentSpeedCSDtoById(String idEt);
	
	@Query("Select trim(u.adressemailesp) from StudentCJ u, InscriptionCJ y where u.idEt = y.id.idEt and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') and y.id.idEt=?1 order by y.saisonClasse.id.anneeDeb desc")
	List<String> findStudentCJMailEspById(String idEt);
	
	@Query("Select trim(u.adressemailesp) from StudentCS u, InscriptionCS y where u.idEt = y.id.idEt and y.saisonClasse.id.codeCl like '4%' and y.id.idEt=?1 order by y.saisonClasse.id.anneeDeb desc")
	List<String> findStudentCSMailEspById(String idEt);
	
	@Query("select y.saisonClasse.id.codeCl from InscriptionCJ y where (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') and y.id.idEt=?1")
	String findCurrentClassByIdEt(String idEt);
	
	@Query("select y.saisonClasse.id.codeCl from InscriptionCJ y where (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') and y.id.idEt=?1")
	List<String> findCurrentClassCJByIdEt(String idEt);
	
	@Query("select y.saisonClasse.id.codeCl from InscriptionCS y where y.saisonClasse.id.codeCl like '4%' and y.id.idEt=?1")
	List<String> findCurrentClassCSByIdEt(String idEt);
	
//	@Query("select s.prenomet, s.nomet from "
//			+ " StudentCJ s where s.idEt=?1 and s.idEt in (select y.id.idEt from InscriptionCJ y where y.codeCl like '5%')")
//	List<Object[]> findStudentFullNameByIdStudent(String idEt);
	
	
	/*
	@Query("Delete from EncadrantSociete e where e.email=?1")
	void deleteEncadrantSocieteByEmail(String email);
	*/
	
	@Query(value="SELECT e from StudentCJ e join InscriptionCJ i "
			+ "on e.idEt = i.studentInscription.idEt where "
			+ " ( i.encadrantPedagogique.idEns =:idEns and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') )")
	List<StudentCJ> getEtudiantEncadrés(@Param("idEns") String idEns);
	
	@Query(value="SELECT e from StudentCJ e join InscriptionCJ i "
			+ "on e.idEt = i.studentInscription.idEt where "
			+ " ( i.encadrantPedagogique.idEns =:idEns and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') and  i.saisonClasse.id.codeCl like %:code% ) order by i.id.anneeDeb desc ")
	List<StudentCJ> getEtudiantEncadrésbyOption(@Param("idEns") String idEns ,@Param("code") String code);
	
	@Query(value="select i.saisonClasse.id.codeCl from InscriptionCJ i where i.id.idEt =:idET and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl not like '----' and i.saisonClasse.id.codeCl not like '-----' order by i.id.anneeDeb desc")
	List<String> getOptionForEtudiant(@Param("idET") String idET);
	
	@Query(value="SELECT e.adressemailesp from  StudentCJ e "
			+ "WHERE e.idEt=:idET ")
	String getEmail(@Param("idET")  String idET);
	
//	@Query(value="SELECT c.libSpecialite FROM  Classe c , InscriptionCJ i "
//			+ "WHERE ( i.id.idEt=?1 "
//			+ "and c.codeCl=i.saisonClasse.classe.codeCl )  order by i.id.anneeDeb desc")
//	List<String> getdepartementForEtudiant(String idetu);
	

	
	@Query(value="SELECT C.LIB_SPECIALITE FROM  syn_classe c , SYN_ESP_INSCRIPTION i " + 
			"			WHERE ( i.id_et=:idET " + 
			"			and C.CODE_CL=i.CODE_CL )  order by i.annee_deb desc " , nativeQuery=true)
	List<String> getdepartementForEtudiant(@Param("idET") String idET);
	
//	
//	@Query("SELECT e from StudentCJ e join InscriptionCJ i "
//			+ "on e.idEt = i.studentInscription.idEt where "
//			+ " (i.saisonClasse.id.codeCl like '5%' and "
//			+ "  i.saisonClasse.id.codeCl like CONCAT('%', ?1, '%'))")
	
//	@Query(value="SELECT * from syn_esp_etudiant e , syn_esp_inscription i where i.CODE_CL like '5%' and i.CODE_CL like '%INFOB%'" , nativeQuery=true)
//	List<StudentCJ> test();
	
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCJ u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCJ y where (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "and y.saisonClasse.id.codeCl like CONCAT ('%', ?1, '%'))")  // anneeDeb
	List<Object[]> getEtudiantTerminal(String code);
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCJ u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCJ y where (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "and y.saisonClasse.id.codeCl like CONCAT ('%', ?1, '%'))")  // anneeDeb
	List<Object[]> getEtudiantTerminalNewCJ(String code);
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCS u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCS y where ( y.saisonClasse.id.codeCl like '4%' "
			+ "and y.saisonClasse.id.codeCl like CONCAT ('%', ?1, '%')))")  // anneeDeb
	List<Object[]> getEtudiantTerminalNewCS(String code);
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCJ u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCJ y where (lower(y.saisonClasse.id.codeCl) like '5%' or lower(y.saisonClasse.id.codeCl) like '4ALINFO%') "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT ('%', ?1, '%'))")  // anneeDeb
	List<Object[]> getEtudiantTerminalLowerCJ(String code);
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCS u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCS y where "
			+ "( lower(y.saisonClasse.id.codeCl) like '4%' and lower(y.saisonClasse.id.codeCl) like CONCAT ('%', ?1, '%')) "
			+ "or lower(y.saisonClasse.id.codeCl) like CONCAT('4cinfo-', ?1, '%')"
			+ ")")  // anneeDeb
	List<Object[]> getEtudiantTerminalLowerCS(String code);
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCJ u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCJ y where (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "and y.saisonClasse.id.codeCl like CONCAT ('%', ?1, '%') and y.saisonClasse.id.anneeDeb= '2017')")  // anneeDeb
	List<Object[]> getEtudiantTerminal2020(String code);
	
	@Query(value="SELECT e from StudentCJ e join InscriptionCJ i "
			+ "on e.idEt = i.studentInscription.idEt join Convention c "
			+ "on c.conventionPK.idEt=e.idEt "
			+ "where (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "and c.responsableServiceStage.idUserSce=:idServiceStage  ")
	List<StudentCJ> getEtudiantbyDep(@Param("idServiceStage") String idServiceStage);
	
	@Query(value="SELECT e.idEt from StudentCJ e join InscriptionCJ i "
			+ "on e.idEt = i.studentInscription.idEt where "
			+ "i.encadrantPedagogique.idEns =:idEns and "
			+ "(i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<String> getEtudiantEncadrés2(@Param("idEns") String idEns);
	
	@Query(value="SELECT i.id.idEt from InscriptionCJ i "
			+ "where ( i.encadrantPedagogique.idEns =:idEns and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') and i.saisonClasse.id.anneeDeb=:annee)")
	List<String> getEtudiantEncadrésbyAnnee(@Param("idEns") String idEns,@Param("annee") String annee);
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb IN (?1) and u.idEt =?2 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETCJALTWithActivatedYears(List<String> activatedYears, String idEt);
	
	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb IN (?1) and u.idEt =?2 "
			+ "and y.saisonClasse.id.codeCl like '4%' "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETCSWithActivatedYears(List<String> activatedYears, String idEt);

	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and u.idEt =?1 and y.saisonClasse.id.anneeDeb =?2 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETAndYearCJALT(String idEt, String year);

	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and u.idEt =?1 and y.saisonClasse.id.anneeDeb =?2 "
			+ "and y.saisonClasse.id.codeCl like '4%' "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETAndYearCS(String idEt, String year);
	

	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb =?1 and u.idEt =?2 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETCJALTWithYear(String year, String idEt);

	@Query("Select new com.esprit.gdp.dto.StudentFullNameMailTelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.adressemailesp, u.telet, 'PAS ENCORE') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and y.saisonClasse.id.anneeDeb =?1 and u.idEt =?2 "
			+ "and y.saisonClasse.id.codeCl like '4%' "
			+ "order by CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) asc")
	List<StudentFullNameMailTelDto> findStudentsFullNameMailTelByIdETCSWithYear(String year, String idEt);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementRSSStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '', y.encadrantPedagogique.idEns, '', '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogique.idEns is not null "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%'))")
	List<EncadrementRSSStatusExcelDto> findEncadrementStatusCJALTByYear(String year);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementRSSStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '', y.encadrantPedagogiqueCS.idEns, '', '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.encadrantPedagogiqueCS.idEns is not null "
			//+ "and y.encadrantPedagogiqueCS.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<EncadrementRSSStatusExcelDto> findEncadrementStatusCSByYear(String year);
	
	// Find Expertise Status Global
	@Query("Select new com.esprit.gdp.dto.EncadrementRSSStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '', y.pedagogicalExpertCJ.idEns, '', '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.pedagogicalExpertCJ.idEns is not null "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%'))")
	List<EncadrementRSSStatusExcelDto> findExpertiseStatusCJALTByYear(String year);
		
	@Query("Select new com.esprit.gdp.dto.EncadrementRSSStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, '', y.pedagogicalExpertCS.idEns, '', '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			+ "and y.pedagogicalExpertCS.idEns is not null "
			//+ "and y.encadrantPedagogiqueCS.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<EncadrementRSSStatusExcelDto> findExpertiseStatusCSByYear(String year);

	@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt and u.idEt=?1 "
			+ "and y.saisonClasse.id.anneeDeb =?2 "
			+ "and (y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%')")
	List<String> findStudentsFullNameCJandALTYear(String idEt, String year);

	@Query("Select CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)) "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt and u.idEt=?1 "
			+ "and y.saisonClasse.id.anneeDeb =?2 "
			+ "and y.saisonClasse.id.codeCl like '4%'")
	List<String> findStudentsFullNameCSYear(String idEt, String year);
	

	@Query("Select new com.esprit.gdp.dto.StudentConfigDto(u.idEt, u.telet, u.adressemailesp, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), y.saisonClasse.id.codeCl) "
			+ "from StudentCJ u, InscriptionCJ y where "
			+ "u.idEt = y.id.idEt and "
			+ "(y.saisonClasse.id.codeCl like '5%' or y.saisonClasse.id.codeCl like '4ALINFO%') and "
			+ "y.saisonClasse.id.anneeDeb =?1")
	List<StudentConfigDto> findStudentsCJALTConfigByYear(String year);

	@Query("Select new com.esprit.gdp.dto.StudentConfigDto(u.idEt, u.telet, u.adressemailesp, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), y.saisonClasse.id.codeCl) "
			+ "from StudentCS u, InscriptionCS y where "
			+ "u.idEt = y.id.idEt and "
			+ "y.saisonClasse.id.codeCl like '4%' and "
			+ "y.saisonClasse.id.anneeDeb =?1")
	List<StudentConfigDto> findStudentsCSConfigByYear(String year);


	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCJ.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5', ?2, '%')")
	List<ExpertiseStatusExcelDto> findExpertisetatusCJByYearAndOption(String selectedYear, String optionlabel);

	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCS.idEns, '') "
			+ "from StudentCS u, InscriptionCS y "
			+ "where u.idEt = y.id.idEt "
			//+ "and y.encadrantPedagogiqueCS.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?2, '%')")
	List<ExpertiseStatusExcelDto> findExpertiseStatusCSByYearAndOption(String selectedYear, String optionlabel);

}

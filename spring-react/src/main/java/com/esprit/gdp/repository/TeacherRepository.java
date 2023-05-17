package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.ExpertDto;
import com.esprit.gdp.dto.TeacherAffectationAcademicEncadrantDto;
import com.esprit.gdp.dto.TeacherAffectationExpertDto;
import com.esprit.gdp.dto.TeacherConfigDto;
import com.esprit.gdp.dto.TeacherDetailsDto;
import com.esprit.gdp.dto.TeacherDtoSTN;
import com.esprit.gdp.dto.TeacherDtoSTN2;
import com.esprit.gdp.dto.TeacherProfileDto;
import com.esprit.gdp.dto.TeacherQuotaEncadrementExpertiseDto;
import com.esprit.gdp.dto.TeacherQuotaPresidenceMembreDto;
import com.esprit.gdp.models.Teacher;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>
{
	Teacher findByIdEns(String idEns);
	
	Boolean existsByIdEns(String idEns);

	Boolean existsByMailEns(String mailEns);

	Teacher findByToken(String token);

	@Query("Select u from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.etatFromPE = 'A'")
	Optional<Teacher> findByMailEns(String mailEns);

	@Query("Select u from Teacher u where u.token =?1 and u.etatFromPE = 'A'")
	Optional<Teacher> findEnsByToken(String mailEns);
	
	@Query("Select u from Teacher u where lower(trim(both from u.mailEns)) =?1 and FS_deCRYPT_ORACLE(u.pwdEns,HEXTORAW('9CA181F1F7E94D1EADBFC270C8BC53EB'))=?2")
	Optional<Teacher> firstAuthenticationTeacher(String mailEns, String pwdEns);
	
	@Query("Select u from Teacher u where u.idEns =?1 and FS_deCRYPT_ORACLE(u.pwdEns,HEXTORAW('9CA181F1F7E94D1EADBFC270C8BC53EB'))=?2")
	Optional<Teacher> firstAuthentication1(String idEns, String pwdEns);
	
	@Query("Select u from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.etatFromPE = 'A'")
	Teacher findEnsByMailEns(String mailEns);
	
	@Query("Select u from Teacher u where u.idEns =?1")
	Optional<Teacher> findTeaByIdEns(String idEns);
	
	@Query("Select u.idEns from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.etatFromPE = 'A'")
	String findIdTeacherByMailEns(String mailEns);
	
//	@Query("Select u from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.etatFromPE = 'A'")
//	Optional<Teacher> findTeacherByMailEns(String mailEns);
	@Query("Select u from Teacher u where lower(trim(both from u.mailEns)) like CONCAT ('%', ?1, '%') and u.etatFromPE = 'A'")
	Optional<Teacher> findTeacherByMailEns(String mailEns);
	
	// order by FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') asc
	@Query("Select u.pwdJWTEnseignant from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.etatFromPE = 'A'")
	String findTeacherJWTPWDById(String mailEns);
	
	@Query("Select u.pwdJWTEnseignant from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.idEns =?2 and u.etatFromPE = 'A'")
	String findTeacherJWTPWDByMailAndId(String mailEns, String idEns);
	
//	@Query("Select new com.esprit.gdp.dto.TeacherDtoSTN(u.idEns, u.nomEns, u.typeEns, u.mailEns, u.tel1, '') from Teacher u where u.idEns=?1")
//	List<TeacherDtoSTN> findTeacherById(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.TeacherDtoSTN(u.idEns, u.up, u.nomEns) from Teacher u where u.idEns=?1")
	TeacherDtoSTN findTeacherById(String idEns);
	
//	@Query("Select trim(u.nomEns) from Teacher u where lower(trim(both from u.mailEns)) =?1")
//	List<String> findTeacherFullNameById(String idEns);
	
	@Query("Select trim(u.nomEns) from Teacher u where lower(trim(u.mailEns)) =?1")
	List<String> findTeacherFullNameByMail(String mailEns);
	
	@Query("Select trim(u.nomEns) from Teacher u where u.idEns =?1")
	String findTeacherFullNameById(String idEns);
	
	@Query("Select trim(u.mailEns) from Teacher u where u.idEns =?1")
	String findTeacherMailById(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.TeacherDtoSTN(u.idEns, u.up, u.nomEns) from Teacher u where u.idEns IN (?1)")
	List<TeacherDtoSTN> fillOne(List<String> idEns);
	
	@Query("Select new com.esprit.gdp.dto.TeacherDtoSTN2(u.idEns, u.up, u.nomEns, u.typeEns, u.mailEns, u.tel1) from Teacher u where u.idEns=?1")
	TeacherDtoSTN2 getTeacherDetails(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.TeacherAffectationAcademicEncadrantDto(u.idEns, u.up, u.nomEns, u.typeEns, u.mailEns, u.tel1, 0) from Teacher u where u.idEns=?1")
	TeacherAffectationAcademicEncadrantDto getTeacherAffectationAcademicEncadrantDetails(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.TeacherAffectationExpertDto(u.idEns, u.up, u.nomEns, u.typeEns, u.mailEns, u.tel1, 0) from Teacher u where u.idEns=?1")
	TeacherAffectationExpertDto getTeacherAffectationExpertDetails(String idEns);
	
	@Query("Select new com.esprit.gdp.dto.TeacherProfileDto(u.idEns, u.nomEns, u.tel1, FUNCTION('to_char', u.dateModifyJwtPwd, 'dd-mm-yyyy HH24:MI:SS')) from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.etatFromPE = 'A'")
	TeacherProfileDto getTeacherProfile(String mailEns);
	
	@Query("Select u.nomEns from Teacher u where u.idEns=?1")
	String findNameTeacherById(String idEns);
	
	@Query("Select u.mailEns from Teacher u where u.idEns=?1")
	String findMailTeacherById(String idEns);
	
	@Query("Select i.id.idEt from InscriptionCJ i where "
			+ "i.id.anneeDeb='2021' " //in ('2019', '2021') "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "and i.encadrantPedagogique.idEns=?1 ")
	List<String> findStudentsByPE(String idPE);
	
	@Query("Select i.id.idEt from InscriptionCJ i where "
			+ "i.id.anneeDeb =?1 " //in ('2019', '2021') "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') "
			+ "and i.encadrantPedagogique.idEns=?2 ")
	List<String> findStudentsByPEAndYear(String year, String idPE);
	
	@Query("Select i.id.idEt from InscriptionCJ i where i.saisonClasse.id.codeCl like '4ALINFO%' "
			+ "and i.id.anneeDeb='2021' " //in ('2019', '2021') "
			+ "and i.encadrantPedagogique.idEns=?1 ")
	List<String> findStudentsALTByPE(String idPE);
	
	@Query("Select i.id.idEt from InscriptionCS i where i.saisonClasse.id.codeCl like '4%' "
			+ "and i.id.anneeDeb='2021' "// in ('2019', '2021') "
			+ "and i.encadrantPedagogiqueCS.idEns=?1 ")
	List<String> findStudentsCSByPE(String idPE);
	
	@Query("Select i.id.idEt from InscriptionCS i where i.saisonClasse.id.codeCl like '4%' "
			+ "and i.id.anneeDeb =?1 "// in ('2019', '2021') "
			+ "and i.encadrantPedagogiqueCS.idEns=?2")
	List<String> findStudentsCSByPEAndYear(String year, String idPE);
	
	@Query("Select distinct t.idEns "
			+ "from Teacher t, InscriptionCJ i "
			+ ", InscriptionCS ics "
			+ "where "
			+ "("
			+ "(t.idEns=i.encadrantPedagogique.idEns and i.encadrantPedagogique.idEns is not null and i.id.anneeDeb=?1 and t.typeEns = 'P' and t.etatFromPE = 'A') "
			+ "or "
			+ "(t.idEns=ics.encadrantPedagogiqueCS.idEns and ics.encadrantPedagogiqueCS.idEns is not null and ics.id.anneeDeb=?1 and t.typeEns = 'P' and t.etatFromPE = 'A') "
			+ ")"
			+ "")
	List<String> getAlreadyAcademicEncadrants(String annee);
	
	@Query("Select new com.esprit.gdp.dto.TeacherDetailsDto(u.idEns, u.nomEns, u.mailEns, u.tel1, u.tel2) "
			+ "from Teacher u join InscriptionCJ i on u.idEns=i.encadrantPedagogique.idEns where i.id.idEt =:idEt")
	TeacherDetailsDto getEncadrantByEtudiantCJ(@Param("idEt") String idEt);
	
	@Query("Select new com.esprit.gdp.dto.TeacherDetailsDto(u.idEns, u.nomEns, u.mailEns, u.tel1, u.tel2) "
			+ "from Teacher u join InscriptionCS i on u.idEns=i.encadrantPedagogiqueCS.idEns where i.id.idEt =:idEt")
	TeacherDetailsDto getEncadrantByEtudiantCS(@Param("idEt") String idEt);
	
	
	
	@Query(value="SELECT e from Teacher e join InscriptionCJ i on e.idEns=i.encadrantPedagogique.idEns where i.id.idEt =:idEt")
	Teacher getEncadrantByEtudiant(@Param("idEt") String idEt);
	
	
	
	 /*
	  SELECT e from SYN_ESP_PLAN saiso, ESP_ENSEIGNANT e where 
saiso.ANNEE_DEB = '2021' and saiso.NUM_SEMESTRE = 2 and
( e.ID_ENS = saiso.ID_ENS or  e.ID_ENS = saiso.ID_ENS2 or  e.ID_ENS = saiso.ID_ENS3 or  e.ID_ENS = saiso.ID_ENS4 or  e.ID_ENS = saiso.ID_ENS5);
	  */
	@Query(value="SELECT t from Plan p, Teacher t "
			+ "where p.id.anneeDeb =?1 and p.id.numSemestre =?2 and "
			+ "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5)")
	List<Teacher> getTeachers(String anneeDeb, String numSemestre);
	
	@Query("Select distinct t.idEns ,t.nomEns from Teacher t, InscriptionCJ i where t.idEns=i.encadrantPedagogique.idEns and i.encadrantPedagogique.idEns is not null and i.id.anneeDeb=?1 ")
	List<Object[]> getTeachers(String annee);
	
	@Query(value="SELECT new com.esprit.gdp.dto.TeacherDtoSTN(t1.idEns, t1.up, t1.nomEns, t1.tel1, t1.mailEns, 0) from Teacher t1 where t1.idEns in "
			+ "( select t.idEns "
				+ "from Plan p, Teacher t "
				+ "where t.idEns != 'V-463-12' and t.typeEns = 'P' and t.etatFromPE = 'A' and p.id.anneeDeb =?1 and "
				+ "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5)"
			+ ") order by t1.nomEns")
	List<TeacherDtoSTN> allAcademicEncadrants (String anneeDeb);
	

	@Query("Select u.pwdEns from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.idEns =?2 and u.etatFromPE = 'A'")
	String findTeacherPWDByMailAndId(String mailEns, String idEns);
	
	@Query("Select u.pwdEns from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.idEns =?2 and FS_deCRYPT_ORACLE(u.pwdEns,HEXTORAW('9CA181F1F7E94D1EADBFC270C8BC53EB'))=?3 and u.etatFromPE = 'A'")
	String findTeacherPWDByMailAndIdAndPwd(String mailEns, String idEns, String pwd);
	
	
//	@Query(value="SELECT new com.esprit.gdp.dto.TeacherDtoSTN(t1.idEns, t1.up, t1.nomEns, 0) from Teacher t1 where t1.idEns in "
//			+ "( select t.idEns "
//				+ "from Plan p, Teacher t "
//				+ "where t.idEns != 'V-463-12' and t.typeEns = 'P' and p.id.anneeDeb =?1 and p.id.numSemestre =?2 and "
//				+ "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5)"
//			+ ") order by t1.nomEns")
//	List<TeacherDtoSTN> allAcademicEncadrants (String anneeDeb, Long numSemestre);
	
//	@Query(value="select new com.esprit.gdp.dto.ExpertDto(snct.idEns, snc.nomSTRCEP, '', 0) "
	@Query(value="select distinct new com.esprit.gdp.dto.ExpertDto(snct.idEns, snc.nomSTRCEP) "
			    +"from Teacher t, StrNomeCEP snc join snc.teachers snct "
			    +"where snc.sncepi.anneeDeb =?1 "
			    +"and t.idEns = snct.idEns "
			    +"and snc.nomSTRCEP =?2")
	List<ExpertDto> allExperts (String anneeDeb, String labelCEP);
	
	@Query(value="SELECT new com.esprit.gdp.dto.TeacherQuotaEncadrementExpertiseDto(t1.idEns, t1.up, t1.nomEns, 0, 0) from Teacher t1 where t1.idEns in "
			+ "( select t.idEns "
				+ "from Plan p, Teacher t "
				+ "where t.idEns != 'V-463-12' and t.typeEns = 'P' and t.etatFromPE = 'A' and p.id.anneeDeb =?1 and "
				+ "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5)"
			+ ") order by t1.nomEns")
	List<TeacherQuotaEncadrementExpertiseDto> allAcademicEncadrantsAndExperts (String anneeDeb);

	@Query(value="SELECT new com.esprit.gdp.dto.TeacherQuotaPresidenceMembreDto(t1.idEns, t1.up, t1.nomEns, 0, 0) from Teacher t1 where t1.idEns in "
			+ "( select t.idEns "
				+ "from Plan p, Teacher t "
				+ "where t.idEns != 'V-463-12' and t.typeEns = 'P' and t.etatFromPE = 'A' and p.id.anneeDeb =?1 and "
				+ "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5)"
			+ ") order by t1.nomEns")
	List<TeacherQuotaPresidenceMembreDto> allTeachersForSTNByYear (String anneeDeb);
	

	@Query("Select u.pwdJWTEnseignant from Teacher u where lower(trim(both from u.mailEns)) =?1 and u.idEns =?2 and u.etatFromPE = 'A'")
	String findTeacherPWDJWTByMailAndId(String mailEns, String idEns);
	

	@Query("Select  distinct new com.esprit.gdp.dto.TeacherConfigDto(t.idEns, t.tel1, lower(trim(both from t.mailEns)), t.nomEns, t.up) "
		   + "from Teacher t, Plan p where "
		   + "t.typeEns = 'P' and t.etatFromPE = 'A' and "
		   + "p.id.anneeDeb =?1 and "
		   + "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5) "
		   + "order by t.nomEns")
	List<TeacherConfigDto> getActiveTeacherConfigFromPE(String year);

//	@Query(value="select distinct snct.idEns "
//		    +"from Teacher t, StrNomeCEP snc join snc.teachers snct where snc.sncepi.anneeDeb =?1 "
//		    +"and t.idEns = snct.idEns")
//	List<String> allExperts (String anneeDeb);
	
	
	
	
	
//	@Query(value="SELECT new com.esprit.gdp.dto.TeacherDtoSTN(t1.idEns, t1.up, t1.nomEns, 0) from Teacher t1 where t1.idEns in "
//			+ "( select t.idEns "
//				+ "from Plan p, Teacher t "
//				+ "where t.idEns != 'V-463-12' and t.typeEns = 'P' and p.id.anneeDeb =?1 and p.id.numSemestre =?2 and "
//				+ "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5)"
//			+ ") order by t1.nomEns")
//	List<TeacherDtoSTN> allExperts (String anneeDeb, Long numSemestre);
	
	
//	@Query(value="SELECT new com.esprit.gdp.dto.TeacherDtoSTN(t.idEns, t.up, t.nomEns, 0) "
//				+ "from Plan p, Teacher t "
//				+ "where t.idEns != 'V-463-12' and p.id.anneeDeb =?1 and p.id.numSemestre =?2 and "
//				+ "(t.idEns = p.idEns or t.idEns = p.idEns2 or t.idEns = p.idEns3 or t.idEns = p.idEns4 or t.idEns = p.idEns5) "
//			    + "order by t.nomEns")
//	List<TeacherDtoSTN> allAcademicEncadrants (String anneeDeb, Long numSemestre);
	
}

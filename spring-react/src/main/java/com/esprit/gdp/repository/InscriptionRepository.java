package com.esprit.gdp.repository;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.esprit.gdp.dto.StudentAffectationDetailsDto;
import com.esprit.gdp.dto.StudentDemandeStageDto;
import com.esprit.gdp.dto.StudentJustificatifStageDto;
import com.esprit.gdp.dto.StudentMandatoryInternshipDto;
import com.esprit.gdp.dto.TeacherEncadrantPedaDto;
import com.esprit.gdp.models.InscriptionCJ;
import com.esprit.gdp.models.InscriptionCJPK;


@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionCJ, InscriptionCJPK>
{
	
	@Query("select i.saisonClasse.id.codeCl from InscriptionCJ i where i.id.idEt =?1 and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') order by i.id.anneeDeb desc")
	List<String> findCurrentClassByIdEt(String idEt);
	
	@Query("select i.saisonClasse.id.codeCl from InscriptionCS i where i.id.idEt =?1 and i.saisonClasse.id.codeCl like '4%' order by i.id.anneeDeb desc")
	List<String> findCurrentClassCSByIdEt(String idEt);
	
	@Query("select i.encadrantPedagogique.idEns from InscriptionCJ i where i.id.idEt =?1 and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findEncadrantPedagogiqueByStudent(String idEt);
	
	@Query("select i.pedagogicalExpertCJ.idEns from InscriptionCJ i where i.id.idEt =?1 and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findExpertPedagogiqueByStudent(String idEt);
	
	@Query("select i.pedagogicalExpertCJ.idEns from InscriptionCJ i where i.id.idEt =?1 and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') order by i.id.anneeDeb desc")
	List<String> findExpertCJByStudent(String idEt);
	
	@Query("select new com.esprit.gdp.dto.TeacherEncadrantPedaDto(i.encadrantPedagogique.idEns, i.encadrantPedagogique.nomEns, "
			+ "i.encadrantPedagogique.mailEns) from InscriptionCJ i where i.id.idEt =?1 and i.saisonClasse.id.codeCl not like '---' "
			+ "order by i.id.anneeDeb desc")
	List<TeacherEncadrantPedaDto> findEncadrantPedagogiqueByStudentCJ(String idEt);
	
	@Query("select new com.esprit.gdp.dto.TeacherEncadrantPedaDto(i.encadrantPedagogiqueCS.idEns, i.encadrantPedagogiqueCS.nomEns, "
			+ "i.encadrantPedagogiqueCS.mailEns) from InscriptionCS i where i.id.idEt =?1 and i.saisonClasse.id.codeCl not like '---' "
			+ "order by i.id.anneeDeb desc")
	List<TeacherEncadrantPedaDto> findEncadrantPedagogiqueByStudentCS(String idEt);
	
	@Query("select i.id.idEt from InscriptionCJ i "
			+ "where i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<String> findListOfStudentsByPedagogicalEncadrantCJ(String idEns, String anneeDeb);
	
	@Query("select i.id.idEt from InscriptionCJ i "
			+ "where i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 "
			+ "and i.saisonClasse.id.codeCl like '4ALINFO%'")
	List<String> findListOfStudentsByPedagogicalEncadrantCJALT(String idEns, String anneeDeb);

	@Query("select i.id.idEt from InscriptionCJ i "
			+ "where i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<String> findListOfStudentsByPedagogicalEncadrantCJandALT(String idEns, String anneeDeb);
	
	@Query("select i.id.idEt from InscriptionCS i "
			+ "where i.encadrantPedagogiqueCS.idEns =?1 and i.id.anneeDeb =?2 "
			+ "and i.saisonClasse.id.codeCl like '4%'")
	List<String> findListOfStudentsByPedagogicalEncadrantCS(String idEns, String anneeDeb);
	
	
	@Query("select i.id.idEt from InscriptionCJ i "
			+ "where i.pedagogicalExpertCJ.idEns =?1 and i.id.anneeDeb =?2 "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<String> findListOfStudentsByExpertCJandALT(String idEns, String anneeDeb);
	
	@Query("select i.id.idEt from InscriptionCS i "
			+ "where i.pedagogicalExpertCS.idEns =?1 and i.id.anneeDeb =?2 "
			+ "and i.saisonClasse.id.codeCl like '4%'")
	List<String> findListOfStudentsByExpertCS(String idEns, String anneeDeb);
	
//	@Query("select icj.id.idEt from InscriptionCJ icj, InscriptionCS ics "
//			+ "where "
//			+ "( "
//				+ "("
//					+ "icj.encadrantPedagogique.idEns =?1 and icj.id.anneeDeb =?2 "
//					+ "and (icj.saisonClasse.id.codeCl like '5%' or icj.saisonClasse.id.codeCl like '4ALINFO%') "
//				+ ")"
//				+ "or "
//				+ "( "
//					+ "ics.encadrantPedagogiqueCS.idEns =?1 and ics.id.anneeDeb =?2 "
//					+ "and ics.saisonClasse.id.codeCl like '4%'"
//				+ ") "
//			+ ")")
//	List<String> sars(String idEns, String anneeDeb);
	
	
//	@Query("select i.id.idEt from InscriptionCJ i "
//			+ "where (i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 "
//			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')) "
//			+ "or "
//			+ "select a.id.idEt from InscriptionCS a "
//			+ "where a.encadrantPedagogiqueCS.idEns =?1 and a.id.anneeDeb =?2 "
//			+ "and a.saisonClasse.id.codeCl like '4%'")
//	List<String> sars(String idEns, String anneeDeb);
	
	
//	@Query("select x from (select a1.liba1 from A1 a1) as x or select y from (select a2.libelle from A2 a2) as y")
//	List<String> sars();
	
//	@Query("select a1.liba1, a2.libelle from A1 a1, A2 a2")
//	List<String> sars();
	
	// select (select count(*) from table1) ,(select count(*) from table2)
//	@Query("select (select count(a1.liba1) from A1 a1) ,(select count(a2.libelle) from A2 a2)")
//	List<Integer> sars2();
	
	@Query(value = "select e.id_et from SYN_ESP_ETUDIANT e, SYN_ESP_INSCRIPTION i "
	             + "where e.id_et=i.id_et and i.CODE_CL like '%5%' and i.annee_deb =?2 and i.encadrant_peda =?1 "
	             + "union "
			     + "select e.id_et from SYN_ESP_ETUDIANT_CS e, SYN_ESP_INSCRIPTION_CS i "
			     + "where e.id_et=i.id_et and i.CODE_CL like '%4%' and i.annee_deb =?2 and i.encadrant_peda =?1", nativeQuery = true)
	List<String> sars1(String idEns, String anneeDeb);
	
	
	@Query(value="SELECT t1.id_Ens, t1.nom_Ens, t1.up, "
			+ "(select count(*) from (select e.id_et from SYN_ESP_ETUDIANT e, SYN_ESP_INSCRIPTION i "
	             + "where e.id_et=i.id_et and i.CODE_CL like '%5%' and i.ane_deb =?1 and i.encadrant_peda =?3 "
	             + "union "
			     + "select e.id_et from SYN_ESP_ETUDIANT_CS e, SYN_ESP_INSCRIPTION_CS i "
			     + "where e.id_et=i.id_et and i.CODE_CL like '%4%' and i.annee_deb =?1 and i.encadrant_peda =?3)) "
			+ "from SYN_ESP_ENSEIGNANT t1 where t1.id_Ens in "
			+ "( select t.id_Ens "
				+ "from SYN_ESP_PLAN p, SYN_ESP_ENSEIGNANT t "
				+ "where t.id_Ens != 'V-463-12' and t.type_Ens = 'P' and p.annee_Deb =?1 and p.num_Semestre =?2 and "
				+ "(t.id_Ens = p.id_Ens or t.id_Ens = p.id_Ens2 or t.id_Ens = p.id_Ens3 "
				+ "or t.id_Ens = p.id_Ens4 or t.id_Ens = p.id_Ens5)"
			+ ") order by t1.nom_Ens", nativeQuery = true)
	List<Object[]> sars (String anneeDeb, Long numSemestre, String idEns);
	
	@Query(value="SELECT t1.id_Ens, t1.nom_Ens, t1.up, "
			+ "(select count(*) from (select e.id_et from SYN_ESP_ETUDIANT e, SYN_ESP_INSCRIPTION i "
	             + "where e.id_et=i.id_et and i.CODE_CL like '%5%' and i.annee_deb =?1 and i.encadrant_peda =?2 "
	             + "union "
			     + "select e.id_et from SYN_ESP_ETUDIANT_CS e, SYN_ESP_INSCRIPTION_CS i "
			     + "where e.id_et=i.id_et and i.CODE_CL like '%4%' and i.annee_deb =?1 and i.encadrant_peda =?2)) "
			+ "from SYN_ESP_ENSEIGNANT t1 where t1.id_Ens =?2 ", nativeQuery = true)
	List<Object[]> sars11 (String anneeDeb, String idEns);
	
	@Query(value="SELECT t1.id_Ens "
			+ "from SYN_ESP_ENSEIGNANT t1 where t1.id_Ens in "
			+ "( select t.id_Ens "
				+ "from SYN_ESP_PLAN p, SYN_ESP_ENSEIGNANT t "
				+ "where t.id_Ens != 'V-463-12' and t.type_Ens = 'P' and p.annee_Deb =?1 and p.num_Semestre =?2 and "
				+ "(t.id_Ens = p.id_Ens or t.id_Ens = p.id_Ens2 or t.id_Ens = p.id_Ens3 "
				+ "or t.id_Ens = p.id_Ens4 or t.id_Ens = p.id_Ens5)"
			+ ") order by t1.nom_Ens", nativeQuery = true)
	List<String> ensIds (String anneeDeb, Long numSemestre);
	
	
	@Query("select new com.esprit.gdp.dto.StudentJustificatifStageDto(CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), FUNCTION('to_char', s.datenaisset, 'dd-mm-yyyy'), trim(s.numcinpasseport)) "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where s.idEt =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<StudentJustificatifStageDto> findStudentJustificatifStageDtoCJ(String idEt, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentJustificatifStageDto(CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), FUNCTION('to_char', s.datenaisset, 'dd-mm-yyyy'), trim(s.numcinpasseport)) "
			+ "from InscriptionCS i, StudentCS s "
			+ "where s.idEt =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '4%'")
	List<StudentJustificatifStageDto> findStudentJustificatifStageDtoCS(String idEt, String anneeDeb);
	
	
	@Query("select new com.esprit.gdp.dto.StudentMandatoryInternshipDto(CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), FUNCTION('to_char', s.datenaisset, 'dd-mm-yyyy')) "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where s.idEt =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<StudentMandatoryInternshipDto> findStudentMandatoryInternshipDtoCJ(String idEt, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentMandatoryInternshipDto(CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), FUNCTION('to_char', s.datenaisset, 'dd-mm-yyyy')) "
			+ "from InscriptionCS i, StudentCS s "
			+ "where s.idEt =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '4%'")
	List<StudentMandatoryInternshipDto> findStudentMandatoryInternshipDtoCS(String idEt, String anneeDeb);
	
	
	@Query("select new com.esprit.gdp.dto.StudentDemandeStageDto(CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), i.saisonClasse.id.codeCl) "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where s.idEt =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<StudentDemandeStageDto> findStudentDemandeStageDtoCJ(String idEt, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentDemandeStageDto(CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), i.saisonClasse.id.codeCl) "
			+ "from InscriptionCS i, StudentCS s "
			+ "where s.idEt =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '4%'")
	List<StudentDemandeStageDto> findStudentDemandeStageDtoCS(String idEt, String anneeDeb);
	
	
	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(s.idEt, CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), "
//			+ "CONCAT(i.id.anneeDeb, '-',  FUNCTION('TO_NUMBER', i.id.anneeDeb)), "
			+ "i.id.anneeDeb, "
			+ "i.saisonClasse.id.codeCl, s.adressemailesp, s.telet, '') "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	List<StudentAffectationDetailsDto> findListOfStudentsDtoByPedagogicalEncadrantCJ(String idEns, String anneeDeb);

	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(s.idEt, CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), "
//			+ "CONCAT(i.id.anneeDeb, '-',  FUNCTION('TO_NUMBER', i.id.anneeDeb)), "
			+ "i.id.anneeDeb, "
			+ "i.saisonClasse.id.codeCl, s.adressemailesp, s.telet, '') "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl like '5%'")
	List<StudentAffectationDetailsDto> findListOfStudentsDtoByPedagogicalEncadrantCJTC(String idEns, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(s.idEt, CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), "
//			+ "CONCAT(i.id.anneeDeb, '-',  FUNCTION('TO_NUMBER', i.id.anneeDeb)), "
			+ "i.id.anneeDeb, "
			+ "i.saisonClasse.id.codeCl, s.adressemailesp, s.telet, '') "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where i.pedagogicalExpertCJ.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl like '5%'")
	List<StudentAffectationDetailsDto> findListOfStudentsDtoByPedagogicalExpertCJ(String idEns, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(s.idEt, CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), "
//			+ "CONCAT(i.id.anneeDeb, '-',  FUNCTION('TO_NUMBER', i.id.anneeDeb)), "
			+ "i.id.anneeDeb, "
			+ "i.saisonClasse.id.codeCl, s.adressemailesp, s.telet, '') "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '4ALINFO%'")
	List<StudentAffectationDetailsDto> findListOfStudentsDtoByPedagogicalEncadrantCJALT(String idEns, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(s.idEt, CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), "
//			+ "CONCAT(i.id.anneeDeb, '-',  FUNCTION('TO_NUMBER', i.id.anneeDeb)), "
			+ "i.id.anneeDeb, "
			+ "i.saisonClasse.id.codeCl, s.adressemailesp, s.telet, '') "
			+ "from InscriptionCJ i, StudentCJ s "
			+ "where i.pedagogicalExpertCJ.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '4ALINFO%'")
	List<StudentAffectationDetailsDto> findListOfStudentsDtoByPedagogicalExpertCJALT(String idEns, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(s.idEt, CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), "
//			+ "CONCAT(i.id.anneeDeb, '-',  FUNCTION('TO_NUMBER', i.id.anneeDeb)),"
			+ "i.id.anneeDeb, "
			+ "i.saisonClasse.id.codeCl, s.adressemailesp, s.telet, '') "
			+ "from InscriptionCS i, StudentCS s "
			+ "where i.encadrantPedagogiqueCS.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '4%'")
	List<StudentAffectationDetailsDto> findListOfStudentsDtoByPedagogicalEncadrantCS(String idEns, String anneeDeb);
	
	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(s.idEt, CONCAT(trim(s.prenomet), ' ',  trim(s.nomet)), "
//			+ "CONCAT(i.id.anneeDeb, '-',  FUNCTION('TO_NUMBER', i.id.anneeDeb)),"
			+ "i.id.anneeDeb, "
			+ "i.saisonClasse.id.codeCl, s.adressemailesp, s.telet, '') "
			+ "from InscriptionCS i, StudentCS s "
			+ "where i.pedagogicalExpertCS.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '4%'")
	List<StudentAffectationDetailsDto> findListOfStudentsDtoByPedagogicalExpertCS(String idEns, String anneeDeb);
	
	// FUNCTION('to_char'
//	@Query("select new com.esprit.gdp.dto.StudentAffectationDetailsDto(CONCAT('a', (FUNCTION('TO_NUMBER', i.id.anneeDeb)) + 1, 'z')) "
//	@Query("select CAST(i.id.anneeDeb AS NUMERIC(10,5)) "
//			+ "from InscriptionCJ i, StudentCJ s "
//			+ "where i.encadrantPedagogique.idEns =?1 and i.id.anneeDeb =?2 and s.idEt = i.id.idEt "
//			+ "and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl like '5%'")
//	List<Integer> sample(String idEns, String anneeDeb);
	
	
	@Query("select i.encadrantPedagogiqueCS.idEns from InscriptionCS i where i.id.idEt =?1 and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findEncadrantPedagogiqueCSByStudent(String idEt);
	
	@Query("select i.pedagogicalExpertCS.idEns from InscriptionCS i where i.id.idEt =?1 and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findExpertPedagogiqueCSByStudent(String idEt);
	
	@Query("select i.pedagogicalExpertCS.idEns from InscriptionCS i where i.id.idEt =?1 and i.saisonClasse.id.codeCl like '4%' order by i.id.anneeDeb desc")
	List<String> findExpertCSByStudent(String idEt);
	
	@Query("select y.id.anneeDeb from InscriptionCJ y where y.saisonClasse.id.codeCl not like '---' and y.encadrantPedagogique.idEns =?1")
	List<String> findNbrEncadrementCJByAE(String idEns);
	
	@Query("select y.id.anneeDeb from InscriptionCS y where y.saisonClasse.id.codeCl not like '---' and y.encadrantPedagogiqueCS.idEns =?1")
	List<String> findNbrEncadrementCSByAE(String idEns);
	
	@Transactional
	@Modifying
	@Query("update InscriptionCJ i set i.pedagogicalExpertCJ.idEns =?1 "
			+ "where i.id.idEt =?2 "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	void affectExpertToStudentCJ(String idEns, String idEt);
	
	@Transactional
	@Modifying
	@Query("update InscriptionCS i set i.pedagogicalExpertCS.idEns =?1 "
			+ "where i.id.idEt =?2 and i.saisonClasse.id.codeCl like '4%'")
	void affectExpertToStudentCS(String idEns, String idEt);
	
	
	@Transactional
	@Modifying
	@Query("update InscriptionCJ i set i.encadrantPedagogique.idEns =?1 "
			+ "where i.id.idEt =?2 "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')")
	void affectAcademicEncadrantToStudentCJandALT(String idEns, String idEt);
	
	@Transactional
	@Modifying
	@Query("update InscriptionCS i set i.encadrantPedagogiqueCS.idEns =?1 "
			+ "where i.id.idEt =?2 and i.saisonClasse.id.codeCl like '4%'")
	void affectAcademicEncadrantToStudentCS(String idEns, String idEt);
	
	
	@Transactional
	@Modifying
	@Query(value="UPDATE InscriptionCJ i SET i.encadrantPedagogique.idEns=:idEns "
			+ "where i.id.idEt=:idEt and i.saisonClasse.id.codeCl like '5%'")
	int AffecteEncadrantPeda(@Param("idEns") String idEns ,@Param("idEt") String idEt);
	
	
	@Query(value="SELECT i from InscriptionCJ i "
			+ "where  ( i.studentInscription.idEt=:idEt and  i.encadrantPedagogique.idEns =:idEns  "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%'))")
	InscriptionCJ getInscriptionbyid(@Param("idEns")  String idEns ,@Param("idEt")  String idEt);
	
	@Query(value="SELECT i from InscriptionCJ i "
			+ "where  (i.encadrantPedagogique.idEns =:idEns  "
			+ "and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%')) order by i.id.anneeDeb asc")
	List<InscriptionCJ> getAllEnseignantEncad(@Param("idEns") String idEns);
	

	@Query(value="SELECT i from InscriptionCS i "
			+ "where  (i.encadrantPedagogiqueCS.idEns =:idEns) order by i.id.anneeDeb asc")
	List<InscriptionCJ> getAllEnseignantEncadCS(@Param("idEns") String idEns);

	
	@Query("select i.id.anneeDeb from InscriptionCJ i where i.id.idEt =:idEt and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findCurrentAnnneInscriptiobByIdEt(@Param("idEt") String idEt);
	
	@Query("select i.id.anneeDeb from InscriptionCS i where i.id.idEt =:idEt and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findCurrentAnnneInscriptiobCSByIdEt(@Param("idEt") String idEt);
	

	@Query("select distinct i.saisonClasse.id.codeCl from InscriptionCJ i where i.id.anneeDeb =?1 and i.saisonClasse.id.codeCl like '4ALINFO%'")
	List<String> findALTClassesByYear(String anneeDeb);
	

	@Query("select i.pedagogicalExpertCJ.idEns from InscriptionCJ i where i.id.idEt =?1 and (i.saisonClasse.id.codeCl like '5%' or i.saisonClasse.id.codeCl like '4ALINFO%') and i.id.anneeDeb =?2")
	List<String> findExpertCJByStudentAndYear(String idEt, String year);

	@Query("select i.pedagogicalExpertCS.idEns from InscriptionCS i where i.id.idEt =?1 and i.saisonClasse.id.codeCl like '4%' and i.id.anneeDeb =?2")
	List<String> findExpertCSByStudentAndYear(String idEt, String year);
	
	@Query("select i.encadrantPedagogique.idEns from InscriptionCJ i where i.id.idEt =?1 and i.id.anneeDeb =?2 and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findEncadrantPedagogiqueByStudentByYear(String idEt, String year);

	@Query("select i.encadrantPedagogiqueCS.idEns from InscriptionCS i where i.id.idEt =?1 and i.id.anneeDeb =?2 and i.saisonClasse.id.codeCl not like '---' order by i.id.anneeDeb desc")
	List<String> findEncadrantPedagogiqueCSByStudentByYear(String idEt, String year);
	
}

package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.EncadrementStatusExcelDto;
import com.esprit.gdp.dto.ExpertiseStatusExcelDto;
import com.esprit.gdp.models.OptionStudentALT;
import com.esprit.gdp.models.OptionStudentALTPK;


@Repository
public interface OptionStudentALTRepository extends JpaRepository<OptionStudentALT, OptionStudentALTPK>
{
	@Query("Select o from OptionStudentALT o where o.idOptStuALT.anneeDeb =?1 and o.codeOption =?2")
	OptionStudentALT findStudentsALTByYearAndOption(String year, String codeOption);
	
	@Query("Select o.idOptStuALT.idEt from OptionStudentALT o where o.idOptStuALT.anneeDeb =?1 and lower(o.codeOption) =?2")
	List<String> findIdStudentsALTByYearAndOption(String year, String codeOption);

	@Query("Select o.idOptStuALT.idEt from OptionStudentALT o where "
			+ "o.idOptStuALT.anneeDeb =?1 and lower(o.codeOption) like '%sae%'")
	List<String> findIdStudentsSAEALTByYearAndOption(String year);

	@Query("Select o.codeOption from OptionStudentALT o where o.idOptStuALT.anneeDeb =?1 and o.idOptStuALT.idEt =?2")//
	String findOptionByYearAndStudentALT(String year, String idEt);
	
	@Query("Select o.codeOption from OptionStudentALT o where o.idOptStuALT.idEt =?1 and o.idOptStuALT.anneeDeb =?2")
	String findOptionByStudentALTAndYear(String idEt, String year);

	@Query("Select distinct lower(o.codeOption) from OptionStudentALT o where o.idOptStuALT.anneeDeb =?1")//
	List<String> findOptionALTByYear(String year);
	
	@Query("Select o.idOptStuALT.idEt from OptionStudentALT o where o.idOptStuALT.anneeDeb =?1 and o.codeClasse =?2") //
	List<String> findStudentsALTByYearAndClasse(String year, String codeClasse);
	
	@Query("Select o.idOptStuALT.idEt from OptionStudentALT o where o.idOptStuALT.anneeDeb =?1 and o.codeOption =?2 and o.codeClasse =?3") //
	List<String> findStudentsALTByYearAndOptionAndClasse(String year, String codeOption, String codeClasse);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogique.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(o.codeOption) =?2 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCJALTByYearAndOption(String year, String codeOption); //
	
	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCJ.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(o.codeOption) =?2 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<ExpertiseStatusExcelDto> findExpertiseStatusCJALTByYearAndOption(String year, String codeOption); //
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogique.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCJALTByYear(String year);
	
	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCJ.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<ExpertiseStatusExcelDto> findExpertiseStatusCJALTByYear(String year);//
	
	
	
	/*********************************************************************************************************************************/
	
	@Query("Select o from OptionStudentALT o where o.codeOption =?1")
	OptionStudentALT findStudentsALTByOption(String codeOption);

	@Query("Select o.idOptStuALT.idEt from OptionStudentALT o where lower(o.codeOption) =?1")
	List<String> findIdStudentsALTByOption(String codeOption);
	
	@Query("Select o.codeOption from OptionStudentALT o where o.idOptStuALT.idEt =?1")
	String findOptionByStudentALT(String idEt);
	
	@Query("Select o.idOptStuALT.idEt from OptionStudentALT o where o.codeClasse =?1")
	List<String> findStudentsALTByClasse(String codeClasse);
	
	@Query("Select o.idOptStuALT.idEt from OptionStudentALT o where o.codeOption =?1 and o.codeClasse =?2")
	List<String> findStudentsALTByOptionAndClasse(String codeOption, String codeClasse);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogique.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(o.codeOption) =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCJALTByOption(String codeOption);
	
	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCJ.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(o.codeOption) =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<ExpertiseStatusExcelDto> findExpertiseStatusCJALTByOption(String codeOption);
	
	@Query("Select new com.esprit.gdp.dto.EncadrementStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.encadrantPedagogique.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<EncadrementStatusExcelDto> findEncadrementStatusCJALT();
	
	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCJ.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<ExpertiseStatusExcelDto> findExpertiseStatusCJALT();


	@Query("Select new com.esprit.gdp.dto.ExpertiseStatusExcelDto(u.idEt, CONCAT(trim(u.prenomet), ' ',  trim(u.nomet)), u.telet, u.adressemailesp, y.saisonClasse.id.codeCl, y.pedagogicalExpertCJ.idEns, '') "
			+ "from StudentCJ u, InscriptionCJ y, OptionStudentALT o "
			+ "where u.idEt = y.id.idEt and u.idEt = o.idOptStuALT.idEt "
			//+ "and y.encadrantPedagogique.idEns = t.idEns "
			+ "and y.saisonClasse.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<ExpertiseStatusExcelDto> findExpertiseStatusCJALTAndYear(String selectedYear);

}

package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Option;
import com.esprit.gdp.models.OptionPK;


@Repository
public interface OptionRepository extends JpaRepository<Option, OptionPK>
{
	
	@Query("Select distinct o.id.codeOption from Option o where o.id.anneeDeb=?1")
	List<String> listOptionsByYear(String anneeDeb);
	
//	@Query("Select u.idEt from StudentCJ u where "
//			+ "u.idEt in "
//			+ "(select y.id.idEt from InscriptionCJ y where y.saisonClasse.id.codeCl like CONCAT('5%', ?1, '%'))")
	@Query("Select u.idEt, y.saisonClasse.id.codeCl from StudentCJ u, InscriptionCJ y where "
			+ "u.idEt = y.id.idEt and y.saisonClasse.id.codeCl like CONCAT('5%', ?1, '%')")
	// List<String> 
	List<Object[]> findStudentsByCodeOption(String codeOption);
	
//	@Query("Select u.idEt from StudentCJ u where "
//	+ "u.idEt in "
//	+ "(select y.id.idEt from InscriptionCJ y where "
//	//+ "y.id.anneeDeb = '1988' "
//	+ "and y.saisonClasse.id.codeCl like CONCAT('5%', ?1, '%'))") 
//	List<String> findStudentsByOption(String codeOption);
	
	@Query("select y.id.idEt from InscriptionCJ y where y.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5%', ?1, '%')") // CAN: CONCAT('5%', ?1, '%')
	List<String> findStudentsByOption(String codeOption);

	@Query("select y.id.idEt from InscriptionCJ y where y.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5%', ?2, '%')") // CAN: CONCAT('5%', ?1, '%')
	List<String> findStudentsByYearAndOption(String year, String codeOption);
	
	@Query("select y.id.idEt from InscriptionCS y where y.id.anneeDeb = '2021' "
			+ "and (lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?1, '%') or lower(y.saisonClasse.id.codeCl) like CONCAT('4cinfo-', ?1, '%'))") 
	List<String> findStudentsCSByOption(String codeOption);

	@Query("select y.id.idEt from InscriptionCJ y where y.id.anneeDeb =?1 "
			+ "and (lower(y.saisonClasse.id.codeCl) like '5mécat%' or lower(y.saisonClasse.id.codeCl) like '5ogi%')") // CAN: CONCAT('5%', ?1, '%')
	List<String> findStudentsByYearAndOptionForEMCJ(String year);

	@Query("select y.id.idEt from InscriptionCS y where y.id.anneeDeb =?1 "
			+ "and (lower(y.saisonClasse.id.codeCl) like '4cemmec%' or lower(y.saisonClasse.id.codeCl) like '4cemogi%')") // CAN: CONCAT('5%', ?1, '%')
	List<String> findStudentsByYearAndOptionForEMCS(String year);

	@Query("select y.id.idEt from InscriptionCJ y where y.id.anneeDeb =?1 "
			+ "and (lower(y.saisonClasse.id.codeCl) like '5gc%' or lower(y.saisonClasse.id.codeCl) like '5me21-gc%')") // CAN: CONCAT('5%', ?1, '%')
	List<String> findStudentsByYearAndOptionForGCCJ(String year);

	@Query("select y.id.idEt from InscriptionCS y where y.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like '4cinfo-arc%'")
	List<String> findStudentsARCCSByYearAndOption(String year);

	@Query("select y.id.idEt from InscriptionCS y where y.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like '4cinfo-bi%'")
	List<String> findStudentsBICSByYearAndOption(String year);

	@Query("select y.id.idEt from InscriptionCS y where y.id.anneeDeb =?1 and lower(y.saisonClasse.id.codeCl) like '4cinfo-gl%'")
	List<String> findStudentsSAECSByYearAndOption(String year);

	@Query("select y.id.idEt from InscriptionCJ y where y.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5', ?2, '%')") // CAN: CONCAT('5%', ?1, '%')
	List<String> findStudentsCJByYearAndOption(String year, String codeOption);

	@Query("select y.id.idEt from InscriptionCS y where y.id.anneeDeb =?1 "
			+ "and (lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?2, '%') or lower(y.saisonClasse.id.codeCl) like CONCAT('4cinfo-', ?1, '%'))") 
	List<String> findStudentsCSByYearAndOption(String year, String codeOption);
	
	@Query("select distinct y.saisonClasse.id.codeCl from InscriptionCJ y where y.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5', ?1, '%')")
	List<String> findClassesCJByOption(String codeOption);

	@Query("select distinct y.saisonClasse.id.codeCl from InscriptionCJ y where y.id.anneeDeb =?1 "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5', ?2, '%')")
	List<String> findClassesCJByYearAndOption(String year, String codeOption);
	
	@Query("select distinct y.saisonClasse.id.codeCl from InscriptionCJ y where y.id.anneeDeb = '2021' "
			+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('4alinfo%')")
	List<String> findClassesCJALTByOption();
	
	@Query("select distinct y.saisonClasse.id.codeCl from InscriptionCS y where y.id.anneeDeb = '2021' "
			+ "and (lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?1, '%') or lower(y.saisonClasse.id.codeCl) like CONCAT('4cinfo-', ?1, '%'))") 
	List<String> findClassesCSByOption(String codeOption);

	@Query("select distinct y.saisonClasse.id.codeCl from InscriptionCS y where y.id.anneeDeb =?1 "
			+ "and (lower(y.saisonClasse.id.codeCl) like CONCAT('4', ?2, '%') or lower(y.saisonClasse.id.codeCl) like CONCAT('4cinfo-', ?1, '%'))") 
	List<String> findClassesCSByYearAndOption(String year, String codeOption);
	
//   For Optimisation	
//	 @Query("select y.id.idEt from InscriptionCJ y "
//	 		+ "where y.id.anneeDeb = '1988' "
//	 		+ "and lower(y.saisonClasse.id.codeCl) like CONCAT('5%', (select o.lol from PedagogicalCoordinator o where o.id.anneeDeb= '2019'), '%')")
//	List<String> findStudentsByListOptions();

	// SUBSTRING(lower(o.option.id.codeOption), 0, lower(o.option.id.codeOption).lastIndexOf("_01"))
	 
	 /*
	 SELECT japanese
	 FROM edict
	 WHERE japanese LIKE CONCAT('%', 
	                            (SELECT japanese FROM edict WHERE english LIKE 'dog' LIMIT 1), 
	                            '%');
	 */
	 
	 
	public static String createQuery(List<String> names)
	{
	    StringBuilder query = new StringBuilder("select y.id.idEt from InscriptionCJ y where y.id.anneeDeb = '2021' and");
	    int size = names.size();
	    for(int i = 0; i < size; i++){
	        query.append("lower(y.saisonClasse.id.codeCl) like '5%").append(names.get(i)).append("%'");
	        if(i != size-1){
	            query.append(" OR ");
	        }
	    }
	    return query.toString();
		// return "dfd";
	}

	@Query("select distinct o.id.codeOption from Option o where o.id.anneeDeb in ('2021', '2022')")
	List<String> allOptionsForActivatedYears();
	
	
}

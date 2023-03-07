package com.esprit.gdp.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Problematique;
import com.esprit.gdp.models.ProblematiquePK;


@Repository
public interface ProblematicRepository extends JpaRepository<Problematique, ProblematiquePK>
{
	
	@Query("select p from Problematique p "
			+ "where p.problematicPK.fichePFEPK.conventionPK.idEt =?1 and "
			+ "p.problematicPK.fichePFEPK.dateDepotFiche =?2 "
			+ "order by p.problematicPK.numOrdre")
	List<Problematique> findAllProblematicsByStudentAndFiche(String idEt, Date dateDepotFiche);
	
	@Query("select p.name from Problematique p where p.problematicPK.fichePFEPK.conventionPK.idEt =?1 and FUNCTION('to_char', p.problematicPK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') =?2")
	List<String> findAllProblematicsByStudentAndDateDepotFiche(String idEt, String dateDepotFiche);
	
	@Query("select f.name from Problematique f where f.problematicPK.fichePFEPK.conventionPK.idEt =?1 and "
			+ "FUNCTION('to_char', f.problematicPK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 "
			+ "order by f.problematicPK.numOrdre")
	List<String> findAllProblematics(String idEt, String dateDepotFiche);
	
	@Query("select f.name from Problematique f where f.problematicPK.fichePFEPK.conventionPK.idEt =?1 and "
			+ "FUNCTION('to_char', f.problematicPK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 "
			+ "order by f.problematicPK.numOrdre")
	List<String> findAllProblematicsByStudentAndFichePFE(String idEt, String dateDepotFiche);
	
	@Transactional
	@Modifying
	@Query("delete from Problematique p where p.problematicPK.fichePFEPK.conventionPK.idEt =?1 and FUNCTION('to_char', p.problematicPK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') =?2")
	void deleteAllProblematicsByIdFichePFE(String idEt, String dateDepotFiche);
	
	
//	@Query("select FUNCTION('to_char', p.problematicPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') from Problematique p where p.problematicPK.idEt =?1")
//	List<String> lol1(String idEt);
	
	/*
	Optional<Problematique> findByIdProblematique(Integer idProblematique);
	Problematique findByNameProblematique(String nameProblematique);
	
	@Transactional
	@Modifying
	@Query("delete from Problematique p where p.fichePfe.idFiche =?1")
	void deleteAllProblematicsByIdFichePFE(int idFiche);
	
	@Query("select p from Problematique p where p.fichePfe.idFiche =?1")
	List<Problematique> findAllProblematicsByIdFichePFE(int idFiche);
	*/
}

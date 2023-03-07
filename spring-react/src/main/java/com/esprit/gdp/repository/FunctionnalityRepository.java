package com.esprit.gdp.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.esprit.gdp.models.Fonctionnalite;
import com.esprit.gdp.models.FunctionnalityPK;


@Repository
public interface FunctionnalityRepository extends JpaRepository<Fonctionnalite, FunctionnalityPK>
{

	Fonctionnalite findByName(String name);
	
	@Query("select f.name from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 and f.functionnalityPK.fichePFEPK.dateDepotFiche =?2")
	List<String> findAllFonctionalitiesLibelleByFichePFE(String idEt, Timestamp dateDepotFiche);
	
	@Query("select f.name from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 and FUNCTION('to_char', f.functionnalityPK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') =?2")
	List<String> findAllFonctionalitiesLibelleByFichePFE(String idEt, String dateDepotFiche);
	
//	@Query("select FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') from Fonctionnalite f where f.functionnalityPK.idEt =?1")
//	String lol1(String idEt);
//	
//	@Query("select f.functionnalityPK.dateDepotFiche from Fonctionnalite f where f.functionnalityPK.idEt =?1")
//	String lol2(String idEt);
	
	@Query("select f from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 and f.functionnalityPK.fichePFEPK.dateDepotFiche =?2")
	List<Fonctionnalite> findAllFonctionalitiesByFichePFE(String idEt, Timestamp dateDepotFiche);
	
	
	@Query("select f from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 and trunc(f.functionnalityPK.fichePFEPK.dateDepotFiche) = TO_DATE('01/02/21','DD-MM-YY')")
	List<Fonctionnalite> findAllFuncs(String idEt);
	
	// yyyy-mm-dd hh:mm:ss a z
	@Query("select f from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 and trunc(f.functionnalityPK.fichePFEPK.dateDepotFiche) = TO_DATE('01/02/21 11:17:02','dd/MM/yy hh:mm:ss')")
	List<Fonctionnalite> hi(String idEt);
	
	
	@Transactional
	@Modifying
	@Query("delete from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 and FUNCTION('to_char', f.functionnalityPK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') =?2")
	void deleteAllFunctionnalitiesByIdFichePFE(String idEt, String dateDepotFiche);
	
	// yyyy-mm-dd hh:mm:ss[.fffffffff]
	// 01/02/21
//	@Query("select FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') from Fonctionnalite f where f.functionnalityPK.idEt =?1 and FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') =?2")
	
	// SELECT * FROM MYTABLE WHERE MYTABLE.DATEIN = TO_DATE('23/04/49', 'DD/MM/YY');
	// f.functionnalityPK.dateDepotFiche  1988-02-01 15:41:24.993
	@Query("select f.functionnalityPK.fichePFEPK.dateDepotFiche from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1")
	//@Query("select FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'yyyy-MM-dd HH:mm:ss') from Fonctionnalite f where f.functionnalityPK.idEt =?1 ")
	//		+ "and FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'yyyy-MM-dd HH:mm:ss') = '1988-02-01 15:41:24'")  // yyyy-MM-dd HH:mm:ss
	// @Query("select FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy hh:mm:ss') from Fonctionnalite f where f.functionnalityPK.idEt =?1")
	List<String> hello5(String idEt);
	
	// 01/02/21 11:17:02,646000000
	
	//  trunc(f.functionnalityPK.dateDepotFiche) = TO_DATE('01/02/21 11:17:02,646000000','dd/MM/yy hh:mm:ss a z')
	//  FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') = '28/01/21'
	
	
	// function('date_format', s.date, '%Y, %m, %d')
	// FUNCTION('date_format', f.functionnalityPK.dateDepotFiche, '%d, %m, %y')
	
	// DATEFORMAT(f.functionnalityPK.dateDepotFiche, 'dd/MM/yy')
	// OPERATOR('ToChar', empolyee.startDate,'YYYY-MM-DD')<= :value1
	
	// OPERATOR('ToChar', f.functionnalityPK.dateDepotFiche,'dd/MM/yy')<= :value1
//	@Query("select f from Fonctionnalite f where f.functionnalityPK.idEt = '05-3FT-129' and FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy') like '28/01/21'")
//	List<Fonctionnalite> findAllFonctionalitiesByStudent();
//	
	@Query("select FUNCTION('to_char', f.functionnalityPK.fichePFEPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') "
			+ "from Fonctionnalite f where "
			+ "f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 and "
			+ "FUNCTION('to_char', f.functionnalityPK.fichePFEPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') =?2")
	List<String> findAllFonctionalitiesByStudentAndFiche(String idEt, String dateDepotFiche);
//	
	@Query("select f.name, f.description, f.functionnalityPK.numOrdre from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 "
			+ "and FUNCTION('to_char', f.functionnalityPK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 "
			+ "order by f.functionnalityPK.numOrdre")
	List<Object[]> findAllFuncs(String idEt, String dateDepotFiche);
	
	@Query("select f.name, f.description, f.functionnalityPK.numOrdre from Fonctionnalite f where f.functionnalityPK.fichePFEPK.conventionPK.idEt =?1 "
			+ "and FUNCTION('to_char', f.functionnalityPK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 "
			+ "order by f.functionnalityPK.numOrdre")
	List<Object[]> findAllFunctionnalitiesByStudentAndFichePFE(String idEt, String dateDepotFiche);

//	@Query("select FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') from Fonctionnalite f where f.functionnalityPK.idEt =?1 and FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') =?2")
//	List<String> saria3(String idEt, String dateDepotFiche);
	

//	@Query("select FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy') from Fonctionnalite f where f.functionnalityPK.idEt =?1 and FUNCTION('to_char', f.functionnalityPK.dateDepotFiche,'dd/MM/yy HH:mm:ss') =?2")
//	List<String> saria3(String idEt, String dateDepotFiche);
	
	
	
	/*
	 
	  	@Override
		public List<Object[]> chargeAnnuelDesEnseignantsWithSomme(String anneeDeb, Set<String> codeSpecialites) {
		Query req = em
				.createQuery("select distinct e.idEns, CONCAT(e.pnom, ' ',  e.nom), e.typeEns, c.codeCl, m.designation, sum(a.dure)/6 "
						+ "FROM Classe c, EspModule m, EspEnseignant e, Journee j, Emploisemaine a join a.classes ac join a.matiere am join a.ens ae "
						+ "where " + "    e.idEns = ae.idEns " + "and c.codeCl = ac.codeCl "
						+ "and m.codeModule = am.codeModule " + "and c.codeSpecialite IN (:x) "
						+ "and e.idEns not like 'V-463-12' " + "and e.etat like 'A' "
						+ "and c.codeCl not like '---' and c.codeCl not like '----' and c.codeCl not like '-----' "
						+ "and a.anneedeb =:y " + "and a.actif=1 "
						+ "group by e.idEns, CONCAT(e.pnom, ' ',  e.nom), e.typeEns, c.codeCl, m.designation "
						+ "order by CONCAT(e.pnom, ' ',  e.nom), c.codeCl, m.designation");

		req.setParameter("x", codeSpecialites);
		req.setParameter("y", anneeDeb);

		List<Object[]> lst = req.getResultList();
		return lst;
	}
	  
	 */
	
	
	// and DATEFORMAT(PDM.PDM_PRODUCTITEM.CREATEDON,'dd/MM/YYYY') = 10/12/2003
	// and to_char(PDM.PDM_PRODUCTITEM.CREATEDON,'dd/MM/YYYY') = '10/12/2003'
			
			
	//  SELECT function('date_format', f.functionnalityPK.dateDepotFiche,'%d, %m, %y') from Fonctionnalite f; 
	
	//	@Query("SELECT function('date_format', f.functionnalityPK.dateDepotFiche,'%d, %m, %y') from Fonctionnalite f")
	//	List<Fonctionnalite> findAllFonctionalitiesByStudent(String idEt, String dateDepotFiche);
	
	/*
	@Transactional
	@Modifying
	@Query("delete from Fonctionalite f where f.fichepfe.idFiche =?1")
	void deleteAllFunctionnalitiesByIdFichePFE(int idFiche);
	
	*/
}

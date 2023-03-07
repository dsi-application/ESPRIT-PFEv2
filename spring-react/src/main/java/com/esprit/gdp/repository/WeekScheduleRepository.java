package com.esprit.gdp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.models.edt.WeekSchedule;


@Repository
public interface WeekScheduleRepository extends JpaRepository<WeekSchedule, Long>
{
	
//	@Query("select te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//			+ " where w.actif = '1' and FUNCTION('to_char', w.datecour,'yyyy-mm-dd') =?1"
//			+ " and te.idEns = wt.idEns and te.idEns not in "
//			+ "("
//			+ " select t.idEns from Teacher t, WeekSchedule w join w.teachers wt"
//			+ " where w.actif = '1' and FUNCTION('to_char', w.datecour,'yyyy-mm-dd') =?1"
//			+ " and t.idEns = wt.idEns and w.seance.codeseance IN (?2)"
//			+ ")"
//			+ " order by te.nomEns")
//	List<String> findAvailableTeachers(String Date, List<String> seances);
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in" + 
//			"("
//				"select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ "and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "
			
			 	  "select distinct te.idEns from Plan saiso, Teacher te "
				+ "where "
				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not IN ('V-463-12', 'visiteur', ?5) and "
				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
				+ "and te.idEns not in "
				+ "("
						+ "select t.idEns from Teacher t, WeekSchedule w join w.teachers wt "
						+ "where w.actif = '1' "
						//+ " and t.nomEns like 'D%'"
						+ "and FUNCTION('to_char', w.datecour,'dd-mm-yyyy') =?3 "
						+ "and t.idEns = wt.idEns and w.seance.codeseance IN (?4) "
				+ ")"
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailableTeachers(String anneDeb, long semestre, String Date, List<String> seances, String idPE);
	
	@Query(
			 	  "select distinct te.idEns from Plan saiso, Teacher te "
				+ "where "
				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not IN ('V-463-12', 'visiteur', ?3) and "
				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
	)
	List<String> findAllTeachersFromPE(String anneDeb, long semestre, String idPE);
	
	@Query(
		 	  "select distinct te.idEns from Plan saiso, Teacher te "
			+ "where "
			+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not IN ('V-463-12', 'visiteur', ?3) and "
			+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
	)
	List<String> findAllTeachersFromPEs(String anneDeb, long semestre, List<String> lidPEs);
	
//	@Query(
//		 	  "select distinct te.idEns from Plan saiso, Teacher te "
//			+ "where "
//			+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not IN ('V-463-12', 'visiteur') and "
//			+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
//	)
//	List<String> hi(String anneDeb, long semestre);
	
	@Query(
		 	  "select CONCAT(tea.idEns, '$$',  trim(tea.nomEns)) from Teacher tea "
		 	  + "where tea.idEns in "
			 	  + "(select distinct te.idEns from "
			 	  + "Teacher te , Plan saiso "
				  + "where "
				  + "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not IN ('V-463-12', 'visiteur') "
				  + "and (te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5)) "
			  + "order by tea.nomEns"
	)
	List<String> findAllTeachersFromPEMP(String anneDeb, long semestre);
	
	@Query("select distinct (CONCAT(trim(te.nomEns), '$$', te.idEns)) from Teacher te ORDER BY CONCAT(trim(te.nomEns), '$$', te.idEns)")
	List<String> lol5();
	
	/*
	@Query("select distinct te.idEns from Teacher te order by te.idEns")
	List<String> lol1();
	
	@Query("select CONCAT(trim(te.nomEns), '$$', te.idEns) from Teacher te order by te.nomEns")
	List<String> lol2();
	
	@Query("SELECT CONCAT(trim(te.nomEns), '$$', te.idEns) AS emp FROM Teacher te"
			+ " GROUP BY CONCAT(trim(te.nomEns), '$$', te.idEns) ORDER BY emp")
	List<String> lol3();
	
	@Query("select distinct te.nomEns, te.idEns from Teacher te order by te.nomEns")
	List<String> lol4();
	*/
	

	
	@Query(
		 	  "select distinct te.idEns from Plan saiso, Teacher te "
			+ "where te.up =?4 and "
			+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not IN ('V-463-12', 'visiteur', ?3) and "
			+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
	)
	List<String> findAllTeachersFromPEUP(String anneDeb, long semestre, String idPE, String up);
	
	@Query(
		 	  "select t.idEns from Teacher t, WeekSchedule w join w.teachers wt "
					+ "where w.actif = '1' "
					+ "and FUNCTION('to_char', w.datecour,'dd-mm-yyyy') =?1 "
					+ "and t.idEns = wt.idEns and w.seance.codeseance IN (?2) "
	)
	List<String> findNotAvailableTeachers(String Date, List<String> seances);
	
	@Query(
		 	  "select t.idEns from Teacher t, WeekSchedule w join w.teachers wt "
					+ "where w.actif = '1' and t.up =?3 "
					+ "and FUNCTION('to_char', w.datecour,'dd-mm-yyyy') =?1 "
					+ "and t.idEns = wt.idEns and w.seance.codeseance IN (?2) "
	)
	List<String> findNotAvailableTeachersUP(String Date, List<String> seances, String up);
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

//			    "select distinct te.idEns from Plan saiso, Teacher te "
//				+ "where "
//				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns NOT IN ('V-463-12', 'visiteur', ?6) and "
//				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
//				+ "and te.idEns not in "
//				+ "( "
						"select distinct tep.presidentJuryEns.idEns from FichePFE tep "
						+ "where tep.presidentJuryEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
//									+ "(tep.heureDebut =?2) "
//									+ "or "
//									+ "(tep.heureFin =?3) "
//									+ "or "
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
//				+ ")"
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailableJuryPresidentFichePFE(String Date, String selectedStartHour, String selectedEndHour);
	

	@Query(
						"select distinct tep.presidentJuryEns.idEns from FichePFE tep "
						+ "where tep.etatFiche in ('06', '07', '08') and tep.presidentJuryEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
	)
	List<String> findAvailableJuryPresidentFichePFEForAdvancedPlanifStep1(String Date, String selectedStartHour, String selectedEndHour);
	// Just I added tep.etatFiche in ('06', '07', '08')
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

			    "select distinct te.idEns from Plan saiso, Teacher te "
				+ "where te.up =?7 and "
				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns NOT IN ('V-463-12', 'visiteur', ?6) and "
				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
				+ "and te.idEns not in "
				+ "( "
						+ "select tep.presidentJuryEns.idEns from FichePFE tep "
						+ "where tep.presidentJuryEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?3 "
						+ "and ("
//									+ "(tep.heureDebut =?4) "
//									+ "or "
//									+ "(tep.heureFin =?5) "
//									+ "or "
									+ "(?4 = tep.heureDebut AND ?5 = tep.heureFin) "
									+ "or "
									+ "(?4 > tep.heureDebut AND ?4 < tep.heureFin) "
									+ "or "
									+ "(?5 > tep.heureDebut AND ?5 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?4 AND tep.heureDebut < ?5) "
									+ "or "
									+ "(tep.heureFin > ?4 AND tep.heureFin < ?5) "
						+ ")"
				+ ")"
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailableJuryPresidentFichePFEUP(String anneDeb, long semestre, String Date, String selectedStartHour, String selectedEndHour, String idPE, String up);
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

//			 	"select distinct te.idEns from Plan saiso, Teacher te "
//				+ "where "
//				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns NOT IN ('V-463-12', 'visiteur', ?6) and "
//				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
//				+ "and te.idEns not in "
//				+ " ( "
//						+ 
"select tep.expertEns.idEns from FichePFE tep "
						+ "where tep.expertEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
//									+ "(tep.heureDebut =?2) "
//									+ "or "
//									+ "(tep.heureFin =?3) "
//									+ "or "
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
//				+ " ) "
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailableExpertsFichePFE(String Date, String selectedStartHour, String selectedEndHour);
	

	@Query(
				"select tep.expertEns.idEns from FichePFE tep "
						+ "where tep.etatFiche in ('06', '07', '08') and tep.expertEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
	)
	List<String> findAvailableExpertsFichePFEForAdvancedPlanifStep1(String Date, String selectedStartHour, String selectedEndHour);
	
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

			 	"select distinct te.idEns from Plan saiso, Teacher te "
				+ "where te.up =?7 and "
				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns NOT IN ('V-463-12', 'visiteur', ?6) and "
				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
				+ "and te.idEns not in "
				+ " ( "
						+ "select tep.expertEns.idEns from FichePFE tep "
						+ "where tep.expertEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?3 "
						+ "and ("
//									+ "(tep.heureDebut =?4) "
//									+ "or "
//									+ "(tep.heureFin =?5) "
//									+ "or "
									+ "(?4 = tep.heureDebut AND ?5 = tep.heureFin) "
									+ "or "
									+ "(?4 > tep.heureDebut AND ?4 < tep.heureFin) "
									+ "or "
									+ "(?5 > tep.heureDebut AND ?5 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?4 AND tep.heureDebut < ?5) "
									+ "or "
									+ "(tep.heureFin > ?4 AND tep.heureFin < ?5) "
						+ ")"
				+ " ) "
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailableExpertsFichePFEUP(String anneDeb, long semestre, String Date, String selectedStartHour, String selectedEndHour, String idPE, String up);
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

//			 	"select distinct te.idEns from Plan saiso, Teacher te "
//				+ "where "
//				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not in ('V-463-12', 'visiteur') and "
//				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
//				+ "and te.idEns not in "
//				+ " ( "
//						+ 
"select tep.pedagogicalEncadrant.idEns from FichePFE tep "
						+ "where tep.pedagogicalEncadrant.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
//									+ "(tep.heureDebut =?2) "
//									+ "or "
//									+ "(tep.heureFin =?3) "
//									+ "or "
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
//				+ " ) "
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailablePEsFichePFE(String Date, String selectedStartHour, String selectedEndHour);
	

	@Query(
				"select tep.pedagogicalEncadrant.idEns from FichePFE tep "
						+ "where tep.etatFiche in ('06', '07', '08') and tep.pedagogicalEncadrant.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
	)
	List<String> findAvailablePEsFichePFEForAdvancedPlanifStep1(String Date, String selectedStartHour, String selectedEndHour);
	
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

			 	"select distinct te.idEns from Plan saiso, Teacher te "
				+ "where te.up =?6 and "
				+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not in ('V-463-12', 'visiteur') and "
				+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) "
				+ "and te.idEns not in "
				+ " ( "
						+ "select tep.pedagogicalEncadrant.idEns from FichePFE tep "
						+ "where tep.pedagogicalEncadrant.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?3 "
						+ "and ("
//									+ "(tep.heureDebut =?4) "
//									+ "or "
//									+ "(tep.heureFin =?5) "
//									+ "or "
									+ "(?4 = tep.heureDebut AND ?5 = tep.heureFin) "
									+ "or "
									+ "(?4 > tep.heureDebut AND ?4 < tep.heureFin) "
									+ "or "
									+ "(?5 > tep.heureDebut AND ?5 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?4 AND tep.heureDebut < ?5) "
									+ "or "
									+ "(tep.heureFin > ?4 AND tep.heureFin < ?5) "
						+ ")"
				+ " ) "
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailablePEsFichePFEUP(String anneDeb, long semestre, String Date, String selectedStartHour, String selectedEndHour, String up);
	
	@Query("select distinct sa.codeSalle from Salle sa where sa.pole IN ('g', 'e') order by sa.codeSalle")
	List<String> findAllClassroomsMP();
	
	
	@Query(
			"select distinct sa.codeSalle from Salle sa where sa.pole IN ('g', 'e') "
				+ "and sa.codeSalle not in "
				+ "( "
						+ "select s.codeSalle from Salle s, WeekSchedule w join w.salles ws "
						+ "where w.actif = '1' and s.pole IN ('g', 'e') and FUNCTION('to_char', w.datecour,'dd-mm-yyyy') =?1 "
						+ "and w.seance.codeseance IN (?2) and s.codeSalle = ws.codeSalle "
				+ ") "
			//+ "order by sa.codeSalle"
	)
	List<String> findAvailableClassrooms(String Date, List<String> seances);
	
	@Query(
			"select distinct sa.codeSalle from Salle sa where sa.pole IN ('g', 'e') "
				+ "and sa.codeSalle not in "
				+ "( "
						+ "select tep.salle.codeSalle from FichePFE tep "
						+ "where tep.salle.codeSalle != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
//									+ "(tep.heureDebut =?2) "
//									+ "or "
//									+ "(tep.heureFin =?3) "
//									+ "or "
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
				+ ") "
			//+ "order by sa.codeSalle"
	)
	List<String> findAvailableClassroomsFichePFE(String Date, String selectedStartHour, String selectedEndHour);
	

	
//	@Query("select s.codeSalle from Salle s, WeekSchedule w join w.salles ws"
//			+ " where w.actif = '1' and FUNCTION('to_char', w.datecour,'yyyy-mm-dd') =?1"
//			+ " and w.seance.codeseance not IN (?2) and s.codeSalle = ws.codeSalle"
//			+ " order by s.codeSalle")
	
	
	@Query("select t from Teacher t, WeekSchedule w join w.teachers wt"
			+ " where w.actif = '1' and FUNCTION('to_char', w.datecour,'dd-mm-yyyy') =?1"
			+ " and w.seance.codeseance IN (?2) and t.idEns = wt.idEns and t.idEns=?3"
			+ " order by t.nomEns")
	Teacher findAvailabilityPedagogicalEncadrant(String Date, List<String> seances, String idEns);
	
//	@Query("select FUNCTION('to_char', w.datecour,'yyyy-mm-dd') from WeekSchedule w where w.codemploi =?1")
//	String sars(Long l);
	
//	@Query("select w.codemploi from Teacher t, WeekSchedule w join w.teachers wt"
//			+ " where w.actif = '1' "
//			//+ " and w.anneedeb = '1988' and w.semestre = 2"
//			+ " and FUNCTION('to_char', w.datecour,'yyyy-mm-dd') = ?1 "
//			+ " and t.idEns = wt.idEns and t.idEns=?2"
//			+ " and w.seance.codeseance =?3"
//			+ " order by t.nomEns")
//	List<Long> sars1(String Date, String idEns, String seances);
	
	// selectedDate, seances, idPedagocialEncadrant
	
//	@Query("select distinct t.idEns, t.nomEns from Teacher t, Plan plan, WeekSchedule w join w.teachers wt"
//			+ " where w.actif = '1' and FUNCTION('to_char', w.datecour,'yyyy-mm-dd') =?1"
//			+ " and t.idEns = wt.idEns and (t.idEns=plan.idEns or t.idEns=plan.idEns2 or t.idEns=plan.idEns3 or t.idEns=plan.idEns4 or t.idEns=plan.idEns5)"
//			+ " and w.seance.codeseance not IN (?2)"
//			+ " order by t.nomEns")
//	List<Object[]> findAvailableTeachers(String Date, List<String> seances);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Query("select te.idEns from Teacher te "
				+ "where te.idEns =?3 "
				+ "and te.idEns not in "
				+ "( "
						+ "select t.idEns from Teacher t, WeekSchedule w join w.teachers wt "
						+ "where w.actif = '1' and "
						+ "t.idEns =?3 "
						+ "and FUNCTION('to_char', w.datecour,'dd-mm-yyyy') =?1 "
						+ "and t.idEns = wt.idEns and w.seance.codeseance IN (?2) "
				+ ")"
	)
	List<String> findAvailibilityPEfromEDT(String Date, List<String> seances, String idEns);
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

				"select te.idEns from Teacher te "
				+ "where te.idEns =?4 "
				+ "and te.idEns not in "
				+ "( "
						+ "select tep.presidentJuryEns.idEns from FichePFE tep "
						+ "where tep.presidentJuryEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
//									+ "(tep.heureDebut =?2) "
//									+ "or "
//									+ "(tep.heureFin =?3) "
//									+ "or "
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
				+ ")"
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailibilityPEasJP(String Date, String selectedStartHour, String selectedEndHour, String idEns);
	
	@Query(
				"select te.idEns from Teacher te "
				+ "where te.idEns =?4 "
				+ "and te.idEns not in "
				+ "( "
						+ "select tep.presidentJuryEns.idEns from FichePFE tep "
						+ "where tep.presidentJuryEns.idEns != null and tep.etatFiche in ('06', '07', '08') and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
				+ ")"
	)
	List<String> findAvailibilityPEasJPForAdvancedPlanifStep1(String Date, String selectedStartHour, String selectedEndHour, String idEns);
	
	@Query(
//			"select tea.idEns from Teacher tea where tea.idEns in " + 
//			"( "
//				+ " select distinct te.idEns from Teacher te, WeekSchedule w join w.teachers wt"
//				+ " where w.actif = '1' "
//				//+ " and te.nomEns like 'D%'"
//				+ " and w.anneedeb =?1 and w.semestre =?2 "
//				+ " and te.idEns = wt.idEns "

				"select te.idEns from Teacher te "
				+ "where te.idEns =?4 "
				+ "and te.idEns not in "
				+ " ( "
						+ "select tep.expertEns.idEns from FichePFE tep "
						+ "where tep.expertEns.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
//									+ "(tep.heureDebut =?2) "
//									+ "or "
//									+ "(tep.heureFin =?3) "
//									+ "or "
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
				+ " ) "
//			+ ")"
//			+ " order by tea.nomEns"
	)
	List<String> findAvailibilityPEasEXP(String Date, String selectedStartHour, String selectedEndHour, String idEns);
	
	@Query(
				"select te.idEns from Teacher te "
				+ "where te.idEns =?4 "
				+ "and te.idEns not in "
				+ " ( "
						+ "select tep.expertEns.idEns from FichePFE tep "
						+ "where tep.expertEns.idEns != null and tep.etatFiche in ('06', '07', '08') and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and ("
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
				+ " ) "
	)
	List<String> findAvailibilityPEasEXPForAdvancedPlanifStep1(String Date, String selectedStartHour, String selectedEndHour, String idEns);
	
	@Query("select te.idEns from Teacher te "
				+ "where te.idEns =?4 "
				+ "and te.idEns not in "
				+ " ( "
						+ "select tep.pedagogicalEncadrant.idEns from FichePFE tep "
						+ "where tep.pedagogicalEncadrant.idEns != null and "
						+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
						+ "and tep.idFichePFE.conventionPK.idEt not in (?5, ?6) "
						+ "and ("
//									+ "(tep.heureDebut =?2) "
//									+ "or "
//									+ "(tep.heureFin =?3) "
//									+ "or "
									+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
									+ "or "
									+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
									+ "or "
									+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
									+ "or "
									+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
									+ "or "
									+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
						+ ")"
				+ " ) "
	)
	List<String> findAvailibilityPEasPENew(String Date, String selectedStartHour, String selectedEndHour, String idEns, String idEt, String idPr);
	
	@Query("select te.idEns from Teacher te "
			+ "where te.idEns =?4 "
			+ "and te.idEns not in "
			+ " ( "
					+ "select tep.pedagogicalEncadrant.idEns from FichePFE tep "
					+ "where tep.pedagogicalEncadrant.idEns != null and "
					+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
					+ "and tep.idFichePFE.conventionPK.idEt !=?5 "
					+ "and ("
//								+ "(tep.heureDebut =?2) "
//								+ "or "
//								+ "(tep.heureFin =?3) "
//								+ "or "
								+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
								+ "or "
								+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
								+ "or "
								+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
								+ "or "
								+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
								+ "or "
								+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
					+ ")"
			+ " ) "
)
List<String> findAvailibilityPEasPE(String Date, String selectedStartHour, String selectedEndHour, String idEns, String idEt);
	
	@Query("select te.idEns from Teacher te "
			+ "where te.idEns =?4 "
			+ "and te.idEns not in "
			+ " ( "
					+ "select tep.pedagogicalEncadrant.idEns from FichePFE tep "
					+ "where tep.pedagogicalEncadrant.idEns != null and tep.etatFiche in ('06', '07', '08') and "
					+ "FUNCTION('to_char', tep.dateSoutenance,'dd-mm-yyyy') =?1 "
					+ "and tep.idFichePFE.conventionPK.idEt !=?5 "
					+ "and ("
								+ "(?2 = tep.heureDebut AND ?3 = tep.heureFin) "
								+ "or "
								+ "(?2 > tep.heureDebut AND ?2 < tep.heureFin) "
								+ "or "
								+ "(?3 > tep.heureDebut AND ?3 < tep.heureFin) "
								+ "or "
								+ "(tep.heureDebut > ?2 AND tep.heureDebut < ?3) "
								+ "or "
								+ "(tep.heureFin > ?2 AND tep.heureFin < ?3) "
					+ ")"
			+ " ) "
)
List<String> findAvailibilityPEasPEForAdvancedPlanifStep1(String Date, String selectedStartHour, String selectedEndHour, String idEns, String idEt);

	@Query(
		 	  "select distinct te.up from Plan saiso, Teacher te "
			+ "where "
			+ "saiso.id.anneeDeb =?1 and saiso.id.numSemestre =?2 and te.idEns not IN ('V-463-12', 'visiteur') and "
			+ "(te.idEns = saiso.idEns or te.idEns = saiso.idEns2 or te.idEns = saiso.idEns3 or te.idEns = saiso.idEns4 or te.idEns = saiso.idEns5) order by te.up"
			)
	List<String> getAllUPs(String anneDeb, long semestre);
	
	
}

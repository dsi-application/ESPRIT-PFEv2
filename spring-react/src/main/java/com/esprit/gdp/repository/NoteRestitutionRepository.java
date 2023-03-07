package com.esprit.gdp.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.NoteRestitution;
import com.esprit.gdp.models.NoteRestitutionPK;


@Repository
public interface NoteRestitutionRepository extends JpaRepository<NoteRestitution , NoteRestitutionPK>
{	
	
	@Query("select u from NoteRestitution u "
			+ "where u.idNoteRestitutionPK.teacherId =?1 and "
			+ "u.idNoteRestitutionPK.studentId =?2 and "
			+ "u.idNoteRestitutionPK.anneeDeb =?3")
	NoteRestitution findNoteRestitutionByAEandStu(String idEns, String idEt, String year);
	
	@Query("select u.noteRest from NoteRestitution u "
			+ "where u.idNoteRestitutionPK.teacherId =?1 and "
			+ "u.idNoteRestitutionPK.studentId =?2 and "
			+ "u.idNoteRestitutionPK.anneeDeb =?3")
	BigDecimal findNoteRestitutionMarkByAEandStu(String idEns, String idEt, String year);
	
	@Query("select u.noteRest from NoteRestitution u "
			+ "where "
			+ "u.idNoteRestitutionPK.studentId =?1 and "
			+ "u.idNoteRestitutionPK.anneeDeb =?2")
	BigDecimal findNoteRestitutionByStu(String idEt, String year);
	
}

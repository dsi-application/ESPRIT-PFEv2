package com.esprit.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.NoteStageIngenieur;
import com.esprit.gdp.models.NoteStageIngenieurPK;


@Repository
public interface NoteStageIngenieurRepository extends JpaRepository<NoteStageIngenieur , NoteStageIngenieurPK>
{	
	
	@Query("select u from NoteStageIngenieur u where u.idNoteStageIngenieur.teacherId =?1 and u.idNoteStageIngenieur.studentId =?2 and u.idNoteStageIngenieur.anneeDeb =?3")
	NoteStageIngenieur findNoteStageIngenieurByAEandStu(String idEns, String idEt, String year);
	
}

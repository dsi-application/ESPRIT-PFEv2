package com.esprit.gdp.repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.esprit.gdp.dto.DepotRapportINGDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.esprit.gdp.models.EvaluationEngineeringTraining;


@Repository
public interface EvaluationEngineeringTrainingRepository extends JpaRepository<EvaluationEngineeringTraining, String>
{
	
	@Query("select es.pathJournal, FUNCTION('to_char', es.dateUploadJournal, 'dd-mm-yyyy HH24:MI:SS')"
		 + " from EvaluationEngineeringTraining es where es.studentId=?1")
	List<Object[]> findJournalStageING(String idEt);

	@Query("select es.pathJournal from EvaluationEngineeringTraining es where es.studentId =?1")
	String findPathJournalStageING(String idEt);

	@Query("select es.dateUploadJournal from EvaluationEngineeringTraining es where es.studentId=?1")
	Date findUploadJournalStageINGDate(String idEt);

	
	@Query("select es.pathAttestation, FUNCTION('to_char', es.dateUploadAttestation, 'dd-mm-yyyy HH24:MI:SS')"
		 + " from EvaluationEngineeringTraining es where es.studentId=?1")
	List<Object[]> findAttestationStageING(String idEt);

	@Query("select es.pathAttestation from EvaluationEngineeringTraining es where es.studentId =?1")
	String findPathAttestationStageING(String idEt);

	@Query("select es.dateUploadAttestation from EvaluationEngineeringTraining es where es.studentId=?1")
	Date findUploadAttestationStageINGDate(String idEt);

	@Query("select es.pathRapport, FUNCTION('to_char', es.dateUploadRapport, 'dd-mm-yyyy HH24:MI:SS')"
		+ " from EvaluationEngineeringTraining es where es.studentId=?1")
	List<Object[]> findRapportStageING(String idEt);


	@Query("select new com.esprit.gdp.dto.DepotRapportINGDto(es.pathRapport, FUNCTION('to_char', es.dateUploadRapport, 'dd-mm-yyyy HH24:MI:SS')) "
			+ "from EvaluationEngineeringTraining es where es.studentId=?1")
	List<DepotRapportINGDto> findRapportStageINGByStudent(String idEt);

	@Query("select es.pathRapport from EvaluationEngineeringTraining es where es.studentId =?1")
	String findPathRapportStageING(String idEt);

	@Query("select es.dateUploadRapport from EvaluationEngineeringTraining es where es.studentId=?1")
	Date findUploadRapportStageINGDate(String idEt);
		
		
	@Query("select es from EvaluationEngineeringTraining es where es.studentId=?1")
	EvaluationEngineeringTraining findEvaluationStageIngenieurByStudent(String idEt);
	
	@Query("select es.noteStagIngenieur from EvaluationEngineeringTraining es where es.studentId =?1")
	BigDecimal findNoteStageIngenieurByAEandStu(String idEt);
	
	@Query("select es.etatDepot from EvaluationEngineeringTraining es where es.studentId=?1")
	String findEtatDepotStageIngenieur(String idEt);
	
}
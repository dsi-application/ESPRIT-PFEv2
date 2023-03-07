package com.esprit.gdp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.models.EvaluationStage;
import com.esprit.gdp.models.EvaluationStagePK;



@Repository
public interface EvaluationRepository  extends JpaRepository<EvaluationStage, EvaluationStagePK>{
	
	@Query(value="SELECT e from EvaluationStage e " + 
			"where (e.evaluationStagePK.codeEvaluation =:code "
			+ " and FUNCTION('to_char',e.evaluationStagePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:dateFiche "
			+ " and e.evaluationStagePK.fichePFEPK.conventionPK.idEt =:idET)")
	EvaluationStage getEvaluationStagebyId(@Param("idET") String idET,@Param("dateFiche") String dateFiche, @Param("code") String code);
	
	@Query(value="SELECT c.LIB_NOME FROM SYN_CODE_NOMENCLATURE c , ESP_FICHE_PFE f  , ESP_EVALUATION_STAGE e "
			+ "WHERE (e.ID_ET = f.ID_ET "
			+ "AND  f.ID_ET =:idET "
			+ "AND  TO_CHAR(f.DATE_DEPOT_FICHE, 'DD-MON-YYYY HH24:MI:SS')=:datefiche "
			+ "AND c.CODE_NOME= e.CODE_EVALUATION "
			+ "AND  e.CODE_EVALUATION=:code "
			+ "AND c.CODE_STR =107)",
			 nativeQuery = true)
	String getTypeEvaluation(@Param("idET") String idET, @Param("datefiche") Date datefiche, @Param("code") String code);
	
	@Query(value="SELECT e from EvaluationStage e " + 
			"where (FUNCTION('to_char',e.evaluationStagePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:dateFiche "
			+ " and e.evaluationStagePK.fichePFEPK.conventionPK.idEt =:idET)")
	List<EvaluationStage> getEvaluationStagebyEt(@Param("idET") String idET,@Param("dateFiche")  String dateFiche);
	
	@Transactional
	@Modifying
	@Query(value="DELETE  from EvaluationStage e"
			+ " WHERE (e.evaluationStagePK.codeEvaluation =:code "
			+ "and e.evaluationStagePK.fichePFEPK.conventionPK.idEt=:idET "
			+ "and FUNCTION('to_char',e.evaluationStagePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:dateFiche) "
			)
	int deleteEvaluationStage(@Param("idET") String idET,@Param("dateFiche") String dateFiche,@Param("code") String code);
}

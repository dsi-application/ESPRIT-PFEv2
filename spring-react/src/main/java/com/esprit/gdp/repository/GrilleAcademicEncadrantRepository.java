package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.GrilleAcademicEncadrantDto;
import com.esprit.gdp.models.FichePFEPK;
import com.esprit.gdp.models.GrilleAcademicEncadrant;
import com.esprit.gdp.models.GrilleAcademicEncadrantPK;


@Repository
public interface GrilleAcademicEncadrantRepository extends JpaRepository<GrilleAcademicEncadrant , GrilleAcademicEncadrantPK>
{	
	@Query("select g from GrilleAcademicEncadrant g where g.grilleAcademicEncadrantPK.fichePFEPK =?1 order by g.grilleAcademicEncadrantPK.dateSaisieGAE desc")
	GrilleAcademicEncadrant findGrilleByFiche(FichePFEPK fichePFEPK);
	
	@Query("select new com.esprit.gdp.dto.GrilleAcademicEncadrantDto(g.etatGrille, g.notePlanningStage, g.noteBilanPeriodiqueDebutStage, "
			+ "g.noteBilanPeriodiqueMilieuStage, g.noteBilanPeriodiqueFinStage, g.noteJournalBord, g.noteFicheEvaluationMiParcour, g.noteFicheEvaluationFinale, "
			+ "g.noteRestitution1, g.noteRestitution2, g.noteRDVPedagogique, g.noteAppreciationGlobale, g.noteAcademicEncadrant, g.noteExpert, "
			+ "g.noteEncadrantEntreprise, g.noteFinaleEncadrement) "
			+ "from GrilleAcademicEncadrant g where g.grilleAcademicEncadrantPK.fichePFEPK.conventionPK.idEt =?1 "
			+ "order by g.grilleAcademicEncadrantPK.dateSaisieGAE desc")
	GrilleAcademicEncadrantDto findGrilleEncadrementDtoByFiche(String IdEt);
	
	
}

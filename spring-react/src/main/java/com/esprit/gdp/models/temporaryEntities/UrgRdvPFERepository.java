package com.esprit.gdp.models.temporaryEntities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UrgRdvPFERepository extends JpaRepository<UrgRdvPFE, UrgRdvPFEPK>
{
	
	@Query("Select new com.esprit.gdp.models.temporaryEntities.UrgRdvPFEDto(u.note, "
			+ "u.onPlanningStg, u.onBilanPerDebStg, u.onBilanPerMilStg, u.onBilanPerFinStg, u.onFicheEvalMiPar, u.onFicheEvalFin, u.onJournalBord, u.onRapportStg, "
			+ "u.dateRDV1, u.dateRDV2, u.dateRDV3, "
			+ "u.presenceKindRDV1, u.presenceKindRDV2, u.presenceKindRDV3, "
			+ "u.satisfactionRDV1, u.satisfactionRDV2, u.satisfactionRDV3, "
			+ "u.etatTreatEA, "
			+ "FUNCTION('to_char', u.urgRdvPFEPK.dateSaisieRDV,'dd-mm-yyyy')) "
			+ "from UrgRdvPFE u where u.urgRdvPFEPK.fichePFEPK.conventionPK.idEt=?1")
	UrgRdvPFEDto findUrgRdvPFEDtoByStudent(String idEt);
	
	@Query("Select u from UrgRdvPFE u where u.urgRdvPFEPK.fichePFEPK.conventionPK.idEt=?1")
	UrgRdvPFE findUrgRdvPFEByStudent(String idEt);
	
	@Query("Select u.etatTreatCP from UrgRdvPFE u where u.urgRdvPFEPK.fichePFEPK.conventionPK.idEt=?1")
	String findEtatTreatCPForUrgRdvPFEByStudent(String idEt);
	
}

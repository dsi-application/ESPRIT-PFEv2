package com.esprit.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.temporaryEntities.UrgRdvPFE;
import com.esprit.gdp.models.temporaryEntities.UrgRdvPFEPK;


@Repository
public interface UrgRDVSuiviStage extends JpaRepository<UrgRdvPFE , UrgRdvPFEPK>
{	
	
	@Query("select u.etatTreatEA from UrgRdvPFE u where u.urgRdvPFEPK.fichePFEPK.conventionPK.idEt =?1")
	String findEtatTrtFicheDepotByAEForStudent(String idEt);
	
}

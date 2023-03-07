package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.models.SaisonUniversitaire;

public interface SaisonUniversitaireRepository extends JpaRepository<SaisonUniversitaire, String>{

	
	@Query("SELECT DISTINCT c.anneeDeb ,c.anneeFin FROM SaisonUniversitaire c order by c.anneeDeb desc ")
	List<Object[]> findDistinctAnnee();
}

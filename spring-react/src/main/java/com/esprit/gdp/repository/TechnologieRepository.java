package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.models.Technologie;


@Repository
public interface TechnologieRepository extends JpaRepository<Technologie, Integer> {
	Optional<Technologie> findByIdTechnologie(Integer idTechnologie);
	
	/*
	Etudiant findByEmail(String email);
	Boolean existsByCode(String code);
	Boolean existsByEmail(String email);
	*/
	
	Technologie findByName(String name);
	
//	@Query("Select t from Technologie t where t.etat=1 and t.type=?1 order by t.nameTechnologie")
//	List<Technologie> findAllTechnologiesByTypeAndStatus(String type);
	
	@Query("Select t.name from Technologie t where t.etat='A' order by t.name")
	List<String> findAllTechnologiesByTypeAndStatus();
	
	@Transactional
	@Modifying
	@Query("Delete from Technologie t where t.name=?1")
	void deleteTechnologyByName(String name);
	
	
}

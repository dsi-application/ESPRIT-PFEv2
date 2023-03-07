package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.SecteurEntreprise;


@Repository
public interface SectorEntrepriseRepository extends JpaRepository<SecteurEntreprise, Integer>
{
	Optional<SecteurEntreprise> findByIdSecteur(String idSecteur);
	
	@Query("Select se.libelleSecteur from SecteurEntreprise se order by se.libelleSecteur")
	List<String> findAllLibelleSectorsEntreprise();
	
	@Query("Select se from SecteurEntreprise se where se.libelleSecteur=?1")
	SecteurEntreprise findSecorEntrepriseByLibelleSecteur(String libelleSecteur);
}

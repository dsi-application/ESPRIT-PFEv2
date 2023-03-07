package com.esprit.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Salle;


@Repository
public interface SalleRepository extends JpaRepository<Salle, String>
{
	Salle findByCodeSalle(String codeSalle);
}

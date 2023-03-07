package com.esprit.gdp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Societe;


@Repository
public interface SocieteRepository extends JpaRepository<Societe, String>
{
	@Query("Select s.dureePFE from Societe s where s.codeSoc = '01'")
	Integer findTraineeDuration();
}

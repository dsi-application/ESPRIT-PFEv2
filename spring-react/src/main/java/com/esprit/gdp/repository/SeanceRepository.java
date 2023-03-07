package com.esprit.gdp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Societe;
import com.esprit.gdp.models.edt.Seance;


@Repository
public interface SeanceRepository extends JpaRepository<Seance, String>
{
	//@Query("Select s from Seance s where s.codeseance = '01'")
	Seance findSeanceByCodeseance(String codeseance);
}

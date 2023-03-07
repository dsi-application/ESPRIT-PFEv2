package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Avenant;
import com.esprit.gdp.models.Classe;
@Repository
public interface ClasseRepository extends JpaRepository<Classe, String>{
	
	@Query("SELECT DISTINCT c.codeCl FROM Classe c where c.codeCl like '5%' ")
	List<String> findDistinctcodeCl();
	
	@Query("SELECT DISTINCT c.libSpecialite ,c.codeSpecialite FROM Classe c ")
	List<Object[]> findDistinctlibSpecialites();
}

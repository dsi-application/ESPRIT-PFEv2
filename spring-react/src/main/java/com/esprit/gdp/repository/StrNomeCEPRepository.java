package com.esprit.gdp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.StrNomeCEP;
import com.esprit.gdp.models.StrNomeCEPId;


@Repository
public interface StrNomeCEPRepository extends JpaRepository<StrNomeCEP, StrNomeCEPId>
{
	
	@Query("Select distinct snc.nomSTRCEP from StrNomeCEP snc where snc.sncepi.anneeDeb=?1 order by snc.nomSTRCEP")
	List<String> listLibCEPByYear(String anneeDeb);
	
}

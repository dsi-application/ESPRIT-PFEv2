package com.esprit.gdp.repository;

import com.esprit.gdp.models.SurveyFirstJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SurveyFirstJobRepository extends JpaRepository<SurveyFirstJob, Integer>{
	
	@Query("SELECT sfj.idSurvey FROM SurveyFirstJob sfj where sfj.idEt =?1")
	List<Integer> findIdSurveyByStudent(String idEt);

}
